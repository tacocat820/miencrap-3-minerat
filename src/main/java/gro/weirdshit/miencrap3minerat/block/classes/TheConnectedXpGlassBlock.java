package gro.weirdshit.miencrap3minerat.block.classes;

import gro.weirdshit.miencrap3minerat.block.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.state.BlockState;

public class TheConnectedXpGlassBlock {
    //blk
    static public class Block extends TransparentConnectedTextureBlock implements EntityBlock {
        public Block(Properties properties) {
            super(properties);
        }

        @Override
        public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
            return new BlockEntity(pos, state);
        }
    }

    //ent
    static public class BlockEntity extends BlockEntityForShader {
        public BlockEntity(BlockPos pos, BlockState state) {
            super(ModBlockEntities.CONNECTED_XP_GLASS_ENTITY_TYPE, pos, state);
        }
    }
    
}

