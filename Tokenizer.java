package project;

import java.util.ArrayList;
import java.util.Arrays;

public class Tokenizer {

    //check if c is an operator or special character like ( or )
    public static boolean isOperator(char c) {
        //String regexUnaryOperator = String.format("\\s*?[\\+\\-]{1,2}\\s*?\\d|\\s*?[!]\\s*?|\\s*?[=]{1,2}\\s*?|\\s*[+]\\s*%s");
        if(c == '+' || c == '-' || c == '/' || c == '(' || c == ')' || c == '*' || c == '=') {
            return true;
        }
        return false;
    }
    //check for whitespace
    static boolean isWhiteSpace(char c) {
        if(c == ' ') {
            return true;
        }
        return false;
    }

    static ArrayList<String> tokenize(String s) {
        ArrayList<String> tokens = new ArrayList<String>();
        String formToken = "";
        for(int i =0; i < s.length();i++) {

            //checks for a special case where the first character is an operator
            if(i==0 && isOperator(s.charAt(i))) {
                tokens.add(Character.toString(s.charAt(i)));
                continue;
            }

            if(isOperator(s.charAt(i))) {
                if(isOperator(s.charAt(i-1)) && !isWhiteSpace(s.charAt(i))) {
                    tokens.add(Character.toString(s.charAt(i)));
                }
                else {
                    if(formToken != ""){
                        tokens.add(formToken);
                        tokens.add(Character.toString(s.charAt(i)));
                        formToken = "";
                    }
                    else{
                        tokens.add(Character.toString(s.charAt(i)));
                        formToken = "";
                    }

                }
            }
            else {
                if(isWhiteSpace(s.charAt(i))) {
                }
                else {
                    formToken = formToken.concat(Character.toString(s.charAt(i)));
                }
            }
        }
        tokens.add(formToken);
        tokens.removeAll(Arrays.asList(null, ""));
        return tokens;
    }

    public static void main(String[] args){
        //run one at a time to avoid confusion. feel free to add more
        tokenize("(1 + 3) * 45").forEach(System.out::println);
//		tokenize("-123+(  56    +num1  ").forEach(System.out::println);
//		tokenize("  135+e").forEach(System.out::println);
//		tokenize("(5+5)*16").forEach(System.out::println);
//		tokenize("(1+num1+5)*16").forEach(System.out::println);
//		tokenize("(5+5)*num23  ++ 5").forEach(System.out::println);
    }

}
