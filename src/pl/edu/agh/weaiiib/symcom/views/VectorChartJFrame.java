package pl.edu.agh.weaiiib.symcom.views;

import javax.swing.JFrame;

public class VectorChartJFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6990506460934749279L;

	private static final String TITLE = "Fazory F1, F2, F3";

	public VectorChartJFrame() {
		this.setTitle(TITLE);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocation(100, 10);
		this.setSize(500, 500);
	}
}
