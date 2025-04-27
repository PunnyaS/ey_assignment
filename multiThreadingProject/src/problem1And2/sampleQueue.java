package problem1And2;

//using linkedlist
public class sampleQueue {

	Node front, rear;
	int length;
	private final int capacity;

	public sampleQueue(int capacity) {
		super();
		this.capacity = capacity;
		this.front = null;
		this.rear = null;
		this.length = 0;
	}

	public static class Node {
		int data;
		private Node next;

		public Node(int data) {
			this.next = null;
			this.data = data;
		}
	}

	public synchronized void enqueue(int data) throws InterruptedException {
		
		//waits if the queue is full
		while(length == capacity) {
			wait();
		}
		Node newNode = new Node(data);
		if (rear == null) {
			front = rear = newNode;
		} else {
			rear.next = newNode;
			rear = newNode;
		}
		
		length++;
		notifyAll();
	}

	public synchronized int dequeue() throws InterruptedException{

		if (isEmpty()) {
			wait();
		}
		int data = front.data;
		front = front.next;
		if (front == null) {
			rear = null;
		}
		length--;
		notifyAll();
		return data;
	}
	
	public synchronized void print() {

		if (isEmpty()) {
			throw new IllegalStateException("Queue is empty");
		}
		Node current = front;
		while(current != null) {
			System.out.print(current.data + " ");
			current = current.next;
		}
		System.out.println();
		
	}

	public boolean isEmpty() {
		return (front == null);
	}

	public int size() {
		return length;
	}

	
}

