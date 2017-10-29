/*********************************************************************
 * Malcolm Riley
 *
 * CruzID: masriley
 * Assignment: pa3
 *
 * 10-2017
 *********************************************************************/

public class MatrixTest {
	
	/* Test Implementations */
	private static final ListTest.ITestOperator<Matrix> INITIALIZE_BASIC = (matrix) -> {
		matrix.changeEntry(1, 1, 2.0);
		return matrix.toString();
	};
	
	/* Tests */
	
	private enum EnumMatrixTest {
		InitBasic("Basic Initialization", 1, "", INITIALIZE_BASIC),
		;
		
		private final String NAME;
		private final String RESULT;
		private final int DIMENSION;
		private final ListTest.ITestOperator<Matrix> OPERATOR;
		
		EnumMatrixTest(String passedTestName, int passedDimension, String passedResult, ListTest.ITestOperator<Matrix> passedOperator) {
			this.NAME = passedTestName;
			this.DIMENSION = passedDimension;
			this.RESULT = passedResult;
			this.OPERATOR = passedOperator;
		}
		
		public void execute() {
			ListTest.performTest(this.NAME, new Matrix(this.DIMENSION), this.RESULT, this.OPERATOR);
		}
	}
	
	public static void main(String[] passedArguments) {
		System.out.println("Running Matrix tests...");
		
		for (EnumMatrixTest iteratedTest : EnumMatrixTest.values()) {
			iteratedTest.execute();
		}
	}
}
