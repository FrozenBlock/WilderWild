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

package net.frozenblock.wilderwild.mixin.block.potent_sulfur;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import java.util.Optional;
import net.frozenblock.lib.wind.api.WindDisturbance;
import net.frozenblock.lib.wind.api.WindDisturbanceLogic;
import net.frozenblock.lib.wind.api.WindManager;
import net.frozenblock.wilderwild.registry.WWWindDisturbances;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.PotentSulfurBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PotentSulfurBlockEntity.class)
public class PotentSulfurBlockEntityMixin {

	@Unique
	private static final WindDisturbanceLogic<PotentSulfurBlockEntity> WILDER_WILD$DUMMY_WIND_LOGIC = new WindDisturbanceLogic<>((source, level, origin, area, target) -> WindDisturbance.DUMMY_RESULT);

	@ModifyExpressionValue(
		method = "lambda$static$5",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/phys/AABB;expandTowards(DDD)Lnet/minecraft/world/phys/AABB;"
		)
	)
	private static AABB wilderWild$addWindDisturbanceToGeyser(
		AABB original,
		Level level, BlockPos pos, BlockState state, PotentSulfurBlockEntity entity
	) {
		if (!(level instanceof ServerLevel serverLevel)) return original;
		WindManager.getOrCreateWindManager(serverLevel).addWindDisturbanceAndSync(
			new WindDisturbance<PotentSulfurBlockEntity>(
				Optional.of(entity),
				Vec3.atCenterOf(pos),
				original.inflate(0.5D).move(0D, 0.5D, 0D),
				WindDisturbanceLogic.getWindDisturbanceLogic(WWWindDisturbances.GEYSER).orElse(WILDER_WILD$DUMMY_WIND_LOGIC)
			),
			serverLevel
		);
		return original;
	}

}
