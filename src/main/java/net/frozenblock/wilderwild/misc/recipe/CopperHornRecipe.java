package net.frozenblock.wilderwild.misc.recipe;

import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;

public class CopperHornRecipe
	extends ShapedRecipe {

	public CopperHornRecipe(ResourceLocation id, CraftingBookCategory category) {
		super(id, "", category, 3, 3, NonNullList.of(Ingredient.EMPTY, new Ingredient[]{Ingredient.of(Items.PAPER), Ingredient.of(Items.PAPER), Ingredient.of(Items.PAPER), Ingredient.of(Items.PAPER), Ingredient.of(Items.FILLED_MAP), Ingredient.of(Items.PAPER), Ingredient.of(Items.PAPER), Ingredient.of(Items.PAPER), Ingredient.of(Items.PAPER)}), new ItemStack(Items.MAP));
	}

	@Override
	public boolean matches(CraftingContainer inv, Level level) {
		if (!super.matches(inv, level)) {
			return false;
		}
		ItemStack itemStack = MapExtendingRecipe.findFilledMap(inv);
		if (itemStack.isEmpty()) {
			return false;
		}
		MapItemSavedData mapItemSavedData = MapItem.getSavedData(itemStack, level);
		if (mapItemSavedData == null) {
			return false;
		}
		if (mapItemSavedData.isExplorationMap()) {
			return false;
		}
		return mapItemSavedData.scale < 4;
	}

	@Override
	public ItemStack assemble(CraftingContainer container, RegistryAccess registryAccess) {
		ItemStack itemStack = MapExtendingRecipe.findFilledMap(container).copyWithCount(1);
		itemStack.getOrCreateTag().putInt("map_scale_direction", 1);
		ItemStack stack = super.assemble(container, registryAccess);
		if (stack.getTag() != null) {
			CompoundTag instrumentTag = stack.getTag().getCompound("Instrument");
			String stringValue = property.getName();
			if (stateTag.contains(stringValue)) {
				return property.getValue(stateTag.getString(stringValue)).orElse(defaultValue);
			}
		}
		return super.assemble(container, registryAccess);
	}

	private static ItemStack findFilledMap(CraftingContainer container) {
		for (int i = 0; i < container.getContainerSize(); ++i) {
			ItemStack itemStack = container.getItem(i);
			if (!itemStack.is(Items.FILLED_MAP)) continue;
			return itemStack;
		}
		return ItemStack.EMPTY;
	}

	@Override
	public boolean isSpecial() {
		return true;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return RecipeSerializer.MAP_EXTENDING;
	}
}

