package gro.weirdshit.miencrap3minerat.block.classes;

import com.mojang.serialization.MapCodec;

import gro.weirdshit.miencrap3minerat.menu.classes.WatercoolerMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.FaceAttachedHorizontalDirectionalBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.network.chat.Component;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.Block;

public class SpecificWatercoolerBlock extends BaseEntityBlock {
	public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;
    public static final MapCodec<SpecificWatercoolerBlock> CODEC = simpleCodec(SpecificWatercoolerBlock::new);

	public SpecificWatercoolerBlock(Properties settings) {
		super(settings);
    	this.registerDefaultState((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue(FACING, Direction.NORTH)));
	}

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new SpecificWatercoolerBlockEntity(pos, state);
	}

	@Override
	protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
		if (!level.isClientSide() && level.getBlockEntity(pos) instanceof SpecificWatercoolerBlockEntity watercoolerBlockEntity) {
			player.openMenu(watercoolerBlockEntity);
		}

		return InteractionResult.SUCCESS;
	}

	protected BlockState rotate(final BlockState state, final Rotation rotation) {
    	return (BlockState)state.setValue(FACING, rotation.rotate((Direction)state.getValue(FACING)));
	}

	public BlockState getStateForPlacement(final BlockPlaceContext context) {
    	return (BlockState)this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
   	}

	protected void createBlockStateDefinition(final Builder<Block, BlockState> builder) {
    	builder.add(FACING);
	}

	public static void tick(Level world, BlockPos blockPos, BlockState blockState, SpecificWatercoolerBlockEntity watercoolerBlockEntity) {
		//
	}
}