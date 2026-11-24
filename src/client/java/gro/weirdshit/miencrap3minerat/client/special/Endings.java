package gro.weirdshit.miencrap3minerat.client.special;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.BitSet;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import net.fabricmc.loader.api.FabricLoader;

public class Endings {
    
    public static BitSet bs = new BitSet(1024);
    private static Path endings = FabricLoader.getInstance().getConfigDir().resolve("datatatatata.bin");

    public static String[] ENDINGS = {
        "See you :3",
        "MIENCRAP 4 MINERAT",
        "Narcovania",
    };

    public static void markAchieved(int e) {
        bs.set(e, true);
        save();
    }

    public static boolean get(int e) {
        return bs.get(e);
    }

    public static void generate() {
        if (Files.isRegularFile(endings)) {
            load();
        } else {
            save();
        }
    }
    public static void save() {
        try {

            MIENCRAP3MINERAT.LOGGER.info("savign bits");
            Files.write(endings, bs.toByteArray(), StandardOpenOption.CREATE);

        } catch (Exception e) {
            throw new Error("cannot write to datatatatata.bin");
        }
    }
    public static void load() {
        try {

            MIENCRAP3MINERAT.LOGGER.info("loding bits");
            byte[] data = Files.readAllBytes(endings);
            bs = BitSet.valueOf(data);

        } catch (Exception e) {
            throw new Error("cannot read / parse datatatatata.bin");
        }
    }


}
