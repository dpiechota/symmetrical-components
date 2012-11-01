package pl.edu.agh.weaiiib.elektrotechnika.symcom;

import static java.lang.Math.PI;
import static java.lang.Math.sin;
import static org.math.array.DoubleArray.increment;

import java.awt.Color;

import javax.swing.JFrame;

import org.apache.commons.math3.complex.Complex;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.DefaultXYDataset;

public class XYChart extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5428701242107845411L;
	private static final double FREQ = 50;
	static final Logger logger = Logger.getLogger(XYChart.class);

	private static final double[] LAxis = increment(0.0, 0.0001, 0.03);

	private Complex F1;
	private Complex F2;
	private Complex F3;

	// Date set for chart
	DefaultXYDataset dataset = new DefaultXYDataset();

	// Chart itself
	JFreeChart chart;

	// Class Constructor
	public XYChart(Complex F1, Complex F2, Complex F3) {

		DOMConfigurator.configure("log4j.xml");
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

	public void updateDataSet() {

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
	}

	public void printChart() {
		updateDataSet();
		// Create the chart
		chart = ChartFactory.createXYLineChart(
				"Przebieg czasowy faz F1, F2, F3", // The chart title
				"Czas [s]", // x axis label
				"Amplituda", // y axis label
				dataset, // The dataset for the chart
				PlotOrientation.VERTICAL, true, // Is a legend required?
				true, // Use tooltips
				false // Configure chart to generate URLs?
				);

		chart.setBackgroundPaint(Color.white);

		XYPlot plot = chart.getXYPlot();

		plot.setBackgroundPaint(Color.white);
		plot.setDomainGridlinePaint(Color.lightGray);
		plot.setRangeGridlinePaint(Color.lightGray);

		this.getContentPane().add(new ChartPanel(chart));
		this.setSize(700, 500);
		this.setTitle("Przebieg czasowy F1, F2, F3");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
}