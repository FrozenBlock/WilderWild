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

package net.frozenblock.wilderwild.client.renderer.item.properties.conditional;

import com.mojang.serialization.MapCodec;
import net.frozenblock.wilderwild.client.renderer.entity.HarpoonRenderer;
import net.frozenblock.wilderwild.registry.WWAttachmentTypes;
import net.mehvahdjukaar.candlelight.api.ClientOnly;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.conditional.ConditionalItemModelProperty;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

@ClientOnly
public final class CrossbowHarpoonCast implements ConditionalItemModelProperty {
	public static final MapCodec<CrossbowHarpoonCast> MAP_CODEC = MapCodec.unit(new CrossbowHarpoonCast());

	@Nullable
	@Override
	public boolean get(ItemStack itemStack, @Nullable ClientLevel level, @Nullable LivingEntity owner, int seed, ItemDisplayContext context) {
		if (owner == null || WWAttachmentTypes.HARPOON.get(owner) == null) return false;

		final HumanoidArm holdingArm = HarpoonRenderer.getHoldingArm(null, owner);
		return holdingArm != null & owner.getItemHeldByArm(holdingArm) == itemStack;
	}

	@Override
	public MapCodec<CrossbowHarpoonCast> type() {
		return MAP_CODEC;
	}
}
