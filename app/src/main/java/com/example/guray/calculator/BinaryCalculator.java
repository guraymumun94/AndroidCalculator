package com.example.guray.calculator;

/**
 * Created by guray on 3/17/16.
 */
public class BinaryCalculator {
    private String expression = "1111 \nXOR      \n0000";

    public BinaryCalculator(String expression) {
        this.expression = expression;
    }

    private static String[] getElements(String expression) {
        return expression.split("[ \n]+");
    }

    private static String calculate(String[] elements) {

        int firstNumber = Integer.parseInt(elements[0], 2);
        int secondNumber = Integer.parseInt(elements[2], 2);

        int result = elements[1].equals("AND") ? firstNumber & secondNumber :
                     elements[1].equals("OR") ? firstNumber | secondNumber : firstNumber ^ secondNumber;

        return Integer.toBinaryString(result);
    }

    public String calculate() {
        return calculate(getElements(expression));
    }


}
