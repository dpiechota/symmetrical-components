package pl.edu.agh.weaiiib.symcom.datainput;

/*
/*************************************************************************
 * 
 * Compute the FFT and inverse FFT of a length N complex sequence. Bare bones
 * implementation that runs in O(N log N) time. Our goal is to optimize the
 * clarity of the code, rather than performance.
 * 
 * Limitations ----------- - assumes N is a power of 2
 * 
 * - not the most memory efficient algorithm (because it uses an object type for
 * representing complex numbers and because it re-allocates memory for the
 * subarray, instead of doing in-place or reusing a single temporary array)
 * 
 *************************************************************************
 */
import org.apache.commons.math3.complex.Complex;
import org.apache.log4j.Logger;

public class FFT {
	private SampledData sampledData;
	private Complex[] fft_A;
	private Complex[] fft_B;
	private Complex[] fft_C;
	public Double Freq;

	public FFT(SampledData sampledData_){
		sampledData = sampledData_;
		setFft_A(fft(sampledData.getPhase_A_samples().toArray(new Complex[sampledData.getPhase_A_samples().size()])));
		setFft_B(fft(sampledData.getPhase_B_samples().toArray(new Complex[sampledData.getPhase_B_samples().size()])));
		setFft_C(fft(sampledData.getPhase_C_samples().toArray(new Complex[sampledData.getPhase_C_samples().size()])));
	}
	
	public void printFFT(){
		for (Complex i : this.fft_A)
		    System.out.println(i);
	}
	// compute the FFT of x[], assuming its length is a power of 2
	public static Complex[] fft(Complex[] x) {
		int N = x.length;

		// base case
		if (N == 1)
			return new Complex[] { x[0] };

		// radix 2 Cooley-Tukey FFT
		if (N % 2 != 0) {
			throw new RuntimeException("N is not a power of 2");
		}

		// fft of even terms
		Complex[] even = new Complex[N / 2];
		for (int k = 0; k < N / 2; k++) {
			even[k] = x[2 * k];
		}
		Complex[] q = fft(even);

		// fft of odd terms
		Complex[] odd = even; // reuse the array
		for (int k = 0; k < N / 2; k++) {
			odd[k] = x[2 * k + 1];
		}
		Complex[] r = fft(odd);

		// combine
		Complex[] y = new Complex[N];
		for (int k = 0; k < N / 2; k++) {
			double kth = -2 * k * Math.PI / N;
			Complex wk = new Complex(Math.cos(kth), Math.sin(kth));
			y[k] = q[k].add(wk.multiply(r[k]));
			y[k + N / 2] = q[k].subtract(wk.multiply(r[k]));
		}
		return y;
	}

	// compute the inverse FFT of x[], assuming its length is a power of 2
	public static Complex[] ifft(Complex[] x) {
		int N = x.length;
		Complex[] y = new Complex[N];

		// take conjugate
		for (int i = 0; i < N; i++) {
			y[i] = x[i].conjugate();
		}

		// compute forward FFT
		y = fft(y);

		// take conjugate again
		for (int i = 0; i < N; i++) {
			y[i] = y[i].conjugate();
		}

		// divide by N
		for (int i = 0; i < N; i++) {
			y[i] = y[i].multiply(1.0 / N);
		}

		return y;

	}
	
	// compute the circular convolution of x and y
	public static Complex[] cconvolve(Complex[] x, Complex[] y) {

		// should probably pad x and y with 0s so that they have same length
		// and are powers of 2
		if (x.length != y.length) {
			throw new RuntimeException("Dimensions don't agree");
		}

		int N = x.length;

		// compute FFT of each sequence
		Complex[] a = fft(x);
		Complex[] b = fft(y);

		// point-wise multiply
		Complex[] c = new Complex[N];
		for (int i = 0; i < N; i++) {
			c[i] = a[i].multiply(b[i]);
		}

		// compute inverse FFT
		return ifft(c);
	}
	// compute the linear convolution of x and y
	public static Complex[] convolve(Complex[] x, Complex[] y) {
		Complex ZERO = new Complex(0, 0);

		Complex[] a = new Complex[2 * x.length];
		for (int i = 0; i < x.length; i++)
			a[i] = x[i];
		for (int i = x.length; i < 2 * x.length; i++)
			a[i] = ZERO;

		Complex[] b = new Complex[2 * y.length];
		for (int i = 0; i < y.length; i++)
			b[i] = y[i];
		for (int i = y.length; i < 2 * y.length; i++)
			b[i] = ZERO;

		return cconvolve(a, b);
	}
	
	// display an array of Complex numbers to standard output
	public static void show(Complex[] x, String title) {
		System.out.println(title);
		System.out.println("-------------------");
		for (int i = 0; i < x.length; i++) {
			System.out.println(x[i]);
		}
		System.out.println();
	}
	
	public Complex findMax(Complex[] y){
		Integer N = y.length;
		Integer nth = 0;
		Double max = 0.0;
		for (int i = 0; i < N/2; i++) {
			if (i > 0 && (y[i].abs() > y[nth].abs())){
				nth = i;
				max = y[i].abs();
				System.out.println("Max abs = " + max + " ; " + "Probka = " + nth);
				System.out.println("Re = " + y[nth].getReal() + " ; " + "IM = " + y[nth].getImaginary() + " Freq. = " + (nth * sampledData.getSamplingFreq()) / y.length);
				this.Freq = (nth * sampledData.getSamplingFreq()) / y.length;
			}
		}
		return y[nth];		
	}
	
	public Complex[] getPhases(){
		Complex[] FABC = new Complex[3];
		FABC[0] = findMax(this.fft_A);
		FABC[1] = findMax(this.fft_B);
		FABC[2] = findMax(this.fft_C);
		return FABC;
	}
	
	public Complex compute(Integer N) {
		Complex[] x = new Complex[N];

		Double Fs = 1000.0; // Sampling frequency
		Double T = 1 / Fs; // Sample time
		
		// original data
		for (int i = 0; i < N; i++) {
			//System.out.println(0.7 * Math.sin(2 * Math.PI * 50 * i * T));
			x[i] = new Complex(Math.sin((2 * Math.PI * 50 * i * T)), 0);
			// x[i] = new Complex(-2 * Math.random() + 1, 0);
		}
		//show(x, "x");

		// FFT of original data
		Complex[] y = fft(x);
		// show(y, "y = fft(x)");
		
		Double max = 0.0;
		Integer nth = 0;
		
		for (int i = 0; i < N/2; i++) {
			if (i > 0 && y[i].abs() > y[i-1].abs()){
				max = y[i].abs();
				nth = i;
				//System.out.println("Max abs = " + max + " ; " + "Probka = " + nth);
			}
		}
		return y[nth];
		
		/*
		// take inverse FFT
		Complex[] z = ifft(y);
		// show(z, "z = ifft(y)");
		
		/*
		 * http://stackoverflow.com/questions/4364823/how-to-get-frequency-from-fft-result
		 * The first bin in the FFT is DC (0 Hz), the second bin is Fs / N, where Fs is the sample rate and N is the size of the FFT.
		 * The next bin is 2 * Fs / N. To express this in general terms, the nth bin is n * Fs / N.
		 * So if your sample rate, Fs is say 44.1 kHz and your FFT size, N is 1024, then the FFT output bins are at:
		 * 	0:   0 * 44100 / 1024 =     0.0 Hz
		 * 	1:   1 * 44100 / 1024 =    43.1 Hz
		 * 	2:   2 * 44100 / 1024 =    86.1 Hz
		 * 	3:   3 * 44100 / 1024 =   129.2 Hz
		 * 	4: 	...
		 * 		...
		 * 	511: 511 * 44100 / 1024 = 22006.9 Hz
		 */
		/*
		Double freq = (nth * Fs) / N;
		Double magnitude = y[nth].abs();
		Double phaze = y[nth].phase();
		System.out.println("Freq = " + freq + " ; " + ". Magnitude = " + magnitude + ". Phaze = " + phaze);

		// circular convolution of x with itself
		// Complex[] c = cconvolve(x, x);
		// show(c, "c = cconvolve(x, x)");

		// linear convolution of x with itself
		// Complex[] d = convolve(x, x);
		// show(d, "d = convolve(x, x)");
		 *
		 */
	}
	public Complex[] getFft_A() {
		return fft_A;
	}
	public void setFft_A(Complex[] fft_A) {
		this.fft_A = fft_A;
	}
	public Complex[] getFft_B() {
		return fft_B;
	}
	public void setFft_B(Complex[] fft_B) {
		this.fft_B = fft_B;
	}
	public Complex[] getFft_C() {
		return fft_C;
	}
	public void setFft_C(Complex[] fft_C) {
		this.fft_C = fft_C;
	}

}
