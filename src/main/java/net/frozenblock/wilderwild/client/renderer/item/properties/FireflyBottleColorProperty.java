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

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.entity.variant.firefly.FireflyColor;
import net.frozenblock.wilderwild.registry.WWDataComponents;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.select.SelectItemModelProperty;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class FireflyBottleColorProperty implements SelectItemModelProperty<String> {
	public static final MapCodec<FireflyBottleColorProperty> MAP_CODEC = MapCodec.unit(new FireflyBottleColorProperty());
	public static final SelectItemModelProperty.Type<FireflyBottleColorProperty, String> TYPE = SelectItemModelProperty.Type.create(
		MAP_CODEC,
		Codec.STRING
	);

	@Nullable
	@Override
	public String get(ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entity, int i, ItemDisplayContext context) {
		final Holder<FireflyColor> fireflyColor = stack.get(WWDataComponents.FIREFLY_COLOR);
		if (fireflyColor != null) return fireflyColor.value().name();
		return null;
	}

	@Override
	public Codec<String> valueCodec() {
		return Codec.STRING;
	}

	@Override
	public Type<? extends SelectItemModelProperty<String>, String> type() {
		return TYPE;
	}

}
