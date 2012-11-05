package pl.edu.agh.weaiiib.symcom.logic;

import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.complex.ComplexUtils;
import org.apache.commons.math3.linear.Array2DRowFieldMatrix;
import org.apache.commons.math3.linear.FieldDecompositionSolver;
import org.apache.commons.math3.linear.FieldLUDecomposition;
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

		FieldMatrix<Complex> phazeVectorsM = new Array2DRowFieldMatrix<Complex>(
				phazeVectors);

		logger.debug("Phaze rows = " + phazeVectorsM.getRowDimension()
				+ " cols = " + phazeVectorsM.getColumnDimension());
		logger.info("Phaze Matrix " + phazeVectorsM);

		FieldMatrix<Complex> SM = TransformMatrix.SM;

		setSymmetricalCompMatrix(SM.multiply(phazeVectorsM));
		logger.info("Symetrical Matrix " + getSymmetricalCompMatrix());

		// setThreePhazeMatrix((FieldDecompositionSolver.getInverse(SM)).multiply(getSymmetricalCompMatrix()));
		FieldDecompositionSolver<Complex> solver = (FieldDecompositionSolver<Complex>) new org.apache.commons.math3.linear.FieldLUDecomposition<Complex>(SM);
		setThreePhazeMatrix(solver.getInverse());
		logger.info("Three Phaze Matrix " + getThreePhazeMatrix());
		
		setfA_0(symmetricalCompMatrix.getRow(0)[0]);
		setfA_1(symmetricalCompMatrix.getRow(1)[0]);
		setfA_2(symmetricalCompMatrix.getRow(2)[0]);

		setfB_0(getfA_0());
		setfB_1(ComplexUtils.polar2Complex(getfA_1().abs(), getfA_1()
				.getArgument() + FastMath.toRadians(-120)));
		setfB_2(ComplexUtils.polar2Complex(getfA_2().abs(), getfA_2()
				.getArgument() + FastMath.toRadians(120)));

		setfC_0(getfA_0());
		setfC_1(ComplexUtils.polar2Complex(getfA_1().abs(), getfA_1()
				.getArgument() + FastMath.toRadians(-240)));
		setfC_2(ComplexUtils.polar2Complex(getfA_2().abs(), getfA_2()
				.getArgument() + FastMath.toRadians(240)));
		logger.info(this.toString());
	}

	public String toString() {
		return "\nF_A_0 = " + getfA_0() + " abs = " + getfA_0().abs()
				+ " fi = " + FastMath.toDegrees(getfA_0().getArgument())
				+ "\nF_A_1 = " + getfA_1() + " abs = " + getfB_1().abs()
				+ " fi = " + FastMath.toDegrees(getfB_1().getArgument())
				+ "\nF_A_2 = " + getfA_2() + " abs = " + getfC_2().abs()
				+ " fi = " + FastMath.toDegrees(getfC_2().getArgument())
				+ "\nF_B_0 = " + getfB_0() + " abs = " + getfA_0().abs()
				+ " fi = " + FastMath.toDegrees(getfA_0().getArgument())
				+ "\nF_B_1 = " + getfB_1() + " abs = " + getfB_1().abs()
				+ " fi = " + FastMath.toDegrees(getfB_1().getArgument())
				+ "\nF_B_2 = " + getfB_2() + " abs = " + getfC_2().abs()
				+ " fi = " + FastMath.toDegrees(getfC_2().getArgument())
				+ "\nF_C_0 = " + getfC_0() + " abs = " + getfA_0().abs()
				+ " fi = " + FastMath.toDegrees(getfA_0().getArgument())
				+ "\nF_C_1 = " + getfC_1() + " abs = " + getfB_1().abs()
				+ " fi = " + FastMath.toDegrees(getfB_1().getArgument())
				+ "\nF_C_2 = " + getfA_2() + " abs = " + getfC_2().abs()
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
