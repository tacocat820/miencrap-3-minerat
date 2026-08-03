package gro.weirdshit.miencrap3minerat.block.classes;


import com.mojang.serialization.MapCodec;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.etc.RotableShape;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class FaceRotShapeBlock extends FaceRotatingBlock {
    public static final MapCodec<FaceRotatingBlock> CODEC = simpleCodec(FaceRotatingBlock::new);

    public RotableShape SHAPES = RotableShape.single( Block.cube(16.0) );

    public FaceRotShapeBlock shape(VoxelShape shape) {
        this.SHAPES = RotableShape.all(shape);
        return this;
    }

    @Override
    protected VoxelShape getShape(final BlockState state, final BlockGetter level, final BlockPos pos, final CollisionContext context) {
        return this.SHAPES.get( state.getValue(FACING) );
    }

    public FaceRotShapeBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    
}