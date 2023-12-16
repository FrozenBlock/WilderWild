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
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public record WilderControlledSeedParticlePacket(double x, double y, double z, double xd, double yd, double zd, int count, boolean isMilkweed, double randomizer) implements FabricPacket {

	public static final PacketType<WilderControlledSeedParticlePacket> PACKET_TYPE = PacketType.create(
			WilderSharedConstants.id("controlled_seed_particle_packet"),
			WilderControlledSeedParticlePacket::new
	);

	public WilderControlledSeedParticlePacket(@NotNull FriendlyByteBuf buf) {
		this(buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readVarInt(), buf.readBoolean(), buf.readDouble());
	}

	public static void sendToAll(@NotNull Level level, @NotNull Vec3 pos, double xd, double yd, double zd, int count, boolean isMilkweed, double randomizer) {
		WilderControlledSeedParticlePacket controlledSeedParticlePacket = new WilderControlledSeedParticlePacket(
				pos.x(),
				pos.y(),
				pos.z(),
				xd,
				yd,
				zd,
				count,
				isMilkweed,
				randomizer
		);
		for (ServerPlayer player : PlayerLookup.tracking((ServerLevel) level, BlockPos.containing(pos))) {
			ServerPlayNetworking.send(player, controlledSeedParticlePacket);
		}
	}

	@Environment(EnvType.CLIENT)
	public static void receive() {
		ClientPlayNetworking.registerGlobalReceiver(PACKET_TYPE, (packet, player, responseSender) -> {
			ClientLevel clientLevel = player.clientLevel;
			if (clientLevel != null) {
				RandomSource random = clientLevel.getRandom();
				for (int i = 0; i < packet.count(); i++) {
					clientLevel.addParticle(
							new SeedParticleOptions(packet.isMilkweed(), true),
							packet.x(),
							packet.y() + ((random.nextBoolean() ? -1D : 1D) * (random.nextDouble() * packet.randomizer())),
							packet.z(),
							packet.xd() * 1.5D,
							packet.yd() + (random.nextDouble() * 0.07D),
							packet.zd() * 1.5D
					);
				}
			}
		});
	}

	@Override
	public void write(@NotNull FriendlyByteBuf buf) {
		buf.writeDouble(this.x());
		buf.writeDouble(this.y());
		buf.writeDouble(this.z());
		buf.writeDouble(this.xd());
		buf.writeDouble(this.yd());
		buf.writeDouble(this.zd());
		buf.writeVarInt(this.count());
		buf.writeBoolean(this.isMilkweed());
		buf.writeDouble(this.zd());
	}

	@Override
	public PacketType<?> getType() {
		return PACKET_TYPE;
	}
}
