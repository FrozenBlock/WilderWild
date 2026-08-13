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

package net.frozenblock.wilderwild.block.impl.client;

import net.frozenblock.lib.particle.api.ParticleSpawner;
import net.frozenblock.lib.platform.api.ClientOnly;
import net.frozenblock.wilderwild.registry.WWParticleTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;

@ClientOnly
public final class HugePaleMushroomParticleSpawner extends ParticleSpawner {
	public static final HugePaleMushroomParticleSpawner INSTANCE = new HugePaleMushroomParticleSpawner();

	private HugePaleMushroomParticleSpawner() {
		super(
			() -> Minecraft.getInstance().options.improvedTransparency().get() ? 0.333F : 0.25F,
			7,
			2,
			-2,
			-1
		);
	}

	@Override
	public boolean canSpawnAtPos(Level level, BlockPos pos) {
		return true;
	}

	@Override
	public ParticleOptions selectParticleOptions(Level level, BlockPos pos, RandomSource random) {
		return Minecraft.getInstance().options.improvedTransparency().get()
			? random.nextBoolean() ? WWParticleTypes.PALE_FOG_SMALL.get() : WWParticleTypes.PALE_FOG.get()
			: random.nextFloat() <= 0.65F ? WWParticleTypes.PALE_FOG_SMALL.get() : WWParticleTypes.PALE_FOG.get();
	}
}
