package test;

import java.lang.System.Logger;
import java.util.concurrent.*;

public class Main {
    private static final int MAX_COUNT = 10000;
    private static final BlockingQueue<Integer> queue = new LinkedBlockingQueue<>(5);
    private static final BlockingQueue<Double> responseQueue = new LinkedBlockingQueue<>();
   // private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        int numWorkers = 3; // Fixed to 3 workers

        long startTime = System.currentTimeMillis();

        // Create and start worker threads
        for (int i = 0; i < numWorkers; i++) {
            Thread workerThread = new Thread(new Worker(queue, responseQueue));
            workerThread.start();
        }

        // Main thread publishes tasks
        for (int i = 100; i <= MAX_COUNT; i += 100) {
            try {
                queue.put(i);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        // Main thread consumes responses
        double total = 0;
        for (int i = 0; i < MAX_COUNT / 100; i++) {
            try {
                total += responseQueue.take();
                System.out.println("from receiver "+ total);// Get the result from workers
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Main thread finished in " + (endTime - startTime) + "ms");
        System.out.println("Total sum of log values: " + total);
    }
}

class Worker implements Runnable {
    private final BlockingQueue<Integer> queue;
    private final BlockingQueue<Double> responseQueue;

    public Worker(BlockingQueue<Integer> queue, BlockingQueue<Double> responseQueue) {
        this.queue = queue;
        this.responseQueue = responseQueue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Integer count = queue.take();
                if (count == -1) { // Signal to stop worker
                    break;
                }

                // Perform task
                double logValue = Math.log(count);
                System.out.println("received "+count);
                responseQueue.put(logValue); // Send result to main thread
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
