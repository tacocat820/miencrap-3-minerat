package gro.weirdshit.miencrap3minerat.schedule.s2s;

import gro.weirdshit.miencrap3minerat.item.ModItems;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;

public class Rickroll {
    public static void run(MinecraftServer m, Player p) {

        ItemStack rickroll = new ItemStack(ModItems.RICKROLL.item, 1);

        boolean added = p.getInventory().add(rickroll);
        if (added) {
            p.containerMenu.broadcastChanges();
        }

        if (p.level().dimension().identifier().equals(BuiltinDimensionTypes.END.identifier())) p.addEffect(new MobEffectInstance(MobEffects.RESISTANCE, 500, 3));

    }
}
