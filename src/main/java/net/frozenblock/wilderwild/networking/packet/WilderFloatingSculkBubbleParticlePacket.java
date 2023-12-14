package net.frozenblock.wilderwild.networking.packet;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.particle.options.FloatingSculkBubbleParticleOptions;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public record WilderFloatingSculkBubbleParticlePacket(double x, double y, double z, double size, int maxAge, double yd, int count) implements FabricPacket {

	public static final PacketType<WilderFloatingSculkBubbleParticlePacket> PACKET_TYPE = PacketType.create(
			WilderSharedConstants.id("floating_sculk_bubble_packet"),
			WilderFloatingSculkBubbleParticlePacket::new
	);

	public WilderFloatingSculkBubbleParticlePacket(@NotNull FriendlyByteBuf buf) {
		this(buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readVarInt(), buf.readDouble(), buf.readVarInt());
	}

	public static void sendToAll(@NotNull Level level, @NotNull Vec3 pos, double size, int maxAge, double yVel, int count) {
		WilderFloatingSculkBubbleParticlePacket floatingSculkBubbleParticlePacket = new WilderFloatingSculkBubbleParticlePacket(
				pos.x(),
				pos.y(),
				pos.z(),
				size,
				maxAge,
				yVel,
				count
		);
		for (ServerPlayer player : PlayerLookup.tracking((ServerLevel) level, BlockPos.containing(pos))) {
			ServerPlayNetworking.send(player, floatingSculkBubbleParticlePacket);
		}
	}

	@Environment(EnvType.CLIENT)
	public static void receive() {
		ClientPlayNetworking.registerGlobalReceiver(PACKET_TYPE, (packet, player, responseSender) -> {
			ClientLevel clientLevel = player.clientLevel;
			if (clientLevel != null) {
				RandomSource random = clientLevel.getRandom();
				for (int i = 0; i < packet.count(); i++) {
					double xd = (random.nextDouble() - 0.5D) / 9.5D;
					double zd = (random.nextDouble() - 0.5D) / 9.5D;
					if (packet.size() >= 1) {
						xd = (random.nextDouble() - 0.5D) / 10.5D;
						zd = (random.nextDouble() - 0.5D) / 10.5D;
					}
					clientLevel.addParticle(
							new FloatingSculkBubbleParticleOptions(packet.size(), packet.maxAge(),new Vec3(xd, packet.yd(), zd)),
							packet.x(),
							packet.y(),
							packet.z(),
							0,
							0,
							0
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
		buf.writeDouble(this.size());
		buf.writeVarInt(this.maxAge());
		buf.writeDouble(this.yd());
		buf.writeVarInt(this.count());
	}

	@Override
	public PacketType<?> getType() {
		return PACKET_TYPE;
	}
}
