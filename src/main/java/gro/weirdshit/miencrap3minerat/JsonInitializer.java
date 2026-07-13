package gro.weirdshit.miencrap3minerat;

import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.fabricmc.fabric.impl.resource.loader.ResourceManagerHelperImpl;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;

public class JsonInitializer {
    public static void generate() {
        // ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(new SimpleSynchronousResourceReloadListener() {

        //     @Override
        //     public Identifier getFabricId() {
        //         // TODO Auto-generated method stub
        //         throw new UnsupportedOperationException("Unimplemented method 'getFabricId'");
        //     }

        //     @Override
        //     public void onResourceManagerReload(ResourceManager resourceManager) {
        //         // TODO Auto-generated method stub
        //         throw new UnsupportedOperationException("Unimplemented method 'onResourceManagerReload'");
        //     }});
    }
}
