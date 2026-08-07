package gro.weirdshit.miencrap3minerat.item.classes;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.level.Level;

public class LavaPotionItem extends Item {

    public LavaPotionItem(Properties properties) {
        super(properties);
    }
    
    @Override
    public ItemStack finishUsingItem(final ItemStack itemStack, final Level level, final LivingEntity entity) {
		// MIENCRAP3MINERAT.LOGGER.info("sus "+itemStack);
        if (level instanceof ServerLevel serverLevel) {
            entity.hurtServer(serverLevel, level.damageSources().lava(), 9);
            entity.igniteForTicks(80);
        }
        
        Consumable consumable = itemStack.get(DataComponents.CONSUMABLE);
		return consumable != null ? consumable.onConsume(level, entity, itemStack) : itemStack;
	}

}
