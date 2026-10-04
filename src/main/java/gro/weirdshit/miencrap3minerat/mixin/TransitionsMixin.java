package gro.weirdshit.miencrap3minerat.mixin;

import java.util.Set;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.block.ModFluids;
import gro.weirdshit.miencrap3minerat.etc.ThirstMixinInterface;
import gro.weirdshit.miencrap3minerat.etc.TransitionsMixinInterface;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;

@Mixin(Player.class)
public abstract class TransitionsMixin implements TransitionsMixinInterface {

    private int transition = 0;
    private ResourceKey<Level> transitionDimension = null;
    private Vec3 transitionPos = null;

    @Inject(at = @At("HEAD"), method = "addAdditionalSaveData")
    protected void addAdditionalSaveData(final ValueOutput output, CallbackInfo cir) {

        output.putBoolean("transitioning", transition >= 0);
        output.store("transitionDimension", Level.RESOURCE_KEY_CODEC, Level.END);
        output.store("transitionPos", Vec3.CODEC, transitionPos);
        

    }    
    @Inject(at = @At("HEAD"), method = "readAdditionalSaveData")
    protected void readAdditionalSaveData(final ValueInput input, CallbackInfo cir) {

        transition = -1 + (input.getBooleanOr("transitioning", false) ? 1 : 0);
        transitionDimension = input.read("transitionDimension", Level.RESOURCE_KEY_CODEC).orElse(Level.OVERWORLD);
        transitionPos = input.read("transitionPos", Vec3.CODEC).orElse(new Vec3(0,0,0));

    }    


    public void teleportTo(ResourceKey<Level> dimension, Vec3 pos, int when) {
        this.transitionDimension = dimension; this.transitionPos = pos;
        this.transition = when;
    }

    @Inject(at = @At("HEAD"), method = "tick")
    public void tick(CallbackInfo cir) {

        if (transition >= 0) {
            --transition;

            if (transition < 0) {

                if (transitionDimension == null || transitionPos == null) return;

                MinecraftServer server = ((Player)(Object)this).level().getServer();
                ServerLevel targetLevel = server.getLevel(transitionDimension);

                ((Player)(Object)this).teleportTo(targetLevel, transitionPos.x, transitionPos.y, transitionPos.z, 
                    Set.of(), 
                    ((Player)(Object)this).getYRot(), ((Player)(Object)this).getXRot(), //add direction later i guess
                    true);
            }
        }

    }
}
