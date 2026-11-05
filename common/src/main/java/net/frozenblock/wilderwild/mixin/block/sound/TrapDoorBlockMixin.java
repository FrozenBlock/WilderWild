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
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.TrapDoorBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(TrapDoorBlock.class)
public class TrapDoorBlockMixin {

	@ModifyExpressionValue(
		method = "playSound",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/state/properties/BlockSetType;trapdoorOpen()Lnet/minecraft/sounds/SoundEvent;"
		)
	)
	public SoundEvent wilderWild$paleOakTrapDoorOpenSound(
		SoundEvent original,
		@Local(argsOnly = true) Level level
	) {
		if (Block.class.cast(this) != Blocks.PALE_OAK_TRAPDOOR) return original;
		if (!WWConfigPredicates.testPaleOakWoodPredicate(level.registryAccess())) return original;

		return WWSounds.BLOCK_PALE_OAK_WOOD_TRAPDOOR_OPEN.get();
	}

	@ModifyExpressionValue(
		method = "playSound",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/state/properties/BlockSetType;trapdoorClose()Lnet/minecraft/sounds/SoundEvent;"
		)
	)
	public SoundEvent wilderWild$paleOakTrapDoorCloseSound(
		SoundEvent original,
		@Local(argsOnly = true) Level level
	) {
		if (Block.class.cast(this) != Blocks.PALE_OAK_TRAPDOOR) return original;
		if (!WWConfigPredicates.testPaleOakWoodPredicate(level.registryAccess())) return original;

		return WWSounds.BLOCK_PALE_OAK_WOOD_TRAPDOOR_CLOSE.get();
	}
}
