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
 * You should have received a copy of the FrozenBlock Modding Oasis License
 * along with this program; if not, see <https://github.com/FrozenBlock/Licenses>.
 */

package net.frozenblock.wilderwild.mixin.client.equipment;

import java.util.List;
import com.google.common.collect.ImmutableList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.client.WWEquipmentClientInfo;
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
	private static final List<EquipmentClientInfo.Layer> WILDERWILD$OSTRICH_SADDLE_LAYERS = ImmutableList.of(WWEquipmentClientInfo.OSTRICH_SADDLE_LAYER);
	@Unique
	private static final List<EquipmentClientInfo.Layer> WILDERWILD$OSTRICH_ZOMBIE_SADDLE_LAYERS = ImmutableList.of(WWEquipmentClientInfo.OSTRICH_ZOMBIE_SADDLE_LAYER);

	@Inject(method = "getLayers", at = @At("HEAD"), cancellable = true)
	public void wilderWild$returnOstrichSaddleIfPossible(EquipmentClientInfo.LayerType layerType, CallbackInfoReturnable<List<EquipmentClientInfo.Layer>> info) {
		if (layerType == WWEquipmentClientInfo.OSTRICH_SADDLE) info.setReturnValue(WILDERWILD$OSTRICH_SADDLE_LAYERS);
		if (layerType == WWEquipmentClientInfo.OSTRICH_ZOMBIE_SADDLE) info.setReturnValue(WILDERWILD$OSTRICH_ZOMBIE_SADDLE_LAYERS);
	}
}
