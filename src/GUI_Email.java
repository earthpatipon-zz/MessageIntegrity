import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.BoxLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Panel;
import java.awt.Label;
import java.awt.SystemColor;
import java.awt.Font;
import java.awt.TextField;
import java.awt.Toolkit;

import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Button;
import javax.swing.UIManager;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JSeparator;

public class GUI_Email {

	private JFrame frame;
	private JTextField textField_2;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI_Email window = new GUI_Email();
					
					window.frame.setVisible(true);
					
					
			
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI_Email() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(SystemColor.info);
		frame.setBounds(100, 100, 672, 578);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null); 
		
		
		Label label = new Label("To");
		label.setFont(new Font("Dialog", Font.BOLD, 12));
		label.setAlignment(Label.CENTER);
		label.setBackground(SystemColor.menu);
		label.setBounds(43, 92, 70, 37);
		frame.getContentPane().add(label);
		
		textField_2 = new JTextField();
		textField_2.setBackground(SystemColor.window);
		textField_2.setUI(new JTextFieldHintUI("Add a subject", Color.gray));
		textField_2.setBounds(43, 165, 577, 37);
		frame.getContentPane().add(textField_2);
		textField_2.setColumns(10);
		
		textField = new JTextField();
		textField.setBounds(148, 92, 472, 37);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JTextArea textArea = new JTextArea();
		textArea.setRows(10);
		textArea.setBounds(43, 241, 577, 204);
		frame.getContentPane().add(textArea);
		textArea.setUI(new JTextFieldHintUI("Add a message", Color.gray));
		
		Button button = new Button("Send");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				JOptionPane.showMessageDialog(null, "This is the encrypt message","Encrypt message", JOptionPane.INFORMATION_MESSAGE);
				Sender_GUI sender = new Sender_GUI();
				sender.setVisible(true);
			}
		});
		button.setForeground(Color.WHITE);
		button.setBackground(SystemColor.textHighlight);
		button.setFont(new Font("Dialog", Font.BOLD, 12));
		button.setBounds(43, 473, 90, 37);
		frame.getContentPane().add(button);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(43, 64, 577, 22);
		frame.getContentPane().add(separator);
		
		JLabel lblNewLabel = new JLabel("Sender Mailbox");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblNewLabel.setBounds(189, 14, 271, 37);
		frame.getContentPane().add(lblNewLabel);
	}
}
