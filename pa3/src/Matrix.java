import java.util.Objects;

/*********************************************************************
 * Malcolm Riley
 *
 * CruzID: masriley
 * Assignment: pa3
 *
 * 10-2017
 *********************************************************************/

public class Matrix {
	
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
