/*
 * Copyright 2022-2023 FrozenBlock
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

package net.frozenblock.wilderwild.mixin.server.projectile;

import net.frozenblock.wilderwild.config.ItemConfig;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractArrow.class)
public class AbstractArrowMixin {

	@Inject(method = "onHitBlock", at = @At("HEAD"))
	public void wilderWild$sendProjectileBreakParticles(BlockHitResult blockHitResult, CallbackInfo info) {
		if (ItemConfig.get().projectileBreakParticles) {
			AbstractArrow arrow = AbstractArrow.class.cast(this);
			if (!arrow.level().isClientSide) {
				if (arrow.level() instanceof ServerLevel server) {
					BlockState state = server.getBlockState(blockHitResult.getBlockPos());
					double d = arrow.getDeltaMovement().length(); //The distance the arrow travels on this given tick.
					int particleCalc = ((int) ((d * d) * 1.5));
					if (particleCalc > 1 || (particleCalc == 1 && arrow.level().random.nextBoolean())) {
						server.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, state), blockHitResult.getLocation().x(), blockHitResult.getLocation().y(), blockHitResult.getLocation().z(), particleCalc == 1 ? 1 : server.random.nextIntBetweenInclusive(1, particleCalc), 0, 0, 0, 0.05D);
					}
				}
			}
		}
	}

}
