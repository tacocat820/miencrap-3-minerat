package gro.weirdshit.miencrap3minerat.client.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import net.minecraft.client.player.ClientInput;
import net.minecraft.world.phys.Vec2;

@Mixin (ClientInput.class)
public interface ClientInputMoveVectorAccessorMixin {
    
    //Vec2 moveVector

    @Accessor("moveVector")
    public void setMoveVector(Vec2 value);

}
