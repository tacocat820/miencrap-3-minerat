package gro.weirdshit.miencrap3minerat.entity;


import java.util.Arrays;
import java.util.List;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.entity.ai.InnovationalBehavior;
import gro.weirdshit.miencrap3minerat.entity.ai.classes.DoNothing;
import gro.weirdshit.miencrap3minerat.entity.ai.classes.PsychotherapistGoHome;
import gro.weirdshit.miencrap3minerat.entity.ai.classes.PsychotherapistHuntForPsychos;
import gro.weirdshit.miencrap3minerat.entity.ai.classes.PsychotherapistHuntSomeone;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal; 
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal; 
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal; 
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal; 
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.entity.player.Player; 


public class Psychotherapist extends PathfinderMob {

	private static final List<InnovationalBehavior<Psychotherapist>> BEHAVIORS = Arrays.asList(
		new PsychotherapistHuntForPsychos(),
		new PsychotherapistHuntForPsychos(),
		new PsychotherapistHuntForPsychos(),
		new PsychotherapistHuntForPsychos(),
		new PsychotherapistGoHome(),
		new DoNothing<Psychotherapist>()
	);
	public InnovationalBehavior<Psychotherapist> currentBehavior = null;

	int i = 0; long behaviorTime = 0;

	public void stopBehavior(ServerLevel level) {
		if (currentBehavior != null) currentBehavior.stop(level, this);
		currentBehavior = null;
	}
	public void setBehavior(ServerLevel level, InnovationalBehavior<Psychotherapist> to) {
		if (currentBehavior != null) currentBehavior.stop(level, this);
		currentBehavior = to;
	}

	private Vec3 home;
	private Vec3 cell;

	@Override 
    public boolean hurtServer(final ServerLevel level, final DamageSource source, float damage) {
		
		if (source.is(DamageTypes.IN_WALL)) return false;
		if (source.getEntity() != null && !currentBehavior.isAbsolutelyImportant() && source.getEntity() instanceof LivingEntity livingEntity) {
			setBehavior(level, new PsychotherapistHuntSomeone(livingEntity));
		}

		return super.hurtServer(level, source, damage);
    }

	@Override
	protected void addAdditionalSaveData(final ValueOutput output) {
		super.addAdditionalSaveData(output);
		output.store("homePos", Vec3.CODEC, home);
		output.store("cellPos", Vec3.CODEC, cell);
	}

	@Override
	protected void readAdditionalSaveData(final ValueInput input) {
		super.readAdditionalSaveData(input);
		home = input.read("homePos", Vec3.CODEC).orElse(null);
		cell = input.read("cellPos", Vec3.CODEC).orElse(null);
	}

	public Vec3 getHome() {
		return home;
	}
	public Vec3 getCell() {
		if (cell == null) return new Vec3(0.0, -101.0, 0.0);
		return cell;
	}
	
	public Psychotherapist(Level world) {
		this(ModEntityTypes.PSYCHOTHERAPIST, world);
	}

	public Psychotherapist(EntityType<? extends Psychotherapist> entityType, Level world) {
		super(entityType, world);
	}
	
	public boolean isPersistenceRequired() {
		return true;
	}

	public static AttributeSupplier.Builder createCubeAttributes() {
		return PathfinderMob.createMobAttributes()
				.add(Attributes.MAX_HEALTH, 70)
				.add(Attributes.TEMPT_RANGE, 60)
				.add(Attributes.FOLLOW_RANGE, 60)
				.add(Attributes.MOVEMENT_SPEED, 0.4)
				.add(Attributes.ATTACK_DAMAGE, 4.0);
	}

	private InnovationalBehavior<Psychotherapist> getNextAction() {
		if (i >= BEHAVIORS.size()) i = 0;
		return BEHAVIORS.get(i++);
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
	TagKey<Block> IMMUNE = TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "therapist_immune"));
	public void destroyAnnoyingBlock(BlockPos pos) {
		if (!level().getBlockState(pos).is(IMMUNE)) level().destroyBlock(pos, true); 
	}

	protected void customServerAiStep(final ServerLevel level) {

		if (this.home == null) home = this.position();
		if (this.cell == null || this.cell.y <= -100.0) {

			List<HospitalMarker> markers = level()
				.getEntitiesOfClass(HospitalMarker.class, getBoundingBox().inflate(40));
			if (markers.size() > 0) {
				cell = markers.get(0).position();
			}
		}

		behaviors(level);
		
		super.customServerAiStep(level); 
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