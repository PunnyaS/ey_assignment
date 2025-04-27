package part2;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class mainImpl {

	public static void main(String[] args) throws InterruptedException {

		BlockingQueue<request> reqQueue = new LinkedBlockingQueue<>();
		BlockingQueue<response> resQueue = new LinkedBlockingQueue<>();
		BlockingQueue<Double> resultQueue = new LinkedBlockingQueue<>();
		final int WORKERS = 4;

		String expression = "((3 + 5) * (2 - 1)) + (4 * 6)";
		List<String> tokens = expressionEvaluator.tokenize(expression);

		System.out.println("tokens are ");
		for (String string : tokens) {
			System.out.println(string);
		}
		List<String> postfix = expressionEvaluator.toPostfix(tokens);
		System.out.println("postfix are ");
		for (String string : postfix) {
			System.out.print(string);
		}

		Node node = expressionEvaluator.build(postfix);
		expressionEvaluator.printDAG(node, "");
		ExecutorService executor = Executors.newFixedThreadPool(WORKERS);
		for (int i = 0; i < WORKERS; i++) {
			executor.submit(new workerImpl(reqQueue, resQueue));
		}
		managerImpl manager = new managerImpl(reqQueue, resQueue, resultQueue, node);
		Thread managerThread = new Thread(manager);
		managerThread.start();
		 double result = resultQueue.take();
	        managerThread.join();
	        
	        System.out.println("result is " + result);
	        System.out.println("Number of Nodes = " + manager.getNodeCount());
	        System.out.println("Batches Sent For Execution = " + manager.getBatchCount());
	        executor.shutdown();
	        executor.awaitTermination(1, TimeUnit.MINUTES);

	}

}
