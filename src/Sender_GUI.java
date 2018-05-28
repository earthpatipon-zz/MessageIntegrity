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
import java.util.Base64;

import javax.swing.JTextArea;
import javax.swing.event.AncestorListener;
import javax.swing.event.AncestorEvent;

public class Sender_GUI extends JFrame {

	private JPanel contentPane;
	private String decrypt="";
	private static String text;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					text = readFile("email");
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
//	public Sender_GUI(String text) {
//		initialize(text);
//	}
	
	public Sender_GUI() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		
		Recipient rp = new Recipient();
		String algorithm = rp.readFile("algorithm");
		if(algorithm != "None") {
			PrivateKey privatekey = rp.getPrivateKey();
			rp.read(algorithm, privatekey);
			decrypt = rp.getHash();
		}
		
		String[] message = rp.readFile("email").split(",");
		
		rp.setName(message[0]);
		text = message[1];
		if (message.length > 2) {
			for (int i = 2; i < message.length; i++) {
				text += "," + message[i];
			}
		}
		initialize(rp, text);
	}
	
	private void initialize(Recipient rp, String text) {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 672, 578);
		contentPane = new JPanel();
		contentPane.addAncestorListener(new AncestorListener() {
			public void ancestorAdded(AncestorEvent arg0) {
				if (!rp.readFile("algorithm").equals("None")) {
					String warning = rp.validate(rp.getHash(), rp.readFile("checksum"));
					if (warning.equals("Integrity lost!"))
					{
						JOptionPane.showMessageDialog(null, warning, "Warning", JOptionPane.ERROR_MESSAGE);
					}
					else
						JOptionPane.showMessageDialog(null, warning, "Confirm message", JOptionPane.INFORMATION_MESSAGE);
				}
			}
			public void ancestorMoved(AncestorEvent arg0) {
			}
			public void ancestorRemoved(AncestorEvent arg0) {
			}
		});
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
		
		Label label_4 = new Label(rp.getName());
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
		textArea.setText(text);
		textArea.setEditable(false);
		textArea.setBounds(189, 204, 440, 295);
		contentPane.add(textArea);
	}
	public static String readFile(String filename) {
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
//			String[] message = sb.toString().split(",");
//			sb.setLength(0); 
//			sb.append(message[1]);
//			if (message.length > 2) {
//				for (int i = 2; i < message.length; i++) {
//					sb.append("," + message[i]);
//				}
//			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
}
