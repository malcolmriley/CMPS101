package pa3;

/**
 * Implementation for {@link Anagram} type as per assignment requirements: 
 * <li> 1) a constructor which uses a String, made up of alphabetic characters either upper or lower case as an input argument. </li>
 * <li> 2) a constructor which uses a char array, made up of alphabetic characters either upper or lower case as an input argument. </li>
 * <li> 3) a method for printing. </li>
 * <li> 4) a method for comparing two Anagram variables that returns true or false. </li>
 * <li> 5) a method that returns the word part of an Anagram variable. </li>
 * <li> 6) do not allow user to get the code part of an Anagram variable. </li>
 * 
 * @author Malcolm Riley 2018
 */
public class Anagram {
	
	/**
	 * Constructor for a new {@link Anagram} instance.
	 * <p>
	 * Fulfills requirement #1.
	 * 
	 * @param passedString - The {@code String} to use for the {@link Anagram}.
	 */
	public Anagram(String passedString) {
		this(passedString.toCharArray());
	}
	
	/**
	 * Constructor for a new {@link Anagram} instance.
	 * <p>
	 * Fulfills requirement #2.
	 * 
	 * @param passedArray - The {@code char} array to use for the {@link Anagram}.
	 */
	public Anagram(char[] passedArray) {
		// TODO
	}
	
	/**
	 * Print method.
	 * <p>
	 * Fulfills requirement #3.
	 */
	public void print() {
		System.out.println(this.getWord());
	}
	
	/**
	 * Method for directly comparing two {@link Anagram} instances.
	 * <p>
	 * Fulfills requirement #4.
	 * 
	 * @param passedAnagram - The {@link Anagram} to compare against
	 * @return Whether or not the two {@link Anagram} instances' backing words are anagrams.
	 */
	public boolean areAnagrams(Anagram passedAnagram) {
		// TODO
		return false;
	}
	
	/**
	 * Method returning the "word" part of an {@link Anagram}.
	 * <p>
	 * Fulfills requirement #5.
	 * 
	 * @return The {@code String} word backing this {@link Anagram}.
	 */
	public String getWord() {
		// TODO
		return null;
	}
	
	/* Internal Methods */
	
	/**
	 * Returns whether or not the two passed {@code String} instances are the same length.
	 * 
	 * @param passedFirst - The first String
	 * @param passedSecond - The second String
	 * @return Whether or not {@link String#length()} is the same for both.
	 */
	private static final boolean sameLength(String passedFirst, String passedSecond) {
		return (passedFirst.length() == passedSecond.length());
	}
	
	/**
	 * Converts {@code passedCharacter} to its integer "index" value; that is, a = 0, b = 1, c = 2, etc.
	 * <p>
	 * This method ignores case: A = a = 0, B = b = 1, C = c = 2, etc.
	 *
	 * @param passedCharacter - The character to convert
	 * @return The index of that character, minus 1.
	 */
	private static final int toIndex(char passedCharacter) {
		return (Character.toLowerCase(passedCharacter) - 'a');
	}

}
