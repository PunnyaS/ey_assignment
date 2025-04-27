package problem3;

public class workerThreadImpl implements Runnable {
	public workerThreadImpl(customPriorityQueue pqueue) {
		super();
		this.pqueue = pqueue;
	}

	customPriorityQueue pqueue;
	static long highPriorityTime = 0, lowPriorityTime = 0;
	private final Object lock = new Object();

	public void run() {

		try {
			while (true) {

				taskImpl task = pqueue.dequeue();
				int data = task.getData();
				if (data == -1) {
					break;
				}
				System.out.println("REQUEST RECEIVED " + data + " with priority " + task.getPriority());
				Thread.sleep(data);
				double logValue = Math.log(data);
				System.out.println("log(" + data + ") = " + logValue);

				synchronized (lock) {

					if (task.getPriority() == 100) {
						highPriorityTime = System.currentTimeMillis();
					} else {
						lowPriorityTime = System.currentTimeMillis();
					}
				}

			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}

	}

	static long  getHighPriorityTime() {
		return highPriorityTime;
	}

	

	static long  getLowPriorityTime() {
		return lowPriorityTime;
	}

	
	
}
