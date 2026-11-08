package gro.weirdshit.miencrap3minerat.schedule.s2s;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.etc.ModDimensionTypes;
import gro.weirdshit.miencrap3minerat.etc.EventStackMixinInterface;
import gro.weirdshit.miencrap3minerat.etc.PlayerEventStack;
import gro.weirdshit.miencrap3minerat.item.ModItems;
import gro.weirdshit.miencrap3minerat.saved_data.WtfLevel;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;

public class Rickroll {

    public static Item chooseItem(ServerPlayer p) {
        if (PlayerEventStack.back(p).id().equals("backrooms")) {
            RandomSource random = RandomSource.create();
            if (random.nextFloat() < 0.05) {
                return ModItems.LARPINGTHEROOMS.item;
            } else {
                return ModItems.LOOPINGTHEROOMS.item;
            }
        };
        if (p.level().dimension().identifier().equals(BuiltinDimensionTypes.END.identifier())) return ModItems.MONITORING.item;
        if (p.level().dimension().identifier().equals(BuiltinDimensionTypes.NETHER.identifier())) return ModItems.TOGETHER_FOREVER.item;
        if (p.level().dimension().identifier().equals(ModDimensionTypes.ORANGE)) return ModItems.PPAP.item;
        return ModItems.RICKROLL.item;
    }

    public static void run(MinecraftServer m, ServerPlayer p) {

        ItemStack rickroll = new ItemStack(chooseItem(p), 1);

        boolean added = p.getInventory().add(rickroll);
        if (added) {
            p.containerMenu.broadcastChanges();
        }

        if (p.level().dimension().identifier().equals(BuiltinDimensionTypes.END.identifier())) p.addEffect(new MobEffectInstance(MobEffects.RESISTANCE, 500, 3));

    }
}
