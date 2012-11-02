package pl.edu.agh.weaiiib.symcom.views;

import javax.swing.JFrame;

public class XYChartJFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1551704505269874597L;

	private static final String TITLE = "Fazory F1, F2, F3";

	public XYChartJFrame() {
		this.setTitle(TITLE);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocation(600, 10);
		this.setSize(500, 500);
	}
}
