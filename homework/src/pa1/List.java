package pa1;

import pa1.library.IIntListADT;

public class List implements IIntListADT {
	
	private Node<Integer> cursor;
	private Node<Integer> front;
	private Node<Integer> back;
	private int cursorIndex;
	
	private int length;
	
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
				// TODO: Compare
			}
		}
		return false;
	}

	@Override
	public void clear() {
		if (!this.isEmpty()) {
			
		}
	}

	@Override
	public void moveFront() {
		while (this.cursor.getPrevious() != null) {
			this.cursor = this.cursor.getPrevious();
		}
	}

	@Override
	public void moveBack() {
		while (this.cursor.getNext() != null) {
			this.cursor = this.cursor.getNext();
		}
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
		// TODO Auto-generated method stub
		this.length += 1;
		this.cursorIndex += 1;
	}

	@Override
	public void append(int passedData) {
		// TODO Auto-generated method stub
		this.length += 1;
	}

	@Override
	public void insertBefore(int passedData) {
		Node<Integer> newNode = this.newNode(passedData);
		// TODO Auto-generated method stub
		this.length += 1;
		this.cursorIndex += 1;
	}

	@Override
	public void insertAfter(int passedData) {
		Node<Integer> newNode = this.newNode(passedData);
		// TODO Auto-generated method stub
		this.length += 1;
	}

	@Override
	public void deleteFront() {
		if (this.cursor.equals(this.front)) {
			this.cursor = this.front.getNext();
		}
		// TODO Auto-generated method stub
		this.length -= 1;
	}

	@Override
	public void deleteBack() {
		if (this.cursor.equals(this.back)) {
			this.cursor = this.back.getPrevious();
			this.cursorIndex -= 1;
		}
		// TODO Auto-generated method stub
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
	
	/* Internal Methods */
	public boolean isEmpty() {
		return (this.length > 0);
	}
	
	public static boolean isNodeDefined(Node<?> passedNode) {
		return (passedNode != null) && (passedNode.get() != null);
	}
	
	private Node<Integer> newNode(int passedValue) {
		Node<Integer> newNode = new Node<Integer>(new Integer(passedValue));
		return newNode;
	}
	
	/* Node Implementation */
	
	private class Node<T> {
		private T element;
		private Node<T> previous;
		private Node<T> next;
		
		public Node(T passedElement) {
			this.element = passedElement;
		}
		
		public T get() {
			return this.element;
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
