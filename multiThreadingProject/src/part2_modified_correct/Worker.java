package part2_modified_correct;

import java.util.List;
import java.util.concurrent.BlockingQueue;

public class Worker implements Runnable {
    private final BlockingQueue<DAGNode> taskQueue;
    private final BlockingQueue<DAGNode> resultQueue;

    public Worker(BlockingQueue<DAGNode> taskQueue, BlockingQueue<DAGNode> resultQueue) {
        this.taskQueue = taskQueue;
        this.resultQueue = resultQueue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                DAGNode node = taskQueue.take();
                if (node == null || node.getExpression().equals("PILL")) break;

                if (node.getDependencies().isEmpty()) {
                    node.setResult(Integer.parseInt(node.getExpression()));
                } else {
                    List<DAGNode> deps = node.getDependencies();
                    int a = deps.get(0).getResult();
                    int b = deps.get(1).getResult();
                    String op = node.getOperator();
                    switch (op) {
                        case "+" -> node.setResult(a + b);
                        case "-" -> node.setResult(a - b);
                        case "*" -> node.setResult(a * b);
                        case "/" -> node.setResult(a / b);
                        default -> throw new IllegalStateException("Unknown operator: " + op);
                    }
                   
                }

                node.setExecuted(true);
                resultQueue.put(node);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

