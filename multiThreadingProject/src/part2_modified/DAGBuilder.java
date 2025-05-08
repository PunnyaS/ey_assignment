package part2_modified;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;



public class DAGBuilder {
	private Map<String,Node> nodeMap = new HashMap();
	private static final Set<String> OPERATORS = Set.of("+", "-", "*", "/");
	
	
	public Node buildDag(List<String> postfix) {
		Stack<Node> stack = new Stack<Node>();
		for (String value : postfix) {
			if (value.matches("[0-9.]+")) {
				Node newnode = new Node(value, null);
				//Node node = nodeCache.computeIfAbsent(newnode.expression, k -> newnode);
				stack.push(newnode);

			} else if (OPERATORS.contains(value)) {
				Node right = stack.pop();
				Node left = stack.pop();
				String expression = "(" + left.getExpression() + value + right.getExpression() + ")";
				Node newnode = new Node(expression, Arrays.asList(left,right) );
				left.addDependent(newnode);
				right.addDependent(newnode);
				
				stack.push(newnode);
				}
			
		}
		return stack.pop();

	}
	
	public void addNode(Node node) {
        nodeMap.put(node.getExpression(), node);
    }

    public Node getNode(String expression) {
        return nodeMap.get(expression);
    }

    public Collection<Node> getAllNodes() {
        return nodeMap.values();
    }
    public void addNodeAndDependencies(Node node) {
        Set<Node> visited = new HashSet<>();
        traverseAndAdd(node, visited);
    }

    private void traverseAndAdd(Node node, Set<Node> visited) {
    	 if (node == null || visited.contains(node)) {
    	        return;
    	    }

    	    visited.add(node);
    	    nodeMap.putIfAbsent(node.getExpression(), node);

    	    for (Node dependency : node.getDependencies()) {
    	        traverseAndAdd(dependency, visited);
    	    }
    }


}
