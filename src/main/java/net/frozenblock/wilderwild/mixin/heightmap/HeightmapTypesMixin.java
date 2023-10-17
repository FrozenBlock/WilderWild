/*
 * Copyright 2023 FrozenBlock
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

package net.frozenblock.wilderwild.mixin.heightmap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;
import net.frozenblock.wilderwild.misc.WilderEnumValues;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Heightmap.Types.class)
public class HeightmapTypesMixin {

	//CREDIT TO nyuppo/fabric-boat-example ON GITHUB

	@SuppressWarnings("ShadowTarget")
	@Final
	@Shadow
	@Mutable
	private static Heightmap.Types[] $VALUES;

	@SuppressWarnings("InvokerTarget")
	@Invoker("<init>")
	private static Heightmap.Types wilderWild$newType(String internalName, int internalId, String serializationID, Heightmap.Usage usage, Predicate<BlockState> heightmapChecker) {
		throw new AssertionError("Mixin injection failed - Wilder Wild HeightmapTypesMixin.");
	}

	@Inject(method = "<clinit>", at = @At(value = "FIELD",
		opcode = Opcodes.PUTSTATIC,
		target = "Lnet/minecraft/world/level/levelgen/Heightmap$Types;$VALUES:[Lnet/minecraft/world/level/levelgen/Heightmap$Types;",
		shift = At.Shift.AFTER))
	private static void wilderWild$addCustomHeightmapType(CallbackInfo info) {
		var types = new ArrayList<>(Arrays.asList($VALUES));
		var last = types.get(types.size() - 1);

		var oceanFloorNoLeaves = wilderWild$newType(
			"WILDERWILDOCEAN_FLOOR_NO_LEAVES",
			last.ordinal() + 1,
			"WILDERWILDOCEAN_FLOOR_NO_LEAVES",
			Heightmap.Usage.LIVE_WORLD,
			state -> state.blocksMotion() && !(state.getBlock() instanceof LeavesBlock)
		);
		WilderEnumValues.OCEAN_FLOOR_NO_LEAVES = oceanFloorNoLeaves;
		types.add(oceanFloorNoLeaves);

		$VALUES = types.toArray(new Heightmap.Types[0]);
	}

}
