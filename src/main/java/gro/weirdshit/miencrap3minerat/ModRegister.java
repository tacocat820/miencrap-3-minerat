package gro.weirdshit.miencrap3minerat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.stream.Stream;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import gro.weirdshit.miencrap3minerat.menu.classes.CTableMenu;
import gro.weirdshit.miencrap3minerat.menu.classes.CTableMenu.CraftRecipe;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.impl.resource.loader.ResourceManagerHelperImpl;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;

import net.fabricmc.fabric.api.resource.v1.ResourceLoader;

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
        
        ResourceLoader.get(PackType.SERVER_DATA).registerReloadListener(Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "recipes2"), new PreparableReloadListener() {

            @Override
            public CompletableFuture<Void> reload(SharedState currentReload, Executor taskExecutor, PreparationBarrier preparationBarrier, Executor reloadExecutor) {
                CompletableFuture<List<CraftRecipe>> recipeList = CompletableFuture.supplyAsync(() -> {
                    var recipes = new ArrayList<CraftRecipe>();
                    var resources = currentReload.resourceManager().listResources("recipes2", path -> path.toString().endsWith(".json"));

                    MIENCRAP3MINERAT.LOGGER.info("shitty");

                    resources.forEach((i, resource) -> {
                        CTableMenu.CraftRecipe registered = classFromResource(resource, CTableMenu.CraftRecipe.class);
                        MIENCRAP3MINERAT.LOGGER.info("resource " + registered.result.name);

                        recipes.add(registered);
                    });
                    MIENCRAP3MINERAT.LOGGER.info("addeded");

                    return recipes;
                }, taskExecutor);

                CompletableFuture<List<CraftRecipe>> applyFuture = recipeList.thenCompose(preparationBarrier::wait); 
                /*
                preparedCompletableFuture.thenAccept((data) -> {
                    RECIPES_2.add(data);
                }); */

                return applyFuture.thenRunAsync(() -> {
                    RECIPES_2.clear();
                    RECIPES_2.addAll(applyFuture.join());
                    MIENCRAP3MINERAT.LOGGER.info("loaded " + RECIPES_2.size() + " recipes");
                }, reloadExecutor);
            }}
        );
    }

    private static <T> void generate_json_server_data(Class<T> class_type, ArrayList<T> REGISTER_ARRAY, String data_path ) {
        ResourceLoader.get(PackType.SERVER_DATA).registerReloadListener(Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, data_path), new PreparableReloadListener() {
            @Override
            public CompletableFuture<Void> reload(SharedState currentReload, Executor taskExecutor, PreparationBarrier preparationBarrier, Executor reloadExecutor) {
                CompletableFuture<List<T>> dataList = CompletableFuture.supplyAsync(() -> {
                    var res_data = new ArrayList<T>();
                    var resources = currentReload.resourceManager().listResources(data_path, path -> path.toString().endsWith(".json"));

                    MIENCRAP3MINERAT.LOGGER.info("shitty");

                    resources.forEach((i, resource) -> {
                        T registered = classFromResource(resource, class_type);
                        //MIENCRAP3MINERAT.LOGGER.info("resource " + registered.result.name);

                        res_data.add(registered);
                    });
                    MIENCRAP3MINERAT.LOGGER.info("addeded");

                    return res_data;
                }, taskExecutor);

                CompletableFuture<List<T>> applyFuture = dataList.thenCompose(preparationBarrier::wait); 
                /*
                preparedCompletableFuture.thenAccept((data) -> {
                    RECIPES_2.add(data);
                }); */

                return applyFuture.thenRunAsync(() -> {
                    REGISTER_ARRAY.clear();
                    REGISTER_ARRAY.addAll(applyFuture.join());
                    MIENCRAP3MINERAT.LOGGER.info("loaded " + REGISTER_ARRAY.size() + " '" + class_type + "'");
                }, reloadExecutor);
            }}
        );
    }
}
