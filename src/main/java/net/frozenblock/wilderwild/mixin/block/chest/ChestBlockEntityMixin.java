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

package net.frozenblock.wilderwild.mixin.block.chest;

import net.frozenblock.wilderwild.block.entity.StoneChestBlockEntity;
import net.frozenblock.wilderwild.config.WWBlockConfig;
import net.frozenblock.wilderwild.registry.WWSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.CompoundContainer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.ContainerOpenersCounter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChestBlockEntity.class)
public class ChestBlockEntityMixin {

	@Shadow
	@Final
	@Mutable
	private ContainerOpenersCounter openersCounter;

	@Inject(
		method = "<init>(Lnet/minecraft/world/level/block/entity/BlockEntityType;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)V",
		at = @At("TAIL")
	)
	public void wilderWild$customStoneChestOpenersCounter(BlockEntityType type, BlockPos worldPosition, BlockState blockState, CallbackInfo info) {
		if (ChestBlockEntity.class.cast(this) instanceof StoneChestBlockEntity stoneChestBlockEntity) {
			this.openersCounter = new ContainerOpenersCounter() {

				@Override
				protected void onOpen(Level level, BlockPos pos, BlockState state) {
				}

				@Override
				protected void onClose(Level level, BlockPos pos, BlockState state) {
				}

				@Override
				protected void openerCountChanged(Level level, BlockPos pos, BlockState state, int count, int openCount) {
					stoneChestBlockEntity.signalOpenCount(level, pos, state, count, openCount);
				}

				@Override
				public boolean isOwnContainer(Player player) {
					if (player.containerMenu instanceof ChestMenu chest) {
						Container inventory = chest.getContainer();
						return inventory == stoneChestBlockEntity || inventory instanceof CompoundContainer container && container.contains(stoneChestBlockEntity);
					}
					return false;
				}
			};
		}
	}

	@ModifyVariable(method = "playSound", at = @At("HEAD"), argsOnly = true)
	private static SoundEvent wilderWild$playSound(SoundEvent event, Level level, BlockPos worldPosition, BlockState blockState) {
		if (blockState.getFluidState().is(Fluids.WATER) && WWBlockConfig.CHEST_BUBBLING.get()) {
			if (event == SoundEvents.CHEST_OPEN) return WWSounds.BLOCK_CHEST_OPEN_UNDERWATER;
			if (event == SoundEvents.CHEST_CLOSE) return WWSounds.BLOCK_CHEST_CLOSE_UNDERWATER;
			if (event == SoundEvents.COPPER_CHEST_OPEN) return WWSounds.BLOCK_COPPER_CHEST_OPEN_UNDERWATER;
			if (event == SoundEvents.COPPER_CHEST_CLOSE) return WWSounds.BLOCK_COPPER_CHEST_CLOSE_UNDERWATER;
			if (event == SoundEvents.COPPER_CHEST_OXIDIZED_OPEN) return WWSounds.BLOCK_COPPER_CHEST_OXIDIZED_OPEN_UNDERWATER;
			if (event == SoundEvents.COPPER_CHEST_OXIDIZED_CLOSE) return WWSounds.BLOCK_COPPER_CHEST_OXIDIZED_CLOSE_UNDERWATER;
			if (event == SoundEvents.COPPER_CHEST_WEATHERED_OPEN) return WWSounds.BLOCK_COPPER_CHEST_WEATHERED_OPEN_UNDERWATER;
			if (event == SoundEvents.COPPER_CHEST_WEATHERED_CLOSE) return WWSounds.BLOCK_COPPER_CHEST_WEATHERED_CLOSE_UNDERWATER;
		}
		return event;
	}
}
