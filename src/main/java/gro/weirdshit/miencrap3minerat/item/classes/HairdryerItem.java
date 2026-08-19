package gro.weirdshit.miencrap3minerat.item.classes;

import java.util.List;
import java.util.Random;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.ModSounds;
import gro.weirdshit.miencrap3minerat.block.ModBlocks;
import gro.weirdshit.miencrap3minerat.entity.HappyGhast;
import gro.weirdshit.miencrap3minerat.item.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
// import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.NetherPortalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.portal.TeleportTransition;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.minecart.Minecart;
import net.minecraft.world.food.FoodProperties;

public class HairdryerItem extends Item {
    public static final ResourceKey<DamageType> HAIRDRYER_DAMAGE = ResourceKey.create(Registries.DAMAGE_TYPE, Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "hairdry"));

	private static final TargetingConditions.Selector LIVING_ENTITY_SELECTOR = (target, level) ->  true;
	private static final TargetingConditions TARGETING_CONDITIONS = TargetingConditions.forNonCombat().range(8.0).selector(LIVING_ENTITY_SELECTOR);


    public HairdryerItem(Properties properties) {
        super(properties.stacksTo(1).durability(1000));
    }
    
    public InteractionResult use(final Level level, final Player player, final InteractionHand hand) { 

        player.playSound(ModSounds.HAIRDRYER);

        float xr = player.getXRot();
        float yr = player.getYRot();
        Vec3 direction = Vec3.directionFromRotation(xr, yr);

        Vec3 playerPos = player.getEyePosition();

        Random rand = new Random();
        
        MIENCRAP3MINERAT.LOGGER.info(String.valueOf((player.getHeadLookAngle().y + 0.8 + rand.nextFloat() * 0.4f) * 0.3));

        for (int i = 0; i < 8; ++i) {
            level.addParticle(ParticleTypes.CLOUD, 
                playerPos.x, playerPos.y, playerPos.z, 
                (direction.x - 0.2 + rand.nextFloat() * 0.4f) * 0.7, 
                (player.getHeadLookAngle().y + rand.nextFloat() * 0.4f) * 0.7, 
                (direction.z - 0.2 + rand.nextFloat() * 0.4f) * 0.7
            );    
        }

        for (int I = 0; I < 2; ++I) {
            Vec3 pos = player.pick(1.5 + I, 0.0f, false).getLocation();
            
            if (level instanceof ServerLevel serverLevel && !level.isClientSide()) {
                
                List<HappyGhast> entities = serverLevel.getNearbyEntities(HappyGhast.class, TARGETING_CONDITIONS, player, player.getBoundingBox()
                    .setMinX(pos.x).setMinY(pos.y).setMinZ(pos.z)
                    .setMaxX(pos.x).setMaxY(pos.y).setMaxZ(pos.z).inflate(0.4, 0.4, 0.4));

                DamageSource damageSource = new DamageSource(
                        level.registryAccess()
                                .lookupOrThrow(Registries.DAMAGE_TYPE)
                                .get(HAIRDRYER_DAMAGE.identifier()).orElseThrow());
                
                for (HappyGhast i : entities) {

                    ItemEntity n = new ItemEntity(serverLevel, i.getX(), i.getY(), i.getZ(), new ItemStack(ModBlocks.DRIED_GHAST.item, 1));
                    n.setDefaultPickUpDelay();
                    serverLevel.addFreshEntity(n);
                    
                    i.hurtServer(serverLevel, damageSource, 100.0f);
                }
                
                BlockPos hitPos = BlockPos.containing(pos);
                for (int x = hitPos.getX() - 1; x <= hitPos.getX() + 1; ++x) {
                    for (int y = hitPos.getY() - 1; y <= hitPos.getY() + 1; ++y) {  
                        for (int z = hitPos.getZ() - 1; z <= hitPos.getZ() + 1; ++z) {
                            BlockPos blockPos = BlockPos.containing(x, y, z);
                            MIENCRAP3MINERAT.LOGGER.info(blockPos.toShortString());
                            BlockState state = level.getBlockState(blockPos);
                            //level.setBlockAndUpdate(blockPos, Blocks.BASALT.defaultBlockState());

                            if (state.is(Blocks.WATER)) level.setBlockAndUpdate(blockPos, ModBlocks.DRY_WATER.block.defaultBlockState());
                            else if (state.is(ModBlocks.WETWALL.block)) level.setBlockAndUpdate(blockPos, ModBlocks.DRYWALL.block.defaultBlockState());
                            else if (state.is(Blocks.TALL_GRASS)) level.setBlockAndUpdate(blockPos, Blocks.TALL_DRY_GRASS.defaultBlockState());
                            else if (state.is(Blocks.SHORT_GRASS)) level.setBlockAndUpdate(blockPos, Blocks.SHORT_DRY_GRASS.defaultBlockState());
                            else if (state.is(Blocks.BUSH)) level.setBlockAndUpdate(blockPos, Blocks.DEAD_BUSH.defaultBlockState());
                            else if (state.is(Blocks.WET_SPONGE)) level.setBlockAndUpdate(blockPos, Blocks.SPONGE.defaultBlockState());
                            else if (state.is(Blocks.WATER_CAULDRON)) level.setBlockAndUpdate(blockPos, Blocks.CAULDRON.defaultBlockState());
                            else if (state.is(Blocks.GRASS_BLOCK)) level.setBlockAndUpdate(blockPos, Blocks.DIRT.defaultBlockState());
                            else if (state.is(BlockTags.LEAVES)) level.setBlockAndUpdate(blockPos, Blocks.MANGROVE_ROOTS.defaultBlockState());
                            else if (state.is(Blocks.FARMLAND)) level.setBlockAndUpdate(blockPos, Blocks.DIRT_PATH.defaultBlockState());

                        }
                    }
                }
            }
        }

        return InteractionResult.SUCCESS;
    }
}
