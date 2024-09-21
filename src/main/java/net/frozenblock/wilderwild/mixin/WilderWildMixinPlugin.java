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
import net.frozenblock.wilderwild.config.WWMixinsConfig;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

public final class WilderWildMixinPlugin implements IMixinConfigPlugin {
	private WWMixinsConfig mixinsConfig;
	private boolean hasEmbeddium;
	private boolean disableNonSodium;
	private boolean enableIndium;

	@Override
	public void onLoad(String mixinPackage) {
		this.mixinsConfig = WWMixinsConfig.get();
		this.hasEmbeddium = FabricLoader.getInstance().isModLoaded("embeddium");
		this.disableNonSodium = this.hasEmbeddium || FrozenBools.HAS_SODIUM;
		this.enableIndium = FrozenBools.HAS_SODIUM && FrozenBools.HAS_INDIUM;
	}

	@Override
	@Nullable
	public String getRefMapperConfig() {
		return null;
	}

	@Override
	public boolean shouldApplyMixin(String targetClassName, @NotNull String mixinClassName) {
		if (mixinClassName.contains("client.sodium.")) {
			return this.mixinsConfig.client_sodium && FrozenBools.HAS_SODIUM && !this.hasEmbeddium;
		}
		if (mixinClassName.contains("client.indium")) {
			return this.mixinsConfig.client_indium && this.enableIndium && !this.hasEmbeddium;
		}
		if (mixinClassName.contains("client.allay.")) return this.mixinsConfig.client_allay;
		if (mixinClassName.contains("client.brush.")) return this.mixinsConfig.client_brush;
		if (mixinClassName.contains("client.easter.")) return this.mixinsConfig.client_easter;
		if (mixinClassName.contains("client.mesoglea.")) {
			if ((mixinClassName.contains("LiquidBlockRenderer") || mixinClassName.contains("EntityRenderDispatcher")) && this.disableNonSodium)
				return false;
			return this.mixinsConfig.client_mesoglea;
		}
		if (mixinClassName.contains("client.shrieker.")) return this.mixinsConfig.client_shrieker;
		if (mixinClassName.contains("client.warden.")) return this.mixinsConfig.client_warden;
		if (mixinClassName.contains("client.wind.")) {
			if (mixinClassName.contains("fallingleaves") && !FabricLoader.getInstance().isModLoaded("fallingleaves"))
				return false;
			if (mixinClassName.contains("particlerain") && !FabricLoader.getInstance().isModLoaded("particlerain"))
				return false;
			if (mixinClassName.contains("CloudRenderer") && this.disableNonSodium)
				return false;
			return this.mixinsConfig.client_wind;
		}

		if (mixinClassName.contains("block.cactus.")) return this.mixinsConfig.block_cactus;
		if (mixinClassName.contains("block.chest.")) return this.mixinsConfig.block_chest;
		if (mixinClassName.contains("block.dripleaf.")) return this.mixinsConfig.block_dripleaf;
		if (mixinClassName.contains("block.dripstone.")) return this.mixinsConfig.block_dripstone;
		if (mixinClassName.contains("block.echo_glass.")) return this.mixinsConfig.block_echo_glass;
		if (mixinClassName.contains("block.fire.")) return this.mixinsConfig.block_fire;
		if (mixinClassName.contains("block.ice.")) return this.mixinsConfig.block_ice;
		if (mixinClassName.contains("block.lava.")) return this.mixinsConfig.block_lava;
		if (mixinClassName.contains("block.leaves.")) return this.mixinsConfig.block_leaves;
		if (mixinClassName.contains("block.mesoglea.")) return this.mixinsConfig.block_mesoglea;
		if (mixinClassName.contains("block.reinforced_deepslate.")) return this.mixinsConfig.block_reinforced_deepslate;
		if (mixinClassName.contains("block.spawner.")) return this.mixinsConfig.block_spawner;
		if (mixinClassName.contains("block.termite.")) return this.mixinsConfig.block_termite;
		if (mixinClassName.contains("snowlogging.")) return this.mixinsConfig.snowlogging;
		if (mixinClassName.contains("entity.ai.")) return this.mixinsConfig.entity_ai;
		if (mixinClassName.contains("entity.allay.")) return this.mixinsConfig.entity_allay;
		if (mixinClassName.contains("entity.boat.")) return this.mixinsConfig.entity_boat;
		if (mixinClassName.contains("entity.easter.")) return this.mixinsConfig.entity_easter;
		if (mixinClassName.contains("entity.enderman.")) return this.mixinsConfig.entity_enderman;
		if (mixinClassName.contains("entity.experience.")) return this.mixinsConfig.entity_experience;
		if (mixinClassName.contains("entity.jellyfish.")) return this.mixinsConfig.entity_jellyfish;
		if (mixinClassName.contains("entity.lightning.")) return this.mixinsConfig.entity_lightning;
		if (mixinClassName.contains("entity.slime.")) return this.mixinsConfig.entity_slime;
		if (mixinClassName.contains("entity.stray.")) return this.mixinsConfig.entity_stray;
		if (mixinClassName.contains("entity.tumbleweed.")) return this.mixinsConfig.entity_tumbleweed;
		if (mixinClassName.contains("entity.turtle.")) return this.mixinsConfig.entity_turtle;
		if (mixinClassName.contains("entity.firework_rocket.")) return this.mixinsConfig.entity_firework_rocket;
		if (mixinClassName.contains("item.axe.")) return this.mixinsConfig.item_axe;
		if (mixinClassName.contains("item.brush.")) return this.mixinsConfig.item_brush;
		if (mixinClassName.contains("item.instrument.")) return this.mixinsConfig.item_instrument;
		if (mixinClassName.contains("loot.")) return this.mixinsConfig.loot;
		if (mixinClassName.contains("projectile.")) return this.mixinsConfig.projectile;
		if (mixinClassName.contains("sculk.")) return this.mixinsConfig.sculk;
		if (mixinClassName.contains("warden.")) return this.mixinsConfig.warden;
		if (mixinClassName.contains("worldgen.biome.")) return this.mixinsConfig.worldgen_biome;
		if (mixinClassName.contains("worldgen.structure.")) return this.mixinsConfig.worldgen_structure;
		if (mixinClassName.contains("worldgen.tree.")) return this.mixinsConfig.worldgen_tree;

		return true;
	}

	@Override
	public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {}

	@Override
	@Nullable
	public List<String> getMixins() {
		return null;
	}

	@Override
	public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {}

	@Override
	public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {}
}
