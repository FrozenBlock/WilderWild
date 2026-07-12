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

package net.frozenblock.wilderwild.registry;

import com.mojang.serialization.MapCodec;
import java.util.function.Consumer;
import java.util.function.Function;
import net.frozenblock.lib.particle.api.VibrationParticleVisibilityApi;
import net.frozenblock.lib.platform.api.registry.FrozenDeferredRegister;
import net.frozenblock.lib.platform.api.registry.FrozenHolder;
import net.frozenblock.lib.platform.api.registry.ParticleTypeHelper;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.block.entity.IcicleBlockEntity;
import net.frozenblock.wilderwild.entity.Crab;
import net.frozenblock.wilderwild.particle.options.FloatingSculkBubbleParticleOptions;
import net.frozenblock.wilderwild.particle.options.LeafClusterSeedParticleOptions;
import net.frozenblock.wilderwild.particle.options.SeedParticleOptions;
import net.frozenblock.wilderwild.particle.options.WWFallingLeavesParticleOptions;
import net.frozenblock.wilderwild.particle.options.WindClusterSeedParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;

public final class WWParticleTypes {
	private static final FrozenDeferredRegister<ParticleType<?>> REGISTER = FrozenDeferredRegister.create(
		Registries.PARTICLE_TYPE,
		WWConstants.MOD_ID
	);

	public static final FrozenHolder<ParticleType<?>, SimpleParticleType> POLLEN = register("pollen");
	public static final FrozenHolder<ParticleType<?>, ParticleType<SeedParticleOptions>> SEED = register("seed",
		false,
		particleType -> SeedParticleOptions.CODEC,
		particleType -> SeedParticleOptions.STREAM_CODEC
	);
	public static final FrozenHolder<ParticleType<?>, SimpleParticleType> PALE_FOG = register("pale_fog");
	public static final FrozenHolder<ParticleType<?>, SimpleParticleType> PALE_FOG_SMALL = register("pale_fog_small");
	public static final FrozenHolder<ParticleType<?>, SimpleParticleType> PALE_SPORE = register("pale_spore");
	public static final FrozenHolder<ParticleType<?>, ParticleType<FloatingSculkBubbleParticleOptions>> FLOATING_SCULK_BUBBLE = register("floating_sculk_bubble",
		false,
		particleType -> FloatingSculkBubbleParticleOptions.CODEC,
		particleType -> FloatingSculkBubbleParticleOptions.STREAM_CODEC
	);
	public static final FrozenHolder<ParticleType<?>, SimpleParticleType> TERMITE = register("termite");
	public static final FrozenHolder<ParticleType<?>, SimpleParticleType> COCONUT_SPLASH = register("coconut_splash");
	public static final FrozenHolder<ParticleType<?>, SimpleParticleType> SCORCHING_FLAME = register("scorching_flame");
	public static final FrozenHolder<ParticleType<?>, SimpleParticleType> UNDERWATER_ASH = register("underwater_ash");
	public static final FrozenHolder<ParticleType<?>, SimpleParticleType> PLANKTON = register("plankton");
	public static final FrozenHolder<ParticleType<?>, SimpleParticleType> GLOWING_PLANKTON = register("glowing_plankton");
	public static final FrozenHolder<ParticleType<?>, ParticleType<LeafClusterSeedParticleOptions>> LEAF_CLUSTER_SPAWNER = register("leaf_cluster",
		false,
		particleType -> LeafClusterSeedParticleOptions.CODEC,
		particleType -> LeafClusterSeedParticleOptions.STREAM_CODEC
	);
	public static final FrozenHolder<ParticleType<?>, SimpleParticleType> CHEST_BUBBLE_SPAWNER = register("chest_bubbles");
	public static final FrozenHolder<ParticleType<?>, SimpleParticleType> SHRIEKER_BUBBLE_SPAWNER = register("shrieker_bubbles");

	public static final FrozenHolder<ParticleType<?>, SimpleParticleType> HANGING_MESOGLEA_PEARLESCENT_BLUE = register("hanging_mesoglea_pearlescent_blue");
	public static final FrozenHolder<ParticleType<?>, SimpleParticleType> FALLING_MESOGLEA_PEARLESCENT_BLUE = register("falling_mesoglea_pearlescent_blue");
	public static final FrozenHolder<ParticleType<?>, SimpleParticleType> LANDING_MESOGLEA_PEARLESCENT_BLUE = register("landing_mesoglea_pearlescent_blue");
	public static final FrozenHolder<ParticleType<?>, SimpleParticleType> MESOGLEA_BUBBLE_PEARLESCENT_BLUE = register("mesoglea_bubble_pearlescent_blue");
	public static final FrozenHolder<ParticleType<?>, SimpleParticleType> BUBBLE_COLUMN_UP_MESOGLEA_PEARLESCENT_BLUE = register("bubble_column_up_pearlescent_blue");
	public static final FrozenHolder<ParticleType<?>, SimpleParticleType> CURRENT_DOWN_MESOGLEA_PEARLESCENT_BLUE = register("current_down_mesoglea_pearlescent_blue");
	public static final FrozenHolder<ParticleType<?>, SimpleParticleType> MESOGLEA_BUBBLE_POP_PEARLESCENT_BLUE = register("mesoglea_bubble_pop_pearlescent_blue");
	public static final FrozenHolder<ParticleType<?>, SimpleParticleType> MESOGLEA_SPLASH_PEARLESCENT_BLUE = register("mesoglea_splash_pearlescent_blue");

	public static final FrozenHolder<ParticleType<?>, SimpleParticleType> HANGING_MESOGLEA_PEARLESCENT_PURPLE = register("hanging_mesoglea_pearlescent_purple");
	public static final FrozenHolder<ParticleType<?>, SimpleParticleType> FALLING_MESOGLEA_PEARLESCENT_PURPLE = register("falling_mesoglea_pearlescent_purple");
	public static final FrozenHolder<ParticleType<?>, SimpleParticleType> LANDING_MESOGLEA_PEARLESCENT_PURPLE = register("landing_mesoglea_pearlescent_purple");
	public static final FrozenHolder<ParticleType<?>, SimpleParticleType> MESOGLEA_BUBBLE_PEARLESCENT_PURPLE = register("mesoglea_bubble_pearlescent_purple");
	public static final FrozenHolder<ParticleType<?>, SimpleParticleType> BUBBLE_COLUMN_UP_MESOGLEA_PEARLESCENT_PURPLE = register("bubble_column_up_mesoglea_pearlescent_purple");
	public static final FrozenHolder<ParticleType<?>, SimpleParticleType> CURRENT_DOWN_MESOGLEA_PEARLESCENT_PURPLE = register("current_down_mesoglea_pearlescent_purple");
	public static final FrozenHolder<ParticleType<?>, SimpleParticleType> MESOGLEA_BUBBLE_POP_PEARLESCENT_PURPLE = register("mesoglea_bubble_pop_pearlescent_purple");
	public static final FrozenHolder<ParticleType<?>, SimpleParticleType> MESOGLEA_SPLASH_PEARLESCENT_PURPLE = register("mesoglea_splash_pearlescent_purple");

	public static final FrozenHolder<ParticleType<?>, SimpleParticleType> HANGING_MESOGLEA_PINK = register("hanging_mesoglea_pink");
	public static final FrozenHolder<ParticleType<?>, SimpleParticleType> FALLING_MESOGLEA_PINK = register("falling_mesoglea_pink");
	public static final FrozenHolder<ParticleType<?>, SimpleParticleType> LANDING_MESOGLEA_PINK = register("landing_mesoglea_pink");
	public static final FrozenHolder<ParticleType<?>, SimpleParticleType> MESOGLEA_BUBBLE_PINK = register("mesoglea_bubble_pink");
	public static final FrozenHolder<ParticleType<?>, SimpleParticleType> BUBBLE_COLUMN_UP_MESOGLEA_PINK = register("bubble_column_up_mesoglea_pink");
	public static final FrozenHolder<ParticleType<?>, SimpleParticleType> CURRENT_DOWN_MESOGLEA_PINK = register("current_down_mesoglea_pink");
	public static final FrozenHolder<ParticleType<?>, SimpleParticleType> MESOGLEA_BUBBLE_POP_PINK = register("mesoglea_bubble_pop_pink");
	public static final FrozenHolder<ParticleType<?>, SimpleParticleType> MESOGLEA_SPLASH_PINK = register("mesoglea_splash_pink");

	public static final FrozenHolder<ParticleType<?>, SimpleParticleType> HANGING_MESOGLEA_RED = register("hanging_mesoglea_red");
	public static final FrozenHolder<ParticleType<?>, SimpleParticleType> FALLING_MESOGLEA_RED = register("falling_mesoglea_red");
	public static final FrozenHolder<ParticleType<?>, SimpleParticleType> LANDING_MESOGLEA_RED = register("landing_mesoglea_red");
	public static final FrozenHolder<ParticleType<?>, SimpleParticleType> MESOGLEA_BUBBLE_RED = register("mesoglea_bubble_red");
	public static final FrozenHolder<ParticleType<?>, SimpleParticleType> BUBBLE_COLUMN_UP_MESOGLEA_RED = register("bubble_column_up_mesoglea_red");
	public static final FrozenHolder<ParticleType<?>, SimpleParticleType> CURRENT_DOWN_MESOGLEA_RED = register("current_down_mesoglea_red");
	public static final FrozenHolder<ParticleType<?>, SimpleParticleType> MESOGLEA_BUBBLE_POP_RED = register("mesoglea_bubble_pop_red");
	public static final FrozenHolder<ParticleType<?>, SimpleParticleType> MESOGLEA_SPLASH_RED = register("mesoglea_splash_red");

	public static final FrozenHolder<ParticleType<?>, SimpleParticleType> HANGING_MESOGLEA_YELLOW = register("hanging_mesoglea_yellow");
	public static final FrozenHolder<ParticleType<?>, SimpleParticleType> FALLING_MESOGLEA_YELLOW = register("falling_mesoglea_yellow");
	public static final FrozenHolder<ParticleType<?>, SimpleParticleType> LANDING_MESOGLEA_YELLOW = register("landing_mesoglea_yellow");
	public static final FrozenHolder<ParticleType<?>, SimpleParticleType> MESOGLEA_BUBBLE_YELLOW = register("mesoglea_bubble_yellow");
	public static final FrozenHolder<ParticleType<?>, SimpleParticleType> BUBBLE_COLUMN_UP_MESOGLEA_YELLOW = register("bubble_column_up_mesoglea_yellow");
	public static final FrozenHolder<ParticleType<?>, SimpleParticleType> CURRENT_DOWN_MESOGLEA_YELLOW = register("current_down_mesoglea_yellow");
	public static final FrozenHolder<ParticleType<?>, SimpleParticleType> MESOGLEA_BUBBLE_POP_YELLOW = register("mesoglea_bubble_pop_yellow");
	public static final FrozenHolder<ParticleType<?>, SimpleParticleType> MESOGLEA_SPLASH_YELLOW = register("mesoglea_splash_yellow");

	public static final FrozenHolder<ParticleType<?>, SimpleParticleType> HANGING_MESOGLEA_LIME = register("hanging_mesoglea_lime");
	public static final FrozenHolder<ParticleType<?>, SimpleParticleType> FALLING_MESOGLEA_LIME = register("falling_mesoglea_lime");
	public static final FrozenHolder<ParticleType<?>, SimpleParticleType> LANDING_MESOGLEA_LIME = register("landing_mesoglea_lime");
	public static final FrozenHolder<ParticleType<?>, SimpleParticleType> MESOGLEA_BUBBLE_LIME = register("mesoglea_bubble_lime");
	public static final FrozenHolder<ParticleType<?>, SimpleParticleType> BUBBLE_COLUMN_UP_MESOGLEA_LIME = register("bubble_column_up_mesoglea_lime");
	public static final FrozenHolder<ParticleType<?>, SimpleParticleType> CURRENT_DOWN_MESOGLEA_LIME = register("current_down_mesoglea_lime");
	public static final FrozenHolder<ParticleType<?>, SimpleParticleType> MESOGLEA_BUBBLE_POP_LIME = register("mesoglea_bubble_pop_lime");
	public static final FrozenHolder<ParticleType<?>, SimpleParticleType> MESOGLEA_SPLASH_LIME = register("mesoglea_splash_lime");

	public static final FrozenHolder<ParticleType<?>, SimpleParticleType> HANGING_MESOGLEA_BLUE = register("hanging_mesoglea_blue");
	public static final FrozenHolder<ParticleType<?>, SimpleParticleType> FALLING_MESOGLEA_BLUE = register("falling_mesoglea_blue");
	public static final FrozenHolder<ParticleType<?>, SimpleParticleType> LANDING_MESOGLEA_BLUE = register("landing_mesoglea_blue");
	public static final FrozenHolder<ParticleType<?>, SimpleParticleType> MESOGLEA_BUBBLE_BLUE = register("mesoglea_bubble_blue");
	public static final FrozenHolder<ParticleType<?>, SimpleParticleType> BUBBLE_COLUMN_UP_MESOGLEA_BLUE = register("bubble_column_up_mesoglea_blue");
	public static final FrozenHolder<ParticleType<?>, SimpleParticleType> CURRENT_DOWN_MESOGLEA_BLUE = register("current_down_mesoglea_blue");
	public static final FrozenHolder<ParticleType<?>, SimpleParticleType> MESOGLEA_BUBBLE_POP_BLUE = register("mesoglea_bubble_pop_blue");
	public static final FrozenHolder<ParticleType<?>, SimpleParticleType> MESOGLEA_SPLASH_BLUE = register("mesoglea_splash_blue");

	public static final FrozenHolder<ParticleType<?>, ParticleType<WindClusterSeedParticleOptions>> WIND_CLUSTER = register("wind_cluster",
		false,
		particleType -> WindClusterSeedParticleOptions.CODEC,
		particleType -> WindClusterSeedParticleOptions.STREAM_CODEC
	);

	public static final FrozenHolder<ParticleType<?>, ParticleType<WWFallingLeavesParticleOptions>> OAK_LEAVES = createLeafParticle("oak_leaves");
	public static final FrozenHolder<ParticleType<?>, ParticleType<WWFallingLeavesParticleOptions>> OAK_LITTER_LEAVES = createLeafParticle("oak_litter_leaves");
	public static final FrozenHolder<ParticleType<?>, ParticleType<WWFallingLeavesParticleOptions>> SPRUCE_LEAVES = createLeafParticle("spruce_leaves");
	public static final FrozenHolder<ParticleType<?>, ParticleType<WWFallingLeavesParticleOptions>> SPRUCE_LITTER_LEAVES = createLeafParticle("spruce_litter_leaves");
	public static final FrozenHolder<ParticleType<?>, ParticleType<WWFallingLeavesParticleOptions>> BIRCH_LEAVES = createLeafParticle("birch_leaves");
	public static final FrozenHolder<ParticleType<?>, ParticleType<WWFallingLeavesParticleOptions>> BIRCH_LITTER_LEAVES = createLeafParticle("birch_litter_leaves");
	public static final FrozenHolder<ParticleType<?>, ParticleType<WWFallingLeavesParticleOptions>> JUNGLE_LEAVES = createLeafParticle("jungle_leaves");
	public static final FrozenHolder<ParticleType<?>, ParticleType<WWFallingLeavesParticleOptions>> JUNGLE_LITTER_LEAVES = createLeafParticle("jungle_litter_leaves");
	public static final FrozenHolder<ParticleType<?>, ParticleType<WWFallingLeavesParticleOptions>> ACACIA_LEAVES = createLeafParticle("acacia_leaves");
	public static final FrozenHolder<ParticleType<?>, ParticleType<WWFallingLeavesParticleOptions>> ACACIA_LITTER_LEAVES = createLeafParticle("acacia_litter_leaves");
	public static final FrozenHolder<ParticleType<?>, ParticleType<WWFallingLeavesParticleOptions>> DARK_OAK_LEAVES = createLeafParticle("dark_oak_leaves");
	public static final FrozenHolder<ParticleType<?>, ParticleType<WWFallingLeavesParticleOptions>> DARK_OAK_LITTER_LEAVES = createLeafParticle("dark_oak_litter_leaves");
	public static final FrozenHolder<ParticleType<?>, ParticleType<WWFallingLeavesParticleOptions>> PALE_OAK_LEAVES = createLeafParticle("pale_oak_leaves");
	public static final FrozenHolder<ParticleType<?>, ParticleType<WWFallingLeavesParticleOptions>> PALE_OAK_LITTER_LEAVES = createLeafParticle("pale_oak_litter_leaves");
	public static final FrozenHolder<ParticleType<?>, ParticleType<WWFallingLeavesParticleOptions>> MANGROVE_LEAVES = createLeafParticle("mangrove_leaves");
	public static final FrozenHolder<ParticleType<?>, ParticleType<WWFallingLeavesParticleOptions>> MANGROVE_LITTER_LEAVES = createLeafParticle("mangrove_litter_leaves");
	public static final FrozenHolder<ParticleType<?>, ParticleType<WWFallingLeavesParticleOptions>> CHERRY_LEAVES = createLeafParticle("cherry_leaves");
	public static final FrozenHolder<ParticleType<?>, ParticleType<WWFallingLeavesParticleOptions>> CHERRY_LITTER_LEAVES = createLeafParticle("cherry_litter_leaves");
	public static final FrozenHolder<ParticleType<?>, ParticleType<WWFallingLeavesParticleOptions>> AZALEA_LEAVES = createLeafParticle("azalea_leaves");
	public static final FrozenHolder<ParticleType<?>, ParticleType<WWFallingLeavesParticleOptions>> FLOWERING_AZALEA_LEAVES = createLeafParticle("flowering_azalea_leaves");
	public static final FrozenHolder<ParticleType<?>, ParticleType<WWFallingLeavesParticleOptions>> AZALEA_LITTER_LEAVES = createLeafParticle("azalea_litter_leaves");

	public static final FrozenHolder<ParticleType<?>, ParticleType<WWFallingLeavesParticleOptions>> BAOBAB_LEAVES = createLeafParticle("baobab_leaves");
	public static final FrozenHolder<ParticleType<?>, ParticleType<WWFallingLeavesParticleOptions>> BAOBAB_LITTER_LEAVES = createLeafParticle("baobab_litter_leaves");
	public static final FrozenHolder<ParticleType<?>, ParticleType<WWFallingLeavesParticleOptions>> WILLOW_LEAVES = createLeafParticle("willow_leaves");
	public static final FrozenHolder<ParticleType<?>, ParticleType<WWFallingLeavesParticleOptions>> WILLOW_LITTER_LEAVES = createLeafParticle("willow_litter_leaves");
	public static final FrozenHolder<ParticleType<?>, ParticleType<WWFallingLeavesParticleOptions>> CYPRESS_LEAVES = createLeafParticle("cypress_leaves");
	public static final FrozenHolder<ParticleType<?>, ParticleType<WWFallingLeavesParticleOptions>> CYPRESS_LITTER_LEAVES = createLeafParticle("cypress_litter_leaves");
	public static final FrozenHolder<ParticleType<?>, ParticleType<WWFallingLeavesParticleOptions>> PALM_FRONDS = createLeafParticle("palm_fronds");
	public static final FrozenHolder<ParticleType<?>, ParticleType<WWFallingLeavesParticleOptions>> PALM_LITTER_FRONDS = createLeafParticle("palm_litter_fronds");

	public static final FrozenHolder<ParticleType<?>, ParticleType<WWFallingLeavesParticleOptions>> YELLOW_MAPLE_LEAVES = createLeafParticle("yellow_maple_leaves");
	public static final FrozenHolder<ParticleType<?>, ParticleType<WWFallingLeavesParticleOptions>> ORANGE_MAPLE_LEAVES = createLeafParticle("orange_maple_leaves");
	public static final FrozenHolder<ParticleType<?>, ParticleType<WWFallingLeavesParticleOptions>> RED_MAPLE_LEAVES = createLeafParticle("red_maple_leaves");

	static {
		REGISTER.register();
	}

	public static void init() {
		VibrationParticleVisibilityApi.registerVisibilityTest((data, user) -> !(user instanceof Crab.VibrationUser) && !(user instanceof IcicleBlockEntity.VibrationUser));
	}

	public static FrozenHolder<ParticleType<?>, ParticleType<WWFallingLeavesParticleOptions>> createLeafParticle(Identifier id) {
		return register(
			id,
			false,
			particleType -> WWFallingLeavesParticleOptions.CODEC,
			particleType -> WWFallingLeavesParticleOptions.STREAM_CODEC
		);
	}

	private static FrozenHolder<ParticleType<?>, ParticleType<WWFallingLeavesParticleOptions>> createLeafParticle(String name) {
		return createLeafParticle(WWConstants.id(name));
	}

	private static FrozenHolder<ParticleType<?>, SimpleParticleType> register(String name, boolean alwaysShow) {
		return REGISTER.register(name, () -> ParticleTypeHelper.simple(alwaysShow));
	}

	private static FrozenHolder<ParticleType<?>, SimpleParticleType> register(String name) {
		return register(name, false);
	}

	private static <T extends ParticleOptions> FrozenHolder<ParticleType<?>, ParticleType<T>> register(
		String name,
		boolean alwaysShow,
		Function<ParticleType<T>, MapCodec<T>> codec,
		Function<ParticleType<T>, StreamCodec<? super RegistryFriendlyByteBuf, T>> streamCodec
	) {
		return register(WWConstants.id(name), alwaysShow, codec, streamCodec);
	}

	private static <T extends ParticleOptions> FrozenHolder<ParticleType<?>, ParticleType<T>> register(
		Identifier id,
		boolean alwaysShow,
		Function<ParticleType<T>, MapCodec<T>> codec,
		Function<ParticleType<T>, StreamCodec<? super RegistryFriendlyByteBuf, T>> streamCodec
	) {
		return register(id, alwaysShow, codec, streamCodec, null);
	}

	private static <T extends ParticleOptions> FrozenHolder<ParticleType<?>, ParticleType<T>> register(
		Identifier id,
		boolean alwaysShow,
		Function<ParticleType<T>, MapCodec<T>> codec,
		Function<ParticleType<T>, StreamCodec<? super RegistryFriendlyByteBuf, T>> streamCodec,
		Consumer<ParticleType<T>> also
	) {
		return REGISTER.register(id.getPath(), () -> ParticleTypeHelper.complex(alwaysShow, codec, streamCodec), also);
	}
}
