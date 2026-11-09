package gro.weirdshit.miencrap3minerat.client.special.credits;

import java.util.Random;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.client.MusicSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.ARGB;

public class HeavenCredits extends AbstractCredits {

    public HeavenCredits() {
        super();
        super.start();
    }
    protected double getTimeDivisor() {
        return 20.0;
    }
    private int multiplier = 35;
    private int multiplier2 = 70;
    private int p = 250;

    private Identifier texture = Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "textures/sky/orange_main.png");

    protected SoundEvent getMusic() {
        return MusicSounds.BLOCKS_AND_SHIT_MEIJI;
    }
    public void preDraw(GuiGraphicsExtractor graphics, long time) {

        double progress = Math.min((double)(time - start) / 1000, 1);  

        int left = 0;

        //renderLayer, texture, x, y, u, v, width, height, textureWidth, textureHeight
        graphics.blit(RenderPipelines.GUI_TEXTURED, texture, 
            0, 0, 0, 0, left + (int)(graphics.guiWidth() * progress), graphics.guiHeight(), 600, 389);

    }
    public void postDraw(GuiGraphicsExtractor graphics, long time) {
        graphics.text(Minecraft.getInstance().font, "MIENCRAP 3 MINERAT", graphics.guiWidth() - 100, graphics.guiHeight() - 100, ARGB.color(255, 255, 0, 0));

        if (time - start > 4200 * 20) {
            if (new Random().nextInt(50) > 40)
                System.exit(33);
            else 
                System.exit(0);
        }
    }

    private CreditsLine[] lines = {
        new CreditsTranslatableLine(2 * multiplier, "heaven_credits.hello", 255, 0, 0),
        new CreditsTranslatableLine(3 * multiplier, "heaven_credits.1"),
        new CreditsTranslatableLine(4 * multiplier, "heaven_credits.1.1", 60, 60, 180),
        new CreditsTranslatableLine(5 * multiplier, "heaven_credits.1.2", 180, 180, 60),
        new CreditsTranslatableLine(6 * multiplier, "heaven_credits.1.3", 180, 60, 180),
        
        new CreditsTranslatableLine(2 * p + 2 * multiplier, "heaven_credits.2"),
        new CreditsTranslatableLine(2 * p + 3 * multiplier, "heaven_credits.2.1"),
        new CreditsTranslatableLine(2 * p + 4 * multiplier, "heaven_credits.2.2"),
        new CreditsTranslatableLine(2 * p + 5 * multiplier, "heaven_credits.2.3"),
        
        new CreditsTranslatableLine(3 * p + 2 * multiplier, "heaven_credits.music"),
        new CreditsTranslatableLine(3 * p + 3 * multiplier, "heaven_credits.music.1"),
        new CreditsTranslatableLine(3 * p + 4 * multiplier, "heaven_credits.music.2"),
        new CreditsTranslatableLine(3 * p + 5 * multiplier, "heaven_credits.music.3"),
        
        new CreditsTranslatableLine(4 * p + 2 * multiplier, "heaven_credits.3"),
        new CreditsTranslatableLine(4 * p + 3 * multiplier, "heaven_credits.3.1"),
        new CreditsTranslatableLine(4 * p + 4 * multiplier, "heaven_credits.3.2"),
        new CreditsTranslatableLine(4 * p + 5 * multiplier, "heaven_credits.3.3", 255, 0, 0),
        new CreditsTranslatableLine(4 * p + 6 * multiplier, "heaven_credits.3.4"),
        new CreditsTranslatableLine(4 * p + 7 * multiplier, "heaven_credits.3.5"),
        new CreditsTranslatableLine(4 * p + 8 * multiplier, "heaven_credits.3.6"),
        new CreditsTranslatableLine(4 * p + 9 * multiplier, "heaven_credits.3.7"),
        new CreditsTranslatableLine(4 * p + 10 * multiplier, "heaven_credits.3.8"),
        new CreditsTranslatableLine(4 * p + 12 * multiplier, "heaven_credits.3.9"),
        new CreditsTranslatableLine(4 * p + 13 * multiplier, "heaven_credits.3.10"),
        new CreditsTranslatableLine(4 * p + 14 * multiplier, "heaven_credits.3.11"),
        new CreditsTranslatableLine(4 * p + 15 * multiplier, "heaven_credits.3.12"),
        new CreditsTranslatableLine(4 * p + 16 * multiplier, "heaven_credits.3.13"),
        new CreditsTranslatableLine(4 * p + 17 * multiplier, "heaven_credits.3.14"),
        new CreditsTranslatableLine(4 * p + 18 * multiplier, "heaven_credits.3.15"),
        new CreditsTranslatableLine(4 * p + 24 * multiplier, "heaven_credits.3.16"),
        new CreditsTranslatableLine(4 * p + 25 * multiplier, "heaven_credits.3.17"),

        new CreditsTranslatableLine(8 * p + 2 * multiplier2, "heaven_credits.4"),
        new CreditsTranslatableLine(8 * p + 3 * multiplier2, "heaven_credits.4.1"),
        new CreditsTranslatableLine(8 * p + 4 * multiplier2, "heaven_credits.4.2"),
        new CreditsTranslatableLine(8 * p + 5 * multiplier2, "heaven_credits.4.3"),
        new CreditsTranslatableLine(8 * p + 6 * multiplier2, "heaven_credits.4.4"),
        new CreditsTranslatableLine(8 * p + 7 * multiplier2, "heaven_credits.4.5"),
        new CreditsTranslatableLine(8 * p + 8 * multiplier2, "heaven_credits.4.6"),
        new CreditsTranslatableLine(8 * p + 9 * multiplier2, "heaven_credits.4.7"),
        new CreditsTranslatableLine(8 * p + 10 * multiplier2, "heaven_credits.4.8"),
        new CreditsTranslatableLine(8 * p + 11 * multiplier2, "heaven_credits.4.9"),
        new CreditsTranslatableLine(8 * p + 12 * multiplier2, "heaven_credits.4.10"),
        new CreditsTranslatableLine(8 * p + 13 * multiplier2, "heaven_credits.4.11"),
        new CreditsTranslatableLine(8 * p + 14 * multiplier2, "heaven_credits.4.12"),
        new CreditsTranslatableLine(8 * p + 15 * multiplier2, "heaven_credits.4.13"),
        new CreditsTranslatableLine(8 * p + 16 * multiplier2, "heaven_credits.4.14"),
        new CreditsTranslatableLine(8 * p + 17 * multiplier2, "heaven_credits.4.15"),
        new CreditsTranslatableLine(8 * p + 18 * multiplier2, "heaven_credits.4.16"),
        new CreditsTranslatableLine(8 * p + 19 * multiplier2, "heaven_credits.4.17"),
        new CreditsTranslatableLine(8 * p + 20 * multiplier2, "heaven_credits.4.18"),
        new CreditsTranslatableLine(8 * p + 21 * multiplier2, "heaven_credits.4.19"),
        new CreditsTranslatableLine(8 * p + 22 * multiplier2, "heaven_credits.4.20"),
        new CreditsTranslatableLine(8 * p + 23 * multiplier2, "heaven_credits.4.21"),
        new CreditsTranslatableLine(8 * p + 24 * multiplier2, "heaven_credits.4.22"),

        new CreditsTranslatableLine(8 * p + 29 * multiplier2, "heaven_credits.5"),
        new CreditsTranslatableLine(8 * p + 30 * multiplier2, "heaven_credits.5.1"),
    }; // 8 * 250 + 30 * 70
    // 4100

    protected CreditsLine[] getLines() {
        return lines;
    }
}
