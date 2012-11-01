package pl.edu.agh.weaiiib.elektrotechnika.symcom;

import java.awt.Color;

import javax.swing.JFrame;

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

public class VectorChart extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -476818969263809235L;

	static final Logger logger = Logger.getLogger(VectorChart.class);

	private Complex F1;
	private Complex F2;
	private Complex F3;

	VectorSeriesCollection dataSet = new VectorSeriesCollection();

	VectorRenderer r = new VectorRenderer();

	// r.setBasePaint(Color.white);
	// r.setSeriesPaint(0, Color.blue);
	// Create a Chart
	JFreeChart chart;

	public VectorChart(Complex F1, Complex F2, Complex F3) {
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
		VectorSeries vectorSeriesF1 = new VectorSeries("F1");
		VectorSeries vectorSeriesF2 = new VectorSeries("F2");
		VectorSeries vectorSeriesF3 = new VectorSeries("F3");

		vectorSeriesF1.add(0, 0, getF1().getReal(), getF1().getImaginary());
		vectorSeriesF2.add(0, 0, getF2().getReal(), getF2().getImaginary());
		vectorSeriesF3.add(0, 0, getF3().getReal(), getF3().getImaginary());

		dataSet.addSeries(vectorSeriesF1);
		dataSet.addSeries(vectorSeriesF2);
		dataSet.addSeries(vectorSeriesF3);
	}

	public void printChart() {

		updateDataSet();

		r.setSeriesPaint(0, Color.red);
		r.setSeriesPaint(1, Color.blue);
		r.setSeriesPaint(2, Color.green);

		XYPlot plot = new XYPlot(dataSet, new NumberAxis("Czêœæ Rzeczywista"),
				new NumberAxis("Czêœæ urojona"), r);

		plot.setBackgroundPaint(Color.white);

		chart = new JFreeChart(plot);
		chart.setTitle("Fazory F1, F2, F3");
		chart.setBackgroundPaint(Color.white);

		// create and display a frame...
		this.getContentPane().add(new ChartPanel(chart));
		this.setSize(700, 500);
		this.setVisible(true);
		this.setTitle("Fazory F1, F2, F3");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
}
