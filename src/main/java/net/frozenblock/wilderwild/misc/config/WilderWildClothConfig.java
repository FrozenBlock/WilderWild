package net.frozenblock.wilderwild.misc.config;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.WilderWildClient;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

@Config(name = WilderWild.MOD_ID)
public class WilderWildClothConfig extends WilderWildConfig implements ConfigData {

    public static Screen buildScreen(Screen parent) {
        var configBuilder = ConfigBuilder.create().setParentScreen(parent).setTitle(text("component.title"));
        configBuilder.setSavingRunnable(() -> AutoConfig.getConfigHolder(WilderWildClothConfig.class).save());
        //ConfigCategory general = configBuilder.getOrCreateCategory(text("general"));
        ConfigCategory block = configBuilder.getOrCreateCategory(text("block"));
        ConfigCategory entity = configBuilder.getOrCreateCategory(text("entity"));
        ConfigCategory worldgen = configBuilder.getOrCreateCategory(text("worldgen"));
        ConfigEntryBuilder entryBuilder = configBuilder.entryBuilder();
        BlockConfig.setupEntries(block, entryBuilder);
        EntityConfig.setupEntries(entity, entryBuilder);
        WorldgenConfig.setupEntries(worldgen, entryBuilder);
        //WilderWildClothConfig.setupEntries(general, entryBuilder);
        return configBuilder.build();
    }


    public static void setupEntries(ConfigCategory category, ConfigEntryBuilder entryBuilder) {
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

    }

    public static WilderWildClothConfig init() {
        if (!WilderWild.areConfigsInit) {
            AutoConfig.register(WilderWildClothConfig.class, PartitioningSerializer.wrap(GsonConfigSerializer::new));
            WilderWild.areConfigsInit = true;
        }
        return AutoConfig.getConfigHolder(WilderWildClothConfig.class).getConfig();
    }
}
