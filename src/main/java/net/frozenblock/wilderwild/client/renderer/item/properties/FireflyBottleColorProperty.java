/*
 * Copyright 2024-2025 FrozenBlock
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

package net.frozenblock.wilderwild.client.renderer.item.properties;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.entity.variant.firefly.FireflyColor;
import net.frozenblock.wilderwild.item.MobBottleItem;
import net.frozenblock.wilderwild.registry.WWDataComponents;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.select.SelectItemModelProperty;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class FireflyBottleColorProperty implements SelectItemModelProperty<String> {
	public static final MapCodec<FireflyBottleColorProperty> MAP_CODEC = MapCodec.unit(new FireflyBottleColorProperty());
	public static final SelectItemModelProperty.Type<FireflyBottleColorProperty, String> TYPE = SelectItemModelProperty.Type.create(
		MAP_CODEC,
		Codec.STRING
	);

	@Override
	public @Nullable String get(
		@NotNull ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity, int i, ItemDisplayContext itemDisplayContext
	) {
		Holder<FireflyColor> fireflyColor = itemStack.get(WWDataComponents.FIREFLY_COLOR);
		if (fireflyColor != null) {
			return fireflyColor.value().name();
		}
		return null;
	}

	@Override
	public @NotNull Codec<String> valueCodec() {
		return Codec.STRING;
	}

	@Override
	public @NotNull Type<? extends SelectItemModelProperty<String>, String> type() {
		return TYPE;
	}


}
