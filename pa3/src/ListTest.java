/*********************************************************************
 * Malcolm Riley
 *
 * CruzID: masriley
 * Assignment: pa3
 *
 * 10-2017
 *********************************************************************/

public class ListTest {
	
	public static void main(String[] passedArguments) {
		System.out.println("Running List tests...");
		
	}
	
	private static void performTests(String passedTestName, String[] passedExpectedOutputs, IListTest[] passedOperators) {
		if (passedExpectedOutputs.length != passedOperators.length) {
			System.out.println("ERROR: Cannot perform tests - Expected outputs array and operator array sizes do not match.");
			return;
		}
		else {
			for (int iterator = 0; iterator < passedExpectedOutputs.length; iterator += 1) {
				String testName = String.format("%s: (%d of %d)", iterator + 1, passedExpectedOutputs.length);
				performTest(testName, passedExpectedOutputs[iterator], passedOperators[iterator]);
			}
		}
	}
	
	private static void performTest(String passedTestName, List passedList, String passedExpectedOutput, IListTest passedOperator) {
		System.out.println(passedTestName);
		passedOperator.test(passedList);
		String output = passedList.toString();
		if (output.equals(passedExpectedOutput)) {
			System.out.println("\tTest PASSED!");
		}
		else {
			System.out.println("\tTest FAILED!");
			System.out.println(String.format("\tExpected: \t%s", passedExpectedOutput));
			System.out.println(String.format("\tActual: \t%s", output));
		}
	}
	
	private static void performTest(String passedTestName, String passedExpectedOutput, IListTest passedOperator) {
		performTest(passedTestName, new List(), passedExpectedOutput, passedOperator);
	}
	
	/* IListTest Implementation */
	
	public interface IListTest {
		
		/**
		 * This method should perform a number of operations on the passed {@link List}.
		 * 
		 * @param passedList - The {@link List} to operate on
		 */
		public void test(List passedList);
	}
}
