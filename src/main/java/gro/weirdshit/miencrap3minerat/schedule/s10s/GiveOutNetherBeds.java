package gro.weirdshit.miencrap3minerat.schedule.s10s;

import gro.weirdshit.miencrap3minerat.block.ModBlocks;
import gro.weirdshit.miencrap3minerat.saved_data.WtfLevel;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;

public class GiveOutNetherBeds {

    public static void run(MinecraftServer m, Player p, RandomSource random) {
        
        WtfLevel wtf = WtfLevel.getSavedBlockData(m);
        boolean pn = (p.level().dimension().identifier().equals(BuiltinDimensionTypes.OVERWORLD.identifier()) && wtf.getPostNether());

        if ((p.level().dimension().identifier().equals(BuiltinDimensionTypes.NETHER.identifier()) || p.level().dimension().identifier().equals(BuiltinDimensionTypes.END.identifier()) || pn)
            && p.getRandom().nextInt(10) >= 6
        ) {
            ItemStack bed = new ItemStack(ModBlocks.NETHER_BED.item, 1);

            boolean added = p.getInventory().add(bed);
            if (added) {
                ItemEntity drop = p.drop(new ItemStack(ModBlocks.NETHER_BED.item, 1), false);
                if (drop != null) {
                    drop.makeFakeItem();
                }

                p.containerMenu.broadcastChanges();
            }
        }

    }
    
}
