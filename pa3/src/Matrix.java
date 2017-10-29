
/*********************************************************************
 * Malcolm Riley
 *
 * CruzID: masriley
 * Assignment: pa3
 *
 * 10-2017
 *********************************************************************/
import java.util.Objects;

public class Matrix {

	protected final int DIMENSION;
	protected final List[] VALUES;

	public Matrix(int passedDimension) {
		this.DIMENSION = passedDimension;
		this.VALUES = new List[passedDimension];
	}

	/* PA3 Required Methods */

	public void changeEntry(int passedRow, int passedColumn, double passedNewValue) {
		passedRow -= 1;
		passedColumn -= 1;
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
				MatrixEntry<Double> insertedEntry = new MatrixEntry<Double>(Double.valueOf(passedNewValue), passedRow, passedColumn);
				if (this.getRow(passedRow).isEmpty()) {
					this.getRow(passedRow).append(insertedEntry);
				}
				else {
					this.getRow(passedRow).insertBefore(insertedEntry);
				}
			}
		}
	}

	public int getSize() {
		return this.DIMENSION;
	}

	public int getNNZ() {
		int nonzeroes = 0;
		for (List iteratedList : this.VALUES) {
			nonzeroes += iteratedList.length();
		}
		return nonzeroes;
	}

	public void makeZero() {
		for (List iteratedList : this.VALUES) {
			iteratedList.clear();
		}
	}

	public Matrix copy() {
		final IEntryModifier<Double> operator = (matrix, entry) -> { matrix.addEntry(entry.getRow(), entry.getColumn(), entry.getValue());};
		return modifyUsing(this, operator);
	}

	public Matrix scalarMult(double passedValue) {
		final IEntryModifier<Double> operator = (matrix, entry) -> { matrix.addEntry(entry.getRow(), entry.getColumn(), entry.getValue() * passedValue);};
		return modifyUsing(this, operator);
	}

	public Matrix transpose() {
		final IEntryModifier<Double> operator = (matrix, entry) -> { matrix.addEntry(entry.getColumn(), entry.getRow(), entry.getValue());};
		return modifyUsing(this, operator);
	}

	public Matrix add(Matrix passedMatrix) {
		final IDoubleOperator<Double> operation = (first, second) -> { return (first + second); };
		return modifyRowsUsing(this, passedMatrix, operation);
	}

	public Matrix sub(Matrix passedMatrix) {
		final IDoubleOperator<Double> operation = (first, second) -> { return (first - second); };
		return modifyRowsUsing(this, passedMatrix, operation);
	}

	public Matrix mult(Matrix passedMatrix) {
		Matrix newMatrix = null;
		if (this.validateSize(passedMatrix)) {
			Matrix transpose = passedMatrix.transpose();
			newMatrix = new Matrix(this.DIMENSION);
			for (int iteratedRow = 0; iteratedRow < this.DIMENSION; iteratedRow += 1) {
				List firstRow = this.getRow(iteratedRow);
				for (int iteratedColumn = 0; iteratedColumn < this.DIMENSION; iteratedColumn += 1) {
					List secondRow = transpose.getRow(iteratedColumn);
					double dotProduct = 0;
					int column = 0;
					for (parallelFront(firstRow, secondRow); parallelValid(firstRow, secondRow); column = parallelNext(firstRow, secondRow)) {
						MatrixEntry<Double> firstEntry = getAsMatrixEntry(firstRow.get());
						MatrixEntry<Double> secondEntry = getAsMatrixEntry(secondRow.get());
						
						dotProduct += (getValue(firstEntry, column) * getValue(secondEntry, column));
					}
					newMatrix.addEntry(iteratedRow, iteratedColumn, dotProduct);
				}
			}
		}
		return newMatrix;
	}
	
	public boolean equals(Object passedObject) {
		if (passedObject instanceof Matrix) {
			Matrix matrix = (Matrix)passedObject;
			if (this.validateSize(matrix)) {
				for (int iteratedRow = 0; iteratedRow < this.DIMENSION; iteratedRow += 1) {
					// Check lengths before checking each entry (cheaper)
					if (this.getRow(iteratedRow).length() != matrix.getRow(iteratedRow).length()) {
						return false;
					}
					if (!this.getRow(iteratedRow).listsAreEqual(matrix.getRow(iteratedRow))) {
						return false;
					}
				}
			}
		}
		return false;
	}
	
	public String toString() {
		String matrix = "";
		for (int iteratedRow = 0; iteratedRow < this.DIMENSION; iteratedRow += 1) {
			if (!this.getRow(iteratedRow).isEmpty()) {
				matrix += String.format("%d: %s", (iteratedRow + 1), this.getRow(iteratedRow).toString());
			}
			if (iteratedRow < (this.DIMENSION - 1) && (!matrix.isEmpty())) {
				matrix += "\n";
			}
		}
		return matrix;
	}
	
	/* Protected Methods */
	
	/**
	 * Returns the {@link List} that backs the Matrix row indicated by {@code passedRowIndex}.
	 * 
	 * If that particular {@link List} instance is {@code null}, it initializes it before returning it.
	 * 
	 * If {@code passedRowIndex} is not a valid index value, returns {@code null}.
	 * 
	 * @param passedRowIndex - The index of the row to fetch
	 * @return The {@link List} corresponding to that row, or {@code null} if the index value is invalid.
	 */
	protected List getRow(int passedRowIndex) {
		if (this.validateIndex(passedRowIndex)) {
			if (this.VALUES[passedRowIndex] == null) {
				this.VALUES[passedRowIndex] = new List();
			}
			return this.VALUES[passedRowIndex];
		}
		return null;
	}
	
	/**
	 * Adds a new {@link MatrixEntry} to this {@link Matrix}.
	 * 
	 * IMPORTANT: This method does not verify that a {@link MatrixEntry} with {@code passedColumnIndex} does not already exist.
	 * 
	 * @param passedRowIndex - The index of the row to add a {@link MatrixEntry} at
	 * @param passedColumnIndex - The index of the column to add a {@link MatrixEntry} at
	 * @param passedNewValue - The value of the new {@link MatrixEntry}
	 */
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

	/**
	 * Returns the {@link MatrixEntry} at the indices specified, or {@code null} if no such {@link MatrixEntry} exists, or if the indices
	 * fall out of range.
	 * 
	 * Importantly, it does not modify the cursors of the row {@link List} instances backing this {@link Matrix} before it returns.
	 * 
	 * @param passedRowIndex - The row index to fetch from
	 * @param passedColumnIndex - The column index to fetch from
	 * @return The {@link MatrixEntry} at those indices, or {@code null} if no such entry exists, or if the indices are out of range.
	 */
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

	/**
	 * Verifies that this {@link Matrix} is the same size as {@code passedMatrix}, by calling {@link Matrix#getSize()} on both.
	 * 
	 * @param passedMatrix - The {@link Matrix} instance to examine
	 * @return Whether this and the passed {@link Matrix} instances are the same size.
	 */
	private boolean validateSize(Matrix passedMatrix) {
		if (passedMatrix != null) {
			return (this.getSize() == passedMatrix.getSize());
		}
		return false;
	}

	/**
	 * Validates both passed index values, by calling {@link #validateIndex(int)} on both.
	 * 
	 * Though the parameters have specific names, the method is agnostic of their representational value.
	 * 
	 * @see {@link Matrix#validateIndex(int)}
	 * @param passedRowIndex - The "row" index to validate
	 * @param passedColumnIndex - The "column" index to validate.
	 * @return Whether both pass {@link Matrix#validateIndex(int)}.
	 */
	private boolean validateIndices(int passedRowIndex, int passedColumnIndex) {
		return this.validateIndex(passedRowIndex) && this.validateIndex(passedColumnIndex);
	}

	/**
	 * Verifies that the passed value is greater than 0 and less than {@link #DIMENSION}.
	 * 
	 * @param passedIndex - The index to validate
	 * @return Whether or not (0 <= value < {@link #DIMENSION}).
	 */
	private boolean validateIndex(int passedIndex) {
		return (passedIndex >= 0) && (passedIndex < this.DIMENSION);
	}
	
	/**
	 * Performs an operation on individual entries in this {@link Matrix}, using the passed {@link IEntryModifier}.
	 * 
	 * @param passedMatrix - The source {@link Matrix} to operate on
	 * @param passedOperator - The operation to perform on each individual {@link MatrixEntry}
	 * @return A new {@link Matrix} instance, the result of the completed operation.
	 */
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
	
	/**
	 * Modifies all contained {@link MatrixEntry} instances owned by the passed {@link Matrix} instances using the passed {@link IDoubleOperator<Double>}, returning the result as a new instance.
	 * 
	 * @param passedFirstMatrix - A {@link Matrix} to operate on
	 * @param passedSecondMatrix - Another {@link Matrix} to operate on
	 * @param passedOperator - The operation that will be performed on all discovered {@link MatrixEntry} instances
	 * @return A new {@link Matrix}, the result of performing the completed operation.
	 */
	private static Matrix modifyRowsUsing(Matrix passedFirstMatrix, Matrix passedSecondMatrix, IDoubleOperator<Double> passedOperator) {
		if (passedFirstMatrix.validateSize(passedSecondMatrix)) {
			Matrix newMatrix = new Matrix(passedFirstMatrix.getSize());
			for (int iteratedRow = 0; iteratedRow < passedFirstMatrix.getSize(); iteratedRow += 1) {
				List firstRow = passedFirstMatrix.getRow(iteratedRow);
				List secondRow = passedSecondMatrix.getRow(iteratedRow);
				newMatrix.VALUES[iteratedRow] = interleaveAndOperate(firstRow, secondRow, iteratedRow, passedOperator);
			}
			return newMatrix;
		}
		return null;
	}
	
	/**
	 * Performs an interleaving operation on the passed row {@link List} instances. That is to say, it iterates over both {@link List} instances in parallel,
	 * operating on the value of the entry with the lower {@link MatrixEntry#getColumn()} value (or both, if they are equal), using the passed {@link IDoubleOperator<Double>}.
	 * 
	 * @param passedFirstRow - A row-representing {@link List} to operate on
	 * @param passedSecondRow - Another row-representing {@link List} to operate on
	 * @param passedRowIndex - The index of both rows
	 * @param passedOperator - The operation to perform
	 * @return A new {@link List} instance, the result of performing the completed operation.
	 */
	private static List interleaveAndOperate(List passedFirstRow, List passedSecondRow, int passedRowIndex, IDoubleOperator<Double> passedOperator) {
		List newList = new List();
		int column = -1;
		for (parallelFront(passedFirstRow, passedSecondRow); parallelValid(passedFirstRow, passedSecondRow); column = parallelNext(passedFirstRow, passedSecondRow)) {
			MatrixEntry<Double> firstEntry = getAsMatrixEntry(passedFirstRow.get());
			MatrixEntry<Double> secondEntry = getAsMatrixEntry(passedSecondRow.get());
			if (column >= 0) {
				double result = passedOperator.operate(getValue(firstEntry, column), getValue(secondEntry, column));
				if (result != 0) {
					newList.append(new MatrixEntry<Double>(result, passedRowIndex, column));
				}
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
	 * Calls {@link List#moveNext()} on the {@link List} instance with the lesser of the two {@link MatrixEntry#getColumn()} values.
	 * 
	 * If one or both are {@code null}, or if both returned {@link MatrixEntry#getColumn()} values are equal, calls {@link List#moveNext()} on both.
	 * 
	 * Returns the index of the lesser column (or the value of both if both are equal or {@code null}).
	 * 
	 * @param passedFirstList - A {@link List}
	 * @param passedSecondList - Another {@link List}
	 */
	private static int parallelNext(List passedFirstList, List passedSecondList) {
		MatrixEntry<Double> firstEntry = getAsMatrixEntry(passedFirstList.get());
		MatrixEntry<Double> secondEntry = getAsMatrixEntry(passedSecondList.get());
		
		if ((firstEntry != null) && (secondEntry != null)) {
			if (firstEntry.getColumn() > secondEntry.getColumn()) {
				passedSecondList.moveNext();
				return secondEntry.getColumn();
			}
			if (firstEntry.getColumn() < secondEntry.getColumn()){
				passedFirstList.moveNext();
				return firstEntry.getColumn();
			}
		}
		passedFirstList.moveNext();
		passedSecondList.moveNext();
		return (firstEntry != null) ? firstEntry.getColumn() : -1;
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
		
		public String toString() {
			return String.format("(%d, %s)", (this.COLUMN + 1), this.VALUE);
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
