package gro.weirdshit.miencrap3minerat.entity;

import java.util.List;

import org.jetbrains.annotations.Nullable;

import gro.weirdshit.miencrap3minerat.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.util.Util;
import net.minecraft.world.BossEvent.BossBarColor;
import net.minecraft.world.BossEvent.BossBarOverlay;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.PlayerRideable;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
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
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.ai.targeting.TargetingConditions.Selector;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.hurtingprojectile.WitherSkull;


public class Hooorse extends PathfinderMob implements PlayerRideable {

	private double speedModifier = 1.0;
	private static final TargetingConditions.Selector LIVING_ENTITY_SELECTOR = (target, level) ->  true;
	private static final TargetingConditions TARGETING_CONDITIONS = TargetingConditions.forNonCombat().range(8.0).selector(new Selector() {
		public boolean test(LivingEntity target, ServerLevel level) {
			return !(target instanceof Hooorse);
		}
	});
	
	public Hooorse(Level world) {
		this(ModEntityTypes.HOOORSE, world);
	}

	public Hooorse(EntityType<? extends Hooorse> entityType, Level world) {
		super(entityType, world);
	}
	
	public boolean isPersistenceRequired() {
		return true;
	}


	@Override
	public InteractionResult mobInteract(final Player player, final InteractionHand hand) {


		if (player.getY() < this.getY() + 6.0) {
			player.sendOverlayMessage(Component.translatable("entity.miencrap3minerat.hooorse.ionlydatetallguys"));
		} else {
			this.doPlayerRide(player);
		}
		
		return super.mobInteract(player, hand);
	}

	public void tick() {
		super.tick();

		if (this.level() instanceof ServerLevel level && !(this.getDeltaMovement().x == 0 && this.getDeltaMovement().z == 0)) {
			List<LivingEntity> entities = level.getNearbyEntities(LivingEntity.class, TARGETING_CONDITIONS, this, AABB.ofSize(this.getPosition(0.0f), 3.0, 1.0, 3.0));

			for (LivingEntity i : entities) {
				DamageSource shockwaveDamageSource = new DamageSource(
					level.registryAccess()
						.lookupOrThrow(Registries.DAMAGE_TYPE)
						.get(DamageTypes.CRAMMING.identifier()).orElseThrow());
				
				i.playSound(ModSounds.SQUISH);
				i.hurtServer(level, shockwaveDamageSource, 100.0f);
			}

		}
	}

	public void wheel(Vec3 where) {
		//speedModifier += 0.1;
	}

	@Override
	protected void checkFallDamage(final double ya, final boolean onGround, final BlockState onState, final BlockPos pos) {
	}

	@Override
	protected void addAdditionalSaveData(final ValueOutput output) {
		super.addAdditionalSaveData(output);
		output.putDouble("speedModifier", this.speedModifier);
	}

	@Override
	protected void readAdditionalSaveData(final ValueInput input) {
		super.readAdditionalSaveData(input);
		this.speedModifier = input.getDoubleOr("speedModifier", 1.0);
	}

	protected void doPlayerRide(final Player player) {
		if (!this.level().isClientSide()) {
			player.setYRot(this.getYRot());
			player.setXRot(this.getXRot());
			player.startRiding(this);
		}
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
		this.goalSelector.addGoal(0, new AvoidEntityGoal<>(this, Ptl.class, 60, 1.0, 1.0));
		this.goalSelector.addGoal(0, new AvoidEntityGoal<>(this, Notjuan.class, 60, 1.0, 1.0));
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new PanicGoal(this, 1.25));
		this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0));
		this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
		this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
    }
}