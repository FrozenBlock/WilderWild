package net.frozenblock.wilderwild.client.resources.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.resources.model.EquipmentClientInfo;

@Environment(EnvType.CLIENT)
public final class WWEquipmentClientInfoLayerType {
	public static EquipmentClientInfo.LayerType WILDERWILD_OSTRICH_SADDLE;
	public static EquipmentClientInfo.LayerType WILDERWILD_OSTRICH_ZOMBIE_SADDLE;

	static {
		EquipmentClientInfo.LayerType.values();
	}
}
