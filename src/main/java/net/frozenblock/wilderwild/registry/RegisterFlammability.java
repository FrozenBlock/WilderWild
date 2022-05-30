package net.frozenblock.wilderwild.registry;

import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.frozenblock.wilderwild.WilderWild;

public class RegisterFlammability {

    public static void register() {
        WilderWild.logWild("Registering Flammability for", true);
        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.POLLEN_BLOCK,100,60);
        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.WHITE_DANDELION,100,60);
        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.CARNATION,100,60);
        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.CATTAIL,100,60);
        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.DATURA,100,60);
        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.MILKWEED,100,60);

        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.HOLLOWED_BIRCH_LOG,5,5);
        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.HOLLOWED_OAK_LOG,5,5);
        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.HOLLOWED_ACACIA_LOG,5,5);
        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.HOLLOWED_JUNGLE_LOG,5,5);
        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.HOLLOWED_DARK_OAK_LOG,5,5);
        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.HOLLOWED_MANGROVE_LOG,5,5);
        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.HOLLOWED_SPRUCE_LOG,5,5);
    }

}
