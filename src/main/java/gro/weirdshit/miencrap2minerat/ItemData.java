package gro.weirdshit.miencrap2minerat;

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
class GenericItem {
    String name;
    Item.Properties properties = new Item.Properties();

    public GenericItem(String name) {
        this.name = name;
    }

    public GenericItem food(boolean always, int nutrition, float saturation) {
        FoodProperties.Builder food = new FoodProperties.Builder();
        food = food.nutrition(nutrition).saturationModifier(saturation);

        if (always) food = food.alwaysEdible();

        this.properties.food(food.build());

        return this;
    }

    // public Item.Properties configure(DeferredRegister.Items regItems) {
    //     return null;
    // }
    
    public DeferredItem<Item> toDeferredItem(DeferredRegister.Items regItems) {
        final DeferredItem<Item> ITEM = regItems.registerSimpleItem(
            this.name,
            p -> properties
        );

        return ITEM;
    }
}

@EventBusSubscriber(modid = MIENCRAP3MINERAT.MODID, value = Dist.DEDICATED_SERVER) 
public class ItemData {
    
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MIENCRAP3MINERAT.MODID);

    public static final DeferredItem<Item> CHEESEBURGER_CRUMBS = ITEMS.registerSimpleItem("cheeseburger_crumbs", 
        p -> p.food(new FoodProperties.Builder().alwaysEdible().nutrition(3).saturationModifier(1f).build()));

    public static final DeferredItem<Item> CHEESEBURGER = ITEMS.registerSimpleItem("cheeseburger", 
        p -> p.food(new FoodProperties.Builder().alwaysEdible().nutrition(9).saturationModifier(5f).build())
        .usingConvertsTo(CHEESEBURGER_CRUMBS.asItem()));

    public static final DeferredItem<Item> EXAMPLE_ITEM = ITEMS.registerSimpleItem("example_item", p -> p.equippable(EquipmentSlot.HEAD)
        .food(new FoodProperties.Builder().alwaysEdible().nutrition(0).saturationModifier(0f).build()));


}
