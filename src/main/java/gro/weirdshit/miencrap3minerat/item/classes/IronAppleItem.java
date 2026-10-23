package gro.weirdshit.miencrap3minerat.item.classes;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.item.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.food.FoodProperties;

public class IronAppleItem extends Item {
    public static final ResourceKey<DamageType> IRON_APPLE_DAMAGE = ResourceKey.create(Registries.DAMAGE_TYPE, Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "iron_apple"));

    public IronAppleItem(Properties properties) {
        super(properties.food(new FoodProperties
		.Builder().alwaysEdible().nutrition(3).saturationModifier(1f).build()));
    }
    
    public ItemStack finishUsingItem(final ItemStack itemStack, final Level level, final LivingEntity entity) {

        entity.playSound(SoundEvents.ANVIL_DESTROY);

        if (level instanceof ServerLevel serverLevel && !level.isClientSide()) {
            DamageSource damageSource = new DamageSource(
					level.registryAccess()
							.lookupOrThrow(Registries.DAMAGE_TYPE)
							.get(IRON_APPLE_DAMAGE.identifier()).orElseThrow());

            entity.hurtServer(serverLevel, damageSource, 6.0f);
            
            ItemEntity n = new ItemEntity(serverLevel, entity.getX(), entity.getY(), entity.getZ(), new ItemStack(ModItems.TOOTH.item, 6));
            n.setDefaultPickUpDelay();
            serverLevel.addFreshEntity(n);
        }
        return super.finishUsingItem(itemStack, level, entity);
    }
}
