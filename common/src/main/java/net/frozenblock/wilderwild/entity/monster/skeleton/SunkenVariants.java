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

package net.frozenblock.wilderwild.entity.monster.skeleton;

import java.util.Optional;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.registry.WilderWildRegistries;
import net.frozenblock.wilderwild.tag.WWBiomeTags;
import net.minecraft.core.ClientAsset;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.util.StringUtil;
import net.minecraft.world.entity.variant.BiomeCheck;
import net.minecraft.world.entity.variant.SpawnPrioritySelectors;
import net.minecraft.world.level.biome.Biome;

public final class SunkenVariants {
	public static final ResourceKey<SunkenVariant> DEFAULT = createKey("default");
	// CORAL
	public static final ResourceKey<SunkenVariant> BRAIN_CORAL = createKey("brain_coral");
	public static final ResourceKey<SunkenVariant> BUBBLE_CORAL = createKey("bubble_coral");
	public static final ResourceKey<SunkenVariant> FIRE_CORAL = createKey("fire_coral");
	public static final ResourceKey<SunkenVariant> HORN_CORAL = createKey("horn_coral");
	public static final ResourceKey<SunkenVariant> TUBE_CORAL = createKey("tube_coral");
	// DEAD CORAL
	public static final ResourceKey<SunkenVariant> DEAD_BRAIN_CORAL = createKey("dead_brain_coral");
	public static final ResourceKey<SunkenVariant> DEAD_BUBBLE_CORAL = createKey("dead_bubble_coral");
	public static final ResourceKey<SunkenVariant> DEAD_FIRE_CORAL = createKey("dead_fire_coral");
	public static final ResourceKey<SunkenVariant> DEAD_HORN_CORAL = createKey("dead_horn_coral");
	public static final ResourceKey<SunkenVariant> DEAD_TUBE_CORAL = createKey("dead_tube_coral");

	private static ResourceKey<SunkenVariant> createKey(String path) {
		return ResourceKey.create(WilderWildRegistries.SUNKEN_VARIANT, WWConstants.id(path));
	}

	public static void bootstrap(BootstrapContext<SunkenVariant> context) {
		final HolderGetter<SunkenVariant> sunkenVariants = context.lookup(WilderWildRegistries.SUNKEN_VARIANT);
		final Holder<SunkenVariant> defaultVariant = sunkenVariants.getOrThrow(DEFAULT);

		register(context, DEFAULT, "");
		// CORAL
		register(context, BRAIN_CORAL, "brain_coral", sunkenVariants.getOrThrow(DEAD_BRAIN_CORAL), defaultVariant, WWBiomeTags.CORAL_VARIANT_SUNKEN);
		register(context, BUBBLE_CORAL, "bubble_coral", sunkenVariants.getOrThrow(DEAD_BUBBLE_CORAL), defaultVariant, WWBiomeTags.CORAL_VARIANT_SUNKEN);
		register(context, FIRE_CORAL, "fire_coral", sunkenVariants.getOrThrow(DEAD_FIRE_CORAL), defaultVariant, WWBiomeTags.CORAL_VARIANT_SUNKEN);
		register(context, HORN_CORAL, "horn_coral", sunkenVariants.getOrThrow(DEAD_HORN_CORAL), defaultVariant, WWBiomeTags.CORAL_VARIANT_SUNKEN);
		register(context, TUBE_CORAL, "tube_coral", sunkenVariants.getOrThrow(DEAD_TUBE_CORAL), defaultVariant, WWBiomeTags.CORAL_VARIANT_SUNKEN);
		// DEAD CORAL
		register(context, DEAD_BRAIN_CORAL, "dead_brain_coral", defaultVariant);
		register(context, DEAD_BUBBLE_CORAL, "dead_bubble_coral", defaultVariant);
		register(context, DEAD_FIRE_CORAL, "dead_fire_coral", defaultVariant);
		register(context, DEAD_HORN_CORAL, "dead_horn_coral", defaultVariant);
		register(context, DEAD_TUBE_CORAL, "dead_tube_coral", defaultVariant);

	}

	private static void register(
		BootstrapContext<SunkenVariant> context,
		ResourceKey<SunkenVariant> name,
		String textureName,
		Optional<Holder<SunkenVariant>> driedVariant,
		Optional<Holder<SunkenVariant>> shearedVariant,
		SpawnPrioritySelectors spawnConditions
	) {
		final String texturePath = "entity/skeleton/sunken" + (StringUtil.isNullOrEmpty(textureName) ? "" : "_" + textureName);
		context.register(
			name,
			new SunkenVariant(
				new ClientAsset.ResourceTexture(WWConstants.id(texturePath)),
				driedVariant,
				shearedVariant,
				spawnConditions
			)
		);
	}

	private static void register(
		BootstrapContext<SunkenVariant> context,
		ResourceKey<SunkenVariant> name,
		String textureName,
		Holder<SunkenVariant> driedVariant,
		Holder<SunkenVariant> shearedVariant,
		TagKey<Biome> spawnBiome
	) {
		final HolderSet<Biome> biomes = context.lookup(Registries.BIOME).getOrThrow(spawnBiome);
		register(context, name, textureName, Optional.of(driedVariant), Optional.of(shearedVariant), SpawnPrioritySelectors.single(new BiomeCheck(biomes), 1));
	}

	private static void register(
		BootstrapContext<SunkenVariant> context,
		ResourceKey<SunkenVariant> name,
		String textureName,
		Holder<SunkenVariant> shearedVariant
	) {
		register(context, name, textureName, Optional.empty(), Optional.of(shearedVariant), SpawnPrioritySelectors.EMPTY);
	}

	private static void register(
		BootstrapContext<SunkenVariant> context,
		ResourceKey<SunkenVariant> name,
		String textureName
	) {
		register(context, name, textureName, Optional.empty(), Optional.empty(), SpawnPrioritySelectors.fallback(0));
	}
}
