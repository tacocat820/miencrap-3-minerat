package gro.weirdshit.miencrap3minerat.block.classes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class MiniBlocksBlock extends Block {
    public static final MapCodec<MiniBlocksBlock> CODEC = simpleCodec(MiniBlocksBlock::new);

	public static final BooleanProperty X0_Z0_Y0 = BooleanProperty.create("blk_000");
	public static final BooleanProperty X1_Z0_Y0 = BooleanProperty.create("blk_100");
	public static final BooleanProperty X0_Z0_Y1 = BooleanProperty.create("blk_001");
	public static final BooleanProperty X1_Z0_Y1 = BooleanProperty.create("blk_101");

	public static final BooleanProperty X0_Z1_Y0 = BooleanProperty.create("blk_010");
	public static final BooleanProperty X1_Z1_Y0 = BooleanProperty.create("blk_110");
	public static final BooleanProperty X0_Z1_Y1 = BooleanProperty.create("blk_011");
	public static final BooleanProperty X1_Z1_Y1 = BooleanProperty.create("blk_111");

    public static final Map<Integer, VoxelShape> SHAPES = createVariants(); //ну и чё, что их 256 штук будет?
    public static final Map< List<Integer>, BooleanProperty > MINIBLOCKS_PROPS = definePropsMap();

    // private final Block base;

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(X0_Z0_Y0);
		builder.add(X1_Z0_Y0);
		builder.add(X0_Z0_Y1);
		builder.add(X1_Z0_Y1);

		builder.add(X0_Z1_Y0);
		builder.add(X1_Z1_Y0);
		builder.add(X0_Z1_Y1);
		builder.add(X1_Z1_Y1);

	}

    private static Map< List<Integer>, BooleanProperty > definePropsMap() {
        Map< List<Integer>, BooleanProperty > map = new HashMap<>();

        map.put(List.of(0,0,0), MiniBlocksBlock.X0_Z0_Y0);
        map.put(List.of(1,0,0), MiniBlocksBlock.X1_Z0_Y0);
        map.put(List.of(0,0,1), MiniBlocksBlock.X0_Z1_Y0);
        map.put(List.of(1,0,1), MiniBlocksBlock.X1_Z1_Y0);

        map.put(List.of(0,1,0), MiniBlocksBlock.X0_Z0_Y1);
        map.put(List.of(1,1,0), MiniBlocksBlock.X1_Z0_Y1);
        map.put(List.of(0,1,1), MiniBlocksBlock.X0_Z1_Y1);
        map.put(List.of(1,1,1), MiniBlocksBlock.X1_Z1_Y1);
        
        return map;
    }

    static int create_bitmask(boolean[] flags) {
        int mask = 0;
        for (int i = 0; i < flags.length; i++) {
            mask = mask | ((flags[i] ? 1 : 0) << i);
        }
        return mask;
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
                    int x = (j & 1);
                    int y = ((j >> 1) & 1);
                    int z = ((j >> 2) & 1);
                    
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

    static public void removeOneMiniblock(ServerLevel serverLevel, BlockState blockState, BlockPos blockPos, int x, int y, int z) {
        BooleanProperty prop = MINIBLOCKS_PROPS.get(List.of(x, y, z));

        BlockState newBlockState = blockState.setValue(prop, false);

        if ( create_bitmask(getBooleans(newBlockState)) == 0 ) {
            serverLevel.destroyBlock(blockPos, true);
        } else {
            serverLevel.setBlock(blockPos, newBlockState, UPDATE_ALL_IMMEDIATE);
        }
    }

    static public boolean[] getBooleans(final BlockState state) {
        boolean[] combination = {
		    state.getValue(X0_Z0_Y0),
		    state.getValue(X1_Z0_Y0),
		    state.getValue(X0_Z0_Y1),
		    state.getValue(X1_Z0_Y1),

		    state.getValue(X0_Z1_Y0),
            state.getValue(X1_Z1_Y0),
            state.getValue(X0_Z1_Y1),
            state.getValue(X1_Z1_Y1),
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
    protected MapCodec<? extends MiniBlocksBlock> codec() { return CODEC; }
    
    public MiniBlocksBlock(BlockBehaviour.Properties properties) {
        super(properties);

        registerDefaultState(
            defaultBlockState()
                .setValue(X0_Z0_Y0, true)
                .setValue(X1_Z0_Y0, true)
                .setValue(X0_Z0_Y1, true)
                .setValue(X1_Z0_Y1, true)

                .setValue(X0_Z1_Y0, true)
                .setValue(X1_Z1_Y0, true)
                .setValue(X0_Z1_Y1, true)
                .setValue(X1_Z1_Y1, true)
        );
    }

}
