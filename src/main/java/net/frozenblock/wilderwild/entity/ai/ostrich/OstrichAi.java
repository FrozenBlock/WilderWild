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

package net.frozenblock.wilderwild.entity.ai.ostrich;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
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
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.Attributes;
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

public class OstrichAi {
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
		WWSensorTypes.OSTRICH_TEMPTATIONS,
		SensorType.NEAREST_ADULT,
		SensorType.NEAREST_PLAYERS,
		WWSensorTypes.OSTRICH_SPECIFIC_SENSOR
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
		WWMemoryModuleTypes.NEARBY_OSTRICHES,
		MemoryModuleType.ATTACK_COOLING_DOWN
	);


	public static Brain.Provider<AbstractOstrich> brainProvider() {
		return Brain.provider(MEMORY_TYPES, SENSOR_TYPES);
	}

	public static Brain<?> makeBrain(AbstractOstrich ostrich, Brain<AbstractOstrich> brain, boolean zombie) {
		initCoreActivity(brain, zombie);
		initIdleActivity(brain, zombie);
		initFightActivity(ostrich, brain);
		brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
		brain.setDefaultActivity(Activity.IDLE);
		brain.useDefaultActivity();
		return brain;
	}

	private static void initCoreActivity(Brain<AbstractOstrich> brain, boolean zombie) {
		final ImmutableList.Builder<BehaviorControl<? super AbstractOstrich>> builder = ImmutableList.builder();
		builder.add(new Swim<>(0.8F));
		if (!zombie) builder.add(new OstrichLayEgg(WWBlocks.OSTRICH_EGG));
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


		brain.addActivity(Activity.CORE, 0, builder.build());
	}

	private static void initIdleActivity(Brain<AbstractOstrich> brain, boolean zombie) {
		final ImmutableList.Builder<Pair<Integer, ? extends BehaviorControl<? super AbstractOstrich>>> builder = ImmutableList.builder();
		builder.add(Pair.of(0, SetEntityLookTargetSometimes.create(EntityType.PLAYER, 6F, UniformInt.of(30, 60))));
		if (!zombie) builder.add(Pair.of(1, new AnimalMakeLove(WWEntityTypes.OSTRICH, SPEED_MULTIPLIER_WHEN_MAKING_LOVE, 2)));

		final ImmutableList.Builder<Pair<? extends BehaviorControl<? super AbstractOstrich>, Integer>> temptAndFollowAdultBuilder = ImmutableList.builder();
		temptAndFollowAdultBuilder.add(
			Pair.of(
				new FollowTemptation(
					livingEntity -> SPEED_MULTIPLIER_WHEN_TEMPTED,
					livingEntity -> livingEntity.isBaby() ? 2.5D : 3.5D),
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

		brain.addActivity(Activity.IDLE, builder.build());
	}

	private static void initFightActivity(AbstractOstrich ostrich, Brain<AbstractOstrich> brain) {
		brain.addActivityAndRemoveMemoryWhenStopped(
			Activity.FIGHT,
			10,
			ImmutableList.of(
				StopAttackingIfTargetInvalid.create(
					(level, livingEntity) -> !ostrich.canTargetEntity(livingEntity), OstrichAi::onTargetInvalid, true
				),
				SetEntityLookTarget.create(livingEntity -> isTarget(ostrich, livingEntity), (float) ostrich.getAttributeValue(Attributes.FOLLOW_RANGE)),
				SetWalkTargetFromAttackTargetIfTargetOutOfReach.create(OstrichAi::getSpeedModifierChasing),
				OstrichMeleeAttack.create(5),
				EraseMemoryIf.create(BehaviorUtils::isBreeding, MemoryModuleType.ATTACK_TARGET)
			),
			MemoryModuleType.ATTACK_TARGET
		);
	}

	public static void updateActivity(AbstractOstrich ostrich) {
		ostrich.getBrain().setActiveActivityToFirstValid(ImmutableList.of(Activity.FIGHT, Activity.IDLE));
	}

	private static boolean isTarget(AbstractOstrich ostrich, LivingEntity entity) {
		return ostrich.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).filter(livingEntity2 -> livingEntity2 == entity).isPresent();
	}

	private static void onTargetInvalid(ServerLevel level, AbstractOstrich ostrich, LivingEntity target) {
		if (ostrich.getTarget() == target) removeAttackAndAngerTarget(ostrich);
		ostrich.getNavigation().stop();
	}

	private static Optional<? extends LivingEntity> findNearestValidAttackTarget(ServerLevel level, AbstractOstrich ostrich) {
		final Brain<AbstractOstrich> brain = ostrich.getBrain();
		final Optional<LivingEntity> angryAt = BehaviorUtils.getLivingEntityFromUUIDMemory(ostrich, MemoryModuleType.ANGRY_AT);
		if (angryAt.isPresent() && Sensor.isEntityAttackableIgnoringLineOfSight(level, ostrich, angryAt.get())) return angryAt;

		if (brain.hasMemoryValue(MemoryModuleType.UNIVERSAL_ANGER)) {
			final Optional<? extends LivingEntity> nearestVisibleAttackablePlayer = brain.getMemory(MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER);
			if (nearestVisibleAttackablePlayer.isPresent()) return nearestVisibleAttackablePlayer;
		}
		return brain.getMemory(MemoryModuleType.NEAREST_ATTACKABLE);
	}

	public static void wasHurtBy(ServerLevel level, AbstractOstrich ostrich, LivingEntity target) {
		if (!ostrich.canTargetEntity(target)) return;
		if (!Sensor.isEntityAttackableIgnoringLineOfSight(level, ostrich, target)) return;
		if (BehaviorUtils.isOtherTargetMuchFurtherAwayThanCurrentAttackTarget(ostrich, target, 4.0)) return;

		if (ostrich.isBaby()) {
			if (Sensor.isEntityAttackableIgnoringLineOfSight(level, ostrich, target)) broadcastAngerTarget(level, ostrich, target);
			return;
		}

		if (target.getType() == EntityType.PLAYER && level.getGameRules().get(GameRules.UNIVERSAL_ANGER)) {
			setAngerTargetToNearestTargetablePlayerIfFound(level, ostrich, target);
			broadcastUniversalAnger(level, ostrich);
		} else {
			setAngerTarget(level, ostrich, target);
			broadcastAngerTarget(level, ostrich, target);
		}
	}

	public static void setAngerTarget(ServerLevel level, AbstractOstrich ostrich, LivingEntity target) {
		if (ostrich.isBaby()) return;
		if (!Sensor.isEntityAttackableIgnoringLineOfSight(level, ostrich, target)) return;

		ostrich.getBrain().eraseMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
		ostrich.getBrain().setMemory(MemoryModuleType.ATTACK_TARGET, target);
		ostrich.getBrain().setMemoryWithExpiry(MemoryModuleType.ANGRY_AT, target.getUUID(), 600L);
		if (target.getType() == EntityType.PLAYER && level.getGameRules().get(GameRules.UNIVERSAL_ANGER)) {
			ostrich.getBrain().setMemoryWithExpiry(MemoryModuleType.UNIVERSAL_ANGER, true, 600L);
		}
	}

	private static void broadcastUniversalAnger(ServerLevel level, AbstractOstrich ostrichEntity) {
		final Optional<List<AbstractOstrich>> nearbyAbstractOstriches = getNearbyAbstractOstriches(ostrichEntity);
		nearbyAbstractOstriches.ifPresent(
			ostriches -> ostriches.forEach(
				ostrich -> getNearestVisibleTargetablePlayer(ostrich).ifPresent(
					player -> setAngerTarget(level, ostrich, player)
				)
			)
		);
	}

	public static void broadcastAngerTarget(ServerLevel level, AbstractOstrich ostrich, LivingEntity target) {
		final Optional<List<AbstractOstrich>> nearbyAbstractOstriches = getNearbyAbstractOstriches(ostrich);
		nearbyAbstractOstriches.ifPresent(ostriches -> ostriches.forEach(
			listedAbstractOstrich -> setAngerTargetIfCloserThanCurrent(level, listedAbstractOstrich, target)
		));
	}

	private static void setAngerTargetIfCloserThanCurrent(ServerLevel level, AbstractOstrich ostrich, LivingEntity currentTarget) {
		final Optional<LivingEntity> optional = getAngerTarget(ostrich);
		final LivingEntity livingEntity = BehaviorUtils.getNearestTarget(ostrich, optional, currentTarget);
		if (optional.isPresent() && optional.get() == livingEntity) return;
		setAngerTarget(level, ostrich, livingEntity);
	}

	private static void setAngerTargetToNearestTargetablePlayerIfFound(ServerLevel level, AbstractOstrich ostrich, LivingEntity currentTarget) {
		final Optional<Player> optional = getNearestVisibleTargetablePlayer(ostrich);
		if (optional.isPresent()) {
			setAngerTarget(level, ostrich, optional.get());
		} else {
			setAngerTarget(level, ostrich, currentTarget);
		}
	}

	private static Optional<List<AbstractOstrich>> getNearbyAbstractOstriches(AbstractOstrich ostrich) {
		return ostrich.getBrain().getMemory(WWMemoryModuleTypes.NEARBY_OSTRICHES);
	}

	public static Optional<Player> getNearestVisibleTargetablePlayer(AbstractOstrich ostrich) {
		return ostrich.getBrain().hasMemoryValue(MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER) ? ostrich.getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER) : Optional.empty();
	}

	private static Optional<LivingEntity> getAngerTarget(AbstractOstrich ostrich) {
		return ostrich.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET);
	}

	public static void removeAttackAndAngerTarget(AbstractOstrich ostrich) {
		final Brain<AbstractOstrich> brain = ostrich.getBrain();
		brain.eraseMemory(MemoryModuleType.ATTACK_TARGET);
		brain.eraseMemory(MemoryModuleType.ANGRY_AT);
		brain.eraseMemory(MemoryModuleType.UNIVERSAL_ANGER);
	}

	private static float getSpeedModifierChasing(@Nullable LivingEntity entity) {
		return SPEED_MULTIPLIER_WHEN_ATTACKING;
	}

	public static Predicate<ItemStack> getTemptations() {
		return stack -> stack.is(WWItemTags.OSTRICH_FOOD);
	}

}
