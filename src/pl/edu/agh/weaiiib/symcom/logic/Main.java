package pl.edu.agh.weaiiib.symcom.logic;

import java.awt.BorderLayout;
import java.io.IOException;

import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.complex.ComplexUtils;
import org.apache.commons.math3.util.FastMath;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import pl.edu.agh.weaiiib.symcom.plots.VectorChart;
import pl.edu.agh.weaiiib.symcom.views.ChartFrame;

public class Main {
	static final Logger logger = Logger.getLogger(Main.class);

	/**
	 * @param args
	 */
	/***
	public static void main(String[] args) throws IOException,
			InterruptedException {
		DOMConfigurator.configure("log4j.xml");

		Complex fA = ComplexUtils.polar2Complex(100, FastMath.toRadians(0));
		Complex fB = ComplexUtils.polar2Complex(50, FastMath.toRadians(90));
		Complex fC = ComplexUtils.polar2Complex(100, FastMath.toRadians(-120));

		logger.info("FA = " + fA);
		logger.info("FB = " + fB);
		logger.info("FC = " + fC);
		
		VectorChart vectorABC = new VectorChart(fA, fC, fB);

		ChartFrame vectorchartjframe = new ChartFrame(
				"Fazory faz A, B, C", 100, 0);
		vectorchartjframe.add(vectorABC.getChartPanel(), BorderLayout.CENTER);
		vectorchartjframe.setVisible(true);

		SymmetricalComponents symcom = new SymmetricalComponents(fA, fB, fC);

		VectorChart vectorABC_0 = new VectorChart(symcom.getfA_0(),
				symcom.getfB_0(), symcom.getfC_0());
		ChartFrame vectorchartjframe_0 = new ChartFrame(
				"Kolejnoœæ zerowa", 500, 0);
		vectorchartjframe_0.add(vectorABC_0.getChartPanel(),
				BorderLayout.CENTER);
		vectorchartjframe_0.setVisible(true);

		VectorChart vectorABC_1 = new VectorChart(symcom.getfA_1(),
				symcom.getfB_1(), symcom.getfC_1());
		ChartFrame vectorchartjframe_1 = new ChartFrame(
				"Kolejnoœæ zgodna", 900, 0);
		vectorchartjframe_1.add(vectorABC_1.getChartPanel(),
				BorderLayout.CENTER);
		vectorchartjframe_1.setVisible(true);

		VectorChart vectorABC_2 = new VectorChart(symcom.getfA_2(),
				symcom.getfB_2(), symcom.getfC_2());
		ChartFrame vectorchartjframe_2 = new ChartFrame(
				"Kolejnoœæ przeciwna", 100, 400);
		vectorchartjframe_2.add(vectorABC_2.getChartPanel(),
				BorderLayout.CENTER);
		vectorchartjframe_2.setVisible(true);

		VectorChart vectorABC_spr = new VectorChart(
				((symcom.getfA_0().add(symcom.getfA_1())).add(symcom.getfA_2())),
				((symcom.getfC_0().add(symcom.getfC_1())).add(symcom.getfC_2())),
				((symcom.getfB_0().add(symcom.getfB_1())).add(symcom.getfB_2())));
		ChartFrame vectorchartjframe_spr = new ChartFrame(
				"Sprawdzenie", 500, 400);
		vectorchartjframe_spr.add(vectorABC_spr.getChartPanel(),
				BorderLayout.CENTER);
		vectorchartjframe_spr.setVisible(true);

	}
	*///
}
