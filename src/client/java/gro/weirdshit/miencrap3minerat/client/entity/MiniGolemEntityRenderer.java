package gro.weirdshit.miencrap3minerat.client.entity;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.entity.MiniGolemEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;

public class MiniGolemEntityRenderer extends MobRenderer<MiniGolemEntity, MiniGolemEntityRenderState, MiniGolemEntityModel> {
	private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "textures/entity/mini_golem.png");

	public MiniGolemEntityRenderer(EntityRendererProvider.Context context) {
		super(context, new MiniGolemEntityModel(context.bakeLayer(ModEntityModelLayers.MINI_GOLEM)), 0.375f); // 0.375 shadow radius
	}

	@Override
	public MiniGolemEntityRenderState createRenderState() {
		return new MiniGolemEntityRenderState();
	}

	@Override
	public Identifier getTextureLocation(MiniGolemEntityRenderState state) {
		return TEXTURE;
	}
}