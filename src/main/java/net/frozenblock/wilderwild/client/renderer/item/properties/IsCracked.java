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

package net.frozenblock.wilderwild.client.renderer.item.properties;

import com.mojang.serialization.MapCodec;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.item.api.ItemBlockStateTagUtils;
import net.frozenblock.wilderwild.registry.WWBlockStateProperties;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.conditional.ConditionalItemModelProperty;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public record IsCracked() implements ConditionalItemModelProperty {
	public static final MapCodec<IsCracked> MAP_CODEC = MapCodec.unit(new IsCracked());

	@Override
	public boolean get(ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity, int i, ItemDisplayContext itemDisplayContext) {
		return ItemBlockStateTagUtils.getBoolProperty(itemStack, WWBlockStateProperties.CRACKED, false);
	}

	@Override
	@NotNull
	public MapCodec<? extends ConditionalItemModelProperty> type() {
		return MAP_CODEC;
	}
}
