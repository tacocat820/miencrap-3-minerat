package gro.weirdshit.miencrap3minerat.client.entity;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.client.entity.classes.NotjuanRenderer;
import gro.weirdshit.miencrap3minerat.client.entity.classes.NotjuanSpitRenderer;
import gro.weirdshit.miencrap3minerat.entity.ModEntityTypes;
import gro.weirdshit.miencrap3minerat.entity.NotjuanSpit;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.resources.Identifier;

public class ModEntityModelLayers {
	public static final ModelLayerLocation NOTJUAN = createMain("notjuan");
	public static final ModelLayerLocation NOTJUAN_SPIT = createMain("notjuan_spit");

	private static ModelLayerLocation createMain(String name) {
		return new ModelLayerLocation(Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, name), "main");
	}

	public static void registerModelLayers() {
		NotjuanRenderer.register(NOTJUAN);
		NotjuanSpitRenderer.register(NOTJUAN_SPIT);
	}
}
