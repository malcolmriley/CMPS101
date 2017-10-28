/*********************************************************************
 * Malcolm Riley
 *
 * CruzID: masriley
 * Assignment: pa3
 *
 * 10-2017
 *********************************************************************/
import java.util.Arrays;

public class ListTest {
	
	private static final int CHARACTER_COLUMN_ALIGN = 30;
	
	/* Test Implementations */
	public static final ITestOperator<List> INITIALIZE_BASIC = (list) -> { 
		list.append(3.0D);
		return list.toString();
	};
	public static final ITestOperator<List> INITIALIZE_POLYMORPHIC = (list) -> {
		list.append(2.0D);
		list.append("This is a String!");
		list.append(1);
		list.append(new Matrix.MatrixEntry<Double>(2.5, 1, 4));
		return list.toString();
	};
	public static final ITestOperator<List> INITIALIZE_ALTERNATE = (list) -> {
		list.append(1);
		list.prepend(2);
		list.append(3);
		list.prepend(4);
		list.append(5);
		list.prepend(6);
		return list.toString();
	};
	
	/* Tests */
	
	private enum EnumListTest {
		INIT("Basic Initialization", "3.0", INITIALIZE_BASIC),
		INIT_POLY("Polymorphic Initialization", "2.0 This is a String! 1 (4, 2.5)", INITIALIZE_POLYMORPHIC),
		INIT_ALT("Alternating Initialization", "6 4 2 1 3 5", INITIALIZE_ALTERNATE)
		;
		
		private final String NAME;
		private final String RESULT;
		private final ITestOperator<List> OPERATOR;
		
		EnumListTest(String passedTestName, String passedResult, ITestOperator<List> passedOperator) {
			this.NAME = passedTestName;
			this.RESULT = passedResult;
			this.OPERATOR = passedOperator;
		}
		
		public void execute() {
			performTest(this.NAME, new List(), this.RESULT, this.OPERATOR);
		}
	}
	
	public static void main(String[] passedArguments) {
		System.out.println("Running List tests...");
		
		for (EnumListTest iteratedTest : EnumListTest.values()) {
			iteratedTest.execute();
		}
	}
	
	/* Test Methods */
	
	public static <T> void performTest(String passedTestName, T passedInstance, String passedExpectedOutput, ITestOperator<T> passedOperator) {
		String result = getResult(passedTestName, passedInstance, passedExpectedOutput, passedOperator);
		System.out.println(result);
	}
	
	public static <T> void performTests(String passedTestName, T passedInstance, String[] passedExpectedOutputs, ITestOperator<T>[] passedOperators) {
		if (passedExpectedOutputs.length != passedOperators.length) {
			System.out.println("ERROR: Cannot perform tests - Expected outputs array and operator array sizes do not match.");
			return;
		}
		else {
			for (int iterator = 0; iterator < passedExpectedOutputs.length; iterator += 1) {
				String testName = String.format("%s (%d of %d):", iterator + 1, passedExpectedOutputs.length);
				String result = getResult(testName, passedInstance, passedExpectedOutputs[iterator], passedOperators[iterator]);
				System.out.println(result);
			}
		}
	}
	
	public static <T> String getResult(String passedTestName, T passedInstance, String passedExpectedOutput, ITestOperator<T> passedOperator) {
		String padding = getPadding(passedTestName);
		String result = padding + "PASSED!";
		String output = passedOperator.test(passedInstance);
		if (!output.equals(passedExpectedOutput)) {
			result = String.format(padding + "FAILED!\n\tExpected: \t%s\n\tActual: \t\t%s", passedExpectedOutput, output);
		}
		return String.format("%s: %s", passedTestName, result);
	}
	
	private static String getPadding(String passedTestName) {
		int paddingQuantity = (CHARACTER_COLUMN_ALIGN - passedTestName.length());
		char paddingArray[] = new char[paddingQuantity];
		Arrays.fill(paddingArray, ' ');
		return new String(paddingArray);
	}
	
	/* ITestOperator Implementation */
	
	public interface ITestOperator<T> {
		
		/**
		 * This method should perform a number of operations on the passed T, and return a particular result (in the form of a String) from the passed T.
		 * 
		 * This is usually simply {@link #toString()}, but may be other methods depending on the type of T.
		 * 
		 * @param passedList - The T to operate on
		 */
		public String test(T passedList);
	}
}
