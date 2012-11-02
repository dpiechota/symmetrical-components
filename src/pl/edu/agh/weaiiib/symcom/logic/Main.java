package pl.edu.agh.weaiiib.symcom.logic;

import java.io.IOException;

import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.complex.ComplexUtils;
import org.apache.commons.math3.util.FastMath;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

public class Main {
	static final Logger logger = Logger.getLogger(Main.class);
	double x = FastMath.toDegrees(5.0);

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException,
			InterruptedException {
		DOMConfigurator.configure("log4j.xml");

		Complex F1 = ComplexUtils.polar2Complex(230, FastMath.toRadians(0.0));
		Complex F2 = ComplexUtils.polar2Complex(230, FastMath.toRadians(120.0));
		Complex F3 = ComplexUtils
				.polar2Complex(230, FastMath.toRadians(-120.0));

		logger.info("F1 Argument" + F1.getArgument());
		logger.info("F2 Argument" + F2.getArgument());
		logger.info("F3 Argument" + F3.getArgument());

		// Print time dimension chart
		XYChart XYframe = new XYChart(F1, F2, F3);
		XYframe.prepareChart();

		// Print phasor chart
		VectorChart vector = new VectorChart(F1, F2, F3);
		vector.prepareChart();
	}

}
