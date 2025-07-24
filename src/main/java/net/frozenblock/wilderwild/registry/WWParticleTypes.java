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

package net.frozenblock.wilderwild.registry;

import com.mojang.serialization.MapCodec;
import java.util.function.Function;
import java.util.function.Supplier;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.block.impl.FallingLeafUtil;
import net.frozenblock.wilderwild.config.WWAmbienceAndMiscConfig;
import net.frozenblock.wilderwild.particle.options.FloatingSculkBubbleParticleOptions;
import net.frozenblock.wilderwild.particle.options.SeedParticleOptions;
import net.frozenblock.wilderwild.particle.options.WWFallingLeavesParticleOptions;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

public final class WWParticleTypes {
	public static final SimpleParticleType POLLEN = register("pollen");
	public static final ParticleType<SeedParticleOptions> SEED = register(
		"seed", false, particleType -> SeedParticleOptions.CODEC, particleType -> SeedParticleOptions.STREAM_CODEC
	);
	public static final SimpleParticleType PALE_FOG = register("pale_fog");
	public static final SimpleParticleType PALE_FOG_SMALL = register("pale_fog_small");
	public static final SimpleParticleType PALE_SPORE = register("pale_spore");
	public static final ParticleType<FloatingSculkBubbleParticleOptions> FLOATING_SCULK_BUBBLE = register(
		"floating_sculk_bubble", false, particleType -> FloatingSculkBubbleParticleOptions.CODEC, particleType -> FloatingSculkBubbleParticleOptions.STREAM_CODEC
	);
	public static final SimpleParticleType TERMITE = register("termite");
	public static final SimpleParticleType COCONUT_SPLASH = register("coconut_splash");
	public static final SimpleParticleType SCORCHING_FLAME = register("scorching_flame");
	public static final SimpleParticleType UNDERWATER_ASH = register("underwater_ash");
	public static final SimpleParticleType PLANKTON = register("plankton");
	public static final SimpleParticleType GLOWING_PLANKTON = register("glowing_plankton");
	public static final SimpleParticleType LEAF_CLUSTER_SPAWNER = register("leaf_cluster");
	public static final SimpleParticleType CHEST_BUBBLE_SPAWNER = register("chest_bubbles");
	public static final SimpleParticleType SHRIEKER_BUBBLE_SPAWNER = register("shrieker_bubbles");
	public static final SimpleParticleType BLUE_PEARLESCENT_HANGING_MESOGLEA = register("blue_pearlescent_hanging_mesoglea_drip");
	public static final SimpleParticleType BLUE_PEARLESCENT_FALLING_MESOGLEA = register("blue_pearlescent_falling_mesoglea_drip");
	public static final SimpleParticleType BLUE_PEARLESCENT_LANDING_MESOGLEA = register("blue_pearlescent_landing_mesoglea_drip");
	public static final SimpleParticleType BLUE_PEARLESCENT_MESOGLEA_BUBBLE = register("blue_pearlescent_mesoglea_bubble");
	public static final SimpleParticleType BLUE_PEARLESCENT_MESOGLEA_BUBBLE_COLUMN_UP = register("blue_pearlescent_mesoglea_bubble_column_up");
	public static final SimpleParticleType BLUE_PEARLESCENT_MESOGLEA_CURRENT_DOWN = register("blue_pearlescent_mesoglea_current_down");
	public static final SimpleParticleType BLUE_PEARLESCENT_MESOGLEA_BUBBLE_POP = register("blue_pearlescent_mesoglea_bubble_pop");
	public static final SimpleParticleType BLUE_PEARLESCENT_MESOGLEA_SPLASH = register("blue_pearlescent_mesoglea_splash");
	public static final SimpleParticleType PURPLE_PEARLESCENT_HANGING_MESOGLEA = register("purple_pearlescent_hanging_mesoglea_drip");
	public static final SimpleParticleType PURPLE_PEARLESCENT_FALLING_MESOGLEA = register("purple_pearlescent_falling_mesoglea_drip");
	public static final SimpleParticleType PURPLE_PEARLESCENT_LANDING_MESOGLEA = register("purple_pearlescent_landing_mesoglea_drip");
	public static final SimpleParticleType PURPLE_PEARLESCENT_MESOGLEA_BUBBLE = register("purple_pearlescent_mesoglea_bubble");
	public static final SimpleParticleType PURPLE_PEARLESCENT_MESOGLEA_BUBBLE_COLUMN_UP = register("purple_pearlescent_mesoglea_bubble_column_up");
	public static final SimpleParticleType PURPLE_PEARLESCENT_MESOGLEA_CURRENT_DOWN = register("purple_pearlescent_mesoglea_current_down");
	public static final SimpleParticleType PURPLE_PEARLESCENT_MESOGLEA_BUBBLE_POP = register("purple_pearlescent_mesoglea_bubble_pop");
	public static final SimpleParticleType PURPLE_PEARLESCENT_MESOGLEA_SPLASH = register("purple_pearlescent_mesoglea_splash");
	public static final SimpleParticleType PINK_HANGING_MESOGLEA = register("pink_hanging_mesoglea_drip");
	public static final SimpleParticleType PINK_FALLING_MESOGLEA = register("pink_falling_mesoglea_drip");
	public static final SimpleParticleType PINK_LANDING_MESOGLEA = register("pink_landing_mesoglea_drip");
	public static final SimpleParticleType PINK_MESOGLEA_BUBBLE = register("pink_mesoglea_bubble");
	public static final SimpleParticleType PINK_MESOGLEA_BUBBLE_COLUMN_UP = register("pink_mesoglea_bubble_column_up");
	public static final SimpleParticleType PINK_MESOGLEA_CURRENT_DOWN = register("pink_mesoglea_current_down");
	public static final SimpleParticleType PINK_MESOGLEA_BUBBLE_POP = register("pink_mesoglea_bubble_pop");
	public static final SimpleParticleType PINK_MESOGLEA_SPLASH = register("pink_mesoglea_splash");
	public static final SimpleParticleType RED_HANGING_MESOGLEA = register("red_hanging_mesoglea_drip");
	public static final SimpleParticleType RED_FALLING_MESOGLEA = register("red_falling_mesoglea_drip");
	public static final SimpleParticleType RED_LANDING_MESOGLEA = register("red_landing_mesoglea_drip");
	public static final SimpleParticleType RED_MESOGLEA_BUBBLE = register("red_mesoglea_bubble");
	public static final SimpleParticleType RED_MESOGLEA_BUBBLE_COLUMN_UP = register("red_mesoglea_bubble_column_up");
	public static final SimpleParticleType RED_MESOGLEA_CURRENT_DOWN = register("red_mesoglea_current_down");
	public static final SimpleParticleType RED_MESOGLEA_BUBBLE_POP = register("red_mesoglea_bubble_pop");
	public static final SimpleParticleType RED_MESOGLEA_SPLASH = register("red_mesoglea_splash");
	public static final SimpleParticleType YELLOW_HANGING_MESOGLEA = register("yellow_hanging_mesoglea_drip");
	public static final SimpleParticleType YELLOW_FALLING_MESOGLEA = register("yellow_falling_mesoglea_drip");
	public static final SimpleParticleType YELLOW_LANDING_MESOGLEA = register("yellow_landing_mesoglea_drip");
	public static final SimpleParticleType YELLOW_MESOGLEA_BUBBLE = register("yellow_mesoglea_bubble");
	public static final SimpleParticleType YELLOW_MESOGLEA_BUBBLE_COLUMN_UP = register("yellow_mesoglea_bubble_column_up");
	public static final SimpleParticleType YELLOW_MESOGLEA_CURRENT_DOWN = register("yellow_mesoglea_current_down");
	public static final SimpleParticleType YELLOW_MESOGLEA_BUBBLE_POP = register("yellow_mesoglea_bubble_pop");
	public static final SimpleParticleType YELLOW_MESOGLEA_SPLASH = register("yellow_mesoglea_splash");
	public static final SimpleParticleType LIME_HANGING_MESOGLEA = register("lime_hanging_mesoglea_drip");
	public static final SimpleParticleType LIME_FALLING_MESOGLEA = register("lime_falling_mesoglea_drip");
	public static final SimpleParticleType LIME_LANDING_MESOGLEA = register("lime_landing_mesoglea_drip");
	public static final SimpleParticleType LIME_MESOGLEA_BUBBLE = register("lime_mesoglea_bubble");
	public static final SimpleParticleType LIME_MESOGLEA_BUBBLE_COLUMN_UP = register("lime_mesoglea_bubble_column_up");
	public static final SimpleParticleType LIME_MESOGLEA_CURRENT_DOWN = register("lime_mesoglea_current_down");
	public static final SimpleParticleType LIME_MESOGLEA_BUBBLE_POP = register("lime_mesoglea_bubble_pop");
	public static final SimpleParticleType LIME_MESOGLEA_SPLASH = register("lime_mesoglea_splash");
	public static final SimpleParticleType BLUE_HANGING_MESOGLEA = register("blue_hanging_mesoglea_drip");
	public static final SimpleParticleType BLUE_FALLING_MESOGLEA = register("blue_falling_mesoglea_drip");
	public static final SimpleParticleType BLUE_LANDING_MESOGLEA = register("blue_landing_mesoglea_drip");
	public static final SimpleParticleType BLUE_MESOGLEA_BUBBLE = register("blue_mesoglea_bubble");
	public static final SimpleParticleType BLUE_MESOGLEA_BUBBLE_COLUMN_UP = register("blue_mesoglea_bubble_column_up");
	public static final SimpleParticleType BLUE_MESOGLEA_CURRENT_DOWN = register("blue_mesoglea_current_down");
	public static final SimpleParticleType BLUE_MESOGLEA_BUBBLE_POP = register("blue_mesoglea_bubble_pop");
	public static final SimpleParticleType BLUE_MESOGLEA_SPLASH = register("blue_mesoglea_splash");

	public static final ParticleType<WWFallingLeavesParticleOptions> OAK_LEAVES = createLeafParticle(
		WWConstants.id("oak_leaves"),
		Blocks.OAK_LEAVES,
		0.0095F,
		() -> WWAmbienceAndMiscConfig.Client.OAK_LEAF_FREQUENCY,
		5,
		1.4F,
		10F,
		FallingLeafUtil.LeafMovementType.SWIRL
	);
	public static final ParticleType<WWFallingLeavesParticleOptions> SPRUCE_LEAVES = createLeafParticle(
		WWConstants.id("spruce_leaves"),
		Blocks.SPRUCE_LEAVES,
		0.0075F,
		() -> WWAmbienceAndMiscConfig.Client.SPRUCE_LEAF_FREQUENCY,
		5,
		2F,
		5F,
		FallingLeafUtil.LeafMovementType.SWIRL
	);
	public static final ParticleType<WWFallingLeavesParticleOptions> BIRCH_LEAVES = createLeafParticle(
		WWConstants.id("birch_leaves"),
		Blocks.BIRCH_LEAVES,
		0.0095F,
		() -> WWAmbienceAndMiscConfig.Client.BIRCH_LEAF_FREQUENCY,
		4,
		1F,
		10F,
		FallingLeafUtil.LeafMovementType.SWIRL
	);
	public static final ParticleType<WWFallingLeavesParticleOptions> JUNGLE_LEAVES = createLeafParticle(
		WWConstants.id("jungle_leaves"),
		Blocks.JUNGLE_LEAVES,
		0.0095F,
		() -> WWAmbienceAndMiscConfig.Client.JUNGLE_LEAF_FREQUENCY,
		4,
		1.4F,
		10F,
		FallingLeafUtil.LeafMovementType.SWIRL
	);
	public static final ParticleType<WWFallingLeavesParticleOptions> ACACIA_LEAVES = createLeafParticle(
		WWConstants.id("acacia_leaves"),
		Blocks.ACACIA_LEAVES,
		0.0125F,
		() -> WWAmbienceAndMiscConfig.Client.ACACIA_LEAF_FREQUENCY,
		3,
		1.4F,
		10F,
		FallingLeafUtil.LeafMovementType.SWIRL
	);
	public static final ParticleType<WWFallingLeavesParticleOptions> DARK_OAK_LEAVES = createLeafParticle(
		WWConstants.id("dark_oak_leaves"),
		Blocks.DARK_OAK_LEAVES,
		0.0095F,
		() -> WWAmbienceAndMiscConfig.Client.DARK_OAK_LEAF_FREQUENCY,
		5,
		1.4F,
		10F,
		FallingLeafUtil.LeafMovementType.SWIRL
	);
	public static final ParticleType<WWFallingLeavesParticleOptions> PALE_OAK_LEAVES = createLeafParticle(
		WWConstants.id("pale_oak_leaves"),
		Blocks.PALE_OAK_LEAVES,
		0.0045F,
		() -> WWAmbienceAndMiscConfig.Client.PALE_OAK_LEAF_FREQUENCY,
		5,
		0.28F,
		20F,
		FallingLeafUtil.LeafMovementType.SWIRL
	);
	public static final ParticleType<WWFallingLeavesParticleOptions> MANGROVE_LEAVES = createLeafParticle(
		WWConstants.id("mangrove_leaves"),
		Blocks.MANGROVE_LEAVES,
		0.0095F,
		() -> WWAmbienceAndMiscConfig.Client.MANGROVE_LEAF_FREQUENCY,
		6,
		2.5F,
		10F,
		FallingLeafUtil.LeafMovementType.SWIRL
	);
	public static final ParticleType<WWFallingLeavesParticleOptions> CHERRY_LEAVES = createLeafParticle(
		WWConstants.id("cherry_leaves"),
		Blocks.CHERRY_LEAVES,
		0.0125F,
		() -> WWAmbienceAndMiscConfig.Client.CHERRY_LEAF_FREQUENCY,
		4,
		1F,
		2F,
		FallingLeafUtil.LeafMovementType.FLOW_AWAY
	);
	public static final ParticleType<WWFallingLeavesParticleOptions> AZALEA_LEAVES = createLeafParticle(
		WWConstants.id("azalea_leaves"),
		Blocks.AZALEA_LEAVES,
		0.0095F,
		() -> WWAmbienceAndMiscConfig.Client.AZALEA_LEAF_FREQUENCY,
		4,
		2F,
		10F,
		FallingLeafUtil.LeafMovementType.SWIRL
	);
	public static final ParticleType<WWFallingLeavesParticleOptions> FLOWERING_AZALEA_LEAVES = createLeafParticle(
		WWConstants.id("flowering_azalea_leaves"),
		Blocks.FLOWERING_AZALEA_LEAVES,
		0.0095F,
		() -> WWAmbienceAndMiscConfig.Client.FLOWERING_AZALEA_LEAF_FREQUENCY,
		4,
		2F,
		10F,
		FallingLeafUtil.LeafMovementType.SWIRL
	);
	public static final ParticleType<WWFallingLeavesParticleOptions> BAOBAB_LEAVES = createLeafParticle(
		WWConstants.id("baobab_leaves"),
		WWBlocks.BAOBAB_LEAVES,
		0.0095F,
		() -> WWAmbienceAndMiscConfig.Client.BAOBAB_LEAF_FREQUENCY,
		4,
		2F,
		15F,
		FallingLeafUtil.LeafMovementType.SWIRL
	);
	public static final ParticleType<WWFallingLeavesParticleOptions> CYPRESS_LEAVES = createLeafParticle(
		WWConstants.id("cypress_leaves"),
		WWBlocks.CYPRESS_LEAVES,
		0.0075F,
		() -> WWAmbienceAndMiscConfig.Client.CYPRESS_LEAF_FREQUENCY,
		4,
		2F,
		5F,
		FallingLeafUtil.LeafMovementType.SWIRL
	);
	public static final ParticleType<WWFallingLeavesParticleOptions> PALM_FRONDS = createLeafParticle(
		WWConstants.id("palm_fronds"),
		WWBlocks.PALM_FRONDS,
		0.00055F,
		() -> WWAmbienceAndMiscConfig.Client.PALM_FROND_FREQUENCY,
		6,
		4.5F,
		5F,
		FallingLeafUtil.LeafMovementType.SWIRL
	);

	public static final ParticleType<WWFallingLeavesParticleOptions> YELLOW_MAPLE_LEAVES = createLeafParticle(WWConstants.id("yellow_maple_leaves"));
	public static final ParticleType<WWFallingLeavesParticleOptions> ORANGE_MAPLE_LEAVES = createLeafParticle(WWConstants.id("orange_maple_leaves"));
	public static final ParticleType<WWFallingLeavesParticleOptions> RED_MAPLE_LEAVES = createLeafParticle(WWConstants.id("red_maple_leaves"));

	public static final ParticleType<WWFallingLeavesParticleOptions> WILLOW_LEAVES = createLeafParticle(
		WWConstants.id("willow_leaves"),
		WWBlocks.WILLOW_LEAVES,
		0.0045F,
		() -> WWAmbienceAndMiscConfig.Client.WILLOW_LEAF_FREQUENCY,
		5,
		1.4F,
		10F,
		FallingLeafUtil.LeafMovementType.SWIRL
	);

	private WWParticleTypes() {
		throw new UnsupportedOperationException("WWParticleTypes contains only static declarations.");
	}

	public static void registerParticles() {
		WWConstants.logWithModId("Registering Particles for", WWConstants.UNSTABLE_LOGGING);
	}

	private static @NotNull ParticleType<WWFallingLeavesParticleOptions> createLeafParticle(ResourceLocation location) {
		return register(
			location, false, particleType -> WWFallingLeavesParticleOptions.CODEC, particleType -> WWFallingLeavesParticleOptions.STREAM_CODEC
		);
	}

	private static @NotNull ParticleType<WWFallingLeavesParticleOptions> createLeafParticle(
		ResourceLocation location,
		Block sourceBlock,
		float particleChance,
		Supplier<Double> frequencyModifier,
		int textureSize,
		float particleGravityScale,
		float windScale,
		FallingLeafUtil.LeafMovementType leafMovementType
	) {
		ParticleType<WWFallingLeavesParticleOptions> leafParticle = createLeafParticle(location);
		FallingLeafUtil.registerLeaves(
			sourceBlock,
			leafParticle,
			particleChance,
			frequencyModifier,
			textureSize,
			particleGravityScale,
			windScale,
			leafMovementType
		);
		return leafParticle;
	}

	@NotNull
	private static SimpleParticleType register(@NotNull String name, boolean alwaysShow) {
		return Registry.register(BuiltInRegistries.PARTICLE_TYPE, WWConstants.id(name), FabricParticleTypes.simple(alwaysShow));
	}

	@NotNull
	private static SimpleParticleType register(@NotNull String name) {
		return register(name, false);
	}

	@NotNull
	private static <T extends ParticleOptions> ParticleType<T> register(
		String string,
		boolean alwaysShow,
		Function<ParticleType<T>, MapCodec<T>> function,
		Function<ParticleType<T>, StreamCodec<? super RegistryFriendlyByteBuf, T>> function2
	) {
		return register(WWConstants.id(string), alwaysShow, function, function2);
	}

	@NotNull
	private static <T extends ParticleOptions> ParticleType<T> register(
		ResourceLocation resourceLocation,
		boolean alwaysShow,
		Function<ParticleType<T>, MapCodec<T>> function,
		Function<ParticleType<T>, StreamCodec<? super RegistryFriendlyByteBuf, T>> function2
	) {
		return Registry.register(BuiltInRegistries.PARTICLE_TYPE, resourceLocation, new ParticleType<T>(alwaysShow) {
			@Override
			public @NotNull MapCodec<T> codec() {
				return function.apply(this);
			}

			@NotNull
			@Override
			public StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec() {
				return function2.apply(this);
			}
		});
	}
}
