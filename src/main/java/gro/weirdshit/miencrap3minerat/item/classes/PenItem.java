package gro.weirdshit.miencrap3minerat.item.classes;

import gro.weirdshit.miencrap3minerat.ModSounds;
import gro.weirdshit.miencrap3minerat.item.ModItems;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;


public class PenItem extends Item {

    public PenItem(Properties properties) {
        super(properties);
    }

    // public InteractionResult useOn(final UseOnContext context) {  // use on terrain
    //     return InteractionResult.SUCCESS;
    // }

    static public ItemStack breakItem(int damage, LivingEntity mob, ItemStack itemStack) { 
        InteractionHand hand = mob.getUsedItemHand();
        EquipmentSlot slot = hand.asEquipmentSlot();
        ItemStack result = itemStack.hurtAndConvertOnBreak(damage, Items.AIR, mob, slot);
        return result;
    }

    public InteractionResult use(final Level level, final Player player, final InteractionHand hand) {

        if (!player.getFoodData().needsFood()) return  InteractionResult.PASS;

        player.playSound(SoundEvents.GENERIC_EAT.value());
    
        player.spawnItemParticles(player.getItemInHand(hand), 50);
        player.getFoodData().eat(1, 1.0f);
        
        if (!level.isClientSide()) {
            ItemStack itemstack_broken = WrenchItem.getBreakedItem(1, player, player.getItemInHand(hand));
            return InteractionResult.SUCCESS_SERVER.heldItemTransformedTo(itemstack_broken);
        }

        return InteractionResult.PASS;

    }
}
