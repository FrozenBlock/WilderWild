package net.frozenblock.wilderwild.block.impl;

import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.material.MapColor;

public record MapleCollection<T>(T yellow, T orange, T red) {
	public static final MapleCollection<DyeColor> DYE_COLORS = new MapleCollection<>(DyeColor.YELLOW, DyeColor.ORANGE, DyeColor.RED);
	public static final MapleCollection<MapColor> MAP_COLORS = new MapleCollection<>(MapColor.COLOR_YELLOW, MapColor.COLOR_ORANGE, MapColor.COLOR_RED);
	public static final MapleCollection<String> NAMES = DYE_COLORS.map(DyeColor::getName);

	public static <T> MapleCollection<T> create(T value) {
		return new MapleCollection<>(value, value, value);
	}

	public static MapleCollection<String> prefixWithColor(MapleCollection<String> ids) {
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
			default -> throw new IllegalArgumentException("Invalid maple color: " + dyeColor);
		};
	}

	public <U> MapleCollection<U> map(Function<T, U> mapper) {
		return new MapleCollection<>(
			mapper.apply(this.yellow),
			mapper.apply(this.orange),
			mapper.apply(this.red)
		);
	}

	public static <T, U> void zipApply(MapleCollection<T> first, MapleCollection<U> second, BiConsumer<T, U> consumer) {
		consumer.accept(first.yellow(), second.yellow());
		consumer.accept(first.orange(), second.orange());
		consumer.accept(first.red(), second.red());
	}

	public static <T, U, R> MapleCollection<R> zipMap(MapleCollection<T> first, MapleCollection<U> second, BiFunction<T, U, R> operation) {
		return new MapleCollection<>(
			operation.apply(first.yellow(), second.yellow()),
			operation.apply(first.orange(), second.orange()),
			operation.apply(first.red(), second.red())
		);
	}
}
