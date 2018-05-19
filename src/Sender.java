import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;

public class Sender {
	
	private String algorithm;
	private String email;
	
	public Sender(String algo) {
		this.algorithm = algo;
	}
	
	public void writeEmail(String text) {
		FileOutputStream fop = null;
		File file;

		try {
			file = new File("inbox/email.txt");
			fop = new FileOutputStream(file);

			// if file doesn't exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			// get the content in bytes
			byte[] contentInBytes = text.getBytes();

			fop.write(contentInBytes);
			fop.flush();
			fop.close();

			System.out.println("Done");

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fop != null) {
					fop.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public String md5 (FileOutputStream fop) {
		String str = "";
		
		return str;
		
	}
	
	
}
