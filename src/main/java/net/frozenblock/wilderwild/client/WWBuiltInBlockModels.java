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

package net.frozenblock.wilderwild.client;

import java.util.Optional;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.renderer.block.BuiltInBlockModelRegistry;
import net.frozenblock.wilderwild.client.renderer.block.model.properties.conditional.HasSculk;
import net.frozenblock.wilderwild.client.renderer.special.StoneChestSpecialRenderer;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.minecraft.client.renderer.MultiblockChestResources;
import net.minecraft.client.renderer.block.BuiltInBlockModels;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.renderer.block.model.ConditionalBlockModel;
import net.minecraft.client.renderer.blockentity.ChestRenderer;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.state.properties.ChestType;

@Environment(EnvType.CLIENT)
public final class WWBuiltInBlockModels {

	public static void init() {
		BuiltInBlockModelRegistry.REGISTER.register(builder -> {
			builder.put(createStoneChest(StoneChestSpecialRenderer.STONE, StoneChestSpecialRenderer.STONE_SCULK), WWBlocks.STONE_CHEST);
			builder.put(BuiltInBlockModels::createBlockStateModelWrapper, WWBlocks.HANGING_TENDRIL);
			builder.put(BuiltInBlockModels::createFlowerBedModel, WWBlocks.CLOVERS);
			builder.put(BuiltInBlockModels::createFlowerBedModel, WWBlocks.PHLOX, WWBlocks.LANTANAS);
		});

	}

	private static BuiltInBlockModels.SpecialModelFactory createStoneChest(MultiblockChestResources<Identifier> textures, MultiblockChestResources<Identifier> sculkTextures) {
		return BuiltInBlockModels.specialModelWithPropertyDispatch(
			ChestBlock.FACING,
			ChestBlock.TYPE,
			(facing, type) -> new ConditionalBlockModel.Unbaked(
				Optional.empty(),
				new HasSculk(),
				createStoneChest(sculkTextures.select(type), type, facing),
				createStoneChest(textures.select(type), type, facing)
			)
		);
	}

	private static BlockModel.Unbaked createStoneChest(Identifier texture, ChestType chestType, Direction facing) {
		return BuiltInBlockModels.special(new StoneChestSpecialRenderer.Unbaked(texture, chestType), ChestRenderer.modelTransformation(facing));
	}
}
