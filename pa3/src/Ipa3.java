/*********************************************************************
 * Malcolm Riley
 * CruzID: masriley
 * Assignment: pa3
 * 10-2017
 *********************************************************************/

public interface Ipa3 {

	// Access functions
	/**
	 * Returns n, the number of rows and columns of this Matrix
	 * 
	 * @return
	 */
	public int getSize();

	/**
	 * Returns the number of non-zero entries in this Matrix
	 */
	public int getNNZ();

	/**
	 * overrides Object's equals() method
	 * 
	 * @param x
	 * @return
	 */
	public boolean equals(Object x);

	// Manipulation procedures
	/**
	 * sets this Matrix to the zero state
	 */
	public void makeZero();

	/**
	 * returns a new Matrix having the same entries as this Matrix void changeEntry(int i, int j, double x)
	 * changes ith row, jth column of this Matrix to x
	 * pre: 1<=i<=getSize(), 1<=j<=getSize()
	 * 
	 * @return
	 */
	public Matrix copy();

	/**
	 * changes ith row, jth column of this Matrix to x
	 * 
	 * pre: 1<=i<=getSize(), 1<=j<=getSize()
	 * @param i
	 * @param j
	 * @param x
	 */
	public void changeEntry(int i, int j, double x);

	/**
	 * returns a new Matrix that is the scalar product of this Matrix with x Matrix add(Matrix M)
	 * 
	 * @param x
	 * @return
	 */
	public Matrix scalarMult(double x);

	/**
	 * returns a new Matrix that is the sum of this Matrix with M
	 * pre: getSize()==M.getSize()
	 * 
	 * @param M
	 * @return
	 */
	public Matrix add(Matrix M);

	/**
	 * returns a new Matrix that is the difference of this Matrix with M
	 * pre: getSize()==M.getSize()
	 * 
	 * @param M
	 * @return
	 */
	public Matrix sub(Matrix M);

	/**
	 * returns a new Matrix that is the transpose of this Matrix
	 * 
	 * @return
	 */
	public Matrix transpose();

	/**
	 * returns a new Matrix that is the product of this Matrix with M
	 * pre: getSize()==M.getSize()
	 * 
	 * @param M
	 * @return
	 */
	public Matrix mult(Matrix M);

	// Other functions
	/**
	 * overrides Object's toString() method
	 * 
	 * @return
	 */
	public String toString(); //

}
