package gro.weirdshit.miencrap3minerat.entity;

import org.jetbrains.annotations.Nullable;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.ModSounds;
import gro.weirdshit.miencrap3minerat.etc.GrantAdvancement;
import gro.weirdshit.miencrap3minerat.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.Identifier;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.entity.player.Player;


public class Damn extends Animal {
	
	private static final EntityDataAccessor<Integer> LEG_COUNT = SynchedEntityData.defineId(Damn.class, EntityDataSerializers.INT);

	public Damn(Level world) {
		this(ModEntityTypes.DAMN, world);
	}

	public Damn(EntityType<? extends Damn> entityType, Level world) {
		super(entityType, world);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(LEG_COUNT, 2);
	}

	@Override
	protected void addAdditionalSaveData(final ValueOutput output) {
		super.addAdditionalSaveData(output);
		output.putInt("legCount", this.getLegCount());
	}

	@Override
	protected void readAdditionalSaveData(final ValueInput input) {
		super.readAdditionalSaveData(input);
		this.setLegCount(input.getIntOr("legCount", 2));
	}

	@Override
	protected void checkFallDamage(final double ya, final boolean onGround, final BlockState onState, final BlockPos pos) {
	}

	public static AttributeSupplier.Builder createCubeAttributes() {
		return PathfinderMob.createMobAttributes()
				.add(Attributes.MAX_HEALTH, 10)
				.add(Attributes.TEMPT_RANGE, 10)
				.add(Attributes.MOVEMENT_SPEED, 0.25)
				.add(Attributes.ATTACK_DAMAGE, 4.0);
	}

	public @Nullable Damn getBreedOffspring(final ServerLevel level, final AgeableMob partner) {
		Damn baby = ModEntityTypes.DAMN.create(level, EntitySpawnReason.BREEDING);
		
		return baby;
	}

	@Override
	public boolean isFood(final ItemStack itemStack) {
		return itemStack.is(ItemTags.PIG_FOOD);
	}

	public boolean isPersistenceRequired() {
		return true;
	}

	protected @Nullable SoundEvent getHurtSound(final DamageSource source) {
		return ModSounds.DAMN;
	}

	@Override
	public void aiStep() {
		if (this.getLegCount() < 1 && this.onGround() && this.verticalCollision) {
			this.setDeltaMovement(
				this.getDeltaMovement().add((this.random.nextFloat() * 2.0F - 1.0F) * 0.05F, 0.8F, (this.random.nextFloat() * 2.0F - 1.0F) * 0.05F)
			);
			this.needsSync = true;
			this.makeSound(ModSounds.BOUNCE);
		}

		super.aiStep();
	}

	@Override
	public InteractionResult mobInteract(final Player player, final InteractionHand hand) {
		ItemStack itemStack = player.getItemInHand(hand);
		if (itemStack.is(Items.SHEARS)) {
			if (this.level() instanceof ServerLevel level && this.getLegCount() > 0) {
				this.setLegCount(this.getLegCount() - 1);
				this.playSound(SoundEvents.SHEARS_SNIP);
				itemStack.hurtAndBreak(1, player, hand.asEquipmentSlot());

				ItemEntity n = new ItemEntity(level, this.getX(), this.getY(), this.getZ(), new ItemStack(ModItems.DAMN_LEG.item, 1));
				n.setDefaultPickUpDelay();
				level.addFreshEntity(n);

				if (player instanceof ServerPlayer serverPlayer) {
					GrantAdvancement.grant(serverPlayer, Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "poor_pukeko"));
				}

				return InteractionResult.SUCCESS_SERVER;
			} else {
				return InteractionResult.CONSUME;
			}
		} else {
			return super.mobInteract(player, hand);
		}
	}

	@Override
	public void tick() {
		super.tick();
	}
	public int getLegCount() {
		return entityData.get(LEG_COUNT);
	}

	private void setLegCount(int count) {
		entityData.set(LEG_COUNT, count);
	}

    @Override
    protected void registerGoals() {
		this.goalSelector.addGoal(0, new AvoidEntityGoal<>(this, Ptl.class, 60, 1.0, 1.0));
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new PanicGoal(this, 1.25));
		this.goalSelector.addGoal(3, new BreedGoal(this, 1.0));
		this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1));
		this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0));
		this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
		this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
    }
}