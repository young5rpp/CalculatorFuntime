package young5rp.calcmod.math;

public class Zapros {
    private static String expr;
    private static boolean hide;
    public static void set(String expression, boolean hideChat) {
        expr = expression;
        hide = hideChat;
    }
    public static String getAndClear() {
        String temp = expr;
        expr = null;
        hide = false;
        return temp;
    }
    public static boolean shouldHide() {
        return hide;
    }
}
