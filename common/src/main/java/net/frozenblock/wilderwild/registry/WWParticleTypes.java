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
import lombok.experimental.UtilityClass;
import net.frozenblock.lib.particle.api.ParticleTypeHelper;
import net.frozenblock.lib.particle.api.VibrationSignalParticleEvents;
import net.frozenblock.lib.platform.api.registry.DeferredParticleType;
import net.frozenblock.lib.platform.api.registry.DeferredRegister;
import net.frozenblock.lib.platform.api.registry.DeferredSimpleParticleType;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.block.impl.MapleCollection;
import net.frozenblock.wilderwild.block.impl.MesogleaCollection;
import net.frozenblock.wilderwild.block.impl.PoplarCollection;
import net.frozenblock.wilderwild.entity.Crab;
import net.frozenblock.wilderwild.particle.options.FloatingSculkBubbleParticleOptions;
import net.frozenblock.wilderwild.particle.options.LeafClusterSeedParticleOptions;
import net.frozenblock.wilderwild.particle.options.SeedParticleOptions;
import net.frozenblock.wilderwild.particle.options.WWFallingLeavesParticleOptions;
import net.frozenblock.wilderwild.particle.options.WindClusterSeedParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.ApiStatus;

@UtilityClass
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

	// MESOGLEA
	public static final MesogleaCollection<DeferredSimpleParticleType> HANGING_MESOGLEA = MesogleaCollection.NAMES.map(name -> register("hanging_mesoglea_" + name));
	public static final MesogleaCollection<DeferredSimpleParticleType> FALLING_MESOGLEA = MesogleaCollection.NAMES.map(name -> register("falling_mesoglea_" + name));
	public static final MesogleaCollection<DeferredSimpleParticleType> LANDING_MESOGLEA = MesogleaCollection.NAMES.map(name -> register("landing_mesoglea_" + name));
	public static final MesogleaCollection<DeferredSimpleParticleType> MESOGLEA_BUBBLE = MesogleaCollection.NAMES.map(name -> register("mesoglea_bubble_" + name));
	public static final MesogleaCollection<DeferredSimpleParticleType> MESOGLEA_BUBBLE_COLUMN_UP = MesogleaCollection.NAMES.map(name -> register("mesoglea_bubble_column_up_" + name));
	public static final MesogleaCollection<DeferredSimpleParticleType> MESOGLEA_CURRENT_DOWN = MesogleaCollection.NAMES.map(name -> register("mesoglea_current_down_" + name));
	public static final MesogleaCollection<DeferredSimpleParticleType> MESOGLEA_BUBBLE_POP = MesogleaCollection.NAMES.map(name -> register("mesoglea_bubble_pop_" + name));
	public static final MesogleaCollection<DeferredSimpleParticleType> MESOGLEA_SPLASH = MesogleaCollection.NAMES.map(name -> register("mesoglea_splash_" + name));

	// VANILLA LEAVES
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
	public static final PoplarCollection<DeferredParticleType<WWFallingLeavesParticleOptions>> POPLAR_LEAVES = PoplarCollection.NAMES.map(
		name -> createLeafParticle(name + "_poplar_leaves")
	);
	public static final PoplarCollection<DeferredParticleType<WWFallingLeavesParticleOptions>> POPLAR_LITTER_LEAVES = PoplarCollection.NAMES.map(
		name -> createLeafParticle(name + "_poplar_litter_leaves")
	);
	public static final DeferredParticleType<WWFallingLeavesParticleOptions> AZALEA_LEAVES = createLeafParticle("azalea_leaves");
	public static final DeferredParticleType<WWFallingLeavesParticleOptions> FLOWERING_AZALEA_LEAVES = createLeafParticle("flowering_azalea_leaves");
	public static final DeferredParticleType<WWFallingLeavesParticleOptions> AZALEA_LITTER_LEAVES = createLeafParticle("azalea_litter_leaves");

	// WILDER WILD LEAVES
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

	// SPAWNER PARTICLES
	public static final DeferredParticleType<LeafClusterSeedParticleOptions> LEAF_CLUSTER_SPAWNER = register("leaf_cluster",
		false,
		LeafClusterSeedParticleOptions.CODEC,
		LeafClusterSeedParticleOptions.STREAM_CODEC
	);
	public static final DeferredSimpleParticleType CHEST_BUBBLE_SPAWNER = register("chest_bubbles");
	public static final DeferredSimpleParticleType SHRIEKER_BUBBLE_SPAWNER = register("shrieker_bubbles");
	public static final DeferredParticleType<WindClusterSeedParticleOptions> WIND_CLUSTER = register("wind_cluster",
		false,
		WindClusterSeedParticleOptions.CODEC,
		WindClusterSeedParticleOptions.STREAM_CODEC
	);

	static {
		REGISTER.register();
	}

	@ApiStatus.Internal
	public static void init() {
		VibrationSignalParticleEvents.EVALUATE_VISIBILITY.register(((data, user) -> !(user instanceof Crab.VibrationUser)));
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
