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

package net.frozenblock.wilderwild.registry;

import com.mojang.serialization.Codec;
import lombok.experimental.UtilityClass;
import net.frozenblock.lib.platform.api.registry.DeferredRegister;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.advancements.predicates.entity.WasGrappledByPlayerHarpoonPredicate;
import net.minecraft.advancements.predicates.entity.EntitySubPredicate;
import net.minecraft.core.registries.Registries;
import org.jetbrains.annotations.ApiStatus;

@UtilityClass
public final class WWEntitySubPredicateTypes {

	static {
		final DeferredRegister<Codec<? extends EntitySubPredicate>> register = DeferredRegister.create(
			Registries.ENTITY_SUB_PREDICATE_TYPE,
			WWConstants.MOD_ID
		);

		register.register("was_grappled_by_harpoon", () -> WasGrappledByPlayerHarpoonPredicate.CODEC);

		register.register();
	}

	@ApiStatus.Internal
	public static void init() {}
}
