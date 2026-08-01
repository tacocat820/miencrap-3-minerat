package gro.weirdshit.miencrap3minerat.client.entity;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.client.entity.classes.ActualFishRenderer;
import gro.weirdshit.miencrap3minerat.client.entity.classes.DolphinRenderer;
import gro.weirdshit.miencrap3minerat.client.entity.classes.FloatRenderer;
import gro.weirdshit.miencrap3minerat.client.entity.classes.NotjuanRenderer;
import gro.weirdshit.miencrap3minerat.client.entity.classes.NotjuanSpitRenderer;
import gro.weirdshit.miencrap3minerat.client.entity.classes.PsychoRenderer;
import gro.weirdshit.miencrap3minerat.client.entity.classes.PuffercopterRenderer;
import gro.weirdshit.miencrap3minerat.client.entity.classes.RedLightningBoltRenderer;
import gro.weirdshit.miencrap3minerat.client.entity.classes.RepeerRenderer;
import gro.weirdshit.miencrap3minerat.entity.ModEntityTypes;
import gro.weirdshit.miencrap3minerat.entity.NotjuanSpit;
import gro.weirdshit.miencrap3minerat.entity.RedLightningBolt;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.resources.Identifier;

public class ModEntityModelLayers {
	public static final ModelLayerLocation NOTJUAN = createMain("notjuan");
	public static final ModelLayerLocation DOLPHIN = createMain("dolphin");
	public static final ModelLayerLocation ACTUAL_FISH = createMain("actual_fish");
	public static final ModelLayerLocation PSYCHO = createMain("psycho");
	public static final ModelLayerLocation FLOAT = createMain("float");
	public static final ModelLayerLocation REPEER = createMain("repeer");
	public static final ModelLayerLocation PUFFERCOPTER = createMain("puffercopter");
	public static final ModelLayerLocation NOTJUAN_SPIT = createMain("notjuan_spit");
	public static final ModelLayerLocation RED_LIGHTNING_BOLT = createMain("red_lightning_bolt");

	private static ModelLayerLocation createMain(String name) {
		return new ModelLayerLocation(Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, name), "main");
	}

	public static void registerModelLayers() {
		NotjuanRenderer.register(NOTJUAN);
		DolphinRenderer.register(DOLPHIN);
		ActualFishRenderer.register(ACTUAL_FISH);
		PsychoRenderer.register(PSYCHO);
		FloatRenderer.register(FLOAT);
		RepeerRenderer.register(REPEER);
		PuffercopterRenderer.register(PUFFERCOPTER);
		NotjuanSpitRenderer.register(NOTJUAN_SPIT);
		RedLightningBoltRenderer.register(RED_LIGHTNING_BOLT);
	}
}
