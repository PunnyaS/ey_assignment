package problem1And2;

//This has the solution for Assignment1 problem 1
public class threadImpl {

	public static void main(String[] args) throws InterruptedException {
		
		sampleQueue queue = new sampleQueue(10000);
		Thread worker = new Thread(new workerThreadImpl(queue));
		long mainStartTime = System.currentTimeMillis();
		worker.start();

		// main thread
		for (int i = 0; i <= 1000; i++) {
			if (i % 100 == 0) {
				queue.enqueue(i);
			}
		}
		long mainEndTime = System.currentTimeMillis();
		System.out.println("total time by main thread " + (mainEndTime - mainStartTime)+ "ms");

		// to exit the worker thread
		queue.enqueue(-1);
		//wait for the worker thread to exit
		worker.join();
		long workerEndTime = System.currentTimeMillis();
		System.out.println("total time by worker thread " + (workerEndTime - mainStartTime) + "ms");

	}

}

