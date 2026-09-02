package gro.weirdshit.miencrap3minerat.item.classes;

import java.util.List;
import java.util.Random;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.ModSounds;
import gro.weirdshit.miencrap3minerat.block.ModBlocks;
import gro.weirdshit.miencrap3minerat.entity.HappyGhast;
import gro.weirdshit.miencrap3minerat.entity.Hooorse;
import gro.weirdshit.miencrap3minerat.item.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
// import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.NetherPortalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.portal.TeleportTransition;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.minecart.Minecart;
import net.minecraft.world.food.FoodProperties;

public class SteeringWheelItem extends Item {

    public SteeringWheelItem(Properties properties) {
        super(properties.stacksTo(1).durability(1000));
    }
    
    public InteractionResult use(final Level level, final Player player, final InteractionHand hand) { 

        player.playSound(ModSounds.WRENCH);

        if (player.getRootVehicle() != null && player.getRootVehicle() instanceof Hooorse hooorse) {
            float xr = player.getXRot();
            float yr = player.getYHeadRot();
            Vec3 direction = Vec3.directionFromRotation(xr, yr);

            hooorse.wheel(new Vec3(direction.x * 8.0, 0.0, direction.z * 8.0));
            hooorse.setDeltaMovement(direction.x * 1.4, hooorse.onGround() ? 1.0 : hooorse.getDeltaMovement().y, direction.z);
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

        return InteractionResult.SUCCESS;
    }
}
