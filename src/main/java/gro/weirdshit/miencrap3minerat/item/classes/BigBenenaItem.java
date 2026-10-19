package gro.weirdshit.miencrap3minerat.item.classes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import gro.weirdshit.miencrap3minerat.ModSounds;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;

public class BigBenenaItem extends Item {
    
    public BigBenenaItem(Properties properties) {
        super(properties.food(new FoodProperties
		.Builder().alwaysEdible().nutrition(12).saturationModifier(19f).build()));
    }
    
    public ItemStack finishUsingItem(final ItemStack itemStack, final Level level, final LivingEntity entity) {

        entity.playSound(ModSounds.TACOBELL);

        if (level.isClientSide() && entity instanceof Player player) {
            LocalDateTime time = java.time.LocalDateTime.now();
            player.sendOverlayMessage(Component.literal(time.format(DateTimeFormatter.ISO_DATE) + " " + time.getHour() + ":" + time.getMinute()));
        }
        return super.finishUsingItem(itemStack, level, entity);
    }
}
