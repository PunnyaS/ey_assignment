package exampleDAG;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class blockingQueueImpl<T> {
	
	private final List<T> queue = new ArrayList();
	private  int capacity;
	public blockingQueueImpl(int capacity) {
		super();
		capacity = capacity;
	}
	
	synchronized T dequeue() throws InterruptedException {
		while(queue.isEmpty()) {
			wait();
		}
		T data = queue.remove(0);
		notifyAll();
		return data;
	}
	
	synchronized void enqueue(T data) throws InterruptedException {
		while(queue.size() == capacity) {
			wait();
		}
		queue.add(data);
		notifyAll();
	}
	
	

}
