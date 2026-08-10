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

package net.frozenblock.wilderwild.mixin.worldgen.biome;

import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.data.worldgen.biome.impl.WWGrassColorModifier;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(BiomeSpecialEffects.GrassColorModifier.class)
public enum GrassColorModifierMixin { // In common mixins.json
	WILDERWILD_TUNDRA(WWConstants.safeString("tundra")) {
		@Override
		public int modifyColor(double x, double z, int baseColor) {
			return WWGrassColorModifier.modifyColorTundra(x, z, baseColor);
		}
	};

	static {
		WWGrassColorModifier.WILDERWILD_TUNDRA = BiomeSpecialEffects.GrassColorModifier.class.cast(WILDERWILD_TUNDRA);
	}

	@Shadow
	public abstract int modifyColor(double x, double z, int baseColor);

	@Shadow
	GrassColorModifierMixin(String name) {}
}
