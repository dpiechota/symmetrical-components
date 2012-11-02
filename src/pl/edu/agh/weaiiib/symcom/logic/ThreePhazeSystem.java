package pl.edu.agh.weaiiib.symcom.logic;

import org.apache.commons.math3.complex.Complex;
import org.apache.log4j.Logger;

public class ThreePhazeSystem {
	static final Logger logger = Logger.getLogger(Main.class);

	private Complex F1;
	private Complex F2;
	private Complex F3;

	public ThreePhazeSystem(Complex F1, Complex F2, Complex F3) {
		setF1(F1);
		setF2(F2);
		setF3(F3);
	}

	public Complex getF1() {
		return F1;
	}

	public void setF1(Complex f1) {
		F1 = f1;
	}

	public Complex getF2() {
		return F2;
	}

	public void setF2(Complex f2) {
		F2 = f2;
	}

	public Complex getF3() {
		return F3;
	}

	public void setF3(Complex f3) {
		F3 = f3;
	}

}
