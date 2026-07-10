package gro.weirdshit.miencrap3minerat.menu;

import gro.weirdshit.miencrap3minerat.menu.classes.CTableMenu;
import gro.weirdshit.miencrap3minerat.menu.classes.WatercoolerMenu;

import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.Registry;
import net.minecraft.world.flag.FeatureFlagSet;

public class ModMenuType {
	public static final MenuType<WatercoolerMenu> WATERCOOLER = register("watercooler", WatercoolerMenu::new);
	public static final MenuType<CTableMenu> CRAFTING_TABLE_2 = register("crafting_table_2", CTableMenu::new);

	public static <T extends AbstractContainerMenu> MenuType<T> register(
					String name,
					MenuType.MenuSupplier<T> constructor
	) {
		return Registry.register(BuiltInRegistries.MENU, name, new MenuType<>(constructor, FeatureFlagSet.of()));
	}
}