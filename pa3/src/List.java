/*********************************************************************
 * Malcolm Riley
 *
 * CruzID: masriley
 * Assignment: pa3
 *
 * 10-2017
 *********************************************************************/

public class List<K> {

	private static final int CURSOR_INDEX_INVALID = -1;

	private Node<K> cursor;
	private Node<K> front;
	private Node<K> back;
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

	public K front() {
		return this.front.get();
	}

	public K back() {
		return this.back.get();
	}

	public K get() {
		return this.cursor.get();
	}
	
	/**
	 * Overrides {@link Object#equals(Object)}.
	 */
	public boolean equals(Object passedList) {
		if (passedList instanceof List<?>) {
			return this.equals(passedList);
		}
		return false;
	}

	/**
	 * Determines whether this {@link List} is equivalent to the passed {@link List}; that is, whether it contains equivalent values in the same order.
	 * 
	 * @param passedList - The list to compare this one to
	 * @return Whether or not the two lists are equivalent.
	 */
	public boolean equals(List<?> passedList) {
		if (this.length() == passedList.length()) {
			if (!this.isEmpty()) {
				List<K>.Node<K> thisListIterator = this.getFrontNode();
				List<?>.Node<?> passedListIterator = passedList.getFrontNode();
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
		while (!this.isEmpty()) {
			this.deleteBack();
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
		if (this.setAndCheckCursor(this.cursor.getPrevious())) {
			this.cursorIndex -= 1;
			if (this.cursorIndex < 0) {
				this.cursorIndex = CURSOR_INDEX_INVALID;
			}
		}
	}

	/**
	 * Moves the cursor to the position one next from its current position in this {@link List}.
	 */
	public void moveNext() {
		if (this.setAndCheckCursor(this.cursor.getNext())) {
			this.cursorIndex += 1;
			if (this.cursorIndex > (this.length - 1)) {
				this.cursorIndex = CURSOR_INDEX_INVALID;
			}
		}
	}

	public void prepend(K passedData) {
		Node<K> newNode = new Node<K>(this, passedData);
		if(this.insertNodeBefore(this.front, newNode)) {
			this.cursorIndex += 1;
		}
		this.front = newNode;
	}

	public void append(K passedData) {
		Node<K> newNode = new Node<K>(this, passedData);
		this.insertNodeAfter(this.back, newNode);
		this.back = newNode;
	}

	public void insertBefore(K passedData) {
		Node<K> newNode = new Node<K>(this, passedData);
		if (this.insertNodeBefore(this.cursor, newNode)) {
			this.cursorIndex += 1;
		}
	}

	public void insertAfter(K passedData) {
		Node<K> newNode = new Node<K>(this, passedData);
		this.insertNodeAfter(this.cursor, newNode);
	}

	public void deleteFront() {
		if (this.cursor == this.front) {
			this.cursorIndex = CURSOR_INDEX_INVALID;
		}
		else {
			this.cursorIndex -= 1;
		}
		this.onNodeRemoved(this.front);
	}

	public void deleteBack() {
		if (this.cursor == this.back) {
			this.cursorIndex = CURSOR_INDEX_INVALID;
		}
		this.onNodeRemoved(this.back);
	}

	public void delete() {
		this.onNodeRemoved(this.cursor);
	}

	public List<K> copy() {
		List<K> newList = new List<K>();
		Node<K> thisListIterator = this.getFrontNode();
		while (this.isNodeDefined(thisListIterator)) {
			newList.append(thisListIterator.get());
			thisListIterator = thisListIterator.getNext();
		}
		return newList;
	}

	public List<K> concat(List<K> passedList) {
		List<K> newList = new List<K>();
		Node<K> thisListIterator = this.getFrontNode();
		while (this.isNodeDefined(thisListIterator)) {
			newList.append(thisListIterator.get());
			thisListIterator = thisListIterator.getNext();
		}
		return null;
	}

	@Override
	public String toString() {
		String stringRepresentation = "";
		Node<K> thisListIterator = this.getFrontNode();
		while (this.isNodeDefined(thisListIterator)) {
			stringRepresentation += thisListIterator.toString();
			thisListIterator = thisListIterator.getNext();
			if (thisListIterator != null) {
				stringRepresentation += " ";
			}
		}
		return stringRepresentation;
	}

	protected Node<K> getFrontNode() {
		return this.front;
	}

	protected Node<K> getBackNode() {
		return this.back;
	}

	protected Node<K> getCursorNode() {
		return this.cursor;
	}

	protected boolean isEmpty() {
		return (this.length < 1);
	}

	protected boolean isNodeDefined(Node<?> passedNode) {
		return (passedNode != null) && (passedNode.get() != null) && (passedNode.getOwner() == this);
	}
	
	private boolean insertNodeBefore(Node<K> passedExistingNode, Node<K> passedInsertedNode) {
		this.onNodeAdded(passedInsertedNode);
		if (isNodeDefined(passedExistingNode)) {
			// Set existing links
			Node<K> previous = passedExistingNode.getPrevious();
			if (isNodeDefined(previous)) {
				previous.setNext(passedInsertedNode);
			}
			passedExistingNode.setPrevious(passedInsertedNode);
			
			// Set new links
			passedInsertedNode.setNext(passedExistingNode);
			passedInsertedNode.setPrevious(previous);

			// Set new front if applicable
			if (this.front == passedExistingNode) {
				this.front = passedInsertedNode;
			}
			return true;
		}
		return false;
	}

	private boolean insertNodeAfter(Node<K> passedExistingNode, Node<K> passedInsertedNode) {
		this.onNodeAdded(passedInsertedNode);
		if (isNodeDefined(passedExistingNode)) {
			// Set existing links
			Node<K> next = passedExistingNode.getNext();
			if (isNodeDefined(next)) {
				next.setPrevious(passedInsertedNode);
			}
			passedExistingNode.setNext(passedInsertedNode);
			// Set new links
			passedInsertedNode.setPrevious(passedExistingNode);
			passedInsertedNode.setNext(next);
			
			// Set new back if applicable
			if (this.back == passedExistingNode) {
				this.back = passedInsertedNode;
			}
			return true;
		}
		return false;
	}
	
	private void onNodeAdded(Node<K> passedNode) {
		if (this.isEmpty()) {
			this.front = passedNode;
			this.back = passedNode;
		}
		this.length += 1;
	}

	private void onNodeRemoved(Node<K> passedNode) {
		if (this.isNodeDefined(passedNode)) {
			Node<K> nextNode = passedNode.getNext();
			Node<K> previousNode = passedNode.getPrevious();
			if (this.isNodeDefined(nextNode)) {
				nextNode.setPrevious(passedNode.getPrevious());
				if (passedNode == this.front) {
					this.front = nextNode;
				}
			}
			if (this.isNodeDefined(previousNode)) {
				previousNode.setNext(passedNode.getNext());
				if (passedNode == this.back) {
					this.back = previousNode;
				}
			}
			passedNode.reset();
			passedNode = null;
			this.length -= 1;
		}
	}
	
	private boolean setAndCheckCursor(Node<K> passedNode) {
		this.cursor = passedNode;
		if (!isNodeDefined(this.cursor)) {
			this.cursorIndex = CURSOR_INDEX_INVALID;
			return false;
		}
		return true;
	}

	/* Node Implementation */

	private class Node<T> {
		private final List<T> owningList;
		private T element;
		private Node<T> previous;
		private Node<T> next;

		public Node(List<T> passedOwningList, T passedElement) {
			this.owningList = passedOwningList;
			this.element = passedElement;
		}

		public T get() {
			return this.element;
		}

		protected List<T> getOwner() {
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
			if (passedObject instanceof List<?>.Node<?>) {
				return ((List<?>.Node<?>)passedObject).get().equals(this.get());
			}
			return false;
		}

		@Override
		public String toString() {
			return this.element.toString();
		}
	}

}
