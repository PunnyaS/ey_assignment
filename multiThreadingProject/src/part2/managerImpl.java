package part2;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

public class managerImpl implements Runnable {
	BlockingQueue<request> requestQueue;
	BlockingQueue<response> responseQueue;
	BlockingQueue<Double> resultQueue;
	private Node root;

	private final Set<Node> executed = ConcurrentHashMap.newKeySet();

	private final Map<Node, Integer> children = new ConcurrentHashMap<>();
	private int batchCount = 0;
	private final Set<Node> allNodes = ConcurrentHashMap.newKeySet();

	public managerImpl(BlockingQueue<request> requestQueue, BlockingQueue<response> responseQueue,
			BlockingQueue<Double> resultQueue, Node root) {
		super();
		this.requestQueue = requestQueue;
		this.responseQueue = responseQueue;
		this.resultQueue = resultQueue;
		this.root = root;
	}

	

	private void addNodes(Node node) {
		if (node == null || allNodes.contains(node))
			return;
		allNodes.add(node);
		addNodes(node.left);
		addNodes(node.right);

	}
	 public int getBatchCount() {
	        return batchCount;
	    }

	    public int getNodeCount() {
	        return allNodes.size();
	    }
	    
	    @Override
	    public void run() {
	        addNodes(root);
	        Queue<Node> queue = new LinkedList<>();

	        for (Node node : allNodes) {
	            int deps = 0;
	            if (!node.isLeaf()) {
	                if (node.left != null) deps++;
	                if (node.right != null) deps++;
	            }
	            children.put(node, deps);
	            if (deps == 0) queue.add(node);
	        }

	        while (!queue.isEmpty()) {
	            List<Node> batch = new ArrayList<>();
	            while (!queue.isEmpty()) {
	                Node node = queue.poll();
	                if (!executed.contains(node)) {
	                    batch.add(node);
	                    executed.add(node);
	                }
	            }

	            for (Node node : batch) {
	                requestQueue.add(new request(node));
	            }

	            batchCount++;

	            for (int i = 0; i < batch.size(); i++) {
	                try {
	                    response res = responseQueue.take();
	                    res.node.result = res.value;
	                    for (Node next : allNodes) {
	                        if ((next.left == res.node || next.right == res.node) && !executed.contains(next)) {
	                            children.put(next, children.get(next) - 1);
	                            if (children.get(next) == 0) {
	                                queue.add(next);
	                            }
	                        }
	                    }
	                } catch (InterruptedException e) {
	                    Thread.currentThread().interrupt();
	                }
	            }
	        }

	        try {
	            resultQueue.put(root.result);
	        } catch (InterruptedException e) {
	            Thread.currentThread().interrupt();
	        }
	    }


}
