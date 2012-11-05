package pl.edu.agh.weaiiib.symcom.views;

import java.awt.Toolkit;

import javax.swing.JFrame;

public class XYChartJFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1551704505269874597L;

	private static final String TITLE = "Przebieg czasowy faz F1, F2, F3";

	public XYChartJFrame() {
		this.setTitle(TITLE);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocation(500, 10);
		this.setSize(400, 400);
		setIconImage(Toolkit
				.getDefaultToolkit()
				.getImage(
						MainJFrame.class
								.getResource("/pl/edu/agh/weaiiib/symcom/resources/lightning-icon.png")));
	}
}
