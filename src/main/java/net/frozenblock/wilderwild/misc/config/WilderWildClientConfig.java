package net.frozenblock.wilderwild.misc.config;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.WilderWild;
import net.minecraft.client.gui.screens.Screen;

@Environment(EnvType.CLIENT)
@Config(name = WilderWild.MOD_ID)
public class WilderWildClientConfig extends WilderWildConfig implements ConfigData {

    @Environment(EnvType.CLIENT)
    public static Screen buildScreen(Screen parent) {
        var configBuilder = ConfigBuilder.create().setParentScreen(parent).setTitle(text("component.title"));
        configBuilder.setSavingRunnable(() -> AutoConfig.getConfigHolder(WilderWildConfig.class).save());
        //ConfigCategory general = configBuilder.getOrCreateCategory(text("general"));
        var block = configBuilder.getOrCreateCategory(text("block"));
        var entity = configBuilder.getOrCreateCategory(text("entity"));
        var item = configBuilder.getOrCreateCategory(text("item"));
        var worldgen = configBuilder.getOrCreateCategory(text("worldgen"));
        ConfigEntryBuilder entryBuilder = configBuilder.entryBuilder();
        BlockConfig.setupEntries(block, entryBuilder);
        EntityConfig.setupEntries(entity, entryBuilder);
        ItemConfig.setupEntries(item, entryBuilder);
        WorldgenConfig.setupEntries(worldgen, entryBuilder);
        //WilderWildClientConfig.setupEntries(general, entryBuilder);
        return configBuilder.build();
    }


    /*public static void setupEntries(ConfigCategory category, ConfigEntryBuilder entryBuilder) {
        var config = WilderWildClient.config;
        category.addEntry(entryBuilder.startBooleanToggle(text("beta_beaches"), config.betaBeaches)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> config.betaBeaches = newValue)
                .requireRestart()
                .build());
        category.addEntry(entryBuilder.startBooleanToggle(text("modify_desert_placement"), config.modifyDesertPlacement)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> config.modifyDesertPlacement = newValue)
                .setYesNoTextSupplier(bool -> text("biome_placement." + bool))
                .requireRestart()
                .build());
        category.addEntry(entryBuilder.startBooleanToggle(text("modify_badlands_placement"), config.modifyBadlandsPlacement)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> config.modifyBadlandsPlacement = newValue)
                .setYesNoTextSupplier(bool -> text("biome_placement." + bool))
                .requireRestart()
                .build());
        category.addEntry(entryBuilder.startBooleanToggle(text("modify_windswept_savanna_placement"), config.modifyWindsweptSavannaPlacement)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> config.modifyWindsweptSavannaPlacement = newValue)
                .setYesNoTextSupplier(bool -> text("biome_placement." + bool))
                .requireRestart()
                .build());
        category.addEntry(entryBuilder.startBooleanToggle(text("modify_jungle_placement"), config.modifyJunglePlacement)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> config.modifyJunglePlacement = newValue)
                .setYesNoTextSupplier(bool -> text("biome_placement." + bool))
                .requireRestart()
                .build());
        category.addEntry(entryBuilder.startBooleanToggle(text("modify_swamp_placement"), config.modifySwampPlacement)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> config.modifySwampPlacement = newValue)
                .setYesNoTextSupplier(bool -> text("biome_placement." + bool))
                .requireRestart()
                .build());
        category.addEntry(entryBuilder.startBooleanToggle(text("modify_mangrove_swamp_placement"), config.modifyMangroveSwampPlacement)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> config.modifyMangroveSwampPlacement = newValue)
                .setYesNoTextSupplier(bool -> text("biome_placement." + bool))
                .requireRestart()
                .build());

        category.addEntry(entryBuilder.startBooleanToggle(text("mc_live_sensor_tendrils"), config.mcLiveSensorTendrils)
                .setDefaultValue(false)
                .setSaveConsumer(newValue -> config.mcLiveSensorTendrils = newValue)
                .setYesNoTextSupplier(bool -> text("mc_live_sensor_tendrils." + bool))
                .build());
        category.addEntry(entryBuilder.startBooleanToggle(text("warden_emerges_from_egg"), config.wardenEmergesFromEgg)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> config.wardenEmergesFromEgg = newValue)
                .build());
        category.addEntry(entryBuilder.startBooleanToggle(text("warden_custom_tendrils"), config.customWardenTendrils)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> config.customWardenTendrils = newValue)
                .setYesNoTextSupplier(bool -> text("warden_custom_tendrils." + bool))
                .build());
        category.addEntry(entryBuilder.startBooleanToggle(text("warden_swim_animation"), config.wardenSwimAnimation)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> config.wardenSwimAnimation = newValue)
                .build());
        category.addEntry(entryBuilder.startBooleanToggle(text("shrieker_gargling"), config.shriekerGargling)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> config.shriekerGargling = newValue)
                .build());

    }*/
}
