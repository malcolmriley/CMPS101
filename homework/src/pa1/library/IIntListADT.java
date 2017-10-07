package pa1.library;

public interface IIntListADT {
	
	/**
	 * Returns the number of elements in the {@link {@link IIntListADT}}.
	 * @return The number of elements in the {@link {@link IIntListADT}}.
	 */
	int length();
	
	/**
	 * If the cursor is defined, returns the index of the cursor element, otherwise returns -1.
	 * @return The index of the cursor element if defined, else -1.
	 */
	int index();
	
	/**
	 * Returns the element at the front of the {@link {@link IIntListADT}}.
	 * 
	 * @return The element at the front of the {@link {@link IIntListADT}}.
	 */
	int front();
	
	/**
	 * Returns the element at the back of the {@link {@link IIntListADT}}.
	 * 
	 * @return The element at the back of the {@link {@link IIntListADT}}.
	 */
	int back();
	
	/**
	 * Returns the element at the cursor.
	 * 
	 * @return - The element at the cursor.
	 */
	int get();
	
	/**
	 *  Returns true if and only if this {@link IIntListADT} and L are the same integer sequence. The states of the cursors in the two {@link IIntListADT}s are not used in determining equality.
	 *  
	 *  @param passedList - The list to compare for equality
	 *  @return Whether or not the two {@link IIntListADT}s are comprised of the same elements in the same order.
	 */
	boolean equals(IIntListADT passedList);
	
	/**
	 * Resets this {@link IIntListADT} to its original empty state.
	 */
	void clear();
	
	/**
	 * If {@link IIntListADT} is non-empty, places the cursor under the front element, otherwise does nothing.
	 */
	void moveFront();
	
	/**
	 * If {@link IIntListADT} is non-empty, places the cursor under the back element, otherwise does nothing.
	 */
	void moveBack();
	
	/**
	 * If cursor is defined and not at front, moves cursor one step toward front of this {@link IIntListADT}, if cursor is defined and at front, cursor becomes undefined, if cursor is undefined does nothing.
	 */
	void movePrev();
	
	/**
	 * If cursor is defined and not at back, moves cursor one step toward back of this {@link IIntListADT}, if cursor is defined and at back, cursor becomes undefined, if cursor is undefined does nothing.
	 */
	void moveNext();
	
	/**
	 * Insert a new element into this {@link IIntListADT}. If the {@link IIntListADT} is non-empty, insertion takes place before front element.
	 * 
	 * @param passedData - The element to insert
	 */
	void prepend(int passedData);
	
	/**
	 * Insert a new element into this {@link IIntListADT}. If the {@link IIntListADT} is non-empty, insertion takes place after the last element.
	 * 
	 * @param passedData - The element to insert
	 */
	void append(int passedData);
	
	/**
	 * Insert new element before cursor.
	 * 
	 * @param passedData - The element to insert
	 */
	void insertBefore(int passedData);
	
	/**
	 * Insert new element after cursor.
	 * 
	 * @param passedData - The element to insert
	 */
	void insertAfter(int passedData);
	
	/**
	 * Deletes the front element.
	 */
	void deleteFront();
	
	/**
	 * Deletes the back element.
	 */
	void deleteBack();
	
	/**
	 * Deletes cursor element, making cursor undefined.
	 */
	void delete();
	
	/**
	 * Overrides Object's toString method.
	 * 
	 * @return - A String representation of this {@link IIntListADT} consisting of a space separated sequence of integers, with front on left.
	 */
	public String toString();
	
	/**
	 * Returns a new {@link IIntListADT} representing the same sequence as this {@link IIntListADT}. The cursor in the new {@link {@link IIntListADT}} is undefined, regardless of the state of the cursor in this {@link IIntListADT}. This {@link IIntListADT} is unchanged.
	 * 
	 * @return - A new instance of this {@link IIntListADT}, with all nodes copied.
	 */
	public IIntListADT copy();
	
	/**
	 * Returns a new {@link IIntListADT} which is the concatenation of this {@link {@link IIntListADT}} followed by L. The cursor in the new {@link IIntListADT} is undefined, regardless of the states of the cursors in this {@link IIntListADT} and L. The states of this {@link IIntListADT} and L are unchanged.
	 * 
	 * @param passedList - The {@link IIntListADT} to concatenate with this one
	 * @return A new {@link IIntListADT}, consisting of this {@link IIntListADT} concatenated with the passed {@link IIntListADT}.
	 */
	public IIntListADT concat(IIntListADT passedList);
}
