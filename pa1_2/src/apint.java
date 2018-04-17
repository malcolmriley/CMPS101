/**
 * Arbitrary-precision integer type. Named as per requirements of the assignment.
 * <p>
 * Other specified requirements are transcluded here from the assignment sheet:
 * 1) a default constructor
 * 2) a constructor which uses a string, made up of optional {+,-} followed by a string of characters from {0,1,2,3,4,5,6,7,8,9} as an input argument.
 * 3) a constructor for conversion of ints.
 * 4) a constructor for conversion of reals that truncates the fractional part.
 * 5) a method for printing.
 * 6) methods for addition, subtraction, multiplication and division.
 *
 * @author Malcolm Riley 04-2018
 */
public class apint {
	
	/* Constructors */
	
	/**
	 * Default Constructor. Creates a new {@link apint} instance with a value of 0.
	 * <p>
	 * This constructor fulfills requirement #1.
	 */
	public apint() {
		this(0L);
	}
	
	/**
	 * Constructor that converts the passed {@code String} representation into a new {@link apint} instance.
	 * <p>
	 * The new instance is created per the following rules:
	 * <li> The first character may optionally be + or -, representing the sign of the new instance. If the sign character is not
	 * included, the returned instance will be positive. </li>
	 * <li> The remaining characters are interpreted as digits. </li> 
	 * <p>
	 * This constructor fulfills requirement #2.
	 * 
	 * @param passedString - The {@code String} to use for conversion.
	 */
	public apint(String passedString) {
		// TODO
	}
	
	/**
	 * Constructor that converts the passed {@code int} value into a new {@link apint} instance.
	 * <p>
	 * This constructor, together with {@link #apint(long)} fulfills requirement #3.
	 * 
	 * @param passedValue - The {@code int} to use for conversion.
	 */
	public apint(int passedValue) {
		this((long)passedValue);
	}
	
	/**
	 * Constructor that converts the passed {@code long} value into a new {@link apint} instance.
	 * <p>
	 * This constructor, together with {@link #apint(int)} fulfills requirement #3.
	 * 
	 * @param passedValue - The {@code long} to use for conversion.
	 */
	public apint(long passedValue) {
		// TODO
	}
	
	/**
	 * Constructor that converts the passed double value into a new {@link apint} instance.
	 * <p>
	 * This will truncate the fractional part of the passed value.
	 * <p>
	 * This constructor, together with {@link #apint(float)} fulfills requirement #4.
	 * 
	 * @param passedValue - The {@code double} to use for conversion.
	 */
	public apint(double passedValue) {
		this((long)passedValue);
	}
	
	/**
	 * Constructor that converts the passed {@code float} value into a new {@link apint} instance.
	 * <p>
	 * This will truncate the fractional part of the passed value.
	 * <p>
	 * This constructor, together with {@link #apint(double)}, fulfills requirement #4.
	 * 
	 * @param passedValue - The {@code float} to use for conversion.
	 */
	public apint(float passedValue) {
		this((long)passedValue);
	}
	
	/* Public Methods */
	
	/**
	 * Converts this {@link apint} to a {@code String} representation.
	 * <p>
	 * Along with {@link #print()}, fulfills requirement #5.
	 */
	@Override
	public String toString() {
		// TODO
		return "";
	}
	
	/**
	 * Convenience method that prints this instance as a line to {@link System#out}. Actual work is handled by {@link #print()}.
	 * <p>
	 * Along with {@link #toString()}, fulfills requirement #5.
	 */
	public void print() {
		System.out.println(this.toString());
	}
	
	/**
	 * Adds the value of the passed {@link apint} to {@code this}, returning the result as a new {@link apint} instance.
	 * <p>
	 * Along with {@link #subtract(apint)}, {@link #multiply(apint)}, and {@link #divide(apint)}, fulfills requirement #6.
	 * 
	 * @param passedValue - The value to add to {@code this}.
	 */
	public void add(apint passedValue) {
		// TODO
	}
	
	/**
	 * Subtracts the value of the passed {@link apint} from {@code this}, returning the result as a new {@link apint} instance.
	 * <p>
	 * Along with {@link #add(apint)}, {@link #multiply(apint)}, and {@link #divide(apint)}, fulfills requirement #6.
	 * 
	 * @param passedValue - The value to subtract from {@code this}.
	 */
	public void subtract(apint passedValue) {
		// TODO
	}
	
	/**
	 * Multiplies the value of the passed {@link apint} with {@code this}, returning the result as a new {@link apint} instance.
	 * <p>
	 * Along with {@link #add(apint)}, {@link #subtract(apint)}, and {@link #divide(apint)}, fulfills requirement #6.
	 * 
	 * @param passedValue - The value to subtract from {@code this}.
	 */
	public void multiply(apint passedValue) {
		// TODO
	}
	
	/**
	 * Divides {@code this} by value of the passed {@link apint}, returning the result as a new {@link apint} instance.
	 * <p>
	 * Along with {@link #add(apint)}, {@link #multiply(apint)}, and {@link #subtract(apint)}, fulfills requirement #6.
	 * 
	 * @param passedValue - The value to divide {@code this} by.
	 */
	public void divide(apint passedValue) {
		// TODO
	}
	
	/**
	 * Returns the factorial of this {@link apint}.
	 * <p>
	 * Fulfills the extra credit component of the assignment.
	 */
	public void factorial() {
		// TODO
	}
	
	/* Internal Methods */
 }
