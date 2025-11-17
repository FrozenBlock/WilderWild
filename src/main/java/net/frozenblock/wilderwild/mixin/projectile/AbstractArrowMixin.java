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

package net.frozenblock.wilderwild.mixin.projectile;

import net.frozenblock.wilderwild.config.WWItemConfig;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractArrow.class)
public class AbstractArrowMixin {

	@Shadow
	@Nullable
	private BlockState lastState;

	@Inject(
		method = "onHitBlock",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/entity/projectile/AbstractArrow;getWeaponItem()Lnet/minecraft/world/item/ItemStack;",
			ordinal = 0
		)
	)
	public void wilderWild$sendProjectileBreakParticles(BlockHitResult hitResult, CallbackInfo info) {
		if (this.lastState == null ||  !WWItemConfig.get().projectileBreakParticles) return;

		final AbstractArrow arrow = AbstractArrow.class.cast(this);
		if (!(arrow.level() instanceof ServerLevel serverLevel)) return;

		final int particleCalc = ((int) (arrow.getDeltaMovement().lengthSqr() * 1.5D));
		if (!(particleCalc > 1 || (particleCalc == 1 && serverLevel.random.nextBoolean()))) return;

		serverLevel.sendParticles(
			new BlockParticleOption(ParticleTypes.BLOCK, this.lastState),
			hitResult.getLocation().x(),
			hitResult.getLocation().y(),
			hitResult.getLocation().z(),
			particleCalc == 1 ? 1 : serverLevel.random.nextIntBetweenInclusive(1, particleCalc),
			0D, 0D, 0D,
			0.05D
		);
	}

}
