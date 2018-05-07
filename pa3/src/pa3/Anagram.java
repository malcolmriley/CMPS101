package pa3;

/**
 * Implementation for {@link Anagram} type as per assignment requirements: 
 * <li> a constructor which uses a String, made up of alphabetic characters either upper or lower case as an input argument. </li>
 * <li> a constructor which uses a char array, made up of alphabetic characters either upper or lower case as an input argument. </li>
 * <li> a method for printing. </li>
 * <li> a method for comparing two Anagram variables that returns true or false. </li>
 * <li> a method that returns the word part of an Anagram variable. </li>
 * <li> do not allow user to get the code part of an Anagram variable. </li>
 * 
 * @author Malcolm Riley 2018
 */
public class Anagram {
	
	public Anagram(String passedString) {
		this(passedString.toCharArray());
	}
	
	public Anagram(char[] passedArray) {
		// TODO
	}
	
	public void print() {
		// TODO
	}
	
	public String getWord() {
		// TODO
		return null;
	}
	
	public boolean areAnagrams(Anagram passedAnagram) {
		// TODO
		return false;
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
