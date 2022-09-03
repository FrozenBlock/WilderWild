package net.frozenblock.wilderwild.misc.config;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.impl.ConfigEntryBuilderImpl;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.WilderWild;
import net.minecraft.client.gui.screens.Screen;

import static net.frozenblock.wilderwild.misc.config.WilderWildConfig.text;
import static net.frozenblock.wilderwild.misc.config.WilderWildConfig.tooltip;

@Config(name = "item")
@Config.Gui.Background("wilderwild:textures/block/chiseled_mud_bricks.png")
public class ItemConfig implements ConfigData {

    @ConfigEntry.Gui.Tooltip
    public boolean ancientHornShattersGlass = false;

    @Environment(EnvType.CLIENT)
    protected static void setupEntries(ConfigCategory category, ConfigEntryBuilder entryBuilder) {
        var config = WilderWildConfig.get().item;
        category.setBackground(WilderWild.id("textures/block/chiseled_mud_bricks.png"));
        category.addEntry(entryBuilder.startBooleanToggle(text("ancient_horn_shatters_glass"), config.ancientHornShattersGlass)
                .setDefaultValue(false)
                .setSaveConsumer(newValue -> config.ancientHornShattersGlass = newValue)
                .setTooltip(tooltip("ancient_horn_shatters_glass"))
                .build());

    }


    //public static final EnumConfigOption<ModMenuConfig.ModsButtonStyle> MODS_BUTTON_STYLE = new EnumConfigOption<>("mods_button_style", ModMenuConfig.ModsButtonStyle.CLASSIC);
    //public static final StringSetConfigOption HIDDEN_MODS = new StringSetConfigOption("hidden_mods", new HashSet<>());
    /*public static EntityConfig get() {
        AutoConfig.register(EntityConfig.class, GsonConfigSerializer::new);
        return AutoConfig.getConfigHolder(EntityConfig.class).getConfig();
    }*/
}
