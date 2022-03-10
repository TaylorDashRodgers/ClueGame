package clueGame;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class BadConfigFormatException extends Exception {
	
	// Default constructor with default message
    public BadConfigFormatException() throws FileNotFoundException{
        super("Error: Bad File Format");
        
    }
    
    // Parameterized constructor with a string input for exception message   
    public BadConfigFormatException(String reason) throws FileNotFoundException{
        super("Error: Bad File Format: " + reason);
		PrintWriter out = new PrintWriter("logfile.txt");
		out.println("Error: Bad File Format: " + reason);
		out.close();
    }

}
