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

package net.frozenblock.wilderwild.datagen;

import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.frozenblock.wilderwild.registry.RegisterEntities;
import net.frozenblock.wilderwild.tag.WilderEntityTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.EntityType;
import org.jetbrains.annotations.NotNull;

final class WWEntityTagProvider extends FabricTagProvider.EntityTypeTagProvider {

	public WWEntityTagProvider(@NotNull FabricDataOutput output, @NotNull CompletableFuture<HolderLookup.Provider> completableFuture) {
		super(output, completableFuture);
	}

	@Override
	protected void addTags(@NotNull HolderLookup.Provider arg) {
		this.getOrCreateTagBuilder(WilderEntityTags.CAN_SWIM_IN_ALGAE)
			.add(EntityType.SLIME)
			.add(EntityType.WARDEN)
			.add(EntityType.WITHER)
			.add(RegisterEntities.JELLYFISH);

		this.getOrCreateTagBuilder(WilderEntityTags.STAYS_IN_MESOGLEA)
			.add(RegisterEntities.JELLYFISH);

		this.getOrCreateTagBuilder(WilderEntityTags.JELLYFISH_CANT_STING)
			.add(EntityType.SQUID)
			.add(EntityType.GLOW_SQUID)
			.add(EntityType.DOLPHIN)
			.add(EntityType.TROPICAL_FISH)
			.add(EntityType.PUFFERFISH)
			.add(EntityType.AXOLOTL)
			.add(EntityType.TURTLE)
			.add(RegisterEntities.JELLYFISH)
			.add(RegisterEntities.TUMBLEWEED);

		this.getOrCreateTagBuilder(WilderEntityTags.COCONUT_CANT_BONK)
			.add(EntityType.SQUID)
			.add(EntityType.GLOW_SQUID)
			.add(EntityType.DOLPHIN)
			.add(EntityType.SPIDER)
			.add(EntityType.CAVE_SPIDER)
			.add(EntityType.GHAST)
			.add(RegisterEntities.FIREFLY)
			.add(RegisterEntities.JELLYFISH)
			.add(RegisterEntities.TUMBLEWEED)
			.add(EntityType.BREEZE);

		this.getOrCreateTagBuilder(WilderEntityTags.COCONUT_CANT_SPLIT)
			.add(EntityType.SQUID)
			.add(EntityType.GLOW_SQUID)
			.add(EntityType.DOLPHIN)
			.add(EntityType.COD)
			.add(EntityType.SALMON)
			.add(EntityType.TROPICAL_FISH)
			.add(EntityType.PUFFERFISH)
			.add(EntityType.SPIDER)
			.add(EntityType.CAVE_SPIDER)
			.add(EntityType.GHAST)
			.add(RegisterEntities.FIREFLY)
			.add(RegisterEntities.JELLYFISH)
			.add(RegisterEntities.TUMBLEWEED)
			.add(EntityType.BREEZE);

		this.getOrCreateTagBuilder(WilderEntityTags.ANCIENT_HORN_IMMUNE)
			.add(EntityType.WITHER)
			.add(EntityType.WARDEN)
			.add(EntityType.ENDER_DRAGON)
			.add(RegisterEntities.TUMBLEWEED)
			.add(EntityType.BREEZE);

		this.getOrCreateTagBuilder(WilderEntityTags.TUMBLEWEED_PASSES_THROUGH)
			.add(EntityType.BREEZE);

		this.getOrCreateTagBuilder(EntityTypeTags.CAN_BREATHE_UNDER_WATER)
			.add(EntityType.WARDEN)
			.add(RegisterEntities.CRAB)
			.add(RegisterEntities.JELLYFISH);

		this.getOrCreateTagBuilder(EntityTypeTags.NOT_SCARY_FOR_PUFFERFISH)
			.add(RegisterEntities.JELLYFISH);

		this.getOrCreateTagBuilder(WilderEntityTags.CRAB_HUNT_TARGETS)
			.add(EntityType.SQUID)
			.add(EntityType.GLOW_SQUID)
			.add(EntityType.COD)
			.add(EntityType.SALMON)
			.add(EntityType.TROPICAL_FISH)
			.add(EntityType.TADPOLE);

		this.getOrCreateTagBuilder(EntityTypeTags.DISMOUNTS_UNDERWATER)
			.add(RegisterEntities.OSTRICH);

		this.getOrCreateTagBuilder(EntityTypeTags.ARTHROPOD)
			.add(RegisterEntities.CRAB);

		this.getOrCreateTagBuilder(EntityTypeTags.AQUATIC)
			.add(RegisterEntities.CRAB)
			.add(RegisterEntities.JELLYFISH);

	}
}
