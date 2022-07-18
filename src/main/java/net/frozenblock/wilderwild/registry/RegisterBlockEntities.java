package net.frozenblock.wilderwild.registry;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.block.entity.HangingTendrilBlockEntity;
import net.frozenblock.wilderwild.block.entity.SculkEchoerBlockEntity;
import net.frozenblock.wilderwild.block.entity.TermiteMoundBlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.registry.Registry;

public class RegisterBlockEntities {
    public static final BlockEntityType<HangingTendrilBlockEntity> HANGING_TENDRIL = Registry.register(Registry.BLOCK_ENTITY_TYPE, WilderWild.id("hanging_tendril"), FabricBlockEntityTypeBuilder.create(HangingTendrilBlockEntity::new, RegisterBlocks.HANGING_TENDRIL).build(null));
    public static final BlockEntityType<SculkEchoerBlockEntity> SCULK_ECHOER = Registry.register(Registry.BLOCK_ENTITY_TYPE, WilderWild.id("sculk_echoer"), FabricBlockEntityTypeBuilder.create(SculkEchoerBlockEntity::new, RegisterBlocks.SCULK_ECHOER).build(null));
    public static final BlockEntityType<TermiteMoundBlockEntity> TERMITE_MOUND = Registry.register(Registry.BLOCK_ENTITY_TYPE, WilderWild.id("termite_mound"), FabricBlockEntityTypeBuilder.create(TermiteMoundBlockEntity::new, RegisterBlocks.TERMITE_MOUND).build(null));

    public static void register() {
        WilderWild.logWild("Registering BlockEntities for", WilderWild.UNSTABLE_LOGGING);
    }
}
