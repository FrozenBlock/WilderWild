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

package net.frozenblock.wilderwild.entity;

import com.mojang.serialization.Dynamic;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import net.frozenblock.lib.block.api.shape.FrozenShapes;
import net.frozenblock.lib.entity.api.EntityUtils;
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
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.network.syncher.EntityDataAccessor;
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
import net.minecraft.world.level.Explosion;
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
	public static final float STEP_HEIGHT = 0.2F;
	public static final double WATER_MOVEMENT_SPEED = 0.576;
	public static final int DIG_LENGTH_IN_TICKS = 95;
	public static final int EMERGE_LENGTH_IN_TICKS = 29;
	public static final double UNDERGROUND_PLAYER_RANGE = 4;
	private static final int DIG_TICKS_UNTIL_PARTICLES = 17;
	private static final int DIG_TICKS_UNTIL_STOP_PARTICLES = 82;
	private static final int EMERGE_TICKS_UNTIL_PARTICLES = 1;
	private static final int EMERGE_TICKS_UNTIL_STOP_PARTICLES = 16;
	public static final float DIGGING_PARTICLE_OFFSET = 0.25F;
	public static final float IDLE_SOUND_VOLUME_PERCENTAGE = 0.065F;
	private static final double LATCH_TO_WALL_FORCE = 0.0195D;
	public static final int SPAWN_CHANCE = 30;
	public static final int SPAWN_CHANCE_COMMON = 90;
	private static final Map<ServerLevelAccessor, Integer> CRABS_PER_LEVEL = new Object2IntOpenHashMap<>();
	private static final EntityDataAccessor<String> MOVE_STATE = SynchedEntityData.defineId(Crab.class, EntityDataSerializers.STRING);
	private static final EntityDataAccessor<Float> TARGET_CLIMBING_ANIM_X = SynchedEntityData.defineId(Crab.class, EntityDataSerializers.FLOAT);
	private static final EntityDataAccessor<Float> TARGET_CLIMBING_ANIM_Y = SynchedEntityData.defineId(Crab.class, EntityDataSerializers.FLOAT);
	private static final EntityDataAccessor<String> CLIMBING_FACE = SynchedEntityData.defineId(Crab.class, EntityDataSerializers.STRING);
	private static final EntityDataAccessor<Float> TARGET_CLIMBING_ANIM_AMOUNT = SynchedEntityData.defineId(Crab.class, EntityDataSerializers.FLOAT);
	private static final EntityDataAccessor<Integer> DIGGING_TICKS = SynchedEntityData.defineId(Crab.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Boolean> FROM_BUCKET = SynchedEntityData.defineId(Crab.class, EntityDataSerializers.BOOLEAN);
	public final AnimationState diggingAnimationState = new AnimationState();
	public final AnimationState emergingAnimationState = new AnimationState();
	public final AnimationState hidingAnimationState = new AnimationState();
	private final DynamicGameEventListener<VibrationSystem.Listener> dynamicGameEventListener;
	private final VibrationSystem.User vibrationUser;
	public Vec3 prevMovement;
	public boolean cancelMovementToDescend;
	// CLIENT VARIABLES
	public float climbAnimX;
	public float prevClimbAnimX;
	public float climbAnimY;
	public float prevClimbAnimY;
	public float prevClimbDirectionAmount;
	public float climbDirectionAmount;
	private VibrationSystem.Data vibrationData;

	public Crab(EntityType<? extends Crab> entityType, Level level) {
		super(entityType, level);
		this.vibrationUser = new Crab.VibrationUser();
		this.vibrationData = new VibrationSystem.Data();
		this.dynamicGameEventListener = new DynamicGameEventListener<>(new VibrationSystem.Listener(this));
		this.jumpControl = new CrabJumpControl(this);
		this.prevMovement = Vec3.ZERO;
		this.setMaxUpStep(STEP_HEIGHT);
		this.setPathfindingMalus(BlockPathTypes.LAVA, -1F);
		this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, -1F);
		this.setPathfindingMalus(BlockPathTypes.WATER, 0F);
		this.setPathfindingMalus(BlockPathTypes.WATER_BORDER, 0F);
		if (EntityConfig.get().unpassableRail) {
			this.setPathfindingMalus(BlockPathTypes.UNPASSABLE_RAIL, 0F);
		}
		this.moveControl = new CrabMoveControl(this);
	}

	@NotNull
	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 8D)
			.add(Attributes.MOVEMENT_SPEED, MOVEMENT_SPEED)
			.add(Attributes.JUMP_STRENGTH, 0D)
			.add(Attributes.ATTACK_DAMAGE, 2D)
			.add(Attributes.FOLLOW_RANGE, MAX_TARGET_DISTANCE);
	}

	public static void clearLevelToCrabCount() {
		CRABS_PER_LEVEL.clear();
	}

	public static boolean checkCrabSpawnRules(@NotNull EntityType<Crab> type, @NotNull ServerLevelAccessor level, @NotNull MobSpawnType spawnType, @NotNull BlockPos pos, @NotNull RandomSource random) {
		if (MobSpawnType.isSpawner(spawnType)) return true;
		if (!EntityConfig.get().crab.spawnCrabs) return false;
		Holder<Biome> biome = level.getBiome(pos);
		int randomBound = SPAWN_CHANCE;
		if (!biome.is(WilderBiomeTags.HAS_COMMON_CRAB)) {
			randomBound = SPAWN_CHANCE_COMMON;
			if (getCrabsPerLevel(level.getLevel()) >= type.getCategory().getMaxInstancesPerChunk() / 3) {
				return false;
			}
		}
		int seaLevel = level.getSeaLevel();
		return random.nextInt(0, randomBound) == 0 && pos.getY() >= seaLevel - 33 && pos.getY() <= seaLevel + 3 && level.getBlockState(pos.below()).is(WilderBlockTags.CRAB_CAN_HIDE);
	}

	public static int getCrabsPerLevel(@NotNull ServerLevel level) {
		AtomicInteger count = new AtomicInteger();
		if (!CRABS_PER_LEVEL.containsKey(level)) {
			EntityUtils.getEntitiesPerLevel(level).forEach(entity -> {
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

	private static float getAngleFromVec3(@NotNull Vec3 vec3) {
		float angle = (float) Math.atan2(vec3.z(), vec3.x());
		angle = (float) (180F * angle / Math.PI);
		angle = (360F + angle) % 360F;
		return angle;
	}

	@Override
	@NotNull
	protected PathNavigation createNavigation(@NotNull Level level) {
		return new WallClimberNavigation(this, level);
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
	public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor level, @NotNull DifficultyInstance difficulty, @NotNull MobSpawnType reason, @Nullable SpawnGroupData spawnData, CompoundTag dataTag) {
		this.getBrain().setMemoryWithExpiry(MemoryModuleType.DIG_COOLDOWN, Unit.INSTANCE, CrabAi.getRandomDigCooldown(this));
		switch (reason) {
			case BUCKET -> {
				return spawnData;
			}
			case NATURAL, TRIGGERED, REINFORCEMENT ->
				this.getBrain().setMemoryWithExpiry(MemoryModuleType.IS_EMERGING, Unit.INSTANCE, EMERGE_LENGTH_IN_TICKS);
			default -> {
			}
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

	@Override
	public float getWalkTargetValue(@NotNull BlockPos pos, @NotNull LevelReader level) {
		return 0F;
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
		this.entityData.define(MOVE_STATE, MoveState.WALKING.name());
		this.entityData.define(TARGET_CLIMBING_ANIM_X, 0F);
		this.entityData.define(TARGET_CLIMBING_ANIM_Y, 0F);
		this.entityData.define(TARGET_CLIMBING_ANIM_AMOUNT, 0F);
		this.entityData.define(DIGGING_TICKS, 0);
		this.entityData.define(FROM_BUCKET, false);
		this.entityData.define(CLIMBING_FACE, ClimbingFace.NORTH.name());
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

	@Override
	public void tick() {
		if (this.isDiggingOrEmerging()) {
			this.xxa = 0F;
			this.zza = 0F;
		}
		boolean isClient = this.level().isClientSide;
		if (this.level() instanceof ServerLevel serverLevel) {
			VibrationSystem.Ticker.tick(serverLevel, this.vibrationData, this.vibrationUser);
		}
		super.tick();
		if (this.isDiggingOrEmerging()) {
			this.xxa = 0F;
			this.zza = 0F;
		}
		if (!isClient) {
			this.cancelMovementToDescend = false;
			if (this.horizontalCollision) {
				Vec3 usedMovement = this.getDeltaMovement();
				this.setMoveState(this.getDeltaPos().y() >= 0 ? MoveState.CLIMBING : MoveState.DESCENDING);
				if (this.isCrabDescending() && this.level().noBlockCollision(this, this.makeBoundingBox().expandTowards(0D, -this.getEmptyAreaSearchDistance(), 0D))) {
					this.cancelMovementToDescend = this.latchOntoWall(LATCH_TO_WALL_FORCE, false);
				} else if (!this.onGround()) {
					//this.latchOntoWall(LATCH_TO_WALL_FORCE, false);
				}
				//TODO: (Treetrain) find a way to get the face the Crab is walking on
				Direction climbedDirection = Direction.getNearest(usedMovement.x(), usedMovement.y(), usedMovement.z());
				this.setClimbingFace(climbedDirection);
				if (usedMovement.x == 0D && usedMovement.z == 0D) usedMovement = this.prevMovement;
				this.setTargetClimbAnimX(
					Math.abs(getAngleFromVec3(usedMovement) - getAngleFromVec3(this.getViewVector(1F))) / 180F
				);
				this.setTargetClimbAnimY(
					this.getClimbingFace().rotation
				);
				this.setTargetClimbAnimAmount(1F);
				this.prevMovement = usedMovement;
			} else {
				this.setMoveState(MoveState.WALKING);
				this.setTargetClimbAnimX(0F);
				if (!this.onGround() && !this.isInWater()) {
					if (this.level().noBlockCollision(this, this.makeBoundingBox().expandTowards(0D, -this.getEmptyAreaSearchDistance(), 0D))) {
						this.cancelMovementToDescend = this.latchOntoWall(LATCH_TO_WALL_FORCE, false);
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
				default -> {
				}
			}
			this.prevClimbAnimX = this.climbAnimX;
			Supplier<Float> climbingVal = () -> (Math.cos(this.targetClimbAnimX() * Mth.PI) >= -0.275F ? -1F : 1F) * (this.isClimbing() ? 1F : -1F);
			this.climbAnimX += ((this.onClimbable() ? climbingVal.get() : 0F) - this.climbAnimX) * 0.2F;
			this.prevClimbAnimY = this.climbAnimY;
			this.climbAnimY += ((this.onClimbable() ? this.getClimbingFace().rotation : 0F) - this.climbDirectionAmount) * 0.2F;
			this.prevClimbDirectionAmount = this.climbDirectionAmount;
			this.climbDirectionAmount += ((this.onClimbable() ? 1F : 0F) - this.climbDirectionAmount) * 0.2F;
		}
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
		if (this.isDiggingOrEmerging()) {
			this.xxa = 0F;
			this.zza = 0F;
		}
	}

	public double getEmptyAreaSearchDistance() {
		return this.isBaby() ? 0.8D : 2D;
	}

	@Nullable
	public Vec3 findNearestWall() {
		BlockPos crabPos = this.blockPosition();
		ArrayList<Vec3> vecs = new ArrayList<>();
		for (BlockPos pos : BlockPos.betweenClosed(crabPos.offset(-1, 0, -1), crabPos.offset(1, 0, 1))) {
			BlockState state = this.level().getBlockState(pos);
			VoxelShape collisionShape = state.getCollisionShape(this.level(), pos, CollisionContext.of(this));
			if (isWallPosSlowable(pos, state, collisionShape)) {
				Optional<Vec3> optionalVec3 = FrozenShapes.closestPointTo(pos, collisionShape, this.position());
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
	public Vec3 getClosestPos(@NotNull List<Vec3> vec3s) {
		double lowestDistance = Double.MAX_VALUE;
		Vec3 selectedVec3 = null;
		Vec3 thisPos = this.getEyePosition();
		for (Vec3 vec3 : vec3s) {
			double distance = vec3.distanceTo(thisPos);
			if (distance < lowestDistance) {
				lowestDistance = distance;
				selectedVec3 = vec3;
			}
		}
		return selectedVec3;
	}

	public boolean isWallPosSlowable(@NotNull BlockPos pos, @NotNull BlockState state, @NotNull VoxelShape collisionShape) {
		if (state.isAir() || state.getFluidState().is(FluidTags.LAVA)) {
			return false;
		}
		return (!collisionShape.isEmpty() && pos.getY() + collisionShape.min(Direction.Axis.Y) <= this.getEyeY()) || (state.getFluidState().is(FluidTags.WATER));
	}

	public boolean latchOntoWall(double latchForce, boolean stopDownwardsMovement) {
		boolean canLatch = false;
		Vec3 wallPos = this.findNearestWall();
		if (wallPos != null) {
			canLatch = true;
			Vec3 differenceBetween = wallPos.subtract(this.position());
			Vec3 deltaMovement = this.getDeltaMovement();
			this.setDeltaMovement(
				deltaMovement.x() + (differenceBetween.x() < 0D ? -latchForce : differenceBetween.x() > 0D ? latchForce : 0D),
				(stopDownwardsMovement ? Math.max(0, deltaMovement.y()) : deltaMovement.y()),
				deltaMovement.z() + (differenceBetween.z() < 0D ? -latchForce : differenceBetween.z() > 0D ? latchForce : 0D)
			);
		}
		return canLatch;
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
			this.playSound(soundEvent, this.getSoundVolume() * IDLE_SOUND_VOLUME_PERCENTAGE, this.getVoicePitch());
		}
	}

	@Override
	public boolean doHurtTarget(@NotNull Entity target) {
		this.level().broadcastEntityEvent(this, EntityEvent.START_ATTACKING);
		this.playSound(RegisterSounds.ENTITY_CRAB_ATTACK, this.getSoundVolume(), this.getVoicePitch());
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
	public boolean ignoreExplosion(Explosion explosion) {
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

	@NotNull
	public Vec3 getDeltaPos() {
		return this.getPosition(1F).subtract(this.getPosition(0F));
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
		return MoveState.valueOf(this.entityData.get(MOVE_STATE));
	}

	public boolean isClimbing() {
		return this.moveState() == MoveState.CLIMBING;
	}

	public boolean isCrabDescending() {
		return this.moveState() == MoveState.DESCENDING;
	}

	public void setMoveState(@NotNull MoveState state) {
		this.entityData.set(MOVE_STATE, state.name());
	}

	public ClimbingFace getClimbingFace() {
		return ClimbingFace.valueOf(this.entityData.get(CLIMBING_FACE));
	}

	public void setClimbingFace(@NotNull Direction direction) {
		this.entityData.set(
			CLIMBING_FACE,
			switch (direction) {
				case EAST -> ClimbingFace.EAST.name();
				case WEST -> ClimbingFace.WEST.name();
				case SOUTH -> ClimbingFace.SOUTH.name();
				default -> ClimbingFace.NORTH.name();
			}
		);
	}

	public float targetClimbAnimX() {
		return this.entityData.get(TARGET_CLIMBING_ANIM_X);
	}

	public void setTargetClimbAnimX(float f) {
		this.entityData.set(TARGET_CLIMBING_ANIM_X, f);
	}

	public float targetClimbAnimY() {
		return this.entityData.get(TARGET_CLIMBING_ANIM_Y);
	}

	public void setTargetClimbAnimY(float f) {
		this.entityData.set(TARGET_CLIMBING_ANIM_Y, f);
	}

	public float targetClimbAnimAmount() {
		return this.entityData.get(TARGET_CLIMBING_ANIM_AMOUNT);
	}

	public void setTargetClimbAnimAmount(float f) {
		this.entityData.set(TARGET_CLIMBING_ANIM_AMOUNT, f);
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
		return stack.is(WilderItemTags.CRAB_FOOD);
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
	@NotNull
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
			double y = this.getY();
			double x = this.getX();
			double z = this.getZ();
			ParticleOptions particleOptions = new BlockParticleOption(ParticleTypes.BLOCK, blockState);
			for (int i = 0; i < 8; ++i) {
				double particleX = x + Mth.randomBetween(randomSource, -DIGGING_PARTICLE_OFFSET, DIGGING_PARTICLE_OFFSET);
				double particleZ = z + Mth.randomBetween(randomSource, -DIGGING_PARTICLE_OFFSET, DIGGING_PARTICLE_OFFSET);
				this.level().addParticle(particleOptions, particleX, y, particleZ, 0D, 0D, 0D);
			}
		}
	}

	public boolean isDitto() {
		return this.hasCustomName() && this.getCustomName().getString().equalsIgnoreCase("ditto");
	}

	@Override
	public void addAdditionalSaveData(@NotNull CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putBoolean("FromBucket", this.fromBucket());
		compound.putInt("DigTicks", this.getDiggingTicks());
		VibrationSystem.Data.CODEC.encodeStart(NbtOps.INSTANCE, this.vibrationData).resultOrPartial(WilderSharedConstants.LOGGER::error).ifPresent(tag -> compound.put("listener", tag));
		compound.putString("EntityPose", this.getPose().name());
		compound.putDouble("PrevX", this.prevMovement.x);
		compound.putDouble("PrevY", this.prevMovement.y);
		compound.putDouble("PrevZ", this.prevMovement.z);
		compound.putBoolean("CancelMovementToDescend", this.cancelMovementToDescend);
		compound.putString("ClimbingFace", this.getClimbingFace().name());
		compound.putFloat("TargetClimbAnimX", this.targetClimbAnimX());
		compound.putFloat("TargetClimbAnimY", this.targetClimbAnimY());
		compound.putFloat("TargetClimbAnimAmount", this.targetClimbAnimAmount());
	}

	@Override
	public void readAdditionalSaveData(@NotNull CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		this.setFromBucket(compound.getBoolean("FromBucket"));
		this.setDiggingTicks(compound.getInt("DigTicks"));
		if (compound.contains("listener", 10)) {
			VibrationSystem.Data.CODEC.parse(new Dynamic<>(NbtOps.INSTANCE, compound.getCompound("listener"))).resultOrPartial(WilderSharedConstants.LOGGER::error).ifPresent(data -> this.vibrationData = data);
		}
		if (compound.contains("EntityPose") && (Arrays.stream(Pose.values()).anyMatch(pose -> pose.name().equals(compound.getString("EntityPose"))))) {
			this.setPose(Pose.valueOf(compound.getString("EntityPose")));
		}
		this.prevMovement = new Vec3(compound.getDouble("PrevX"), compound.getDouble("PrevY"), compound.getDouble("PrevZ"));
		this.cancelMovementToDescend = compound.getBoolean("CancelMovementToDescend");
		if (compound.contains("ClimbingFace") && (Arrays.stream(ClimbingFace.values()).anyMatch(climbingFace -> climbingFace.name().equals(compound.getString("ClimbingFace"))))) {
			this.setClimbingFace(ClimbingFace.valueOf(compound.getString("ClimbingFace")).direction);
		}
		this.setTargetClimbAnimX(compound.getFloat("TargetClimbAnimX"));
		this.setTargetClimbAnimY(compound.getFloat("TargetClimbAnimY"));
		this.setTargetClimbAnimAmount(compound.getFloat("TargetClimbAnimAmount"));
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

	public enum MoveState {
		WALKING("walking"),
		CLIMBING("climbing"),
		DESCENDING("descending");

		public final String name;

		MoveState(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return this.name;
		}
	}

	public enum ClimbingFace {
		NORTH("north", Direction.NORTH, -90F),
		EAST("east", Direction.EAST, 90F),
		SOUTH("south", Direction.SOUTH, 0F),
		WEST("west", Direction.WEST, 180F);

		public final Direction direction;
		public final String name;
		public final float rotation;

		ClimbingFace(String name, Direction direction, float rotation) {
			this.name = name;
			this.direction = direction;
			this.rotation = rotation;
		}

		@Override
		public String toString() {
			return this.name;
		}
	}

	public static class CrabGroupData
		extends AgeableMob.AgeableMobGroupData {

		public CrabGroupData() {
			super(false);
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
}
