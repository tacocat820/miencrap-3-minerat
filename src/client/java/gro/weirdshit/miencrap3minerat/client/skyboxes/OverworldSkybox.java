package gro.weirdshit.miencrap3minerat.client.skyboxes;

import org.joml.Vector4f;

import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.buffers.Std140Builder;
import com.mojang.blaze3d.buffers.Std140SizeCalculator;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.systems.RenderPass;
import com.mojang.blaze3d.systems.RenderSystem;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.client.special.ClientWtf;
import gro.weirdshit.miencrap3minerat.client.special.CurrentBiome;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MappableRingBuffer;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;

class SkyboxTexture {
    private final Identifier textureIdentifier;
    private AbstractTexture texture;

    public SkyboxTexture(String path) {
        this.textureIdentifier = Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, path);
    }

    public AbstractTexture getTexture() {
        if (this.texture == null)  texture = Minecraft.getInstance().getTextureManager().getTexture(textureIdentifier);
        return this.texture;
    }
}

enum Sky {
    DAY_1(new SkyboxTexture("textures/sky/overworld_day_1.png")),
    DAY_2(new SkyboxTexture("textures/sky/overworld_day_2.png")),
    DAY_PINK(new SkyboxTexture("textures/sky/overworld_day_pink.png")),
    DAY_PINKNT(new SkyboxTexture("textures/sky/overworld_day_pinknt.png")),
    DAY_BIGCITY(new SkyboxTexture("textures/sky/overworld_day_bigcity.png")),
    DAY_BIGCITY_EVIL(new SkyboxTexture("textures/sky/overworld_day_bigcity_evil.png")),
    DAY_INVERTED(new SkyboxTexture("textures/sky/overworld_day_inverted.png")),
    DAY_PURPLE(new SkyboxTexture("textures/sky/overworld_day_purple.png")),
    DAY_BLACK(new SkyboxTexture("textures/sky/overworld_day_black.png")),
    EVENING(new SkyboxTexture("textures/sky/overworld_evening.png")),
    NIGHT(new SkyboxTexture("textures/sky/overworld_night.png")),
    NIGHT_BIGCITY(new SkyboxTexture("textures/sky/overworld_night_bigcity.png"));

    public SkyboxTexture texture;
    private Sky(SkyboxTexture texture) {
        this.texture = texture;
    }
}

public class OverworldSkybox extends Skybox {

    private static final int UBO_SIZE = new Std140SizeCalculator().putFloat().get();
    private final MappableRingBuffer ubo = new MappableRingBuffer(() -> "OW Skybox UBO", 130, UBO_SIZE);
   
    //textures/sky.png
    private final SkyboxTexture skyDay_1 = new SkyboxTexture("textures/sky/overworld_day_1.png");
    
    private Vector4f colorModulator = new Vector4f(1f, 1f, 1f, 1f);

    private int brightness = 5;
    private int process = 0;
    Sky transitionFrom = Sky.NIGHT;
    Sky transitionTo   = Sky.DAY_1;

    public OverworldSkybox() {
        super(Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "textures/sky.png"));
    }
    public RenderPipeline getPipeline() {
        return Pipelines.SKYBOX_OVERWORLD_PIPELINE;
    }
    public AbstractTexture getTexture() {
        return this.skyDay_1.getTexture();
    }

    public void transition(Sky to) {
        if (process != 100 && process != 0) return;

        if (!transitionTo.equals(to)) {
            transitionFrom = transitionTo;
            transitionTo = to; process = 0;
        }
    }

    TagKey<Biome> SKY_2_BIOME = TagKey.create(Registries.BIOME, Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "has_sky2"));
    TagKey<Biome> BIGCITY_BIOME = TagKey.create(Registries.BIOME, Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "bigcity"));
    TagKey<Biome> INVERTED_SKY_BIOME = TagKey.create(Registries.BIOME, Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "has_inverted_sky"));
    TagKey<Biome> PINK_SKY_BIOME = TagKey.create(Registries.BIOME, Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "has_pink_sky"));
    TagKey<Biome> BLACK_SKY_BIOME = TagKey.create(Registries.BIOME, Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "has_black_sky"));

    public void preRenderPass() {

        this.ubo.rotate();

        float dt = Minecraft.getInstance().level.getOverworldClockTime() % 24000;

        if (ClientWtf.wtf >= 4) transition(Sky.NIGHT_BIGCITY);
        else if (dt < 10_000) {
            if (ClientWtf.wtf >= 3) transition(Sky.DAY_INVERTED);
            else if (ClientWtf.wtf >= 2 && CurrentBiome.is(Biomes.DESERT)) transition(Sky.DAY_2); 
            else if (ClientWtf.wtf >= 2 && CurrentBiome.is(PINK_SKY_BIOME)) transition(Sky.DAY_PINKNT); 
            else if (ClientWtf.wtf >= 2 && CurrentBiome.is(BIGCITY_BIOME)) transition(Sky.DAY_BIGCITY_EVIL); 
            else if (CurrentBiome.is(INVERTED_SKY_BIOME)) transition(Sky.DAY_INVERTED);
            else if (CurrentBiome.is(Biomes.DESERT)) transition(Sky.DAY_PURPLE);
            else if (CurrentBiome.is(PINK_SKY_BIOME)) transition(Sky.DAY_PINK);
            else if (CurrentBiome.is(BLACK_SKY_BIOME)) transition(Sky.DAY_BLACK);
            else if (CurrentBiome.is(BIGCITY_BIOME)) transition(Sky.DAY_BIGCITY);
            else if (ClientWtf.wtf >= 2) transition(Sky.DAY_PURPLE);
            else if (CurrentBiome.is(SKY_2_BIOME)) transition(Sky.DAY_2);
            else transition(Sky.DAY_1);
        } else if (dt < 14_000) {
            transition(Sky.EVENING);
        } else {
            if (CurrentBiome.is(BIGCITY_BIOME)) transition(Sky.NIGHT_BIGCITY);
            else transition(Sky.NIGHT);
        }

        transitionFrom.texture.getTexture();
        transitionTo.texture.getTexture();

        if (process < 100) process += 1;

        try (GpuBuffer.MappedView view = RenderSystem.getDevice()
            .createCommandEncoder()
            .mapBuffer(this.ubo.currentBuffer(), false, true)) 
        {    
            Std140Builder.intoBuffer(view.data())
                .putFloat(0.01f *
                     process);
        }

    }
    
    public void renderPasses(RenderPass renderPass) {
        
        renderPass.setUniform("Whatever", this.ubo.currentBuffer());

        renderPass.bindTexture("Sampler0", transitionFrom.texture.getTexture().getTextureView(), transitionFrom.texture.getTexture().getSampler());
        renderPass.bindTexture("Sampler1", transitionTo.texture.getTexture().getTextureView(), transitionTo.texture.getTexture().getSampler());
    }
    public Vector4f getColorModulator() {
        this.colorModulator.x = 0.1f * brightness;
        this.colorModulator.y = 0.1f * brightness;
        this.colorModulator.z = 0.1f * brightness;
        return this.colorModulator;
    }
}
