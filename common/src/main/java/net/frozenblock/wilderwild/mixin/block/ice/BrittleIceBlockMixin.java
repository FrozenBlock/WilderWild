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

package net.frozenblock.wilderwild.mixin.block.ice;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.frozenblock.wilderwild.config.WWBlockConfig;
import net.frozenblock.wilderwild.registry.WWCriteria;
import net.frozenblock.wilderwild.tag.WWEntityTypeTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.BiasedToBottomInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BrittleIceBlock;
import net.minecraft.world.level.block.CrackingIceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BrittleIceBlock.class)
public abstract class BrittleIceBlockMixin extends CrackingIceBlock {
	@Unique
	private static final IntProvider WILDERWILD$SHEET_SHATTER_INCREMENT = BiasedToBottomInt.of(1, 3);
	@Unique
	private static final IntProvider WILDERWILD$SHEET_SHATTER_DELAY = UniformInt.of(1, 5);

	protected BrittleIceBlockMixin(Properties properties) {
		super(properties);
	}

	@ModifyReturnValue(method = "getTickDelay", at = @At("RETURN"))
	private static int wilderWild$modifyCrackTickDelay(int original) {
		return Math.clamp(WWBlockConfig.BRITTLE_ICE_CRACK_DELAY.get(), 5, 40);
	}

	@WrapOperation(
		method = "stepOn",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/BrittleIceBlock;slightlyMelt(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;)Z"
		)
	)
	public boolean wilderWild$skipMeltIfSheetShattering(
		BrittleIceBlock instance,
		BlockState blockState,
		Level level,
		BlockPos neighbourPos,
		Operation<Boolean> original
	) {
		if (WWBlockConfig.BRITTLE_ICE_SHATTERING_SPREADS.get()) return false;
		return original.call(instance, blockState, level, neighbourPos);
	}

	@Override
	protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		super.tick(state, level, pos, random);
		if (WWBlockConfig.BRITTLE_ICE_SHATTERING_SPREADS.get()) {
			if (this.slightlyMelt(state, level, pos)) this.propagateMelting(level, pos, random);
		}
	}

	@Override
	protected void affectNeighborsAfterRemoval(BlockState state, ServerLevel level, BlockPos pos, boolean movedByPiston) {
		if (WWBlockConfig.BRITTLE_ICE_SHATTERING_SPREADS.get()) {
			final BlockPos.MutableBlockPos neighborPos = new BlockPos.MutableBlockPos();
			for (Direction direction : Direction.values()) {
				neighborPos.setWithOffset(pos, direction);
				final BlockState neighbor = level.getBlockState(neighborPos);
				if (!neighbor.is(BrittleIceBlock.class.cast(this))) continue;

				final int age = neighbor.getValue(AGE);
				if (age < MAX_AGE) {
					level.setBlock(
						neighborPos,
						neighbor.setValue(
							AGE,
							Math.min(age + WILDERWILD$SHEET_SHATTER_INCREMENT.sample(level.getRandom()), MAX_AGE)
						),
						UPDATE_CLIENTS
					);
				}

				level.scheduleTick(neighborPos.immutable(), neighbor.getBlock(), WILDERWILD$SHEET_SHATTER_DELAY.sample(level.getRandom()));
			}
		}

		super.affectNeighborsAfterRemoval(state, level, pos, movedByPiston);
	}

	@Override
	public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, double fallDistance) {
		super.fallOn(level, state, pos, entity, fallDistance);

		if (!WWBlockConfig.BRITTLE_ICE_SHATTERS_ON_FALL.get() || entity.is(WWEntityTypeTags.FRAGILE_ICE_DOES_NOT_CRACK_ON_FALL)) return;

		if (fallDistance >= 4F) {
			level.destroyBlock(pos, false);
			if (entity instanceof ServerPlayer serverPlayer) WWCriteria.BRITTLE_ICE_FALL_ONTO_AND_BREAK.get().trigger(serverPlayer);
		}
	}

	@Override
	protected void onProjectileHit(Level level, BlockState state, BlockHitResult hitResult, Projectile projectile) {
		super.onProjectileHit(level, state, hitResult, projectile);

		if (!WWBlockConfig.BRITTLE_ICE_SHATTERS_FROM_PROJECTILE.get() || projectile.is(WWEntityTypeTags.BRITTLE_ICE_DOES_NOT_CRACK_PROJECTILES)) return;

		final double velocity = projectile.getDeltaMovement().length();
		if (velocity >= 1.6D) level.destroyBlock(hitResult.getBlockPos(), false);
	}
}
