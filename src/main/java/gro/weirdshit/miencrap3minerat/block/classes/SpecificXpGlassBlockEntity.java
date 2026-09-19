package gro.weirdshit.miencrap3minerat.block.classes;

import gro.weirdshit.miencrap3minerat.block.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class SpecificXpGlassBlockEntity extends BlockEntityForShader {
    public SpecificXpGlassBlockEntity(BlockPos pos, BlockState state) {
		super(ModBlockEntities.XP_GLASS_ENTITY_TYPE, pos, state);
	}
}
