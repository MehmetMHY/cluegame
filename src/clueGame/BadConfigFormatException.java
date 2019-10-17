/**. 
 * BadConfigFormatException class for csv and text files
 * 
 * @author Mehmet Yilmaz
 * @author Ruidi Huang
 */

package clueGame;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.NumberFormat;

public class BadConfigFormatException extends Throwable {
	
	public BadConfigFormatException() {
		super();
	}

	public BadConfigFormatException(String message) {
		super(message);
	}
	
	@Override
	public String toString() {
		return null;
	}
	
}

