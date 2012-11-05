package pl.edu.agh.weaiiib.symcom.logic;

import static java.lang.Math.PI;
import static java.lang.Math.sin;
import static org.math.array.DoubleArray.increment;

import java.awt.Color;

import org.apache.commons.math3.complex.Complex;
import org.apache.log4j.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.DefaultXYDataset;

public class XYChart {

	static final Logger logger = Logger.getLogger(XYChart.class);

	private static final double FREQ = 50;
	private static final String TITLE = "Przebieg czasowy faz F1, F2, F3";
	private static final double[] LAxis = increment(0.0, 0.0001, 0.03);

	private Complex fA;
	private Complex fB;
	private Complex fC;

	private ChartPanel chartPanel;

	// Class Constructor
	public XYChart(Complex fA, Complex fB, Complex fC) {

		//DOMConfigurator.configure("log4j.xml");
		setfA(fA);
		setfB(fB);
		setfC(fC);

		setChartPanel(createChart());
		getChartPanel().setMouseWheelEnabled(true);
	}

	public Complex getF1() {
		return fA;
	}

	public void setfA(Complex f1) {
		fA = f1;
	}

	public Complex getF2() {
		return fB;
	}

	public void setfB(Complex f2) {
		fB = f2;
	}

	public Complex getF3() {
		return fC;
	}

	public void setfC(Complex f3) {
		fC = f3;
	}

	public ChartPanel getChartPanel() {
		return chartPanel;
	}

	public void setChartPanel(ChartPanel chartPanel) {
		this.chartPanel = chartPanel;
	}

	public DefaultXYDataset createDataset() {
		DefaultXYDataset dataset = new DefaultXYDataset();

		double[][] L1Values = new double[2][LAxis.length];
		double[][] L2Values = new double[2][LAxis.length];
		double[][] L3Values = new double[2][LAxis.length];

		L1Values[0] = L2Values[0] = L3Values[0] = LAxis;

		for (int i = 0; i < LAxis.length; i++) {

			L1Values[1][i] = getF1().abs()
					* sin(L1Values[0][i] * 2 * PI * FREQ
							+ getF1().getArgument());
			L2Values[1][i] = getF2().abs()
					* sin(L1Values[0][i] * 2 * PI * FREQ
							+ getF2().getArgument());
			L3Values[1][i] = getF3().abs()
					* sin(L1Values[0][i] * 2 * PI * FREQ
							+ getF3().getArgument());

			logger.debug("XY Data Set F1" + L1Values[0][i] + " "
					+ L1Values[1][i]);
			logger.debug("XY Data Set F1" + L2Values[0][i] + " "
					+ L2Values[1][i]);
			logger.debug("XY Data Set F1" + L3Values[0][i] + " "
					+ L3Values[1][i]);
		}

		dataset.addSeries("F1", L1Values);
		dataset.addSeries("F2", L2Values);
		dataset.addSeries("F3", L3Values);
		return dataset;
	}

	public ChartPanel createChart() {
		DefaultXYDataset dataset = createDataset();

		// Create the chart
		JFreeChart chart = ChartFactory.createXYLineChart(TITLE, // The chart
																	// title
				"Czas [s]", // x axis label
				"Amplituda", // y axis label
				dataset, // The dataset for the chart
				PlotOrientation.VERTICAL, true, // Is a legend required?
				false, // Use tooltips
				false // Configure chart to generate URLs?
				);

		chart.setBackgroundPaint(Color.white);

		XYPlot plot = chart.getXYPlot();

		plot.setBackgroundPaint(Color.white);
		plot.setDomainGridlinePaint(Color.lightGray);
		plot.setRangeGridlinePaint(Color.lightGray);
		return new ChartPanel(chart);
	}
}