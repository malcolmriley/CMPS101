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
	
	public void changeEntry(int passedRow, int passedColumn, double passedNewValue) {
		if (this.validateIndices(passedRow, passedColumn)) {
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
	}
	
	@Override
	public int getSize() {
		return this.DIMENSION;
	}

	@Override
	public int getNNZ() {
		int nonzeroes = 0;
		for (List list : this.VALUES) {
			nonzeroes += list.length();
		}
		return nonzeroes;
	}

	@Override
	public void makeZero() {
		for (List list : this.VALUES) {
			list.clear();
		}
	}

	@Override
	public Matrix copy() {
		Matrix newMatrix = new Matrix(this.DIMENSION);
		// Optimization: If this matrix has no nonzero entries, simply return new (empty) Matrix.
		if (isZeroMatrix(this)) {
			for (List iteratedList : this.VALUES) {
				iteratedList.moveFront();
				while (iteratedList.index() >= 0) {
					MatrixEntry<Double> entry = getAsMatrixEntry(iteratedList.get());
					newMatrix.changeEntry(entry.getRow(), entry.getColumn(), entry.getValue().doubleValue());
					iteratedList.moveNext();
				}
			}
		}
		return newMatrix;
	}

	@Override
	public Matrix scalarMult(double passedValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Matrix add(Matrix passedMatrix) {
		if (this.validateSize(passedMatrix)) {
			
			// TODO:
		}
		return null;
	}

	@Override
	public Matrix sub(Matrix passedMatrix) {
		if (this.validateSize(passedMatrix)) {
			// TODO:
		}
		return null;
	}

	@Override
	public Matrix transpose() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Matrix mult(Matrix passedMatrix) {
		if (this.validateSize(passedMatrix)) {
			if (!isZeroMatrix(this) && !isZeroMatrix(passedMatrix)) {
				// TODO: perform matrix multiplication
			}
			else {
				/*
				 *  Optimization: If the number of nonzero entries in one or both matrices is zero, one or both is a zero matrix.
				 *  Therefore, the product is zero, so return a new (empty) Matrix.
				 */
				return new Matrix(this.DIMENSION);
			}
		}
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
	
	private static boolean isZeroMatrix(Matrix passedMatrix) {
		return (passedMatrix.getNNZ() <= 0);
	}
	
	@SuppressWarnings("unchecked") // Cast checked by instance of contained entry value
	private static MatrixEntry<Double> getAsMatrixEntry(Object passedObject) {
		if (passedObject instanceof MatrixEntry<?>) {
			if (((MatrixEntry<?>)passedObject).getValue() instanceof Double) {
				return (MatrixEntry<Double>)passedObject;
			}
		}
		return null;
	}
	
	/* MatrixEntry Implementation */
	
	public class MatrixEntry<T> {
		
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
		
		public boolean equals(Object passedObject) {
			if (passedObject instanceof MatrixEntry<?>) {
				Object thisValue = this.getValue();
				Object passedValue = ((MatrixEntry<?>)passedObject).getValue();
				return Objects.equals(thisValue, passedValue);
			}
			return false;
		}
		
		public MatrixEntry<T> copy() {
			return new MatrixEntry<T>(this.VALUE, this.ROW, this.COLUMN);
		}
	}
}
