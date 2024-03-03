package net.frozenblock.wilderwild.registry;

import net.frozenblock.wilderwild.block.entity.DisplayLanternBlockEntity;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.codec.ByteBufCodecs;
import java.util.List;
import java.util.function.UnaryOperator;

public class RegisterDataComponents {

	public static final DataComponentType<List<DisplayLanternBlockEntity.Occupant>> FIREFLIES = register(
		"fireflies",
		builder -> builder.persistent(DisplayLanternBlockEntity.Occupant.LIST_CODEC)
			.networkSynchronized(DisplayLanternBlockEntity.Occupant.STREAM_CODEC.apply(ByteBufCodecs.list()))
	);

	public static void init() {}

	private static <T> DataComponentType<T> register(String id, UnaryOperator<DataComponentType.Builder<T>> unaryOperator) {
		return Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, WilderSharedConstants.id(id), unaryOperator.apply(DataComponentType.builder()).build());
	}
}
