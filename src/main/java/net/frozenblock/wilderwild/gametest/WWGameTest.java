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

package net.frozenblock.wilderwild.gametest;

import java.util.List;
import net.fabricmc.fabric.api.gametest.v1.FabricGameTest;
import net.frozenblock.lib.gametest.api.TrackedPosition;
import net.frozenblock.wilderwild.WilderConstants;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class WWGameTest implements FabricGameTest {

	private static final String AXE_INTERACTION = WilderConstants.MOD_ID + ":gametest/axe_interaction";

	@GameTest(template = AXE_INTERACTION)
	public void axeInteraction(@NotNull GameTestHelper helper) {
		ServerPlayer player = helper.makeMockServerPlayerInLevel();
		player.setPos(helper.absoluteVec(new Vec3(7.5, 2.0, 0.5)));
		ItemStack stack = new ItemStack(Items.DIAMOND_AXE);

		// variable names reflect the end result after axe interaction
		TrackedPosition<Vec3> strippedOak = TrackedPosition.createRelative(helper, Blocks.OAK_LOG, new Vec3(9.5, 4.5, 0.5));
		TrackedPosition<Vec3> strippedSpruce = TrackedPosition.createRelative(helper, Blocks.SPRUCE_LOG, new Vec3(9.5, 4.5, 2.5));
		TrackedPosition<Vec3> strippedBirch = TrackedPosition.createRelative(helper, Blocks.BIRCH_LOG, new Vec3(9.5, 4.5, 4.5));
		TrackedPosition<Vec3> strippedJungle = TrackedPosition.createRelative(helper, Blocks.JUNGLE_LOG, new Vec3(9.5, 4.5, 6.5));
		TrackedPosition<Vec3> strippedAcacia = TrackedPosition.createRelative(helper, Blocks.ACACIA_LOG, new Vec3(9.5, 4.5, 8.5));
		TrackedPosition<Vec3> strippedDarkOak = TrackedPosition.createRelative(helper, Blocks.DARK_OAK_LOG, new Vec3(6.5, 4.5, 0.5));
		TrackedPosition<Vec3> strippedMangrove = TrackedPosition.createRelative(helper, Blocks.MANGROVE_LOG, new Vec3(6.5, 4.5, 2.5));
		TrackedPosition<Vec3> strippedBaobab = TrackedPosition.createRelative(helper, WWBlocks.BAOBAB_LOG, new Vec3(6.5, 4.5, 4.5));
		TrackedPosition<Vec3> strippedCypress = TrackedPosition.createRelative(helper, WWBlocks.CYPRESS_LOG, new Vec3(6.5, 4.5, 6.5));
		TrackedPosition<Vec3> strippedPalm = TrackedPosition.createRelative(helper, WWBlocks.PALM_LOG, new Vec3(6.5, 4.5, 8.5));
		TrackedPosition<Vec3> strippedCherry = TrackedPosition.createRelative(helper, Blocks.CHERRY_LOG, new Vec3(3.5, 4.5, 0.5));
		TrackedPosition<Vec3> strippedCrimson = TrackedPosition.createRelative(helper, Blocks.CRIMSON_STEM, new Vec3(3.5, 4.5, 2.5));
		TrackedPosition<Vec3> strippedWarped = TrackedPosition.createRelative(helper, Blocks.WARPED_STEM, new Vec3(3.5, 4.5, 4.5));
		List<TrackedPosition<Vec3>> strippedLogs = List.of(
			strippedOak, strippedSpruce, strippedBirch, strippedJungle,strippedAcacia,
			strippedDarkOak, strippedMangrove, strippedBaobab, strippedCypress, strippedPalm,
			strippedCherry, strippedCrimson, strippedWarped
		);

		TrackedPosition<Vec3> hollowedOak = TrackedPosition.createRelative(helper, Blocks.OAK_LOG, new Vec3(9.5, 2.5, 0.5));
		TrackedPosition<Vec3> strippedHollowedOak = TrackedPosition.createRelative(helper, Blocks.STRIPPED_OAK_LOG, new Vec3(9.5, 2.5, 1.5));
		TrackedPosition<Vec3> hollowedSpruce = TrackedPosition.createRelative(helper, Blocks.SPRUCE_LOG, new Vec3(9.5, 2.5, 2.5));
		TrackedPosition<Vec3> strippedHollowedSpruce = TrackedPosition.createRelative(helper, Blocks.STRIPPED_SPRUCE_LOG, new Vec3(9.5, 2.5, 3.5));
		TrackedPosition<Vec3> hollowedBirch = TrackedPosition.createRelative(helper, Blocks.BIRCH_LOG, new Vec3(9.5, 2.5, 4.5));
		TrackedPosition<Vec3> strippedHollowedBirch = TrackedPosition.createRelative(helper, Blocks.STRIPPED_BIRCH_LOG, new Vec3(9.5, 2.5, 5.5));
		TrackedPosition<Vec3> hollowedJungle = TrackedPosition.createRelative(helper, Blocks.JUNGLE_LOG, new Vec3(9.5, 2.5, 6.5));
		TrackedPosition<Vec3> strippedHollowedJungle = TrackedPosition.createRelative(helper, Blocks.STRIPPED_JUNGLE_LOG, new Vec3(9.5, 2.5, 7.5));
		TrackedPosition<Vec3> hollowedAcacia = TrackedPosition.createRelative(helper, Blocks.ACACIA_LOG, new Vec3(9.5, 2.5, 8.5));
		TrackedPosition<Vec3> strippedHollowedAcacia = TrackedPosition.createRelative(helper, Blocks.STRIPPED_ACACIA_LOG, new Vec3(9.5, 2.5, 9.5));
		TrackedPosition<Vec3> hollowedDarkOak = TrackedPosition.createRelative(helper, Blocks.DARK_OAK_LOG, new Vec3(6.5, 2.5, 0.5));
		TrackedPosition<Vec3> strippedHollowedollowedDarkOak = TrackedPosition.createRelative(helper, Blocks.STRIPPED_DARK_OAK_LOG, new Vec3(6.5, 2.5, 1.5));
		TrackedPosition<Vec3> hollowedMangrove = TrackedPosition.createRelative(helper, Blocks.MANGROVE_LOG, new Vec3(6.5, 2.5, 2.5));
		TrackedPosition<Vec3> strippedHollowedMangrove = TrackedPosition.createRelative(helper, Blocks.STRIPPED_MANGROVE_LOG, new Vec3(6.5, 2.5, 3.5));
		TrackedPosition<Vec3> hollowedBaobab = TrackedPosition.createRelative(helper, WWBlocks.BAOBAB_LOG, new Vec3(6.5, 2.5, 4.5));
		TrackedPosition<Vec3> strippedHollowedBaobab = TrackedPosition.createRelative(helper, WWBlocks.STRIPPED_BAOBAB_LOG, new Vec3(6.5, 2.5, 5.5));
		TrackedPosition<Vec3> hollowedCypress = TrackedPosition.createRelative(helper, WWBlocks.CYPRESS_LOG, new Vec3(6.5, 2.5, 6.5));
		TrackedPosition<Vec3> strippedHollowedCypress = TrackedPosition.createRelative(helper, WWBlocks.STRIPPED_CYPRESS_LOG, new Vec3(6.5, 2.5, 7.5));
		TrackedPosition<Vec3> hollowedPalm = TrackedPosition.createRelative(helper, WWBlocks.PALM_LOG, new Vec3(6.5, 2.5, 8.5));
		TrackedPosition<Vec3> strippedHollowedPalm = TrackedPosition.createRelative(helper, WWBlocks.STRIPPED_PALM_LOG, new Vec3(6.5, 2.5, 9.5));
		TrackedPosition<Vec3> hollowedCherry = TrackedPosition.createRelative(helper, Blocks.CHERRY_LOG, new Vec3(3.5, 2.5, 0.5));
		TrackedPosition<Vec3> strippedHollowedCherry = TrackedPosition.createRelative(helper, Blocks.STRIPPED_CHERRY_LOG, new Vec3(3.5, 2.5, 1.5));
		TrackedPosition<Vec3> hollowedCrimson = TrackedPosition.createRelative(helper, Blocks.CRIMSON_STEM, new Vec3(3.5, 2.5, 2.5));
		TrackedPosition<Vec3> strippedHollowedCrimson = TrackedPosition.createRelative(helper, Blocks.STRIPPED_CRIMSON_STEM, new Vec3(3.5, 2.5, 3.5));
		TrackedPosition<Vec3> hollowedWarped = TrackedPosition.createRelative(helper, Blocks.WARPED_STEM, new Vec3(3.5, 2.5, 4.5));
		TrackedPosition<Vec3> strippedHollowedWarped = TrackedPosition.createRelative(helper, Blocks.STRIPPED_WARPED_STEM, new Vec3(3.5, 2.5, 5.5));
		List<TrackedPosition<Vec3>> hollowedLogs = List.of(
			hollowedOak, hollowedSpruce, hollowedBirch, hollowedJungle, hollowedAcacia,
			hollowedDarkOak, hollowedMangrove, hollowedBaobab, hollowedCypress, hollowedPalm,
			hollowedCherry, hollowedCrimson, hollowedWarped,
			strippedHollowedOak, strippedHollowedSpruce, strippedHollowedBirch, strippedHollowedJungle, strippedHollowedAcacia,
			strippedHollowedollowedDarkOak, strippedHollowedMangrove, strippedHollowedBaobab, strippedHollowedCypress, strippedHollowedPalm,
			strippedHollowedCherry, strippedHollowedCrimson, strippedHollowedWarped
		);

		helper.runAfterDelay(1L, () -> {
			for (TrackedPosition<Vec3> location : strippedLogs) {
				Vec3 absolute = location.absolute();

				player.setPos(absolute.add(-2, -2.5, 0));
				player.lookAt(EntityAnchorArgument.Anchor.EYES, absolute.add(0, 0, -0.5));
				BlockHitResult hitResult = (BlockHitResult) player.pick(2, 1.0F, false);
				UseOnContext context = new UseOnContext(player, InteractionHand.MAIN_HAND, hitResult);
				stack.useOn(context); // simulates a player using an axe on the log
			}
			for (TrackedPosition<Vec3> location : hollowedLogs) {
				Vec3 absolute = location.absolute();

				player.setPos(absolute.add(-1, -0.5, 0));
				player.lookAt(EntityAnchorArgument.Anchor.EYES, absolute.add(0, 0.5, 0));
				BlockHitResult hitResult = (BlockHitResult) player.pick(2, 1.0F, false);
				UseOnContext context = new UseOnContext(player, InteractionHand.MAIN_HAND, hitResult);
				stack.useOn(context); // simulates a player using an axe on the log
			}
		});

		helper.runAfterDelay(2, () -> {
			// remove fake player
			helper.getLevel().getServer().getPlayerList().remove(player);
			player.remove(Entity.RemovalReason.DISCARDED);

			// check logs
			strippedOak.assertBlockPresent(helper, Blocks.STRIPPED_OAK_LOG);
			strippedSpruce.assertBlockPresent(helper, Blocks.STRIPPED_SPRUCE_LOG);
			strippedBirch.assertBlockPresent(helper, Blocks.STRIPPED_BIRCH_LOG);
			strippedJungle.assertBlockPresent(helper, Blocks.STRIPPED_JUNGLE_LOG);
			strippedAcacia.assertBlockPresent(helper, Blocks.STRIPPED_ACACIA_LOG);
			strippedDarkOak.assertBlockPresent(helper, Blocks.STRIPPED_DARK_OAK_LOG);
			strippedMangrove.assertBlockPresent(helper, Blocks.STRIPPED_MANGROVE_LOG);
			strippedBaobab.assertBlockPresent(helper, WWBlocks.STRIPPED_BAOBAB_LOG);
			strippedCypress.assertBlockPresent(helper, WWBlocks.STRIPPED_CYPRESS_LOG);
			strippedPalm.assertBlockPresent(helper, WWBlocks.STRIPPED_PALM_LOG);
			strippedCherry.assertBlockPresent(helper, Blocks.STRIPPED_CHERRY_LOG);
			strippedCrimson.assertBlockPresent(helper, Blocks.STRIPPED_CRIMSON_STEM);
			strippedWarped.assertBlockPresent(helper, Blocks.STRIPPED_WARPED_STEM);

			hollowedOak.assertBlockPresent(helper, WWBlocks.HOLLOWED_OAK_LOG);
			strippedHollowedOak.assertBlockPresent(helper, WWBlocks.STRIPPED_HOLLOWED_OAK_LOG);
			hollowedSpruce.assertBlockPresent(helper, WWBlocks.HOLLOWED_SPRUCE_LOG);
			strippedHollowedSpruce.assertBlockPresent(helper, WWBlocks.STRIPPED_HOLLOWED_SPRUCE_LOG);
			hollowedBirch.assertBlockPresent(helper, WWBlocks.HOLLOWED_BIRCH_LOG);
			strippedHollowedBirch.assertBlockPresent(helper, WWBlocks.STRIPPED_HOLLOWED_BIRCH_LOG);
			hollowedJungle.assertBlockPresent(helper, WWBlocks.HOLLOWED_JUNGLE_LOG);
			strippedHollowedJungle.assertBlockPresent(helper, WWBlocks.STRIPPED_HOLLOWED_JUNGLE_LOG);
			hollowedAcacia.assertBlockPresent(helper, WWBlocks.HOLLOWED_ACACIA_LOG);
			strippedHollowedAcacia.assertBlockPresent(helper, WWBlocks.STRIPPED_HOLLOWED_ACACIA_LOG);
			hollowedDarkOak.assertBlockPresent(helper, WWBlocks.HOLLOWED_DARK_OAK_LOG);
			strippedHollowedollowedDarkOak.assertBlockPresent(helper, WWBlocks.STRIPPED_HOLLOWED_DARK_OAK_LOG);
			hollowedMangrove.assertBlockPresent(helper, WWBlocks.HOLLOWED_MANGROVE_LOG);
			strippedHollowedMangrove.assertBlockPresent(helper, WWBlocks.STRIPPED_HOLLOWED_MANGROVE_LOG);
			hollowedBaobab.assertBlockPresent(helper, WWBlocks.HOLLOWED_BAOBAB_LOG);
			strippedHollowedBaobab.assertBlockPresent(helper, WWBlocks.STRIPPED_HOLLOWED_BAOBAB_LOG);
			hollowedCypress.assertBlockPresent(helper, WWBlocks.HOLLOWED_CYPRESS_LOG);
			strippedHollowedCypress.assertBlockPresent(helper, WWBlocks.STRIPPED_HOLLOWED_CYPRESS_LOG);
			hollowedPalm.assertBlockPresent(helper, WWBlocks.HOLLOWED_PALM_LOG);
			strippedHollowedPalm.assertBlockPresent(helper, WWBlocks.STRIPPED_HOLLOWED_PALM_LOG);
			hollowedCherry.assertBlockPresent(helper, WWBlocks.HOLLOWED_CHERRY_LOG);
			strippedHollowedCherry.assertBlockPresent(helper, WWBlocks.STRIPPED_HOLLOWED_CHERRY_LOG);
			hollowedCrimson.assertBlockPresent(helper, WWBlocks.HOLLOWED_CRIMSON_STEM);
			strippedHollowedCrimson.assertBlockPresent(helper, WWBlocks.STRIPPED_HOLLOWED_CRIMSON_STEM);
			hollowedWarped.assertBlockPresent(helper, WWBlocks.HOLLOWED_WARPED_STEM);
			strippedHollowedWarped.assertBlockPresent(helper, WWBlocks.STRIPPED_HOLLOWED_WARPED_STEM);

			helper.succeed();
		});
	}
}
