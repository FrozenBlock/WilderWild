/*
 * Copyright 2023-2024 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.wilderwild.entity.render.easter;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.entity.api.rendering.EntityTextureOverride;
import net.frozenblock.wilderwild.WilderConstants;
import net.frozenblock.wilderwild.entity.render.animations.WilderWarden;
import net.minecraft.world.entity.EntityType;

@Environment(EnvType.CLIENT)
public class WilderEasterEggs {

	public static void hatchEasterEggs() {
		EntityTextureOverride.register(WilderConstants.id("stella_warden"), EntityType.WARDEN, WilderConstants.id("textures/entity/warden/stella_warden.png"),
			(entity -> entity instanceof WilderWarden wilderWarden && wilderWarden.wilderWild$isStella())
		);
		EntityTextureOverride.register(WilderConstants.id("treetrain1_goat"), EntityType.GOAT, WilderConstants.id("textures/entity/goat/treetrain1_goat.png"),
			true, "Treetrain1", "Treetrain"
		);
		EntityTextureOverride.register(WilderConstants.id("xfrtrex_frog"), EntityType.FROG, WilderConstants.id("textures/entity/frog/sus_frog.png"),
			"Xfrtrex", "BluePhoenixLOL"
		);
		EntityTextureOverride.register(WilderConstants.id("saisho_axolotl"), EntityType.AXOLOTL, WilderConstants.id("textures/entity/axolotl/saisho_axolotl.png"),
			true, "Saisho"
		);
		EntityTextureOverride.register(WilderConstants.id("alex_dolphin"), EntityType.DOLPHIN, WilderConstants.id("textures/entity/dolphin/alex_dolphin.png"),
			"AlexTheDolphin0"
		);
	}
}
