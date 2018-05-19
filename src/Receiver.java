import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Receiver {

	private String hash;
	private String text;
	private String checksum;
	
	public Receiver() {
	}

	public void read(String algorithm) {
		readFile("email", text);

		switch (algorithm) {
		case "SHA-256":
			try {
				hash = sha256(text);
				readFile("Checksum", checksum);
				validate(hash, checksum);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			break;
		default:
			break;
		}
	}

	public void readFile(String filename, String toStr) {
		BufferedReader br = null;
		FileReader fr = null;
		StringBuilder sb;
		String currentLine;

		try {
			fr = new FileReader("inbox/" + filename + ".txt");
			br = new BufferedReader(fr);
			sb = new StringBuilder();
			
			while ((currentLine = br.readLine()) != null) {
				sb.append(currentLine);
			}
			toStr = sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void validate(String hashText, String checksumValue) {
		System.out.println(hashText);
		System.out.println(checksumValue);
		if(hashText.equals(checksumValue)) {
			System.out.println("Correct. This message is secured.");
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
}
