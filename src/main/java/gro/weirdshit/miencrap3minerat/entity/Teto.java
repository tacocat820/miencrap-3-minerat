package gro.weirdshit.miencrap3minerat.entity;

import java.util.Arrays;
import java.util.List;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.entity.player.Player;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.entity.ai.InnovationalBehavior;
import gro.weirdshit.miencrap3minerat.entity.ai.classes.DoNothing;
import gro.weirdshit.miencrap3minerat.entity.ai.classes.TetoAttack;
import gro.weirdshit.miencrap3minerat.entity.ai.classes.TetoJumpOnBed;
import gro.weirdshit.miencrap3minerat.entity.ai.classes.TetoKillBadGuys;
import gro.weirdshit.miencrap3minerat.entity.ai.classes.TetoKissing;
import gro.weirdshit.miencrap3minerat.entity.ai.classes.TetoPlayTag;
import gro.weirdshit.miencrap3minerat.entity.ai.classes.TetoPlayTagTagging;

public class Teto extends PathfinderMob {

	private static final List<InnovationalBehavior<Teto>> BEHAVIORS = Arrays.asList(
		new TetoJumpOnBed(),
		new TetoPlayTag(),
		new TetoKissing(),
		new TetoKillBadGuys(),
		new DoNothing<Teto>()
	);
	public InnovationalBehavior<Teto> currentBehavior = null;

	int i = 0; long behaviorTime = 0;
	BlockPos lastFoundBed = null;

	private boolean checkBed(final ServerLevel level) {
		BlockState blockState = level.getBlockState(lastFoundBed);

		if (blockState.is(BlockTags.BEDS)) return true;
		else {
			this.lastFoundBed = null;
			return false;
		}
	}

	public void stopBehavior(ServerLevel level) {
		if (currentBehavior != null) currentBehavior.stop(level, this);
		currentBehavior = null;
	}
	public void setBehavior(ServerLevel level, InnovationalBehavior<Teto> to) {
		if (currentBehavior != null) currentBehavior.stop(level, this);
		currentBehavior = to;
	}

	public BlockPos findABed(ServerLevel level) {
		if (this.lastFoundBed != null && checkBed(level)) return lastFoundBed;
		for (int dx = -8; dx <= 8; ++dx) {
			for (int dz = -8; dz <= 8; ++dz) {
				for (int dy = -2; dy <= 2; ++dy) {
					BlockPos p = BlockPos.containing(this.getX() + dx, this.getY() + dy, this.getZ() + dz);
					if (level.getBlockState(p).is(BlockTags.BEDS)) {
						this.lastFoundBed = p;
						return p;
					}
				}
			}
		}
		return null;
	}

	private InnovationalBehavior<Teto> getNextAction() {
		if (i >= BEHAVIORS.size()) i = 0;
		return BEHAVIORS.get(i++);
	}

	public boolean canPlayTag() {
		return currentBehavior == null || !(currentBehavior.isImportant()) || currentBehavior instanceof TetoPlayTag;
	}
	public boolean canKiss() {
		return currentBehavior == null || !(currentBehavior.isImportant());
	}
	public void letsPlayTag(final ServerLevel level, Teto with) {
		if (canPlayTag()) {
			MIENCRAP3MINERAT.LOGGER.info("yay tag with teto");
			this.setBehavior(level, new TetoPlayTagTagging(with));
		}
	}
	public void letsKiss(final ServerLevel level, Teto with) {
		if (canPlayTag()) {
			this.setBehavior(level, new TetoKissing(with));
		}
	}
	public void letsKillBadGuys(final ServerLevel level, Monster who) {
		if (currentBehavior == null || !currentBehavior.isAbsolutelyImportant()) {
			this.setBehavior(level, new TetoAttack(who));
		}
	}

	private void behaviors(final ServerLevel level) {

		if (currentBehavior == null) {
			currentBehavior = getNextAction();

			if (!currentBehavior.start(level, this)) {
				currentBehavior = null; behaviorTime = 0; return;
			}
		}
		if (!currentBehavior.tick(level, this, ++behaviorTime)) {
			currentBehavior = null; behaviorTime = 0; return;
		}
		
	}

	protected void customServerAiStep(final ServerLevel level) {
		behaviors(level);
		
		super.customServerAiStep(level); 
	}
	
	public Teto(Level world) {
		this(ModEntityTypes.TETO, world);
	}

	public Teto(EntityType<? extends Teto> entityType, Level world) {
		super(entityType, world);
		this.setPathfindingMalus(PathType.WATER, 1.0f);
		this.setPathfindingMalus(PathType.FIRE, -1.0f);
		this.setPathfindingMalus(PathType.FIRE_IN_NEIGHBOR, 1.0f);
	}
	
	public boolean isPersistenceRequired() {
		return true;
	}

	public static AttributeSupplier.Builder createCubeAttributes() {
		return PathfinderMob.createMobAttributes()
				.add(Attributes.MAX_HEALTH, 10)
				.add(Attributes.TEMPT_RANGE, 10)
				.add(Attributes.MOVEMENT_SPEED, 0.4)
				.add(Attributes.ATTACK_DAMAGE, 4.0);
	}

    @Override
    protected void registerGoals() {
      this.goalSelector.addGoal(0, new FloatGoal(this));
      this.goalSelector.addGoal(1, new PanicGoal(this, 1.25));
      this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0));
      this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
      this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
    }
}