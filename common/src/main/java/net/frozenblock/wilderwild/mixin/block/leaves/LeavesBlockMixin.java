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

package net.frozenblock.wilderwild.mixin.block.leaves;

import java.util.Optional;
import net.frozenblock.wilderwild.block.leaves.FallingLeafData;
import net.frozenblock.wilderwild.block.leaves.FallingLeafUtil;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LeavesBlock.class)
public abstract class LeavesBlockMixin extends Block {
	@Shadow
	@Final
	public static BooleanProperty PERSISTENT;

	public LeavesBlockMixin(Properties properties) {
		super(properties);
	}

	@Inject(method = "isRandomlyTicking", at = @At("HEAD"), cancellable = true)
	public void wilderWild$markRandomlyTickingIfHasFallingLeafLitter(BlockState state, CallbackInfoReturnable<Boolean> info) {
		final FallingLeafData fallingLeafData = state.getBlock().frozenLib$getAttached(FallingLeafUtil.FALLING_LEAF_DATA_KEY);
		if (fallingLeafData == null) return;

		final Optional<FallingLeafData.FallingLeafLitterData> fallingLeafLitterData = fallingLeafData.fallingLeafLitterData();
		if (fallingLeafLitterData.isPresent() && fallingLeafLitterData.get().fallChance() > 0F && state.hasProperty(PERSISTENT)) info.setReturnValue(!state.getValue(PERSISTENT));
	}
}
