package net.frozenblock.wilderwild.registry;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.frozenblock.wilderwild.block.TermiteBlock;
import net.frozenblock.wilderwild.block.entity.HangingTendrilBlockEntity;
import net.frozenblock.wilderwild.block.entity.SculkEchoerBlockEntity;
import net.frozenblock.wilderwild.block.entity.TermiteBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.registry.Registry;

public class RegisterBlockEntityType {
    public static BlockEntityType<SculkEchoerBlockEntity> SCULK_ECHOER;
    public static BlockEntityType<HangingTendrilBlockEntity> HANGING_TENDRIL;
    public static BlockEntityType<TermiteBlockEntity> TERMITE;

    public static void init() {
        SCULK_ECHOER = Registry.register(Registry.BLOCK_ENTITY_TYPE, "wilderwild:sculk_echoer", FabricBlockEntityTypeBuilder.create(SculkEchoerBlockEntity::new, RegisterBlocks.SCULK_ECHOER).build(null));
        HANGING_TENDRIL = Registry.register(Registry.BLOCK_ENTITY_TYPE, "wilderwild:hanging_tendril", FabricBlockEntityTypeBuilder.create(HangingTendrilBlockEntity::new, RegisterBlocks.HANGING_TENDRIL).build(null));
        TERMITE = Registry.register(Registry.BLOCK_ENTITY_TYPE, "wilderwild:termite_mound", FabricBlockEntityTypeBuilder.create(TermiteBlockEntity::new, RegisterBlocks.TERMITE_BLOCK).build(null));

    }
}
