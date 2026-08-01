package gro.weirdshit.miencrap3minerat;

import gro.weirdshit.miencrap3minerat.net.classes.FlashPayload;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.permissions.Permissions;
import net.minecraft.world.entity.player.Player;

public class ModCommands {
    public static void generate() {

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(Commands.literal("gex").executes(context -> {
                context.getSource().sendSuccess(() -> Component.literal("gex"), false);
                return 1;
            }));
        });

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(Commands.literal("flash")
                    .requires(source -> source.permissions().hasPermission(Permissions.COMMANDS_MODERATOR))
                    .executes(ctx -> {
                        

                        FlashPayload payload = new FlashPayload(3);
						if (ctx.getSource().getEntityOrException() instanceof ServerPlayer player) {
							ServerPlayNetworking.send(player, payload);
                        }
                        return 1;
                    }));
        });

    }
}
