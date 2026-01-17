package young5rp.calcmod.math;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.*;

public class Cheto {
    private static final MathContext MC = MathContext.DECIMAL128;
    public static BigDecimal eval(String expr, Map<String, BigDecimal> vars) {
        List<String> rpn = toRPN(tokenize(expr));
        return evalRPN(rpn, vars);
    }
    private static List<String> tokenize(String s) {
        List<String> tokens = new ArrayList<>();
        StringBuilder num = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isDigit(c) || c == '.') {
                num.append(c);
            } else {
                if (num.length() > 0) {
                    tokens.add(num.toString());
                    num.setLength(0);
                }
                if (!Character.isWhitespace(c)) {
                    tokens.add(String.valueOf(c));
                }
            }
        }
        if (num.length() > 0) tokens.add(num.toString());
        return tokens;
    }
    private static int prec(String op) {
        return (op.equals("+") || op.equals("-")) ? 1 : 2;
    }
    private static List<String> toRPN(List<String> tokens) {
        List<String> out = new ArrayList<>();
        Stack<String> ops = new Stack<>();
        for (String t : tokens) {
            if (t.matches("[0-9.]+") || t.matches("[a-zA-Z]+")) {
                out.add(t);
            } else if ("+-*/".contains(t)) {
                while (!ops.isEmpty() && "+-*/".contains(ops.peek())
                        && prec(ops.peek()) >= prec(t)) {
                    out.add(ops.pop());
                }
                ops.push(t);
            } else if (t.equals("(")) {
                ops.push(t);
            } else if (t.equals(")")) {
                while (!ops.peek().equals("(")) {
                    out.add(ops.pop());
                }
                ops.pop();
            }
        }
        while (!ops.isEmpty()) out.add(ops.pop());
        return out;
    }
    private static BigDecimal evalRPN(List<String> rpn, Map<String, BigDecimal> vars) {
        Stack<BigDecimal> stack = new Stack<>();
        for (String t : rpn) {
            if (t.matches("[0-9.]+")) {
                stack.push(new BigDecimal(t));
            } else if (vars.containsKey(t)) {
                stack.push(vars.get(t));
            } else {
                BigDecimal b = stack.pop();
                BigDecimal a = stack.pop();
                switch (t) {
                    case "+" -> stack.push(a.add(b, MC));
                    case "-" -> stack.push(a.subtract(b, MC));
                    case "*" -> stack.push(a.multiply(b, MC));
                    case "/" -> stack.push(a.divide(b, MC));
                }
            }
        }
        return stack.pop();
    }
}
