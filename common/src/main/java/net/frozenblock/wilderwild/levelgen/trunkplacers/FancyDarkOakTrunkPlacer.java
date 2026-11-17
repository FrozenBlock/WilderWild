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

package net.frozenblock.wilderwild.levelgen.trunkplacers;

import com.google.common.collect.Sets;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import net.frozenblock.lib.config.v2.entry.predicates.ConfigPredicate;
import net.frozenblock.wilderwild.registry.WWFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.CompositeDirection;
import net.minecraft.core.Direction;
import net.minecraft.core.Directional;
import net.minecraft.core.Holder;
import net.minecraft.core.Vec3i;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Util;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import org.jetbrains.annotations.Nullable;

public class FancyDarkOakTrunkPlacer extends TrunkPlacer {
	public static final MapCodec<FancyDarkOakTrunkPlacer> CODEC = RecordCodecBuilder.mapCodec(instance ->
		trunkPlacerParts(instance)
			.and(ConfigPredicate.HOLDER_CODEC.optionalFieldOf("branch_condition").forGetter(trunkPlacer -> trunkPlacer.branchCondition))
			.apply(instance, FancyDarkOakTrunkPlacer::new)
	);

	private final Optional<Holder<ConfigPredicate>> branchCondition;

	public FancyDarkOakTrunkPlacer(int baseHeight, int heightRandA, int heightRandB, Optional<Holder<ConfigPredicate>> branchCondition) {
		super(baseHeight, heightRandA, heightRandB);
		this.branchCondition = branchCondition;
	}

	@Override
	protected TrunkPlacerType<?> type() {
		return WWFeatures.FANCY_DARK_OAK_TRUNK_PLACER.get();
	}

	@Override
	public List<FoliagePlacer.FoliageAttachment> placeTrunk(
		WorldGenLevel level,
		BiConsumer<BlockPos, BlockState> trunkSetter,
		RandomSource random,
		int treeHeight,
		BlockPos origin,
		TreeFeature tree
	) {
		final List<FoliagePlacer.FoliageAttachment> attachments = new ArrayList<>();
		final BlockPos.MutableBlockPos logPos = new BlockPos.MutableBlockPos();

		placeBelowTrunkBlock(level, trunkSetter, random, logPos.setWithOffset(origin, Direction.DOWN), tree);
		placeBelowTrunkBlock(level, trunkSetter, random, logPos.move(Direction.EAST), tree);
		placeBelowTrunkBlock(level, trunkSetter, random, logPos.move(Direction.SOUTH), tree);
		placeBelowTrunkBlock(level, trunkSetter, random, logPos.move(Direction.WEST), tree);

		final Direction leanDirection = Direction.Plane.HORIZONTAL.getRandomDirection(random);
		int leanHeight = treeHeight - random.nextInt(4);
		int leanSteps = 2 - random.nextInt(3);
		int x = origin.getX();
		int y = origin.getY();
		int z = origin.getZ();
		int currentLeanX = x;
		int currentLeanZ = z;
		int treeEndY = y + treeHeight - 1;

		for (int yStep = 0; yStep < treeHeight; ++yStep) {
			if (yStep >= leanHeight && leanSteps > 0) {
				currentLeanX += leanDirection.getStepX();
				currentLeanZ += leanDirection.getStepZ();
				--leanSteps;
			}

			final int currentY = y + yStep;
			logPos.set(currentLeanX, currentY, currentLeanZ);
			if (!TreeFeature.isAirOrLeaves(level, logPos)) continue;

			this.placeLog(level, trunkSetter, random, logPos, tree);
			this.placeLog(level, trunkSetter, random, logPos.move(Direction.EAST), tree);
			this.placeLog(level, trunkSetter, random, logPos.move(Direction.SOUTH), tree);
			this.placeLog(level, trunkSetter, random, logPos.move(Direction.WEST), tree);
		}

		attachments.add(new FoliagePlacer.FoliageAttachment(new BlockPos(currentLeanX, treeEndY, currentLeanZ), 0, true));

		if (this.branchCondition.isPresent() && !this.branchCondition.get().value().test()) return attachments;

		// I know this is silly, but I've implemented this as a safeguard so the `nextIntBetweenInclusive` method won't crash if someone screws around.
		if (treeHeight <= 3) return attachments;

		final List<BranchInfo> branches = new ArrayList<>();
		final BlockPos.MutableBlockPos branchPos = new BlockPos.MutableBlockPos();
		for (int branch = 0; branch < treeHeight / 2; branch++) {
			final BranchInfo newBranch = BranchInfo.tryCreate(treeHeight, random, branches);
			if (newBranch == null) continue;

			branches.add(newBranch);
			newBranch.generate(
				pos -> this.placeLog(level, trunkSetter, random, pos, tree),
				attachments,
				random,
				origin,
				branchPos,
				logPos
			);
		}

		return attachments;
	}

	private static final Set<Directional> SIDE_BRANCH_DIRECTIONS = Set.of(
		Direction.NORTH,
		Direction.EAST,
		Direction.SOUTH,
		Direction.WEST
	);
	private static final Set<Directional> CORNER_BRANCH_DIRECTIONS = Set.of(
		CompositeDirection.Direction8.NORTH_EAST,
		CompositeDirection.Direction8.SOUTH_EAST,
		CompositeDirection.Direction8.SOUTH_WEST,
		CompositeDirection.Direction8.NORTH_WEST
	);
	private static final List<Directional> ALL_BRANCH_DIRECTIONS = Sets.symmetricDifference(SIDE_BRANCH_DIRECTIONS, CORNER_BRANCH_DIRECTIONS).stream().toList();

	private enum BranchType {
		STRAIGHT(SIDE_BRANCH_DIRECTIONS) {
			@Override
			void generate(
				Consumer<BlockPos> trunkSetter,
				List<FoliagePlacer.FoliageAttachment> attachments,
				Directional direction,
				RandomSource random,
				BlockPos.MutableBlockPos logPos
			) {
				final Direction side = setupStraightSide(logPos, direction, random);
				BranchType.generateStraightBase(trunkSetter, logPos, direction, side);
				attachments.add(
					new FoliagePlacer.FoliageAttachment(
						logPos.move(Direction.UP).immutable(),
						0,
						false
					)
				);
			}
		},
		STRAIGHT_THICK(SIDE_BRANCH_DIRECTIONS) {
			@Override
			void generate(
				Consumer<BlockPos> trunkSetter,
				List<FoliagePlacer.FoliageAttachment> attachments,
				Directional direction,
				RandomSource random,
				BlockPos.MutableBlockPos logPos
			) {
				final Direction side = ((Direction) direction).getClockWise();
				generateSquareAndReturn(trunkSetter, logPos, side);
				generateSquareAndReturn(trunkSetter, logPos.move(direction.getStep()).move(Direction.UP), side);

				final Vec3i foliagePosOffset = random.nextBoolean() ? side.getStep() : Vec3i.ZERO;
				attachments.add(
					new FoliagePlacer.FoliageAttachment(
						logPos.move(foliagePosOffset).move(Direction.UP).immutable(),
						0,
						false
					)
				);
			}
		},
		STRAIGHT_LONG(SIDE_BRANCH_DIRECTIONS) {
			@Override
			void generate(
				Consumer<BlockPos> trunkSetter,
				List<FoliagePlacer.FoliageAttachment> attachments,
				Directional direction,
				RandomSource random,
				BlockPos.MutableBlockPos logPos
			) {
				final Direction side = setupStraightSide(logPos, direction, random);
				BranchType.generateStraightBase(trunkSetter, logPos, direction, side);
				trunkSetter.accept(logPos.move(direction.getStep()).move(Direction.UP));
				attachments.add(
					new FoliagePlacer.FoliageAttachment(
						logPos.move(Direction.UP).immutable(),
						0,
						false
					)
				);
			}
		},
		CORNER(CORNER_BRANCH_DIRECTIONS) {
			@Override
			void generate(
				Consumer<BlockPos> trunkSetter,
				List<FoliagePlacer.FoliageAttachment> attachments,
				Directional direction,
				RandomSource random,
				BlockPos.MutableBlockPos logPos
			) {
				final int sideX = direction.getStepX();
				final int sideZ = direction.getStepZ();

				// FIRST STEP
				generateCornerBaseAndReturn(trunkSetter, logPos, sideX, sideZ);
				trunkSetter.accept(logPos.move(Direction.UP));

				// SECOND STEP
				trunkSetter.accept(logPos.move(direction.getStep()));
				trunkSetter.accept(logPos.move(Direction.UP));

				attachments.add(
					new FoliagePlacer.FoliageAttachment(
						logPos.move(Direction.UP).immutable(),
						0,
						false
					)
				);
			}
		},
		CORNER_THICK(CORNER_BRANCH_DIRECTIONS) {
			@Override
			void generate(
				Consumer<BlockPos> trunkSetter,
				List<FoliagePlacer.FoliageAttachment> attachments,
				Directional direction,
				RandomSource random,
				BlockPos.MutableBlockPos logPos
			) {
				generateThickCornerBase(trunkSetter, logPos, direction, false);
				attachments.add(
					new FoliagePlacer.FoliageAttachment(
						logPos.move(Direction.UP).immutable(),
						0,
						false
					)
				);
			}
		},
		CORNER_LONG(CORNER_BRANCH_DIRECTIONS) {
			@Override
			void generate(
				Consumer<BlockPos> trunkSetter,
				List<FoliagePlacer.FoliageAttachment> attachments,
				Directional direction,
				RandomSource random,
				BlockPos.MutableBlockPos logPos
			) {
				generateThickCornerBase(trunkSetter, logPos, direction, true);

				// THIRD STEP
				trunkSetter.accept(logPos.move(Direction.UP).move(direction.getStep()));
				trunkSetter.accept(logPos.move(Direction.UP));

				attachments.add(
					new FoliagePlacer.FoliageAttachment(
						logPos.move(Direction.UP).immutable(),
						0,
						false
					)
				);
			}
		},
		CORNER_LONG_TALL(CORNER_BRANCH_DIRECTIONS) {
			@Override
			void generate(
				Consumer<BlockPos> trunkSetter,
				List<FoliagePlacer.FoliageAttachment> attachments,
				Directional direction,
				RandomSource random,
				BlockPos.MutableBlockPos logPos
			) {
				generateThickCornerBase(trunkSetter, logPos, direction, false);

				// THIRD STEP
				trunkSetter.accept(logPos.move(direction.getStep()));
				trunkSetter.accept(logPos.move(Direction.UP));

				attachments.add(
					new FoliagePlacer.FoliageAttachment(
						logPos.move(Direction.UP).immutable(),
						0,
						false
					)
				);
			}
		};

		private final Set<Directional> validDirections;

		BranchType(Set<Directional> validDirections) {
			this.validDirections = validDirections;
		}

		private static Direction setupStraightSide(BlockPos.MutableBlockPos logPos, Directional directional, RandomSource random) {
			Direction side = ((Direction) directional).getClockWise();
			if (random.nextBoolean()) {
				logPos.move(side);
				side = side.getOpposite();
			}
			return side;
		}

		private static void generateStraightBase(
			Consumer<BlockPos> trunkSetter,
			BlockPos.MutableBlockPos logPos,
			Directional direction,
			Direction side
		) {
			// FIRST STEP
			trunkSetter.accept(logPos);
			trunkSetter.accept(logPos.move(Direction.DOWN));
			trunkSetter.accept(logPos.move(Direction.UP).move(side));

			// SECOND STEP
			trunkSetter.accept(logPos.move(side.getOpposite()).move(direction.getStep()));
			trunkSetter.accept(logPos.move(Direction.UP));
		}

		private static void generateSquareAndReturn(
			Consumer<BlockPos> trunkSetter,
			BlockPos.MutableBlockPos logPos,
			Direction side
		) {
			trunkSetter.accept(logPos.move(Direction.DOWN));
			trunkSetter.accept(logPos.move(side));
			trunkSetter.accept(logPos.move(Direction.UP));
			trunkSetter.accept(logPos.move(side.getOpposite()));
		}

		private static void generateCornerBaseAndReturn(
			Consumer<BlockPos> trunkSetter,
			BlockPos.MutableBlockPos logPos,
			int sideX,
			int sideZ
		) {
			trunkSetter.accept(logPos.move(-sideX, 0, 0));
			trunkSetter.accept(logPos.move(0, -1, 0));

			trunkSetter.accept(logPos.move(sideX, 0, -sideZ));
			trunkSetter.accept(logPos.move(0, 1, 0));

			trunkSetter.accept(logPos.move(0, 0, sideZ));
		}

		private static void generateThickCornerBase(
			Consumer<BlockPos> trunkSetter,
			BlockPos.MutableBlockPos logPos,
			Directional direction,
			boolean isSecondStepCube
		) {
			final int sideX = direction.getStepX();
			final int sideZ = direction.getStepZ();

			// FIRST STEP
			generateCornerBaseAndReturn(trunkSetter, logPos, sideX, sideZ);
			trunkSetter.accept(logPos.move(Direction.DOWN));

			// SECOND STEP
			generateCornerBaseAndReturn(trunkSetter, logPos.move(direction.getStep()).move(Direction.UP, 2), sideX, sideZ);
			trunkSetter.accept(logPos.move(!isSecondStepCube ? Direction.UP : Direction.DOWN));
		}

		abstract void generate(
			Consumer<BlockPos> trunkSetter,
			List<FoliagePlacer.FoliageAttachment> attachments,
			Directional direction,
			RandomSource random,
			BlockPos.MutableBlockPos logPos
		);

		static BranchType select(Directional directional, RandomSource random) {
			return Util.getRandom(
				Arrays.stream(values())
					.filter(type -> type.validDirections.contains(directional))
					.toList(),
				random
			);
		}
	}

	private record BranchInfo(int startHeight, BranchType type, Directional direction) {
		private static final int MAX_CREATION_ATTEMPTS = 5;

		private static BranchInfo create(int treeHeight, RandomSource random) {
			final Directional chosenDirection = Util.getRandom(ALL_BRANCH_DIRECTIONS, random);
			return new BranchInfo(
				random.nextIntBetweenInclusive(treeHeight / 2, treeHeight - 1),
				BranchType.select(chosenDirection, random),
				chosenDirection
			);
		}

		static @Nullable BranchInfo tryCreate(int treeHeight, RandomSource random, List<BranchInfo> branches) {
			for (int i = 0; i < MAX_CREATION_ATTEMPTS; i++) {
				final BranchInfo branch = create(treeHeight, random);
				if (branch.isValid(branches)) return branch;
			}

			return null;
		}

		boolean isValid(List<BranchInfo> branches) {
			return branches.stream()
				.filter(branch -> branch.direction == this.direction)
				.noneMatch(branch -> branch.startHeight <= this.startHeight + 2 && branch.startHeight >= this.startHeight - 2);
		}

		void generate(
			Consumer<BlockPos> trunkSetter,
			List<FoliagePlacer.FoliageAttachment> attachments,
			RandomSource random,
			BlockPos origin,
			BlockPos.MutableBlockPos branchPos,
			BlockPos.MutableBlockPos logPos
		) {
			this.moveToStartingPosition(origin, branchPos);
			logPos.set(branchPos);
			this.type.generate(trunkSetter, attachments, this.direction, random, logPos);
		}

		private void moveToStartingPosition(BlockPos origin, BlockPos.MutableBlockPos branchPos) {
			branchPos.setWithOffset(origin, 0, this.startHeight, 0).move(this.direction.getStep());

			if (!this.isCorner()) {
				if (((Direction) this.direction).getAxis() == Direction.Axis.Y) throw new IllegalStateException("Branch direction cannot be vertical!");

				//      N(-)
				//     X  |
				//W(-) ---|--- E(+)
				//        |
				//      S(+)
				if (this.direction == Direction.NORTH) return;

				//      N(-)
				//        |  X
				//W(-) ---|--- E(+)
				//        |
				//      S(+)
				if (this.direction == Direction.EAST) {
					branchPos.move(Direction.EAST);
					return;
				}

				//      N(-)
				//        |
				//W(-) ---|--- E(+)
				//        |  X
				//      S(+)
				if (this.direction == Direction.SOUTH) {
					branchPos.move(Direction.EAST).move(Direction.SOUTH);
					return;
				}

				//      N(-)
				//        |
				//W(-) ---|--- E(+)
				//     X  |
				//      S(+)
				if (this.direction == Direction.WEST) {
					branchPos.move(Direction.SOUTH);
					return;
				}
			}

			final int stepX = this.direction.getStepX();
			final int stepZ = this.direction.getStepZ();
			if (stepX == 0 || stepZ == 0) throw new IllegalStateException("Branch direction is either not composite or not horizontal!");

			branchPos.move(Math.clamp(stepX, 0, 1), 0, Math.clamp(stepZ, 0, 1));
		}

		private boolean isCorner() {
			return !(this.direction instanceof Direction);
		}
	}
}
