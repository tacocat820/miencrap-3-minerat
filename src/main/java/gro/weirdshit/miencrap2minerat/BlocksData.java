package gro.weirdshit.miencrap2minerat;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.world.level.block.SoundType;

/**
 * GenericBlock
 */
class GenericBlock {
    public GenericBlock(String name, int light, SoundType sound, float destroyT, float explRes) {
        this.name = name; this.lightLevel = light;
        this.sound = sound; this.destroyTime = destroyT;
        this.explosionResistance = explRes;
    }
    String name;
    int lightLevel;
    SoundType sound;
    float destroyTime;
    float explosionResistance;
}

@EventBusSubscriber(modid = MIENCRAP3MINERAT.MODID, value = Dist.DEDICATED_SERVER) 
public class BlocksData {
    // Create a Deferred Register to hold Blocks which will all be registered under the "meincrap3minerat" namespace
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MIENCRAP3MINERAT.MODID);
    // Create a Deferred Register to hold Items which will all be registered under the "meincrap3minerat" namespace
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MIENCRAP3MINERAT.MODID);
    
    //blocks:::::generic
    public static final GenericBlock[] genericBlocks = {
    new GenericBlock("backrooms_lamp", 15, SoundType.GLASS,32000.0f, 32000.0f),
    new GenericBlock("backrooms_carpet", 0, SoundType.WOOL,32000.0f, 32000.0f),
    };

    public static final void genBlocks() {
        for (GenericBlock gblock : genericBlocks ) {
            
            DeferredBlock<Block> BLOCK = BLOCKS.register(
                gblock.name, 
                registryName -> new Block(BlockBehaviour.Properties.of()
                    .setId(ResourceKey.create(Registries.BLOCK, registryName))
                    .destroyTime( gblock.destroyTime )
                    .lightLevel( state -> gblock.lightLevel )
                    .explosionResistance( gblock.explosionResistance )
                    .sound( gblock.sound )
                ));
            ITEMS.registerSimpleBlockItem(gblock.name, BLOCK);

        };
    }

    //bcroomz
    public static final DeferredBlock<Block> BACKROOMS_BLOCK = BLOCKS.register(
        "backrooms", 
        registryName -> new Block(BlockBehaviour.Properties.of()
            .setId(ResourceKey.create(Registries.BLOCK, registryName))
            .destroyTime(16000.0f)
            .explosionResistance(16000.0f)
        ));
    public static final DeferredItem<BlockItem> BACKROOMS_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("backrooms", BACKROOMS_BLOCK);
}