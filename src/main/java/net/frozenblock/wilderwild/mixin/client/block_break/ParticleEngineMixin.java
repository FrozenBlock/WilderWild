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

package net.frozenblock.wilderwild.mixin.client.block_break;

import java.util.Optional;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.block.impl.FallingLeafUtil;
import net.frozenblock.wilderwild.config.WWAmbienceAndMiscConfig;
import net.frozenblock.wilderwild.tag.WWBlockTags;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.LeafLitterBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(ParticleEngine.class)
public abstract class ParticleEngineMixin {

	@Shadow
	@Final
	private RandomSource random;

	@Shadow
	public abstract @Nullable Particle createParticle(ParticleOptions particleOptions, double d, double e, double f, double g, double h, double i);

	@Inject(method = "destroy", at = @At(value = "HEAD"))
	public void wilderWild$spawnLeafParticlesOnDestroy(BlockPos pos, BlockState state, CallbackInfo info) {
		boolean litter = false;
		if (state.is(WWBlockTags.LEAF_LITTERS)) {
			litter = true;
			if (!WWAmbienceAndMiscConfig.Client.BREAKING_LEAF_LITTER_PARTICLES) return;
		} else if (!WWAmbienceAndMiscConfig.Client.BREAKING_LEAF_PARTICLES) {
			return;
		}

		Optional<FallingLeafUtil.FallingLeafData> optionalFallingLeafData = FallingLeafUtil.getFallingLeafData(state.getBlock());
		if (optionalFallingLeafData.isEmpty()) return;

		FallingLeafUtil.FallingLeafData fallingLeafData = optionalFallingLeafData.get();
		int count = !litter ? this.random.nextInt(2, 4) : state.getOptionalValue(LeafLitterBlock.AMOUNT).orElse(2);
		for (int i = 0; i < count; i++) {
			this.createParticle(
				FallingLeafUtil.createLeafParticleOptions(fallingLeafData, litter),
				pos.getX() + 0.5D + this.random.nextDouble() * 0.25D,
				pos.getY() + (!litter ? 0.5D + this.random.nextDouble() * 0.25D : 0.1D),
				pos.getZ() + 0.5D + this.random.nextDouble() * 0.25D,
				this.random.nextGaussian() * 0.05D,
				!litter ? this.random.nextGaussian() * 0.025D : (this.random.nextDouble() * 0.015D + 0.01D),
				this.random.nextGaussian() * 0.05D
			);
		}
	}

}
