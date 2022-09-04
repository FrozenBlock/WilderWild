package net.frozenblock.wilderwild.world.gen.trunk;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.frozenblock.wilderwild.WilderWild;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

import java.util.List;
import java.util.OptionalInt;
import java.util.function.BiConsumer;

public class JuniperTrunkPlacer extends TrunkPlacer {
    public static final Codec<JuniperTrunkPlacer> CODEC = RecordCodecBuilder.create((instance) ->
            trunkPlacerParts(instance).apply(instance, JuniperTrunkPlacer::new));

    public JuniperTrunkPlacer(int i, int j, int k) {
        super(i, j, k);
    }

    protected TrunkPlacerType<?> type() {
        return WilderWild.JUNIPER_TRUNK_PLACER;
    }


    public List<FoliagePlacer.FoliageAttachment> placeTrunk(LevelSimulatedReader levelSimulatedReader, BiConsumer<BlockPos, BlockState> biConsumer, RandomSource randomSource, int i, BlockPos blockPos, TreeConfiguration treeConfiguration) {
        setDirtAt(levelSimulatedReader, biConsumer, randomSource, blockPos.below(), treeConfiguration);
        List<FoliagePlacer.FoliageAttachment> list = Lists.newArrayList();
        Direction direction = Direction.Plane.HORIZONTAL.getRandomDirection(randomSource);
        int j = i - randomSource.nextInt(6) - 1;
        int k = 4 - randomSource.nextInt(3);
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        int l = blockPos.getX();
        int m = blockPos.getZ();
        OptionalInt optionalInt = OptionalInt.empty();

        int o;
        for(int n = 0; n < i; ++n) {
            o = blockPos.getY() + n;
            if (n >= j && k > 0) {
                l += direction.getStepX();
                m += direction.getStepZ();
                --k;
            }

            if (this.placeLog(levelSimulatedReader, biConsumer, randomSource, mutableBlockPos.set(l, o, m), treeConfiguration)) {
                optionalInt = OptionalInt.of(o + 1);
            }
        }

        if (optionalInt.isPresent()) {
            list.add(new FoliagePlacer.FoliageAttachment(new BlockPos(l, optionalInt.getAsInt(), m), 1, false));
        }

        l = blockPos.getX();
        m = blockPos.getZ();
        Direction direction2 = Direction.Plane.HORIZONTAL.getRandomDirection(randomSource);
        if (direction2 != direction) {
            o = j - randomSource.nextInt(2) - 3;
            int p = 4 + randomSource.nextInt(3);
            optionalInt = OptionalInt.empty();

            for(int q = o; q < i && p > 0; --p) {
                if (q >= 1) {
                    int r = blockPos.getY() + q;
                    l += direction2.getStepX();
                    m += direction2.getStepZ();
                    if (this.placeLog(levelSimulatedReader, biConsumer, randomSource, mutableBlockPos.set(l, r, m), treeConfiguration)) {
                        optionalInt = OptionalInt.of(r + 1);
                    }
                }

                ++q;
            }

            if (optionalInt.isPresent()) {
                list.add(new FoliagePlacer.FoliageAttachment(new BlockPos(l, optionalInt.getAsInt(), m), 0, false));
            }
        }

        return list;
    }
}


