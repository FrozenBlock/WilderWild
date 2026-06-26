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

package net.frozenblock.wilderwild.mixin.datagen;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.frozenblock.wilderwild.WWConstants;
import net.minecraft.data.worldgen.AbandonedCampStructurePools;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AbandonedCampStructurePools.class)
public class AbandonedCampStructurePoolsMixin {

	@WrapOperation(
		method = {"registerTentPool", "registerCampsitePool"},
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/data/worldgen/Pools;register(Lnet/minecraft/data/worldgen/BootstrapContext;Ljava/lang/String;Lnet/minecraft/world/level/levelgen/structure/pools/StructureTemplatePool;)V"
		)
	)
	private static void wilderWild$registerAbandonedCampPoolsWithCorrectNamespace(
		BootstrapContext<StructureTemplatePool> context, String name, StructureTemplatePool pool, Operation<Void> original
	) {
		if (name.startsWith(WWConstants.MOD_ID)) {
			context.register(
				Pools.parseKey(name),
				pool
			);
			return;
		}

		original.call(context, name, pool);
	}

	@ModifyReturnValue(method = "getBiomeSpecificStructureName", at = @At("RETURN"))
	private static String wilderWild$useCorrectNamespaceForBiomeSpecificStructureName(
		String original,
		AbandonedCampStructurePools.AbandonedCampStructure biomeVariant
	) {
		if (biomeVariant.campStructureDirectory().identifier().getNamespace().equals(WWConstants.MOD_ID)) return WWConstants.string(original);
		return original;
	}
}
