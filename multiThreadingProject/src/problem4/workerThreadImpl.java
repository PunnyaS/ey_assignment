package problem4;

import java.util.concurrent.BlockingQueue;


public class workerThreadImpl implements Runnable {

	private  BlockingQueue<Task> bQueue ;
	
	public workerThreadImpl(BlockingQueue<Task> bQueue) {
		super();
		this.bQueue = bQueue;
	}

	@Override
	public void run() {
		try {
		while(true) {
			
				Task task = bQueue.take();
				if(task.value == -1) {
					break;
				}
				System.out.println(Thread.currentThread().getName()+ " RECEIVED TASK "+ task.value);
				Thread.sleep(task.value);
				System.out.println("log(" +task.value+ ") = "+ Math.log(task.value)+ ")");
			
		}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		
	}

}
