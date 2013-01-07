package driverProg;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.BoxLayout;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import java.awt.BorderLayout;

public class InterWin {

	private JFrame frmMoea;
	private JTextField txtNumVars;
	private JTextField txtNumObjs;
	private JTextField txtPopSize;
	private JTextField txtNumGens;
	private JTextField txtPc;
	private JTextField txtPm;
	private JTextField txtBeta;
	private JTextField txtEta;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InterWin window = new InterWin();
					window.frmMoea.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public InterWin() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmMoea = new JFrame();
		frmMoea.setTitle("MOEA");
		frmMoea.setBounds(100, 100, 729, 415);
		frmMoea.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {420, 256, 0};
		gridBagLayout.rowHeights = new int[] {63};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0};
		frmMoea.getContentPane().setLayout(gridBagLayout);
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.anchor = GridBagConstraints.NORTH;
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 0;
		frmMoea.getContentPane().add(panel, gbc_panel);
		
		JButton btnStop = new JButton("Stop");
		panel.add(btnStop);
		btnStop.setBounds(525, 16, 89, 23);
		
		JButton btnRun = new JButton("Run");
		panel.add(btnRun);
		
				btnRun.setBounds(624, 16, 89, 23);
				
						
						btnRun.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								for (int i = 0;i < 100;i++) {
								}
							}
						});
		
		JPanel panelOptions = new JPanel();
		panelOptions.setLayout(new GridLayout(10, 2, 0, 0));
		
		
		JLabel lblNewLabel = new JLabel("# of Varialbes");
		panelOptions.add(lblNewLabel);
		
		txtNumVars = new JTextField();
		panelOptions.add(txtNumVars);
		txtNumVars.setColumns(10);
		
		JLabel lblOfObjectives = new JLabel("# of Objectives");
		panelOptions.add(lblOfObjectives);
		
		txtNumObjs = new JTextField();
		panelOptions.add(txtNumObjs);
		txtNumObjs.setColumns(10);
		
		JLabel lblPopulationSize = new JLabel("Population Size");
		panelOptions.add(lblPopulationSize);
		
		txtPopSize = new JTextField();
		panelOptions.add(txtPopSize);
		txtPopSize.setColumns(10);
		
		JLabel lblOfGenerations = new JLabel("# of Generations");
		panelOptions.add(lblOfGenerations);
		
		txtNumGens = new JTextField();
		txtNumGens.setText("");
		panelOptions.add(txtNumGens);
		txtNumGens.setColumns(10);
		
		JLabel lblProblem = new JLabel("Problem");
		panelOptions.add(lblProblem);
		
		JComboBox cmbProblem = new JComboBox();
		panelOptions.add(cmbProblem);
		
		JLabel lblCrossoverRate = new JLabel("Crossover Rate");
		panelOptions.add(lblCrossoverRate);
		
		txtPc = new JTextField();
		txtPc.setText("");
		panelOptions.add(txtPc);
		txtPc.setColumns(10);
		
		JLabel lblMutationRate = new JLabel("Mutation Rate");
		panelOptions.add(lblMutationRate);
		
		txtPm = new JTextField();
		txtPm.setText("");
		panelOptions.add(txtPm);
		txtPm.setColumns(10);
		
		JLabel lblBeta = new JLabel("Beta");
		panelOptions.add(lblBeta);
		
		txtBeta = new JTextField();
		txtBeta.setText("");
		panelOptions.add(txtBeta);
		txtBeta.setColumns(10);
		
		JLabel lblEta = new JLabel("Eta");
		panelOptions.add(lblEta);
		
		txtEta = new JTextField();
		txtEta.setText("");
		panelOptions.add(txtEta);
		txtEta.setColumns(10);
		GridBagConstraints gbc_panelOptions = new GridBagConstraints();
		gbc_panelOptions.fill = GridBagConstraints.BOTH;
		gbc_panelOptions.gridx = 1;
		gbc_panelOptions.gridy = 1;
		frmMoea.getContentPane().add(panelOptions, gbc_panelOptions);
	}
}
