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

package net.frozenblock.wilderwild.mixin;

import java.util.List;
import java.util.Set;
import net.fabricmc.loader.api.FabricLoader;
import net.frozenblock.lib.FrozenBools;
import net.frozenblock.wilderwild.config.MixinsConfig;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

public class WilderWildMixinPlugin implements IMixinConfigPlugin {

	@Override
	public void onLoad(String mixinPackage) {

	}

	@Override
	@Nullable
	public String getRefMapperConfig() {
		return null;
	}

	@Override
	public boolean shouldApplyMixin(String targetClassName, @NotNull String mixinClassName) {
		MixinsConfig config = MixinsConfig.get();
		boolean hasEmbeddium = FabricLoader.getInstance().isModLoaded("embeddium");
		boolean disableNonSodium = hasEmbeddium || FrozenBools.HAS_SODIUM;
		boolean enableSodium = FrozenBools.HAS_SODIUM;
		boolean enableIndium = FrozenBools.HAS_INDIUM;
		if (mixinClassName.contains("client.sodium")) {
			return config.client_sodium && enableSodium && !hasEmbeddium;
		} else if (mixinClassName.contains("client.embeddium")) {
			return config.client_sodium && hasEmbeddium;
		}
		if (mixinClassName.contains("client.indium")) {
			return config.client_indium && enableIndium;
		}
		if (mixinClassName.contains("client.allay")) return config.client_allay;
		if (mixinClassName.contains("client.brush")) return config.client_brush;
		if (mixinClassName.contains("client.easter")) return config.client_easter;
		if (mixinClassName.contains("client.mesoglea")) {
			if ((mixinClassName.contains("LiquidBlockRenderer") || mixinClassName.contains("EntityRenderDispatcher")) && disableNonSodium)
				return false;
			return config.client_mesoglea;
		}
		if (mixinClassName.contains("client.shrieker")) return config.client_shrieker;
		if (mixinClassName.contains("client.warden")) return config.client_warden;
		if (mixinClassName.contains("client.wind")) {
			if (mixinClassName.contains("fallingleaves") && !FabricLoader.getInstance().isModLoaded("fallingleaves"))
				return false;
			if (mixinClassName.contains("particlerain") && !FabricLoader.getInstance().isModLoaded("particlerain"))
				return false;
			if (mixinClassName.contains("CloudRenderer") && disableNonSodium)
				return false;
			return config.client_wind;
		}

		if (mixinClassName.contains("block.beacon")) return config.block_beacon;
		if (mixinClassName.contains("block.cactus")) return config.block_cactus;
		if (mixinClassName.contains("block.chest")) return config.block_chest;
		if (mixinClassName.contains("block.dripleaf")) return config.block_dripleaf;
		if (mixinClassName.contains("block.dripstone")) return config.block_dripstone;
		if (mixinClassName.contains("block.fire")) return config.block_fire;
		if (mixinClassName.contains("block.ice")) return config.block_ice;
		if (mixinClassName.contains("block.lava")) return config.block_lava;
		if (mixinClassName.contains("block.leaves")) return config.block_leaves;
		if (mixinClassName.contains("block.mesoglea")) return config.block_mesoglea;
		if (mixinClassName.contains("block.palm_fronds")) return config.block_palm_fronds;
		if (mixinClassName.contains("block.reinforced_deepslate")) return config.block_reinforced_deepslate;
		if (mixinClassName.contains("block.spawner")) return config.block_spawner;
		if (mixinClassName.contains("block.termite")) return config.block_termite;
		if (mixinClassName.contains("snowlogging")) return config.snowlogging;
		if (mixinClassName.contains("entity.ai")) return config.entity_ai;
		if (mixinClassName.contains("entity.allay")) return config.entity_allay;
		if (mixinClassName.contains("entity.boat")) return config.entity_boat;
		if (mixinClassName.contains("entity.easter")) return config.entity_easter;
		if (mixinClassName.contains("entity.enderman")) return config.entity_enderman;
		if (mixinClassName.contains("entity.experience")) return config.entity_experience;
		if (mixinClassName.contains("entity.jellyfish")) return config.entity_jellyfish;
		if (mixinClassName.contains("entity.lightning")) return config.entity_lightning;
		if (mixinClassName.contains("entity.slime")) return config.entity_slime;
		if (mixinClassName.contains("entity.stray")) return config.entity_stray;
		if (mixinClassName.contains("entity.tumbleweed")) return config.entity_tumbleweed;
		if (mixinClassName.contains("entity.turtle")) return config.entity_turtle;
		if (mixinClassName.contains("entity.firework_rocket")) return config.entity_firework_rocket;
		if (mixinClassName.contains("item.axe")) return config.item_axe;
		if (mixinClassName.contains("item.brush")) return config.item_brush;
		if (mixinClassName.contains("item.instrument")) return config.item_instrument;
		if (mixinClassName.contains("loot")) return config.loot;
		if (mixinClassName.contains("projectile")) return config.projectile;
		if (mixinClassName.contains("sculk")) return config.sculk;
		if (mixinClassName.contains("warden")) return config.warden;
		if (mixinClassName.contains("worldgen.biome")) return config.worldgen_biome;
		if (mixinClassName.contains("worldgen.structure")) return config.worldgen_structure;
		if (mixinClassName.contains("worldgen.tree")) return config.worldgen_tree;

		return true;
	}

	@Override
	public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {

	}

	@Override
	@Nullable
	public List<String> getMixins() {
		return null;
	}

	@Override
	public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
	}

	@Override
	public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

	}
}
