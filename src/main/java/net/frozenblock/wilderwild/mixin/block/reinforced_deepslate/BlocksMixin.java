/*
 * Copyright 2023-2024 FrozenBlock
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

package net.frozenblock.wilderwild.mixin.block.reinforced_deepslate;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;
import java.util.function.Function;

@Mixin(Blocks.class)
public abstract class BlocksMixin {

	@Shadow
	private static Block register(String string, Function<BlockBehaviour.Properties, Block> function, BlockBehaviour.Properties properties) {
		return null;
	}

	@Redirect(
		method = "<clinit>",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/Blocks;register(Ljava/lang/String;Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)Lnet/minecraft/world/level/block/Block;",
			ordinal = 0
		),
		slice = @Slice(
			from = @At(
				value = "CONSTANT",
				args = "stringValue=frogspawn"
			)
		)
	)
	private static Block wilderWild$newReinforced(String string, BlockBehaviour.Properties properties) {
		return register(string, RotatedPillarBlock::new, properties);
	}

}
