import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;

import java.awt.Color;
import java.awt.Dimension;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
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
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;
import java.util.Scanner;

import javax.swing.JSeparator;
import java.awt.List;
import javax.swing.JList;
import java.awt.ScrollPane;
import javax.swing.JScrollPane;

public class GUI_Email {

	private JFrame frame;
	private JTextField textField_2;
	private JTextField textField;
	private String word;
	

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
		label.setBounds(202, 92, 70, 37);
		frame.getContentPane().add(label);

		textField_2 = new JTextField();
		textField_2.setBackground(SystemColor.window);
		textField_2.setUI(new JTextFieldHintUI("Add a subject", Color.gray));
		textField_2.setBounds(202, 165, 435, 37);
		frame.getContentPane().add(textField_2);
		textField_2.setColumns(10);

		textField = new JTextField();
		textField.setBounds(278, 92, 359, 37);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		JTextArea textArea = new JTextArea();
		textArea.setRows(10);
		textArea.setBounds(202, 241, 435, 204);
		frame.getContentPane().add(textArea);
		textArea.setUI(new JTextFieldHintUI("Add a message", Color.gray));

		Button button = new Button("Send");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});

		button.setForeground(Color.WHITE);
		button.setBackground(SystemColor.textHighlight);
		button.setFont(new Font("Dialog", Font.BOLD, 12));
		button.setBounds(530, 477, 90, 37);
		frame.getContentPane().add(button);

		JSeparator separator = new JSeparator();
		separator.setBounds(21, 64, 616, 22);
		frame.getContentPane().add(separator);

		JLabel lblNewLabel = new JLabel("Sender Mailbox");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblNewLabel.setBounds(189, 14, 271, 37);
		frame.getContentPane().add(lblNewLabel);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(21, 92, 153, 353);
		frame.getContentPane().add(scrollPane);

		JList list = new JList();
		list.setFont(new Font("Tahoma", Font.PLAIN, 16));
		scrollPane.setViewportView(list);
		DefaultListModel suggestList = new DefaultListModel();
		suggestList.addElement("MD5");
		suggestList.addElement("SHA-1");
		suggestList.addElement("SHA-256");
		suggestList.addElement("Asymmetric Encryption");
		suggestList.addElement("None");
		list.setModel(suggestList);

		// click list
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent mouse) {
				int point;
				int count = mouse.getClickCount();
				JList theList = (JList) mouse.getSource();
				if (count == 2) // double click
				{
					point = theList.locationToIndex(mouse.getPoint());
					if (point >= 0) {
						Object object = theList.getModel().getElementAt(point);
						word = object.toString();
						System.out.println("Your selected algorithm is " + word);

					}
				}
			}
		});

		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {

				Sender sender = new Sender();
				Recipient recipient = new Recipient();
				
				PrivateKey privateKey = null;
				PublicKey publicKey = null;
				KeyPairGenerator keyGen;
				
				try {
					if (word.equals("Asymmetric Encryption")) {
						keyGen = KeyPairGenerator.getInstance("RSA");
						keyGen.initialize(1024);
						KeyPair keyPair = keyGen.genKeyPair();
						publicKey = keyPair.getPublic();
						privateKey = keyPair.getPrivate();
						recipient.setPrivateKey(privateKey);
					}
				} catch (NoSuchAlgorithmException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				try {
					sender.send(word, textField.getText() + "," + textArea.getText(), publicKey);
					sender.writeFile("algorithm", word);
					if(!word.equals("None")) {
						String encrypt = sender.getHash();
						JOptionPane.showMessageDialog(null, "Encrypted message with " + word + " :\n"
								+ encrypt, "Send Completed", JOptionPane.INFORMATION_MESSAGE);
					}
					else {
						JOptionPane.showMessageDialog(null, "Send Completed", "", JOptionPane.INFORMATION_MESSAGE);
					}
				} catch (InvalidKeyException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException
						| NoSuchAlgorithmException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
}
