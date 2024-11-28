/*
 * Copyright 2024 FrozenBlock
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

package net.frozenblock.wilderwild;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.MapCodec;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.block.client.entity.SpecialModelRenderersEntrypoint;
import net.frozenblock.wilderwild.client.renderer.special.StoneChestSpecialRenderer;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class WWSpecialModelRenderers implements SpecialModelRenderersEntrypoint {

	@Override
	public void registerSpecialModelRenderers(ExtraCodecs.@NotNull LateBoundIdMapper<ResourceLocation, MapCodec<? extends SpecialModelRenderer.Unbaked>> mapper) {
		mapper.put(WWConstants.id("stone_chest"), StoneChestSpecialRenderer.Unbaked.MAP_CODEC);
	}

	@Override
	public void onMapInit(ImmutableMap.Builder map) {
		map.put(WWBlocks.STONE_CHEST, new StoneChestSpecialRenderer.Unbaked(StoneChestSpecialRenderer.STONE_CHEST_TEXTURE));
	}
}
