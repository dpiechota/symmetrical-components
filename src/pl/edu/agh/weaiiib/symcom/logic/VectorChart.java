package pl.edu.agh.weaiiib.symcom.logic;

import java.awt.Color;

import org.apache.commons.math3.complex.Complex;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.VectorRenderer;
import org.jfree.data.xy.VectorSeries;
import org.jfree.data.xy.VectorSeriesCollection;

public class VectorChart {
	/**
	 * 
	 */

	static final Logger logger = Logger.getLogger(VectorChart.class);

	private static final String TITLE = "Fazory F1, F2, F3";

	private Complex F1;
	private Complex F2;
	private Complex F3;

	private ChartPanel chartPanel;

	public VectorChart(Complex F1, Complex F2, Complex F3) {
		DOMConfigurator.configure("log4j.xml");
		setF1(F1);
		setF2(F2);
		setF3(F3);

		setChartPanel(createChart());
		getChartPanel().setMouseWheelEnabled(true);
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

	public ChartPanel getChartPanel() {
		return chartPanel;
	}

	public void setChartPanel(ChartPanel chartPanel) {
		this.chartPanel = chartPanel;
	}

	public VectorSeriesCollection createDataset() {

		VectorSeriesCollection dataSet = new VectorSeriesCollection();

		VectorSeries vectorSeriesF1 = new VectorSeries("Fazor F1");
		VectorSeries vectorSeriesF2 = new VectorSeries("Fazor F2");
		VectorSeries vectorSeriesF3 = new VectorSeries("Fazor F3");

		vectorSeriesF1.add(0, 0, getF1().getReal(), getF1().getImaginary());
		vectorSeriesF2.add(0, 0, getF2().getReal(), getF2().getImaginary());
		vectorSeriesF3.add(0, 0, getF3().getReal(), getF3().getImaginary());

		dataSet.addSeries(vectorSeriesF1);
		dataSet.addSeries(vectorSeriesF2);
		dataSet.addSeries(vectorSeriesF3);

		return dataSet;
	}

	public ChartPanel createChart() {

		VectorSeriesCollection dataSet = createDataset();

		VectorRenderer r = new VectorRenderer();
		r.setSeriesPaint(0, Color.red);
		r.setSeriesPaint(1, Color.blue);
		r.setSeriesPaint(2, Color.green);

		XYPlot plot = new XYPlot(dataSet, new NumberAxis("Re"), new NumberAxis(
				"Im"), r);
		plot.setBackgroundPaint(Color.white);
        
		JFreeChart chart = new JFreeChart(plot);
		chart.setTitle(TITLE);
		chart.setBackgroundPaint(Color.white);
		
		return new ChartPanel(chart);
	}
}
