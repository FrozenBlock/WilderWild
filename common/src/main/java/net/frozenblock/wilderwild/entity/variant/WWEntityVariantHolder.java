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

package net.frozenblock.wilderwild.entity.variant;

import java.util.Optional;
import net.frozenblock.lib.platform.api.attachment.DataAttachmentType;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.variant.VariantUtils;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.Nullable;

public interface WWEntityVariantHolder<T> {

	ResourceKey<Registry<T>> wilderWild$variantRegistry();

	ResourceKey<T> wilderWild$defaultVariant();

	DataAttachmentType<Holder<T>> wilderWild$variantAttachmentType();

	default void wilderWild$setVariant(Holder<T> variant) {
		this.wilderWild$variantAttachmentType().set((Entity) this, variant);
	}

	default @Nullable Holder<T> wilderWild$getVariant() {
		return this.wilderWild$variantAttachmentType().get((Entity) this);
	}

	default T wilderWild$getVariantOr(T fallback) {
		final Holder<T> variant = this.wilderWild$getVariant();
		if (variant != null) return variant.value();
		return fallback;
	}

	default void wilderWild$setVariantToDefaultIfEmpty() {
		if (this.wilderWild$getVariant() != null) return;

		final RegistryAccess registryAccess = ((Entity) this).registryAccess();
		final Holder<T> defaultVariant = VariantUtils.getDefaultOrAny(registryAccess, this.wilderWild$defaultVariant());
		this.wilderWild$setVariant(defaultVariant);
	}

	default void wilderWild$writeVariant(ValueOutput output) {
		final Holder<T> variant = this.wilderWild$getVariant();
		if (variant != null) VariantUtils.writeVariant(output, variant);
	}

	default Optional<Holder<T>> wilderWild$readVariant(ValueInput input) {
		return VariantUtils.readVariant(input, this.wilderWild$variantRegistry());
	}
}
