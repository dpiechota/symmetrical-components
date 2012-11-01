package pl.edu.agh.weaiiib.elektrotechnika.symcom;

import org.apache.commons.math3.complex.Complex;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.VectorRenderer;
import org.jfree.data.xy.VectorSeries;
import org.jfree.data.xy.VectorSeriesCollection;

public class Main {
	static final Logger logger = Logger.getLogger(Main.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		VectorSeriesCollection dataSet = new VectorSeriesCollection();
		DOMConfigurator.configure("log4j.xml");
		Complex F1 = new Complex(1.0, 3.0);
		Complex F2 = new Complex(1.0, -3.0);
		Complex F3 = new Complex(-1.0, 3.0);

		VectorSeries vectorSeries = new VectorSeries("First Series");
		
		vectorSeries.add(0, 0, F1.getReal(), F1.getImaginary());
		vectorSeries.add(0, 0, F2.getReal(), F2.getImaginary());
		vectorSeries.add(0, 0, F3.getReal(), F3.getImaginary());
		
		dataSet.addSeries(vectorSeries);

		VectorRenderer r = new VectorRenderer();
		// r.setBasePaint(Color.white);
		// r.setSeriesPaint(0, Color.blue);

		XYPlot xyPlot = new XYPlot(dataSet, new NumberAxis("Axis X"),
				new NumberAxis("Axis Y"), r);

		// Create a Chart
		JFreeChart theChart;

		theChart = new JFreeChart(xyPlot);
		theChart.setTitle("El Nuevo Chart");

		// create and display a frame...
		ChartFrame frame = new ChartFrame("First", theChart);
		frame.pack();
		frame.setVisible(true);

		logger.debug("Complex number : " + F1.toString());

	}

}
