/*
 * Copyright 2026 FrozenBlock
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

package net.frozenblock.wilderwild.mod_compat;

import net.irisshaders.iris.api.v0.IrisApi;
import net.mehvahdjukaar.candlelight.api.ClientOnly;

@ClientOnly
public final class WWIrisCompat {

	/**
	 * I decided to not include a {@link net.frozenblock.lib.FrozenBools#HAS_IRIS FrozenBools#HAS_IRIS} check here, as from experience, loading this class anyway
	 * is bound to cause random issues later when Java randomly decides that it NEEDS to load {@code IrisApi} despite it never being called.
	 * <p>
	 * That is to say, ALWAYS check {@link net.frozenblock.lib.FrozenBools#HAS_IRIS FrozenBools#HAS_IRIS} separately before invoking this!
	 */
	public static boolean usingShaderPack() {
		return IrisApi.getInstance().isShaderPackInUse();
	}

	private WWIrisCompat() {}
}
