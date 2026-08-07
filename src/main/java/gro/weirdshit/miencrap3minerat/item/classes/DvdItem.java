package gro.weirdshit.miencrap3minerat.item.classes;

import gro.weirdshit.miencrap3minerat.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

public class DvdItem extends Item {

    public DvdItem(Properties properties) {
        super(properties);
    }
    
    @Override
    public InteractionResult useOn(final UseOnContext context) {

        BlockPos pos = context.getClickedPos();
        Level level = context.getLevel();

        level.setBlock(pos, Blocks.CHEST.defaultBlockState(), 0);

		return InteractionResult.SUCCESS;
	}
}
