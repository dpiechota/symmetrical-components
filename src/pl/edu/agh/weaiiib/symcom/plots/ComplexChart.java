package pl.edu.agh.weaiiib.symcom.plots;

import org.apache.commons.math3.complex.Complex;
import org.apache.log4j.Logger;
import org.jfree.chart.ChartPanel;

public abstract class ComplexChart {
	static final Logger logger = Logger.getLogger(VectorChart.class);
	
	private ChartPanel chartPanel;
	
	private Complex fA;
	private Complex fB;
	private Complex fC;
	
	public ComplexChart(Complex fA, Complex fB, Complex fC) {
		// DOMConfigurator.configure("log4j.xml");
		setfA(fA);
		setfB(fB);
		setfC(fC);
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
}
