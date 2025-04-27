package problem1And2;

// workerthread implementation
public class workerThreadImpl implements Runnable {

	private final sampleQueue queue;

	public workerThreadImpl(sampleQueue queue) {
		super();
		this.queue = queue;
	}

	@Override
	public void run() {
		try {
			while (true) {
				int data = queue.dequeue();
				// exit condition
				if (data == -1) {
					break;
				}

				System.out.println("REQUEST RECEIVED " + data);
				Thread.sleep(data);
				double logValue = Math.log(data);
				System.out.println("log(" + data + ") = " + logValue);
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}

	}
}