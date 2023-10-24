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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import net.frozenblock.wilderwild.config.EntityConfig;
import net.frozenblock.wilderwild.entity.ai.crab.CrabAi;
import net.frozenblock.wilderwild.entity.ai.crab.CrabJumpControl;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.registry.RegisterEntities;
import net.frozenblock.wilderwild.registry.RegisterItems;
import net.frozenblock.wilderwild.registry.RegisterMemoryModuleTypes;
import net.frozenblock.wilderwild.registry.RegisterSensorTypes;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.frozenblock.wilderwild.tag.WilderBiomeTags;
import net.frozenblock.wilderwild.tag.WilderBlockTags;
import net.frozenblock.wilderwild.tag.WilderGameEventTags;
import net.frozenblock.wilderwild.tag.WilderItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.BlockParticleOption;
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
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
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
import net.minecraft.world.phys.Vec3;
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
	protected static final List<SensorType<? extends Sensor<? super Crab>>> SENSORS = List.of(
		SensorType.NEAREST_LIVING_ENTITIES,
		SensorType.NEAREST_PLAYERS,
		SensorType.NEAREST_ADULT,
		SensorType.HURT_BY,
		RegisterSensorTypes.CRAB_TEMPTATIONS,
		RegisterSensorTypes.CRAB_SPECIFIC_SENSOR,
		RegisterSensorTypes.CRAB_NEARBY_PLAYER_SENSOR,
		RegisterSensorTypes.CRAB_CAN_DIG_SENSOR
	);
	protected static final List<? extends MemoryModuleType<?>> MEMORY_MODULES = List.of(
		MemoryModuleType.NEAREST_LIVING_ENTITIES,
		MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES,
		MemoryModuleType.NEAREST_PLAYERS,
		MemoryModuleType.NEAREST_VISIBLE_PLAYER,
		MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER,
		RegisterMemoryModuleTypes.IS_PLAYER_NEARBY,
		MemoryModuleType.LOOK_TARGET,
		MemoryModuleType.WALK_TARGET,
		MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
		MemoryModuleType.PATH,
		MemoryModuleType.ATTACK_TARGET,
		MemoryModuleType.NEAREST_ATTACKABLE,
		MemoryModuleType.ATTACK_COOLING_DOWN,
		MemoryModuleType.HURT_BY,
		MemoryModuleType.HURT_BY_ENTITY,
		MemoryModuleType.IS_PANICKING,
		MemoryModuleType.IS_EMERGING,
		MemoryModuleType.DIG_COOLDOWN,
		RegisterMemoryModuleTypes.CAN_DIG,
		MemoryModuleType.TEMPTING_PLAYER,
		MemoryModuleType.IS_TEMPTED,
		MemoryModuleType.TEMPTATION_COOLDOWN_TICKS,
		MemoryModuleType.BREED_TARGET,
		RegisterMemoryModuleTypes.IS_UNDERGROUND,
		RegisterMemoryModuleTypes.NEARBY_CRABS,
		RegisterMemoryModuleTypes.HEAL_COOLDOWN_TICKS,
		RegisterMemoryModuleTypes.FIRST_BRAIN_TICK
	);
	private static final int DIG_TICKS_UNTIL_PARTICLES = 17;
	private static final int DIG_TICKS_UNTIL_STOP_PARTICLES = 82;
	private static final int EMERGE_TICKS_UNTIL_PARTICLES = 1;
	private static final int EMERGE_TICKS_UNTIL_STOP_PARTICLES = 16;
	private static final EntityDataAccessor<Byte> DATA_FLAGS_ID = SynchedEntityData.defineId(Crab.class, EntityDataSerializers.BYTE);
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
	}

	@Override
	@NotNull
	protected PathNavigation createNavigation(Level level) {
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
		return Brain.provider(MEMORY_MODULES, SENSORS);
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
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag dataTag) {
		this.getBrain().setMemoryWithExpiry(MemoryModuleType.DIG_COOLDOWN, Unit.INSTANCE, CrabAi.getRandomDigCooldown(this));
		switch (reason) {
			case BUCKET -> { return spawnData; }
			case NATURAL -> this.getBrain().setMemoryWithExpiry(MemoryModuleType.IS_EMERGING, Unit.INSTANCE, EMERGE_LENGTH_IN_TICKS);
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
	public float getWalkTargetValue(BlockPos pos, LevelReader level) {
		return 0.0f;
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
		this.entityData.define(DATA_FLAGS_ID, (byte) 0);
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

	@Override
	public void tick() {
		boolean isClient = this.level().isClientSide;
		if (this.level() instanceof ServerLevel serverLevel) {
			VibrationSystem.Ticker.tick(serverLevel, this.vibrationData, this.vibrationUser);
		}
		super.tick();
		if (!isClient) {
			this.setClimbing(this.horizontalCollision);
			if (this.isClimbing()) {
				Vec3 usedMovement = this.getDeltaMovement();
				if (usedMovement.x == 0 && usedMovement.z == 0) usedMovement = this.prevMovement;
				this.setTargetClimbAnimX(Math.abs(getAngleFromVec3(usedMovement) - getAngleFromVec3(this.getViewVector(1F))) / 180F);
				this.prevMovement = usedMovement;
			} else {
				this.setTargetClimbAnimX(0F);
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
		Supplier<Float> climbingVal = () -> Math.cos(this.targetClimbAnimX() * Mth.PI) >= -0.275F ? -1F : 1F;
		this.climbAnimX += ((this.isClimbing() ? climbingVal.get() : 0F) - this.climbAnimX) * 0.2F;
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
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
	protected SoundEvent getHurtSound(DamageSource damageSource) {
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
	public boolean doHurtTarget(Entity target) {
		this.level().broadcastEntityEvent(this, EntityEvent.START_ATTACKING);
		this.playSound(RegisterSounds.ENTITY_CRAB_ATTACK, 1.0F, this.getVoicePitch());
		return super.doHurtTarget(target);
	}

	@Override
	public boolean isInvulnerableTo(DamageSource source) {
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
	protected void doPush(Entity entity) {
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
	public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
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
		} else if (DIGGING_TICKS.equals(key) && this.getDiggingTicks() > DIG_LENGTH_IN_TICKS) {
			if (this.getPose() == Pose.DIGGING) {
				this.diggingAnimationState.stop();
				this.emergingAnimationState.stop();
				this.hidingAnimationState.start(this.tickCount);
			}
		}
		super.onSyncedDataUpdated(key);
	}

	@Override
	public boolean onClimbable() {
		return this.isClimbing();
	}

	public boolean isClimbing() {
		return (this.entityData.get(DATA_FLAGS_ID) & 1) != 0;
	}

	public void setClimbing(boolean climbing) {
		byte b = this.entityData.get(DATA_FLAGS_ID);
		b = climbing ? (byte) (b | 1) : (byte) (b & 0xFFFFFFFE);
		this.entityData.set(DATA_FLAGS_ID, b);
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
		super.calculateEntityAnimation(this.isClimbing() || includeHeight);
	}

	@Nullable
	@Override
	public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
		Crab crab = RegisterEntities.CRAB.create(level);
		if (crab != null) {
			crab.setPersistenceRequired();
			crab.getBrain().setMemoryWithExpiry(MemoryModuleType.DIG_COOLDOWN, Unit.INSTANCE, CrabAi.getRandomDigCooldown(crab));
		}
		return crab;
	}

	@Override
	public InteractionResult mobInteract(Player player, InteractionHand hand) {
		return Bucketable.bucketMobPickup(player, hand, this).orElse(super.mobInteract(player, hand));
	}

	@Override
	public void saveToBucketTag(ItemStack stack) {
		Bucketable.saveDefaultDataToBucketTag(this, stack);
		CompoundTag compoundTag = stack.getOrCreateTag();
		compoundTag.putInt("Age", this.getAge());
	}

	@Override
	public void loadFromBucketTag(CompoundTag tag) {
		Bucketable.loadDefaultDataFromBucketTag(this, tag);
		if (tag.contains("Age")) {
			this.setAge(tag.getInt("Age"));
		}
	}

	@Override
	public ItemStack getBucketItemStack() {
		return new ItemStack(RegisterItems.CRAB_BUCKET);
	}

	@Override
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
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putBoolean("FromBucket", this.fromBucket());
		compound.putInt("DigTicks", this.getDiggingTicks());
		VibrationSystem.Data.CODEC.encodeStart(NbtOps.INSTANCE, this.vibrationData).resultOrPartial(WilderSharedConstants.LOGGER::error).ifPresent(tag -> compound.put("listener", tag));
		compound.putString("EntityPose", this.getPose().name());
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		this.setFromBucket(compound.getBoolean("FromBucket"));
		this.setDiggingTicks(compound.getInt("DigTicks"));
		if (compound.contains("listener", 10)) {
			VibrationSystem.Data.CODEC.parse(new Dynamic<>(NbtOps.INSTANCE, compound.getCompound("listener"))).resultOrPartial(WilderSharedConstants.LOGGER::error).ifPresent(data -> this.vibrationData = data);
		}
		if (compound.contains("EntityPose")) {
			if (Arrays.stream(Pose.values()).anyMatch(pose -> pose.name().equals(compound.getString("EntityPose")))) {
				this.setPose(Pose.valueOf(compound.getString("EntityPose")));
			}
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
	public void updateDynamicGameEventListener(BiConsumer<DynamicGameEventListener<?>, ServerLevel> listenerConsumer) {
		if (this.level() instanceof ServerLevel serverLevel) {
			listenerConsumer.accept(this.dynamicGameEventListener, serverLevel);
		}
	}

	public class VibrationUser implements VibrationSystem.User {
		private static final int GAME_EVENT_LISTENER_RANGE = 8;
		private final PositionSource positionSource;

		VibrationUser() {
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
		public boolean canReceiveVibration(ServerLevel level, BlockPos pos, GameEvent gameEvent, GameEvent.Context context) {
			return Crab.this.isAlive() && Crab.this.isInvisibleWhileUnderground() && (context.sourceEntity() instanceof Player || gameEvent.is(WilderGameEventTags.CRAB_CAN_ALWAYS_DETECT));
		}

		@Override
		public void onReceiveVibration(ServerLevel level, BlockPos pos, GameEvent gameEvent, @Nullable Entity entity, @Nullable Entity playerEntity, float distance) {
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

}
