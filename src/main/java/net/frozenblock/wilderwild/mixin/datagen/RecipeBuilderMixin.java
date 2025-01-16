package net.frozenblock.wilderwild.mixin.datagen;

import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.datagen.recipe.WWRecipeProvider;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(RecipeBuilder.class)
public interface RecipeBuilderMixin {

	@ModifyVariable(
		method = "save(Lnet/minecraft/data/recipes/RecipeOutput;Ljava/lang/String;)V",
		at = @At("HEAD"),
		argsOnly = true,
		ordinal = 0
	)
	default String trailierTales$save(String original) {
		if (WWRecipeProvider.GENERATING_WW_RECIPES) {
			ResourceLocation originalLocation = ResourceLocation.tryParse(original);
			ResourceLocation wilderLocation = WWConstants.id(originalLocation.getPath());
			return wilderLocation.toString();
		}
		return original;
	}

}
