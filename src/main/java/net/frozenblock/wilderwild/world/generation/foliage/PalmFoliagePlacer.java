package net.frozenblock.wilderwild.world.generation.foliage;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.frozenblock.wilderwild.WilderWild;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;

import java.util.function.BiConsumer;

public class PalmFoliagePlacer extends FoliagePlacer {
    public static final Codec<PalmFoliagePlacer> CODEC = RecordCodecBuilder.create((instance) -> {
        return foliagePlacerParts(instance).apply(instance, PalmFoliagePlacer::new);
    });

    public PalmFoliagePlacer(IntProvider intProvider, IntProvider intProvider2) {
        super(intProvider, intProvider2);
    }

    protected FoliagePlacerType<?> type() {
        return WilderWild.PALM_FOLIAGE_PLACER;
    }

    protected void createFoliage(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> blockSetter, RandomSource random, TreeConfiguration config, int i, FoliagePlacer.FoliageAttachment foliageAttachment, int j, int k, int l) {
		BlockPos blockPos = foliageAttachment.pos().above(l);
		int radius = this.radius.sample(random);
		double divRad = radius / 1.3;
		double minus = (Math.PI * radius) / (radius * radius);
		double rot = (random.nextDouble() * 0.15) * (random.nextBoolean() ? 1 : -1);

		for (Direction direction1 : Direction.values()) {
			if (direction1 != Direction.DOWN && direction1 != Direction.UP) {
				double dirX = direction1.getStepX() < 0 ? direction1.getStepX() + rot : direction1.getStepX() - rot;
				double dirZ = direction1.getStepZ() < 0 ? direction1.getStepZ() + rot : direction1.getStepZ() - rot;
				for (int r = 0; r < radius; r++) {
					double yOffset = (2 * (Math.sin(((Math.PI * r) / divRad) - minus))) - 1.3;
					placeLeavesAtPos(level, blockSetter, random, config, blockPos, (dirX * r), yOffset, (dirZ * r));
				}

				for (Direction direction2 : Direction.values()) {
					if (direction2 != Direction.DOWN && direction2 != Direction.UP && direction2 != direction1) {
						double dirX2 = direction2.getStepX() < 0 ? direction2.getStepX() + rot : direction2.getStepX() - rot;
						double dirZ2 = direction2.getStepZ() < 0 ? direction2.getStepZ() + rot : direction2.getStepZ() - rot;
						for (int r = 0; r < radius; r++) {
							double yOffset = (2 * (Math.sin(((Math.PI * r) / divRad) - minus))) - 1.3;
							placeLeavesAtPos(level, blockSetter, random, config, blockPos, (dirX * r) + (dirX2 * r), yOffset, (dirZ * r) + (dirZ2 * r));
						}
					}
				}
			}
		}
    }

	public static void placeLeavesAtPos(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> blockSetter, RandomSource random, TreeConfiguration config, BlockPos pos, double offX, double offY, double offZ) {
		BlockPos placePos = pos.offset(offX, offY, offZ);
		tryPlaceLeaf(level, blockSetter, random, config, placePos);
		if (shouldPlaceAbove(offX)) {
			tryPlaceLeaf(level, blockSetter, random, config, placePos.offset(1, 0, 0));
		}
		if (shouldPlaceBelow(offX)) {
			tryPlaceLeaf(level, blockSetter, random, config,  placePos.offset(-1, 0, 0));
		}
		if (shouldPlaceAbove(offY)) {
			tryPlaceLeaf(level, blockSetter, random, config, placePos.above());
		}
		if (shouldPlaceBelow(offY)) {
			tryPlaceLeaf(level, blockSetter, random, config, placePos.below());
		}
		if (shouldPlaceAbove(offZ)) {
			tryPlaceLeaf(level, blockSetter, random, config, placePos.offset(0, 0, 1));
		}
		if (shouldPlaceBelow(offZ)) {
			tryPlaceLeaf(level, blockSetter, random, config,  placePos.offset(0, 0, -1));
		}
	}

	public static boolean shouldPlaceAbove(double d) {
		return d > 0.55 && d < 0.85;
	}

	public static boolean shouldPlaceBelow(double d) {
		return d < 0.45 && d > 0.15;
	}

    public int foliageHeight(RandomSource randomSource, int i, TreeConfiguration treeConfiguration) {
        return 0;
    }

    protected boolean shouldSkipLocation(RandomSource randomSource, int i, int j, int k, int l, boolean bl) {
        if (j == 0) {
            return (i > 1 || k > 1) && i != 0 && k != 0;
        } else {
            return i == l && k == l && l > 0;
        }
    }
}
