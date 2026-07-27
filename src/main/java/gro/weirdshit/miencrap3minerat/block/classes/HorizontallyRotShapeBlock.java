package gro.weirdshit.miencrap3minerat.block.classes;

// import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.etc.RotableShape;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class HorizontallyRotShapeBlock extends HorizontallyRotatingBlock {
    private static RotableShape SHAPES;

    public HorizontallyRotShapeBlock(Properties properties, VoxelShape shape) {
        super(properties);
        SHAPES = RotableShape.horiz(shape);
    }
    
    @Override
    protected VoxelShape getShape(final BlockState state, final BlockGetter level, final BlockPos pos, final CollisionContext context) {
        //MIENCRAP3MINERAT.LOGGER.warn("yuyuyu "+FACING + "  " + FACING.getName()+ "  " + FACING.getValue(FACING.getName()));
        VoxelShape shape = SHAPES.get( //палучаем направенье!
            state.getValue(FACING)
        );
        return shape;
    }
}
