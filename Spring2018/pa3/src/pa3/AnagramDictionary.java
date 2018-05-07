package pa3;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Implementation for a specialized "dictionary" type that stores {@link Anagram} instances keyed by their backing codes.
 * 
 * @author Malcolm Riley 2018
 */
public class AnagramDictionary {
	
	private HashMap<BigInteger, Set<Anagram>> DICTIONARY;
	
	public AnagramDictionary(int passedEntryQuantity) {
		this.DICTIONARY = new HashMap<BigInteger, Set<Anagram>>(passedEntryQuantity);
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
