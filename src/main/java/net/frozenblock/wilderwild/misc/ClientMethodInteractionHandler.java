package net.frozenblock.wilderwild.misc;

import net.frozenblock.lib.sound.api.instances.RestrictedMovingSound;
import net.minecraft.world.entity.monster.EnderMan;

public class ClientMethodInteractionHandler {

    public static RestrictedMovingSound<EnderMan> playClientEnderManSound(EnderMan enderMan) {
        return ClientMethods.playClientEnderManSound(enderMan);
    }

}
