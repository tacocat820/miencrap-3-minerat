package gro.weirdshit.miencrap3minerat.block.classes;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class SpecificConveyorBlock extends HorizontallyRotatingBlock {
    public SpecificConveyorBlock(Properties properties) {
        super(properties);
    }
    
    @Override 
    public void stepOn(Level level, BlockPos pos, BlockState onState, Entity entity) {
        Vec3 position = entity.getPosition(0);
        Direction dir = onState.getValue(FACING);
        // MIENCRAP3MINERAT.LOGGER.info("ya " + dir.getStepX() + " " + dir.getStepY() + " " + dir.getStepZ());
        
        entity.setPos(position.add(((double)dir.getStepX())/10, ((double)dir.getStepY())/10, ((double)dir.getStepZ())/10));

        //level.explode(entity, pos.getX(), pos.getY(), pos.getZ(), 3.3f, true, ExplosionInteraction.TNT);
        // Vec3 movement = entity.getDeltaMovement();
        // entity.setDeltaMovement(movement.x * 2.0, movement.y * 2.0, movement.z * 2.0);
    }
}
