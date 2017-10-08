/*********************************************************************
 * Malcolm Riley
 *
 * CruzID: masriley
 * Assignment: pa1
 *
 * 10-2017
 *********************************************************************/
package pa1;

public class List {

	private static final int CURSOR_INDEX_INVALID = -1;

	private Node<Integer> cursor;
	private Node<Integer> front;
	private Node<Integer> back;
	private int cursorIndex = CURSOR_INDEX_INVALID;
	private int length;

	public List() {

	}

	/* Required Methods */

	public int length() {
		return this.length;
	}

	public int index() {
		if (this.isNodeDefined(this.cursor)) {
			return this.cursorIndex;
		}
		return -1;
	}

	public int front() {
		return this.front.get().intValue();
	}

	public int back() {
		return this.back.get().intValue();
	}

	public int get() {
		return this.cursor.get().intValue();
	}

	/**
	 * Determines whether this {@link List} is equivalent to the passed {@link List}; that is, whether it contains equivalent values in the same order.
	 * 
	 * @param passedList - The list to compare this one to
	 * @return Whether or not the two lists are equivalent.
	 */
	public boolean equals(List passedList) {
		if (this.length() == passedList.length()) {
			if (!this.isEmpty()) {
				Node<Integer> thisListIterator = this.getFrontNode();
				Node<Integer> passedListIterator = passedList.getFrontNode();
				while (isNodeDefined(thisListIterator)) {
					if (!thisListIterator.equals(passedListIterator)) {
						return false;
					}
					thisListIterator = thisListIterator.getNext();
					passedListIterator = passedListIterator.getNext();
				}
			}
			return true;
		}
		return false;
	}

	/**
	 * Clears the list, removing and resetting all owned {@link Node}s.
	 */
	public void clear() {
		if (!this.isEmpty()) {
			Node<Integer> thisListIterator = this.getFrontNode();
			while(isNodeDefined(thisListIterator)) {
				if (isNodeDefined(thisListIterator.getPrevious())) {
					thisListIterator.getPrevious().reset();
				}
				if (isNodeDefined(thisListIterator.getNext())) {
					thisListIterator.reset();
				}
				thisListIterator = thisListIterator.getNext();
			}
		}
		this.front = null;
		this.back = null;
		this.cursor = null;
		this.cursorIndex = CURSOR_INDEX_INVALID;
		this.length = 0;
	}

	/**
	 * Moves the cursor to the front of the {@link List}.
	 */
	public void moveFront() {
		if (this.isNodeDefined(this.front)) {
			this.cursor = this.front;
			this.cursorIndex = 0;
		}
	}

	/**
	 * Moves the cursor to the back of the {@link List}.
	 */
	public void moveBack() {
		if (this.isNodeDefined(this.back)) {
			this.cursor = this.back;
			this.cursorIndex = (this.length - 1);
		}
	}

	/**
	 * Moves the cursor to the position one previous from its current position in this {@link List}.
	 */
	public void movePrev() {
		this.cursor = this.cursor.getPrevious();
		if (!this.isNodeDefined(this.cursor)) {
			this.cursorIndex = CURSOR_INDEX_INVALID;
		}
		else {
			this.cursorIndex -= 1;
		}
	}

	/**
	 * Moves the cursor to the position one next from its current position in this {@link List}.
	 */
	public void moveNext() {
		this.cursor = this.cursor.getNext();
		if (!this.isNodeDefined(this.cursor)) {
			this.cursorIndex = CURSOR_INDEX_INVALID;
		}
		else {
			this.cursorIndex += 1;
		}
	}

	public void prepend(int passedData) {
		Node<Integer> newNode = this.newNode(passedData);
		if (this.isNodeDefined(this.front)) {
			newNode.setNext(this.front);
			this.front.setPrevious(newNode);
		}
		this.front = newNode;
		this.cursorIndex += 1;
		this.onNodeAdded(newNode);
	}

	public void append(int passedData) {
		Node<Integer> newNode = this.newNode(passedData);
		if (this.isNodeDefined(this.back)) {
			newNode.setPrevious(this.back);
			this.back.setNext(newNode);
		}
		this.back = newNode;
		this.onNodeAdded(newNode);
	}

	public void insertBefore(int passedData) {
		if (isNodeDefined(this.cursor)) {
			// Set links on new node
			Node<Integer> newNode = this.newNode(passedData);
			newNode.setNext(this.cursor);
			newNode.setPrevious(this.cursor.getPrevious());
			
			// Set links on previous node
			this.cursor.getPrevious().setNext(newNode);
			
			// Set links on cursor node
			this.cursor.setPrevious(newNode);
			this.cursorIndex += 1;
			this.onNodeAdded(newNode);
		}
	}

	public void insertAfter(int passedData) {
		if (isNodeDefined(this.cursor)) {
			// Set links on new node
			Node<Integer> newNode = this.newNode(passedData);
			newNode.setNext(this.cursor.getNext());
			newNode.setPrevious(this.cursor);
			
			// Set links on next node
			this.cursor.getNext().setPrevious(newNode);
			
			// Set links on cursor node
			this.cursor.setNext(newNode);
			this.onNodeAdded(newNode);
		}
	}

	public void deleteFront() {
		if (this.cursor.equals(this.front)) {
			this.cursorIndex = CURSOR_INDEX_INVALID;
		}
		this.onNodeRemoved(this.front);
	}

	public void deleteBack() {
		if (this.cursor.equals(this.back)) {
			this.cursorIndex = CURSOR_INDEX_INVALID;
		}
		this.onNodeRemoved(this.back);
	}

	public void delete() {
		this.onNodeRemoved(this.cursor);
	}

	public List copy() {
		List newList = new List();
		Node<Integer> thisListIterator = this.getFrontNode();
		while (this.isNodeDefined(thisListIterator)) {
			newList.append(thisListIterator.get().intValue());
			thisListIterator = thisListIterator.getNext();
		}
		return newList;
	}

	public List concat(List passedList) {
		List newList = new List();
		Node<Integer> thisListIterator = this.getFrontNode();
		while (this.isNodeDefined(thisListIterator)) {
			newList.append(thisListIterator.get().intValue());
			thisListIterator = thisListIterator.getNext();
		}
		return null;
	}

	@Override
	public String toString() {
		String stringRepresentation = "";
		Node<Integer> thisListIterator = this.getFrontNode();
		while (this.isNodeDefined(thisListIterator)) {
			stringRepresentation += thisListIterator.toString();
			thisListIterator = thisListIterator.getNext();
			if (thisListIterator != null) {
				stringRepresentation += " ";
			}
		}
		return stringRepresentation;
	}

	protected Node<Integer> getFrontNode() {
		return this.front;
	}

	protected Node<Integer> getBackNode() {
		return this.back;
	}

	protected Node<Integer> getCursorNode() {
		return this.cursor;
	}

	protected boolean isEmpty() {
		return (this.length < 1);
	}

	protected boolean isNodeDefined(Node<?> passedNode) {
		return (passedNode != null) && (passedNode.get() != null) && (passedNode.getOwner() == this);
	}

	private Node<Integer> newNode(int passedValue) {
		Node<Integer> newNode = new Node<Integer>(this, new Integer(passedValue));
		return newNode;
	}
	
	private void onNodeAdded(Node<Integer> passedNode) {
		if (this.isEmpty()) {
			this.front = passedNode;
			this.back = passedNode;
		}
		this.length += 1;
	}

	private void onNodeRemoved(Node<Integer> passedNode) {
		if (this.isNodeDefined(passedNode)) {
			Node<Integer> nextNode = passedNode.getNext();
			Node<Integer> previousNode = passedNode.getPrevious();
			if (this.isNodeDefined(nextNode)) {
				nextNode.setPrevious(passedNode.getPrevious());
			}
			if (this.isNodeDefined(previousNode)) {
				previousNode.setNext(passedNode.getNext());
			}
			passedNode.reset();
			this.length -= 1;
		}
	}

	/* Node Implementation */

	private class Node<T> {
		private final List owningList;
		private T element;
		private Node<T> previous;
		private Node<T> next;

		public Node(List passedOwningList, T passedElement) {
			this.owningList = passedOwningList;
			this.element = passedElement;
		}

		public T get() {
			return this.element;
		}

		protected List getOwner() {
			return this.owningList;
		}

		protected void reset() {
			this.element = null;
			this.previous = null;
			this.next = null;
		}

		protected Node<T> getNext() {
			return this.next;
		}

		protected Node<T> getPrevious() {
			return this.previous;
		}

		protected void setNext(Node<T> passedNode) {
			this.next = passedNode;
		}

		protected void setPrevious(Node<T> passedNode) {
			this.previous = passedNode;
		}
		
		@Override
		public boolean equals(Object passedObject) {
			if (passedObject instanceof Node<?>) {
				return this.get().equals(((Node<?>)passedObject).get());
			}
			return super.equals(passedObject);
		}

		@Override
		public String toString() {
			return this.element.toString();
		}
	}

}
