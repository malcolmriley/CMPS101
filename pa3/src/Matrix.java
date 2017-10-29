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

	public void changeEntry(int passedRowIndex, int passedColumnIndex, double passedNewValue) {
		int rowIndex = (passedRowIndex - 1);
		int columnIndex = (passedColumnIndex - 1);
		this.changeEntryInternal(rowIndex, columnIndex, passedNewValue);
	}

	public int getSize() {
		return this.DIMENSION;
	}

	public int getNNZ() {
		int nonzeroes = 0;
		for (List iteratedList : this.VALUES) {
			if (iteratedList != null) {
				nonzeroes += iteratedList.length();
			}
		}
		return nonzeroes;
	}

	public void makeZero() {
		for (List iteratedList : this.VALUES) {
			if (iteratedList != null) {
				iteratedList.clear();
			}
		}
	}

	public Matrix copy() {
		final IEntryModifier<Double> operator = (matrix, entry) -> { matrix.changeEntryInternal(entry.getRow(), entry.getColumn(), entry.getValue()); };
		return modifyUsing(this, operator);
	}

	public Matrix scalarMult(double passedValue) {
		final IEntryModifier<Double> operator = (matrix, entry) -> { matrix.changeEntryInternal(entry.getRow(), entry.getColumn(), entry.getValue() * passedValue); };
		return modifyUsing(this, operator);
	}

	public Matrix transpose() {
		final IEntryModifier<Double> operator = (matrix, entry) -> { matrix.changeEntryInternal(entry.getColumn(), entry.getRow(), entry.getValue()); };
		return modifyUsing(this, operator);
	}

	public Matrix add(Matrix passedMatrix) {
		final IDoubleOperator<Double> operation = (first, second) -> { return (first + second); };
		return modifyRowsUsing(this, getCopyIfThis(passedMatrix), operation);
	}

	public Matrix sub(Matrix passedMatrix) {
		final IDoubleOperator<Double> operation = (first, second) -> { return (first - second); };
		return modifyRowsUsing(this, getCopyIfThis(passedMatrix), operation);
	}

	public Matrix mult(Matrix passedMatrix) {
		Matrix newMatrix = getCopyIfThis(passedMatrix);
		if (this.validateSize(passedMatrix)) {
			if (passedMatrix.getNNZ() > 0) {
				Matrix transpose = passedMatrix.transpose();
				newMatrix = new Matrix(this.DIMENSION);
				for (int iteratedRow = 0; iteratedRow < this.DIMENSION; iteratedRow += 1) {
					List firstRow = this.getRow(iteratedRow);
					for (int iteratedColumn = 0; iteratedColumn < this.DIMENSION; iteratedColumn += 1) {
						List secondRow = transpose.getRow(iteratedColumn);
						double dotProduct = 0;
						for (int column = parallelFront(firstRow, secondRow); parallelValid(firstRow, secondRow); column = parallelNext(firstRow, secondRow)) {
							MatrixEntry<Double> firstEntry = getAsMatrixEntry(firstRow.get());
							MatrixEntry<Double> secondEntry = getAsMatrixEntry(secondRow.get());
							
							dotProduct += (getValue(firstEntry, column) * getValue(secondEntry, column));
						}
						newMatrix.changeEntryInternal(iteratedRow, iteratedColumn, dotProduct);
					}
				}
			}
		}
		return newMatrix;
	}
	
	public boolean equals(Object passedObject) {
		if (passedObject == this) {
			return true;
		}
		else if (passedObject instanceof Matrix) {
			Matrix matrix = (Matrix)passedObject;
			if (this.validateSize(matrix)) {
				for (int iteratedRow = 0; iteratedRow < this.DIMENSION; iteratedRow += 1) {
					if (!listsAreEqual(this.VALUES[iteratedRow], matrix.VALUES[iteratedRow])) {
						return false;
					}
				}
				return true;
			}
		}
		return false;
	}
	
	public String toString() {
		String representation = "";
		for (int iteratedRow = 0; iteratedRow < this.getSize(); iteratedRow += 1) {
			if (!this.getRow(iteratedRow).isEmpty()) {
				if (iteratedRow != 0) {
					representation += "\n";
				}
				representation += String.format("%d: %s", (iteratedRow + 1), this.getRow(iteratedRow).toString());
			}
		}
		return representation;
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

	/* Internal Methods */

	/**
	 * If {@code passedMatrix} is reference-equal to {@code this}, returns the result of {@link #copy()}.
	 * 
	 * Used to avoid some very confusing bugs if the methods are passed {@code this}, as the backing {@link List} instances occasionally double-iterate.
	 * 
	 * @param passedMatrix - The matrix to be used for the operation
	 * @return If not reference-equal to {@code this}, {@code passedMatrix}, otherwise a {@link #copy()}.
	 */
	private Matrix getCopyIfThis(Matrix passedMatrix) {
		if (passedMatrix == this) {
			return this.copy();
		}
		return passedMatrix;
	}
	
	/**
	 * Internal version of {@link #changeEntry(int, int, double)}, that doesn't modify the row and column indices.
	 * 
	 * @param passedRowIndex - The row index of the entry to modify
	 * @param passedColumnIndex - The column index of the entry to modify
	 * @param passedNewValue - The new value to use for modifying
	 */
	private void changeEntryInternal(int passedRowIndex, int passedColumnIndex, double passedNewValue) {
		if (this.validateIndices(passedRowIndex, passedColumnIndex)) {
			List row = this.getRow(passedRowIndex);
			// * If the list is empty, just append a new entry
			if (row.isEmpty()) {
				if (passedNewValue != 0) {
					row.append(new MatrixEntry<Double>(passedNewValue, passedRowIndex, passedColumnIndex));
				}
			}
			// Guaranteed to operate on a List with at least one entry
			else {
				for (row.moveFront(); row.index() >= 0; row.moveNext()) {
					MatrixEntry<Double> iteratedEntry = getAsMatrixEntry(row.get());
					// * If the discovered column index is greater than the inserted, we passed the entry, insert before the cursor and return
					if (iteratedEntry.getColumn() > passedColumnIndex) {
						if (passedNewValue != 0) {
							row.insertBefore(new MatrixEntry<Double>(passedNewValue, passedRowIndex, passedColumnIndex));
							return;
						}
					}
					// * If the discovered column index equals the desired index, alter the cursor node
					else if (iteratedEntry.getColumn() == passedColumnIndex) {
						if (passedNewValue == 0) {
							row.delete();
							return;
						}
						else {
							iteratedEntry.setValue(passedNewValue);
							return;
						}
					}
				}
				// If we reach the end of the List before either previous * condition is met, append a new entry
				if (passedNewValue != 0) {
					row.append(new MatrixEntry<Double>(passedNewValue, passedRowIndex, passedColumnIndex));
				}
			}
		}
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
	 * @param passedSourceMatrix - The source {@link Matrix} to operate on
	 * @param passedOperator - The operation to perform on each individual {@link MatrixEntry}
	 * @return A new {@link Matrix} instance, the result of the completed operation.
	 */
	private static Matrix modifyUsing(Matrix passedSourceMatrix, IEntryModifier<Double> passedOperator) {
		Matrix newMatrix = new Matrix(passedSourceMatrix.getSize());
		for (List iteratedList : passedSourceMatrix.VALUES) {
			if (iteratedList != null) {
				for (iteratedList.moveFront(); iteratedList.index() >= 0; iteratedList.moveNext()) {
					MatrixEntry<Double> entry = getAsMatrixEntry(iteratedList.get());
					if (entry != null) {
						passedOperator.modify(newMatrix, entry);
					}
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
		for (int column = parallelFront(passedFirstRow, passedSecondRow); parallelValid(passedFirstRow, passedSecondRow); column = parallelNext(passedFirstRow, passedSecondRow)) {
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
	private static int parallelFront(List passedFirstList, List passedSecondList) {
		passedFirstList.moveFront();
		passedSecondList.moveFront();

		MatrixEntry<Double> firstEntry = getAsMatrixEntry(passedFirstList.get());
		MatrixEntry<Double> secondEntry = getAsMatrixEntry(passedSecondList.get());
		
		return getLesserColumn(firstEntry, secondEntry);
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
		int firstValue = getColumnValue(getAsMatrixEntry(passedFirstList.get()));
		int secondValue = getColumnValue(getAsMatrixEntry(passedSecondList.get()));

		if (firstValue == secondValue) {
			passedFirstList.moveNext();
			passedSecondList.moveNext();
		}
		else if (firstValue < secondValue) {
			passedFirstList.moveNext();
		}
		else if (firstValue > secondValue) {
			passedSecondList.moveNext();
		}

		return getLesserColumn(getAsMatrixEntry(passedFirstList.get()), getAsMatrixEntry(passedSecondList.get()));
	}
	
	/**
	 * Returns the lesser of the two {@link MatrixEntry#getColumn()} values.
	 * 
	 * @param passedFirstEntry - A {@link MatrixEntry} to examine
	 * @param passedSecondEntry - Another {@link MatrixEntry} to examine
	 * @return The result of {@link Integer#min(int, int)} upon the two {@link MatrixEntry#getColumn()} values, or -1 if both are null.
	 */
	private static <T> int getLesserColumn(MatrixEntry<T> passedFirstEntry, MatrixEntry<T> passedSecondEntry) {
		if ((passedFirstEntry == null) && (passedSecondEntry == null)) {
			return -1;
		}
		return Integer.min(getColumnValue(passedFirstEntry), getColumnValue(passedSecondEntry));
	}
	
	/**
	 * Returns the result of {@link MatrixEntry#getColumn()} on the passed {@link MatrixEntry} instance, or {@link Integer#MAX_VALUE} if it is null.
	 * 
	 * @param passedFirstEntry - A {@link MatrixEntry} to examine
	 * @return The column value.
	 */
	private static int getColumnValue(MatrixEntry<?> passedEntry) {
		if (passedEntry == null) {
			return Integer.MAX_VALUE;
		}
		return passedEntry.getColumn();
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
	
	/**
	 * Returns whether the two passed {@link List} instances are equal.
	 * 
	 * If they are both {@code null}, also returns true (since they are considered empty lists).
	 * 
	 * @param passedFirstList - A {@link List} to examine
	 * @param passedSecondList - Another {@link List} to examine
	 * @return Whether or not both lists are {@code null} or equal.
	 */
	private static boolean listsAreEqual(List passedFirstList, List passedSecondList) {
		if (isEmptyOrNull(passedFirstList) && isEmptyOrNull(passedSecondList)) {
			return true;
		}
		else {
			if (passedFirstList != null) {
				return passedFirstList.equals(passedSecondList);
			}
			if (passedSecondList != null) {
				return passedSecondList.equals(passedFirstList);
			}
		}
		return false;
	}
	
	private static boolean isEmptyOrNull(List passedList) {
		if (passedList == null) {
			return true;
		}
		else return passedList.isEmpty();
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
