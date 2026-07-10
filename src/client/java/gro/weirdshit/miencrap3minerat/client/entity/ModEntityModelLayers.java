package gro.weirdshit.miencrap3minerat.client.entity;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.Identifier;

public class ModEntityModelLayers {
	public static final ModelLayerLocation MINI_GOLEM = createMain("mini_golem");

	private static ModelLayerLocation createMain(String name) {
		return new ModelLayerLocation(Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, name), "main");
	}

	public static void registerModelLayers() {
		ModelLayerRegistry.registerModelLayer(ModEntityModelLayers.MINI_GOLEM, MiniGolemEntityModel::getTexturedModelData);
	}
}
