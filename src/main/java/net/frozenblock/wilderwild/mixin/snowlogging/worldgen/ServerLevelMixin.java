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

package net.frozenblock.wilderwild.mixin.snowlogging.worldgen;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import com.llamalad7.mixinextras.sugar.ref.LocalIntRef;
import net.frozenblock.wilderwild.block.impl.SnowloggingUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ServerLevel.class)
public class ServerLevelMixin {

	@WrapOperation(method = "tickChunk",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/world/level/block/Block;)Z",
			ordinal = 1
		)
	)
	public boolean wilderWild$tickPrecipitationA(
		BlockState instance, Block block, Operation<Boolean> original,
		@Share("wilderWild$runSnowlogging") LocalBooleanRef runSnowlogging, @Share("wilderWild$snowloggedLayers") LocalIntRef snowloggedLayers
	) {
		int layers = 0;
		runSnowlogging.set(SnowloggingUtils.canSnowlog(instance) && (layers = SnowloggingUtils.getSnowLayers(instance)) < 8);
		snowloggedLayers.set(layers);
		return runSnowlogging.get() || original.call(instance, block);
	}

	@WrapOperation(method = "tickChunk",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/state/BlockState;getValue(Lnet/minecraft/world/level/block/state/properties/Property;)Ljava/lang/Comparable;",
			ordinal = 0
		)
	)
	public Comparable wilderWild$tickPrecipitationB(
		BlockState instance, Property property, Operation<Comparable> original,
		@Share("wilderWild$runSnowlogging") LocalBooleanRef runSnowlogging, @Share("wilderWild$snowloggedLayers") LocalIntRef snowloggedLayers
	) {
		return runSnowlogging.get() ? snowloggedLayers.get() : original.call(instance, property);
	}

	@WrapOperation(method = "tickChunk",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/state/BlockState;setValue(Lnet/minecraft/world/level/block/state/properties/Property;Ljava/lang/Comparable;)Ljava/lang/Object;",
			ordinal = 0
		)
	)
	public Object wilderWild$tickPrecipitationC(
		BlockState instance, Property property, Comparable comparable, Operation<Object> original,
		@Share("wilderWild$runSnowlogging") LocalBooleanRef runSnowlogging, @Share("wilderWild$snowloggedLayers") LocalIntRef snowloggedLayers
	) {
		return original.call(instance, runSnowlogging.get() ? SnowloggingUtils.SNOW_LAYERS : property, comparable);
	}

}
