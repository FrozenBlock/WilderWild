/*
 * Copyright 2025 FrozenBlock
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

package net.frozenblock.wilderwild.entity.effect;

import com.google.common.collect.Sets;
import java.util.Iterator;
import java.util.Set;
import java.util.function.ToIntFunction;
import net.frozenblock.wilderwild.networking.packet.WWScorchingFirePlacePacket;
import net.frozenblock.wilderwild.registry.WWParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class ScorchingMobEffect extends MobEffect {
	private final float chanceToScorch;
	private final ToIntFunction<RandomSource> fireDurationInSeconds;
	private final ToIntFunction<RandomSource> maxFires;

	public ScorchingMobEffect(
		MobEffectCategory type,
		int color,
		float chanceToScorch,
		ToIntFunction<RandomSource> fireDurationInSeconds,
		ToIntFunction<RandomSource> maxFires
	) {
		super(type, color, WWParticleTypes.SCORCHING_FLAME);
		this.chanceToScorch = chanceToScorch;
		this.fireDurationInSeconds = fireDurationInSeconds;
		this.maxFires = maxFires;
	}

	@Override
	public void onMobHurt(ServerLevel level, @NotNull LivingEntity livingEntity, int i, DamageSource damageSource, float f) {
		RandomSource random = livingEntity.getRandom();
		if (random.nextFloat() <= this.chanceToScorch && damageSource.getDirectEntity() != null) {
			int fireTicks = this.fireDurationInSeconds.applyAsInt(random);
			damageSource.getDirectEntity().igniteForSeconds(fireTicks);
		}
	}

	@Override
	public void onMobRemoved(ServerLevel level, LivingEntity entity, int amplifier, Entity.RemovalReason reason) {
		if (reason == Entity.RemovalReason.KILLED && (entity instanceof Player || level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING))) {
			this.spawnFireRandomlyAround(entity.level(), entity.getRandom(), entity.getOnPos());
		}
	}

	private void spawnFireRandomlyAround(Level level, RandomSource random, BlockPos pos) {
		if (level instanceof ServerLevel serverLevel) {
			Set<BlockPos> set = Sets.newHashSet();
			int i = this.maxFires.applyAsInt(random);
			Iterator<BlockPos> poses = BlockPos.randomInCube(random, 15, pos, 1).iterator();

			BlockPos blockPos;
			while (poses.hasNext()) {
				blockPos = poses.next();
				BlockPos blockPos2 = blockPos.below();
				BlockState blockState = level.getBlockState(blockPos);
				if (!set.contains(blockPos)
					&& blockState.canBeReplaced()
					&& blockState.getFluidState().isEmpty()
					&& level.getBlockState(blockPos2).isFaceSturdy(level, blockPos2, Direction.UP)
				) {
					set.add(blockPos.immutable());
					if (set.size() >= i) break;
				}
			}

			poses = set.iterator();
			while (poses.hasNext()) {
				blockPos = poses.next();
				BlockState fireState;
				if (level.getBlockState(blockPos.below()).is(BlockTags.SOUL_FIRE_BASE_BLOCKS)) {
					fireState = Blocks.SOUL_FIRE.defaultBlockState();
				} else {
					fireState = Blocks.FIRE.defaultBlockState();
				}
				if (fireState.canSurvive(level, blockPos)) {
					level.setBlockAndUpdate(blockPos, fireState);
					WWScorchingFirePlacePacket.sendToAll(serverLevel, blockPos);
				}
			}
		}
	}

}
