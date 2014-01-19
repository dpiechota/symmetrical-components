package pl.edu.agh.weaiiib.symcom.plots;

import java.awt.Color;

import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.util.FastMath;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.VectorRenderer;
import org.jfree.data.xy.VectorSeries;
import org.jfree.data.xy.VectorSeriesCollection;

public class VectorChart extends ComplexChart {
	/**
	 * 
	 */
	protected final static String TITLE = "Phasors of A, B, C phases";
	
	public VectorChart(Complex fA, Complex fB, Complex fC, Double frequency) {
		super(fA, fB, fC, frequency);
		setChartPanel(createChart());
		getChartPanel().setMouseWheelEnabled(true);
	}
	
	public VectorSeriesCollection createDataset() {

		VectorSeriesCollection dataSet = new VectorSeriesCollection();

		VectorSeries vectorSeriesF1 = new VectorSeries("Phase A Magnitude = " + String.format("%.2f",getfA().abs()) + ". Arg = " + String.format("%.2f",FastMath.toDegrees(getfA().getArgument())));
		VectorSeries vectorSeriesF2 = new VectorSeries("Phase B Magnitude = " + String.format("%.2f",getfB().abs()) + ". Arg = " + String.format("%.2f",FastMath.toDegrees(getfB().getArgument())));
		VectorSeries vectorSeriesF3 = new VectorSeries("Phase C Magnitude = " + String.format("%.2f",getfC().abs()) + ". Arg = " + String.format("%.2f",FastMath.toDegrees(getfC().getArgument())));

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
