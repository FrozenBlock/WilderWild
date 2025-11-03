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

package net.frozenblock.wilderwild.entity.variant.firefly;

import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.registry.WilderWildRegistries;
import net.minecraft.core.ClientAsset;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.variant.PriorityProvider;
import net.minecraft.world.entity.variant.SpawnContext;
import net.minecraft.world.entity.variant.SpawnPrioritySelectors;
import net.minecraft.world.item.DyeColor;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class FireflyColors {
	public static final ResourceKey<FireflyColor> ON = createKey("on");
	public static final ResourceKey<FireflyColor> BLACK = createKey("black");
	public static final ResourceKey<FireflyColor> BLUE = createKey("blue");
	public static final ResourceKey<FireflyColor> BROWN = createKey("brown");
	public static final ResourceKey<FireflyColor> CYAN = createKey("cyan");
	public static final ResourceKey<FireflyColor> GRAY = createKey("gray");
	public static final ResourceKey<FireflyColor> GREEN = createKey("green");
	public static final ResourceKey<FireflyColor> LIGHT_BLUE = createKey("light_blue");
	public static final ResourceKey<FireflyColor> LIGHT_GRAY = createKey("light_gray");
	public static final ResourceKey<FireflyColor> LIME = createKey("lime");
	public static final ResourceKey<FireflyColor> MAGENTA = createKey("magenta");
	public static final ResourceKey<FireflyColor> ORANGE = createKey("orange");
	public static final ResourceKey<FireflyColor> PINK = createKey("pink");
	public static final ResourceKey<FireflyColor> PURPLE = createKey("purple");
	public static final ResourceKey<FireflyColor> RED = createKey("red");
	public static final ResourceKey<FireflyColor> WHITE = createKey("white");
	public static final ResourceKey<FireflyColor> YELLOW = createKey("yellow");
	public static final ResourceKey<FireflyColor> DEFAULT = ON;

	private static final List<String> VANILLA_FIREFLY_COLORS = ImmutableList.<String>builder()
		.add(WWConstants.string("on"))
		.add(WWConstants.string("black"))
		.add(WWConstants.string("blue"))
		.add(WWConstants.string("brown"))
		.add(WWConstants.string("cyan"))
		.add(WWConstants.string("gray"))
		.add(WWConstants.string("green"))
		.add(WWConstants.string("light_blue"))
		.add(WWConstants.string("light_gray"))
		.add(WWConstants.string("lime"))
		.add(WWConstants.string("magenta"))
		.add(WWConstants.string("orange"))
		.add(WWConstants.string("pink"))
		.add(WWConstants.string("purple"))
		.add(WWConstants.string("red"))
		.add(WWConstants.string("white"))
		.add(WWConstants.string("yellow"))
		.build();

	private static @NotNull ResourceKey<FireflyColor> createKey(String string) {
		return ResourceKey.create(WilderWildRegistries.FIREFLY_COLOR, WWConstants.id(string));
	}

	private static void register(
		@NotNull BootstrapContext<FireflyColor> bootstrapContext,
		ResourceKey<FireflyColor> resourceKey,
		String name,
		Optional<DyeColor> dyeColor,
		int spawnPriority
	) {
		String texturePath = "entity/firefly/firefly_" + name;
		register(bootstrapContext, resourceKey, texturePath, name, dyeColor, spawnPriority);
	}

	private static void register(
		@NotNull BootstrapContext<FireflyColor> bootstrapContext,
		ResourceKey<FireflyColor> resourceKey,
		String texturePath,
		String name,
		Optional<DyeColor> dyeColor,
		int spawnPriority
	) {
		bootstrapContext.register(resourceKey, new FireflyColor(new ClientAsset(WWConstants.id(texturePath)), SpawnPrioritySelectors.fallback(spawnPriority), name, dyeColor));
	}

	public static @NotNull Optional<Holder.Reference<FireflyColor>> selectVariantToSpawn(
		RandomSource randomSource,
		@NotNull RegistryAccess registryAccess,
		SpawnContext spawnContext
	) {
		return PriorityProvider.pick(registryAccess.lookupOrThrow(WilderWildRegistries.FIREFLY_COLOR).listElements(), Holder::value, randomSource, spawnContext);
	}

	@Contract(pure = true)
	public static @NotNull Stream<String> getVanillaColors() {
		return VANILLA_FIREFLY_COLORS.stream();
	}

	public static void bootstrap(BootstrapContext<FireflyColor> bootstrapContext) {
		register(bootstrapContext, ON, "on", Optional.empty(), 1);
		register(bootstrapContext, BLACK, "black", Optional.of(DyeColor.BLACK), 0);
		register(bootstrapContext, BLUE, "blue", Optional.of(DyeColor.BLUE), 0);
		register(bootstrapContext, BROWN, "brown", Optional.of(DyeColor.BROWN), 0);
		register(bootstrapContext, CYAN, "cyan", Optional.of(DyeColor.CYAN), 0);
		register(bootstrapContext, GRAY, "gray", Optional.of(DyeColor.GRAY), 0);
		register(bootstrapContext, GREEN, "green", Optional.of(DyeColor.GREEN), 0);
		register(bootstrapContext, LIGHT_BLUE, "light_blue", Optional.of(DyeColor.LIGHT_BLUE), 0);
		register(bootstrapContext, LIGHT_GRAY, "light_gray", Optional.of(DyeColor.LIGHT_GRAY), 0);
		register(bootstrapContext, LIME, "lime", Optional.of(DyeColor.LIME), 0);
		register(bootstrapContext, MAGENTA, "magenta", Optional.of(DyeColor.MAGENTA), 0);
		register(bootstrapContext, ORANGE, "orange", Optional.of(DyeColor.ORANGE), 0);
		register(bootstrapContext, PINK, "pink", Optional.of(DyeColor.PINK), 0);
		register(bootstrapContext, PURPLE, "purple", Optional.of(DyeColor.PURPLE), 0);
		register(bootstrapContext, RED, "red", Optional.of(DyeColor.RED), 0);
		register(bootstrapContext, WHITE, "white", Optional.of(DyeColor.WHITE), 0);
		register(bootstrapContext, YELLOW, "yellow", Optional.of(DyeColor.YELLOW), 0);
	}
}
