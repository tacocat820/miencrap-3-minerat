package gro.weirdshit.miencrap3minerat.block.classes;

import org.jspecify.annotations.Nullable;

import com.mojang.serialization.MapCodec;

import gro.weirdshit.miencrap3minerat.block.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class TheMicrowaveBlock {
    
    static public class Blk extends AbstractMassCookingBlock.Blk {

        public Blk(Properties properties) {
            super(properties);
        }

        @Override
        public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
            return new BlockEnt(pos, state);
        }

        // @Override
        // public <T extends BlockEntity> @Nullable BlockEntityTicker<T> getTicker(Level level, BlockState state,
        //         BlockEntityType<T> type) {

        //     // return BaseEntityBlock.createTickerHelper(type, ModBlockEntities.MICROWAVE_ENTITY_TYPE, BlockEnt::tick);
        // }
        
    }

    static public class BlockEnt extends AbstractMassCookingBlock.BlockEnt {

        public BlockEnt(BlockPos worldPosition, BlockState blockState) {
            super(ModBlockEntities.MICROWAVE_ENTITY_TYPE, worldPosition, blockState);
        }

        @Override
        protected ItemStack getCookedItem(ItemStack original) {
            return new ItemStack(Items.APPLE);
        }

        @Override
        public int getCookingTicks() {
            return 5;
        }

    }

}
