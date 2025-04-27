package exampleDAG;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class DAGGraph {

	Map<Node, Double> result;
	private static final Set<String> OPERATORS = Set.of("+", "-", "*", "/");

	public DAGGraph() {
		super();
		this.result = new HashMap<Node, Double>();
	}

	 double evaluate(Node node) {
		
		if (node == null) {
			return 0;
		}

		if (node.left == null && node.right == null) {
			return Double.parseDouble(node.value);
		}
		double left = evaluate(node.left);
		double right = evaluate(node.right);
		return switch (node.value) {
		case "+" -> left + right;
		case "-" -> left - right;
		case "*" -> left * right;
		case "/" -> left / right;
		default -> throw new IllegalArgumentException("Unknown operator: " + node.value);
		};
	}

	

	Node createDAG(List<String> postfix) {
		Map<Node, Node> dagGraph = new HashMap<Node, Node>();
		List<String> result = new ArrayList<String>();
		Stack<Node> stack = new Stack<>();

		for (String value : postfix) {
			if (value.matches("\\d+")) {
				Node newNode = new Node(value);
				stack.push(newNode);

			} else if (OPERATORS.contains(value)) {
				Node right = stack.pop();
				Node left = stack.pop();
				Node newNode = new Node(value, left, right);
				Node existingNode = dagGraph.getOrDefault(newNode, null);
				if (existingNode != null) {
					stack.push(existingNode);
				} else {
					dagGraph.put(newNode, newNode);
					stack.push(newNode);
				}
			}
		}
		return stack.pop();

	}

	public void printDAG(Node node, String indent) {
		if (node == null)
			return;
		printDAG(node.right, indent + "    ");
		System.out.println(indent + node.value);
		printDAG(node.left, indent + "    ");
	}
	
	
		
	}


