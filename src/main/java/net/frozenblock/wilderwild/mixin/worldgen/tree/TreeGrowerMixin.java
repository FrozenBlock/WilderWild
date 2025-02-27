/*
 * Copyright 2023-2025 FrozenBlock
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

package net.frozenblock.wilderwild.mixin.worldgen.tree;

import net.frozenblock.wilderwild.config.WWWorldgenConfig;
import net.frozenblock.wilderwild.worldgen.features.configured.WWTreeConfigured;
import net.frozenblock.wilderwild.worldgen.impl.sapling.impl.TreeGrowerInterface;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = TreeGrower.class, priority = 69420)
public class TreeGrowerMixin implements TreeGrowerInterface {

	@Unique
	private ServerLevel wilderWild$level;

	@Unique
	private BlockPos wilderWild$pos;

	@Inject(method = "growTree", at = @At("HEAD"))
	public void wilderWild$growTree(ServerLevel level, ChunkGenerator generator, BlockPos pos, BlockState state, RandomSource random, CallbackInfoReturnable<Boolean> info) {
		this.wilderWild$level = level;
		this.wilderWild$pos = pos;
	}

	@Unique
	@Override
	public ServerLevel wilderWild$getLevel() {
		return this.wilderWild$level;
	}

	@Unique
	@Override
	public BlockPos wilderWild$getPos() {
		return this.wilderWild$pos;
	}

	@Inject(method = "getConfiguredFeature", at = @At("HEAD"), cancellable = true)
	private void setCustomFeatures(RandomSource random, boolean flowers, CallbackInfoReturnable<@Nullable ResourceKey<ConfiguredFeature<?, ?>>> info) {
		if (!WWWorldgenConfig.get().treeGeneration) return;
		TreeGrower treeGrower = TreeGrower.class.cast(this);

		if (treeGrower == TreeGrower.OAK) {
			if (random.nextInt(10) == 0) {
				info.setReturnValue(flowers ? WWTreeConfigured.FANCY_OAK_BEES_0004.getKey() : WWTreeConfigured.FANCY_OAK.getKey());
			} else {
				if (random.nextFloat() < 0.075F) {
					info.setReturnValue(random.nextBoolean() ? WWTreeConfigured.SHRUB.getKey() : WWTreeConfigured.BIG_SHRUB.getKey());
					return;
				}
				info.setReturnValue(flowers ? WWTreeConfigured.OAK_BEES_0004.getKey() : WWTreeConfigured.OAK.getKey());
			}
		} else if (treeGrower == TreeGrower.SPRUCE) {
			info.setReturnValue(random.nextFloat() < 0.1F ? WWTreeConfigured.SPRUCE_SHORT.getKey() : WWTreeConfigured.SPRUCE.getKey());
		} else if (treeGrower == TreeGrower.BIRCH) {
			if (random.nextFloat() < 0.15F) {
				info.setReturnValue(flowers ? WWTreeConfigured.SHORT_BIRCH_BEES_0004.getKey() : WWTreeConfigured.SHORT_BIRCH.getKey());
			} else if (random.nextFloat() < 0.65F) {
				info.setReturnValue(flowers ? WWTreeConfigured.MEDIUM_BIRCH_BEES_025.getKey() : WWTreeConfigured.MEDIUM_BIRCH.getKey());
			} else if (random.nextFloat() < 0.85F) {
				info.setReturnValue(flowers ? WWTreeConfigured.BIRCH_BEES_025.getKey() : WWTreeConfigured.BIRCH_TREE.getKey());
			} else {
				info.setReturnValue(flowers ? WWTreeConfigured.SUPER_BIRCH_BEES_0004.getKey() : WWTreeConfigured.SUPER_BIRCH.getKey());
			}
		} else if (treeGrower == TreeGrower.CHERRY) {
			if (random.nextFloat() < 0.15F) {
				info.setReturnValue(flowers ? WWTreeConfigured.TALL_CHERRY_BEES_025.getKey() : WWTreeConfigured.TALL_CHERRY_TREE.getKey());
			} else {
				info.setReturnValue(flowers ? WWTreeConfigured.CHERRY_BEES_025.getKey() : WWTreeConfigured.CHERRY_TREE.getKey());
			}
		} else if (treeGrower == TreeGrower.JUNGLE) {
			if (random.nextFloat() < 0.75F) {
				info.setReturnValue(WWTreeConfigured.JUNGLE_TREE_NO_VINE.getKey());
			}
		} else if (treeGrower == TreeGrower.MANGROVE) {
			if (random.nextFloat() < 0.85F) {
				info.setReturnValue(WWTreeConfigured.TALL_MANGROVE.getKey());
			} else {
				info.setReturnValue(WWTreeConfigured.MANGROVE.getKey());
			}
		}
	}

	@Inject(method = "getConfiguredMegaFeature", at = @At("HEAD"), cancellable = true)
	private void setCustomMegaFeatures(RandomSource random, CallbackInfoReturnable<@Nullable ResourceKey<ConfiguredFeature<?, ?>>> info) {
		if (!WWWorldgenConfig.get().treeGeneration) return;
		TreeGrower treeGrower = TreeGrower.class.cast(this);

		if (treeGrower == TreeGrower.SPRUCE) {
			if (random.nextFloat() < 0.25F) {
				info.setReturnValue(WWTreeConfigured.SHORT_MEGA_SPRUCE.getKey());
			}
		} else if (treeGrower == TreeGrower.DARK_OAK) {
			if (random.nextFloat() < 0.2F) {
				info.setReturnValue(WWTreeConfigured.TALL_DARK_OAK.getKey());
			} else if (random.nextFloat() < 0.2F) {
				info.setReturnValue(WWTreeConfigured.FANCY_TALL_DARK_OAK.getKey());
			}
		} else if (treeGrower == TreeGrower.JUNGLE) {
			if (random.nextFloat() < 0.60F) {
				info.setReturnValue(WWTreeConfigured.MEGA_JUNGLE_TREE.getKey());
			}
		}
	}
}
