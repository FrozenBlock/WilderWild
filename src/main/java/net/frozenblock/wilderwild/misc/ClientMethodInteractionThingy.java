package net.frozenblock.wilderwild.misc;

import net.frozenblock.wilderwild.WilderWildClient;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public class ClientMethodInteractionThingy {

    public static void requestBlockEntitySync(BlockPos pos, Level world) {
        WilderWildClient.requestBlockEntitySync(pos, world);
    }

}
