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
import net.frozenblock.wilderwild.item.MobBottleItem;
import net.frozenblock.wilderwild.registry.WWDataComponents;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.select.SelectItemModelProperty;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class FireflyBottleColorProperty implements SelectItemModelProperty<ResourceLocation> {
	public static final MapCodec<FireflyBottleColorProperty> MAP_CODEC = MapCodec.unit(new FireflyBottleColorProperty());
	public static final SelectItemModelProperty.Type<FireflyBottleColorProperty, ResourceLocation> TYPE = SelectItemModelProperty.Type.create(
		MAP_CODEC,
		ResourceLocation.CODEC
	);

	@Override
	public @Nullable ResourceLocation get(
		@NotNull ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity, int i, ItemDisplayContext itemDisplayContext
	) {
		CustomData customData = itemStack.get(WWDataComponents.BOTTLE_ENTITY_DATA);
		if (customData != null) {
			CompoundTag tag = customData.copyTag();
			if (tag.contains(MobBottleItem.FIREFLY_BOTTLE_VARIANT_FIELD)) {
				try {
					return ResourceLocation.tryParse(tag.getString(MobBottleItem.FIREFLY_BOTTLE_VARIANT_FIELD));
				} catch (Exception ignored) {}
			}
		}
		return null;
	}

	@Override
	public @NotNull Type<? extends SelectItemModelProperty<ResourceLocation>, ResourceLocation> type() {
		return TYPE;
	}


}
