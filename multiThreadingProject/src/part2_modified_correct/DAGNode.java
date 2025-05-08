package part2_modified_correct;

import java.util.*;

public class DAGNode {
	private static int idCounter = 0;
	private final int id;
	private final String expression;
	private final List<DAGNode> dependencies;
	private final List<DAGNode> dependents;
	private Integer result;
	private boolean executed;
	private final String operator;

	public DAGNode(String expression, List<DAGNode> dependencies, String operator) {
		this.id = idCounter++;
		this.expression = expression;
		this.dependencies = dependencies == null ? new ArrayList<>() : dependencies;
		this.dependents = new ArrayList<>();
		this.executed = false;
		this.operator = operator;
	}
	
	public DAGNode(String expression) {
	    this(expression, null, null);
	}

	public int getId() {
		return id;
	}

	public String getExpression() {
		return expression;
	}

	public List<DAGNode> getDependencies() {
		return dependencies;
	}

	public void addDependent(DAGNode dependent) {
		this.dependents.add(dependent);
	}
	 public String getOperator() {
	        return operator;
	    }

	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
	}

	public boolean isExecuted() {
		return executed;
	}

	public void setExecuted(boolean executed) {
		this.executed = executed;
	}

	@Override
	public String toString() {
		return "Node " + id + ": " + expression;
	}

	@Override
	public int hashCode() {
		return Objects.hash(expression);
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof DAGNode))
			return false;
		DAGNode other = (DAGNode) obj;
		return this.expression.equals(other.expression);
	}
}
