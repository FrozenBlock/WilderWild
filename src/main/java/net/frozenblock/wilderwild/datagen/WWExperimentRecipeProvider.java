package net.frozenblock.wilderwild.datagen;

import net.frozenblock.wilderwild.misc.WilderFeatureFlags;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.registry.RegisterItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.flag.FeatureFlagSet;
import org.jetbrains.annotations.NotNull;
import java.util.function.Consumer;

final class WWExperimentRecipeProvider extends RecipeProvider {

	WWExperimentRecipeProvider(@NotNull PackOutput packOutput) {
		super(packOutput);
	}

	@Override
	public void buildRecipes(final @NotNull Consumer<FinishedRecipe> output) {
		generateForEnabledBlockFamilies(output, FeatureFlagSet.of(WilderFeatureFlags.UPDATE_1_20_ADDITIONS));
		hangingSign(output, RegisterItems.BAOBAB_HANGING_SIGN, RegisterBlocks.STRIPPED_BAOBAB_LOG);
		hangingSign(output, RegisterItems.CYPRESS_HANGING_SIGN, RegisterBlocks.STRIPPED_CYPRESS_LOG);
	}
}
