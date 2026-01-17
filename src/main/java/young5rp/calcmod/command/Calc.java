package young5rp.calcmod.command;

import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import static com.mojang.brigadier.arguments.StringArgumentType.greedyString;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.*;
import static com.mojang.brigadier.arguments.StringArgumentType.getString;
import young5rp.calcmod.math.Matematichka;

public class Calc {
    public static void register() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(
                    literal("calc")
                            .then(
                                    argument("expression", greedyString())
                                            .executes(ctx -> {
                                                String expr = getString(ctx, "expression");
                                                Matematichka.process(expr);
                                                return 1;
                                            })
                            )
            );
        });
    }
}
