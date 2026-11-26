package gro.weirdshit.miencrap3minerat.block.classes;

import com.mojang.serialization.MapCodec;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class SpecificBloodClaudeBlock extends Block {
    public static final MapCodec<SpecificBloodClaudeBlock> CODEC = simpleCodec(SpecificBloodClaudeBlock::new);
    public static final ResourceKey<DamageType> DAMAGE_TYPE = ResourceKey.create(Registries.DAMAGE_TYPE, Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "blood_claude"));

    public SpecificBloodClaudeBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends SpecificBloodClaudeBlock> codec() {
        return CODEC;
    }

    @Override 
    public void stepOn(Level level, BlockPos pos, BlockState onState, Entity entity) {
        //level.explode(entity, pos.getX(), pos.getY(), pos.getZ(), 3.3f, true, ExplosionInteraction.TNT);
        
        if (level instanceof ServerLevel serverLevel) {

			DamageSource damageSource = new DamageSource(
					level.registryAccess()
							.lookupOrThrow(Registries.DAMAGE_TYPE)
							.get(DAMAGE_TYPE.identifier()).orElseThrow());
			entity.hurtServer(serverLevel, damageSource, 8.0f);
        }
    }

    @Override
    public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, double fallDistance) {
        //level.destroyBlock(pos, false);
    }
    
}