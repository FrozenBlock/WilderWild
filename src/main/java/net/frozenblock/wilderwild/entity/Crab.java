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

package net.frozenblock.wilderwild.entity;

import com.mojang.serialization.Dynamic;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import net.frozenblock.wilderwild.MoveToFrozenLib;
import net.frozenblock.wilderwild.config.EntityConfig;
import net.frozenblock.wilderwild.entity.ai.crab.CrabAi;
import net.frozenblock.wilderwild.entity.ai.crab.CrabJumpControl;
import net.frozenblock.wilderwild.entity.ai.crab.CrabMoveControl;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.registry.RegisterEntities;
import net.frozenblock.wilderwild.registry.RegisterItems;
import net.frozenblock.wilderwild.registry.RegisterMemoryModuleTypes;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.frozenblock.wilderwild.tag.WilderBiomeTags;
import net.frozenblock.wilderwild.tag.WilderBlockTags;
import net.frozenblock.wilderwild.tag.WilderGameEventTags;
import net.frozenblock.wilderwild.tag.WilderItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Unit;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityEvent;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WallClimberNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.DynamicGameEventListener;
import net.minecraft.world.level.gameevent.EntityPositionSource;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.PositionSource;
import net.minecraft.world.level.gameevent.vibrations.VibrationSystem;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Math;

public class Crab extends Animal implements VibrationSystem, Bucketable {
	public static final float MAX_TARGET_DISTANCE = 16F;
	public static final double MOVEMENT_SPEED = 0.16;
	public static final double WATER_MOVEMENT_SPEED = 0.576;
	public static final int DIG_LENGTH_IN_TICKS = 95;
	public static final int EMERGE_LENGTH_IN_TICKS = 29;
	public static final double UNDERGROUND_PLAYER_RANGE = 4;
	private static final Map<ServerLevelAccessor, Integer> CRABS_PER_LEVEL = new HashMap<>();
	private static final int DIG_TICKS_UNTIL_PARTICLES = 17;
	private static final int DIG_TICKS_UNTIL_STOP_PARTICLES = 82;
	private static final int EMERGE_TICKS_UNTIL_PARTICLES = 1;
	private static final int EMERGE_TICKS_UNTIL_STOP_PARTICLES = 16;
	private static final EntityDataAccessor<MoveState> MOVE_STATE = SynchedEntityData.defineId(Crab.class, MoveState.SERIALIZER);
	private static final EntityDataAccessor<Float> TARGET_CLIMBING_ANIM_X = SynchedEntityData.defineId(Crab.class, EntityDataSerializers.FLOAT);
	private static final EntityDataAccessor<Integer> DIGGING_TICKS = SynchedEntityData.defineId(Crab.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Boolean> FROM_BUCKET = SynchedEntityData.defineId(Crab.class, EntityDataSerializers.BOOLEAN);
	public final AnimationState diggingAnimationState = new AnimationState();
	public final AnimationState emergingAnimationState = new AnimationState();
	public final AnimationState hidingAnimationState = new AnimationState();
	private final DynamicGameEventListener<VibrationSystem.Listener> dynamicGameEventListener;
	private final VibrationSystem.User vibrationUser;
	public float climbAnimX;
	public float prevClimbAnimX;
	public Vec3 prevMovement;
	private VibrationSystem.Data vibrationData;

	public Crab(EntityType<? extends Crab> entityType, Level level) {
		super(entityType, level);
		this.vibrationUser = new Crab.VibrationUser();
		this.vibrationData = new VibrationSystem.Data();
		this.dynamicGameEventListener = new DynamicGameEventListener<>(new VibrationSystem.Listener(this));
		this.jumpControl = new CrabJumpControl(this);
		this.prevMovement = Vec3.ZERO;
		this.setMaxUpStep(0.2F);
		this.setPathfindingMalus(BlockPathTypes.LAVA, -1.0F);
		this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, -1.0F);
		this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
		this.setPathfindingMalus(BlockPathTypes.WATER_BORDER, 0.0F);
		if (EntityConfig.get().unpassableRail) {
			this.setPathfindingMalus(BlockPathTypes.UNPASSABLE_RAIL, 0.0F);
		}
		this.moveControl = new CrabMoveControl(this);
	}

	@Override
	@NotNull
	protected PathNavigation createNavigation(@NotNull Level level) {
		return new WallClimberNavigation(this, level);
	}

	@NotNull
	public static AttributeSupplier.Builder addAttributes() {
		return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 8.0D)
			.add(Attributes.MOVEMENT_SPEED, MOVEMENT_SPEED)
			.add(Attributes.JUMP_STRENGTH, 0.0D)
			.add(Attributes.ATTACK_DAMAGE, 2.0D)
			.add(Attributes.FOLLOW_RANGE, MAX_TARGET_DISTANCE);
	}

	@Override
	@NotNull
	protected Brain.Provider<Crab> brainProvider() {
		return Brain.provider(CrabAi.MEMORY_MODULES, CrabAi.SENSORS);
	}

	@Override
	@NotNull
	protected Brain<?> makeBrain(@NotNull Dynamic<?> dynamic) {
		return CrabAi.makeBrain(this, this.brainProvider().makeBrain(dynamic));
	}

	@Override
	@NotNull
	@SuppressWarnings("unchecked")
	public Brain<Crab> getBrain() {
		return (Brain<Crab>) super.getBrain();
	}

	@Override
	protected void sendDebugPackets() {
		super.sendDebugPackets();
		DebugPackets.sendEntityBrain(this);
	}

	@Override
	public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor level, @NotNull DifficultyInstance difficulty, @NotNull MobSpawnType reason, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag dataTag) {
		this.getBrain().setMemoryWithExpiry(MemoryModuleType.DIG_COOLDOWN, Unit.INSTANCE, CrabAi.getRandomDigCooldown(this));
		switch (reason) {
			case BUCKET -> { return spawnData; }
			case NATURAL, TRIGGERED, REINFORCEMENT -> this.getBrain().setMemoryWithExpiry(MemoryModuleType.IS_EMERGING, Unit.INSTANCE, EMERGE_LENGTH_IN_TICKS);
			default -> {}
		}
		if (spawnData instanceof CrabGroupData crabGroupData) {
			if (crabGroupData.getGroupSize() >= 2) {
				this.setAge(-24000);
			}
		} else {
			spawnData = new CrabGroupData();
		}
		return super.finalizeSpawn(level, difficulty, reason, spawnData, dataTag);
	}

	public static int getCrabs(@NotNull ServerLevel level) {
		AtomicInteger count = new AtomicInteger();
		if (!CRABS_PER_LEVEL.containsKey(level)) {
			level.entityManager.getEntityGetter().getAll().forEach(entity -> {
				if (entity instanceof Crab) {
					count.addAndGet(1);
				}
			});
			CRABS_PER_LEVEL.put(level, count.get());
		} else {
			count.set(CRABS_PER_LEVEL.get(level));
		}
		return count.get();
	}

	@Override
	public float getWalkTargetValue(@NotNull BlockPos pos, @NotNull LevelReader level) {
		return 0F;
	}

	@Override
	public boolean isSuppressingSlidingDownLadder() {
		return !this.getMoveControl().hasWanted() && this.onClimbable();
	}

	public static void clearLevelToCrabCount() {
		CRABS_PER_LEVEL.clear();
	}

	public static boolean canSpawn(@NotNull EntityType<Crab> type, @NotNull ServerLevelAccessor level, @NotNull MobSpawnType reason, @NotNull BlockPos pos, @NotNull RandomSource random) {
		if (reason == MobSpawnType.SPAWNER) {
			return true;
		}
		Holder<Biome> biome = level.getBiome(pos);
		int randomBound = 30;
		if (!biome.is(WilderBiomeTags.HAS_COMMON_CRAB)) {
			randomBound = 90;
			if (getCrabs(level.getLevel()) >= type.getCategory().getMaxInstancesPerChunk() / 3) {
				return false;
			}
		}
		int seaLevel = level.getSeaLevel();
		return random.nextInt(0, randomBound) == 0 && pos.getY() >= seaLevel - 33 && pos.getY() <= seaLevel + 3 && level.getBlockState(pos.below()).is(WilderBlockTags.CRAB_CAN_HIDE);
	}

	@Override
	@Nullable
	public LivingEntity getTarget() {
		return this.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).orElse(null);
	}

	@Override
	public boolean isInvisible() {
		return super.isInvisible() || this.isInvisibleWhileUnderground();
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(MOVE_STATE, MoveState.WALKING);
		this.entityData.define(TARGET_CLIMBING_ANIM_X, 0F);
		this.entityData.define(DIGGING_TICKS, 0);
		this.entityData.define(FROM_BUCKET, false);
	}

	@Override
	public boolean canBreatheUnderwater() {
		return true;
	}

	@Override
	@NotNull
	public MobType getMobType() {
		return MobType.ARTHROPOD;
	}

	@Override
	public boolean checkSpawnObstruction(@NotNull LevelReader level) {
		return level.isUnobstructed(this);
	}

	@Override
	public void aiStep() {
		this.updateSwingTime();
		AttributeInstance movementSpeed = this.getAttribute(Attributes.MOVEMENT_SPEED);
		if (movementSpeed != null) {
			movementSpeed.setBaseValue(this.isInWater() ? WATER_MOVEMENT_SPEED : MOVEMENT_SPEED);
		}
		super.aiStep();
	}

	@Nullable
	public Vec3 findNearestWall() {
		BlockPos crabPos = this.blockPosition();
		ArrayList<Vec3> vecs = new ArrayList<>();
		Vec3 crabVec3 = this.position();
		CollisionContext collisionContext = CollisionContext.of(this);
		for (BlockPos pos : BlockPos.betweenClosed(crabPos.offset(-1, 0, -1), crabPos.offset(1, 0, 1))) {
			BlockState state = this.level().getBlockState(pos);
			VoxelShape collisionShape = state.getCollisionShape(this.level(), pos, collisionContext);
			if (isWallPosSlowable(pos, state, collisionShape)) {
				Optional<Vec3> optionalVec3 = MoveToFrozenLib.closestPointTo(pos, collisionShape, crabVec3);
				if (optionalVec3.isPresent()) {
					vecs.add(optionalVec3.get());
				} else if (state.getFluidState().is(FluidTags.WATER)) {
					vecs.add(pos.getCenter());
				}
			}
		}
		return getClosestPos(vecs);
	}

	@Nullable
	public Vec3 getClosestPos(@NotNull ArrayList<Vec3> vec3s) {
		double lowestDistance = Double.MAX_VALUE;
		Vec3 selectedVec3 = null;
		Vec3 thisPos = this.getEyePosition();
		if (!vec3s.isEmpty()) {
			for (Vec3 vec3 : vec3s) {
				double distance = vec3.distanceTo(thisPos);
				if (distance < lowestDistance) {
					lowestDistance = distance;
					selectedVec3 = vec3;
				}
			}
		}
		return selectedVec3;
	}

	public boolean isWallPosSlowable(@NotNull BlockPos pos, @NotNull BlockState state, @NotNull VoxelShape collisionShape) {
		if (state.isAir() || state.getFluidState().is(FluidTags.LAVA)) {
			return false;
		}
		if (state.getFluidState().is(FluidTags.WATER)) {
			return true;
		}
		if (collisionShape.isEmpty() || pos.getY() + collisionShape.min(Direction.Axis.Y) > this.getEyeY()) {
			return false;
		}
		return true;
	}

	@Override
	public void tick() {
		boolean isClient = this.level().isClientSide;
		if (this.level() instanceof ServerLevel serverLevel) {
			VibrationSystem.Ticker.tick(serverLevel, this.vibrationData, this.vibrationUser);
		}
		super.tick();
		if (!isClient) {
			if (this.horizontalCollision) {
				Vec3 usedMovement = this.getDeltaMovement();
				this.setMoveState(usedMovement.y() >= 0 ? MoveState.CLIMBING : MoveState.DESCENDING);
				if (usedMovement.x == 0 && usedMovement.z == 0) usedMovement = this.prevMovement;
				this.setTargetClimbAnimX(
					Math.abs(getAngleFromVec3(usedMovement) - getAngleFromVec3(this.getViewVector(1F))) / 180F
				);
				this.prevMovement = usedMovement;
			} else {
				this.setMoveState(MoveState.WALKING);
				this.setTargetClimbAnimX(0F);
				if (!this.onGround() && !this.isInWater()) {
					AABB floorCheckBox = this.makeBoundingBox().expandTowards(0, -2, 0);
					if (!level().getBlockCollisions(this, floorCheckBox).iterator().hasNext()) {
						Vec3 wallPos = this.findNearestWall();
						if (wallPos != null) {
							Vec3 differenceBetween = wallPos.subtract(this.position());
							this.setDeltaMovement(
								this.getDeltaMovement().add(
									differenceBetween.x() < 0D ? -0.025D : differenceBetween.x() > 0D ? 0.025D : 0D,
									0D,
									differenceBetween.z() < 0D ? -0.025D : differenceBetween.z() > 0D ? 0.025D : 0D
								)
							);
						}
					}
				}
			}
			if (this.isDiggingOrEmerging()) {
				this.setDiggingTicks(this.getDiggingTicks() + 1);
			}
		} else {
			switch (this.getPose()) {
				case DIGGING -> {
					if (this.getDiggingTicks() > DIG_TICKS_UNTIL_PARTICLES && this.getDiggingTicks() < DIG_TICKS_UNTIL_STOP_PARTICLES) {
						this.clientDiggingParticles();
					}
				}
				case EMERGING -> {
					if (this.getDiggingTicks() >= EMERGE_TICKS_UNTIL_PARTICLES && this.getDiggingTicks() <= EMERGE_TICKS_UNTIL_STOP_PARTICLES) {
						this.clientDiggingParticles();
					}
				}
				default -> {}
			}
		}
		this.prevClimbAnimX = this.climbAnimX;
		// supplier so that it isn't evaluated each time
		Supplier<Float> climbingVal = () -> (Math.cos(this.targetClimbAnimX() * Mth.PI) >= -0.275F ? -1F : 1F);
		this.climbAnimX += ((this.isClimbing() ? climbingVal.get() : 0F) - this.climbAnimX) * 0.2F;
	}

	@Override
	public boolean hurt(@NotNull DamageSource source, float amount) {
		boolean bl = super.hurt(source, amount);
		if (this.level().isClientSide) {
			return false;
		}
		if (bl) {
			if (source.getEntity() instanceof LivingEntity livingEntity) {
				CrabAi.wasHurtBy(this, livingEntity);
			}
			if (!this.isDiggingOrEmerging()) {
				CrabAi.setDigCooldown(this);
			}
		}
		return bl;
	}

	@Override
	protected void customServerAiStep() {
		this.level().getProfiler().push("crabBrain");
		this.getBrain().tick((ServerLevel) this.level(), this);
		this.level().getProfiler().pop();
		this.level().getProfiler().push("crabActivityUpdate");
		CrabAi.updateActivity(this);
		this.level().getProfiler().pop();
		super.customServerAiStep();
		this.getBrain().setMemory(RegisterMemoryModuleTypes.FIRST_BRAIN_TICK, Unit.INSTANCE);
	}

	@Nullable
	@Override
	protected SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
		return RegisterSounds.ENTITY_CRAB_HURT;
	}

	@Nullable
	@Override
	protected SoundEvent getDeathSound() {
		return RegisterSounds.ENTITY_CRAB_DEATH;
	}

	@Nullable
	@Override
	protected SoundEvent getAmbientSound() {
		return RegisterSounds.ENTITY_CRAB_IDLE;
	}

	@Override
	public void playAmbientSound() {
		SoundEvent soundEvent = this.getAmbientSound();
		if (soundEvent != null) {
			this.playSound(soundEvent, this.getSoundVolume() * 0.065F, this.getVoicePitch());
		}
	}

	@Override
	public boolean doHurtTarget(@NotNull Entity target) {
		this.level().broadcastEntityEvent(this, EntityEvent.START_ATTACKING);
		this.playSound(RegisterSounds.ENTITY_CRAB_ATTACK, 1.0F, this.getVoicePitch());
		return super.doHurtTarget(target);
	}

	@Override
	public boolean isInvulnerableTo(@NotNull DamageSource source) {
		if (this.isDiggingOrEmerging() && !source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
			return true;
		}
		return super.isInvulnerableTo(source);
	}

	@Override
	public boolean isPushable() {
		return !this.isDiggingOrEmerging() && super.isPushable();
	}

	@Override
	protected void doPush(@NotNull Entity entity) {
		if (this.isInvisibleWhileUnderground()) {
			return;
		}
		super.doPush(entity);
	}

	@Override
	public boolean isPushedByFluid() {
		return false;
	}

	@Override
	public boolean ignoreExplosion() {
		return this.isDiggingOrEmerging();
	}

	public boolean isDiggingOrEmerging() {
		return this.hasPose(Pose.DIGGING) || this.hasPose(Pose.EMERGING);
	}

	public boolean isInvisibleWhileUnderground() {
		return this.hasPose(Pose.DIGGING) && this.getDiggingTicks() > DIG_LENGTH_IN_TICKS;
	}

	@Contract("null->false")
	public boolean canTargetEntity(@Nullable Entity entity) {
		return entity instanceof LivingEntity livingEntity
			&& this.level() == livingEntity.level()
			&& !this.level().getDifficulty().equals(Difficulty.PEACEFUL)
			&& EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(livingEntity)
			&& !this.isAlliedTo(livingEntity)
			&& livingEntity.getType() != EntityType.ARMOR_STAND
			&& livingEntity.getType() != RegisterEntities.CRAB
			&& !livingEntity.isInvulnerable()
			&& !livingEntity.isDeadOrDying()
			&& !livingEntity.isRemoved()
			&& this.level().getWorldBorder().isWithinBounds(livingEntity.getBoundingBox());
	}

	public boolean canHideOnGround() {
		BlockPos onPos = this.getOnPos();
		BlockPos topPos = this.blockPosition();
		if (onPos.equals(topPos)) {
			topPos = topPos.above();
		}
		return this.onGround()
			&& !this.isColliding(topPos, this.level().getBlockState(topPos))
			&& this.level().getBlockState(onPos).is(WilderBlockTags.CRAB_CAN_HIDE);
	}

	public boolean canEmerge() {
		BlockPos blockPos = this.blockPosition();
		if (blockPos.equals(this.getOnPos())) {
			blockPos = blockPos.above();
		}
		BlockState state = this.level().getBlockState(blockPos);
		return !state.is(BlockTags.FIRE) && !state.getFluidState().is(FluidTags.LAVA);
	}

	public void endNavigation() {
		this.getNavigation().stop();
		if (this.getNavigation() instanceof WallClimberNavigation wallClimberNavigation) {
			wallClimberNavigation.pathToPosition = null;
		}
	}

	@Override
	public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> key) {
		if (DATA_POSE.equals(key)) {
			switch (this.getPose()) {
				case DIGGING -> {
					this.diggingAnimationState.start(this.tickCount);
					this.emergingAnimationState.stop();
					this.hidingAnimationState.stop();
				}
				case EMERGING -> {
					this.diggingAnimationState.stop();
					this.emergingAnimationState.start(this.tickCount);
					this.hidingAnimationState.stop();
				}
				default -> {
					this.diggingAnimationState.stop();
					this.emergingAnimationState.stop();
					this.hidingAnimationState.stop();
				}

			}
		} else if (DIGGING_TICKS.equals(key) && this.getDiggingTicks() > DIG_LENGTH_IN_TICKS && this.getPose() == Pose.DIGGING) {
			this.diggingAnimationState.stop();
			this.emergingAnimationState.stop();
			this.hidingAnimationState.start(this.tickCount);
		}
		super.onSyncedDataUpdated(key);
	}

	@Override
	public boolean onClimbable() {
		return this.isClimbing() || this.isCrabDescending();
	}

	@Override
	public boolean isDescending() {
		return this.isCrabDescending();
	}

	public MoveState moveState() {
		return this.entityData.get(MOVE_STATE);
	}

	public boolean isClimbing() {
		return this.moveState() == MoveState.CLIMBING;
	}

	public boolean isCrabDescending() {
		return this.moveState() == MoveState.DESCENDING;
	}

	public void setMoveState(MoveState state) {
		this.entityData.set(MOVE_STATE, state);
	}

	public float targetClimbAnimX() {
		return this.entityData.get(TARGET_CLIMBING_ANIM_X);
	}

	public void setTargetClimbAnimX(float f) {
		this.entityData.set(TARGET_CLIMBING_ANIM_X, f);
	}

	public int getDiggingTicks() {
		return this.entityData.get(DIGGING_TICKS);
	}

	public void setDiggingTicks(int i) {
		this.entityData.set(DIGGING_TICKS, i);
	}

	public void resetDiggingTicks() {
		this.setDiggingTicks(0);
	}

	@Override
	public boolean isFood(@NotNull ItemStack stack) {
		return stack.is(WilderItemTags.CRAB_TEMPT_ITEMS);
	}

	@Override
	public boolean fromBucket() {
		return this.entityData.get(FROM_BUCKET);
	}

	@Override
	public void setFromBucket(boolean fromBucket) {
		this.entityData.set(FROM_BUCKET, fromBucket);
	}

	@Override
	public void calculateEntityAnimation(boolean includeHeight) {
		super.calculateEntityAnimation(this.onClimbable() || includeHeight);
	}

	@Override
	protected float getStandingEyeHeight(@NotNull Pose pose, @NotNull EntityDimensions dimensions) {
		return dimensions.height * 0.65F;
	}

	@Nullable
	@Override
	public AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob otherParent) {
		Crab crab = RegisterEntities.CRAB.create(level);
		if (crab != null) {
			crab.setPersistenceRequired();
			crab.getBrain().setMemoryWithExpiry(MemoryModuleType.DIG_COOLDOWN, Unit.INSTANCE, CrabAi.getRandomDigCooldown(crab));
		}
		return crab;
	}

	@Override
	public InteractionResult mobInteract(@NotNull Player player, @NotNull InteractionHand hand) {
		return Bucketable.bucketMobPickup(player, hand, this).orElse(super.mobInteract(player, hand));
	}

	@Override
	public void saveToBucketTag(@NotNull ItemStack stack) {
		Bucketable.saveDefaultDataToBucketTag(this, stack);
		CompoundTag compoundTag = stack.getOrCreateTag();
		compoundTag.putInt("Age", this.getAge());
		Brain<Crab> brain = this.getBrain();
		if (brain.hasMemoryValue(MemoryModuleType.HAS_HUNTING_COOLDOWN)) {
			compoundTag.putLong("HuntingCooldown", brain.getTimeUntilExpiry(MemoryModuleType.HAS_HUNTING_COOLDOWN));
		}
	}

	@Override
	public void loadFromBucketTag(@NotNull CompoundTag tag) {
		Bucketable.loadDefaultDataFromBucketTag(this, tag);
		if (tag.contains("Age")) {
			this.setAge(tag.getInt("Age"));
		}
		if (tag.contains("HuntingCooldown")) {
			this.getBrain().setMemoryWithExpiry(MemoryModuleType.HAS_HUNTING_COOLDOWN, true, tag.getLong("HuntingCooldown"));
		}
	}

	@Override
	@NotNull
	public ItemStack getBucketItemStack() {
		return new ItemStack(RegisterItems.CRAB_BUCKET);
	}


	@Override
	@NotNull
	public SoundEvent getPickupSound() {
		return RegisterSounds.ITEM_BUCKET_FILL_CRAB;
	}

	@Override
	public boolean requiresCustomPersistence() {
		return super.requiresCustomPersistence() || this.fromBucket();
	}

	@Override
	public boolean removeWhenFarAway(double distanceToClosestPlayer) {
		return !this.fromBucket() && !this.hasCustomName();
	}

	private void clientDiggingParticles() {
		RandomSource randomSource = this.getRandom();
		BlockState blockState = this.getBlockStateOn();
		if (blockState.getRenderShape() != RenderShape.INVISIBLE) {
			for (int i = 0; i < 8; ++i) {
				double x = this.getX() + Mth.randomBetween(randomSource, -0.25f, 0.25f);
				double y = this.getY();
				double z = this.getZ() + Mth.randomBetween(randomSource, -0.25f, 0.25f);
				this.level().addParticle(new BlockParticleOption(ParticleTypes.BLOCK, blockState), x, y, z, 0.0, 0.0, 0.0);
			}
		}
	}

	private static float getAngleFromVec3(@NotNull Vec3 vec3) {
		float angle = (float) Math.atan2(vec3.z(), vec3.x());
		angle = (float) (180F * angle / Math.PI);
		angle = (360F + angle) % 360F;
		return angle;
	}

	@Override
	public void addAdditionalSaveData(@NotNull CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putBoolean("FromBucket", this.fromBucket());
		compound.putInt("DigTicks", this.getDiggingTicks());
		VibrationSystem.Data.CODEC.encodeStart(NbtOps.INSTANCE, this.vibrationData).resultOrPartial(WilderSharedConstants.LOGGER::error).ifPresent(tag -> compound.put("listener", tag));
		compound.putString("EntityPose", this.getPose().name());
		compound.putDouble("prevX", this.prevMovement.x);
		compound.putDouble("prevY", this.prevMovement.y);
		compound.putDouble("prevZ", this.prevMovement.z);
	}

	@Override
	public void readAdditionalSaveData(@NotNull CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		this.setFromBucket(compound.getBoolean("FromBucket"));
		this.setDiggingTicks(compound.getInt("DigTicks"));
		if (compound.contains("listener", 10)) {
			VibrationSystem.Data.CODEC.parse(new Dynamic<>(NbtOps.INSTANCE, compound.getCompound("listener"))).resultOrPartial(WilderSharedConstants.LOGGER::error).ifPresent(data -> this.vibrationData = data);
		}
		if (compound.contains("EntityPose")
			&& (Arrays.stream(Pose.values()).anyMatch(pose -> pose.name().equals(compound.getString("EntityPose"))))
		) {
			this.setPose(Pose.valueOf(compound.getString("EntityPose")));
		}
		this.prevMovement = new Vec3(compound.getDouble("prevX"), compound.getDouble("prevY"), compound.getDouble("prevZ"));
	}

	@Override
	@NotNull
	public VibrationSystem.Data getVibrationData() {
		return this.vibrationData;
	}

	@Override
	@NotNull
	public VibrationSystem.User getVibrationUser() {
		return this.vibrationUser;
	}

	@Override
	public void updateDynamicGameEventListener(@NotNull BiConsumer<DynamicGameEventListener<?>, ServerLevel> listenerConsumer) {
		if (this.level() instanceof ServerLevel serverLevel) {
			listenerConsumer.accept(this.dynamicGameEventListener, serverLevel);
		}
	}

	public class VibrationUser implements VibrationSystem.User {
		private static final int GAME_EVENT_LISTENER_RANGE = 8;
		private final PositionSource positionSource;

		private VibrationUser() {
			this.positionSource = new EntityPositionSource(Crab.this, Crab.this.getEyeHeight());
		}

		@Override
		public int getListenerRadius() {
			return GAME_EVENT_LISTENER_RANGE;
		}

		@Override
		@NotNull
		public PositionSource getPositionSource() {
			return this.positionSource;
		}

		@Override
		@NotNull
		public TagKey<GameEvent> getListenableEvents() {
			return WilderGameEventTags.CRAB_CAN_DETECT;
		}

		@Override
		public boolean canTriggerAvoidVibration() {
			return false;
		}

		@Override
		public boolean canReceiveVibration(@NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull GameEvent gameEvent, GameEvent.@NotNull Context context) {
			return Crab.this.isAlive() && Crab.this.isInvisibleWhileUnderground() && (context.sourceEntity() instanceof Player || gameEvent.is(WilderGameEventTags.CRAB_CAN_ALWAYS_DETECT));
		}

		@Override
		public void onReceiveVibration(@NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull GameEvent gameEvent, @Nullable Entity entity, @Nullable Entity playerEntity, float distance) {
			if (Crab.this.isAlive() && Crab.this.isInvisibleWhileUnderground()) {
				CrabAi.clearDigCooldown(Crab.this);
			}

		}
	}

	public static class CrabGroupData
		extends AgeableMob.AgeableMobGroupData {

		public CrabGroupData() {
			super(false);
		}

	}

	public enum MoveState {
		WALKING,
		CLIMBING,
		DESCENDING;

		public static final EntityDataSerializer<MoveState> SERIALIZER = EntityDataSerializer.simpleEnum(MoveState.class);

		static {
			EntityDataSerializers.registerSerializer(SERIALIZER);
		}
	}
}
