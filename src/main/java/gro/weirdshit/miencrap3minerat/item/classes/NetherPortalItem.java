package gro.weirdshit.miencrap3minerat.item.classes;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import net.minecraft.ChatFormatting;
// import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.NetherPortalBlock;
import net.minecraft.world.level.portal.TeleportTransition;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.minecart.Minecart;

public class NetherPortalItem extends Item {

    public NetherPortalItem(Properties properties) {
        super(properties);
    }
    
    // public InteractionResult useOn(final UseOnContext context) {  // use on terrain
    //     MIENCRAP3MINERAT.LOGGER.warn("SUUUUKAKAKAAAAAAAAAAAAAA\n\nrrrrrrrrrr");
    //     return InteractionResult.SUCCESS;
    // }

    public InteractionResult use(final Level level, final Player player, final InteractionHand hand) {
        MIENCRAP3MINERAT.LOGGER.warn("blyaaaat't'! "+player.getName().getString()+".");
        //Minecraft.getInstance().player.connection.sendChat("bluaaaaaat'");
        player.sendSystemMessage(Component.literal("suuuka")
            .withStyle(ChatFormatting.AQUA, ChatFormatting.BOLD, ChatFormatting.UNDERLINE));
        return InteractionResult.SUCCESS;
    }
}
