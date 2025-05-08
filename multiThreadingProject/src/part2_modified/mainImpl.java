package part2_modified;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class mainImpl {

	public static void main(String[] args) throws InterruptedException {

		BlockingQueue<Node> taskQueue = new LinkedBlockingQueue<>();
		BlockingQueue<Node> resultQueue = new LinkedBlockingQueue<>();
		BlockingQueue<Integer> finalQueue = new LinkedBlockingQueue<>();
		final int WORKERS = 4;

		String expression = "((3 + 5) * (6 + 7))";

		/*
		 * List<String> tokens = expressionEvaluator.tokenize(expression);
		 * 
		 * System.out.println("tokens are "); for (String string : tokens) {
		 * System.out.println(string); }
		 */

		List<String> postfix = expressionEvaluator.toPostfix(expression);
		System.out.println("postfix are ");
		for (String string : postfix) {
			System.out.println(string);
		}
		DAGBuilder builder = new DAGBuilder();
		Node node = builder.buildDag(postfix);
		builder.addNodeAndDependencies(node);
		// expressionEvaluator.printDAG(node, "");

		ExecutorService executor = Executors.newFixedThreadPool(WORKERS);
		for (int i = 0; i < WORKERS; i++) {
			executor.submit(new workerImpl(taskQueue, resultQueue));
		}
		managerImpl manager = new managerImpl(node, builder.getAllNodes(), WORKERS, taskQueue, resultQueue, finalQueue);
		Thread managerThread = new Thread(manager);
		managerThread.start();
		Integer result = finalQueue.take();
		managerThread.join();
		  for (int i = 0; i < WORKERS; i++) {
	            taskQueue.put(null); // Poison pill to shut down workers
	        }
		  executor.shutdown();
			executor.awaitTermination(1, TimeUnit.MINUTES);

		System.out.println("result is " + result);
		System.out.println("Number of Nodes = " + countDAGNodes(node));
		System.out.println("Batches Sent For Execution = " + manager.getBatchCount());
		
	}

	private static int countDAGNodes(Node root) {
		Set<Node> visited = new HashSet<>();
		Queue<Node> queue = new LinkedList<>();
		queue.add(root);
		while (!queue.isEmpty()) {
			Node node = queue.poll();
			if (visited.add(node)) {
				queue.addAll(node.getDependencies());
			}
		}
		return visited.size();
	}
}
