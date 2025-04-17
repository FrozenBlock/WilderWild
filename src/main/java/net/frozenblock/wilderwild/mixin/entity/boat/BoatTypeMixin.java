/*
 * Copyright 2025 FrozenBlock
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
 * You should have received a copy of the FrozenBlock Modding oasis License
 * along with this program; if not, see <https://github.com/FrozenBlock/Licenses>.
 */

package net.frozenblock.wilderwild.mixin.entity.boat;

import java.util.ArrayList;
import java.util.Arrays;
import net.frozenblock.wilderwild.entity.impl.WWBoatTypes;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.level.block.Block;
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
	private static Boat.Type wilderWild$newType(String internalName, int internalId, Block baseBlock, String name) {
		throw new AssertionError("Mixin injection failed - Wilder Wild BoatTypeMixin.");
	}

	@Inject(
		method = "<clinit>",
		at = @At(value = "FIELD",
			opcode = Opcodes.PUTSTATIC,
			target = "Lnet/minecraft/world/entity/vehicle/Boat$Type;$VALUES:[Lnet/minecraft/world/entity/vehicle/Boat$Type;",
			shift = At.Shift.AFTER
		)
	)
	private static void wilderWild$addCustomBoatTypes(CallbackInfo info) {
		var types = new ArrayList<>(Arrays.asList($VALUES));
		var last = types.get(types.size() - 1);

		var baobab = wilderWild$newType("WILDERWILDBAOBAB", last.ordinal() + 1, WWBlocks.BAOBAB_PLANKS, "wilderwildbaobab");
		WWBoatTypes.BAOBAB = baobab;
		types.add(baobab);

		var willow = wilderWild$newType("WILDERWILDWILLOW", last.ordinal() + 2, WWBlocks.WILLOW_PLANKS, "wilderwildwillow");
		WWBoatTypes.WILLOW = willow;
		types.add(willow);

		var cypress = wilderWild$newType("WILDERWILDCYPRESS", last.ordinal() + 3, WWBlocks.CYPRESS_PLANKS, "wilderwildcypress");
		WWBoatTypes.CYPRESS = cypress;
		types.add(cypress);

		var palm = wilderWild$newType("WILDERWILDPALM", last.ordinal() + 4, WWBlocks.PALM_PLANKS, "wilderwildpalm");
		WWBoatTypes.PALM = palm;
		types.add(palm);

		var maple = wilderWild$newType("WILDERWILDMAPLE", last.ordinal() + 5, WWBlocks.MAPLE_PLANKS, "wilderwildmaple");
		WWBoatTypes.MAPLE = maple;
		types.add(maple);

		$VALUES = types.toArray(new Boat.Type[0]);
	}

}
