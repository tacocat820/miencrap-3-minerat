package gro.weirdshit.miencrap3minerat;

import java.io.BufferedReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import gro.weirdshit.miencrap3minerat.ModRegister.Recipe2_Registry;
import gro.weirdshit.miencrap3minerat.menu.classes.CTableMenu;
import gro.weirdshit.miencrap3minerat.menu.classes.CTableMenu.CraftRecipe;
import gro.weirdshit.miencrap3minerat.menu.classes.CTableMenu.ItemsSet;
import gro.weirdshit.miencrap3minerat.menu.classes.CTableMenu.ShapedRecipe;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.impl.resource.loader.ResourceManagerHelperImpl;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.feline.Cat;
import net.minecraft.world.level.block.Block;
import net.fabricmc.fabric.api.resource.v1.ResourceLoader;

record Pair<K, V>(K key, V value) {}
//
class JsonClassParser {
    public HashMap<String, Class<?>> parsers = new HashMap<>();
    Class<?> fallbackClass;
    String key_field;

    public JsonClassParser(String key_field, Class<?> fallbackClass) {
        this.key_field = key_field;
        this.fallbackClass = fallbackClass;
    }

    public JsonClassParser add(String field_value, Class<?> classType) {
        parsers.put(field_value, classType);
        return this;
    }

    public Class<?> get(String field_value) {
        return parsers.getOrDefault(field_value, fallbackClass);
    }
}

public class ModRegister {
    private static final Identifier fabricid = Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "sus");
    private static final Identifier customRecipes = Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "recipes2");
   
    private static final Gson gson = new GsonBuilder()
        .create(); //.setPrettyPrinting()

    //public static ArrayList<CTableMenu.CraftRecipe> RECIPES_2 = new ArrayList<CTableMenu.CraftRecipe>();
    public static Recipe2_Registry RECIPES_2 = new Recipe2_Registry();
    public static ConvertableToMiniblocks_Registry MINIBLOCKS = new ConvertableToMiniblocks_Registry();

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
    private static <T> T classFromJsonObject(JsonObject jobject, Class<T> type) {
        T a = gson.fromJson(jobject, type);
        return a;
    }
    // private static <T> T classesFromResource(Resource res, JsonClassParser classparser, Class<T> type) {
    //     try {
    //         BufferedReader reader = res.openAsReader();
    //         JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();

    //         String value = jsonObject.get(classparser.key_field).getAsString();
    //         Class<?> type2 = classparser.get(value);

    //         T a = (T) classFromResource(res, type2);
            
    //         return a;
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }

    //     return null;
    // }

    public static class ConvertableToMiniblocks_Registry {
        private Map<Block, Block> reg = new HashMap<>();

        public Optional<Block> get(Block original) {
            Block result = reg.get(original);
            if (result != null) {
                return Optional.of(result);
            } else {
                return Optional.empty();
            }
        }
        public void set(Block original, Block miniblocks) {
            reg.put(original, miniblocks);
        }

        public ConvertableToMiniblocks_Registry() {}
    }

    public static class Recipe2_Registry { //реестр
        private HashMap<Integer, ArrayList<CraftRecipe>> recipes = new HashMap<>();
        
        public Recipe2_Registry() { /* хз */}

        public Optional<CraftRecipe> get(CTableMenu.ItemsGrid optimizedGrid) {
            ItemsSet itemset = optimizedGrid.toItemsSet();
            // if (itemset == null) {
            //     MIENCRAP3MINERAT.LOGGER.error("wtf!! ");
            //     optimizedGrid.display();
            // }
            ArrayList<CraftRecipe> matched_recipes = recipes.getOrDefault(
                itemset.hashCode(), /* default: */ new ArrayList<>()
            );

			for (CraftRecipe recipe : matched_recipes) {
				MIENCRAP3MINERAT.LOGGER.info("(#) matched: " + recipe);
				if (
                    recipe instanceof CTableMenu.ShapedRecipe
                    && ((CTableMenu.ShapedRecipe)recipe).grid.equal( optimizedGrid )
                ) { //shaped
					return Optional.of(recipe);
				
                } else if (
                    recipe instanceof CTableMenu.ShapelessRecipe
                    && ((CTableMenu.ShapelessRecipe)recipe).set.equal( itemset )
                ) { //shapeless
                    return Optional.of(recipe);

				}
			}
            return Optional.empty();
        };

        public void add(CraftRecipe recipe) {
            CTableMenu.ItemsSet set = recipe.toItemsSet();
            MIENCRAP3MINERAT.LOGGER.debug(set.items+" iiiii");

            ArrayList<CraftRecipe> arr = recipes.computeIfAbsent(set.hashCode(), k -> new ArrayList<>());

            if (recipe instanceof CTableMenu.ShapedRecipe) {
                arr
                    .addFirst(recipe);
            } else if (recipe instanceof CTableMenu.ShapelessRecipe) {
                arr
                    .addLast(recipe);
            }
        }

        public void addList(List<CraftRecipe> list) {
            for (CraftRecipe recipe : list) {
                // MIENCRAP3MINERAT.LOGGER.debug("111111 "+recipe);
                add(recipe);
            }
        }

        public void clear() {
            recipes.clear();
        }
    }

    public static void generate() {
        
        ResourceLoader.get(PackType.SERVER_DATA).registerReloadListener(Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "recipes2"), new PreparableReloadListener() {

            @Override
            public CompletableFuture<Void> reload(SharedState currentReload, Executor taskExecutor, PreparationBarrier preparationBarrier, Executor reloadExecutor) {
                CompletableFuture<List<CraftRecipe>> recipeList = CompletableFuture.supplyAsync(() -> {
                    var recipes = new ArrayList<CraftRecipe>();
                    var resources = currentReload.resourceManager().listResources("recipes2", path -> path.toString().endsWith(".json"));

                    MIENCRAP3MINERAT.LOGGER.debug("shitty");

                    resources.forEach((i, resource) -> {
                        CTableMenu.CraftRecipe registered;

                        BufferedReader reader;
                        try {
                            reader = resource.openAsReader();
                        } catch (IOException e) {
                            e.printStackTrace();
                            return;
                        }
                        JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
                        String value = jsonObject.get("type").getAsString();

                        if (value.equals("shaped")) {
                            registered = classFromJsonObject(jsonObject, CTableMenu.ShapedRecipe.class);
                        } else if (value.equals("shapeless")) {
                            registered = classFromJsonObject(jsonObject, CTableMenu.ShapelessRecipe.class);
                        } else {
                            MIENCRAP3MINERAT.LOGGER.warn("Inknown type: "+i+" Ignored.");
                            return;
                        }

                        if (registered == null) {
                            MIENCRAP3MINERAT.LOGGER.warn("Resource "+i+" isn't valid. Ignored.");
                            return;
                        }
                        MIENCRAP3MINERAT.LOGGER.info("Resource "+i);
                        //CTableMenu.CraftRecipe registered = classFromResource(resource, CTableMenu.CraftRecipe.class);
                        //MIENCRAP3MINERAT.LOGGER.info("resource " + registered.result.name);

                        recipes.add(registered);
                    });

                    MIENCRAP3MINERAT.LOGGER.debug("end shitty");

                    return recipes;
                }, taskExecutor);

                CompletableFuture<List<CraftRecipe>> applyFuture = recipeList.thenCompose(preparationBarrier::wait); 
                /*
                preparedCompletableFuture.thenAccept((data) -> {
                    RECIPES_2.add(data);
                }); */

                return applyFuture.thenRunAsync(() -> {
                    RECIPES_2.clear();
                    List<CraftRecipe> list = applyFuture.join();
                    RECIPES_2.addList(list);
                    MIENCRAP3MINERAT.LOGGER.warn(list.size()+"list wtf"+list+"\nfewf: "+RECIPES_2.recipes);
                    MIENCRAP3MINERAT.LOGGER.info("loaded " + RECIPES_2.recipes.size() + " recipes 2!");
                    // RECIPES_2.addAll(applyFuture.join());
                    // MIENCRAP3MINERAT.LOGGER.info("loaded " + RECIPES_2.size() + " recipes 2");
                }, reloadExecutor);
            }}
        );
    }
}
