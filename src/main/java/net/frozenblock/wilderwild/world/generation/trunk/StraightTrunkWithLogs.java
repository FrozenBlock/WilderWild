/*
 * Copyright 2022-2023 FrozenBlock
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

package net.frozenblock.wilderwild.world.generation.trunk;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.function.BiConsumer;
import net.frozenblock.wilderwild.registry.RegisterFeatures;
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
import org.jetbrains.annotations.NotNull;

public class StraightTrunkWithLogs extends TrunkPlacer {
    public static final Codec<StraightTrunkWithLogs> CODEC = RecordCodecBuilder.create((instance) -> trunkPlacerParts(instance).and(instance.group(Codec.floatRange(0.0F, 1.0F).fieldOf("place_branch_chance").forGetter((trunkPlacer) -> trunkPlacer.logChance), IntProvider.NON_NEGATIVE_CODEC.fieldOf("max_logs").forGetter((trunkPlacer) -> trunkPlacer.maxLogs), IntProvider.NON_NEGATIVE_CODEC.fieldOf("log_height_from_top").forGetter((trunkPlacer) -> trunkPlacer.logHeightFromTop), IntProvider.NON_NEGATIVE_CODEC.fieldOf("extra_branch_length").forGetter((trunkPlacer) -> trunkPlacer.extraBranchLength))).apply(instance, StraightTrunkWithLogs::new));

    private final IntProvider extraBranchLength;
    private final float logChance;
    private final IntProvider maxLogs;
    private final IntProvider logHeightFromTop;

    public StraightTrunkWithLogs(int baseHeight, int firstRandomHeight, int secondRandomHeight, float logChance, @NotNull IntProvider maxLogs, @NotNull IntProvider logHeightFromTop, @NotNull IntProvider extraBranchLength) {
        super(baseHeight, firstRandomHeight, secondRandomHeight);
        this.logChance = logChance;
        this.maxLogs = maxLogs;
        this.logHeightFromTop = logHeightFromTop;
        this.extraBranchLength = extraBranchLength;
    }

	@Override
	@NotNull
    protected TrunkPlacerType<?> type() {
        return RegisterFeatures.STRAIGHT_TRUNK_WITH_LOGS_PLACER_TYPE;
    }

	@Override
	@NotNull
    public List<FoliagePlacer.FoliageAttachment> placeTrunk(@NotNull LevelSimulatedReader level, @NotNull BiConsumer<BlockPos, BlockState> replacer, @NotNull RandomSource random, int height, @NotNull BlockPos startPos, @NotNull TreeConfiguration config) {
        setDirtAt(level, replacer, random, startPos.below(), config);
        List<FoliagePlacer.FoliageAttachment> list = Lists.newArrayList();
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
        int maxLogs = this.maxLogs.sample(random);
        int logHeightFromTop = this.logHeightFromTop.sample(random);
        int extraLogs = 0;
        for (int i = 0; i < height; ++i) {
            int j = startPos.getY() + i;
            if (this.placeLog(level, replacer, random, mutable.set(startPos.getX(), j, startPos.getZ()), config)
                    && i < height - 1 && random.nextFloat() < this.logChance && extraLogs < maxLogs && (height - 4) - i <= logHeightFromTop) {
                Direction direction = Direction.Plane.HORIZONTAL.getRandomDirection(random);
                this.generateExtraBranch(level, replacer, random, config, mutable, j, direction, this.extraBranchLength.sample(random));
                ++extraLogs;
            }
            if (i == height - 1) {
                list.add(new FoliagePlacer.FoliageAttachment(mutable.set(startPos.getX(), j + 1, startPos.getZ()), 0, false));
            }
        }

        return list;
    }

    private void generateExtraBranch(@NotNull LevelSimulatedReader level, @NotNull BiConsumer<BlockPos, BlockState> replacer, @NotNull RandomSource random, @NotNull TreeConfiguration config, @NotNull BlockPos.MutableBlockPos pos, int yOffset, @NotNull Direction direction, int length) {
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
