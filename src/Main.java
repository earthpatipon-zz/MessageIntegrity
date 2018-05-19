import java.io.IOException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		String algorithm;
		Sender sd = new Sender();
		Receiver rv = new Receiver();
		
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
		input.close();
		
		try {
			sd.send(algorithm, text);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("-----Receiver-----");
		rv.read(algorithm);
	}

}
