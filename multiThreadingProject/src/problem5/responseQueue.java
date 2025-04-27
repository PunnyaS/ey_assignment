package problem5;

import java.util.LinkedList;
import java.util.Queue;

public class responseQueue {
	
	private Queue<Double> response ;

	public responseQueue() {
		super();
		this.response =  new LinkedList<>();
	}
	
	public synchronized void add(double value) {
		
		
		response.offer(value);
		notifyAll();
		
		
	}
 synchronized Double get() throws InterruptedException {
		/*
		 * while(response.isEmpty()) { wait(); }
		 */
		
	Double data = response.poll();
	notifyAll();
	return data;
		
		
	}

}
