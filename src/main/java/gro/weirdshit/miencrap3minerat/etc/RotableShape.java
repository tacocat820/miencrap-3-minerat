package gro.weirdshit.miencrap3minerat.etc;

import java.util.Map;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class RotableShape {
    private static Map<Direction, VoxelShape> SHAPES;

    public RotableShape(Map<Direction, VoxelShape> shapes) {
        SHAPES = shapes;
    }

    static public RotableShape single(VoxelShape shape) {
        Map<Direction, VoxelShape> shapes = Map.of(Direction.NORTH, shape);
        return new RotableShape(shapes);
    }

    static public RotableShape horiz(VoxelShape shape) {
        Map<Direction, VoxelShape> shapes = Shapes.rotateHorizontal(shape);
        return new RotableShape(shapes);
    }

    static public RotableShape vert(VoxelShape shape) {
        Map<Direction, VoxelShape> shapes = Shapes.rotateHorizontal(shape);
        return new RotableShape(shapes);
    }

    public RotableShape all(VoxelShape shape) {
        Map<Direction, VoxelShape> shapes = Shapes.rotateAll(shape);
        return new RotableShape(shapes);
    }

    public VoxelShape get(Direction dir) {
        return SHAPES.getOrDefault(dir, SHAPES.get(SHAPES.keySet().toArray()[0]));
    }
}
