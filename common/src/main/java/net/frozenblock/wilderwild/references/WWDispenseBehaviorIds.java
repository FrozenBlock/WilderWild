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

package net.frozenblock.wilderwild.references;

import java.util.List;
import lombok.experimental.UtilityClass;
import net.frozenblock.wilderwild.WWConstants;
import net.minecraft.core.dispenser.DispenseBehavior;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;

@UtilityClass
public final class WWDispenseBehaviorIds {
	public static final ResourceKey<List<DispenseBehavior>> COCONUT = create("coconut");
	public static final ResourceKey<List<DispenseBehavior>> TUMBLEWEED = create("tumbleweed");
	public static final ResourceKey<List<DispenseBehavior>> FILLED_MOB_BOTTLE = create("filled_mob_bottle");

	private static ResourceKey<List<DispenseBehavior>> create(String name) {
		return ResourceKey.create(Registries.DISPENSE_BEHAVIOR, WWConstants.id(name));
	}
}
