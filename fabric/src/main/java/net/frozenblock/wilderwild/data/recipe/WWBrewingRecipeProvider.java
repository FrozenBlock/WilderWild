package net.frozenblock.wilderwild.data.recipe;

import net.frozenblock.wilderwild.registry.WWItems;
import net.frozenblock.wilderwild.registry.WWPotions;
import net.minecraft.core.Holder;
import net.minecraft.data.recipes.BrewingRecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;

public final class WWBrewingRecipeProvider {

	static void buildRecipes(RecipeOutput output) {
		buildMix(output, Potions.AWKWARD, WWItems.CRAB_CLAW.get(), WWPotions.REACH.asHolder());
		buildMix(output, WWPotions.REACH.asHolder(), Items.REDSTONE, WWPotions.LONG_REACH.asHolder());
		buildMix(output, WWPotions.REACH.asHolder(), Items.GLOWSTONE_DUST, WWPotions.STRONG_REACH.asHolder());
		buildMix(output, Potions.AWKWARD, WWItems.FERMENTED_SCORCHED_EYE.get(), WWPotions.SCORCHING.asHolder());
	}

	private static void buildMix(RecipeOutput output, Holder<Potion> input, Item reagent, Holder<Potion> result) {
		BrewingRecipeBuilder.brewingMix(Items.POTION, input, reagent, result).save(output);
		BrewingRecipeBuilder.brewingMix(Items.SPLASH_POTION, input, reagent, result).save(output);
		BrewingRecipeBuilder.brewingMix(Items.LINGERING_POTION, input, reagent, result).save(output);
	}
}
