package gro.weirdshit.miencrap3minerat.block.classes;

// import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.etc.RotableShape;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.block.Block;

public class HorizontallyRotShapeBlock extends HorizontallyRotatingBlock {
    public RotableShape SHAPES = RotableShape.single( Block.cube(16.0) );

    @Override
    public HorizontallyRotShapeBlock shape(VoxelShape shape) {
        this.SHAPES = RotableShape.horiz(shape);
        // this.SHAPE = shape;
        return this;
    }

    @Override
    protected VoxelShape getShape(final BlockState state, final BlockGetter level, final BlockPos pos, final CollisionContext context) {
        return this.SHAPES.get( state.getValue(FACING) );
    }

    //------//
    public HorizontallyRotShapeBlock(Properties properties) {
        super(properties);
    }
}
