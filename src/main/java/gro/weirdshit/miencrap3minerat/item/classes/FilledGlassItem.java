package gro.weirdshit.miencrap3minerat.item.classes;

import gro.weirdshit.miencrap3minerat.item.ModItems;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class FilledGlassItem extends Item {

    public FilledGlassItem(Properties properties) {
        super(properties);
    }

    static public ItemStack getBrokenItem(int damage, LivingEntity mob, ItemStack itemStack) {
        InteractionHand hand = mob.getUsedItemHand();
        EquipmentSlot slot = hand.asEquipmentSlot();
        ItemStack result = itemStack.hurtAndConvertOnBreak(damage, ModItems.GLASS_EMPTY.item, mob, slot);
        return result;
    }
    
    @Override
    public InteractionResult use(final Level level, final Player player, final InteractionHand hand) { 

        ItemStack broken = getBrokenItem(1, player, player.getItemInHand(hand));
        return InteractionResult.SUCCESS_SERVER.heldItemTransformedTo( broken );
        
    }
}
