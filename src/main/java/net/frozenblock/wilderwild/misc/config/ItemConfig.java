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
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import static net.frozenblock.wilderwild.misc.config.WilderWildConfig.*;

@Config(name = "item")
public final class ItemConfig implements ConfigData {

    @ConfigEntry.Gui.CollapsibleObject
    public AncientHornConfig ancientHorn = new AncientHornConfig();

    public static class AncientHornConfig {
        public boolean ancientHornShattersGlass = false;
        public boolean ancientHornCanSummonWarden = true;
    }

	@ConfigEntry.Gui.CollapsibleObject
	public static ProjectileLandingSoundsConfig projectileLandingSounds = new ProjectileLandingSoundsConfig();

	public static class ProjectileLandingSoundsConfig {
		public boolean snowballLandingSounds = true;
		public boolean eggLandingSounds = true;
		public boolean enderpearlLandingSounds = true;
	}

    public boolean projectileBreakParticles = true;

    @Environment(EnvType.CLIENT)
    static void setupEntries(ConfigCategory category, ConfigEntryBuilder entryBuilder) {
        var config = WilderWildConfig.get().item;
        var ancientHorn = config.ancientHorn;
        category.setBackground(WilderSharedConstants.id("textures/config/item.png"));
        var shattersGlass = entryBuilder.startBooleanToggle(text("ancient_horn_shatters_glass"), ancientHorn.ancientHornShattersGlass)
                .setDefaultValue(false)
                .setSaveConsumer(newValue -> ancientHorn.ancientHornShattersGlass = newValue)
                .setTooltip(tooltip("ancient_horn_shatters_glass"))
                .build();
        var summonsWarden = entryBuilder.startBooleanToggle(text("ancient_horn_can_summon_warden"), ancientHorn.ancientHornCanSummonWarden)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> ancientHorn.ancientHornCanSummonWarden = newValue)
                .setTooltip(tooltip("ancient_horn_can_summon_warden"))
                .build();

        var ancientHornCategory = FrozenConfig.createSubCategory(entryBuilder, category, text("ancient_horn"),
                false,
                tooltip("ancient_horn"),
                shattersGlass, summonsWarden
        );

		var snowballLandingSounds = entryBuilder.startBooleanToggle(text("snowball_landing_sounds"), projectileLandingSounds.snowballLandingSounds)
				.setDefaultValue(true)
				.setSaveConsumer(newValue -> projectileLandingSounds.snowballLandingSounds = newValue)
				.setTooltip(tooltip("snowball_landing_sounds"))
				.build();
		var eggLandingSounds = entryBuilder.startBooleanToggle(text("egg_landing_sounds"), projectileLandingSounds.eggLandingSounds)
				.setDefaultValue(true)
				.setSaveConsumer(newValue -> projectileLandingSounds.eggLandingSounds = newValue)
				.setTooltip(tooltip("egg_landing_sounds"))
				.build();
		var enderpearlLandingSounds = entryBuilder.startBooleanToggle(text("enderpearl_landing_sounds"), projectileLandingSounds.enderpearlLandingSounds)
				.setDefaultValue(true)
				.setSaveConsumer(newValue -> projectileLandingSounds.enderpearlLandingSounds = newValue)
				.setTooltip(tooltip("enderpearl_landing_sounds"))
				.build();

		var projectileLandingSoundsCategory = FrozenConfig.createSubCategory(entryBuilder, category, text("projectile_landing"),
				false,
				tooltip("projectile_landing_sounds"),
				snowballLandingSounds, eggLandingSounds, enderpearlLandingSounds
		);

        /*var copperHornCategory = FrozenConfig.createSubCategory(entryBuilder, category, text("copper_horn"),
                false,
                tooltip("copper_horn")

        );*/

        var breakParticles = category.addEntry(entryBuilder.startBooleanToggle(text("projectile_break_particles"), config.projectileBreakParticles)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> config.projectileBreakParticles = newValue)
                .setTooltip(tooltip("projectile_break_particles"))
                .build());
    }


    //public static final EnumConfigOption<ModMenuConfig.ModsButtonStyle> MODS_BUTTON_STYLE = new EnumConfigOption<>("mods_button_style", ModMenuConfig.ModsButtonStyle.CLASSIC);
    //public static final StringSetConfigOption HIDDEN_MODS = new StringSetConfigOption("hidden_mods", new HashSet<>());
    /*public static EntityConfig get() {
        AutoConfig.register(EntityConfig.class, GsonConfigSerializer::new);
        return AutoConfig.getConfigHolder(EntityConfig.class).getConfig();
    }*/
}
