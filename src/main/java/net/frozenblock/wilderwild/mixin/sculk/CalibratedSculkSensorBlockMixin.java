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

package net.frozenblock.wilderwild.mixin.sculk;

import net.frozenblock.wilderwild.block.entity.impl.SculkSensorInterface;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.CalibratedSculkSensorBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CalibratedSculkSensorBlock.class)
public abstract class CalibratedSculkSensorBlockMixin extends BaseEntityBlock {

	protected CalibratedSculkSensorBlockMixin(Properties settings) {
		super(settings);
	}

	@Inject(at = @At("HEAD"), method = "getTicker", cancellable = true)
	public <T extends BlockEntity> void wilderWild$overrideTicker(Level level, BlockState state, BlockEntityType<T> type, CallbackInfoReturnable<BlockEntityTicker<T>> info) {
		if (level.isClientSide) {
			info.setReturnValue(
				createTickerHelper(type, BlockEntityType.CALIBRATED_SCULK_SENSOR, (worldx, pos, statex, blockEntity) -> {
					if (blockEntity instanceof SculkSensorInterface sculkSensorInterface) sculkSensorInterface.wilderWild$tickClient(worldx, pos, statex);
				})
			);
		}
	}

}
