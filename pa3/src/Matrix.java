
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
				this.getRow(passedRow).delete();
			}
			else {
				entry.setValue(Double.valueOf(passedNewValue));
			}
		}
		else {
			if (passedNewValue != 0) {
				this.getRow(passedRow).insertBefore(new MatrixEntry<Double>(Double.valueOf(passedNewValue), passedRow, passedColumn));
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
		final IEntryModifier<Double> operator = (matrix, entry) -> { matrix.addEntry(entry.getRow(), entry.getColumn(), entry.getValue());};
		return modifyUsing(this, operator);
	}

	@Override
	public Matrix scalarMult(double passedValue) {
		final IEntryModifier<Double> operator = (matrix, entry) -> { matrix.addEntry(entry.getRow(), entry.getColumn(), entry.getValue() * passedValue);};
		return modifyUsing(this, operator);
	}

	@Override
	public Matrix transpose() {
		final IEntryModifier<Double> operator = (matrix, entry) -> { matrix.addEntry(entry.getColumn(), entry.getRow(), entry.getValue());};
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
		if (this.validateSize(passedMatrix)) {
			Matrix transpose = passedMatrix.transpose();
			Matrix newMatrix = new Matrix(this.DIMENSION);
			for (int iteratedRow = 0; iteratedRow < this.DIMENSION; iteratedRow += 1) {
				List firstRow = this.getRow(iteratedRow);
				for (int iteratedColumn = 0; iteratedColumn < this.DIMENSION; iteratedColumn += 1) {
					List secondRow = transpose.getRow(iteratedColumn);
					double dotProduct = 0;
					for (parallelFront(firstRow, secondRow); parallelValid(firstRow, secondRow); parallelNext(firstRow, secondRow)) {
						MatrixEntry<Double> firstEntry = getAsMatrixEntry(firstRow.get());
						MatrixEntry<Double> secondEntry = getAsMatrixEntry(secondRow.get());
						
						int column = getLesserColumn(firstEntry, secondEntry);
						
						double firstValue = getValue(firstEntry, column);
						double secondValue = getValue(secondEntry, column);
						dotProduct += (firstValue * secondValue);
					}
					newMatrix.addEntry(iteratedRow, iteratedColumn, dotProduct);
				}
			}
		}
		return null;
	}
	
	/* Protected Methods */
	
	protected List getRow(int passedRowIndex) {
		if (this.VALUES[passedRowIndex] == null) {
			this.VALUES[passedRowIndex] = new List();
		}
		return this.VALUES[passedRowIndex];
	}
	
	protected void addEntry(int passedRowIndex, int passedColumnIndex, double passedNewValue) {
		if (this.validateIndices(passedRowIndex, passedColumnIndex)) {
			if (passedNewValue != 0) {
				List row = this.getRow(passedRowIndex);
				for (row.moveBack(); row.index() >= 0; row.movePrev()) {
					MatrixEntry<Double> iteratedEntry = getAsMatrixEntry(row.get());
					if (iteratedEntry != null ) {
						if (iteratedEntry.getColumn() < passedColumnIndex) {
							row.insertAfter(new MatrixEntry<Double>(Double.valueOf(passedNewValue), passedRowIndex, passedColumnIndex));
						}
					}
				}
			}
		}
	}

	/* Internal Methods */

	private MatrixEntry<Double> getEntry(int passedRowIndex, int passedColumnIndex) {
		if (this.validateIndices(passedRowIndex, passedColumnIndex)) {
			List row = this.getRow(passedRowIndex);
			for (row.moveFront(); row.index() >= 0; row.moveNext()) {
				MatrixEntry<Double> iteratedEntry = getAsMatrixEntry(row.get());
				if (iteratedEntry != null ) {
					if (iteratedEntry.getColumn() > passedColumnIndex) {
						break;
					}
					if (iteratedEntry.getColumn() == passedColumnIndex) {
						return iteratedEntry;
					}
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
			if (!isListEmpty(iteratedList)) {
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
				List firstRow = passedFirstMatrix.getRow(iteratedRow);
				List secondRow = passedSecondMatrix.getRow(iteratedRow);
				newMatrix.VALUES[iteratedRow] = interleaveAndOperate(firstRow, secondRow, passedOperator);
			}
			return newMatrix;
		}
		return null;
	}
	
	private static List interleaveAndOperate(List passedFirstRow, List passedSecondRow, IDoubleOperator<Double> passedOperator) {
		List newList = new List();
		for (parallelFront(passedFirstRow, passedSecondRow); parallelValid(passedFirstRow, passedSecondRow); parallelNext(passedFirstRow, passedSecondRow)) {
			MatrixEntry<Double> firstEntry = getAsMatrixEntry(passedFirstRow.get());
			MatrixEntry<Double> secondEntry = getAsMatrixEntry(passedSecondRow.get());
			if (column >= 0) {
				double result = passedOperator.operate(getValue(firstEntry, column), getValue(secondEntry, column));
				newList.append(new MatrixEntry<Double>(result, row, column));
			}
		}
		return newList;
	}

	/**
	 * Casts the passed {@link Object} to {@link MatrixEntry<Double>}, returning it, or {@code null} instead if:
	 * <li>
	 * {@code passedObject} is not an instance of {@link MatrixEntry}
	 * </li>
	 * <li>
	 * {@link MatrixEntry#getValue()} does not return an instance of {@link Double} (effectively verifying that the {@link MatrixEntry} can be safely cast to {@code MatrixEntry<Double>}.
	 * </li>
	 * 
	 * @param passedObject - The {@link Object} to check and cast
	 * @return An appropriately-cast {@link MatrixEntry<Double>}, or {@code null} if the cast is invalid.
	 */
	@SuppressWarnings("unchecked") // Cast checked by instance of contained entry value
	private static MatrixEntry<Double> getAsMatrixEntry(Object passedObject) {
		if (passedObject instanceof MatrixEntry<?>) {
			if (((MatrixEntry<?>) passedObject).getValue() instanceof Double) {
				return (MatrixEntry<Double>) passedObject;
			}
		}
		return null;
	}
	
	/**
	 * Calls {@link List#moveFront()} on both passed {@link List} instances.
	 * 
	 * @param passedFirstList - A {@link List}
	 * @param passedSecondList - Another {@link List}
	 */
	private static void parallelFront(List passedFirstList, List passedSecondList) {
		passedFirstList.moveFront();
		passedSecondList.moveBack();
	}
	
	/**
	 * Calls {@link List#moveNext()} on both passed {@link List} instances.
	 * 
	 * @param passedFirstList - A {@link List}
	 * @param passedSecondList - Another {@link List}
	 */
	private static void parallelNext(List passedFirstList, List passedSecondList) {
		passedFirstList.moveNext();
		passedSecondList.moveNext();
	}
	
	/**
	 * Checks whether {@link List#index()} is greater than or equal to zero for either of the passed {@link List} instances.
	 * 
	 * @param passedFirstList - The first {@link List} to check
	 * @param passedSecondList - The second {@link List} to check
	 * @return Whether either or both {@link List} indices are valid.
	 */
	private static boolean parallelValid(List passedFirstList, List passedSecondList) {
		return (passedFirstList.index() >= 0) || (passedSecondList.index() >= 0);
	}
	
	/**
	 * If {@code passedList} is {@code null}, returns true. Otherwise, returns {@link List#isEmpty()}.
	 * 
	 * @param passedList - The {@link List} to check
	 * @return Whether {@code passedList} is {@code null} or empty.
	 */
	private static boolean isListEmpty(List passedList) {
		if (passedList == null) {
			return true;
		}
		return passedList.isEmpty();
	}
	
	/**
	 * Returns the value of the passed {@link MatrixEntry} if its column value matches {@code passedExpectedColumn}.
	 * 
	 * Returns 0 for all other cases, including if {@code passedEntry} is {@code null}.
	 * 
	 * @param passedEntry - The {@link MatrixEntry} to examine
	 * @param passedExpectedColumn - The expected column index
	 * @return {@link MatrixEntry<Double>#getValue()} if non-{@code null} and column matches {@code passedExpectedColumn}, 0 for all other cases.
	 */
	private static double getValue(MatrixEntry<Double> passedEntry, int passedExpectedColumn) {
		if (passedEntry != null) {
			if (passedEntry.getColumn() == passedExpectedColumn) {
				return passedEntry.getValue().doubleValue();
			}
		}
		return 0;
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
