package gro.weirdshit.miencrap3minerat.item.classes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joml.Math;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.block.classes.MiniBlocksBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.Vec3;

public class ChiselItem extends Item {
    public static final Map< List<Integer>, BooleanProperty > MINIBLOCKS_PROPS = defineMap();

    static Map< List<Integer>, BooleanProperty > defineMap() {
        Map< List<Integer>, BooleanProperty > map = new HashMap<>();

        map.put(List.of(0,0,0), MiniBlocksBlock.X0_Z0_Y0);
        map.put(List.of(1,0,0), MiniBlocksBlock.X1_Z0_Y0);
        map.put(List.of(0,0,1), MiniBlocksBlock.X0_Z1_Y0);
        map.put(List.of(1,0,1), MiniBlocksBlock.X1_Z1_Y0);

        map.put(List.of(0,1,0), MiniBlocksBlock.X0_Z0_Y1);
        map.put(List.of(1,1,0), MiniBlocksBlock.X1_Z0_Y1);
        map.put(List.of(0,1,1), MiniBlocksBlock.X0_Z1_Y1);
        map.put(List.of(1,1,1), MiniBlocksBlock.X1_Z1_Y1);
        
        return map;
    }

    public ChiselItem(Properties properties) {
        super(properties);
    }
    
    @Override
    public InteractionResult useOn(final UseOnContext context) {

        BlockPos pos = context.getClickedPos();
        Level level = context.getLevel();
        // Level level = context.;
        Vec3 clickloc = context.getClickLocation();

        if (level instanceof ServerLevel serverLevel) {
            BlockState bstate = serverLevel.getBlockState(pos);
            if (!(bstate.getBlock() instanceof MiniBlocksBlock)) {
                return InteractionResult.PASS;
            }

            double x = clickloc.x - pos.getX();
            double y = clickloc.y - pos.getY();
            double z = clickloc.z - pos.getZ();

            int bX = Math.clamp((int)Math.ceil(x*2)-1, 0, 1);
            int bY = Math.clamp((int)Math.ceil(y*2)-1, 0, 1);
            int bZ = Math.clamp((int)Math.ceil(z*2)-1, 0, 1);

            System.out.println(""+
                bX + " " +
                bY + " " +
                bZ + " "
            );

            BooleanProperty prop = MINIBLOCKS_PROPS.get(List.of(bX, bY, bZ));

            serverLevel.setBlock(pos, bstate.setValue(prop, false), 3);

            return InteractionResult.SUCCESS_SERVER;
        }
		return InteractionResult.PASS;
	}

}
