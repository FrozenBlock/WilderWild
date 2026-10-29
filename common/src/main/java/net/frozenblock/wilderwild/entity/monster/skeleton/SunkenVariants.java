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
import net.frozenblock.wilderwild.registry.WWLootTables;
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
import net.minecraft.world.level.storage.loot.LootTable;

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
		registerCoral(
			context,
			BRAIN_CORAL,
			"brain_coral",
			sunkenVariants.getOrThrow(DEAD_BRAIN_CORAL),
			defaultVariant,
			WWLootTables.SHEAR_SUNKEN_BRAIN_CORAL,
			WWBiomeTags.CORAL_VARIANT_SUNKEN
		);
		registerCoral(
			context,
			BUBBLE_CORAL,
			"bubble_coral",
			sunkenVariants.getOrThrow(DEAD_BUBBLE_CORAL),
			defaultVariant,
			WWLootTables.SHEAR_SUNKEN_BUBBLE_CORAL,
			WWBiomeTags.CORAL_VARIANT_SUNKEN
		);
		registerCoral(
			context,
			FIRE_CORAL,
			"fire_coral",
			sunkenVariants.getOrThrow(DEAD_FIRE_CORAL),
			defaultVariant,
			WWLootTables.SHEAR_SUNKEN_FIRE_CORAL,
			WWBiomeTags.CORAL_VARIANT_SUNKEN
		);
		registerCoral(
			context,
			HORN_CORAL,
			"horn_coral",
			sunkenVariants.getOrThrow(DEAD_HORN_CORAL),
			defaultVariant,
			WWLootTables.SHEAR_SUNKEN_HORN_CORAL,
			WWBiomeTags.CORAL_VARIANT_SUNKEN
		);
		registerCoral(
			context,
			TUBE_CORAL,
			"tube_coral",
			sunkenVariants.getOrThrow(DEAD_TUBE_CORAL),
			defaultVariant,
			WWLootTables.SHEAR_SUNKEN_TUBE_CORAL,
			WWBiomeTags.CORAL_VARIANT_SUNKEN
		);
		// DEAD CORAL
		registerDeadCoral(context, DEAD_BRAIN_CORAL, "dead_brain_coral", defaultVariant, WWLootTables.SHEAR_SUNKEN_DEAD_BRAIN_CORAL);
		registerDeadCoral(context, DEAD_BUBBLE_CORAL, "dead_bubble_coral", defaultVariant, WWLootTables.SHEAR_SUNKEN_DEAD_BUBBLE_CORAL);
		registerDeadCoral(context, DEAD_FIRE_CORAL, "dead_fire_coral", defaultVariant, WWLootTables.SHEAR_SUNKEN_DEAD_FIRE_CORAL);
		registerDeadCoral(context, DEAD_HORN_CORAL, "dead_horn_coral", defaultVariant, WWLootTables.SHEAR_SUNKEN_DEAD_HORN_CORAL);
		registerDeadCoral(context, DEAD_TUBE_CORAL, "dead_tube_coral", defaultVariant, WWLootTables.SHEAR_SUNKEN_DEAD_TUBE_CORAL);
	}

	private static void register(
		BootstrapContext<SunkenVariant> context,
		ResourceKey<SunkenVariant> name,
		SunkenVariant.ModelType model,
		String textureName,
		Optional<SunkenVariant.ConversionResult> dryResult,
		Optional<SunkenVariant.ConversionResult> shearResult,
		SpawnPrioritySelectors spawnConditions
	) {
		final String texturePath = "entity/skeleton/sunken" + (StringUtil.isNullOrEmpty(textureName) ? "" : "_" + textureName);
		final String eyePatchLeftHandedTexturePath = texturePath + "_eye_patch_left_handed";
		final String eyePatchRightHandedTexturePath = texturePath + "_eye_patch_right_handed";
		context.register(
			name,
			new SunkenVariant(
				model,
				new ClientAsset.ResourceTexture(WWConstants.id(texturePath)),
				new ClientAsset.ResourceTexture(WWConstants.id(eyePatchLeftHandedTexturePath)),
				new ClientAsset.ResourceTexture(WWConstants.id(eyePatchRightHandedTexturePath)),
				dryResult,
				shearResult,
				spawnConditions
			)
		);
	}

	private static void registerCoral(
		BootstrapContext<SunkenVariant> context,
		ResourceKey<SunkenVariant> name,
		String textureName,
		Holder<SunkenVariant> driedVariant,
		Holder<SunkenVariant> shearedVariant,
		ResourceKey<LootTable> shearLootTable,
		TagKey<Biome> spawnBiome
	) {
		final HolderSet<Biome> biomes = context.lookup(Registries.BIOME).getOrThrow(spawnBiome);
		register(
			context,
			name,
			SunkenVariant.ModelType.CORAL,
			textureName,
			Optional.of(new SunkenVariant.ConversionResult(driedVariant, Optional.empty())),
			Optional.of(new SunkenVariant.ConversionResult(shearedVariant, Optional.of(shearLootTable))),
			SpawnPrioritySelectors.single(new BiomeCheck(biomes), 1)
		);
	}

	private static void registerDeadCoral(
		BootstrapContext<SunkenVariant> context,
		ResourceKey<SunkenVariant> name,
		String textureName,
		Holder<SunkenVariant> shearedVariant,
		ResourceKey<LootTable> shearLootTable
	) {
		register(
			context,
			name,
			SunkenVariant.ModelType.CORAL,
			textureName,
			Optional.empty(),
			Optional.of(new SunkenVariant.ConversionResult(shearedVariant, Optional.of(shearLootTable))),
			SpawnPrioritySelectors.EMPTY
		);
	}

	private static void register(
		BootstrapContext<SunkenVariant> context,
		ResourceKey<SunkenVariant> name,
		String textureName
	) {
		register(
			context,
			name,
			SunkenVariant.ModelType.NORMAL,
			textureName,
			Optional.empty(),
			Optional.empty(),
			SpawnPrioritySelectors.fallback(0)
		);
	}
}
