package gro.weirdshit.miencrap3minerat.etc;

import net.minecraft.util.Util;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ModShapes {
    public static void generate() {}

    //for chair!!!!!!!!
    public static final VoxelShape CHAIR_SHAPE = (VoxelShape)Util.make(() -> {
        return Shapes.join(
            Block.column(14, 0, 11),
            Block.box(1, 11, 1, 15, 24, 3),
            BooleanOp.OR
        );
    });

    public static final VoxelShape WEDGE = (VoxelShape)Util.make(() -> {
        return Shapes.or(
            Block.box(0, 0, 0, 16, 16, 4),
            Block.box(0, 0, 0, 16, 12, 8),
            Block.box(0, 0, 0, 16, 8, 12),
            Block.box(0, 0, 0, 16, 4, 16)
        );
    });

    public static final VoxelShape CARPET_SHAPE = (VoxelShape)Util.make(() -> {
        return Block.box(0, 0, 0, 16, 2, 16);
    });

    public static final VoxelShape VLC_SHAPE = Block.column(10.0, 0.0, 8.0);
    public static final VoxelShape PACKAGE_SHAPE = Block.column(10.0, 0.0, 8.0);
    public static final VoxelShape RTX_SHAPE = Block.box(-4, 0, 3, 20, 4, 14);
    public static final VoxelShape ICICLE_SHAPE = Block.box(4, 0, 4, 12, 16, 12);

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

}
