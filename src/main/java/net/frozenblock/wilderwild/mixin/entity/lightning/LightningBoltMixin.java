/*
 * Copyright 2023 FrozenBlock
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

package net.frozenblock.wilderwild.mixin.entity.lightning;

import net.frozenblock.wilderwild.world.additions.feature.WilderMiscConfigured;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.level.chunk.ChunkGenerator;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LightningBolt.class)
public class LightningBoltMixin {

	@Shadow
	private boolean visualOnly;

	@Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LightningBolt;spawnFire(I)V", ordinal = 1, shift = At.Shift.AFTER))
	private void wilderWild$tick(CallbackInfo info) {
		this.wilderWild$scorchSand(LightningBolt.class.cast(this));
	}

	@Unique
	private void wilderWild$scorchSand(@NotNull LightningBolt bolt) {
		if (this.visualOnly || bolt.level().isClientSide) {
			return;
		}
		if (bolt.level() instanceof ServerLevel serverLevel) {
			BlockPos blockPos = this.getStrikePosition();
			if (bolt.level().getBlockState(blockPos).is(BlockTags.SAND)) {
				ChunkGenerator chunkGenerator = serverLevel.getChunkSource().getGenerator();
				RandomSource randomSource = serverLevel.getRandom();
				WilderMiscConfigured.SCORCHED_SAND_DISK_LIGHTNING.getConfiguredFeature(serverLevel).place(serverLevel, chunkGenerator, randomSource, blockPos);
				WilderMiscConfigured.SCORCHED_RED_SAND_DISK_LIGHTNING.getConfiguredFeature(serverLevel).place(serverLevel, chunkGenerator, randomSource, blockPos);
			}
		}
	}

	@Shadow
	private BlockPos getStrikePosition() {
		throw new AssertionError("Mixin injection failed - Wilder Wild LightningBoltMixin.");
	}
}
