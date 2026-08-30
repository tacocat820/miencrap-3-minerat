package gro.weirdshit.miencrap3minerat.block.classes;

import gro.weirdshit.miencrap3minerat.etc.ModShapes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class SpecificPackageBlock extends HorizontallyRotShapeBlock {
	public static final BooleanProperty OPENED = BooleanProperty.create("opened");

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(OPENED);
        builder.add(FACING);
    }

    public SpecificPackageBlock(Properties properties) {
        super(properties);
        this.shape(ModShapes.PACKAGE_SHAPE);
        registerDefaultState(
            defaultBlockState().setValue(OPENED, false)
        );
    }
    
}
