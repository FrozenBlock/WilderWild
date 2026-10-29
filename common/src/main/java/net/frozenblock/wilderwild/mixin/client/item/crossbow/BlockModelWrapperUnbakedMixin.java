/*
 * Copyright 2026 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can modify it under
 * the terms of version 1 of the FrozenBlock Modding Oasis License
 * as published by FrozenBlock Modding Oasis.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * FrozenBlock Modding Oasis License for more details.
 *
 * You should have received a copy of the FrozenBlock Modding Oasis License
 * along with this program; if not, see <https://github.com/FrozenBlock/Licenses>.
 */

package net.frozenblock.wilderwild.mixin.client.item.crossbow;

import com.google.common.collect.ImmutableMap;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import java.util.Map;
import net.frozenblock.lib.data.api.client.FakeUnbakedItemModel;
import net.frozenblock.lib.data.api.client.NamedModelState;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.client.renderer.item.properties.conditional.ChargedHarpoon;
import net.mehvahdjukaar.candlelight.api.ClientOnly;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.renderer.block.dispatch.ModelState;
import net.minecraft.client.renderer.item.CuboidItemModelWrapper;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.ResolvedModel;
import net.minecraft.client.resources.model.geometry.QuadCollection;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.client.resources.model.sprite.TextureSlots;
import net.minecraft.resources.Identifier;
import org.joml.Matrix4fc;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@ClientOnly
@Mixin(value = CuboidItemModelWrapper.Unbaked.class, priority = 898)
public abstract class BlockModelWrapperUnbakedMixin {

	@Shadow
	public abstract Identifier model();

	@Shadow
	public abstract ItemModel bake(ItemModel.BakingContext context, Matrix4fc transformation);

	@Unique
	private boolean wilderWild$generatingNewModel = false;
	@Unique
	private Material wilderWild$material = null;

	@ModifyReturnValue(method = "bake", at = @At("RETURN"))
	public ItemModel wilderWild$createCrossbowHarpoon(
		ItemModel original,
		ItemModel.BakingContext context, Matrix4fc transformation,
		@Local(name = "textureSlots") TextureSlots textureSlots
	) {
		if (this.wilderWild$generatingNewModel) return original;

		final Identifier id = this.model();
		if (!id.getNamespace().equals("minecraft") || !id.getPath().equals("item/crossbow_arrow")) return original;

		final Material layer0Material = textureSlots.getMaterial("layer0");
		if (layer0Material == null) return original;

		this.wilderWild$generatingNewModel = true;
		this.wilderWild$material = new Material(WWConstants.id("item/crossbow_harpoon"), layer0Material.forceTranslucent());
		final ItemModel.Unbaked loadedHarpoon = new FakeUnbakedItemModel(this.bake(context, transformation));
		this.wilderWild$generatingNewModel = false;
		this.wilderWild$material = null;

		if (WWConstants.UNSTABLE_LOGGING) WWConstants.LOGGER.info("Created Crossbow Harpoon Model!");

		return wilderWild$createCrossbowHarpoonDispatch(
			new FakeUnbakedItemModel(original),
			loadedHarpoon
		).bake(context, transformation);
	}

	@ModifyExpressionValue(
		method = "bake",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/resources/model/ResolvedModel;getTopTextureSlots()Lnet/minecraft/client/resources/model/sprite/TextureSlots;"
		)
	)
	public TextureSlots wilderWild$useCrossbowHarpoonSlots(TextureSlots original) {
		if (!this.wilderWild$generatingNewModel) return original;

		final Map<String, Material> newSlotsMap = new Object2ObjectLinkedOpenHashMap<>();
		newSlotsMap.putAll(original.resolvedValues);
		newSlotsMap.put("layer0", this.wilderWild$material);
		newSlotsMap.put("particle", this.wilderWild$material);
		return new TextureSlots(ImmutableMap.copyOf(newSlotsMap));
	}

	@WrapOperation(
		method = "bake",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/resources/model/ResolvedModel;bakeTopGeometry(Lnet/minecraft/client/resources/model/sprite/TextureSlots;Lnet/minecraft/client/resources/model/ModelBaker;Lnet/minecraft/client/renderer/block/dispatch/ModelState;)Lnet/minecraft/client/resources/model/geometry/QuadCollection;"
		)
	)
	public QuadCollection wilderWild$useCrossbowHarpoonModelState(
		ResolvedModel instance, TextureSlots textureSlots, ModelBaker baker, ModelState state, Operation<QuadCollection> original
	) {
		if (this.wilderWild$generatingNewModel) return original.call(instance, textureSlots, baker, NamedModelState.create(WWConstants.id("crossbow_harpoon"), state));
		return original.call(instance, textureSlots, baker, state);
	}

	@Unique
	private static ItemModel.Unbaked wilderWild$createCrossbowHarpoonDispatch(ItemModel.Unbaked original, ItemModel.Unbaked loadedHarpoon) {
		return ItemModelUtils.conditional(new ChargedHarpoon(), loadedHarpoon, original);
	}
}
