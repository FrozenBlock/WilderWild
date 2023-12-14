package net.frozenblock.wilderwild.networking;

import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.minecraft.network.FriendlyByteBuf;

public record LightningStrikePacket(
	int blockStateId,
	double x,
	double y,
	double z,
	int tickCount
) implements FabricPacket {

	public static final PacketType<LightningStrikePacket> PACKET_TYPE = PacketType.create(
		WilderSharedConstants.id("lightning_strike_packet"),
		LightningStrikePacket::new
	);

	public LightningStrikePacket(FriendlyByteBuf buf) {
		this(buf.readInt(), buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readVarInt());
	}

	@Override
	public void write(FriendlyByteBuf buf) {
		buf.writeInt(this.blockStateId());
		buf.writeDouble(this.x());
		buf.writeDouble(this.y());
		buf.writeDouble(this.z());
		buf.writeVarInt(this.tickCount());
	}

	@Override
	public PacketType<?> getType() {
		return null;
	}
}
