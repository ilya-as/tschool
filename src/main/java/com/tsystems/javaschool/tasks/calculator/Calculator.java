package com.tsystems.javaschool.tasks.calculator;

import java.util.*;
import java.text.DecimalFormat;
import java.util.LinkedList;

public class Calculator {

    /**
     * Evaluate statement represented as string.
     *
     * @param statement mathematical statement containing digits, '.' (dot) as decimal mark,
     *                  parentheses, operations signs '+', '-', '*', '/'<br>
     *                  Example: <code>(1 + 38) * 4.5 - 1 / 2.</code>
     * @return string value containing result of evaluation or null if statement is invalid
     */
    public String evaluate(String statement) {
        try {
            LinkedList<Double> st = new LinkedList<>();
            LinkedList<Character> op = new LinkedList<>();
            Locale locale = new Locale("en");
            Locale.setDefault(locale);
            DecimalFormat df = new DecimalFormat("###.####");
            for (int i = 0; i < statement.length(); i++) {
                char c = statement.charAt(i);
                if (isSpace(c))
                    continue;
                if (c == '(')
                    op.add('(');
                else if (c == ')') {
                    while (op.getLast() != '(')
                        processOperator(st, op.removeLast());
                    op.removeLast();
                } else if (isOperator(c)) {
                    while (!op.isEmpty() && isPrior(op.getLast()) >= isPrior(c))
                        processOperator(st, op.removeLast());
                    op.add(c);
                } else {
                    String operand = "";
                    while (i < statement.length() && Character.isDigit(statement.charAt(i)) | statement.charAt(i) == '.') {
                        operand += statement.charAt(i++);
                    }
                    i--;
                    st.add(Double.parseDouble(operand));
                }
            }
            while (!op.isEmpty())
                processOperator(st, op.removeLast());
            return st.get(0).isInfinite() ? null : df.format(st.get(0));// возвращаем полученный результат в нужном виде ##.####
        } catch (Exception ex) {
            return null;
        }
    }

    private boolean isSpace(char c) {
        return c == ' '; // true, если пробел
    }

    private boolean isOperator(char с) { // true, если знак
        return с == '+' || с == '-' || с == '*' || с == '/';
    }

    private int isPrior(char op) {
        switch (op) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            default:
                return -1;
        }
    }

    private void processOperator(LinkedList<Double> st, char operation) {
        double one = st.removeLast();
        double two = st.removeLast();
        switch (operation) {
            case '+':
                st.add(two + one);
                break;
            case '-':
                st.add(two - one);
                break;
            case '*':
                st.add(two * one);
                break;
            case '/':
                st.add(two / one);
                break;
        }
    }
}
