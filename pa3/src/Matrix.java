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
	}
	
	/* PA3 Required Methods */
	
	public void changeEntry(int passedRow, int passedColumn, double passedNewValue) {
		if (passedNewValue == 0) {
			// TODO: If value already exists, subtract one from nonzero entries.
		}
	}
	
	@Override
	public int getSize() {
		return this.DIMENSION;
	}

	@Override
	public int getNNZ() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void makeZero() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Matrix copy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Matrix scalarMult(double passedValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Matrix add(Matrix passedMatrix) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Matrix sub(Matrix passedMatrix) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Matrix transpose() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Matrix mult(Matrix passedMatrix) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/* Internal Methods */
	
	private void addEntry(double passedValue) {
		// TODO:
	}
	
	private void removeEntry(double passedValue) {
		// TODO:
	}
	
	private MatrixEntry<?> getEntry(int passedRowIndex, int passedColumnIndex) {
		if (this.validateIndices(passedRowIndex, passedColumnIndex)) {
			List row = this.VALUES[passedRowIndex];
			if (!row.isEmpty()) {
				row.moveFront();
				while (row.index() >= 0) {
					MatrixEntry<?> iteratedObject = getAsMatrixEntry(row.get());
					if (iteratedObject != null) {
						if (iteratedObject.getColumn() == passedColumnIndex) {
							return iteratedObject;
						}
					}
					row.moveNext();
				}
			}
		}
		return null;
	}
	
	private static Double getMatrixEntryValue(MatrixEntry<?> passedMatrixEntry) {
		Object matrixEntryValue = passedMatrixEntry.getValue();
		if (matrixEntryValue instanceof Double) {
			return (Double)matrixEntryValue;
		}
		return null;
	}
	
	private static MatrixEntry<?> getAsMatrixEntry(Object passedObject) {
		if (passedObject instanceof MatrixEntry<?>) {
			return (MatrixEntry<?>)passedObject;
		}
		return null;
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
	}
}
