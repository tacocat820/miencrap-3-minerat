package gro.weirdshit.miencrap3minerat.block.classes;

import gro.weirdshit.miencrap3minerat.block.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class SpecificXpGlassBlockEntity extends BlockEntity {
    
    public SpecificXpGlassBlockEntity(BlockPos pos, BlockState state) {
		super(ModBlockEntities.XP_GLASS_ENTITY_TYPE, pos, state);
	}

	public boolean shouldRenderFace(final Direction direction) {
		return Block.shouldRenderFace(this.getBlockState(), this.level.getBlockState(this.getBlockPos().relative(direction)), direction);
	}
}
