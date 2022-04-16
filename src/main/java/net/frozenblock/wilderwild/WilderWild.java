package net.frozenblock.wilderwild;

import net.fabricmc.api.ModInitializer;
import net.frozenblock.wilderwild.block.ModBlocks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WilderWild implements ModInitializer {
    public static final String MOD_ID = "wilderwild";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {

        ModBlocks.registerModBlocks();

    }
}
