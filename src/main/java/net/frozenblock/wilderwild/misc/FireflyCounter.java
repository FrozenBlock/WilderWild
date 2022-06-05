package net.frozenblock.wilderwild.misc;

import com.google.gson.*;
import net.frozenblock.wilderwild.WilderWild;
import net.minecraft.client.MinecraftClient;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.util.Objects;

public class FireflyCounter {

    public static void addCapture(boolean creative, boolean natural) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client!=null && client.player!=null) {
            File directory = new File(client.runDirectory, "wilderwild");
            File directory1 = new File(directory, "competitions");
            File destination = new File(directory1, "fireflies.wild");
            directory1.mkdirs();
            if (creative && destination.exists()) {
                WilderWild.logInsane("WHAT!?!??! YOU'RE PLAYING IN CREATIVE MODE!!!!!! YOUR FIREFLY CAPTURING DATA IS BEING DELETED, I CAN NOT TOLERATE CHEATING ON THE FIREFLY COUNTER!!!!!!", true);
                destination.delete();
                return;
            }
            if (!natural) {
                WilderWild.logInsane("Oh no! The firefly you captured was not spawned via natural means! These will not count towards the firefly counter, you must locate a biome that spawns fireflies and capture natural ones there.", true);
                return;
            }
            Gson gson = new GsonBuilder()
                    .disableHtmlEscaping()
                    .setPrettyPrinting()
                    .serializeNulls()
                    .create();

            int count = 0;
            Counter flyCounterObj = new Counter();
            flyCounterObj.setPlayerUUID(client.player.getUuidAsString());

            if (destination.exists()) {
                try (Reader reader = Files.newBufferedReader(destination.toPath())) {
                    JsonElement counter = JsonParser.parseReader(reader);
                    if (counter.isJsonObject()) {
                        JsonObject obj = counter.getAsJsonObject();
                        count = obj.get("firefly_count").getAsInt();
                        String UUID = obj.get("player_uuid").getAsString();
                        if (!Objects.equals(UUID, flyCounterObj.getPlayerUUID())) {
                            WilderWild.logInsane("Using alt accounts to capture fireflies is against the rules as it can create inaccurate firefly capture information. Please delete wilderwild/competitions/fireflies.wild or switch back to the first account you captured a firefly with to continue.", true);
                            return;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            flyCounterObj.setCount(count+1);

            String json = gson.toJson(flyCounterObj);

            try {
                FileWriter writer = new FileWriter(destination);
                writer.write(json);
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    public static class Counter {

        private int firefly_count;
        private String player_uuid;

        public int getCount() {
            return firefly_count;
        }

        public void setCount(int count) {
            this.firefly_count = count;
        }

        public String getPlayerUUID() {
            return player_uuid;
        }

        public void setPlayerUUID(String uuid) {
            this.player_uuid = uuid;
        }

    }
}
