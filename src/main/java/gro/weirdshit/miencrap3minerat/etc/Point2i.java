package gro.weirdshit.miencrap3minerat.etc;

public record Point2i(int x, int y) {
    public static Point2i from(double x, double y) {
        return new Point2i( (int)x, (int)y );
    }
}