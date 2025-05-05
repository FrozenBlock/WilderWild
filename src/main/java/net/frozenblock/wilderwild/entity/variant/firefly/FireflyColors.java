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
		int spawnPriority
	) {
		String texturePath = "entity/firefly/firefly_" + name;
		register(bootstrapContext, resourceKey, texturePath, name, spawnPriority);
	}

	private static void register(
		@NotNull BootstrapContext<FireflyColor> bootstrapContext,
		ResourceKey<FireflyColor> resourceKey,
		String texturePath,
		String name,
		int spawnPriority
	) {
		bootstrapContext.register(resourceKey, new FireflyColor(new ClientAsset(WWConstants.id(texturePath)), SpawnPrioritySelectors.fallback(spawnPriority), name));
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
		register(bootstrapContext, ON, "on", 1);
		register(bootstrapContext, BLACK, "black", 0);
		register(bootstrapContext, BLUE, "blue", 0);
		register(bootstrapContext, BROWN, "brown", 0);
		register(bootstrapContext, CYAN, "cyan", 0);
		register(bootstrapContext, GRAY, "gray", 0);
		register(bootstrapContext, GREEN, "green", 0);
		register(bootstrapContext, LIGHT_BLUE, "light_blue", 0);
		register(bootstrapContext, LIGHT_GRAY, "light_gray", 0);
		register(bootstrapContext, LIME, "lime", 0);
		register(bootstrapContext, MAGENTA, "magenta", 0);
		register(bootstrapContext, ORANGE, "orange", 0);
		register(bootstrapContext, PINK, "pink", 0);
		register(bootstrapContext, PURPLE, "purple", 0);
		register(bootstrapContext, RED, "red", 0);
		register(bootstrapContext, WHITE, "white", 0);
		register(bootstrapContext, YELLOW, "yellow", 0);
	}
}
