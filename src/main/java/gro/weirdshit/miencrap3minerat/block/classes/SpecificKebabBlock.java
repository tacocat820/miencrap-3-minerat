package gro.weirdshit.miencrap3minerat.block.classes;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class SpecificKebabBlock extends RotatedPillarBlock {
	public static final IntegerProperty COOKING = IntegerProperty.create("cooking", 0, 3);

    TagKey<Block> HOT = TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "hot"));

    public SpecificKebabBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(COOKING, 0));
    }
    
	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(AXIS);
		builder.add(COOKING);
	}

    @Override
    protected boolean isRandomlyTicking(final BlockState state) {
        return true;
    }

    @Override
    protected void randomTick(final BlockState state, final ServerLevel level, final BlockPos pos, final RandomSource random) {
        
        BlockState below = level.getBlockState(pos.below());
		if (below.is(HOT) && !(below.is(Blocks.CAMPFIRE) && !below.getValue(CampfireBlock.LIT))) {
            int progress = state.getValue(COOKING) + 1;

            if (progress > 3 )  {
                level.setBlock(pos, ModBlocks.KEBAB_COOKED.block.defaultBlockState().setValue(AXIS, state.getValue(AXIS)), UPDATE_ALL); 
            }
            else {
                level.setBlockAndUpdate(pos, state.setValue(COOKING, progress));
            }
            
        }
    }

}
