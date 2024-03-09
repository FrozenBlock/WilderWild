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

package net.frozenblock.wilderwild.datagen.tag;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.frozenblock.wilderwild.registry.RegisterDamageTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageType;
import org.jetbrains.annotations.NotNull;

public final class WWDamageTypeTagProvider extends FabricTagProvider<DamageType> {

	public WWDamageTypeTagProvider(@NotNull FabricDataOutput output, @NotNull CompletableFuture<HolderLookup.Provider> registries) {
		super(output, Registries.DAMAGE_TYPE, registries);
	}

	@Override
	public void addTags(@NotNull HolderLookup.Provider arg) {
		this.getOrCreateTagBuilder(DamageTypeTags.NO_ANGER)
			.add(RegisterDamageTypes.TUMBLEWEED)
			.add(RegisterDamageTypes.PRICKLY_PEAR);

		this.getOrCreateTagBuilder(DamageTypeTags.BYPASSES_ARMOR)
			.add(RegisterDamageTypes.PRICKLY_PEAR)
			.add(RegisterDamageTypes.ANCIENT_HORN);

		this.getOrCreateTagBuilder(DamageTypeTags.BYPASSES_EFFECTS)
			.add(RegisterDamageTypes.PRICKLY_PEAR);

		this.getOrCreateTagBuilder(DamageTypeTags.BYPASSES_ENCHANTMENTS)
			.add(RegisterDamageTypes.ANCIENT_HORN);

		this.getOrCreateTagBuilder(DamageTypeTags.WITCH_RESISTANT_TO)
			.add(RegisterDamageTypes.ANCIENT_HORN);
	}
}
