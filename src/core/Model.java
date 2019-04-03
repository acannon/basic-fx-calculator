package core;

import java.util.EmptyStackException;
import java.util.Stack;
import java.util.StringTokenizer;

public class Model {
    private Stack<Double> operand = new Stack<>();
    private Stack<String> operator = new Stack<>();

    private void eval() {
        // pop two operands and one operator, calculate, and push result onto operand stack
        Double num1 = operand.pop();
        Double num2 = operand.pop();
        Double result = null;
        String op = operator.pop();

        switch (op) {
            case "+":
                result = num2 + num1;
                break;
            case "-":
                result = num2 - num1;
                break;
            case "*":
                result = num2 * num1;
                break;
            case "/":
                result = num2 / num1;
                break;
            default:
                throw new NumberFormatException();
        }
        operand.push(result);
    }

    Double calculate(String eq) throws EmptyStackException {
        StringTokenizer t = new StringTokenizer(eq, "+-*/()", true);
        String nextToken;
        Double num = null;
        String currentOp = null;

        // variables to allow for unary operators at the start of expression or after a left paren
        boolean unaryOK = true;
        double sign = 1;

        // WHILE more tokens exist, get nextToken
        while (t.hasMoreTokens()) {
            nextToken = t.nextToken();
            // SWITCH nextToken
            switch (nextToken) {
                // + or –: WHILE top of operator stack is '+', '-', '*', or '/'
                case "+":
                    if (unaryOK) {
                        sign = 1;
                        unaryOK = false;
                        break;
                    }
                case "-":
                    if (unaryOK) {
                        sign = -1;
                        unaryOK = false;
                        break;
                    }

                    // if operator stack is empty, do not peak, just push operator
                    if (operator.isEmpty())
                        operator.push(nextToken);
                    else {
                        currentOp = operator.peek();
                        while (currentOp.equals("+") || currentOp.equals("-") || currentOp.equals("*") || currentOp.equals("/")) {
                            eval();

                            if (!operator.isEmpty())
                                currentOp = operator.peek();
                            else
                                currentOp = "";
                        }
                        operator.push(nextToken);
                    }
                    break;

                // * or /:  WHILE top of operator() stack is '*' or '/'
                case "*":
                case "/":
                    unaryOK = false;

                    // if operator stack is empty, do not peak, just push operator
                    if (operator.isEmpty())
                        operator.push(nextToken);

                    else {
                        currentOp = operator.peek();
                        while (currentOp.equals("*") || currentOp.equals("/")) {
                            // CALL eval()
                            eval();
                            if (!operator.isEmpty())
                                currentOp = operator.peek();
                            else
                                currentOp = "";
                        }
                        operator.push(nextToken);
                    }
                    break;

                // DEFAULT: Convert to double & push nextToken to operand stack
                default:
                    unaryOK = false;
                    num = Double.parseDouble(nextToken) * sign;
                    operand.push(num);
                    sign = 1;
            }
        }

        // WHILE operator stack is not empty CALL eval()
        while (!operator.isEmpty())
            eval();

        // Result is on top of operand stack
        if (!operand.isEmpty())
            return operand.peek();
        else
            throw new EmptyStackException();
    }
}