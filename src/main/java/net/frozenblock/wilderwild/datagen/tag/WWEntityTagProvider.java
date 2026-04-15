/*
 * Copyright 2025-2026 FrozenBlock
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
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.frozenblock.lib.tag.api.FrozenEntityTags;
import net.frozenblock.wilderwild.references.WWEntityTypeIds;
import net.frozenblock.wilderwild.tag.WWEntityTypeTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.EntityTypeIds;

public final class WWEntityTagProvider extends FabricTagsProvider.EntityTypeTagsProvider {

	public WWEntityTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
		super(output, registries);
	}

	@Override
	protected void addTags(HolderLookup.Provider arg) {
		this.builder(FrozenEntityTags.CREEPER_IGNORES)
			.add(WWEntityTypeIds.JELLYFISH);

		this.builder(FrozenEntityTags.WARDEN_CANNOT_TARGET)
			.add(WWEntityTypeIds.TUMBLEWEED);

		this.builder(WWEntityTypeTags.CAN_SWIM_IN_ALGAE)
			.add(EntityTypeIds.SLIME)
			.add(EntityTypeIds.WARDEN)
			.add(EntityTypeIds.WITHER)
			.addOptionalTag(EntityTypeTags.AQUATIC);

		this.builder(WWEntityTypeTags.STAYS_IN_MESOGLEA)
			.add(WWEntityTypeIds.JELLYFISH);

		this.builder(WWEntityTypeTags.JELLYFISH_CANT_STING)
			.add(EntityTypeIds.SQUID, EntityTypeIds.GLOW_SQUID)
			.add(EntityTypeIds.DOLPHIN)
			.add(EntityTypeIds.TROPICAL_FISH)
			.add(EntityTypeIds.PUFFERFISH)
			.add(EntityTypeIds.AXOLOTL)
			.add(EntityTypeIds.TURTLE)
			.add(EntityTypeIds.NAUTILUS, EntityTypeIds.ZOMBIE_NAUTILUS)
			.add(WWEntityTypeIds.JELLYFISH)
			.add(WWEntityTypeIds.TUMBLEWEED);

		this.builder(WWEntityTypeTags.COCONUT_CANT_BONK)
			.add(EntityTypeIds.SQUID, EntityTypeIds.GLOW_SQUID)
			.add(EntityTypeIds.DOLPHIN)
			.add(EntityTypeIds.SPIDER)
			.add(EntityTypeIds.CAVE_SPIDER)
			.add(WWEntityTypeIds.SCORCHED)
			.add(EntityTypeIds.GHAST)
			.add(WWEntityTypeIds.FIREFLY, WWEntityTypeIds.BUTTERFLY)
			.add(WWEntityTypeIds.JELLYFISH)
			.add(WWEntityTypeIds.TUMBLEWEED)
			.add(EntityTypeIds.BREEZE);

		this.builder(WWEntityTypeTags.COCONUT_CANT_SPLIT)
			.add(EntityTypeIds.SQUID, EntityTypeIds.GLOW_SQUID)
			.add(EntityTypeIds.DOLPHIN)
			.add(EntityTypeIds.COD)
			.add(EntityTypeIds.SALMON)
			.add(EntityTypeIds.TROPICAL_FISH)
			.add(EntityTypeIds.PUFFERFISH)
			.add(EntityTypeIds.SPIDER)
			.add(EntityTypeIds.CAVE_SPIDER)
			.add(WWEntityTypeIds.SCORCHED)
			.add(EntityTypeIds.GHAST)
			.add(WWEntityTypeIds.FIREFLY, WWEntityTypeIds.BUTTERFLY)
			.add(WWEntityTypeIds.JELLYFISH)
			.add(WWEntityTypeIds.TUMBLEWEED)
			.add(EntityTypeIds.BREEZE);

		this.builder(WWEntityTypeTags.TUMBLEWEED_PASSES_THROUGH)
			.add(EntityTypeIds.BREEZE);

		this.builder(EntityTypeTags.CAN_BREATHE_UNDER_WATER)
			.add(EntityTypeIds.WARDEN)
			.add(WWEntityTypeIds.CRAB)
			.add(WWEntityTypeIds.JELLYFISH)
			.add(WWEntityTypeIds.PENGUIN);

		this.builder(EntityTypeTags.NOT_SCARY_FOR_PUFFERFISH)
			.add(WWEntityTypeIds.JELLYFISH);

		this.builder(WWEntityTypeTags.CRAB_HUNT_TARGETS)
			.add(EntityTypeIds.SQUID, EntityTypeIds.GLOW_SQUID)
			.add(EntityTypeIds.COD)
			.add(EntityTypeIds.SALMON)
			.add(EntityTypeIds.TROPICAL_FISH)
			.add(EntityTypeIds.TADPOLE);

		this.builder(WWEntityTypeTags.PENGUIN_HUNT_TARGETS)
			.add(EntityTypeIds.SQUID, EntityTypeIds.GLOW_SQUID);

		this.builder(WWEntityTypeTags.LEAF_PARTICLES_FRANTIC_SPAWN)
			.add(EntityTypeIds.BREEZE);

		this.builder(WWEntityTypeTags.GEYSER_PUSHES_FURTHER)
			.add(EntityTypeIds.ARROW, EntityTypeIds.SPECTRAL_ARROW);

		this.builder(WWEntityTypeTags.GEYSER_CANNOT_PUSH)
			.add(EntityTypeIds.WITHER, EntityTypeIds.ENDER_DRAGON)
			.add(EntityTypeIds.VEX)
			.add(EntityTypeIds.EYE_OF_ENDER);

		this.builder(WWEntityTypeTags.GEYSER_CANNOT_PUSH)
			.addOptional(ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath("trailiertales", "apparition")));

		this.builder(WWEntityTypeTags.FRAGILE_ICE_UNWALKABLE_MOBS)
			.add(EntityTypeIds.PLAYER)
			.add(EntityTypeIds.IRON_GOLEM)
			.add(EntityTypeIds.RAVAGER)
			.add(EntityTypeIds.SNIFFER)
			.add(EntityTypeIds.HOGLIN)
			.add(EntityTypeIds.ZOGLIN)
			.add(EntityTypeIds.BLAZE)
			.add(EntityTypeIds.MAGMA_CUBE)
			.add(EntityTypeIds.GIANT);

		this.builder(WWEntityTypeTags.FRAGILE_ICE_UNWALKABLE_MOBS)
			.addOptional(ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath("netheriernether", "wildfire")))
			.addOptional(ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath("netheriernether", "soul_blaze")));

		this.builder(WWEntityTypeTags.FRAGILE_ICE_DOESNT_CRACK_ON_FALL)
			.add(EntityTypeIds.PHANTOM)
			.add(EntityTypeIds.BREEZE)
			.add(EntityTypeIds.CHICKEN)
			.add(EntityTypeIds.CAT)
			.add(EntityTypeIds.OCELOT)
			.add(EntityTypeIds.RABBIT)
			.add(EntityTypeIds.ITEM)
			.add(EntityTypeIds.PARROT)
			.add(EntityTypeIds.BEE)
			.add(EntityTypeIds.BAT)
			.add(EntityTypeIds.ALLAY)
			.add(EntityTypeIds.EXPERIENCE_ORB)
			.add(WWEntityTypeIds.FIREFLY)
			.add(WWEntityTypeIds.BUTTERFLY)
			.add(WWEntityTypeIds.TUMBLEWEED);

		this.builder(WWEntityTypeTags.FRAGILE_ICE_DOESNT_CRACK_PROJECTILE)
			.add(EntityTypeIds.EGG)
			.add(EntityTypeIds.SPLASH_POTION)
			.add(EntityTypeIds.LINGERING_POTION)
			.add(EntityTypeIds.SNOWBALL)
			.add(EntityTypeIds.EXPERIENCE_BOTTLE)
			.add(EntityTypeIds.WIND_CHARGE)
			.add(EntityTypeIds.BREEZE_WIND_CHARGE)
			.add(EntityTypeIds.LLAMA_SPIT)
			.add(EntityTypeIds.ENDER_PEARL)
			.add(EntityTypeIds.FIREWORK_ROCKET);

		this.builder(EntityTypeTags.FREEZE_IMMUNE_ENTITY_TYPES)
			.add(WWEntityTypeIds.PENGUIN);

		this.builder(EntityTypeTags.NO_ANGER_FROM_WIND_CHARGE)
			.add(WWEntityTypeIds.SCORCHED);

		this.builder(EntityTypeTags.FROG_FOOD)
			.add(WWEntityTypeIds.BUTTERFLY);

		this.builder(EntityTypeTags.DISMOUNTS_UNDERWATER)
			.add(WWEntityTypeIds.OSTRICH, WWEntityTypeIds.ZOMBIE_OSTRICH)
			.add(WWEntityTypeIds.SCORCHED);

		this.builder(EntityTypeTags.ARTHROPOD)
			.add(WWEntityTypeIds.CRAB)
			.add(WWEntityTypeIds.SCORCHED);

		this.builder(EntityTypeTags.AQUATIC)
			.add(WWEntityTypeIds.CRAB)
			.add(WWEntityTypeIds.JELLYFISH);

		this.builder(EntityTypeTags.FREEZE_HURTS_EXTRA_TYPES)
			.add(WWEntityTypeIds.SCORCHED);

		this.builder(EntityTypeTags.BOAT)
			.add(
				WWEntityTypeIds.BAOBAB_BOAT,
				WWEntityTypeIds.BAOBAB_CHEST_BOAT,
				WWEntityTypeIds.WILLOW_BOAT,
				WWEntityTypeIds.WILLOW_CHEST_BOAT,
				WWEntityTypeIds.CYPRESS_BOAT,
				WWEntityTypeIds.CYPRESS_CHEST_BOAT,
				WWEntityTypeIds.PALM_BOAT,
				WWEntityTypeIds.PALM_CHEST_BOAT,
				WWEntityTypeIds.MAPLE_BOAT,
				WWEntityTypeIds.MAPLE_CHEST_BOAT
			);

		this.builder(EntityTypeTags.CANNOT_BE_PUSHED_ONTO_BOATS)
			.add(WWEntityTypeIds.FIREFLY, WWEntityTypeIds.BUTTERFLY)
			.add(WWEntityTypeIds.JELLYFISH)
			.add(WWEntityTypeIds.TUMBLEWEED);

		this.builder(EntityTypeTags.CAN_EQUIP_SADDLE)
			.add(WWEntityTypeIds.OSTRICH, WWEntityTypeIds.ZOMBIE_OSTRICH);

		this.builder(EntityTypeTags.CAN_FLOAT_WHILE_RIDDEN)
			.add(WWEntityTypeIds.OSTRICH, WWEntityTypeIds.ZOMBIE_OSTRICH);

		this.builder(EntityTypeTags.ZOMBIES)
			.add(WWEntityTypeIds.ZOMBIE_OSTRICH);

		this.builder(EntityTypeTags.BURN_IN_DAYLIGHT)
			.add(WWEntityTypeIds.ZOMBIE_OSTRICH);
	}
}
