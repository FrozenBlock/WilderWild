package net.frozenblock.wilderwild.world.generation.trunk;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Function3;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.function.BiConsumer;
import net.frozenblock.wilderwild.WilderWild;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

public class FancyDarkOakTrunkPlacer extends TrunkPlacer {
	public static final Codec<FancyDarkOakTrunkPlacer> CODEC = RecordCodecBuilder.create((instance) -> trunkPlacerParts(instance).and(instance.group(Codec.floatRange(0.0F, 1.0F).fieldOf("place_branch_chance").forGetter((trunkPlacer) -> trunkPlacer.logChance), IntProvider.NON_NEGATIVE_CODEC.fieldOf("max_logs").forGetter((trunkPlacer) -> trunkPlacer.maxLogs), IntProvider.NON_NEGATIVE_CODEC.fieldOf("log_height_from_top").forGetter((trunkPlacer) -> trunkPlacer.logHeightFromTop), IntProvider.NON_NEGATIVE_CODEC.fieldOf("extra_branch_length").forGetter((trunkPlacer) -> trunkPlacer.extraBranchLength))).apply(instance, FancyDarkOakTrunkPlacer::new));

	private final IntProvider extraBranchLength;
	private final float logChance;
	private final IntProvider maxLogs;
	private final IntProvider logHeightFromTop;

	public FancyDarkOakTrunkPlacer(int baseHeight, int firstRandomHeight, int secondRandomHeight, float logChance, IntProvider maxLogs, IntProvider logHeightFromTop, IntProvider extraBranchLength) {
		super(baseHeight, firstRandomHeight, secondRandomHeight);
		this.logChance = logChance;
		this.maxLogs = maxLogs;
		this.logHeightFromTop = logHeightFromTop;
		this.extraBranchLength = extraBranchLength;
	}

	protected TrunkPlacerType<?> type() {
		return WilderWild.FANCY_DARK_OAK_TRUNK_PLACER;
	}

	public List<FoliagePlacer.FoliageAttachment> placeTrunk(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> replacer, RandomSource random, int height, BlockPos startPos, TreeConfiguration config) {
		List<FoliagePlacer.FoliageAttachment> list = Lists.newArrayList();
		BlockPos blockPos = startPos.below();
		setDirtAt(level, replacer, random, blockPos, config);
		setDirtAt(level, replacer, random, blockPos.east(), config);
		setDirtAt(level, replacer, random, blockPos.south(), config);
		setDirtAt(level, replacer, random, blockPos.south().east(), config);
		Direction direction = Direction.Plane.HORIZONTAL.getRandomDirection(random);
		int i = height - random.nextInt(4);
		int j = 2 - random.nextInt(3);
		int k = startPos.getX();
		int l = startPos.getY();
		int m = startPos.getZ();
		int n = k;
		int o = m;
		int p = l + height - 1;
		BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
		int maxLogs = this.maxLogs.sample(random);
		int logHeightFromTop = this.logHeightFromTop.sample(random);
		int placedLogs = 0;

		int q;
		int r;
		for (q = 0; q < height; ++q) {
			if (q >= i && j > 0) {
				n += direction.getStepX();
				o += direction.getStepZ();
				--j;
			}

			r = l + q;
			BlockPos blockPos2 = new BlockPos(n, r, o);
			if (TreeFeature.isAirOrLeaves(level, blockPos2)) {
				this.placeLog(level, replacer, random, blockPos2, config);
				this.placeLog(level, replacer, random, blockPos2.east(), config);
				this.placeLog(level, replacer, random, blockPos2.south(), config);
				this.placeLog(level, replacer, random, blockPos2.east().south(), config);
			}
		}

		list.add(new FoliagePlacer.FoliageAttachment(new BlockPos(n, p, o), 0, true));

		for (q = -1; q <= 2; ++q) {
			for (r = -1; r <= 2; ++r) {
				if ((q < 0 || q > 1 || r < 0 || r > 1) && random.nextInt(3) <= 0) {
					int s = random.nextInt(3) + 2;

					for (int t = 0; t < s; ++t) {
						this.placeLog(level, replacer, random, new BlockPos(k + q, p - t - 1, m + r), config);
					}

					list.add(new FoliagePlacer.FoliageAttachment(new BlockPos(n + q, p, o + r), 0, false));
				}
			}
		}
		for (i = 0; i < height; ++i) {
			j = startPos.getY() + i;
			if (this.placeLog(level, replacer, random, mutable.set(startPos.getX(), j, startPos.getZ()), config)
					&& i < height - 1 && random.nextFloat() < this.logChance && placedLogs < maxLogs && (height - 6) - i <= logHeightFromTop) {
				Direction direction2 = Direction.Plane.HORIZONTAL.getRandomDirection(random);
				this.generateExtraBranch(level, replacer, random, config, mutable, j, direction2, this.extraBranchLength.sample(random));
				++placedLogs;
			}
			list.add(new FoliagePlacer.FoliageAttachment(new BlockPos(i, j, q), 0, false));
		}
		return list;
	}

	private void generateExtraBranch(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> replacer, RandomSource random, TreeConfiguration config, BlockPos.MutableBlockPos pos, int yOffset, Direction direction, int length) {
		int j = pos.getX();
		int k = pos.getZ();
		for (int l = 0; l < length; ++l) {
			j += direction.getStepX();
			k += direction.getStepZ();
			if (TreeFeature.validTreePos(level, pos.set(j, yOffset, k))) {
				if (config.trunkProvider.getState(random, pos.set(j, yOffset, k)).hasProperty(BlockStateProperties.AXIS)) {
					Direction.Axis axis = direction.getStepX() != 0 ? Direction.Axis.X : Direction.Axis.Z;
					replacer.accept(pos.set(j, yOffset, k), config.trunkProvider.getState(random, pos.set(j, yOffset, k)).setValue(BlockStateProperties.AXIS, axis));
				} else {
					this.placeLog(level, replacer, random, pos.set(j, yOffset, k), config);
				}
			}
		}
	}
}
