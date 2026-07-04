package net.frozenblock.wilderwild.registry;

import java.util.List;
import java.util.function.UnaryOperator;
import net.frozenblock.lib.platform.api.registry.FrozenDeferredRegister;
import net.frozenblock.lib.platform.api.registry.FrozenHolder;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.block.entity.DisplayLanternBlockEntity;
import net.frozenblock.wilderwild.entity.variant.butterfly.ButterflyVariant;
import net.frozenblock.wilderwild.entity.variant.crab.CrabVariant;
import net.frozenblock.wilderwild.entity.variant.firefly.FireflyColor;
import net.frozenblock.wilderwild.entity.variant.jellyfish.JellyfishVariant;
import net.frozenblock.wilderwild.entity.variant.moobloom.MoobloomVariant;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.world.item.component.CustomData;

public final class WWFabricDataComponents {
	private static final FrozenDeferredRegister.DataComponents REGISTER = FrozenDeferredRegister.createDataComponents(
		WWConstants.MOD_ID
	);

	public static final FrozenHolder<DataComponentType<?>, DataComponentType<List<DisplayLanternBlockEntity.Occupant>>> FIREFLIES = register(
		"fireflies",
		builder -> builder.persistent(DisplayLanternBlockEntity.Occupant.LIST_CODEC)
			.networkSynchronized(DisplayLanternBlockEntity.Occupant.STREAM_CODEC.apply(ByteBufCodecs.list()))
	);

	static {
		REGISTER.register();
	}

	public static void init() {}

	private static <T> FrozenHolder<DataComponentType<?>, DataComponentType<T>> register(String name, UnaryOperator<DataComponentType.Builder<T>> builder) {
		return REGISTER.registerComponent(name, builder);
	}
}
