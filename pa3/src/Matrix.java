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
	protected int NONZERO_ENTRIES;
	
	public Matrix(int passedDimension) {
		this.DIMENSION = passedDimension;
		this.VALUES = new List[passedDimension];
		this.NONZERO_ENTRIES = 0;
	}
	
	/* PA3 Required Methods */
	
	public void changeEntry(int passedRow, int passedColumn, double passedNewValue) {
		if (this.validateIndices(passedRow, passedColumn)) {
			MatrixEntry<Double> entry = this.getEntry(passedRow, passedColumn);
			if (entry != null) {
				if (passedNewValue == 0) {
					this.VALUES[passedRow].delete();
					this.NONZERO_ENTRIES -= 1;
				}
				else {
					entry.setValue(Double.valueOf(passedNewValue));
				}
			}
			else {
				if (passedNewValue != 0) {
					this.VALUES[passedRow].insertBefore(new MatrixEntry<Double>(Double.valueOf(passedNewValue), passedRow, passedColumn));
					this.NONZERO_ENTRIES += 1;
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
		return this.NONZERO_ENTRIES;
	}

	@Override
	public void makeZero() {
		for (List list : this.VALUES) {
			list.clear();
		}
		this.NONZERO_ENTRIES = 0;
	}

	@Override
	public Matrix copy() {
		Matrix newMatrix = new Matrix(this.DIMENSION);
		// Optimization: If this matrix has no nonzero entries, simply return new (empty) Matrix.
		if (this.getNNZ() > 0) {
			for (List list : this.VALUES) {
				
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
			if ((this.getNNZ() > 0) && (passedMatrix.getNNZ() > 0)) {
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
	
	@SuppressWarnings("unchecked") // Cast checked by instance of contained entry value
	private static MatrixEntry<Double> getAsMatrixEntry(Object passedObject) {
		if (passedObject instanceof MatrixEntry<?>) {
			if (((MatrixEntry<?>)passedObject).getValue() instanceof Double) {
				return (MatrixEntry<Double>)passedObject;
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
