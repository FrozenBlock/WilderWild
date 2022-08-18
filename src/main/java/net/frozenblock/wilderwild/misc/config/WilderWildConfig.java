package net.frozenblock.wilderwild.misc.config;

import com.terraformersmc.modmenu.config.option.BooleanConfigOption;
import com.terraformersmc.modmenu.config.option.OptionConvertable;
import net.minecraft.client.OptionInstance;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

public class WilderWildConfig {

    //public static final EnumConfigOption<ModMenuConfig.ModsButtonStyle> MODS_BUTTON_STYLE = new EnumConfigOption<>("mods_button_style", ModMenuConfig.ModsButtonStyle.CLASSIC);
    public static final BooleanConfigOption MODIFY_DESERT_PLACEMENT = new BooleanConfigOption("modify_desert_placement", true);
    public static final BooleanConfigOption MODIFY_BADLANDS_PLACEMENT = new BooleanConfigOption("modify_badlands_placement", true);
    public static final BooleanConfigOption MODIFY_WINDSWEPT_SAVANNA_PLACEMENT = new BooleanConfigOption("modify_windswept_savanna_placement", true);
    public static final BooleanConfigOption MODIFY_JUNGLE_PLACEMENT = new BooleanConfigOption("modify_jungle_placement", true);
    public static final BooleanConfigOption MODIFY_SWAMP_PLACEMENT = new BooleanConfigOption("modify_swamp_placement", true);
    public static final BooleanConfigOption MODIFY_MANGROVE_SWAMP_PLACEMENT = new BooleanConfigOption("modify_mangrove_swamp_placement", true);
    public static final BooleanConfigOption MC_LIVE_SENSOR_TENDRILS = new BooleanConfigOption("mc_live_sensor_tendrils", false);
    //public static final StringSetConfigOption HIDDEN_MODS = new StringSetConfigOption("hidden_mods", new HashSet<>());

    public static OptionInstance<?>[] asOptions() {
        ArrayList<OptionInstance<?>> options = new ArrayList<>();
        for (Field field : WilderWildConfig.class.getDeclaredFields()) {
            if (Modifier.isStatic(field.getModifiers()) && Modifier.isFinal(field.getModifiers()) && OptionConvertable.class.isAssignableFrom(field.getType())) {
                try {
                    options.add(((OptionConvertable) field.get(null)).asOption());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return options.toArray(OptionInstance[]::new);
    }

}
