package problem5;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class executorServiceImpl {

	public static void main(String[] args) throws InterruptedException {
		final int MAX_SIZE = 3;
		final int Q_CAPACITY = 5;
		BlockingQueue<Task> bQueue = new ArrayBlockingQueue(Q_CAPACITY);
		responseQueue rQueue = new responseQueue();
		double logTotal = 0;
		ExecutorService executor = Executors.newFixedThreadPool(MAX_SIZE);
		for (int i = 0; i < MAX_SIZE; i++) {
			executor.submit(new workerThreadImpl(bQueue, rQueue));
		}
		long startTime = System.currentTimeMillis();
		for (int j = 100; j <= 10000; j += 100) {

			bQueue.put(new Task(j));

		}
		long mainEndTime = System.currentTimeMillis();
		System.out.println("Main thread stopped at   " + (mainEndTime - startTime) + " ms");
		// exit condition
		for (int i = 0; i < MAX_SIZE; i++) {
			bQueue.put(new Task(-1));
		}
		System.out.println("get from rqueue");
		for (int k = 0; k < 101; k++) {
			Double value = rQueue.get();
			if (!(value == null)) {
				logTotal += value;
			}

			System.out.println("logtotal is " + logTotal);

		}

		executor.shutdown();
		executor.awaitTermination(1, TimeUnit.MINUTES);
		long totalTime = System.currentTimeMillis();
		System.out.println("Total time  " + (totalTime - startTime) + " ms");
		System.out.println("Log total is " + logTotal);
	}

}
