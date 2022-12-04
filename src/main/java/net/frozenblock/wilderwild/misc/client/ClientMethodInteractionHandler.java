package net.frozenblock.wilderwild.misc.client;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.EnderMan;

public class ClientMethodInteractionHandler {

    public static void playClientEnderManSound(EnderMan enderMan) {
        ClientMethods.playClientEnderManSound(enderMan);
    }

	public static void playClientPlayerSoundIfSamePlayer(SoundEvent sound, float volume, float pitch, Entity compareTo) {
		ClientMethods.playClientPlayerSoundIfSamePlayer(sound, volume, pitch, compareTo);
	}
}
