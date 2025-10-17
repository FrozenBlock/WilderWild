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
import net.frozenblock.wilderwild.particle.MesogleaBubbleColumnUpParticle;
import net.frozenblock.wilderwild.particle.MesogleaBubbleParticle;
import net.frozenblock.wilderwild.particle.MesogleaBubblePopParticle;
import net.frozenblock.wilderwild.particle.MesogleaCurrentDownParticle;
import net.frozenblock.wilderwild.particle.MesogleaDripParticle;
import net.frozenblock.wilderwild.particle.MesogleaSplashParticle;
import net.frozenblock.wilderwild.particle.PaleFogParticle;
import net.frozenblock.wilderwild.particle.PlanktonParticle;
import net.frozenblock.wilderwild.particle.PollenParticle;
import net.frozenblock.wilderwild.particle.SeedParticle;
import net.frozenblock.wilderwild.particle.ShriekerBubbleSeedParticle;
import net.frozenblock.wilderwild.particle.TermiteParticle;
import net.frozenblock.wilderwild.particle.WWFallingLeavesParticle;
import net.frozenblock.wilderwild.particle.provider.WWParticleProviders;
import net.frozenblock.wilderwild.registry.WWParticleTypes;

@Environment(EnvType.CLIENT)
public final class WWParticleEngine {

	public static void init() {
		ParticleFactoryRegistry particleRegistry = ParticleFactoryRegistry.getInstance();

		particleRegistry.register(WWParticleTypes.LEAF_CLUSTER_SPAWNER, LeafClusterSeedParticle.Provider::new);
		particleRegistry.register(WWParticleTypes.OAK_LEAVES, WWFallingLeavesParticle.Provider::new);
		particleRegistry.register(WWParticleTypes.OAK_LITTER_LEAVES, WWFallingLeavesParticle.Provider::new);
		particleRegistry.register(WWParticleTypes.SPRUCE_LEAVES, WWFallingLeavesParticle.Provider::new);
		particleRegistry.register(WWParticleTypes.SPRUCE_LITTER_LEAVES, WWFallingLeavesParticle.Provider::new);
		particleRegistry.register(WWParticleTypes.BIRCH_LEAVES, WWFallingLeavesParticle.Provider::new);
		particleRegistry.register(WWParticleTypes.BIRCH_LITTER_LEAVES, WWFallingLeavesParticle.Provider::new);
		particleRegistry.register(WWParticleTypes.JUNGLE_LEAVES, WWFallingLeavesParticle.Provider::new);
		particleRegistry.register(WWParticleTypes.JUNGLE_LITTER_LEAVES, WWFallingLeavesParticle.Provider::new);
		particleRegistry.register(WWParticleTypes.ACACIA_LEAVES, WWFallingLeavesParticle.Provider::new);
		particleRegistry.register(WWParticleTypes.ACACIA_LITTER_LEAVES, WWFallingLeavesParticle.Provider::new);
		particleRegistry.register(WWParticleTypes.DARK_OAK_LEAVES, WWFallingLeavesParticle.Provider::new);
		particleRegistry.register(WWParticleTypes.DARK_OAK_LITTER_LEAVES, WWFallingLeavesParticle.Provider::new);
		particleRegistry.register(WWParticleTypes.PALE_OAK_LEAVES, WWFallingLeavesParticle.Provider::new);
		particleRegistry.register(WWParticleTypes.PALE_OAK_LITTER_LEAVES, WWFallingLeavesParticle.Provider::new);
		particleRegistry.register(WWParticleTypes.MANGROVE_LEAVES, WWFallingLeavesParticle.Provider::new);
		particleRegistry.register(WWParticleTypes.MANGROVE_LITTER_LEAVES, WWFallingLeavesParticle.Provider::new);
		particleRegistry.register(WWParticleTypes.CHERRY_LEAVES, WWFallingLeavesParticle.Provider::new);
		particleRegistry.register(WWParticleTypes.CHERRY_LITTER_LEAVES, WWFallingLeavesParticle.Provider::new);
		particleRegistry.register(WWParticleTypes.AZALEA_LEAVES, WWFallingLeavesParticle.Provider::new);
		particleRegistry.register(WWParticleTypes.FLOWERING_AZALEA_LEAVES, WWFallingLeavesParticle.Provider::new);
		particleRegistry.register(WWParticleTypes.AZALEA_LITTER_LEAVES, WWFallingLeavesParticle.Provider::new);
		particleRegistry.register(WWParticleTypes.BAOBAB_LEAVES, WWFallingLeavesParticle.Provider::new);
		particleRegistry.register(WWParticleTypes.BAOBAB_LITTER_LEAVES, WWFallingLeavesParticle.Provider::new);
		particleRegistry.register(WWParticleTypes.CYPRESS_LEAVES, WWFallingLeavesParticle.Provider::new);
		particleRegistry.register(WWParticleTypes.CYPRESS_LITTER_LEAVES, WWFallingLeavesParticle.Provider::new);
		particleRegistry.register(WWParticleTypes.PALM_FRONDS, WWFallingLeavesParticle.Provider::new);
		particleRegistry.register(WWParticleTypes.PALM_LITTER_FRONDS, WWFallingLeavesParticle.Provider::new);
		particleRegistry.register(WWParticleTypes.YELLOW_MAPLE_LEAVES, WWFallingLeavesParticle.Provider::new);
		particleRegistry.register(WWParticleTypes.ORANGE_MAPLE_LEAVES, WWFallingLeavesParticle.Provider::new);
		particleRegistry.register(WWParticleTypes.RED_MAPLE_LEAVES, WWFallingLeavesParticle.Provider::new);
		particleRegistry.register(WWParticleTypes.WILLOW_LEAVES, WWFallingLeavesParticle.Provider::new);
		particleRegistry.register(WWParticleTypes.WILLOW_LITTER_LEAVES, WWFallingLeavesParticle.Provider::new);

		particleRegistry.register(WWParticleTypes.CHEST_BUBBLE_SPAWNER, ChestBubbleSeedParticle.Provider::new);
		particleRegistry.register(WWParticleTypes.SHRIEKER_BUBBLE_SPAWNER, ShriekerBubbleSeedParticle.Provider::new);
		particleRegistry.register(WWParticleTypes.POLLEN, PollenParticle.Provider::new);
		particleRegistry.register(WWParticleTypes.SEED, SeedParticle.Provider::new);
		particleRegistry.register(WWParticleTypes.PALE_FOG, PaleFogParticle.LargeFactory::new);
		particleRegistry.register(WWParticleTypes.PALE_FOG_SMALL, PaleFogParticle.SmallFactory::new);
		particleRegistry.register(WWParticleTypes.PALE_SPORE, PollenParticle.PaleSporeFactory::new);
		particleRegistry.register(WWParticleTypes.FLOATING_SCULK_BUBBLE, FloatingSculkBubbleParticle.Provider::new);
		particleRegistry.register(WWParticleTypes.TERMITE, TermiteParticle.Provider::new);
		particleRegistry.register(WWParticleTypes.COCONUT_SPLASH, FallingParticle.Provider::new);
		particleRegistry.register(WWParticleTypes.SCORCHING_FLAME, WWParticleProviders.ScorchingEffectFlameProvider::new);
		particleRegistry.register(WWParticleTypes.UNDERWATER_ASH, WWParticleProviders.UnderwaterAshProvider::new);

		particleRegistry.register(WWParticleTypes.PLANKTON, PlanktonParticle.PlanktonProvider::new);
		particleRegistry.register(WWParticleTypes.GLOWING_PLANKTON, PlanktonParticle.GlowingProvider::new);

		particleRegistry.register(WWParticleTypes.HANGING_MESOGLEA_PEARLESCENT_BLUE, MesogleaDripParticle.PearlescentBlueHangProvider::new);
		particleRegistry.register(WWParticleTypes.FALLING_MESOGLEA_PEARLESCENT_BLUE, MesogleaDripParticle.PearlescentBlueFallProvider::new);
		particleRegistry.register(WWParticleTypes.LANDING_MESOGLEA_PEARLESCENT_BLUE, MesogleaDripParticle.LandProvider::new);
		particleRegistry.register(
			WWParticleTypes.MESOGLEA_BUBBLE_PEARLESCENT_BLUE,
			(spriteProvider) -> new MesogleaBubbleParticle.Provider(spriteProvider, WWParticleTypes.MESOGLEA_BUBBLE_POP_PEARLESCENT_BLUE)
		);
		particleRegistry.register(
			WWParticleTypes.BUBBLE_COLUMN_UP_MESOGLEA_PEARLESCENT_BLUE,
			(spriteProvider) -> new MesogleaBubbleColumnUpParticle.Provider(spriteProvider, WWParticleTypes.MESOGLEA_BUBBLE_POP_PEARLESCENT_BLUE)
		);
		particleRegistry.register(
			WWParticleTypes.CURRENT_DOWN_MESOGLEA_PEARLESCENT_BLUE,
			(spriteProvider) -> new MesogleaCurrentDownParticle.Provider(spriteProvider, WWParticleTypes.MESOGLEA_BUBBLE_POP_PEARLESCENT_BLUE)
		);
		particleRegistry.register(WWParticleTypes.MESOGLEA_BUBBLE_POP_PEARLESCENT_BLUE, MesogleaBubblePopParticle.Provider::new);
		particleRegistry.register(WWParticleTypes.MESOGLEA_SPLASH_PEARLESCENT_BLUE, MesogleaSplashParticle.Provider::new);

		particleRegistry.register(WWParticleTypes.HANGING_MESOGLEA_PEARLESCENT_PURPLE, MesogleaDripParticle.PearlescentPurpleHangProvider::new);
		particleRegistry.register(WWParticleTypes.FALLING_MESOGLEA_PEARLESCENT_PURPLE, MesogleaDripParticle.PearlescentPurpleFallProvider::new);
		particleRegistry.register(WWParticleTypes.LANDING_MESOGLEA_PEARLESCENT_PURPLE, MesogleaDripParticle.LandProvider::new);
		particleRegistry.register(
			WWParticleTypes.MESOGLEA_BUBBLE_PEARLESCENT_PURPLE,
			(spriteProvider) -> new MesogleaBubbleParticle.Provider(spriteProvider, WWParticleTypes.MESOGLEA_BUBBLE_POP_PEARLESCENT_PURPLE)
		);
		particleRegistry.register(
			WWParticleTypes.BUBBLE_COLUMN_UP_MESOGLEA_PEARLESCENT_PURPLE,
			(spriteProvider) -> new MesogleaBubbleColumnUpParticle.Provider(spriteProvider, WWParticleTypes.MESOGLEA_BUBBLE_POP_PEARLESCENT_PURPLE)
		);
		particleRegistry.register(
			WWParticleTypes.CURRENT_DOWN_MESOGLEA_PEARLESCENT_PURPLE,
			(spriteProvider) -> new MesogleaCurrentDownParticle.Provider(spriteProvider, WWParticleTypes.MESOGLEA_BUBBLE_POP_PEARLESCENT_PURPLE)
		);
		particleRegistry.register(WWParticleTypes.MESOGLEA_BUBBLE_POP_PEARLESCENT_PURPLE, MesogleaBubblePopParticle.Provider::new);
		particleRegistry.register(WWParticleTypes.MESOGLEA_SPLASH_PEARLESCENT_PURPLE, MesogleaSplashParticle.Provider::new);

		particleRegistry.register(WWParticleTypes.HANGING_MESOGLEA_BLUE, MesogleaDripParticle.BlueHangProvider::new);
		particleRegistry.register(WWParticleTypes.FALLING_MESOGLEA_BLUE, MesogleaDripParticle.BlueFallProvider::new);
		particleRegistry.register(WWParticleTypes.LANDING_MESOGLEA_BLUE, MesogleaDripParticle.LandProvider::new);
		particleRegistry.register(
			WWParticleTypes.MESOGLEA_BUBBLE_BLUE,
			(spriteProvider) -> new MesogleaBubbleParticle.Provider(spriteProvider, WWParticleTypes.MESOGLEA_BUBBLE_POP_BLUE)
		);
		particleRegistry.register(
			WWParticleTypes.BUBBLE_COLUMN_UP_MESOGLEA_BLUE,
			(spriteProvider) -> new MesogleaBubbleColumnUpParticle.Provider(spriteProvider, WWParticleTypes.MESOGLEA_BUBBLE_POP_BLUE)
		);
		particleRegistry.register(
			WWParticleTypes.CURRENT_DOWN_MESOGLEA_BLUE,
			(spriteProvider) -> new MesogleaCurrentDownParticle.Provider(spriteProvider, WWParticleTypes.MESOGLEA_BUBBLE_POP_BLUE)
		);
		particleRegistry.register(WWParticleTypes.MESOGLEA_BUBBLE_POP_BLUE, MesogleaBubblePopParticle.Provider::new);
		particleRegistry.register(WWParticleTypes.MESOGLEA_SPLASH_BLUE, MesogleaSplashParticle.Provider::new);

		particleRegistry.register(WWParticleTypes.HANGING_MESOGLEA_YELLOW, MesogleaDripParticle.YellowHangProvider::new);
		particleRegistry.register(WWParticleTypes.FALLING_MESOGLEA_YELLOW, MesogleaDripParticle.YellowFallProvider::new);
		particleRegistry.register(WWParticleTypes.LANDING_MESOGLEA_YELLOW, MesogleaDripParticle.LandProvider::new);
		particleRegistry.register(
			WWParticleTypes.MESOGLEA_BUBBLE_YELLOW,
			(spriteProvider) -> new MesogleaBubbleParticle.Provider(spriteProvider, WWParticleTypes.MESOGLEA_BUBBLE_POP_YELLOW)
		);
		particleRegistry.register(
			WWParticleTypes.BUBBLE_COLUMN_UP_MESOGLEA_YELLOW,
			(spriteProvider) -> new MesogleaBubbleColumnUpParticle.Provider(spriteProvider, WWParticleTypes.MESOGLEA_BUBBLE_POP_YELLOW)
		);
		particleRegistry.register(
			WWParticleTypes.CURRENT_DOWN_MESOGLEA_YELLOW,
			(spriteProvider) -> new MesogleaCurrentDownParticle.Provider(spriteProvider, WWParticleTypes.MESOGLEA_BUBBLE_POP_YELLOW)
		);
		particleRegistry.register(WWParticleTypes.MESOGLEA_BUBBLE_POP_YELLOW, MesogleaBubblePopParticle.Provider::new);
		particleRegistry.register(WWParticleTypes.MESOGLEA_SPLASH_YELLOW, MesogleaSplashParticle.Provider::new);

		particleRegistry.register(WWParticleTypes.HANGING_MESOGLEA_LIME, MesogleaDripParticle.LimeHangProvider::new);
		particleRegistry.register(WWParticleTypes.FALLING_MESOGLEA_LIME, MesogleaDripParticle.LimeFallProvider::new);
		particleRegistry.register(WWParticleTypes.LANDING_MESOGLEA_LIME, MesogleaDripParticle.LandProvider::new);
		particleRegistry.register(
			WWParticleTypes.MESOGLEA_BUBBLE_LIME,
			(spriteProvider) -> new MesogleaBubbleParticle.Provider(spriteProvider, WWParticleTypes.MESOGLEA_BUBBLE_POP_LIME)
		);
		particleRegistry.register(
			WWParticleTypes.BUBBLE_COLUMN_UP_MESOGLEA_LIME,
			(spriteProvider) -> new MesogleaBubbleColumnUpParticle.Provider(spriteProvider, WWParticleTypes.MESOGLEA_BUBBLE_POP_LIME)
		);
		particleRegistry.register(
			WWParticleTypes.CURRENT_DOWN_MESOGLEA_LIME,
			(spriteProvider) -> new MesogleaCurrentDownParticle.Provider(spriteProvider, WWParticleTypes.MESOGLEA_BUBBLE_POP_LIME)
		);
		particleRegistry.register(WWParticleTypes.MESOGLEA_BUBBLE_POP_LIME, MesogleaBubblePopParticle.Provider::new);
		particleRegistry.register(WWParticleTypes.MESOGLEA_SPLASH_LIME, MesogleaSplashParticle.Provider::new);

		particleRegistry.register(WWParticleTypes.HANGING_MESOGLEA_PINK, MesogleaDripParticle.PinkHangProvider::new);
		particleRegistry.register(WWParticleTypes.FALLING_MESOGLEA_PINK, MesogleaDripParticle.PinkFallProvider::new);
		particleRegistry.register(WWParticleTypes.LANDING_MESOGLEA_PINK, MesogleaDripParticle.LandProvider::new);
		particleRegistry.register(
			WWParticleTypes.MESOGLEA_BUBBLE_PINK,
			(spriteProvider) -> new MesogleaBubbleParticle.Provider(spriteProvider, WWParticleTypes.MESOGLEA_BUBBLE_POP_PINK)
		);
		particleRegistry.register(
			WWParticleTypes.BUBBLE_COLUMN_UP_MESOGLEA_PINK,
			(spriteProvider) -> new MesogleaBubbleColumnUpParticle.Provider(spriteProvider, WWParticleTypes.MESOGLEA_BUBBLE_POP_PINK)
		);
		particleRegistry.register(
			WWParticleTypes.CURRENT_DOWN_MESOGLEA_PINK,
			(spriteProvider) -> new MesogleaCurrentDownParticle.Provider(spriteProvider, WWParticleTypes.MESOGLEA_BUBBLE_POP_PINK)
		);
		particleRegistry.register(WWParticleTypes.MESOGLEA_BUBBLE_POP_PINK, MesogleaBubblePopParticle.Provider::new);
		particleRegistry.register(WWParticleTypes.MESOGLEA_SPLASH_PINK, MesogleaSplashParticle.Provider::new);

		particleRegistry.register(WWParticleTypes.HANGING_MESOGLEA_RED, MesogleaDripParticle.RedHangProvider::new);
		particleRegistry.register(WWParticleTypes.FALLING_MESOGLEA_RED, MesogleaDripParticle.RedFallProvider::new);
		particleRegistry.register(WWParticleTypes.LANDING_MESOGLEA_RED, MesogleaDripParticle.LandProvider::new);
		particleRegistry.register(
			WWParticleTypes.MESOGLEA_BUBBLE_RED,
			(spriteProvider) -> new MesogleaBubbleParticle.Provider(spriteProvider, WWParticleTypes.MESOGLEA_BUBBLE_POP_RED)
		);
		particleRegistry.register(
			WWParticleTypes.BUBBLE_COLUMN_UP_MESOGLEA_RED,
			(spriteProvider) -> new MesogleaBubbleColumnUpParticle.Provider(spriteProvider, WWParticleTypes.MESOGLEA_BUBBLE_POP_RED)
		);
		particleRegistry.register(
			WWParticleTypes.CURRENT_DOWN_MESOGLEA_RED,
			(spriteProvider) -> new MesogleaCurrentDownParticle.Provider(spriteProvider, WWParticleTypes.MESOGLEA_BUBBLE_POP_RED)
		);
		particleRegistry.register(WWParticleTypes.MESOGLEA_BUBBLE_POP_RED, MesogleaBubblePopParticle.Provider::new);
		particleRegistry.register(WWParticleTypes.MESOGLEA_SPLASH_RED, MesogleaSplashParticle.Provider::new);
	}
}
