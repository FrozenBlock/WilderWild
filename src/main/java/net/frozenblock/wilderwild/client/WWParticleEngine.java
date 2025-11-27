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

package net.frozenblock.wilderwild.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.frozenblock.wilderwild.particle.ChestBubbleSeedParticle;
import net.frozenblock.wilderwild.particle.FallingParticle;
import net.frozenblock.wilderwild.particle.FloatingSculkBubbleParticle;
import net.frozenblock.wilderwild.particle.LeafClusterSeedParticle;
import net.frozenblock.wilderwild.particle.LeafParticle;
import net.frozenblock.wilderwild.particle.MesogleaBubbleColumnUpParticle;
import net.frozenblock.wilderwild.particle.MesogleaBubbleParticle;
import net.frozenblock.wilderwild.particle.MesogleaBubblePopParticle;
import net.frozenblock.wilderwild.particle.MesogleaCurrentDownParticle;
import net.frozenblock.wilderwild.particle.MesogleaDripParticle;
import net.frozenblock.wilderwild.particle.MesogleaSplashParticle;
import net.frozenblock.wilderwild.particle.PlanktonParticle;
import net.frozenblock.wilderwild.particle.PollenParticle;
import net.frozenblock.wilderwild.particle.SeedParticle;
import net.frozenblock.wilderwild.particle.ShriekerBubbleSeedParticle;
import net.frozenblock.wilderwild.particle.TermiteParticle;
import net.frozenblock.wilderwild.particle.WindClusterSeedParticle;
import net.frozenblock.wilderwild.particle.provider.WWParticleProviders;
import net.frozenblock.wilderwild.registry.WWParticleTypes;

@Environment(EnvType.CLIENT)
public final class WWParticleEngine {

	public static void init() {
		ParticleFactoryRegistry particleRegistry = ParticleFactoryRegistry.getInstance();

		particleRegistry.register(WWParticleTypes.LEAF_CLUSTER_SPAWNER, LeafClusterSeedParticle.Provider::new);
		particleRegistry.register(WWParticleTypes.OAK_LEAVES, LeafParticle.Provider::new);
		particleRegistry.register(WWParticleTypes.SPRUCE_LEAVES, LeafParticle.Provider::new);
		particleRegistry.register(WWParticleTypes.BIRCH_LEAVES, LeafParticle.Provider::new);
		particleRegistry.register(WWParticleTypes.JUNGLE_LEAVES, LeafParticle.Provider::new);
		particleRegistry.register(WWParticleTypes.ACACIA_LEAVES, LeafParticle.Provider::new);
		particleRegistry.register(WWParticleTypes.DARK_OAK_LEAVES, LeafParticle.Provider::new);
		particleRegistry.register(WWParticleTypes.MANGROVE_LEAVES, LeafParticle.Provider::new);
		particleRegistry.register(WWParticleTypes.CHERRY_LEAVES, LeafParticle.Provider::new);
		particleRegistry.register(WWParticleTypes.AZALEA_LEAVES, LeafParticle.Provider::new);
		particleRegistry.register(WWParticleTypes.FLOWERING_AZALEA_LEAVES, LeafParticle.Provider::new);
		particleRegistry.register(WWParticleTypes.BAOBAB_LEAVES, LeafParticle.Provider::new);
		particleRegistry.register(WWParticleTypes.CYPRESS_LEAVES, LeafParticle.Provider::new);
		particleRegistry.register(WWParticleTypes.PALM_FRONDS, LeafParticle.Provider::new);
		particleRegistry.register(WWParticleTypes.YELLOW_MAPLE_LEAVES, LeafParticle.Provider::new);
		particleRegistry.register(WWParticleTypes.ORANGE_MAPLE_LEAVES, LeafParticle.Provider::new);
		particleRegistry.register(WWParticleTypes.RED_MAPLE_LEAVES, LeafParticle.Provider::new);
		particleRegistry.register(WWParticleTypes.WILLOW_LEAVES, LeafParticle.Provider::new);

		particleRegistry.register(WWParticleTypes.CHEST_BUBBLE_SPAWNER, ChestBubbleSeedParticle.Provider::new);
		particleRegistry.register(WWParticleTypes.SHRIEKER_BUBBLE_SPAWNER, ShriekerBubbleSeedParticle.Provider::new);
		particleRegistry.register(WWParticleTypes.WIND_CLUSTER, WindClusterSeedParticle.Provider::new);
		particleRegistry.register(WWParticleTypes.POLLEN, PollenParticle.Provider::new);
		particleRegistry.register(WWParticleTypes.SEED, SeedParticle.Provider::new);
		particleRegistry.register(WWParticleTypes.FLOATING_SCULK_BUBBLE, FloatingSculkBubbleParticle.Provider::new);
		particleRegistry.register(WWParticleTypes.TERMITE, TermiteParticle.Provider::new);
		particleRegistry.register(WWParticleTypes.COCONUT_SPLASH, FallingParticle.Provider::new);
		particleRegistry.register(WWParticleTypes.SCORCHING_FLAME, WWParticleProviders.ScorchingEffectFlameProvider::new);
		particleRegistry.register(WWParticleTypes.UNDERWATER_ASH, WWParticleProviders.UnderwaterAshProvider::new);
		particleRegistry.register(WWParticleTypes.PLANKTON, PlanktonParticle.PlanktonProvider::new);
		particleRegistry.register(WWParticleTypes.GLOWING_PLANKTON, PlanktonParticle.GlowingProvider::new);
		particleRegistry.register(WWParticleTypes.BLUE_PEARLESCENT_HANGING_MESOGLEA, MesogleaDripParticle.BPMesogleaHangProvider::new);
		particleRegistry.register(WWParticleTypes.BLUE_PEARLESCENT_FALLING_MESOGLEA, MesogleaDripParticle.BPMesogleaFallProvider::new);
		particleRegistry.register(WWParticleTypes.BLUE_PEARLESCENT_LANDING_MESOGLEA, MesogleaDripParticle.BPMesogleaLandProvider::new);
		particleRegistry.register(
			WWParticleTypes.BLUE_PEARLESCENT_MESOGLEA_BUBBLE,
			(spriteProvider) -> new MesogleaBubbleParticle.Provider(spriteProvider, WWParticleTypes.BLUE_PEARLESCENT_MESOGLEA_BUBBLE_POP)
		);
		particleRegistry.register(
			WWParticleTypes.BLUE_PEARLESCENT_MESOGLEA_BUBBLE_COLUMN_UP,
			(spriteProvider) -> new MesogleaBubbleColumnUpParticle.Provider(spriteProvider, WWParticleTypes.BLUE_PEARLESCENT_MESOGLEA_BUBBLE_POP)
		);
		particleRegistry.register(
			WWParticleTypes.BLUE_PEARLESCENT_MESOGLEA_CURRENT_DOWN,
			(spriteProvider) -> new MesogleaCurrentDownParticle.Provider(spriteProvider, WWParticleTypes.BLUE_PEARLESCENT_MESOGLEA_BUBBLE_POP)
		);
		particleRegistry.register(WWParticleTypes.BLUE_PEARLESCENT_MESOGLEA_BUBBLE_POP, MesogleaBubblePopParticle.Provider::new);
		particleRegistry.register(WWParticleTypes.BLUE_PEARLESCENT_MESOGLEA_SPLASH, MesogleaSplashParticle.Provider::new);
		particleRegistry.register(WWParticleTypes.PURPLE_PEARLESCENT_HANGING_MESOGLEA, MesogleaDripParticle.PPMesogleaHangProvider::new);
		particleRegistry.register(WWParticleTypes.PURPLE_PEARLESCENT_FALLING_MESOGLEA, MesogleaDripParticle.PPMesogleaFallProvider::new);
		particleRegistry.register(WWParticleTypes.PURPLE_PEARLESCENT_LANDING_MESOGLEA, MesogleaDripParticle.PPMesogleaLandProvider::new);
		particleRegistry.register(
			WWParticleTypes.PURPLE_PEARLESCENT_MESOGLEA_BUBBLE,
			(spriteProvider) -> new MesogleaBubbleParticle.Provider(spriteProvider, WWParticleTypes.PURPLE_PEARLESCENT_MESOGLEA_BUBBLE_POP)
		);
		particleRegistry.register(
			WWParticleTypes.PURPLE_PEARLESCENT_MESOGLEA_BUBBLE_COLUMN_UP,
			(spriteProvider) -> new MesogleaBubbleColumnUpParticle.Provider(spriteProvider, WWParticleTypes.PURPLE_PEARLESCENT_MESOGLEA_BUBBLE_POP)
		);
		particleRegistry.register(
			WWParticleTypes.PURPLE_PEARLESCENT_MESOGLEA_CURRENT_DOWN,
			(spriteProvider) -> new MesogleaCurrentDownParticle.Provider(spriteProvider, WWParticleTypes.PURPLE_PEARLESCENT_MESOGLEA_BUBBLE_POP)
		);
		particleRegistry.register(WWParticleTypes.PURPLE_PEARLESCENT_MESOGLEA_BUBBLE_POP, MesogleaBubblePopParticle.Provider::new);
		particleRegistry.register(WWParticleTypes.PURPLE_PEARLESCENT_MESOGLEA_SPLASH, MesogleaSplashParticle.Provider::new);
		particleRegistry.register(WWParticleTypes.BLUE_HANGING_MESOGLEA, MesogleaDripParticle.BMesogleaHangProvider::new);
		particleRegistry.register(WWParticleTypes.BLUE_FALLING_MESOGLEA, MesogleaDripParticle.BMesogleaFallProvider::new);
		particleRegistry.register(WWParticleTypes.BLUE_LANDING_MESOGLEA, MesogleaDripParticle.BMesogleaLandProvider::new);
		particleRegistry.register(
			WWParticleTypes.BLUE_MESOGLEA_BUBBLE,
			(spriteProvider) -> new MesogleaBubbleParticle.Provider(spriteProvider, WWParticleTypes.BLUE_MESOGLEA_BUBBLE_POP)
		);
		particleRegistry.register(
			WWParticleTypes.BLUE_MESOGLEA_BUBBLE_COLUMN_UP,
			(spriteProvider) -> new MesogleaBubbleColumnUpParticle.Provider(spriteProvider, WWParticleTypes.BLUE_MESOGLEA_BUBBLE_POP)
		);
		particleRegistry.register(
			WWParticleTypes.BLUE_MESOGLEA_CURRENT_DOWN,
			(spriteProvider) -> new MesogleaCurrentDownParticle.Provider(spriteProvider, WWParticleTypes.BLUE_MESOGLEA_BUBBLE_POP)
		);
		particleRegistry.register(WWParticleTypes.BLUE_MESOGLEA_BUBBLE_POP, MesogleaBubblePopParticle.Provider::new);
		particleRegistry.register(WWParticleTypes.BLUE_MESOGLEA_SPLASH, MesogleaSplashParticle.Provider::new);
		particleRegistry.register(WWParticleTypes.YELLOW_HANGING_MESOGLEA, MesogleaDripParticle.YMesogleaHangProvider::new);
		particleRegistry.register(WWParticleTypes.YELLOW_FALLING_MESOGLEA, MesogleaDripParticle.YMesogleaFallProvider::new);
		particleRegistry.register(WWParticleTypes.YELLOW_LANDING_MESOGLEA, MesogleaDripParticle.YMesogleaLandProvider::new);
		particleRegistry.register(
			WWParticleTypes.YELLOW_MESOGLEA_BUBBLE,
			(spriteProvider) -> new MesogleaBubbleParticle.Provider(spriteProvider, WWParticleTypes.YELLOW_MESOGLEA_BUBBLE_POP)
		);
		particleRegistry.register(
			WWParticleTypes.YELLOW_MESOGLEA_BUBBLE_COLUMN_UP,
			(spriteProvider) -> new MesogleaBubbleColumnUpParticle.Provider(spriteProvider, WWParticleTypes.YELLOW_MESOGLEA_BUBBLE_POP)
		);
		particleRegistry.register(
			WWParticleTypes.YELLOW_MESOGLEA_CURRENT_DOWN,
			(spriteProvider) -> new MesogleaCurrentDownParticle.Provider(spriteProvider, WWParticleTypes.YELLOW_MESOGLEA_BUBBLE_POP)
		);
		particleRegistry.register(WWParticleTypes.YELLOW_MESOGLEA_BUBBLE_POP, MesogleaBubblePopParticle.Provider::new);
		particleRegistry.register(WWParticleTypes.YELLOW_MESOGLEA_SPLASH, MesogleaSplashParticle.Provider::new);
		particleRegistry.register(WWParticleTypes.LIME_HANGING_MESOGLEA, MesogleaDripParticle.LMesogleaHangProvider::new);
		particleRegistry.register(WWParticleTypes.LIME_FALLING_MESOGLEA, MesogleaDripParticle.LMesogleaFallProvider::new);
		particleRegistry.register(WWParticleTypes.LIME_LANDING_MESOGLEA, MesogleaDripParticle.LMesogleaLandProvider::new);
		particleRegistry.register(
			WWParticleTypes.LIME_MESOGLEA_BUBBLE,
			(spriteProvider) -> new MesogleaBubbleParticle.Provider(spriteProvider, WWParticleTypes.LIME_MESOGLEA_BUBBLE_POP)
		);
		particleRegistry.register(
			WWParticleTypes.LIME_MESOGLEA_BUBBLE_COLUMN_UP,
			(spriteProvider) -> new MesogleaBubbleColumnUpParticle.Provider(spriteProvider, WWParticleTypes.LIME_MESOGLEA_BUBBLE_POP)
		);
		particleRegistry.register(
			WWParticleTypes.LIME_MESOGLEA_CURRENT_DOWN,
			(spriteProvider) -> new MesogleaCurrentDownParticle.Provider(spriteProvider, WWParticleTypes.LIME_MESOGLEA_BUBBLE_POP)
		);
		particleRegistry.register(WWParticleTypes.LIME_MESOGLEA_BUBBLE_POP, MesogleaBubblePopParticle.Provider::new);
		particleRegistry.register(WWParticleTypes.LIME_MESOGLEA_SPLASH, MesogleaSplashParticle.Provider::new);
		particleRegistry.register(WWParticleTypes.PINK_HANGING_MESOGLEA, MesogleaDripParticle.PMesogleaHangProvider::new);
		particleRegistry.register(WWParticleTypes.PINK_FALLING_MESOGLEA, MesogleaDripParticle.PMesogleaFallProvider::new);
		particleRegistry.register(WWParticleTypes.PINK_LANDING_MESOGLEA, MesogleaDripParticle.PMesogleaLandProvider::new);
		particleRegistry.register(
			WWParticleTypes.PINK_MESOGLEA_BUBBLE,
			(spriteProvider) -> new MesogleaBubbleParticle.Provider(spriteProvider, WWParticleTypes.PINK_MESOGLEA_BUBBLE_POP)
		);
		particleRegistry.register(
			WWParticleTypes.PINK_MESOGLEA_BUBBLE_COLUMN_UP,
			(spriteProvider) -> new MesogleaBubbleColumnUpParticle.Provider(spriteProvider, WWParticleTypes.PINK_MESOGLEA_BUBBLE_POP)
		);
		particleRegistry.register(
			WWParticleTypes.PINK_MESOGLEA_CURRENT_DOWN,
			(spriteProvider) -> new MesogleaCurrentDownParticle.Provider(spriteProvider, WWParticleTypes.PINK_MESOGLEA_BUBBLE_POP)
		);
		particleRegistry.register(WWParticleTypes.PINK_MESOGLEA_BUBBLE_POP, MesogleaBubblePopParticle.Provider::new);
		particleRegistry.register(WWParticleTypes.PINK_MESOGLEA_SPLASH, MesogleaSplashParticle.Provider::new);
		particleRegistry.register(WWParticleTypes.RED_HANGING_MESOGLEA, MesogleaDripParticle.RMesogleaHangProvider::new);
		particleRegistry.register(WWParticleTypes.RED_FALLING_MESOGLEA, MesogleaDripParticle.RMesogleaFallProvider::new);
		particleRegistry.register(WWParticleTypes.RED_LANDING_MESOGLEA, MesogleaDripParticle.RMesogleaLandProvider::new);
		particleRegistry.register(
			WWParticleTypes.RED_MESOGLEA_BUBBLE,
			(spriteProvider) -> new MesogleaBubbleParticle.Provider(spriteProvider, WWParticleTypes.RED_MESOGLEA_BUBBLE_POP)
		);
		particleRegistry.register(
			WWParticleTypes.RED_MESOGLEA_BUBBLE_COLUMN_UP,
			(spriteProvider) -> new MesogleaBubbleColumnUpParticle.Provider(spriteProvider, WWParticleTypes.RED_MESOGLEA_BUBBLE_POP)
		);
		particleRegistry.register(
			WWParticleTypes.RED_MESOGLEA_CURRENT_DOWN,
			(spriteProvider) -> new MesogleaCurrentDownParticle.Provider(spriteProvider, WWParticleTypes.RED_MESOGLEA_BUBBLE_POP)
		);
		particleRegistry.register(WWParticleTypes.RED_MESOGLEA_BUBBLE_POP, MesogleaBubblePopParticle.Provider::new);
		particleRegistry.register(WWParticleTypes.RED_MESOGLEA_SPLASH, MesogleaSplashParticle.Provider::new);
	}
}
