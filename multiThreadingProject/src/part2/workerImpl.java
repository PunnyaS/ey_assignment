package part2;

import java.util.concurrent.BlockingQueue;

public class workerImpl implements Runnable{
	
	private BlockingQueue<request> requestQueue;
	private BlockingQueue<response> responseQueue;
	
	public workerImpl(BlockingQueue<request> requestQueue, BlockingQueue<response> responseQueue) {
		super();
		this.requestQueue = requestQueue;
		this.responseQueue = responseQueue;
	}

	@Override
	public void run() {
		try {
		while(true) {
			request req = requestQueue.take();
			Node n = req.node;
			double result;
            if (n.isLeaf()) {
                result = Double.parseDouble(n.expression);
            } else {
                double l = n.left.result;
                double r = n.right.result;
                switch (n.value) {
                    case "+": result = l + r; break;
                    case "-": result = l - r; break;
                    case "*": result = l * r; break;
                    case "/": result = l / r; break;
                    default: throw new RuntimeException("Unknown operator");
                }
            } 
            responseQueue.put(new response(result, n));
		}
		}catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		
	}
	


}
