import java.util.Objects;

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

	private Node<K> NODE_CURSOR;
	private Node<K> NODE_FRONT;
	private Node<K> NODE_BACK;
	private int INDEX = CURSOR_INDEX_INVALID;
	private int LENGTH;

	public List() {

	}

	/* Required Methods */

	public int length() {
		return this.LENGTH;
	}

	public int index() {
		if (this.isNodeDefined(this.NODE_CURSOR)) {
			return this.INDEX;
		}
		return -1;
	}

	public K front() {
		return this.NODE_FRONT.get();
	}

	public K back() {
		return this.NODE_BACK.get();
	}

	public K get() {
		return this.NODE_CURSOR.get();
	}
	
	/**
	 * Overrides {@link Object#equals(Object)}.
	 */
	public boolean equals(Object passedList) {
		if (passedList instanceof List<?>) {
			return Objects.equals(this, passedList);
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
					if (!Objects.equals(thisListIterator, passedListIterator)) {
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
		this.NODE_FRONT = null;
		this.NODE_BACK = null;
		this.NODE_CURSOR = null;
		this.INDEX = CURSOR_INDEX_INVALID;
		this.LENGTH = 0;
	}

	/**
	 * Moves the cursor to the front of the {@link List}.
	 */
	public void moveFront() {
		if (this.isNodeDefined(this.NODE_FRONT)) {
			this.NODE_CURSOR = this.NODE_FRONT;
			this.INDEX = 0;
		}
	}

	/**
	 * Moves the cursor to the back of the {@link List}.
	 */
	public void moveBack() {
		if (this.isNodeDefined(this.NODE_BACK)) {
			this.NODE_CURSOR = this.NODE_BACK;
			this.INDEX = (this.LENGTH - 1);
		}
	}

	/**
	 * Moves the cursor to the position one previous from its current position in this {@link List}.
	 */
	public void movePrev() {
		if (this.setAndCheckCursor(this.NODE_CURSOR.getPrevious())) {
			this.INDEX -= 1;
			if (this.INDEX < 0) {
				this.INDEX = CURSOR_INDEX_INVALID;
			}
		}
	}

	/**
	 * Moves the cursor to the position one next from its current position in this {@link List}.
	 */
	public void moveNext() {
		if (this.setAndCheckCursor(this.NODE_CURSOR.getNext())) {
			this.INDEX += 1;
			if (this.INDEX > (this.LENGTH - 1)) {
				this.INDEX = CURSOR_INDEX_INVALID;
			}
		}
	}

	public void prepend(K passedData) {
		Node<K> newNode = new Node<K>(this, passedData);
		if(this.insertNodeBefore(this.NODE_FRONT, newNode)) {
			this.INDEX += 1;
		}
		this.NODE_FRONT = newNode;
	}

	public void append(K passedData) {
		Node<K> newNode = new Node<K>(this, passedData);
		this.insertNodeAfter(this.NODE_BACK, newNode);
		this.NODE_BACK = newNode;
	}

	public void insertBefore(K passedData) {
		Node<K> newNode = new Node<K>(this, passedData);
		if (this.insertNodeBefore(this.NODE_CURSOR, newNode)) {
			this.INDEX += 1;
		}
	}

	public void insertAfter(K passedData) {
		Node<K> newNode = new Node<K>(this, passedData);
		this.insertNodeAfter(this.NODE_CURSOR, newNode);
	}

	public void deleteFront() {
		if (this.NODE_CURSOR == this.NODE_FRONT) {
			this.INDEX = CURSOR_INDEX_INVALID;
		}
		else {
			this.INDEX -= 1;
		}
		this.onNodeRemoved(this.NODE_FRONT);
	}

	public void deleteBack() {
		if (this.NODE_CURSOR == this.NODE_BACK) {
			this.INDEX = CURSOR_INDEX_INVALID;
		}
		this.onNodeRemoved(this.NODE_BACK);
	}

	public void delete() {
		this.onNodeRemoved(this.NODE_CURSOR);
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
		return this.NODE_FRONT;
	}

	protected Node<K> getBackNode() {
		return this.NODE_BACK;
	}

	protected Node<K> getCursorNode() {
		return this.NODE_CURSOR;
	}

	protected boolean isEmpty() {
		return (this.LENGTH < 1);
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
			if (this.NODE_FRONT == passedExistingNode) {
				this.NODE_FRONT = passedInsertedNode;
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
			if (this.NODE_BACK == passedExistingNode) {
				this.NODE_BACK = passedInsertedNode;
			}
			return true;
		}
		return false;
	}
	
	private void onNodeAdded(Node<K> passedNode) {
		if (this.isEmpty()) {
			this.NODE_FRONT = passedNode;
			this.NODE_BACK = passedNode;
		}
		this.LENGTH += 1;
	}

	private void onNodeRemoved(Node<K> passedNode) {
		if (this.isNodeDefined(passedNode)) {
			Node<K> nextNode = passedNode.getNext();
			Node<K> previousNode = passedNode.getPrevious();
			if (this.isNodeDefined(nextNode)) {
				nextNode.setPrevious(passedNode.getPrevious());
				if (passedNode == this.NODE_FRONT) {
					this.NODE_FRONT = nextNode;
				}
			}
			if (this.isNodeDefined(previousNode)) {
				previousNode.setNext(passedNode.getNext());
				if (passedNode == this.NODE_BACK) {
					this.NODE_BACK = previousNode;
				}
			}
			passedNode.reset();
			passedNode = null;
			this.LENGTH -= 1;
		}
	}
	
	private boolean setAndCheckCursor(Node<K> passedNode) {
		this.NODE_CURSOR = passedNode;
		if (!isNodeDefined(this.NODE_CURSOR)) {
			this.INDEX = CURSOR_INDEX_INVALID;
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
				Object thisValue = this.get();
				Object passedValue = ((List<?>.Node<?>) passedObject).get();
				return Objects.equals(thisValue, passedValue);
			}
			return false;
		}

		@Override
		public String toString() {
			return this.element.toString();
		}
	}

}
