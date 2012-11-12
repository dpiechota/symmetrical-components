package pl.edu.agh.weaiiib.symcom.logic;

import java.awt.Color;

import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.util.FastMath;
import org.apache.log4j.Logger;
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

	protected final static String TITLE = "Fazory faz A, B, C";

	private Complex fA;
	private Complex fB;
	private Complex fC;

	private ChartPanel chartPanel;

	public VectorChart(Complex fA, Complex fB, Complex fC) {
		// DOMConfigurator.configure("log4j.xml");
		setfA(fA);
		setfB(fB);
		setfC(fC);

		setChartPanel(createChart());
		getChartPanel().setMouseWheelEnabled(true);
	}
	
	public Complex getfA() {
		return fA;
	}

	public void setfA(Complex f1) {
		fA = f1;
	}

	public Complex getfB() {
		return fB;
	}

	public void setfB(Complex f2) {
		fB = f2;
	}

	public Complex getfC() {
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

	public VectorSeriesCollection createDataset() {

		VectorSeriesCollection dataSet = new VectorSeriesCollection();

		VectorSeries vectorSeriesF1 = new VectorSeries("Faza A. Modu³ = " + String.format("%.2f",getfA().abs()) + ". Arg = " + String.format("%.2f",FastMath.toDegrees(getfA().getArgument())));
		VectorSeries vectorSeriesF2 = new VectorSeries("Faza B. Modu³ = " + String.format("%.2f",getfB().abs()) + ". Arg = " + String.format("%.2f",FastMath.toDegrees(getfB().getArgument())));
		VectorSeries vectorSeriesF3 = new VectorSeries("Faza C. Modu³ = " + String.format("%.2f",getfC().abs()) + ". Arg = " + String.format("%.2f",FastMath.toDegrees(getfC().getArgument())));

		vectorSeriesF1.add(0, 0, getfA().getReal(), getfA().getImaginary());
		vectorSeriesF2.add(0, 0, getfB().getReal(), getfB().getImaginary());
		vectorSeriesF3.add(0, 0, getfC().getReal(), getfC().getImaginary());

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
		//TextTitle legendText = new TextTitle("");
		//legendText.setPosition(RectangleEdge.BOTTOM);
		//chart.addSubtitle(legendText);
		return new ChartPanel(chart);
	}
}
