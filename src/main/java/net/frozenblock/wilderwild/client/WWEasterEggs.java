/*
 * Copyright 2025 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can modify it under
 * the terms of version 1 of the FrozenBlock Modding Oasis License
 * as published by FrozenBlock Modding Oasis.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * FrozenBlock Modding Oasis License for more details.
 *
 * You should have received a copy of the FrozenBlock Modding Oasis License
 * along with this program; if not, see <https://github.com/FrozenBlock/Licenses>.
 */

package net.frozenblock.wilderwild.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.entity.api.rendering.EntityTextureOverride;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.client.animation.definitions.impl.WilderWarden;
import net.minecraft.world.entity.EntityType;

@Environment(EnvType.CLIENT)
public final class WWEasterEggs {

	public static void hatchEasterEggs() {
		EntityTextureOverride.register(WWConstants.id("stella_warden"), EntityType.WARDEN, WWConstants.id("textures/entity/warden/stella_warden.png"),
			(entity -> entity instanceof WilderWarden wilderWarden && wilderWarden.wilderWild$isStella())
		);
		EntityTextureOverride.register(WWConstants.id("treetrain1_goat"), EntityType.GOAT, WWConstants.id("textures/entity/goat/treetrain1_goat.png"),
			"Treetrain1", "Treetrain"
		);
		EntityTextureOverride.register(WWConstants.id("xfrtrex_frog"), EntityType.FROG, WWConstants.id("textures/entity/frog/sus_frog.png"),
			"Xfrtrex", "BluePhoenixLOL"
		);
		EntityTextureOverride.register(WWConstants.id("saisho_axolotl"), EntityType.AXOLOTL, WWConstants.id("textures/entity/axolotl/saisho_axolotl.png"),
			"Saisho", "SaishoVibes"
		);
		EntityTextureOverride.register(WWConstants.id("alex_dolphin"), EntityType.DOLPHIN, WWConstants.id("textures/entity/dolphin/alex_dolphin.png"),
			"AlexTheDolphin0"
		);
		EntityTextureOverride.register(WWConstants.id("avftt_pig"), EntityType.PIG, WWConstants.id("textures/entity/pig/uppy_balloo.png"),
			"AViewFromTheTop", "A View From The Top", "AViewFromTheEnd", "Lunade", "Lunade_"
		);
	}
}
