package gro.weirdshit.miencrap3minerat.block.classes;

import gro.weirdshit.miencrap3minerat.block.ModBlockEntities;
import gro.weirdshit.miencrap3minerat.etc.PlayerStartTransition;
import net.minecraft.core.BlockPos;
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
                PlayerStartTransition.run(player, Level.NETHER, new Vec3(0, 0, 0), "rect_close", 20);
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

