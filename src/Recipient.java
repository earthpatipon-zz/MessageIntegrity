import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class Recipient {

	private String hash;
	private String text;
	private String checksum;
	private PrivateKey privateKey;
	private String rpName;
	
	public Recipient() {
	}

	public void read(String algorithm, PrivateKey privateKey) throws NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
		text = readFile("email");
		
		String[] message = text.split(","); //message[0] = recipient name
											//other = content
		text = message[1];
		if (message.length > 2) {
			for (int i = 2; i < message.length; i++) {
				text += "," + message[i];
			}
		}

		switch (algorithm) {
			case "SHA-1":
				try {
					hash = sha1(text);
					checksum = readFile("checksum");
					validate(hash, checksum);
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				}
				break;
			case "SHA-256":
				try {
					hash = sha256(text);
					checksum = readFile("checksum");
					validate(hash, checksum);
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				}
				break;
			case "MD5":
				try {
					hash = md5(text);
					checksum = readFile("checksum");
					validate(hash, checksum);
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				}
				break;
			case "Asymmetric Encryption":
				byte[] textByte = Base64.getDecoder().decode(text);
				Cipher cipher = Cipher.getInstance("RSA");
				cipher.init(Cipher.DECRYPT_MODE, privateKey);
				text = new String(cipher.doFinal(textByte));
				hash = text;
				writeFile("email", hash);
			default:
				
				break;
			}
	}
	
	public void writeFile(String filename, String text) {
		BufferedWriter bw = null;
		FileWriter fw = null;

		try {
			fw = new FileWriter("inbox/" + filename + ".txt");
			bw = new BufferedWriter(fw);
			bw.write(text);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

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
	
	public String validate(String hashText, String checksumValue) {
		if(hashText.equals(checksumValue)) {
			//System.out.println("Correct. This email message is secured.");
			return "Integrity maintain!";
		}
		else {
			//System.out.println("Incorrect. This email message isn't secured.");
			return "Integrity lost!";
		}
	}
	
	public String md5 (String text) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(text.getBytes());
		
		//convert byte to hex
		byte[] mdBytes = md.digest();
		StringBuffer strBuffer = new StringBuffer();
		for (int i = 0; i < mdBytes.length; i++) {
			// set strBuffer 
			strBuffer.append(Integer.toString((mdBytes[i] & 0xff) + 0x100, 16).substring(1));
		}
		//System.out.println("Text in email: " + text);
		//System.out.println("Checksum with MD5 (Recipient): " + strBuffer.toString());
		return strBuffer.toString();
	}
	
	public String sha1 (String text) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-1");
		md.update(text.getBytes());
		
		//convert byte to hex
		byte[] mdBytes = md.digest();
		StringBuffer strBuffer = new StringBuffer();
		for (int i = 0; i < mdBytes.length; i++) {
			// set strBuffer 
			strBuffer.append(Integer.toString((mdBytes[i] & 0xff) + 0x100, 16).substring(1));
		}
		//System.out.println("Text in email: " + text);
		//System.out.println("Checksum with SHA-1 (Recipient): " + strBuffer.toString());
		return strBuffer.toString();
	}
	
	public String sha256(String text) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(text.getBytes());

		// convert byte to hex
		byte byteData[] = md.digest();
		StringBuffer strBuffer = new StringBuffer();
		for (int i = 0; i < byteData.length; i++) {
			String hex = Integer.toHexString(0xff & byteData[i]);
			if (hex.length() == 1)
				strBuffer.append('0');
			strBuffer.append(hex);
		}
		//System.out.println("Text in email: " + text);
		//System.out.println("Checksum with SHA-256 (Recipient): " + strBuffer.toString());
		return strBuffer.toString();
	}
	
	public String getHash() {
		return this.hash;
	}

	public void setPrivateKey(PrivateKey key) {
		this.privateKey = key;
	}
	
	public PrivateKey getPrivateKey() {
		return this.privateKey;
	}
	
	public void setName(String name) {
		this.rpName = name;
	}
	
	public String getName() {
		return this.rpName;
	}
}
