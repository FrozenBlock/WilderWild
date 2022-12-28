package net.frozenblock.wilderwild.misc.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.config.api.FrozenConfig;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.misc.config.defaultconfig.DefaultItemConfig;
import static net.frozenblock.wilderwild.misc.config.WilderWildConfig.text;
import static net.frozenblock.wilderwild.misc.config.WilderWildConfig.tooltip;

@Config(name = "item")
public final class ItemConfig implements ConfigData {

    @ConfigEntry.Gui.CollapsibleObject
    public final AncientHornConfig ancientHorn = new AncientHornConfig();

	@ConfigEntry.Gui.CollapsibleObject
	public final ProjectileLandingSoundsConfig projectileLandingSounds = new ProjectileLandingSoundsConfig();

    protected static class AncientHornConfig {
		public boolean ancientHornCanSummonWarden = DefaultItemConfig.AncientHornConfig.ANCIENT_HORN_CAN_SUMMON_WARDEN;
		public int ancientHornLifespan = DefaultItemConfig.AncientHornConfig.ANCIENT_HORN_LIFESPAN;
		public int ancientHornMobDamage = DefaultItemConfig.AncientHornConfig.ANCIENT_HORN_MOB_DAMAGE;
		public int ancientHornPlayerDamage = DefaultItemConfig.AncientHornConfig.ANCIENT_HORN_PLAYER_DAMAGE;
        public boolean ancientHornShattersGlass = DefaultItemConfig.AncientHornConfig.ANCIENT_HORN_SHATTERS_GLASS;
    }

	public static class ProjectileLandingSoundsConfig {
		public boolean snowballLandingSounds = DefaultItemConfig.ProjectileLandingSoundsConfig.SNOWBALL_LANDING_SOUNDS;
		public boolean eggLandingSounds = DefaultItemConfig.ProjectileLandingSoundsConfig.EGG_LANDING_SOUNDS;
		public boolean enderpearlLandingSounds = DefaultItemConfig.ProjectileLandingSoundsConfig.ENDER_PEARL_LANGING_SOUNDS;
		public boolean potionLandingSounds = DefaultItemConfig.ProjectileLandingSoundsConfig.POTION_LANDING_SOUNDS;
	}

    public boolean projectileBreakParticles = DefaultItemConfig.PROJECTILE_BREAK_PARTICLES;

    @Environment(EnvType.CLIENT)
    static void setupEntries(ConfigCategory category, ConfigEntryBuilder entryBuilder) {
        var config = WilderWildConfig.get().item;
        var ancientHorn = config.ancientHorn;
		var projectileLandingSounds = config.projectileLandingSounds;
        category.setBackground(WilderSharedConstants.id("textures/config/item.png"));
		var summonsWarden = entryBuilder.startBooleanToggle(text("ancient_horn_can_summon_warden"), ancientHorn.ancientHornCanSummonWarden)
				.setDefaultValue(DefaultItemConfig.AncientHornConfig.ANCIENT_HORN_CAN_SUMMON_WARDEN)
				.setSaveConsumer(newValue -> ancientHorn.ancientHornCanSummonWarden = newValue)
				.setTooltip(tooltip("ancient_horn_can_summon_warden"))
				.build();

		var lifespan = entryBuilder.startIntSlider(text("ancient_horn_lifespan"), ancientHorn.ancientHornLifespan, 0, 500)
				.setDefaultValue(DefaultItemConfig.AncientHornConfig.ANCIENT_HORN_LIFESPAN)
				.setSaveConsumer(newValue -> ancientHorn.ancientHornLifespan = newValue)
				.setTooltip(tooltip("ancient_horn_lifespan"))
				.build();

		var mobDamage = entryBuilder.startIntSlider(text("ancient_horn_mob_damage"), ancientHorn.ancientHornMobDamage, 0, 50)
				.setDefaultValue(DefaultItemConfig.AncientHornConfig.ANCIENT_HORN_MOB_DAMAGE)
				.setSaveConsumer(newValue -> ancientHorn.ancientHornMobDamage = newValue)
				.setTooltip(tooltip("ancient_horn_mob_damage"))
				.build();

		var playerDamage = entryBuilder.startIntSlider(text("ancient_horn_player_damage"), ancientHorn.ancientHornPlayerDamage, 0, 50)
				.setDefaultValue(DefaultItemConfig.AncientHornConfig.ANCIENT_HORN_PLAYER_DAMAGE)
				.setSaveConsumer(newValue -> ancientHorn.ancientHornPlayerDamage = newValue)
				.setTooltip(tooltip("ancient_horn_player_damage"))
				.build();

        var shattersGlass = entryBuilder.startBooleanToggle(text("ancient_horn_shatters_glass"), ancientHorn.ancientHornShattersGlass)
                .setDefaultValue(DefaultItemConfig.AncientHornConfig.ANCIENT_HORN_SHATTERS_GLASS)
                .setSaveConsumer(newValue -> ancientHorn.ancientHornShattersGlass = newValue)
                .setTooltip(tooltip("ancient_horn_shatters_glass"))
                .build();

        var ancientHornCategory = FrozenConfig.createSubCategory(entryBuilder, category, text("ancient_horn"),
                false,
                tooltip("ancient_horn"),
				summonsWarden, lifespan, mobDamage, playerDamage, shattersGlass
        );

		var snowballLandingSounds = entryBuilder.startBooleanToggle(text("snowball_landing_sounds"), projectileLandingSounds.snowballLandingSounds)
				.setDefaultValue(DefaultItemConfig.ProjectileLandingSoundsConfig.SNOWBALL_LANDING_SOUNDS)
				.setSaveConsumer(newValue -> projectileLandingSounds.snowballLandingSounds = newValue)
				.setTooltip(tooltip("snowball_landing_sounds"))
				.build();

		var eggLandingSounds = entryBuilder.startBooleanToggle(text("egg_landing_sounds"), projectileLandingSounds.eggLandingSounds)
				.setDefaultValue(DefaultItemConfig.ProjectileLandingSoundsConfig.EGG_LANDING_SOUNDS)
				.setSaveConsumer(newValue -> projectileLandingSounds.eggLandingSounds = newValue)
				.setTooltip(tooltip("egg_landing_sounds"))
				.build();

		var enderpearlLandingSounds = entryBuilder.startBooleanToggle(text("enderpearl_landing_sounds"), projectileLandingSounds.enderpearlLandingSounds)
				.setDefaultValue(DefaultItemConfig.ProjectileLandingSoundsConfig.ENDER_PEARL_LANDING_SOUNDS)
				.setSaveConsumer(newValue -> projectileLandingSounds.enderpearlLandingSounds = newValue)
				.setTooltip(tooltip("enderpearl_landing_sounds"))
				.build();

		var potionLandingSounds = entryBuilder.startBooleanToggle(text("potion_landing_sounds"), projectileLandingSounds.potionLandingSounds)
				.setDefaultValue(DefaultItemConfig.ProjectileLandingSoundsConfig.POTION_LANDING_SOUNDS)
				.setSaveConsumer(newValue -> projectileLandingSounds.potionLandingSounds = newValue)
				.setTooltip(tooltip("potion_landing_sounds"))
				.build();

		var projectileLandingSoundsCategory = FrozenConfig.createSubCategory(entryBuilder, category, text("projectile_landing_sounds"),
				false,
				tooltip("projectile_landing_sounds"),
				snowballLandingSounds, eggLandingSounds, enderpearlLandingSounds, potionLandingSounds
		);

        /*var copperHornCategory = FrozenConfig.createSubCategory(entryBuilder, category, text("copper_horn"),
                false,
                tooltip("copper_horn")

        );*/

        var breakParticles = category.addEntry(entryBuilder.startBooleanToggle(text("projectile_break_particles"), config.projectileBreakParticles)
                .setDefaultValue(DefaultItemConfig.PROJECTILE_BREAK_PARTICLES)
                .setSaveConsumer(newValue -> config.projectileBreakParticles = newValue)
                .setTooltip(tooltip("projectile_break_particles"))
                .build());
    }
}
