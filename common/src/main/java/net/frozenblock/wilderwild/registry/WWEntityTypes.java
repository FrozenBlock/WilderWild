package net.frozenblock.wilderwild.registry;

import net.frozenblock.lib.entity.api.attribute.FrozenDefaultAttributeRegistry;
import net.frozenblock.lib.entity.api.category.FrozenMobCategories;
import net.frozenblock.lib.platform.api.registry.FrozenDeferredRegister;
import net.frozenblock.lib.platform.api.registry.FrozenHolder;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.entity.Firefly;
import net.frozenblock.wilderwild.references.WWEntityTypeIds;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

public final class WWEntityTypes {
	private static final FrozenDeferredRegister.Entities REGISTER = FrozenDeferredRegister.createEntities(
		WWConstants.MOD_ID
	);

	public static final FrozenHolder<EntityType<?>, EntityType<Firefly>> FIREFLY = register(WWEntityTypeIds.FIREFLY,
		Firefly::new, FrozenMobCategories.getCategory(WWConstants.MOD_ID, "firefly"),
		builder -> builder
			.sized(0.3F, 0.3F)
			.eyeHeight(0.3F * 0.85F) // 0.85F is default eye height scaler
			.clientTrackingRange(5),
		fireflyType -> {
			FrozenDefaultAttributeRegistry.register(fireflyType, Firefly.createAttributes());
			SpawnPlacements.register(
				fireflyType,
				SpawnPlacementTypes.NO_RESTRICTIONS,
				Heightmap.Types.MOTION_BLOCKING,
				Firefly::checkFireflySpawnRules
			);
		}
	);

	public static void init() {}

	static {
		REGISTER.register();
	}

	private static <E extends Entity> FrozenHolder<EntityType<?>, EntityType<E>> register(ResourceKey<EntityType<?>> id, EntityType.EntityFactory<E> factory, MobCategory category, UnaryOperator<EntityType.Builder<E>> builder, Consumer<EntityType<E>> also) {
		return REGISTER.registerEntityType(id.identifier().getPath(), factory, category, builder, also);
	}
}
