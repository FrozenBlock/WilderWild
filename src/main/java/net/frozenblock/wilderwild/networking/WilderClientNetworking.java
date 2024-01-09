package net.frozenblock.wilderwild.networking;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.networking.packet.WilderJellyfishStingPacket;
import net.frozenblock.wilderwild.networking.packet.WilderLightningStrikePacket;
import net.frozenblock.wilderwild.networking.packet.WilderSensorHiccupPacket;

@Environment(EnvType.CLIENT)
public class WilderClientNetworking {

	public static void registerPacketReceivers() {
		WilderSensorHiccupPacket.receive();
		WilderJellyfishStingPacket.receive();
		WilderLightningStrikePacket.receive();
	}

}
