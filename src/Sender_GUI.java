import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import java.awt.SystemColor;
import java.awt.Label;
import java.awt.Color;
import javax.swing.JSlider;
import java.awt.Canvas;
import java.awt.TextArea;
import java.awt.ScrollPane;
import javax.swing.JScrollPane;
import java.awt.Panel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;

import javax.swing.JTextArea;

public class Sender_GUI extends JFrame {

	private JPanel contentPane;
	private String decrypt="";
	private String text;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Sender_GUI frame = new Sender_GUI();
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
	public Sender_GUI() {
		initialize();
		
		
	}
	
	public Sender_GUI(String algorithm, PrivateKey privatekey) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
	
		Recipient rp = new Recipient();
		rp.read(algorithm, privatekey);
		
		switch (algorithm) {
		case "SHA-1":
			try {
				text = readFile("email");
				decrypt = rp.sha1(algorithm);
				
			
				
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			break;
		case "SHA-256":
			try {
				text = readFile("email");
				decrypt = rp.sha256(algorithm);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			break;
		case "MD5":
			try {
				text = readFile("email");
				decrypt = rp.md5(algorithm);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			break;
		case "Key":
			text = readFile("email");
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, privatekey);
			text = cipher.doFinal(text.getBytes()).toString();
//			System.out.println("\nin case using key: "+text);
		default:
			break;
		}
		initialize();
	}
	
	private void initialize() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 672, 578);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.info);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null); 

		
		Label label_2 = new Label("Sent Items");
		label_2.setFont(new Font("Dialog", Font.PLAIN, 18));
		label_2.setBackground(new Color(255, 255, 255));
		label_2.setAlignment(Label.CENTER);
		label_2.setBounds(0, 83, 163, 24);
		contentPane.add(label_2);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(178, 64, 464, 22);
		contentPane.add(separator);
		
		JLabel lblNewLabel = new JLabel("Receiver Mailbox");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblNewLabel.setBounds(189, 14, 271, 37);
		contentPane.add(lblNewLabel);
		
		Label label = new Label("Inbox 1");
		label.setAlignment(Label.CENTER);
		label.setBackground(new Color(176, 224, 230));
		label.setFont(new Font("Dialog", Font.BOLD, 18));
		label.setBounds(0, 42, 163, 24);
		contentPane.add(label);
		
		Label label_3 = new Label("Junk Email");
		label_3.setFont(new Font("Dialog", Font.PLAIN, 18));
		label_3.setBackground(Color.WHITE);
		label_3.setAlignment(Label.CENTER);
		label_3.setBounds(0, 123, 163, 24);
		contentPane.add(label_3);
		
		Label label_4 = new Label("Name Surname");
		label_4.setFont(new Font("Dialog", Font.PLAIN, 20));
		label_4.setBackground(SystemColor.info);
		label_4.setBounds(271, 113, 293, 24);
		contentPane.add(label_4);
		
		JLabel lblNewLabel_1 = new JLabel("");
		Image img = new ImageIcon (this.getClass().getResource("/user.png")).getImage();
		lblNewLabel_1.setIcon(new ImageIcon(img));
		lblNewLabel_1.setBounds(194, 103, 62, 64);
		contentPane.add(lblNewLabel_1);
		
		Label label_5 = new Label("Tue 5/22, 10:30 AM");
		label_5.setFont(new Font("Dialog", Font.PLAIN, 16));
		label_5.setBackground(SystemColor.info);
		label_5.setBounds(271, 143, 293, 24);
		contentPane.add(label_5);
		
		Label label_1 = new Label("");
		label_1.setBackground(SystemColor.text);
		label_1.setBounds(0, 0, 163, 531);
		contentPane.add(label_1);
		
		TextArea textArea = new TextArea();
		textArea.setFont(new Font("Dialog", Font.PLAIN, 20));
		textArea.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JOptionPane.showMessageDialog(null, decrypt,"Decrypt message", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		textArea.setText(text);
		textArea.setEditable(false);
		textArea.setBounds(189, 204, 440, 295);
		contentPane.add(textArea);
	}
	public String readFile(String filename) {
		BufferedReader br = null;
		FileReader fr = null;
		StringBuilder sb = null;
		String currentLine = "";

		try {
			fr = new FileReader("inbox/" + filename + ".txt");
			br = new BufferedReader(fr);
			sb = new StringBuilder();
			
			while ((currentLine = br.readLine()) != null) {
				sb.append(currentLine);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
}
