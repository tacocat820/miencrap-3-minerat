package gro.weirdshit.miencrap3minerat.etc;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.server.commands.SpreadPlayersCommand;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;

public class FlatPosition {
    public double x;
    public double z;

    double dist(final FlatPosition target) {
        double dx = this.x - target.x;
        double dz = this.z - target.z;
        return Math.sqrt(dx * dx + dz * dz);
    }

    public void moveAway(final FlatPosition pos) {
        this.x = this.x - pos.x;
        this.z = this.z - pos.z;
    }

    public int getSpawnY(final BlockGetter level, final int maxHeight) {
        MutableBlockPos pos = new MutableBlockPos(this.x, maxHeight + 1, this.z);
        boolean air2Above = level.getBlockState(pos).isAir();
        pos.move(Direction.DOWN);
        boolean air1Above = level.getBlockState(pos).isAir();

        while (pos.getY() > level.getMinY()) {
            pos.move(Direction.DOWN);
            boolean currentIsAir = level.getBlockState(pos).isAir();
            if (!currentIsAir && air1Above && air2Above) {
                return pos.getY() + 1;
            }

            air2Above = air1Above;
            air1Above = currentIsAir;
        }

        return maxHeight + 1;
    }

    public boolean isSafe(final BlockGetter level, final int maxHeight) {
        BlockPos pos = BlockPos.containing(this.x, this.getSpawnY(level, maxHeight) - 1, this.z);
        BlockState state = level.getBlockState(pos);
        return pos.getY() < maxHeight && !state.liquid() && !state.is(BlockTags.FIRE);
    }

    public void randomize(final RandomSource random, final double minX, final double minZ, final double maxX, final double maxZ) {
        this.x = Mth.nextDouble(random, minX, maxX);
        this.z = Mth.nextDouble(random, minZ, maxZ);
    }
    public void randomizeDistant(final RandomSource random, final double minX, final double minZ, final double maxX, final double maxZ) {
        this.x = Mth.nextDouble(random, minX, maxX);
        this.x += this.x / Math.abs(this.x) * 20.0;
        this.z = Mth.nextDouble(random, minZ, maxZ);
        this.z += this.z / Math.abs(this.z) * 20.0;
    }
}
