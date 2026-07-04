package gro.weirdshit.miencrap3minerat.item;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.world.item.Item;

public class RegisteredItem<T extends Item> {
    public static List<RegisteredItem<?>> registeredItems = new ArrayList<RegisteredItem<?>>();

    public T item; 

    public RegisteredItem(T item) {
        this.item = item;
        registeredItems.add(this);
    }
}
