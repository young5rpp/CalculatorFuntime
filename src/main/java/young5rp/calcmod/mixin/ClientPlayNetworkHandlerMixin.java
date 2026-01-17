package young5rp.calcmod.mixin;

import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import young5rp.calcmod.math.Matematichka;
import young5rp.calcmod.math.Zapros;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin {
    private static final Pattern balik = Pattern.compile("\\$(\\d[\\d,]*\\.?\\d*)");
    @Inject(method = "onGameMessage", at = @At("HEAD"), cancellable = true)
    private void onChatMessage(GameMessageS2CPacket packet, CallbackInfo ci) {
        String msg = packet.content().getString();
        Matcher matcher = balik.matcher(msg);
        if (!matcher.find()) return;
        if (!Zapros.shouldHide()) return;
        String numberStr = matcher.group(1).replace(",", "");
        if (numberStr.isEmpty()) return;
        BigDecimal balance = new BigDecimal(numberStr);
        String expr = Zapros.getAndClear();
        if (expr != null && expr.contains("bal")) {
            String replaced = expr.replace("bal", balance.toPlainString());
            Matematichka.process(replaced);
        }
        ci.cancel();
    }
}
