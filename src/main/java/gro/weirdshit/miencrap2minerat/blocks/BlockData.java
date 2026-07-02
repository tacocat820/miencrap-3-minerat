package gro.weirdshit.miencrap2minerat.blocks;

import gro.weirdshit.miencrap2minerat.blocks.classes.HorizontallyRotatingBlock;
import gro.weirdshit.miencrap2minerat.blocks.classes.SpecificDrywallBlock;
import gro.weirdshit.miencrap2minerat.blocks.classes.SpecificRedIceBlock;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

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

import org.apache.commons.lang3.ArrayUtils;

/**
 * GenericBlock
 */
class GenericBlock<T extends Block> {
    
    public static List<GenericBlock<?>> registeredBlocks = new ArrayList<GenericBlock<?>>();

    public GenericBlock(Class<T> type, String name, SoundType sound, float destroyT, float explRes) {
        this.type = type;
        this.name = name;
        this.sound = sound; this.destroyTime = destroyT;
        this.explosionResistance = explRes;

        registeredBlocks.add(this);
    }
    String name;
    int lightLevel;
    SoundType sound;
    float destroyTime;
    float explosionResistance;
    boolean noOcclusion = false;
    boolean noCollision = false;
    boolean emissiveRendering = false;
    float friction = 0.6f;

    private Class<T> type;
    public DeferredBlock<T> deferredBlock = null;

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
        emissiveRendering = true;
        return this;
    }

    public BlockBehaviour.Properties configure(DeferredRegister.Blocks regBlocks) {

        BlockBehaviour.Properties properties = BlockBehaviour.Properties.of()
            .setId(ResourceKey.create(Registries.BLOCK, regBlocks.getRegistryName()))
            .destroyTime( destroyTime )
            .explosionResistance( explosionResistance )
            .sound( sound )
            .emissiveRendering(state -> emissiveRendering)
            //.overrideLootTable(ResourceKey.create(, null))
            .friction(friction);

        if (lightLevel != 0) properties = properties.lightLevel( state -> lightLevel );
        if (noCollision) properties = properties.noCollision();
        if (noOcclusion) properties = properties.noOcclusion();

        return properties;

    }
    public DeferredBlock<T> intoDeferredBlock(DeferredRegister.Blocks regBlocks) {

        deferredBlock = BlockData.BLOCKS.register(name, registryName -> {
            
            T constructed = null;
            try {

                BlockBehaviour.Properties properties = configure(regBlocks);
                constructed = type.getDeclaredConstructor(BlockBehaviour.Properties.class).newInstance(properties);
                
            } catch(Exception e) {
                throw new RuntimeException("cannot make class " + type.getSimpleName() + " into a deferred block : " + e.toString());
            }
            
            return constructed;
        });
        return deferredBlock;
    }
    public DeferredBlock<T> intoDeferredBlockArgs(DeferredRegister.Blocks regBlocks, Object[] args) {

        deferredBlock = BlockData.BLOCKS.register(name, registryName -> {

            Class<?>[] argClasses = new Class[args.length + 1];
            for (int i = 0; i < args.length; ++i) {
                argClasses[i] = args[i].getClass();
            }
            argClasses[argClasses.length - 1] = BlockBehaviour.Properties.class;
            
            T constructed = null;
            try {

                BlockBehaviour.Properties properties = configure(regBlocks);
                Constructor<T> constructor = type.getDeclaredConstructor(argClasses); 
                constructed = constructor.newInstance(ArrayUtils.addAll(args, new Object[] { properties } ));
                
            } catch(Exception e) {
                throw new RuntimeException("cannot make class " + type.getSimpleName() + " into a deferred block : " + e.toString());
            }
            
            return constructed;
        });
        return deferredBlock;
    }

    public GenericBlock<T> reg() {
        DeferredBlock<?> BLOCK = this.intoDeferredBlock(BlockData.BLOCKS);
        BlockData.ITEMS.registerSimpleBlockItem(this.name, BLOCK);

        return this;
    }
    public GenericBlock<T> reg(Object... args) {

        MIENCRAP3MINERAT.LOGGER.info("makign args");

        DeferredBlock<?> BLOCK = this.intoDeferredBlockArgs(BlockData.BLOCKS, args);
        BlockData.ITEMS.registerSimpleBlockItem(this.name, BLOCK);

        return this;
    }
}

@EventBusSubscriber(modid = MIENCRAP3MINERAT.MODID, value = Dist.DEDICATED_SERVER) 
public class BlockData {
    
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MIENCRAP3MINERAT.MODID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MIENCRAP3MINERAT.MODID);

    // VLC as modのicon
    public static final GenericBlock<?> VLC = new GenericBlock<FaceRotatingBlock>(FaceRotatingBlock.class, "vlc",SoundType.STONE,0.5f,0.5f).noCollision().noOcclusion();
    public static final DeferredBlock<?> VLC_BLOCK = VLC.intoDeferredBlock(BLOCKS);
    public static final DeferredItem<BlockItem> VLC_BLOCK_ITEM = ITEMS.registerSimpleBlockItem(VLC.name, VLC_BLOCK);

    public static GenericBlock<?> URANIUM_BLOCK = new GenericBlock<Block>(Block.class, "uranium_block", SoundType.STONE,4.0f, 10.0f).light(5).emissiveRendering().reg();
    public static GenericBlock<?> URANIUM_ORE = new GenericBlock<Block>(Block.class, "uranium_ore", SoundType.STONE,4.0f, 10.0f).light(5).emissiveRendering().reg();
    public static GenericBlock<?> BACKROOMS_LAMP = new GenericBlock<Block>(Block.class, "backrooms_lamp", SoundType.GLASS,32000.0f, 32000.0f).light(15).reg();
    public static GenericBlock<?> BACKROOMS_CARPET = new GenericBlock<Block>(Block.class, "backrooms_carpet", SoundType.WOOL,32000.0f, 32000.0f).reg();
    public static GenericBlock<?> BACKROOMS = new GenericBlock<Block>(Block.class, "backrooms",SoundType.STONE,32000.0f,32000.0f).reg();
    public static GenericBlock<?> BLACKSCREEN = new GenericBlock<Block>(Block.class, "blackscreen",SoundType.STONE,-1.0f,-1.0f).reg();
    public static GenericBlock<?> WHITESCREEN = new GenericBlock<Block>(Block.class, "whitescreen",SoundType.STONE,-1.0f,-1.0f).light(15).friction(0.1f).reg();
    public static GenericBlock<?> DIRT = new GenericBlock<Block>(Block.class, "dirt",SoundType.GRAVEL,0.5f,0.5f).noCollision().reg();
    public static GenericBlock<?> CHAIR = new GenericBlock<HorizontallyRotatingBlock>(HorizontallyRotatingBlock.class, "chair",SoundType.STONE,0.5f,0.5f).noCollision().reg();
    public static GenericBlock<?> RED_ICE = new GenericBlock<SpecificRedIceBlock>(SpecificRedIceBlock.class, "red_ice",SoundType.GLASS,3.0f,1.5f).friction(12.0f).noOcclusion().reg();
    public static GenericBlock<?> XP_GLASS = new GenericBlock<Block>(Block.class, "xp_glass",SoundType.GLASS,3.0f,1.5f).friction(12.0f).noOcclusion().reg();
    public static GenericBlock<?> WETWALL = new GenericBlock<Block>(Block.class, "wetwall",SoundType.CROP,3.0f,0.5f).friction(0.07f).reg();
    public static GenericBlock<?> DRYWALL = new GenericBlock<SpecificDrywallBlock>(SpecificDrywallBlock.class, "drywall",SoundType.CROP,32000.0f,32000.0f).reg(WETWALL.deferredBlock);

}