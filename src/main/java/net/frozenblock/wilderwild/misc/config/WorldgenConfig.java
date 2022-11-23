package net.frozenblock.wilderwild.misc.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.config.api.FrozenConfig;
import net.frozenblock.wilderwild.WilderWild;
import static net.frozenblock.wilderwild.misc.config.WilderWildConfig.text;
import static net.frozenblock.wilderwild.misc.config.WilderWildConfig.tooltip;

@Config(name = "worldgen")
public final class WorldgenConfig implements ConfigData {
	//public static final EnumConfigOption<ModMenuConfig.ModsButtonStyle> MODS_BUTTON_STYLE = new EnumConfigOption<>("mods_button_style", ModMenuConfig.ModsButtonStyle.CLASSIC);

	@ConfigEntry.Gui.CollapsibleObject
	public BiomePlacement biomePlacement = new BiomePlacement();

	public static class BiomePlacement {
		//public boolean modifyDesertPlacement = true;
		//public boolean modifyBadlandsPlacement = true;
		public boolean modifyWindsweptSavannaPlacement = true;
		public boolean modifyJunglePlacement = true;
		public boolean modifySwampPlacement = true;
		public boolean modifyMangroveSwampPlacement = true;
	}

	public boolean betaBeaches = true;
	public boolean dyingTrees = true;
	public boolean fallenLogs = true;
	public boolean wilderWildTreeGen = true;
	public boolean wilderWildGrassGen = true;

	@Environment(EnvType.CLIENT)
	static void setupEntries(ConfigCategory category, ConfigEntryBuilder entryBuilder) {
		var config = WilderWildConfig.get().worldgen;
		var biomePlacement = config.biomePlacement;
		category.setBackground(WilderWild.id("textures/config/worldgen.png"));
		var betaBeaches = category.addEntry(entryBuilder.startBooleanToggle(text("beta_beaches"), config.betaBeaches)
				.setDefaultValue(true)
				.setSaveConsumer(newValue -> config.betaBeaches = newValue)
				.setTooltip(tooltip("beta_beaches"))
				.requireRestart()
				.build());
        /*
        var badlands = category.addEntry(entryBuilder.startBooleanToggle(text("modify_badlands_placement"), biomePlacement.modifyBadlandsPlacement)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> biomePlacement.modifyBadlandsPlacement = newValue)
                .setYesNoTextSupplier(bool -> text("biome_placement." + bool))
                .setTooltip(tooltip("modify_badlands_placement"))
                .requireRestart()
                .build());
        var desert = category.addEntry(entryBuilder.startBooleanToggle(text("modify_desert_placement"), biomePlacement.modifyDesertPlacement)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> biomePlacement.modifyDesertPlacement = newValue)
                .setYesNoTextSupplier(bool -> text("biome_placement." + bool))
                .setTooltip(tooltip("modify_desert_placement"))
                .requireRestart()
                .build());
         */
		var jungle = entryBuilder.startBooleanToggle(text("modify_jungle_placement"), biomePlacement.modifyJunglePlacement)
				.setDefaultValue(true)
				.setSaveConsumer(newValue -> biomePlacement.modifyJunglePlacement = newValue)
				.setYesNoTextSupplier(bool -> text("biome_placement." + bool))
				.setTooltip(tooltip("modify_jungle_placement"))
				.requireRestart()
				.build();
		var mangroveSwamp = entryBuilder.startBooleanToggle(text("modify_mangrove_swamp_placement"), biomePlacement.modifyMangroveSwampPlacement)
				.setDefaultValue(true)
				.setSaveConsumer(newValue -> biomePlacement.modifyMangroveSwampPlacement = newValue)
				.setYesNoTextSupplier(bool -> text("biome_placement." + bool))
				.setTooltip(tooltip("modify_mangrove_swamp_placement"))
				.requireRestart()
				.build();
		var swamp = entryBuilder.startBooleanToggle(text("modify_swamp_placement"), biomePlacement.modifySwampPlacement)
				.setDefaultValue(true)
				.setSaveConsumer(newValue -> biomePlacement.modifySwampPlacement = newValue)
				.setYesNoTextSupplier(bool -> text("biome_placement." + bool))
				.setTooltip(tooltip("modify_swamp_placement"))
				.requireRestart()
				.build();
		var windsweptSavanna = entryBuilder.startBooleanToggle(text("modify_windswept_savanna_placement"), biomePlacement.modifyWindsweptSavannaPlacement)
				.setDefaultValue(true)
				.setSaveConsumer(newValue -> biomePlacement.modifyWindsweptSavannaPlacement = newValue)
				.setYesNoTextSupplier(bool -> text("biome_placement." + bool))
				.setTooltip(tooltip("modify_windswept_savanna_placement"))
				.requireRestart()
				.build();

		var biomePlacementCategory = FrozenConfig.createSubCategory(entryBuilder, category, text("biome_placement"),
				false,
				tooltip("biome_placement"),
				jungle, mangroveSwamp, swamp, windsweptSavanna
		);

        /*var dyingTrees = category.addEntry(entryBuilder.startBooleanToggle(text("dying_trees"), config.dyingTrees)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> config.dyingTrees = newValue)
                .setTooltip(tooltip("dying_trees"))
                .requireRestart()
                .build()
        );*/
		var fallenLogs = category.addEntry(entryBuilder.startBooleanToggle(text("fallen_logs"), config.fallenLogs)
				.setDefaultValue(true)
				.setSaveConsumer(newValue -> config.fallenLogs = newValue)
				.setTooltip(tooltip("fallen_logs"))
				.requireRestart()
				.build()
		);
		var wilderWildGrass = category.addEntry(entryBuilder.startBooleanToggle(text("wilder_wild_grass"), config.wilderWildGrassGen)
				.setDefaultValue(true)
				.setSaveConsumer(newValue -> config.wilderWildGrassGen = newValue)
				.setTooltip(tooltip("wilder_wild_grass"))
				.requireRestart()
				.build()
		);
		var wilderWildTrees = category.addEntry(entryBuilder.startBooleanToggle(text("wilder_wild_trees"), config.wilderWildTreeGen)
				.setDefaultValue(true)
				.setSaveConsumer(newValue -> config.wilderWildTreeGen = newValue)
				.setTooltip(tooltip("wilder_wild_trees"))
				.requireRestart()
				.build()
		);
	}

	//public static final StringSetConfigOption HIDDEN_MODS = new StringSetConfigOption("hidden_mods", new HashSet<>());
    /*public static WorldgenConfig get() {
        AutoConfig.register(WorldgenConfig.class, GsonConfigSerializer::new);
        return AutoConfig.getConfigHolder(WorldgenConfig.class).getConfig();
    }*/
}
