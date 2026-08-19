package gro.weirdshit.miencrap3minerat.block.classes;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.ModSounds;
import gro.weirdshit.miencrap3minerat.block.ModBlockEntities;
import gro.weirdshit.miencrap3minerat.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Level.ExplosionInteraction;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SculkCatalystBlockEntity;
import net.minecraft.world.level.block.entity.TickingBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class SpecificPanGhastBlockEntity extends BlockEntity {
    public SpecificPanGhastBlockEntity(BlockPos pos, BlockState state) {
		super(ModBlockEntities.PAN_GHAST_ENTITY_TYPE, pos, state);
	}

	TagKey<Block> HOT = TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "hot"));
	private int fryingTimer = 0;

	public void tick(final BlockState state, final ServerLevel level, final BlockPos pos, final RandomSource random) {
		
		BlockState below = level.getBlockState(pos.below());
		if (!below.is(HOT)) { fryingTimer = 0; return; }

		fryingTimer += 1;
		
		if (fryingTimer >= 3) {
			level.sendParticles(ParticleTypes.LAVA, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 30, 0.1, 0.1, 0.1, 1.0);
		}
		if (fryingTimer >= 7) {
			level.setBlockAndUpdate(pos, ModBlocks.PAN_FRIED_GHAST.block.defaultBlockState()
				.setValue(SpecificPanFriedGhastBlock.FACING, state.getValue(SpecificPanGhastBlock.FACING)));
			level.playSound(null, pos, ModSounds.SCREAM, SoundSource.BLOCKS);
		}
	}
}
