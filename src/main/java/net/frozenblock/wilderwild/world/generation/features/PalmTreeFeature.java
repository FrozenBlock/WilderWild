package net.frozenblock.wilderwild.world.generation.features;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import java.util.Iterator;
import java.util.List;
import java.util.OptionalInt;
import java.util.Set;
import net.frozenblock.wilderwild.block.PalmFrondsBlock;
import net.frozenblock.wilderwild.misc.interfaces.TreeFeatureLeavesUpdate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.shapes.BitSetDiscreteVoxelShape;
import net.minecraft.world.phys.shapes.DiscreteVoxelShape;
import org.jetbrains.annotations.NotNull;

public class PalmTreeFeature extends TreeFeature implements TreeFeatureLeavesUpdate {

	private static final int MAX_DISTANCE = PalmFrondsBlock.DECAY_DISTANCE;

	public PalmTreeFeature(Codec<TreeConfiguration> codec) {
		super(codec);
	}

	@Override
	public DiscreteVoxelShape wilderWild$updateLeaves(LevelAccessor level, @NotNull BoundingBox box, Set<BlockPos> rootPositions, Set<BlockPos> trunkPositions, Set<BlockPos> foliagePositions) {
		DiscreteVoxelShape discreteVoxelShape = new BitSetDiscreteVoxelShape(box.getXSpan(), box.getYSpan(), box.getZSpan());
		List<Set<BlockPos>> list = Lists.newArrayList();

		for(int j = 0; j < MAX_DISTANCE; ++j) {
			list.add(Sets.newHashSet());
		}

		for(BlockPos blockPos : Lists.newArrayList(Sets.union(trunkPositions, foliagePositions))) {
			if (box.isInside(blockPos)) {
				discreteVoxelShape.fill(blockPos.getX() - box.minX(), blockPos.getY() - box.minY(), blockPos.getZ() - box.minZ());
			}
		}

		BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
		int k = 0;
		list.get(0).addAll(rootPositions);

		while(true) {
			while(k >= MAX_DISTANCE || !list.get(k).isEmpty()) {
				if (k >= MAX_DISTANCE) {
					return discreteVoxelShape;
				}

				Iterator<BlockPos> iterator = list.get(k).iterator();
				BlockPos blockPos2 = iterator.next();
				iterator.remove();
				if (box.isInside(blockPos2)) {
					if (k != 0) {
						BlockState blockState = level.getBlockState(blockPos2);
						setBlockKnownShape(level, blockPos2, blockState.setValue(BlockStateProperties.DISTANCE, k));
					}

					discreteVoxelShape.fill(blockPos2.getX() - box.minX(), blockPos2.getY() - box.minY(), blockPos2.getZ() - box.minZ());

					for(Direction direction : Direction.values()) {
						mutableBlockPos.setWithOffset(blockPos2, direction);
						if (box.isInside(mutableBlockPos)) {
							int l = mutableBlockPos.getX() - box.minX();
							int m = mutableBlockPos.getY() - box.minY();
							int n = mutableBlockPos.getZ() - box.minZ();
							if (!discreteVoxelShape.isFull(l, m, n)) {
								BlockState blockState2 = level.getBlockState(mutableBlockPos);
								OptionalInt optionalInt = LeavesBlock.getOptionalDistanceAt(blockState2);
								if (optionalInt.isPresent()) {
									int o = Math.min(optionalInt.getAsInt(), k + 1);
									if (o < MAX_DISTANCE) {
										list.get(o).add(mutableBlockPos.immutable());
										k = Math.min(k, o);
									}
								}
							}
						}
					}
				}
			}

			++k;
		}
	}
}
