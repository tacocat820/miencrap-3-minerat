package gro.weirdshit.miencrap3minerat.item.classes;

import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.level.Level;

public class UraniumLollipopItem extends Item {

    public UraniumLollipopItem(Properties properties) {
        super(properties);
    }
    
    @Override
    public ItemStack finishUsingItem(final ItemStack itemStack, final Level level, final LivingEntity entity) {
		// MIENCRAP3MINERAT.LOGGER.info("sus "+itemStack);
        if (level instanceof ServerLevel) {
            // entity.hurtServer(serverLevel, level.damageSources().lava(), 9);
            entity.die(level.damageSources().wither());
            // entity.igniteForTicks(80);
        }
        
        Consumable consumable = itemStack.get(DataComponents.CONSUMABLE);
		return consumable != null ? consumable.onConsume(level, entity, itemStack) : itemStack;
	}
}
