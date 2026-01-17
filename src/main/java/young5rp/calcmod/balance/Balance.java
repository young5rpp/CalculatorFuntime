package young5rp.calcmod.balance;

import net.minecraft.client.MinecraftClient;
import young5rp.calcmod.math.Zapros;

public class Balance {
    public static void resolve(String expr) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;
        Zapros.set(expr, true);
        client.player.networkHandler.sendCommand("bal");
    }
}
