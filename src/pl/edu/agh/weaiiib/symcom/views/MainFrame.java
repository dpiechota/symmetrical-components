package pl.edu.agh.weaiiib.symcom.views;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.complex.ComplexUtils;
import org.apache.commons.math3.util.FastMath;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import pl.edu.agh.weaiiib.symcom.datainput.FFT;
import pl.edu.agh.weaiiib.symcom.datainput.SampledData;
import pl.edu.agh.weaiiib.symcom.logic.Main;
import pl.edu.agh.weaiiib.symcom.logic.SymmetricalComponents;
import pl.edu.agh.weaiiib.symcom.plots.ComplexChart;
import pl.edu.agh.weaiiib.symcom.plots.HistogramChart;
import pl.edu.agh.weaiiib.symcom.plots.TimeDomainDataChart;
import pl.edu.agh.weaiiib.symcom.plots.TimeDomianChart;
import pl.edu.agh.weaiiib.symcom.plots.VectorChart;

public class MainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7961045185909916423L;
	static final Logger logger = Logger.getLogger(Main.class);

	private JPanel contentPane;
	private JSpinner magnitudeF1;
	private JSpinner angelF1;
	private JSpinner magnitudeF2;
	private JSpinner angelF2;
	private JSpinner magnitudeF3;
	private JSpinner angelF3;

	private ChartFrame timeProcess;
	private ChartFrame threePhaze;
	private ChartFrame zero_seq;
	private ChartFrame positiveSequence;
	private ChartFrame negativeSequence;
	private ChartFrame threePhazeCheck;
	private ChartFrame histogramFrameA;
	private ChartFrame histogramFrameB;
	private ChartFrame histogramFrameC;
	private ChartFrame timeDomianDataFile;
	private File dataFile;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		DOMConfigurator.configure("log4j.xml");
		logger.debug("Starting Main class");
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setLocation(1200, 500);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/pl/edu/agh/weaiiib/symcom/resources/lightning-icon.png")));

		timeProcess = new ChartFrame("Przebieg czasowy faz A, B, C", 100, 0);
		threePhaze = new ChartFrame("Fazory faz A, B, C", 500, 0);
		zero_seq = new ChartFrame("Kolejnosc zerowa", 100, 400);
		positiveSequence = new ChartFrame("Kolejnosc zgodna", 500, 400);
		negativeSequence = new ChartFrame("Kolejnosc przeciwna", 900, 400);
		threePhazeCheck = new ChartFrame("Sprawdzenie", 900, 0);
		histogramFrameA = new ChartFrame("FFT results phase A", 100, 800);
		histogramFrameB = new ChartFrame("FFT results phase B", 500, 800);
		histogramFrameC = new ChartFrame("FFT results phase C", 900, 800);
		timeDomianDataFile = new ChartFrame("Time Domain Data File", 900, 400);
		
		setTitle("Skladowe Symetryczne - Dariusz Piechota");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 604, 253);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnPlik = new JMenu("Plik");
		menuBar.add(mnPlik);

		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.setIcon(new ImageIcon(MainFrame.class.getResource("/pl/edu/agh/weaiiib/symcom/resources/lightning-icon.png")));
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		mnPlik.add(mntmExit);

		JMenu mnAbout = new JMenu("About");
		menuBar.add(mnAbout);

		JMenuItem mntmAboutThisProgram = new JMenuItem("About this program");
		mntmAboutThisProgram.setIcon(new ImageIcon(MainFrame.class.getResource("/pl/edu/agh/weaiiib/symcom/resources/lightning-icon.png")));
		mntmAboutThisProgram.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane
						.showMessageDialog(contentPane,
								"Dariusz Piechota. Praca magisterska: Symmetrical Components in proactive relays for power systems");
			}
		});
		mnAbout.add(mntmAboutThisProgram);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setToolTipText("");
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 583, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 177, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(268, Short.MAX_VALUE))
		);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Simulation", null, panel, null);
		
				JPanel panel_1 = new JPanel();
				panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Phase A", TitledBorder.LEFT, TitledBorder.TOP, null, null));
				
						JLabel lblAmplituda = new JLabel("Amplituda");
						
								JLabel lblFazastopnie = new JLabel("Faza (stopnie)");
								
										magnitudeF1 = new JSpinner();
										magnitudeF1.setModel(new SpinnerNumberModel(new Double(100),
												new Double(0), null, new Double(1)));
										
												angelF1 = new JSpinner();
												angelF1.setModel(new SpinnerNumberModel(new Double(0), null, null,
														new Double(1)));
												GroupLayout gl_panel_1 = new GroupLayout(panel_1);
												gl_panel_1.setHorizontalGroup(gl_panel_1.createParallelGroup(
														Alignment.LEADING).addGroup(
														gl_panel_1
																.createSequentialGroup()
																.addContainerGap()
																.addGroup(
																		gl_panel_1
																				.createParallelGroup(Alignment.LEADING)
																				.addComponent(lblAmplituda)
																				.addComponent(lblFazastopnie))
																.addPreferredGap(ComponentPlacement.RELATED, 18,
																		Short.MAX_VALUE)
																.addGroup(
																		gl_panel_1
																				.createParallelGroup(Alignment.LEADING,
																						false)
																				.addComponent(angelF1)
																				.addComponent(magnitudeF1,
																						GroupLayout.DEFAULT_SIZE, 56,
																						Short.MAX_VALUE))
																.addContainerGap()));
												gl_panel_1
														.setVerticalGroup(gl_panel_1
																.createParallelGroup(Alignment.LEADING)
																.addGroup(
																		gl_panel_1
																				.createSequentialGroup()
																				.addContainerGap()
																				.addGroup(
																						gl_panel_1
																								.createParallelGroup(
																										Alignment.BASELINE)
																								.addComponent(
																										lblAmplituda)
																								.addComponent(
																										magnitudeF1,
																										GroupLayout.PREFERRED_SIZE,
																										GroupLayout.DEFAULT_SIZE,
																										GroupLayout.PREFERRED_SIZE))
																				.addPreferredGap(
																						ComponentPlacement.RELATED)
																				.addGroup(
																						gl_panel_1
																								.createParallelGroup(
																										Alignment.BASELINE)
																								.addComponent(
																										lblFazastopnie)
																								.addComponent(
																										angelF1,
																										GroupLayout.PREFERRED_SIZE,
																										GroupLayout.DEFAULT_SIZE,
																										GroupLayout.PREFERRED_SIZE))
																				.addContainerGap(67, Short.MAX_VALUE)));
												panel_1.setLayout(gl_panel_1);
		
				JPanel panel_2 = new JPanel();
				panel_2.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Phase B", TitledBorder.LEFT, TitledBorder.TOP, null, null));
				
						JLabel label = new JLabel("Amplituda");
						
								JLabel label_2 = new JLabel("Faza (stopnie)");
								
										magnitudeF2 = new JSpinner();
										magnitudeF2.setModel(new SpinnerNumberModel(new Double(50), new Double(
												0), null, new Double(1)));
										
												angelF2 = new JSpinner();
												angelF2.setModel(new SpinnerNumberModel(new Double(90), null, null,
														new Double(1)));
												GroupLayout gl_panel_2 = new GroupLayout(panel_2);
												gl_panel_2
														.setHorizontalGroup(gl_panel_2
																.createParallelGroup(Alignment.LEADING)
																.addGroup(
																		gl_panel_2
																				.createSequentialGroup()
																				.addContainerGap()
																				.addGroup(
																						gl_panel_2
																								.createParallelGroup(
																										Alignment.LEADING)
																								.addComponent(label)
																								.addComponent(
																										label_2,
																										GroupLayout.DEFAULT_SIZE,
																										79,
																										Short.MAX_VALUE))
																				.addPreferredGap(
																						ComponentPlacement.UNRELATED)
																				.addGroup(
																						gl_panel_2
																								.createParallelGroup(
																										Alignment.TRAILING)
																								.addComponent(
																										magnitudeF2,
																										GroupLayout.PREFERRED_SIZE,
																										56,
																										GroupLayout.PREFERRED_SIZE)
																								.addComponent(
																										angelF2,
																										GroupLayout.PREFERRED_SIZE,
																										56,
																										GroupLayout.PREFERRED_SIZE))
																				.addContainerGap()));
												gl_panel_2
														.setVerticalGroup(gl_panel_2
																.createParallelGroup(Alignment.LEADING)
																.addGroup(
																		gl_panel_2
																				.createSequentialGroup()
																				.addContainerGap()
																				.addGroup(
																						gl_panel_2
																								.createParallelGroup(
																										Alignment.BASELINE)
																								.addComponent(label)
																								.addComponent(
																										magnitudeF2,
																										GroupLayout.PREFERRED_SIZE,
																										GroupLayout.DEFAULT_SIZE,
																										GroupLayout.PREFERRED_SIZE))
																				.addPreferredGap(
																						ComponentPlacement.RELATED)
																				.addGroup(
																						gl_panel_2
																								.createParallelGroup(
																										Alignment.BASELINE)
																								.addComponent(label_2)
																								.addComponent(
																										angelF2,
																										GroupLayout.PREFERRED_SIZE,
																										GroupLayout.DEFAULT_SIZE,
																										GroupLayout.PREFERRED_SIZE))
																				.addContainerGap(67, Short.MAX_VALUE)));
												panel_2.setLayout(gl_panel_2);
		
				JPanel panel_3 = new JPanel();
				panel_3.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Phase C", TitledBorder.LEFT, TitledBorder.TOP, null, null));
				
						JLabel label_1 = new JLabel("Amplituda");
						
								JLabel label_3 = new JLabel("Faza (stopnie)");
								
										magnitudeF3 = new JSpinner();
										magnitudeF3.setModel(new SpinnerNumberModel(new Double(100),
												new Double(0), null, new Double(1)));
										
												angelF3 = new JSpinner();
												angelF3.setModel(new SpinnerNumberModel(new Double(-120), null, null,
														new Double(1)));
												GroupLayout gl_panel_3 = new GroupLayout(panel_3);
												gl_panel_3.setHorizontalGroup(gl_panel_3.createParallelGroup(
														Alignment.LEADING).addGroup(
														gl_panel_3
																.createSequentialGroup()
																.addContainerGap()
																.addGroup(
																		gl_panel_3
																				.createParallelGroup(Alignment.LEADING,
																						false)
																				.addComponent(label_3,
																						Alignment.TRAILING,
																						GroupLayout.DEFAULT_SIZE,
																						GroupLayout.DEFAULT_SIZE,
																						Short.MAX_VALUE)
																				.addComponent(label_1,
																						Alignment.TRAILING,
																						GroupLayout.DEFAULT_SIZE, 86,
																						Short.MAX_VALUE))
																.addPreferredGap(ComponentPlacement.RELATED)
																.addGroup(
																		gl_panel_3
																				.createParallelGroup(Alignment.LEADING)
																				.addComponent(magnitudeF3,
																						GroupLayout.PREFERRED_SIZE, 56,
																						GroupLayout.PREFERRED_SIZE)
																				.addComponent(angelF3,
																						GroupLayout.PREFERRED_SIZE, 56,
																						GroupLayout.PREFERRED_SIZE))
																.addContainerGap()));
												gl_panel_3
														.setVerticalGroup(gl_panel_3
																.createParallelGroup(Alignment.LEADING)
																.addGroup(
																		gl_panel_3
																				.createSequentialGroup()
																				.addContainerGap()
																				.addGroup(
																						gl_panel_3
																								.createParallelGroup(
																										Alignment.BASELINE)
																								.addComponent(label_1)
																								.addComponent(
																										magnitudeF3,
																										GroupLayout.PREFERRED_SIZE,
																										GroupLayout.DEFAULT_SIZE,
																										GroupLayout.PREFERRED_SIZE))
																				.addPreferredGap(
																						ComponentPlacement.RELATED)
																				.addGroup(
																						gl_panel_3
																								.createParallelGroup(
																										Alignment.BASELINE)
																								.addComponent(label_3)
																								.addComponent(
																										angelF3,
																										GroupLayout.PREFERRED_SIZE,
																										GroupLayout.DEFAULT_SIZE,
																										GroupLayout.PREFERRED_SIZE))
																				.addContainerGap(67, Short.MAX_VALUE)));
												panel_3.setLayout(gl_panel_3);
		
				JButton btnRysuj = new JButton("Draw plots");
				btnRysuj.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent arg0) {
						updatePhaseValues();
					}

				});
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 183, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 183, GroupLayout.PREFERRED_SIZE)
							.addGap(6)
							.addComponent(panel_3, GroupLayout.PREFERRED_SIZE, 183, GroupLayout.PREFERRED_SIZE))
						.addComponent(btnRysuj, GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(7, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(1)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE)
								.addComponent(panel_3, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE)))
						.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnRysuj)
					.addContainerGap(13, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		
		JPanel panel_4 = new JPanel();
		tabbedPane.addTab("Data input from file", null, panel_4, null);
		
		JButton btnNewButton = new JButton("File with data samples");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					JFileChooser jFileChooser = new JFileChooser();
					jFileChooser.setDialogTitle("Choose file with data points");
					jFileChooser.showOpenDialog(null);
					setDataFile(jFileChooser.getSelectedFile());
					
				}
				catch (Exception e){
					logger.error(e.getStackTrace());
				}
			}
		});
		
		JButton btnNewButton_1 = new JButton("Draw plots");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (dataFile != null){
					logger.info(getDataFile().getAbsolutePath().toString());
					try {
						actionOnButtonDrawDataFile();
					} catch (IOException e) {
						
						e.printStackTrace();
					}
				}
				else{
					String message = "Please provide file with signal samples";
					logger.error(message);
					JOptionPane
					.showMessageDialog(contentPane,message);
				}
			}
		});
		GroupLayout gl_panel_4 = new GroupLayout(panel_4);
		gl_panel_4.setHorizontalGroup(
			gl_panel_4.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_4.createSequentialGroup()
					.addContainerGap()
					.addComponent(btnNewButton)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnNewButton_1)
					.addContainerGap(336, Short.MAX_VALUE))
		);
		gl_panel_4.setVerticalGroup(
			gl_panel_4.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_4.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_4.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnNewButton)
						.addComponent(btnNewButton_1))
					.addContainerGap(115, Short.MAX_VALUE))
		);
		panel_4.setLayout(gl_panel_4);
		contentPane.setLayout(gl_contentPane);
	}

	private void doPrintButton(Complex fA, Complex fB, Complex fC) {
		/*
		 * Print time-functions of phases
		 */
		TimeDomianChart xychart = new TimeDomianChart(fA, fB, fC);
		refreshJFrame(timeProcess, xychart);

		/*
		 * Print original 3-phase system
		 */
		VectorChart vector = new VectorChart(fA, fB, fC);
		refreshJFrame(threePhaze, vector);

		/*
		 * Compute symmetrical components
		 */
		SymmetricalComponents symcom = new SymmetricalComponents(fA, fB, fC);

		/*
		 * Print zero sequence phasers
		 */
		VectorChart vectorABC_0 = new VectorChart(symcom.getfA_0(), symcom.getfB_0(), symcom.getfC_0());
		refreshJFrame(zero_seq, vectorABC_0);

		/*
		 * Print positive sequence phasers
		 */
		VectorChart vectorABC_1 = new VectorChart(symcom.getfA_1(), symcom.getfB_1(), symcom.getfC_1());
		refreshJFrame(positiveSequence, vectorABC_1);

		/*
		 * Print negative sequence phasers
		 */
		VectorChart vectorABC_2 = new VectorChart(symcom.getfA_2(), symcom.getfB_2(), symcom.getfC_2());
		refreshJFrame(negativeSequence, vectorABC_2);

		/*
		 * Print check phasers
		 */
		VectorChart vectorABC_check = new VectorChart(
				((symcom.getfA_0().add(symcom.getfA_1())).add(symcom.getfA_2())),
				((symcom.getfB_0().add(symcom.getfB_1())).add(symcom.getfB_2())),
				((symcom.getfC_0().add(symcom.getfC_1())).add(symcom.getfC_2())));
		refreshJFrame(threePhazeCheck, vectorABC_check);
		
	}

	private void refreshJFrame(JFrame frame, ComplexChart chart) {

		frame.getContentPane().removeAll();
		frame.getContentPane().add(chart.getChartPanel(), BorderLayout.CENTER);
		frame.revalidate();
		frame.repaint();
		frame.setVisible(true);
	}
	
	private void refreshJFrame(JFrame frame, HistogramChart chart) {

		frame.getContentPane().removeAll();
		frame.getContentPane().add(chart.getChartPanel(), BorderLayout.CENTER);
		frame.revalidate();
		frame.repaint();
		frame.setVisible(true);
	}
	
	
	private void updatePhaseValues() {
		logger.debug("Button Click event");

		Complex FA = ComplexUtils.polar2Complex(Double.valueOf(magnitudeF1
				.getValue().toString()), FastMath.toRadians(Double
				.valueOf(angelF1.getValue().toString())));
		Complex FB = ComplexUtils.polar2Complex(Double.valueOf(magnitudeF2
				.getValue().toString()), FastMath.toRadians(Double
				.valueOf(angelF2.getValue().toString())));
		Complex FC = ComplexUtils.polar2Complex(Double.valueOf(magnitudeF3
				.getValue().toString()), FastMath.toRadians(Double
				.valueOf(angelF3.getValue().toString())));
		
		logger.info("\nPhase values: \nfA = " + FA + "\nfB = " + FB + "\nfC = " + FC);
		
		doPrintButton(FA, FB, FC);
	}
	
	private void actionOnButtonDrawDataFile() throws IOException{
		SampledData sampledData = new SampledData(dataFile);
		/*
		 * Print histogram
		 */
		FFT fft = new FFT(sampledData);
		HistogramChart histogramA = new HistogramChart(fft.getFft_A(),sampledData.getSamplingFreq(), "Phase A", "RED");
		HistogramChart histogramB = new HistogramChart(fft.getFft_B(),sampledData.getSamplingFreq(), "Phase B", "BLUE");
		HistogramChart histogramC = new HistogramChart(fft.getFft_C(),sampledData.getSamplingFreq(), "Phase C", "GREEN");
		
		refreshJFrame(histogramFrameA, histogramA);
		refreshJFrame(histogramFrameB, histogramB);
		refreshJFrame(histogramFrameC, histogramC);
		
		//new Complex are fakes 
		TimeDomainDataChart timeDomainDataChart = new TimeDomainDataChart(sampledData, new Complex(0,0), new Complex(0,0), new Complex(0,0));
		refreshJFrame(timeDomianDataFile, timeDomainDataChart);
		
	}
	public File getDataFile() {
		return dataFile;
	}

	private void setDataFile(File dataFile) {
		this.dataFile = dataFile;
	}
}
