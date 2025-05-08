package part2_modified;

import java.util.ArrayList;
import java.util.List;

public class Node {
	
	private String expression;
	private Integer result;
	private  List<Node> dependencies;
	private  List<Node> dependents;
	private boolean executed;
	
	public Node(String expression, List<Node> dependencies) {
		super();
		this.expression = expression;
		this.dependencies = dependencies != null ? dependencies : new ArrayList<Node>();
		this.dependents = new ArrayList<>();
        this.executed = false;
	}
	
	
	public void addDependent(Node dependent) {
        dependents.add(dependent);
    }

    String getExpression() {
		return expression;
	}


	Integer getResult() {
		return result;
	}


	List<Node> getDependencies() {
		return dependencies;
	}


	List<Node> getDependents() {
		return dependents;
	}


	boolean isExecuted() {
		return executed;
	}






	public void setResult(Integer result) {
        this.result = result;
        this.executed = true;
    }
	

}
