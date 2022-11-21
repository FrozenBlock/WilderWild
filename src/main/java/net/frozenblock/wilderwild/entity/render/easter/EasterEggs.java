package net.frozenblock.wilderwild.entity.render.easter;

import net.frozenblock.lib.entity.api.rendering.EntityTextureOverride;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.entity.render.animations.WilderWarden;
import net.minecraft.world.entity.EntityType;

public class EasterEggs {

	public static void registerEaster() {
		EntityTextureOverride.register(WilderWild.id("osmiooo_warden"), EntityType.WARDEN, WilderWild.id("textures/entity/warden/osmiooo_warden.png"),
				(entity -> ((WilderWarden) entity).isOsmiooo())
		);
		EntityTextureOverride.register(WilderWild.id("treetrain1_goat"), EntityType.GOAT, WilderWild.id("textures/entity/goat/treetrain1_goat.png"),
				true, "Treetrain1", "Treetrain"
		);
        /*EntityTextureOverride.register(WilderWild.id("merp_slime"), EntityType.SLIME, WilderWild.id("textures/entity/slime/merp_slime.png"),
				true, "Merp"
		);*/
		EntityTextureOverride.register(WilderWild.id("xfrtrex_frog"), EntityType.FROG, WilderWild.id("textures/entity/frog/sus_frog.png"),
				"Xfrtrex"
		);
		EntityTextureOverride.register(WilderWild.id("saisho_axolotl"), EntityType.AXOLOTL, WilderWild.id("textures/entity/axolotl/saisho_axolotl.png"),
				true, "Saisho"
		);
        /*EntityTextureOverride.register(WilderWild.id("uppy_balloo"), EntityType.PIG, WilderWild.id("textures/entity/pig/uppy_balloo.png"),
				"a view from the top"
		);*/
		EntityTextureOverride.register(WilderWild.id("alex_dolphin"), EntityType.DOLPHIN, WilderWild.id("textures/entity/dolphin/alex_dolphin.png"),
				"AlexTheDolphin0"
		);
	}
}
