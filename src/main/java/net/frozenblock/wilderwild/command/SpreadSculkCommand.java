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
