package gro.weirdshit.miencrap3minerat.item.classes;

import gro.weirdshit.miencrap3minerat.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.Level;

public class WrenchItem extends Item {
    static final SimpleParticleType particle = ParticleTypes.HAPPY_VILLAGER;

    public WrenchItem(Properties properties) {
        super(properties);
    }
    
    @Override
    public InteractionResult useOn(final UseOnContext context) {

        BlockPos pos = context.getClickedPos();
        Level level = context.getLevel();

        if (level instanceof ServerLevel serverLevel) {
            context.getPlayer().playSound(ModSounds.WRENCH);
            serverLevel.sendParticles(particle, pos.getX()+0.5, pos.getY()+0.5, pos.getZ()+0.5, 20, 0.4, 0.4, 0.4, 0.12);
        }

        // level.setBlock(pos, Blocks.CHEST.defaultBlockState(), 0);

		return InteractionResult.SUCCESS;
	}
}
