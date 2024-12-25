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
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.registry.WilderWildRegistries;
import net.minecraft.core.Registry;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public record FireflyColor(ResourceLocation key, ResourceLocation texture, float modelPredicate) implements StringRepresentable {
	public static final Codec<FireflyColor> CODEC = WilderWildRegistries.FIREFLY_COLOR.byNameCodec();
	public static final StreamCodec<RegistryFriendlyByteBuf, FireflyColor> STREAM_CODEC = ByteBufCodecs.fromCodecWithRegistries(CODEC);

	public static final FireflyColor ON = register(WWConstants.id("on"), WWConstants.id("textures/entity/firefly/firefly_on.png"), 0F);
	public static final FireflyColor BLACK = register(WWConstants.id("black"), WWConstants.id("textures/entity/firefly/firefly_black.png"), 0.01F);
	public static final FireflyColor BLUE = register(WWConstants.id("blue"), WWConstants.id("textures/entity/firefly/firefly_blue.png"), 0.02F);
	public static final FireflyColor BROWN = register(WWConstants.id("brown"), WWConstants.id("textures/entity/firefly/firefly_brown.png"), 0.03F);
	public static final FireflyColor CYAN = register(WWConstants.id("cyan"), WWConstants.id("textures/entity/firefly/firefly_cyan.png"), 0.04F);
	public static final FireflyColor GRAY = register(WWConstants.id("gray"), WWConstants.id("textures/entity/firefly/firefly_gray.png"), 0.05F);
	public static final FireflyColor GREEN = register(WWConstants.id("green"), WWConstants.id("textures/entity/firefly/firefly_green.png"), 0.06F);
	public static final FireflyColor LIGHT_BLUE = register(WWConstants.id("light_blue"), WWConstants.id("textures/entity/firefly/firefly_light_blue.png"), 0.07F);
	public static final FireflyColor LIGHT_GRAY = register(WWConstants.id("light_gray"), WWConstants.id("textures/entity/firefly/firefly_light_gray.png"), 0.08F);
	public static final FireflyColor LIME = register(WWConstants.id("lime"), WWConstants.id("textures/entity/firefly/firefly_lime.png"), 0.09F);
	public static final FireflyColor MAGENTA = register(WWConstants.id("magenta"), WWConstants.id("textures/entity/firefly/firefly_magenta.png"), 0.10F);
	public static final FireflyColor ORANGE = register(WWConstants.id("orange"), WWConstants.id("textures/entity/firefly/firefly_orange.png"), 0.11F);
	public static final FireflyColor PINK = register(WWConstants.id("pink"), WWConstants.id("textures/entity/firefly/firefly_pink.png"), 0.12F);
	public static final FireflyColor PURPLE = register(WWConstants.id("purple"), WWConstants.id("textures/entity/firefly/firefly_purple.png"), 0.13F);
	public static final FireflyColor RED = register(WWConstants.id("red"), WWConstants.id("textures/entity/firefly/firefly_red.png"), 0.14F);
	public static final FireflyColor WHITE = register(WWConstants.id("white"), WWConstants.id("textures/entity/firefly/firefly_white.png"), 0.15F);
	public static final FireflyColor YELLOW = register(WWConstants.id("yellow"), WWConstants.id("textures/entity/firefly/firefly_yellow.png"), 0.16F);

	public FireflyColor(@NotNull ResourceLocation key, @NotNull ResourceLocation texture, float modelPredicate) {
		this.key = key;
		this.texture = texture;
		this.modelPredicate = modelPredicate;
	}

	@NotNull
	public static FireflyColor register(@NotNull ResourceLocation key, @NotNull ResourceLocation texture, float modelPredicate) {
		return Registry.register(WilderWildRegistries.FIREFLY_COLOR, key, new FireflyColor(key, texture, modelPredicate));
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
		var key = WilderWildRegistries.FIREFLY_COLOR.getKey(this);
		return key != null ? key.toString() : "null";
	}
}
