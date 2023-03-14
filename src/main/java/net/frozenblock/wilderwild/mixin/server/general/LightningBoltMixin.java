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

package net.frozenblock.wilderwild.mixin.server.general;

import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.world.additions.feature.WilderMiscConfigured;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.registries.VanillaRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
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

	private void wilderWild$scorchSand(LightningBolt bolt) {
		if (this.visualOnly || bolt.level.isClientSide) {
			return;
		}
		if (bolt.level instanceof ServerLevel serverLevel) {
			BlockPos blockPos = this.getStrikePosition();
			BlockState strikeState = bolt.level.getBlockState(blockPos);
			if (strikeState.is(Blocks.SAND) || strikeState.is(RegisterBlocks.SCORCHED_SAND)) {
				WilderMiscConfigured.SCORCHED_SAND_DISK_LIGHTNING.getConfiguredFeatureOrThrow().place(serverLevel, serverLevel.getChunkSource().getGenerator(), serverLevel.getRandom(), blockPos);
			}
			if (strikeState.is(Blocks.RED_SAND) || strikeState.is(RegisterBlocks.SCORCHED_RED_SAND)) {
				WilderMiscConfigured.SCORCHED_RED_SAND_DISK_LIGHTNING.getConfiguredFeatureOrThrow().place(serverLevel, serverLevel.getChunkSource().getGenerator(), serverLevel.getRandom(), blockPos);
			}
		}
	}

	@Shadow
	private BlockPos getStrikePosition() {
		throw new AssertionError("Mixin injection failed - Wilder Wild LightningBoltMixin.");
	}
}
