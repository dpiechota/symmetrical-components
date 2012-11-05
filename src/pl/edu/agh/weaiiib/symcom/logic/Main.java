package pl.edu.agh.weaiiib.symcom.logic;

import java.awt.BorderLayout;
import java.io.IOException;

import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.complex.ComplexUtils;
import org.apache.commons.math3.util.FastMath;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import pl.edu.agh.weaiiib.symcom.views.VectorChartJFrame;

public class Main {
	static final Logger logger = Logger.getLogger(Main.class);
	double x = FastMath.toDegrees(5.0);

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException,
			InterruptedException {
		DOMConfigurator.configure("log4j.xml");

		Complex fA = ComplexUtils.polar2Complex(100, FastMath.toRadians(10));
		Complex fB = ComplexUtils.polar2Complex(200, FastMath.toRadians(-240));
		Complex fC = ComplexUtils.polar2Complex(70, FastMath.toRadians(240));

		logger.info("FA = " + fA);
		logger.info("FB = " + fB);
		logger.info("FC = " + fC);

		VectorChart vectorABC = new VectorChart(fA, fC, fB);

		VectorChartJFrame vectorchartjframe = new VectorChartJFrame(
				"Fazory faz A, B, C", 100, 0);
		vectorchartjframe.add(vectorABC.getChartPanel(), BorderLayout.CENTER);
		//vectorchartjframe.setVisible(true);

		SymmetricalComponents symcom = new SymmetricalComponents(fA, fC, fB);

		VectorChart vectorABC_0 = new VectorChart(symcom.getfA_0(),
				symcom.getfB_0(), symcom.getfC_0());
		VectorChartJFrame vectorchartjframe_0 = new VectorChartJFrame(
				"Kolejno�� zerowa", 500, 0);
		vectorchartjframe_0.add(vectorABC_0.getChartPanel(),
				BorderLayout.CENTER);
		//vectorchartjframe_0.setVisible(true);

		VectorChart vectorABC_1 = new VectorChart(symcom.getfA_1(),
				symcom.getfB_1(), symcom.getfC_1());
		VectorChartJFrame vectorchartjframe_1 = new VectorChartJFrame(
				"Kolejno�� zgodna", 900, 0);
		vectorchartjframe_1.add(vectorABC_1.getChartPanel(),
				BorderLayout.CENTER);
		//vectorchartjframe_1.setVisible(true);

		VectorChart vectorABC_2 = new VectorChart(symcom.getfA_2(),
				symcom.getfB_2(), symcom.getfC_2());
		VectorChartJFrame vectorchartjframe_2 = new VectorChartJFrame(
				"Kolejno�� przeciwna", 100, 400);
		vectorchartjframe_2.add(vectorABC_2.getChartPanel(),
				BorderLayout.CENTER);
		//vectorchartjframe_2.setVisible(true);

		VectorChart vectorABC_spr = new VectorChart(
				((symcom.getfA_0().add(symcom.getfA_1())).add(symcom.getfA_2()))
						.multiply(0.33),
				((symcom.getfB_0().add(symcom.getfB_1())).add(symcom.getfB_2()))
						.multiply(0.33),
				((symcom.getfC_0().add(symcom.getfC_1())).add(symcom.getfC_2()))
						.multiply(0.33));
		VectorChartJFrame vectorchartjframe_spr = new VectorChartJFrame(
				"Sprawdzenie", 500, 400);
		vectorchartjframe_spr.add(vectorABC_spr.getChartPanel(),
				BorderLayout.CENTER);
		//vectorchartjframe_spr.setVisible(true);

	}
}
