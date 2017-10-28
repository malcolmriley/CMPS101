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
	
	/* Test Methods */
	
	private static void performTest(String passedTestName, List passedList, String passedExpectedOutput, IListTest passedOperator) {
		String result = getResult(passedTestName, passedList, passedExpectedOutput, passedOperator);
		System.out.println(result);
	}
	
	private static void performTests(String passedTestName, String[] passedExpectedOutputs, IListTest[] passedOperators) {
		if (passedExpectedOutputs.length != passedOperators.length) {
			System.out.println("ERROR: Cannot perform tests - Expected outputs array and operator array sizes do not match.");
			return;
		}
		else {
			for (int iterator = 0; iterator < passedExpectedOutputs.length; iterator += 1) {
				String testName = String.format("%s (%d of %d):", iterator + 1, passedExpectedOutputs.length);
				String result = getResult(testName, passedExpectedOutputs[iterator], passedOperators[iterator]);
				System.out.println(result);
			}
		}
	}
	
	private static String getResult(String passedTestName, List passedList, String passedExpectedOutput, IListTest passedOperator) {
		String result = "\tPASSED!";
		passedOperator.test(passedList);
		String output = passedList.toString();
		if (!output.equals(passedExpectedOutput)) {
			result = String.format("\tFAILED!\n\tExpected: \t%s\n\tActual: \t%s", passedExpectedOutput, output);
		}
		return String.format("%s: %s", passedTestName, result);
	}
	
	private static String getResult(String passedTestName, String passedExpectedOutput, IListTest passedOperator) {
		return getResult(passedTestName, new List(), passedExpectedOutput, passedOperator);
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
