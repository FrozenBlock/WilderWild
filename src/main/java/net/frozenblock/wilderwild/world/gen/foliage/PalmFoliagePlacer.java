package net.frozenblock.wilderwild.world.gen.foliage;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.frozenblock.wilderwild.WilderWild;
import net.minecraft.core.BlockPos;
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

    protected void createFoliage(LevelSimulatedReader levelSimulatedReader, BiConsumer<BlockPos, BlockState> biConsumer, RandomSource randomSource, TreeConfiguration treeConfiguration, int i, FoliagePlacer.FoliageAttachment foliageAttachment, int j, int k, int l) {
       BlockPos basePos = foliageAttachment.pos();
       //Inner Ring
        tryPlaceLeaf(levelSimulatedReader, biConsumer, randomSource, treeConfiguration, basePos);
        tryPlaceLeaf(levelSimulatedReader, biConsumer, randomSource, treeConfiguration, basePos.above());
        tryPlaceLeaf(levelSimulatedReader, biConsumer, randomSource, treeConfiguration, basePos.north());
        tryPlaceLeaf(levelSimulatedReader, biConsumer, randomSource, treeConfiguration, basePos.south());
        tryPlaceLeaf(levelSimulatedReader, biConsumer, randomSource, treeConfiguration, basePos.east());
        tryPlaceLeaf(levelSimulatedReader, biConsumer, randomSource, treeConfiguration, basePos.west());
        tryPlaceLeaf(levelSimulatedReader, biConsumer, randomSource, treeConfiguration, basePos.north().east());
        tryPlaceLeaf(levelSimulatedReader, biConsumer, randomSource, treeConfiguration, basePos.south().west());
        tryPlaceLeaf(levelSimulatedReader, biConsumer, randomSource, treeConfiguration, basePos.east().south());
        tryPlaceLeaf(levelSimulatedReader, biConsumer, randomSource, treeConfiguration, basePos.west().north());
       //Offshoots
        tryPlaceLeaf(levelSimulatedReader, biConsumer, randomSource, treeConfiguration, basePos.north(2));
        tryPlaceLeaf(levelSimulatedReader, biConsumer, randomSource, treeConfiguration, basePos.north(2).below());
        tryPlaceLeaf(levelSimulatedReader, biConsumer, randomSource, treeConfiguration, basePos.north(3).below());
        tryPlaceLeaf(levelSimulatedReader, biConsumer, randomSource, treeConfiguration, basePos.south(2));
        tryPlaceLeaf(levelSimulatedReader, biConsumer, randomSource, treeConfiguration, basePos.south(2).below());
        tryPlaceLeaf(levelSimulatedReader, biConsumer, randomSource, treeConfiguration, basePos.south(3).below());
        tryPlaceLeaf(levelSimulatedReader, biConsumer, randomSource, treeConfiguration, basePos.east(2));
        tryPlaceLeaf(levelSimulatedReader, biConsumer, randomSource, treeConfiguration, basePos.east(2).below());
        tryPlaceLeaf(levelSimulatedReader, biConsumer, randomSource, treeConfiguration, basePos.east(3).below());
        tryPlaceLeaf(levelSimulatedReader, biConsumer, randomSource, treeConfiguration, basePos.west(2));
        tryPlaceLeaf(levelSimulatedReader, biConsumer, randomSource, treeConfiguration, basePos.west(2).below());
        tryPlaceLeaf(levelSimulatedReader, biConsumer, randomSource, treeConfiguration, basePos.west(3).below());
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
