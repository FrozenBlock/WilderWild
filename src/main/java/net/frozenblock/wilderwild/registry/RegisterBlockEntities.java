package net.frozenblock.wilderwild.registry;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.block.entity.DisplayLanternBlockEntity;
import net.frozenblock.wilderwild.block.entity.HangingTendrilBlockEntity;
import net.frozenblock.wilderwild.block.entity.StoneChestBlockEntity;
import net.frozenblock.wilderwild.block.entity.TermiteMoundBlockEntity;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.entity.BlockEntityType;

public final class RegisterBlockEntities {
	private RegisterBlockEntities() {
		throw new UnsupportedOperationException("RegisterBlockEntities contains only static declarations.");
	}

    public static final BlockEntityType<HangingTendrilBlockEntity> HANGING_TENDRIL = Registry.register(Registry.BLOCK_ENTITY_TYPE, WilderWild.id("hanging_tendril"), FabricBlockEntityTypeBuilder.create(HangingTendrilBlockEntity::new, RegisterBlocks.HANGING_TENDRIL).build(null));
    public static final BlockEntityType<TermiteMoundBlockEntity> TERMITE_MOUND = Registry.register(Registry.BLOCK_ENTITY_TYPE, WilderWild.id("termite_mound"), FabricBlockEntityTypeBuilder.create(TermiteMoundBlockEntity::new, RegisterBlocks.TERMITE_MOUND).build(null));
    public static final BlockEntityType<DisplayLanternBlockEntity> DISPLAY_LANTERN = Registry.register(Registry.BLOCK_ENTITY_TYPE, WilderWild.id("display_lantern"), FabricBlockEntityTypeBuilder.create(DisplayLanternBlockEntity::new, RegisterBlocks.DISPLAY_LANTERN).build(null));
    public static final BlockEntityType<StoneChestBlockEntity> STONE_CHEST = Registry.register(Registry.BLOCK_ENTITY_TYPE, WilderWild.id("stone_chest"), FabricBlockEntityTypeBuilder.create(StoneChestBlockEntity::new, RegisterBlocks.STONE_CHEST).build(null));

    public static void register() {
        WilderWild.logWild("Registering BlockEntities for", WilderWild.UNSTABLE_LOGGING);
    }
}
