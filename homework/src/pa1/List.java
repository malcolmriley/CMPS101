package pa1;

import pa1.library.IIntListADT;

public class List implements IIntListADT {
	
	private Node<Integer> cursor;
	private Node<Integer> front;
	private Node<Integer> back;
	private int cursorIndex;
	private int length;
	
	private static final int CURSOR_INDEX_INVALID = -1;
	
	public List() { 
		
	}
	
	/* Required Methods */

	@Override
	public int length() {
		return this.length;
	}

	@Override
	public int index() {
		if (isNodeDefined(this.cursor)) {
			return cursorIndex;
		}
		return -1;
	}

	@Override
	public int front() {
		return this.front.get().intValue();
	}

	@Override
	public int back() {
		return this.back.get().intValue();
	}

	@Override
	public int get() {
		return this.cursor.get().intValue();
	}

	@Override
	public boolean equals(IIntListADT passedList) {
		if (this.length() == passedList.length()) {
			if (!this.isEmpty()) {
				Node<Integer> thisListIterator = this.getFrontNode();
				Node<Integer> passedListIterator = this.getFrontNode();
				while (thisListIterator.getNext() != null) {
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

	@Override
	public void clear() {
		if (!this.isEmpty()) {
			Node<Integer> thisListIterator = this.getFrontNode();
			while (thisListIterator.getNext() != null) {
				thisListIterator = thisListIterator.getNext();
				thisListIterator.previous.reset();
			}
			thisListIterator.reset();
		}
		this.front = null;
		this.back = null;
		this.cursor = null;
		this.cursorIndex = CURSOR_INDEX_INVALID;
		this.length = 0;
	}

	@Override
	public void moveFront() {
		this.cursor = this.front;
		this.cursorIndex = 0;
	}

	@Override
	public void moveBack() {
		this.cursor = this.back;
		this.cursorIndex = (this.length - 1);
	}

	@Override
	public void movePrev() {
		this.cursor = this.cursor.getPrevious();
		this.cursorIndex -= 1;
	}

	@Override
	public void moveNext() {
		this.cursor = this.cursor.getNext();
		this.cursorIndex += 1;
	}

	@Override
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

	@Override
	public void append(int passedData) {
		Node<Integer> newNode = this.newNode(passedData);
		if (isNodeDefined(this.back)) {
			newNode.setPrevious(this.back);
			this.back.setNext(newNode);
		}
		this.back = newNode;
		this.length += 1;
	}

	@Override
	public void insertBefore(int passedData) {
		Node<Integer> newNode = this.newNode(passedData);
		newNode.setNext(this.cursor);
		newNode.setPrevious(this.cursor.getPrevious());
		this.cursor.setPrevious(newNode);
		this.length += 1;
		this.cursorIndex += 1;
	}

	@Override
	public void insertAfter(int passedData) {
		Node<Integer> newNode = this.newNode(passedData);
		newNode.setNext(this.cursor.getNext());
		newNode.setPrevious(this.cursor);
		this.cursor.setNext(newNode);
		this.length += 1;
	}

	@Override
	public void deleteFront() {
		if (this.cursor.equals(this.front)) {
			this.cursorIndex = CURSOR_INDEX_INVALID;
		}
		Node<Integer> second = this.front.getNext();
		if (isNodeDefined(second)) {
			second.setPrevious(null);
		}
		this.front.reset();
		this.front = second;
		this.length -= 1;
	}

	@Override
	public void deleteBack() {
		if (this.cursor.equals(this.back)) {
			this.cursorIndex = CURSOR_INDEX_INVALID;
		}
		Node<Integer> secondLast = this.back.getPrevious();
		if (isNodeDefined(secondLast)) {
			secondLast.setNext(null);
		}
		this.back.reset();
		this.back = secondLast;
		this.length -= 1;
	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub
		this.length -= 1;
	}

	@Override
	public IIntListADT copy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IIntListADT concat(IIntListADT passedList) {
		// TODO Auto-generated method stub
		return null;
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
		
		public void setValue(T passedValue) {
			this.element = passedValue;
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
