package pl.edu.agh.weaiiib.symcom.plots;

import static java.lang.Math.PI;
import static java.lang.Math.sin;

import java.awt.Color;

import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.util.FastMath;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.DefaultXYDataset;

public class TimeDomianChart extends VectorChart {

	public TimeDomianChart(Complex fA, Complex fB, Complex fC) {
		super(fA, fB, fC);
	}

	private static final double FREQ = 50;
	private static final double[] LAxis = increment(0.0, 0.0001, 0.03);

	public DefaultXYDataset createXYDataset() {
		DefaultXYDataset dataset = new DefaultXYDataset();

		double[][] L1Values = new double[2][LAxis.length];
		double[][] L2Values = new double[2][LAxis.length];
		double[][] L3Values = new double[2][LAxis.length];

		L1Values[0] = L2Values[0] = L3Values[0] = LAxis;

		for (int i = 0; i < LAxis.length; i++) {

			L1Values[1][i] = getfA().abs()
					* sin(L1Values[0][i] * 2 * PI * FREQ
							+ getfA().getArgument());
			L2Values[1][i] = getfB().abs()
					* sin(L1Values[0][i] * 2 * PI * FREQ
							+ getfB().getArgument());
			L3Values[1][i] = getfC().abs()
					* sin(L1Values[0][i] * 2 * PI * FREQ
							+ getfC().getArgument());

			logger.debug("XY Data Set F1" + L1Values[0][i] + " "
					+ L1Values[1][i]);
			logger.debug("XY Data Set F1" + L2Values[0][i] + " "
					+ L2Values[1][i]);
			logger.debug("XY Data Set F1" + L3Values[0][i] + " "
					+ L3Values[1][i]);
		}

		dataset.addSeries(
				"Faza A. Modul = "
						+ String.format("%.2f", getfA().abs())
						+ ". Arg = "
						+ String.format("%.2f",
								FastMath.toDegrees(getfA().getArgument())),
				L1Values);
		dataset.addSeries(
				"Faza B. Modul = "
						+ String.format("%.2f", getfB().abs())
						+ ". Arg = "
						+ String.format("%.2f",
								FastMath.toDegrees(getfB().getArgument())),
				L2Values);
		dataset.addSeries(
				"Faza C. Modul = "
						+ String.format("%.2f", getfC().abs())
						+ ". Arg = "
						+ String.format("%.2f",
								FastMath.toDegrees(getfC().getArgument())),
				L3Values);
		return dataset;
	}

	public static double[] increment(double begin, double pitch, double end) {
		double[] array = new double[(int) ((end - begin) / pitch)];
		for (int i = 0; i < array.length; i++) {
			array[i] = begin + i * pitch;
		}
		return array;
	}

	public ChartPanel createChart() {
		DefaultXYDataset dataset = createXYDataset();

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