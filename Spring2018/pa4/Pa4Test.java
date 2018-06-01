import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class Pa4Test {
	
	private enum EnumConstructorTests implements Runnable {
		DEFAULT("default", "(0.0, 0.0)", Vector::new),
		XY("xy", "(3.0, -1.5)", () -> { return new Vector(3.0F, -1.5F); }),
		ANGULAR("angular", "(4.844562, 1.2370198)", () -> { return Vector.polarVector(0.25F, 5.0F); }),
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
		COPY("copy()", "(0.125, 1.0)", new Vector(0.125F, 1.0F), Vector::copy),
		GET_X("getX()", "0.5", new Vector(0.5F, 325F), Vector::getX),
		GET_Y("getY()", "325.0", new Vector(0.5F, 325F), Vector::getY),
		ANGLE("getAngle()", "0.785398", new Vector(1F, 1F), Vector::getAngle),
		ANGLE_2("getAngle() from Angle Constructor", "0.45", Vector.polarVector(0.45F, 2), Vector::getAngle),
		MAGNITUDE("getMagnitude()", "2.915475947", new Vector(2.5F, 1.5F), Vector::getMagnitude),
		SLOPE("slope()", "0.4", new Vector(5.0F, 2.0F), Vector::slope),
		NORMALIZE("normalize()", "(0.990187, 0.139747)", new Vector(1.325F, 0.187F), Vector::normalize),
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
		ADD("add()", "(0.5, 0.6)", new Vector(0.3F, 0.3F), new Vector(0.2F, 0.3F), Vector::add),
		SUBTRACT("subtract()", "(-0.2, 1.4)", new Vector(0.0F, 1.8F), new Vector(0.2F, 0.4F), Vector::subtract),
		DOT("dotProduct()", "-28.0", new Vector(0.0F, 4.0F), new Vector(3.2F, -7.0F), Vector::dotProduct),
		CROSS("crossProduct()", "(0.0, 12.8)", new Vector(0.0F, 4.0F), new Vector(3.2F, -7.0F), Vector::crossProduct),
		ANGLE("angleBetween()", "0.785398", new Vector(3.0F, 3.0F), new Vector(1.0F, 0.0F), Vector::angleBetween),
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
		final String template = "\t%s\t %s\n";
		System.out.println(passedComment);
		System.out.format(template, "EXPECTED: ~", passedExpectedResult);
		System.out.format(template, "ACTUAL:    ", passedActualResult);
		System.out.println();
	}
}
