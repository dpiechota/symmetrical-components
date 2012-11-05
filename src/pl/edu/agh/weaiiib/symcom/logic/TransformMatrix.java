package pl.edu.agh.weaiiib.symcom.logic;

import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.complex.ComplexUtils;
import org.apache.commons.math3.linear.Array2DRowFieldMatrix;
import org.apache.commons.math3.linear.FieldMatrix;
import org.apache.commons.math3.util.FastMath;

public class TransformMatrix {

	private static Complex a = ComplexUtils.polar2Complex(1,
			FastMath.toRadians(120));
	private static Complex a2 = ComplexUtils.polar2Complex(1,
			FastMath.toRadians(-120));

	private static Complex[] S1 = { Complex.ONE, Complex.ONE, Complex.ONE };
	private static Complex[] S2 = { Complex.ONE, a, a2 };
	private static Complex[] S3 = { Complex.ONE, a2, a };
	private static Complex[][] S = { S1, S2, S3 };
	public static final FieldMatrix<Complex> SM = new Array2DRowFieldMatrix<Complex>(S);
}
