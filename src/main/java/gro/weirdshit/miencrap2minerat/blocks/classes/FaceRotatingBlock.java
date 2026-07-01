package gro.weirdshit.miencrap2minerat.blocks.classes;

import javax.annotation.Nullable;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FaceAttachedHorizontalDirectionalBlock;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.AttachFace;

public class FaceRotatingBlock extends FaceAttachedHorizontalDirectionalBlock {
    public static final MapCodec<FaceRotatingBlock> CODEC = simpleCodec(FaceRotatingBlock::new);

    public FaceRotatingBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends FaceAttachedHorizontalDirectionalBlock> codec() {
        return CODEC;
    }

    @Nullable
    @Override 
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        
        return super.getStateForPlacement(context);
        
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACE, FACING);
    }
    
}
