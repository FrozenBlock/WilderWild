/*
 * Copyright 2023 FrozenBlock
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

package net.frozenblock.wilderwild.mixin.block.mesoglea;

import net.frozenblock.wilderwild.block.MesogleaBlock;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(BlockGetter.class)
public interface BlockGetterMixin {

	@Shadow
	BlockState getBlockState(BlockPos var1);

	@Inject(method = "clip", at = @At("HEAD"))
	default void wilderWild$checkIfInMesoglea(ClipContext clipContext, CallbackInfoReturnable<BlockHitResult> cir) {
		if (clipContext.collisionContext instanceof EntityCollisionContext entityCollisionContext
			&& entityCollisionContext.getEntity() instanceof Player player) {
			BlockState headState = getBlockState(BlockPos.containing(player.getEyePosition()));
			if (headState.getBlock() instanceof MesogleaBlock && headState.getValue(BlockStateProperties.WATERLOGGED)) {
				WilderSharedConstants.IN_MESOGLEA = true;
				return;
			}
		}
		WilderSharedConstants.IN_MESOGLEA = false;
	}

	@ModifyArgs(method = "method_17743", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/BlockGetter;clipWithInteractionOverride(Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/phys/shapes/VoxelShape;Lnet/minecraft/world/level/block/state/BlockState;)Lnet/minecraft/world/phys/BlockHitResult;"))
	default void wilderWild$mesogleaClip(Args args) {
		BlockState blockState = args.get(4);
		if (WilderSharedConstants.IN_MESOGLEA && blockState.getBlock() instanceof MesogleaBlock && blockState.getValue(BlockStateProperties.WATERLOGGED)) {
			args.set(3, Shapes.empty());
		}
	}

}
