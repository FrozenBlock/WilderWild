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

package net.frozenblock.wilderwild.mixin.worldgen.tree;

import net.frozenblock.wilderwild.config.WWWorldgenConfig;
import net.frozenblock.wilderwild.tag.WWBiomeTags;
import net.frozenblock.wilderwild.worldgen.feature.configured.WWTreeConfigured;
import net.frozenblock.wilderwild.worldgen.impl.sapling.TreeGrowerInterface;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.biome.Biome;
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
	private void setCustomFeatures(RandomSource random, boolean flowers, CallbackInfoReturnable<@Nullable ResourceKey<ConfiguredFeature<?, ?>>> cir) {
		if (!WWWorldgenConfig.get().treeGeneration) return;
		TreeGrower treeGrower = TreeGrower.class.cast(this);

		if (treeGrower == TreeGrower.OAK) {
			if (this.wilderWild$getLevel() != null && this.wilderWild$getPos() != null && random.nextFloat() <= 0.4F) {
				Holder<Biome> biome = this.wilderWild$getLevel().getBiome(this.wilderWild$getPos());
				if (biome.is(WWBiomeTags.OAK_SAPLINGS_GROW_SWAMP_VARIANT)) {
					cir.setReturnValue(WWTreeConfigured.SWAMP_TREE.getKey());
					return;
				}
			}
			if (random.nextInt(10) == 0) {
				cir.setReturnValue(flowers ? WWTreeConfigured.FANCY_OAK_BEES_0004.getKey() : WWTreeConfigured.FANCY_OAK.getKey());
			} else {
				if (random.nextFloat() < 0.075F) {
					cir.setReturnValue(random.nextBoolean() ? WWTreeConfigured.SHRUB.getKey() : WWTreeConfigured.BIG_SHRUB.getKey());
					return;
				}
				cir.setReturnValue(flowers ? WWTreeConfigured.OAK_BEES_0004.getKey() : WWTreeConfigured.OAK.getKey());
			}
		} else if (treeGrower == TreeGrower.SPRUCE) {
			cir.setReturnValue(random.nextFloat() < 0.1F ? WWTreeConfigured.SPRUCE_SHORT.getKey() : WWTreeConfigured.SPRUCE.getKey());
		} else if (treeGrower == TreeGrower.BIRCH) {
			if (random.nextFloat() < 0.15F)
				cir.setReturnValue(flowers ? WWTreeConfigured.SHORT_BIRCH_BEES_0004.getKey() : WWTreeConfigured.SHORT_BIRCH.getKey());
			else
				cir.setReturnValue(flowers ? WWTreeConfigured.BIRCH_BEES_025.getKey() : WWTreeConfigured.BIRCH_TREE.getKey());
		} else if (treeGrower == TreeGrower.CHERRY) {
			if (random.nextFloat() < 0.15F)
				cir.setReturnValue(flowers ? WWTreeConfigured.TALL_CHERRY_BEES_025.getKey() : WWTreeConfigured.TALL_CHERRY_TREE.getKey());
			else
				cir.setReturnValue(flowers ? WWTreeConfigured.CHERRY_BEES_025.getKey() : WWTreeConfigured.CHERRY_TREE.getKey());
		}
	}

	@Inject(method = "getConfiguredMegaFeature", at = @At("HEAD"), cancellable = true)
	private void setCustomMegaFeatures(RandomSource random, CallbackInfoReturnable<@Nullable ResourceKey<ConfiguredFeature<?, ?>>> cir) {
		if (!WWWorldgenConfig.get().treeGeneration) return;
		TreeGrower treeGrower = TreeGrower.class.cast(this);

		if (treeGrower == TreeGrower.SPRUCE) {
			if (random.nextFloat() < 0.25F)
				cir.setReturnValue(WWTreeConfigured.SHORT_MEGA_SPRUCE.getKey());
		} else if (treeGrower == TreeGrower.DARK_OAK) {
			if (random.nextFloat() < 0.2F)
				cir.setReturnValue(WWTreeConfigured.TALL_DARK_OAK.getKey());
			else if (random.nextFloat() < 0.2F)
				cir.setReturnValue(WWTreeConfigured.FANCY_TALL_DARK_OAK.getKey());
		}
	}
}
