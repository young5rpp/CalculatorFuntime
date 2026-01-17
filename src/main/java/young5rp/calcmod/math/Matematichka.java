package young5rp.calcmod.math;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import young5rp.calcmod.balance.Balance;
import java.math.BigDecimal;
import java.math.MathContext;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;

public class Matematichka {
    private static final MathContext MC = MathContext.DECIMAL128;
    public static void process(String input) {
        if (input == null || input.isEmpty()) {
            send("Empty expression");
            return;
        }
        input = normalize(input);
        if (input.contains("bal")) {
            Balance.resolve(input);
            return;
        }
        if (input.contains("=")) {
            solveForX(input);
            return;
        }
        evaluate(input);
    }
    private static String normalize(String in) {
        return in.replace(" ", "")
                .replace("+-", "-")
                .replace("--", "+");
    }
    private static void evaluate(String expr) {
        try {
            BigDecimal result = Cheto.eval(expr, Map.of());
            send(result);
        } catch (Exception e) {
            send("Invalid expression");
        }
    }
    private static void solveForX(String input) {
        try {
            String[] parts = input.split("=");
            if (parts.length != 2) {
                send("Invalid equation");
                return;
            }
            String left = normalize(parts[0]);
            String right = normalize(parts[1]);
            double a = coefX(left);
            double b = constant(left);
            if (a == 0) {
                send("No solution");
                return;
            }
            BigDecimal c = Cheto.eval(right, Map.of());
            BigDecimal x = c.subtract(BigDecimal.valueOf(b))
                    .divide(BigDecimal.valueOf(a), MC);
            send("x = " + formatBigDecimal(x));
        } catch (Exception e) {
            send("Invalid equation");
        }
    }
    private static double coefX(String expr) {
        expr = expr.replace("-", "+-");
        double coef = 0;
        for (String part : expr.split("\\+")) {
            if (part.contains("x")) {
                String num = part.replace("x", "");
                coef += num.isEmpty() || num.equals("+") ? 1
                        : num.equals("-") ? -1
                        : Double.parseDouble(num);
            }
        }
        return coef;
    }
    private static double constant(String expr) {
        expr = expr.replace("-", "+-");
        double c = 0;
        for (String part : expr.split("\\+")) {
            if (!part.contains("x") && !part.isEmpty()) {
                c += Double.parseDouble(part);
            }
        }
        return c;
    }
    private static void send(String msg) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player != null) {
            client.player.sendMessage(Text.literal("§a[Calc] §f" + msg), false);
        }
    }
    private static void send(BigDecimal number) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;
        NumberFormat nf = NumberFormat.getInstance(Locale.US);
        nf.setGroupingUsed(true);
        nf.setMaximumFractionDigits(10);
        nf.setMinimumFractionDigits(0);
        String formatted = nf.format(number);
        client.player.sendMessage(Text.literal("§a[Calc] §f" + formatted), false);
    }
    private static String formatBigDecimal(BigDecimal number) {
        NumberFormat nf = NumberFormat.getInstance(Locale.US);
        nf.setGroupingUsed(true);
        nf.setMaximumFractionDigits(10);
        nf.setMinimumFractionDigits(0);
        return nf.format(number);
    }
}
