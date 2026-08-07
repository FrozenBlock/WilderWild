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

import net.frozenblock.lib.renderer.RenderStateDataKey;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.wind.client.CloudWindPositioner;
import net.mehvahdjukaar.candlelight.api.ClientOnly;

@ClientOnly
public final class WWRenderStateDataKeys {
	public static final RenderStateDataKey<CloudWindPositioner> CLOUD_WIND_POSITIONER = RenderStateDataKey.create(
		WWConstants.id("cloud_wind_positioner")
	);
	public static final RenderStateDataKey<Float> WARDEN_SWIM_AMOUNT = RenderStateDataKey.create(
		WWConstants.id("warden_swim_amount")
	);
	public static final RenderStateDataKey<Float> WARDEN_WADE_AMOUNT = RenderStateDataKey.create(
		WWConstants.id("warden_wade_amount")
	);

	public static void init() {}
}
