/*
 * Copyright 2025-2026 FrozenBlock
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

package net.frozenblock.wilderwild.mixin.worldgen.tree;

import net.frozenblock.wilderwild.config.WWWorldgenConfig;
import net.frozenblock.wilderwild.data.worldgen.features.WWTreeFeatures;
import net.frozenblock.wilderwild.levelgen.grower.WWTreeGrowers;
import net.frozenblock.wilderwild.levelgen.grower.impl.TreeGrowerInterface;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
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
	private void wilderWild$setCustomFeatures(RandomSource random, boolean hasFlowers, CallbackInfoReturnable<@Nullable ResourceKey<Feature>> info) {
		if (!WWWorldgenConfig.TREE_GENERATION.get()) return;
		final TreeGrower treeGrower = TreeGrower.class.cast(this);

		if (treeGrower == TreeGrower.OAK) {
			if (random.nextInt(10) == 0) {
				info.setReturnValue(hasFlowers ? WWTreeFeatures.FANCY_OAK_BEES_0004.getKey() : WWTreeFeatures.FANCY_OAK.getKey());
			} else {
				if (random.nextFloat() < 0.075F) {
					info.setReturnValue(random.nextBoolean() ? WWTreeFeatures.BIG_BUSH.getKey() : WWTreeFeatures.LARGE_BUSH.getKey());
					return;
				}
				info.setReturnValue(hasFlowers ? WWTreeFeatures.OAK_BEES_0004.getKey() : WWTreeFeatures.OAK.getKey());
			}
		}

		if (treeGrower == TreeGrower.SPRUCE) {
			info.setReturnValue(random.nextFloat() < 0.1F ? WWTreeFeatures.SHORT_SPRUCE.getKey() : WWTreeFeatures.SPRUCE.getKey());
		}

		if (treeGrower == TreeGrower.BIRCH) {
			if (random.nextFloat() < 0.15F) {
				info.setReturnValue(hasFlowers ? WWTreeFeatures.SHORT_BIRCH_BEES_0004.getKey() : WWTreeFeatures.SHORT_BIRCH.getKey());
			} else if (random.nextFloat() < 0.65F) {
				info.setReturnValue(hasFlowers ? WWTreeFeatures.MEDIUM_BIRCH_BEES_025.getKey() : WWTreeFeatures.MEDIUM_BIRCH.getKey());
			} else if (random.nextFloat() < 0.85F) {
				info.setReturnValue(hasFlowers ? WWTreeFeatures.BIRCH_BEES_025.getKey() : WWTreeFeatures.BIRCH.getKey());
			} else {
				info.setReturnValue(hasFlowers ? WWTreeFeatures.SUPER_BIRCH_BEES_0004.getKey() : WWTreeFeatures.SUPER_BIRCH.getKey());
			}
		}

		if (treeGrower == TreeGrower.CHERRY) {
			if (random.nextFloat() < 0.15F) {
				info.setReturnValue(hasFlowers ? WWTreeFeatures.TALL_CHERRY_BEES_025.getKey() : WWTreeFeatures.TALL_CHERRY.getKey());
			} else {
				info.setReturnValue(hasFlowers ? WWTreeFeatures.CHERRY_BEES_025.getKey() : WWTreeFeatures.CHERRY.getKey());
			}
		}

		if (treeGrower == TreeGrower.JUNGLE) {
			if (random.nextFloat() < 0.75F) {
				info.setReturnValue(WWTreeFeatures.JUNGLE_NO_VINE.getKey());
			}
		}

		if (treeGrower == TreeGrower.MANGROVE) {
			if (random.nextFloat() < 0.85F) {
				info.setReturnValue(WWTreeFeatures.TALL_MANGROVE.getKey());
			} else {
				info.setReturnValue(WWTreeFeatures.MANGROVE.getKey());
			}
		}
	}

	@Inject(method = "getConfiguredMegaFeature", at = @At("HEAD"), cancellable = true)
	private void wilderWild$setCustomMegaFeatures(RandomSource random, CallbackInfoReturnable<@Nullable ResourceKey<Feature>> info) {
		if (!WWWorldgenConfig.TREE_GENERATION.get()) return;
		final TreeGrower treeGrower = TreeGrower.class.cast(this);

		if (treeGrower == TreeGrower.SPRUCE) {
			if (random.nextFloat() < 0.25F) {
				info.setReturnValue(WWTreeFeatures.SHORT_MEGA_SPRUCE.getKey());
			}
		}

		if (treeGrower == TreeGrower.DARK_OAK) {
			if (random.nextFloat() < 0.2F) {
				info.setReturnValue(WWTreeFeatures.TALL_DARK_OAK.getKey());
			} else if (random.nextFloat() < 0.2F) {
				info.setReturnValue(WWTreeFeatures.FANCY_TALL_DARK_OAK.getKey());
			}
		}

		if (treeGrower == TreeGrower.JUNGLE) {
			if (random.nextFloat() < 0.60F) {
				info.setReturnValue(WWTreeFeatures.MEGA_JUNGLE.getKey());
			}
		}

		if (treeGrower == TreeGrower.PALE_OAK) {
			if (random.nextFloat() < 0.175F) {
				info.setReturnValue(random.nextFloat() < 0.75F ? WWTreeFeatures.TALL_PALE_OAK_BONEMEAL.getKey() : WWTreeFeatures.FANCY_TALL_PALE_OAK_BONEMEAL.getKey());
			} else {
				info.setReturnValue(WWTreeFeatures.PALE_OAK_BONEMEAL.getKey());
			}
		}
	}


	@Inject(method = "growTree", at = @At("HEAD"), cancellable = true)
	private void wilderWild$growOrangePoplarTree(
		ServerLevel level, ChunkGenerator generator, BlockPos pos, BlockState state, RandomSource random, CallbackInfoReturnable<Boolean> info
	) {
		if (!WWWorldgenConfig.TREE_GENERATION.get()) return;
		final TreeGrower treeGrower = TreeGrower.class.cast(this);
		if (treeGrower == TreeGrower.POPLAR) info.setReturnValue(WWTreeGrowers.POPLAR.orange().growTree(level, generator, pos, state, random));
	}
}
