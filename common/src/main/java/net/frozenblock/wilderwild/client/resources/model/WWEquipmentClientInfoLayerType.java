package net.frozenblock.wilderwild.client.resources.model;

import net.mehvahdjukaar.candlelight.api.ClientOnly;
import net.minecraft.client.resources.model.EquipmentClientInfo;

@ClientOnly
public final class WWEquipmentClientInfoLayerType {
	public static EquipmentClientInfo.LayerType WILDERWILD_OSTRICH_SADDLE;
	public static EquipmentClientInfo.LayerType WILDERWILD_OSTRICH_ZOMBIE_SADDLE;

	static {
		EquipmentClientInfo.LayerType.values();
	}
}
