import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Sender {

	private String hash;

	public Sender() {
	}

	public void send(String algorithm, String text) throws IOException {
		writeFile("email", text);

		switch (algorithm) {
		case "SHA-256":
			try {
				hash = sha256(text);
				writeFile("Checksum", hash);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			break;
		case "MD5":
			try {
				hash = md5(text);
				writeFile("Checksum", hash);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			break;
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

	public String sha256(String text) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(text.getBytes());

		// convert the byte to hex
		byte byteData[] = md.digest();
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < byteData.length; i++) {
			String hex = Integer.toHexString(0xff & byteData[i]);
			if (hex.length() == 1)
				hexString.append('0');
			hexString.append(hex);
		}
		System.out.println("Hex format (Sender): " + hexString.toString());
		return hexString.toString();
	}
	
	public String md5 (String text) throws NoSuchAlgorithmException, IOException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(text.getBytes());
		byte[] mdBytes = md.digest();
		
		//convert byte to hex
		StringBuffer strBuffer = new StringBuffer();
		for (int i = 0; i < mdBytes.length; i++) {
			// set strBuffer 
			strBuffer.append(Integer.toString((mdBytes[i] & 0xff) + 0x100, 16).substring(1));
		}
		
		return strBuffer.toString();
	}
}
