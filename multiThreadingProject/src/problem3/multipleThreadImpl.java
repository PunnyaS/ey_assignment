package problem3;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class multipleThreadImpl {

	public static void main(String[] args) throws InterruptedException {
		
		customPriorityQueue pqueue = new customPriorityQueue(200);
		ExecutorService executor = Executors.newFixedThreadPool(5);
		long startTime = System.currentTimeMillis();
		//worker threads
		for(int i=0;i<5;i++) {
			executor.submit(new workerThreadImpl(pqueue));
		}
		
		// main thread adds task with priority
				for (int i = 0; i <= 10000; i++) {
					if (i % 100 == 0) {
						pqueue.enqueue(new taskImpl(i,i));
					}
				}
				long mainEndTime = System.currentTimeMillis();
				System.out.println("total time by main thread " + (mainEndTime - startTime)+ "ms");
				
				//exit condition
				for(int i=0;i<5;i++) {
					pqueue.enqueue(new taskImpl(-1,-1));
				}
				executor.shutdown();
				executor.awaitTermination(5, TimeUnit.MINUTES);
				long endTime = System.currentTimeMillis();
				System.out.println("time taken for high priority task is "+ (workerThreadImpl.getHighPriorityTime()-startTime) + " ms");
				System.out.println("time taken for  low priority task is "+ (workerThreadImpl.getLowPriorityTime()-startTime) + " ms");
				System.out.println("Total time is "+ (endTime- startTime) + " ms");
	
	 
	}

}
