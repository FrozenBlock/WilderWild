package net.frozenblock.wilderwild.networking.packet;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import org.jetbrains.annotations.NotNull;

public record WilderJellyfishStingPacket(boolean isBaby) implements FabricPacket {

	public static final PacketType<WilderJellyfishStingPacket> PACKET_TYPE = PacketType.create(
			WilderSharedConstants.id("jellyfish_sting_packet"),
			WilderJellyfishStingPacket::new
	);

	public WilderJellyfishStingPacket(@NotNull FriendlyByteBuf buf) {
		this(buf.readBoolean());
	}

	public static void sendTo(ServerPlayer serverPlayer, boolean isBaby) {
		WilderJellyfishStingPacket jellyfishStingPacket = new WilderJellyfishStingPacket(isBaby);
		ServerPlayNetworking.send(serverPlayer, jellyfishStingPacket);
	}

	@Environment(EnvType.CLIENT)
	public static void receive() {
		ClientPlayNetworking.registerGlobalReceiver(PACKET_TYPE, (packet, player, responseSender) -> {
			ClientLevel clientLevel = player.clientLevel;
			if (clientLevel != null) {
				clientLevel.playSound(
						player,
						player.getX(),
						player.getY(),
						player.getZ(),
						RegisterSounds.ENTITY_JELLYFISH_STING,
						SoundSource.NEUTRAL,
						1.0F,
						clientLevel.getRandom().nextFloat() * 0.2F + (packet.isBaby() ? 1.2F : 0.9F)
				);
			}
		});
	}

	@Override
	public void write(@NotNull FriendlyByteBuf buf) {
		buf.writeBoolean(this.isBaby());
	}

	@Override
	public PacketType<?> getType() {
		return PACKET_TYPE;
	}
}
