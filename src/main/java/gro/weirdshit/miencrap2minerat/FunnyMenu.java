package gro.weirdshit.miencrap2minerat;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.TitleScreen;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;


import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.neoforged.neoforge.client.event.ScreenEvent;

import net.minecraft.client.resources.sounds.TickableSoundInstance;

import java.util.ArrayList;
import java.util.Random;

import java.util.List;

enum Direction {
    UP_RIGHT, UP_LEFT, DOWN_RIGHT, DOWN_LEFT;
}

final class MenuButtonInfo {
    public int chx;
    public int chy;

    public MenuButtonInfo() {
        this.chx = -1;
        this.chy = 1;
    }

}

@EventBusSubscriber(modid = MIENCRAP3MINERAT.MODID, value = Dist.CLIENT) 
public class FunnyMenu {

    static Random rand = new Random();
    static List<MenuButtonInfo> buttons = new ArrayList<MenuButtonInfo>();
    
    @SubscribeEvent
    public static void onInit(ScreenEvent.Init.Post event) {
        int width = event.getScreen().width;
        int height = event.getScreen().height;

        if (event.getScreen() instanceof TitleScreen) {            
            for (GuiEventListener e : event.getScreen().children()) {

                buttons.add(new MenuButtonInfo());
                
                if (e instanceof Button button) {
                    button.setX(rand.nextInt(width - 90));
                    button.setY(rand.nextInt(height - 40));
                }
            }
        }
    }
    
    @SubscribeEvent
    public static void onRender(ScreenEvent.Render.Post event) {

        //if (buttons.size() < event.getScreen().children().size()) return;

        int width = event.getScreen().width;
        int height = event.getScreen().height;

        if (event.getScreen() instanceof TitleScreen) {

            var screenChildren = event.getScreen().children();

            for (int i = 0; i < screenChildren.size(); ++i) {
                if (event.getScreen().children().get(i) instanceof Button button) {
                    

                    button.setY(button.getY() + buttons.get(i).chy);
                    if (button.getY() <= 0 || button.getY() + button.getHeight() > height) buttons.get(i).chy *= -1;

                    button.setX(button.getX() + buttons.get(i).chx);
                    if (button.getX() <= 0 || button.getX() + button.getWidth() > width) buttons.get(i).chx *= -1;
                    
                }
            }
        }
    }
}
