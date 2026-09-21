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

package net.frozenblock.wilderwild.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.datafixers.util.Either;
import java.util.Collection;
import java.util.List;
import net.minecraft.tags.TagEntry;
import net.minecraft.tags.TagLoader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// todo remove this once multiloader data is done
@Mixin(TagLoader.class)
public class TempTagLoaderMixin<T> {

	@Inject(
		method = "tryBuildTag",
		at = @At(
			value = "INVOKE",
			target = "Ljava/util/List;isEmpty()Z"
		)
	)
	private void wilderWild$TEMPORARY$ignoreInvalidTags(
		TagEntry.Lookup<T> lookup,
		List<TagLoader.EntryWithSource> entries,
		CallbackInfoReturnable<Either<Collection<TagLoader.EntryWithSource>, Collection<T>>> info,
		@Local(name = "missingElements") List<TagLoader.EntryWithSource> missingElements
	) {
		missingElements.clear();
	}
}
