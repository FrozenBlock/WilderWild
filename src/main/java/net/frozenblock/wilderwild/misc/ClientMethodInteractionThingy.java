package net.frozenblock.wilderwild.misc;

import net.frozenblock.wilderwild.WilderWildClient;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ClientMethodInteractionThingy {

    public static void requestClientLanternBlahBlahBlah(BlockPos pos, World world) {
        WilderWildClient.requestLanternSync(pos, world);
    }

}
