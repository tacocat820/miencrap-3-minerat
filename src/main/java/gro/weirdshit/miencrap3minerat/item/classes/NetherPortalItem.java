package gro.weirdshit.miencrap3minerat.item.classes;

import java.rmi.registry.Registry;
import java.util.Set;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.etc.TransitionsMixinInterface;
import gro.weirdshit.miencrap3minerat.net.classes.StartTransitionPayload;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.Registries;
// import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.NetherPortalBlock;
import net.minecraft.world.level.portal.TeleportTransition;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.minecart.Minecart;
import net.minecraft.world.level.portal.PortalForcer;
public class NetherPortalItem extends Item {

    public NetherPortalItem(Properties properties) {
        super(properties);
    }

    private InteractionResult tpToDimension(MinecraftServer server, ServerPlayer player, ResourceKey<Level> level_res_key, Vec3 targetPos) {
        
        ServerLevel level = server.getLevel(level_res_key);
        // BlockPos checkPos = new BlockPos((int)targetPos.x, ((int)targetPos.y) + 1, (int)targetPos.z);
        // boolean is_valid = level.getBlockState(checkPos).isAir();
        
        Vec3 finalTargetPos = null;

        for (int y = 0; y < 120; y++) {

            BlockPos checkPos = new BlockPos((int)targetPos.x, y, (int)targetPos.z);
            if (
                level.getBlockState(checkPos).isAir() &&
                level.getBlockState(checkPos.above()).isAir() &&
                level.getBlockState(checkPos.below()).isSolidRender()
            ) {
                finalTargetPos = new Vec3(targetPos.x, y, targetPos.z);
                break;
            }
            
        }

        if (finalTargetPos != null && player instanceof ServerPlayer serverPlayer) {

            ((TransitionsMixinInterface)(Object)player).teleportTo(
                level_res_key,
                finalTargetPos, 20
            );

            StartTransitionPayload payload = new StartTransitionPayload(true, "rect_close");
            ServerPlayNetworking.send(serverPlayer, payload);

            return InteractionResult.SUCCESS_SERVER;
        }
        return InteractionResult.FAIL;
    }

    public InteractionResult use(final Level level, final Player player, final InteractionHand hand) {
        if (player instanceof ServerPlayer server_pl) {

            MinecraftServer server = level.getServer();
            Vec3 targetPos;
            ResourceKey<Level> target_level;
            Vec3 poss = (player.position());

            if (level.dimension().equals( Level.NETHER )) {
                target_level = (Level.OVERWORLD);
                targetPos = new Vec3(poss.x * 8, poss.y, poss.z * 8);
            } else {
                target_level = (Level.NETHER);
                targetPos = new Vec3(poss.x / 8, poss.y, poss.z / 8);
            }

            return tpToDimension(server, server_pl, target_level, targetPos);
        }
        return InteractionResult.PASS;
    }
}
