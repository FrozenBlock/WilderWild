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

import lombok.experimental.UtilityClass;
import net.frozenblock.lib.particle.client.api.ParticleProviderRegistry;
import net.frozenblock.lib.platform.api.registry.DeferredSimpleParticleType;
import net.frozenblock.wilderwild.block.impl.MesogleaCollection;
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
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
@UtilityClass
@ClientOnly
public final class WWParticleResources {

	public static void init() {
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

		// MESOGLEA
		MesogleaCollection.DYE_COLORS.forEach(color -> {
			final DeferredSimpleParticleType hangingParticle = WWParticleTypes.HANGING_MESOGLEA.pick(color);
			final DeferredSimpleParticleType fallingParticle = WWParticleTypes.FALLING_MESOGLEA.pick(color);
			final DeferredSimpleParticleType landingParticle = WWParticleTypes.LANDING_MESOGLEA.pick(color);
			ParticleProviderRegistry.register(hangingParticle, spriteSet -> new MesogleaDripParticle.HangProvider(spriteSet, () -> fallingParticle.get()));
			ParticleProviderRegistry.register(fallingParticle, spriteSet -> new MesogleaDripParticle.FallProvider(spriteSet, () -> landingParticle.get()));
			ParticleProviderRegistry.register(landingParticle, MesogleaDripParticle.LandProvider::new);

			final DeferredSimpleParticleType bubblePopParticle = WWParticleTypes.MESOGLEA_BUBBLE_POP.pick(color);
			ParticleProviderRegistry.register(
				WWParticleTypes.MESOGLEA_BUBBLE.pick(color),
				spriteProvider -> new MesogleaBubbleParticle.Provider(spriteProvider, () -> bubblePopParticle.get())
			);
			ParticleProviderRegistry.register(
				WWParticleTypes.MESOGLEA_BUBBLE_COLUMN_UP.pick(color),
				spriteProvider -> new MesogleaBubbleColumnUpParticle.Provider(spriteProvider, () -> bubblePopParticle.get())
			);
			ParticleProviderRegistry.register(
				WWParticleTypes.MESOGLEA_CURRENT_DOWN.pick(color),
				spriteProvider -> new MesogleaCurrentDownParticle.Provider(spriteProvider, () -> bubblePopParticle.get())
			);
			ParticleProviderRegistry.register(bubblePopParticle, MesogleaBubblePopParticle.Provider::new);

			ParticleProviderRegistry.register(WWParticleTypes.MESOGLEA_SPLASH.pick(color), MesogleaSplashParticle.Provider::new);
		});

		// VANILLA LEAVES
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
		WWParticleTypes.POPLAR_LEAVES.forEach(leaf -> ParticleProviderRegistry.register(leaf, WWFallingLeavesParticle.Provider::new));
		WWParticleTypes.POPLAR_LITTER_LEAVES.forEach(leaf -> ParticleProviderRegistry.register(leaf, WWFallingLeavesParticle.Provider::new));
		ParticleProviderRegistry.register(WWParticleTypes.AZALEA_LEAVES::get, WWFallingLeavesParticle.Provider::new);
		ParticleProviderRegistry.register(WWParticleTypes.FLOWERING_AZALEA_LEAVES::get, WWFallingLeavesParticle.Provider::new);
		ParticleProviderRegistry.register(WWParticleTypes.AZALEA_LITTER_LEAVES::get, WWFallingLeavesParticle.Provider::new);

		// WILDER WILD LEAVES
		ParticleProviderRegistry.register(WWParticleTypes.BAOBAB_LEAVES::get, WWFallingLeavesParticle.Provider::new);
		ParticleProviderRegistry.register(WWParticleTypes.BAOBAB_LITTER_LEAVES::get, WWFallingLeavesParticle.Provider::new);
		ParticleProviderRegistry.register(WWParticleTypes.WILLOW_LEAVES::get, WWFallingLeavesParticle.Provider::new);
		ParticleProviderRegistry.register(WWParticleTypes.WILLOW_LITTER_LEAVES::get, WWFallingLeavesParticle.Provider::new);
		ParticleProviderRegistry.register(WWParticleTypes.CYPRESS_LEAVES::get, WWFallingLeavesParticle.Provider::new);
		ParticleProviderRegistry.register(WWParticleTypes.CYPRESS_LITTER_LEAVES::get, WWFallingLeavesParticle.Provider::new);
		ParticleProviderRegistry.register(WWParticleTypes.PALM_FRONDS::get, WWFallingLeavesParticle.Provider::new);
		ParticleProviderRegistry.register(WWParticleTypes.PALM_LITTER_FRONDS::get, WWFallingLeavesParticle.Provider::new);
		WWParticleTypes.MAPLE_LEAVES.forEach(leaf -> ParticleProviderRegistry.register(leaf, WWFallingLeavesParticle.Provider::new));

		// SPAWNER PARTICLES
		ParticleProviderRegistry.register(WWParticleTypes.LEAF_CLUSTER_SPAWNER::get, LeafClusterSeedParticle.Provider::new);
		ParticleProviderRegistry.register(WWParticleTypes.CHEST_BUBBLE_SPAWNER::get, ChestBubbleSeedParticle.Provider::new);
		ParticleProviderRegistry.register(WWParticleTypes.SHRIEKER_BUBBLE_SPAWNER::get, ShriekerBubbleSeedParticle.Provider::new);
		ParticleProviderRegistry.register(WWParticleTypes.WIND_CLUSTER::get, WindClusterSeedParticle.Provider::new);
	}
}
