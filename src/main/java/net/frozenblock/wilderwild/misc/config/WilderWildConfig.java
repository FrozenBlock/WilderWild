package net.frozenblock.wilderwild.misc.config;

import com.google.gson.annotations.SerializedName;
import com.terraformersmc.modmenu.config.ModMenuConfig;
import com.terraformersmc.modmenu.config.option.BooleanConfigOption;
import com.terraformersmc.modmenu.config.option.EnumConfigOption;
import com.terraformersmc.modmenu.config.option.OptionConvertable;
import com.terraformersmc.modmenu.config.option.StringSetConfigOption;
import com.terraformersmc.modmenu.util.mod.Mod;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import net.frozenblock.wilderwild.WilderWild;
import net.minecraft.client.option.SimpleOption;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Locale;

@Config(name = WilderWild.MOD_ID)
public class WilderWildConfig implements ConfigData {

    public static final EnumConfigOption<ModMenuConfig.ModsButtonStyle> MODS_BUTTON_STYLE = new EnumConfigOption<>("mods_button_style", ModMenuConfig.ModsButtonStyle.CLASSIC);
    public static final BooleanConfigOption HIDE_MOD_LINKS = new BooleanConfigOption("hide_mod_links", false);
    public static final StringSetConfigOption HIDDEN_MODS = new StringSetConfigOption("hidden_mods", new HashSet<>());

    public static SimpleOption<?>[] asOptions() {
        ArrayList<SimpleOption<?>> options = new ArrayList<>();
        for (Field field : WilderWildConfig.class.getDeclaredFields()) {
            if (Modifier.isStatic(field.getModifiers()) && Modifier.isFinal(field.getModifiers()) && OptionConvertable.class.isAssignableFrom(field.getType())) {
                try {
                    options.add(((OptionConvertable) field.get(null)).asOption());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return options.toArray(SimpleOption[]::new);
    }

    public enum Sorting {
        @SerializedName("ascending")
        ASCENDING(Comparator.comparing(mod -> mod.getName().toLowerCase(Locale.ROOT))),
        @SerializedName("descending")
        DESCENDING(ASCENDING.getComparator().reversed());

        Comparator<Mod> comparator;

        Sorting(Comparator<Mod> comparator) {
            this.comparator = comparator;
        }

        public Comparator<Mod> getComparator() {
            return comparator;
        }
    }

    public enum ModCountLocation {
        @SerializedName("title_screen")
        TITLE_SCREEN(true, false),
        @SerializedName("mods_button")
        MODS_BUTTON(false, true),
        @SerializedName("title_screen_and_mods_button")
        TITLE_SCREEN_AND_MODS_BUTTON(true, true),
        @SerializedName("none")
        NONE(false, false);

        private final boolean titleScreen, modsButton;

        ModCountLocation(boolean titleScreen, boolean modsButton) {
            this.titleScreen = titleScreen;
            this.modsButton = modsButton;
        }

        public boolean isOnTitleScreen() {
            return titleScreen;
        }

        public boolean isOnModsButton() {
            return modsButton;
        }
    }

    public enum ModsButtonStyle {
        @SerializedName("classic")
        CLASSIC(false),
        @SerializedName("replace_realms")
        REPLACE_REALMS(true),
        @SerializedName("shrink")
        SHRINK(false),
        @SerializedName("icon")
        ICON(false);

        private final boolean titleScreenOnly;

        ModsButtonStyle(boolean titleScreenOnly) {
            this.titleScreenOnly = titleScreenOnly;
        }

        public ModsButtonStyle forGameMenu() {
            if (titleScreenOnly) {
                return CLASSIC;
            }
            return this;
        }
    }
}
