package net.frozenblock.wilderwild.misc;

import net.frozenblock.wilderwild.WilderWildClient;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ClientMethodInteractionThingy {

    public static void requestBlockEntitySync(BlockPos pos, World world) {
        WilderWildClient.requestBlockEntitySync(pos, world);
    }

}
