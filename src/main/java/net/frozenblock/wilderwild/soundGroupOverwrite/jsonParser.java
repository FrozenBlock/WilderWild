package net.frozenblock.wilderwild.soundGroupOverwrite;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;

import java.io.File;
import java.io.FilenameFilter;
import java.util.*;

public class jsonParser {
/*"block.echo_glass.crack": {
        "sounds": [
        "wilderwild:block/echo_glass/crack1",
                "wilderwild:block/echo_glass/crack2",
                "wilderwild:block/echo_glass/crack3"
		]
    },*/
    public static void writeSoundsJSON() {
        MinecraftClient client = MinecraftClient.getInstance();
        File directory = new File(client.getResourcePackDir(), "new_sounds");
        File directory1 = new File(directory, "assets");
        directory1.mkdirs();
        JsonObject json = new JsonObject();

        for (Block entry : Registry.BLOCK) {
            File namespace = new File(directory1, Registry.BLOCK.getId(entry).getNamespace());
            File sounds = new File(namespace, "sounds");
            File blocks = new File(sounds, "block");
            File block = new File(blocks, Registry.BLOCK.getId(entry).getPath());
            block.mkdirs();

            File placing = new File(block, "place");
            placing.mkdirs();
            File breaking = new File(block, "break");
            breaking.mkdirs();
            File stepping = new File(block, "step");
            stepping.mkdirs();
            File hitting = new File(block, "hit");
            hitting.mkdirs();
            File falling = new File(block, "fall");
            falling.mkdirs();

            Map<String, String> map = new LinkedHashMap<>();
            //map.put(); NEED HELP
            JsonArray breakMap = new JsonArray();
            for (File sound : Objects.requireNonNull(breaking.listFiles(new oggFilter()))) {
                breakMap.add(sound.getName());
            }
            String blockString = "block." + Registry.BLOCK.getId(entry).getPath();
            json.add((blockString + ".break"), breakMap);
        }
    }

    public static class oggFilter implements FilenameFilter {
        @Override
        public boolean accept(File dir, String name) { return name.endsWith(".ogg"); }
    }
}
