/*********************************************************************
 * Malcolm Riley
 *
 * CruzID: masriley
 * Assignment: pa3
 *
 * 10-2017
 *********************************************************************/
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;

public class Sparse {

	public static void main(String[] passedArguments) {
		if (passedArguments.length < 2) {
			System.out.println("ERROR: Incorrect number of program arguments.");
			System.out.println("\tUsage: \"Sparse [input] [output]\", where [input] is the path to the input file, and [output] is the path to the output file.");
		}
		else {
			boolean headerInterpreted = false;
			int dimensions = 0;
			Integer iteration = 0;
			boolean firstDefined = false;
			boolean secondDefined = false;
			String[] firstMatrix = null;
			String[] secondMatrix = null;
			
			Matrix A = null;
			Matrix B = null;
			
			try (FileReader reader = new FileReader(passedArguments[0])) {
				try (Scanner scanner = new Scanner(reader)) {
					while (scanner.hasNextLine()) {
						String iteratedLine = scanner.nextLine();
						if ((iteratedLine == null) || (iteratedLine.isEmpty())) {
							continue;
						}
						else if (!headerInterpreted) {
							String[] arguments = iteratedLine.split(" ");
							dimensions = Integer.valueOf(arguments[0]);
							firstMatrix = new String[Integer.valueOf(arguments[1])];
							secondMatrix = new String[Integer.valueOf(arguments[2])];
							headerInterpreted = true;
							continue;
						}
						else if (!firstDefined) {
							firstDefined = defineFromLine(firstMatrix, iteratedLine, iteration);
							iteration += 1;
							if (firstDefined) {
								iteration = 0;
							}
						}
						if ((!secondDefined) && (firstDefined)) {
							secondDefined = defineFromLine(secondMatrix, iteratedLine, iteration);
							iteration += 1;
							if (secondDefined) {
								iteration = 0;
							}
						}
					}
				}
				
				A = fromArray(firstMatrix, dimensions);
				B = fromArray(secondMatrix, dimensions);
			}
			catch (Exception passedException) {
				System.out.println("Error during read of input file:");
				passedException.printStackTrace();
				return;
			}
			
			try (FileWriter writer = new FileWriter(passedArguments[1])) {
				String[] results = {
					getResult(String.format("A has %d non-zero entries:", A.getNNZ()), A),
					getResult(String.format("B has %d non-zero entries:", B.getNNZ()), B),
					getResult("(1.5)*A =", A.scalarMult(1.5)),
					getResult("A+B =", A.add(B)),
					getResult("A+A =", A.add(A)),
					getResult("B-A =", B.sub(A)),
					getResult("A-A =", A.sub(A)),
					getResult("Transpose(A) =", A.transpose()),
					getResult("A*B =", A.mult(B)),
					getResult("B*B =", B.mult(B))
				};
				
				for (int ii = 0; ii < results.length; ii += 1) {
					String result = results[ii];
					writer.write(result);
				}
			}
			catch (Exception passedException) {
				System.out.println("Error during write of output file:");
				passedException.printStackTrace();
				return;
			}
		}
	}
	
	private static final String getResult(String passedHeader, Matrix passedMatrix){
		String matrix = passedMatrix.toString();
		return String.format("%s%s\n", passedHeader, matrix);
	}
	
	private static final boolean defineFromLine(String[] passedArray, String passedLine, Integer passedIteration) {
		if (passedIteration < passedArray.length) {
			passedArray[passedIteration.intValue()] = passedLine;
			return false;
		}
		return true;
	}
	
	private static final Matrix fromArray(String[] passedArray, int passedDimensions) {
		Matrix matrix = new Matrix(passedDimensions);
		for (String iteratedString : passedArray) {
			if (iteratedString != null) {
				String[] arguments = iteratedString.split(" ");
				matrix.changeEntry(Integer.valueOf(arguments[0]), Integer.valueOf(arguments[1]), Double.valueOf(arguments[2]));
			}
		}
		return matrix;
	}
}
