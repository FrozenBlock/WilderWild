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

package net.frozenblock.wilderwild.world.generation.foliage;

import com.mojang.datafixers.Products;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.function.BiConsumer;
import net.frozenblock.lib.math.api.AdvancedMath;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class PalmFoliagePlacer extends FoliagePlacer {
	public static final Codec<PalmFoliagePlacer> CODEC = RecordCodecBuilder.create((instance) ->
			palmCodec(instance).apply(instance, PalmFoliagePlacer::new)
	);

	protected static <P extends PalmFoliagePlacer> Products.P3<RecordCodecBuilder.Mu<P>, IntProvider, IntProvider, IntProvider> palmCodec(RecordCodecBuilder.Instance<P> builder) {
		return foliagePlacerParts(builder).and((IntProvider.codec(0, 16).fieldOf("fronds")).forGetter(placer -> placer.fronds));
	}

	private static final double SURROUNDING_LEAF_THRESHOLD = 0.175;

	public PalmFoliagePlacer(IntProvider intProvider, IntProvider intProvider2, IntProvider fronds) {
		super(intProvider, intProvider2);
		this.fronds = fronds;
	}

	public final IntProvider fronds;

	protected FoliagePlacerType<?> type() {
		return WilderWild.PALM_FOLIAGE_PLACER;
	}

	protected void createFoliage(@NotNull LevelSimulatedReader level, @NotNull BiConsumer<BlockPos, BlockState> blockSetter, @NotNull RandomSource random, @NotNull TreeConfiguration config, int i, FoliageAttachment foliageAttachment, int j, int k, int l) {
		BlockPos blockPos = foliageAttachment.pos().above(l);
		blockSetter.accept(blockPos.below(), RegisterBlocks.PALM_CROWN.defaultBlockState());
		Vec3 origin = new Vec3(blockPos.getX(), blockPos.getY(), blockPos.getZ());
		double minRadius = this.radius.getMinValue();
		double radius = minRadius + ((this.radius.getMaxValue() - minRadius) * random.nextDouble());
		double minus = (Math.PI * radius) / (radius * radius);
		int fronds = this.fronds.sample(random);
		double rotAngle = 360 / (double) fronds;
		double angle = random.nextDouble() * 360;

		for (int a = 0; a < fronds; a++) {
			Vec3 offsetPos = AdvancedMath.rotateAboutXZ(origin, 1, angle + (((random.nextDouble() * rotAngle) * 0.35) * (random.nextBoolean() ? 1 : -1)));
			double dirX = offsetPos.x - origin.x;
			double dirZ = offsetPos.z - origin.z;
			for (double r = 0; r < radius; r += 0.2) {
				double yOffset = (2 * (Math.sin((Math.PI * (r - 0.1)) / radius) - minus)) + (4.2 * (minus * 0.4));
				placeLeavesAtPos(level, blockSetter, random, config, blockPos, (dirX * r), yOffset, (dirZ * r));
			}
			angle += rotAngle;
		}
	}

	public static void placeLeavesAtPos(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> blockSetter, RandomSource random, TreeConfiguration config, BlockPos pos, double offX, double offY, double offZ) {
		BlockPos placePos = pos.offset(offX, offY, offZ);
		tryPlaceLeaf(level, blockSetter, random, config, placePos);
		if (shouldPlaceAbove(offX)) {
			tryPlaceLeaf(level, blockSetter, random, config, placePos.offset(1, 0, 0));
		}
		if (shouldPlaceBelow(offX)) {
			tryPlaceLeaf(level, blockSetter, random, config, placePos.offset(-1, 0, 0));
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
			tryPlaceLeaf(level, blockSetter, random, config, placePos.offset(0, 0, -1));
		}
	}

	public static boolean shouldPlaceAbove(double d) {
		return d > 0.5 + SURROUNDING_LEAF_THRESHOLD;
	}

	public static boolean shouldPlaceBelow(double d) {
		return d < 0.5 - SURROUNDING_LEAF_THRESHOLD;
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
