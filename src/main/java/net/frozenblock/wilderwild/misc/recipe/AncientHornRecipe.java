package net.frozenblock.wilderwild.misc.recipe;

import net.frozenblock.wilderwild.registry.RegisterItems;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.InstrumentItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;

public class AncientHornRecipe extends ShapedRecipe {

	public AncientHornRecipe(CraftingBookCategory category) {
		super("wilderwild_ancient_horn", category, 3, 3,
			NonNullList.of(
				Ingredient.of(RegisterItems.ANCIENT_HORN_FRAGMENT), Ingredient.of(Items.GOLD_INGOT), Ingredient.of(RegisterItems.ANCIENT_HORN_FRAGMENT),
				Ingredient.of(RegisterItems.ANCIENT_HORN_FRAGMENT), Ingredient.of(Items.ECHO_SHARD), Ingredient.of(RegisterItems.ANCIENT_HORN_FRAGMENT),
				Ingredient.of(Items.ECHO_SHARD), Ingredient.of(RegisterItems.ANCIENT_HORN_FRAGMENT), Ingredient.of(Items.ECHO_SHARD)
			), new ItemStack(RegisterItems.COPPER_HORN)
		);
	}

	@Override
	public ItemStack assemble(CraftingContainer container, RegistryAccess registryAccess) {
		return InstrumentItem.create(super.assemble(container, registryAccess).getItem(), BuiltInRegistries.INSTRUMENT.getHolderOrThrow(RegisterItems.ANCIENT_HORN_INSTRUMENT));
	}

	@Override
	public boolean isSpecial() {
		return true;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return RegisterItems.ANCIENT_HORN_CRAFTING;
	}
}

