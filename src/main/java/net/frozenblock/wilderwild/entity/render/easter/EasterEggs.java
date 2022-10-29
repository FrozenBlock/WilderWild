package net.frozenblock.wilderwild.entity.render.easter;

import net.frozenblock.lib.entity.render.EntityTextureOverride;
import net.frozenblock.wilderwild.entity.render.animations.WilderWarden;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.minecraft.ChatFormatting;
import net.minecraft.world.entity.EntityType;

public class EasterEggs {

    public static void registerEaster() {
        EntityTextureOverride.register(WilderSharedConstants.id("osmiooo_warden"), EntityType.WARDEN, WilderSharedConstants.id("textures/entity/warden/osmiooo_warden.png"), ((entity) -> {
            String name = ChatFormatting.stripFormatting(entity.getName().getString());
            if (name != null && (name.equalsIgnoreCase("Osmiooo") || name.equalsIgnoreCase("Mossmio") || name.equalsIgnoreCase("Osmio"))) {
                ((WilderWarden) entity).setOsmiooo(true);
                return true;
            }
            return false;
        }));
        EntityTextureOverride.register(WilderSharedConstants.id("treetrain1_goat"), EntityType.GOAT, WilderSharedConstants.id("textures/entity/goat/treetrain1_goat.png"), true, "Treetrain1", "Treetrain");
        //EasterEgg.register(WilderWild.id("merp_slime"), EntityType.SLIME, WilderWild.id("textures/entity/slime/merp_slime.png"), true, "Merp");
        EntityTextureOverride.register(WilderSharedConstants.id("xfrtrex_frog"), EntityType.FROG, WilderSharedConstants.id("textures/entity/frog/sus_frog.png"), "Xfrtrex");
        EntityTextureOverride.register(WilderSharedConstants.id("saisho_axolotl"), EntityType.AXOLOTL, WilderSharedConstants.id("textures/entity/axolotl/saisho_axolotl.png"), true, "Saisho");
        //EasterEgg.register(WilderWild.id("uppy_balloo"), EntityType.PIG, WilderWild.id("textures/entity/pig/uppy_balloo.png"), "a view from the top");
        EntityTextureOverride.register(WilderSharedConstants.id("alex_dolphin"), EntityType.DOLPHIN, WilderSharedConstants.id("textures/entity/dolphin/alex_dolphin.png"), "AlexTheDolphin0");
    }
}
