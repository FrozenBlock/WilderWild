package net.frozenblock.wilderwild.misc;

import net.frozenblock.lib.sound.MovingSoundWithRestriction;
import net.minecraft.world.entity.monster.EnderMan;

public class ClientMethodInteractionHandler {

    public static MovingSoundWithRestriction<EnderMan> playClientEnderManSound(EnderMan enderMan) {
        return ClientMethods.playClientEnderManSound(enderMan);
    }

}
