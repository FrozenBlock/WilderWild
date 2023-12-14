package net.frozenblock.wilderwild.networking.packet;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.frozenblock.lib.math.api.AdvancedMath;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.networking.WilderNetworking;
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

public record WilderFloatingSculkBubblePacket(double x, double y, double z, double size, int maxAge, double yd, int count) implements FabricPacket {

	public static final PacketType<WilderFloatingSculkBubblePacket> PACKET_TYPE = PacketType.create(
			WilderSharedConstants.id("floating_sculk_bubble_packet"),
			WilderFloatingSculkBubblePacket::new
	);

	public WilderFloatingSculkBubblePacket(@NotNull FriendlyByteBuf buf) {
		this(buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readVarInt(), buf.readDouble(), buf.readVarInt());
	}

	public static void sendToAll(@NotNull Level level, @NotNull Vec3 pos, double size, int maxAge, double yVel, int count) {
		WilderFloatingSculkBubblePacket seedParticlePacket = new WilderFloatingSculkBubblePacket(
				pos.x(),
				pos.y(),
				pos.z(),
				size,
				maxAge,
				yVel,
				count
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

	private static void receiveEasyEchoerBubblePacket() {
		ClientPlayNetworking.registerGlobalReceiver(WilderNetworking.FLOATING_SCULK_BUBBLE_PACKET, (ctx, handler, byteBuf, responseSender) -> {
			Vec3 pos = new Vec3(byteBuf.readDouble(), byteBuf.readDouble(), byteBuf.readDouble());
			double size = byteBuf.readDouble();
			int age = byteBuf.readInt();
			double yVel = byteBuf.readDouble();
			int count = byteBuf.readVarInt();
			ctx.execute(() -> {
				if (ctx.level == null)
					throw new IllegalStateException("why is your world null");
				var random = AdvancedMath.random();
				for (int i = 0; i < count; i++) {
					double xVel = (random.nextDouble() - 0.5) / 9.5;
					double zVel = (random.nextDouble() - 0.5) / 9.5;
					if (size >= 1) {
						xVel = (random.nextDouble() - 0.5) / 10.5;
						zVel = (random.nextDouble() - 0.5) / 10.5;
					}
					ctx.level.addParticle(new FloatingSculkBubbleParticleOptions(size, age, new Vec3(xVel, yVel, zVel)), pos.x, pos.y, pos.z, 0, 0, 0);
				}
			});
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
