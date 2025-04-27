package part2;

import java.util.Objects;

public class Node {

	Node left, right;
	String expression;
	String value;
	volatile Double result;

	public Node(String expression) {
		super();
		this.expression = expression;
	}

	public Node(Node left, Node right, String value) {
		super();
		this.left = left;
		this.right = right;
		this.value = value;
		this.expression = "(" + left.expression + value + right.expression + ")";
	}

	boolean isLeaf() {
		return left == null && right == null;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Node))
			return false;
		return expression.equals(((Node) o).expression);
	}

	@Override
	public int hashCode() {
		return Objects.hash(expression);
	}

}
