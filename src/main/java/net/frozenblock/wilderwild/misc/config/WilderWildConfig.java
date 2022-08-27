package net.frozenblock.wilderwild.misc.config;

import com.terraformersmc.modmenu.config.option.OptionConvertable;
import net.minecraft.client.OptionInstance;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

public class WilderWildConfig {

    //public static final EnumConfigOption<ModMenuConfig.ModsButtonStyle> MODS_BUTTON_STYLE = new EnumConfigOption<>("mods_button_style", ModMenuConfig.ModsButtonStyle.CLASSIC);
    public boolean betaBeaches = true;
    public boolean modifyDesertPlacement = true;
    public boolean modifyBadlandsPlacement = true;
    public boolean modifyWindsweptSavannaPlacement = true;
    public boolean modifyJunglePlacement = true;
    public boolean modifySwampPlacement = true;
    public boolean modifyMangroveSwampPlacement = true;
    public boolean mcLiveSensorTendrils = false;
    public boolean wardenEmergesFromEgg = true;
    public boolean customWardenTendrils = true;
    public boolean wardenSwimAnimation = true;
    public boolean shriekerGargling = true;
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
