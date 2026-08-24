package gro.weirdshit.miencrap3minerat.block.classes;

import org.jetbrains.annotations.Nullable;

import com.mojang.serialization.MapCodec;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;

public class SpecificStaticBlock extends BaseEntityBlock {
    public static final MapCodec<SpecificStaticBlock> CODEC = simpleCodec(SpecificStaticBlock::new);

    public SpecificStaticBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends SpecificStaticBlock> codec() {
        return CODEC;
    }

    //protected RenderShape getRenderShape(final BlockState state) {
    //    return RenderShape.INVISIBLE;
    //}

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
        MIENCRAP3MINERAT.LOGGER.info("bitch");

        return InteractionResult.PASS;
    }

    protected RenderShape getRenderShape(final BlockState state) {
        return RenderShape.INVISIBLE;
    }
    
	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new SpecificStaticBlockEntity(pos, state);
	}

    
}
