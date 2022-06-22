package net.frozenblock.wilderwild.registry;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.block.entity.HangingTendrilBlockEntity;
import net.frozenblock.wilderwild.block.entity.NewSculkSensorBlockEntity;
import net.frozenblock.wilderwild.block.entity.TermiteMoundBlockEntity;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.registry.Registry;

public class RegisterBlockEntityType {
    public static BlockEntityType<NewSculkSensorBlockEntity> NEW_SCULK_SENSOR;
    public static BlockEntityType<HangingTendrilBlockEntity> HANGING_TENDRIL;
    public static BlockEntityType<TermiteMoundBlockEntity> TERMITE_MOUND;

    public static void register() {
        WilderWild.logWild("Registering BlockEntityTypes for", WilderWild.UNSTABLE_LOGGING);
        NEW_SCULK_SENSOR = Registry.register(Registry.BLOCK_ENTITY_TYPE, "wilderwild:new_sculk_sensor", FabricBlockEntityTypeBuilder.create(NewSculkSensorBlockEntity::new, Blocks.SCULK_SENSOR).build(null));
        HANGING_TENDRIL = Registry.register(Registry.BLOCK_ENTITY_TYPE, "wilderwild:hanging_tendril", FabricBlockEntityTypeBuilder.create(HangingTendrilBlockEntity::new, RegisterBlocks.HANGING_TENDRIL).build(null));
        TERMITE_MOUND = Registry.register(Registry.BLOCK_ENTITY_TYPE, "wilderwild:termite_mound", FabricBlockEntityTypeBuilder.create(TermiteMoundBlockEntity::new, RegisterBlocks.TERMITE_MOUND).build(null));

    }
}
