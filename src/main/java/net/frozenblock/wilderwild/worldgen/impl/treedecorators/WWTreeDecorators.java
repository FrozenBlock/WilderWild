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

package net.frozenblock.wilderwild.worldgen.impl.treedecorators;

import com.mojang.serialization.MapCodec;
import net.frozenblock.wilderwild.WWConstants;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import org.jetbrains.annotations.NotNull;

public final class WWTreeDecorators {
	public static final TreeDecoratorType<ShelfFungiTreeDecorator> SHELF_FUNGI_TREE_DECORATOR = register("shelf_fungi_tree_decorator", ShelfFungiTreeDecorator.CODEC);
	public static final TreeDecoratorType<HeightBasedVineTreeDecorator> HEIGHT_BASED_VINE_TREE_DECORATOR = register("height_based_vine_tree_decorator", HeightBasedVineTreeDecorator.CODEC);
	public static final TreeDecoratorType<HeightBasedCobwebTreeDecorator> HEIGHT_BASED_COBWEB_TREE_DECORATOR = register("height_based_cobweb_tree_decorator", HeightBasedCobwebTreeDecorator.CODEC);
	public static final TreeDecoratorType<PollenTreeDecorator> POLLEN_TREE_DECORATOR = register("pollen_tree_decorator", PollenTreeDecorator.CODEC);
	public static final TreeDecoratorType<AboveLogsTreeDecorator> ABOVE_LOGS_TREE_DECORATOR = register("above_logs_tree_decorator", AboveLogsTreeDecorator.CODEC);

	public static void generateTreeDecorators() {
		//Just to ensure the class is loaded.
	}

	@NotNull
	private static <P extends TreeDecorator> TreeDecoratorType<P> register(@NotNull String id, @NotNull MapCodec<P> codec) {
		return Registry.register(BuiltInRegistries.TREE_DECORATOR_TYPE, WWConstants.id(id), new TreeDecoratorType<P>(codec));
	}
}
