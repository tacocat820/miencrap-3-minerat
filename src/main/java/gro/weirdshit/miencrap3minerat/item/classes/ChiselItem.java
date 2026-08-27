package gro.weirdshit.miencrap3minerat.item.classes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.joml.Math;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.block.classes.MiniBlocksBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.Vec3;

public class ChiselItem extends Item {
    public ChiselItem(Properties properties) {
        super(properties);
    }
    
    @Override
    public InteractionResult useOn(final UseOnContext context) {

        BlockPos pos = context.getClickedPos();
        Level level = context.getLevel();
        // Level level = context.;
        Vec3 clickloc = context.getClickLocation();

        BlockState bstate = level.getBlockState(pos);

        if (!(bstate.getBlock() instanceof MiniBlocksBlock)) {
            return InteractionResult.PASS;
        }

        double x = clickloc.x - pos.getX();
        double y = clickloc.y - pos.getY();
        double z = clickloc.z - pos.getZ();

        int bX = Math.clamp((int)Math.ceil(x*1.7)-1, 0, 1);
        int bY = Math.clamp((int)Math.ceil(y*1.7)-1, 0, 1);
        int bZ = Math.clamp((int)Math.ceil(z*1.7)-1, 0, 1);

        MIENCRAP3MINERAT.LOGGER.debug("chisel sub-pos "+ bX + " " + bY + " " + bZ + " ");

        boolean success = MiniBlocksBlock.removeOneMiniblock(level, bstate, pos, bX, bY, bZ);
        if (level.isClientSide() && success) {
            BlockParticleOption breakParticle = new BlockParticleOption(ParticleTypes.BLOCK, bstate);
            
            for (int i = 0; i < 10; i++) {
                context.getPlayer().playSound(bstate.getSoundType().getBreakSound(), 0.125f, 1.2f);
                level.addParticle(breakParticle, clickloc.x+getParticlePosA(), clickloc.y+getParticlePosA(), clickloc.z+getParticlePosA(), 10, 10, 10);
            }

        }
        return (
            success ? (level.isClientSide() ? InteractionResult.SUCCESS : InteractionResult.SUCCESS_SERVER) : InteractionResult.FAIL
        );
	}

    static double getParticlePosA() {
        Random rand = new Random();
        return (rand.nextDouble()-0.5) / 6 ;
    }

}
