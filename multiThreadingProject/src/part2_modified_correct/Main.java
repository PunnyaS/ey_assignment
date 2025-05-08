package part2_modified_correct;

import java.util.*;
import java.util.concurrent.*;



public class Main {
    public static void main(String[] args) throws InterruptedException {
        List<String> expressions = List.of(
            "(2+2)*(4+4)+3",           // Expected = 35
            "(2+3)*(2+3)+5/5",         // Expected = 26
            "(3*5)+(3*5)"              // Expected = 30 (deduped node)
        );

        for (String expr : expressions) {
            System.out.println("Evaluating: " + expr);
            
            // Convert infix expression to postfix
            List<String> postfix = ExpressionEvaluator.infixToPostfix(expr);
            
            // Build DAG from postfix expression
            DAGBuilder builder = new DAGBuilder();
            DAGNode root = builder.buildDAG(postfix);
            Collection<DAGNode> allNodes = builder.getAllNodes();
            
            // Create taskQueue and resultQueue
            BlockingQueue<DAGNode> taskQueue = new LinkedBlockingQueue<>();
            BlockingQueue<DAGNode> resultQueue = new LinkedBlockingQueue<>();
            
            // Start worker threads
            int numWorkers = 4; // Configurable number of workers
            ExecutorService executor = Executors.newFixedThreadPool(numWorkers);
            for (int i = 0; i < numWorkers; i++) {
                executor.submit(new Worker(taskQueue, resultQueue));
            }
            
            // Create and execute the manager
            Manager manager = new Manager(root, allNodes, taskQueue, resultQueue);
            manager.execute();

            // Shutdown workers gracefully
            for (int i = 0; i < numWorkers; i++) {
                taskQueue.add(new DAGNode("PILL")); // Poison pill to stop workers
            }
            executor.shutdown();
            executor.awaitTermination(1, TimeUnit.MINUTES);

            // Display results
            System.out.println("Answer = " + manager.getFinalResult());
            System.out.println("Number of Nodes = " + allNodes.size());
            System.out.println("Batches Sent For Execution = " + manager.getBatchesSent());
            System.out.println();
        }
    }
}
