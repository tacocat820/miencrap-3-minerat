package gro.weirdshit.miencrap3minerat.etc;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class Helpers {
    static public Item parse_item(String in_game_id) {
        Identifier id = Identifier.tryParse( in_game_id );
        Item item = BuiltInRegistries.ITEM.get(id).map(Holder::value).orElse(Items.AIR);

        return item;
    }
}
