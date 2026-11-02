package gro.weirdshit.miencrap3minerat.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import net.minecraft.world.entity.boss.enderdragon.EnderDragon;

@Mixin (EnderDragon.class)
public class EnderDragonMixin {
    @ModifyConstant(method = "checkCrystals", constant = @Constant(intValue = 10))
    private int changeHealingChance(int original) {
        return 1;
    }
    @ModifyConstant(method = "checkCrystals", constant = @Constant(floatValue = 1.0f))
    private float changeConstant(float original) {
        return 6.0f;
    }
}
