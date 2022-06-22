package net.frozenblock.wilderwild.registry;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.block.entity.HangingTendrilBlockEntity;
import net.frozenblock.wilderwild.block.entity.TermiteMoundBlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.registry.Registry;

public class RegisterBlockEntityType {
    //public static BlockEntityType<SculkEchoerBlockEntity> SCULK_ECHOER;
    public static BlockEntityType<HangingTendrilBlockEntity> HANGING_TENDRIL;
    public static BlockEntityType<TermiteMoundBlockEntity> TERMITE_MOUND;

    public static void register() {
        WilderWild.logWild("Registering BlockEntityTypes for", WilderWild.UNSTABLE_LOGGING);
        //SCULK_ECHOER = Registry.register(Registry.BLOCK_ENTITY_TYPE, "wilderwild:sculk_echoer", FabricBlockEntityTypeBuilder.create(SculkEchoerBlockEntity::new, RegisterBlocks.SCULK_ECHOER).build(null));
        HANGING_TENDRIL = Registry.register(Registry.BLOCK_ENTITY_TYPE, "wilderwild:hanging_tendril", FabricBlockEntityTypeBuilder.create(HangingTendrilBlockEntity::new, RegisterBlocks.HANGING_TENDRIL).build(null));
        TERMITE_MOUND = Registry.register(Registry.BLOCK_ENTITY_TYPE, "wilderwild:termite_mound", FabricBlockEntityTypeBuilder.create(TermiteMoundBlockEntity::new, RegisterBlocks.TERMITE_MOUND).build(null));

    }
}
