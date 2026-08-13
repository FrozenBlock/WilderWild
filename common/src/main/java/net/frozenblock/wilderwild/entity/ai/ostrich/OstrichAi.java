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

package net.frozenblock.wilderwild.entity.ai.ostrich;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import net.frozenblock.wilderwild.entity.AbstractOstrich;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.registry.WWEntityTypes;
import net.frozenblock.wilderwild.registry.WWMemoryModuleTypes;
import net.frozenblock.wilderwild.registry.WWSensorTypes;
import net.frozenblock.wilderwild.tag.WWItemTags;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.ActivityData;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.AnimalMakeLove;
import net.minecraft.world.entity.ai.behavior.BabyFollowAdult;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.behavior.CountDownCooldownTicks;
import net.minecraft.world.entity.ai.behavior.DoNothing;
import net.minecraft.world.entity.ai.behavior.EraseMemoryIf;
import net.minecraft.world.entity.ai.behavior.FollowTemptation;
import net.minecraft.world.entity.ai.behavior.LookAtTargetSink;
import net.minecraft.world.entity.ai.behavior.MoveToTargetSink;
import net.minecraft.world.entity.ai.behavior.RandomLookAround;
import net.minecraft.world.entity.ai.behavior.RandomStroll;
import net.minecraft.world.entity.ai.behavior.RunOne;
import net.minecraft.world.entity.ai.behavior.SetEntityLookTarget;
import net.minecraft.world.entity.ai.behavior.SetEntityLookTargetSometimes;
import net.minecraft.world.entity.ai.behavior.SetWalkTargetFromAttackTargetIfTargetOutOfReach;
import net.minecraft.world.entity.ai.behavior.SetWalkTargetFromLookTarget;
import net.minecraft.world.entity.ai.behavior.StartAttacking;
import net.minecraft.world.entity.ai.behavior.StopAttackingIfTargetInvalid;
import net.minecraft.world.entity.ai.behavior.StopBeingAngryIfTargetDead;
import net.minecraft.world.entity.ai.behavior.Swim;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.gamerules.GameRules;
import org.jetbrains.annotations.Nullable;

public final class OstrichAi {
	private static final float SPEED_MULTIPLIER_WHEN_PANICKING = 2.0F;
	private static final float SPEED_MULTIPLIER_WHEN_ATTACKING = 1.75F;
	private static final float SPEED_MULTIPLIER_WHEN_IDLING = 1.0F;
	private static final float SPEED_MULTIPLIER_WHEN_TEMPTED = 1.25F;
	private static final float SPEED_MULTIPLIER_WHEN_FOLLOWING_ADULT = 1.25F;
	private static final float SPEED_MULTIPLIER_WHEN_MAKING_LOVE = 0.8F;
	private static final UniformInt ADULT_FOLLOW_RANGE = UniformInt.of(5, 16);
	private static final ImmutableList<SensorType<? extends Sensor<? super AbstractOstrich>>> SENSOR_TYPES = ImmutableList.of(
		SensorType.NEAREST_LIVING_ENTITIES,
		SensorType.HURT_BY,
		WWSensorTypes.OSTRICH_TEMPTATIONS.get(),
		SensorType.NEAREST_ADULT,
		SensorType.NEAREST_PLAYERS,
		WWSensorTypes.OSTRICH_SPECIFIC_SENSOR.get()
	);

	private OstrichAi() {}

	public static Brain.Provider<AbstractOstrich> brainProvider(boolean zombie) {
		return Brain.provider(SENSOR_TYPES, body -> getActivities(body, zombie));
	}

	public static List<ActivityData<AbstractOstrich>> getActivities(final AbstractOstrich body, final boolean zombie) {
		return List.of(initCoreActivity(zombie), initIdleActivity(zombie), initFightActivity(body));
	}

	private static ActivityData<AbstractOstrich> initCoreActivity(final boolean zombie) {
		final ImmutableList.Builder<BehaviorControl<? super AbstractOstrich>> builder = ImmutableList.builder();
		builder.add(new Swim<>(0.8F));
		if (!zombie) builder.add(new OstrichLayEgg(WWBlocks.OSTRICH_EGG.get()));
		builder.add(
			new OstrichPanic(
				SPEED_MULTIPLIER_WHEN_PANICKING,
				pathfinderMob -> pathfinderMob.isBaby() ? DamageTypeTags.PANIC_CAUSES : DamageTypeTags.PANIC_ENVIRONMENTAL_CAUSES
			)
		);
		builder.add(new OstrichRunAroundLikeCrazy(1.5F, zombie));
		builder.add(new LookAtTargetSink(45, 90));
		builder.add(new MoveToTargetSink());
		builder.add(new CountDownCooldownTicks(MemoryModuleType.TEMPTATION_COOLDOWN_TICKS));
		builder.add(new CountDownCooldownTicks(MemoryModuleType.GAZE_COOLDOWN_TICKS));
		builder.add(StopBeingAngryIfTargetDead.create());

		return ActivityData.create(Activity.CORE, 0, builder.build());
	}

	private static ActivityData<AbstractOstrich> initIdleActivity(final boolean zombie) {
		final ImmutableList.Builder<Pair<Integer, ? extends BehaviorControl<? super AbstractOstrich>>> builder = ImmutableList.builder();
		builder.add(Pair.of(0, SetEntityLookTargetSometimes.create(EntityTypes.PLAYER, 6F, UniformInt.of(30, 60))));
		if (!zombie) builder.add(Pair.of(1, new AnimalMakeLove(WWEntityTypes.OSTRICH.get(), SPEED_MULTIPLIER_WHEN_MAKING_LOVE, 2)));

		final ImmutableList.Builder<Pair<? extends BehaviorControl<? super AbstractOstrich>, Integer>> temptAndFollowAdultBuilder = ImmutableList.builder();
		temptAndFollowAdultBuilder.add(
			Pair.of(
				new FollowTemptation(
					entity -> SPEED_MULTIPLIER_WHEN_TEMPTED,
					entity -> entity.isBaby() ? 2.5D : 3.5D),
				1
			)
		);
		if (!zombie) temptAndFollowAdultBuilder.add(
			Pair.of(
				BehaviorBuilder.triggerIf(
					Predicate.not(AbstractOstrich::refuseToMove),
					BabyFollowAdult.create(ADULT_FOLLOW_RANGE, SPEED_MULTIPLIER_WHEN_FOLLOWING_ADULT)
				),
				1
			)
		);
		builder.add(Pair.of(2, new RunOne<>(temptAndFollowAdultBuilder.build())));

		builder.add(Pair.of(3, StartAttacking.create(OstrichAi::findNearestValidAttackTarget)));
		builder.add(Pair.of(4, new RandomLookAround(UniformInt.of(150, 250), 80F, -70F, 70F)));
		builder.add(
			Pair.of(
				5,
				new RunOne<>(
					ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT),
					ImmutableList.of(
						Pair.of(BehaviorBuilder.triggerIf(Predicate.not(AbstractOstrich::refuseToMove), RandomStroll.stroll(SPEED_MULTIPLIER_WHEN_IDLING)), 1),
						Pair.of(BehaviorBuilder.triggerIf(Predicate.not(AbstractOstrich::refuseToMove), SetWalkTargetFromLookTarget.create(SPEED_MULTIPLIER_WHEN_IDLING, 3)), 1),
						Pair.of(new DoNothing(30, 60), 1)
					)
				)
			)
		);

		return ActivityData.create(Activity.IDLE, builder.build());
	}

	private static ActivityData<AbstractOstrich> initFightActivity(final AbstractOstrich body) {
		return ActivityData.create(
			Activity.FIGHT,
			10,
			ImmutableList.of(
				StopAttackingIfTargetInvalid.create(
					(level, entity) -> !body.canTargetEntity(entity), OstrichAi::onTargetInvalid, true
				),
				SetEntityLookTarget.create(entity -> isTarget(body, entity), 16F),
				SetWalkTargetFromAttackTargetIfTargetOutOfReach.create(OstrichAi::getSpeedModifierChasing),
				OstrichMeleeAttack.create(5),
				EraseMemoryIf.create(BehaviorUtils::isBreeding, MemoryModuleType.ATTACK_TARGET)
			),
			MemoryModuleType.ATTACK_TARGET
		);
	}

	public static void updateActivity(AbstractOstrich body) {
		body.getBrain().setActiveActivityToFirstValid(ImmutableList.of(Activity.FIGHT, Activity.IDLE));
	}

	private static boolean isTarget(AbstractOstrich body, LivingEntity entity) {
		return body.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).filter(livingEntity2 -> livingEntity2 == entity).isPresent();
	}

	private static void onTargetInvalid(ServerLevel level, AbstractOstrich body, LivingEntity target) {
		if (body.getTarget() == target) removeAttackAndAngerTarget(body);
		body.getNavigation().stop();
	}

	private static Optional<? extends LivingEntity> findNearestValidAttackTarget(ServerLevel level, AbstractOstrich body) {
		final Brain<AbstractOstrich> brain = body.getBrain();
		final Optional<LivingEntity> angryAt = BehaviorUtils.getLivingEntityFromUUIDMemory(body, MemoryModuleType.ANGRY_AT);
		if (angryAt.isPresent() && Sensor.isEntityAttackableIgnoringLineOfSight(level, body, angryAt.get())) return angryAt;

		if (brain.hasMemoryValue(MemoryModuleType.UNIVERSAL_ANGER)) {
			final Optional<? extends LivingEntity> nearestVisibleAttackablePlayer = brain.getMemory(MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER);
			if (nearestVisibleAttackablePlayer.isPresent()) return nearestVisibleAttackablePlayer;
		}
		return Optional.empty();
	}

	public static void wasHurtBy(ServerLevel level, AbstractOstrich body, LivingEntity target) {
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

	public static void setAngerTarget(ServerLevel level, AbstractOstrich body, LivingEntity target) {
		if (body.isBaby()) return;
		if (!Sensor.isEntityAttackableIgnoringLineOfSight(level, body, target)) return;

		body.getBrain().eraseMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
		body.getBrain().setMemory(MemoryModuleType.ATTACK_TARGET, target);
		body.getBrain().setMemoryWithExpiry(MemoryModuleType.ANGRY_AT, target.getUUID(), 600L);
		if (target.getType() == EntityTypes.PLAYER && level.getGameRules().get(GameRules.UNIVERSAL_ANGER)) {
			body.getBrain().setMemoryWithExpiry(MemoryModuleType.UNIVERSAL_ANGER, true, 600L);
		}
	}

	private static void broadcastUniversalAnger(ServerLevel level, AbstractOstrich body) {
		final Optional<List<AbstractOstrich>> nearbyAbstractOstriches = getNearbyAbstractOstriches(body);
		nearbyAbstractOstriches.ifPresent(
			ostriches -> ostriches.forEach(
				ostrich -> getNearestVisibleTargetablePlayer(ostrich).ifPresent(
					player -> setAngerTarget(level, ostrich, player)
				)
			)
		);
	}

	public static void broadcastAngerTarget(ServerLevel level, AbstractOstrich body, LivingEntity target) {
		final Optional<List<AbstractOstrich>> nearbyAbstractOstriches = getNearbyAbstractOstriches(body);
		nearbyAbstractOstriches.ifPresent(ostriches -> ostriches.forEach(
			listedAbstractOstrich -> setAngerTargetIfCloserThanCurrent(level, listedAbstractOstrich, target)
		));
	}

	private static void setAngerTargetIfCloserThanCurrent(ServerLevel level, AbstractOstrich body, LivingEntity currentTarget) {
		final Optional<LivingEntity> optional = getAngerTarget(body);
		final LivingEntity livingEntity = BehaviorUtils.getNearestTarget(body, optional, currentTarget);
		if (optional.isPresent() && optional.get() == livingEntity) return;
		setAngerTarget(level, body, livingEntity);
	}

	private static void setAngerTargetToNearestTargetablePlayerIfFound(ServerLevel level, AbstractOstrich body, LivingEntity currentTarget) {
		final Optional<Player> optional = getNearestVisibleTargetablePlayer(body);
		if (optional.isPresent()) {
			setAngerTarget(level, body, optional.get());
		} else {
			setAngerTarget(level, body, currentTarget);
		}
	}

	private static Optional<List<AbstractOstrich>> getNearbyAbstractOstriches(AbstractOstrich body) {
		return body.getBrain().getMemory(WWMemoryModuleTypes.NEARBY_OSTRICHES.get());
	}

	public static Optional<Player> getNearestVisibleTargetablePlayer(AbstractOstrich body) {
		return body.getBrain().hasMemoryValue(MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER)
			? body.getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER)
			: Optional.empty();
	}

	private static Optional<LivingEntity> getAngerTarget(AbstractOstrich body) {
		return body.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET);
	}

	public static void removeAttackAndAngerTarget(AbstractOstrich body) {
		final Brain<AbstractOstrich> brain = body.getBrain();
		brain.eraseMemory(MemoryModuleType.ATTACK_TARGET);
		brain.eraseMemory(MemoryModuleType.ANGRY_AT);
		brain.eraseMemory(MemoryModuleType.UNIVERSAL_ANGER);
	}

	private static float getSpeedModifierChasing(@Nullable LivingEntity body) {
		return SPEED_MULTIPLIER_WHEN_ATTACKING;
	}

	public static Predicate<ItemStack> getTemptations() {
		return itemStack -> itemStack.is(WWItemTags.OSTRICH_FOOD);
	}

}
