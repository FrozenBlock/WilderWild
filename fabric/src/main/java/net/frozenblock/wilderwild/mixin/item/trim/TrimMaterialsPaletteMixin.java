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

package net.frozenblock.wilderwild.mixin.item.trim;

import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.registry.WWTrimMaterials;
import net.minecraft.world.item.equipment.trim.TrimMaterials;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(TrimMaterials.Palette.class)
public enum TrimMaterialsPaletteMixin { // In common mixins.json
	WILDERWILD_PEARL(WWConstants.safeString("pearl"));

	static {
		WWTrimMaterials.WWPalettes.WILDERWILD_PEARL = TrimMaterials.Palette.class.cast(WILDERWILD_PEARL);
	}

	@Shadow
    TrimMaterialsPaletteMixin(String name) {}
}
