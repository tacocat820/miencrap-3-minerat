package gro.weirdshit.miencrap3minerat.client.special.credits;

import gro.weirdshit.miencrap3minerat.client.Music;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.ARGB;
import net.minecraft.util.Util;

public abstract class AbstractCredits {
    public long start;

    public AbstractCredits() {
        this.start = Util.getMillis();
    }

    abstract class CreditsLine {
        public int y;
        public CreditsLine(int y) { this.y = y; }
        public abstract void draw(GuiGraphicsExtractor graphics, int y);
    }
    class CreditsTranslatableLine extends CreditsLine {
        String translation;
        int r,g,b;
        public CreditsTranslatableLine(int y, String translation) {
            this.translation = translation;
            this.r = 255; this.g = 255; this.b = 255;
            super(y);
        }
        public CreditsTranslatableLine(int y, String translation, int r, int g, int b) {
            this.translation = translation;
            this.r = r; this.g = g; this.b = b;
            super(y);
        }
        public void draw(GuiGraphicsExtractor graphics, int y) {
            
            graphics.text(Minecraft.getInstance().font, I18n.get(translation), 100, y, ARGB.color(255, r, g, b));
        }
    }

    protected abstract CreditsLine[] getLines();
    protected abstract double getTimeDivisor();
    protected abstract void preDraw(GuiGraphicsExtractor graphics, long time);
    protected abstract void postDraw(GuiGraphicsExtractor graphics, long time);
    protected abstract SoundEvent getMusic();

    public SoundInstance music = null;

    public void start() {
        Music.Stop();
    }
    
    public void draw(GuiGraphicsExtractor graphics, long time) {
        
        if (this.music == null || !Minecraft.getInstance().getSoundManager().isActive(music)) {
            music = SimpleSoundInstance.forMusic(this.getMusic());
            Minecraft.getInstance().getSoundManager().play(music);
        }

        this.preDraw(graphics, time);
        
        CreditsLine[] lines = this.getLines(); 
        // binary search doesn't help that much in this case i just wanted to show off how smart i am
        int l = 0, r = lines.length;
        int timeSinceStart = (int)((double)(time - start) / this.getTimeDivisor());
        int searchLeft = timeSinceStart - graphics.guiHeight();

        while (r - l > 1) {
            int m = l + (r - l) / 2;

            if (lines[m].y > searchLeft) r = m;
            else l = m;
        }
        //l -= 1;

        for (int i = l; i < lines.length; ++i) {
            int y = graphics.guiHeight() + lines[i].y - timeSinceStart;
            lines[i].draw(graphics, y);
            
            if (y > graphics.guiHeight()) break;
        }

        this.postDraw(graphics, time);
    }

}
