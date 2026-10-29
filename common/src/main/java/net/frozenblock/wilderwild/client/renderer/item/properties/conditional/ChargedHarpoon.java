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
import net.frozenblock.wilderwild.registry.WWItems;
import net.mehvahdjukaar.candlelight.api.ClientOnly;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.conditional.ConditionalItemModelProperty;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ChargedProjectiles;
import org.jetbrains.annotations.Nullable;

@ClientOnly
public final class ChargedHarpoon implements ConditionalItemModelProperty {
	public static final MapCodec<ChargedHarpoon> MAP_CODEC = MapCodec.unit(new ChargedHarpoon());

	@Nullable
	@Override
	public boolean get(ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity owner, int seed, ItemDisplayContext context) {
		// Yes, I'm very much aware that Charge could be used instead.
		// I tried that, actually, but attempting a proper implementation required replacing every single Crossbow pulling + the normal model...
		// With this, the Crossbow Arrow model will be selected and I can simply wrap it with this condition. Yay.
		return stack.getOrDefault(DataComponents.CHARGED_PROJECTILES, ChargedProjectiles.EMPTY).contains(WWItems.HARPOON.get());
	}

	@Override
	public MapCodec<ChargedHarpoon> type() {
		return MAP_CODEC;
	}
}
