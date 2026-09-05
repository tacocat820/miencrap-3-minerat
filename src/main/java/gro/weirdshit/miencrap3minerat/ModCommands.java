package gro.weirdshit.miencrap3minerat;

import gro.weirdshit.miencrap3minerat.etc.ThirstMixinInterface;
import gro.weirdshit.miencrap3minerat.etc.markov_chains.TestChain;
import gro.weirdshit.miencrap3minerat.mixin.ThirstMixin;
import gro.weirdshit.miencrap3minerat.net.classes.FlashPayload;
import gro.weirdshit.miencrap3minerat.net.classes.ScreenshakePayload;
import gro.weirdshit.miencrap3minerat.saved_data.WtfLevel;
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
            dispatcher.register(Commands.literal("getwtf")
                    .requires(source -> source.permissions().hasPermission(Permissions.COMMANDS_MODERATOR))
                    .executes(ctx -> {
                        WtfLevel wtf = WtfLevel.getSavedBlockData(ctx.getSource().getServer());
                        ctx.getSource().sendSuccess(() -> Component.literal(
                            String.valueOf(wtf.getWtf(ctx.getSource().getLevel()))
                        ), false);
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

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(Commands.literal("welcom")
                    .requires(source -> source.permissions().hasPermission(Permissions.COMMANDS_MODERATOR))
                    .executes(ctx -> {
                        
                        if (ctx.getSource().getEntityOrException() instanceof Player player) {
                            MIENCRAP3MINERAT.LOGGER.info("asd");
                            ((ThirstMixinInterface)(Object)player).setThirst(0);
                        }
                        return 1;
                    }));
        });

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(Commands.literal("shake")
                    .requires(source -> source.permissions().hasPermission(Permissions.COMMANDS_MODERATOR))
                    .executes(ctx -> {
                        

                        ScreenshakePayload payload = new ScreenshakePayload(1.0f);
						if (ctx.getSource().getEntityOrException() instanceof ServerPlayer player) {
							ServerPlayNetworking.send(player, payload);
                        }
                        return 1;
                    }));
        });


        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(Commands.literal("savemarkov")
                    .requires(source -> source.permissions().hasPermission(Permissions.COMMANDS_MODERATOR))
                    .executes(ctx -> {
                        
                        TestChain.save();
                        return 1;
                    }));
        });

    }
}
