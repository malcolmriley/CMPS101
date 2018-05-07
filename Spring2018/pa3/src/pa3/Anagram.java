package pa3;

import java.math.BigInteger;

/**
 * Implementation for {@link Anagram} type as per assignment requirements: 
 * <li> 1) a constructor which uses a String, made up of alphabetic characters either upper or lower case as an input argument. </li>
 * <li> 2) a constructor which uses a char array, made up of alphabetic characters either upper or lower case as an input argument. </li>
 * <li> 3) a method for printing. </li>
 * <li> 4) a method for comparing two Anagram variables that returns true or false. </li>
 * <li> 5) a method that returns the word part of an Anagram variable. </li>
 * <li> 6) do not allow user to get the code part of an Anagram variable. </li>
 * 
 * Chosen approach:
 * Express a single word as a multiple of primes. Assign each letter in the alphabet a prime number, the hash code for each word is the product of these primes.
 * Two words that have the same "product" will be anagrams.
 * 
 * Optimizations:
 * The internal list of primes is not in ascending order, but in order according to statistical letter occurrence in the English language. E corresponds to 2, for instance.
 * This should lead to an average reduction in the size of the "code" for each letter.
 * 
 * @author Malcolm Riley 2018
 */
public class Anagram {
	
	// Constants
	private static final int[] PRIMES = { 5, 71, 37, 29, 2, 53, 59, 19, 11, 83, 79, 31, 43, 13, 7, 67, 97, 23, 17, 3, 41, 73, 47, 89, 61, 101 };
	
	// Local Fields
	private final String WORD;
	private final BigInteger CODE;
	
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
		this.WORD = String.valueOf(passedArray);
		this.CODE = getCode(passedArray);
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
		return this.CODE.equals(passedAnagram.CODE);
	}
	
	/**
	 * Method returning the "word" part of an {@link Anagram}.
	 * <p>
	 * Fulfills requirement #5.
	 * 
	 * @return The {@code String} word backing this {@link Anagram}.
	 */
	public String getWord() {
		return this.WORD;
	}
	
	/* Internal Methods */
	
	/**
	 * Returns a composite number that is the product of the prime keys corresponding to each character in the array.
	 * 
	 * @param passedArray - The character array to convert
	 * @return A composite number, the product of the character key primes.
	 */
	private static final BigInteger getCode(char[] passedArray) {
		BigInteger result = BigInteger.valueOf(1);
		for (char iteratedCharacter : passedArray) {
			BigInteger multiplicand = BigInteger.valueOf(toPrime(iteratedCharacter));
			result = result.multiply(multiplicand);
		}
		return result;
	}
	
	/**
	 * Method returns the prime key corresponding to the passed character.
	 * 
	 * @param passedCharacter - The character to convert
	 * @return The prime number corresponding to the passed character.
	 */
	private static final int toPrime(char passedCharacter) {
		return PRIMES[toIndex(passedCharacter)];
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
