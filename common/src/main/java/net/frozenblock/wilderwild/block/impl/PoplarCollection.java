package net.frozenblock.wilderwild.block.impl;

import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.material.MapColor;

public record PoplarCollection<T>(T yellow, T orange, T red) {
	public static final PoplarCollection<DyeColor> DYE_COLORS = new PoplarCollection<>(DyeColor.YELLOW, DyeColor.ORANGE, DyeColor.RED);
	public static final PoplarCollection<MapColor> MAP_COLORS = new PoplarCollection<>(MapColor.COLOR_YELLOW, MapColor.COLOR_ORANGE, MapColor.COLOR_RED);
	public static final PoplarCollection<String> NAMES = DYE_COLORS.map(DyeColor::getName);

	public static <T> PoplarCollection<T> create(T value) {
		return new PoplarCollection<>(value, value, value);
	}

	public static PoplarCollection<String> prefixWithColor(PoplarCollection<String> ids) {
		return zipMap(NAMES, ids, (color, id) -> color + "_" + id);
	}

	public List<T> asList() {
		final ImmutableList.Builder<T> builder = ImmutableList.builderWithExpectedSize(3);
		this.forEach(builder::add);
		return builder.build();
	}

	public void forEach(Consumer<T> consumer) {
		consumer.accept(this.yellow);
		consumer.accept(this.orange);
		consumer.accept(this.red);
	}

	public T pick(DyeColor dyeColor) {
		return switch (dyeColor) {
			case YELLOW -> this.yellow;
			case ORANGE -> this.orange;
			case RED -> this.red;
			default -> throw new IllegalArgumentException("Invalid poplar color: " + dyeColor);
		};
	}

	public <U> PoplarCollection<U> map(Function<T, U> mapper) {
		return new PoplarCollection<>(
			mapper.apply(this.yellow),
			mapper.apply(this.orange),
			mapper.apply(this.red)
		);
	}

	public static <T, U> void zipApply(PoplarCollection<T> first, PoplarCollection<U> second, BiConsumer<T, U> consumer) {
		consumer.accept(first.yellow(), second.yellow());
		consumer.accept(first.orange(), second.orange());
		consumer.accept(first.red(), second.red());
	}

	public static <T, U, R> PoplarCollection<R> zipMap(PoplarCollection<T> first, PoplarCollection<U> second, BiFunction<T, U, R> operation) {
		return new PoplarCollection<>(
			operation.apply(first.yellow(), second.yellow()),
			operation.apply(first.orange(), second.orange()),
			operation.apply(first.red(), second.red())
		);
	}
}
