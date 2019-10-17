/**. 
 * BadConfigFormatException class for csv and text files
 * 
 * @author Mehmet Yilmaz
 * @author Ruidi Huang
 */

package clueGame;

@SuppressWarnings("serial")
public class BadConfigFormatException extends Exception{
	
	public BadConfigFormatException() {
		super();
	}

	public BadConfigFormatException(String message) {
		super(message);
	}
	
	public String toString() {
		return null;
	}
	
}

