import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by IntelliJ IDEA.
 * User: sthirumuru
 * Date: 16/12/15
 * Time: 6:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class ExpressionEvaluation {

    public static List<String> operators = new ArrayList<String>();

    static {
        operators.add("*");
        operators.add("/");
        operators.add("+");
        operators.add("-");
    }

    public static double evaluateExpression(String exp) {
        List<String> expParts = breakTheExpression(exp);
        Stack<String> operatorStack = new Stack<String>();
        Stack<Double> operandStack = new Stack<Double>();
        double value = 0d;
        for (String part : expParts) {
            if (operators.contains(part)) {

                if (operatorStack.isEmpty()) {
                    operatorStack.push(part);
                } else {
                    while (!operatorStack.isEmpty()) {
                        boolean priority = operators.indexOf(operatorStack.peek()) < operators.indexOf(part);
                        if (priority) {
                            String operatorToEvaluate = operatorStack.pop();
                            double operand2 = operandStack.pop();
                            double operand1 = operandStack.pop();
                            value = evaluate(operatorToEvaluate, operand1, operand2);
                            operandStack.push(value);
                        } else {
                            break;
                        }
                    }
                    operatorStack.push(part);
                }
            } else {
                value = Double.parseDouble(part);
                operandStack.push(value);
            }
        }
        while (!operatorStack.isEmpty()) {
            String operator = operatorStack.pop();
            double operand2 = operandStack.pop();
            double operand1 = operandStack.pop();
            value = evaluate(operator, operand1, operand2);
            operandStack.push(value);
        }
        assert (operandStack.size() == 1);
        return operandStack.pop();
    }

    public static List<String> breakTheExpression(String exp) {
        List<String> expParts = new ArrayList<String>();
        expParts.add(exp);
        List<String> tempParts = new ArrayList<String>();
        for (String operator : operators) {
            String pattern = operator.equals("+") || operator.equals("*") ? "\\" + operator : operator;
            for (String part : expParts) {
                String[] splits = part.split(pattern);
                for (String split : splits) {
                    tempParts.add(split);
                    tempParts.add(operator);
                }
                tempParts.remove(tempParts.size() - 1);
            }
            expParts.clear();
            expParts.addAll(tempParts);
            tempParts.clear();
        }
        return expParts;
    }

    public static double evaluate(String operator, double operand1, double operand2) {
        if (operator.equals("/")) {
            return operand1 / operand2;
        } else if (operator.equals("*")) {
            return operand1 * operand2;
        } else if (operator.equals("+")) {
            return operand1 + operand2;
        } else if (operator.equals("-")) {
            return operand1 - operand2;
        }

        throw new UnsupportedOperationException();
    }


    public static String getPostFixExpression(String exp)
    {
        Stack<String> operatorStack = new Stack<String>();
        StringBuilder postFixExp = new StringBuilder();
        for(Character c : exp.toCharArray())
        {
            if(operators.contains(Character.toString(c)))
            {
                String operatorString = Character.toString(c);
                if(operatorStack.isEmpty())
                {
                    operatorStack.push(operatorString);
                }
                else
                {
                    while(!operatorStack.isEmpty())
                    {
                        if(operators.indexOf(operatorStack.peek()) < operatorStack.indexOf(operatorString))
                        {
                            postFixExp.append(operatorStack.pop());
                        }
                        else
                        {
                            break;
                        }
                    }
                    operatorStack.push(operatorString);
                }
            }
            else
            {
                postFixExp.append(c);
            }
        }
        while(!operatorStack.isEmpty())
        {
            postFixExp.append(operatorStack.pop());
        }
        return postFixExp.toString();
    }

    public static void main(String[] args) {
        String exp = "1*5*4+2";
        System.out.println("Evaluating " + exp);
        System.out.println("Post fix Exp " + getPostFixExpression(exp));
        System.out.println(evaluateExpression(exp));
    }
}
