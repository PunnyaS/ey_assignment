package problem4;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class executoreServiceForBQueue {

	public static void main(String[] args) throws InterruptedException {
		final int MAX_SIZE = 3;
		final int Q_CAPACITY = 5;
		BlockingQueue<Task> bQueue = new ArrayBlockingQueue(Q_CAPACITY);
		ExecutorService executor = Executors.newFixedThreadPool(MAX_SIZE);
		
		for (int i = 0; i < MAX_SIZE; i++) {
			executor.submit(new workerThreadImpl(bQueue));
		}
		long startTime = System.currentTimeMillis();
		for(int k=0;k<=10000;k++) {
			if (k % 100 == 0) {
			bQueue.put(new Task(k));
			}
		}
		long mainEndTime = System.currentTimeMillis();
		System.out.println("Main thread completed in " + (mainEndTime-startTime) + " ms");
		
		//exit condition
		for (int i = 0; i < MAX_SIZE; i++) {
			bQueue.put(new Task(-1));
		}
		
		executor.shutdown();
		executor.awaitTermination(1, TimeUnit.MINUTES);
		long workerEndTime = System.currentTimeMillis();
		System.out.println("Worker thread completed in " + (workerEndTime-startTime) + " ms");
		
	}

	

}

