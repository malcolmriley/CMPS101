package pa3;

import java.io.File;
import java.util.List;
import java.util.Scanner;

/**
 * Anagram finding program for PA3.
 * 
 * @author Malcolm Riley 2018
 */
public class FindAnagram {

	public static void main(String[] passedArguments) {
		if (passedArguments.length > 0) {
			File dictionaryFile = new File(passedArguments[0]);
			if (dictionaryFile.exists()) {
				System.out.println("Loading Dictionary...");
				AnagramDictionary dictionary = AnagramDictionary.fromFile(dictionaryFile);
				System.out.println("Load Complete!\n");
				
				try(Scanner input = new Scanner(System.in)) {
					do {
						System.out.print("Enter a string of characters: ");
						String inputLine = input.nextLine().trim();
						printAnagramsFor(dictionary, inputLine);
					} while(!shouldExit(input));
				}
			}
			else {
				System.out.format("File \"%s\" not found!\n");
			}
		}
		else {
			System.out.println("Usage: \"FindAnagram <File>\", where <File> is the location of the dictionary to use.");
		}
	}
	
	/* Internal Methods */
	
	private static boolean shouldExit(Scanner passedScanner) {
		boolean parsed = false;
		boolean result = false;
		while (!parsed) {
			System.out.print("Find more anagrams? (y/n): ");
			String input = passedScanner.nextLine().trim();
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
	
	private static void printAnagramsFor(AnagramDictionary passedDictionary, String passedWord) {
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
