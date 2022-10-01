package net.frozenblock.wilderwild.misc.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.config.FrozenConfig;
import net.frozenblock.wilderwild.WilderWild;

import static net.frozenblock.wilderwild.misc.config.WilderWildConfig.text;
import static net.frozenblock.wilderwild.misc.config.WilderWildConfig.tooltip;

@Config(name = "entity")
public final class EntityConfig implements ConfigData {

    @ConfigEntry.Gui.CollapsibleObject
    public WardenConfig warden = new WardenConfig();

    public static class WardenConfig {
        public boolean wardenCustomTendrils = true;
        public boolean wardenDyingAnimation = true;
        public boolean wardenEmergesFromEgg = false;
        public boolean wardenSwimAnimation = true;
    }

    public boolean unpassableRail = true;

    @Environment(EnvType.CLIENT)
    static void setupEntries(ConfigCategory category, ConfigEntryBuilder entryBuilder) {
        var config = WilderWildConfig.get().entity;
        var warden = config.warden;
        category.setBackground(WilderWild.id("textures/config/entity.png"));
        var unpassableRail = category.addEntry(entryBuilder.startBooleanToggle(text("unpassable_rail"), config.unpassableRail)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> config.unpassableRail = newValue)
                .setTooltip(tooltip("unpassable_rail"))
                .requireRestart()
                .build());

        var allayCategory = FrozenConfig.createSubCategory(entryBuilder, category, text("allay"),
                false,
                tooltip("allay")

        );

        var fireflyCategory = FrozenConfig.createSubCategory(entryBuilder, category, text("firefly"),
                false,
                tooltip("firefly")

        );

        var jellyfishCategory = FrozenConfig.createSubCategory(entryBuilder, category, text("jellyfish"),
                false,
                tooltip("jellyfish")

        );

        var dying = entryBuilder.startBooleanToggle(text("warden_dying_animation"), warden.wardenDyingAnimation)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> warden.wardenDyingAnimation = newValue)
                .setTooltip(tooltip("warden_dying_animation"))
                .build();
        var emerging = entryBuilder.startBooleanToggle(text("warden_emerges_from_egg"), warden.wardenEmergesFromEgg)
                .setDefaultValue(false)
                .setSaveConsumer(newValue -> warden.wardenEmergesFromEgg = newValue)
                .setTooltip(tooltip("warden_emerges_from_egg"))
                .build();
        var swimming = entryBuilder.startBooleanToggle(text("warden_swim_animation"), warden.wardenSwimAnimation)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> warden.wardenSwimAnimation = newValue)
                .setTooltip(tooltip("warden_swim_animation"))
                .build();
        var tendrils = entryBuilder.startBooleanToggle(text("warden_custom_tendrils"), warden.wardenCustomTendrils)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> warden.wardenCustomTendrils = newValue)
                .setYesNoTextSupplier(bool -> text("warden_custom_tendrils." + bool))
                .setTooltip(tooltip("warden_custom_tendrils"))
                .build();

        var wardenCategory = FrozenConfig.createSubCategory(entryBuilder, category, text("warden"),
                false,
                tooltip("warden"),
                dying, emerging, swimming, tendrils
        );
    }


    //public static final EnumConfigOption<ModMenuConfig.ModsButtonStyle> MODS_BUTTON_STYLE = new EnumConfigOption<>("mods_button_style", ModMenuConfig.ModsButtonStyle.CLASSIC);
    //public static final StringSetConfigOption HIDDEN_MODS = new StringSetConfigOption("hidden_mods", new HashSet<>());
    /*public static EntityConfig get() {
        AutoConfig.register(EntityConfig.class, GsonConfigSerializer::new);
        return AutoConfig.getConfigHolder(EntityConfig.class).getConfig();
    }*/
}
