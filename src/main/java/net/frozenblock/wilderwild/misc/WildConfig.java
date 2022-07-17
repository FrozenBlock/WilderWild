package net.frozenblock.wilderwild.misc;

import com.google.gson.*;
import net.fabricmc.loader.api.FabricLoader;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;

public class WildConfig {
    public static boolean hasRun = false;

    public static WildConfigJson config = null;

    @Nullable
    public static void makeConfig() {
        if (!hasRun) {
            FabricLoader loader = FabricLoader.getInstance();
            if (loader != null) {
                File directory = loader.getConfigDir().toFile();
                File destination = new File(directory, "config.wild");
                directory.mkdirs();
                Gson gson = new GsonBuilder()
                        .disableHtmlEscaping()
                        .setPrettyPrinting()
                        .serializeNulls()
                        .create();

                WildConfigJson configJson = new WildConfigJson();
                boolean skipWriting = false;

                if (destination.exists()) {
                    try (Reader reader = Files.newBufferedReader(destination.toPath())) {
                        JsonElement values = JsonParser.parseReader(reader);
                        if (values.isJsonObject()) {
                            JsonObject obj = values.getAsJsonObject();
                            configJson.setOverwrite_Fabric(obj.get("overwrite_fabric").getAsBoolean());
                            configJson.setInclude_Wild(obj.get("include_wild").getAsBoolean());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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
    }

    @Nullable
    public static WildConfigJson getConfig() {
        if (!hasRun) {
            FabricLoader loader = FabricLoader.getInstance();
            if (loader != null) {
                if (config == null) {
                    makeConfig();
                }
                File directory = loader.getConfigDir().toFile();
                File destination = new File(directory, "config.wild");
                directory.mkdirs();

                WildConfigJson configJson = new WildConfigJson();

                if (destination.exists()) {
                    try (Reader reader = Files.newBufferedReader(destination.toPath())) {
                        JsonElement values = JsonParser.parseReader(reader);
                        if (values.isJsonObject()) {
                            JsonObject obj = values.getAsJsonObject();
                            configJson.setOverwrite_Fabric(obj.get("overwrite_fabric").getAsBoolean());
                            configJson.setInclude_Wild(obj.get("include_wild").getAsBoolean());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                config = configJson;
                hasRun = true;
                return config;
            }
        }
        return config;
    }

    public static class WildConfigJson {

        private boolean overwrite_fabric;
        private boolean include_wild;

        public boolean getOverwrite_Fabric() {
            return overwrite_fabric;
        }

        public void setOverwrite_Fabric(boolean bool) {
            this.overwrite_fabric = bool;
        }

        public boolean getIncludeWild() {
            return include_wild;
        }

        public void setInclude_Wild(boolean bool) {
            this.include_wild = bool;
        }

    }

}
