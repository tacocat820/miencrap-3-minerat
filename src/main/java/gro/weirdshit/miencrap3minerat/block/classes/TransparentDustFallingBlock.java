package gro.weirdshit.miencrap3minerat.block.classes;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

public class TransparentDustFallingBlock extends DustFallingBlock {
	@Override
	protected boolean skipRendering(final BlockState state, final BlockState neighborState, final Direction direction) {
		return neighborState.is(this) ? true : super.skipRendering(state, neighborState, direction);
	}
    
    public TransparentDustFallingBlock(Properties properties) {
        super(properties);
    }
    
}
