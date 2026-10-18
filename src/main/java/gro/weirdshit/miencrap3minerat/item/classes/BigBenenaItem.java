package gro.weirdshit.miencrap3minerat.item.classes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.ModSounds;
import gro.weirdshit.miencrap3minerat.item.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
// import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.NetherPortalBlock;
import net.minecraft.world.level.portal.TeleportTransition;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.minecart.Minecart;
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
