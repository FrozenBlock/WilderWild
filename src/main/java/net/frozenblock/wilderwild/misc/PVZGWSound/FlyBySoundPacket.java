package net.frozenblock.wilderwild.misc.PVZGWSound;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.frozenblock.wilderwild.WilderWild;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

public class FlyBySoundPacket {

    public static void createFlybySound(Level world, Entity entity, SoundEvent sound, SoundSource category, float volume, float pitch) {
        if (world.isClientSide)
            throw new IllegalStateException("no sounds on the client, you freaking idiot!");
        FriendlyByteBuf byteBuf = new FriendlyByteBuf(Unpooled.buffer());
        byteBuf.writeVarInt(entity.getId());
        byteBuf.writeId(Registry.SOUND_EVENT, sound);
        byteBuf.writeEnum(category);
        byteBuf.writeFloat(volume);
        byteBuf.writeFloat(pitch);
        for (ServerPlayer player : PlayerLookup.around((ServerLevel) world, entity.blockPosition(), 128)) {
            ServerPlayNetworking.send(player, WilderWild.FLYBY_SOUND_PACKET, byteBuf);
        }
    }

}
