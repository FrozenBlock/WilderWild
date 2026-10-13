/*
 * Copyright 2025-2026 FrozenBlock
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

import net.frozenblock.lib.entity.client.api.renderer.entity.EntityTextureOverride;
import net.frozenblock.lib.platform.api.registry.DeferredRegister;
import net.frozenblock.lib.registry.client.FrozenLibClientRegistries;
import net.frozenblock.wilderwild.WWConstants;
import net.mehvahdjukaar.candlelight.api.ClientOnly;

@ClientOnly
public final class WWEasterEggs {

	// TODO: FIXME!!
	public static void hatchEasterEggs() {
		final DeferredRegister<EntityTextureOverride<?, ?>> register = DeferredRegister.create(
			FrozenLibClientRegistries.ENTITY_TEXTURE_OVERRIDE,
			WWConstants.MOD_ID
		);

		/*
		register.register(
			"stella_warden",
			() -> new EntityTextureOverride<Warden, WardenRenderState>(
				EntityTypes.WARDEN,
				WWConstants.id("textures/entity/warden/stella_warden.png"),
				state -> state instanceof WilderWarden wilderState && wilderState.wilderWild$isStella()
			)
		);

		register.register(
			"treetrain1_goat",
			() -> EntityTextureOverride.create(
				EntityTypes.GOAT,
				WWConstants.id("textures/entity/goat/treetrain1_goat.png"),
				"Treetrain1", "Treetrain", "EthanStokes", "Ethan Stokes", "Ethan Strokes"
			)
		);

		register.register(
			"xfrtrex_frog",
			() -> EntityTextureOverride.create(
				EntityTypes.FROG,
				WWConstants.id("textures/entity/frog/sus_frog.png"),
				"Xfrtrex", "BluePhoenixLOL"
			)
		);

		register.register(
			"saisho_axolotl",
			() -> EntityTextureOverride.create(
				EntityTypes.AXOLOTL,
				WWConstants.id("textures/entity/axolotl/saisho_axolotl.png"),
				"Saisho", "SaishoVibes"
			)
		);

		register.register(
			"alex_dolphin",
			() -> EntityTextureOverride.create(
				EntityTypes.DOLPHIN,
				WWConstants.id("textures/entity/dolphin/alex_dolphin.png"),
				"AlexTheDolphin0"
			)
		);

		register.register(
			"avftt_pig",
			() -> EntityTextureOverride.create(
				EntityTypes.DOLPHIN,
				WWConstants.id("textures/entity/pig/uppy_balloo.png"),
				"AViewFromTheTop", "A View From The Top", "AViewFromTheEnd", "Lunade", "Lunade_"
			)
		);
		 */

		register.register();
	}

	private WWEasterEggs() {}
}
