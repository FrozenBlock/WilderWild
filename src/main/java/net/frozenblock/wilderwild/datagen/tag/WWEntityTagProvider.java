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
import net.frozenblock.lib.tag.api.FrozenEntityTags;
import net.frozenblock.wilderwild.registry.WWEntityTypes;
import net.frozenblock.wilderwild.tag.WWEntityTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.EntityType;

public final class WWEntityTagProvider extends FabricTagProvider.EntityTypeTagProvider {

	public WWEntityTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registries) {
		super(output, registries);
	}

	@Override
	protected void addTags(HolderLookup.Provider arg) {
		this.valueLookupBuilder(FrozenEntityTags.CREEPER_IGNORES)
			.add(WWEntityTypes.JELLYFISH);

		this.valueLookupBuilder(FrozenEntityTags.WARDEN_CANNOT_TARGET)
			.add(WWEntityTypes.TUMBLEWEED);

		this.valueLookupBuilder(WWEntityTags.CAN_SWIM_IN_ALGAE)
			.add(EntityType.SLIME)
			.add(EntityType.WARDEN)
			.add(EntityType.WITHER)
			.addOptionalTag(EntityTypeTags.AQUATIC);

		this.valueLookupBuilder(WWEntityTags.STAYS_IN_MESOGLEA)
			.add(WWEntityTypes.JELLYFISH);

		this.valueLookupBuilder(WWEntityTags.JELLYFISH_CANT_STING)
			.add(EntityType.SQUID, EntityType.GLOW_SQUID)
			.add(EntityType.DOLPHIN)
			.add(EntityType.TROPICAL_FISH)
			.add(EntityType.PUFFERFISH)
			.add(EntityType.AXOLOTL)
			.add(EntityType.TURTLE)
			.add(WWEntityTypes.JELLYFISH)
			.add(WWEntityTypes.TUMBLEWEED);

		this.valueLookupBuilder(WWEntityTags.COCONUT_CANT_BONK)
			.add(EntityType.SQUID, EntityType.GLOW_SQUID)
			.add(EntityType.DOLPHIN)
			.add(EntityType.SPIDER)
			.add(EntityType.CAVE_SPIDER)
			.add(WWEntityTypes.SCORCHED)
			.add(EntityType.GHAST)
			.add(WWEntityTypes.FIREFLY)
			.add(WWEntityTypes.JELLYFISH)
			.add(WWEntityTypes.TUMBLEWEED)
			.add(EntityType.BREEZE);

		this.valueLookupBuilder(WWEntityTags.COCONUT_CANT_SPLIT)
			.add(EntityType.SQUID, EntityType.GLOW_SQUID)
			.add(EntityType.DOLPHIN)
			.add(EntityType.COD)
			.add(EntityType.SALMON)
			.add(EntityType.TROPICAL_FISH)
			.add(EntityType.PUFFERFISH)
			.add(EntityType.SPIDER)
			.add(EntityType.CAVE_SPIDER)
			.add(WWEntityTypes.SCORCHED)
			.add(EntityType.GHAST)
			.add(WWEntityTypes.FIREFLY)
			.add(WWEntityTypes.JELLYFISH)
			.add(WWEntityTypes.TUMBLEWEED)
			.add(EntityType.BREEZE);

		this.valueLookupBuilder(WWEntityTags.TUMBLEWEED_PASSES_THROUGH)
			.add(EntityType.BREEZE);

		this.valueLookupBuilder(EntityTypeTags.CAN_BREATHE_UNDER_WATER)
			.add(EntityType.WARDEN)
			.add(WWEntityTypes.CRAB)
			.add(WWEntityTypes.JELLYFISH)
			.add(WWEntityTypes.PENGUIN);

		this.valueLookupBuilder(EntityTypeTags.NOT_SCARY_FOR_PUFFERFISH)
			.add(WWEntityTypes.JELLYFISH);

		this.valueLookupBuilder(WWEntityTags.CRAB_HUNT_TARGETS)
			.add(EntityType.SQUID, EntityType.GLOW_SQUID)
			.add(EntityType.COD)
			.add(EntityType.SALMON)
			.add(EntityType.TROPICAL_FISH)
			.add(EntityType.TADPOLE);

		this.valueLookupBuilder(WWEntityTags.PENGUIN_HUNT_TARGETS)
			.add(EntityType.SQUID, EntityType.GLOW_SQUID);

		this.valueLookupBuilder(WWEntityTags.LEAF_PARTICLES_FRANTIC_SPAWN)
			.add(EntityType.BREEZE);

		this.valueLookupBuilder(WWEntityTags.GEYSER_PUSHES_FURTHER)
			.add(EntityType.ARROW, EntityType.SPECTRAL_ARROW);

		this.valueLookupBuilder(WWEntityTags.GEYSER_CANNOT_PUSH)
			.add(EntityType.WITHER, EntityType.ENDER_DRAGON)
			.add(EntityType.VEX)
			.add(EntityType.EYE_OF_ENDER);

		this.builder(WWEntityTags.GEYSER_CANNOT_PUSH)
			.addOptional(ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath("trailiertales", "apparition")));

		this.valueLookupBuilder(WWEntityTags.FRAGILE_ICE_UNWALKABLE_MOBS)
			.add(EntityType.PLAYER)
			.add(EntityType.IRON_GOLEM)
			.add(EntityType.RAVAGER)
			.add(EntityType.SNIFFER)
			.add(EntityType.HOGLIN)
			.add(EntityType.ZOGLIN)
			.add(EntityType.BLAZE)
			.add(EntityType.MAGMA_CUBE)
			.add(EntityType.GIANT);

		this.valueLookupBuilder(WWEntityTags.FRAGILE_ICE_DOESNT_CRACK_ON_FALL)
			.add(EntityType.PHANTOM)
			.add(EntityType.BREEZE)
			.add(EntityType.CHICKEN)
			.add(EntityType.CAT)
			.add(EntityType.OCELOT)
			.add(EntityType.RABBIT)
			.add(EntityType.ITEM)
			.add(EntityType.PARROT)
			.add(EntityType.BEE)
			.add(EntityType.BAT)
			.add(EntityType.ALLAY)
			.add(EntityType.EXPERIENCE_ORB)
			.add(WWEntityTypes.FIREFLY)
			.add(WWEntityTypes.BUTTERFLY)
			.add(WWEntityTypes.TUMBLEWEED);

		this.valueLookupBuilder(WWEntityTags.FRAGILE_ICE_DOESNT_CRACK_PROJECTILE)
			.add(EntityType.EGG)
			.add(EntityType.SPLASH_POTION)
			.add(EntityType.LINGERING_POTION)
			.add(EntityType.SNOWBALL)
			.add(EntityType.EXPERIENCE_BOTTLE)
			.add(EntityType.WIND_CHARGE)
			.add(EntityType.BREEZE_WIND_CHARGE)
			.add(EntityType.LLAMA_SPIT)
			.add(EntityType.ENDER_PEARL)
			.add(EntityType.FIREWORK_ROCKET);

		this.valueLookupBuilder(EntityTypeTags.FREEZE_IMMUNE_ENTITY_TYPES)
			.add(WWEntityTypes.PENGUIN);

		this.valueLookupBuilder(EntityTypeTags.FROG_FOOD)
			.add(WWEntityTypes.BUTTERFLY);

		this.valueLookupBuilder(EntityTypeTags.DISMOUNTS_UNDERWATER)
			.add(WWEntityTypes.OSTRICH)
			.add(WWEntityTypes.SCORCHED);

		this.valueLookupBuilder(EntityTypeTags.ARTHROPOD)
			.add(WWEntityTypes.CRAB)
			.add(WWEntityTypes.SCORCHED);

		this.valueLookupBuilder(EntityTypeTags.AQUATIC)
			.add(WWEntityTypes.CRAB)
			.add(WWEntityTypes.JELLYFISH);

		this.valueLookupBuilder(EntityTypeTags.FREEZE_HURTS_EXTRA_TYPES)
			.add(WWEntityTypes.SCORCHED);

		this.valueLookupBuilder(EntityTypeTags.BOAT)
			.add(
				WWEntityTypes.BAOBAB_BOAT,
				WWEntityTypes.BAOBAB_CHEST_BOAT,
				WWEntityTypes.WILLOW_BOAT,
				WWEntityTypes.WILLOW_CHEST_BOAT,
				WWEntityTypes.CYPRESS_BOAT,
				WWEntityTypes.CYPRESS_CHEST_BOAT,
				WWEntityTypes.PALM_BOAT,
				WWEntityTypes.PALM_CHEST_BOAT,
				WWEntityTypes.MAPLE_BOAT,
				WWEntityTypes.MAPLE_CHEST_BOAT
			);

		this.valueLookupBuilder(EntityTypeTags.CAN_EQUIP_SADDLE)
			.add(WWEntityTypes.OSTRICH);
	}
}
