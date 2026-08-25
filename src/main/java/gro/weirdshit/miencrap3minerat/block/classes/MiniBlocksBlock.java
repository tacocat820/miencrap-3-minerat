package gro.weirdshit.miencrap3minerat.block.classes;

import java.util.HashMap;
import java.util.Map;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
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

	public static final BooleanProperty X0_Y0_Z0 = BooleanProperty.create("blk_000");
	public static final BooleanProperty X1_Y0_Z0 = BooleanProperty.create("blk_100");
	public static final BooleanProperty X0_Y0_Z1 = BooleanProperty.create("blk_001");
	public static final BooleanProperty X1_Y0_Z1 = BooleanProperty.create("blk_101");

	public static final BooleanProperty X0_Y1_Z0 = BooleanProperty.create("blk_010");
	public static final BooleanProperty X1_Y1_Z0 = BooleanProperty.create("blk_110");
	public static final BooleanProperty X0_Y1_Z1 = BooleanProperty.create("blk_011");
	public static final BooleanProperty X1_Y1_Z1 = BooleanProperty.create("blk_111");

    public static final Map<Integer, VoxelShape> SHAPES = createVariants(); //ну и чё, что их 256 штук будет?

    // private final Block base;

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(X0_Y0_Z0);
		builder.add(X1_Y0_Z0);
		builder.add(X0_Y0_Z1);
		builder.add(X1_Y0_Z1);

		builder.add(X0_Y1_Z0);
		builder.add(X1_Y1_Z0);
		builder.add(X0_Y1_Z1);
		builder.add(X1_Y1_Z1);

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

    private boolean[] getBooleans(final BlockState state) {
        boolean[] combination = {
		    state.getValue(X0_Y0_Z0),
		    state.getValue(X1_Y0_Z0),
		    state.getValue(X0_Y0_Z1),
		    state.getValue(X1_Y0_Z1),

		    state.getValue(X0_Y1_Z0),
            state.getValue(X1_Y1_Z0),
            state.getValue(X0_Y1_Z1),
            state.getValue(X1_Y1_Z1),
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
                .setValue(X0_Y0_Z0, true)
                .setValue(X1_Y0_Z0, true)
                .setValue(X0_Y0_Z1, true)
                .setValue(X1_Y0_Z1, true)

                .setValue(X0_Y1_Z0, true)
                .setValue(X1_Y1_Z0, true)
                .setValue(X0_Y1_Z1, true)
                .setValue(X1_Y1_Z1, true)
        );
    }

}
