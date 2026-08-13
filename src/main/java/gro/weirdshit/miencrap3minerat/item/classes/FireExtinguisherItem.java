package gro.weirdshit.miencrap3minerat.item.classes;

import java.rmi.registry.Registry;
import java.util.Set;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.ModSounds;
import gro.weirdshit.miencrap3minerat.block.ModBlocks;
import gro.weirdshit.miencrap3minerat.item.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
// import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.NetherPortalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.portal.TeleportTransition;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.minecart.Minecart;
import net.minecraft.world.level.portal.PortalForcer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;

import java.util.List;
import java.util.Random;


public class FireExtinguisherItem extends Item {

    public FireExtinguisherItem(Properties properties) {
        super(properties);
    }

    // public InteractionResult useOn(final UseOnContext context) {  // use on terrain
    //     return InteractionResult.SUCCESS;
    // }
    private static final TargetingConditions.Selector LIVING_ENTITY_SELECTOR = (target, level) ->  true;
	private static final TargetingConditions TARGETING_CONDITIONS = TargetingConditions.forNonCombat().range(3.0).selector(LIVING_ENTITY_SELECTOR);

    @Override
    public InteractionResult use(final Level level, final Player player, final InteractionHand hand) { 


        float xr = player.getXRot();
        float yr = player.getYRot();
        Vec3 direction = Vec3.directionFromRotation(xr, yr);

        Vec3 playerPos = player.getEyePosition();

        level.playSound(null, 
            player.getX(), player.getY(), player.getZ(), 
            SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS);

        Random rand = new Random();
        
        for (int i = 0; i < 8; ++i) {
            level.addParticle(ParticleTypes.CLOUD, 
                playerPos.x, playerPos.y, playerPos.z, 
                (direction.x - 0.2 + rand.nextFloat() * 0.4f) * 0.3, 
                (direction.y - 0.2 + rand.nextFloat() * 0.4f) * 0.3, 
                (direction.z - 0.2 + rand.nextFloat() * 0.4f) * 0.3
            );    
        }

        Vec3 pos = player.pick(3.5, 0.0f, false).getLocation();
        if (!level.isClientSide() && level instanceof ServerLevel serverLevel) {
                
            BlockPos hitPos = BlockPos.containing(pos);
            for (int x = hitPos.getX() - 1; x <= hitPos.getX() + 1; ++x) {
                for (int y = hitPos.getY() - 1; y <= hitPos.getY() + 1; ++y) {  
                    for (int z = hitPos.getZ() - 1; z <= hitPos.getZ() + 1; ++z) {
                        BlockPos blockPos = BlockPos.containing(x, y, z);
                        MIENCRAP3MINERAT.LOGGER.info(blockPos.toShortString());
                        BlockState state = level.getBlockState(blockPos);
                        //level.setBlockAndUpdate(blockPos, Blocks.BASALT.defaultBlockState());

                        if (state.is(Blocks.LAVA)) level.setBlockAndUpdate(blockPos, Blocks.BASALT.defaultBlockState());
                        else if (state.is(Blocks.MAGMA_BLOCK)) level.setBlockAndUpdate(blockPos, Blocks.COBBLESTONE.defaultBlockState());
                        else if (state.is(Blocks.FIRE)) level.setBlockAndUpdate(blockPos, Blocks.AIR.defaultBlockState());
                        else if (state.is(Blocks.FIREFLY_BUSH)) level.setBlockAndUpdate(blockPos, Blocks.BUSH.defaultBlockState());
                        else if (state.is(Blocks.SOUL_FIRE)) level.setBlockAndUpdate(blockPos, ModBlocks.DRY_WATER.block.defaultBlockState());
                        else if (state.is(Blocks.LAVA_CAULDRON)) level.setBlockAndUpdate(blockPos, Blocks.CAULDRON.defaultBlockState());
                        else if (state.is(Blocks.CAMPFIRE)) level.setBlockAndUpdate(blockPos, Blocks.CAMPFIRE.defaultBlockState().setValue(CampfireBlock.LIT, false).setValue(CampfireBlock.FACING, state.getValue(CampfireBlock.FACING)));
                        else if (state.is(Blocks.SOUL_CAMPFIRE)) level.setBlockAndUpdate(blockPos, Blocks.SOUL_CAMPFIRE.defaultBlockState().setValue(CampfireBlock.LIT, false).setValue(CampfireBlock.FACING, state.getValue(CampfireBlock.FACING)));
                    }
                }
            }

            List<LivingEntity> entities = serverLevel.getNearbyEntities(LivingEntity.class, TARGETING_CONDITIONS, null, AABB.ofSize(pos, 3.0, 3.0, 3.0));
            for (LivingEntity i : entities) {
                i.setRemainingFireTicks(0);
            }
        }
        for (int i = 0; i < 20; ++i) {
            level.addParticle(ParticleTypes.CLOUD, 
                pos.x, pos.y, pos.z, 
                (-0.5 + rand.nextFloat()) * 0.2, 
                (-0.5 + rand.nextFloat()) * 0.2, 
                (-0.5 + rand.nextFloat()) * 0.2
            );    
        } 
        return InteractionResult.SUCCESS;

    }
}
