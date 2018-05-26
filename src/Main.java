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
		Attacker at = new Attacker();
		
		// TODO Auto-generated method stub
		System.out.println("Available algorigthm for Message Integrity Service:");
		System.out.println("1. Checksum with SHA-256");
		System.out.println("2. Checksum with MD5");
		System.out.println("3. Checksum with SHA-1");
		System.out.print("Select algorithm (in number): ");
		
		Scanner input = new Scanner(System.in);
		int chose = Integer.parseInt(input.nextLine());
	
		switch(chose){
			case 1:
				algorithm = "SHA-256";
				break;
			case 2:
				algorithm = "MD5";
				break;
			case 3:
				algorithm = "SHA-1";
				break;
			default:
				algorithm = "";
				break;
		}
		
		System.out.println("-----Sender-----");
		System.out.print("Write text in email: ");
		String text = input.nextLine();
		
		try {
			sd.send(algorithm, text, rp.getPublicKey());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("-----Attacker-----");
		System.out.println("Have attacker or not? (y/n): ");
		if (input.nextLine().equals("y")) {
			at.attack(sd.getPath());
		}

		input.close();
		
		System.out.println("-----Recipient-----");
		rp.read(algorithm, sd.getPublicKey());
	}

}
