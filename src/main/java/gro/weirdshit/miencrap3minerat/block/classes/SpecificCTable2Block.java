package gro.weirdshit.miencrap3minerat.block.classes;

import org.jspecify.annotations.Nullable;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.network.chat.Component;

public class SpecificCTable2Block extends BaseEntityBlock {
    public static final MapCodec<SpecificCTable2Block> CODEC = simpleCodec(SpecificCTable2Block::new);
    public SpecificCTable2Block(BlockBehaviour.Properties properties) {super(properties);}
    @Override protected MapCodec<? extends SpecificCTable2Block> codec() {return CODEC;}
    
    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos arg0, BlockState arg1) {
        return new SpecificCTable2BlockEntity(arg0, arg1);
    }


	@Override
	protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
		if (!level.isClientSide() && level.getBlockEntity(pos) instanceof SpecificCTable2BlockEntity menuuu) {
			player.openMenu(menuuu);
		}

		return InteractionResult.SUCCESS;
	}
}