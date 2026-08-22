package gro.weirdshit.miencrap3minerat.block.classes;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;

public class PrismarineLampBlock extends Block {
	public static final BooleanProperty ACTIVATED = BooleanProperty.create("activated");

	public PrismarineLampBlock(Properties settings) {
		super(settings);

		registerDefaultState(defaultBlockState().setValue(ACTIVATED, false));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(ACTIVATED);
	}

	@Override
	protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
		if (!player.getAbilities().mayBuild) {
			return InteractionResult.PASS;
		} else {
			boolean activated = state.getValue(ACTIVATED);
			level.setBlockAndUpdate(pos, state.setValue(ACTIVATED, !activated));
			level.playSound(player, pos, SoundEvents.COMPARATOR_CLICK, SoundSource.BLOCKS, 1.0F, 1.0F);
			return InteractionResult.SUCCESS;
		}
	}

	public static int getLuminance(BlockState currentBlockState) {
		boolean activated = currentBlockState.getValue(PrismarineLampBlock.ACTIVATED);
		return activated ? 15 : 0;
	}

}
