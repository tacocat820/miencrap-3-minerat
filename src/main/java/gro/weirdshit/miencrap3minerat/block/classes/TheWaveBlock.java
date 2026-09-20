package gro.weirdshit.miencrap3minerat.block.classes;

import gro.weirdshit.miencrap3minerat.block.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.InsideBlockEffectType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.TransparentBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.phys.Vec3;

public class TheWaveBlock {
    //blk
    static public class Block extends TransparentBlock implements EntityBlock {
        public Block(Properties properties) {
            super(properties);
        }

        @Override
        protected RenderShape getRenderShape(final BlockState state) {
        return RenderShape.INVISIBLE;
        }

        @Override
        public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
            return new BlockEntity(pos, state);
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
                    if (isMoving) {
                        level.addParticle(
                            ParticleTypes.CLOUD,
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
                    }
                    }
                );
                effectApplier.apply(InsideBlockEffectType.EXTINGUISH);
            }
    }

    //ent
    static public class BlockEntity extends BlockEntityForShader {
        public BlockEntity(BlockPos pos, BlockState state) {
            super(ModBlockEntities.WAVE_BLOCK_ENTITY_TYPE, pos, state);
        }
    }
    
}

