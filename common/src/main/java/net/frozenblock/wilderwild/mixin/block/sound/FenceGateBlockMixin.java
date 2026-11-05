/*
 * Copyright 2026 FrozenBlock
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

package net.frozenblock.wilderwild.mixin.block.sound;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.frozenblock.wilderwild.registry.WWConfigPredicates;
import net.frozenblock.wilderwild.registry.WWSounds;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FenceGateBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(FenceGateBlock.class)
public class FenceGateBlockMixin {

	@ModifyExpressionValue(
		method = {
			"useWithoutItem",
			"neighborChanged"
		},
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/state/properties/WoodType;fenceGateOpen()Lnet/minecraft/sounds/SoundEvent;"
		)
	)
	public SoundEvent wilderWild$paleOakFenceGateOpenSoundInLevel(
		SoundEvent original,
		@Local(argsOnly = true) Level level
	) {
		return wilderWild$originalOrPaleOakOpenSound(level, original);
	}

	@ModifyExpressionValue(
		method = {
			"useWithoutItem",
			"neighborChanged",
		},
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/state/properties/WoodType;fenceGateClose()Lnet/minecraft/sounds/SoundEvent;"
		)
	)
	public SoundEvent wilderWild$paleOakFenceGateCloseSoundInLevel(
		SoundEvent original,
		@Local(argsOnly = true) Level level
	) {
		return wilderWild$originalOrPaleOakCloseSound(level, original);
	}

	@ModifyExpressionValue(
		method = "onExplosionHit",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/state/properties/WoodType;fenceGateOpen()Lnet/minecraft/sounds/SoundEvent;"
		)
	)
	public SoundEvent wilderWild$paleOakFenceGateOpenSoundInServerLevel(
		SoundEvent original,
		@Local(argsOnly = true) ServerLevel level
	) {
		return wilderWild$originalOrPaleOakOpenSound(level, original);
	}

	@ModifyExpressionValue(
		method = "onExplosionHit",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/state/properties/WoodType;fenceGateClose()Lnet/minecraft/sounds/SoundEvent;"
		)
	)
	public SoundEvent wilderWild$paleOakFenceGateCloseSoundInServerLevel(
		SoundEvent original,
		@Local(argsOnly = true) ServerLevel level
	) {
		return wilderWild$originalOrPaleOakCloseSound(level, original);
	}

	@Unique
	private SoundEvent wilderWild$originalOrPaleOakOpenSound(LevelAccessor level, SoundEvent original) {
		if (Block.class.cast(this) != Blocks.PALE_OAK_FENCE_GATE) return original;
		if (!WWConfigPredicates.testPaleOakWoodPredicate(level.registryAccess())) return original;

		return WWSounds.BLOCK_PALE_OAK_WOOD_FENCE_GATE_OPEN.get();
	}

	@Unique
	private SoundEvent wilderWild$originalOrPaleOakCloseSound(LevelAccessor level, SoundEvent original) {
		if (Block.class.cast(this) != Blocks.PALE_OAK_FENCE_GATE) return original;
		if (!WWConfigPredicates.testPaleOakWoodPredicate(level.registryAccess())) return original;

		return WWSounds.BLOCK_PALE_OAK_WOOD_FENCE_GATE_CLOSE.get();
	}
}
