package pl.edu.agh.weaiiib.elektrotechnika.symcom;

import static java.lang.Math.PI;
import static java.lang.Math.sin;
import static org.math.array.DoubleArray.increment;

import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.DefaultXYDataset;

public class XYChart extends JFrame {
	static final Logger logger = Logger.getLogger(XYChart.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final double[] LAxis = increment(0.0, 0.01, 3.0);

	private Double AF1 = 0.0;
	private Double FF1 = 0.0;
	private Double FiF1 = 0.0;
	private Double AF2 = 0.0;
	private Double FF2 = 0.0;
	private Double FiF2 = 0.0;
	private Double AF3 = 0.0;
	private Double FF3 = 0.0;
	private Double FiF3 = 0.0;

	// Date set for chart
	DefaultXYDataset dataset = new DefaultXYDataset();

	// Chart itself
	JFreeChart chart;

	// Constructor
	public XYChart(Double AF1, Double FF1, Double FiF1, Double AF2, Double FF2,
			Double FiF2, Double AF3, Double FF3, Double FiF3) {
		DOMConfigurator.configure("log4j.xml");
		this.setAF1(AF1);
		this.setFF1(FF1);
		this.setFiF1(FiF1);
		this.setAF2(AF2);
		this.setFF2(FF2);
		this.setFiF2(FiF2);
		this.setAF3(AF3);
		this.setFF3(FF3);
		this.setFiF3(FiF3);
	}

	private Double getAF1() {
		return AF1;
	}

	private void setAF1(Double aF1) {
		AF1 = aF1;
	}

	private Double getFF1() {
		return FF1;
	}

	private void setFF1(Double fF1) {
		FF1 = fF1;
	}

	private Double getFiF1() {
		return FiF1;
	}

	private void setFiF1(Double fiF1) {
		FiF1 = fiF1;
	}

	private Double getAF2() {
		return AF2;
	}

	private void setAF2(Double aF2) {
		AF2 = aF2;
	}

	private Double getFF2() {
		return FF2;
	}

	private void setFF2(Double fF2) {
		FF2 = fF2;
	}

	private Double getFiF2() {
		return FiF2;
	}

	private void setFiF2(Double fiF2) {
		FiF2 = fiF2;
	}

	private Double getAF3() {
		return AF3;
	}

	private void setAF3(Double aF3) {
		AF3 = aF3;
	}

	private Double getFF3() {
		return FF3;
	}

	private void setFF3(Double fF3) {
		FF3 = fF3;
	}

	private Double getFiF3() {
		return FiF3;
	}

	private void setFiF3(Double fiF3) {
		FiF3 = fiF3;
	}

	public void updateDataSet() {

		double[][] L1Values = new double[2][LAxis.length];
		double[][] L2Values = new double[2][LAxis.length];
		double[][] L3Values = new double[2][LAxis.length];

		L1Values[0] = L2Values[0] = L3Values[0] = LAxis;

		for (int i = 0; i < LAxis.length; i++) {

			L1Values[1][i] = getAF1()
					* sin(L1Values[0][i] * getFF1() * 2 * PI
							+ ((PI * getFiF1()) / 180));
			L2Values[1][i] = getAF2()
					* sin(L2Values[0][i] * getFF2() * 2 * PI
							+ ((PI * getFiF2()) / 180));
			L3Values[1][i] = getAF3()
					* sin(L3Values[0][i] * getFF3() * 2 * PI
							+ ((PI * getFiF3()) / 180));

			logger.debug(L1Values[0][i] + " " + L1Values[1][i]);
			logger.debug(L2Values[0][i] + " " + L2Values[1][i]);
			logger.debug(L3Values[0][i] + " " + L3Values[1][i]);
		}

		logger.debug(L1Values[0].length);
		logger.debug(L1Values[0].length);
		logger.debug(L1Values[0].length);

		dataset.addSeries("f1", L1Values);
		dataset.addSeries("f2", L2Values);
		dataset.addSeries("f3", L3Values);
	}

	public void printChart() {
		updateDataSet();
		// Create the chart
		chart = ChartFactory.createXYLineChart(
				"Przebieg czasowy faz F1, F2, F3", // The chart title
				"Czas", // x axis label
				"Amplituda", // y axis label
				dataset, // The dataset for the chart
				PlotOrientation.VERTICAL, true, // Is a legend required?
				false, // Use tooltips
				false // Configure chart to generate URLs?
				);
		this.getContentPane().add(new ChartPanel(chart));
		this.setSize(700, 500);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void saveChart() throws IOException {
		// save it to a file
		File imageFile = new File("time-based.png");
		ChartUtilities.saveChartAsPNG(imageFile, chart, 500, 300);
	}

	public static void main(String[] args) throws IOException,
			InterruptedException {

		// display it on the screen
		XYChart frame = new XYChart(230.0, 0.5, 0.0, 230.0, 0.5, 120.0, 230.0,
				0.5, -120.0);
		frame.printChart();
		frame.setAF1(100.0);
		Thread.sleep(1000);
		frame.printChart();
	}

}