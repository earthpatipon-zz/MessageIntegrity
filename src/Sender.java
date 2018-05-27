import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class Sender {

	private String hash;

	public Sender() {
	}

	public void send(String algorithm, String text, PublicKey publicKey) throws IOException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException {
		//writeFile("email", text);

		switch (algorithm) {
		case "SHA-1":
			try {
				hash = sha1(text);
				writeFile("checksum", hash);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			break;
		case "SHA-256":
			try {
				hash = sha256(text);
				writeFile("checksum", hash);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			break;
		case "MD5":
			try {
				hash = md5(text);
				writeFile("checksum", hash);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			break;
		case "Key":
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			text = cipher.doFinal(text.getBytes()).toString();
//			System.out.println("in case using key: "+text);
			break;
		default:
			break;
		}
		
		System.out.println(text);
		writeFile("email", text);
	}

	public void writeFile(String filename, String text) {
		BufferedWriter bw = null;
		FileWriter fw = null;

		try {
//			fw = new FileWriter("email/" + filename + ".txt");
			fw = new FileWriter("inbox/" + filename + ".txt");
			bw = new BufferedWriter(fw);
			bw.write(text);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public String md5(String text) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(text.getBytes());

		// convert byte to hex
		byte[] mdBytes = md.digest();
		StringBuffer strBuffer = new StringBuffer();
		for (int i = 0; i < mdBytes.length; i++) {
			// set strBuffer
			strBuffer.append(Integer.toString((mdBytes[i] & 0xff) + 0x100, 16).substring(1));
		}
		System.out.println("Checksum with MD5 (Sender): " + strBuffer.toString());
		return strBuffer.toString();
	}

	public String sha1(String text) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-1");
		md.update(text.getBytes());

		// convert byte to hex
		byte[] mdBytes = md.digest();
		StringBuffer strBuffer = new StringBuffer();
		for (int i = 0; i < mdBytes.length; i++) {
			// set strBuffer
			strBuffer.append(Integer.toString((mdBytes[i] & 0xff) + 0x100, 16).substring(1));
		}
		System.out.println("Checksum with SHA-1 (Sender): " + strBuffer.toString());
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
		System.out.println("Checksum with SHA-256 (Sender): " + strBuffer.toString());
		return strBuffer.toString();
	}
	
}
