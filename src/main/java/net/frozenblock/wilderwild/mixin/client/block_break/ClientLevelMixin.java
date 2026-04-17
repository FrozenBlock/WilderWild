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

package net.frozenblock.wilderwild.mixin.client.block_break;

import java.util.Optional;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.block.impl.FallingLeafUtil;
import net.frozenblock.wilderwild.config.WWAmbienceAndMiscConfig;
import net.frozenblock.wilderwild.tag.WWBlockTags;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.LeafLitterBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(ClientLevel.class)
public class ClientLevelMixin {

	@Shadow
	@Final
	private Minecraft minecraft;

	@Inject(
		method = "addDestroyBlockEffect",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/state/BlockState;getShape(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/phys/shapes/VoxelShape;"
		)
	)
	public void wilderWild$spawnLeafParticlesOnDestroy(BlockPos pos, BlockState blockState, CallbackInfo info) {
		boolean litter = false;
		if (blockState.is(WWBlockTags.LEAF_LITTERS)) {
			litter = true;
			if (!WWAmbienceAndMiscConfig.BREAKING_LEAF_LITTER_PARTICLES.get()) return;
		} else if (!WWAmbienceAndMiscConfig.BREAKING_LEAF_PARTICLES.get()) {
			return;
		}

		final Optional<FallingLeafUtil.FallingLeafData> optionalFallingLeafData = FallingLeafUtil.getFallingLeafData(blockState.getBlock());
		if (optionalFallingLeafData.isEmpty()) return;

		final RandomSource random = ClientLevel.class.cast(this).getRandom();
		final FallingLeafUtil.FallingLeafData fallingLeafData = optionalFallingLeafData.get();
		final int count = !litter ? random.nextInt(2, 4) : blockState.getOptionalValue(LeafLitterBlock.AMOUNT).orElse(2);
		for (int i = 0; i < count; i++) {
			this.minecraft.particleEngine.createParticle(
				FallingLeafUtil.createLeafParticleOptions(fallingLeafData, litter),
				pos.getX() + 0.5D + random.nextDouble() * 0.25D,
				pos.getY() + (!litter ? 0.5D + random.nextDouble() * 0.25D : 0.1D),
				pos.getZ() + 0.5D + random.nextDouble() * 0.25D,
				random.nextGaussian() * 0.05D,
				!litter ? random.nextGaussian() * 0.025D : (random.nextDouble() * 0.015D + 0.01D),
				random.nextGaussian() * 0.05D
			);
		}
	}

}
