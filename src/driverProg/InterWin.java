package driverProg;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class InterWin {

	private JFrame frmMoea;
	private JTextField txtNumGens;
	private JTextArea textArea;
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
		frmMoea.setResizable(false);
		frmMoea.setTitle("MOEA");
		frmMoea.setBounds(100, 100, 729, 415);
		frmMoea.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMoea.getContentPane().setLayout(null);
		
		JLabel lblThisIsA = new JLabel("# Generations");
		lblThisIsA.setBounds(10, 11, 82, 23);
		lblThisIsA.setHorizontalAlignment(SwingConstants.CENTER);
		frmMoea.getContentPane().add(lblThisIsA);
		
		JButton btnRun = new JButton("Run");

		btnRun.setBounds(624, 16, 89, 23);
		frmMoea.getContentPane().add(btnRun);
		
		txtNumGens = new JTextField();
		txtNumGens.setBounds(102, 12, 86, 20);
		frmMoea.getContentPane().add(txtNumGens);
		txtNumGens.setColumns(10);
		
		JButton btnStop = new JButton("Stop");
		btnStop.setBounds(525, 16, 89, 23);
		frmMoea.getContentPane().add(btnStop);
		
		textArea = new JTextArea();
		textArea.setBounds(20, 45, 680, 330);		
		JScrollPane scrl = new JScrollPane(textArea);
		scrl.setBounds(20, 45, 680, 330);
		frmMoea.getContentPane().add(scrl);

		
		btnRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for (int i = 0;i < 100;i++) {
				textArea.setText(textArea.getText() + "i am using this kind of software to express my short-commings\n i need nastaran\n she is" +
						"the only way i can set my self");
				}
			}
		});
	}
}
