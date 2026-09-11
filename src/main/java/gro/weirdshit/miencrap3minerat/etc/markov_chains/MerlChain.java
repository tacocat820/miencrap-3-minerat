package gro.weirdshit.miencrap3minerat.etc.markov_chains;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Path;
import java.util.List;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.MinecraftServer;

public class MerlChain implements Serializable {
    public static MarkovModel model = new MarkovModel(3);

    public static void generate() {
        MIENCRAP3MINERAT.LOGGER.info("hello merl");
        
        load();
    }

    public static void save(Path path) {
        MIENCRAP3MINERAT.LOGGER.info(FabricLoader.getInstance().getGameDir().toString());

        try {
            FileOutputStream fout = new FileOutputStream(path.toFile());
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(model);
            fout.close(); oos.close(); 
        } catch(Exception e) {
            MIENCRAP3MINERAT.LOGGER.error("fuck: " + e.toString());
        }
    }

    public static void load() {
        
        ObjectInputStream objectinputstream = null;
        try {
            InputStream streamIn = MerlChain.class.getResourceAsStream("/models/merl.model");
            objectinputstream = new ObjectInputStream(streamIn);
            MarkovModel readCase = (MarkovModel) objectinputstream.readObject();
            model = readCase;
        } catch (Exception e) {
            MIENCRAP3MINERAT.LOGGER.error("fuck: " + e.toString());
        } 
    }
}
