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

package net.frozenblock.wilderwild.client;

import net.frozenblock.lib.particle.client.api.ParticleProviderRegistry;
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
import net.frozenblock.wilderwild.particle.WindClusterSeedParticle;
import net.frozenblock.wilderwild.particle.provider.WWParticleProviders;
import net.frozenblock.wilderwild.registry.WWParticleTypes;
import net.mehvahdjukaar.candlelight.api.ClientOnly;

@ClientOnly
public final class WWParticleEngine {

	public static void init() {
		ParticleProviderRegistry.register(WWParticleTypes.LEAF_CLUSTER_SPAWNER::get, LeafClusterSeedParticle.Provider::new);
		ParticleProviderRegistry.register(WWParticleTypes.OAK_LEAVES::get, WWFallingLeavesParticle.Provider::new);
		ParticleProviderRegistry.register(WWParticleTypes.OAK_LITTER_LEAVES::get, WWFallingLeavesParticle.Provider::new);
		ParticleProviderRegistry.register(WWParticleTypes.SPRUCE_LEAVES::get, WWFallingLeavesParticle.Provider::new);
		ParticleProviderRegistry.register(WWParticleTypes.SPRUCE_LITTER_LEAVES::get, WWFallingLeavesParticle.Provider::new);
		ParticleProviderRegistry.register(WWParticleTypes.BIRCH_LEAVES::get, WWFallingLeavesParticle.Provider::new);
		ParticleProviderRegistry.register(WWParticleTypes.BIRCH_LITTER_LEAVES::get, WWFallingLeavesParticle.Provider::new);
		ParticleProviderRegistry.register(WWParticleTypes.JUNGLE_LEAVES::get, WWFallingLeavesParticle.Provider::new);
		ParticleProviderRegistry.register(WWParticleTypes.JUNGLE_LITTER_LEAVES::get, WWFallingLeavesParticle.Provider::new);
		ParticleProviderRegistry.register(WWParticleTypes.ACACIA_LEAVES, WWFallingLeavesParticle.Provider::new);
		ParticleProviderRegistry.register(WWParticleTypes.ACACIA_LITTER_LEAVES::get, WWFallingLeavesParticle.Provider::new);
		ParticleProviderRegistry.register(WWParticleTypes.DARK_OAK_LEAVES::get, WWFallingLeavesParticle.Provider::new);
		ParticleProviderRegistry.register(WWParticleTypes.DARK_OAK_LITTER_LEAVES::get, WWFallingLeavesParticle.Provider::new);
		ParticleProviderRegistry.register(WWParticleTypes.PALE_OAK_LEAVES::get, WWFallingLeavesParticle.Provider::new);
		ParticleProviderRegistry.register(WWParticleTypes.PALE_OAK_LITTER_LEAVES::get, WWFallingLeavesParticle.Provider::new);
		ParticleProviderRegistry.register(WWParticleTypes.MANGROVE_LEAVES::get, WWFallingLeavesParticle.Provider::new);
		ParticleProviderRegistry.register(WWParticleTypes.MANGROVE_LITTER_LEAVES::get, WWFallingLeavesParticle.Provider::new);
		ParticleProviderRegistry.register(WWParticleTypes.CHERRY_LEAVES::get, WWFallingLeavesParticle.Provider::new);
		ParticleProviderRegistry.register(WWParticleTypes.CHERRY_LITTER_LEAVES::get, WWFallingLeavesParticle.Provider::new);
		ParticleProviderRegistry.register(WWParticleTypes.AZALEA_LEAVES::get, WWFallingLeavesParticle.Provider::new);
		ParticleProviderRegistry.register(WWParticleTypes.FLOWERING_AZALEA_LEAVES::get, WWFallingLeavesParticle.Provider::new);
		ParticleProviderRegistry.register(WWParticleTypes.AZALEA_LITTER_LEAVES::get, WWFallingLeavesParticle.Provider::new);
		ParticleProviderRegistry.register(WWParticleTypes.BAOBAB_LEAVES::get, WWFallingLeavesParticle.Provider::new);
		ParticleProviderRegistry.register(WWParticleTypes.BAOBAB_LITTER_LEAVES::get, WWFallingLeavesParticle.Provider::new);
		ParticleProviderRegistry.register(WWParticleTypes.CYPRESS_LEAVES::get, WWFallingLeavesParticle.Provider::new);
		ParticleProviderRegistry.register(WWParticleTypes.CYPRESS_LITTER_LEAVES::get, WWFallingLeavesParticle.Provider::new);
		ParticleProviderRegistry.register(WWParticleTypes.PALM_FRONDS::get, WWFallingLeavesParticle.Provider::new);
		ParticleProviderRegistry.register(WWParticleTypes.PALM_LITTER_FRONDS::get, WWFallingLeavesParticle.Provider::new);
		ParticleProviderRegistry.register(WWParticleTypes.YELLOW_MAPLE_LEAVES::get, WWFallingLeavesParticle.Provider::new);
		ParticleProviderRegistry.register(WWParticleTypes.ORANGE_MAPLE_LEAVES::get, WWFallingLeavesParticle.Provider::new);
		ParticleProviderRegistry.register(WWParticleTypes.RED_MAPLE_LEAVES::get, WWFallingLeavesParticle.Provider::new);
		ParticleProviderRegistry.register(WWParticleTypes.WILLOW_LEAVES::get, WWFallingLeavesParticle.Provider::new);
		ParticleProviderRegistry.register(WWParticleTypes.WILLOW_LITTER_LEAVES::get, WWFallingLeavesParticle.Provider::new);

		ParticleProviderRegistry.register(WWParticleTypes.CHEST_BUBBLE_SPAWNER::get, ChestBubbleSeedParticle.Provider::new);
		ParticleProviderRegistry.register(WWParticleTypes.SHRIEKER_BUBBLE_SPAWNER::get, ShriekerBubbleSeedParticle.Provider::new);
		ParticleProviderRegistry.register(WWParticleTypes.WIND_CLUSTER::get, WindClusterSeedParticle.Provider::new);
		ParticleProviderRegistry.register(WWParticleTypes.POLLEN::get, PollenParticle.Provider::new);
		ParticleProviderRegistry.register(WWParticleTypes.SEED::get, SeedParticle.Provider::new);
		ParticleProviderRegistry.register(WWParticleTypes.PALE_FOG::get, PaleFogParticle.LargeFactory::new);
		ParticleProviderRegistry.register(WWParticleTypes.PALE_FOG_SMALL::get, PaleFogParticle.SmallFactory::new);
		ParticleProviderRegistry.register(WWParticleTypes.PALE_SPORE::get, PollenParticle.PaleSporeFactory::new);
		ParticleProviderRegistry.register(WWParticleTypes.FLOATING_SCULK_BUBBLE::get, FloatingSculkBubbleParticle.Provider::new);
		ParticleProviderRegistry.register(WWParticleTypes.TERMITE::get, TermiteParticle.Provider::new);
		ParticleProviderRegistry.register(WWParticleTypes.COCONUT_SPLASH::get, FallingParticle.Provider::new);
		ParticleProviderRegistry.register(WWParticleTypes.SCORCHING_FLAME::get, WWParticleProviders.ScorchingEffectFlameProvider::new);
		ParticleProviderRegistry.register(WWParticleTypes.UNDERWATER_ASH::get, WWParticleProviders.UnderwaterAshProvider::new);

		ParticleProviderRegistry.register(WWParticleTypes.PLANKTON::get, PlanktonParticle.PlanktonProvider::new);
		ParticleProviderRegistry.register(WWParticleTypes.GLOWING_PLANKTON::get, PlanktonParticle.GlowingProvider::new);

		ParticleProviderRegistry.register(WWParticleTypes.HANGING_MESOGLEA_PEARLESCENT_BLUE::get, MesogleaDripParticle.PearlescentBlueHangProvider::new);
		ParticleProviderRegistry.register(WWParticleTypes.FALLING_MESOGLEA_PEARLESCENT_BLUE::get, MesogleaDripParticle.PearlescentBlueFallProvider::new);
		ParticleProviderRegistry.register(WWParticleTypes.LANDING_MESOGLEA_PEARLESCENT_BLUE::get, MesogleaDripParticle.LandProvider::new);
		ParticleProviderRegistry.register(
			WWParticleTypes.MESOGLEA_BUBBLE_PEARLESCENT_BLUE::get,
			(spriteProvider) -> new MesogleaBubbleParticle.Provider(spriteProvider, WWParticleTypes.MESOGLEA_BUBBLE_POP_PEARLESCENT_BLUE.get())
		);
		ParticleProviderRegistry.register(
			WWParticleTypes.BUBBLE_COLUMN_UP_MESOGLEA_PEARLESCENT_BLUE::get,
			(spriteProvider) -> new MesogleaBubbleColumnUpParticle.Provider(spriteProvider, WWParticleTypes.MESOGLEA_BUBBLE_POP_PEARLESCENT_BLUE.get())
		);
		ParticleProviderRegistry.register(
			WWParticleTypes.CURRENT_DOWN_MESOGLEA_PEARLESCENT_BLUE::get,
			(spriteProvider) -> new MesogleaCurrentDownParticle.Provider(spriteProvider, WWParticleTypes.MESOGLEA_BUBBLE_POP_PEARLESCENT_BLUE.get())
		);
		ParticleProviderRegistry.register(WWParticleTypes.MESOGLEA_BUBBLE_POP_PEARLESCENT_BLUE::get, MesogleaBubblePopParticle.Provider::new);
		ParticleProviderRegistry.register(WWParticleTypes.MESOGLEA_SPLASH_PEARLESCENT_BLUE::get, MesogleaSplashParticle.Provider::new);

		ParticleProviderRegistry.register(WWParticleTypes.HANGING_MESOGLEA_PEARLESCENT_PURPLE::get, MesogleaDripParticle.PearlescentPurpleHangProvider::new);
		ParticleProviderRegistry.register(WWParticleTypes.FALLING_MESOGLEA_PEARLESCENT_PURPLE::get, MesogleaDripParticle.PearlescentPurpleFallProvider::new);
		ParticleProviderRegistry.register(WWParticleTypes.LANDING_MESOGLEA_PEARLESCENT_PURPLE::get, MesogleaDripParticle.LandProvider::new);
		ParticleProviderRegistry.register(
			WWParticleTypes.MESOGLEA_BUBBLE_PEARLESCENT_PURPLE::get,
			(spriteProvider) -> new MesogleaBubbleParticle.Provider(spriteProvider, WWParticleTypes.MESOGLEA_BUBBLE_POP_PEARLESCENT_PURPLE.get())
		);
		ParticleProviderRegistry.register(
			WWParticleTypes.BUBBLE_COLUMN_UP_MESOGLEA_PEARLESCENT_PURPLE::get,
			(spriteProvider) -> new MesogleaBubbleColumnUpParticle.Provider(spriteProvider, WWParticleTypes.MESOGLEA_BUBBLE_POP_PEARLESCENT_PURPLE.get())
		);
		ParticleProviderRegistry.register(
			WWParticleTypes.CURRENT_DOWN_MESOGLEA_PEARLESCENT_PURPLE::get,
			(spriteProvider) -> new MesogleaCurrentDownParticle.Provider(spriteProvider, WWParticleTypes.MESOGLEA_BUBBLE_POP_PEARLESCENT_PURPLE.get())
		);
		ParticleProviderRegistry.register(WWParticleTypes.MESOGLEA_BUBBLE_POP_PEARLESCENT_PURPLE::get, MesogleaBubblePopParticle.Provider::new);
		ParticleProviderRegistry.register(WWParticleTypes.MESOGLEA_SPLASH_PEARLESCENT_PURPLE::get, MesogleaSplashParticle.Provider::new);

		ParticleProviderRegistry.register(WWParticleTypes.HANGING_MESOGLEA_BLUE::get, MesogleaDripParticle.BlueHangProvider::new);
		ParticleProviderRegistry.register(WWParticleTypes.FALLING_MESOGLEA_BLUE::get, MesogleaDripParticle.BlueFallProvider::new);
		ParticleProviderRegistry.register(WWParticleTypes.LANDING_MESOGLEA_BLUE::get, MesogleaDripParticle.LandProvider::new);
		ParticleProviderRegistry.register(
			WWParticleTypes.MESOGLEA_BUBBLE_BLUE::get,
			(spriteProvider) -> new MesogleaBubbleParticle.Provider(spriteProvider, WWParticleTypes.MESOGLEA_BUBBLE_POP_BLUE.get())
		);
		ParticleProviderRegistry.register(
			WWParticleTypes.BUBBLE_COLUMN_UP_MESOGLEA_BLUE::get,
			(spriteProvider) -> new MesogleaBubbleColumnUpParticle.Provider(spriteProvider, WWParticleTypes.MESOGLEA_BUBBLE_POP_BLUE.get())
		);
		ParticleProviderRegistry.register(
			WWParticleTypes.CURRENT_DOWN_MESOGLEA_BLUE::get,
			(spriteProvider) -> new MesogleaCurrentDownParticle.Provider(spriteProvider, WWParticleTypes.MESOGLEA_BUBBLE_POP_BLUE.get())
		);
		ParticleProviderRegistry.register(WWParticleTypes.MESOGLEA_BUBBLE_POP_BLUE::get, MesogleaBubblePopParticle.Provider::new);
		ParticleProviderRegistry.register(WWParticleTypes.MESOGLEA_SPLASH_BLUE::get, MesogleaSplashParticle.Provider::new);

		ParticleProviderRegistry.register(WWParticleTypes.HANGING_MESOGLEA_YELLOW::get, MesogleaDripParticle.YellowHangProvider::new);
		ParticleProviderRegistry.register(WWParticleTypes.FALLING_MESOGLEA_YELLOW::get, MesogleaDripParticle.YellowFallProvider::new);
		ParticleProviderRegistry.register(WWParticleTypes.LANDING_MESOGLEA_YELLOW::get, MesogleaDripParticle.LandProvider::new);
		ParticleProviderRegistry.register(
			WWParticleTypes.MESOGLEA_BUBBLE_YELLOW::get,
			(spriteProvider) -> new MesogleaBubbleParticle.Provider(spriteProvider, WWParticleTypes.MESOGLEA_BUBBLE_POP_YELLOW.get())
		);
		ParticleProviderRegistry.register(
			WWParticleTypes.BUBBLE_COLUMN_UP_MESOGLEA_YELLOW::get,
			(spriteProvider) -> new MesogleaBubbleColumnUpParticle.Provider(spriteProvider, WWParticleTypes.MESOGLEA_BUBBLE_POP_YELLOW.get())
		);
		ParticleProviderRegistry.register(
			WWParticleTypes.CURRENT_DOWN_MESOGLEA_YELLOW::get,
			(spriteProvider) -> new MesogleaCurrentDownParticle.Provider(spriteProvider, WWParticleTypes.MESOGLEA_BUBBLE_POP_YELLOW.get())
		);
		ParticleProviderRegistry.register(WWParticleTypes.MESOGLEA_BUBBLE_POP_YELLOW::get, MesogleaBubblePopParticle.Provider::new);
		ParticleProviderRegistry.register(WWParticleTypes.MESOGLEA_SPLASH_YELLOW::get, MesogleaSplashParticle.Provider::new);

		ParticleProviderRegistry.register(WWParticleTypes.HANGING_MESOGLEA_LIME::get, MesogleaDripParticle.LimeHangProvider::new);
		ParticleProviderRegistry.register(WWParticleTypes.FALLING_MESOGLEA_LIME::get, MesogleaDripParticle.LimeFallProvider::new);
		ParticleProviderRegistry.register(WWParticleTypes.LANDING_MESOGLEA_LIME::get, MesogleaDripParticle.LandProvider::new);
		ParticleProviderRegistry.register(
			WWParticleTypes.MESOGLEA_BUBBLE_LIME::get,
			(spriteProvider) -> new MesogleaBubbleParticle.Provider(spriteProvider, WWParticleTypes.MESOGLEA_BUBBLE_POP_LIME.get())
		);
		ParticleProviderRegistry.register(
			WWParticleTypes.BUBBLE_COLUMN_UP_MESOGLEA_LIME::get,
			(spriteProvider) -> new MesogleaBubbleColumnUpParticle.Provider(spriteProvider, WWParticleTypes.MESOGLEA_BUBBLE_POP_LIME.get())
		);
		ParticleProviderRegistry.register(
			WWParticleTypes.CURRENT_DOWN_MESOGLEA_LIME::get,
			(spriteProvider) -> new MesogleaCurrentDownParticle.Provider(spriteProvider, WWParticleTypes.MESOGLEA_BUBBLE_POP_LIME.get())
		);
		ParticleProviderRegistry.register(WWParticleTypes.MESOGLEA_BUBBLE_POP_LIME::get, MesogleaBubblePopParticle.Provider::new);
		ParticleProviderRegistry.register(WWParticleTypes.MESOGLEA_SPLASH_LIME::get, MesogleaSplashParticle.Provider::new);

		ParticleProviderRegistry.register(WWParticleTypes.HANGING_MESOGLEA_PINK::get, MesogleaDripParticle.PinkHangProvider::new);
		ParticleProviderRegistry.register(WWParticleTypes.FALLING_MESOGLEA_PINK::get, MesogleaDripParticle.PinkFallProvider::new);
		ParticleProviderRegistry.register(WWParticleTypes.LANDING_MESOGLEA_PINK::get, MesogleaDripParticle.LandProvider::new);
		ParticleProviderRegistry.register(
			WWParticleTypes.MESOGLEA_BUBBLE_PINK::get,
			(spriteProvider) -> new MesogleaBubbleParticle.Provider(spriteProvider, WWParticleTypes.MESOGLEA_BUBBLE_POP_PINK.get())
		);
		ParticleProviderRegistry.register(
			WWParticleTypes.BUBBLE_COLUMN_UP_MESOGLEA_PINK::get,
			(spriteProvider) -> new MesogleaBubbleColumnUpParticle.Provider(spriteProvider, WWParticleTypes.MESOGLEA_BUBBLE_POP_PINK.get())
		);
		ParticleProviderRegistry.register(
			WWParticleTypes.CURRENT_DOWN_MESOGLEA_PINK::get,
			(spriteProvider) -> new MesogleaCurrentDownParticle.Provider(spriteProvider, WWParticleTypes.MESOGLEA_BUBBLE_POP_PINK.get())
		);
		ParticleProviderRegistry.register(WWParticleTypes.MESOGLEA_BUBBLE_POP_PINK::get, MesogleaBubblePopParticle.Provider::new);
		ParticleProviderRegistry.register(WWParticleTypes.MESOGLEA_SPLASH_PINK::get, MesogleaSplashParticle.Provider::new);

		ParticleProviderRegistry.register(WWParticleTypes.HANGING_MESOGLEA_RED::get, MesogleaDripParticle.RedHangProvider::new);
		ParticleProviderRegistry.register(WWParticleTypes.FALLING_MESOGLEA_RED::get, MesogleaDripParticle.RedFallProvider::new);
		ParticleProviderRegistry.register(WWParticleTypes.LANDING_MESOGLEA_RED::get, MesogleaDripParticle.LandProvider::new);
		ParticleProviderRegistry.register(
			WWParticleTypes.MESOGLEA_BUBBLE_RED::get,
			(spriteProvider) -> new MesogleaBubbleParticle.Provider(spriteProvider, WWParticleTypes.MESOGLEA_BUBBLE_POP_RED.get())
		);
		ParticleProviderRegistry.register(
			WWParticleTypes.BUBBLE_COLUMN_UP_MESOGLEA_RED::get,
			(spriteProvider) -> new MesogleaBubbleColumnUpParticle.Provider(spriteProvider, WWParticleTypes.MESOGLEA_BUBBLE_POP_RED.get())
		);
		ParticleProviderRegistry.register(
			WWParticleTypes.CURRENT_DOWN_MESOGLEA_RED::get,
			(spriteProvider) -> new MesogleaCurrentDownParticle.Provider(spriteProvider, WWParticleTypes.MESOGLEA_BUBBLE_POP_RED.get())
		);
		ParticleProviderRegistry.register(WWParticleTypes.MESOGLEA_BUBBLE_POP_RED::get, MesogleaBubblePopParticle.Provider::new);
		ParticleProviderRegistry.register(WWParticleTypes.MESOGLEA_SPLASH_RED::get, MesogleaSplashParticle.Provider::new);
	}
}
