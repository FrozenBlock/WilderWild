package net.frozenblock.wilderwild.misc.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.config.api.FrozenConfig;
import net.frozenblock.wilderwild.entity.AncientHornProjectile;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import static net.frozenblock.wilderwild.misc.config.WilderWildConfig.text;
import static net.frozenblock.wilderwild.misc.config.WilderWildConfig.tooltip;

@Config(name = "item")
public final class ItemConfig implements ConfigData {

    @ConfigEntry.Gui.CollapsibleObject
    public final AncientHornConfig ancientHorn = new AncientHornConfig();

    public static class AncientHornConfig {
		public boolean ancientHornCanSummonWarden = true;
		public int ancientHornLifespan = AncientHornProjectile.DEFAULT_LIFESPAN;
		public int ancientHornMobDamage = 15;
		public int ancientHornPlayerDamage = 22;
        public boolean ancientHornShattersGlass = false;
    }

    public boolean projectileBreakParticles = true;

    @Environment(EnvType.CLIENT)
    static void setupEntries(ConfigCategory category, ConfigEntryBuilder entryBuilder) {
        var config = WilderWildConfig.get().item;
        var ancientHorn = config.ancientHorn;
        category.setBackground(WilderSharedConstants.id("textures/config/item.png"));
		var summonsWarden = entryBuilder.startBooleanToggle(text("ancient_horn_can_summon_warden"), ancientHorn.ancientHornCanSummonWarden)
				.setDefaultValue(true)
				.setSaveConsumer(newValue -> ancientHorn.ancientHornCanSummonWarden = newValue)
				.setTooltip(tooltip("ancient_horn_can_summon_warden"))
				.build();

		var lifespan = entryBuilder.startIntSlider(text("ancient_horn_lifespan"), ancientHorn.ancientHornLifespan, 0, 500)
				.setDefaultValue(AncientHornProjectile.DEFAULT_LIFESPAN)
				.setSaveConsumer(newValue -> ancientHorn.ancientHornLifespan = newValue)
				.setTooltip(tooltip("ancient_horn_lifespan"))
				.build();

		var mobDamage = entryBuilder.startIntSlider(text("ancient_horn_mob_damage"), ancientHorn.ancientHornMobDamage, 0, 50)
				.setDefaultValue(15)
				.setSaveConsumer(newValue -> ancientHorn.ancientHornMobDamage = newValue)
				.setTooltip(tooltip("ancient_horn_mob_damage"))
				.build();

		var playerDamage = entryBuilder.startIntSlider(text("ancient_horn_player_damage"), ancientHorn.ancientHornPlayerDamage, 0, 50)
				.setDefaultValue(22)
				.setSaveConsumer(newValue -> ancientHorn.ancientHornPlayerDamage = newValue)
				.setTooltip(tooltip("ancient_horn_player_damage"))
				.build();

        var shattersGlass = entryBuilder.startBooleanToggle(text("ancient_horn_shatters_glass"), ancientHorn.ancientHornShattersGlass)
                .setDefaultValue(false)
                .setSaveConsumer(newValue -> ancientHorn.ancientHornShattersGlass = newValue)
                .setTooltip(tooltip("ancient_horn_shatters_glass"))
                .build();

        var ancientHornCategory = FrozenConfig.createSubCategory(entryBuilder, category, text("ancient_horn"),
                false,
                tooltip("ancient_horn"),
				summonsWarden, lifespan, mobDamage, playerDamage, shattersGlass
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
