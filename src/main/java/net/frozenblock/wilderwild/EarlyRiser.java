package net.frozenblock.wilderwild;

import com.chocohead.mm.api.ClassTinkerers;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.MappingResolver;

/**
 * For Fabric ASM
 */
public final class EarlyRiser implements Runnable {
    @Override
    public void run() {
        MappingResolver remapper = FabricLoader.getInstance().getMappingResolver();

        String mobCategory = remapper.mapClassName("intermediary", "net.minecraft.class_1311");
        ClassTinkerers.enumBuilder(mobCategory, String.class, int.class, boolean.class, boolean.class, int.class).addEnum("WILDERWILDFIREFLIES", "wilderwildfireflies", 56, true, false, 80).build();
        ClassTinkerers.enumBuilder(mobCategory, String.class, int.class, boolean.class, boolean.class, int.class).addEnum("WILDERWILDJELLYFISH", "wilderwildjellyfish", 30, true, false, 64).build();
		ClassTinkerers.enumBuilder(mobCategory, String.class, int.class, boolean.class, boolean.class, int.class).addEnum("WILDERWILDTUMBLEWEED", "wilderwildtumbleweed", 16, true, false, 64).build();
	}
}
