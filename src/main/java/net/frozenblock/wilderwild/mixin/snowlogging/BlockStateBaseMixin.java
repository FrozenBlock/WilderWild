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
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import java.util.ArrayList;
import java.util.List;
import net.frozenblock.wilderwild.block.impl.SnowloggingUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockBehaviour.BlockStateBase.class)
public abstract class BlockStateBaseMixin {

	@Shadow
	protected abstract BlockState asState();

	@Shadow
	public abstract Block getBlock();

	@ModifyReturnValue(
		method = "getCollisionShape(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/phys/shapes/CollisionContext;)Lnet/minecraft/world/phys/shapes/VoxelShape;",
		at = @At("RETURN")
	)
	public VoxelShape wilderWild$getCollisionShape(VoxelShape original, BlockGetter level, BlockPos pos, CollisionContext context) {
		BlockState blockState = this.asState();
		if (SnowloggingUtils.isSnowlogged(blockState)) {
			return Shapes.or(original, SnowloggingUtils.getSnowEquivalent(blockState).getCollisionShape(level, pos, context));
		}
		return original;
	}

	@ModifyReturnValue(
		method = "getShape(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/phys/shapes/CollisionContext;)Lnet/minecraft/world/phys/shapes/VoxelShape;",
		at = @At("RETURN")
	)
	public VoxelShape wilderWild$getShape(VoxelShape original, BlockGetter level, BlockPos pos, CollisionContext context) {
		BlockState blockState = this.asState();
		if (SnowloggingUtils.isSnowlogged(blockState)) {
			return Shapes.or(SnowloggingUtils.getSnowEquivalent(blockState).getShape(level, pos, context), original);
		}
		return original;
	}

	@ModifyReturnValue(method = "getVisualShape", at = @At("RETURN"))
	public VoxelShape wilderWild$getVisualShape(VoxelShape original, BlockGetter level, BlockPos pos, CollisionContext context) {
		BlockState blockState = this.asState();
		if (SnowloggingUtils.isSnowlogged(blockState)) {
			return Shapes.or(original, SnowloggingUtils.getSnowEquivalent(blockState).getVisualShape(level, pos, context));
		}
		return original;
	}

	@ModifyReturnValue(method = "getInteractionShape", at = @At("RETURN"))
	public VoxelShape wilderWild$getInteractionShape(VoxelShape original, BlockGetter level, BlockPos pos) {
		BlockState blockState = this.asState();
		if (SnowloggingUtils.isSnowlogged(blockState)) {
			return Shapes.or(original, SnowloggingUtils.getSnowEquivalent(blockState).getInteractionShape(level, pos));
		}
		return original;
	}

	@ModifyReturnValue(method = "getBlockSupportShape", at = @At("RETURN"))
	public VoxelShape wilderWild$getBlockSupportShape(VoxelShape original, BlockGetter level, BlockPos pos) {
		BlockState blockState = this.asState();
		if (SnowloggingUtils.isSnowlogged(blockState)) {
			return Shapes.or(original, SnowloggingUtils.getSnowEquivalent(blockState).getBlockSupportShape(level, pos));
		}
		return original;
	}

	@ModifyReturnValue(method = "getOcclusionShape", at = @At("RETURN"))
	public VoxelShape wilderWild$getOcclusionShape(VoxelShape original, BlockGetter level, BlockPos pos) {
		BlockState blockState = this.asState();
		if (SnowloggingUtils.isSnowlogged(blockState)) {
			return Shapes.or(original, SnowloggingUtils.getSnowEquivalent(blockState).getOcclusionShape(level, pos));
		}
		return original;
	}

	@ModifyReturnValue(method = "getMapColor", at = @At("RETURN"))
	public MapColor wilderWild$getMapColor(MapColor original, BlockGetter level, BlockPos pos) {
		if (SnowloggingUtils.isOriginalBlockCovered(this.asState(), level, pos)) {
			return Blocks.SNOW.defaultMapColor();
		}
		return original;
	}

	@ModifyReturnValue(method = "getDrops", at = @At("RETURN"))
	public List<ItemStack> wilderWild$getDrops(List<ItemStack> original, LootParams.Builder lootParams) {
		BlockState state = this.asState();
		if (!SnowloggingUtils.isSnowlogged(state)) {
			return original;
		}
		if (lootParams.getOptionalParameter(LootContextParams.THIS_ENTITY) instanceof Player player) {
			BlockPos blockPos = BlockPos.containing(lootParams.getOptionalParameter(LootContextParams.ORIGIN));
			Level level = lootParams.getLevel();
			if (SnowloggingUtils.shouldHitSnow(state, blockPos, level, player)) {
				return SnowloggingUtils.getSnowEquivalent(state).getDrops(lootParams);
			} else {
				return SnowloggingUtils.getStateWithoutSnow(state).getDrops(lootParams);
			}
		} else {
			List<ItemStack> finalList = new ArrayList<>(original);
			BlockState snowEquivalent = SnowloggingUtils.getSnowEquivalent(state);
			finalList.addAll(snowEquivalent.getDrops(lootParams));
			return finalList;
		}
	}

	@WrapOperation(
		method = "getDestroyProgress",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/Block;getDestroyProgress(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;)F"
		)
	)
	public float wilderWild$getDestroyProgress(Block instance, BlockState state, Player player, BlockGetter blockGetter, BlockPos pos, Operation<Float> original) {
		if (SnowloggingUtils.isSnowlogged(state)) {
			if (SnowloggingUtils.shouldHitSnow(state, pos, (Level) blockGetter, player)) {
				state = SnowloggingUtils.getSnowEquivalent(state);
				instance = state.getBlock();
			} else {
				state = SnowloggingUtils.getStateWithoutSnow(state);
			}
		}
		return original.call(instance, state, player, blockGetter, pos);
	}

	@Inject(method = "randomTick", at = @At("HEAD"))
	public void wilderWild$randomTick(ServerLevel level, BlockPos pos, RandomSource random, CallbackInfo info) {
		SnowloggingUtils.onRandomTick(this.asState(), level, pos);
	}

	@Inject(method = "useItemOn", at = @At("HEAD"), cancellable = true)
	public void wilderWild$useItemOn(ItemStack stack, Level level, Player player, InteractionHand hand, BlockHitResult hitResult, CallbackInfoReturnable<ItemInteractionResult> info) {
		if (SnowloggingUtils.isOriginalBlockCovered(this.asState(), level, hitResult.getBlockPos())) {
			info.setReturnValue(ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION);
		}
	}

	@Inject(method = "useWithoutItem", at = @At("HEAD"), cancellable = true)
	public void wilderWild$useWithoutItem(Level level, Player player, BlockHitResult hitResult, CallbackInfoReturnable<InteractionResult> info) {
		if (SnowloggingUtils.isOriginalBlockCovered(this.asState(), level, hitResult.getBlockPos())) {
			info.setReturnValue(InteractionResult.PASS);
		}
	}

	@Inject(method = "attack", at = @At("HEAD"), cancellable = true)
	public void wilderWild$attack(Level level, BlockPos pos, Player player, CallbackInfo info) {
		if (SnowloggingUtils.isOriginalBlockCovered(this.asState(), level, pos)) {
			info.cancel();
		}
	}

	@Inject(method = "canBeReplaced(Lnet/minecraft/world/item/context/BlockPlaceContext;)Z", at = @At("HEAD"), cancellable = true)
	public void wilderWild$canBeReplaced(BlockPlaceContext context, CallbackInfoReturnable<Boolean> info) {
		BlockState state;
		if (SnowloggingUtils.isItemSnow(context.getItemInHand()) && SnowloggingUtils.supportsSnowlogging(state = this.asState())) {
			if (SnowloggingUtils.canBeReplacedWithSnow(state, context)) {
				info.setReturnValue(true);
			} else {
				info.setReturnValue(false);
			}
		}
	}

	@WrapOperation(
		method = "updateShape",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/Block;updateShape(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/Direction;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;"
		)
	)
	public BlockState wilderWild$updateShape(Block instance, BlockState state, Direction direction, BlockState neighborState, LevelAccessor levelAccessor, BlockPos pos, BlockPos neighborPos, Operation<BlockState> original) {
		BlockState newState = original.call(instance, SnowloggingUtils.onUpdateShape(state, levelAccessor, pos), direction, neighborState, levelAccessor, pos, neighborPos);
		BlockState snowEquivalent;
		if (newState.isAir() && SnowloggingUtils.isSnowlogged(state) && Blocks.SNOW.canSurvive((snowEquivalent = SnowloggingUtils.getSnowEquivalent(state)), levelAccessor, pos)) {
			state = snowEquivalent;
			levelAccessor.destroyBlock(pos, true);
		} else {
			state = newState;
		}
		return state;
	}

	@WrapOperation(method = "propagatesSkylightDown",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/Block;propagatesSkylightDown(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;)Z"
		)
	)
	public boolean wilderWild$propagatesSkylightDown(Block instance, BlockState blockState, BlockGetter blockGetter, BlockPos pos, Operation<Boolean> original) {
		return original.call(instance, blockState, blockGetter, pos) && !(SnowloggingUtils.isSnowlogged(blockState) && SnowloggingUtils.getSnowLayers(blockState) >= SnowloggingUtils.MAX_LAYERS);
	}

	@WrapOperation(
		method = "<init>",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/Block;useShapeForLightOcclusion(Lnet/minecraft/world/level/block/state/BlockState;)Z"
		)
	)
	public boolean wilderWild$useShapeForLightOcclusion(Block instance, BlockState blockState, Operation<Boolean> original) {
		return original.call(instance, blockState) || SnowloggingUtils.isSnowlogged(blockState);
	}

	// Occludes if it normally occludes, or if the block is fully snowlogged.
	// Done this way to get around a pair of bugs.
	@ModifyExpressionValue(
		method = "<init>",
		at = @At(
			value = "FIELD",
			target = "Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;canOcclude:Z"
		)
	)
	public boolean wilderWild$canOcclude(boolean original) {
		BlockState blockState = this.asState();
		return original || (SnowloggingUtils.isSnowlogged(blockState) && SnowloggingUtils.getSnowLayers(blockState) >= SnowloggingUtils.MAX_LAYERS);
	}

	@WrapOperation(
		method = "getSoundType",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/Block;getSoundType(Lnet/minecraft/world/level/block/state/BlockState;)Lnet/minecraft/world/level/block/SoundType;"
		)
	)
	public SoundType wilderWild$getSoundType(Block instance, BlockState blockState, Operation<SoundType> original) {
		if (SnowloggingUtils.isSnowlogged(blockState)) {
			return SnowloggingUtils.getSnowEquivalent(blockState).getSoundType();
		}
		return original.call(instance, blockState);
	}
}
