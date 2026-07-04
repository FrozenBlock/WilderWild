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

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.platform.api.client.particle.FrozenParticleProviderRegistry;
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

@Environment(EnvType.CLIENT)
public final class WWParticleEngine {

	public static void init() {
		FrozenParticleProviderRegistry.register(WWParticleTypes.LEAF_CLUSTER_SPAWNER::get, LeafClusterSeedParticle.Provider::new);
		FrozenParticleProviderRegistry.register(WWParticleTypes.OAK_LEAVES::get, WWFallingLeavesParticle.Provider::new);
		FrozenParticleProviderRegistry.register(WWParticleTypes.OAK_LITTER_LEAVES::get, WWFallingLeavesParticle.Provider::new);
		FrozenParticleProviderRegistry.register(WWParticleTypes.SPRUCE_LEAVES::get, WWFallingLeavesParticle.Provider::new);
		FrozenParticleProviderRegistry.register(WWParticleTypes.SPRUCE_LITTER_LEAVES::get, WWFallingLeavesParticle.Provider::new);
		FrozenParticleProviderRegistry.register(WWParticleTypes.BIRCH_LEAVES::get, WWFallingLeavesParticle.Provider::new);
		FrozenParticleProviderRegistry.register(WWParticleTypes.BIRCH_LITTER_LEAVES::get, WWFallingLeavesParticle.Provider::new);
		FrozenParticleProviderRegistry.register(WWParticleTypes.JUNGLE_LEAVES::get, WWFallingLeavesParticle.Provider::new);
		FrozenParticleProviderRegistry.register(WWParticleTypes.JUNGLE_LITTER_LEAVES::get, WWFallingLeavesParticle.Provider::new);
		FrozenParticleProviderRegistry.register(WWParticleTypes.ACACIA_LEAVES, WWFallingLeavesParticle.Provider::new);
		FrozenParticleProviderRegistry.register(WWParticleTypes.ACACIA_LITTER_LEAVES::get, WWFallingLeavesParticle.Provider::new);
		FrozenParticleProviderRegistry.register(WWParticleTypes.DARK_OAK_LEAVES::get, WWFallingLeavesParticle.Provider::new);
		FrozenParticleProviderRegistry.register(WWParticleTypes.DARK_OAK_LITTER_LEAVES::get, WWFallingLeavesParticle.Provider::new);
		FrozenParticleProviderRegistry.register(WWParticleTypes.PALE_OAK_LEAVES::get, WWFallingLeavesParticle.Provider::new);
		FrozenParticleProviderRegistry.register(WWParticleTypes.PALE_OAK_LITTER_LEAVES::get, WWFallingLeavesParticle.Provider::new);
		FrozenParticleProviderRegistry.register(WWParticleTypes.MANGROVE_LEAVES::get, WWFallingLeavesParticle.Provider::new);
		FrozenParticleProviderRegistry.register(WWParticleTypes.MANGROVE_LITTER_LEAVES::get, WWFallingLeavesParticle.Provider::new);
		FrozenParticleProviderRegistry.register(WWParticleTypes.CHERRY_LEAVES::get, WWFallingLeavesParticle.Provider::new);
		FrozenParticleProviderRegistry.register(WWParticleTypes.CHERRY_LITTER_LEAVES::get, WWFallingLeavesParticle.Provider::new);
		FrozenParticleProviderRegistry.register(WWParticleTypes.AZALEA_LEAVES::get, WWFallingLeavesParticle.Provider::new);
		FrozenParticleProviderRegistry.register(WWParticleTypes.FLOWERING_AZALEA_LEAVES::get, WWFallingLeavesParticle.Provider::new);
		FrozenParticleProviderRegistry.register(WWParticleTypes.AZALEA_LITTER_LEAVES::get, WWFallingLeavesParticle.Provider::new);
		FrozenParticleProviderRegistry.register(WWParticleTypes.BAOBAB_LEAVES::get, WWFallingLeavesParticle.Provider::new);
		FrozenParticleProviderRegistry.register(WWParticleTypes.BAOBAB_LITTER_LEAVES::get, WWFallingLeavesParticle.Provider::new);
		FrozenParticleProviderRegistry.register(WWParticleTypes.CYPRESS_LEAVES::get, WWFallingLeavesParticle.Provider::new);
		FrozenParticleProviderRegistry.register(WWParticleTypes.CYPRESS_LITTER_LEAVES::get, WWFallingLeavesParticle.Provider::new);
		FrozenParticleProviderRegistry.register(WWParticleTypes.PALM_FRONDS::get, WWFallingLeavesParticle.Provider::new);
		FrozenParticleProviderRegistry.register(WWParticleTypes.PALM_LITTER_FRONDS::get, WWFallingLeavesParticle.Provider::new);
		FrozenParticleProviderRegistry.register(WWParticleTypes.YELLOW_MAPLE_LEAVES::get, WWFallingLeavesParticle.Provider::new);
		FrozenParticleProviderRegistry.register(WWParticleTypes.ORANGE_MAPLE_LEAVES::get, WWFallingLeavesParticle.Provider::new);
		FrozenParticleProviderRegistry.register(WWParticleTypes.RED_MAPLE_LEAVES::get, WWFallingLeavesParticle.Provider::new);
		FrozenParticleProviderRegistry.register(WWParticleTypes.WILLOW_LEAVES::get, WWFallingLeavesParticle.Provider::new);
		FrozenParticleProviderRegistry.register(WWParticleTypes.WILLOW_LITTER_LEAVES::get, WWFallingLeavesParticle.Provider::new);

		FrozenParticleProviderRegistry.register(WWParticleTypes.CHEST_BUBBLE_SPAWNER::get, ChestBubbleSeedParticle.Provider::new);
		FrozenParticleProviderRegistry.register(WWParticleTypes.SHRIEKER_BUBBLE_SPAWNER::get, ShriekerBubbleSeedParticle.Provider::new);
		FrozenParticleProviderRegistry.register(WWParticleTypes.WIND_CLUSTER::get, WindClusterSeedParticle.Provider::new);
		FrozenParticleProviderRegistry.register(WWParticleTypes.POLLEN::get, PollenParticle.Provider::new);
		FrozenParticleProviderRegistry.register(WWParticleTypes.SEED::get, SeedParticle.Provider::new);
		FrozenParticleProviderRegistry.register(WWParticleTypes.PALE_FOG::get, PaleFogParticle.LargeFactory::new);
		FrozenParticleProviderRegistry.register(WWParticleTypes.PALE_FOG_SMALL::get, PaleFogParticle.SmallFactory::new);
		FrozenParticleProviderRegistry.register(WWParticleTypes.PALE_SPORE::get, PollenParticle.PaleSporeFactory::new);
		FrozenParticleProviderRegistry.register(WWParticleTypes.FLOATING_SCULK_BUBBLE::get, FloatingSculkBubbleParticle.Provider::new);
		FrozenParticleProviderRegistry.register(WWParticleTypes.TERMITE::get, TermiteParticle.Provider::new);
		FrozenParticleProviderRegistry.register(WWParticleTypes.COCONUT_SPLASH::get, FallingParticle.Provider::new);
		FrozenParticleProviderRegistry.register(WWParticleTypes.SCORCHING_FLAME::get, WWParticleProviders.ScorchingEffectFlameProvider::new);
		FrozenParticleProviderRegistry.register(WWParticleTypes.UNDERWATER_ASH::get, WWParticleProviders.UnderwaterAshProvider::new);

		FrozenParticleProviderRegistry.register(WWParticleTypes.PLANKTON::get, PlanktonParticle.PlanktonProvider::new);
		FrozenParticleProviderRegistry.register(WWParticleTypes.GLOWING_PLANKTON::get, PlanktonParticle.GlowingProvider::new);

		FrozenParticleProviderRegistry.register(WWParticleTypes.HANGING_MESOGLEA_PEARLESCENT_BLUE::get, MesogleaDripParticle.PearlescentBlueHangProvider::new);
		FrozenParticleProviderRegistry.register(WWParticleTypes.FALLING_MESOGLEA_PEARLESCENT_BLUE::get, MesogleaDripParticle.PearlescentBlueFallProvider::new);
		FrozenParticleProviderRegistry.register(WWParticleTypes.LANDING_MESOGLEA_PEARLESCENT_BLUE::get, MesogleaDripParticle.LandProvider::new);
		FrozenParticleProviderRegistry.register(
			WWParticleTypes.MESOGLEA_BUBBLE_PEARLESCENT_BLUE::get,
			(spriteProvider) -> new MesogleaBubbleParticle.Provider(spriteProvider, WWParticleTypes.MESOGLEA_BUBBLE_POP_PEARLESCENT_BLUE.get())
		);
		FrozenParticleProviderRegistry.register(
			WWParticleTypes.BUBBLE_COLUMN_UP_MESOGLEA_PEARLESCENT_BLUE::get,
			(spriteProvider) -> new MesogleaBubbleColumnUpParticle.Provider(spriteProvider, WWParticleTypes.MESOGLEA_BUBBLE_POP_PEARLESCENT_BLUE.get())
		);
		FrozenParticleProviderRegistry.register(
			WWParticleTypes.CURRENT_DOWN_MESOGLEA_PEARLESCENT_BLUE::get,
			(spriteProvider) -> new MesogleaCurrentDownParticle.Provider(spriteProvider, WWParticleTypes.MESOGLEA_BUBBLE_POP_PEARLESCENT_BLUE.get())
		);
		FrozenParticleProviderRegistry.register(WWParticleTypes.MESOGLEA_BUBBLE_POP_PEARLESCENT_BLUE::get, MesogleaBubblePopParticle.Provider::new);
		FrozenParticleProviderRegistry.register(WWParticleTypes.MESOGLEA_SPLASH_PEARLESCENT_BLUE::get, MesogleaSplashParticle.Provider::new);

		FrozenParticleProviderRegistry.register(WWParticleTypes.HANGING_MESOGLEA_PEARLESCENT_PURPLE::get, MesogleaDripParticle.PearlescentPurpleHangProvider::new);
		FrozenParticleProviderRegistry.register(WWParticleTypes.FALLING_MESOGLEA_PEARLESCENT_PURPLE::get, MesogleaDripParticle.PearlescentPurpleFallProvider::new);
		FrozenParticleProviderRegistry.register(WWParticleTypes.LANDING_MESOGLEA_PEARLESCENT_PURPLE::get, MesogleaDripParticle.LandProvider::new);
		FrozenParticleProviderRegistry.register(
			WWParticleTypes.MESOGLEA_BUBBLE_PEARLESCENT_PURPLE::get,
			(spriteProvider) -> new MesogleaBubbleParticle.Provider(spriteProvider, WWParticleTypes.MESOGLEA_BUBBLE_POP_PEARLESCENT_PURPLE.get())
		);
		FrozenParticleProviderRegistry.register(
			WWParticleTypes.BUBBLE_COLUMN_UP_MESOGLEA_PEARLESCENT_PURPLE::get,
			(spriteProvider) -> new MesogleaBubbleColumnUpParticle.Provider(spriteProvider, WWParticleTypes.MESOGLEA_BUBBLE_POP_PEARLESCENT_PURPLE.get())
		);
		FrozenParticleProviderRegistry.register(
			WWParticleTypes.CURRENT_DOWN_MESOGLEA_PEARLESCENT_PURPLE::get,
			(spriteProvider) -> new MesogleaCurrentDownParticle.Provider(spriteProvider, WWParticleTypes.MESOGLEA_BUBBLE_POP_PEARLESCENT_PURPLE.get())
		);
		FrozenParticleProviderRegistry.register(WWParticleTypes.MESOGLEA_BUBBLE_POP_PEARLESCENT_PURPLE::get, MesogleaBubblePopParticle.Provider::new);
		FrozenParticleProviderRegistry.register(WWParticleTypes.MESOGLEA_SPLASH_PEARLESCENT_PURPLE::get, MesogleaSplashParticle.Provider::new);

		FrozenParticleProviderRegistry.register(WWParticleTypes.HANGING_MESOGLEA_BLUE::get, MesogleaDripParticle.BlueHangProvider::new);
		FrozenParticleProviderRegistry.register(WWParticleTypes.FALLING_MESOGLEA_BLUE::get, MesogleaDripParticle.BlueFallProvider::new);
		FrozenParticleProviderRegistry.register(WWParticleTypes.LANDING_MESOGLEA_BLUE::get, MesogleaDripParticle.LandProvider::new);
		FrozenParticleProviderRegistry.register(
			WWParticleTypes.MESOGLEA_BUBBLE_BLUE::get,
			(spriteProvider) -> new MesogleaBubbleParticle.Provider(spriteProvider, WWParticleTypes.MESOGLEA_BUBBLE_POP_BLUE.get())
		);
		FrozenParticleProviderRegistry.register(
			WWParticleTypes.BUBBLE_COLUMN_UP_MESOGLEA_BLUE::get,
			(spriteProvider) -> new MesogleaBubbleColumnUpParticle.Provider(spriteProvider, WWParticleTypes.MESOGLEA_BUBBLE_POP_BLUE.get())
		);
		FrozenParticleProviderRegistry.register(
			WWParticleTypes.CURRENT_DOWN_MESOGLEA_BLUE::get,
			(spriteProvider) -> new MesogleaCurrentDownParticle.Provider(spriteProvider, WWParticleTypes.MESOGLEA_BUBBLE_POP_BLUE.get())
		);
		FrozenParticleProviderRegistry.register(WWParticleTypes.MESOGLEA_BUBBLE_POP_BLUE::get, MesogleaBubblePopParticle.Provider::new);
		FrozenParticleProviderRegistry.register(WWParticleTypes.MESOGLEA_SPLASH_BLUE::get, MesogleaSplashParticle.Provider::new);

		FrozenParticleProviderRegistry.register(WWParticleTypes.HANGING_MESOGLEA_YELLOW::get, MesogleaDripParticle.YellowHangProvider::new);
		FrozenParticleProviderRegistry.register(WWParticleTypes.FALLING_MESOGLEA_YELLOW::get, MesogleaDripParticle.YellowFallProvider::new);
		FrozenParticleProviderRegistry.register(WWParticleTypes.LANDING_MESOGLEA_YELLOW::get, MesogleaDripParticle.LandProvider::new);
		FrozenParticleProviderRegistry.register(
			WWParticleTypes.MESOGLEA_BUBBLE_YELLOW::get,
			(spriteProvider) -> new MesogleaBubbleParticle.Provider(spriteProvider, WWParticleTypes.MESOGLEA_BUBBLE_POP_YELLOW.get())
		);
		FrozenParticleProviderRegistry.register(
			WWParticleTypes.BUBBLE_COLUMN_UP_MESOGLEA_YELLOW::get,
			(spriteProvider) -> new MesogleaBubbleColumnUpParticle.Provider(spriteProvider, WWParticleTypes.MESOGLEA_BUBBLE_POP_YELLOW.get())
		);
		FrozenParticleProviderRegistry.register(
			WWParticleTypes.CURRENT_DOWN_MESOGLEA_YELLOW::get,
			(spriteProvider) -> new MesogleaCurrentDownParticle.Provider(spriteProvider, WWParticleTypes.MESOGLEA_BUBBLE_POP_YELLOW.get())
		);
		FrozenParticleProviderRegistry.register(WWParticleTypes.MESOGLEA_BUBBLE_POP_YELLOW::get, MesogleaBubblePopParticle.Provider::new);
		FrozenParticleProviderRegistry.register(WWParticleTypes.MESOGLEA_SPLASH_YELLOW::get, MesogleaSplashParticle.Provider::new);

		FrozenParticleProviderRegistry.register(WWParticleTypes.HANGING_MESOGLEA_LIME::get, MesogleaDripParticle.LimeHangProvider::new);
		FrozenParticleProviderRegistry.register(WWParticleTypes.FALLING_MESOGLEA_LIME::get, MesogleaDripParticle.LimeFallProvider::new);
		FrozenParticleProviderRegistry.register(WWParticleTypes.LANDING_MESOGLEA_LIME::get, MesogleaDripParticle.LandProvider::new);
		FrozenParticleProviderRegistry.register(
			WWParticleTypes.MESOGLEA_BUBBLE_LIME::get,
			(spriteProvider) -> new MesogleaBubbleParticle.Provider(spriteProvider, WWParticleTypes.MESOGLEA_BUBBLE_POP_LIME.get())
		);
		FrozenParticleProviderRegistry.register(
			WWParticleTypes.BUBBLE_COLUMN_UP_MESOGLEA_LIME::get,
			(spriteProvider) -> new MesogleaBubbleColumnUpParticle.Provider(spriteProvider, WWParticleTypes.MESOGLEA_BUBBLE_POP_LIME.get())
		);
		FrozenParticleProviderRegistry.register(
			WWParticleTypes.CURRENT_DOWN_MESOGLEA_LIME::get,
			(spriteProvider) -> new MesogleaCurrentDownParticle.Provider(spriteProvider, WWParticleTypes.MESOGLEA_BUBBLE_POP_LIME.get())
		);
		FrozenParticleProviderRegistry.register(WWParticleTypes.MESOGLEA_BUBBLE_POP_LIME::get, MesogleaBubblePopParticle.Provider::new);
		FrozenParticleProviderRegistry.register(WWParticleTypes.MESOGLEA_SPLASH_LIME::get, MesogleaSplashParticle.Provider::new);

		FrozenParticleProviderRegistry.register(WWParticleTypes.HANGING_MESOGLEA_PINK::get, MesogleaDripParticle.PinkHangProvider::new);
		FrozenParticleProviderRegistry.register(WWParticleTypes.FALLING_MESOGLEA_PINK::get, MesogleaDripParticle.PinkFallProvider::new);
		FrozenParticleProviderRegistry.register(WWParticleTypes.LANDING_MESOGLEA_PINK::get, MesogleaDripParticle.LandProvider::new);
		FrozenParticleProviderRegistry.register(
			WWParticleTypes.MESOGLEA_BUBBLE_PINK::get,
			(spriteProvider) -> new MesogleaBubbleParticle.Provider(spriteProvider, WWParticleTypes.MESOGLEA_BUBBLE_POP_PINK.get())
		);
		FrozenParticleProviderRegistry.register(
			WWParticleTypes.BUBBLE_COLUMN_UP_MESOGLEA_PINK::get,
			(spriteProvider) -> new MesogleaBubbleColumnUpParticle.Provider(spriteProvider, WWParticleTypes.MESOGLEA_BUBBLE_POP_PINK.get())
		);
		FrozenParticleProviderRegistry.register(
			WWParticleTypes.CURRENT_DOWN_MESOGLEA_PINK::get,
			(spriteProvider) -> new MesogleaCurrentDownParticle.Provider(spriteProvider, WWParticleTypes.MESOGLEA_BUBBLE_POP_PINK.get())
		);
		FrozenParticleProviderRegistry.register(WWParticleTypes.MESOGLEA_BUBBLE_POP_PINK::get, MesogleaBubblePopParticle.Provider::new);
		FrozenParticleProviderRegistry.register(WWParticleTypes.MESOGLEA_SPLASH_PINK::get, MesogleaSplashParticle.Provider::new);

		FrozenParticleProviderRegistry.register(WWParticleTypes.HANGING_MESOGLEA_RED::get, MesogleaDripParticle.RedHangProvider::new);
		FrozenParticleProviderRegistry.register(WWParticleTypes.FALLING_MESOGLEA_RED::get, MesogleaDripParticle.RedFallProvider::new);
		FrozenParticleProviderRegistry.register(WWParticleTypes.LANDING_MESOGLEA_RED::get, MesogleaDripParticle.LandProvider::new);
		FrozenParticleProviderRegistry.register(
			WWParticleTypes.MESOGLEA_BUBBLE_RED::get,
			(spriteProvider) -> new MesogleaBubbleParticle.Provider(spriteProvider, WWParticleTypes.MESOGLEA_BUBBLE_POP_RED.get())
		);
		FrozenParticleProviderRegistry.register(
			WWParticleTypes.BUBBLE_COLUMN_UP_MESOGLEA_RED::get,
			(spriteProvider) -> new MesogleaBubbleColumnUpParticle.Provider(spriteProvider, WWParticleTypes.MESOGLEA_BUBBLE_POP_RED.get())
		);
		FrozenParticleProviderRegistry.register(
			WWParticleTypes.CURRENT_DOWN_MESOGLEA_RED::get,
			(spriteProvider) -> new MesogleaCurrentDownParticle.Provider(spriteProvider, WWParticleTypes.MESOGLEA_BUBBLE_POP_RED.get())
		);
		FrozenParticleProviderRegistry.register(WWParticleTypes.MESOGLEA_BUBBLE_POP_RED::get, MesogleaBubblePopParticle.Provider::new);
		FrozenParticleProviderRegistry.register(WWParticleTypes.MESOGLEA_SPLASH_RED::get, MesogleaSplashParticle.Provider::new);
	}
}
