package exampleDAG;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class expressionEvaluator {

	
	//converts the expression into list of tokens
	public static  List<String> tokenize(String expression) {
		
		List<String> tokens = new ArrayList<>();
		StringBuilder number = new StringBuilder();
		for (char c : expression.toCharArray()) {
			if (Character.isDigit(c)) {
				number.append(c);
			} else {
				if (number.length() > 0) {
					tokens.add(number.toString());
					number.setLength(0);
				}
				if (c == ' ')
					continue;
				tokens.add(Character.toString(c));
			}
		}
		if (number.length() > 0) {
			tokens.add(number.toString());

		}
		return tokens;
	}
	
	//convert the infix to postfix
	public static List<String> toPostfix(List<String> expression) {
		List<String> result = new ArrayList<String>();
		Stack<String> stack = new Stack<>();
		Map<String,Integer> precedence = new HashMap<String,Integer>();
		precedence=
		Map.of(
		        "+", 1, "-", 1,
		        "*", 2, "/", 2
		    );
		
		for(String var : expression) {
			if(var.matches("\\d+")) {
				result.add(var);
			}else if(precedence.containsKey(var)) {
				while(!stack.isEmpty() && (precedence.getOrDefault(stack.peek(),0) >= precedence.get(var))) {
					result.add(stack.pop());
				}
				stack.push(var);
			}else if(var.equals("(")) {
				stack.push(var);
			}else if(var.equals(")")) {
				while (!stack.peek().equals("(")) {
	                result.add(stack.pop());
	            }
				stack.pop();
			}
		}
		while(!stack.isEmpty()) {
			result.add(stack.pop());
		}
		return result;
	}
	
	
	
	
	

}
