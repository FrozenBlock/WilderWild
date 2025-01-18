/*
 * Copyright 2023-2025 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
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
import net.frozenblock.wilderwild.particle.MesogleaDripParticle;
import net.frozenblock.wilderwild.particle.PollenParticle;
import net.frozenblock.wilderwild.particle.SeedParticle;
import net.frozenblock.wilderwild.particle.TermiteParticle;
import net.frozenblock.wilderwild.particle.WindParticle;
import net.frozenblock.wilderwild.particle.factory.WilderParticleFactories;
import net.frozenblock.wilderwild.registry.WWParticleTypes;

@Environment(EnvType.CLIENT)
public final class WWParticleEngine {

	public static void init() {
		ParticleFactoryRegistry particleRegistry = ParticleFactoryRegistry.getInstance();

		particleRegistry.register(WWParticleTypes.LEAF_CLUSTER_SPAWNER, LeafClusterSeedParticle.Factory::new);
		particleRegistry.register(WWParticleTypes.OAK_LEAVES, LeafParticle.Factory::new);
		particleRegistry.register(WWParticleTypes.SPRUCE_LEAVES, LeafParticle.Factory::new);
		particleRegistry.register(WWParticleTypes.BIRCH_LEAVES, LeafParticle.Factory::new);
		particleRegistry.register(WWParticleTypes.JUNGLE_LEAVES, LeafParticle.Factory::new);
		particleRegistry.register(WWParticleTypes.ACACIA_LEAVES, LeafParticle.Factory::new);
		particleRegistry.register(WWParticleTypes.DARK_OAK_LEAVES, LeafParticle.Factory::new);
		particleRegistry.register(WWParticleTypes.MANGROVE_LEAVES, LeafParticle.Factory::new);
		particleRegistry.register(WWParticleTypes.CHERRY_LEAVES, LeafParticle.Factory::new);
		particleRegistry.register(WWParticleTypes.AZALEA_LEAVES, LeafParticle.Factory::new);
		particleRegistry.register(WWParticleTypes.FLOWERING_AZALEA_LEAVES, LeafParticle.Factory::new);
		particleRegistry.register(WWParticleTypes.BAOBAB_LEAVES, LeafParticle.Factory::new);
		particleRegistry.register(WWParticleTypes.CYPRESS_LEAVES, LeafParticle.Factory::new);
		particleRegistry.register(WWParticleTypes.PALM_FRONDS, LeafParticle.Factory::new);
		particleRegistry.register(WWParticleTypes.YELLOW_MAPLE_LEAVES, LeafParticle.Factory::new);
		particleRegistry.register(WWParticleTypes.ORANGE_MAPLE_LEAVES, LeafParticle.Factory::new);
		particleRegistry.register(WWParticleTypes.RED_MAPLE_LEAVES, LeafParticle.Factory::new);

		particleRegistry.register(WWParticleTypes.CHEST_BUBBLE_SPAWNER, ChestBubbleSeedParticle.Factory::new);
		particleRegistry.register(WWParticleTypes.POLLEN, PollenParticle.PollenFactory::new);
		particleRegistry.register(WWParticleTypes.SEED, SeedParticle.Factory::new);
		particleRegistry.register(WWParticleTypes.FLOATING_SCULK_BUBBLE, FloatingSculkBubbleParticle.BubbleFactory::new);
		particleRegistry.register(WWParticleTypes.WIND, WindParticle.Factory::new);
		particleRegistry.register(WWParticleTypes.TERMITE, TermiteParticle.Factory::new);
		particleRegistry.register(WWParticleTypes.COCONUT_SPLASH, FallingParticle.Factory::new);
		particleRegistry.register(WWParticleTypes.SCORCHING_FLAME, WilderParticleFactories.ScorchingEffectFlameFactory::new);
		particleRegistry.register(WWParticleTypes.BLUE_PEARLESCENT_HANGING_MESOGLEA, MesogleaDripParticle.BPMesogleaHangProvider::new);
		particleRegistry.register(WWParticleTypes.BLUE_PEARLESCENT_FALLING_MESOGLEA, MesogleaDripParticle.BPMesogleaFallProvider::new);
		particleRegistry.register(WWParticleTypes.BLUE_PEARLESCENT_LANDING_MESOGLEA, MesogleaDripParticle.BPMesogleaLandProvider::new);
		particleRegistry.register(WWParticleTypes.PURPLE_PEARLESCENT_HANGING_MESOGLEA, MesogleaDripParticle.PPMesogleaHangProvider::new);
		particleRegistry.register(WWParticleTypes.PURPLE_PEARLESCENT_FALLING_MESOGLEA, MesogleaDripParticle.PPMesogleaFallProvider::new);
		particleRegistry.register(WWParticleTypes.PURPLE_PEARLESCENT_LANDING_MESOGLEA, MesogleaDripParticle.PPMesogleaLandProvider::new);
		particleRegistry.register(WWParticleTypes.BLUE_HANGING_MESOGLEA, MesogleaDripParticle.BMesogleaHangProvider::new);
		particleRegistry.register(WWParticleTypes.BLUE_FALLING_MESOGLEA, MesogleaDripParticle.BMesogleaFallProvider::new);
		particleRegistry.register(WWParticleTypes.BLUE_LANDING_MESOGLEA, MesogleaDripParticle.BMesogleaLandProvider::new);
		particleRegistry.register(WWParticleTypes.YELLOW_HANGING_MESOGLEA, MesogleaDripParticle.YMesogleaHangProvider::new);
		particleRegistry.register(WWParticleTypes.YELLOW_FALLING_MESOGLEA, MesogleaDripParticle.YMesogleaFallProvider::new);
		particleRegistry.register(WWParticleTypes.YELLOW_LANDING_MESOGLEA, MesogleaDripParticle.YMesogleaLandProvider::new);
		particleRegistry.register(WWParticleTypes.LIME_HANGING_MESOGLEA, MesogleaDripParticle.LMesogleaHangProvider::new);
		particleRegistry.register(WWParticleTypes.LIME_FALLING_MESOGLEA, MesogleaDripParticle.LMesogleaFallProvider::new);
		particleRegistry.register(WWParticleTypes.LIME_LANDING_MESOGLEA, MesogleaDripParticle.LMesogleaLandProvider::new);
		particleRegistry.register(WWParticleTypes.PINK_HANGING_MESOGLEA, MesogleaDripParticle.PMesogleaHangProvider::new);
		particleRegistry.register(WWParticleTypes.PINK_FALLING_MESOGLEA, MesogleaDripParticle.PMesogleaFallProvider::new);
		particleRegistry.register(WWParticleTypes.PINK_LANDING_MESOGLEA, MesogleaDripParticle.PMesogleaLandProvider::new);
		particleRegistry.register(WWParticleTypes.RED_HANGING_MESOGLEA, MesogleaDripParticle.RMesogleaHangProvider::new);
		particleRegistry.register(WWParticleTypes.RED_FALLING_MESOGLEA, MesogleaDripParticle.RMesogleaFallProvider::new);
		particleRegistry.register(WWParticleTypes.RED_LANDING_MESOGLEA, MesogleaDripParticle.RMesogleaLandProvider::new);
	}
}
