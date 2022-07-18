package net.frozenblock.wilderwild.misc.PVZGWSound;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.frozenblock.wilderwild.WilderWild;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class FlyBySoundPacket {

    public static void createFlybySound(World world, Entity entity, SoundEvent sound, SoundCategory category, float volume, float pitch) {
        if (world.isClient)
            throw new IllegalStateException("no sounds on the client, you freaking idiot!");
        PacketByteBuf byteBuf = new PacketByteBuf(Unpooled.buffer());
        byteBuf.writeVarInt(entity.getId());
        byteBuf.writeRegistryValue(Registry.SOUND_EVENT, sound);
        byteBuf.writeEnumConstant(category);
        byteBuf.writeFloat(volume);
        byteBuf.writeFloat(pitch);
        for (ServerPlayerEntity player : PlayerLookup.around((ServerWorld) world, entity.getBlockPos(), 128)) {
            ServerPlayNetworking.send(player, WilderWild.FLYBY_SOUND_PACKET, byteBuf);
        }
    }

}
