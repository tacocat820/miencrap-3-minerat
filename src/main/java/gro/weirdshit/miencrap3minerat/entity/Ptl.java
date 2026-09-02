package gro.weirdshit.miencrap3minerat.entity;

import java.util.Collection;
import java.util.List;

import org.jetbrains.annotations.Nullable;

import gro.weirdshit.miencrap3minerat.ModParticles;
import gro.weirdshit.miencrap3minerat.ModSounds;
import gro.weirdshit.miencrap3minerat.net.classes.ScreenshakePayload;
import gro.weirdshit.miencrap3minerat.saved_data.WtfLevel;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.Util;
import net.minecraft.world.BossEvent.BossBarColor;
import net.minecraft.world.BossEvent.BossBarOverlay;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.ZombieAttackGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.world.entity.animal.cow.Cow;
import net.minecraft.world.entity.animal.golem.IronGolem;
import net.minecraft.world.entity.animal.turtle.Turtle;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.monster.zombie.Zombie;
import net.minecraft.world.entity.npc.villager.AbstractVillager;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.hurtingprojectile.WitherSkull;


public class Ptl extends PathfinderMob {

	private int jumpTimer = 100;
	private static final TargetingConditions.Selector LIVING_ENTITY_SELECTOR = (target, level) ->  true;
	private static final TargetingConditions TARGETING_CONDITIONS = TargetingConditions.forNonCombat().range(8.0).selector(LIVING_ENTITY_SELECTOR);

	public Ptl(Level world) {
		this(ModEntityTypes.PTL, world);
	}

	public Ptl(EntityType<? extends Ptl> entityType, Level world) {
		super(entityType, world);
	}

	public static AttributeSupplier.Builder createCubeAttributes() {
		return PathfinderMob.createMobAttributes()
				.add(Attributes.MAX_HEALTH, 400)
				.add(Attributes.TEMPT_RANGE, 10)
				.add(Attributes.MOVEMENT_SPEED, 0.6)
				.add(Attributes.ATTACK_DAMAGE, 4.0);
	}
	
	public boolean isPersistenceRequired() {
		return true;
	}

	@Override
	public void tick() {
		super.tick();

		this.jumpTimer -= 1;
		if (this.jumpTimer <= 0) {
			
			if (!level().isClientSide()) {

				float wtf = WtfLevel.getWtfForLevel(level());
				if (wtf < 100.0f || wtf > 995.0f) {
					this.jumpTimer = 100;
					return;
				} 

				if (this.level() instanceof ServerLevel level) {
					Collection<ServerPlayer> players = PlayerLookup.around(level, this.getPosition(0.0f), 100.0);
					for (ServerPlayer player : players) {
						ScreenshakePayload screenshake = new ScreenshakePayload(Math.max(0.0f, 2.0f - player.distanceTo(this) / 30.0f));
						ServerPlayNetworking.send(player, screenshake);
					}
				}

				this.playSound(ModSounds.CREAK, 3.0f, 1.0f);

				float r = this.getYHeadRot();
				Vec3 direction = Vec3.directionFromRotation(0, r);
				this.setDeltaMovement(direction.x, 2.5, direction.z);
			}

			for (int i = 0; i < 500; ++i) {
				float directionX = this.random.nextFloat() - 0.5f;
				float directionZ = this.random.nextFloat() - 0.5f;

				this.level().addParticle(ModParticles.FALL_DUST, 
					this.getX() + directionX * 3.0, this.getY(), this.getZ() + directionX * 3.0, 
					directionX * 3.0, 0.5, directionZ * 3.0);
			}

			this.jumpTimer = 100;
		}
	}
	@Override
	protected void addAdditionalSaveData(final ValueOutput output) {
		super.addAdditionalSaveData(output);
		output.putInt("jumpTimer", this.jumpTimer);
	}

	@Override
	protected void readAdditionalSaveData(final ValueInput input) {
		super.readAdditionalSaveData(input);
		this.jumpTimer = input.getIntOr("jumpTimer", 100);
	}

	public boolean causeFallDamage(final double fallDistance, final float damageModifier, final DamageSource damageSource) {
		
		if (fallDistance < 5.0) return false;

		if (this.level() instanceof ServerLevel level) {

			List<LivingEntity> entities = level.getNearbyEntities(LivingEntity.class, TARGETING_CONDITIONS, this, this.getBoundingBox().inflate(8.0, 4.0, 8.0));

			for (LivingEntity i : entities) {
				DamageSource shockwaveDamageSource = new DamageSource(
					level.registryAccess()
						.lookupOrThrow(Registries.DAMAGE_TYPE)
						.get(DamageTypes.CRAMMING.identifier()).orElseThrow());
				i.hurtServer(level, shockwaveDamageSource, 100.0f);
			}

			Collection<ServerPlayer> players = PlayerLookup.around(level, this.getPosition(0.0f), 200.0);
			for (ServerPlayer player : players) {
				ScreenshakePayload screenshake = new ScreenshakePayload(Math.max(0.0f, 5.0f - player.distanceTo(this) / 30.0f));
				ServerPlayNetworking.send(player, screenshake);
			}

			for (int i = 0; i < 500; ++i) {
				level.sendParticles(ModParticles.FALL_DUST, 
					this.getX(), this.getY(), this.getZ(), 500, 3.0, 0.5, 3.0, 1.0);
			}
		}

		this.playSound(ModSounds.BOOM, 4.0f, 1.0f);
		
		
		return false;
	}

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new AvoidEntityGoal<>(this, IronGolem.class, 20, 1.0, 1.0));
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Cow.class, 4));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
      	this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
      	this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<AbstractVillager>(this, AbstractVillager.class, false));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<Player>(this, Player.class, true));
    }
}