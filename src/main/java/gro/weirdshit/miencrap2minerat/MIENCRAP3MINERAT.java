package gro.weirdshit.miencrap2minerat;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.SoundType;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(MIENCRAP3MINERAT.MODID)
public class MIENCRAP3MINERAT {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "miencrap3minerat";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();
    // Create a Deferred Register to hold Blocks which will all be registered under the "meincrap3minerat" namespace
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);
    // Create a Deferred Register to hold Items which will all be registered under the "meincrap3minerat" namespace
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);
    // Create a Deferred Register to hold CreativeModeTabs which will all be registered under the "meincrap3minerat" namespace
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    // Creates a new Block with the id "meincrap3minerat:example_block", combining the namespace and path

    //URANIUM
    public static final DeferredBlock<Block> URANIUM_BLOCK = BLOCKS.register(
        "uranium_block", 
        registryName -> new Block(BlockBehaviour.Properties.of()
            .setId(ResourceKey.create(Registries.BLOCK, registryName))
            .destroyTime(4.0f)
            .explosionResistance(10.0f)
            //.sound(SoundType.GRAVEL)
            .lightLevel(state -> 7)
        ));
    
    public static final DeferredItem<BlockItem> URANIUM_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("uranium_block", URANIUM_BLOCK);

    //ORE
    public static final DeferredBlock<Block> URANIUM_ORE = BLOCKS.register(
        "uranium_ore", 
        registryName -> new Block(BlockBehaviour.Properties.of()
            .setId(ResourceKey.create(Registries.BLOCK, registryName))
            .destroyTime(4.0f)
            .explosionResistance(10.0f)
            //.sound(SoundType.GRAVEL)
            .lightLevel(state -> 7)
        ));
    
    public static final DeferredItem<BlockItem> URANIUM_ORE_ITEM = ITEMS.registerSimpleBlockItem("uranium_ore", URANIUM_ORE);

    //VLC
    public static final DeferredBlock<Block> VLC_BLOCK = BLOCKS.register(
        "vlc", 
        registryName -> new Block(BlockBehaviour.Properties.of()
            .setId(ResourceKey.create(Registries.BLOCK, registryName))
            .destroyTime(0.5f)
            //.sound(SoundType.GRAVEL)
            .lightLevel(state -> 7)
            .noOcclusion().noCollision()
        ));
    
    public static final DeferredItem<BlockItem> VLC_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("vlc", VLC_BLOCK);

    //BAD ROCK
    public static final DeferredBlock<Block> BAD_ROCK_BLOCK = BLOCKS.register(
        "bad_rock", 
        registryName -> new Block(BlockBehaviour.Properties.of()
            .setId(ResourceKey.create(Registries.BLOCK, registryName))
            .destroyTime(0.1f)
            .sound(SoundType.GRAVEL)
        ));
    
    public static final DeferredItem<BlockItem> BAD_ROCK_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("bad_rock", BAD_ROCK_BLOCK);


    // Creates a new food item with the id "meincrap3minerat:example_id", nutrition 1 and saturation 2
    public static final DeferredItem<Item> EXAMPLE_ITEM = ITEMS.registerSimpleItem("example_item", p -> p.food(new FoodProperties.Builder()
            .alwaysEdible().nutrition(1).saturationModifier(2f).build()));

    // Creates a creative tab with the id "meincrap3minerat:example_tab" for the example item, that is placed after the combat tab
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> EXAMPLE_TAB = CREATIVE_MODE_TABS.register("miencrap3minerat_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.miencrap3minerat")) //The language key for the title of your CreativeModeTab
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> VLC_BLOCK_ITEM.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                for (DeferredHolder<Item,? extends Item> item : ITEMS.getEntries()) {
                    output.accept(item.get());
                }
                for (DeferredHolder<Item,? extends Item> item : BlocksData.ITEMS.getEntries()) {
                    output.accept(item.get());
                }
            }).build());

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public MIENCRAP3MINERAT(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register the Deferred Register to the mod event bus so blocks get registered
        BLOCKS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so items get registered
        ITEMS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so tabs get registered
        CREATIVE_MODE_TABS.register(modEventBus);

        MusicSounds.SOUND_EVENTS.register(modEventBus);

        //BlocksData
        BlocksData.gen_blocks();
        BlocksData.BLOCKS.register(modEventBus);
        BlocksData.ITEMS.register(modEventBus);
        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (MIENCRAP3MINERAT) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        // Some common setup code
        LOGGER.info("HAIIII!! haiii:3 haiiIiii!!!");

        if (Config.LOG_DIRT_BLOCK.getAsBoolean()) {
            LOGGER.info("DIRT BLOCK >> {}", BuiltInRegistries.BLOCK.getKey(Blocks.DIRT));
        }

        LOGGER.info("{}{}", Config.MAGIC_NUMBER_INTRODUCTION.get(), Config.MAGIC_NUMBER.getAsInt());

        Config.ITEM_STRINGS.get().forEach((item) -> LOGGER.info("ITEM >> {}", item));
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            event.accept(URANIUM_BLOCK_ITEM);
        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }
}
