package gro.weirdshit.miencrap2minerat.blocks;

import gro.weirdshit.miencrap2minerat.blocks.classes.HorizontallyRotatingBlock;
import gro.weirdshit.miencrap2minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap2minerat.blocks.classes.FaceRotatingBlock;
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
import net.minecraft.world.level.block.TestBlock;

/**
 * GenericBlock
 */
class GenericBlock<T extends Block> {
    public GenericBlock(Class<T> type, String name, SoundType sound, float destroyT, float explRes) {
        this.type = type;
        this.name = name;
        this.sound = sound; this.destroyTime = destroyT;
        this.explosionResistance = explRes;
    }
    String name;
    int lightLevel;
    SoundType sound;
    float destroyTime;
    float explosionResistance;
    boolean noOcclusion = false;
    boolean noCollision = false;
    boolean emissiveRend = false;
    float friction = 0.6f;

    private Class<T> type;

    public GenericBlock<T> light(int v) {
        lightLevel = v;
        return this;
    }
    public GenericBlock<T> friction(float v) {
        friction = v;
        return this;
    }
    public GenericBlock<T> noOcclusion() {
        noOcclusion = true;
        return this;
    }
    public GenericBlock<T> noCollision() {
        noCollision = true;
        return this;
    }

    public GenericBlock<T> emissiveRendering() {
        emissiveRend = true;
        return this;
    }

    public BlockBehaviour.Properties configure(DeferredRegister.Blocks regBlocks) {

        BlockBehaviour.Properties properties = BlockBehaviour.Properties.of()
            .setId(ResourceKey.create(Registries.BLOCK, regBlocks.getRegistryName()))
            .destroyTime( destroyTime )
            .explosionResistance( explosionResistance )
            .sound( sound )
            .emissiveRendering(state -> emissiveRend)
            .friction(friction);

        if (lightLevel != 0) properties = properties.lightLevel( state -> lightLevel );
        if (noCollision) properties = properties.noCollision();
        if (noOcclusion) properties = properties.noOcclusion();

        return properties;

    }
    public DeferredBlock<T> intoDeferredBlock(DeferredRegister.Blocks regBlocks) {

        DeferredBlock<T> BLOCK = BlockData.BLOCKS.register(name, registryName -> {
            
            T constructed = null;
            try {

                BlockBehaviour.Properties properties = configure(regBlocks);
                constructed = type.getDeclaredConstructor(BlockBehaviour.Properties.class).newInstance(properties);
                
            } catch(Exception e) {

                throw new RuntimeException("cannot make class " + type.getSimpleName() + " into a deferred block : " + e.toString());

            }
            
            return constructed;
        });
        return BLOCK;
    }
}

@EventBusSubscriber(modid = MIENCRAP3MINERAT.MODID, value = Dist.DEDICATED_SERVER) 
public class BlockData {
    
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MIENCRAP3MINERAT.MODID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MIENCRAP3MINERAT.MODID);
    
    //VLC, as mod's icon
    public static final GenericBlock<?> VLC = new GenericBlock<FaceRotatingBlock>(FaceRotatingBlock.class, "vlc",SoundType.STONE,0.5f,0.5f).noCollision().noOcclusion();
    public static final DeferredBlock<?> VLC_BLOCK = VLC.intoDeferredBlock(BLOCKS);
    public static final DeferredItem<BlockItem> VLC_BLOCK_ITEM = ITEMS.registerSimpleBlockItem(VLC.name, VLC_BLOCK);

    //automatically generated blocks
    public static final GenericBlock<?>[] genericBlocks = {
        //new GenericBlock<Block>(Block.class, "blk", SoundType.GLASS,12.0f, 12.0f),
        new GenericBlock<Block>(Block.class, "uranium_block", SoundType.STONE,4.0f, 10.0f).light(5).emissiveRendering(),
        new GenericBlock<Block>(Block.class, "uranium_ore", SoundType.STONE,4.0f, 10.0f).light(5).emissiveRendering(),
        new GenericBlock<Block>(Block.class, "backrooms_lamp", SoundType.GLASS,32000.0f, 32000.0f).light(15),
        new GenericBlock<Block>(Block.class, "backrooms_carpet", SoundType.WOOL,32000.0f, 32000.0f),
        new GenericBlock<Block>(Block.class, "backrooms",SoundType.STONE,32000.0f,32000.0f),
        new GenericBlock<Block>(Block.class, "blackscreen",SoundType.STONE,-1.0f,-1.0f),
        new GenericBlock<Block>(Block.class, "whitescreen",SoundType.STONE,-1.0f,-1.0f).light(15).friction(0.1f),
        new GenericBlock<Block>(Block.class, "dirt",SoundType.GRAVEL,0.5f,0.5f).noCollision(),
        new GenericBlock<HorizontallyRotatingBlock>(HorizontallyRotatingBlock.class, "chair",SoundType.STONE,0.5f,0.5f).noCollision(),
        new GenericBlock<Block>(Block.class, "red_ice",SoundType.GLASS,3.0f,1.5f).friction(12.0f).noOcclusion(),
        new GenericBlock<Block>(Block.class, "xp_glass",SoundType.GLASS,3.0f,1.5f).friction(12.0f).noOcclusion(),
        new GenericBlock<Block>(Block.class, "drywall",SoundType.CROP,32000.0f,32000.0f),
    };

    //fnction
    public static final void genBlocks() {
        for (GenericBlock<?> genericBlock : genericBlocks ) {
            
            DeferredBlock<?> BLOCK = genericBlock.intoDeferredBlock(BLOCKS);
            ITEMS.registerSimpleBlockItem(genericBlock.name, BLOCK);

        };
    }

}