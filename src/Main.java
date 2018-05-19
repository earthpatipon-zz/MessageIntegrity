import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Available algorigthm for Message Integrity Service:");
		System.out.println("1. SHA-256");
		System.out.print("Select algorithm (in number): ");
		
		String algorithm;
		Scanner input = new Scanner(System.in);
		int chose = Integer.parseInt( input.nextLine() );
		
		switch(chose){
		case 1:
			algorithm = "SHA-256";
			break;
		case 2:
			algorithm = "MD5";
		default:
			algorithm = "";
			break;
		}
		
		Sender sd = new Sender(algorithm);
		Receiver rv = new Receiver(algorithm);
		
		System.out.println("Write text in email: ");
		String text = input.nextLine();
		input.close();
		
		sd.writeEmail(text);
	}

}
