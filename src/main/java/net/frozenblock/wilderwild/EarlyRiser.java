package net.frozenblock.wilderwild;

import com.chocohead.mm.api.ClassTinkerers;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.MappingResolver;


public class EarlyRiser implements Runnable {
    @Override
    public void run() {
        MappingResolver remapper = FabricLoader.getInstance().getMappingResolver();

        String spawnGroup = remapper.mapClassName("intermediary", "net.minecraft.class_1311");
        ClassTinkerers.enumBuilder(spawnGroup, String.class, int.class, boolean.class, boolean.class, int.class).addEnum("FIREFLIES", "fireflies", 56, true, false, 80).build();

        String entityPose = remapper.mapClassName("intermediary", "net.minecraft.class_4050");
        ClassTinkerers.enumBuilder(entityPose).addEnum("SWIMMING_ROARING").build();
        ClassTinkerers.enumBuilder(entityPose).addEnum("SWIMMING_SNIFFING").build();
        ClassTinkerers.enumBuilder(entityPose).addEnum("SWIMMING_DYING").build();
        ClassTinkerers.enumBuilder(entityPose).addEnum("SWIMMING_EMERGING").build();
    }
}
