package young5rp.calcmod.balance;

import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import java.util.function.Consumer;

public class Chat {
    private static Consumer<String> callback;
    public static void register() {
        ClientReceiveMessageEvents.CHAT.register((message, signedMessage, sender, overlay, position) -> {
            if (callback != null) {
                callback.accept(message.getString());
            }
        });
    }
    public static void waitFor(Consumer<String> consumer) {
        callback = consumer;
    }
    public static void clear() {
        callback = null;
    }
}
