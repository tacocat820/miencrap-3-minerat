package gro.weirdshit.miencrap3minerat.block.classes;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public interface ImplEdible {
    public InteractionResult eat(final BlockState state, final Level level, final BlockPos pos, final Player player, final BlockHitResult hitResult);
}