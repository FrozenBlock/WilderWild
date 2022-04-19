package net.frozenblock.wilderwild.block.entity;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.block.SculkEchoerBlock;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class NewBlockEntityType {
    public static BlockEntityType<SculkEchoerBlockEntity> SCULK_ECHOER;

    public static void init() {
        SCULK_ECHOER = Registry.register(Registry.BLOCK_ENTITY_TYPE, "wilderwild:sculk_echoer", FabricBlockEntityTypeBuilder.create(SculkEchoerBlockEntity::new, RegisterBlocks.SCULK_ECHOER).build(null));
    }
}
