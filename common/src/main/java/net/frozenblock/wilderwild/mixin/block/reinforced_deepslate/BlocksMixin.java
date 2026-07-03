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

package net.frozenblock.wilderwild.mixin.block.reinforced_deepslate;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import java.util.function.Function;
import net.frozenblock.wilderwild.config.WWBlockConfig;
import net.minecraft.references.BlockItemId;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(Blocks.class)
public abstract class BlocksMixin {

	@Shadow
	public static Block register(BlockItemId id, Function<BlockBehaviour.Properties, Block> factory, BlockBehaviour.Properties properties) {
		throw new UnsupportedOperationException("Implemented via mixin");
	}

	@WrapOperation(
		method = "<clinit>",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/Blocks;register(Lnet/minecraft/references/BlockItemId;Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)Lnet/minecraft/world/level/block/Block;",
			ordinal = 0
		),
		slice = @Slice(
			from = @At(
				value = "FIELD",
				target = "Lnet/minecraft/references/BlockItemIds;FROGSPAWN:Lnet/minecraft/references/BlockItemId;",
				opcode = Opcodes.GETSTATIC
			)
		)
	)
	private static Block wilderWild$newReinforcedDeepslate(BlockItemId id, BlockBehaviour.Properties properties, Operation<Block> original) {
		if (WWBlockConfig.NEW_REINFORCED_DEEPSLATE.get()) return register(id, RotatedPillarBlock::new, properties);
		return original.call(id, properties);
	}

}
