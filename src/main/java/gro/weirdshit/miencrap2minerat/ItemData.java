package gro.weirdshit.miencrap2minerat;

import java.util.ArrayList;
import java.util.Arrays;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
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
 * GenericItem
 */
class GenericItem<T extends Item> {

    private Class<T> type;

    String name;
    FoodProperties.Builder food = null;

    public GenericItem(Class<T> type, String name) {
        this.type = type;
        this.name = name;
    }


    public GenericItem<T> food(boolean always, int nutrition, float saturation) {
        food = new FoodProperties.Builder();
        food = food.nutrition(nutrition).saturationModifier(saturation);

        if (always) food = food.alwaysEdible();

        return this;
    }
    // public Item.Properties configure(DeferredRegister.Items regItems) {
    //     return null;
    // }


    public Item.Properties configure(Item.Properties properties) {

        if (food != null) properties.food(food.build());
        
        return properties;

    }

    public DeferredItem<T> toDeferredItem(DeferredRegister.Items regItems) {

        DeferredItem<T> BLOCK = regItems.registerItem(name, p -> {

            Item.Properties configuredProperties = configure(p);

            T constructed = null;
            try {
                constructed = type.getDeclaredConstructor(Item.Properties.class).newInstance(configuredProperties);
            } catch (Exception e) {
                throw new RuntimeException("cannot make class " + type.getSimpleName() + " into a deferred item : " + e.toString());
            }
            return constructed;
        });

        return BLOCK;
    }
}

@EventBusSubscriber(modid = MIENCRAP3MINERAT.MODID, value = Dist.DEDICATED_SERVER) 
public class ItemData {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MIENCRAP3MINERAT.MODID);

    // public static final GenericItem[] genericItems = {
    //     new GenericItem("test_item").food(true, 3, 1f)
    // };
    public static final GenericItem<?>[] genericItems = {
        new GenericItem<Item>(Item.class, "test_item").food(true, 3, 1f),
        new GenericItem<Item>(Item.class, "bedian"),
    };


    public static final void genItems() {
        for (GenericItem<?> genericItem : genericItems) {
            DeferredItem<?> ITEM = genericItem.toDeferredItem(ITEMS);
        }
    }

    public static final DeferredItem<Item> CHEESEBURGER_CRUMBS = ITEMS.registerSimpleItem("cheeseburger_crumbs", 
        p -> p.food(new FoodProperties.Builder().alwaysEdible().nutrition(3).saturationModifier(1f).build()));

    public static final DeferredItem<Item> CHEESEBURGER = ITEMS.registerSimpleItem("cheeseburger", 
        p -> p.food(new FoodProperties.Builder().alwaysEdible().nutrition(9).saturationModifier(5f).build())
        .usingConvertsTo(CHEESEBURGER_CRUMBS.asItem()));

    public static final DeferredItem<Item> EXAMPLE_ITEM = ITEMS.registerSimpleItem("example_item", p -> p.equippable(EquipmentSlot.HEAD)
        .food(new FoodProperties.Builder().alwaysEdible().nutrition(0).saturationModifier(0f).build()));


}
