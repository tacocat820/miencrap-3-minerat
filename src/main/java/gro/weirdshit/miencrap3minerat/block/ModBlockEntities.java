package gro.weirdshit.miencrap3minerat.block;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.block.classes.SpecificCTable2BlockEntity;
import gro.weirdshit.miencrap3minerat.block.classes.SpecificPanGhastBlockEntity;
import gro.weirdshit.miencrap3minerat.block.classes.SpecificStaticBlockEntity;
import gro.weirdshit.miencrap3minerat.block.classes.SpecificWatercoolerBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.world.level.block.Block;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ModBlockEntities {
  public static final BlockEntityType<SpecificWatercoolerBlockEntity> WATERCOOLER_BLOCK_ENTITY_TYPE =
  register("counter", SpecificWatercoolerBlockEntity::new, ModBlocks.WATERCOOLER.block);

  public static final BlockEntityType<SpecificPanGhastBlockEntity> PAN_GHAST_ENTITY_TYPE =
  register("pan_ghast", SpecificPanGhastBlockEntity::new, ModBlocks.PAN_GHAST.block);

  public static final BlockEntityType<SpecificStaticBlockEntity> STATIC_ENTITY_TYPE =
  register("static", SpecificStaticBlockEntity::new, ModBlocks.STATIC.block);

  public static final BlockEntityType<SpecificCTable2BlockEntity> CRAFTING_TABLE_2_ENTITY =
  register("crafting_table_2", SpecificCTable2BlockEntity::new, ModBlocks.CRAFTING_TABLE_2.block);

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
