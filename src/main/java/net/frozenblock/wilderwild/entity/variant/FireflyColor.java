/*
 * Copyright 2023-2024 FrozenBlock
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

package net.frozenblock.wilderwild.entity.variant;

import com.mojang.serialization.Codec;
import net.frozenblock.wilderwild.WilderSharedConstants;
import net.frozenblock.wilderwild.registry.WilderRegistry;
import net.minecraft.core.Registry;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public record FireflyColor(ResourceLocation key, ResourceLocation texture) implements StringRepresentable {

	public static final Codec<FireflyColor> CODEC = WilderRegistry.FIREFLY_COLOR.byNameCodec();
	public static final StreamCodec<RegistryFriendlyByteBuf, FireflyColor> STREAM_CODEC = ByteBufCodecs.fromCodecWithRegistries(CODEC);

	public static final FireflyColor BLACK = register(WilderSharedConstants.id("black"), WilderSharedConstants.id("textures/entity/firefly/firefly_black.png"));
	public static final FireflyColor BLUE = register(WilderSharedConstants.id("blue"), WilderSharedConstants.id("textures/entity/firefly/firefly_blue.png"));
	public static final FireflyColor BROWN = register(WilderSharedConstants.id("brown"), WilderSharedConstants.id("textures/entity/firefly/firefly_brown.png"));
	public static final FireflyColor CYAN = register(WilderSharedConstants.id("cyan"), WilderSharedConstants.id("textures/entity/firefly/firefly_cyan.png"));
	public static final FireflyColor GRAY = register(WilderSharedConstants.id("gray"), WilderSharedConstants.id("textures/entity/firefly/firefly_gray.png"));
	public static final FireflyColor GREEN = register(WilderSharedConstants.id("green"), WilderSharedConstants.id("textures/entity/firefly/firefly_green.png"));
	public static final FireflyColor LIGHT_BLUE = register(WilderSharedConstants.id("light_blue"), WilderSharedConstants.id("textures/entity/firefly/firefly_light_blue.png"));
	public static final FireflyColor LIGHT_GRAY = register(WilderSharedConstants.id("light_gray"), WilderSharedConstants.id("textures/entity/firefly/firefly_light_gray.png"));
	public static final FireflyColor LIME = register(WilderSharedConstants.id("lime"), WilderSharedConstants.id("textures/entity/firefly/firefly_lime.png"));
	public static final FireflyColor MAGENTA = register(WilderSharedConstants.id("magenta"), WilderSharedConstants.id("textures/entity/firefly/firefly_magenta.png"));
	public static final FireflyColor ON = register(WilderSharedConstants.id("on"), WilderSharedConstants.id("textures/entity/firefly/firefly_on.png"));
	public static final FireflyColor ORANGE = register(WilderSharedConstants.id("orange"), WilderSharedConstants.id("textures/entity/firefly/firefly_orange.png"));
	public static final FireflyColor PINK = register(WilderSharedConstants.id("pink"), WilderSharedConstants.id("textures/entity/firefly/firefly_pink.png"));
	public static final FireflyColor PURPLE = register(WilderSharedConstants.id("purple"), WilderSharedConstants.id("textures/entity/firefly/firefly_purple.png"));
	public static final FireflyColor RED = register(WilderSharedConstants.id("red"), WilderSharedConstants.id("textures/entity/firefly/firefly_red.png"));
	public static final FireflyColor WHITE = register(WilderSharedConstants.id("white"), WilderSharedConstants.id("textures/entity/firefly/firefly_white.png"));
	public static final FireflyColor YELLOW = register(WilderSharedConstants.id("yellow"), WilderSharedConstants.id("textures/entity/firefly/firefly_yellow.png"));

	public FireflyColor(@NotNull ResourceLocation key, @NotNull ResourceLocation texture) {
		this.key = key;
		this.texture = texture;
	}

	@NotNull
	public static FireflyColor register(@NotNull ResourceLocation key, @NotNull ResourceLocation texture) {
		return Registry.register(WilderRegistry.FIREFLY_COLOR, key, new FireflyColor(key, texture));
	}

	public static void init() {
	}

	@NotNull
	public ResourceLocation key() {
		return this.key;
	}

	@NotNull
	public ResourceLocation texture() {
		return this.texture;
	}

	@Override
	@NotNull
	public String getSerializedName() {
		var key = WilderRegistry.FIREFLY_COLOR.getKey(this);
		return key != null ? key.toString() : "null";
	}
}
