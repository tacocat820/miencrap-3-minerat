package gro.weirdshit.miencrap3minerat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Stream;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import gro.weirdshit.miencrap3minerat.menu.classes.CTableMenu;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.fabricmc.fabric.impl.resource.loader.ResourceManagerHelperImpl;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;

public class ModRegister {
    private static final Identifier fabricid = Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "sus");
    private static final Identifier customRecipes = Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "recipes2");
    private static final Gson gson = new GsonBuilder().create(); //.setPrettyPrinting()

    public static ArrayList<CTableMenu.CraftRecipe> RECIPES_2 = new ArrayList<CTableMenu.CraftRecipe>();

    private static <T> T classFromResource(Resource res, Class<T> type) {
        try {
            BufferedReader reader = res.openAsReader();
            T a = gson.fromJson(reader, type);
            return a;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void generate() {
        ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(new SimpleSynchronousResourceReloadListener() {

            @Override
            public Identifier getFabricId() {
                return fabricid;
            }

            @Override
            public void onResourceManagerReload(ResourceManager resourceManager) {
                for (Identifier id : resourceManager.listResources("recipes2", path -> path.toString().endsWith(".json")).keySet()) {
                    Resource res = resourceManager.getResource(id).get();
                    ////
                    CTableMenu.CraftRecipe registered = classFromResource(res, CTableMenu.CraftRecipe.class);
                    ////
                    if (registered != null) {
                        RECIPES_2.add(registered);
                        //
                        MIENCRAP3MINERAT.LOGGER.info("Recipe for crafting table 2 registered! at "+id);
                        registered.grid.display();
                    } else { MIENCRAP3MINERAT.LOGGER.error("Recipe parsing error! at "+id); }
                }
            }});
    }

    public class sus {
    
    }
}
