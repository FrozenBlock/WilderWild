package net.frozenblock.wilderwild.world.gen.foliage;

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
		for (Direction direction1 : Direction.values()) {
			if (direction1 != Direction.DOWN && direction1 != Direction.UP) {
				int dirX = direction1.getStepX();
				int dirZ = direction1.getStepZ();
				for (int r = 0; r < radius; r++) {
					double liftBy = Math.sin((Math.PI * r) / radius) - r;
					tryPlaceLeaf(level, blockSetter, random, config, blockPos.offset(dirX * r, liftBy, dirZ * r));
				}
				for (Direction direction2 : Direction.values()) {
					if (direction2 != Direction.DOWN && direction2 != Direction.UP && direction2 != direction1) {
						int dirX2 = direction2.getStepX();
						int dirZ2 = direction2.getStepZ();
						for (int r = 0; r < radius; r++) {
							double liftBy = Math.sin((Math.PI * r) / radius) - r;
							tryPlaceLeaf(level, blockSetter, random, config, blockPos.offset((dirX * r) + (dirX2 * r), liftBy, (dirZ * r) + (dirZ2 * r)));
						}
					}
				}
			}
		}
		/*
        this.placeLeavesRow(levelSimulatedReader, biConsumer, randomSource, treeConfiguration, blockPos, k + foliageAttachment.radiusOffset(), -1 - j, bl);
        this.placeLeavesRow(levelSimulatedReader, biConsumer, randomSource, treeConfiguration, blockPos, k - 1, -j, bl);
        this.placeLeavesRow(levelSimulatedReader, biConsumer, randomSource, treeConfiguration, blockPos, k + foliageAttachment.radiusOffset() - 1, 0, bl);
		 */
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
