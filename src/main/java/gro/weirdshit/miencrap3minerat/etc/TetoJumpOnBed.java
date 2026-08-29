package gro.weirdshit.miencrap3minerat.etc;

import java.util.Optional;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.behavior.JumpOnBed;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

public class TetoJumpOnBed extends JumpOnBed {


    public TetoJumpOnBed(final float speedModifier) {
        super(speedModifier);
    }

    protected boolean checkExtraStartConditions(final ServerLevel level, final Mob body) {
        return this.nearBed(level, body);
    }
    private boolean nearBed(final ServerLevel level, final Mob body) {
        return this.onOrOverBed(level, body) || this.getNearestBed(body).isPresent();
    }
    private boolean onOrOverBed(final ServerLevel level, final Mob body) {
        BlockPos bodyPos = body.blockPosition();
        BlockPos oneBelow = bodyPos.below();
        return this.isBed(level, bodyPos) || this.isBed(level, oneBelow);
    }
    private boolean isBed(final ServerLevel level, final BlockPos bodyPos) {
        return level.getBlockState(bodyPos).is(BlockTags.BEDS);
    }
    private Optional<BlockPos> getNearestBed(final Mob body) {
        return body.getBrain().getMemory(MemoryModuleType.NEAREST_BED);
    }

}
