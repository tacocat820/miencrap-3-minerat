package gro.weirdshit.miencrap3minerat.block.classes;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ConcretePowderBlock;
import net.minecraft.world.level.block.FaceAttachedHorizontalDirectionalBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.InsideBlockEffectType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Level.ExplosionInteraction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.phys.Vec3;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;

import java.util.function.Supplier;
import java.lang.Math;

public class SpecificDirtBlock extends Block {

    public static final MapCodec<FaceRotatingBlock> CODEC = simpleCodec(FaceRotatingBlock::new);

    public SpecificDirtBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends FaceAttachedHorizontalDirectionalBlock> codec() {
        return CODEC;
    }

    @Override
   protected void entityInside(
        final BlockState state, final Level level, final BlockPos pos, final Entity entity, final InsideBlockEffectApplier effectApplier, final boolean isPrecise
    ) {
        if (!(entity instanceof LivingEntity) || entity.getInBlockState().is(this)) {
            entity.makeStuckInBlock(state, new Vec3(0.9F, 1.5, 0.9F));
            if (level.isClientSide()) {
            RandomSource random = level.getRandom();
            boolean isMoving = entity.xOld != entity.getX() || entity.zOld != entity.getZ();
            if (isMoving && random.nextBoolean()) {
                level.addParticle(
                    new BlockParticleOption(ParticleTypes.BLOCK, state),
                    entity.getX(),
                    pos.getY() + 1,
                    entity.getZ(),
                    Mth.randomBetween(random, -1.0F, 1.0F) * 0.083333336F,
                    0.05F,
                    Mth.randomBetween(random, -1.0F, 1.0F) * 0.083333336F
                );
            }
            }
        }

        BlockPos position = pos.immutable();
        effectApplier.runBefore(
            InsideBlockEffectType.EXTINGUISH,
            e -> {
            if (level instanceof ServerLevel serverLevel
                && e.isOnFire()
                && (serverLevel.getGameRules().get(GameRules.MOB_GRIEFING) || e instanceof Player)
                && e.mayInteract(serverLevel, position)) {
                level.destroyBlock(position, false);
            }
            }
        );
        effectApplier.apply(InsideBlockEffectType.EXTINGUISH);
    }
    
}