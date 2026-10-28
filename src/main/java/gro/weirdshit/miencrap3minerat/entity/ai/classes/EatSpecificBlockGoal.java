package gro.weirdshit.miencrap3minerat.entity.ai.classes;

import java.util.EnumSet;
import java.util.function.Predicate;

import gro.weirdshit.miencrap3minerat.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gamerules.GameRules;

public class EatSpecificBlockGoal extends Goal {
    private Block yummyBlock;
    private Predicate<BlockState> IS_EDIBLE = state -> state.is(yummyBlock);
    private final Mob mob;
    private final Level level;
    private int eatAnimationTick;

   public EatSpecificBlockGoal(final Mob mob, Block yummyBlock) {
      this.mob = mob;
      this.yummyBlock = yummyBlock;
      this.level = mob.level();
      this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK, Goal.Flag.JUMP));
   }

   @Override
   public boolean canUse() {
      BlockPos pos = this.mob.blockPosition();
      // System.out.println("rrrr "+IS_EDIBLE.test(this.level.getBlockState(pos)));
      return this.level.getBlockState(pos.below()).is(ModBlocks.CARAMEL_BRICKS.block);
   }

   @Override
   public void start() {
      this.eatAnimationTick = this.adjustedTickDelay(40);
      this.level.broadcastEntityEvent(this.mob, (byte)10);
      this.mob.getNavigation().stop();
   }

   @Override
   public void stop() {
      this.eatAnimationTick = 0;
   }

   @Override
   public boolean canContinueToUse() {
      return this.eatAnimationTick > 0;
   }

   @Override
   public void tick() {
      this.eatAnimationTick = Math.max(0, this.eatAnimationTick - 1);
      if (this.eatAnimationTick == this.adjustedTickDelay(4)) {
         BlockPos pos = this.mob.blockPosition();
         if (IS_EDIBLE.test(this.level.getBlockState(pos))) {
            if (getServerLevel(this.level).getGameRules().get(GameRules.MOB_GRIEFING)) {
               this.level.destroyBlock(pos, false);
            }

            this.mob.ate();
         } else {
            BlockPos below = pos.below();
            if (getServerLevel(this.level).getGameRules().get(GameRules.MOB_GRIEFING)) {
                this.level.levelEvent(2001, below, Block.getId(yummyBlock.defaultBlockState()));
                // this.level.setBlock(below, Blocks.DIRT.defaultBlockState(), 2);
                this.level.destroyBlock(below, false);
            }

            this.mob.ate();
         }
      }
   }
}