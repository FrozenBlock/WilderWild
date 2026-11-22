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

package net.frozenblock.wilderwild.data;

import java.util.List;
import lombok.experimental.UtilityClass;
import net.frozenblock.wilderwild.block.dispenser.EmptyMobBottleDispenseBehavior;
import net.frozenblock.wilderwild.block.dispenser.SpawnTumbleweedDispenseBehavior;
import net.frozenblock.wilderwild.references.WWDispenseBehaviorIds;
import net.frozenblock.wilderwild.registry.WWSoundEvents;
import net.minecraft.core.dispenser.DispenseBehavior;
import net.minecraft.core.dispenser.DropItemDispenseBehavior;
import net.minecraft.core.dispenser.ShootProjectileDispenseBehavior;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.util.valueproviders.ConstantFloat;
import net.minecraft.util.valueproviders.UniformFloat;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
@UtilityClass
public final class WWDispenseBehaviors {

	public static void bootstrap(BootstrapContext<List<DispenseBehavior>> context) {
		context.register(
			WWDispenseBehaviorIds.COCONUT,
			List.of(
				ShootProjectileDispenseBehavior.builder()
					.position(Vec3.ZERO, 1D)
					.uncertainty(9F)
					.power(0.75F)
					.sound(
						new ShootProjectileDispenseBehavior.DispenseSound(
							WWSoundEvents.COCONUT_THROW.asHolder(),
							ConstantFloat.of(0.5F),
							UniformFloat.of(0.33333334F, 0.5F)
						)
					)
					.build()
			)
		);
		context.register(WWDispenseBehaviorIds.TUMBLEWEED, List.of(SpawnTumbleweedDispenseBehavior.INSTANCE));
		context.register(WWDispenseBehaviorIds.FILLED_MOB_BOTTLE, List.of(EmptyMobBottleDispenseBehavior.INSTANCE, DropItemDispenseBehavior.INSTANCE));
	}
}
