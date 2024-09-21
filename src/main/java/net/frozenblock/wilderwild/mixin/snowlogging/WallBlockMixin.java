/*
 * Copyright 2024 FrozenBlock
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

package net.frozenblock.wilderwild.mixin.snowlogging;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import java.util.Map;
import net.frozenblock.wilderwild.block.impl.SnowloggingUtils;
import net.frozenblock.wilderwild.config.WWBlockConfig;
import net.frozenblock.wilderwild.registry.WWBlockStateProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WallBlock.class)
public abstract class WallBlockMixin extends Block {

	public WallBlockMixin(Properties properties) {
		super(properties);
	}

	@Unique
	@Override
	public boolean isRandomlyTicking(BlockState state) {
		return super.isRandomlyTicking(state) || SnowloggingUtils.isSnowlogged(state);
	}

	@WrapOperation(
		method = "getShape",
		at = @At(
			value = "INVOKE",
			target = "Ljava/util/Map;get(Ljava/lang/Object;)Ljava/lang/Object;"
		)
	)
	public Object wilderWild$getShape(Map instance, Object o, Operation<Object> original) {
		if (o instanceof BlockState blockState) {
			if (SnowloggingUtils.supportsSnowlogging(blockState)) {
				o = blockState.setValue(WWBlockStateProperties.SNOW_LAYERS, 0);
			}
		}
		return original.call(instance, o);
	}

	@WrapOperation(
		method = "getCollisionShape",
		at = @At(
			value = "INVOKE",
			target = "Ljava/util/Map;get(Ljava/lang/Object;)Ljava/lang/Object;"
		)
	)
	public Object wilderWild$getCollisionShape(Map instance, Object o, Operation<Object> original) {
		if (o instanceof BlockState blockState && SnowloggingUtils.supportsSnowlogging(blockState)) {
			o = blockState.setValue(WWBlockStateProperties.SNOW_LAYERS, 0);
		}
		return original.call(instance, o);
	}

	@ModifyExpressionValue(
		method = "getStateForPlacement",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/WallBlock;defaultBlockState()Lnet/minecraft/world/level/block/state/BlockState;"
		)
	)
	public BlockState wilderWild$getStateForPlacement(BlockState original, BlockPlaceContext context) {
		return SnowloggingUtils.getSnowPlacementState(original, context);
	}

	@Unique
	@Override
	public void destroy(LevelAccessor level, BlockPos pos, BlockState state) {
		if (SnowloggingUtils.isSnowlogged(state)) {
			super.destroy(level, pos, SnowloggingUtils.getSnowEquivalent(state));
		} else {
			super.destroy(level, pos, state);
		}
	}

	@Unique
	@Override
	public void playerDestroy(@NotNull Level level, @NotNull Player player, @NotNull BlockPos pos, @NotNull BlockState state, @Nullable BlockEntity blockEntity, @NotNull ItemStack stack) {
		if (SnowloggingUtils.isSnowlogged(state)) {
			BlockState snowEquivalent = SnowloggingUtils.getSnowEquivalent(state);
			if (player.hasCorrectToolForDrops(snowEquivalent)) {
				super.playerDestroy(level, player, pos, snowEquivalent, blockEntity, stack);
			}
		} else {
			super.playerDestroy(level, player, pos, state, blockEntity, stack);
		}
	}

	@Inject(method = "createBlockStateDefinition", at = @At(value = "TAIL"))
	public void wilderWild$createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder, CallbackInfo info) {
		if (!(WWBlockConfig.get().snowlogging.snowlogWalls && WWBlockConfig.get().snowlogging.snowlogging)) return;
		builder.add(SnowloggingUtils.SNOW_LAYERS);
	}

}
