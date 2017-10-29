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
	private static final ListTest.ITestOperator<Matrix> INITIALIZE_ORDERED = (matrix) -> {
		matrix.changeEntry(1, 2, 2.0);
		matrix.changeEntry(1, 3, 3.1);
		matrix.changeEntry(2, 1, 5.0);
		return matrix;
	};
	private static final ListTest.ITestOperator<Matrix> INITIALIZE_OTHER = (matrix) -> {
		matrix.changeEntry(3, 3, 0.5);
		matrix.changeEntry(1, 2, 1.5);
		matrix.changeEntry(1, 1, -0.5);
		return matrix;
	};
	private static final ListTest.ITestOperator<Matrix> INITIALIZE_UNORDERED = (matrix) -> {
		matrix.changeEntry(1, 2, 2.0);
		matrix.changeEntry(2, 1, 5.0);
		matrix.changeEntry(1, 3, 3.1);
		return matrix;
	};
	private static final ListTest.ITestOperator<Matrix> INITIALIZE_COPY = (matrix) -> {
		INITIALIZE_UNORDERED.test(matrix);
		Matrix newMatrix = matrix.copy();
		return newMatrix.toString();
	};

	private static final ListTest.ITestOperator<Matrix> EQUALS_SELF = (matrix) -> {
		INITIALIZE_ORDERED.test(matrix);
		return matrix.equals(matrix);
	};
	private static final ListTest.ITestOperator<Matrix> EQUALS_COPY = (matrix) -> {
		INITIALIZE_ORDERED.test(matrix);
		return matrix.equals(matrix.copy());
	};
	private static final ListTest.ITestOperator<Matrix> EQUALS_IDENTICAL = (matrix) -> {
		Matrix other = new Matrix(3);
		INITIALIZE_ORDERED.test(matrix);
		INITIALIZE_ORDERED.test(other);
		return matrix.equals(other);
	};
	private static final ListTest.ITestOperator<Matrix> EQUALS_EMPTY = (matrix) -> {
		INITIALIZE_ORDERED.test(matrix);
		return matrix.equals(new Matrix(3));
	};
	private static final ListTest.ITestOperator<Matrix> EQUALS_BOTH_EMPTY = (matrix) -> {
		return matrix.equals(new Matrix(3));
	};
	private static final ListTest.ITestOperator<Matrix> EQUALS_BOTH_EMPTY_MISSIZED = (matrix) -> {
		return matrix.equals(new Matrix(4));
	};
	private static final ListTest.ITestOperator<Matrix> EQUALS_NULL = (matrix) -> {
		INITIALIZE_ORDERED.test(matrix);
		return matrix.equals(null);
	};
	private static final ListTest.ITestOperator<Matrix> EQUALS_OTHER = (matrix) -> {
		Matrix other = new Matrix(3);
		INITIALIZE_ORDERED.test(matrix);
		INITIALIZE_OTHER.test(other);
		return matrix.equals(other);
	};
	private static final ListTest.ITestOperator<Matrix> EQUALS_MISSIZED = (matrix) -> {
		Matrix other = new Matrix(4);
		INITIALIZE_ORDERED.test(matrix);
		INITIALIZE_ORDERED.test(other);
		return matrix.equals(other);
	};

	
	private static final ListTest.ITestOperator<Matrix> GET_NONZERO_EMPTY = (matrix) -> {
		return matrix.getNNZ();
	};
	private static final ListTest.ITestOperator<Matrix> GET_NONZERO_NONEMPTY = (matrix) -> {
		INITIALIZE_UNORDERED.test(matrix);
		return matrix.getNNZ();
	};
	private static final ListTest.ITestOperator<Matrix> GET_NONZERO_CLEARED = (matrix) -> {
		INITIALIZE_UNORDERED.test(matrix);
		matrix.makeZero();
		return matrix.getNNZ();
	};

	
	private static final ListTest.ITestOperator<Matrix> SCALAR_MULT_EMPTY = (matrix) -> {
		Matrix result = matrix.scalarMult(2.0);
		return result;
	};
	private static final ListTest.ITestOperator<Matrix> SCALAR_MULT_NONEMPTY = (matrix) -> {
		INITIALIZE_ORDERED.test(matrix);
		Matrix result = matrix.scalarMult(2.0);
		return result;
	};
	private static final ListTest.ITestOperator<Matrix> SCALAR_MULT_NEGATIVE = (matrix) -> {
		INITIALIZE_ORDERED.test(matrix);
		Matrix result = matrix.scalarMult(-2.0);
		return result;
	};

	
	private static final ListTest.ITestOperator<Matrix> TRANSPOSE_EMPTY = (matrix) -> {
		Matrix result = matrix.transpose();
		return result;
	};
	private static final ListTest.ITestOperator<Matrix> TRANSPOSE_NONEMPTY = (matrix) -> {
		INITIALIZE_ORDERED.test(matrix);
		Matrix result = matrix.transpose();
		return result;
	};
	
	
	private static final ListTest.ITestOperator<Matrix> ADD_SELF = (matrix) -> {
		INITIALIZE_UNORDERED.test(matrix);
		Matrix result = matrix.add(matrix);
		return result;
	};
	private static final ListTest.ITestOperator<Matrix> ADD_COPY = (matrix) -> {
		INITIALIZE_UNORDERED.test(matrix);
		Matrix result = matrix.add(matrix.copy());
		return result;
	};
	private static final ListTest.ITestOperator<Matrix> ADD_EMPTY = (matrix) -> {
		INITIALIZE_UNORDERED.test(matrix);
		Matrix result = matrix.add(new Matrix(3));
		return result;
	};
	private static final ListTest.ITestOperator<Matrix> ADD_OTHER = (matrix) -> {
		INITIALIZE_ORDERED.test(matrix);
		Matrix otherMatrix = new Matrix(3);
		INITIALIZE_OTHER.test(otherMatrix);
		Matrix result = matrix.add(otherMatrix);
		return result;
	};
	
	
	private static final ListTest.ITestOperator<Matrix> SUBTRACT_SELF = (matrix) -> {
		INITIALIZE_ORDERED.test(matrix);
		Matrix result = matrix.sub(matrix);
		return result;
	};
	private static final ListTest.ITestOperator<Matrix> SUBTRACT_COPY = (matrix) -> {
		INITIALIZE_ORDERED.test(matrix);
		Matrix result = matrix.sub(matrix.copy());
		return result;
	};
	private static final ListTest.ITestOperator<Matrix> SUBTRACT_EMPTY = (matrix) -> {
		INITIALIZE_ORDERED.test(matrix);
		Matrix result = matrix.sub(new Matrix(3));
		return result;
	};
	private static final ListTest.ITestOperator<Matrix> SUBTRACT_OTHER = (matrix) -> {
		INITIALIZE_ORDERED.test(matrix);
		Matrix otherMatrix = new Matrix(3);
		INITIALIZE_OTHER.test(otherMatrix);
		Matrix result = matrix.sub(otherMatrix);
		return result;
	};
	
	private static final ListTest.ITestOperator<Matrix> MULTIPLY_ZERO = (matrix) -> {
		INITIALIZE_ORDERED.test(matrix);
		Matrix result = matrix.mult(new Matrix(3));
		return result;
	};
	private static final ListTest.ITestOperator<Matrix> MULTIPLY_SELF = (matrix) -> {
		INITIALIZE_ORDERED.test(matrix);
		Matrix result = matrix.mult(matrix);
		return result;
	};
	private static final ListTest.ITestOperator<Matrix> MULTIPLY_COPY = (matrix) -> {
		INITIALIZE_ORDERED.test(matrix);
		Matrix result = matrix.mult(matrix.copy());
		return result;
	};
	private static final ListTest.ITestOperator<Matrix> MULTIPLY_IDENTITY = (matrix) -> {
		INITIALIZE_ORDERED.test(matrix);
		Matrix identity = new Matrix(3);
		identity.changeEntry(1, 1, 1);
		identity.changeEntry(2, 2, 1);
		identity.changeEntry(3, 3, 1);
		Matrix result = matrix.mult(identity);
		return result.equals(matrix);
	};
	private static final ListTest.ITestOperator<Matrix> MULTIPLY_OTHER_1 = (matrix) -> {
		Matrix other = new Matrix(3);
		INITIALIZE_ORDERED.test(matrix);
		INITIALIZE_OTHER.test(other);
		Matrix result = matrix.mult(other);
		return result;
	};
	private static final ListTest.ITestOperator<Matrix> MULTIPLY_OTHER_2 = (matrix) -> {
		Matrix other = new Matrix(3);
		INITIALIZE_ORDERED.test(matrix);
		INITIALIZE_OTHER.test(other);
		Matrix result = other.mult(matrix);
		return result;
	};
	
	
	private static final ListTest.ITestOperator<Matrix> TEST_FAIL = (matrix) -> {
		System.out.println();
		matrix.changeEntry(1, 2, 3.0);
		matrix.changeEntry(1, 1, 4.0);
		return matrix;
	};
	
	/* Tests */
	
	private enum EnumMatrixTest {
		// Initialization Tests
		InitBasic("Basic Initialization", 1, "1: (1, 2.0)", INITIALIZE_BASIC),
		InitOrdered("Ordered Initialization", 3, "1: (2, 2.0) (3, 3.1)\n2: (1, 5.0)", INITIALIZE_ORDERED),
		InitUnordered("Unordered Initialization", 3, "1: (2, 2.0) (3, 3.1)\n2: (1, 5.0)", INITIALIZE_UNORDERED),
		InitCopy("Copy Initialization", 3, "1: (2, 2.0) (3, 3.1)\n2: (1, 5.0)", INITIALIZE_COPY),
		
		// Equality Tests
		EqualsSelf("Equals Self", 3, "true", EQUALS_SELF),
		EqualsCopy("Equals Copy", 3, "true", EQUALS_COPY),
		EqualsIdentical("Equals Identical", 3, "true", EQUALS_IDENTICAL),
		EqualsEmpty("Equals Empty", 3, "false", EQUALS_EMPTY),
		EqualsBothEmpty("Equals Both Empty", 3, "true", EQUALS_BOTH_EMPTY),
		EqualsBothEmptyMissized("Equals Both Empty Missized", 3, "false", EQUALS_BOTH_EMPTY_MISSIZED),
		EqualsNull("Equals Null", 3, "false", EQUALS_NULL),
		EqualsOther("Equals Other", 3, "false", EQUALS_OTHER),
		EqualsMissized("Equals Missized", 3, "false", EQUALS_MISSIZED),
		
		// GetNonzero Tests
		GetNonzeroEmpty("Nonzero Entries of Empty", 3, "0", GET_NONZERO_EMPTY),
		GetNonzeroNonEmpty("Nonzero Entries of Nonempty", 3, "3", GET_NONZERO_NONEMPTY),
		GetNonzeroCleared("Nonzero Entries of Cleared", 3, "0", GET_NONZERO_CLEARED),
		
		// Scalar Mult Tests
		ScalarMultEmpty("Scalar Mult of Empty", 3, "", SCALAR_MULT_EMPTY),
		ScalarMultNonempty("Scalar Mult of Nonempty", 3, "1: (2, 4.0) (3, 6.2)\n2: (1, 10.0)", SCALAR_MULT_NONEMPTY),
		ScalarMultNegative("Scalar Mult of Negative", 3, "1: (2, -4.0) (3, -6.2)\n2: (1, -10.0)", SCALAR_MULT_NEGATIVE),
		
		// Transpose Tests
		TransposeEmpty("Transpose Empty", 3, "", TRANSPOSE_EMPTY),
		TransposeNonempty("Transpose Nonempty", 3, "1: (2, 5.0)\n2: (1, 2.0)\n3: (1, 3.1)", TRANSPOSE_NONEMPTY),
		
		// Addition Tests
		AddSelf("Adding self", 3, "1: (2, 4.0) (3, 6.2)\n2: (1, 10.0)", ADD_SELF),
		AddCopy("Adding copy", 3, "1: (2, 4.0) (3, 6.2)\n2: (1, 10.0)", ADD_COPY),
		AddEmpty("Adding Empty", 3, "1: (2, 2.0) (3, 3.1)\n2: (1, 5.0)", ADD_EMPTY),
		AddOther("Adding Other", 3, "1: (1, -0.5) (2, 3.5) (3, 3.1)\n2: (1, 5.0)\n3: (3, 0.5)", ADD_OTHER),
		
		// Subtraction Tests
		SubSelf("Subtracting Self", 3, "", SUBTRACT_SELF),
		SubCopy("Subtracting Self", 3, "", SUBTRACT_COPY),
		SubEmpty("Subtracting Self", 3, "1: (2, 2.0) (3, 3.1)\n2: (1, 5.0)", SUBTRACT_EMPTY),
		SubOther("Subtracting Other", 3, "1: (1, 0.5) (2, 0.5) (3, 3.1)\n2: (1, 5.0)\n3: (3, -0.5)", SUBTRACT_OTHER),
		
		// Multiplication Tests
		MultZero("Multiplying by Zero", 3, "", MULTIPLY_ZERO),
		MultIdentity("Multiplying by Identity", 3, "true", MULTIPLY_IDENTITY),
		MultSelf("Multipyling by Self", 3, "1: (1, 10.0)\n2: (2, 10.0) (3, 15.5)", MULTIPLY_SELF),
		MultCopy("Multiplying by Copy", 3, "1: (1, 10.0)\n2: (2, 10.0) (3, 15.5)", MULTIPLY_COPY),
		MultOther1("Multiplying by Other 1", 3, "1: (3, 1.55)\n2: (1, -2.5) (2, 7.5)", MULTIPLY_OTHER_1),
		MultOther2("Multiplying by Other 2", 3, "1: (1, 7.5) (2, -1.0) (3, -1.55)", MULTIPLY_OTHER_2),
		
		// Test Tester
		TestFail("This Test Deliberately Fails", 3, "What tests the tester? Itself, of course!", TEST_FAIL),
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
		
		System.out.println("\nMatrix tests complete.");
	}
}
