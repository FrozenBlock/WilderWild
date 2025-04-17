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
 * You should have received a copy of the FrozenBlock Modding oasis License
 * along with this program; if not, see <https://github.com/FrozenBlock/Licenses>.
 */

package net.frozenblock.wilderwild.mixin.block.block_break;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.frozenblock.wilderwild.block.EchoGlassBlock;
import net.frozenblock.wilderwild.block.MesogleaBlock;
import net.frozenblock.wilderwild.block.impl.SnowloggingUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerPlayerGameMode;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ServerPlayerGameMode.class)
public abstract class ServerPlayerGameModeMixin {

	@Shadow
	public abstract GameType getGameModeForPlayer();

	@Shadow
	@Final
	protected ServerPlayer player;

	@WrapOperation(
		method = "destroyBlock",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/server/level/ServerLevel;removeBlock(Lnet/minecraft/core/BlockPos;Z)Z"
		)
	)
	public boolean wilderWild$destroyBlockB(
		ServerLevel instance, BlockPos pos, boolean b, Operation<Boolean> original,
		@Local(ordinal = 1) BlockState destroyedState
	) {
		if (SnowloggingUtils.isSnowlogged(destroyedState)) {
			instance.setBlockAndUpdate(pos, destroyedState.setValue(SnowloggingUtils.SNOW_LAYERS, 0));
			return true;
		} else if (destroyedState.getBlock() instanceof MesogleaBlock) {
			instance.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
			return true;
		} else if (destroyedState.getBlock() instanceof EchoGlassBlock && EchoGlassBlock.canDamage(destroyedState) && !this.getGameModeForPlayer().isCreative()) {
			var silkTouch = instance.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.SILK_TOUCH);
			if (EnchantmentHelper.getItemEnchantmentLevel(silkTouch, this.player.getMainHandItem()) < 1) {
				EchoGlassBlock.setDamagedState(instance, pos, destroyedState);
				return true;
			}
		}
		return original.call(instance, pos, b);
	}

}
