package project;

import java.util.ArrayList;

public class Evaluator {

    /*
        <assign> -> <id> = <expr>
        <id> -> number
        <expr> -> <expr> + <term>| <term>
        <term> -> <term> * <factor> | <factor>
        <factor> -> (<expr>) | <id>
     */

    static Integer evalExpr(String s) {
        // ArrayList<String> tokens = tokenizer.tokenize(s.replaceAll("\\s+",""));
        if(!Parser.isExpr(s)){
            return null;
        }
        ArrayList<String> tokens = Tokenizer.tokenize(s);
        /*if(tokens.get(tokens.size()-1).equals(""))
            tokens.remove(tokens.get(tokens.size()-1));*/

        return evalExprHelper(tokens, 0, tokens.size()-1);
    }
    /**
     * <expr> ->
     * */
    static Integer evalExprHelper(ArrayList<String> tokens, int start, int end) {
        // <expr> --> <expr> + <term> | <term>
        if (start > end) return null;

        for(int i=start;i<end;i++) {
            if (tokens.get(i).equals("+")) {
                Integer i1 = evalExprHelper(tokens, start, i - 1);
                Integer i2 = evalExprHelper(tokens, i + 1, end);
                if (i2 != null && i1 != null) {
                    return i1 + i2;
                }
            }
        }
        return evalTerm(tokens, start, end);
    }

    static Integer evalTerm(ArrayList<String> tokens, int start, int end) {
        // <term> -> <term>  * <factor> | <factor>
        if (start > end) return null;

        for (int i = start + 1; i < end; i++) {
            if (tokens.get(i).equals("*")) {
                Integer i1 = evalTerm(tokens, start, i - 1);
                Integer i2 = evalFactor(tokens, i + 1, end);
                if (i2 != null && i1 != null) {
                    return (i1 * i2);
                }
            }
        }
        return evalFactor(tokens, start, end);
    }

    static Integer evalFactor(ArrayList<String> tokens, int start, int end) {
        // <factor> -> (<expr>) | <id>
        // (1 + 3) * 45
        if (start > end) return null;

        if (tokens.get(start).equals("(") && tokens.get(end-1).equals(")")) {
            return evalExprHelper(tokens, start + 1, end - 1);
        }
        if (tokens.get(start).equals("(") && tokens.get(end).equals(")")) {
            return evalExprHelper(tokens, start + 1, end - 1);
        }
        //get the integer
        return evalID(tokens,start,end);
    }
    static Integer evalID(ArrayList<String> tokens, int start, int end){
        //regex
        String regexIntegers = "\\d+";
        String regexNumber = String.format("%s", regexIntegers);

        if(tokens.get(start).matches(regexNumber)){
            return Integer.parseInt(tokens.get(start));
        }
        else
            return null;
    }

    public static boolean specialEval(String s){
        for(int i = 0; i <= s.length()-1;i++){
            if(!Tokenizer.isWhiteSpace(s.charAt(i))){
                return true;
            }
        }
        return  false;
    }



    public static void main(String[] args) {
        //System.out.println(evalExpr("234")); // 234
        //System.out.println(evalExpr("1 + 3"));    // 4
        // System.out.println(evalExpr("(1 + 3) * 45"));    // 180 works
        //System.out.println(evalExpr("4 + 6 + (1 * 3 + 10)"));    // 23 gives 22
        //System.out.println(evalExpr("(1 + (2 + 1)) * 45"));    // 180 works
        //System.out.println(evalExpr("(1 + (2 + 1)) * (78+3*15) +45"));    // 537 works
        //System.out.println(evalExpr("(1 +")); // null
        //System.out.println(evalExpr("1 + * 2")); // null
        System.out.println(evalExpr("3 * 2 + 2 * 3"));



    }



}