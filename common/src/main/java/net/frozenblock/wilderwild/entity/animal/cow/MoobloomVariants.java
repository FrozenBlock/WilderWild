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

package net.frozenblock.wilderwild.entity.animal.cow;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.registry.WilderWildRegistries;
import net.minecraft.core.ClientAsset;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Util;
import net.minecraft.world.entity.variant.SpawnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.SimpleBlockFeature;

public final class MoobloomVariants {
	public static final ResourceKey<MoobloomVariant> DANDELION = createKey("dandelion");
	public static final ResourceKey<MoobloomVariant> POPPY = createKey("poppy");
	public static final ResourceKey<MoobloomVariant> AZURE_BLUET = createKey("azure_bluet");
	public static final ResourceKey<MoobloomVariant> RED_TULIP = createKey("red_tulip");
	public static final ResourceKey<MoobloomVariant> ORANGE_TULIP = createKey("orange_tulip");
	public static final ResourceKey<MoobloomVariant> WHITE_TULIP = createKey("white_tulip");
	public static final ResourceKey<MoobloomVariant> PINK_TULIP = createKey("pink_tulip");
	public static final ResourceKey<MoobloomVariant> OXEYE_DAISY = createKey("oxeye_daisy");
	public static final ResourceKey<MoobloomVariant> CORNFLOWER = createKey("cornflower");
	public static final ResourceKey<MoobloomVariant> LILY_OF_THE_VALLEY = createKey("lily_of_the_valley");
	public static final ResourceKey<MoobloomVariant> ALLIUM = createKey("allium");
	public static final ResourceKey<MoobloomVariant> BLUE_ORCHID = createKey("blue_orchid");
	public static final ResourceKey<MoobloomVariant> CARNATION = createKey("carnation");
	public static final ResourceKey<MoobloomVariant> MARIGOLD = createKey("marigold");
	public static final ResourceKey<MoobloomVariant> PASQUEFLOWER = createKey("pasqueflower");
	public static final ResourceKey<MoobloomVariant> DEFAULT = DANDELION;

	private static ResourceKey<MoobloomVariant> createKey(String name) {
		return ResourceKey.create(WilderWildRegistries.MOOBLOOM_VARIANT, WWConstants.id(name));
	}

	public static Optional<Holder<MoobloomVariant>> selectVariantToSpawn(RandomSource random, RegistryAccess registryAccess, SpawnContext context) {
		final List<Feature> boneMealFeatures = context.biome().value().getGenerationSettings().getBoneMealFeatures();
		final List<BlockState> foundBlockStates = new ArrayList<>();
		for (Feature feature : boneMealFeatures) {
			if (!(feature instanceof SimpleBlockFeature simpleBlockFeature)) continue;

			final BlockState state = simpleBlockFeature.toPlace().value().getOptionalState(context.level().getLevel(), random, context.pos());
			foundBlockStates.add(state);
		}

		final List<Holder<MoobloomVariant>> variants = registryAccess.lookupOrThrow(WilderWildRegistries.MOOBLOOM_VARIANT)
			.listElements()
			.filter(variantHolder -> foundBlockStates.contains(variantHolder.value().flowerBlockState()))
			.collect(Collectors.toList());
		return Util.getRandomSafe(variants, random);
	}

	public static void bootstrap(BootstrapContext<MoobloomVariant> context) {
		register(context, DANDELION, Blocks.DANDELION, "dandelion");
		register(context, POPPY, Blocks.POPPY, "poppy");
		register(context, AZURE_BLUET, Blocks.AZURE_BLUET, "azure_bluet");
		register(context, RED_TULIP, Blocks.RED_TULIP, "red_tulip");
		register(context, ORANGE_TULIP, Blocks.ORANGE_TULIP, "orange_tulip");
		register(context, WHITE_TULIP, Blocks.WHITE_TULIP, "white_tulip");
		register(context, PINK_TULIP, Blocks.PINK_TULIP, "pink_tulip");
		register(context, OXEYE_DAISY, Blocks.OXEYE_DAISY, "oxeye_daisy");
		register(context, CORNFLOWER, Blocks.CORNFLOWER, "cornflower");
		register(context, LILY_OF_THE_VALLEY, Blocks.LILY_OF_THE_VALLEY, "lily_of_the_valley");
		register(context, ALLIUM, Blocks.ALLIUM, "allium");
		register(context, BLUE_ORCHID, Blocks.BLUE_ORCHID, "blue_orchid");
		register(context, CARNATION, WWBlocks.CARNATION.get(), "carnation");
		register(context, MARIGOLD, WWBlocks.MARIGOLD.get(), "marigold");
		register(context, PASQUEFLOWER, WWBlocks.PASQUEFLOWER.get(), "pasqueflower");
	}

	private static void register(
		BootstrapContext<MoobloomVariant> context,
		ResourceKey<MoobloomVariant> name,
		Block flowerBlock,
		String textureName
	) {
		final String texturePath = "entity/cow/moobloom_" + textureName;
		context.register(
			name,
			new MoobloomVariant(
				new ClientAsset.ResourceTexture(WWConstants.id(texturePath)),
				new ClientAsset.ResourceTexture(WWConstants.id(texturePath + "_baby")),
				flowerBlock.defaultBlockState(),
				Optional.empty()
			)
		);
	}
}
