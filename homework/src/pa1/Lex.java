/*********************************************************************
 * Malcolm Riley
 *
 * CruzID: masriley
 * Assignment: pa1
 *
 * 10-2017
 *********************************************************************/
package pa1;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Lex {

	public static void main(String[] passedArguments) {
		if (passedArguments.length < 2) {
			System.err.println("Fewer than two arguments provided. Usage: [inputfile] [outputfile]. Lex will now terminate.");
		}
		else {
			// Build input Array
			String[] stringArray = null;
			try (FileReader lineFileReader = new FileReader(passedArguments[0])) {
				try (Scanner lineScanner = new Scanner(lineFileReader)) {
					int numLines = 0;
					while (lineScanner.hasNextLine()) {
						numLines += 1;
					}
					stringArray = new String[numLines];
					try(FileReader fileReader = new FileReader(passedArguments[0])) {
						try(Scanner scanner = new Scanner(fileReader)) {
							for (int ii = 0; ii < numLines; ii += 1) {
								stringArray[ii] = scanner.nextLine();
							}
						}
					}
				}
			}
			catch (IOException passedException) {
				System.err.println("Error reading input file!");
				passedException.printStackTrace();
			}

			// Build List
			if ((stringArray != null) && (stringArray.length > 0)) {
				List list = new List();

				// Insert elements sorted
				list.append(0);
				for (int ii = 1; ii < stringArray.length; ii += 1) {
					list.moveBack();
					String currentString = stringArray[ii];
					String cursorString;
					while (list.index() >= 0) {
						cursorString = stringArray[list.get()];
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
				try (FileWriter writer = new FileWriter(passedArguments[1])) {
					list.moveFront();
					while ((list.index() < list.length()) && (list.index() >= 0)) {
						writer.write(stringArray[list.get()] + "\n");
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
