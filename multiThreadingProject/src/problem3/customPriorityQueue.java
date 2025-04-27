package problem3;

import java.util.LinkedList;

public class customPriorityQueue {

	private LinkedList<taskImpl> list;
	private int capacity;

	public customPriorityQueue(int capacity) {
		this.list = new LinkedList<taskImpl>();
		this.capacity = capacity;
	}

	public synchronized void enqueue(taskImpl task) throws InterruptedException {
		while (list.size() == capacity) {
			wait();
		}
		list.add(task);
		list.sort(null);
		notifyAll();
	}

	public synchronized taskImpl dequeue() throws InterruptedException {
		while (list.isEmpty()) {
			wait();
		}
		taskImpl task = list.removeFirst();
		notifyAll();
		return task;

	}

}
