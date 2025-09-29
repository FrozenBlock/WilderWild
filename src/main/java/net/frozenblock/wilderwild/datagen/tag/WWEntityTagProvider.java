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
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.EntityType;
import org.jetbrains.annotations.NotNull;

public final class WWEntityTagProvider extends FabricTagProvider.EntityTypeTagProvider {

	public WWEntityTagProvider(@NotNull FabricDataOutput output, @NotNull CompletableFuture<HolderLookup.Provider> registries) {
		super(output, registries);
	}

	@Override
	protected void addTags(@NotNull HolderLookup.Provider arg) {
		this.getOrCreateTagBuilder(FrozenEntityTags.CREEPER_IGNORES)
			.add(WWEntityTypes.JELLYFISH);

		this.getOrCreateTagBuilder(FrozenEntityTags.WARDEN_CANNOT_TARGET)
			.add(WWEntityTypes.TUMBLEWEED);

		this.getOrCreateTagBuilder(WWEntityTags.CAN_SWIM_IN_ALGAE)
			.add(EntityType.SLIME)
			.add(EntityType.WARDEN)
			.add(EntityType.WITHER)
			.addOptionalTag(EntityTypeTags.AQUATIC);

		this.getOrCreateTagBuilder(WWEntityTags.STAYS_IN_MESOGLEA)
			.add(WWEntityTypes.JELLYFISH);

		this.getOrCreateTagBuilder(WWEntityTags.JELLYFISH_CANT_STING)
			.add(EntityType.SQUID, EntityType.GLOW_SQUID)
			.add(EntityType.DOLPHIN)
			.add(EntityType.TROPICAL_FISH)
			.add(EntityType.PUFFERFISH)
			.add(EntityType.AXOLOTL)
			.add(EntityType.TURTLE)
			.add(WWEntityTypes.JELLYFISH)
			.add(WWEntityTypes.TUMBLEWEED);

		this.getOrCreateTagBuilder(WWEntityTags.COCONUT_CANT_BONK)
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

		this.getOrCreateTagBuilder(WWEntityTags.COCONUT_CANT_SPLIT)
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

		this.getOrCreateTagBuilder(WWEntityTags.TUMBLEWEED_PASSES_THROUGH)
			.add(EntityType.BREEZE);

		this.getOrCreateTagBuilder(EntityTypeTags.CAN_BREATHE_UNDER_WATER)
			.add(EntityType.WARDEN)
			.add(WWEntityTypes.CRAB)
			.add(WWEntityTypes.JELLYFISH)
			.add(WWEntityTypes.PENGUIN);

		this.getOrCreateTagBuilder(EntityTypeTags.NOT_SCARY_FOR_PUFFERFISH)
			.add(WWEntityTypes.JELLYFISH);

		this.getOrCreateTagBuilder(WWEntityTags.CRAB_HUNT_TARGETS)
			.add(EntityType.SQUID, EntityType.GLOW_SQUID)
			.add(EntityType.COD)
			.add(EntityType.SALMON)
			.add(EntityType.TROPICAL_FISH)
			.add(EntityType.TADPOLE);

		this.getOrCreateTagBuilder(WWEntityTags.PENGUIN_HUNT_TARGETS)
			.add(EntityType.SQUID, EntityType.GLOW_SQUID);

		this.getOrCreateTagBuilder(WWEntityTags.LEAF_PARTICLES_FRANTIC_SPAWN)
			.add(EntityType.BREEZE);

		this.getOrCreateTagBuilder(WWEntityTags.GEYSER_PUSHES_FURTHER)
			.add(EntityType.ARROW, EntityType.SPECTRAL_ARROW);

		this.getOrCreateTagBuilder(WWEntityTags.GEYSER_CANNOT_PUSH)
			.add(EntityType.WITHER, EntityType.ENDER_DRAGON)
			.add(EntityType.VEX)
			.addOptional(ResourceLocation.fromNamespaceAndPath("trailiertales", "apparition"));

		this.getOrCreateTagBuilder(WWEntityTags.FRAGILE_ICE_UNWALKABLE_MOBS)
			.add(EntityType.PLAYER)
			.add(EntityType.IRON_GOLEM)
			.add(EntityType.RAVAGER)
			.add(EntityType.SNIFFER)
			.add(EntityType.HOGLIN)
			.add(EntityType.ZOGLIN)
			.add(EntityType.BLAZE)
			.add(EntityType.MAGMA_CUBE)
			.add(EntityType.GIANT);

		this.getOrCreateTagBuilder(WWEntityTags.FRAGILE_ICE_DOESNT_CRACK_ON_FALL)
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

		this.getOrCreateTagBuilder(WWEntityTags.FRAGILE_ICE_DOESNT_CRACK_PROJECTILE)
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

		this.getOrCreateTagBuilder(EntityTypeTags.FREEZE_IMMUNE_ENTITY_TYPES)
			.add(WWEntityTypes.PENGUIN);

		this.getOrCreateTagBuilder(EntityTypeTags.FROG_FOOD)
			.add(WWEntityTypes.BUTTERFLY);

		this.getOrCreateTagBuilder(EntityTypeTags.DISMOUNTS_UNDERWATER)
			.add(WWEntityTypes.OSTRICH)
			.add(WWEntityTypes.SCORCHED);

		this.getOrCreateTagBuilder(EntityTypeTags.ARTHROPOD)
			.add(WWEntityTypes.CRAB)
			.add(WWEntityTypes.SCORCHED);

		this.getOrCreateTagBuilder(EntityTypeTags.AQUATIC)
			.add(WWEntityTypes.CRAB)
			.add(WWEntityTypes.JELLYFISH);

		this.getOrCreateTagBuilder(EntityTypeTags.FREEZE_HURTS_EXTRA_TYPES)
			.add(WWEntityTypes.SCORCHED);

		this.getOrCreateTagBuilder(EntityTypeTags.BOAT)
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

		this.getOrCreateTagBuilder(EntityTypeTags.CAN_EQUIP_SADDLE)
			.add(WWEntityTypes.OSTRICH);
	}
}
