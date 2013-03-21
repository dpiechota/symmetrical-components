package pl.edu.agh.weaiiib.symcom.plots;

import java.awt.Color;

import org.apache.commons.math3.complex.Complex;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * This demo shows a simple bar chart created using the
 * {@link XYSeriesCollection} dataset.
 * 
 */
public class HistogramChart {
	private Complex[] fft;
	private Double samplingFreq;
	private String LEGENDTITLE = "";
	private ChartPanel chartPanel;
	private static final String TITLE = "Single-Sided Amplitude Spectrum of y(t)";
	private String Colour = "RED";

	/**
	 * Creates a new demo instance.
	 * 
	 * @param title
	 *            the frame title.
	 */
	public HistogramChart(Complex[] fft_a, Double Freq, String title,
			String colour) {
		Colour = colour;
		LEGENDTITLE = title;
		setFft_A(fft_a);
		samplingFreq = Freq;
		IntervalXYDataset dataset = createDataset();
		setChartPanel(createChart(dataset));
	}

	/**
	 * Creates a sample dataset.
	 * 
	 * @return A sample dataset.
	 */
	private IntervalXYDataset createDataset() {
		XYSeries seriesA = new XYSeries(LEGENDTITLE);
		for (int i = 0; i < getFft_A().length / 2; i++) {
			seriesA.add((i * samplingFreq) / getFft_A().length,
					getFft_A()[i].abs());
		}
		final XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(seriesA);
		return dataset;
	}

	/**
	 * Creates a sample chart.
	 * 
	 * @param dataset
	 *            the dataset.
	 * 
	 * @return A sample chart.
	 */
	private ChartPanel createChart(IntervalXYDataset dataset) {
		final JFreeChart chart = ChartFactory.createXYBarChart(TITLE,
				"Freq [Hz]", false, "|Y(f)|", dataset,
				PlotOrientation.VERTICAL, true, true, false);
		XYPlot plot = (XYPlot) chart.getPlot();
		plot.setBackgroundPaint(Color.white);
		plot.setDomainGridlinePaint(Color.lightGray);
		plot.setRangeGridlinePaint(Color.lightGray);

		switch (Colour) {
		case "RED":
			plot.getRenderer().setSeriesPaint(0, Color.RED);
			break;
		case "BLUE":
			plot.getRenderer().setSeriesPaint(0, Color.BLUE);
			break;
		case "GREEN":
			plot.getRenderer().setSeriesPaint(0, Color.GREEN);
			break;
		}
		XYBarRenderer renderer = (XYBarRenderer) plot.getRenderer();
		renderer.setShadowVisible(false);
		return new ChartPanel(chart);
	}

	public ChartPanel getChartPanel() {
		return chartPanel;
	}

	private void setChartPanel(ChartPanel chartPanel) {
		this.chartPanel = chartPanel;
	}

	public Complex[] getFft_A() {
		return fft;
	}

	private void setFft_A(Complex[] fft_A) {
		this.fft = fft_A;
	}
}
