package gro.weirdshit.miencrap3minerat.item.classes;

import gro.weirdshit.miencrap3minerat.saved_data.WtfLevel;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Level.ExplosionInteraction;

public class UraniumLollipopItem extends Item {

    public UraniumLollipopItem(Properties properties) {
        super(properties);
    }
    
    @Override
    public ItemStack finishUsingItem(final ItemStack itemStack, final Level level, final LivingEntity entity) {
		// MIENCRAP3MINERAT.LOGGER.info("sus "+itemStack);
        if (level instanceof ServerLevel serverLevel && entity instanceof Player player) {


			float wtf = WtfLevel.getWtfForLevel(serverLevel);
			if (wtf > 700.0f) {
				level.explode(entity, entity.getX(), entity.getY(), entity.getZ(), 100.0f, ExplosionInteraction.BLOCK);
			} else if (wtf > 100.0f) {
                entity.hurtServer(serverLevel, level.damageSources().playerAttack(player), 10);
            } else {
                entity.hurtServer(serverLevel, level.damageSources().playerAttack(player), 200);

            }
           
            // entity.die(level.damageSources().wither());
            // entity.igniteForTicks(80);
        }
        
        Consumable consumable = itemStack.get(DataComponents.CONSUMABLE);
		return consumable != null ? consumable.onConsume(level, entity, itemStack) : itemStack;
	}
}
