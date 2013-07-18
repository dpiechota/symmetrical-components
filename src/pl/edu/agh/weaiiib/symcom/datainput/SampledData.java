package pl.edu.agh.weaiiib.symcom.datainput;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;

import org.apache.commons.math3.complex.Complex;
import org.apache.log4j.Logger;

public class SampledData {
	static final Logger logger = Logger.getLogger(SampledData.class);
	/*
	 * File containing sampled signal. By design dataFile is set in MainFrame
	 * Class by JFileChosser dialog. This class is created in MainFrame Class by
	 * action performed on Draw Plot button Constrains : dataFile format is CSV
	 * containing time samples and three phases samples:
	 * 
	 * time ,phaseA,phaseB,phaseC
	 * 
	 * 0.000200,1.7866,0.1666,5.6542 0.000400,1.8839,0.1556,5.9920
	 * 0.000600,1.9591,0.1300,6.2908 0.000800,2.0205,0.1099,6.5239
	 * 0.001000,2.0772,0.1032,6.7199 0.001200,2.1171,0.0812,6.8975
	 */

	private File dataFile;
	private ArrayList<Complex> phase_A_samples = new ArrayList<Complex>();
	private ArrayList<Complex> phase_B_samples = new ArrayList<Complex>();
	private ArrayList<Complex> phase_C_samples = new ArrayList<Complex>();
	private ArrayList<Double> timeSamples = new ArrayList<Double>();
	// Number of samples
	private Integer N;
	// Sampling frequency
	private Double SamplingFreq;

	public SampledData(File dataFile) throws IOException {
		setDataFile(dataFile);
		processFile();
		
		setSamplingFreq(1/(getTimeSamples().get(1) - getTimeSamples().get(0)));
		logger.debug("Sampling Frequency = " + getSamplingFreq());
	}

	private void processFile() throws IOException {

		try (InputStream fileInputStream = new FileInputStream(getDataFile())) {
			LineNumberReader  lineNumberReader = new LineNumberReader(new FileReader(getDataFile()));
			lineNumberReader.skip(Long.MAX_VALUE);
			Integer numberOfLines = lineNumberReader.getLineNumber() + 1;
			logger.debug("Number od lines in data file = " + numberOfLines);
			
			//Next power of 2 from the number of samples in the file
			//This will be numbr of samples used for computations
			setN(neareastPower2(numberOfLines));
			logger.debug("Max power of 2 from number of lines in file= " + getN());

			InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
			BufferedReader stream = new BufferedReader(inputStreamReader);

			/*
			 * Read line by line from dataFile and add to ArrayList of time and
			 * phases
			 */
			String line;
			Integer counter = 0;
			Integer numberOfSamples = getN();
			while (counter < numberOfSamples) {
				String[] split = stream.readLine().split(",");
				getTimeSamples().add(Double.parseDouble(split[0]));
				getPhase_A_samples().add(new Complex(Double.parseDouble(split[1]), 0));
				getPhase_B_samples().add(new Complex(Double.parseDouble(split[2]), 0));
				getPhase_C_samples().add(new Complex(Double.parseDouble(split[3]), 0));
				counter = counter + 1;
				//logger.debug("Samples : " + split + ". Counter = " + counter);
			}
		} catch (FileNotFoundException ex) {
			System.err.println("Missing file " + getDataFile().getAbsolutePath());
		}
		finally {
			logger.debug("Finished file processing");
		}
	}

	public static int neareastPower2(int in) {
		if (in <= 1) {
			return 1;
		}
		int result = 2;

		while (in > 3) {
			in = in >> 1;
			result = result << 1;
		}

		if (in == 3) {
			return (result << 1) / 2;
		} else {
			return result / 2;
		}
	}

	/*
	 * Setters and getters
	 */
	private File getDataFile() {
		return dataFile;
	}

	private void setDataFile(File dataFile) {
		this.dataFile = dataFile;
	}

	public ArrayList<Complex> getPhase_A_samples() {
		return phase_A_samples;
	}

	private void setPhase_A_samples(ArrayList<Complex> phase_A_samples) {
		this.phase_A_samples = phase_A_samples;
	}

	public ArrayList<Complex> getPhase_B_samples() {
		return phase_B_samples;
	}

	private void setPhase_B_samples(ArrayList<Complex> phase_B_samples) {
		this.phase_B_samples = phase_B_samples;
	}

	public ArrayList<Complex> getPhase_C_samples() {
		return phase_C_samples;
	}

	private void setPhase_C_samples(ArrayList<Complex> phase_C_samples) {
		this.phase_C_samples = phase_C_samples;
	}

	public ArrayList<Double> getTimeSamples() {
		return timeSamples;
	}

	private void setTimeSamples(ArrayList<Double> timeSamples) {
		this.timeSamples = timeSamples;
	}

	public Integer getN() {
		return N;
	}

	private void setN(Integer n) {
		N = n;
	}

	public Double getSamplingFreq() {
		return SamplingFreq;
	}

	private void setSamplingFreq(Double samplingFreq) {
		SamplingFreq = samplingFreq;
	}
}
