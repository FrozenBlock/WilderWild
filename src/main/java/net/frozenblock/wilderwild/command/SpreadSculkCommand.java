/*
 * Copyright 2023-2025 FrozenBlock
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

package net.frozenblock.wilderwild.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.SculkSpreader;
import org.jetbrains.annotations.NotNull;

public final class SpreadSculkCommand {
	public static void register(@NotNull CommandDispatcher<CommandSourceStack> dispatcher) {
		dispatcher.register(
			Commands.literal("sculkspread")
				.requires(source -> source.hasPermission(2))
				.executes(
					context -> spreadSculk(
						context.getSource(),
						BlockPos.containing(context.getSource().getPosition()),
						false,
						10
					)
				).then(
					Commands.argument("position", BlockPosArgument.blockPos())
						.executes(
							context -> spreadSculk(
								context.getSource(),
								BlockPosArgument.getLoadedBlockPos(context, "position"),
								false,
								10
							)
						).then(
							Commands.argument("charge", IntegerArgumentType.integer(1))
							.executes(
								context -> spreadSculk(
									context.getSource(),
									BlockPosArgument.getLoadedBlockPos(context, "position"),
									false,
									IntegerArgumentType.getInteger(context, "charge")
								)
							).then(
								Commands.argument("isWorldGen", BoolArgumentType.bool())
								.executes(
									context -> spreadSculk(
										context.getSource(),
										BlockPosArgument.getLoadedBlockPos(context, "position"),
										BoolArgumentType.getBool(context, "isWorldGen"),
										IntegerArgumentType.getInteger(context, "charge")
									)
								)
							)
						)
				)
		);
	}

	private static int spreadSculk(@NotNull CommandSourceStack source, BlockPos pos, boolean worldGen, int charge) {
		SculkSpreader sculkSpreader = worldGen ? SculkSpreader.createWorldGenSpreader() : SculkSpreader.createLevelSpreader();
		sculkSpreader.addCursors(pos, charge);
		ServerLevel level = source.getLevel();
		while (!sculkSpreader.getCursors().isEmpty()) {
			sculkSpreader.updateCursors(level, pos, level.getRandom(), true);
		}
		source.sendSuccess(
			() -> Component.translatable(
				worldGen ? "commands.sculkspread.worldgen.success" : "commands.sculkspread.success",
				pos.getX(),
				pos.getY(),
				pos.getZ(),
				charge
			),
			true
		);
		return 1;
	}
}
