package gro.weirdshit.miencrap3minerat.block;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;

public class RegisteredBlock {
    public static List<RegisteredBlock> registeredBlocks = new ArrayList<RegisteredBlock>();

	public Block block; 
	public BlockItem item;

	public RegisteredBlock(Block block, BlockItem item) {
        this.block = block; this.item = item;
		registeredBlocks.add(this);
    }
}