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
import net.minecraft.client.renderer.entity.AxolotlRenderer;
import net.minecraft.client.renderer.entity.DolphinRenderer;
import net.minecraft.client.renderer.entity.FrogRenderer;
import net.minecraft.client.renderer.entity.GoatRenderer;
import net.minecraft.client.renderer.entity.PigRenderer;
import net.minecraft.client.renderer.entity.WardenRenderer;

@Environment(EnvType.CLIENT)
public final class WilderEasterEggs {

	public static void hatchEasterEggs() {
		EntityTextureOverride.register(WWConstants.id("stella_warden"), WardenRenderer.class, WWConstants.id("textures/entity/warden/stella_warden.png"),
			(entity -> entity instanceof WilderWarden wilderWarden && wilderWarden.wilderWild$isStella())
		);
		EntityTextureOverride.register(WWConstants.id("treetrain1_goat"), GoatRenderer.class, WWConstants.id("textures/entity/goat/treetrain1_goat.png"),
			"Treetrain1", "Treetrain"
		);
		EntityTextureOverride.register(WWConstants.id("xfrtrex_frog"), FrogRenderer.class, WWConstants.id("textures/entity/frog/sus_frog.png"),
			"Xfrtrex", "BluePhoenixLOL"
		);
		EntityTextureOverride.register(WWConstants.id("saisho_axolotl"), AxolotlRenderer.class, WWConstants.id("textures/entity/axolotl/saisho_axolotl.png"),
			"Saisho", "SaishoVibes"
		);
		EntityTextureOverride.register(WWConstants.id("alex_dolphin"), DolphinRenderer.class, WWConstants.id("textures/entity/dolphin/alex_dolphin.png"),
			"AlexTheDolphin0"
		);
		EntityTextureOverride.register(WWConstants.id("avftt_pig"), PigRenderer.class, WWConstants.id("textures/entity/pig/uppy_balloo.png"),
			"AViewFromTheTop", "A View From The Top", "AViewFromTheEnd", "Lunade", "Lunade_"
		);
	}
}
