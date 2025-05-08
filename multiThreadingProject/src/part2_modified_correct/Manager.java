package part2_modified_correct;

import java.util.*;
import java.util.concurrent.*;

public class Manager {
    private final DAGNode rootNode;
    private final Collection<DAGNode> allNodes;
    private final BlockingQueue<DAGNode> taskQueue;
    private final BlockingQueue<DAGNode> resultQueue;
    private int batchesSent;

    public Manager(DAGNode rootNode, Collection<DAGNode> allNodes,
                   BlockingQueue<DAGNode> taskQueue,
                   BlockingQueue<DAGNode> resultQueue) {
        this.rootNode = rootNode;
        this.allNodes = allNodes;
        this.taskQueue = taskQueue;
        this.resultQueue = resultQueue;
        this.batchesSent = 0;
    }

    public void execute() throws InterruptedException {
        Set<DAGNode> remaining = new HashSet<>(allNodes);

        while (!remaining.isEmpty()) {
            List<DAGNode> ready = new ArrayList<>();
            for (DAGNode node : remaining) {
                if (!node.isExecuted() && node.getDependencies().stream().allMatch(DAGNode::isExecuted)) {
                    ready.add(node);
                }
            }

            if (!ready.isEmpty()) {
                batchesSent++;
                for (DAGNode node : ready) {
                    taskQueue.put(node);
                }

                for (int i = 0; i < ready.size(); i++) {
                    DAGNode completed = resultQueue.take();
                    remaining.remove(completed);
                }
            }
        }
    }

    public int getBatchesSent() {
        return batchesSent;
    }

    public Integer getFinalResult() {
        return rootNode.getResult();
    }
}
