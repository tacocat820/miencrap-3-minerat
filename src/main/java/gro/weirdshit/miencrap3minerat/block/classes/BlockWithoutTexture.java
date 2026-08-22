package gro.weirdshit.miencrap3minerat.block.classes;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;

public class BlockWithoutTexture extends Block {

    public BlockWithoutTexture(Properties properties) {
        super(properties);
    }

    protected RenderShape getRenderShape(final BlockState state) {
        return RenderShape.INVISIBLE;
    }
    
}
