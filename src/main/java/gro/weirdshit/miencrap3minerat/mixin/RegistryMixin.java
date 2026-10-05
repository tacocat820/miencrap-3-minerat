package gro.weirdshit.miencrap3minerat.mixin;

import java.util.function.Function;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.block.classes.EdibleBlock;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.ColorRGBA;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SandBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SandBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.storage.LevelStorageSource;

@Mixin(Blocks.class)
public class RegistryMixin {


    @Shadow
    public static native Block register(final ResourceKey<Block> id, final BlockBehaviour.Properties properties);
    @Shadow
    public static native Block register(final ResourceKey<Block> id, final Function<BlockBehaviour.Properties, Block> factory, final BlockBehaviour.Properties properties);
    @Shadow
    public static native ResourceKey<Block> vanillaBlockId(final String name);

    @Inject(method="register(Ljava/lang/String;Ljava/util/function/Function;Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)Lnet/minecraft/world/level/block/Block;", at=@At("HEAD"), cancellable = true)
    private static void replaceReg(final String id, final Function<BlockBehaviour.Properties, Block> factory, final BlockBehaviour.Properties properties, CallbackInfoReturnable<Object> cir) {
        Block replaced;

        switch (id) {
            case "stone":
                replaced = register(vanillaBlockId(id), p -> new SandBlock(new ColorRGBA(14406560), p), properties);
                break;

            case "sand":
                replaced = register(vanillaBlockId(id), p -> new Block(p), properties);
                break;


            default:
                return;
        }

        cir.setReturnValue(replaced);
    }

    @Inject(
        method = "register(Lnet/minecraft/resources/ResourceKey; Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties; )Lnet/minecraft/world/level/block/Block;", 
        at = @At("HEAD"), 
        cancellable = true
    )
    private static void replaceSimpleReg(final ResourceKey<Block> id, final BlockBehaviour.Properties properties, CallbackInfoReturnable<Object> cir) {
        Block replaced;

        System.out.print(" *"+id.identifier().toString());//

        switch ( id.identifier().getPath() ) {
            case "melon":
                replaced = register(id, p -> new EdibleBlock(p), properties);
                break;

            default:
                return;
        }

        cir.setReturnValue(replaced);
    }
}
