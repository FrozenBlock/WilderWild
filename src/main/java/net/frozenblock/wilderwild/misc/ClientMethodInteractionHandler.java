package net.frozenblock.wilderwild.misc;

import net.minecraft.world.entity.monster.EnderMan;

public class ClientMethodInteractionHandler {

    public static void playClientEnderManSound(EnderMan enderMan) {
        ClientMethods.playClientEnderManSound(enderMan);
    }

	public static void playEnderManAngerLoop(EnderMan enderMan) {
		ClientMethods.playClientEnderManLoop(enderMan);
	}
}
