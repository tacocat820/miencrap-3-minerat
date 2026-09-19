package gro.weirdshit.miencrap3minerat.block.classes;

import gro.weirdshit.miencrap3minerat.block.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.TransparentBlock;
import net.minecraft.world.level.block.state.BlockState;

public class TheWaveBlock {
    //blk
    static public class Block extends TransparentBlock implements EntityBlock {
        public Block(Properties properties) {
            super(properties);
        }

        @Override
        protected RenderShape getRenderShape(final BlockState state) {
        return RenderShape.INVISIBLE;
        }

        @Override
        public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
            return new BlockEntity(pos, state);
        }
    }

    //ent
    static public class BlockEntity extends BlockEntityForShader {
        public BlockEntity(BlockPos pos, BlockState state) {
            super(ModBlockEntities.WAVE_BLOCK_ENTITY_TYPE, pos, state);
        }
    }
    
}

