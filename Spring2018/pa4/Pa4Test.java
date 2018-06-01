import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class Pa4Test {
	
	private enum EnumConstructorTests implements Runnable {
		;
		
		private final String COMMENT;
		private final String RESULT;
		private final Supplier<Vector> CONSTRUCTOR;
		
		private EnumConstructorTests(String passedComment, String passedResult, Supplier<Vector> passedConstructor) {
			this.COMMENT = passedComment;
			this.RESULT = passedResult;
			this.CONSTRUCTOR = passedConstructor;
		}
		
		@Override
		public void run() {
			String comment = String.format("Testing \"%s\" constructor...", this.COMMENT);
			String result = String.valueOf(this.CONSTRUCTOR.get());
			printResult(comment, this.RESULT, result);
		}
	}
	
	private enum EnumUnaryTests implements Runnable {
		;
		
		private final String COMMENT;
		private final String RESULT;
		private final Vector VECTOR;
		private final Function<Vector, ?> OPERATION;
		
		private EnumUnaryTests(String passedComment, String passedResult, Vector passedVector, Function<Vector, ?> passedOperation) {
			this.COMMENT = passedComment;
			this.RESULT = passedResult;
			this.VECTOR = passedVector;
			this.OPERATION = passedOperation;
		}
		
		@Override
		public void run() {
			String comment = String.format("Performing \"%s\" on %s...", this.COMMENT, this.VECTOR);
			String result = String.valueOf(this.OPERATION.apply(this.VECTOR));
			printResult(comment, this.RESULT, result);
		}
	}
	
	private enum EnumBinaryTests implements Runnable {
		;
		
		private final String COMMENT;
		private final String RESULT;
		private final Vector FIRST;
		private final Vector SECOND;
		private final BiFunction<Vector, Vector, ?> OPERATION;
		
		private EnumBinaryTests(String passedComment, String passedResult, Vector passedFirstVector, Vector passedSecondVector, BiFunction<Vector, Vector, ?> passedOperation) {
			this.COMMENT = passedComment;
			this.RESULT = passedResult;
			this.FIRST = passedFirstVector;
			this.SECOND = passedSecondVector;
			this.OPERATION = passedOperation;
		}
		
		@Override
		public void run() {
			String comment = String.format("Performing \"%s\" on %s and %s...", this.COMMENT, this.FIRST, this.SECOND);
			String result = String.valueOf(this.OPERATION.apply(this.FIRST, this.SECOND));
			printResult(comment, this.RESULT, result);
		}
	}

	public static void main(String[] passedArguments) {
		doTests("Constructor", EnumConstructorTests.values());
		doTests("Unary", EnumUnaryTests.values());
		doTests("Binary", EnumBinaryTests.values());
	}
	
	public static void doTests(String passedCategory, Runnable[] passedTests) {
		System.out.format("******************** %s TESTS ********************\n", passedCategory.toUpperCase());
		for (Runnable iteratedTest : passedTests) {
			iteratedTest.run();
		}
		System.out.println();
	}
	
	public static void printResult(String passedComment, String passedExpectedResult, String passedActualResult) {
		final String template = "\t%s:\t\t %s\n";
		System.out.println(passedComment);
		System.out.format(template, "EXPECTED", passedExpectedResult);
		System.out.format(template, "ACTUAL", passedActualResult);
		System.out.println();
	}
}
