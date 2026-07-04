package gro.weirdshit.miencrap3minerat.block.classes;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class SpecificRedIceBlock extends Block {
    public static final MapCodec<SpecificRedIceBlock> CODEC = simpleCodec(SpecificRedIceBlock::new);

    public SpecificRedIceBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends SpecificRedIceBlock> codec() {
        return CODEC;
    }

    @Override 
    public void stepOn(Level level, BlockPos pos, BlockState onState, Entity entity) {
        //level.explode(entity, pos.getX(), pos.getY(), pos.getZ(), 3.3f, true, ExplosionInteraction.TNT);
        Vec3 movement = entity.getDeltaMovement();
        entity.setDeltaMovement(movement.x * 2.0, movement.y * 2.0, movement.z * 2.0);
    }

    @Override
    public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, double fallDistance) {
        //level.destroyBlock(pos, false);
    }
    
}