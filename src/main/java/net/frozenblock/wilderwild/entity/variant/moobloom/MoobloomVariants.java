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

import java.util.Optional;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.registry.WilderWildRegistries;
import net.frozenblock.wilderwild.tag.WWBiomeTags;
import net.minecraft.core.ClientAsset;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.variant.BiomeCheck;
import net.minecraft.world.entity.variant.PriorityProvider;
import net.minecraft.world.entity.variant.SpawnContext;
import net.minecraft.world.entity.variant.SpawnPrioritySelectors;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

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
		String name,
		TagKey<Biome> biomeTag
	) {
		String texturePath = "entity/moobloom/moobloom_" + name;
		HolderSet<Biome> holderSet = bootstrapContext.lookup(Registries.BIOME).getOrThrow(biomeTag);
		bootstrapContext.register(
			resourceKey,
			new MoobloomVariant(
				new ClientAsset(WWConstants.id(texturePath)),
				flowerBlock.defaultBlockState(),
				SpawnPrioritySelectors.single(new BiomeCheck(holderSet), 1)
			)
		);
	}

	public static @NotNull Optional<Holder.Reference<MoobloomVariant>> selectVariantToSpawn(
		RandomSource randomSource,
		@NotNull RegistryAccess registryAccess,
		SpawnContext spawnContext
	) {
		return PriorityProvider.pick(registryAccess.lookupOrThrow(WilderWildRegistries.MOOBLOOM_VARIANT).listElements(), Holder::value, randomSource, spawnContext);
	}

	public static void bootstrap(BootstrapContext<MoobloomVariant> bootstrapContext) {
		register(bootstrapContext, DANDELION, Blocks.DANDELION, "dandelion", WWBiomeTags.MOOBLOOM_DANDELION);
		register(bootstrapContext, POPPY, Blocks.POPPY, "poppy", WWBiomeTags.MOOBLOOM_POPPY);
		register(bootstrapContext, AZURE_BLUET, Blocks.AZURE_BLUET, "azure_bluet", WWBiomeTags.MOOBLOOM_AZURE_BLUET);
		register(bootstrapContext, RED_TULIP, Blocks.RED_TULIP, "red_tulip", WWBiomeTags.MOOBLOOM_RED_TULIP);
		register(bootstrapContext, ORANGE_TULIP, Blocks.ORANGE_TULIP, "orange_tulip", WWBiomeTags.MOOBLOOM_ORANGE_TULIP);
		register(bootstrapContext, WHITE_TULIP, Blocks.WHITE_TULIP, "white_tulip", WWBiomeTags.MOOBLOOM_WHITE_TULIP);
		register(bootstrapContext, PINK_TULIP, Blocks.PINK_TULIP, "pink_tulip", WWBiomeTags.MOOBLOOM_PINK_TULIP);
		register(bootstrapContext, OXEYE_DAISY, Blocks.OXEYE_DAISY, "oxeye_daisy", WWBiomeTags.MOOBLOOM_OXEYE_DAISY);
		register(bootstrapContext, CORNFLOWER, Blocks.CORNFLOWER, "cornflower", WWBiomeTags.MOOBLOOM_CORNFLOWER);
		register(bootstrapContext, LILY_OF_THE_VALLEY, Blocks.LILY_OF_THE_VALLEY, "lily_of_the_valley", WWBiomeTags.MOOBLOOM_LILY_OF_THE_VALLEY);
		register(bootstrapContext, ALLIUM, Blocks.ALLIUM, "allium", WWBiomeTags.MOOBLOOM_ALLIUM);
		register(bootstrapContext, BLUE_ORCHID, Blocks.BLUE_ORCHID, "blue_orchid", WWBiomeTags.MOOBLOOM_BLUE_ORCHID);
		register(bootstrapContext, CARNATION, WWBlocks.CARNATION, "carnation", WWBiomeTags.MOOBLOOM_CARNATION);
		register(bootstrapContext, MARIGOLD, WWBlocks.MARIGOLD, "marigold", WWBiomeTags.MOOBLOOM_MARIGOLD);
		register(bootstrapContext, PASQUEFLOWER, WWBlocks.PASQUEFLOWER, "pasqueflower", WWBiomeTags.MOOBLOOM_PASQUEFLOWER);
	}
}
