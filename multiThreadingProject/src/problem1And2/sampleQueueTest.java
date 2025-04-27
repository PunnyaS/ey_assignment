package problem1And2;

public class sampleQueueTest {

	public static void main(String[] args) throws InterruptedException {

		sampleQueue queue = new sampleQueue(10);
		queue.enqueue(1);
		System.out.println("Queue size " + queue.size());
		queue.print();
		queue.enqueue(2);
		queue.print();
		queue.enqueue(3);
		queue.enqueue(4);
		queue.print();
		queue.dequeue();
		queue.print();
		queue.dequeue();
		queue.enqueue(7);
		queue.print();
		System.out.println("Queue size " + queue.size());

	}

}
