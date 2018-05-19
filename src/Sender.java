import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Sender {

	private String algorithm;
	private String email;
	private String hash;

	public Sender(String algo) {
		this.algorithm = algo;
	}

	public void writeEmail(String text) {
		BufferedWriter bw = null;
		FileWriter fw = null;

		try {
<<<<<<< HEAD
			file = new File("inbox/email.txt");
			fop = new FileOutputStream(file);

			// if file doesn't exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			// get the content in bytes
			byte[] contentInBytes = text.getBytes();
=======
			fw = new FileWriter("inbox/email.txt");
			bw = new BufferedWriter(fw);
			bw.write(text);
			bw.close();
			System.out.println(text);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
>>>>>>> 5226f9387f0b12fe04cba296000dd958fbaed118

	public void createChecksum(String algorithm) throws NoSuchAlgorithmException {
		String x = sha256("123456");
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
		System.out.println("Hex format : " + hexString.toString());
		return hexString.toString();
	}
<<<<<<< HEAD
	
	public String md5 (FileOutputStream fop) {
		String str = "";
		
		return str;
		
	}
	
	
=======
>>>>>>> 5226f9387f0b12fe04cba296000dd958fbaed118
}
