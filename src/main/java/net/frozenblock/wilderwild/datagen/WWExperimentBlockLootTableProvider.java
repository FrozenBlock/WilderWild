package net.frozenblock.wilderwild.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import org.jetbrains.annotations.NotNull;

final class WWExperimentBlockLootTableProvider extends FabricBlockLootTableProvider {
	WWExperimentBlockLootTableProvider(@NotNull FabricDataOutput dataOutput) {
		super(dataOutput);
	}

	@Override
	public void generate() {
		this.dropSelf(RegisterBlocks.BAOBAB_HANGING_SIGN);
		this.dropSelf(RegisterBlocks.CYPRESS_HANGING_SIGN);
	}
}
