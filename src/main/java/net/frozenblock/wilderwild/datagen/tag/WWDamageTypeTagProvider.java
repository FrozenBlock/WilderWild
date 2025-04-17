/*
 * Copyright 2025 FrozenBlock
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

package net.frozenblock.wilderwild.datagen.tag;

import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.frozenblock.wilderwild.registry.WWDamageTypes;
import net.frozenblock.wilderwild.tag.WWDamageTypeTags;
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
			.add(WWDamageTypes.TUMBLEWEED)
			.add(WWDamageTypes.PRICKLY_PEAR);

		this.getOrCreateTagBuilder(DamageTypeTags.BYPASSES_ARMOR)
			.add(WWDamageTypes.PRICKLY_PEAR);

		this.getOrCreateTagBuilder(DamageTypeTags.BYPASSES_EFFECTS)
			.add(WWDamageTypes.PRICKLY_PEAR);

		this.getOrCreateTagBuilder(DamageTypeTags.CAN_BREAK_ARMOR_STAND)
			.add(WWDamageTypes.OSTRICH);

		this.getOrCreateTagBuilder(DamageTypeTags.PANIC_CAUSES)
			.add(WWDamageTypes.TUMBLEWEED);

		this.getOrCreateTagBuilder(DamageTypeTags.BYPASSES_SHIELD)
			.add(WWDamageTypes.FALLING_ICICLE);

		this.getOrCreateTagBuilder(DamageTypeTags.NO_KNOCKBACK)
			.add(WWDamageTypes.PRICKLY_PEAR);

		this.getOrCreateTagBuilder(WWDamageTypeTags.EMPTY);
	}
}
