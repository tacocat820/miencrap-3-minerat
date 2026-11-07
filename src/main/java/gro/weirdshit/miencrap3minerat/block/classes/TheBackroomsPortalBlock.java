package gro.weirdshit.miencrap3minerat.block.classes;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.block.ModBlockEntities;
import gro.weirdshit.miencrap3minerat.etc.PlayerEventStack;
import gro.weirdshit.miencrap3minerat.etc.PlayerStartTransition;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.TransparentBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class TheBackroomsPortalBlock {
    //blk
    static public class Block extends TransparentBlock implements EntityBlock {
    	private static final ResourceKey<Level> META_DIMENSION = ResourceKey.create(Registries.DIMENSION, Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "meta"));
    	private static final ResourceKey<Level> HEAVEN_DIMENSION = ResourceKey.create(Registries.DIMENSION, Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "heaven"));

        public Block(Properties properties) {
            super(properties);
        }

        @Override
        protected RenderShape getRenderShape(final BlockState state) {
        return RenderShape.INVISIBLE;
        }

        @Override
        public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
            return new BlockEntity(pos, state);
        }

        @Override 
        public void entityInside(final BlockState state, final Level level, final BlockPos pos, final Entity entity, final InsideBlockEffectApplier effectApplier, final boolean isPrecise) {
            Vec3 movement = entity.getDeltaMovement();
            entity.setDeltaMovement(movement.x / 3, movement.y / 3, movement.z / 3);
            if (entity instanceof ServerPlayer player && !PlayerStartTransition.isTrans(player)) {
                
                if (PlayerEventStack.back(player).id().equals("backrooms")) {
                    PlayerEventStack.pop(player);
                    PlayerStartTransition.run(player, HEAVEN_DIMENSION, new Vec3(0, -20, 0), "rect_close", 20);
                } else {
                    PlayerEventStack.push(player, "backrooms");
                    PlayerStartTransition.run(player, META_DIMENSION, new Vec3(1058, 36, 1058), "rect_close", 20);
                }

            }
        }
    }

    //ent
    static public class BlockEntity extends BlockEntityForShader {
        public BlockEntity(BlockPos pos, BlockState state) {
            super(ModBlockEntities.BACKROOMS_PORTAL_ENTITY_TYPE, pos, state);
        }
    }
    
}

