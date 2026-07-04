package gro.weirdshit.miencrap3minerat.block.classes;

import com.mojang.serialization.MapCodec;

import gro.weirdshit.miencrap3minerat.block.BlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.BaseEntityBlock;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.lang.Math;

public class SpecificWatercoolerBlockEntity extends BlockEntity {
    public SpecificWatercoolerBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntities.WATERCOOLER_BLOCK_ENTITY_TYPE, pos, state);
    }

    private int clicks = 0;
    public int getClicks() { return this.clicks; }
    public void incrementClicks() {
        ++this.clicks;
        this.setChanged();
    }
}
