package gro.weirdshit.miencrap3minerat.block.classes;

import gro.weirdshit.miencrap3minerat.block.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class TheBasketBlock {

    static public class Blk extends HorizontallyRotatingBlock implements EntityBlock, SimpleWaterloggedBlock {
        public Blk(Properties properties) {
            super(properties);
        }

        @Override
        public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
            return new BlockEnt(pos, state);
        }
    }

    static public class BlockEnt extends BlockEntity implements ItemOwner {
        private final ItemStack item = ItemStack.EMPTY;
        private int clicks = 0;

        public int getClicks() {
            return clicks;
        }

        public BlockEnt(BlockPos pos, BlockState state) {
            super(ModBlockEntities.BASKET_ENTITY_TYPE, pos, state);
        }

        @Override
        public float getVisualRotationYInDegrees() {
            return 0;
        }

        @Override
        public Level level() {
            return this.level;
        }

        @Override
        public Vec3 position() {
            return this.getBlockPos().getCenter();
        }
    }
}