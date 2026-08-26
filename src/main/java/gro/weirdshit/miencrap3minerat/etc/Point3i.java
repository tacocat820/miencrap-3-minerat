package gro.weirdshit.miencrap3minerat.etc;

import net.minecraft.util.Mth;

public record Point3i(int x, int y, int z) {
    public Point3i rotateY(double rad) {
        float radCos = Mth.cos(rad);
        float radSin = Mth.sin(rad);
        
        int x2 = (int) Math.round(x * radCos + z * radSin);
        int z2 = (int) Math.round(-x * radSin + z * radCos);
        
        return new Point3i(x2, y, z2);
    }

    public Point3i rotateY_relative(double rad, float x_offset, float z_offset) {
        float radCos = Mth.cos(rad);
        float radSin = Mth.sin(rad);

        double x_rel = (x+x_offset);
        double z_rel = (z+z_offset);

        int x2 = (int) Math.round((x_rel * radCos + z_rel * radSin) - x_offset);
        int z2 = (int) Math.round((-x_rel * radSin + z_rel * radCos) - z_offset);
        
        return new Point3i(x2, y, z2);
    }
}