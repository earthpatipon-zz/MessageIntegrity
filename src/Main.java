import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Scanner;
import javax.crypto.Cipher;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;

public class Main {

	public static void main(String[] args) throws FileNotFoundException, NoSuchAlgorithmException, InvalidKeyException,
			SignatureException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException,
			UnsupportedEncodingException, InvalidAlgorithmParameterException {
		
		String algorithm = "";
		Sender sd = new Sender();
		Recipient rp = new Recipient();
		PrivateKey privateKey = null;
		PublicKey publicKey = null;

		// TODO Auto-generated method stub
		System.out.println("Available algorigthm for Message Integrity Service:");
		System.out.println("1. Checksum with MD5");
		System.out.println("2. Checksum with SHA-1");
		System.out.println("3. Checksum with SHA-256");
		System.out.println("4. Using Public Key and Private Key");
		System.out.println("5. No, I prefer to not use message integrity service.");
		System.out.print("Select algorithm (in number): ");

		Scanner input = new Scanner(System.in);
		int chose = Integer.parseInt(input.nextLine());

		switch (chose) {
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
			algorithm = "Key";
			//Build private, public key pair
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
			keyGen.initialize(1024);
			KeyPair keyPair = keyGen.genKeyPair();
			publicKey = keyPair.getPublic();
			privateKey = keyPair.getPrivate();
			break;
		case 5:
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
			sd.send(algorithm, text, publicKey);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		System.out.println("-----Receiver-----");
//		rp.read(algorithm, privateKey);
	}

}
