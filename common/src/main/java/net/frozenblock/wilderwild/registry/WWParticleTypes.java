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
import net.frozenblock.lib.platform.api.registry.DeferredHolder;
import net.frozenblock.lib.platform.api.registry.DeferredParticleType;
import net.frozenblock.lib.platform.api.registry.DeferredRegister;
import net.frozenblock.lib.platform.api.registry.DeferredSimpleParticleType;
import net.frozenblock.lib.platform.api.registry.ParticleTypeHelper;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.block.entity.IcicleBlockEntity;
import net.frozenblock.wilderwild.block.impl.MapleCollection;
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
	private static final DeferredRegister.ParticleTypes REGISTER = DeferredRegister.createParticleTypes(WWConstants.MOD_ID);

	public static final DeferredSimpleParticleType POLLEN = register("pollen");
	public static final DeferredParticleType<SeedParticleOptions> SEED = register("seed",
		false,
		SeedParticleOptions.CODEC,
		SeedParticleOptions.STREAM_CODEC
	);
	public static final DeferredSimpleParticleType PALE_FOG = register("pale_fog");
	public static final DeferredSimpleParticleType PALE_FOG_SMALL = register("pale_fog_small");
	public static final DeferredSimpleParticleType PALE_SPORE = register("pale_spore");
	public static final DeferredParticleType<FloatingSculkBubbleParticleOptions> FLOATING_SCULK_BUBBLE = register("floating_sculk_bubble",
		false,
		FloatingSculkBubbleParticleOptions.CODEC,
		FloatingSculkBubbleParticleOptions.STREAM_CODEC
	);
	public static final DeferredSimpleParticleType TERMITE = register("termite");
	public static final DeferredSimpleParticleType COCONUT_SPLASH = register("coconut_splash");
	public static final DeferredSimpleParticleType SCORCHING_FLAME = register("scorching_flame");
	public static final DeferredSimpleParticleType UNDERWATER_ASH = register("underwater_ash");
	public static final DeferredSimpleParticleType PLANKTON = register("plankton");
	public static final DeferredSimpleParticleType GLOWING_PLANKTON = register("glowing_plankton");
	public static final DeferredParticleType<LeafClusterSeedParticleOptions> LEAF_CLUSTER_SPAWNER = register("leaf_cluster",
		false,
		LeafClusterSeedParticleOptions.CODEC,
		LeafClusterSeedParticleOptions.STREAM_CODEC
	);
	public static final DeferredSimpleParticleType CHEST_BUBBLE_SPAWNER = register("chest_bubbles");
	public static final DeferredSimpleParticleType SHRIEKER_BUBBLE_SPAWNER = register("shrieker_bubbles");

	public static final DeferredSimpleParticleType HANGING_MESOGLEA_PEARLESCENT_BLUE = register("hanging_mesoglea_pearlescent_blue");
	public static final DeferredSimpleParticleType FALLING_MESOGLEA_PEARLESCENT_BLUE = register("falling_mesoglea_pearlescent_blue");
	public static final DeferredSimpleParticleType LANDING_MESOGLEA_PEARLESCENT_BLUE = register("landing_mesoglea_pearlescent_blue");
	public static final DeferredSimpleParticleType MESOGLEA_BUBBLE_PEARLESCENT_BLUE = register("mesoglea_bubble_pearlescent_blue");
	public static final DeferredSimpleParticleType BUBBLE_COLUMN_UP_MESOGLEA_PEARLESCENT_BLUE = register("bubble_column_up_pearlescent_blue");
	public static final DeferredSimpleParticleType CURRENT_DOWN_MESOGLEA_PEARLESCENT_BLUE = register("current_down_mesoglea_pearlescent_blue");
	public static final DeferredSimpleParticleType MESOGLEA_BUBBLE_POP_PEARLESCENT_BLUE = register("mesoglea_bubble_pop_pearlescent_blue");
	public static final DeferredSimpleParticleType MESOGLEA_SPLASH_PEARLESCENT_BLUE = register("mesoglea_splash_pearlescent_blue");

	public static final DeferredSimpleParticleType HANGING_MESOGLEA_PEARLESCENT_PURPLE = register("hanging_mesoglea_pearlescent_purple");
	public static final DeferredSimpleParticleType FALLING_MESOGLEA_PEARLESCENT_PURPLE = register("falling_mesoglea_pearlescent_purple");
	public static final DeferredSimpleParticleType LANDING_MESOGLEA_PEARLESCENT_PURPLE = register("landing_mesoglea_pearlescent_purple");
	public static final DeferredSimpleParticleType MESOGLEA_BUBBLE_PEARLESCENT_PURPLE = register("mesoglea_bubble_pearlescent_purple");
	public static final DeferredSimpleParticleType BUBBLE_COLUMN_UP_MESOGLEA_PEARLESCENT_PURPLE = register("bubble_column_up_mesoglea_pearlescent_purple");
	public static final DeferredSimpleParticleType CURRENT_DOWN_MESOGLEA_PEARLESCENT_PURPLE = register("current_down_mesoglea_pearlescent_purple");
	public static final DeferredSimpleParticleType MESOGLEA_BUBBLE_POP_PEARLESCENT_PURPLE = register("mesoglea_bubble_pop_pearlescent_purple");
	public static final DeferredSimpleParticleType MESOGLEA_SPLASH_PEARLESCENT_PURPLE = register("mesoglea_splash_pearlescent_purple");

	public static final DeferredSimpleParticleType HANGING_MESOGLEA_PINK = register("hanging_mesoglea_pink");
	public static final DeferredSimpleParticleType FALLING_MESOGLEA_PINK = register("falling_mesoglea_pink");
	public static final DeferredSimpleParticleType LANDING_MESOGLEA_PINK = register("landing_mesoglea_pink");
	public static final DeferredSimpleParticleType MESOGLEA_BUBBLE_PINK = register("mesoglea_bubble_pink");
	public static final DeferredSimpleParticleType BUBBLE_COLUMN_UP_MESOGLEA_PINK = register("bubble_column_up_mesoglea_pink");
	public static final DeferredSimpleParticleType CURRENT_DOWN_MESOGLEA_PINK = register("current_down_mesoglea_pink");
	public static final DeferredSimpleParticleType MESOGLEA_BUBBLE_POP_PINK = register("mesoglea_bubble_pop_pink");
	public static final DeferredSimpleParticleType MESOGLEA_SPLASH_PINK = register("mesoglea_splash_pink");

	public static final DeferredSimpleParticleType HANGING_MESOGLEA_RED = register("hanging_mesoglea_red");
	public static final DeferredSimpleParticleType FALLING_MESOGLEA_RED = register("falling_mesoglea_red");
	public static final DeferredSimpleParticleType LANDING_MESOGLEA_RED = register("landing_mesoglea_red");
	public static final DeferredSimpleParticleType MESOGLEA_BUBBLE_RED = register("mesoglea_bubble_red");
	public static final DeferredSimpleParticleType BUBBLE_COLUMN_UP_MESOGLEA_RED = register("bubble_column_up_mesoglea_red");
	public static final DeferredSimpleParticleType CURRENT_DOWN_MESOGLEA_RED = register("current_down_mesoglea_red");
	public static final DeferredSimpleParticleType MESOGLEA_BUBBLE_POP_RED = register("mesoglea_bubble_pop_red");
	public static final DeferredSimpleParticleType MESOGLEA_SPLASH_RED = register("mesoglea_splash_red");

	public static final DeferredSimpleParticleType HANGING_MESOGLEA_YELLOW = register("hanging_mesoglea_yellow");
	public static final DeferredSimpleParticleType FALLING_MESOGLEA_YELLOW = register("falling_mesoglea_yellow");
	public static final DeferredSimpleParticleType LANDING_MESOGLEA_YELLOW = register("landing_mesoglea_yellow");
	public static final DeferredSimpleParticleType MESOGLEA_BUBBLE_YELLOW = register("mesoglea_bubble_yellow");
	public static final DeferredSimpleParticleType BUBBLE_COLUMN_UP_MESOGLEA_YELLOW = register("bubble_column_up_mesoglea_yellow");
	public static final DeferredSimpleParticleType CURRENT_DOWN_MESOGLEA_YELLOW = register("current_down_mesoglea_yellow");
	public static final DeferredSimpleParticleType MESOGLEA_BUBBLE_POP_YELLOW = register("mesoglea_bubble_pop_yellow");
	public static final DeferredSimpleParticleType MESOGLEA_SPLASH_YELLOW = register("mesoglea_splash_yellow");

	public static final DeferredSimpleParticleType HANGING_MESOGLEA_LIME = register("hanging_mesoglea_lime");
	public static final DeferredSimpleParticleType FALLING_MESOGLEA_LIME = register("falling_mesoglea_lime");
	public static final DeferredSimpleParticleType LANDING_MESOGLEA_LIME = register("landing_mesoglea_lime");
	public static final DeferredSimpleParticleType MESOGLEA_BUBBLE_LIME = register("mesoglea_bubble_lime");
	public static final DeferredSimpleParticleType BUBBLE_COLUMN_UP_MESOGLEA_LIME = register("bubble_column_up_mesoglea_lime");
	public static final DeferredSimpleParticleType CURRENT_DOWN_MESOGLEA_LIME = register("current_down_mesoglea_lime");
	public static final DeferredSimpleParticleType MESOGLEA_BUBBLE_POP_LIME = register("mesoglea_bubble_pop_lime");
	public static final DeferredSimpleParticleType MESOGLEA_SPLASH_LIME = register("mesoglea_splash_lime");

	public static final DeferredSimpleParticleType HANGING_MESOGLEA_BLUE = register("hanging_mesoglea_blue");
	public static final DeferredSimpleParticleType FALLING_MESOGLEA_BLUE = register("falling_mesoglea_blue");
	public static final DeferredSimpleParticleType LANDING_MESOGLEA_BLUE = register("landing_mesoglea_blue");
	public static final DeferredSimpleParticleType MESOGLEA_BUBBLE_BLUE = register("mesoglea_bubble_blue");
	public static final DeferredSimpleParticleType BUBBLE_COLUMN_UP_MESOGLEA_BLUE = register("bubble_column_up_mesoglea_blue");
	public static final DeferredSimpleParticleType CURRENT_DOWN_MESOGLEA_BLUE = register("current_down_mesoglea_blue");
	public static final DeferredSimpleParticleType MESOGLEA_BUBBLE_POP_BLUE = register("mesoglea_bubble_pop_blue");
	public static final DeferredSimpleParticleType MESOGLEA_SPLASH_BLUE = register("mesoglea_splash_blue");

	public static final DeferredParticleType<WindClusterSeedParticleOptions> WIND_CLUSTER = register("wind_cluster",
		false,
		WindClusterSeedParticleOptions.CODEC,
		WindClusterSeedParticleOptions.STREAM_CODEC
	);

	public static final DeferredParticleType<WWFallingLeavesParticleOptions> OAK_LEAVES = createLeafParticle("oak_leaves");
	public static final DeferredParticleType<WWFallingLeavesParticleOptions> OAK_LITTER_LEAVES = createLeafParticle("oak_litter_leaves");
	public static final DeferredParticleType<WWFallingLeavesParticleOptions> SPRUCE_LEAVES = createLeafParticle("spruce_leaves");
	public static final DeferredParticleType<WWFallingLeavesParticleOptions> SPRUCE_LITTER_LEAVES = createLeafParticle("spruce_litter_leaves");
	public static final DeferredParticleType<WWFallingLeavesParticleOptions> BIRCH_LEAVES = createLeafParticle("birch_leaves");
	public static final DeferredParticleType<WWFallingLeavesParticleOptions> BIRCH_LITTER_LEAVES = createLeafParticle("birch_litter_leaves");
	public static final DeferredParticleType<WWFallingLeavesParticleOptions> JUNGLE_LEAVES = createLeafParticle("jungle_leaves");
	public static final DeferredParticleType<WWFallingLeavesParticleOptions> JUNGLE_LITTER_LEAVES = createLeafParticle("jungle_litter_leaves");
	public static final DeferredParticleType<WWFallingLeavesParticleOptions> ACACIA_LEAVES = createLeafParticle("acacia_leaves");
	public static final DeferredParticleType<WWFallingLeavesParticleOptions> ACACIA_LITTER_LEAVES = createLeafParticle("acacia_litter_leaves");
	public static final DeferredParticleType<WWFallingLeavesParticleOptions> DARK_OAK_LEAVES = createLeafParticle("dark_oak_leaves");
	public static final DeferredParticleType<WWFallingLeavesParticleOptions> DARK_OAK_LITTER_LEAVES = createLeafParticle("dark_oak_litter_leaves");
	public static final DeferredParticleType<WWFallingLeavesParticleOptions> PALE_OAK_LEAVES = createLeafParticle("pale_oak_leaves");
	public static final DeferredParticleType<WWFallingLeavesParticleOptions> PALE_OAK_LITTER_LEAVES = createLeafParticle("pale_oak_litter_leaves");
	public static final DeferredParticleType<WWFallingLeavesParticleOptions> MANGROVE_LEAVES = createLeafParticle("mangrove_leaves");
	public static final DeferredParticleType<WWFallingLeavesParticleOptions> MANGROVE_LITTER_LEAVES = createLeafParticle("mangrove_litter_leaves");
	public static final DeferredParticleType<WWFallingLeavesParticleOptions> CHERRY_LEAVES = createLeafParticle("cherry_leaves");
	public static final DeferredParticleType<WWFallingLeavesParticleOptions> CHERRY_LITTER_LEAVES = createLeafParticle("cherry_litter_leaves");
	public static final DeferredParticleType<WWFallingLeavesParticleOptions> AZALEA_LEAVES = createLeafParticle("azalea_leaves");
	public static final DeferredParticleType<WWFallingLeavesParticleOptions> FLOWERING_AZALEA_LEAVES = createLeafParticle("flowering_azalea_leaves");
	public static final DeferredParticleType<WWFallingLeavesParticleOptions> AZALEA_LITTER_LEAVES = createLeafParticle("azalea_litter_leaves");

	public static final DeferredParticleType<WWFallingLeavesParticleOptions> BAOBAB_LEAVES = createLeafParticle("baobab_leaves");
	public static final DeferredParticleType<WWFallingLeavesParticleOptions> BAOBAB_LITTER_LEAVES = createLeafParticle("baobab_litter_leaves");
	public static final DeferredParticleType<WWFallingLeavesParticleOptions> WILLOW_LEAVES = createLeafParticle("willow_leaves");
	public static final DeferredParticleType<WWFallingLeavesParticleOptions> WILLOW_LITTER_LEAVES = createLeafParticle("willow_litter_leaves");
	public static final DeferredParticleType<WWFallingLeavesParticleOptions> CYPRESS_LEAVES = createLeafParticle("cypress_leaves");
	public static final DeferredParticleType<WWFallingLeavesParticleOptions> CYPRESS_LITTER_LEAVES = createLeafParticle("cypress_litter_leaves");
	public static final DeferredParticleType<WWFallingLeavesParticleOptions> PALM_FRONDS = createLeafParticle("palm_fronds");
	public static final DeferredParticleType<WWFallingLeavesParticleOptions> PALM_LITTER_FRONDS = createLeafParticle("palm_litter_fronds");
	public static final MapleCollection<DeferredParticleType<WWFallingLeavesParticleOptions>> MAPLE_LEAVES = MapleCollection.NAMES.map(
		name -> createLeafParticle(name + "_maple_leaves")
	);

	static {
		REGISTER.register();
	}

	public static void init() {
		VibrationParticleVisibilityApi.registerVisibilityTest((data, user) -> !(user instanceof Crab.VibrationUser) && !(user instanceof IcicleBlockEntity.VibrationUser));
	}

	private static DeferredParticleType<WWFallingLeavesParticleOptions> createLeafParticle(String name) {
		return register(
			name,
			false,
			WWFallingLeavesParticleOptions.CODEC,
			WWFallingLeavesParticleOptions.STREAM_CODEC
		);
	}

	private static DeferredSimpleParticleType register(String name, boolean overrideLimiter) {
		return REGISTER.register(name, overrideLimiter);
	}

	private static DeferredSimpleParticleType register(String name) {
		return register(name, false);
	}

	private static <T extends ParticleOptions> DeferredParticleType<T> register(
		String name,
		boolean overrideLimiter,
		MapCodec<T> codec,
		StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec
	) {
		return REGISTER.register(name, ParticleTypeHelper.complex(overrideLimiter, codec, streamCodec));
	}
}
