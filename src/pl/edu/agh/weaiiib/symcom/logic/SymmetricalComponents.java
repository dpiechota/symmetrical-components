package pl.edu.agh.weaiiib.symcom.logic;

import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.linear.Array2DRowFieldMatrix;
import org.apache.commons.math3.linear.FieldMatrix;
import org.apache.commons.math3.util.FastMath;
import org.apache.log4j.Logger;

public class SymmetricalComponents {

	static final Logger logger = Logger.getLogger(SymmetricalComponents.class);

	private Complex fA_0;
	private Complex fB_0;
	private Complex fC_0;

	private Complex fA_1;
	private Complex fB_1;
	private Complex fC_1;

	private Complex fA_2;
	private Complex fB_2;
	private Complex fC_2;

	private FieldMatrix<Complex> symmetricalCompMatrix;
	private FieldMatrix<Complex> threePhazeMatrix;

	public SymmetricalComponents(Complex fA, Complex fB, Complex fC) {

		Complex[] phazeVectors = { fA, fB, fC };

		/*
		 * Matrix containing unbalanced 3 phase system, it contains complex
		 * numbers describing 3 phases.
		 */
		FieldMatrix<Complex> phazeVectorsM = new Array2DRowFieldMatrix<Complex>(
				phazeVectors);

		logger.debug("Phaze rows = " + phazeVectorsM.getRowDimension()
				+ " cols = " + phazeVectorsM.getColumnDimension());
		logger.info("Phaze Matrix " + phazeVectorsM);
		
		/*
		 * The S Matrix of symmetrical components transformation.
		 * threePhazeMatrix = S * symmetricalCompMatrix
		 */
		FieldMatrix<Complex> SM = SymmetricalTransformation.SM;
		
		/*
		 * The S^-1 Matrix of of symmetrical components transformation.
		 * symmetricalCompMatrix = S^-1 * threePhazeMatrix
		 */
		FieldMatrix<Complex> S_1M = SymmetricalTransformation.S_1M;
		
		/*
		 * a = -0.5 + j120
		 */
		Complex a = SymmetricalTransformation.a;
		
		/*
		 * a = -0.5 - j120
		 */
		Complex a2 = SymmetricalTransformation.a2;

		/*
		 * Compute symmetrical components matrix SymmetricalCompMatrix = S^-1 * threePhazeMatrix
		 */
		setSymmetricalCompMatrix(S_1M.multiply(phazeVectorsM));
		logger.info("Symetrical Matrix " + getSymmetricalCompMatrix());

		/*
		 * Compute original 3 phase matrix threePhazeMatrix = S * SymmetricalCompMatrix
		 */
		setThreePhazeMatrix(SM.multiply(getSymmetricalCompMatrix()));
		logger.info("Three Phaze Matrix " + getThreePhazeMatrix());

		setfA_0(symmetricalCompMatrix.getRow(0)[0]);
		setfA_1(symmetricalCompMatrix.getRow(1)[0]);
		setfA_2(symmetricalCompMatrix.getRow(2)[0]);

		setfB_0(getfA_0());
		setfB_1(getfA_1().multiply(a2));
		setfB_2(getfA_2().multiply(a));

		setfC_0(getfA_0());
		setfC_1(getfA_1().multiply(a));
		setfC_2(getfA_2().multiply(a2));
		logger.info(this.toString());
	}

	public String toString() {
		return "\nF_A_0 = " + getfA_0() + " abs = " + getfA_0().abs()
				+ " fi = " + FastMath.toDegrees(getfA_0().getArgument())
				+ "\nF_A_1 = " + getfA_1() + " abs = " + getfA_1().abs()
				+ " fi = " + FastMath.toDegrees(getfA_1().getArgument())
				+ "\nF_A_2 = " + getfA_2() + " abs = " + getfA_2().abs()
				+ " fi = " + FastMath.toDegrees(getfA_2().getArgument())
				+ "\nF_B_0 = " + getfB_0() + " abs = " + getfB_0().abs()
				+ " fi = " + FastMath.toDegrees(getfB_0().getArgument())
				+ "\nF_B_1 = " + getfB_1() + " abs = " + getfB_1().abs()
				+ " fi = " + FastMath.toDegrees(getfB_1().getArgument())
				+ "\nF_B_2 = " + getfB_2() + " abs = " + getfB_2().abs()
				+ " fi = " + FastMath.toDegrees(getfB_2().getArgument())
				+ "\nF_C_0 = " + getfC_0() + " abs = " + getfC_0().abs()
				+ " fi = " + FastMath.toDegrees(getfC_0().getArgument())
				+ "\nF_C_1 = " + getfC_1() + " abs = " + getfC_1().abs()
				+ " fi = " + FastMath.toDegrees(getfC_1().getArgument())
				+ "\nF_C_2 = " + getfC_2() + " abs = " + getfC_2().abs()
				+ " fi = " + FastMath.toDegrees(getfC_2().getArgument());
	}

	public Complex getfA_0() {
		return fA_0;
	}

	public void setfA_0(Complex fA_0) {
		this.fA_0 = fA_0;
	}

	public Complex getfA_1() {
		return fA_1;
	}

	public void setfA_1(Complex fA_1) {
		this.fA_1 = fA_1;
	}

	public Complex getfA_2() {
		return fA_2;
	}

	public void setfA_2(Complex fA_2) {
		this.fA_2 = fA_2;
	}

	public Complex getfB_0() {
		return fB_0;
	}

	public void setfB_0(Complex fB_0) {
		this.fB_0 = fB_0;
	}

	public Complex getfC_0() {
		return fC_0;
	}

	public void setfC_0(Complex fC_0) {
		this.fC_0 = fC_0;
	}

	public Complex getfB_1() {
		return fB_1;
	}

	public void setfB_1(Complex fB_1) {
		this.fB_1 = fB_1;
	}

	public Complex getfC_1() {
		return fC_1;
	}

	public void setfC_1(Complex fC_1) {
		this.fC_1 = fC_1;
	}

	public Complex getfB_2() {
		return fB_2;
	}

	public void setfB_2(Complex fB_2) {
		this.fB_2 = fB_2;
	}

	public Complex getfC_2() {
		return fC_2;
	}

	public void setfC_2(Complex fC_2) {
		this.fC_2 = fC_2;
	}

	public FieldMatrix<Complex> getSymmetricalCompMatrix() {
		return symmetricalCompMatrix;
	}

	public void setSymmetricalCompMatrix(
			FieldMatrix<Complex> symmetricalCompMatrix) {
		this.symmetricalCompMatrix = symmetricalCompMatrix;
	}

	public FieldMatrix<Complex> getThreePhazeMatrix() {
		return threePhazeMatrix;
	}

	public void setThreePhazeMatrix(FieldMatrix<Complex> threePhazeMatrix) {
		this.threePhazeMatrix = threePhazeMatrix;
	}
}
