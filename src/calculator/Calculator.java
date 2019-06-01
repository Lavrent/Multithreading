package calculator;

public class Calculator {
    public static String calculateExpression(String expression) {
        while (!isNumber(expression)) {
            System.out.println(expression);
            String mostPrioritizedSubExpression = findMostPrioritizedSubExpression(expression);
            if (isNumber(mostPrioritizedSubExpression)) {
                expression = expression.replace("( " + mostPrioritizedSubExpression + " )", mostPrioritizedSubExpression);
                continue;
            }
            String subExpressionResult = calculate(mostPrioritizedSubExpression);
            expression = expression.replace(mostPrioritizedSubExpression, subExpressionResult);
        }
        return expression;
    }

    private static String findMostPrioritizedSubExpression(String expression) {
        String result = getExpressionInBrackets(expression);
        if (isNumber(result)) {
            return result;
        }
        String[] split = result.split(" ");
        if (result.contains("*") || result.contains("/")) {
            for (int i = 0; i < split.length; i++) {
                if ("*/".contains(split[i])) {
                    return split[i - 1] + " " + split[i] + " " + split[i + 1];
                }
            }
        }
        return split[0] + " " + split[1] + " " + split[2];
    }

    private static String getExpressionInBrackets(String expression) {
        String result = "";
        if (expression.contains("(")) {
            String[] split = expression.split(" ");
            int openingBracketIndex = -1;
            int closingBracketIndex = -1;
            for (int i = 0; i < split.length; i++) {
                if (split[i].equals("(")) {
                    openingBracketIndex = i;
                } else if (split[i].equals(")") && i > openingBracketIndex) {
                    closingBracketIndex = i;
                    break;
                }
            }
            for (int i = openingBracketIndex + 1; i < closingBracketIndex; i++) {
                result += split[i];
                if (i != closingBracketIndex - 1) {
                    result += " ";
                }
            }
            return result;
        }
        return expression;
    }

    private static String calculate(String expression) {
        expression = expression.replace("( ", "");
        expression = expression.replace(" )", "");
        String[] array = expression.split(" ");
        return "" + calculate(Double.parseDouble(array[0]), array[1], Double.parseDouble(array[2]));
    }

    private static double calculate(double a, String operation, double b) {
        switch (operation) {
            case "*":
                return a * b;
            case "/":
                return a / b;
            case "-":
                return a - b;
            case "+":
                return a + b;
            default:
                throw new IllegalArgumentException("No such operation");
        }
    }

    private static boolean isNumber(String expression) {
        // Or you can do like this
        // return str.matches("-?\\d+(\\.\\d+)?");
        try {
            Double.parseDouble(expression);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
