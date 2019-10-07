package project;

import java.util.HashMap;
import java.util.Scanner;
import java.util.ArrayList;

public class Interpreter {

    static HashMap<String, Integer> variables =   new HashMap<String, Integer>();

    /**
     * <assign> -> <id> = <expr>
     * <expr> -> <id> + number | <id>
     *
     */

    // Checks the existance of variables in an expression
    static boolean theyExist(ArrayList<String> tokens, int start) {
        for(int i = start; i < tokens.size(); i++) {
            String elem = tokens.get(i);
            if(elem.matches ("[a-zA-Z]+\\.?") && !variables.containsKey(elem))
                return false;
        }
        return true;
    }

    // Changes variables with their values
    static ArrayList<String> replaceVariables(ArrayList<String> tokens, Integer start) {
        for(int i = start; i < tokens.size(); i++) {
            String elem = tokens.get(i);
            if (variables.containsKey(elem))
                tokens.set(i, Integer.toString(variables.get(elem)));
        }
        return tokens;
    }

    static void interpretExp(String s) {
        ArrayList<String> tokens = Tokenizer.tokenize(s);
        InterpreterHelper(tokens);
    }

    static void InterpreterHelper(ArrayList<String> tokens) {

        // returning value of variable
        if (variables.containsKey(tokens.get(0)) && tokens.size()==1) {
            System.out.println(variables.get(tokens.get(0)));
            return;
        } else if (tokens.size()==1) {
            System.out.println("Variable entered do not exist!!!");
            return;
        }

        // Evaluating expressions with assignment
        if (tokens.get(1).equals("=")) {
            if (!variables.containsKey(tokens.get(0)) && tokens.size()==3) {
                variables.put(tokens.get(0), Evaluator.evalExprHelper(tokens, 2, tokens.size()-1));
            } else if (theyExist(tokens, 2))
                variables.put(tokens.get(0), Evaluator.evalExprHelper(replaceVariables(tokens, 2), 2, (tokens.size()-1)));

        } else if (theyExist(tokens, 0)) {
            // Evaluating mathematical expressions with no assignment
            System.out.println(Evaluator.evalExprHelper(replaceVariables(tokens, 0), 0, (tokens.size()-1)));
        } else{
            System.out.println("Variable entered do not exist!!!");
        }



    }
    static void interpreter() {
        String userInput = "";
        Scanner input = null;
        System.out.println("Andy Python 3.0. Nothing much supported. Feels good though.");
        while(!userInput.equals("quit()")) {

            input = new Scanner(System.in);
            System.out.print(">>> ");
            userInput = input.nextLine();

            if(!Evaluator.specialEval(userInput)){
                continue;
            }

            if(!Parser.isExpr(userInput)){
                System.out.println("Error. Not valid expression");
                continue;
            }

            Integer evaluate = Evaluator.evalExpr(userInput);
            if (evaluate != null)
                System.out.println(evaluate);
            else if(userInput.equals("quit()")){
                break;

            } else {
                interpretExp(userInput);
            }
        }
    }
    public static void main(String[] args) {
        interpreter();

    }



}



