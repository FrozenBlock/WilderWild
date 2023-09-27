package net.frozenblock.wilderwild.misc.recipe;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import net.frozenblock.wilderwild.registry.RegisterItems;
import net.minecraft.core.Holder;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.Instrument;
import net.minecraft.world.item.InstrumentItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class CopperHornRecipe extends ShapedRecipe {

	public static final Map<Instrument, Instrument> INSTRUMENT_TO_COPPER_INSTRUMENT_MAP = new HashMap<>();

	public CopperHornRecipe(CraftingBookCategory category) {
		super("", category, 3, 2, NonNullList.of(Ingredient.EMPTY, new Ingredient[]{Ingredient.of(Items.COPPER_INGOT), Ingredient.of(Items.GOAT_HORN), Ingredient.of(Items.COPPER_INGOT), Ingredient.of(Items.AIR), Ingredient.of(Items.COPPER_INGOT), Ingredient.of(Items.AIR)}), new ItemStack(RegisterItems.COPPER_HORN));
	}

	@Override
	public boolean matches(CraftingContainer inv, Level level) {
		if (!super.matches(inv, level)) {
			return false;
		}

		ItemStack itemStack = findGoatHorn(inv);
		if (itemStack.isEmpty()) {
			return false;
		}

		if (itemStack.getItem() instanceof InstrumentItem instrumentItem) {
			return instrumentItem.getInstrument(itemStack).filter(instrumentHolder -> INSTRUMENT_TO_COPPER_INSTRUMENT_MAP.containsKey(instrumentHolder.value())).isPresent();
		}
		return false;
	}

	@Override
	public ItemStack assemble(CraftingContainer container, RegistryAccess registryAccess) {
		ItemStack goatHorn = findGoatHorn(container);
		Optional<? extends Holder<Instrument>> optionalInstrument;
		if (goatHorn.getItem() instanceof InstrumentItem instrumentItem && (optionalInstrument = instrumentItem.getInstrument(goatHorn)).isPresent()) {
			return InstrumentItem.create(super.assemble(container, registryAccess).getItem(), optionalInstrument.get());
		}
		return super.assemble(container, registryAccess);
	}

	private static ItemStack findGoatHorn(@NotNull CraftingContainer container) {
		for(int i = 0; i < container.getContainerSize(); ++i) {
			ItemStack itemStack = container.getItem(i);
			if (itemStack.is(Items.GOAT_HORN)) {
				return itemStack;
			}
		}

		return ItemStack.EMPTY;
	}

	@Override
	public boolean isSpecial() {
		return true;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return RegisterItems.COPPER_HORN_CRAFTING;
	}
}

