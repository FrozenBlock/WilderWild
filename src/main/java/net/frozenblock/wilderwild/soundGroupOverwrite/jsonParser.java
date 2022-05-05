package net.frozenblock.wilderwild.soundGroupOverwrite;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.realms.util.JsonUtils;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;

import javax.json.Json;
import javax.json.stream.JsonGenerator;
import java.io.*;
import java.util.*;

public class jsonParser {

    public static void writeSoundsJSON() throws FileNotFoundException {
        MinecraftClient client = MinecraftClient.getInstance();
        File directory = new File(client.getResourcePackDir(), "new_sounds");
        File directory1 = new File(directory, "assets");
        directory1.mkdirs();
        FileWriter writer = null;
        FileWriter mcMetaWriter = null;

        try {
            File namespace = new File(directory1, "minecraft");
            File jsonFile = new File(namespace, "sounds.json");
            writer = new FileWriter(jsonFile);
        }
        catch (IOException e) { e.printStackTrace(); }
        try {
            File mcMeta = new File(directory, "pack.mcmeta");
            mcMetaWriter = new FileWriter(mcMeta);
        }
        catch (IOException e) { e.printStackTrace(); }
        JsonGenerator generator = Json.createGenerator(writer);
        generator.writeStartObject();

        JsonGenerator mcGen = Json.createGenerator(mcMetaWriter);
        mcGen.writeStartObject();
        mcGen.writeStartObject("pack");
        mcGen.write("pack_format", 9);
        mcGen.write("description", "Allows you to control each individual block's sounds by adding sounds to each block's respective folder.");
        mcGen.writeEnd(); mcGen.writeEnd(); mcGen.close();

        for (Block entry : Registry.BLOCK) {
            if (Objects.equals(Registry.BLOCK.getId(entry).getNamespace(), "minecraft")) {
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
                String blockString = "new_block." + Registry.BLOCK.getId(entry).getPath();

                if (!Arrays.stream(Objects.requireNonNull(breaking.listFiles(new oggFilter()))).toList().isEmpty()) {
                    generator.writeStartObject(blockString + ".break");
                    generator.writeStartArray("sounds");

                    for (File sound : Objects.requireNonNull(breaking.listFiles(new oggFilter()))) {
                        generator.write(sound.getName());
                    }
                    generator.writeEnd();
                    generator.write("subtitle", "subtitles.block.generic.break");
                    generator.writeEnd();
                }
                if (!Arrays.stream(Objects.requireNonNull(placing.listFiles(new oggFilter()))).toList().isEmpty()) {
                    generator.writeStartObject(blockString + ".place");
                    generator.writeStartArray("sounds");

                    for (File sound : Objects.requireNonNull(placing.listFiles(new oggFilter()))) {
                        generator.write(sound.getName());
                    }
                    generator.writeEnd();
                    generator.write("subtitle", "subtitles.block.generic.place");
                    generator.writeEnd();
                }
                if (!Arrays.stream(Objects.requireNonNull(stepping.listFiles(new oggFilter()))).toList().isEmpty()) {
                    generator.writeStartObject(blockString + ".step");
                    generator.writeStartArray("sounds");

                    for (File sound : Objects.requireNonNull(stepping.listFiles(new oggFilter()))) {
                        generator.write(sound.getName());
                    }
                    generator.writeEnd();
                    generator.write("subtitle", "subtitles.block.generic.footsteps");
                    generator.writeEnd();
                }
                if (!Arrays.stream(Objects.requireNonNull(hitting.listFiles(new oggFilter()))).toList().isEmpty()) {
                    generator.writeStartObject(blockString + ".hit");
                    generator.writeStartArray("sounds");

                    for (File sound : Objects.requireNonNull(hitting.listFiles(new oggFilter()))) {
                        generator.write(sound.getName());
                    }
                    generator.writeEnd();
                    generator.write("subtitle", "subtitles.block.generic.hit");
                    generator.writeEnd();
                }
                if (!Arrays.stream(Objects.requireNonNull(falling.listFiles(new oggFilter()))).toList().isEmpty()) {
                    generator.writeStartObject(blockString + ".fall");
                    generator.writeStartArray("sounds");

                    for (File sound : Objects.requireNonNull(falling.listFiles(new oggFilter()))) {
                        generator.write(sound.getName());
                    }
                    generator.writeEnd();
                    generator.write("subtitle", "subtitles.block.generic.fall");
                    generator.writeEnd();
                }
            }
        }
        generator.writeEnd();
        generator.close();
    }

    public static class oggFilter implements FilenameFilter {
        @Override
        public boolean accept(File dir, String name) { return name.endsWith(".ogg"); }
    }
}
