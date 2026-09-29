package gro.weirdshit.miencrap3minerat;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import com.mojang.brigadier.arguments.IntegerArgumentType;

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
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.permissions.Permissions;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.storage.LevelResource;

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
            dispatcher.register(Commands.literal("unwelcom")
                    .requires(source -> source.permissions().hasPermission(Permissions.COMMANDS_MODERATOR))
                    .executes(ctx -> {
                        
                        if (ctx.getSource().getEntityOrException() instanceof Player player) {
                            MIENCRAP3MINERAT.LOGGER.info("asd");
                            ((ThirstMixinInterface)(Object)player).setThirst(10000);
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
            dispatcher.register(Commands.literal("setwtf")
                    .then(Commands.argument("val", IntegerArgumentType.integer())
                            .executes(ctx -> {
                                        
                                WtfLevel wtf = WtfLevel.getSavedBlockData(ctx.getSource().getServer());


                                int val = IntegerArgumentType.getInteger(ctx, "val");
                                
                                wtf.setWtf(val);
                                return 1;
                            })));
        });


        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(Commands.literal("savemarkov")
                    .requires(source -> source.permissions().hasPermission(Permissions.COMMANDS_MODERATOR))
                    .executes(ctx -> {
                        
                        MIENCRAP3MINERAT.LOGGER.info(ctx.getSource().getServer().getWorldPath(LevelResource.ROOT).toString());
                        Path path = ctx.getSource().getServer().getWorldPath(LevelResource.ROOT).resolve("data").resolve("testchain.model");
                        
                        TestChain.save(path);
                        return 1;
                    }));
        });


        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(Commands.literal("loadmarkov")
                    .requires(source -> source.permissions().hasPermission(Permissions.COMMANDS_MODERATOR))
                    .executes(ctx -> {
                        
                        MIENCRAP3MINERAT.LOGGER.info(ctx.getSource().getServer().getWorldPath(LevelResource.ROOT).toString());
                        Path path = ctx.getSource().getServer().getWorldPath(LevelResource.ROOT).resolve("data").resolve("testchain.model");
                        
                        TestChain.load(path);
                        return 1;
                    }));
        });


        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(Commands.literal("trainmarkov")
                    .requires(source -> source.permissions().hasPermission(Permissions.COMMANDS_MODERATOR))
                    .executes(ctx -> {
                        
                        MIENCRAP3MINERAT.LOGGER.info(ctx.getSource().getServer().getWorldPath(LevelResource.ROOT).toString());
                        Path path = ctx.getSource().getServer().getWorldPath(LevelResource.ROOT).resolve("train.txt");
                        
                        try {
                            try (
                                Stream<String> stream = Files.lines(path)
                            ) {
                                stream.forEach(l -> {
                                    if (l.trim() != "") {
                                        ctx.getSource().sendSystemMessage(Component.literal(l));                    
                                        TestChain.model.train(TestChain.model.encode(l));
                                    }
                                });
                            }
                        } catch(Exception e) {
		
                            PlayerList playerList = ctx.getSource().getServer().getPlayerList();
                            playerList.broadcastSystemMessage(Component.literal(e.toString()), false);

                        }

                        return 1;
                    }));
        });

    }
}
