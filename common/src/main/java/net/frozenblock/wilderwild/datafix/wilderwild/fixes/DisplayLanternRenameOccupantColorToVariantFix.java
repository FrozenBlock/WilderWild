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

package net.frozenblock.wilderwild.datafix.wilderwild.fixes;

import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import net.frozenblock.wilderwild.datafix.fixes.DisplayLanternOccupantFix;

public final class DisplayLanternRenameOccupantColorToVariantFix extends DisplayLanternOccupantFix {

	public DisplayLanternRenameOccupantColorToVariantFix(Schema outputSchema) {
		super(outputSchema, "Rename Display Lantern occupant color field to variant", "fireflies");
	}

	@Override
	protected Dynamic<?> fixOccupant(Dynamic<?> dynamic) {
		return dynamic.renameField("color", "variant");
	}
}
