package problem3;

import java.util.Comparator;

public class taskImpl implements Comparable<taskImpl> {

	private int priority;
	private int data;
	private long time;

	int getData() {
		return data;
	}

 void setData(int data) {
		this.data = data;
	}

	 int getPriority() {
	return priority;
}

 void setPriority(int priority) {
	this.priority = priority;
}

 long getTime() {
	return time;
}

 void setTime(long time) {
	this.time = time;
}

	public taskImpl(int priority,int data) {
		super();
		this.priority = priority % 200;
		this.data = data;
		this.time = System.currentTimeMillis();
	}

	public int compareTo(taskImpl o) {
		
		return Integer.compare(o.priority, this.priority);
	}

}
