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

    public static boolean OVERWRITE_FABRIC = false;

    @Nullable
    public static void makeAndGetConfig() {
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

                boolean overwriteFabric = false;
                WildConfigJson configJson = new WildConfigJson();

                if (destination.exists()) {
                    try (Reader reader = Files.newBufferedReader(destination.toPath())) {
                        JsonElement values = JsonParser.parseReader(reader);
                        if (values.isJsonObject()) {
                            JsonObject obj = values.getAsJsonObject();
                            overwriteFabric = obj.get("overwrite_fabric").getAsBoolean();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                configJson.setOverwrite_Fabric(overwriteFabric);
                String json = gson.toJson(configJson);

                try {
                    FileWriter writer = new FileWriter(destination);
                    writer.write(json);
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                OVERWRITE_FABRIC = configJson.getOverwrite_Fabric();
                hasRun = true;
            }
        }
    }

    public static boolean overwriteFabric() {
        if (!hasRun) {
            FabricLoader loader = FabricLoader.getInstance();
            if (loader != null) {
                File directory = loader.getConfigDir().toFile();
                File destination = new File(directory, "config.wild");
                directory.mkdirs();

                boolean overwriteFabric = false;
                WildConfigJson configJson = new WildConfigJson();

                if (destination.exists()) {
                    try (Reader reader = Files.newBufferedReader(destination.toPath())) {
                        JsonElement values = JsonParser.parseReader(reader);
                        if (values.isJsonObject()) {
                            JsonObject obj = values.getAsJsonObject();
                            overwriteFabric = obj.get("overwrite_fabric").getAsBoolean();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                configJson.setOverwrite_Fabric(overwriteFabric);

                OVERWRITE_FABRIC = configJson.getOverwrite_Fabric();
                hasRun = true;
                return OVERWRITE_FABRIC;
            }
        }
        return OVERWRITE_FABRIC;
    }

    public static class WildConfigJson {

        private boolean overwrite_fabric;

        public boolean getOverwrite_Fabric() {
            return overwrite_fabric;
        }

        public void setOverwrite_Fabric(boolean bool) {
            this.overwrite_fabric = bool;
        }

    }

}
