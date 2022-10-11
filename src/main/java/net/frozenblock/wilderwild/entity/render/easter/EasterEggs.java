package net.frozenblock.wilderwild.entity.render.easter;

import net.frozenblock.lib.entity.render.EasterEgg;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.entity.render.animations.WilderWarden;
import net.minecraft.ChatFormatting;
import net.minecraft.world.entity.EntityType;

public class EasterEggs {

    public static void registerEaster() {
        EasterEgg.register(WilderWild.id("osmiooo_warden"), EntityType.WARDEN, WilderWild.id("textures/entity/warden/osmiooo_warden.png"), ((entity, renderer, model) -> {
            String name = ChatFormatting.stripFormatting(entity.getName().getString());
            if (name != null && (name.equalsIgnoreCase("Osmiooo") || name.equalsIgnoreCase("Mossmio") || name.equalsIgnoreCase("Osmio"))) {
                ((WilderWarden) entity).setOsmiooo(true);
                return true;
            }
            return false;
        }));
        EasterEgg.register(WilderWild.id("treetrain1_goat"), EntityType.GOAT, WilderWild.id("textures/entity/goat/treetrain1_goat.png"), true, "Treetrain1", "Treetrain");
        //EasterEgg.register(WilderWild.id("merp_slime"), EntityType.SLIME, WilderWild.id("textures/entity/slime/merp_slime.png"), true, "Merp");
        EasterEgg.register(WilderWild.id("xfrtrex_frog"), EntityType.FROG, WilderWild.id("textures/entity/frog/sus_frog.png"), "Xfrtrex");
        EasterEgg.register(WilderWild.id("saisho_axolotl"), EntityType.AXOLOTL, WilderWild.id("textures/entity/axolotl/saisho_axolotl.png"), true, "Saisho");
        //EasterEgg.register(WilderWild.id("uppy_balloo"), EntityType.PIG, WilderWild.id("textures/entity/pig/uppy_balloo.png"), "a view from the top");
        EasterEgg.register(WilderWild.id("alex_dolphin"), EntityType.DOLPHIN, WilderWild.id("textures/entity/dolphin/alex_dolphin.png"), "AlexTheDolphin0");
    }
}
