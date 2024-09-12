/*
 * Copyright 2023-2024 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.wilderwild.mixin.entity.boat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Supplier;
import net.frozenblock.wilderwild.entity.impl.WWBoatTypes;
import net.frozenblock.wilderwild.registry.WWItems;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Boat.Type.class)
public class BoatTypeMixin {

	//CREDIT TO nyuppo/fabric-boat-example ON GITHUB

	@SuppressWarnings("ShadowTarget")
	@Final
	@Shadow
	@Mutable
	private static Boat.Type[] $VALUES;

	@SuppressWarnings("InvokerTarget")
	@Invoker("<init>")
	private static Boat.Type wilderWild$newType(String internalName, int internalId, Supplier<Item> boat, String name, String description) {
		throw new AssertionError("Mixin injection failed - Wilder Wild BoatTypeMixin.");
	}

	@Inject(method = "<clinit>", at = @At(value = "FIELD",
		opcode = Opcodes.PUTSTATIC,
		target = "Lnet/minecraft/world/entity/vehicle/Boat$Type;$VALUES:[Lnet/minecraft/world/entity/vehicle/Boat$Type;",
		shift = At.Shift.AFTER))
	private static void wilderWild$addCustomBoatType(CallbackInfo info) {
		var types = new ArrayList<>(Arrays.asList($VALUES));
		var last = types.get(types.size() - 1);

		var baobab = wilderWild$newType("WILDERWILDBAOBAB", last.ordinal() + 1, () -> WWItems.BAOBAB_BOAT, "wilderwildbaobab", "item.wilderwild.baobab_boat");
		WWBoatTypes.BAOBAB = baobab;
		types.add(baobab);

		var cypress = wilderWild$newType("WILDERWILDCYPRESS", last.ordinal() + 2, () -> WWItems.CYPRESS_BOAT, "wilderwildcypress", "item.wilderwild.cypress_boat");
		WWBoatTypes.CYPRESS = cypress;
		types.add(cypress);

		var palm = wilderWild$newType("WILDERWILDPALM", last.ordinal() + 3, () -> WWItems.PALM_BOAT, "wilderwildpalm", "item.wilderwild.palm_boat");
		WWBoatTypes.PALM = palm;
		types.add(palm);

		var maple = wilderWild$newType("WILDERWILDMAPLE", last.ordinal() + 4, () -> WWItems.MAPLE_BOAT, "wilderwildmaple", "item.wilderwild.maple_boat");
		WWBoatTypes.MAPLE = maple;
		types.add(maple);

		$VALUES = types.toArray(new Boat.Type[0]);
	}

}
