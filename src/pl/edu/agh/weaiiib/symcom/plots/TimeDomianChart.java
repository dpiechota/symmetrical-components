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
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class TimeDomianChart extends ComplexChart {
	
	protected final static String TITLE = "Time plots of phases A, B, C";
	
	public TimeDomianChart(Complex fA, Complex fB, Complex fC, Double frequency) {
		super(fA, fB, fC, frequency);
		setChartPanel(createChart());
		getChartPanel().setMouseWheelEnabled(true);
	}

	private double FREQ = getFrequency();
	private static final double[] LAxis = increment(0.0, 0.0001, 0.03);

	public XYDataset createXYDataset() {

		final XYSeriesCollection collection = new XYSeriesCollection();
		
		final XYSeries seriesA = new XYSeries("Phase A. Magnitude = " + String.format("%.2f", getfA().abs()) + ". Arg = " + String.format("%.2f", FastMath.toDegrees(getfA().getArgument())));
		final XYSeries seriesB = new XYSeries("Phase B. Magnitude = " + String.format("%.2f", getfB().abs()) + ". Arg = " + String.format("%.2f", FastMath.toDegrees(getfB().getArgument())));
		final XYSeries seriesC = new XYSeries("Phase C. Magnitude = " + String.format("%.2f", getfC().abs()) + ". Arg = " + String.format("%.2f", FastMath.toDegrees(getfC().getArgument())));
		
		for (int i = 0; i < LAxis.length; i++) {

			seriesA.add(LAxis[i], getfA().abs()* sin(LAxis[i] * 2 * PI * FREQ+ getfA().getArgument()));
			seriesB.add(LAxis[i], getfB().abs()* sin(LAxis[i] * 2 * PI * FREQ+ getfB().getArgument()));
			seriesC.add(LAxis[i], getfC().abs()* sin(LAxis[i] * 2 * PI * FREQ+ getfC().getArgument()));
		}

		collection.addSeries(seriesA);
		collection.addSeries(seriesB);
		collection.addSeries(seriesC);
		return collection;
	}

	public static double[] increment(double begin, double pitch, double end) {
		double[] array = new double[(int) ((end - begin) / pitch)];
		for (int i = 0; i < array.length; i++) {
			array[i] = begin + i * pitch;
		}
		return array;
	}

	public ChartPanel createChart() {
		XYDataset dataset = createXYDataset();

		// Create the chart
		JFreeChart chart = ChartFactory.createXYLineChart(TITLE, // The chart
																	// title
				"Time [s]", // x axis label
				"Magnitude", // y axis label
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