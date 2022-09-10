package net.frozenblock.wilderwild.misc.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.WilderWild;

import static net.frozenblock.wilderwild.misc.config.WilderWildConfig.text;
import static net.frozenblock.wilderwild.misc.config.WilderWildConfig.tooltip;

@Config(name = "entity")
public class EntityConfig implements ConfigData {

    public boolean wardenCustomTendrils = true;
    public boolean wardenDyingAnimation = true;
    public boolean wardenEmergesFromEgg = true;
    public boolean wardenSwimAnimation = true;

    @Environment(EnvType.CLIENT)
    protected static void setupEntries(ConfigCategory category, ConfigEntryBuilder entryBuilder) {
        var config = WilderWildConfig.get().entity;
        category.setBackground(WilderWild.id("textures/config/entity.png"));
        category.addEntry(entryBuilder.startBooleanToggle(text("warden_custom_tendrils"), config.wardenCustomTendrils)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> config.wardenCustomTendrils = newValue)
                .setYesNoTextSupplier(bool -> text("warden_custom_tendrils." + bool))
                .setTooltip(tooltip("warden_custom_tendrils"))
                .build());
        category.addEntry(entryBuilder.startBooleanToggle(text("warden_dying_animation"), config.wardenDyingAnimation)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> config.wardenDyingAnimation = newValue)
                .setTooltip(tooltip("warden_dying_animation"))
                .build());
        category.addEntry(entryBuilder.startBooleanToggle(text("warden_emerges_from_egg"), config.wardenEmergesFromEgg)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> config.wardenEmergesFromEgg = newValue)
                .setTooltip(tooltip("warden_emerges_from_egg"))
                .build());
        category.addEntry(entryBuilder.startBooleanToggle(text("warden_swim_animation"), config.wardenSwimAnimation)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> config.wardenSwimAnimation = newValue)
                .setTooltip(tooltip("warden_swim_animation"))
                .build());

    }


    //public static final EnumConfigOption<ModMenuConfig.ModsButtonStyle> MODS_BUTTON_STYLE = new EnumConfigOption<>("mods_button_style", ModMenuConfig.ModsButtonStyle.CLASSIC);
    //public static final StringSetConfigOption HIDDEN_MODS = new StringSetConfigOption("hidden_mods", new HashSet<>());
    /*public static EntityConfig get() {
        AutoConfig.register(EntityConfig.class, GsonConfigSerializer::new);
        return AutoConfig.getConfigHolder(EntityConfig.class).getConfig();
    }*/
}
