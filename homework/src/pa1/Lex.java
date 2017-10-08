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
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Lex {

	public static void main(String[] passedArguments) {
		if (passedArguments.length < 2) {
			System.err.println("Fewer than two arguments provided. Usage: [inputfile] [outputfile]. Lex will now terminate.");
		}
		else {
			// Build input Array
			ArrayList<String> stringArray = new ArrayList<String>();
			try(FileReader fileReader = new FileReader(passedArguments[0])) {
				try (BufferedReader reader = new BufferedReader(fileReader)) {
					reader.lines().forEachOrdered(stringArray::add);
				}
			}
			catch (IOException passedException) {
				System.err.println("Error reading input file!");
				passedException.printStackTrace();
			}
			
			// Build List
			if (!stringArray.isEmpty()) {
				List list = new List();
				
				// Insert elements sorted
				list.append(0);
				for (int ii = 1; ii < stringArray.size(); ii += 1) {
					list.moveBack();
					String currentString = stringArray.get(ii);
					String cursorString;
					while(list.index() >= 0) {
						cursorString = stringArray.get(list.get());
						if (currentString.compareTo(cursorString) > 0) {
							list.insertAfter(ii);
							break;
						}
						if (list.index() == 0) {
							list.prepend(ii);
							break;
						}
						list.movePrev();
					}
				}

				// Write to file
				try(FileWriter writer = new FileWriter(passedArguments[1])) {
					list.moveFront();
					while((list.index() < list.length()) && (list.index() >= 0)) {
						writer.write(stringArray.get(list.get()) + "\n");
						list.moveNext();
					}
				}
				catch (IOException passedException) {
					System.err.println("Error writing output file!");
					passedException.printStackTrace();
				}
			}
			else {
				System.err.println("Error while reading input file - no valid lines detected!");
			}
		}
	}

}
