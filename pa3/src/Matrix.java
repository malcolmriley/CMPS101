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
	
	public Matrix(int passedDimension) {
		this.DIMENSION = passedDimension;
	}
	
	/* PA3 Required Methods */
	
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
