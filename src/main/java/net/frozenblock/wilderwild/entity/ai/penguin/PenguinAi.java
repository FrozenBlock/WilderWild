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

package net.frozenblock.wilderwild.entity.ai.penguin;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.Optional;
import net.frozenblock.wilderwild.entity.Penguin;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.registry.WWEntityTypes;
import net.frozenblock.wilderwild.registry.WWMemoryModuleTypes;
import net.frozenblock.wilderwild.registry.WWSensorTypes;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.AnimalMakeLove;
import net.minecraft.world.entity.ai.behavior.AnimalPanic;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.behavior.CountDownCooldownTicks;
import net.minecraft.world.entity.ai.behavior.DoNothing;
import net.minecraft.world.entity.ai.behavior.FollowTemptation;
import net.minecraft.world.entity.ai.behavior.LookAtTargetSink;
import net.minecraft.world.entity.ai.behavior.MoveToTargetSink;
import net.minecraft.world.entity.ai.behavior.RandomLookAround;
import net.minecraft.world.entity.ai.behavior.RunOne;
import net.minecraft.world.entity.ai.behavior.SetEntityLookTargetSometimes;
import net.minecraft.world.entity.ai.behavior.StartAttacking;
import net.minecraft.world.entity.ai.behavior.StopBeingAngryIfTargetDead;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.level.GameRules;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PenguinAi {
	private static final float SPEED_MULTIPLIER_WHEN_PANICKING = 1.75F;
	private static final float SPEED_MULTIPLIER_WHEN_ATTACKING = 1.75F;
	private static final float SPEED_MULTIPLIER_WHEN_IDLING = 1.0F;
	private static final float SPEED_MULTIPLIER_WHEN_TEMPTED = 1.25F;
	private static final float SPEED_MULTIPLIER_WHEN_FOLLOWING_ADULT = 1.25F;
	private static final float SPEED_MULTIPLIER_WHEN_MAKING_LOVE = 0.8F;
	private static final UniformInt ADULT_FOLLOW_RANGE = UniformInt.of(5, 16);
	private static final ImmutableList<SensorType<? extends Sensor<? super Penguin>>> SENSOR_TYPES = ImmutableList.of(
		SensorType.NEAREST_LIVING_ENTITIES,
		SensorType.HURT_BY,
		WWSensorTypes.OSTRICH_TEMPTATIONS,
		SensorType.NEAREST_ADULT,
		SensorType.NEAREST_PLAYERS,
		WWSensorTypes.PENGUIN_SPECIFIC_SENSOR
	);
	private static final ImmutableList<MemoryModuleType<?>> MEMORY_TYPES = ImmutableList.of(
		MemoryModuleType.IS_PANICKING,
		MemoryModuleType.HURT_BY,
		MemoryModuleType.HURT_BY_ENTITY,
		MemoryModuleType.WALK_TARGET,
		MemoryModuleType.LOOK_TARGET,
		MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
		MemoryModuleType.PATH,
		MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES,
		MemoryModuleType.TEMPTING_PLAYER,
		MemoryModuleType.TEMPTATION_COOLDOWN_TICKS,
		MemoryModuleType.GAZE_COOLDOWN_TICKS,
		MemoryModuleType.IS_TEMPTED,
		MemoryModuleType.BREED_TARGET,
		MemoryModuleType.NEAREST_VISIBLE_ADULT,
		MemoryModuleType.IS_PREGNANT,
		MemoryModuleType.ANGRY_AT,
		MemoryModuleType.UNIVERSAL_ANGER,
		MemoryModuleType.ATTACK_TARGET,
		MemoryModuleType.NEAREST_ATTACKABLE,
		MemoryModuleType.NEAREST_LIVING_ENTITIES,
		MemoryModuleType.NEAREST_PLAYERS,
		MemoryModuleType.NEAREST_VISIBLE_PLAYER,
		MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER,
		WWMemoryModuleTypes.NEARBY_PENGUINS,
		MemoryModuleType.ATTACK_COOLING_DOWN,
		MemoryModuleType.HOME
	);

	@NotNull
	public static Brain.Provider<Penguin> brainProvider() {
		return Brain.provider(MEMORY_TYPES, SENSOR_TYPES);
	}

	@NotNull
	public static Brain<?> makeBrain(@NotNull Penguin penguin, @NotNull Brain<Penguin> brain) {
		initCoreActivity(brain);
		initIdleActivity(brain);
		initFightActivity(penguin, brain);
		brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
		brain.setDefaultActivity(Activity.IDLE);
		brain.useDefaultActivity();
		return brain;
	}

	private static void initCoreActivity(@NotNull Brain<Penguin> brain) {
		brain.addActivity(
			Activity.CORE,
			0,
			ImmutableList.of(
				new AnimalPanic<>(SPEED_MULTIPLIER_WHEN_PANICKING),
				new PenguinLayEgg(WWBlocks.OSTRICH_EGG),
				//new PenguinRunAroundLikeCrazy(1.5F),
				new LookAtTargetSink(45, 90),
				new MoveToTargetSink(),
				new CountDownCooldownTicks(MemoryModuleType.TEMPTATION_COOLDOWN_TICKS),
				new CountDownCooldownTicks(MemoryModuleType.GAZE_COOLDOWN_TICKS),
				StopBeingAngryIfTargetDead.create()
			)
		);
	}

	private static void initIdleActivity(@NotNull Brain<Penguin> brain) {
		brain.addActivity(
			Activity.IDLE,
			ImmutableList.of(
				Pair.of(0, SetEntityLookTargetSometimes.create(EntityType.PLAYER, 6.0F, UniformInt.of(30, 60))),
				Pair.of(1, new AnimalMakeLove(WWEntityTypes.PENGUIN, SPEED_MULTIPLIER_WHEN_MAKING_LOVE, 2)),
				Pair.of(
					2,
					new RunOne<>(
						ImmutableList.of(
							Pair.of(new FollowTemptation(livingEntity -> SPEED_MULTIPLIER_WHEN_TEMPTED, livingEntity -> livingEntity.isBaby() ? 2.5 : 3.5), 1)
							//Pair.of(BehaviorBuilder.triggerIf(Predicate.not(Penguin::refuseToMove), BabyFollowAdult.create(ADULT_FOLLOW_RANGE, SPEED_MULTIPLIER_WHEN_FOLLOWING_ADULT)), 1)
						)
					)
				),
				Pair.of(3, StartAttacking.create(PenguinAi::findNearestValidAttackTarget)),
				Pair.of(4, new RandomLookAround(UniformInt.of(150, 250), 80.0F, -70.0F, 70.0F)),
				Pair.of(
					5,
					new RunOne<>(
						ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT),
						ImmutableList.of(
							//Pair.of(BehaviorBuilder.triggerIf(Predicate.not(Penguin::refuseToMove), RandomStroll.stroll(SPEED_MULTIPLIER_WHEN_IDLING)), 1),
							//Pair.of(BehaviorBuilder.triggerIf(Predicate.not(Penguin::refuseToMove), SetWalkTargetFromLookTarget.create(SPEED_MULTIPLIER_WHEN_IDLING, 3)), 1),
							Pair.of(new DoNothing(30, 60), 1)
						)
					)
				)
			)
		);
	}

	private static void initFightActivity(@NotNull Penguin penguin, @NotNull Brain<Penguin> brain) {
		/*
		brain.addActivityAndRemoveMemoryWhenStopped(
			Activity.FIGHT,
			10,
			ImmutableList.of(
				StopAttackingIfTargetInvalid.create(
					livingEntity -> !penguin.canTargetEntity(livingEntity), PenguinAi::onTargetInvalid, true
				),
				SetEntityLookTarget.create(livingEntity -> isTarget(penguin, livingEntity), (float) penguin.getAttributeValue(Attributes.FOLLOW_RANGE)),
				SetWalkTargetFromAttackTargetIfTargetOutOfReach.create(PenguinAi::getSpeedModifierChasing),
				PenguinMeleeAttack.create(5),
				EraseMemoryIf.create(BehaviorUtils::isBreeding, MemoryModuleType.ATTACK_TARGET)
			),
			MemoryModuleType.ATTACK_TARGET
		);
		 */
	}

	public static void updateActivity(@NotNull Penguin penguin) {
		penguin.getBrain().setActiveActivityToFirstValid(ImmutableList.of(Activity.FIGHT, Activity.IDLE));
	}

	private static boolean isTarget(@NotNull Penguin penguin, @NotNull LivingEntity livingEntity) {
		return penguin.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).filter(livingEntity2 -> livingEntity2 == livingEntity).isPresent();
	}

	private static void onTargetInvalid(@NotNull Penguin penguin, @NotNull LivingEntity target) {
		if (penguin.getTarget() == target) {
			removeAttackAndAngerTarget(penguin);
		}
		penguin.getNavigation().stop();
	}

	@NotNull
	private static Optional<? extends LivingEntity> findNearestValidAttackTarget(@NotNull Penguin penguin) {
		Brain<Penguin> brain = penguin.getBrain();
		Optional<LivingEntity> optional = BehaviorUtils.getLivingEntityFromUUIDMemory(penguin, MemoryModuleType.ANGRY_AT);
		if (optional.isPresent() && Sensor.isEntityAttackableIgnoringLineOfSight(penguin, optional.get())) {
			return optional;
		} else {
			Optional<? extends LivingEntity> optional2;
			if (brain.hasMemoryValue(MemoryModuleType.UNIVERSAL_ANGER)) {
				optional2 = brain.getMemory(MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER);
				if (optional2.isPresent()) {
					return optional2;
				}
			}
			return brain.getMemory(MemoryModuleType.NEAREST_ATTACKABLE);
		}
	}

	public static void wasHurtBy(@NotNull Penguin penguin, LivingEntity target) {
		/*
		if (penguin.canTargetEntity(target)) {
			if (!Sensor.isEntityAttackableIgnoringLineOfSight(penguin, target)) {
				return;
			}
			if (BehaviorUtils.isOtherTargetMuchFurtherAwayThanCurrentAttackTarget(penguin, target, 4.0)) {
				return;
			}

			if (penguin.isBaby()) {
				if (Sensor.isEntityAttackableIgnoringLineOfSight(penguin, target)) {
					broadcastAngerTarget(penguin, target);
				}
				return;
			}

			if (target.getType() == EntityType.PLAYER && penguin.level().getGameRules().getBoolean(GameRules.RULE_UNIVERSAL_ANGER)) {
				setAngerTargetToNearestTargetablePlayerIfFound(penguin, target);
				broadcastUniversalAnger(penguin);
			} else {
				setAngerTarget(penguin, target);
				broadcastAngerTarget(penguin, target);
			}
		}
		 */
	}

	public static void setAngerTarget(@NotNull Penguin penguin, LivingEntity target) {
		if (penguin.isBaby()) {
			return;
		}
		if (!Sensor.isEntityAttackableIgnoringLineOfSight(penguin, target)) {
			return;
		}
		penguin.getBrain().eraseMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
		penguin.getBrain().setMemory(MemoryModuleType.ATTACK_TARGET, target);
		penguin.getBrain().setMemoryWithExpiry(MemoryModuleType.ANGRY_AT, target.getUUID(), 600L);
		if (target.getType() == EntityType.PLAYER && penguin.level().getGameRules().getBoolean(GameRules.RULE_UNIVERSAL_ANGER)) {
			penguin.getBrain().setMemoryWithExpiry(MemoryModuleType.UNIVERSAL_ANGER, true, 600L);
		}
	}

	private static void broadcastUniversalAnger(Penguin penguinEntity) {
		Optional<List<Penguin>> nearbyPenguines = getNearbyPenguines(penguinEntity);
		nearbyPenguines.ifPresent(
			penguines -> penguines.forEach(
				penguin -> getNearestVisibleTargetablePlayer(penguin).ifPresent(
					player -> setAngerTarget(penguin, player)
				)
			)
		);
	}

	public static void broadcastAngerTarget(@NotNull Penguin crab, LivingEntity target) {
		Optional<List<Penguin>> nearbyPenguines = getNearbyPenguines(crab);
		nearbyPenguines.ifPresent(penguines -> penguines.forEach(listedPenguin -> setAngerTargetIfCloserThanCurrent(listedPenguin, target)));
	}

	private static void setAngerTargetIfCloserThanCurrent(@NotNull Penguin penguin, LivingEntity currentTarget) {
		Optional<LivingEntity> optional = getAngerTarget(penguin);
		LivingEntity livingEntity = BehaviorUtils.getNearestTarget(penguin, optional, currentTarget);
		if (optional.isPresent() && optional.get() == livingEntity) {
			return;
		}
		setAngerTarget(penguin, livingEntity);
	}

	private static void setAngerTargetToNearestTargetablePlayerIfFound(Penguin penguin, LivingEntity currentTarget) {
		Optional<Player> optional = getNearestVisibleTargetablePlayer(penguin);
		if (optional.isPresent()) {
			setAngerTarget(penguin, optional.get());
		} else {
			setAngerTarget(penguin, currentTarget);
		}

	}

	@NotNull
	private static Optional<List<Penguin>> getNearbyPenguines(@NotNull Penguin penguin) {
		return penguin.getBrain().getMemory(WWMemoryModuleTypes.NEARBY_PENGUINS);
	}

	public static Optional<Player> getNearestVisibleTargetablePlayer(@NotNull Penguin penguin) {
		return penguin.getBrain().hasMemoryValue(MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER) ? penguin.getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER) : Optional.empty();
	}

	@NotNull
	private static Optional<LivingEntity> getAngerTarget(@NotNull Penguin penguin) {
		return penguin.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET);
	}

	public static void removeAttackAndAngerTarget(@NotNull Penguin penguin) {
		Brain<Penguin> brain = penguin.getBrain();
		brain.eraseMemory(MemoryModuleType.ATTACK_TARGET);
		brain.eraseMemory(MemoryModuleType.ANGRY_AT);
		brain.eraseMemory(MemoryModuleType.UNIVERSAL_ANGER);
	}

	private static float getSpeedModifierChasing(@Nullable LivingEntity livingEntity) {
		return SPEED_MULTIPLIER_WHEN_ATTACKING;
	}

	/*
	public static Ingredient getTemptations() {
		return Penguin.TEMPTATION_ITEM;
	}
	 */

}
