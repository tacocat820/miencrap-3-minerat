package gro.weirdshit.miencrap3minerat.client.entity;

import net.fabricmc.api.ClientModInitializer;

public class ExampleModCustomEntityClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ModEntityModelLayers.registerModelLayers();
	}
}
