package gro.weirdshit.miencrap3minerat.mixin;

import org.spongepowered.asm.mixin.Mixin;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.block.ModFluids;
import gro.weirdshit.miencrap3minerat.entity.PolPot;
import gro.weirdshit.miencrap3minerat.entity.Slave;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.npc.villager.Villager;
import net.minecraft.world.entity.npc.villager.VillagerProfession;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.StonecutterBlock;
import net.minecraft.world.level.block.state.BlockState;

@Mixin(StonecutterBlock.class)
public class StonecutterMixin {
    
    private static final ResourceKey<DamageType> STONECUTTER_DAMAGE = ResourceKey.create(Registries.DAMAGE_TYPE, Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "stonecutter"));
    
    public void entityInside(final BlockState state, final Level level, final BlockPos pos, final Entity entity, final InsideBlockEffectApplier effectApplier, final boolean isPrecise) {
        if (entity instanceof LivingEntity livingEntity && level instanceof ServerLevel serverLevel) {

            if (entity instanceof Villager villager && villager.getVillagerData().profession().is(VillagerProfession.MASON)) return;
            if (entity instanceof Slave) return;
            if (entity instanceof PolPot) return;

            DamageSource damageSource = new DamageSource(
					level.registryAccess()
							.lookupOrThrow(Registries.DAMAGE_TYPE)
							.get(STONECUTTER_DAMAGE.identifier()).orElseThrow());
			entity.hurtServer(serverLevel, damageSource, 3.0f);

            float health = livingEntity.getHealth();
            if (health < 7.0 && entity.getRandom().nextInt(0, 10) > 7) {
                serverLevel.setBlockAndUpdate(BlockPos.containing(entity.getEyePosition()), ModFluids.BLOOD.block.defaultBlockState());
            } 
		}
    }
}
