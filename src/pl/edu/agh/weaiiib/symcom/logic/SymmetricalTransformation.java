package pl.edu.agh.weaiiib.symcom.logic;

import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.complex.ComplexUtils;
import org.apache.commons.math3.linear.Array2DRowFieldMatrix;
import org.apache.commons.math3.linear.FieldMatrix;
import org.apache.commons.math3.util.FastMath;
import org.apache.log4j.Logger;

public class SymmetricalTransformation {
	static final Logger logger = Logger.getLogger(SymmetricalTransformation.class);
	
	private static Complex  factor = new Complex(0.333333, 0);
	
	/*
	 * a = -0.5 + j120
	 */
	public static final Complex  a = ComplexUtils.polar2Complex(1,FastMath.toRadians(120));
	
	/*
	 * a = -0.5 - j120
	 */
	public static final Complex a2 = ComplexUtils.polar2Complex(1,FastMath.toRadians(-120));

	private static Complex[][] S_1 = {{ Complex.ONE, Complex.ONE, Complex.ONE },
									{ Complex.ONE,           a,          a2 },
									{ Complex.ONE,          a2,           a }};
	
	private static Complex[][] S = {{ Complex.ONE, Complex.ONE, Complex.ONE },
									{ Complex.ONE,          a2,           a },
									{ Complex.ONE,           a,          a2 }};
	/*
	 * The S^-1 Matrix of of symmetrical components transformation.
	 * symmetricalCompMatrix = S^-1 * threePhazeMatrix
	 */
	public static final FieldMatrix<Complex> S_1M = (new Array2DRowFieldMatrix<Complex>(S_1)).scalarMultiply(factor);
	
	/*
	 * The S Matrix of symmetrical components transformation.
	 * threePhazeMatrix = S * symmetricalCompMatrix
	 */
	public static final FieldMatrix<Complex> SM = new Array2DRowFieldMatrix<Complex>(S);
}
