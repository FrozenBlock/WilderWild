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

package net.frozenblock.wilderwild.mixin.entity.lightning;

import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.frozenblock.wilderwild.block.ScorchedBlock;
import net.frozenblock.wilderwild.config.WWEntityConfig;
import net.frozenblock.wilderwild.networking.packet.WWLightningStrikePacket;
import net.frozenblock.wilderwild.worldgen.feature.configured.WWMiscConfigured;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.level.block.state.BlockState;
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

	@Unique
	private int wilderWild$age = 0;

	@Inject(method = "tick", at = @At(value = "HEAD"))
	private void wilderWild$tick(
		CallbackInfo info,
		@Share("wilderWild$strikePos") LocalRef<BlockPos> strikePosLocalRef,
		@Share("wilderWild$strikeState") LocalRef<BlockState> strikeStateLocalRef
	) {
		if (LightningBolt.class.cast(this).level() instanceof ServerLevel serverLevel) {
			BlockPos blockPos = this.getStrikePosition();
			BlockState state = serverLevel.getBlockState(blockPos);
			WWLightningStrikePacket.sendToAll(
				LightningBolt.class.cast(this),
				state,
				this.wilderWild$age
			);
			this.wilderWild$age += 1;
			strikePosLocalRef.set(blockPos);
			strikeStateLocalRef.set(state);
		}
	}

	@Inject(
		method = "tick",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/entity/LightningBolt;spawnFire(I)V"
		)
	)
	private void wilderWild$scorchTheSand(
		CallbackInfo info,
		@Share("wilderWild$strikePos") LocalRef<BlockPos> strikePosLocalRef,
		@Share("wilderWild$strikeState") LocalRef<BlockState> strikeStateLocalRef
	) {
		this.wilderWild$scorchSand(LightningBolt.class.cast(this), strikePosLocalRef.get(), strikeStateLocalRef.get());
	}

	@Unique
	private void wilderWild$scorchSand(@NotNull LightningBolt bolt, BlockPos strikePose, BlockState strikeState) {
		if (!this.visualOnly && bolt.level() instanceof ServerLevel serverLevel && WWEntityConfig.get().lightning.lightningScorchesSand && strikeState.is(BlockTags.SAND)) {
			ChunkGenerator chunkGenerator = serverLevel.getChunkSource().getGenerator();
			RandomSource randomSource = serverLevel.getRandom();
			WWMiscConfigured.SCORCHED_SAND_DISK_LIGHTNING.getConfiguredFeature(serverLevel).place(serverLevel, chunkGenerator, randomSource, strikePose);
			WWMiscConfigured.SCORCHED_RED_SAND_DISK_LIGHTNING.getConfiguredFeature(serverLevel).place(serverLevel, chunkGenerator, randomSource, strikePose);
			ScorchedBlock.scorch(strikeState, serverLevel, strikePose);
		}
	}

	@Shadow
	private BlockPos getStrikePosition() {
		throw new AssertionError("Mixin injection failed - Wilder Wild LightningBoltMixin.");
	}
}
