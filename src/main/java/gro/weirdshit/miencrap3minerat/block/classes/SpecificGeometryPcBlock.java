package gro.weirdshit.miencrap3minerat.block.classes;

import org.jetbrains.annotations.Nullable;

import com.mojang.serialization.MapCodec;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.etc.TransitionsMixinInterface;
import gro.weirdshit.miencrap3minerat.net.classes.StartBoxTransitionPayload;
import gro.weirdshit.miencrap3minerat.net.classes.StartTransitionPayload;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Level.ExplosionInteraction;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class SpecificGeometryPcBlock extends BaseEntityBlock {
    public static final EnumProperty<Direction> FACING;
    public static final MapCodec<SpecificGeometryPcBlock> CODEC = simpleCodec(SpecificGeometryPcBlock::new);

    public SpecificGeometryPcBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends SpecificGeometryPcBlock> codec() {
        return CODEC;
    }

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new SpecificGeometryPcBlockEntity(pos, state);
	}

    @Override 
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    protected BlockState rotate(final BlockState state, final Rotation rotation) {
        return (BlockState)state.setValue(FACING, rotation.rotate((Direction)state.getValue(FACING)));
    }

    protected BlockState mirror(final BlockState state, final Mirror mirror) {
        return state.rotate(mirror.getRotation((Direction)state.getValue(FACING)));
    }

    static {
        FACING = BlockStateProperties.HORIZONTAL_FACING;
    }


    @Override
    protected void onPlace(final BlockState state, final Level level, final BlockPos pos, final BlockState oldState, final boolean movedByPiston) {
        level.scheduleTick(pos, this, 20);
    }

	@Override
	protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
        
        if (player instanceof ServerPlayer serverPlayer) {
            //StartBoxTransitionPayload boxPayload = new StartBoxTransitionPayload(pos);
            //ServerPlayNetworking.send(serverPlayer, boxPayload);

            ((TransitionsMixinInterface)(Object)player).teleportTo(
                ResourceKey.create(Registries.DIMENSION, Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "opera"))
                , new Vec3(0, -28, -4), 20);

            StartTransitionPayload payload = new StartTransitionPayload(true, "vertical_close");
            ServerPlayNetworking.send(serverPlayer, payload);
        }

		return InteractionResult.SUCCESS;
	}
    
}
