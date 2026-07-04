package gro.weirdshit.miencrap3minerat.block.classes;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.FaceAttachedHorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.network.chat.Component;

public class SpecificWatercoolerBlock extends BaseEntityBlock {
    public static final MapCodec<SpecificWatercoolerBlock> CODEC = simpleCodec(SpecificWatercoolerBlock::new);

	public SpecificWatercoolerBlock(Properties settings) {
		super(settings);
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
		if (!(level.getBlockEntity(pos) instanceof SpecificWatercoolerBlockEntity watercoolerBlockEntity)) {
			return super.useWithoutItem(state, level, pos, player, hit);
		}

		watercoolerBlockEntity.incrementClicks();

		if (level.isClientSide()) {
			player.sendOverlayMessage(Component.literal("You've clicked the block for the " + watercoolerBlockEntity.getClicks() + "th time."));
		}

		return InteractionResult.SUCCESS;
	}
}