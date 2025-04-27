package exampleDAG;

import java.util.List;

public class DAGTester {

	//sample program to just create a DAG
	public static void main(String[] args) {
		//expressionEvaluator evaluator = new expressionEvaluator();
		String expression= "((3 + 5) * (2 - 1)) + 4";
		List<String> tokens = expressionEvaluator.tokenize(expression);
		
		System.out.println("tokens are ");
		for (String string : tokens) {
			System.out.println(string);
		}
		List<String> postfix = expressionEvaluator.toPostfix(tokens);
		System.out.println("postfix are ");
		for (String string : postfix) {
			System.out.print(string);
		}
		DAGGraph graph = new DAGGraph();
		Node node = graph.createDAG(postfix);
		System.out.println("print dag");
		graph.printDAG(node, "");
		System.out.println("result is" + graph.evaluate(node));

	}

}
