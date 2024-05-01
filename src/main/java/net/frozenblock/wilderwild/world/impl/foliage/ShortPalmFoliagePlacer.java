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

package net.frozenblock.wilderwild.world.impl.foliage;

import com.mojang.datafixers.Products;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.frozenblock.lib.math.api.AdvancedMath;
import net.frozenblock.wilderwild.registry.RegisterFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class ShortPalmFoliagePlacer extends FoliagePlacer {
	public static final Codec<ShortPalmFoliagePlacer> CODEC = RecordCodecBuilder.create((instance) ->
		shortPalmCodec(instance).apply(instance, ShortPalmFoliagePlacer::new)
	);

	public final BlockStateProvider crownState;

	public ShortPalmFoliagePlacer(IntProvider radius, IntProvider offset, BlockStateProvider crownState) {
		super(radius, offset);
		this.crownState = crownState;
	}

	@Contract("_ -> new")
	protected static <P extends ShortPalmFoliagePlacer> Products.@NotNull P3<RecordCodecBuilder.Mu<P>, IntProvider, IntProvider, BlockStateProvider> shortPalmCodec(RecordCodecBuilder.Instance<P> builder) {
		return foliagePlacerParts(builder)
			.and(
				BlockStateProvider.CODEC.fieldOf("crown_state").forGetter(placer -> placer.crownState)
			);
	}

	@Override
	@NotNull
	protected FoliagePlacerType<?> type() {
		return RegisterFeatures.SHORT_PALM_FOLIAGE_PLACER;
	}

	@Override
	protected void createFoliage(@NotNull LevelSimulatedReader level, @NotNull FoliageSetter blockSetter, @NotNull RandomSource random, @NotNull TreeConfiguration config, int i, @NotNull FoliageAttachment foliageAttachment, int j, int k, int l) {
		BlockPos blockPos = foliageAttachment.pos().above(l);
		BlockPos belowPos = blockPos.below();
		blockSetter.set(belowPos, this.crownState.getState(random, belowPos));
		Vec3 origin = new Vec3(blockPos.getX(), blockPos.getY(), blockPos.getZ());
		int radius = this.radius.sample(random);
		int fronds = random.nextInt(15, 30);
		double rotAngle = 360 / (double) fronds;
		double angle = random.nextDouble() * 360;

		for (int a = 0; a < fronds; a++) {
			Vec3 offsetPos = AdvancedMath.rotateAboutXZ(origin, 1, angle + ((random.nextDouble() * 20) * (random.nextBoolean() ? 1 : -1)));
			double dirX = offsetPos.x - origin.x;
			double dirZ = offsetPos.z - origin.z;
			double dirY = (random.nextDouble() - 0.5) * 2;
			for (int r = 0; r < radius; r++) {
				PalmFoliagePlacer.placeLeavesAtPos(level, blockSetter, random, config, blockPos, (dirX * r), (dirY * r), (dirZ * r));
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
		if (localY == 0) {
			return (localX > 1 || localZ > 1) && localX != 0 && localZ != 0;
		} else {
			return localX == range && localZ == range && range > 0;
		}
	}
}
