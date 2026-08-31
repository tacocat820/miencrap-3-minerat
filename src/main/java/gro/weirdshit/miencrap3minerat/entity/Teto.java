package gro.weirdshit.miencrap3minerat.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jetbrains.annotations.Nullable;

import com.google.common.collect.ImmutableSet;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.util.Util;
import net.minecraft.util.profiling.Profiler;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.util.random.Weighted;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.BossEvent.BossBarColor;
import net.minecraft.world.BossEvent.BossBarOverlay;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.ActivityData;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.VillagerGoalPackages;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.ZombieAttackGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.cow.Cow;
import net.minecraft.world.entity.animal.golem.IronGolem;
import net.minecraft.world.entity.animal.pig.Pig;
import net.minecraft.world.entity.animal.turtle.Turtle;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.monster.zombie.Zombie;
import net.minecraft.world.entity.npc.villager.AbstractVillager;
import net.minecraft.world.entity.npc.villager.Villager;
import net.minecraft.world.entity.npc.villager.VillagerProfession;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.hurtingprojectile.WitherSkull;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.entity.ai.Brain.Provider;
import com.mojang.datafixers.util.Pair;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.entity.ai.InnovationalBehavior;
import gro.weirdshit.miencrap3minerat.entity.ai.classes.DoNothing;
import gro.weirdshit.miencrap3minerat.entity.ai.classes.TetoAttack;
import gro.weirdshit.miencrap3minerat.entity.ai.classes.TetoJumpOnBed;
import gro.weirdshit.miencrap3minerat.entity.ai.classes.TetoKillBadGuys;
import gro.weirdshit.miencrap3minerat.entity.ai.classes.TetoKissing;
import gro.weirdshit.miencrap3minerat.entity.ai.classes.TetoPlayTag;
import gro.weirdshit.miencrap3minerat.entity.ai.classes.TetoPlayTagTagging;
import net.minecraft.world.entity.ai.Brain.Packed;

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