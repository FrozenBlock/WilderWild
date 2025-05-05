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
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
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

	@ModifyReturnValue(
		method = "getCollisionShape(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/phys/shapes/CollisionContext;)Lnet/minecraft/world/phys/shapes/VoxelShape;",
		at = @At("RETURN")
	)
	public VoxelShape wilderWild$getCollisionShape(VoxelShape original, BlockGetter level, BlockPos pos, CollisionContext context) {
		BlockState blockState = this.asState();
		if (SnowloggingUtils.isSnowlogged(blockState)) return Shapes.or(original, SnowloggingUtils.getSnowEquivalent(blockState).getCollisionShape(level, pos, context));
		return original;
	}

	@ModifyReturnValue(
		method = "getShape(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/phys/shapes/CollisionContext;)Lnet/minecraft/world/phys/shapes/VoxelShape;",
		at = @At("RETURN")
	)
	public VoxelShape wilderWild$getShape(VoxelShape original, BlockGetter level, BlockPos pos, CollisionContext context) {
		BlockState blockState = this.asState();
		if (SnowloggingUtils.isSnowlogged(blockState)) return SnowloggingUtils.getSnowEquivalent(blockState).getShape(level, pos, context);
		return original;
	}

	@ModifyReturnValue(method = "getVisualShape", at = @At("RETURN"))
	public VoxelShape wilderWild$getVisualShape(VoxelShape original, BlockGetter level, BlockPos pos, CollisionContext context) {
		BlockState blockState = this.asState();
		if (SnowloggingUtils.isSnowlogged(blockState)) return Shapes.or(original, SnowloggingUtils.getSnowEquivalent(blockState).getVisualShape(level, pos, context));
		return original;
	}

	@ModifyReturnValue(method = "getInteractionShape", at = @At("RETURN"))
	public VoxelShape wilderWild$getInteractionShape(VoxelShape original, BlockGetter level, BlockPos pos) {
		BlockState blockState = this.asState();
		if (SnowloggingUtils.isSnowlogged(blockState)) return Shapes.or(original, SnowloggingUtils.getSnowEquivalent(blockState).getInteractionShape(level, pos));
		return original;
	}

	@ModifyReturnValue(method = "getBlockSupportShape", at = @At("RETURN"))
	public VoxelShape wilderWild$getBlockSupportShape(VoxelShape original, BlockGetter level, BlockPos pos) {
		BlockState blockState = this.asState();
		if (SnowloggingUtils.isSnowlogged(blockState)) return Shapes.or(original, SnowloggingUtils.getSnowEquivalent(blockState).getBlockSupportShape(level, pos));
		return original;
	}

	@ModifyReturnValue(method = "getOcclusionShape", at = @At("RETURN"))
	public VoxelShape wilderWild$getOcclusionShape(VoxelShape original) {
		BlockState blockState = this.asState();
		if (SnowloggingUtils.isSnowlogged(blockState)) return Shapes.or(original, SnowloggingUtils.getSnowEquivalent(blockState).getOcclusionShape());
		return original;
	}

	@ModifyReturnValue(method = "getMapColor", at = @At("RETURN"))
	public MapColor wilderWild$getMapColor(MapColor original, BlockGetter level, BlockPos pos) {
		if (SnowloggingUtils.isOriginalBlockCovered(this.asState(), level, pos)) return Blocks.SNOW.defaultMapColor();
		return original;
	}

	@ModifyReturnValue(method = "getDrops", at = @At("RETURN"))
	public List<ItemStack> wilderWild$getDrops(List<ItemStack> original, LootParams.Builder lootParams) {
		BlockState state = this.asState();
		if (SnowloggingUtils.isSnowlogged(state) && !(lootParams.getOptionalParameter(LootContextParams.THIS_ENTITY) instanceof Player)) {
			List<ItemStack> finalList = new ArrayList<>(original);
			BlockState snowEquivalent = SnowloggingUtils.getSnowEquivalent(state);
			finalList.addAll(snowEquivalent.getDrops(lootParams));
			return finalList;
		}
		return original;
	}

	@ModifyReturnValue(method = "getDestroySpeed", at = @At("RETURN"))
	public float wilderWild$getDestroySpeed(float original, BlockGetter level, BlockPos pos) {
		BlockState state = this.asState();
		if (SnowloggingUtils.isSnowlogged(state)) return SnowloggingUtils.getSnowDestroySpeed(state, level, pos);
		return original;
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
			state = SnowloggingUtils.getSnowEquivalent(state);
			instance = state.getBlock();
		}
		return original.call(instance, state, player, blockGetter, pos);
	}

	@WrapOperation(
		method = "randomTick",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/Block;randomTick(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/core/BlockPos;Lnet/minecraft/util/RandomSource;)V"
		)
	)
	public void wilderWild$randomTick(Block instance, BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource, Operation<Void> original) {
		SnowloggingUtils.onRandomTick(blockState, serverLevel, blockPos);
		original.call(instance, blockState, serverLevel, blockPos, randomSource);
	}

	@Inject(method = "useItemOn", at = @At("HEAD"), cancellable = true)
	public void wilderWild$useItemOn(ItemStack stack, Level level, Player player, InteractionHand hand, BlockHitResult hitResult, CallbackInfoReturnable<InteractionResult> info) {
		if (SnowloggingUtils.isOriginalBlockCovered(this.asState(), level, hitResult.getBlockPos())) {
			info.setReturnValue(InteractionResult.TRY_WITH_EMPTY_HAND);
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
			info.setReturnValue(SnowloggingUtils.canBeReplacedWithSnow(state, context));
		}
	}

	@WrapOperation(
		method = "updateShape",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/Block;updateShape(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/world/level/ScheduledTickAccess;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/Direction;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/util/RandomSource;)Lnet/minecraft/world/level/block/state/BlockState;"
		)
	)
	public BlockState wilderWild$updateShape(
		Block instance,
		BlockState state,
		LevelReader levelReader,
		ScheduledTickAccess scheduledTickAccess,
		BlockPos blockPos,
		Direction direction,
		BlockPos neighborPos,
		BlockState neighborState,
		RandomSource randomSource,
		Operation<BlockState> original
	) {
		BlockState newState = original.call(
			instance,
			SnowloggingUtils.onUpdateShape(state, levelReader, blockPos),
			levelReader,
			scheduledTickAccess,
			blockPos,
			direction,
			neighborPos,
			neighborState,
			randomSource
		);
		BlockState snowEquivalent;
		if (newState.isAir() && SnowloggingUtils.isSnowlogged(state)
			&& Blocks.SNOW.canSurvive((snowEquivalent = SnowloggingUtils.getSnowEquivalent(state)), levelReader, blockPos)) {
			state = snowEquivalent;
		} else {
			state = newState;
		}
		return state;
	}

	@WrapOperation(
		method = "initCache",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/Block;propagatesSkylightDown(Lnet/minecraft/world/level/block/state/BlockState;)Z"
		)
	)
	public boolean wilderWild$cacheSnowloggedPropogatesSkylightDown(Block instance, BlockState blockState, Operation<Boolean> original) {
		return original.call(instance, blockState) && !(SnowloggingUtils.isSnowlogged(blockState) && SnowloggingUtils.getSnowLayers(blockState) >= SnowloggingUtils.MAX_LAYERS);
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

	@ModifyExpressionValue(
		method = "<init>",
		at = @At(
			value = "FIELD",
			target = "Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;canOcclude:Z"
		)
	)
	public boolean wilderWild$canOcclude(boolean original) {
		BlockState blockState = this.asState();
		return original || SnowloggingUtils.isSnowlogged(blockState);
	}

	@WrapOperation(
		method = "getSoundType",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/Block;getSoundType(Lnet/minecraft/world/level/block/state/BlockState;)Lnet/minecraft/world/level/block/SoundType;"
		)
	)
	public SoundType wilderWild$getSoundType(Block instance, BlockState blockState, Operation<SoundType> original) {
		if (SnowloggingUtils.isSnowlogged(blockState)) return SnowloggingUtils.getSnowEquivalent(blockState).getSoundType();
		return original.call(instance, blockState);
	}

}
