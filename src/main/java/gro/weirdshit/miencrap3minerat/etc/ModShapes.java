package gro.weirdshit.miencrap3minerat.etc;

import net.minecraft.util.Util;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ModShapes {
    public static void generate() {}
    // private static final VoxelShape SHAPE_INSIDE = Block.column((double)12.0F, (double)4.0F, (double)16.0F);
    // protected static final VoxelShape SHAPE = (VoxelShape)Util.make(() -> {
    //     return Shapes.join(
    //         Shapes.block(),
    //         Shapes.or(Block.column((double)16.0F, (double)8.0F, (double)0.0F, (double)3.0F),
    //             new VoxelShape[]{Block.column((double)8.0F, (double)16.0F, (double)0.0F, (double)3.0F),
    //                 Block.column((double)12.0F, (double)0.0F, (double)3.0F), SHAPE_INSIDE}),
    //         BooleanOp.ONLY_FIRST
    //     );
    // });
    public static final VoxelShape CHAIR_SHAPE = (VoxelShape)Util.make(() -> {
        return Shapes.join(
            Block.column(14, 0, 11),
            Block.box(1, 11, 1, 15, 24, 3),
            BooleanOp.OR
        );
    });
}
