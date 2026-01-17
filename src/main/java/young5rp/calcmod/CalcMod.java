package young5rp.calcmod;

import young5rp.calcmod.command.Calc;
import young5rp.calcmod.balance.Chat;
import net.fabricmc.api.ClientModInitializer;

public class CalcMod implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        Calc.register();
        Chat.register();
    }
}
