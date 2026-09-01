package gro.weirdshit.miencrap3minerat.block.classes;

import java.util.Map;

import gro.weirdshit.miencrap3minerat.block.ModBlockEntities;
import gro.weirdshit.miencrap3minerat.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class ConnectedTextureBlock extends Block {
    public static final BooleanProperty NORTH;
    public static final BooleanProperty EAST;
    public static final BooleanProperty SOUTH;
    public static final BooleanProperty WEST;
    public static final BooleanProperty UP;
    public static final BooleanProperty DOWN;
    
    static {
        NORTH = BlockStateProperties.NORTH;
        EAST = BlockStateProperties.EAST;
        SOUTH = BlockStateProperties.SOUTH;
        WEST = BlockStateProperties.WEST;
        UP = BlockStateProperties.UP;
        DOWN = BlockStateProperties.DOWN;
    }

    public static final Map<Direction, BooleanProperty> DIRECTIONS_MAP = Map.of(
        Direction.DOWN, DOWN,
        Direction.WEST, WEST,
        Direction.SOUTH, SOUTH,
        Direction.NORTH, NORTH,
        Direction.UP, UP,
        Direction.EAST, EAST
    );


	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(EAST);
		builder.add(WEST);
		builder.add(NORTH);
		builder.add(SOUTH);
		builder.add(UP);
		builder.add(DOWN);
	}

    BlockState updateThatShape(final BlockState state, final LevelReader level, final BlockPos pos) {
        BlockState newState = state;
        for (Direction direction : Direction.values()) {
            // System.out.println("wtf&& "+direction);
            if ( level.getBlockState(pos.relative(direction)).is(this) ) {
                // System.out.println("YEEEAHHHH! "+direction);
                BooleanProperty prop = DIRECTIONS_MAP.get(direction);
                newState = newState.setValue(prop, true);
            }
        }
        return newState;
    }

    @Override
    protected BlockState rotate(final BlockState state, final Rotation rotation) {
        BlockState newstate = state;
        // newstate = newstate.setValue(null, null);
        return newstate;
    }

    @Override
    protected void onPlace(final BlockState state, final Level level, final BlockPos pos, final BlockState oldState, final boolean movedByPiston) {
        level.setBlock(pos, updateThatShape(state, level, pos), UPDATE_ALL);
    }

    @Override
    protected BlockState updateShape(final BlockState state, final LevelReader level, final ScheduledTickAccess ticks, final BlockPos pos, final Direction directionToNeighbour, final BlockPos neighbourPos, final BlockState neighbourState, final RandomSource random) {
        return updateThatShape(state, level, pos);
    }

    static final private boolean defaultValue = false;
    public ConnectedTextureBlock(Properties properties) {
        super(properties);

        registerDefaultState(
            defaultBlockState()
            .setValue(EAST, defaultValue)
            .setValue(WEST, defaultValue)
            .setValue(NORTH, defaultValue)
            .setValue(SOUTH, defaultValue)
            .setValue(UP, defaultValue)
            .setValue(DOWN, defaultValue)
        );
    }
}
