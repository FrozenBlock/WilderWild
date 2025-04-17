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

package net.frozenblock.wilderwild.entity;

import com.mojang.serialization.Dynamic;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import net.frozenblock.lib.block.api.shape.FrozenShapes;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.config.WWEntityConfig;
import net.frozenblock.wilderwild.entity.ai.crab.CrabAi;
import net.frozenblock.wilderwild.entity.ai.crab.CrabJumpControl;
import net.frozenblock.wilderwild.entity.ai.crab.CrabMoveControl;
import net.frozenblock.wilderwild.entity.ai.crab.CrabNavigation;
import net.frozenblock.wilderwild.entity.variant.crab.CrabVariant;
import net.frozenblock.wilderwild.entity.variant.crab.CrabVariants;
import net.frozenblock.wilderwild.registry.WWEntityTypes;
import net.frozenblock.wilderwild.registry.WWItems;
import net.frozenblock.wilderwild.registry.WWMemoryModuleTypes;
import net.frozenblock.wilderwild.registry.WWSounds;
import net.frozenblock.wilderwild.registry.WilderWildRegistries;
import net.frozenblock.wilderwild.tag.WWBlockTags;
import net.frozenblock.wilderwild.tag.WWGameEventTags;
import net.frozenblock.wilderwild.tag.WWItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Unit;
import net.minecraft.util.profiling.Profiler;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityEvent;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.VariantHolder;
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
import net.minecraft.world.item.component.CustomData;
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
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Math;

public class Crab extends Animal implements VibrationSystem, Bucketable, VariantHolder<CrabVariant> {
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
	public static final float IDLE_SOUND_VOLUME_PERCENTAGE = 0.2F;
	private static final double LATCH_TO_WALL_FORCE = 0.0195D;
	private static final EntityDataAccessor<String> MOVE_STATE = SynchedEntityData.defineId(Crab.class, EntityDataSerializers.STRING);
	private static final EntityDataAccessor<Float> TARGET_CLIMBING_ANIM_X = SynchedEntityData.defineId(Crab.class, EntityDataSerializers.FLOAT);
	private static final EntityDataAccessor<Float> TARGET_CLIMBING_ANIM_Y = SynchedEntityData.defineId(Crab.class, EntityDataSerializers.FLOAT);
	private static final EntityDataAccessor<String> CLIMBING_FACE = SynchedEntityData.defineId(Crab.class, EntityDataSerializers.STRING);
	private static final EntityDataAccessor<Float> TARGET_CLIMBING_ANIM_AMOUNT = SynchedEntityData.defineId(Crab.class, EntityDataSerializers.FLOAT);
	private static final EntityDataAccessor<Integer> DIGGING_TICKS = SynchedEntityData.defineId(Crab.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Boolean> FROM_BUCKET = SynchedEntityData.defineId(Crab.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<String> VARIANT = SynchedEntityData.defineId(Crab.class, EntityDataSerializers.STRING);
	public final AnimationState diggingAnimationState = new AnimationState();
	public final AnimationState emergingAnimationState = new AnimationState();
	public final AnimationState hidingAnimationState = new AnimationState();
	private final DynamicGameEventListener<VibrationSystem.Listener> dynamicGameEventListener;
	private final VibrationSystem.User vibrationUser;
	private VibrationSystem.Data vibrationData;
	public Vec3 prevMovement;
	public boolean cancelMovementToDescend;

	// CLIENT VARIABLES
	public float climbAnimX;
	public float prevClimbAnimX;
	private Optional<CrabVariant> crabVariant = Optional.empty();

	public Crab(EntityType<? extends Crab> entityType, Level level) {
		super(entityType, level);
		this.vibrationUser = new Crab.VibrationUser();
		this.vibrationData = new VibrationSystem.Data();
		this.dynamicGameEventListener = new DynamicGameEventListener<>(new VibrationSystem.Listener(this));
		this.jumpControl = new CrabJumpControl(this);
		this.prevMovement = Vec3.ZERO;
		this.setPathfindingMalus(PathType.LAVA, -1F);
		this.setPathfindingMalus(PathType.DANGER_FIRE, -1F);
		this.setPathfindingMalus(PathType.WATER, 0F);
		this.setPathfindingMalus(PathType.WATER_BORDER, 0F);
		if (WWEntityConfig.get().unpassableRail) {
			this.setPathfindingMalus(PathType.UNPASSABLE_RAIL, 0F);
		}
		this.moveControl = new CrabMoveControl(this);
	}

	@NotNull
	public static AttributeSupplier.Builder createAttributes() {
		return Animal.createAnimalAttributes().add(Attributes.MAX_HEALTH, 8D)
			.add(Attributes.MOVEMENT_SPEED, MOVEMENT_SPEED)
			.add(Attributes.STEP_HEIGHT, STEP_HEIGHT)
			.add(Attributes.JUMP_STRENGTH, 0D)
			.add(Attributes.ATTACK_DAMAGE, 2D)
			.add(Attributes.FOLLOW_RANGE, MAX_TARGET_DISTANCE);
	}

	public static boolean checkCrabSpawnRules(
		@NotNull EntityType<Crab> type, @NotNull ServerLevelAccessor level, @NotNull EntitySpawnReason spawnType, @NotNull BlockPos pos, @NotNull RandomSource random
	) {
		if (EntitySpawnReason.isSpawner(spawnType)) return true;
		if (!WWEntityConfig.get().crab.spawnCrabs) return false;
		int seaLevel = level.getSeaLevel();
		return pos.getY() >= seaLevel - 33 && level.getBlockState(pos.below()).is(WWBlockTags.CRAB_HIDEABLE);
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
		return new CrabNavigation(this, level);
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
	public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor level, @NotNull DifficultyInstance difficulty, @NotNull EntitySpawnReason reason, @Nullable SpawnGroupData spawnData) {
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

		Holder<Biome> holder = level.getBiome(this.blockPosition());
		if (spawnData instanceof CrabSpawnGroupData crabGroupData) {
			if (crabGroupData.getGroupSize() >= 2) this.setAge(-24000);
			this.setVariant(crabGroupData.type.value());
		} else {
			Holder<CrabVariant> crabVariantHolder = CrabVariants.getSpawnVariant(this.registryAccess(), holder, level.getRandom());
			spawnData = new CrabSpawnGroupData(crabVariantHolder);
			this.setVariant(crabVariantHolder.value());
		}
		return super.finalizeSpawn(level, difficulty, reason, spawnData);
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
		return super.isInvisible() || this.isHidingUnderground();
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(MOVE_STATE, MoveState.WALKING.name());
		builder.define(TARGET_CLIMBING_ANIM_X, 0F);
		builder.define(TARGET_CLIMBING_ANIM_Y, 0F);
		builder.define(TARGET_CLIMBING_ANIM_AMOUNT, 0F);
		builder.define(DIGGING_TICKS, 0);
		builder.define(FROM_BUCKET, false);
		builder.define(CLIMBING_FACE, ClimbingFace.NORTH.name());
		builder.define(VARIANT, CrabVariants.DEFAULT.location().toString());
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
		boolean isClient = this.level().isClientSide;
		if (this.level() instanceof ServerLevel serverLevel) {
			VibrationSystem.Ticker.tick(serverLevel, this.vibrationData, this.vibrationUser);
		}
		super.tick();
		if (!isClient) {
			this.cancelMovementToDescend = false;
			if (this.horizontalCollision) {
				this.setMoveState(this.getDeltaPos().y() >= 0 ? MoveState.CLIMBING : MoveState.DESCENDING);
				if (this.isCrabDescending() && this.level().noBlockCollision(this, this.makeBoundingBox().expandTowards(0D, -this.getEmptyAreaSearchDistance(), 0D))) {
					this.cancelMovementToDescend = this.latchOntoWall(LATCH_TO_WALL_FORCE, false);
				}
				Vec3 usedMovement = this.getDeltaMovement();
				Direction climbedDirection = Direction.getApproximateNearest(usedMovement.x(), 0D, usedMovement.z());
				this.setClimbingFace(climbedDirection);
				if (usedMovement.x == 0D && usedMovement.z == 0D) usedMovement = this.prevMovement;
				this.setTargetClimbAnimX(
					Math.abs(getAngleFromVec3(usedMovement) - getAngleFromVec3(this.getViewVector(1F))) / 180F
				);
				this.setTargetClimbAnimAmount(1F);
				this.prevMovement = usedMovement;

				if (this.onClimbable()) {
					Optional<Vec3> potentialNearestWallDifference = this.differenceToWallPos();
					if (potentialNearestWallDifference.isPresent()) {
						Vec3 nearestWallDifference = potentialNearestWallDifference.get();
						Direction wallDirection = Direction.getApproximateNearest(nearestWallDifference.x(), 0D, nearestWallDifference.z());

						Vec3 climbOffset = new Vec3(wallDirection.getStepX(), 0D, wallDirection.getStepZ());
						this.lookControl.setLookAt(this.getEyePosition().add(climbOffset));
						this.setYRot(wallDirection.toYRot());
						this.setYHeadRot(wallDirection.toYRot());
						this.setYBodyRot(wallDirection.toYRot());
					}
				}
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

			boolean onClimbable = this.onClimbable();
			this.prevClimbAnimX = this.climbAnimX;
			Supplier<Float> climbingVal = () -> (Math.cos(this.targetClimbAnimX() * Mth.PI) >= -0.275F ? -1F : 1F);
			this.climbAnimX += ((onClimbable ? climbingVal.get() : 0F) - this.climbAnimX) * 0.2F;
		}
	}

	@Override
	public boolean hurtServer(ServerLevel level, @NotNull DamageSource source, float amount) {
		boolean actuallyHurt = super.hurtServer(level, source, amount);
		if (actuallyHurt) {
			if (source.getEntity() instanceof LivingEntity livingEntity) {
				CrabAi.wasHurtBy(level, this, livingEntity);
			}
			if (!this.isDiggingOrEmerging()) {
				CrabAi.setDigCooldown(this);
			}
		}
		return actuallyHurt;
	}

	@Override
	protected void customServerAiStep(ServerLevel level) {
		ProfilerFiller profiler = Profiler.get();
		profiler.push("crabBrain");
		this.getBrain().tick(level, this);
		profiler.pop();
		profiler.push("crabActivityUpdate");
		CrabAi.updateActivity(this);
		profiler.pop();
		super.customServerAiStep(level);
		this.getBrain().setMemory(WWMemoryModuleTypes.FIRST_BRAIN_TICK, Unit.INSTANCE);
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

	private @NotNull Optional<Vec3> differenceToWallPos() {
		Vec3 wallPos = this.findNearestWall();
		if (wallPos != null) return Optional.of(wallPos.subtract(this.position()));
		return Optional.empty();
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
		return WWSounds.ENTITY_CRAB_HURT;
	}

	@Nullable
	@Override
	protected SoundEvent getDeathSound() {
		return WWSounds.ENTITY_CRAB_DEATH;
	}

	@Override
	protected void playStepSound(BlockPos blockPos, BlockState blockState) {
		this.playSound(WWSounds.ENTITY_CRAB_STEP, 0.3F, this.getVoicePitch());
	}

	@Nullable
	@Override
	protected SoundEvent getAmbientSound() {
		return this.isHidingUnderground() ? null : WWSounds.ENTITY_CRAB_IDLE;
	}

	@Override
	public int getAmbientSoundInterval() {
		return 400;
	}

	@Override
	public void playAmbientSound() {
		SoundEvent soundEvent = this.getAmbientSound();
		if (soundEvent != null) {
			this.playSound(soundEvent, this.getSoundVolume() * IDLE_SOUND_VOLUME_PERCENTAGE, this.getVoicePitch());
		}
	}

	@Override
	protected float nextStep() {
		return this.moveDist + 0.55F;
	}

	@Override
	public boolean doHurtTarget(ServerLevel serverLevel, Entity entity) {
		this.level().broadcastEntityEvent(this, EntityEvent.START_ATTACKING);
		this.playSound(WWSounds.ENTITY_CRAB_ATTACK, this.getSoundVolume(), this.getVoicePitch());
		return super.doHurtTarget(serverLevel, entity);
	}

	@Override
	public boolean isInvulnerableTo(ServerLevel level, @NotNull DamageSource source) {
		if (this.isDiggingOrEmerging() && !source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
			return true;
		}
		return super.isInvulnerableTo(level, source);
	}

	@Override
	public boolean isPushable() {
		return !this.isDiggingOrEmerging() && super.isPushable();
	}

	@Override
	protected void doPush(@NotNull Entity entity) {
		if (this.isHidingUnderground()) return;
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

	public boolean isHidingUnderground() {
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
			&& livingEntity.getType() != WWEntityTypes.CRAB
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
			&& this.level().getBlockState(onPos).is(WWBlockTags.CRAB_HIDEABLE);
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
		} else if (VARIANT.equals(key)) {
			this.crabVariant = Optional.of(this.getVariantByLocation());
		}
		super.onSyncedDataUpdated(key);
	}

	@Override
	public boolean onClimbable() {
		return this.isCrabClimbing() || this.isCrabDescending();
	}

	@Override
	public boolean isDescending() {
		return this.isCrabDescending();
	}

	public MoveState moveState() {
		return MoveState.valueOf(this.entityData.get(MOVE_STATE));
	}

	public boolean isCrabClimbing() {
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
		return stack.is(WWItemTags.CRAB_FOOD);
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

	@Nullable
	@Override
	public AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob otherParent) {
		Crab crab = WWEntityTypes.CRAB.create(level, EntitySpawnReason.BREEDING);
		if (crab != null) {
			crab.setPersistenceRequired();
			crab.getBrain().setMemoryWithExpiry(MemoryModuleType.DIG_COOLDOWN, Unit.INSTANCE, CrabAi.getRandomDigCooldown(crab));
			if (otherParent instanceof Crab otherCrab) {
				crab.setVariant(level.random.nextBoolean() ? this.getVariantByLocation() : otherCrab.getVariantByLocation());
			}
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
		CustomData.update(DataComponents.BUCKET_ENTITY_DATA, stack, compoundTag -> {
			compoundTag.putString("Variant", this.getVariantLocation().toString());
			compoundTag.putInt("Age", this.getAge());
			Brain<Crab> brain = this.getBrain();
			if (brain.hasMemoryValue(MemoryModuleType.HAS_HUNTING_COOLDOWN)) {
				compoundTag.putLong("HuntingCooldown", brain.getTimeUntilExpiry(MemoryModuleType.HAS_HUNTING_COOLDOWN));
			}
		});
	}

	@Override
	public void loadFromBucketTag(@NotNull CompoundTag tag) {
		Bucketable.loadDefaultDataFromBucketTag(this, tag);
		if (tag.contains("Age")) this.setAge(tag.getInt("Age"));
		if (tag.contains("HuntingCooldown")) this.getBrain().setMemoryWithExpiry(MemoryModuleType.HAS_HUNTING_COOLDOWN, true, tag.getLong("HuntingCooldown"));
		Optional.ofNullable(ResourceLocation.tryParse(tag.getString("Variant")))
			.map(resourceLocation -> ResourceKey.create(WilderWildRegistries.CRAB_VARIANT, resourceLocation))
			.flatMap(resourceKey -> this.registryAccess().lookupOrThrow(WilderWildRegistries.CRAB_VARIANT).get(resourceKey))
			.ifPresent(reference -> this.setVariant(reference.value()));
	}

	@Override
	@NotNull
	public ItemStack getBucketItemStack() {
		return new ItemStack(WWItems.CRAB_BUCKET);
	}

	@Override
	@NotNull
	public SoundEvent getPickupSound() {
		return WWSounds.ITEM_BUCKET_FILL_CRAB;
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

	public ResourceLocation getVariantLocation() {
		return ResourceLocation.parse(this.entityData.get(VARIANT));
	}

	public CrabVariant getVariantByLocation() {
		return this.registryAccess().lookupOrThrow(WilderWildRegistries.CRAB_VARIANT).getValue(this.getVariantLocation());
	}

	public Holder<CrabVariant> getVariantAsHolder() {
		return this.registryAccess().lookupOrThrow(WilderWildRegistries.CRAB_VARIANT).get(this.getVariantLocation()).orElseThrow();
	}

	public CrabVariant getVariantForRendering() {
		return this.crabVariant.orElse(this.registryAccess().lookupOrThrow(WilderWildRegistries.CRAB_VARIANT).getValue(CrabVariants.DEFAULT));
	}

	@Override
	public void setVariant(@NotNull CrabVariant variant) {
		this.entityData.set(VARIANT, Objects.requireNonNull(this.registryAccess().lookupOrThrow(WilderWildRegistries.CRAB_VARIANT).getKey(variant)).toString());
	}

	@Override
	public @NotNull CrabVariant getVariant() {
		return this.getVariantByLocation();
	}

	public void setVariant(@NotNull ResourceLocation variant) {
		this.entityData.set(VARIANT, variant.toString());
	}

	@Override
	public void addAdditionalSaveData(@NotNull CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putString("variant", this.getVariantLocation().toString());
		compound.putBoolean("FromBucket", this.fromBucket());
		compound.putInt("DigTicks", this.getDiggingTicks());
		compound.putString("EntityPose", this.getPose().name());
		compound.putDouble("PrevX", this.prevMovement.x);
		compound.putDouble("PrevY", this.prevMovement.y);
		compound.putDouble("PrevZ", this.prevMovement.z);
		compound.putBoolean("CancelMovementToDescend", this.cancelMovementToDescend);
		compound.putString("ClimbingFace", this.getClimbingFace().name());
		compound.putFloat("TargetClimbAnimX", this.targetClimbAnimX());
		compound.putFloat("TargetClimbAnimAmount", this.targetClimbAnimAmount());

		RegistryOps<Tag> registryOps = this.registryAccess().createSerializationContext(NbtOps.INSTANCE);
		VibrationSystem.Data.CODEC
			.encodeStart(registryOps, this.vibrationData)
			.resultOrPartial(string -> WWConstants.LOGGER.error("Failed to encode vibration listener for Crab: '{}'", string))
			.ifPresent(tag -> compound.put("listener", tag));
	}

	@Override
	public void readAdditionalSaveData(@NotNull CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		Optional.ofNullable(ResourceLocation.tryParse(compound.getString("variant")))
			.map(resourceLocation -> ResourceKey.create(WilderWildRegistries.CRAB_VARIANT, resourceLocation))
			.flatMap(resourceKey -> this.registryAccess().lookupOrThrow(WilderWildRegistries.CRAB_VARIANT).get(resourceKey))
			.ifPresent(reference -> this.setVariant(reference.value()));
		this.setFromBucket(compound.getBoolean("FromBucket"));
		this.setDiggingTicks(compound.getInt("DigTicks"));
		if (compound.contains("EntityPose") && (Arrays.stream(Pose.values()).anyMatch(pose -> pose.name().equals(compound.getString("EntityPose"))))) {
			this.setPose(Pose.valueOf(compound.getString("EntityPose")));
		}
		this.prevMovement = new Vec3(compound.getDouble("PrevX"), compound.getDouble("PrevY"), compound.getDouble("PrevZ"));
		this.cancelMovementToDescend = compound.getBoolean("CancelMovementToDescend");
		if (compound.contains("ClimbingFace") && (Arrays.stream(ClimbingFace.values()).anyMatch(climbingFace -> climbingFace.name().equals(compound.getString("ClimbingFace"))))) {
			this.setClimbingFace(ClimbingFace.valueOf(compound.getString("ClimbingFace")).direction);
		}
		this.setTargetClimbAnimX(compound.getFloat("TargetClimbAnimX"));
		this.setTargetClimbAnimAmount(compound.getFloat("TargetClimbAnimAmount"));

		if (compound.contains("listener", 10)) {
			RegistryOps<Tag> registryOps = this.registryAccess().createSerializationContext(NbtOps.INSTANCE);
			VibrationSystem.Data.CODEC
				.parse(new Dynamic<>(registryOps, compound.getCompound("listener")))
				.resultOrPartial(string -> WWConstants.LOGGER.error("Failed to decode vibration listener for Warden: '{}'", string))
				.ifPresent(data -> this.vibrationData = data);
		}
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

	public static class CrabSpawnGroupData extends AgeableMob.AgeableMobGroupData {
		public final Holder<CrabVariant> type;

		public CrabSpawnGroupData(Holder<CrabVariant> holder) {
			super(false);
			this.type = holder;
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
			return WWGameEventTags.CRAB_CAN_DETECT;
		}

        @Override
		public boolean canReceiveVibration(@NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull Holder<GameEvent> gameEvent, GameEvent.@NotNull Context context) {
			return Crab.this.isAlive() && Crab.this.isHidingUnderground() && (context.sourceEntity() instanceof Player || gameEvent.is(WWGameEventTags.CRAB_CAN_ALWAYS_DETECT));
		}

		@Override
		public void onReceiveVibration(@NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull Holder<GameEvent> gameEvent, @Nullable Entity entity, @Nullable Entity playerEntity, float distance) {
			if (Crab.this.isAlive() && Crab.this.isHidingUnderground()) {
				CrabAi.clearDigCooldown(Crab.this);
			}

		}
	}
}
