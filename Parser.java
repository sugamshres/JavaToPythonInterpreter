package project;

public class Parser{

    //The following function is to check if a character is an operator or not:
    public static boolean isOperator(char c) {
        return (c == '*' || c == '/' || c == '+' || c == '-' || c == '%' || c == '=');
    }

    public static boolean isExpr(String expression) {

        //The following line removes the unnecessary white spaces from the expression:
        expression = expression.replaceAll("\\s+", "");

        //The following checks if the expression begins or ends with an operator:
        if (isOperator(expression.charAt(0)) || isOperator(expression.charAt(expression.length() - 1)))
            return false;

        //The following lines of code test if the expression has a mismatching number of opening and closing parethesis
        int unclosedParenthesis = 0;

        for (int i = 0; i < expression.length(); i++) {

            if (expression.charAt(i) == '(') {

                unclosedParenthesis++;

                //The following condition checks if the expression ends with '(':
                if (i == expression.length() - 1) return false;
            }
            if (expression.charAt(i) == ')') {

                unclosedParenthesis--;

                //The following condition checks if the expression starts with ')':
                if (i == 0) return false;

            }
            if (isOperator(expression.charAt(i))) {

                //The following condition checks if an operator is preceded by an opening paranthesis
                //or followed by closing paranthesis or an operator itself
                if (expression.charAt(i - 1) == '(' || expression.charAt(i + 1) == ')' || isOperator(expression.charAt(i + 1))) {
                    return false;
                }
            }
        }

        return (unclosedParenthesis == 0);
    }

    public static void main(String[] args) {
        System.out.println(isExpr(" "));
        System.out.println(isExpr("234")); // true
        System.out.println(isExpr("1 + 3"));    // true
        System.out.println(isExpr("(1 + 3) * 45"));    // true
        System.out.println(isExpr("(1 + (2 + 1)) * 45"));    // true
        System.out.println(isExpr("(1 + (2 + 1)) * (78+3*15) +45"));    // true
        System.out.println(isExpr("(1 +")); // false
        System.out.println(isExpr("1 + * 2")); // false
        System.out.println(isExpr("( ( 2 + 3) * ( 5 - 1)/10")); //false
        System.out.println(isExpr("( ( 2 + 3) * ( 5 - 1)/10)")); //true

    }
}
