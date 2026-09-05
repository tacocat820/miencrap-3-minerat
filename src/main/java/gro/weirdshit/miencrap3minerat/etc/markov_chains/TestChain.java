package gro.weirdshit.miencrap3minerat.etc.markov_chains;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Path;
import java.util.List;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.MinecraftServer;

public class TestChain implements Serializable {
    public static MarkovModel model = new MarkovModel(3);

    public static void generate() {
        MIENCRAP3MINERAT.LOGGER.info("hello markov");
        MIENCRAP3MINERAT.LOGGER.info(model.encode("what is minecraft").toString());
        MIENCRAP3MINERAT.LOGGER.info(model.decode(model.encode("sesbian lex")));
        model.train(model.encode("we shall now play MIENCRAP 3 MINERAT"));

        var sesbian = model.encodeNoEnd("shall now");
        int size = sesbian.size();
        while (sesbian.getLast() != 0 && sesbian.size() < size + 50) sesbian.add(model.predictNext(sesbian));

        MIENCRAP3MINERAT.LOGGER.info(model.decode(sesbian));
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

    public static void load(Path path) {
        
        ObjectInputStream objectinputstream = null;
        try {
            FileInputStream streamIn = new FileInputStream(path.toFile());
            objectinputstream = new ObjectInputStream(streamIn);
            MarkovModel readCase = (MarkovModel) objectinputstream.readObject();
            model = readCase;
        } catch (Exception e) {
            MIENCRAP3MINERAT.LOGGER.error("fuck: " + e.toString());
        } 
    }
}
