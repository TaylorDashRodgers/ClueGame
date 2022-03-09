package clueGame;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class BadConfigFormatException extends Exception {
	
    public BadConfigFormatException() throws FileNotFoundException{
        super("Error: Bad File Format");
        
    }
    
    public BadConfigFormatException(String reason) throws FileNotFoundException{
        super("Error: Bad File Format: " + reason);
		PrintWriter out = new PrintWriter("logfile.txt");
		out.println("Error: Bad File Format: " + reason);
		out.close();
    }

}
