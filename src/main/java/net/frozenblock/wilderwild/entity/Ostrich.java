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
import java.util.List;
import net.frozenblock.lib.math.api.AdvancedMath;
import net.frozenblock.lib.screenshake.api.ScreenShakeManager;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.config.WWEntityConfig;
import net.frozenblock.wilderwild.entity.ai.ostrich.OstrichAi;
import net.frozenblock.wilderwild.entity.ai.ostrich.OstrichBodyRotationControl;
import net.frozenblock.wilderwild.entity.ai.ostrich.OstrichLookControl;
import net.frozenblock.wilderwild.entity.ai.ostrich.OstrichMoveControl;
import net.frozenblock.wilderwild.registry.WWDamageTypes;
import net.frozenblock.wilderwild.registry.WWEntityTypes;
import net.frozenblock.wilderwild.registry.WWSounds;
import net.frozenblock.wilderwild.tag.WWBlockTags;
import net.frozenblock.wilderwild.tag.WWItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Unit;
import net.minecraft.util.profiling.Profiler;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.Difficulty;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityReference;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PlayerRideableJumping;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.Equippable;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Ostrich extends AbstractHorse implements PlayerRideableJumping {
	public static final @NotNull ResourceLocation ATTACK_MODIFIER_UUID = WWConstants.id("additional_damage_rider");
	public static final @NotNull ResourceLocation KNOCKBACK_MODIFIER_UUID = WWConstants.id("additional_knockback_rider");
	public static final int BEAK_COOLDOWN_TICKS = 30;
	public static final int BEAK_COOLDOWN_TICKS_SUCCESSFUL_HIT = 20;
	public static final int BEAK_STUCK_TICKS = 36;
	public static final int BEAK_STUCK_TICKS_AGGRESSIVE = 20;
	public static final float MAX_ATTACK_DAMAGE = 6F;
	public static final float ADDITIONAL_DAMAGE_RIDER = 1F;
	public static final float ADDITIONAL_KNOCKBACK_RIDER = 0.5F;
	public static final double ATTACK_BOX_WIDTH = 0.5F;
	public static final double ATTACK_BOX_HEIGHT = 0.6F;
	public static final double DIMENSION_PERCENTAGE_AT_NECK = 0.5163043478260869D;
	public static final AttributeModifier ADDITIONAL_DAMAGE_RIDER_MODIFIER = new AttributeModifier(
		ATTACK_MODIFIER_UUID,
		ADDITIONAL_DAMAGE_RIDER,
		AttributeModifier.Operation.ADD_VALUE
	);

	public static final EntityDataAccessor<Float> TARGET_BEAK_ANIM_PROGRESS = SynchedEntityData.defineId(Ostrich.class, EntityDataSerializers.FLOAT);
	public static final EntityDataAccessor<Float> TARGET_STRAIGHT_PROGRESS = SynchedEntityData.defineId(Ostrich.class, EntityDataSerializers.FLOAT);
	public static final EntityDataAccessor<Boolean> IS_ATTACKING = SynchedEntityData.defineId(Ostrich.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Integer> BEAK_COOLDOWN = SynchedEntityData.defineId(Ostrich.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> STUCK_TICKS = SynchedEntityData.defineId(Ostrich.class, EntityDataSerializers.INT);
	@Nullable
	private EntityReference<Entity> lastAttackCommander;
	public boolean attackHasCommander;
	public boolean commanderWasPlayer;
	private float prevStraightProgress;
	private float straightProgress;
	private float prevBeakAnimProgress;
	private float beakAnimProgress;
	@Nullable
	private Vec3 prevBeakPosition;
	@Nullable
	private Vec3 beakPosition;
	@Nullable
	private BlockState beakState;
	@Nullable
	private VoxelShape beakVoxelShape;

	public Ostrich(EntityType<? extends Ostrich> entityType, Level level) {
		super(entityType, level);
		this.moveControl = new OstrichMoveControl(this);
		this.lookControl = new OstrichLookControl(this);
		GroundPathNavigation groundPathNavigation = (GroundPathNavigation) this.getNavigation();
		groundPathNavigation.setCanFloat(true);
	}

	@NotNull
	public static AttributeSupplier.Builder createAttributes() {
		return Animal.createAnimalAttributes().add(Attributes.MAX_HEALTH, 20D)
			.add(Attributes.MOVEMENT_SPEED, 0.225F)
			.add(Attributes.STEP_HEIGHT, 1.5D)
			.add(Attributes.ATTACK_DAMAGE, MAX_ATTACK_DAMAGE);
	}

	public static boolean checkOstrichSpawnRules(EntityType<? extends Ostrich> ostrich, @NotNull LevelAccessor level, EntitySpawnReason spawnType, @NotNull BlockPos pos, RandomSource random) {
		if (!EntitySpawnReason.isSpawner(spawnType) && !WWEntityConfig.get().ostrich.spawnOstriches) return false;
		return Animal.checkAnimalSpawnRules(ostrich, level, spawnType, pos, random);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(TARGET_BEAK_ANIM_PROGRESS, 0F);
		builder.define(TARGET_STRAIGHT_PROGRESS, 0F);
		builder.define(IS_ATTACKING, false);
		builder.define(BEAK_COOLDOWN, 0);
		builder.define(STUCK_TICKS, 0);
	}

	@Override
	public void registerGoals() {
	}

	@NotNull
	@Override
	public Brain.Provider<Ostrich> brainProvider() {
		return OstrichAi.brainProvider();
	}

	@NotNull
	@Override
	@SuppressWarnings("unchecked")
	public Brain<Ostrich> getBrain() {
		return (Brain<Ostrich>) super.getBrain();
	}

	@NotNull
	@Override
	@SuppressWarnings("unchecked")
	public Brain<Ostrich> makeBrain(@NotNull Dynamic<?> dynamic) {
		return (Brain<Ostrich>) OstrichAi.makeBrain(this, this.brainProvider().makeBrain(dynamic));
	}

	@Override
	protected void updateWalkAnimation(float limbDistance) {
		float fastDistance = limbDistance * 4F;
		float f = Math.min(fastDistance, 1F);
		float difference = fastDistance - f;
		this.walkAnimation.update(f + (difference * 0.15F), 0.4F, this.isBaby() ? 3.0F : 1.0F);
	}

	@Override
	public void customServerAiStep(ServerLevel serverLevel) {
		final ProfilerFiller profiler = Profiler.get();
		profiler.push("ostrichBrain");
		final Brain<Ostrich> brain = this.getBrain();
		brain.tick(serverLevel, this);
		profiler.pop();
		profiler.push("ostrichActivityUpdate");
		OstrichAi.updateActivity(this);
		profiler.pop();
		super.customServerAiStep(serverLevel);
	}

	@Override
	public void tick() {
		this.prevBeakAnimProgress = this.getTargetBeakAnimProgress();
		this.prevBeakPosition = this.getBeakPos();
		super.tick();

		if (this.getBeakCooldown() <= 1 && (this.isBeakTouchingFluid() || this.isEyeTouchingFluid() || this.isBeakTouchingCollidingBlock(false))) {
			this.setBeakCooldown(2);
		}

		if (this.getBeakCooldown() > 0) {
			this.setBeakCooldown(this.getBeakCooldown() - 1);
			if (this.getBeakCooldown() == 0) this.setTargetBeakAnimProgress(0F);
		}

		if (this.refuseToMove()) this.clampHeadRotationToBody(this, 0F);

		this.prevBeakAnimProgress = this.beakAnimProgress;
		this.beakAnimProgress = this.beakAnimProgress + ((this.getTargetBeakAnimProgress() - this.beakAnimProgress) * this.getBeakEaseAmount());
		this.beakPosition = this.makeBeakPos();
		this.beakState = this.makeBeakState();
		this.beakVoxelShape = this.getBeakState().getCollisionShape(this.level(), BlockPos.containing(this.getBeakPos()), CollisionContext.of(this));

		if (!this.level().isClientSide()) {
			this.handleAttackAndStuck((ServerLevel) this.level());

			if (this.getFirstPassenger() != null || this.isAggressive()) {
				this.setTargetStraightProgress(1F);
			} else {
				this.setTargetStraightProgress(0F);
			}

			this.setAggressive(this.hasAttackTarget());
		}
		this.prevStraightProgress = this.straightProgress;
		this.straightProgress = this.straightProgress + ((this.getTargetStraightProgress() - this.straightProgress) * 0.3F);

		if (this.isStuck()) this.getNavigation().stop();
	}

	public AABB createAttackBox(float tickDelta) {
		double height = ATTACK_BOX_HEIGHT * this.getScale();
		double width = ATTACK_BOX_WIDTH * this.getScale();
		return AABB.ofSize(this.getBeakPos(tickDelta), width, height, width).move(0D, -height * 0.5D, 0D);
	}

	private void handleAttackAndStuck(ServerLevel level) {
		if (this.isAttacking()) {
			if (!WWEntityConfig.get().ostrich.allowAttack && this.attackHasCommander) this.cancelAttack(true);

			final BlockPos beakBlockPos = BlockPos.containing(this.getBeakPos());
			boolean hasAttacked = false;
			final AABB attackBox = this.createAttackBox(1F);

			if (this.isBeakTouchingFluid()) this.cancelAttack(false);

			if (this.isBeakTouchingCollidingBlock(false)) {
				SoundType soundType = this.getBeakState().getSoundType();
				if (!this.isSilent()) this.level().playSound(null, beakBlockPos, soundType.getHitSound(), this.getSoundSource(), soundType.getVolume(), soundType.getPitch());
				this.spawnBlockParticles(false, false);
				this.cancelAttack(false);
			}

			final boolean strongEnoughToAttack = this.getBeakAnimProgress(1F) >= 0.2F;
			if (strongEnoughToAttack) {
				final Entity commander = this.getLastAttackCommander();
				final List<Entity> entities = this.level().getEntities(this, attackBox);
				for (Entity entity : entities) {
					if (!this.hasPassenger(entity)) {
						if (commander != null) {
							if (!commander.isAlliedTo(entity) && commander != entity) hasAttacked = this.doHurtOnEntity(level, commander, entity);
						} else {
							if (this.attackHasCommander) {
								this.cancelAttack(false);
								return;
							}
							if (this.canTargetEntity(entity)) hasAttacked = this.doHurtOnEntity(level, null, entity);
						}
					}
					if (hasAttacked) {
						this.cancelAttack(true);
						return;
					}
				}

				if (this.isBeakTouchingCollidingBlock(true)) {
					this.setBeakCooldown(this.isAggressive() ? BEAK_STUCK_TICKS_AGGRESSIVE + BEAK_COOLDOWN_TICKS_SUCCESSFUL_HIT : BEAK_STUCK_TICKS + BEAK_COOLDOWN_TICKS);
					this.setStuckTicks(this.isAggressive() ? BEAK_STUCK_TICKS_AGGRESSIVE : BEAK_STUCK_TICKS);
					this.setAttacking(false);
					this.setTargetBeakAnimProgress(this.getBeakAnimProgress(1F));
					if (!this.isSilent()) {
						boolean inbred = this.isInbred();
						SoundEvent stuckSoundEvent = inbred ? WWSounds.ENTITY_OSTRICH_INBRED_BEAK_STUCK : WWSounds.ENTITY_OSTRICH_BEAK_STUCK;
						float volume = inbred ? 8F : this.getSoundVolume();
						this.level().playSound(null, beakBlockPos, stuckSoundEvent, this.getSoundSource(), volume, this.getVoicePitch());
						if (inbred) ScreenShakeManager.addEntityScreenShake(this, 3.5F, 40, 16F);
					}
					this.spawnBlockParticles(true, false);
					return;
				}
			}

			if (this.getBeakAnimProgress(1F) >= this.getClampedTargetBeakAnimProgress() - 0.025F) this.cancelAttack(false);

		} else {
			if (this.getStuckTicks() > 0) {
				this.setStuckTicks(this.getStuckTicks() - 1);
				if (this.getStuckTicks() == 0 || !this.isBeakTouchingCollidingBlock(true)) this.emergeBeak();
			}
		}
	}

	public boolean doHurtOnEntity(ServerLevel level, @Nullable Entity commander, @NotNull Entity entity) {
		final float beakProgress = ((this.getBeakAnimProgress(1F) + this.getClampedTargetBeakAnimProgress()) * 0.5F);
		final float beakDamage = beakProgress * (float) this.getAttribute(Attributes.ATTACK_DAMAGE).getValue();

		final AttributeInstance knockback = this.getAttribute(Attributes.ATTACK_KNOCKBACK);
		if (knockback != null) {
			knockback.addOrUpdateTransientModifier(
				new AttributeModifier(
					KNOCKBACK_MODIFIER_UUID,
					beakProgress * ADDITIONAL_KNOCKBACK_RIDER,
					AttributeModifier.Operation.ADD_VALUE
				)
			);
		}

		boolean didHurt = entity.hurtServer(level, this.damageSources().source(WWDamageTypes.OSTRICH, commander != null ? commander : this), beakDamage);
		if (!didHurt) {
			knockback.removeModifier(KNOCKBACK_MODIFIER_UUID);
		} else if (entity instanceof LivingEntity livingEntity) {
			livingEntity.knockback(knockback.getValue(), this.getX() - livingEntity.getX(), this.getZ() - livingEntity.getZ());
		}
		return didHurt;
	}

	@Override
	public void swing(@NotNull InteractionHand hand, boolean updateSelf) {
		if (this.isAttacking() || this.getBeakCooldown() > 0 || this.isStuck()) return;
		this.performAttack(0.6F + (this.random.nextFloat() * 0.4F), null);
	}

	public void cancelAttack(boolean successful) {
		this.setTargetBeakAnimProgress(0F);
		this.setAttacking(false);
		this.setBeakCooldown(successful || this.isAggressive() ? BEAK_COOLDOWN_TICKS_SUCCESSFUL_HIT : BEAK_COOLDOWN_TICKS);
		this.setLastAttackCommander(null);
		this.getAttribute(Attributes.ATTACK_DAMAGE).removeModifier(ATTACK_MODIFIER_UUID);
		this.getAttribute(Attributes.ATTACK_KNOCKBACK).removeModifier(KNOCKBACK_MODIFIER_UUID);
	}

	public void emergeBeak() {
		this.setStuckTicks(0);
		this.setBeakCooldown(this.isAggressive() ? BEAK_COOLDOWN_TICKS_SUCCESSFUL_HIT : BEAK_COOLDOWN_TICKS);
		this.setTargetBeakAnimProgress(0F);
	}

	@Nullable
	public Entity getLastAttackCommander() {
		if (this.lastAttackCommander == null) return null;
		return EntityReference.getEntity(this.lastAttackCommander, this.level());
	}

	public void setLastAttackCommander(@Nullable Entity entity) {
		if (entity != null) {
			this.attackHasCommander = true;
			this.lastAttackCommander = EntityReference.of(entity);
			this.commanderWasPlayer = entity instanceof Player;
		} else {
			this.attackHasCommander = false;
			this.lastAttackCommander = null;
			this.commanderWasPlayer = false;
		}
	}

	@NotNull
	@Override
	public AABB getAttackBoundingBox() {
		final float scale = this.getScale();
		final double attackBBOffset = 0.2D * scale;
		return super.getAttackBoundingBox().inflate(attackBBOffset, 0D, attackBBOffset).move(0D, -attackBBOffset, 0D);
	}

	@Override
	public boolean isImmobile() {
		return this.isStuck() || super.isImmobile();
	}

	@Override
	public float getWaterSlowDown() {
		return 0.96F;
	}

	@Override
	public boolean canSprint() {
		return true;
	}

	@Override
	public void tickRidden(@NotNull Player player, @NotNull Vec3 travelVector) {
		final Vec2 riddenRotation = this.getRiddenRotation(player);
		this.setRot(riddenRotation.y, riddenRotation.x);
		this.yRotO = this.yBodyRot = this.yHeadRot = this.getYRot();
		if (this.isLocalInstanceAuthoritative()) {
			if (WWEntityConfig.get().ostrich.allowAttack && this.playerJumpPendingScale > 0F) {
				this.executeRidersJump(this.playerJumpPendingScale, travelVector);
			}
			this.playerJumpPendingScale = 0F;
		}
	}

	@Override
	public void executeRidersJump(float playerJumpPendingScale, @NotNull Vec3 travelVector) {
		this.hasImpulse = true;
	}

	@Override
	public void handleStartJump(int jumpPower) {
		final float powerPercent = ((float) jumpPower) * 0.0125F;
		this.performAttack(powerPercent, this.getFirstPassenger());
	}

	public void performAttack(float power, @Nullable Entity commander) {
		if (commander != null && !WWEntityConfig.get().ostrich.allowAttack) return;
		this.setBeakCooldown(BEAK_COOLDOWN_TICKS);
		this.setAttacking(true);
		this.setTargetBeakAnimProgress(power);
		this.setLastAttackCommander(commander);
		SoundEvent soundEvent = this.isInbred() ? WWSounds.ENTITY_OSTRICH_INBRED_SWING : WWSounds.ENTITY_OSTRICH_SWING;
		this.playSound(soundEvent, 0.4F, 0.9F + this.random.nextFloat() * 0.2F);

		if (this.attackHasCommander) {
			this.getAttribute(Attributes.ATTACK_DAMAGE)
				.addOrUpdateTransientModifier(ADDITIONAL_DAMAGE_RIDER_MODIFIER);
		}
	}

	@Override
	public void handleStopJump() {
	}

	@Override
	public int getJumpCooldown() {
		return this.getBeakCooldown();
	}

	public boolean refuseToMove() {
		return this.isStuck();
	}

	public boolean isStuck() {
		return this.getStuckTicks() > 0;
	}

	@Override
	public float getRiddenSpeed(@NotNull Player player) {
		return (float) this.getAttributeValue(Attributes.MOVEMENT_SPEED) + this.getAdditionalSpeed();
	}

	public float getAdditionalSpeed() {
		return this.getFirstPassenger() instanceof Player player && player.isSprinting() && this.getJumpCooldown() == 0 ? 0.2F : 0F;
	}

	@NotNull
	@Override
	public Vec2 getRiddenRotation(@NotNull LivingEntity entity) {
		return this.refuseToMove() ? new Vec2(this.getXRot(), this.getYRot()) : super.getRiddenRotation(entity);
	}

	@NotNull
	@Override
	public Vec3 getRiddenInput(@NotNull Player player, @NotNull Vec3 travelVector) {
		return this.refuseToMove() ? Vec3.ZERO : super.getRiddenInput(player, travelVector);
	}

	@Override
	public boolean canJump() {
		return WWEntityConfig.get().ostrich.allowAttack && !this.refuseToMove() && super.canJump();
	}

	@Override
	public void onPlayerJump(int jumpPower) {
		if (this.isSaddled() && this.getBeakCooldown() <= 0) super.onPlayerJump(jumpPower);
	}

	@Override
	public boolean isPushable() {
		return !this.refuseToMove() && super.isPushable();
	}

	@Override
	public boolean isPushedByFluid() {
		return this.isBeakTouchingFluid();
	}

	@Override
	public boolean isFood(@NotNull ItemStack stack) {
		return stack.is(WWItemTags.OSTRICH_FOOD);
	}

	@Override
	public void onElasticLeashPull() {
		super.onElasticLeashPull();
		if (this.isStuck()) this.emergeBeak();
	}

	@Override
	@NotNull
	public InteractionResult mobInteract(@NotNull Player player, @NotNull InteractionHand hand) {
		if (this.isAggressive() && !this.isTamed()) return InteractionResult.FAIL;

		final boolean isGrownAndTamedWithShiftHeldDown = !this.isBaby() && this.isTamed() && player.isSecondaryUseActive();
		if (!this.isVehicle() && !isGrownAndTamedWithShiftHeldDown) {
			final ItemStack itemStack = player.getItemInHand(hand);
			if (!itemStack.isEmpty() && this.isFood(itemStack)) {
				OstrichAi.removeAttackAndAngerTarget(this);
				return this.fedFood(player, itemStack);
			}
		}
		return super.mobInteract(player, hand);
	}

	@Override
	public void doPlayerRide(@NotNull Player player) {
		super.doPlayerRide(player);
		OstrichAi.removeAttackAndAngerTarget(this);
	}

	@Override
	public boolean handleEating(@NotNull Player player, @NotNull ItemStack stack) {
		if (!this.isFood(stack)) return false;

		final boolean isHurt = this.getHealth() < this.getMaxHealth();
		if (isHurt) this.heal(2F);

		final boolean canFallInLove = this.isTamed() && this.getAge() == 0 && this.canFallInLove();
		if (canFallInLove) this.setInLove(player);

		final boolean isBaby = this.isBaby();
		if (isBaby) {
			this.level().addParticle(ParticleTypes.HAPPY_VILLAGER, this.getRandomX(1D), this.getRandomY() + 0.5D, this.getRandomZ(1D), 0.0, 0.0, 0.0);
			if (!this.level().isClientSide()) this.ageUp(10);
		}

		if (!isHurt && !canFallInLove && !isBaby) return false;

		final SoundEvent eatingSound = this.getEatingSound();
		if (eatingSound != null) this.playSound(eatingSound, 1F, 1F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F);

		this.gameEvent(GameEvent.EAT);
		return true;
	}

	@Contract("null->false")
	public boolean canTargetEntity(@Nullable Entity entity) {
		return entity instanceof LivingEntity livingEntity
			&& this.level() == livingEntity.level()
			&& !this.level().getDifficulty().equals(Difficulty.PEACEFUL)
			&& EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(livingEntity)
			&& !this.isAlliedTo(livingEntity)
			&& livingEntity.getType() != EntityType.ARMOR_STAND
			&& livingEntity.getType() != WWEntityTypes.OSTRICH
			&& !this.isVehicle()
			&& !(livingEntity instanceof Player && this.isTamed())
			&& !livingEntity.isInvulnerable()
			&& !livingEntity.isDeadOrDying()
			&& !livingEntity.isRemoved()
			&& this.level().getWorldBorder().isWithinBounds(livingEntity.getBoundingBox());
	}

	@Override
	public boolean causeFallDamage(double fallDistance, float multiplier, @NotNull DamageSource source) {
		final int fallDage = this.calculateFallDamage(fallDistance, multiplier);
		if (fallDage <= 0) return false;
		if (fallDistance >= 6F) {
			this.hurt(source, (float) fallDage);
			if (this.isVehicle()) {
				for (Entity entity : this.getIndirectPassengers()) entity.hurt(source, (float) fallDage);
			}
		}

		this.playBlockFallSound();
		return true;
	}

	@Override
	public boolean hurtServer(ServerLevel level, @NotNull DamageSource source, float amount) {
		final boolean wasHurt = super.hurtServer(level, source, amount);
		if (wasHurt && source.getEntity() instanceof LivingEntity livingEntity) OstrichAi.wasHurtBy(level, this, livingEntity);
		return wasHurt;
	}

	@NotNull
	private Vec3 makeBeakPos() {
		final double scale = this.getScale() * this.getAgeScale();
		final float beakAnimProgress = this.getBeakAnimProgress(0F);
		final Vec3 currentPos = this.position().add(0D, DIMENSION_PERCENTAGE_AT_NECK * this.getEyeHeight(), 0D);
		final Vec3 lookOrientation = Vec3.directionFromRotation(new Vec2(0F, this.getYHeadRot()));
		final Vec3 neckBasePos = currentPos.add(lookOrientation.scale(0.475D * scale));

		final Vec3 headBaseRotPos = AdvancedMath.rotateAboutX(Vec3.ZERO, 0.275D * scale, (beakAnimProgress * 180D * 0.3D) + 90D);
		final Vec3 headBasePos = neckBasePos.add(0D, headBaseRotPos.x(), 0D).add(lookOrientation.scale(headBaseRotPos.z()));

		final Vec3 beakRotPos = AdvancedMath.rotateAboutX(Vec3.ZERO, 1.15D * scale, (beakAnimProgress * 180D * 0.7D) + 10D);
		final double downFactor = (Math.max(0, beakAnimProgress - 0.5) * 0.75D * 1.75D) * scale;
		final Vec3 beakPos = headBasePos.add(0D, beakRotPos.x() - downFactor, 0D).add(lookOrientation.scale(beakRotPos.z() - downFactor));
		return beakPos;
	}

	@NotNull
	public List<Vec3> getDebugRenderingPoses(float tickDelta) {
		final List<Vec3> poses = new ArrayList<>();
		final double scale = this.getScale() * this.getAgeScale();
		final float beakAnimProgress = this.getBeakAnimProgress(tickDelta);
		final Vec3 currentPos = this.getPosition(tickDelta).add(0D, DIMENSION_PERCENTAGE_AT_NECK * this.getEyeHeight(), 0D);
		final Vec3 lookOrientation = Vec3.directionFromRotation(new Vec2(0F, Mth.lerp(tickDelta, this.yHeadRotO, this.yHeadRot)));
		final Vec3 neckBasePos = currentPos.add(lookOrientation.scale(0.475D * scale));

		final Vec3 headBaseRotPos = AdvancedMath.rotateAboutX(Vec3.ZERO, 0.275D * scale, (beakAnimProgress * 180D * 0.3D) + 90D);
		final Vec3 headBasePos = neckBasePos.add(0D, headBaseRotPos.x(), 0D).add(lookOrientation.scale(headBaseRotPos.z()));

		final Vec3 beakRotPos = AdvancedMath.rotateAboutX(Vec3.ZERO, 1.15D * scale, (beakAnimProgress * 180D * 0.7D) + 10D);
		final double downFactor = (Math.max(0, beakAnimProgress - 0.5) * 0.75D * 1.75D) * scale;
		final Vec3 beakPos = headBasePos.add(0D, beakRotPos.x() - downFactor, 0D).add(lookOrientation.scale(beakRotPos.z() - downFactor));

		poses.add(neckBasePos);
		poses.add(headBasePos);
		poses.add(beakPos);

		return poses;
	}

	@NotNull
	private Vec3 getPrevBeakPos() {
		return this.prevBeakPosition != null ? this.prevBeakPosition : (this.prevBeakPosition = this.makeBeakPos());
	}

	@NotNull
	private Vec3 getBeakPos() {
		return this.beakPosition != null ? this.beakPosition : (this.beakPosition = this.makeBeakPos());
	}

	@NotNull
	public Vec3 getBeakPos(float tickDelta) {
		return this.getPrevBeakPos().lerp(this.getBeakPos(), tickDelta);
	}

	@NotNull
	public BlockState getBeakState() {
		return this.beakState != null ? this.beakState : (this.beakState = this.makeBeakState());
	}

	@NotNull
	private BlockState makeBeakState() {
		return this.level().getBlockState(BlockPos.containing(this.getBeakPos()));
	}

	public boolean canGetHeadStuckInState(@NotNull BlockState blockState) {
		return blockState.is(WWBlockTags.OSTRICH_BEAK_BURYABLE);
	}

	public boolean isBeakTouchingFluid() {
		final Vec3 beakVec = this.getBeakPos();
		final BlockPos beakPos = BlockPos.containing(beakVec);
		final FluidState fluidState = this.getBeakState().getFluidState();
		return !fluidState.isEmpty() && (fluidState.getHeight(this.level(), beakPos) + beakPos.getY() >= beakVec.y());
	}

	public boolean isBeakTouchingCollidingBlock(boolean canGetStuck) {
		final Vec3 beakVec = this.getBeakPos();
		final BlockPos beakPos = BlockPos.containing(beakVec);
		if (this.beakVoxelShape != null && !this.beakVoxelShape.isEmpty() && this.beakVoxelShape != Shapes.empty()) {
			AABB collisionShape = this.beakVoxelShape.bounds().move(beakPos);
			return canGetStuck == this.canGetHeadStuckInState(this.getBeakState()) && collisionShape.contains(beakVec);
		}
		return false;
	}

	public boolean isEyeTouchingFluid() {
		final Vec3 eyeVec = this.getEyePosition();
		final BlockPos eyePos = BlockPos.containing(eyeVec);
		final FluidState fluidState = this.getBeakState().getFluidState();
		return !fluidState.isEmpty() && (fluidState.getHeight(this.level(), eyePos) + eyePos.getY() >= eyeVec.y());
	}

	@Override
	public int getMaxTemper() {
		return 150;
	}

	@Override
	protected boolean canPerformRearing() {
		return false;
	}

	@Override
	public boolean canEatGrass() {
		return false;
	}

	@Override
	public boolean canMate(@NotNull Animal otherAnimal) {
		if (otherAnimal != this && otherAnimal instanceof Ostrich ostrich) return this.canParent() && ostrich.canParent();
		return false;
	}

	@Override
	public boolean canParent() {
		return !this.hasAttackTarget() && super.canParent();
	}

	public boolean hasAttackTarget() {
		return this.getBrain().hasMemoryValue(MemoryModuleType.ATTACK_TARGET);
	}

	@Override
	public void spawnChildFromBreeding(@NotNull ServerLevel level, @NotNull Animal mate) {
		this.finalizeSpawnChildFromBreeding(level, mate, null);
		this.getBrain().setMemory(MemoryModuleType.IS_PREGNANT, Unit.INSTANCE);
	}

	public boolean isPregnant() {
		return this.getBrain().hasMemoryValue(MemoryModuleType.IS_PREGNANT);
	}

	public void revokePregnancy() {
		this.getBrain().eraseMemory(MemoryModuleType.IS_PREGNANT);
	}

	@Nullable
	@Override
	public Ostrich getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob otherParent) {
		return WWEntityTypes.OSTRICH.create(level, EntitySpawnReason.BREEDING);
	}

	@Override
	public void actuallyHurt(@NotNull ServerLevel level, @NotNull DamageSource damageSource, float damageAmount) {
		this.emergeBeak();
		super.actuallyHurt(level, damageSource, damageAmount);
	}

	@NotNull
	@Override
	public Vec3 getLeashOffset() {
		return new Vec3(0D, DIMENSION_PERCENTAGE_AT_NECK * this.getEyeHeight(), this.getBbWidth() * 0.5D);
	}

	@NotNull
	@Override
	public BodyRotationControl createBodyControl() {
		return new OstrichBodyRotationControl(this);
	}

	public float getBeakEaseAmount() {
		return this.isAttacking() ? 0.3F : 0.1F;
	}

	private float getTargetBeakAnimProgress() {
		return this.entityData.get(TARGET_BEAK_ANIM_PROGRESS);
	}

	public void setTargetBeakAnimProgress(float progress) {
		this.entityData.set(TARGET_BEAK_ANIM_PROGRESS, progress);
	}

	private float getClampedTargetBeakAnimProgress() {
		return Math.min(this.entityData.get(TARGET_BEAK_ANIM_PROGRESS), 1F);
	}

	public float getBeakAnimProgress(float delta) {
		return Math.min(Mth.lerp(delta, this.prevBeakAnimProgress, this.beakAnimProgress), 1F);
	}

	private float getTargetStraightProgress() {
		return this.entityData.get(TARGET_STRAIGHT_PROGRESS);
	}

	public void setTargetStraightProgress(float progress) {
		this.entityData.set(TARGET_STRAIGHT_PROGRESS, progress);
	}

	public float getTargetStraightProgress(float delta) {
		return Mth.lerp(delta, this.prevStraightProgress, this.straightProgress);
	}

	private boolean isAttacking() {
		return this.entityData.get(IS_ATTACKING);
	}

	private void setAttacking(boolean isAttacking) {
		this.entityData.set(IS_ATTACKING, isAttacking);
	}

	private int getBeakCooldown() {
		return this.entityData.get(BEAK_COOLDOWN);
	}

	private void setBeakCooldown(int cooldown) {
		this.entityData.set(BEAK_COOLDOWN, cooldown);
	}

	private int getStuckTicks() {
		return this.entityData.get(STUCK_TICKS);
	}

	private void setStuckTicks(int stuckTicks) {
		this.entityData.set(STUCK_TICKS, stuckTicks);
	}

	private void clampHeadRotationToBody(@NotNull Entity entity, float maxYRot) {
		final float yHeadRot = entity.getYHeadRot();
		final float headToBodyDifference = Mth.wrapDegrees(this.yBodyRot - yHeadRot);
		final float clampedDifference = Mth.clamp(headToBodyDifference, -maxYRot, maxYRot);
		final float finalYHeadRot = yHeadRot + headToBodyDifference - clampedDifference;
		entity.setYHeadRot(finalYHeadRot);
	}

	@Nullable
	@Override
	public SoundEvent getEatingSound() {
		if (this.isInbred()) return WWSounds.ENTITY_OSTRICH_INBRED_IDLE_AH;
		return WWSounds.ENTITY_OSTRICH_EAT;
	}

	@Override
	protected @NotNull Holder<SoundEvent> getEquipSound(@NotNull EquipmentSlot equipmentSlot, ItemStack stack, @NotNull Equippable equippable) {
		return equipmentSlot == EquipmentSlot.SADDLE ? WWSounds.ENTITY_OSTRICH_SADDLE : super.getEquipSound(equipmentSlot, stack, equippable);
	}

	@Nullable
	@Override
	public SoundEvent getAngrySound() {
		if (this.isInbred()) return WWSounds.ENTITY_OSTRICH_INBRED_IDLE_AH;
		return WWSounds.ENTITY_OSTRICH_GRUNT;
	}

	@Override
	public int getAmbientSoundInterval() {
		return !this.isAggressive() ? super.getAmbientSoundInterval() : 50;
	}

	@Nullable
	@Override
	public SoundEvent getAmbientSound() {
		if (this.isInbred()) return this.random.nextFloat() <= 0.555F ? WWSounds.ENTITY_OSTRICH_INBRED_IDLE_AH : WWSounds.ENTITY_OSTRICH_INBRED_IDLE_BOCK;
		if (this.isAggressive()) return this.random.nextBoolean() ? WWSounds.ENTITY_OSTRICH_HISS : WWSounds.ENTITY_OSTRICH_GRUNT;
		return WWSounds.ENTITY_OSTRICH_IDLE;
	}

	@Nullable
	@Override
	public SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
		if (this.isInbred()) return WWSounds.ENTITY_OSTRICH_INBRED_HURT;
		return WWSounds.ENTITY_OSTRICH_HURT;
	}

	@Nullable
	@Override
	public SoundEvent getDeathSound() {
		if (this.isInbred()) return WWSounds.ENTITY_OSTRICH_INBRED_DEATH;
		return WWSounds.ENTITY_OSTRICH_DEATH;
	}

	@Override
	public float nextStep() {
		return this.moveDist + 1F + (this.isVehicle() ? 0.75F : 0F) + (this.getAdditionalSpeed() * 5F);
	}

	@Override
	public void playStepSound(@NotNull BlockPos pos, @NotNull BlockState state) {
		boolean inbred = this.isInbred();
		SoundEvent soundEvent = inbred ? WWSounds.ENTITY_OSTRICH_INBRED_STEP : WWSounds.ENTITY_OSTRICH_STEP;
		float volume = inbred ? 0.5F : 0.1F;
		this.playSound(soundEvent, volume, 0.9F + this.random.nextFloat() * 0.2F);
	}

	@NotNull
	@Override
	public Vec3 getPassengerAttachmentPoint(@NotNull Entity entity, @NotNull EntityDimensions dimensions, float scale) {
		return new Vec3(0D, dimensions.height() * 0.775D, dimensions.width() * -0.1D).yRot(-this.getYRot() * Mth.DEG_TO_RAD);
	}

	@Override
	public boolean requiresCustomPersistence() {
		return super.requiresCustomPersistence() || this.isTamed();
	}

	@Override
	public boolean removeWhenFarAway(double distanceToClosestPlayer) {
		return !this.isTamed() && super.removeWhenFarAway(distanceToClosestPlayer);
	}

	@SuppressWarnings("DataFlowIssue")
	public boolean isInbred() {
		return this.hasCustomName() && this.getCustomName().getString().equalsIgnoreCase("shadownite64");
	}

	public void spawnBlockParticles(boolean beakBury, boolean backwards) {
		if (!(this.level() instanceof ServerLevel serverLevel) || this.beakVoxelShape == null) return;

		final BlockState beakState = this.getBeakState();
		if (!beakState.shouldSpawnTerrainParticles() || beakState.getRenderShape() == RenderShape.INVISIBLE) return;

		final Vec3 particlePos = this.getBeakPos();
		final Vec3 deltaBeakPos = particlePos.subtract(this.getPrevBeakPos()).scale(!backwards ? 2D : -2D);
		final BlockHitResult beakHitResult = this.getBeakHitResult(backwards);
		if (beakHitResult.getType() == HitResult.Type.MISS) return;

		final int count = !beakBury ? this.random.nextInt(7, 12) : this.random.nextInt(12, 20);
		final BlockParticleOption blockParticleOption = new BlockParticleOption(ParticleTypes.BLOCK, beakState);
		final Vec3 hitLocation = beakHitResult.getLocation();
		serverLevel.sendParticles(
			blockParticleOption,
			hitLocation.x(),
			hitLocation.y(),
			hitLocation.z(),
			count,
			0D,
			0D,
			0D,
			0.05D + deltaBeakPos.length()
		);
	}

	public BlockHitResult getBeakHitResult(boolean backwards) {
		return this.level().clip(
			new ClipContext(
				!backwards ? this.getPrevBeakPos() : this.getBeakPos(),
				!backwards ? this.getBeakPos() : this.getPrevBeakPos(),
				ClipContext.Block.COLLIDER,
				ClipContext.Fluid.NONE,
				this
			)
		);
	}

	@Override
	public void addAdditionalSaveData(@NotNull ValueOutput valueOutput) {
		super.addAdditionalSaveData(valueOutput);
		valueOutput.putInt("BeakCooldown", this.getBeakCooldown());
		valueOutput.putFloat("TargetBeakAnimProgress", this.getTargetBeakAnimProgress());
		valueOutput.putFloat("TargetStraightProgress", this.getTargetStraightProgress());
		valueOutput.putBoolean("IsAttacking", this.isAttacking());
		valueOutput.putInt("StuckTicks", this.getStuckTicks());
		valueOutput.putFloat("BeakAnimProgress", this.beakAnimProgress);
		if (this.lastAttackCommander != null) EntityReference.store(this.lastAttackCommander, valueOutput, "LastAttackCommander");
		valueOutput.putBoolean("AttackHasCommander", this.attackHasCommander);
		valueOutput.putBoolean("CommanderWasPlayer", this.commanderWasPlayer);
	}

	@Override
	public void readAdditionalSaveData(@NotNull ValueInput valueInput) {
		super.readAdditionalSaveData(valueInput);
		this.setBeakCooldown(valueInput.getIntOr("BeakCooldown", 0));
		this.setTargetBeakAnimProgress(valueInput.getFloatOr("TargetBeakAnimProgress", 0F));
		this.setTargetStraightProgress(valueInput.getFloatOr("TargetStraightProgress", 0F));
		this.setAttacking(valueInput.getBooleanOr("IsAttacking", false));
		this.setStuckTicks(valueInput.getIntOr("StuckTicks", 0));
		this.beakAnimProgress = valueInput.getFloatOr("BeakAnimProgress", 0F);
		this.lastAttackCommander = EntityReference.read(valueInput, "LastAttackCommander");
		this.attackHasCommander = valueInput.getBooleanOr("AttackHasCommander", false);
		this.commanderWasPlayer = valueInput.getBooleanOr("CommanderWasPlayer", false);
	}
}
