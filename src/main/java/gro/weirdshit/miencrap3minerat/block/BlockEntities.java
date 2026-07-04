package gro.weirdshit.miencrap3minerat.block;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.block.classes.SpecificWatercoolerBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.world.level.block.Block;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class BlockEntities {
  public static final BlockEntityType<SpecificWatercoolerBlockEntity> WATERCOOLER_BLOCK_ENTITY_TYPE =
  register("counter", SpecificWatercoolerBlockEntity::new, ModBlocks.WATERCOOLER.block);

  private static <T extends BlockEntity> BlockEntityType<T> register(
    String name,
    FabricBlockEntityTypeBuilder.Factory<? extends T> entityFactory,
    Block... blocks
  ) {
    Identifier id = Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, name);
    return Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, id, FabricBlockEntityTypeBuilder.<T>create(entityFactory, blocks).build());
  }


  public static void generate() {}
}
