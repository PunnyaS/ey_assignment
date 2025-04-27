package exampleDAG;

import java.util.ArrayList;
import java.util.List;

public class Node {
	public String value;
	public Node left;
	public Node right;

	public Node(String value) {
		super();
		this.value = value;
		
	}

	public Node(String value, Node left, Node right) {
		super();
		this.value = value;
		this.left = left;
		this.right = right;
	}
	

}
