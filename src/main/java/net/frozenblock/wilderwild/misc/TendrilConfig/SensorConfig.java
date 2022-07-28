package net.frozenblock.wilderwild.misc.TendrilConfig;

import com.google.gson.*;
import net.fabricmc.loader.api.FabricLoader;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;

public class SensorConfig {
    public static boolean hasRun = false;

    public static ConfigJson config = null;

    public static void makeConfig() {
        FabricLoader loader = FabricLoader.getInstance();
        if (loader != null) {
            File directory = loader.getConfigDir().toFile();
            directory.mkdirs();
            File destination = new File(directory, "sculk_sensor.config");

            Gson gson = new GsonBuilder()
                    .disableHtmlEscaping()
                    .setPrettyPrinting()
                    .serializeNulls()
                    .create();

            ConfigJson configJson = new ConfigJson();

            if (destination.exists()) {
                try (Reader reader = Files.newBufferedReader(destination.toPath())) {
                    JsonElement values = JsonParser.parseReader(reader);
                    if (values.isJsonObject()) {
                        JsonObject obj = values.getAsJsonObject();
                        configJson.setTendrils(obj.get("show_tendrils").getAsBoolean());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                configJson.setTendrils(false);
            }

            String json = gson.toJson(configJson);

            try {
                FileWriter writer = new FileWriter(destination);
                writer.write(json);
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            config = configJson;
            hasRun = true;
        }
    }

    @Nullable
    public static ConfigJson getConfig(boolean ignore) {
        if (!hasRun || ignore) {
            FabricLoader loader = FabricLoader.getInstance();
            if (loader != null) {
                if (config == null) {
                    makeConfig();
                }
                File directory = loader.getConfigDir().toFile();
                directory.mkdirs();
                File destination = new File(directory, "sculk_sensor.config");

                ConfigJson configJson = new ConfigJson();

                if (destination.exists()) {
                    try (Reader reader = Files.newBufferedReader(destination.toPath())) {
                        JsonElement values = JsonParser.parseReader(reader);
                        if (values.isJsonObject()) {
                            JsonObject obj = values.getAsJsonObject();
                            configJson.setTendrils(obj.get("show_tendrils").getAsBoolean());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                hasRun = true;
                return config;
            }
        }
        return config;
    }

    public static void rewriteConfig() {
        FabricLoader loader = FabricLoader.getInstance();
        if (loader != null) {
            File directory = loader.getConfigDir().toFile();
            directory.mkdirs();
            File destination = new File(directory, "sculk_sensor.config");

            Gson gson = new GsonBuilder()
                    .disableHtmlEscaping()
                    .setPrettyPrinting()
                    .serializeNulls()
                    .create();

            String json = gson.toJson(config);

            try {
                FileWriter writer = new FileWriter(destination);
                writer.write(json);
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            config = getConfig(true);
        }
    }

    public static class ConfigJson {

        private boolean show_tendrils;
        public boolean getTendrils() { return show_tendrils; }
        public void setTendrils(boolean bool) { this.show_tendrils = bool; }

    }

}
