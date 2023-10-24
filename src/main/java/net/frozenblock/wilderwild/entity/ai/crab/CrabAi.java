/*
 * Copyright 2023 FrozenBlock
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

package net.frozenblock.wilderwild.entity.ai.crab;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import net.frozenblock.wilderwild.entity.Crab;
import net.frozenblock.wilderwild.registry.RegisterEntities;
import net.frozenblock.wilderwild.registry.RegisterMemoryModuleTypes;
import net.frozenblock.wilderwild.tag.WilderItemTags;
import net.minecraft.util.Unit;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.behavior.AnimalMakeLove;
import net.minecraft.world.entity.ai.behavior.AnimalPanic;
import net.minecraft.world.entity.ai.behavior.BabyFollowAdult;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.behavior.DoNothing;
import net.minecraft.world.entity.ai.behavior.EraseMemoryIf;
import net.minecraft.world.entity.ai.behavior.FollowTemptation;
import net.minecraft.world.entity.ai.behavior.LookAtTargetSink;
import net.minecraft.world.entity.ai.behavior.MeleeAttack;
import net.minecraft.world.entity.ai.behavior.MoveToTargetSink;
import net.minecraft.world.entity.ai.behavior.RandomStroll;
import net.minecraft.world.entity.ai.behavior.RunOne;
import net.minecraft.world.entity.ai.behavior.SetEntityLookTarget;
import net.minecraft.world.entity.ai.behavior.SetWalkTargetFromAttackTargetIfTargetOutOfReach;
import net.minecraft.world.entity.ai.behavior.StartAttacking;
import net.minecraft.world.entity.ai.behavior.StopAttackingIfTargetInvalid;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.behavior.warden.ForceUnmount;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class CrabAi {
	private static final float SPEED_MODIFIER = 1F;
	private static final float FOLLOWING_ADULT_SPEED_MODIFIER = 1.2F;
	private static final float CHASING_SPEED_MODIFIER = 1.3F;
	private static final UniformInt ADULT_FOLLOW_RANGE = UniformInt.of(5, 16);
	private static final int DIGGING_DURATION = Crab.DIG_LENGTH_IN_TICKS;
	private static final int EMERGE_DURATION = Crab.EMERGE_LENGTH_IN_TICKS;
	public static final double UNDERGROUND_PLAYER_RANGE = Crab.UNDERGROUND_PLAYER_RANGE;

	private static final BehaviorControl<Crab> DIG_COOLDOWN_SETTER = BehaviorBuilder.create(instance -> instance.group(instance.registered(MemoryModuleType.DIG_COOLDOWN)).apply(instance, memoryAccessor -> (world, crab, l) -> {
		if (instance.tryGet(memoryAccessor).isPresent()) {
			memoryAccessor.setWithExpiry(Unit.INSTANCE, getRandomDigCooldown(crab));
		}
		return true;
	}));
	private static final BehaviorControl<Crab> EMERGE_COOLDOWN_SETTER = BehaviorBuilder.create(instance -> instance.group(instance.registered(MemoryModuleType.DIG_COOLDOWN)).apply(instance, memoryAccessor -> (world, crab, l) -> {
		if (instance.tryGet(memoryAccessor).isPresent()) {
			memoryAccessor.setWithExpiry(Unit.INSTANCE, getRandomEmergeCooldown(crab));
		}
		return true;
	}));

	public static void updateActivity(@NotNull Crab crab) {
		crab.getBrain().setActiveActivityToFirstValid(List.of(Activity.EMERGE, Activity.DIG, Activity.HIDE, Activity.FIGHT, Activity.IDLE));
	}

	@NotNull
	public static Brain<Crab> makeBrain(@NotNull Crab crab, @NotNull Brain<Crab> brain) {
		addCoreActivity(brain);
		initEmergeActivity(brain);
		initDiggingActivity(brain);
		initHideActivity(brain);
		addIdleActivity(brain);
		addFightActivity(crab, brain);
		brain.setCoreActivities(Set.of(Activity.CORE));
		brain.setDefaultActivity(Activity.IDLE);
		brain.useDefaultActivity();
		return brain;
	}

	private static void addCoreActivity(@NotNull Brain<Crab> brain) {
		brain.addActivity(
			Activity.CORE,
			0,
			ImmutableList.of(
				new AnimalPanic(1.65F, pathfinderMob -> (pathfinderMob.getLastHurtByMob() != null && pathfinderMob.isBaby()) || (pathfinderMob.isFreezing() || pathfinderMob.isOnFire()) && !((Crab)pathfinderMob).isDiggingOrEmerging()),
				new LookAtTargetSink(45, 90),
				new MoveToTargetSink()
			)
		);
	}

	private static void initEmergeActivity(@NotNull Brain<Crab> brain) {
		brain.addActivityAndRemoveMemoryWhenStopped(
			Activity.EMERGE,
			5,
			ImmutableList.of(
				new CrabEmerge<>(EMERGE_DURATION)
			),
			MemoryModuleType.IS_EMERGING
		);
	}

	private static void initDiggingActivity(@NotNull Brain<Crab> brain) {
		brain.addActivityWithConditions(
			Activity.DIG,
			ImmutableList.of(
				Pair.of(0, new ForceUnmount()),
				Pair.of(1, new CrabDig<>(DIGGING_DURATION))
			),
			Set.of(
				Pair.of(RegisterMemoryModuleTypes.FIRST_BRAIN_TICK, MemoryStatus.VALUE_PRESENT),
				Pair.of(MemoryModuleType.DIG_COOLDOWN, MemoryStatus.VALUE_ABSENT),
				Pair.of(RegisterMemoryModuleTypes.IS_UNDERGROUND, MemoryStatus.VALUE_ABSENT),
				Pair.of(MemoryModuleType.IS_PANICKING, MemoryStatus.VALUE_ABSENT),
				Pair.of(MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_ABSENT),
				Pair.of(RegisterMemoryModuleTypes.IS_PLAYER_NEARBY, MemoryStatus.VALUE_ABSENT),
				Pair.of(RegisterMemoryModuleTypes.CAN_DIG, MemoryStatus.VALUE_PRESENT)
			)
		);
	}

	private static void initHideActivity(@NotNull Brain<Crab> brain) {
		brain.addActivityWithConditions(
			Activity.HIDE,
			ImmutableList.of(
				Pair.of(0, CrabTryToEmerge.create()),
				Pair.of(1, CrabHeal.create())
			),
			Set.of(
				Pair.of(MemoryModuleType.DIG_COOLDOWN, MemoryStatus.VALUE_PRESENT),
				Pair.of(RegisterMemoryModuleTypes.IS_UNDERGROUND, MemoryStatus.VALUE_PRESENT)
			)
		);
	}

	private static void addIdleActivity(@NotNull Brain<Crab> brain) {
		brain.addActivity(
			Activity.IDLE,
			ImmutableList.of(
				Pair.of(1, new AnimalMakeLove(RegisterEntities.CRAB, 0.8F)),
				Pair.of(
					2,
					new RunOne<>(
						List.of(
							Pair.of(new FollowTemptation(CrabAi::getSpeedModifier), 1),
							Pair.of(BabyFollowAdult.create(ADULT_FOLLOW_RANGE, CrabAi::getSpeedModifierFollowingAdult), 1)
						)
					)
				),
				Pair.of(3, StartAttacking.create(CrabAi::findNearestValidAttackTarget)),
				Pair.of(
					4,
					new RunOne<>(
						Map.of(MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT, RegisterMemoryModuleTypes.FIRST_BRAIN_TICK, MemoryStatus.VALUE_PRESENT),
						List.of(
							Pair.of(RandomStroll.swim(1F), 2),
							Pair.of(RandomStroll.stroll(1F), 2),
							Pair.of(new DoNothing(30, 100), 1),
							Pair.of(BehaviorBuilder.triggerIf(Entity::isInWaterOrBubble), 5),
							Pair.of(BehaviorBuilder.triggerIf(Entity::onGround), 5)
						)
					)
				)
			)
		);
	}

	private static void addFightActivity(@NotNull Crab crab, @NotNull Brain<Crab> brain) {
		brain.addActivityAndRemoveMemoryWhenStopped(
			Activity.FIGHT,
			10,
			ImmutableList.of(
				DIG_COOLDOWN_SETTER,
				StopAttackingIfTargetInvalid.create(
					livingEntity -> !crab.canTargetEntity(livingEntity), CrabAi::onTargetInvalid, true
				),
				SetEntityLookTarget.create(livingEntity -> isTarget(crab, livingEntity), (float) crab.getAttributeValue(Attributes.FOLLOW_RANGE)),
				SetWalkTargetFromAttackTargetIfTargetOutOfReach.create(CrabAi::getSpeedModifierChasing),
				MeleeAttack.create(20),
				EraseMemoryIf.create(BehaviorUtils::isBreeding, MemoryModuleType.ATTACK_TARGET)
			),
			MemoryModuleType.ATTACK_TARGET
		);
	}

	private static boolean isTarget(@NotNull Crab crab, @NotNull LivingEntity livingEntity) {
		return crab.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).filter(livingEntity2 -> livingEntity2 == livingEntity).isPresent();
	}

	private static float getSpeedModifierChasing(@Nullable LivingEntity livingEntity) {
		return CHASING_SPEED_MODIFIER;
	}

	private static float getSpeedModifierFollowingAdult(LivingEntity entity) {
		return FOLLOWING_ADULT_SPEED_MODIFIER;
	}

	private static float getSpeedModifier(LivingEntity entity) {
		return SPEED_MODIFIER;
	}

	private static void onTargetInvalid(@NotNull Crab crab, @NotNull LivingEntity target) {
		if (crab.getTarget() == target) {
			crab.getBrain().eraseMemory(MemoryModuleType.ATTACK_TARGET);
		}
		crab.endNavigation();
	}

	@NotNull
	private static Optional<? extends LivingEntity> findNearestValidAttackTarget(@NotNull Crab crab) {
		return crab.getBrain().getMemory(MemoryModuleType.NEAREST_ATTACKABLE);
	}

	public static void wasHurtBy(@NotNull Crab crab, LivingEntity target) {
		if (crab.canTargetEntity(target)) {
			if (!Sensor.isEntityAttackableIgnoringLineOfSight(crab, target)) {
				return;
			}
			if (BehaviorUtils.isOtherTargetMuchFurtherAwayThanCurrentAttackTarget(crab, target, 4.0)) {
				return;
			}

			setAngerTarget(crab, target);
			broadcastAngerTarget(crab, target);
		}
	}

	public static void setAngerTarget(@NotNull Crab crab, LivingEntity target) {
		if (crab.isBaby()) {
			return;
		}
		if (!Sensor.isEntityAttackableIgnoringLineOfSight(crab, target)) {
			return;
		}
		if (crab.getBrain().checkMemory(RegisterMemoryModuleTypes.IS_UNDERGROUND, MemoryStatus.VALUE_PRESENT)) {
			clearDigCooldown(crab);
		}
		crab.getBrain().eraseMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
		crab.getBrain().setMemory(MemoryModuleType.ATTACK_TARGET, target);
	}

	public static void broadcastAngerTarget(@NotNull Crab crab, LivingEntity target) {
		Optional<List<Crab>> nearbyCrabs = getNearbyCrabs(crab);
		nearbyCrabs.ifPresent(crabs -> crabs.forEach(listedCrab -> setAngerTargetIfCloserThanCurrent(listedCrab, target)));
	}

	private static void setAngerTargetIfCloserThanCurrent(@NotNull Crab crab, LivingEntity currentTarget) {
		Optional<LivingEntity> optional = getAngerTarget(crab);
		LivingEntity livingEntity = BehaviorUtils.getNearestTarget(crab, optional, currentTarget);
		if (optional.isPresent() && optional.get() == livingEntity) {
			return;
		}
		setAngerTarget(crab, livingEntity);
	}

	@NotNull
	private static Optional<LivingEntity> getAngerTarget(@NotNull Crab crab) {
		return crab.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET);
	}

	@NotNull
	private static Optional<List<Crab>> getNearbyCrabs(@NotNull Crab crab) {
		return crab.getBrain().getMemory(RegisterMemoryModuleTypes.NEARBY_CRABS);
	}

	public static void setDigCooldown(@NotNull Crab crab) {
		crab.getBrain().setMemoryWithExpiry(
			MemoryModuleType.DIG_COOLDOWN,
			Unit.INSTANCE,
			getRandomDigCooldown(crab)
		);
	}

	public static void clearDigCooldown(@NotNull Crab crab) {
		crab.getBrain().eraseMemory(MemoryModuleType.DIG_COOLDOWN);
	}

	public static boolean isUnderground(@NotNull Crab crab) {
		return crab.getBrain().hasMemoryValue(RegisterMemoryModuleTypes.IS_UNDERGROUND);
	}

	@NotNull
	public static Ingredient getTemptations() {
		return Ingredient.of(WilderItemTags.CRAB_TEMPT_ITEMS);
	}

	public static int getRandomDigCooldown(@NotNull LivingEntity entity) {
		return entity.getRandom().nextInt(800, 2400);
	}

	public static int getRandomEmergeCooldown(@NotNull LivingEntity entity) {
		return entity.getRandom().nextInt(800, 2400);
	}
}
