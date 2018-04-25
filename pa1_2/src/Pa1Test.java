import java.util.function.Supplier;

/**
 * Test class for pa1: Arbitrary Precision Arithmetic.
 *
 * @author Malcolm Riley 04-2018
 */
public class Pa1Test {
	
	// Test Objects
	public static final Supplier<apint> constructor_default = () -> {
		return new apint();
	};
	public static final Supplier<apint> constructor_float = () -> {
		return new apint(42.3F);
	};
	public static final Supplier<apint> constructor_int = () -> {
		return new apint(12);
	};
	public static final Supplier<apint> constructor_string = () -> {
		return new apint("-15");
	};
	public static final Supplier<apint> constructor_big = () -> {
		return new apint("554902859048259048250984902890490318439203908439018490381490");
	};
	public static final Supplier<apint> constructor_big_negative = () -> {
		return new apint("-554902859048259048250984902890490318439203908439018490381490");
	};
	
	public static final Supplier<apint> add_zero = () -> {
		apint first = new apint();
		apint second = new apint();
		return first.add(second);
	};
	public static final Supplier<apint> add_small = () -> {
		apint first = new apint(3);
		apint second = new apint(2);
		return first.add(second);
	};
	public static final Supplier<apint> add_big = () -> {
		apint first = new apint("5849285478957894275894729857489275897489275894729");
		apint second = new apint("4785784927598472895748927598748927589749827598482");
		return first.add(second);
	};
	public static final Supplier<apint> add_negative_to_positive = () -> {
		apint first = new apint("4327483978973857498174893718943");
		apint second = new apint("-548275425439542758947258947298574892");
		return first.add(second);
	};
	public static final Supplier<apint> add_positive_to_negative = () -> {
		
		return null;
	};
	public static final Supplier<apint> add_negative_to_negative = () -> {
		
		return null;
	};
	
	public enum AssignmentTest {
		// Constructor Tests
		CONSTRUCTOR_DEFAULT(constructor_default, "+0"),
		CONSTRUCTOR_FLOAT(constructor_float, "+42"),
		CONSTRUCTOR_INT(constructor_int, "+12"),
		CONSTRUCTOR_STRING(constructor_string, "-15"),
		CONSTRUCTOR_BIG(constructor_big, "+554902859048259048250984902890490318439203908439018490381490"),
		CONSTRUCTOR_BIG_NEGATIVE(constructor_big_negative, "-554902859048259048250984902890490318439203908439018490381490"),
		
		// Addition Tests
		ADD_ZERO(add_zero, "+0"),
		ADD_SMALL(add_small, "+5"),
		ADD_VERY_BIG(add_big, "+10635070406556367171643657456238203487239103493211"),
		ADD_NEGATIVE_TO_POSITIVE(add_negative_to_positive, "-548271097955563785089760772404855949"),
		ADD_POSITIVE_TO_NEGATIVE(add_positive_to_negative, ""),
		ADD_NEGATIVE_TO_NEGATIVE(add_negative_to_negative, ""),
		
		// Subtraction Tests
		
		// Multiplication Tests
		
		// Division Tests
		;
		
		private final Supplier<apint> TEST;
		private final String RESULT_EXPECTED;
		
		private AssignmentTest(Supplier<apint> passedTest, String passedExpectedResult) {
			this.TEST = passedTest;
			this.RESULT_EXPECTED = passedExpectedResult;
		}
		
		public void execute() {
			String result;
			try {
				result = String.valueOf(this.TEST.get());
			}
			catch (Exception passedException) {
				passedException.printStackTrace();
				result = "Exception Thrown";
			}
			String header = String.format("Executing test \"%s\"...", this.name());
			System.out.format("%-40s", header);
			if (result.equals(RESULT_EXPECTED)) {
				System.out.println("\tPASSED");
			}
			else {
				System.out.println("\t\tFAILED!");
				System.out.format("\t Expected Result: \t%s\n\t Received Result: \t%s\n", this.RESULT_EXPECTED, result);
			}
		}
	}

	public static void main(String[] passedArguments) {
		for(AssignmentTest iteratedTest : AssignmentTest.values()) {
			iteratedTest.execute();
		}
	}

}
