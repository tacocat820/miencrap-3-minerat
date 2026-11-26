package gro.weirdshit.miencrap3minerat.block.classes;

import com.mojang.serialization.MapCodec;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Block;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.LevelData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public class SpecificCheckpointBlock extends Block {
    public static final MapCodec<SpecificCheckpointBlock> CODEC = simpleCodec(SpecificCheckpointBlock::new);
    public static final ResourceKey<DamageType> DAMAGE_TYPE = ResourceKey.create(Registries.DAMAGE_TYPE, Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "blood_claude"));

    public SpecificCheckpointBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends SpecificCheckpointBlock> codec() {
        return CODEC;
    }

    @Override 
    public void stepOn(Level level, BlockPos pos, BlockState onState, Entity entity) {
        //level.explode(entity, pos.getX(), pos.getY(), pos.getZ(), 3.3f, true, ExplosionInteraction.TNT);
        
        if (entity instanceof ServerPlayer serverPlayer) {

            ResourceKey<Level> dimension = serverPlayer.level().dimension();
            Vec2 rotationVector = serverPlayer.getRotationVector();
            float yaw = Mth.wrapDegrees(rotationVector.y);
            float pitch = Mth.clamp(rotationVector.x, -90.0F, 90.0F);

            serverPlayer.setRespawnPosition(new ServerPlayer.RespawnConfig(LevelData.RespawnData.of(dimension, pos.above(), yaw, pitch), true), false);

        }
    }

    @Override
    public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, double fallDistance) {
        //level.destroyBlock(pos, false);
    }
    
}