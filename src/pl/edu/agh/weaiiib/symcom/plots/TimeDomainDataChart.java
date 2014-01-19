package pl.edu.agh.weaiiib.symcom.plots;

import java.awt.Color;

import org.apache.commons.math3.complex.Complex;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import pl.edu.agh.weaiiib.symcom.datainput.SampledData;

public class TimeDomainDataChart extends ComplexChart{
	
	private SampledData sampledData;
	protected final static String TITLE = "Time Domain Plot";

	
	public TimeDomainDataChart(SampledData sampleddata,Complex fA, Complex fB, Complex fC, Double frequency) {
		super(fA, fB, fC, frequency);
		sampledData = sampleddata;
		setChartPanel(createChart());
		getChartPanel().setMouseWheelEnabled(true);
	}

	private XYDataset createDataset() {
		
		// create dataset A...
		final XYSeries seriesA = new XYSeries("Phase A");
		final XYSeries seriesB = new XYSeries("Phase B");
		final XYSeries seriesC = new XYSeries("Phase C");
		
		for (int i = 0; i < sampledData.getTimeSamples().size(); i++) {
			seriesA.add(sampledData.getTimeSamples().get(i).doubleValue(), sampledData.getPhase_A_samples().get(i).getReal());
			seriesB.add(sampledData.getTimeSamples().get(i).doubleValue(), sampledData.getPhase_B_samples().get(i).getReal());
			seriesC.add(sampledData.getTimeSamples().get(i).doubleValue(), sampledData.getPhase_C_samples().get(i).getReal());
		}

		final XYSeriesCollection collection = new XYSeriesCollection();
		collection.addSeries(seriesA);
		collection.addSeries(seriesB);
		collection.addSeries(seriesC);
		return collection;

	}

	public ChartPanel createChart() {
		XYDataset dataset = createDataset();

		// Create the chart
		JFreeChart chart = ChartFactory.createXYLineChart(TITLE, // The chart
																	// title
				"Time [s]", // x axis label
				"Amplitude", // y axis label
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
