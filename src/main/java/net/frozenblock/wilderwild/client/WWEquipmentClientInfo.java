package net.frozenblock.wilderwild.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.resources.model.EquipmentClientInfo;

@Environment(EnvType.CLIENT)
public class WWEquipmentClientInfo {
	public static EquipmentClientInfo.LayerType OSTRICH_SADDLE;

	static {
		EquipmentClientInfo.LayerType.values();
	}
}
