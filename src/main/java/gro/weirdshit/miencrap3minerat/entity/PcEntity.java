package gro.weirdshit.miencrap3minerat.entity;

import org.jetbrains.annotations.Nullable;

import com.mojang.logging.LogUtils;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.ModSounds;
import gro.weirdshit.miencrap3minerat.block.ModBlocks;
import gro.weirdshit.miencrap3minerat.block.classes.SpecificPcBlock;
import net.minecraft.CrashReportCategory;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.ProblemReporter.ScopedCollector;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.vehicle.boat.Boat;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.DirectionalPlaceContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Level.ExplosionInteraction;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ConcretePowderBlock;
import net.minecraft.world.level.block.Fallable;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.portal.TeleportTransition;
import net.minecraft.world.level.storage.TagValueInput;
import net.minecraft.world.level.storage.TagValueOutput;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraft.world.level.ClipContext.Fluid;

public class PcEntity extends Entity {
    private static final BlockState DEFAULT_BLOCK_STATE = ModBlocks.PC.block.defaultBlockState();
    private static final int DEFAULT_TIME = 0;
    private static final float DEFAULT_FALL_DAMAGE_PER_DISTANCE = 0.0F;
    private static final int DEFAULT_MAX_FALL_DAMAGE = 40;
    private static final boolean DEFAULT_DROP_ITEM = true;
    private static final boolean DEFAULT_CANCEL_DROP = false;
    private BlockState blockState = DEFAULT_BLOCK_STATE;
    public int time = 0;
    public boolean dropItem = true;
    private boolean cancelDrop = false;
    private boolean hurtEntities;
    private int fallDamageMax = 40;
    private float fallDamagePerDistance = 0.0F;
    public @Nullable CompoundTag blockData;
    public boolean forceTickAfterTeleportToDuplicate;
    protected static final EntityDataAccessor<BlockPos> DATA_START_POS = SynchedEntityData.defineId(PcEntity.class, EntityDataSerializers.BLOCK_POS);
    private static final EntityDataAccessor<Integer> TIMER = SynchedEntityData.defineId(PcEntity.class, EntityDataSerializers.INT);

    public PcEntity(final EntityType<? extends PcEntity> type, final Level level) {
        super(type, level);
    }

    private PcEntity(final Level level, final double x, final double y, final double z, final BlockState blockState) {
        this(ModEntityTypes.PC, level);
        this.blockState = blockState;
        this.blocksBuilding = true;
        this.setPos(x, y, z);
        this.setDeltaMovement(Vec3.ZERO);
        this.xo = x;
        this.yo = y;
        this.zo = z;
        this.setStartPos(this.blockPosition());
    }

    public static PcEntity fall(final Level level, final BlockPos pos, final BlockState state) {
        
        PcEntity entity = new PcEntity(
            level,
            pos.getX() + 0.5,
            pos.getY(),
            pos.getZ() + 0.5,
            state.hasProperty(BlockStateProperties.WATERLOGGED) ? (BlockState)state.setValue(BlockStateProperties.WATERLOGGED, false) : state
        );

        level.setBlock(pos, state.getFluidState().createLegacyBlock(), 3);
        level.addFreshEntity(entity);
        return entity;
    }

    @Override
    public boolean isAttackable() {
        return false;
    }

    @Override
    public final boolean hurtServer(final ServerLevel level, final DamageSource source, final float damage) {
        if (!this.isInvulnerableToBase(source)) {
            this.markHurt();
        }

        return false;
    }

    public void setStartPos(final BlockPos pos) {
        this.entityData.set(DATA_START_POS, pos);
    }

    public BlockPos getStartPos() {
        return this.entityData.get(DATA_START_POS);
    }

    @Override
    protected MovementEmission getMovementEmission() {
        return MovementEmission.NONE;
    }

    @Override
    protected void defineSynchedData(final SynchedEntityData.Builder entityData) {
        entityData.define(DATA_START_POS, BlockPos.ZERO);
        entityData.define(TIMER, 100);
    }

    @Override
    public boolean isPickable() {
        return !this.isRemoved();
    }

    @Override
    protected double getDefaultGravity() {
        return 0.04;
    }

    @Override
    public void tick() {

        if (this.getTimer() < 80) {
            this.playSound(SoundEvents.FIRE_AMBIENT);
            this.level().addParticle(ParticleTypes.FLAME, this.getX(), this.getY(), this.getZ(), 0.1f, 0.1f, 0.1f);
            this.level().addParticle(ParticleTypes.LARGE_SMOKE, this.getX(), this.getY(), this.getZ(), 0.1f, 0.1f, 0.1f);
            this.level().addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, this.getX(), this.getY(), this.getZ(), 0.1f, 0.1f, 0.1f);
        }
        if (this.getTimer() == 60) {
            this.playSound(ModSounds.OHMYPC);
        }
        this.setTimer(this.getTimer() - 1);
        if (this.getTimer() <= 0 && this.level() instanceof ServerLevel serverLevel) {
            serverLevel.explode(this, this.getX(), this.getY(), this.getZ(), 1.5f, ExplosionInteraction.BLOCK);
            this.kill(serverLevel);
        }

        if (this.blockState.isAir()) {
            this.discard();
        } else {
            Block block = this.blockState.getBlock();
            this.time++;
            this.applyGravity();
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.applyEffectsFromBlocks();
            this.handlePortal();
            if (this.level() instanceof ServerLevel serverLevel && (this.isAlive() || this.forceTickAfterTeleportToDuplicate)) {
            BlockPos pos = this.blockPosition();
            boolean isConcrete = this.blockState.getBlock() instanceof ConcretePowderBlock;
            boolean isStuckInWater = isConcrete && this.level().getFluidState(pos).is(FluidTags.WATER);
            double moveVec = this.getDeltaMovement().lengthSqr();
            if (isConcrete && moveVec > 1.0) {
                BlockHitResult clip = this.level()
                    .clip(
                        new ClipContext(
                        new Vec3(this.xo, this.yo, this.zo), this.position(), net.minecraft.world.level.ClipContext.Block.COLLIDER, Fluid.SOURCE_ONLY, this
                        )
                    );
                if (clip.getType() != Type.MISS && this.level().getFluidState(clip.getBlockPos()).is(FluidTags.WATER)) {
                    pos = clip.getBlockPos();
                    isStuckInWater = true;
                }
            }

            if (!this.onGround() && !isStuckInWater) {
                if (this.time > 100 && (pos.getY() <= this.level().getMinY() || pos.getY() > this.level().getMaxY()) || this.time > 600) {
                    if (this.dropItem && serverLevel.getGameRules().get(GameRules.ENTITY_DROPS)) {
                        this.spawnAtLocation(serverLevel, block);
                    }

                    this.discard();
                }
            } else {
                BlockState currentState = this.level().getBlockState(pos);
                this.setDeltaMovement(this.getDeltaMovement().multiply(0.7, -0.5, 0.7));
                if (!currentState.is(Blocks.MOVING_PISTON)) {
                    if (!this.cancelDrop) {
                        boolean mayReplace = currentState.canBeReplaced(
                            new DirectionalPlaceContext(this.level(), pos, Direction.DOWN, ItemStack.EMPTY, Direction.UP)
                        );
                        boolean wouldContinueFalling = FallingBlock.isFree(this.level().getBlockState(pos.below())) && (!isConcrete || !isStuckInWater);
                        boolean wouldSurvive = this.blockState.canSurvive(this.level(), pos) && !wouldContinueFalling;
                        if (mayReplace && wouldSurvive) {
                            if (this.blockState.hasProperty(BlockStateProperties.WATERLOGGED) && this.level().getFluidState(pos).is(Fluids.WATER)) {
                                this.blockState = (BlockState)this.blockState.setValue(BlockStateProperties.WATERLOGGED, true);
                            }
                        }
                    } else {
                        this.discard();
                        this.callOnBrokenAfterFall(block, pos);
                    }
                }
            }
            }

            this.setDeltaMovement(this.getDeltaMovement().scale(0.98));
        }
    }

    public boolean canCollideWith(final Entity entity) {
        return canVehicleCollide(this, entity);
    }

    public static boolean canVehicleCollide(final Entity vehicle, final Entity entity) {
        return (entity.canBeCollidedWith(vehicle) || entity.isPushable()) && !vehicle.isPassengerOfSameVehicle(entity);
    }

    public boolean canBeCollidedWith(final @Nullable Entity other) {
        return true;
    }

    public void callOnBrokenAfterFall(final Block block, final BlockPos pos) {
    }

    @Override
    public boolean causeFallDamage(final double fallDistance, final float damageModifier, final DamageSource damageSource) {
        return false;
    }

    @Override
    protected void addAdditionalSaveData(final ValueOutput output) {
        output.store("BlockState", BlockState.CODEC, this.blockState);
        output.putInt("Time", this.time);
        output.putBoolean("DropItem", this.dropItem);
        output.putBoolean("HurtEntities", this.hurtEntities);
        output.putFloat("FallHurtAmount", this.fallDamagePerDistance);
        output.putInt("FallHurtMax", this.fallDamageMax);
        if (this.blockData != null) {
            output.store("TileEntityData", CompoundTag.CODEC, this.blockData);
        }

        output.putBoolean("CancelDrop", this.cancelDrop);
		output.putInt("timer", this.getTimer());
    }

    @Override
    protected void readAdditionalSaveData(final ValueInput input) {
        this.blockState = (BlockState)input.read("BlockState", BlockState.CODEC).orElse(DEFAULT_BLOCK_STATE);
        this.time = input.getIntOr("Time", 0);
        boolean defaultHurtEntities = this.blockState.is(BlockTags.ANVIL);
        this.hurtEntities = input.getBooleanOr("HurtEntities", defaultHurtEntities);
        this.fallDamagePerDistance = input.getFloatOr("FallHurtAmount", 0.0F);
        this.fallDamageMax = input.getIntOr("FallHurtMax", 40);
        this.dropItem = input.getBooleanOr("DropItem", true);
        this.blockData = (CompoundTag)input.read("TileEntityData", CompoundTag.CODEC).orElse(null);
        this.cancelDrop = input.getBooleanOr("CancelDrop", false);

        this.setTimer(input.getIntOr("timer", 60));
    }
    public int getTimer() {
        return entityData.get(TIMER);
    }

    private void setTimer(int count) {
        entityData.set(TIMER, count);
    }

    public void setHurtsEntities(final float damagePerDistance, final int damageMax) {
        this.hurtEntities = true;
        this.fallDamagePerDistance = damagePerDistance;
        this.fallDamageMax = damageMax;
    }

    public void disableDrop() {
        this.cancelDrop = true;
    }

    @Override
    public boolean displayFireAnimation() {
        return false;
    }

    @Override
    public void fillCrashReportCategory(final CrashReportCategory category) {
        super.fillCrashReportCategory(category);
        category.setDetail("Immitating BlockState", this.blockState.toString());
    }

    public BlockState getBlockState() {
        return this.blockState;
    }

    @Override
    protected Component getTypeName() {
        return Component.translatable("entity.minecraft.falling_block_type", this.blockState.getBlock().getName());
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket(final ServerEntity serverEntity) {
        return new ClientboundAddEntityPacket(this, serverEntity, Block.getId(this.getBlockState()));
    }

    @Override
    public void recreateFromPacket(final ClientboundAddEntityPacket packet) {
        super.recreateFromPacket(packet);
        this.blockState = Block.stateById(packet.getData());
        this.blocksBuilding = true;
        double x = packet.getX();
        double y = packet.getY();
        double z = packet.getZ();
        this.setPos(x, y, z);
        this.setStartPos(this.blockPosition());
    }

    @Override
    public @Nullable Entity teleport(final TeleportTransition transition) {
        ResourceKey<Level> newDimension = transition.newLevel().dimension();
        ResourceKey<Level> oldDimension = this.level().dimension();
        boolean fromOrToEnd = (oldDimension == Level.END || newDimension == Level.END) && oldDimension != newDimension;
        Entity newEntity = super.teleport(transition);
        this.forceTickAfterTeleportToDuplicate = newEntity != null && fromOrToEnd;
        return newEntity;
    }
}
