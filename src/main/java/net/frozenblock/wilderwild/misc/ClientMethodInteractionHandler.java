package net.frozenblock.wilderwild.misc;

import net.frozenblock.lib.sound.api.instances.RestrictedMovingSound;
import net.minecraft.world.entity.monster.EnderMan;

public class ClientMethodInteractionHandler {

    public static void playClientEnderManSound(EnderMan enderMan) {
        ClientMethods.playClientEnderManSound(enderMan);
    }

}
