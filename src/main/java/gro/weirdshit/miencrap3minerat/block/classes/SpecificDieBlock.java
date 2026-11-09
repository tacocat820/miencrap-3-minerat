package gro.weirdshit.miencrap3minerat.block.classes;


import com.mojang.serialization.MapCodec;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.ModSounds;
import gro.weirdshit.miencrap3minerat.block.ModBlocks;
import gro.weirdshit.miencrap3minerat.effects.ModEffects;
import gro.weirdshit.miencrap3minerat.entity.Sacabambaspis;
import gro.weirdshit.miencrap3minerat.etc.GrantAdvancement;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SpecificDieBlock extends HorizontallyRotatingBlock {
    public static final MapCodec<HorizontallyRotatingBlock> CODEC = simpleCodec(SpecificDieBlock::new);
    public static final ResourceKey<DamageType> LEGO_DAMAGE = ResourceKey.create(Registries.DAMAGE_TYPE, Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "lego"));

    private static final VoxelShape SHAPE = Block.column(10.0, 0.0, 8.0);
    @Override
    protected VoxelShape getShape(final BlockState state, final BlockGetter level, final BlockPos pos, final CollisionContext context) {
        return SHAPE;
    }

    public SpecificDieBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return CODEC;
    }

    @Override 
    public void entityInside(final BlockState state, final Level level, final BlockPos pos, final Entity entity, final InsideBlockEffectApplier effectApplier, final boolean isPrecise) {
    
        if (entity instanceof Sacabambaspis sacabambaspis) {
            MobEffectInstance instance = new MobEffectInstance(ModEffects.LIVEITATION, 5 * 60 * 20, 0, false, true, true);
            sacabambaspis.addEffect(instance);
            entity.playSound(ModSounds.TADAAA);
            entity.playSound(SoundEvents.GENERIC_EXPLODE.value());
            entity.setPos(entity.getX(), entity.getY() + 1.0, entity.getZ());

            // TODO
            //level.setBlockAndUpdate(pos, ModBlocks.LIVE.block.defaultBlockState());
            if (level instanceof ServerLevel serverLevel) {

                serverLevel.sendParticles(ParticleTypes.EXPLOSION, (double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), 
                    30, 0.3, 0.3, 0.3, 0.1);
                serverLevel.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, (double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), 
                    30, 0.3, 0.3, 0.3, 0.1);
                serverLevel.sendParticles(ParticleTypes.CRIT, (double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), 
                    30, 0.3, 0.3, 0.3, 0.1);
                serverLevel.sendParticles(ParticleTypes.WAX_ON, (double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), 
                    30, 0.3, 0.3, 0.3, 0.1);
                serverLevel.sendParticles(ParticleTypes.CLOUD, (double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), 
                    30, 0.3, 0.3, 0.3, 0.1);
            }
            
            return;
        }

        Vec3 d = entity.getDeltaMovement();
        entity.playSound(ModSounds.SCREAM);
        entity.setDeltaMovement(d.x, 10.0f, d.z);

        if (entity instanceof ServerPlayer player) {
            GrantAdvancement.grant(player, Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "die"));
        }
    }
    
}