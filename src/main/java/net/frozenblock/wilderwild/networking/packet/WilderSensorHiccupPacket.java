package net.frozenblock.wilderwild.networking.packet;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public record WilderSensorHiccupPacket(double x, double y, double z) implements FabricPacket {
	@Environment(EnvType.CLIENT)
	private static final int PARTICLE_COLOR = 5578058;

	@Environment(EnvType.CLIENT)
	private static final double COLOR_X = (double) (PARTICLE_COLOR >> 16 & 255) / 255.0D;

	@Environment(EnvType.CLIENT)
	private static final double COLOR_Y = (double) (PARTICLE_COLOR >> 8 & 255) / 255.0D;

	@Environment(EnvType.CLIENT)
	private static final double COLOR_Z = (double) (PARTICLE_COLOR & 255) / 255.0D;

	public static final PacketType<WilderSensorHiccupPacket> PACKET_TYPE = PacketType.create(
			WilderSharedConstants.id("sensor_hiccup_packet"),
			WilderSensorHiccupPacket::new
	);

	public WilderSensorHiccupPacket(@NotNull FriendlyByteBuf buf) {
		this(buf.readDouble(), buf.readDouble(), buf.readDouble());
	}

	public static void sendToAll(@NotNull BlockEntity blockEntity, @NotNull Vec3 pos) {
		WilderSensorHiccupPacket sensorHiccupPacket = new WilderSensorHiccupPacket(
				pos.x(),
				pos.y(),
				pos.z()
		);
		for (ServerPlayer player : PlayerLookup.tracking(blockEntity)) {
			ServerPlayNetworking.send(player, sensorHiccupPacket);
		}
	}

	@Environment(EnvType.CLIENT)
	public static void receive() {
		ClientPlayNetworking.registerGlobalReceiver(PACKET_TYPE, (packet, player, responseSender) -> {
			ClientLevel clientLevel = player.clientLevel;
			if (clientLevel != null) {
				clientLevel.addParticle(
						ParticleTypes.ENTITY_EFFECT,
						packet.x(),
						packet.y(),
						packet.z(),
						COLOR_X,
						COLOR_Y,
						COLOR_Z
				);
			}
		});
	}

	@Override
	public void write(@NotNull FriendlyByteBuf buf) {
		buf.writeDouble(this.x());
		buf.writeDouble(this.y());
		buf.writeDouble(this.z());
	}

	@Override
	public PacketType<?> getType() {
		return PACKET_TYPE;
	}
}
