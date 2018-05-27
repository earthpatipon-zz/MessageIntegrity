import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class Main {

	public static void main(String[] args) throws FileNotFoundException, NoSuchAlgorithmException, InvalidKeyException, SignatureException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException, InvalidAlgorithmParameterException {
		String algorithm;
		Sender sd = new Sender();
		Recipient rp = new Recipient();
		
		GUI_Email guiEmail = new GUI_Email();
		
		// TODO Auto-generated method stub
		System.out.println("Available algorigthm for Message Integrity Service:");
		System.out.println("1. Checksum with MD5");
		System.out.println("2. Checksum with SHA-1");
		System.out.println("3. Checksum with SHA-256");
		System.out.println("4. No, I prefer to not use message intigrety service.");
		System.out.print("Select algorithm (in number): ");
		
		Scanner input = new Scanner(System.in);
		int chose = Integer.parseInt(input.nextLine());
	
		switch(chose){
			case 1:
				algorithm = "MD5";
				break;
			case 2:
				algorithm = "SHA-1";
				break;
			case 3:
				algorithm = "SHA-256";
				break;
			case 4:
				algorithm = "NONE";
				break;
			default:
				algorithm = "";
				break;
		}
		
		System.out.println("-----Sender-----");
		System.out.print("Write text in email: ");
		String text = input.nextLine();
		input.close();
		
		try {
			sd.send(algorithm, text);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("-----Receiver-----");
		rp.read(algorithm);
	}

}
