package gro.weirdshit.miencrap3minerat.block.classes;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.ColorRGBA;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.SandBlock;
import net.minecraft.world.level.block.state.BlockState;

public class SpecificStonedSandBlock extends SandBlock {

    public SpecificStonedSandBlock(ColorRGBA dustColor, Properties properties) {
        super(dustColor, properties);
    }
    
    //no it's not laggy

    @Override
    protected BlockState updateShape(final BlockState state, final LevelReader level, final ScheduledTickAccess ticks, final BlockPos pos, final Direction directionToNeighbour, final BlockPos neighbourPos, final BlockState neighbourState, final RandomSource random) {
        if (random.nextFloat() > 0.5f) {
            ticks.scheduleTick(pos, this, this.getDelayAfterPlace());
        }
        return state;

        // if (random.nextFloat() > 0.95f) {
        //     return super.updateShape(state, level, ticks, pos, directionToNeighbour, neighbourPos, neighbourState, random);
        // } else {
        //     return state;
        // }
        
    }

}
