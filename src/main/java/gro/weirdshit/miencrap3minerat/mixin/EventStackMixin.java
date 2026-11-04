package gro.weirdshit.miencrap3minerat.mixin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.serialization.Codec;

import gro.weirdshit.miencrap3minerat.etc.EventRecord;
import gro.weirdshit.miencrap3minerat.etc.EventStackMixinInterface;
import gro.weirdshit.miencrap3minerat.etc.PlayerStartTransition;
import gro.weirdshit.miencrap3minerat.etc.SynchedEventIDMixinInterface;
import net.minecraft.core.GlobalPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

@Mixin(ServerPlayer.class)
public abstract class EventStackMixin implements EventStackMixinInterface {

    private ArrayList<EventRecord> eventStack = new ArrayList<>(Arrays.asList(EventRecord.empty()));
    private ArrayList<EventRecord> spawnpointEventStack = new ArrayList<>(Arrays.asList(EventRecord.empty()));
    private Codec<List<EventRecord>> eventStackCodec = EventRecord.CODEC.listOf();

    @Inject(at = @At("HEAD"), method = "addAdditionalSaveData")
    protected void addAdditionalSaveData(final ValueOutput output, CallbackInfo cir) {

        output.store("eventStack", eventStackCodec, eventStack);
        output.store("spawnpointEventStack", eventStackCodec, spawnpointEventStack);
        

    }    
    @Inject(at = @At("HEAD"), method = "readAdditionalSaveData")
    protected void readAdditionalSaveData(final ValueInput input, CallbackInfo cir) {

        eventStack = new ArrayList<>(input.read("eventStack", eventStackCodec).orElse(Arrays.asList(EventRecord.empty())));
        spawnpointEventStack = new ArrayList<>(input.read("spawnpointEventStack", eventStackCodec).orElse(Arrays.asList(EventRecord.empty())));

        ((SynchedEventIDMixinInterface)(Player)(Object)this).setCurrentEventID(eventStack.getLast().id());

    }    
    
    public void push(String name) {
        ServerPlayer dis = (ServerPlayer)(Object)this;
        eventStack.add(
            new EventRecord(name, new GlobalPos(dis.level().dimension(), dis.blockPosition()))
        );
        ((SynchedEventIDMixinInterface)(Player)(Object)this).setCurrentEventID(name);
    }
    public void push(EventRecord v) {
        eventStack.add(v);
        ((SynchedEventIDMixinInterface)(Player)(Object)this).setCurrentEventID(v.id());
    }
    public void pop() {
        eventStack.removeLast();
        ((SynchedEventIDMixinInterface)(Player)(Object)this).setCurrentEventID(eventStack.getLast().id());
    }
    public void popAndTeleport() {
        EventRecord event = eventStack.removeLast();
        ServerPlayer dis = (ServerPlayer)(Object)this;


        MinecraftServer server = ((Player)(Object)this).level().getServer();
        ServerLevel targetLevel = server.getLevel(event.globalPos().dimension());
    
        dis.teleportTo(targetLevel, 
            event.globalPos().pos().getX(), event.globalPos().pos().getY(), event.globalPos().pos().getZ(),
            Set.of(), 
            ((Player)(Object)this).getYRot(), ((Player)(Object)this).getXRot(), //add direction later i guess
            true
        );

        ((SynchedEventIDMixinInterface)(Player)(Object)this).setCurrentEventID(eventStack.getLast().id());
    }
    public void popAndTransition(String transition, int when) {
        EventRecord event = eventStack.removeLast();
        ServerPlayer dis = (ServerPlayer)(Object)this;

        PlayerStartTransition.run(dis, event.globalPos().dimension(), event.globalPos().pos().getCenter(), transition, when);

        ((SynchedEventIDMixinInterface)(Player)(Object)this).setCurrentEventID(eventStack.getLast().id());
    }
    public EventRecord back() {
        return eventStack.getLast();
    }
    public ArrayList<EventRecord> getSpawnpointArrayStack() {
        return this.spawnpointEventStack;
    }

    @Inject (at = @At("HEAD"), method = "setRespawnPosition")
    public void setRespawnPosition(final ServerPlayer.RespawnConfig respawnConfig, final boolean showMessage, CallbackInfo ci) {
        this.spawnpointEventStack = (ArrayList<EventRecord>)eventStack.clone();
    }

    @Inject (at = @At("HEAD"), method = "copyRespawnPosition")
    public void copyRespawnPosition(final ServerPlayer oldPlayer, CallbackInfo ci) {
        this.spawnpointEventStack = ((EventStackMixinInterface)(Object)oldPlayer).getSpawnpointArrayStack();
        this.eventStack = ((EventStackMixinInterface)(Object)oldPlayer).getSpawnpointArrayStack();    
    }

}
