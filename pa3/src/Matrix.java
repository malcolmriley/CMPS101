
/*********************************************************************
 * Malcolm Riley
 *
 * CruzID: masriley
 * Assignment: pa3
 *
 * 10-2017
 *********************************************************************/
import java.util.Objects;

public class Matrix implements Ipa3 {

	protected final int DIMENSION;
	protected final List[] VALUES;

	public Matrix(int passedDimension) {
		this.DIMENSION = passedDimension;
		this.VALUES = new List[passedDimension];
	}

	/* PA3 Required Methods */

	@Override
	public void changeEntry(int passedRow, int passedColumn, double passedNewValue) {
		MatrixEntry<Double> entry = this.getEntry(passedRow, passedColumn);
		if (entry != null) {
			if (passedNewValue == 0) {
				this.VALUES[passedRow].delete();
			}
			else {
				entry.setValue(Double.valueOf(passedNewValue));
			}
		}
		else {
			if (passedNewValue != 0) {
				this.VALUES[passedRow].insertBefore(new MatrixEntry<Double>(Double.valueOf(passedNewValue), passedRow, passedColumn));
			}
		}
	}

	@Override
	public int getSize() {
		return this.DIMENSION;
	}

	@Override
	public int getNNZ() {
		int nonzeroes = 0;
		for (List iteratedList : this.VALUES) {
			nonzeroes += iteratedList.length();
		}
		return nonzeroes;
	}

	@Override
	public void makeZero() {
		for (List iteratedList : this.VALUES) {
			iteratedList.clear();
		}
	}

	@Override
	public Matrix copy() {
		final IEntryModifier<Double> operator = (matrix, entry) -> { matrix.changeEntry(entry.getRow(), entry.getColumn(), entry.getValue());};
		return modifyUsing(this, operator);
	}

	@Override
	public Matrix scalarMult(double passedValue) {
		final IEntryModifier<Double> operator = (matrix, entry) -> { matrix.changeEntry(entry.getRow(), entry.getColumn(), entry.getValue() * passedValue);};
		return modifyUsing(this, operator);
	}

	@Override
	public Matrix transpose() {
		final IEntryModifier<Double> operator = (matrix, entry) -> { matrix.changeEntry(entry.getColumn(), entry.getRow(), entry.getValue());};
		return modifyUsing(this, operator);
	}

	@Override
	public Matrix add(Matrix passedMatrix) {
		final IDoubleOperator<Double> operation = (first, second) -> { return (first + second); };
		return modifyRowsUsing(this, passedMatrix, operation);
	}

	@Override
	public Matrix sub(Matrix passedMatrix) {
		final IDoubleOperator<Double> operation = (first, second) -> { return (first - second); };
		return modifyRowsUsing(this, passedMatrix, operation);
	}

	@Override
	public Matrix mult(Matrix passedMatrix) {
		// TODO:
		return null;
	}

	/* Internal Methods */

	private MatrixEntry<Double> getEntry(int passedRowIndex, int passedColumnIndex) {
		if (this.validateIndices(passedRowIndex, passedColumnIndex)) {
			List row = this.VALUES[passedRowIndex];
			if (!row.isEmpty()) {
				row.moveFront();
				while (row.index() >= 0) {
					MatrixEntry<Double> iteratedObject = getAsMatrixEntry(row.get());
					if (iteratedObject != null) {
						if (iteratedObject.getColumn() >= passedColumnIndex) {
							return iteratedObject;
						}
					}
					row.moveNext();
				}
			}
		}
		return null;
	}

	private boolean validateSize(Matrix passedMatrix) {
		return (this.getSize() == passedMatrix.getSize());
	}

	private boolean validateIndices(int passedRowIndex, int passedColumnIndex) {
		return this.validateIndex(passedRowIndex) && this.validateIndex(passedColumnIndex);
	}

	private boolean validateIndex(int passedIndex) {
		return (passedIndex >= 0) && (passedIndex < this.DIMENSION);
	}
	
	private static Matrix modifyUsing(Matrix passedMatrix, IEntryModifier<Double> passedOperator) {
		Matrix newMatrix = new Matrix(passedMatrix.getSize());
		for (List iteratedList : passedMatrix.VALUES) {
			if (!iteratedList.isEmpty()) {
				iteratedList.moveFront();
				while (iteratedList.index() >= 0) {
					MatrixEntry<Double> entry = getAsMatrixEntry(iteratedList.get());
					if (entry != null) {
						passedOperator.modify(passedMatrix, entry);
					}
					iteratedList.moveNext();
				}
			}
		}
		return newMatrix;
	}
	
	private static Matrix modifyRowsUsing(Matrix passedFirstMatrix, Matrix passedSecondMatrix, IDoubleOperator<Double> passedOperator) {
		if (passedFirstMatrix.validateSize(passedSecondMatrix)) {
			Matrix newMatrix = new Matrix(passedFirstMatrix.getSize());
			for (int iteratedRow = 0; iteratedRow < passedFirstMatrix.getSize(); iteratedRow += 1) {
				List firstRow = passedFirstMatrix.VALUES[iteratedRow];
				List secondRow = passedSecondMatrix.VALUES[iteratedRow];
				newMatrix.VALUES[iteratedRow] = interleaveAndOperate(firstRow, secondRow, passedOperator);
			}
			return newMatrix;
		}
		return null;
	}
	
	private static List interleaveAndOperate(List passedFirstRow, List passedSecondRow, IDoubleOperator<Double> passedOperator) {
		List newList = new List();
		passedFirstRow.moveFront();
		passedSecondRow.moveFront();
		while ((passedFirstRow.index() >= 0) && (passedSecondRow.index() >= 0)) {
			MatrixEntry<Double> firstEntry = getAsMatrixEntry(passedFirstRow.get());
			MatrixEntry<Double> secondEntry = getAsMatrixEntry(passedSecondRow.get());
			
			if ((firstEntry != null) && (secondEntry != null)) { // Belt
				if (firstEntry.getRow() == secondEntry.getRow()) { // Suspenders
					/**
					 * Case 1: Columns for both entries are equal.
					 */
					if (firstEntry.getColumn() == secondEntry.getColumn()) {
						newList.append(fromOperator(firstEntry.getRow(), firstEntry.getColumn(), firstEntry.getValue(), secondEntry.getValue(), passedOperator));
					}
					/**
					 * Case 2: Column for first entry is less than that of second, implying that the second Matrix has a 0 at (first.row, first.column)
					 */
					else if (firstEntry.getColumn() < secondEntry.getColumn()) {
						newList.append(fromOperator(firstEntry.getRow(), firstEntry.getColumn(), firstEntry.getValue(), 0, passedOperator));
					}
					/**
					 * Case 3: Column for second entry is less than that of first, implying that the first Matrix has a 0 at (second.row, second.column)
					 */
					else {
						newList.append(fromOperator(firstEntry.getRow(), secondEntry.getColumn(), 0, secondEntry.getValue(), passedOperator));
					}
				}
			}
			
			passedFirstRow.moveNext();
			passedSecondRow.moveNext();
		}
		return newList;
	}
	
	private static MatrixEntry<Double> fromOperator(int passedRow, int passedColumn, double passedFirst, double passedSecond, IDoubleOperator<Double> passedOperator) {
		Double result = passedOperator.operate(passedFirst, passedSecond);
		return new MatrixEntry<Double>(result, passedRow, passedColumn);
	}

	@SuppressWarnings("unchecked") // Cast checked by instance of contained entry value
	private static MatrixEntry<Double> getAsMatrixEntry(Object passedObject) {
		if (passedObject instanceof MatrixEntry<?>) {
			if (((MatrixEntry<?>) passedObject).getValue() instanceof Double) {
				return (MatrixEntry<Double>) passedObject;
			}
		}
		return null;
	}
	
	/* IEntryOperator Implementation */
	
	public interface IDoubleOperator<T> {
		/**
		 * Should return the result of an operation between the first and the second.
		 * 
		 * @param first - The first double to operate on
		 * @param second - The second double to operate on
		 * @return The result of the operation
		 */
		public T operate(double first, double second);
	}
	
	public interface IEntryModifier<T> {
		
		/**
		 * This method will modify the passed {@link Matrix#MatrixEntry} in some capacity, and possibly the {@link Matrix.}
		 * 
		 * @param passedMatrix - The {@link Matrix.} the entry belongs to
		 * @param passedEntry - The {@link Matrix#MatrixEntry} in question
		 */
		public void modify(Matrix passedMatrix, MatrixEntry<T> passedEntry);
	}

	/* MatrixEntry Implementation */

	public static class MatrixEntry<T> {

		private final int ROW;
		private final int COLUMN;
		private T VALUE;

		public MatrixEntry(T passedValue, int passedRow, int passedColumn) {
			this.VALUE = passedValue;
			this.ROW = passedRow;
			this.COLUMN = passedColumn;
		}

		public int getRow() {
			return this.ROW;
		}

		public int getColumn() {
			return this.COLUMN;
		}

		public T getValue() {
			return this.VALUE;
		}

		public void setValue(T passedValue) {
			this.VALUE = passedValue;
		}

		@Override
		public boolean equals(Object passedObject) {
			if (passedObject instanceof MatrixEntry<?>) {
				Object thisValue = this.getValue();
				Object passedValue = ((MatrixEntry<?>) passedObject).getValue();
				return Objects.equals(thisValue, passedValue);
			}
			return false;
		}

		public MatrixEntry<T> copy() {
			return new MatrixEntry<T>(this.VALUE, this.ROW, this.COLUMN);
		}
	}
}
