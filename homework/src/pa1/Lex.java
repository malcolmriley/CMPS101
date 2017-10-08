/*********************************************************************
 * Malcolm Riley
 * 
 * CruzID: masriley
 * Assignment: pa1
 * 
 * 10-2017
 *********************************************************************/
package pa1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Lex {

	public static void main(String[] passedArguments) {
		if (passedArguments.length < 2) {
			System.err.println("Fewer than two arguments provided. Usage: [inputfile] [outputfile]");
		}
		else {
			try (BufferedReader reader = new BufferedReader(new FileReader(passedArguments[0]))) {
				ArrayList<String> stringArray = new ArrayList<String>();
				reader.lines().forEachOrdered(stringArray::add);
			} catch (IOException passedException) {
				passedException.printStackTrace();
			}
		}
	}

}
