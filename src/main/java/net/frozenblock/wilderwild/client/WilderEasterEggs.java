/*
 * Copyright 2023-2025 FrozenBlock
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

package net.frozenblock.wilderwild.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.entity.api.rendering.EntityTextureOverride;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.client.animation.definitions.impl.WilderWarden;
import net.minecraft.world.entity.EntityType;

@Environment(EnvType.CLIENT)
public class WilderEasterEggs {

	public static void hatchEasterEggs() {
		EntityTextureOverride.register(WWConstants.id("stella_warden"), EntityType.WARDEN, WWConstants.id("textures/entity/warden/stella_warden.png"),
			(entity -> entity instanceof WilderWarden wilderWarden && wilderWarden.wilderWild$isStella())
		);
		EntityTextureOverride.register(WWConstants.id("treetrain1_goat"), EntityType.GOAT, WWConstants.id("textures/entity/goat/treetrain1_goat.png"),
			true, "Treetrain1", "Treetrain"
		);
		EntityTextureOverride.register(WWConstants.id("xfrtrex_frog"), EntityType.FROG, WWConstants.id("textures/entity/frog/sus_frog.png"),
			"Xfrtrex", "BluePhoenixLOL"
		);
		EntityTextureOverride.register(WWConstants.id("saisho_axolotl"), EntityType.AXOLOTL, WWConstants.id("textures/entity/axolotl/saisho_axolotl.png"),
			true, "Saisho"
		);
		EntityTextureOverride.register(WWConstants.id("alex_dolphin"), EntityType.DOLPHIN, WWConstants.id("textures/entity/dolphin/alex_dolphin.png"),
			"AlexTheDolphin0"
		);
	}
}
