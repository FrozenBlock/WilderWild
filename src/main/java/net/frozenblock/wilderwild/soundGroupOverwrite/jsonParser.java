package net.frozenblock.wilderwild.soundGroupOverwrite;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.realms.util.JsonUtils;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
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
                    SoundEvent newSound = Registry.register(Registry.SOUND_EVENT, new Identifier("new_block", "block." + blockString + ".break"), new SoundEvent(new Identifier("new_block", "block." + blockString + ".break")));
                    generator.writeStartObject(blockString + ".break");
                    generator.writeStartArray("sounds");

                    for (File sound : Objects.requireNonNull(breaking.listFiles(new oggFilter()))) {
                        String addString = sound.getName().substring(0, sound.getName().lastIndexOf(".ogg"));
                        generator.write("block/" + Registry.BLOCK.getId(entry).getPath() + "/" + addString);
                    }
                    generator.writeEnd();
                    generator.write("subtitle", "subtitles.block.generic.break");
                    generator.writeEnd();
                }
                if (!Arrays.stream(Objects.requireNonNull(placing.listFiles(new oggFilter()))).toList().isEmpty()) {
                    SoundEvent newSound = Registry.register(Registry.SOUND_EVENT, new Identifier("new_block", "block." + blockString + ".place"), new SoundEvent(new Identifier("new_block", "block." + blockString + ".place")));
                    generator.writeStartObject(blockString + ".place");
                    generator.writeStartArray("sounds");

                    for (File sound : Objects.requireNonNull(placing.listFiles(new oggFilter()))) {
                        String addString = sound.getName().substring(0, sound.getName().lastIndexOf(".ogg"));
                        generator.write("block/" + Registry.BLOCK.getId(entry).getPath() + "/" + addString);
                    }
                    generator.writeEnd();
                    generator.write("subtitle", "subtitles.block.generic.place");
                    generator.writeEnd();
                }
                if (!Arrays.stream(Objects.requireNonNull(stepping.listFiles(new oggFilter()))).toList().isEmpty()) {
                    SoundEvent newSound = Registry.register(Registry.SOUND_EVENT, new Identifier("new_block", "block." + blockString + ".step"), new SoundEvent(new Identifier("new_block", "block." + blockString + ".step")));
                    generator.writeStartObject(blockString + ".step");
                    generator.writeStartArray("sounds");

                    for (File sound : Objects.requireNonNull(stepping.listFiles(new oggFilter()))) {
                        String addString = sound.getName().substring(0, sound.getName().lastIndexOf(".ogg"));
                        generator.write("block/" + Registry.BLOCK.getId(entry).getPath() + "/" + addString);
                    }
                    generator.writeEnd();
                    generator.write("subtitle", "subtitles.block.generic.footsteps");
                    generator.writeEnd();
                }
                if (!Arrays.stream(Objects.requireNonNull(hitting.listFiles(new oggFilter()))).toList().isEmpty()) {
                    SoundEvent newSound = Registry.register(Registry.SOUND_EVENT, new Identifier("new_block", "block." + blockString + ".hit"), new SoundEvent(new Identifier("new_block", "block." + blockString + ".hit")));
                    generator.writeStartObject(blockString + ".hit");
                    generator.writeStartArray("sounds");

                    for (File sound : Objects.requireNonNull(hitting.listFiles(new oggFilter()))) {
                        String addString = sound.getName().substring(0, sound.getName().lastIndexOf(".ogg"));
                        generator.write("block/" + Registry.BLOCK.getId(entry).getPath() + "/" + addString);
                    }
                    generator.writeEnd();
                    generator.write("subtitle", "subtitles.block.generic.hit");
                    generator.writeEnd();
                }
                if (!Arrays.stream(Objects.requireNonNull(falling.listFiles(new oggFilter()))).toList().isEmpty()) {
                    SoundEvent newSound = Registry.register(Registry.SOUND_EVENT, new Identifier("new_block", "block." + blockString + ".fall"), new SoundEvent(new Identifier("new_block", "block." + blockString + ".fall")));
                    generator.writeStartObject(blockString + ".fall");
                    generator.writeStartArray("sounds");

                    for (File sound : Objects.requireNonNull(falling.listFiles(new oggFilter()))) {
                        String addString = sound.getName().substring(0, sound.getName().lastIndexOf(".ogg"));
                        generator.write("block/" + Registry.BLOCK.getId(entry).getPath() + "/" + addString);
                    }
                    generator.writeEnd();
                    generator.write("subtitle", "subtitles.block.generic.fall");
                    generator.writeEnd();
                }
                BlockSoundGroup newSounds = new BlockSoundGroup(1.0F, 1.0F,
                        entry.getSoundGroup(entry.getDefaultState()).getBreakSound(),
                        entry.getSoundGroup(entry.getDefaultState()).getStepSound(),
                        entry.getSoundGroup(entry.getDefaultState()).getPlaceSound(),
                        entry.getSoundGroup(entry.getDefaultState()).getHitSound(),
                        entry.getSoundGroup(entry.getDefaultState()).getFallSound()
                );
                if (getBreakSoundFromName(Registry.BLOCK.getId(entry).getPath())!=null) {
                    newSounds = new BlockSoundGroup(1.0F, 1.0F,
                            getBreakSoundFromName(Registry.BLOCK.getId(entry).getPath()),
                            newSounds.getStepSound(),
                            newSounds.getPlaceSound(),
                            newSounds.getHitSound(),
                            newSounds.getFallSound()
                            );
                }
                if (getStepSoundFromName(Registry.BLOCK.getId(entry).getPath())!=null) {
                    newSounds = new BlockSoundGroup(1.0F, 1.0F,
                            newSounds.getBreakSound(),
                            getStepSoundFromName(Registry.BLOCK.getId(entry).getPath()),
                            newSounds.getPlaceSound(),
                            newSounds.getHitSound(),
                            newSounds.getFallSound()
                    );
                }
                if (getPlaceSoundFromName(Registry.BLOCK.getId(entry).getPath())!=null) {
                    newSounds = new BlockSoundGroup(1.0F, 1.0F,
                            newSounds.getBreakSound(),
                            newSounds.getStepSound(),
                            getPlaceSoundFromName(Registry.BLOCK.getId(entry).getPath()),
                            newSounds.getHitSound(),
                            newSounds.getFallSound()
                    );
                }
                if (getHitSoundFromName(Registry.BLOCK.getId(entry).getPath())!=null) {
                    newSounds = new BlockSoundGroup(1.0F, 1.0F,
                            newSounds.getBreakSound(),
                            newSounds.getStepSound(),
                            newSounds.getPlaceSound(),
                            getHitSoundFromName(Registry.BLOCK.getId(entry).getPath()),
                            newSounds.getFallSound()
                    );
                }
                if (getFallSoundFromName(Registry.BLOCK.getId(entry).getPath())!=null) {
                    newSounds = new BlockSoundGroup(1.0F, 1.0F,
                            newSounds.getBreakSound(),
                            newSounds.getStepSound(),
                            newSounds.getPlaceSound(),
                            newSounds.getHitSound(),
                            getFallSoundFromName(Registry.BLOCK.getId(entry).getPath())
                    );
                }
                BlockSoundGroupOverwrites.addBlock(entry, newSounds);
            }
        }
        generator.writeEnd();
        generator.close();
    }

    public static SoundEvent getBreakSoundFromName(String string) {
        return Registry.SOUND_EVENT.get(new Identifier("new_sound", "block." + string + ".break"));
    }
    public static SoundEvent getPlaceSoundFromName(String string) {
        return Registry.SOUND_EVENT.get(new Identifier("new_sound", "block." + string + ".place"));
    }
    public static SoundEvent getStepSoundFromName(String string) {
        return Registry.SOUND_EVENT.get(new Identifier("new_sound", "block." + string + ".step"));
    }
    public static SoundEvent getHitSoundFromName(String string) {
        return Registry.SOUND_EVENT.get(new Identifier("new_sound", "block." + string + ".hit"));
    }
    public static SoundEvent getFallSoundFromName(String string) {
        return Registry.SOUND_EVENT.get(new Identifier("new_sound", "block." + string + ".fall"));
    }

    public static class oggFilter implements FilenameFilter {
        @Override
        public boolean accept(File dir, String name) { return name.endsWith(".ogg"); }
    }
}
