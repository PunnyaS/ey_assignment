package problem5;

import java.util.concurrent.BlockingQueue;

public class workerThreadImpl implements Runnable {
	BlockingQueue<Task> bQueue;
	responseQueue rQueue;

	public workerThreadImpl(BlockingQueue<Task> bQueue, responseQueue rQueue) {
		super();
		this.bQueue = bQueue;
		this.rQueue = rQueue;
	}

	@Override
	public void run() {
		try {
			while (true) {

				Task task = bQueue.take();
				if (task.value == -1) {
					break;
				}
				System.out.println(Thread.currentThread().getName() + " RECEIVED TASK " + task.value);
				Thread.sleep(task.value);
				System.out.println("log(" + task.value + ") = " + Math.log(task.value) + ")");
				rQueue.add(Math.log(task.value));
				System.out.println("Added "+ task.value +"to rqueue");

			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}

	}

}
