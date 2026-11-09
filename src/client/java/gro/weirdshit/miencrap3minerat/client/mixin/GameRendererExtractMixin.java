package gro.weirdshit.miencrap3minerat.client.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import gro.weirdshit.miencrap3minerat.client.special.credits.Credits;
import gro.weirdshit.miencrap3minerat.client.transitions.overlay_transitions.OverlayTransitions;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.LevelLoadingScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.util.profiling.Profiler;
import net.minecraft.util.profiling.ProfilerFiller;

@Mixin(GameRenderer.class)
public class GameRendererExtractMixin {


    //private static GpuDevice device = RenderSystem.getDevice();
    //private static GpuTexture texture = device.createTexture("a", 8, TextureFormat.RGBA8, 256, 256, 1, 1);
    //private static GpuTextureView textureView = device.createTextureView(texture);

	@Inject(at = @At("RETURN"), method = "extract")
	private void init(final DeltaTracker deltaTracker, final boolean advanceGameTime, CallbackInfo info) {
        //MIENCRAP3MINERAT.LOGGER.info("piss");

        ProfilerFiller profiler = Profiler.get();

        profiler.push("transition_overlay");


        // a person who thinks all the time 
    
        // has nothing to think about except thoughts
        GuiGraphicsExtractor graphics = new GuiGraphicsExtractor(Minecraft.getInstance(), 
            Minecraft.getInstance().gameRenderer.getGameRenderState().guiRenderState, 
            0, 0);
        
        //int alpha = (int)(Mth.sin((double)(Util.getMillis() % 1000) / 1000 * 3.14) * 255);
        //int alpha = 200;  

        if (Minecraft.getInstance().screen instanceof LevelLoadingScreen)
            OverlayTransitions.drawLoading(graphics);
        else 
            OverlayTransitions.draw(graphics);
            
        /*RenderTarget mainRenderTarget = Minecraft.getInstance().getMainRenderTarget();
        GpuSampler sampler = RenderSystem.getSamplerCache().getSampler(AddressMode.REPEAT, AddressMode.REPEAT, FilterMode.NEAREST, FilterMode.LINEAR, false);
        GpuTextureView textureView = mainRenderTarget.getColorTextureView();
        //mainRenderTarget.blitAndBlendToTexture(textureView);
        
        
		
        int drawHeight = graphics.guiHeight() - (int)(Util.getMillis() / 4 % graphics.guiHeight());

        graphics.blit(textureView, sampler, 0, 0, 
            graphics.guiWidth(), drawHeight, 
            0.0f, 1.0f, 1.0f, 0.0f); */

        Credits.draw(graphics);

        profiler.pop();
    }
}
