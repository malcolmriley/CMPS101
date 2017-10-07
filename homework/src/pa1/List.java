package pa1;

public class List {
	
	public List() { 
		
	}
	
	private class Node<T> {
		private final T element;
		private Node<T> previous;
		private Node<T> next;
		
		public Node(T passedElement) {
			this.element = passedElement;
		}
		
		public T get() {
			return this.element;
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
