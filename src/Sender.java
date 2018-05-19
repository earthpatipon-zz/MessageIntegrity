import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Sender {

	private String hash;
	private String path;

	public Sender() {
	}

	public void send(String algorithm, String text) throws IOException {
		writeFile("email", text);

		switch (algorithm) {
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
		default:
			break;
		}
	}

	public void writeFile(String filename, String text) {
		BufferedWriter bw = null;
		FileWriter fw = null;

		try {
			path = "inbox/" + filename + ".txt";
			fw = new FileWriter(path);
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
		System.out.println("Checksum with MD5 (Sender): " + strBuffer.toString());
		return strBuffer.toString();
	}
	
	public String getPath () {
		return path;
	}
}
