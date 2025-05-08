package part2_modified;

import java.util.concurrent.BlockingQueue;

public class workerImpl implements Runnable{
	
	 private BlockingQueue<Node> taskQueue;
	 private BlockingQueue<Node> resultQueue;
	 
	 

	public workerImpl(BlockingQueue<Node> taskQueue, BlockingQueue<Node> resultQueue) {
		super();
		this.taskQueue = taskQueue;
		this.resultQueue = resultQueue;
	}



	@Override
	public void run() {
		try {
			while(true) {
				Node node = taskQueue.take();
				if(node == null) {
					break;
				}
				if(!node.getDependencies().isEmpty()) {
					String expression = node.getExpression();
					for(Node dep : node.getDependencies()) {
						expression = expression.replace(dep.getExpression(), dep.getResult().toString());
					}
					node.setResult(expressionEvaluator.evaluatePostfix(expressionEvaluator.toPostfix(expression)));
				}else {
					node.setResult(expressionEvaluator.evaluatePostfix(expressionEvaluator.toPostfix(node.getExpression())));
				}
			}
		}catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		
		
		
	}

}
