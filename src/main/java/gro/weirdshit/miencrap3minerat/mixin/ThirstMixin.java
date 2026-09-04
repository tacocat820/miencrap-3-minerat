package gro.weirdshit.miencrap3minerat.mixin;

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
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
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
public abstract class ThirstMixin implements ThirstMixinInterface {


    private static final ResourceKey<DamageType> BLOOD_DAMAGE = ResourceKey.create(Registries.DAMAGE_TYPE, Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "blood"));

    private static final EntityDataAccessor<Integer> DATA_THIRST_ID = SynchedEntityData.defineId(Player.class, EntityDataSerializers.INT);
    private int drankBlood;

    @Shadow
    public abstract boolean hurtServer(ServerLevel level, DamageSource source, float damage);

    @Shadow
    public abstract @Nullable GameType gameMode();

    @Inject(at = @At("HEAD"), method = "defineSynchedData")
    protected void defineSynchedData(final SynchedEntityData.Builder entityData, CallbackInfo cir) {
            
        entityData.define(DATA_THIRST_ID, 500);
    }

    public boolean m3m$isAlive() {
        return ((Player)(Object)this).isAlive();
    }
    public Level m3m$level() {
        return ((Player)(Object)this).level();
    }
    public SynchedEntityData m3m$getEntityData() {
        return ((Player)(Object)this).getEntityData();
    }
    public double m3m$getX() {
        return ((Player)(Object)this).getX();
    }
    public double m3m$getY() {
        return ((Player)(Object)this).getY();
    }
    public double m3m$getZ() {
        return ((Player)(Object)this).getZ();
    }
    public Vec3 m3m$getEyePosition() {
        return ((Player)(Object)this).getEyePosition();
    }

    @Inject(at = @At("HEAD"), method = "addAdditionalSaveData")
    protected void addAdditionalSaveData(final ValueOutput output, CallbackInfo cir) {

        output.putFloat("Thirst", this.getThirst());
        output.putInt("drankBlood", drankBlood);

    }    
    @Inject(at = @At("HEAD"), method = "readAdditionalSaveData")
    protected void readAdditionalSaveData(final ValueInput input, CallbackInfo cir) {

        this.setThirst(input.getIntOr("Thirst", 0));
        drankBlood = input.getIntOr("drankBlood", 0);

    }    
    public int getThirst() {
        return (Integer)this.m3m$getEntityData().get(DATA_THIRST_ID);
    }
    public void setThirst(int to) {
        this.m3m$getEntityData().set(DATA_THIRST_ID, to);
    }

    private int timer = 0;
	private static TagKey<Block> DRINKABLE = TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "drinkable"));

    @Inject(at = @At("HEAD"), method = "tick")
    public void tick(CallbackInfo cir) {
        GameType gmode = gameMode();
        if ( gmode == GameType.SURVIVAL || gmode == GameType.ADVENTURE ) { //tsssssssss
            ++timer;

            if (timer >= 40) {
                if (this.m3m$isAlive()) {
                    int thirst = this.getThirst();
                    System.err.println("thirst thirst" + thirst);
                    if (thirst <= 0) {

                        DamageSource damageSource = new DamageSource(
                            this.m3m$level().registryAccess()
                                .lookupOrThrow(Registries.DAMAGE_TYPE)
                                .get(DamageTypes.DRY_OUT.identifier()).orElseThrow());

                        if (this.m3m$level() instanceof ServerLevel serverLevel) {
                            this.hurtServer(serverLevel, damageSource, 1.0f);
                            serverLevel.sendParticles(ParticleTypes.LAVA, 
                                this.m3m$getX(), this.m3m$getY() + 1.5, this.m3m$getZ(), 50, 0.3, 0.3, 0.3, 1.5);
                        }
                    } else this.setThirst(thirst - 1);
                }
                timer = 0;
            }
            if (!this.m3m$isAlive()) {
                this.setThirst(500);
                this.drankBlood = 0;
            }

            BlockState block = this.m3m$level().getBlockState(BlockPos.containing(this.m3m$getEyePosition()));
            if (block.is(DRINKABLE)) {
                Vec3 eyePos = this.m3m$getEyePosition();
                
                if (this.m3m$level() instanceof ServerLevel serverLevel) {
                    serverLevel.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, Blocks.WATER.defaultBlockState()), 
                        eyePos.x, eyePos.y, eyePos.z, 10, 0.1, 0.1, 0.1, 1.5);
                }
                ((Player)(Object)this).playSound(SoundEvents.GENERIC_DRINK.value());
                int thirst = this.getThirst();
                if (thirst <= 1000) this.setThirst(thirst + 1);

                if (this.m3m$level() instanceof ServerLevel serverLevel && block.is(ModFluids.BLOOD.block)) {
                    ++this.drankBlood;
                    if (this.drankBlood > 30) {

                        DamageSource damageSource = new DamageSource(
                            this.m3m$level().registryAccess()
                                .lookupOrThrow(Registries.DAMAGE_TYPE)
                                .get(BLOOD_DAMAGE.identifier()).orElseThrow());
                        
                        this.hurtServer(serverLevel, damageSource, 10000.0f);
                    }
                }
            }
        }
    }
}
