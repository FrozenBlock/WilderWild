package net.frozenblock.wilderwild.registry;

import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import net.frozenblock.lib.platform.api.registry.FrozenDeferredRegister;
import net.frozenblock.lib.platform.api.registry.FrozenHolder;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.entity.AbstractOstrich;
import net.frozenblock.wilderwild.entity.Crab;
import net.frozenblock.wilderwild.entity.Firefly;
import net.frozenblock.wilderwild.entity.Penguin;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.UUIDUtil;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.ai.behavior.PositionTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.vehicle.boat.Boat;

public final class WWFabricMemoryModuleTypes {
	private static final FrozenDeferredRegister<MemoryModuleType<?>> REGISTER = FrozenDeferredRegister.create(
		Registries.MEMORY_MODULE_TYPE,
		WWConstants.MOD_ID
	);

	public static final FrozenHolder<MemoryModuleType<?>, MemoryModuleType<List<Crab>>> NEARBY_CRABS = register("nearby_crabs");
	public static final FrozenHolder<MemoryModuleType<?>, MemoryModuleType<List<AbstractOstrich>>> NEARBY_OSTRICHES = register("nearby_ostriches");
	public static final FrozenHolder<MemoryModuleType<?>, MemoryModuleType<List<Penguin>>> NEARBY_PENGUINS = register("nearby_penguins");

	static {
		REGISTER.register();
	}

	public static void init() {}

	private static <U> FrozenHolder<MemoryModuleType<?>, MemoryModuleType<U>> register(String name, Codec<U> codec) {
		return REGISTER.register(name, () -> new MemoryModuleType<>(Optional.of(codec)));
	}

	private static <U> FrozenHolder<MemoryModuleType<?>, MemoryModuleType<U>> register(String name) {
		return REGISTER.register(name, () -> new MemoryModuleType<>(Optional.empty()));
	}
}
