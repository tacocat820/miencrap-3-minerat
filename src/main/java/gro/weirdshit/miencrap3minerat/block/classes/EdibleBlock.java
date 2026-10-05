package gro.weirdshit.miencrap3minerat.block.classes;

import java.util.Optional;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class EdibleBlock extends Block implements ImplEdible {

    public EdibleBlock(Properties properties) {
        MIENCRAP3MINERAT.LOGGER.info("REGISTERED EdibleBlock");
        super(properties);
    }
    
    @Override
    public InteractionResult eat(final BlockState state, final Level level, final BlockPos pos, final Player player, final BlockHitResult hitResult) {
        BlockState bs = MiniBlocksBlock.convertToMiniblocksBlockstate(state.getBlock()).get(); //а если нет (должно быть!), то пошёл нахуй 
        Vec3i minipos = MiniBlocksBlock.getMiniXYZ(hitResult.getBlockPos(), hitResult.getLocation(), hitResult.getDirection());
        MiniBlocksBlock.removeOneMiniblock(level, bs, pos, minipos);
        return InteractionResult.SUCCESS;
    }

    @Override
    protected InteractionResult useWithoutItem(final BlockState state, final Level level, final BlockPos pos, final Player player, final BlockHitResult hitResult) {
        return eat(state, level, pos, player, hitResult);
    }

}
