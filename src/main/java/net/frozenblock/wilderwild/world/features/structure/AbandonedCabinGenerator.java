/*
 * Copyright 2023-2024 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.wilderwild.world.features.structure;

import com.mojang.datafixers.util.Either;
import java.util.function.Function;
import net.frozenblock.wilderwild.WilderSharedConstants;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.pools.SinglePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import org.jetbrains.annotations.NotNull;

/**
 * Contains the StructureTemplatePool for Abandoned Cabins
 */
public class AbandonedCabinGenerator {

	public static final ResourceKey<StructureTemplatePool> CABIN = createKey("abandoned_cabin/cabin");

	/**
	 * @param id                 The id for the {@link SinglePoolElement}'s {@link ResourceLocation}
	 * @param processorListEntry The processor list for the {@link SinglePoolElement}
	 * @return A {@link SinglePoolElement} of the parameters given.
	 */
	@NotNull
	public static Function<StructureTemplatePool.Projection, SinglePoolElement> ofProcessedSingle(@NotNull String id, @NotNull Holder<StructureProcessorList> processorListEntry) {
		return projection -> new SinglePoolElement(Either.left(WilderSharedConstants.id(id)), processorListEntry, projection);
	}

	/**
	 * Initializes this class to register the {@link StructureTemplatePool}s
	 */
	public static void init() {
	}

	@NotNull
	public static ResourceKey<StructureTemplatePool> createKey(@NotNull String string) {
		return ResourceKey.create(Registries.TEMPLATE_POOL, WilderSharedConstants.id(string));
	}
}
