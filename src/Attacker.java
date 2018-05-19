import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Attacker {

	public Attacker () {
		// Constructor
	}
	
	public void attack (String path) throws FileNotFoundException {
		try {
			
			String str = "Attacked!";
			BufferedWriter bw = new BufferedWriter (new FileWriter (new File (path)));
			bw.write(str);
			
			bw.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
