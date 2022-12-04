package net.frozenblock.wilderwild;

import com.chocohead.mm.api.ClassTinkerers;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.MappingResolver;
import net.frozenblock.wilderwild.misc.config.ClothConfigInteractionHandler;

/**
 * For Fabric ASM
 */
public final class EarlyRiser implements Runnable {
    @Override
    public void run() {
        MappingResolver remapper = FabricLoader.getInstance().getMappingResolver();

        String spawnGroup = remapper.mapClassName("intermediary", "net.minecraft.class_1311");
        ClassTinkerers.enumBuilder(spawnGroup, String.class, int.class, boolean.class, boolean.class, int.class).addEnum("WILDERWILDFIREFLIES", "wilderwildfireflies", ClothConfigInteractionHandler.fireflySpawnCap(), true, false, 80).build();
        ClassTinkerers.enumBuilder(spawnGroup, String.class, int.class, boolean.class, boolean.class, int.class).addEnum("WILDERWILDJELLYFISH", "wilderwildjellyfish", ClothConfigInteractionHandler.jellyfishSpawnCap(), true, false, 64).build();
    }
}
