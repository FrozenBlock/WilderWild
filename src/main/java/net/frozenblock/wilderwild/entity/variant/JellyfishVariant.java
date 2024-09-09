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
import io.netty.buffer.ByteBuf;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.registry.WilderWildRegistries;
import net.frozenblock.wilderwild.tag.WWItemTags;
import net.minecraft.core.Registry;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;
import java.util.function.IntFunction;

@SuppressWarnings("deprecation")
public enum JellyfishVariant implements StringRepresentable {

	BLUE(WWConstants.id("blue"), WWConstants.id("textures/entity/jellyfish/blue.png"), false, WWItemTags.JELLYFISH_FOOD),
	LIME(WWConstants.id("lime"), WWConstants.id("textures/entity/jellyfish/lime.png"), false, WWItemTags.JELLYFISH_FOOD),
	PINK(WWConstants.id("pink"), WWConstants.id("textures/entity/jellyfish/pink.png"), false, WWItemTags.JELLYFISH_FOOD),
	RED(WWConstants.id("red"), WWConstants.id("textures/entity/jellyfish/red.png"), false, WWItemTags.JELLYFISH_FOOD),
	YELLOW(WWConstants.id("yellow"), WWConstants.id("textures/entity/jellyfish/yellow.png"), false, WWItemTags.JELLYFISH_FOOD),

	PEARLESCENT_BLUE(WWConstants.id("pearlescent_blue"), WWConstants.id("textures/entity/jellyfish/pearlescent_blue.png"), true, WWItemTags.PEARLESCENT_JELLYFISH_FOOD),
	PEARLESCENT_PURPLE(WWConstants.id("pearlescent_purple"), WWConstants.id("textures/entity/jellyfish/pearlescent_purple.png"), true, WWItemTags.PEARLESCENT_JELLYFISH_FOOD);

	public static final EnumCodec<JellyfishVariant> CODEC = StringRepresentable.fromEnum(JellyfishVariant::values);
	public static final StreamCodec<ByteBuf, JellyfishVariant> STREAM_CODEC = ByteBufCodecs.fromCodec(CODEC);

	private final ResourceLocation key;
	private final ResourceLocation texture;
	private final boolean pearlescent;
	private final TagKey<Item> reproductionFood;

	JellyfishVariant(@NotNull ResourceLocation key, @NotNull ResourceLocation texture, boolean pearlescent, TagKey<Item> reproductionFood) {
		this.key = key;
		this.texture = texture;
		this.pearlescent = pearlescent;
		this.reproductionFood = reproductionFood;
	}

	@NotNull
	public ResourceLocation key() {
		return this.key;
	}

	@NotNull
	public ResourceLocation texture() {
		return this.texture;
	}

	public boolean pearlescent() {
		return this.pearlescent;
	}

	public TagKey<Item> reproductionFood() {
		return this.reproductionFood;
	}

	public boolean isNormal() {
		return !this.pearlescent;
	}

	@Override
	@NotNull
	public String getSerializedName() {
		return this.key().toString();
	}
}
