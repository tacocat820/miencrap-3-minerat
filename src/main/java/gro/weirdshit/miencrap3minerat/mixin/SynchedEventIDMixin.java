package gro.weirdshit.miencrap3minerat.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import gro.weirdshit.miencrap3minerat.etc.SynchedEventIDMixinInterface;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.player.Player;

@Mixin(Player.class)
public abstract class SynchedEventIDMixin implements SynchedEventIDMixinInterface {

    private static final EntityDataAccessor<String> DATA_EVENT_ID = SynchedEntityData.defineId(Player.class, EntityDataSerializers.STRING);

    
    @Inject(at = @At("HEAD"), method = "defineSynchedData")
    protected void defineSynchedData(final SynchedEntityData.Builder entityData, CallbackInfo cir) {
        //if (((Player)(Object)this).level().isClientSide()) MIENCRAP3MINERAT.LOGGER.info("CLIENT ALERT");
        entityData.define(DATA_EVENT_ID, "none");
    }
    public SynchedEntityData m3m$getEntityData() {
        return ((Player)(Object)this).getEntityData();
    }
 
    
    public void setCurrentEventID(String to) {
        this.m3m$getEntityData().set(DATA_EVENT_ID, to);
    }
    public String getCurrentEventID() {
        return this.m3m$getEntityData().get(DATA_EVENT_ID);
    }
}
