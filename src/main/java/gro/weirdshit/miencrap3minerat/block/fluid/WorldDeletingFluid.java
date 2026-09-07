package gro.weirdshit.miencrap3minerat.block.fluid;

import java.nio.file.Path;
import java.util.Optional;

import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.block.ModBlocks;
import gro.weirdshit.miencrap3minerat.block.ModFluids;
import gro.weirdshit.miencrap3minerat.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.InsideBlockEffectType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.storage.LevelResource;

public abstract class WorldDeletingFluid extends FlowingFluid {
	public String name;

	@Override
	public void animateTick(Level world, BlockPos pos, FluidState state, RandomSource random) {
		if (!state.isSource() && !(Boolean) state.getValue(FALLING)) {
			if (random.nextInt(64) == 0) {
				world.playLocalSound(
						pos.getX() + 0.5,
						pos.getY() + 0.5,
						pos.getZ() + 0.5,
						SoundEvents.BUBBLE_COLUMN_WHIRLPOOL_AMBIENT, // Bubbling poison/swamp sound
						SoundSource.AMBIENT,
						random.nextFloat() * 0.25F + 0.75F,
						random.nextFloat() + 0.5F,
						false);
			}
		} else if (random.nextInt(10) == 0) {
			world.addParticle(
					ParticleTypes.UNDERWATER, pos.getX() + random.nextDouble(), pos.getY() + random.nextDouble(),
					pos.getZ() + random.nextDouble(), 0.0, 0.0, 0.0);
		}
	}

	@Override
	protected void entityInside(Level world, BlockPos pos, Entity entity, InsideBlockEffectApplier handler) {
		handler.apply(InsideBlockEffectType.EXTINGUISH);

		if (!(world instanceof ServerLevel serverLevel) || !(entity instanceof LivingEntity livingEntity)) return;

		if (entity instanceof Player) {
			MIENCRAP3MINERAT.LOGGER.info(serverLevel.getServer().getWorldPath(LevelResource.ROOT).toString());
			Path path = serverLevel.getServer().getWorldPath(LevelResource.ROOT);
			
			if (!path.resolve("level.dat").toFile().exists()) {
				MIENCRAP3MINERAT.LOGGER.info("wtf"); return;
			}
			
			// kaboom
			try {
				FileUtils.deleteDirectory(path.toFile());
			} catch(Exception e) {
				MIENCRAP3MINERAT.LOGGER.info("eror when deleting " + e.toString());
			}

			for (ServerPlayer player : serverLevel.getServer().getPlayerList().getPlayers()) {
				player.sendOverlayMessage(Component.translatable("msg.miencrap3minerat.thisworldwasdeleted"));
			}
		}
	}

	@Nullable
	@Override
	public ParticleOptions getDripParticle() {
		return ParticleTypes.DRIPPING_WATER;
	}

	@Override
	protected boolean canConvertToSource(ServerLevel world) {
		return world.getGameRules().get(GameRules.WATER_SOURCE_CONVERSION);
	}

	@Override
	protected void beforeDestroyingBlock(LevelAccessor world, BlockPos pos, BlockState state) {
		BlockEntity blockEntity = state.hasBlockEntity() ? world.getBlockEntity(pos) : null;
		Block.dropResources(state, world, pos, blockEntity);
	}

	@Override
	protected int getSlopeFindDistance(LevelReader world) {
		return 4;
	}

	@Override
	public int getDropOff(LevelReader world) {
		return 1;
	}

	@Override
	public int getTickDelay(LevelReader world) {
		return 5;
	}

	@Override
	public boolean canBeReplacedWith(FluidState state, BlockGetter world, BlockPos pos, Fluid fluid,Direction direction) {
		return direction == Direction.DOWN; //&& !fluid.is(ModFluidTags.);
	}

	@Override protected float getExplosionResistance() {return 100.0F;}

	@Override
	public Optional<SoundEvent> getPickupSound() {
		return Optional.of(SoundEvents.BUCKET_FILL);
	}

	public static class Flowing extends WorldDeletingFluid {
	@Override
	protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, FluidState> builder) {
		super.createFluidStateDefinition(builder);
		builder.add(LEVEL);
	}

	@Override
	public int getAmount(FluidState state) {return state.getValue(LEVEL);}

	@Override
	public boolean isSource(FluidState state) {return false;}

	@Override
	public Item getBucket() {
		return ModFluids.WORLD_DELETING_FLUID_BUCKET.item;
		//throw new UnsupportedOperationException("Unimplemented method 'getBucket'");
	}
}

	public static class Source extends WorldDeletingFluid {
		@Override
		public int getAmount(FluidState state) {
			return 8;
		}

		@Override
		public boolean isSource(FluidState state) {
			return true;
		}

		@Override
		public @NonNull Item getBucket() {
			return ModFluids.WORLD_DELETING_FLUID_BUCKET.item; 
			//throw new UnsupportedOperationException("Unimplemented method 'getBucket'");
		}
	}



	@Override
	public Fluid getFlowing() {
		return ModFluids.WORLD_DELETING_FLUID_FLOWING;
	}

	@Override
	public Fluid getSource() {
		return ModFluids.WORLD_DELETING_FLUID_STILL;
	}

	@Override
	public boolean isSame(Fluid fluid) {
		return fluid == ModFluids.WORLD_DELETING_FLUID_FLOWING || fluid == ModFluids.WORLD_DELETING_FLUID_STILL;
	}

	@Override
	protected BlockState createLegacyBlock(FluidState state) {
		return ModFluids.WORLD_DELETING_FLUID.block.defaultBlockState().setValue(LiquidBlock.LEVEL, getLegacyLevel(state));
	}
}