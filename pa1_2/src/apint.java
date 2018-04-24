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
	
	// Static Objects
	public static final apint ZERO = new apint(0);
	public static final apint ONE = new apint(1);
	
	// Local Objects
	private long[] VALUE;
	private int SIGNUM;
	
	// Constants
	/**
	 * This is the largest nine digit integer.
	 * <p>
	 * Ten digit integers do not fully fit within 32 bits; 9,999,999,999 requires 34.
	 */
	private static final long CARRY_THRESHOLD = 999999999L;
	private static final int DIGITS_PER_BLOCK = 9;
	
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
		// Check for sign character
		int signum = 0;
		String digits = passedString;
		if (passedString.startsWith("-")) {
			signum = -1;
			digits = passedString.substring(1);
		}
		else if (passedString.startsWith("+")) {
			signum = 1;
			digits = passedString.substring(1);
		}
		
		// Initialize Digits
		boolean nonzero = false;
		this.VALUE = new long[((digits.length() / DIGITS_PER_BLOCK) + 1)];
		for (int index = 0; (index * DIGITS_PER_BLOCK) < digits.length(); index += 1) {
			int beginIndex = (index * DIGITS_PER_BLOCK);
			int endIndex = Math.min(digits.length(), (index + 1) * DIGITS_PER_BLOCK);
			String subString = digits.substring(beginIndex, endIndex);
			long value = Long.parseLong(subString);
			this.VALUE[index] = value;
			nonzero |= (value != 0);
		}
		
		// Set signum if actually nonzero
		this.SIGNUM = (nonzero) ? signum : 0;
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
		this.SIGNUM = signum(passedValue);
		long value = Math.abs(passedValue);
		long carry = getCarry(value);
		this.VALUE = new long[] {carry, (value - carry)};
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
		return String.format("%s%s", getSign(this.SIGNUM), this.toStringUnsigned());
	}
	
	/**
	 * Returns the signum of this {@link apint} instance.
	 * 
	 * @return The signum.
	 */
	public int signum() {
		return this.SIGNUM;
	}
	
	/**
	 * Returns the {@code String} representation of this {@link apint} instance, without a sign character.
	 * 
	 * @return A {@code String } representation of this {@link apint} instance.
	 */
	public String toStringUnsigned() {
		StringBuilder builder = new StringBuilder(this.VALUE.length * DIGITS_PER_BLOCK);
		for(int index = 0; index < (this.VALUE.length - 1); index += 1) {
			if (this.VALUE[index] > 0) {
				builder.append(String.valueOf(this.VALUE[index]));
			}
		}
		builder.append(String.valueOf(this.VALUE[this.VALUE.length - 1]));
		return builder.toString();
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
	 * Adds the value of the passed {@link apint} to {@code this}. This will modify the value of this {@link apint} instance.
	 * <p>
	 * Along with {@link #subtract(apint)}, {@link #multiply(apint)}, and {@link #divide(apint)}, fulfills requirement #6.
	 * 
	 * @param passedValue - The value to add to {@code this}.
	 * @return {@code this}, for method chaining
	 */
	public apint add(apint passedValue) {
		this.addInternal(passedValue, passedValue.SIGNUM);
		return this;
	}
	
	/**
	 * Subtracts the value of the passed {@link apint} from {@code this}. This will modify the value of this {@link apint} instance.
	 * <p>
	 * Along with {@link #add(apint)}, {@link #multiply(apint)}, and {@link #divide(apint)}, fulfills requirement #6.
	 * 
	 * @param passedValue - The value to subtract from {@code this}.
	 * @return {@code this}, for method chaining
	 */
	public apint subtract(apint passedValue) {
		this.addInternal(passedValue, -1 * passedValue.SIGNUM);
		return this;
	}
	
	/**
	 * Multiplies the value of the passed {@link apint} with {@code this}. This will modify the value of this {@link apint} instance.
	 * <p>
	 * Along with {@link #add(apint)}, {@link #subtract(apint)}, and {@link #divide(apint)}, fulfills requirement #6.
	 * 
	 * @param passedValue - The value to subtract from {@code this}.
	 * @return {@code this}, for method chaining
	 */
	public apint multiply(apint passedValue) {
		// Check for easy multiplications
		if (passedValue.isEqualTo(apint.ONE)) {
			return this;
		}
		if (passedValue.isEqualTo(apint.ZERO)) {
			this.SIGNUM = 0;
			this.VALUE = new long[] {0, 0};
			return this;
		}
		
		// Calculate digit result
		// TODO
		
		// Calculate signum result
		this.SIGNUM = (this.SIGNUM * passedValue.SIGNUM);
		
		return this;
	}
	
	/**
	 * Divides {@code this} by value of the passed {@link apint}. This will modify the value of this {@link apint} instance.
	 * <p>
	 * Along with {@link #add(apint)}, {@link #multiply(apint)}, and {@link #subtract(apint)}, fulfills requirement #6.
	 * 
	 * @param passedValue - The value to divide {@code this} by.
	 * @return {@code this}, for method chaining
	 */
	public apint divide(apint passedValue) {
		// Check division validity
		if (passedValue.isEqualTo(apint.ZERO)) {
			throw new IllegalArgumentException("May not divide by zero.");
		}
		else if (passedValue.isEqualTo(apint.ONE)) {
			return this;
		}
		
		// Calculate digit result
		// TODO
		
		// Calculate signum result
		this.SIGNUM = (this.SIGNUM * passedValue.SIGNUM);
		
		return this;
	}
	
	/**
	 * Performs the factorial operation on this {@link apint}.
	 * <p>
	 * Fulfills the extra credit component of the assignment.
	 * @return {@code this}, for method chaining
	 */
	public apint factorial() {
		apint value = this.copy().subtract(apint.ONE);
		while(value.compare(apint.ZERO) > 0) {
			this.multiply(value);
			value.subtract(apint.ONE);
		}
		return this;
	}
	
	/**
	 * Comparison method. Returns {@code true} if {@code this} > {@code passedValue}.
	 * 
	 * @param passedValue - The {@link apint} instance to compare against
	 * @return Whether or not {@code this} > {@code passedValue}.
	 */
	public boolean isGreaterThan(apint passedValue) {
		return (this.compare(passedValue) == 1);
	}
	
	/**
	 * Comparison method. Returns {@code true} if {@code this} < {@code passedValue}.
	 * 
	 * @param passedValue - The {@link apint} instance to compare against
	 * @return Whether or not {@code this} < {@code passedValue}.
	 */
	public boolean isLessThan(apint passedValue) {
		return (this.compare(passedValue) == -1);
	}
	
	/**
	 * Comparison method. Returns {@code true} if {@code this} == {@code passedValue}.
	 * 
	 * @param passedValue - The {@link apint} instance to compare against
	 * @return Whether or not {@code this} == {@code passedValue}.
	 */
	public boolean isEqualTo(apint passedValue) {
		return (this.compare(passedValue) == 0);
	}
	
	/**
	 * Comparison method. Returns {@code true} if {@code this} ≤ {@code passedValue}.
	 * 
	 * @param passedValue - The {@link apint} instance to compare against
	 * @return Whether or not {@code this} ≤ {@code passedValue}.
	 */
	public boolean isLessThanOrEqual(apint passedValue) {
		int comparison = this.compare(passedValue);
		return (comparison == -1) || (comparison == 0);
	}
	
	/**
	 * Comparison method. Returns {@code true} if {@code this} ≤ {@code passedValue}.
	 * 
	 * @param passedValue - The {@link apint} instance to compare against
	 * @return Whether or not {@code this} ≤ {@code passedValue}.
	 */
	public boolean isGreaterThanOrEqual(apint passedValue) {
		int comparison = this.compare(passedValue);
		return (comparison == 1) || (comparison == 0);
	}
	
	/**
	 * This method performs a comparison function on {@code this} and the passed {@link apint} instance. It returns:
	 * <li> 1 if {@code this} > {@code passedValue} </li>
	 * <li> -1 if {@code this} < {@code passedValue} </li>
	 * <li> -0 if {@code this} == {@code passedValue} </li>
	 * 
	 * @param passedValue - The {@link apint} instance to compare against.
	 */
	public int compare(apint passedValue) {
		// TODO
		return 0;
	}
	
	/**
	 * Returns a new {@link apint} instance that is a value-copy of this instance.
	 * 
	 * @return A new {@link apint} instance with the same value as this one.
	 */
	public apint copy() {
		apint newInstance = new apint();
		newInstance.SIGNUM = this.SIGNUM;
		newInstance.VALUE = new long[this.VALUE.length]; // Avoid unnecessary array copies
		System.arraycopy(this.VALUE, 0, newInstance.VALUE, 0, this.VALUE.length);
		return newInstance;
	}
	
	/* Internal Methods */
	
	protected void addInternal(apint passedValue, int passedSign) {
		// TODO:
	}
	
	private long getCarry(long passedValue) {
		return (Math.abs(passedValue) > CARRY_THRESHOLD) ? (Math.abs(passedValue) / (CARRY_THRESHOLD + 1)) : 0;
	}
	
	private void ensureCapacity(int passedCapacity) {
		if (passedCapacity > this.VALUE.length) {
			long newArray[] = new long[passedCapacity];
			if (this.VALUE != null) {
				long oldArray[] = this.VALUE;
				System.arraycopy(oldArray, 0, newArray, 0, oldArray.length);
			}
			this.VALUE = newArray;
		}
	}
	
	/* Logic Methods */
	
	protected static final String getSign(int passedSignum) {
		return (passedSignum < 0) ? "-" : "+";
	}
	
	private static final int signum(long passedValue) {
		if (passedValue == 0) {
			return 0;
		}
		return (passedValue > 0) ? 1 : -1;
	}
 }
