package part2_modified_correct;

import java.util.*;

public class ExpressionEvaluator {
    public static List<String> infixToPostfix(String expression) {
        List<String> output = new ArrayList<>();
        Stack<String> operators = new Stack<>();
        StringTokenizer tokenizer = new StringTokenizer(expression, "+-*/() ", true);

        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken().trim();
            if (token.isEmpty()) continue;

            if (token.matches("\\d+")) {
                output.add(token);
            } else if (token.equals("(")) {
                operators.push(token);
            } else if (token.equals(")")) {
                while (!operators.isEmpty() && !operators.peek().equals("(")) {
                    output.add(operators.pop());
                }
                operators.pop(); // remove '('
            } else if (isOperator(token)) {
                while (!operators.isEmpty() && precedence(token) <= precedence(operators.peek())) {
                    output.add(operators.pop());
                }
                operators.push(token);
            }
        }

        while (!operators.isEmpty()) {
            output.add(operators.pop());
        }

        return output;
    }

    private static boolean isOperator(String token) {
        return "+-*/".contains(token);
    }

    private static int precedence(String op) {
        if (op.equals("+") || op.equals("-")) return 1;
        if (op.equals("*") || op.equals("/")) return 2;
        return 0;
    }
}
