package gro.weirdshit.miencrap3minerat.item.classes;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class BreadSwordItem extends Item {
    static final ItemParticleOption breakParticle = new ItemParticleOption(ParticleTypes.ITEM, Items.BREAD);

    public BreadSwordItem(Properties properties) {
        super(properties);
    }
    
    private void breakitem(int damage, LivingEntity mob, ItemStack itemStack) {
        InteractionHand hand = mob.getUsedItemHand();
        EquipmentSlot slot = hand.asEquipmentSlot();
        ItemStack result = itemStack.hurtAndConvertOnBreak(damage, Items.AIR, mob, slot);
        mob.setItemInHand(hand, result);;
    }

    @Override
	public void hurtEnemy(final ItemStack itemStack, final LivingEntity mob, final LivingEntity attacker) {
        // MIENCRAP3MINERAT.LOGGER.info("ATTTTACKKKK "+mob);
        if (mob.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(breakParticle, attacker.getX(), attacker.getY(0.5)+1, attacker.getZ(), 20, 0.5, 0.5, 0.5, 0.1);

            if (attacker instanceof Player player) { //player.level() instanceof ServerLevel serverLevel
                
                FoodData fdata = player.getFoodData();
                int food_level = fdata.getFoodLevel();

                if (food_level >= 20) {
                    breakitem(1, player, itemStack);
                } else {
                    fdata.setFoodLevel( food_level + 1 );
                    breakitem(16, player, itemStack);
                }
            }
            else {
                breakitem(1, attacker, itemStack);
            }
            
        }
	}

}
