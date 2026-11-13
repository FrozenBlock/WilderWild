/*
 * Copyright 2026 FrozenBlock
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

package net.frozenblock.wilderwild.block.impl;

import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.material.MapColor;

public record MesogleaCollection<T>(T pearlescentBlue, T pearlescentPurple, T blue, T pink, T red, T yellow, T lime) {
	public static final MesogleaCollection<DyeColor> DYE_COLORS = new MesogleaCollection<>(
		DyeColor.LIGHT_BLUE,
		DyeColor.MAGENTA,
		DyeColor.BLUE,
		DyeColor.PINK,
		DyeColor.RED,
		DyeColor.YELLOW,
		DyeColor.LIME
	);
	public static final MesogleaCollection<MapColor> MAP_COLORS = new MesogleaCollection<>(
		MapColor.GLOW_LICHEN,
		MapColor.COLOR_MAGENTA,
		MapColor.COLOR_BLUE,
		MapColor.COLOR_PINK,
		MapColor.COLOR_RED,
		MapColor.COLOR_YELLOW,
		MapColor.COLOR_LIGHT_GREEN
	);
	public static final MesogleaCollection<Integer> FOG_COLORS = new MesogleaCollection<>(
		Integer.parseInt("B9DAED", 16),
		Integer.parseInt("C6B2F4", 16),
		Integer.parseInt("ED87D1", 16),
		Integer.parseInt("FFC958", 16),
		Integer.parseInt("596BFF", 16),
		Integer.parseInt("55EF1B", 16),
		Integer.parseInt("FD3420", 16)
	);
	public static final MesogleaCollection<Boolean> IS_PEARLESCENT = new MesogleaCollection<>(
		true,
		true,
		false,
		false,
		false,
		false,
		false
	);
	public static final MesogleaCollection<String> NAMES = new MesogleaCollection<>(
		"pearlescent_blue",
		"pearlescent_purple",
		"blue",
		"pink",
		"red",
		"yellow",
		"lime"
	);

	public static <T> MesogleaCollection<T> create(T value) {
		return new MesogleaCollection<>(value, value, value, value, value, value, value);
	}

	public static MesogleaCollection<String> prefixWithColor(MesogleaCollection<String> ids) {
		return zipMap(NAMES, ids, (color, id) -> color + "_" + id);
	}

	public static MesogleaCollection<String> suffixWithColor(MesogleaCollection<String> ids) {
		return zipMap(NAMES, ids, (color, id) -> id + "_" + color);
	}

	public List<T> asList() {
		final ImmutableList.Builder<T> builder = ImmutableList.builderWithExpectedSize(3);
		this.forEach(builder::add);
		return builder.build();
	}

	public void forEach(Consumer<T> consumer) {
		consumer.accept(this.pearlescentBlue);
		consumer.accept(this.pearlescentPurple);
		consumer.accept(this.blue);
		consumer.accept(this.pink);
		consumer.accept(this.red);
		consumer.accept(this.yellow);
		consumer.accept(this.lime);
	}

	public T pick(DyeColor dyeColor) {
		return switch (dyeColor) {
			case LIGHT_BLUE -> this.pearlescentBlue;
			case MAGENTA -> this.pearlescentPurple;
			case BLUE -> this.blue;
			case PINK -> this.pink;
			case RED -> this.red;
			case YELLOW -> this.yellow;
			case LIME -> this.lime;
			default -> throw new IllegalArgumentException("Invalid mesoglea color: " + dyeColor);
		};
	}

	public <U> MesogleaCollection<U> map(Function<T, U> mapper) {
		return new MesogleaCollection<>(
			mapper.apply(this.pearlescentBlue),
			mapper.apply(this.pearlescentPurple),
			mapper.apply(this.blue),
			mapper.apply(this.pink),
			mapper.apply(this.red),
			mapper.apply(this.yellow),
			mapper.apply(this.lime)
		);
	}

	public static <T, U> void zipApply(MesogleaCollection<T> first, MesogleaCollection<U> second, BiConsumer<T, U> consumer) {
		consumer.accept(first.pearlescentBlue(), second.pearlescentBlue());
		consumer.accept(first.pearlescentPurple(), second.pearlescentPurple());
		consumer.accept(first.blue(), second.blue());
		consumer.accept(first.pink(), second.pink());
		consumer.accept(first.red(), second.red());
		consumer.accept(first.yellow(), second.yellow());
		consumer.accept(first.lime(), second.lime());

	}

	public static <T, U, R> MesogleaCollection<R> zipMap(MesogleaCollection<T> first, MesogleaCollection<U> second, BiFunction<T, U, R> operation) {
		return new MesogleaCollection<>(
			operation.apply(first.pearlescentBlue(), second.pearlescentBlue()),
			operation.apply(first.pearlescentPurple(), second.pearlescentPurple()),
			operation.apply(first.blue(), second.blue()),
			operation.apply(first.pink(), second.pink()),
			operation.apply(first.red(), second.red()),
			operation.apply(first.yellow(), second.yellow()),
			operation.apply(first.lime(), second.lime())
		);
	}
}
