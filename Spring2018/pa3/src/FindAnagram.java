import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FindAnagram {
	
	public static void main(String[] passedArguments) {
		if (passedArguments.length > 0) {
			File dictionaryFile = new File(passedArguments[0]);
			if (dictionaryFile.exists()) {

				// Load Dictionary
				System.out.println("Loading Dictionary...");
				List<Anagram> dictionary = buildDictionary(dictionaryFile);
				System.out.println("Load Complete!\n");
				
				if (!dictionary.isEmpty()) {
					// Enter main loop
					try(Scanner input = new Scanner(System.in)) {
						do {
							System.out.print("Enter a string of characters: ");
							String inputLine = input.nextLine().trim();
							printAnagrams(inputLine, getAnagrams(inputLine, dictionary));
						} while(!shouldExit(input));
					}
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
	
	private static List<Anagram> buildDictionary(File passedFile) {
		try(FileReader reader = new FileReader(passedFile)) {
			try(Scanner lineReader = new Scanner(reader)) {
				if (lineReader.hasNextLine()) {
					int numLines = Integer.valueOf(lineReader.nextLine());
					List<Anagram> anagramList = new ArrayList<Anagram>(numLines);
					while (lineReader.hasNextLine()) {
						anagramList.add(new Anagram(lineReader.nextLine().trim()));
					}
					return anagramList;
				}
			}
		}
		catch (IOException passedException) {
			System.out.format("Exception occurred attempting to build dictionary from file: %s \n", passedFile);
			passedException.printStackTrace();
		}
		return new ArrayList<Anagram>();
	}
	
	private static List<Anagram> getAnagrams(String passedWord, List<Anagram> passedDictionary) {
		List<Anagram> anagramList = new ArrayList<Anagram>();
		Anagram word = new Anagram(passedWord);
		for (Anagram iteratedAnagram : passedDictionary) {
			if (iteratedAnagram.areAnagrams(word)) {
				if (!iteratedAnagram.getWord().equals(passedWord)) {
					anagramList.add(iteratedAnagram);
				}
			}
		}
		return anagramList;
	}
	
	private static void printAnagrams(String passedWord, List<Anagram> passedAnagramList) {
		if (!passedAnagramList.isEmpty()) {
			StringBuilder builder = new StringBuilder(String.format("Known anagrams for word \"%s\":\n\t", passedWord.toUpperCase()));
			for (int index = 0; index < passedAnagramList.size(); index += 1) {
				builder.append(passedAnagramList.get(index).getWord().toUpperCase());
				if (index < (passedAnagramList.size() - 1)) {
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
