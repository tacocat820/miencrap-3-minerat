package gro.weirdshit.miencrap3minerat.etc;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;

public class GrantAdvancement {
    public static void grant(ServerPlayer player, Identifier advancement) {
        AdvancementHolder adv = player.level().getServer().getAdvancements().get(advancement);

        AdvancementProgress progress = player.getAdvancements().getOrStartProgress(adv);

        for (String criterion : progress.getRemainingCriteria()) {
            player.getAdvancements().award(adv, criterion);
        }
    } 
}
