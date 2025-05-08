package part2_modified_correct;

import java.util.*;

public class DAGBuilder {
    private final Map<String, DAGNode> nodeMap = new HashMap<>();

    public DAGNode buildDAG(List<String> postfix) {
        Stack<DAGNode> stack = new Stack<>();

        for (String token : postfix) {
            if (token.matches("\\d+")) {
            	DAGNode node = nodeMap.computeIfAbsent(token, k -> new DAGNode(token));
                stack.push(node);
            } else {
                DAGNode b = stack.pop();
                DAGNode a = stack.pop();

                String expr = "(" + a.getExpression() + token + b.getExpression() + ")";
                DAGNode node = nodeMap.get(expr);
                if (node == null) {
                	 node = new DAGNode(expr, Arrays.asList(a, b), token);
                    a.addDependent(node);
                    b.addDependent(node);
                    nodeMap.put(expr, node);
                }
                stack.push(node);
            }
        }

        DAGNode root = stack.pop();
        addNodeAndDependencies(root); 
        return root;
    }

    public void addNodeAndDependencies(DAGNode root) {
        Set<DAGNode> visited = new HashSet<>();
        traverseAndAdd(root, visited);
    }

    private void traverseAndAdd(DAGNode node, Set<DAGNode> visited) {
        if (!visited.add(node)) return;
        nodeMap.putIfAbsent(node.getExpression(), node);
        for (DAGNode dep : node.getDependencies()) {
            traverseAndAdd(dep, visited);
        }
    }

    public Collection<DAGNode> getAllNodes() {
        return nodeMap.values();
    }
}
