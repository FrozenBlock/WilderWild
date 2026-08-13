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

package net.frozenblock.wilderwild.entity.ai.crab;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import net.frozenblock.wilderwild.entity.Crab;
import net.frozenblock.wilderwild.registry.WWEntityTypes;
import net.frozenblock.wilderwild.registry.WWMemoryModuleTypes;
import net.frozenblock.wilderwild.registry.WWSensorTypes;
import net.frozenblock.wilderwild.tag.WWDamageTypeTags;
import net.frozenblock.wilderwild.tag.WWItemTags;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Unit;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.ActivityData;
import net.minecraft.world.entity.ai.Brain;
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
import net.minecraft.world.entity.ai.behavior.StopBeingAngryIfTargetDead;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.behavior.warden.ForceUnmount;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.gamerules.GameRules;
import org.jetbrains.annotations.Nullable;

public final class CrabAi {
	public static final double UNDERGROUND_PLAYER_RANGE = Crab.UNDERGROUND_PLAYER_RANGE;
	public static final List<SensorType<? extends Sensor<? super Crab>>> SENSORS = List.of(
		SensorType.NEAREST_LIVING_ENTITIES,
		SensorType.NEAREST_PLAYERS,
		SensorType.NEAREST_ADULT,
		SensorType.HURT_BY,
		WWSensorTypes.CRAB_ATTACKABLES.get(),
		WWSensorTypes.CRAB_TEMPTATIONS.get(),
		WWSensorTypes.CRAB_SPECIFIC_SENSOR.get(),
		WWSensorTypes.CRAB_NEARBY_PLAYER_SENSOR.get(),
		WWSensorTypes.CRAB_CAN_DIG_SENSOR.get()
	);
	public static final List<? extends MemoryModuleType<?>> MEMORY_MODULES = List.of(
		WWMemoryModuleTypes.HEAL_COOLDOWN_TICKS.get()
	);
	private static final float SPEED_MODIFIER = 1F;
	private static final float FOLLOWING_ADULT_SPEED_MODIFIER = 1.2F;
	private static final float CHASING_SPEED_MODIFIER = 1.3F;
	private static final UniformInt ADULT_FOLLOW_RANGE = UniformInt.of(5, 16);
	private static final int DIGGING_DURATION = Crab.DIG_LENGTH_IN_TICKS;
	private static final int EMERGE_DURATION = Crab.EMERGE_LENGTH_IN_TICKS;
	private static final BehaviorControl<Crab> DIG_COOLDOWN_SETTER = BehaviorBuilder.create(
		instance -> instance.group(instance.registered(MemoryModuleType.DIG_COOLDOWN))
			.apply(instance, digCooldown -> (level, crab, timestamp) -> {
				if (instance.tryGet(digCooldown).isPresent()) digCooldown.setWithExpiry(Unit.INSTANCE, getRandomDigCooldown(crab));
				return true;
			}));
	private static final BehaviorControl<Crab> HUNTING_COOLDOWN_SETTER = BehaviorBuilder.create(
		instance -> instance.group(instance.registered(MemoryModuleType.HAS_HUNTING_COOLDOWN))
			.apply(instance, memoryAccessor -> (level, crab, timestamp) -> {
				memoryAccessor.setWithExpiry(true, 2400);
				return true;
			}));

	public static void updateActivity(Crab crab) {
		crab.getBrain().setActiveActivityToFirstValid(List.of(Activity.EMERGE, Activity.DIG, Activity.HIDE, Activity.FIGHT, Activity.IDLE));
	}

	public static Brain.Provider<Crab> brainProvider() {
		return Brain.provider(MEMORY_MODULES, SENSORS, CrabAi::getActivities);
	}

	public static List<ActivityData<Crab>> getActivities(final Crab body) {
		return List.of(initCoreActivity(), initEmergeActivity(), initDiggingActivity(), initHideActivity(), initIdleActivity(), initFightActivity(body));
	}

	private static ActivityData<Crab> initCoreActivity() {
		return ActivityData.create(
			Activity.CORE,
			0,
			ImmutableList.of(
				new AnimalPanic<>(1.65F, mob -> {
					if (((Crab) mob).isDiggingOrEmerging()) return WWDamageTypeTags.EMPTY;
					return mob.isBaby() ? DamageTypeTags.PANIC_CAUSES : DamageTypeTags.PANIC_ENVIRONMENTAL_CAUSES;
				}),
				new LookAtTargetSink(45, 90),
				new MoveToTargetSink(),
				StopBeingAngryIfTargetDead.create()
			)
		);
	}

	private static ActivityData<Crab> initEmergeActivity() {
		return ActivityData.create(
			Activity.EMERGE,
			5,
			ImmutableList.of(
				new CrabEmerge<>(EMERGE_DURATION)
			),
			MemoryModuleType.IS_EMERGING
		);
	}

	private static ActivityData<Crab> initDiggingActivity() {
		return ActivityData.create(
			Activity.DIG,
			ImmutableList.of(
				Pair.of(0, new ForceUnmount()),
				Pair.of(1, new CrabDig<>(DIGGING_DURATION))
			),
			Set.of(
				Pair.of(WWMemoryModuleTypes.FIRST_BRAIN_TICK.get(), MemoryStatus.VALUE_PRESENT),
				Pair.of(MemoryModuleType.DIG_COOLDOWN, MemoryStatus.VALUE_ABSENT),
				Pair.of(WWMemoryModuleTypes.IS_UNDERGROUND.get(), MemoryStatus.VALUE_ABSENT),
				Pair.of(MemoryModuleType.IS_PANICKING, MemoryStatus.VALUE_ABSENT),
				Pair.of(MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_ABSENT),
				Pair.of(WWMemoryModuleTypes.IS_PLAYER_NEARBY.get(), MemoryStatus.VALUE_ABSENT),
				Pair.of(WWMemoryModuleTypes.CAN_DIG.get(), MemoryStatus.VALUE_PRESENT),
				Pair.of(MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT)
			)
		);
	}

	private static ActivityData<Crab> initHideActivity() {
		return ActivityData.create(
			Activity.HIDE,
			ImmutableList.of(
				Pair.of(0, CrabTryToEmerge.create()),
				Pair.of(1, CrabHeal.create())
			),
			Set.of(
				Pair.of(WWMemoryModuleTypes.IS_UNDERGROUND.get(), MemoryStatus.VALUE_PRESENT)
			)
		);
	}

	private static ActivityData<Crab> initIdleActivity() {
		return ActivityData.create(
			Activity.IDLE,
			1,
			ImmutableList.of(
				new AnimalMakeLove(WWEntityTypes.CRAB.get(), 0.8F, 2),
				new RunOne<>(
					List.of(
						Pair.of(new FollowTemptation(CrabAi::getSpeedModifier), 1),
						Pair.of(BabyFollowAdult.create(ADULT_FOLLOW_RANGE, FOLLOWING_ADULT_SPEED_MODIFIER), 1)
					)
				),
				StartAttacking.create(CrabAi::findNearestValidAttackTarget),
				new RunOne<>(
					Map.of(MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT, WWMemoryModuleTypes.FIRST_BRAIN_TICK.get(), MemoryStatus.VALUE_PRESENT),
					List.of(
						Pair.of(RandomStroll.swim(1F), 2),
						Pair.of(RandomStroll.stroll(1F), 2),
						Pair.of(new DoNothing(30, 100), 1),
						Pair.of(BehaviorBuilder.triggerIf(Entity::isInWater), 5),
						Pair.of(BehaviorBuilder.triggerIf(Entity::onGround), 5)
					)
				)
			)
		);
	}

	private static ActivityData<Crab> initFightActivity(final Crab body) {
		return ActivityData.create(
			Activity.FIGHT,
			10,
			ImmutableList.of(
				DIG_COOLDOWN_SETTER,
				HUNTING_COOLDOWN_SETTER,
				StopAttackingIfTargetInvalid.create(
					(level, entity) -> !body.canTargetEntity(entity), CrabAi::onTargetInvalid, true
				),
				SetEntityLookTarget.create(entity -> isTarget(body, entity), Crab.MAX_TARGET_DISTANCE),
				SetWalkTargetFromAttackTargetIfTargetOutOfReach.create(CrabAi::getSpeedModifierChasing),
				MeleeAttack.create(20),
				EraseMemoryIf.create(BehaviorUtils::isBreeding, MemoryModuleType.ATTACK_TARGET)
			),
			MemoryModuleType.ATTACK_TARGET
		);
	}

	private static boolean isTarget(Crab body, LivingEntity entity) {
		return body.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).filter(possibleEntity -> possibleEntity == entity).isPresent();
	}

	private static float getSpeedModifierChasing(@Nullable LivingEntity body) {
		return CHASING_SPEED_MODIFIER;
	}

	private static float getSpeedModifier(LivingEntity body) {
		return SPEED_MODIFIER;
	}

	private static void onTargetInvalid(ServerLevel level, Crab body, LivingEntity target) {
		if (body.getTarget() == target) body.getBrain().eraseMemory(MemoryModuleType.ATTACK_TARGET);
		body.endNavigation();
	}

	private static Optional<? extends LivingEntity> findNearestValidAttackTarget(ServerLevel level, Crab body) {
		final Brain<Crab> brain = body.getBrain();
		final Optional<LivingEntity> angryAt = BehaviorUtils.getLivingEntityFromUUIDMemory(body, MemoryModuleType.ANGRY_AT);
		if (angryAt.isPresent() && Sensor.isEntityAttackableIgnoringLineOfSight(level, body, angryAt.get())) return angryAt;
		if (brain.hasMemoryValue(MemoryModuleType.UNIVERSAL_ANGER)) {
			final Optional<? extends LivingEntity> nearestVisibleAttackablePlayer = brain.getMemory(MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER);
			if (nearestVisibleAttackablePlayer.isPresent()) return nearestVisibleAttackablePlayer;
			return brain.getMemory(MemoryModuleType.NEAREST_ATTACKABLE);
		}
		return brain.getMemory(MemoryModuleType.NEAREST_ATTACKABLE);
	}

	public static void wasHurtBy(ServerLevel level, Crab body, LivingEntity target) {
		if (!body.canTargetEntity(target)) return;
		if (!Sensor.isEntityAttackableIgnoringLineOfSight(level, body, target)) return;
		if (BehaviorUtils.isOtherTargetMuchFurtherAwayThanCurrentAttackTarget(body, target, 4.0)) return;

		if (body.isBaby()) {
			if (Sensor.isEntityAttackableIgnoringLineOfSight(level, body, target)) broadcastAngerTarget(level, body, target);
			return;
		}

		if (target.getType() == EntityTypes.PLAYER && level.getGameRules().get(GameRules.UNIVERSAL_ANGER)) {
			setAngerTargetToNearestTargetablePlayerIfFound(level, body, target);
			broadcastUniversalAnger(level, body);
		} else {
			setAngerTarget(level, body, target);
			broadcastAngerTarget(level, body, target);
		}
	}

	public static void setAngerTarget(ServerLevel level, Crab body, LivingEntity target) {
		if (body.isBaby()) return;
		if (!Sensor.isEntityAttackableIgnoringLineOfSight(level, body, target)) return;
		if (body.getBrain().checkMemory(WWMemoryModuleTypes.IS_UNDERGROUND.get(), MemoryStatus.VALUE_PRESENT)) clearDigCooldown(body);

		body.getBrain().eraseMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
		body.getBrain().setMemory(MemoryModuleType.ATTACK_TARGET, target);
		body.getBrain().setMemoryWithExpiry(MemoryModuleType.ANGRY_AT, target.getUUID(), 600L);
		if (target.getType() == EntityTypes.PLAYER && level.getGameRules().get(GameRules.UNIVERSAL_ANGER)) {
			body.getBrain().setMemoryWithExpiry(MemoryModuleType.UNIVERSAL_ANGER, true, 600L);
		}
	}

	private static void broadcastUniversalAnger(ServerLevel level, Crab body) {
		final Optional<List<Crab>> nearbyCrabs = getNearbyCrabs(body);
		nearbyCrabs.ifPresent(
			crabs -> crabs.forEach(
				crab -> getNearestVisibleTargetablePlayer(crab).ifPresent(
					player -> setAngerTarget(level, crab, player)
				)
			)
		);
	}

	public static void broadcastAngerTarget(ServerLevel level, Crab body, LivingEntity target) {
		Optional<List<Crab>> nearbyCrabs = getNearbyCrabs(body);
		nearbyCrabs.ifPresent(crabs -> crabs.forEach(listedCrab -> setAngerTargetIfCloserThanCurrent(level, listedCrab, target)));
	}

	private static void setAngerTargetIfCloserThanCurrent(ServerLevel level, Crab body, LivingEntity currentTarget) {
		final Optional<LivingEntity> optional = getAngerTarget(body);
		final LivingEntity entity = BehaviorUtils.getNearestTarget(body, optional, currentTarget);
		if (optional.isPresent() && optional.get() == entity) return;
		setAngerTarget(level, body, entity);
	}

	private static void setAngerTargetToNearestTargetablePlayerIfFound(ServerLevel level, Crab body, LivingEntity currentTarget) {
		final Optional<Player> nearestVisibleTargetablePlayer = getNearestVisibleTargetablePlayer(body);
		if (nearestVisibleTargetablePlayer.isPresent()) {
			setAngerTarget(level, body, nearestVisibleTargetablePlayer.get());
		} else {
			setAngerTarget(level, body, currentTarget);
		}
	}

	private static Optional<LivingEntity> getAngerTarget(Crab body) {
		return body.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET);
	}

	private static Optional<List<Crab>> getNearbyCrabs(Crab body) {
		return body.getBrain().getMemory(WWMemoryModuleTypes.NEARBY_CRABS.get());
	}

	public static Optional<Player> getNearestVisibleTargetablePlayer(Crab body) {
		return body.getBrain().hasMemoryValue(MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER)
			? body.getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER)
			: Optional.empty();
	}

	public static void setDigCooldown(Crab body) {
		body.getBrain().setMemoryWithExpiry(
			MemoryModuleType.DIG_COOLDOWN,
			Unit.INSTANCE,
			getRandomDigCooldown(body)
		);
	}

	public static void clearDigCooldown(Crab body) {
		body.getBrain().eraseMemory(MemoryModuleType.DIG_COOLDOWN);
	}

	public static boolean isUnderground(Crab body) {
		return body.getBrain().hasMemoryValue(WWMemoryModuleTypes.IS_UNDERGROUND.get());
	}

	public static boolean isIdle(Crab body) {
		return body.getBrain().isActive(Activity.IDLE);
	}

	public static Predicate<ItemStack> getTemptations() {
		return itemStack -> itemStack.is(WWItemTags.CRAB_FOOD);
	}

	public static int getRandomDigCooldown(LivingEntity body) {
		return body.getRandom().nextInt(800, 2400);
	}

	public static int getRandomEmergeCooldown(LivingEntity body) {
		return body.getRandom().nextInt(800, 2400);
	}

	public static void stopWalking(Crab body) {
		body.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
		body.endNavigation();
	}
}
