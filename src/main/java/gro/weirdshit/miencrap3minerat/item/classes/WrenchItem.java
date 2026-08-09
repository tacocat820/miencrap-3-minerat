package gro.weirdshit.miencrap3minerat.item.classes;

import java.util.Random;

import gro.weirdshit.miencrap3minerat.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.Level;

public class WrenchItem extends Item {
    static final SimpleParticleType particle = ParticleTypes.HAPPY_VILLAGER;

    public WrenchItem(Properties properties) {
        super(properties.durability(Integer.MAX_VALUE));
    }
    
    static Random rand = new Random();

    static public ItemStack getBreakedItem(int damage, LivingEntity mob, ItemStack itemStack) {
        InteractionHand hand = mob.getUsedItemHand();
        EquipmentSlot slot = hand.asEquipmentSlot();
        ItemStack result = itemStack.hurtAndConvertOnBreak(damage, Items.AIR, mob, slot);
        return result;
    }

    @Override
    public InteractionResult useOn(final UseOnContext context) {

        BlockPos pos = context.getClickedPos();
        Level level = context.getLevel();
        
        context.getPlayer().playSound(ModSounds.WRENCH, 1.1f, rand.nextFloat(0.7f, 1.3f));

        if (level instanceof ServerLevel serverLevel) {
            // context.getPlayer().playSound(SoundEvents.ANVIL_DESTROY);
            serverLevel.sendParticles(particle, pos.getX()+0.5, pos.getY()+0.5, pos.getZ()+0.5, 20, 0.4, 0.4, 0.4, 0.12);
            
            ItemStack itemstack_breaked = getBreakedItem(1, context.getPlayer(), context.getItemInHand());
            return InteractionResult.SUCCESS_SERVER.heldItemTransformedTo( itemstack_breaked );
        }

        // level.setBlock(pos, Blocks.CHEST.defaultBlockState(), 0);

		return InteractionResult.PASS;
	}
}
