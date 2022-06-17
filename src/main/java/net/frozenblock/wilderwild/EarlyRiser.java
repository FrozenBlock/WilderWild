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

        /*String boatType = remapper.mapClassName("intermediary","net.minecraft.class_1690$class_1692");
        String block = "L" + remapper.mapClassName("intermediary","net.minecraft.class_2248") + ";";
        //COMMENTED OUT BECAUSE IT REFUSES TO WORK
        ClassTinkerers.enumBuilder(boatType, block, String.class).addEnum("BAOBAB", RegisterBlocks.BAOBAB_PLANKS, "baobab").build();*/
    }
}
