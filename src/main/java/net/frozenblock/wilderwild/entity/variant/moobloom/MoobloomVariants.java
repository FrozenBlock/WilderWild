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

package net.frozenblock.wilderwild.entity.variant.moobloom;

import java.util.ArrayList;
import java.util.List;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.registry.WilderWildRegistries;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import org.jetbrains.annotations.NotNull;
import net.minecraft.Util;

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

	private static @NotNull ResourceKey<MoobloomVariant> createKey(String string) {
		return ResourceKey.create(WilderWildRegistries.MOOBLOOM_VARIANT, WWConstants.id(string));
	}

	private static void register(
		@NotNull BootstrapContext<MoobloomVariant> bootstrapContext,
		ResourceKey<MoobloomVariant> resourceKey,
		@NotNull Block flowerBlock,
		String textureName
	) {
		ResourceLocation textureLocation = WWConstants.id("entity/moobloom/" + textureName);
		bootstrapContext.register(
			resourceKey,
			new MoobloomVariant(
				flowerBlock.defaultBlockState(),
				textureLocation
			)
		);
	}

	public static Holder<MoobloomVariant> getSpawnVariant(@NotNull RegistryAccess registryAccess, Holder<Biome> holder, BlockPos pos, RandomSource random) {
		Registry<MoobloomVariant> registry = registryAccess.lookupOrThrow(WilderWildRegistries.MOOBLOOM_VARIANT);

		List<ConfiguredFeature<?, ?>> flowerFeatures = holder.value().getGenerationSettings().getFlowerFeatures();
		List<BlockState> biomeFlowerStates = new ArrayList<>();
		for (ConfiguredFeature<?, ?> feature : flowerFeatures) {
			if (feature.config() instanceof RandomPatchConfiguration randomPatchConfiguration) {
				ConfiguredFeature<?, ?> configuredPlacedFeature = randomPatchConfiguration.feature().value().feature().value();
				biomeFlowerStates.addAll(getBlockStatesFromConfiguredFeature(configuredPlacedFeature, pos, random));
			}
		}

		List<Holder.Reference<MoobloomVariant>> variants = registry.listElements()
			.filter(reference -> biomeFlowerStates.contains(reference.value().getFlowerBlockState()))
			.toList();

		if (!variants.isEmpty()) return Util.getRandom(variants, random);
		return registry.getRandom(random).orElseThrow();
	}

	private static @NotNull List<BlockState> getBlockStatesFromConfiguredFeature(@NotNull ConfiguredFeature<?, ?> feature, BlockPos pos, RandomSource random) {
		List<BlockState> blockStates = new ArrayList<>();

		FeatureConfiguration config = feature.config();
		if (config instanceof RandomPatchConfiguration randomPatchConfiguration) {
			ConfiguredFeature<?, ?> configuredPlacedFeature = randomPatchConfiguration.feature().value().feature().value();
			blockStates.addAll(getBlockStatesFromConfiguredFeature(configuredPlacedFeature, pos, random));
		} else if (config instanceof SimpleBlockConfiguration simpleBlockConfiguration) {
			blockStates.add(simpleBlockConfiguration.toPlace().getState(random, pos));
		}

		for (ConfiguredFeature<?, ?> nestedFeature : config.getFeatures().toList()) {
			blockStates.addAll(getBlockStatesFromConfiguredFeature(nestedFeature, pos, random));
		}

		return blockStates;
	}

	public static void bootstrap(BootstrapContext<MoobloomVariant> bootstrapContext) {
		register(bootstrapContext, DANDELION, Blocks.DANDELION, "moobloom_dandelion");
		register(bootstrapContext, POPPY, Blocks.POPPY, "moobloom_poppy");
		register(bootstrapContext, AZURE_BLUET, Blocks.AZURE_BLUET, "moobloom_azure_bluet");
		register(bootstrapContext, RED_TULIP, Blocks.RED_TULIP, "moobloom_red_tulip");
		register(bootstrapContext, ORANGE_TULIP, Blocks.ORANGE_TULIP, "moobloom_orange_tulip");
		register(bootstrapContext, WHITE_TULIP, Blocks.WHITE_TULIP, "moobloom_white_tulip");
		register(bootstrapContext, PINK_TULIP, Blocks.PINK_TULIP, "moobloom_pink_tulip");
		register(bootstrapContext, OXEYE_DAISY, Blocks.OXEYE_DAISY, "moobloom_oxeye_daisy");
		register(bootstrapContext, CORNFLOWER, Blocks.CORNFLOWER, "moobloom_cornflower");
		register(bootstrapContext, LILY_OF_THE_VALLEY, Blocks.LILY_OF_THE_VALLEY, "moobloom_lily_of_the_valley");
		register(bootstrapContext, ALLIUM, Blocks.ALLIUM, "moobloom_allium");
		register(bootstrapContext, BLUE_ORCHID, Blocks.BLUE_ORCHID, "moobloom_blue_orchid");
		register(bootstrapContext, CARNATION, WWBlocks.CARNATION, "moobloom_carnation");
		register(bootstrapContext, MARIGOLD, WWBlocks.MARIGOLD, "moobloom_marigold");
		register(bootstrapContext, PASQUEFLOWER, WWBlocks.PASQUEFLOWER, "moobloom_pasqueflower");
	}
}
