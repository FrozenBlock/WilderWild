package net.frozenblock.wilderwild.networking;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.frozenblock.wilderwild.networking.packet.WilderControlledSeedParticlePacket;
import net.frozenblock.wilderwild.networking.packet.WilderFloatingSculkBubbleParticlePacket;
import net.frozenblock.wilderwild.networking.packet.WilderLightningStrikePacket;
import net.frozenblock.wilderwild.networking.packet.WilderSeedParticlePacket;
import net.frozenblock.wilderwild.networking.packet.WilderSensorHiccupPacket;
import net.frozenblock.wilderwild.networking.packet.WilderTermiteParticlePacket;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.sounds.SoundSource;

@Environment(EnvType.CLIENT)
public class WilderClientNetworking {

	public static void registerPacketReceivers() {
		WilderFloatingSculkBubbleParticlePacket.receive();
		WilderSeedParticlePacket.receive();
		WilderControlledSeedParticlePacket.receive();
		WilderTermiteParticlePacket.receive();
		WilderSensorHiccupPacket.receive();
		receiveJellyStingPacket();
		WilderLightningStrikePacket.receive();
	}

	private static void receiveJellyStingPacket() {
		ClientPlayNetworking.registerGlobalReceiver(WilderNetworking.JELLY_STING_PACKET, (ctx, handler, byteBuf, responseSender) -> ctx.execute(() -> {
			boolean baby = byteBuf.readBoolean();
			if (ctx.level != null) {
				LocalPlayer player = ctx.player;
				if (player != null) {
					ctx.level.playSound(player, player.getX(), player.getY(), player.getZ(), RegisterSounds.ENTITY_JELLYFISH_STING, SoundSource.NEUTRAL, 1.0F, ctx.level.random.nextFloat() * 0.2F + (baby ? 1.2F : 0.9F));
				}
			}
		}));
	}

}
