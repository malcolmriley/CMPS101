package pa3;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 * Implementation for a specialized "dictionary" type that stores {@link Anagram} instances keyed by their backing codes.
 * 
 * @author Malcolm Riley 2018
 */
public class AnagramDictionary {
	
	private HashMap<BigInteger, Set<Anagram>> DICTIONARY;
	
	private AnagramDictionary(int passedEntryQuantity) {
		this.DICTIONARY = new HashMap<BigInteger, Set<Anagram>>(passedEntryQuantity);
	}
	
	public AnagramDictionary() {
		this(16);
	}
	
	/**
	 * Returns a new {@link AnagramDictionary} of words parsed from the passed {@link File}.
	 * <p>
	 * If the passed {@link File} fails to read, an empty {@link AnagramDictionary} will be returned instead.
	 * 
	 * @param passedFile - The file to use for the dictionary
	 * @return A suitably-instantiated and ready-to-use {@link AnagramDictionary} instance
	 */
	public static AnagramDictionary fromFile(File passedFile) {
		try(FileReader reader = new FileReader(passedFile)) {
			try(Scanner lineReader = new Scanner(reader)) {
				if (lineReader.hasNextLine()) {
					// First line in file should be number of words to follow
					int numWords = Integer.valueOf(lineReader.nextLine());
					AnagramDictionary dictionary = new AnagramDictionary(numWords);
					while (lineReader.hasNextLine()) {
						dictionary.add(lineReader.nextLine());
					}
					return dictionary;
				}
			}
		} catch (IOException passedException) {
			System.out.format("Exception occurred attempting to read file: %s \n", passedFile);
			passedException.printStackTrace();
		}
		return new AnagramDictionary();
	}
	
	/**
	 * Adds the passed {@link String} to the {@link AnagramDictionary}. It will be recognized as a word for the purposes of anagrammitization.
	 * <p>
	 * The method returns {@code true} if the {@link String} was successfully added to the dictionary, and {@code false} otherwise.
	 * <p>
	 * Typically, the only reason adding fails is if the {@link passedString} already existed in the dictionary.
	 * 
	 * @param passedString - The {@link String} to add to the {@link AnagramDictionary}
	 * @return Whether or not it was successfully added.
	 */
	public boolean add(String passedString) {
		return this.add(new Anagram(passedString));
	}
	
	/**
	 * Returns a {@link List} of {@link String}s that are anagrams of {@code passedString}.
	 * 
	 * @param passedString - The {@link String} to search for anagrams for
	 * @return A {@link List} of known anagrams.
	 */
	public List<String> getAnagrams(String passedString) {
		return this.getAnagrams(new Anagram(passedString));
	}
	
	/* Internal Methods */
	
	private boolean add(Anagram passedAnagram) {
		BigInteger code = passedAnagram.getCode();
		if (!this.DICTIONARY.containsKey(code)) {
			this.DICTIONARY.put(code, new HashSet<Anagram>(32));
		}
		return this.DICTIONARY.get(code).add(passedAnagram);
	}
	
	private List<String> getAnagrams(Anagram passedAnagram) {
		BigInteger code = passedAnagram.getCode();
		ArrayList<String> list = new ArrayList<String>();
		if (this.DICTIONARY.containsKey(code)) {
			for (Anagram iteratedAnagram : this.DICTIONARY.get(code)) {
				// Don't return the word itself if it is already in the list
				if (!passedAnagram.getWord().equals(iteratedAnagram.getWord())) {
					list.add(iteratedAnagram.getWord());
				}
			}
		}
		return list;
	}

}
