package gro.weirdshit.miencrap3minerat.block.classes;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BubbleColumnBlock;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Level.ExplosionInteraction;
import java.util.Optional;

public class SpecificEarsFluidBlock extends LiquidBlock {
    
    public SpecificEarsFluidBlock(final FlowingFluid fluid, final BlockBehaviour.Properties properties) {
        super(fluid, properties);
    }

    @Override
    protected VoxelShape getCollisionShape(final BlockState state, final BlockGetter level, final BlockPos pos, final CollisionContext context) {
        if (context.alwaysCollideWithFluid()) {
            return Shapes.block();
        } else {
            return (VoxelShape)this.ifMobIsColliding(context).map(v -> Shapes.block()).filter((liquidStableShape) -> true).orElse(Shapes.empty());
        }
    }

   private Optional<LivingEntity> ifMobIsColliding(final CollisionContext context) {
      if (context instanceof EntityCollisionContext entityCollisionContext) {
         Entity var4 = entityCollisionContext.getEntity();
         if (var4 instanceof LivingEntity mob) {
            return Optional.of(mob);
         }
      }

      return Optional.empty();
   }

    @Override 
    public void stepOn(Level level, BlockPos pos, BlockState onState, Entity entity) {
        // no
        //level.explode(entity, pos.getX(), pos.getY(), pos.getZ(), 3.3f, true, ExplosionInteraction.TNT);
    }

    @Override 
    protected VoxelShape getShape(final BlockState state, final BlockGetter level, final BlockPos pos, final CollisionContext context) {
        return Shapes.block();
    }

    protected void onPlace(final BlockState state, final Level level, final BlockPos pos, final BlockState oldState, final boolean movedByPiston) {
        level.scheduleTick(pos, state.getFluidState().getType(), this.fluid.getTickDelay(level));
    }
}
