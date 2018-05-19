import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		String algorithm;
		
		// TODO Auto-generated method stub
		System.out.println("Available algorigthm for Message Integrity Service:");
		System.out.println("1. Checksum with SHA-256");
		System.out.print("Select algorithm (in number): ");
		
		Scanner input = new Scanner(System.in);
		int chose = Integer.parseInt(input.nextLine());
	
		switch(chose){
		case 1:
			algorithm = "SHA-256";
			break;
		default:
			algorithm = "";
			break;
		}
		
		System.out.print("Write text in email: ");
		String text = input.nextLine();
		input.close();
		
		Sender sd = new Sender();
		Receiver rv = new Receiver();
		
		sd.send(algorithm, text);
	}

}
