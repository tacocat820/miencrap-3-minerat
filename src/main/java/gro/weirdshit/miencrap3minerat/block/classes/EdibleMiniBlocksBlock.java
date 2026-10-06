package gro.weirdshit.miencrap3minerat.block.classes;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class EdibleMiniBlocksBlock extends MiniBlocksBlock implements ImplEdible {
    
    public EdibleMiniBlocksBlock(Properties properties, Block original) {
        super(properties, original);
    }

    @Override
    public InteractionResult eat(final BlockState state, final Level level, final BlockPos pos, final Player player, final BlockHitResult hitResult) {
        

        FoodData fdata = player.getFoodData();
        int food_level = fdata.getFoodLevel();

        if (food_level >= 20 && !player.gameMode().isCreative()) return InteractionResult.PASS;

        fdata.setFoodLevel( food_level + 1 );

        player.playSound(SoundEvents.MULE_EAT);
    

        Vec3i minipos = MiniBlocksBlock.getMiniXYZ(hitResult.getBlockPos(), hitResult.getLocation(), hitResult.getDirection().getOpposite());
        MiniBlocksBlock.removeOneMiniblock(level, state, pos, minipos);
        return InteractionResult.SUCCESS;
        
    }

    @Override
    protected InteractionResult useWithoutItem(final BlockState state, final Level level, final BlockPos pos, final Player player, final BlockHitResult hitResult) {
        return eat(state, level, pos, player, hitResult);
    }

}
