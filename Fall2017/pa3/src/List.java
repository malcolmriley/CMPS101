/*********************************************************************
 * Malcolm Riley
 *
 * CruzID: masriley
 * Assignment: pa3
 *
 * 10-2017
 *********************************************************************/
import java.util.Objects;

public class List {

	private static final int CURSOR_INDEX_INVALID = -1;

	private Node<Object> NODE_CURSOR;
	private Node<Object> NODE_FRONT;
	private Node<Object> NODE_BACK;
	private int INDEX = CURSOR_INDEX_INVALID;
	private int LENGTH;

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

	public Object front() {
		if (this.NODE_FRONT != null) {
			return this.NODE_FRONT.get();
		}
		return null;
	}

	public Object back() {
		if (this.NODE_BACK != null) {
			return this.NODE_BACK.get();
		}
		return null;
	}

	public Object get() {
		if (this.NODE_CURSOR != null) {
			return this.NODE_CURSOR.get();
		}
		return null;
	}
	
	/**
	 * Overrides {@link Object#equals(Object)}.
	 */
	public boolean equals(Object passedObject) {
		if (this == passedObject) {
			return true;
		}
		if (passedObject instanceof List) {
			List passedList = (List)passedObject;
			if (this.length() == passedList.length()) {
				// Abuse toString method to test equality
				return this.toString().equals(passedList.toString());
			}
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
		if (this.NODE_CURSOR != null) {
			if (this.setAndCheckCursor(this.NODE_CURSOR.getPrevious())) {
				this.INDEX -= 1;
				if (this.INDEX < 0) {
					this.INDEX = CURSOR_INDEX_INVALID;
				}
			}
		}
	}

	/**
	 * Moves the cursor to the position one next from its current position in this {@link List}.
	 */
	public void moveNext() {
		if (this.NODE_CURSOR != null) {
			if (this.setAndCheckCursor(this.NODE_CURSOR.getNext())) {
				this.INDEX += 1;
				if (this.INDEX > (this.LENGTH - 1)) {
					this.INDEX = CURSOR_INDEX_INVALID;
				}
			}
		}
	}

	public void prepend(Object passedData) {
		Node<Object> newNode = new Node<Object>(this, passedData);
		if(this.insertNodeBefore(this.NODE_FRONT, newNode)) {
			this.INDEX += 1;
		}
		this.NODE_FRONT = newNode;
	}

	public void append(Object passedData) {
		Node<Object> newNode = new Node<Object>(this, passedData);
		this.insertNodeAfter(this.NODE_BACK, newNode);
		this.NODE_BACK = newNode;
	}

	public void insertBefore(Object passedData) {
		Node<Object> newNode = new Node<Object>(this, passedData);
		if (this.insertNodeBefore(this.NODE_CURSOR, newNode)) {
			this.INDEX += 1;
		}
	}

	public void insertAfter(Object passedData) {
		Node<Object> newNode = new Node<Object>(this, passedData);
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

	@Override
	public String toString() {
		StringBuilder representation = new StringBuilder();
		Node<Object> thisListIterator = this.getFrontNode();
		while (this.isNodeDefined(thisListIterator)) {
			representation.append(thisListIterator.toString());
			thisListIterator = thisListIterator.getNext();
			if (thisListIterator != null) {
				representation.append(" ");
			}
		}
		return representation.toString();
	}

	protected Node<Object> getFrontNode() {
		return this.NODE_FRONT;
	}

	protected Node<Object> getBackNode() {
		return this.NODE_BACK;
	}

	protected Node<Object> getCursorNode() {
		return this.NODE_CURSOR;
	}

	protected boolean isEmpty() {
		return (this.LENGTH < 1);
	}

	protected boolean isNodeDefined(Node<Object> passedNode) {
		return (passedNode != null) && (passedNode.get() != null) && (passedNode.getOwner() == this);
	}
	
	private boolean insertNodeBefore(Node<Object> passedExistingNode, Node<Object> passedInsertedNode) {
		this.onNodeAdded(passedInsertedNode);
		if (isNodeDefined(passedExistingNode)) {
			// Set existing links
			Node<Object> previous = passedExistingNode.getPrevious();
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

	private boolean insertNodeAfter(Node<Object> passedExistingNode, Node<Object> passedInsertedNode) {
		this.onNodeAdded(passedInsertedNode);
		if (isNodeDefined(passedExistingNode)) {
			// Set existing links
			Node<Object> next = passedExistingNode.getNext();
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
	
	private void onNodeAdded(Node<Object> passedNode) {
		if (this.isEmpty()) {
			this.NODE_FRONT = passedNode;
			this.NODE_BACK = passedNode;
		}
		this.LENGTH += 1;
	}

	private void onNodeRemoved(Node<Object> passedNode) {
		if (this.isNodeDefined(passedNode)) {
			Node<Object> nextNode = passedNode.getNext();
			Node<Object> previousNode = passedNode.getPrevious();
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
	
	private boolean setAndCheckCursor(Node<Object> passedNode) {
		this.NODE_CURSOR = passedNode;
		if (!isNodeDefined(this.NODE_CURSOR)) {
			this.INDEX = CURSOR_INDEX_INVALID;
			return false;
		}
		return true;
	}

	/* Node Implementation */

	private class Node<K> {
		private final List owningList;
		private K element;
		private Node<K> previous;
		private Node<K> next;

		public Node(List passedOwningList, K passedElement) {
			this.owningList = passedOwningList;
			this.element = passedElement;
		}

		public K get() {
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

		protected Node<K> getNext() {
			return this.next;
		}

		protected Node<K> getPrevious() {
			return this.previous;
		}

		protected void setNext(Node<K> passedNode) {
			this.next = passedNode;
		}

		protected void setPrevious(Node<K> passedNode) {
			this.previous = passedNode;
		}
		
		@Override
		public boolean equals(Object passedObject) {
			if (passedObject instanceof Node<?>) {
				Object thisValue = this.get();
				Object passedValue = ((Node<?>) passedObject).get();
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
