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
		if (isNodeDefined(this.cursor)) {
			return cursorIndex;
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

	public boolean equals(List passedList) {
		if (this.length() == passedList.length()) {
			if (!this.isEmpty()) {
				Node<Integer> thisListIterator = this.getFrontNode();
				Node<Integer> passedListIterator = this.getFrontNode();
				while (this.isNodeDefined(thisListIterator)) {
					if (!thisListIterator.get().equals(passedListIterator.get())) {
						return false;
					}
					thisListIterator = thisListIterator.getNext();
					passedListIterator = passedListIterator.getNext();
				}
				return true;
			}
		}
		return false;
	}

	public void clear() {
		if (!this.isEmpty()) {
			Node<Integer> thisListIterator = this.getFrontNode();
			while (this.isNodeDefined(thisListIterator)) {
				thisListIterator = thisListIterator.getNext();
				thisListIterator.previous.reset();
			}
		}
		this.front = null;
		this.back = null;
		this.cursor = null;
		this.cursorIndex = CURSOR_INDEX_INVALID;
		this.length = 0;
	}

	public void moveFront() {
		if (isNodeDefined(this.front)) {
			this.cursor = this.front;
			this.cursorIndex = 0;
		}
	}

	public void moveBack() {
		if (isNodeDefined(this.back)) {
			this.cursor = this.back;
			this.cursorIndex = (this.length - 1);
		}
	}

	public void movePrev() {
		this.cursor = this.cursor.getPrevious();
		if (!isNodeDefined(this.cursor)) {
			this.cursorIndex = CURSOR_INDEX_INVALID;
		}
		else {
			this.cursorIndex -= 1;
		}
	}
	
	public void moveNext() {
		this.cursor = this.cursor.getNext();
		if (!isNodeDefined(this.cursor)) {
			this.cursorIndex = CURSOR_INDEX_INVALID;
		}
		else {
			this.cursorIndex += 1;
		}
	}

	public void prepend(int passedData) {
		Node<Integer> newNode = this.newNode(passedData);
		if (isNodeDefined(this.front)) {
			newNode.setNext(this.front);
			this.front.setPrevious(newNode);
		}
		this.front = newNode;
		this.length += 1;
		this.cursorIndex += 1;
	}

	public void append(int passedData) {
		Node<Integer> newNode = this.newNode(passedData);
		if (isNodeDefined(this.back)) {
			newNode.setPrevious(this.back);
			this.back.setNext(newNode);
		}
		this.back = newNode;
		this.length += 1;
	}

	public void insertBefore(int passedData) {
		Node<Integer> newNode = this.newNode(passedData);
		newNode.setNext(this.cursor);
		newNode.setPrevious(this.cursor.getPrevious());
		this.cursor.setPrevious(newNode);
		this.length += 1;
		this.cursorIndex += 1;
	}

	public void insertAfter(int passedData) {
		Node<Integer> newNode = this.newNode(passedData);
		newNode.setNext(this.cursor.getNext());
		newNode.setPrevious(this.cursor);
		this.cursor.setNext(newNode);
		this.length += 1;
	}
	
	public void deleteFront() {
		if (this.cursor.equals(this.front)) {
			this.cursorIndex = CURSOR_INDEX_INVALID;
		}
		this.removeNode(this.front);
	}

	public void deleteBack() {
		if (this.cursor.equals(this.back)) {
			this.cursorIndex = CURSOR_INDEX_INVALID;
		}
		this.removeNode(this.back);
	}

	public void delete() {
		this.removeNode(this.cursor);
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
	
	public String toString() {
		Node<Integer> thisListIterator = this.getFrontNode();
		String stringRepresentation = "";
		while (this.isNodeDefined(thisListIterator)) {
			stringRepresentation += thisListIterator.toString();
			thisListIterator = thisListIterator.getNext();
			if (thisListIterator != null) {
				stringRepresentation += " ";
			}
		}
		return stringRepresentation;
	}
	
	public Node<Integer> getFrontNode() {
		return this.front;
	}
	
	public Node<Integer> getBackNode() {
		return this.back;
	}
	
	public Node<Integer> getCursorNode() {
		return this.cursor;
	}
	
	public boolean isEmpty() {
		return (this.length > 0);
	}
	
	public boolean isNodeDefined(Node<?> passedNode) {
		return (passedNode != null) && (passedNode.get() != null) && (passedNode.getOwner().equals(this));
	}
	
	private Node<Integer> newNode(int passedValue) {
		Node<Integer> newNode = new Node<Integer>(this, new Integer(passedValue));
		return newNode;
	}
	
	private void removeNode(Node<Integer> passedNode) {
		if (isNodeDefined(passedNode)) {
			Node<Integer> nextNode = passedNode.getNext();
			Node<Integer> previousNode = passedNode.getPrevious();
			if (isNodeDefined(nextNode)) {
				nextNode.setPrevious(passedNode.getPrevious());
			}
			if (isNodeDefined(previousNode)) {
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
		
		public List getOwner() {
			return this.owningList;
		}
		
		public void reset() {
			this.element = null;
			this.previous = null;
			this.next = null;
		}
		
		public Node<T> getNext() {
			return this.next;
		}
		
		public Node<T> getPrevious() {
			return this.previous;
		}
		
		public void setNext(Node<T> passedNode) {
			this.next = passedNode;
		}
		
		public void setPrevious(Node<T> passedNode) {
			this.previous = passedNode;
		}
		
		public String toString() {
			return this.element.toString();
		}
	}

}
