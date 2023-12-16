package net.frozenblock.wilderwild.networking.packet;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.particle.options.SeedParticleOptions;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public record WilderSeedParticlePacket(double x, double y, double z, int count, boolean isMilkweed) implements FabricPacket {

	public static final PacketType<WilderSeedParticlePacket> PACKET_TYPE = PacketType.create(
			WilderSharedConstants.id("seed_particle_packet"),
			WilderSeedParticlePacket::new
	);

	public WilderSeedParticlePacket(@NotNull FriendlyByteBuf buf) {
		this(buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readVarInt(), buf.readBoolean());
	}

	public static void sendToAll(@NotNull Level level, @NotNull Vec3 pos, int count, boolean isMilkweed) {
		WilderSeedParticlePacket seedParticlePacket = new WilderSeedParticlePacket(
				pos.x(),
				pos.y(),
				pos.z(),
				count,
				isMilkweed
		);
		for (ServerPlayer player : PlayerLookup.tracking((ServerLevel) level, BlockPos.containing(pos))) {
			ServerPlayNetworking.send(player, seedParticlePacket);
		}
	}

	@Environment(EnvType.CLIENT)
	public static void receive() {
		ClientPlayNetworking.registerGlobalReceiver(PACKET_TYPE, (packet, player, responseSender) -> {
			ClientLevel clientLevel = player.clientLevel;
			if (clientLevel != null) {
				for (int i = 0; i < packet.count(); i++) {
					clientLevel.addParticle(new SeedParticleOptions(packet.isMilkweed(), false), packet.x(), packet.y(), packet.z(), 0, 0, 0);
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
		buf.writeBoolean(this.isMilkweed());
	}

	@Override
	public PacketType<?> getType() {
		return PACKET_TYPE;
	}
}
