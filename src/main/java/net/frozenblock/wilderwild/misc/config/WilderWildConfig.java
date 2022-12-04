package net.frozenblock.wilderwild.misc.config;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry.Category;
import me.shedaniel.autoconfig.annotation.ConfigEntry.Gui.TransitiveObject;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

@Config(name = WilderSharedConstants.MOD_ID)
public class WilderWildConfig extends PartitioningSerializer.GlobalData {

    @Category("block")
    @TransitiveObject
    public final BlockConfig block = new BlockConfig();

    @Category("entity")
    @TransitiveObject
    public final EntityConfig entity = new EntityConfig();

    @Category("item")
    @TransitiveObject
    public final ItemConfig item = new ItemConfig();

    @Category("worldgen")
    @TransitiveObject
    public final WorldgenConfig worldgen = new WorldgenConfig();

	//@Category("misc")
	//@TransitiveObject
	//public MiscConfig misc = new MiscConfig();

    public static WilderWildConfig get() {
        if (!WilderSharedConstants.areConfigsInit) {
            AutoConfig.register(WilderWildConfig.class, PartitioningSerializer.wrap(GsonConfigSerializer::new));
            WilderSharedConstants.areConfigsInit = true;
        }
        return AutoConfig.getConfigHolder(WilderWildConfig.class).getConfig();
    }

    public static Component text(String key) {
        return Component.translatable("option." + WilderSharedConstants.MOD_ID + "." + key);
    }

    public static Component tooltip(String key) {
        return Component.translatable("tooltip." + WilderSharedConstants.MOD_ID + "." + key);
    }

    @Environment(EnvType.CLIENT)
    public static Screen buildScreen(Screen parent) {
        var configBuilder = ConfigBuilder.create().setParentScreen(parent).setTitle(text("component.title"));
        configBuilder.setSavingRunnable(() -> AutoConfig.getConfigHolder(WilderWildConfig.class).save());
        //ConfigCategory general = configBuilder.getOrCreateCategory(text("general"));
        var block = configBuilder.getOrCreateCategory(text("block"));
        var entity = configBuilder.getOrCreateCategory(text("entity"));
        var item = configBuilder.getOrCreateCategory(text("item"));
        var worldgen = configBuilder.getOrCreateCategory(text("worldgen"));
		//var misc = configBuilder.getOrCreateCategory(text("misc"));
        ConfigEntryBuilder entryBuilder = configBuilder.entryBuilder();
        BlockConfig.setupEntries(block, entryBuilder);
        EntityConfig.setupEntries(entity, entryBuilder);
        ItemConfig.setupEntries(item, entryBuilder);
        WorldgenConfig.setupEntries(worldgen, entryBuilder);
		//MiscConfig.setupEntries(misc, entryBuilder);
        //WilderWildClientConfig.setupEntries(general, entryBuilder);
        return configBuilder.build();
    }


    /*@Environment(EnvType.CLIENT)
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
        category.addEntry(entryBuilder.startBooleanToggle(text("warden_custom_tendrils"), config.wardenCustomTendrils)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> config.wardenCustomTendrils = newValue)
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
