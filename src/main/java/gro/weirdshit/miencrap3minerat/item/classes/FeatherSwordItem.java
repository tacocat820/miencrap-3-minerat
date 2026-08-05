package gro.weirdshit.miencrap3minerat.item.classes;

import java.rmi.registry.Registry;
import java.util.Set;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.ModSounds;
import gro.weirdshit.miencrap3minerat.item.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.Registries;
// import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;


public class FeatherSwordItem extends Item {

    public FeatherSwordItem(Properties properties) {
        super(properties);
    }

    // public InteractionResult useOn(final UseOnContext context) {  // use on terrain
    //     return InteractionResult.SUCCESS;
    // }

    public InteractionResult use(final Level level, final Player player, final InteractionHand hand) {

        float xr = player.getXRot();
        float yr = player.getYHeadRot();
        Vec3 direction = Vec3.directionFromRotation(xr, yr);

        player.setDeltaMovement(direction.x * 1.4, direction.y * 1.4, direction.z * 1.4);
        ItemStack itemStack = player.getItemInHand(hand);

        player.playSound(ModSounds.WING);

        if (level.isClientSide()) {
            return InteractionResult.PASS;
        } else {
            EquipmentSlot slot = hand.asEquipmentSlot();
            ItemStack result = itemStack.hurtAndConvertOnBreak(1, Items.AIR, player, slot);
            return InteractionResult.SUCCESS_SERVER.heldItemTransformedTo(result);
        }
    }
}
