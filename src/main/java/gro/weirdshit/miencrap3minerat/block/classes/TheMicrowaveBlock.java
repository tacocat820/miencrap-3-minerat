package gro.weirdshit.miencrap3minerat.block.classes;

import org.jspecify.annotations.Nullable;

import com.mojang.serialization.MapCodec;

import gro.weirdshit.miencrap3minerat.ModSounds;
import gro.weirdshit.miencrap3minerat.block.ModBlockEntities;
import gro.weirdshit.miencrap3minerat.block.ModBlocks;
import gro.weirdshit.miencrap3minerat.block.ModFluids;
import gro.weirdshit.miencrap3minerat.item.ModItems;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.game.ClientboundStopSoundPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class TheMicrowaveBlock {
    
    static public class Blk extends AbstractMassCookingBlock.Blk {

        public Blk(Properties properties) {
            super(properties);
        }

        @Override
        public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
            return new BlockEnt(pos, state);
        }

        // @Override
        // public <T extends BlockEntity> @Nullable BlockEntityTicker<T> getTicker(Level level, BlockState state,
        //         BlockEntityType<T> type) {

        //     // return BaseEntityBlock.createTickerHelper(type, ModBlockEntities.MICROWAVE_ENTITY_TYPE, BlockEnt::tick);
        // }
        
    }

    static public class BlockEnt extends AbstractMassCookingBlock.BlockEnt implements @Nullable ItemOwner {

        public BlockEnt(BlockPos worldPosition, BlockState blockState) {
            super(ModBlockEntities.MICROWAVE_ENTITY_TYPE, worldPosition, blockState);
        }

        public ItemStack ofCount(ItemStack itemStack, int count) {
            itemStack.setCount(count);
            return itemStack;
        }

        @Override
        protected ItemStack getCookedItem(ItemStack original) {
            if (this.getTicksLeft() <= 0) return ofCount(new ItemStack(Items.COAL), original.count());
            if (this.getTicksLeft() <= 10) return ofCount(getActuallyCookedItem(original), original.count());
            return original;
        }

        @Override
        public void spitOutItem(BlockPos pos) {
            super.spitOutItem(pos);
            setChanged();
        }

        @Override
        public void tick(Level level, BlockPos pos, BlockState blockState) {
            super.tick(level, pos, blockState);
            setChanged();
        }

        public ItemStack getActuallyCookedItem(ItemStack original) {

            switch (original.typeHolder().getRegisteredName()) {
                case "minecraft:beef": return new ItemStack(Items.COOKED_BEEF);
                case "minecraft:porkchop": return new ItemStack(Items.COOKED_PORKCHOP);
                case "minecraft:chicken": return new ItemStack(Items.COOKED_CHICKEN);
                case "minecraft:mutton": return new ItemStack(Items.COOKED_MUTTON);
                case "minecraft:cod": return new ItemStack(Items.COOKED_COD);
                case "minecraft:rabbit": return new ItemStack(Items.COOKED_RABBIT);
                case "minecraft:salmon": return new ItemStack(Items.COOKED_SALMON);
                case "miencrap3minerat:villager_meat_raw": return new ItemStack(ModItems.VILLAGER_MEAT.item);
                case "miencrap3minerat:villager_meat": return new ItemStack(ModItems.DUST.item);

                case "minecraft:sweet_berries": return new ItemStack(Items.RED_DYE);
                case "minecraft:pufferfish": return new ItemStack(Items.BONE);
                case "minecraft:glow_berries": return new ItemStack(Items.SPYGLASS);
                case "minecraft:apple": return new ItemStack(Items.DRIED_KELP);
                case "minecraft:golden_apple": return new ItemStack(ModItems.ORANGE.item);
                case "minecraft:enchanted_golden_apple": return new ItemStack(Items.GOLDEN_HELMET);
                case "minecraft:kelp": return new ItemStack(Items.DRIED_KELP);
                case "minecraft:wheat": return new ItemStack(Items.BREAD);
                case "minecraft:egg": return new ItemStack(Items.CAKE);
                case "minecraft:cocoa_beans": return new ItemStack(Items.COOKIE);
                case "minecraft:rotten_flesh": return new ItemStack(Items.LEATHER);
                case "minecraft:spider_eye": return new ItemStack(Items.FERMENTED_SPIDER_EYE);
                case "minecraft:brick": return new ItemStack(Items.NETHER_BRICK);
                case "minecraft:bricks": return new ItemStack(Items.NETHER_BRICKS);
                case "minecraft:beetroot": return new ItemStack(Items.DRIED_KELP);
                case "minecraft:carrot": return new ItemStack(Items.DRIED_KELP);
                case "minecraft:golden_carrot": return new ItemStack(Items.DRIED_KELP);
                case "minecraft:ender_eye": return new ItemStack(ModBlocks.EYE.item);
                case "minecraft:bamboo": return new ItemStack(Items.STICK);
                case "minecraft:bone_meal": return new ItemStack(Items.BREAD);
                case "minecraft:powder_snow_bucket": return new ItemStack(Items.WATER_BUCKET);
                case "minecraft:water_bucket": return new ItemStack(Items.BUCKET);
                case "miencrap3minerat:oil_bucket": return new ItemStack(ModItems.FIRE.item);
                case "miencrap3minerat:blood_bucket": return new ItemStack(Items.BUCKET);
                case "miencrap3minerat:piss_bucket": return new ItemStack(Items.BUCKET);
                case "miencrap3minerat:ears_bucket": return new ItemStack(ModFluids.BLOOD.item);
                case "miencrap3minerat:sulfuric_acid_bucket": return new ItemStack(ModItems.FIRE.item);
                case "miencrap3minerat:nuclear_waste_bucket": return new ItemStack(ModItems.FIRE.item);
                case "miencrap3minerat:world_deleting_fluid_bucket": return new ItemStack(Items.AIR);
                case "miencrap3minerat:orange_juice_bucket": return new ItemStack(ModFluids.PISS_BUCKET.item);
                case "miencrap3minerat:cheeseburger": return new ItemStack(Items.BREAD);
                case "miencrap3minerat:uranium": return new ItemStack(Items.LIME_DYE);
                case "miencrap3minerat:tooth": return new ItemStack(Items.BONE);
                case "miencrap3minerat:bedian": return new ItemStack(ModItems.BEDIAN.item);
                case "miencrap3minerat:baguette": return new ItemStack(Items.BREAD);
                case "miencrap3minerat:iron_apple": return new ItemStack(Items.IRON_INGOT);
                case "miencrap3minerat:soap": return new ItemStack(ModItems.DUST.item);
                case "miencrap3minerat:estrogen": return new ItemStack(ModItems.DUST.item);

                case "miencrap3minerat:cold_banana": return new ItemStack(ModItems.BANANA.item);
                case "miencrap3minerat:cold_wood": return new ItemStack(Items.OAK_WOOD);
                case "miencrap3minerat:cold_log": return new ItemStack(Items.OAK_LOG);
                case "miencrap3minerat:cold_stairs": return new ItemStack(Items.OAK_STAIRS);
                case "miencrap3minerat:cold_slab": return new ItemStack(Items.OAK_SLAB);
                case "miencrap3minerat:cold_planks": return new ItemStack(Items.OAK_PLANKS);
                case "miencrap3minerat:cold_planks_miniblocks": return new ItemStack(ModBlocks.OAK_PLANKS_MINIBLOCKS.item);
                case "miencrap3minerat:cold_leaves": return new ItemStack(Items.OAK_LEAVES);
                case "miencrap3minerat:pan_ghast": return new ItemStack(ModBlocks.PAN_FRIED_GHAST.item);
                case "miencrap3minerat:slop_block": return new ItemStack(ModBlocks.COMPRESSED_SLOP_BLOCK.item);
                case "miencrap3minerat:icicle": return new ItemStack(ModItems.WATER_STICK.item);
                case "miencrap3minerat:blood_icicle": return new ItemStack(ModItems.BLOOD_STICK.item);
                case "miencrap3minerat:nokia": return new ItemStack(ModItems.NOKIA.item);
                case "miencrap3minerat:red_ice": return new ItemStack(ModFluids.BLOOD.item);
                
                case "minecraft:cobblestone": return new ItemStack(Items.MAGMA_BLOCK);
                case "minecraft:coal": return new ItemStack(ModItems.FIRE.item);
                default: return new ItemStack(Items.COAL);
            }

        }

        @Override
        public int getCookingTicks() {
            return 30;
        }

        protected void duringCooking(BlockPos pos) {
            if (this.getTicksLeft() <= 18) {
                if (level instanceof ServerLevel serverLevel)
                    serverLevel.sendParticles(ParticleTypes.LAVA, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 30, 0.3, 0.3, 0.3, 0.1);
                
            }
        }
        protected void startCooking(BlockPos pos) {
            
            if (level instanceof ServerLevel serverLevel) {
                serverLevel.playSound(null, pos, ModSounds.MICROWAVE, SoundSource.BLOCKS); 
                // TODO: make the sound stop whenever the microwave is stopped
            }
            
        }
        protected void stopCooking(BlockPos pos) {
            setChanged();
            if (level instanceof ServerLevel serverLevel) {

                
                /*serverLevel.getEntitiesOfClass(ServerPlayer.class, AABB.ofSize(pos.getCenter(), 20.0, 20.0, 20.0)).forEach(player -> {
                    serverLevel.playSound(null, pos, ModSounds.MICROWAVE, SoundSource.BLOCKS);
                });  */
                sendPacketToTrackingClients(new ClientboundStopSoundPacket(ModSounds.MICROWAVE.location(), SoundSource.BLOCKS));
            }
            
        }

        public void sendPacketToTrackingClients(Packet<?> payload) {
            if (payload == null || !(this.getLevel() instanceof ServerLevel)) return;
            PlayerLookup.tracking(this).forEach(player -> player.connection.send(payload));
        }

        public @Nullable Level level() {
            return getLevel();
        }

        @Override
        public float getVisualRotationYInDegrees() {
            return 0f;
        }

        @Override
        public Vec3 position() {
            return position();
        }

    }

}
