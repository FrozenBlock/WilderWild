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

package net.frozenblock.wilderwild.mixin.client.block.block_break;

import net.frozenblock.wilderwild.block.EchoGlassBlock;
import net.frozenblock.wilderwild.block.impl.SnowloggingUtils;
import net.mehvahdjukaar.candlelight.api.ClientOnly;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.neoforged.neoforge.common.extensions.IBlockStateExtension;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@ClientOnly
@Mixin(IBlockStateExtension.class)
public interface IBlockStateExtensionMixin {

	@Shadow
	BlockState self();

	@Inject(method = "onDestroyedByPlayer", at = @At("HEAD"), cancellable = true)
	default void wilderWild$onDestroyedByPlayer(
		Level level,
		BlockPos pos,
		Player player,
		ItemStack toolStack,
		boolean willHarvest,
		FluidState fluid,
		CallbackInfoReturnable<Boolean> info
	) {
		if (!level.isClientSide()) return;

		final BlockState state = this.self();

		if (SnowloggingUtils.isSnowlogged(state)) {
			level.setBlock(pos, state.setValue(SnowloggingUtils.SNOW_LAYERS, 0), 11);
			info.setReturnValue(true);
			return;
		}

		if (state.getBlock() instanceof EchoGlassBlock && EchoGlassBlock.canDamage(state) && player.gameMode().isCreative()) {
			final var silkTouch = level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.SILK_TOUCH);
			if (EnchantmentHelper.getItemEnchantmentLevel(silkTouch, player.getMainHandItem()) < 1) {
				EchoGlassBlock.setDamagedState(level, pos, state);
			}
			info.setReturnValue(true);
			return;
		}
	}
}
