package part2_modified;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.BlockingQueue;

public class managerImpl implements Runnable {

	private Node rootNode;
	private Collection<Node> allNodes;
	private int noofWorkers;
	private int batchCount;
	private BlockingQueue<Node> taskQueue;
	private BlockingQueue<Node> resultQueue;
	BlockingQueue<Integer> finalQueue;

	public managerImpl(Node rootNode, Collection<Node> allNodes, int noofWorkers, BlockingQueue<Node> taskQueue,
			BlockingQueue<Node> resultQueue, BlockingQueue<Integer> finalQueue) {
		
		super();
		this.rootNode = rootNode;
		this.allNodes = allNodes;
		this.noofWorkers = noofWorkers;
		this.batchCount = 0;
		this.taskQueue = taskQueue;
		this.resultQueue = resultQueue;
		
		this.finalQueue = finalQueue;

	}

	@Override
	public void run() {

		Set<Node> remainingNodes = new HashSet<>(allNodes);
		while (!remainingNodes.isEmpty()) {
			List<Node> readyNodes = new ArrayList<>();
			for (Node node : remainingNodes) {
				if (node.getDependencies().stream().allMatch(Node::isExecuted)) {
					readyNodes.add(node);
				}
			}

			if (!readyNodes.isEmpty()) {
				batchCount++;
				for (Node node : readyNodes) {
					
						taskQueue.add(node);
					
				}

				for (int i = 0; i < readyNodes.size(); i++) {
					try {
						Node completed = resultQueue.take();
						completed.setResult(completed.getResult());
						remainingNodes.remove(completed);
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
					}
				}
			}

		}
		
			
				if (rootNode.getResult() != null) {
					finalQueue.add(rootNode.getResult());
				}
			
		
	}

	int getBatchCount() {
		return batchCount;
	}

	void setBatchCount(int batchCount) {
		this.batchCount = batchCount;
	}
}