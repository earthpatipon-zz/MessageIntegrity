import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		String algorithm;
		Sender sd = new Sender();
		Receiver rv = new Receiver();
		Attacker at = new Attacker();
		
		// TODO Auto-generated method stub
		System.out.println("Available algorigthm for Message Integrity Service:");
		System.out.println("1. Checksum with SHA-256");
		System.out.println("2. Checksum with MD5");
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
		default:
			algorithm = "";
			break;
		}
		
		System.out.println("-----Sender-----");
		System.out.print("Write text in email: ");
		String text = input.nextLine();
		
		try {
			sd.send(algorithm, text);
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
		
		System.out.println("-----Receiver-----");
		rv.read(algorithm);
	}

}
