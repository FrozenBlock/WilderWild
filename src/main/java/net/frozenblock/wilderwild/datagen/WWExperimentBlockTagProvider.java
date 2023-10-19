package net.frozenblock.wilderwild.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

final class WWExperimentBlockTagProvider extends FabricTagProvider.BlockTagProvider {

	WWExperimentBlockTagProvider(@NotNull FabricDataOutput output, @NotNull CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, registriesFuture);
	}

	private static ResourceKey<Block> key(@NotNull Block block) {
		return BuiltInRegistries.BLOCK.getResourceKey(block).orElseThrow();
	}

	@Override
	protected void addTags(@NotNull HolderLookup.Provider arg) {
		this.tag(BlockTags.CEILING_HANGING_SIGNS)
			.add(key(RegisterBlocks.BAOBAB_HANGING_SIGN))
			.add(key(RegisterBlocks.CYPRESS_HANGING_SIGN));

		this.tag(BlockTags.WALL_HANGING_SIGNS)
			.add(key(RegisterBlocks.BAOBAB_WALL_HANGING_SIGN))
			.add(key(RegisterBlocks.CYPRESS_WALL_HANGING_SIGN));
	}
}
