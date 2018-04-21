import java.util.Objects;

/**
 * Arbitrary-precision rational type. Named as per requirements of the assignment.
 * <p>
 * Other specified requirements are transcluded here from the assignment sheet:
 * 1) a default constructor
 * 2) a constructor for using apints to represent the numerator and denominator.
 * 3) a constructor for conversion of a pair of ints.
 * 4) a constructor for conversion of reals to a specified precision.
 * 5) a method for printing.
 * 6) methods for addition, subtraction, multiplication and division.
 * 7) normalize the result of every operation, i.e., reduce the fraction to lowest terms.
 * 
 * @author Malcolm Riley 04-2018
 */
public class aprat {
	
	// Local Objects
	private apint NUMERATOR;
	private apint DENOMINATOR;
	
	/* Constructors */
	
	/**
	 * Default Constructor. Creates a new {@link aprat} instance with a value of 0; internally, 0 / 1.
	 * <p>
	 * This constructor fulfills requirement #1.
	 */
	public aprat() {
		this(0L, 1L);
	}
	
	/**
	 * Constructor for conversion of two {@link apint} instances into a new {@link aprat} instance, with a value of {@code passedNumerator} / {@code passedDenominator}.
	 * <p>
	 * This constructor fulfills requirement #2.
	 * 
	 * @param passedNumerator - The numerator for the new {@link aprat}
	 * @param passedDenominator - The denominator for the new {@link aprat}
	 */
	public aprat(apint passedNumerator, apint passedDenominator) {
		this.NUMERATOR = Objects.requireNonNull(passedNumerator);
		this.DENOMINATOR = Objects.requireNonNull(passedDenominator);
	}
	
	/**
	 * Constructor for conversion of two {@code int} values into a new {@link aprat} instance, with a value of {@code passedNumerator} / {@code passedDenominator}.
	 * <p>
	 * This constructor, together with {@link #aprat(long, long)}, fulfills requirement #3.
	 * 
	 * @param passedNumerator - The numerator for the new {@link aprat}
	 * @param passedDenominator - The denominator for the new {@link aprat}
	 */
	public aprat(int passedNumerator, int passedDenominator) {
		this((long)passedNumerator, (long)passedDenominator);
	}
	
	/**
	 * Constructor for conversion of two {@code long} values into a new {@link aprat} instance, with a value of {@code passedNumerator} / {@code passedDenominator}.
	 * <p>
	 * This constructor, together with {@link #aprat(int, int)}, fulfills requirement #3.
	 * 
	 * @param passedNumerator - The numerator for the new {@link aprat}
	 * @param passedDenominator - The denominator for the new {@link aprat}
	 */
	public aprat(long passedNumerator, long passedDenominator) {
		// TODO
	}
	
	/**
	 * Constructor for converting a {@code double} value into a new {@link aprat} instance, with the specified precision.
	 * <p>
	 * This constructor, together with {@link #aprat(float, int)}, fulfills requirement #4.
	 * 
	 * @param passedValue - The value for conversion
	 * @param passedPrecision - The desired precision
	 */
	public aprat(double passedValue, int passedPrecision) {
		// TODO
	}
	
	/**
	 * Constructor for converting a {@code float} value into a new {@link aprat} instance, with the specified precision.
	 * <p>
	 * This constructor, together with {@link #aprat(double, int)}, fulfills requirement #4.
	 * 
	 * @param passedValue - The value for conversion
	 * @param passedPrecision - The desired precision
	 */
	public aprat(float passedValue, int passedPrecision) {
		this((double)passedValue, passedPrecision);
	}
	
	/* Public Methods */
	
	/**
	 * Returns a {@code String} representation of this {@link aprat}.
	 * <p>
	 * Along with {@link #print()}, fulfills requirement #5.
	 */
	@Override
	public String toString() {
		return String.format("%s %s / %s", this.getSign(), this.NUMERATOR.toStringUnsigned(), this.DENOMINATOR.toStringUnsigned());
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
	 * Adds the value of the passed {@link aprat} to {@code this}, returning the result as a new {@link aprat} instance.
	 * <p>
	 * Along with {@link #subtract(aprat)}, {@link #multiply(aprat)}, and {@link #divide(aprat)}, fulfills requirement #6.
	 * 
	 * @param passedValue - The value to add to {@code this}.
	 * @return {@code this}, for method chaining
	 */
	public aprat add(aprat passedValue) {
		this.addInternal(passedValue, passedValue.signum());
		return this;
	}
	
	/**
	 * Subtracts the value of the passed {@link aprat} from {@code this}, returning the result as a new {@link aprat} instance.
	 * <p>
	 * Along with {@link #add(aprat)}, {@link #multiply(aprat)}, and {@link #divide(aprat)}, fulfills requirement #6.
	 * 
	 * @param passedValue - The value to subtract from {@code this}.
	 * @return {@code this}, for method chaining
	 */
	public aprat subtract(aprat passedValue) {
		this.addInternal(passedValue, -1 * passedValue.signum());
		return this;
	}
	
	/**
	 * Multiplies the value of the passed {@link aprat} with {@code this}, returning the result as a new {@link aprat} instance.
	 * <p>
	 * Along with {@link #add(aprat)}, {@link #subtract(aprat)}, and {@link #divide(aprat)}, fulfills requirement #6.
	 * 
	 * @param passedValue - The value to subtract from {@code this}.
	 * @return {@code this}, for method chaining
	 */
	public aprat multiply(aprat passedValue) {
		this.NUMERATOR.multiply(passedValue.NUMERATOR);
		this.DENOMINATOR.multiply(passedValue.DENOMINATOR);
		return this;
	}
	
	/**
	 * Divides {@code this} by value of the passed {@link aprat}, returning the result as a new {@link aprat} instance.
	 * <p>
	 * Along with {@link #add(aprat)}, {@link #multiply(aprat)}, and {@link #subtract(aprat)}, fulfills requirement #6.
	 * 
	 * @param passedValue - The value to divide {@code this} by.
	 * @return {@code this}, for method chaining
	 */
	public aprat divide(aprat passedValue) {
		this.NUMERATOR.multiply(passedValue.DENOMINATOR);
		this.DENOMINATOR.multiply(passedValue.NUMERATOR);
		return this;
	}
	
	public int signum() {
		return (this.NUMERATOR.signum() * this.DENOMINATOR.signum());
	}
	
	/**
	 * Normalizes the value of this {@link aprat}; that is, it reduces the numerator and denominator to lowest terms.
	 * <p>
	 * Fulfills requirement #7.
	 * 
	 * @return {@code this}, for method chaining
	 */
	public aprat normalize() {
		// TODO
		
		return this;
	}
	
	/* Internal Methods */
	
	private String getSign() {
		return apint.getSign(this.signum());
	}
	
	protected void addInternal(aprat passedValue, int passedSignum) {
		// TODO
	}

}
