package net.frozenblock.wilderwild.entity.render.easter;

import net.frozenblock.lib.entity.api.rendering.EntityTextureOverride;
import net.frozenblock.wilderwild.entity.render.animations.WilderWarden;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.minecraft.world.entity.EntityType;

public class EasterEggs {

    public static void registerEaster() {
        EntityTextureOverride.register(WilderSharedConstants.id("stella_warden"), EntityType.WARDEN, WilderSharedConstants.id("textures/entity/warden/stella_warden.png"),
				(entity -> ((WilderWarden) entity).isStella())
		);
        EntityTextureOverride.register(WilderSharedConstants.id("treetrain1_goat"), EntityType.GOAT, WilderSharedConstants.id("textures/entity/goat/treetrain1_goat.png"),
				true, "Treetrain1", "Treetrain"
		);
        /*EntityTextureOverride.register(WilderSharedConstants.id("merp_slime"), EntityType.SLIME, WilderSharedConstants.id("textures/entity/slime/merp_slime.png"),
				true, "Merp"
		);*/
        EntityTextureOverride.register(WilderSharedConstants.id("xfrtrex_frog"), EntityType.FROG, WilderSharedConstants.id("textures/entity/frog/sus_frog.png"),
				"Xfrtrex"
		);
        EntityTextureOverride.register(WilderSharedConstants.id("saisho_axolotl"), EntityType.AXOLOTL, WilderSharedConstants.id("textures/entity/axolotl/saisho_axolotl.png"),
				true, "Saisho"
		);
        /*EntityTextureOverride.register(WilderSharedConstants.id("uppy_balloo"), EntityType.PIG, WilderSharedConstants.id("textures/entity/pig/uppy_balloo.png"),
				"a view from the top"
		);*/
        EntityTextureOverride.register(WilderSharedConstants.id("alex_dolphin"), EntityType.DOLPHIN, WilderSharedConstants.id("textures/entity/dolphin/alex_dolphin.png"),
				"AlexTheDolphin0"
		);
    }
}
