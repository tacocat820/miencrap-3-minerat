package gro.weirdshit.miencrap3minerat.block.classes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.mojang.serialization.MapCodec;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.etc.Point3i;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.WaterloggedTransparentBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class MiniBlocksBlock extends WaterloggedTransparentBlock {
    public static final MapCodec<MiniBlocksBlock> CODEC = simpleCodec(MiniBlocksBlock::new);

	public static final BooleanProperty BLK_000 = BooleanProperty.create("blk_000");
	public static final BooleanProperty BLK_001 = BooleanProperty.create("blk_001");
	public static final BooleanProperty BLK_010 = BooleanProperty.create("blk_010");
	public static final BooleanProperty BLK_011 = BooleanProperty.create("blk_011");
	public static final BooleanProperty BLK_100 = BooleanProperty.create("blk_100");
	public static final BooleanProperty BLK_101 = BooleanProperty.create("blk_101");
	public static final BooleanProperty BLK_110 = BooleanProperty.create("blk_110");
	public static final BooleanProperty BLK_111 = BooleanProperty.create("blk_111");

    public static final Map<Integer, VoxelShape> SHAPES = createVariants(); //ну и чё, что их 256 штук будет?
    public static final Map< Point3i, BooleanProperty > MINIBLOCKS_POSES = definePropsMap();

    public int[] rotatePointY_int(int x, int y, int z, double rad) {
        double radCos = Math.cos(rad);
        double radSin = Math.sin(rad);
        int x2 = (int)(x*( radCos + radSin ));
        int z2 = (int)(z*( radCos - radSin ));
        
        return new int[] {x2, y, z2};
    }

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(BLK_000);
		builder.add(BLK_001);
		builder.add(BLK_010);
		builder.add(BLK_011);

		builder.add(BLK_100);
		builder.add(BLK_101);
		builder.add(BLK_110);
		builder.add(BLK_111);
		builder.add(WATERLOGGED);

	}

    private static Map< Point3i, BooleanProperty > definePropsMap() {
        Map< Point3i, BooleanProperty > map = new HashMap<>();

        map.put(new Point3i(0,0,0), MiniBlocksBlock.BLK_000);
        map.put(new Point3i(0,0,1), MiniBlocksBlock.BLK_001);
        map.put(new Point3i(0,1,0), MiniBlocksBlock.BLK_010);
        map.put(new Point3i(0,1,1), MiniBlocksBlock.BLK_011);

        map.put(new Point3i(1,0,0), MiniBlocksBlock.BLK_100);
        map.put(new Point3i(1,0,1), MiniBlocksBlock.BLK_101);
        map.put(new Point3i(1,1,0), MiniBlocksBlock.BLK_110);
        map.put(new Point3i(1,1,1), MiniBlocksBlock.BLK_111);
        
        return map;
    }

    static int create_bitmask(boolean[] flags) {
        int mask = 0;
        for (int i = 0; i < flags.length; i++) {
            mask = mask | ((flags[i] ? 1 : 0) << i);
        }
        return mask;
    }

    @Override
    protected BlockState updateShape(final BlockState state, final LevelReader level, final ScheduledTickAccess ticks, final BlockPos pos, final Direction directionToNeighbour, final BlockPos neighbourPos, final BlockState neighbourState, final RandomSource random) {
        return super.updateShape(state, level, ticks, pos, directionToNeighbour, neighbourPos, neighbourState, random);
    }

    static private Map<Integer, VoxelShape> createVariants() {
        Map<Integer, VoxelShape> shapes = new HashMap<>();

        for (int i = 0; i < 256; i++) {
            boolean[] combination = new boolean[8];
        
            VoxelShape shape = Shapes.empty();
            System.out.println();

            for (int j = 0; j < 8; j++) {
                //определяем гавно
                boolean bit = ((i >> j) & 1) == 1;
                System.out.print("\t"+bit);
                combination[j] = bit;

                if (bit) {
                    //ааххвыхвхыхвыхвхыхйц
                    int z = (j & 1);
                    int y = ((j >> 1) & 1);
                    int x = ((j >> 2) & 1);
                    
                    //оьъединяем!!
                    shape = Shapes.join(
                        shape,
                        Block.box(x*8, y*8, z*8, (x+1)*8, (y+1)*8, (z+1)*8),
                        BooleanOp.OR
                    );
                }

            }

            Integer bitmask = create_bitmask(combination);
            shapes.put(bitmask, shape); //вот так вот
        }

        return shapes;
    }

    static public boolean removeOneMiniblock(Level level, BlockState blockState, BlockPos blockPos, int x, int y, int z) {
        BooleanProperty prop = MINIBLOCKS_POSES.get(new Point3i(x, y, z));

        if (!blockState.getValue(prop)) {
            return false;
        }

        BlockState newBlockState = blockState.setValue(prop, false);

        if ( create_bitmask(getBooleans(newBlockState)) == 0 ) {
            level.destroyBlock(blockPos, true);
        } else {
            level.setBlockAndUpdate(blockPos, newBlockState);
        }
        return true;
    }

    static public boolean[] getBooleans(final BlockState state) {
        boolean[] combination = {
            state.getValue(BLK_000),
            state.getValue(BLK_001),
            state.getValue(BLK_010),
            state.getValue(BLK_011),

            state.getValue(BLK_100),
            state.getValue(BLK_101),
            state.getValue(BLK_110),
            state.getValue(BLK_111),
        };

        return combination;
    }


    @Override
    protected VoxelShape getShape(final BlockState state, final BlockGetter level, final BlockPos pos, final CollisionContext context) {
        return SHAPES.getOrDefault(
            create_bitmask( getBooleans(state) ),
            Block.cube(5)
        );
    }



    @Override
    protected BlockState rotate(final BlockState state, final Rotation rotation) {
        BlockState new_state = state;

        double rad = switch(rotation) {
            case NONE                   -> 0;
            case CLOCKWISE_180          -> -180*Mth.DEG_TO_RAD;
            case CLOCKWISE_90           -> -90*Mth.DEG_TO_RAD;
            case COUNTERCLOCKWISE_90    -> 90*Mth.DEG_TO_RAD;
        };

        if (rad == 0) {
            return new_state;
        }
        for (Entry<Point3i, BooleanProperty> entry : MINIBLOCKS_POSES.entrySet()) {
            Point3i key_pos = entry.getKey();
            BooleanProperty property = entry.getValue();
            Point3i new_key_pos = key_pos.rotateY_relative(rad, -0.5f, -0.5f);

            // if (state.getValue(property)) {
            //     MIENCRAP3MINERAT.LOGGER.info("\nБыло: "+key_pos+"\nСтало: "+new_key_pos+" странно: "+
            //         "\n0> "+key_pos+
            //         "\n1> "+key_pos.rotateY_relative(rad, -0.5f, -0.5f)+
            //         "\n2> "+key_pos.rotateY_relative(rad, -0.5f, -0.5f).rotateY_relative(rad, -0.5f, -0.5f)+
            //         "\n3> "+key_pos.rotateY_relative(rad, -0.5f, -0.5f).rotateY_relative(rad, -0.5f, -0.5f).rotateY_relative(rad, -0.5f, -0.5f)+
            //         "\n"+
            //         "\n0! "+key_pos+
            //         "\n1! "+key_pos.rotateY(rad)+
            //         "\n2! "+key_pos.rotateY(rad).rotateY(rad)+
            //         "\n3! "+key_pos.rotateY(rad).rotateY(rad).rotateY(rad)
            //     );
            // }

            new_state = new_state.setValue(
                MINIBLOCKS_POSES.get(new_key_pos),
                state.getValue(property)
            );
        }
        return new_state;
    }

    @Override
    protected BlockState mirror(final BlockState state, final Mirror mirror) {  

        Rotation rotation = switch(mirror) {
            case FRONT_BACK -> Rotation.CLOCKWISE_180;
            case LEFT_RIGHT -> Rotation.CLOCKWISE_180;
            case NONE -> Rotation.NONE;
        };

        return rotate(state, rotation);

    }

    @Override
    protected MapCodec<? extends MiniBlocksBlock> codec() { return CODEC; }
    
    public MiniBlocksBlock(BlockBehaviour.Properties properties) {
        super(properties);

        registerDefaultState(
            defaultBlockState()
            .setValue(BLK_000, true)
            .setValue(BLK_001, true)
            .setValue(BLK_010, true)
            .setValue(BLK_011, true)

            .setValue(BLK_100, true)
            .setValue(BLK_101, true)
            .setValue(BLK_110, true)
            .setValue(BLK_111, true)
        );
    }

}
