package net.frozenblock.wilderwild.networking.packet;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.registry.RegisterParticles;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public record WilderTermiteParticlePacket(double x, double y, double z, int count) implements FabricPacket {

	public static final PacketType<WilderTermiteParticlePacket> PACKET_TYPE = PacketType.create(
			WilderSharedConstants.id("termite_particle_packet"),
			WilderTermiteParticlePacket::new
	);

	public WilderTermiteParticlePacket(@NotNull FriendlyByteBuf buf) {
		this(buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readVarInt());
	}

	public static void sendToAll(@NotNull Level level, @NotNull Vec3 pos, int count) {
		WilderTermiteParticlePacket termiteParticlePacket = new WilderTermiteParticlePacket(
				pos.x(),
				pos.y(),
				pos.z(),
				count
		);
		for (ServerPlayer player : PlayerLookup.tracking((ServerLevel) level, BlockPos.containing(pos))) {
			ServerPlayNetworking.send(player, termiteParticlePacket);
		}
	}

	@Environment(EnvType.CLIENT)
	public static void receive() {
		ClientPlayNetworking.registerGlobalReceiver(PACKET_TYPE, (packet, player, responseSender) -> {
			ClientLevel clientLevel = player.clientLevel;
			if (clientLevel != null) {
				int count = packet.count() - 1;
				clientLevel.addAlwaysVisibleParticle(RegisterParticles.TERMITE, packet.x(), packet.y(), packet.z(), 0, 0, 0);
				for (int i = 0; i < count; i++) {
					clientLevel.addParticle(RegisterParticles.TERMITE, packet.x(), packet.y(), packet.z(), 0, 0, 0);
				}
			}
		});
	}

	@Override
	public void write(@NotNull FriendlyByteBuf buf) {
		buf.writeDouble(this.x());
		buf.writeDouble(this.y());
		buf.writeDouble(this.z());
		buf.writeVarInt(this.count());
	}

	@Override
	public PacketType<?> getType() {
		return PACKET_TYPE;
	}
}
