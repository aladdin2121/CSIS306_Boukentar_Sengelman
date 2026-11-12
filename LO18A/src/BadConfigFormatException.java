/*
 * Class: BadConfigFormatException
 * 
 * Details:
 * creates a customized exception for bad configuration
 * 
 * Authors: Sophie Sengelmann-Lipps
 * 
 * Start Date: 10/29/2026
 * 
 * Collaborators: None
 * 
 * Sources: None
 */


public class BadConfigFormatException extends Exception{
	private static final long serialVersionUID = 1L;

	public BadConfigFormatException() {
		super("The configuration file has an invalid format.");
	}

	public BadConfigFormatException(String message) {
		super(message);
	}
}
