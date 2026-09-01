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

package net.frozenblock.wilderwild.levelgen.treedecorators;

import com.mojang.serialization.MapCodec;
import net.frozenblock.lib.platform.api.registry.DeferredHolder;
import net.frozenblock.lib.platform.api.registry.DeferredRegister;
import net.frozenblock.wilderwild.WWConstants;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

public final class WWTreeDecorators {
	private static final DeferredRegister<TreeDecoratorType<?>> REGISTER = DeferredRegister.create(
		Registries.TREE_DECORATOR_TYPE,
		WWConstants.MOD_ID
	);

	public static final DeferredHolder<TreeDecoratorType<?>, TreeDecoratorType<HeightBasedVineTreeDecorator>> HEIGHT_BASED_VINE = register("height_based_vine", HeightBasedVineTreeDecorator.CODEC);
	public static final DeferredHolder<TreeDecoratorType<?>, TreeDecoratorType<HeightBasedCobwebTreeDecorator>> HEIGHT_BASED_COBWEB = register("height_based_cobweb", HeightBasedCobwebTreeDecorator.CODEC);
	public static final DeferredHolder<TreeDecoratorType<?>, TreeDecoratorType<PollenTreeDecorator>> POLLEN = register("pollen", PollenTreeDecorator.CODEC);

	static {
		REGISTER.register();
	}

	public static void init() {}

	private static <P extends TreeDecorator> DeferredHolder<TreeDecoratorType<?>, TreeDecoratorType<P>> register(String name, MapCodec<P> codec) {
		return REGISTER.register(name, () -> new TreeDecoratorType<P>(codec));
	}
}
