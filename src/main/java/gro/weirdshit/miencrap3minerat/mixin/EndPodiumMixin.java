package gro.weirdshit.miencrap3minerat.mixin;

import java.util.function.Function;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import gro.weirdshit.miencrap3minerat.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelWriter;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.EndPodiumFeature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;

@Mixin(EndPodiumFeature.class)
public abstract class EndPodiumMixin {
    
    @Accessor("active")
    public abstract boolean getActive();
    @Shadow
    public abstract void dropPreviousAndSetBlock(final WorldGenLevel level, final BlockPos pos, final Block block);
    
    @Overwrite 
    public boolean place(final FeaturePlaceContext<NoneFeatureConfiguration> context) {
        MakeFeatureMethodsPublicMixin dis = (MakeFeatureMethodsPublicMixin)(Object)this;
        BlockPos origin = context.origin();
        WorldGenLevel level = context.level();

        for (BlockPos pos : BlockPos.betweenClosed(
            new BlockPos(origin.getX() - 4, origin.getY() - 1, origin.getZ() - 4), new BlockPos(origin.getX() + 4, origin.getY() + 32, origin.getZ() + 4)
        )) {
            boolean insideRim = pos.closerThan(origin, 2.5);
            if (insideRim || pos.closerThan(origin, 3.5)) {
            if (pos.getY() < origin.getY()) {
                if (insideRim) {
                    dis.m3m$setBlock(level, pos, ModBlocks.SACABAMBASPIS.block.defaultBlockState());
                } else if (pos.getY() < origin.getY()) {
                    if (this.getActive()) {
                        this.dropPreviousAndSetBlock(level, pos, Blocks.END_STONE);
                    } else {
                        dis.m3m$setBlock(level, pos, Blocks.END_STONE.defaultBlockState());
                    }
                }
            } else if (pos.getY() > origin.getY()) {
                if (this.getActive()) {
                    this.dropPreviousAndSetBlock(level, pos, Blocks.AIR);
                } else {
                    dis.m3m$setBlock(level, pos, Blocks.AIR.defaultBlockState());
                }
            } else if (!insideRim) {
                dis.m3m$setBlock(level, pos, ModBlocks.SACABAMBASPIS.block.defaultBlockState());
            } else if (this.getActive()) {
                this.dropPreviousAndSetBlock(level, new BlockPos(pos), ModBlocks.BACKROOMS_PORTAL.block);
            } else {
                dis.m3m$setBlock(level, new BlockPos(pos), Blocks.AIR.defaultBlockState());
            }
            }
        }

        for (int y = 0; y < 4; y++) {
            dis.m3m$setBlock(level, origin.above(y), ModBlocks.SACABAMBASPIS.block.defaultBlockState());
        }

        BlockPos centerOfPillar = origin.above(2);

        for (Direction face : Direction.Plane.HORIZONTAL) {
            dis.m3m$setBlock(level, centerOfPillar.relative(face), Blocks.WALL_TORCH.defaultBlockState().setValue(WallTorchBlock.FACING, face));
        }

        return true;
    }

}
