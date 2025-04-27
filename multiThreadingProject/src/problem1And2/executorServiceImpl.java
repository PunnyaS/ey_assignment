package problem1And2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

//This has the solution for Assignment1 problem 2
public class executorServiceImpl {

	public static void main(String[] args) throws InterruptedException {

		//n=40, is the ideal thread count
		//attaching the total time vs thread size list in a txt file
		final int MAX_SIZE = 40;
		ExecutorService executor = Executors.newFixedThreadPool(MAX_SIZE);
		sampleQueue queue = new sampleQueue(1000);
		long startTime = System.currentTimeMillis();
		for (int i = 0; i < MAX_SIZE; i++) {
			executor.submit(new workerThreadImpl(queue));
		}

		for (int j = 0; j <= 10000; j++) {
			if (j % 100 == 0) {
				queue.enqueue(j);
			}
		}
		long mainEndTime = System.currentTimeMillis();
		System.out.println("Main thread finished in " + (mainEndTime - startTime) + " ms");

		// exit condition
		for (int k = 0; k < MAX_SIZE; k++) {
			queue.enqueue(-1);
		}
		executor.shutdown();
		executor.awaitTermination(10, TimeUnit.MINUTES);
		long workerEndTime = System.currentTimeMillis();
		System.out.println("Workers finished in " + (workerEndTime - startTime) + " ms");

	}

}
