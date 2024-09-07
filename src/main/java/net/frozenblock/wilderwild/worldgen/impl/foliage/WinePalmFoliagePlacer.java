/*
 * Copyright 2023-2024 FrozenBlock
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

package net.frozenblock.wilderwild.worldgen.impl.foliage;

import com.mojang.datafixers.Products;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.frozenblock.lib.math.api.AdvancedMath;
import net.frozenblock.wilderwild.registry.WWFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import java.text.DecimalFormat;

public class WinePalmFoliagePlacer extends FoliagePlacer {
	public static final MapCodec<WinePalmFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec((instance) ->
		winePalmCodec(instance).apply(instance, WinePalmFoliagePlacer::new)
	);
	private static final double SURROUNDING_LEAF_THRESHOLD = 0D;
	public final IntProvider frondCount;

	public WinePalmFoliagePlacer(@NotNull IntProvider offset, @NotNull IntProvider frondCount) {
		super(ConstantInt.of(2), offset);
		this.frondCount = frondCount;
	}

	@Contract("_ -> new")
	protected static <P extends WinePalmFoliagePlacer> Products.@NotNull P2<RecordCodecBuilder.Mu<P>, IntProvider, IntProvider> winePalmCodec(
		RecordCodecBuilder.@NotNull Instance<P> instance
	) {
		return instance.group(IntProvider.codec(0, 16).fieldOf("offset").forGetter(placer -> placer.offset))
			.and(IntProvider.codec(0, 16).fieldOf("frond_count").forGetter(placer -> placer.frondCount));
	}

	public static void placeLeavesAtPos(
		@NotNull LevelSimulatedReader level,
		@NotNull FoliageSetter blockSetter,
		@NotNull RandomSource random,
		@NotNull TreeConfiguration config,
		@NotNull Vec3 pos
	) {
		BlockPos basePos = BlockPos.containing(pos);
		BlockPos.MutableBlockPos mutableBlockPos = basePos.mutable();
		Vec3 offsetVec = pos.subtract(Vec3.atCenterOf(basePos));
		tryPlaceLeaf(level, blockSetter, random, config, mutableBlockPos);
		if (shouldPlaceAbove(offsetVec.x)) tryPlaceLeaf(level, blockSetter, random, config, mutableBlockPos.move(1, 0, 0));
		if (shouldPlaceBelow(offsetVec.x)) tryPlaceLeaf(level, blockSetter, random, config, mutableBlockPos.set(basePos).move(-1, 0, 0));
		if (shouldPlaceAbove(offsetVec.y)) tryPlaceLeaf(level, blockSetter, random, config, mutableBlockPos.set(basePos).move(0, 1, 0));
		if (shouldPlaceBelow(offsetVec.y)) tryPlaceLeaf(level, blockSetter, random, config, mutableBlockPos.set(basePos).move(0, -1, 0));
		if (shouldPlaceAbove(offsetVec.z)) tryPlaceLeaf(level, blockSetter, random, config, mutableBlockPos.set(basePos).move(0, 0, 1));
		if (shouldPlaceBelow(offsetVec.z)) tryPlaceLeaf(level, blockSetter, random, config, mutableBlockPos.set(basePos).move(0, 0, -1));
	}

	public static boolean shouldPlaceAbove(double d) {
		return d > 0.5D + SURROUNDING_LEAF_THRESHOLD;
	}

	public static boolean shouldPlaceBelow(double d) {
		return d < 0.5D - SURROUNDING_LEAF_THRESHOLD;
	}

	@Override
	@NotNull
	protected FoliagePlacerType<?> type() {
		return WWFeatures.WINE_PALM_FOLIAGE_PLACER;
	}

	@Override
	protected void createFoliage(
		@NotNull LevelSimulatedReader level,
		@NotNull FoliageSetter blockSetter,
		@NotNull RandomSource random,
		@NotNull TreeConfiguration config,
		int i,
		@NotNull FoliageAttachment foliageAttachment,
		int j,
		int k,
		int l
	) {
		BlockPos blockPos = foliageAttachment.pos().above(l);
		Vec3 origin = Vec3.atCenterOf(blockPos);
		double radius = 1.25D;
		double minus = (Math.PI * radius) / (radius * radius);
		int fronds = this.frondCount.sample(random);
		double rotAngle = 360D / (double) fronds;
		double angle = random.nextDouble() * 360D;

		for (int a = 0; a < fronds; a++) {
			Vec3 offsetPos = AdvancedMath.rotateAboutXZ(origin, 1D, angle + (((random.nextDouble() * rotAngle) * 0.35D) * (random.nextBoolean() ? 1D : -1D)));
			double dirX = offsetPos.x - origin.x;
			double dirZ = offsetPos.z - origin.z;
			for (double r = 0D; r < radius; r += 0.2D) {
				double yOffset = (2D * (Math.sin((Math.PI * (r - 0.1D)) / radius) - minus)) + (4.2D * (minus * 0.4D));
				Vec3 placementVec = Vec3.atCenterOf(blockPos).add(dirX * r, yOffset, dirZ * r);
				placeLeavesAtPos(level, blockSetter, random, config, placementVec);
			}
			angle += rotAngle;
		}
	}

	@Override
	public int foliageHeight(@NotNull RandomSource randomSource, int i, @NotNull TreeConfiguration treeConfiguration) {
		return 0;
	}

	@Override
	protected boolean shouldSkipLocation(@NotNull RandomSource random, int localX, int localY, int localZ, int range, boolean large) {
		return false;
	}
}
