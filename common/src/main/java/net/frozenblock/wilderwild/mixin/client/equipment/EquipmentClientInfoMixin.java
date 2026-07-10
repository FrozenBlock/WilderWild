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

package net.frozenblock.wilderwild.mixin.client.equipment;

import com.google.common.collect.ImmutableList;
import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.client.resources.model.WWEquipmentClientInfoLayerType;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(EquipmentClientInfo.class)
public class EquipmentClientInfoMixin {
	@Unique
	private static final List<EquipmentClientInfo.Layer> WILDERWILD$OSTRICH_SADDLE_LAYERS = ImmutableList.of(new EquipmentClientInfo.Layer(WWConstants.id("saddle")));

	@Inject(method = "getLayers", at = @At("HEAD"), cancellable = true)
	public void wilderWild$returnOstrichSaddleIfPossible(EquipmentClientInfo.LayerType type, CallbackInfoReturnable<List<EquipmentClientInfo.Layer>> info) {
		if (type != WWEquipmentClientInfoLayerType.WILDERWILD_OSTRICH_SADDLE && type != WWEquipmentClientInfoLayerType.WILDERWILD_OSTRICH_ZOMBIE_SADDLE) return;
		info.setReturnValue(WILDERWILD$OSTRICH_SADDLE_LAYERS);
	}
}
