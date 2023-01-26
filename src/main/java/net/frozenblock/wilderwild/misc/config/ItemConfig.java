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

    protected static class AncientHornConfig {
		public boolean ancientHornCanSummonWarden = DefaultItemConfig.AncientHornConfig.ANCIENT_HORN_CAN_SUMMON_WARDEN;
		public int ancientHornLifespan = DefaultItemConfig.AncientHornConfig.ANCIENT_HORN_LIFESPAN;
		public int ancientHornMobDamage = DefaultItemConfig.AncientHornConfig.ANCIENT_HORN_MOB_DAMAGE;
		public int ancientHornPlayerDamage = DefaultItemConfig.AncientHornConfig.ANCIENT_HORN_PLAYER_DAMAGE;
        public boolean ancientHornShattersGlass = DefaultItemConfig.AncientHornConfig.ANCIENT_HORN_SHATTERS_GLASS;
		public float ancientHornSizeMultiplier = DefaultItemConfig.AncientHornConfig.ANCIENT_HORN_SIZE_MULTIPLIER;
    }

    public boolean projectileBreakParticles = DefaultItemConfig.PROJECTILE_BREAK_PARTICLES;

    @Environment(EnvType.CLIENT)
    static void setupEntries(ConfigCategory category, ConfigEntryBuilder entryBuilder) {
        var config = WilderWildConfig.get().item;
        var ancientHorn = config.ancientHorn;
        category.setBackground(WilderSharedConstants.id("textures/config/item.png"));
		var summonsWarden = entryBuilder.startBooleanToggle(text("ancient_horn_can_summon_warden"), ancientHorn.ancientHornCanSummonWarden)
				.setDefaultValue(DefaultItemConfig.AncientHornConfig.ANCIENT_HORN_CAN_SUMMON_WARDEN)
				.setSaveConsumer(newValue -> ancientHorn.ancientHornCanSummonWarden = newValue)
				.setTooltip(tooltip("ancient_horn_can_summon_warden"))
				.build();

		var lifespan = entryBuilder.startIntSlider(text("ancient_horn_lifespan"), ancientHorn.ancientHornLifespan, 0, 1000)
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

		var sizeMultiplier = entryBuilder.startFloatField(text("ancient_horn_size_multiplier"), ancientHorn.ancientHornSizeMultiplier)
				.setDefaultValue(DefaultItemConfig.AncientHornConfig.ANCIENT_HORN_SIZE_MULTIPLIER)
				.setSaveConsumer(newValue -> ancientHorn.ancientHornSizeMultiplier = newValue)
				.setTooltip(tooltip("ancient_horn_size_multiplier"))
				.build();

        var ancientHornCategory = FrozenConfig.createSubCategory(entryBuilder, category, text("ancient_horn"),
                false,
                tooltip("ancient_horn"),
				summonsWarden, lifespan, mobDamage, playerDamage, shattersGlass, sizeMultiplier
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
