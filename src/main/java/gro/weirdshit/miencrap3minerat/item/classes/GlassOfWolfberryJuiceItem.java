package gro.weirdshit.miencrap3minerat.item.classes;

import gro.weirdshit.miencrap3minerat.entity.WolfberryJuiceProjectile;
import gro.weirdshit.miencrap3minerat.item.ModItems;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class GlassOfWolfberryJuiceItem extends FilledGlassItem {

    public GlassOfWolfberryJuiceItem(Properties properties) {
        super(properties);
    }


    @Override
    public InteractionResult use(final Level level, final Player player, final InteractionHand hand) { 
        WolfberryJuiceProjectile.performSpitAttack(new WolfberryJuiceProjectile(level), player);
        return super.use(level, player, hand);
    }
}
