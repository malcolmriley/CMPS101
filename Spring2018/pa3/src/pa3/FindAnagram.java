package pa3;

import java.io.File;
import java.util.List;
import java.util.Scanner;

public class FindAnagram {
	
	// Constants
	private static final String PROMPT = "Enter a string of characters: ";
	private static final String AGAIN = "Find more anagrams? (y/n): ";
	private static final String USAGE = "Usage: \"FindAnagram <File>\", where <File> is the location of the dictionary to use.";
	private static final String ERROR = "File \"%s\" not found!\n";

	public static void main(String[] passedArguments) {
		if (passedArguments.length > 0) {
			File dictionaryFile = new File(passedArguments[0]);
			if (dictionaryFile.exists()) {
				System.out.println("Loading Dictionary...");
				AnagramDictionary dictionary = AnagramDictionary.fromFile(dictionaryFile);
				System.out.println("Load Complete!\n");
				
				try(Scanner input = new Scanner(System.in)) {
					do {
						System.out.print(PROMPT);
						String inputLine = input.nextLine();
						printAnagramsFor(dictionary, inputLine);
					} while(!shouldExit(input));
				}
			}
			else {
				System.out.format(ERROR);
			}
		}
		else {
			System.out.println(USAGE);
		}
	}
	
	public static boolean shouldExit(Scanner passedScanner) {
		boolean parsed = false;
		boolean result = false;
		while (!parsed) {
			System.out.print(AGAIN);
			String input = passedScanner.nextLine();
			if (input.equalsIgnoreCase("y")) {
				result = false;
				break;
			}
			else if (input.equalsIgnoreCase("n")) {
				result = true;
				break;
			}
		}
		return result;
	}
	
	public static void printAnagramsFor(AnagramDictionary passedDictionary, String passedWord) {
		List<String> anagramList = passedDictionary.getAnagrams(passedWord);
		if (!anagramList.isEmpty()) {
			StringBuilder builder = new StringBuilder(String.format("Known anagrams for word \"%s\":\n\t", passedWord.toUpperCase()));
			for (int index = 0; index < anagramList.size(); index += 1) {
				builder.append(anagramList.get(index));
				if (index < (anagramList.size() - 1)) {
					builder.append(", ");
				}
			}
			builder.append("\n");
			System.out.println(builder.toString());
		}
		else {
			System.out.format("No known anagrams for word \"%s\"!\n", passedWord);
		}
	}

}
