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
import java.util.List;
import net.frozenblock.lib.math.api.AdvancedMath;
import net.frozenblock.wilderwild.entity.ai.ostrich.OstrichAi;
import net.frozenblock.wilderwild.entity.ai.ostrich.OstrichBodyRotationControl;
import net.frozenblock.wilderwild.entity.ai.ostrich.OstrichLookControl;
import net.frozenblock.wilderwild.entity.ai.ostrich.OstrichMoveControl;
import net.frozenblock.wilderwild.registry.RegisterDamageTypes;
import net.frozenblock.wilderwild.registry.RegisterEntities;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.util.Unit;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PlayerRideableJumping;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.Saddleable;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

public class Ostrich extends AbstractHorse implements PlayerRideableJumping, Saddleable {
	public static final Ingredient TEMPTATION_ITEM = Ingredient.of(ItemTags.LEAVES);
	public static final int BEAK_COOLDOWN_TICKS = 60;
	public static final int BEAK_COOLDOWN_TICKS_SUCCESSFUL_HIT = 20;
	public static final int BEAK_STUCK_TICKS = 100;
	public static final float MAX_ATTACK_DAMAGE = 4F;

	private static final EntityDataAccessor<Float> TARGET_BEAK_ANIM_PROGRESS = SynchedEntityData.defineId(Ostrich.class, EntityDataSerializers.FLOAT);
	private static final EntityDataAccessor<Float> TARGET_PASSENGER_PROGRESS = SynchedEntityData.defineId(Ostrich.class, EntityDataSerializers.FLOAT);
	private static final EntityDataAccessor<Boolean> IS_ATTACKING = SynchedEntityData.defineId(Ostrich.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Integer> BEAK_COOLDOWN = SynchedEntityData.defineId(Ostrich.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> STUCK_TICKS = SynchedEntityData.defineId(Ostrich.class, EntityDataSerializers.INT);
	private float prevPassengerProgress;
	private float passengerProgress;
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
		GroundPathNavigation groundPathNavigation = (GroundPathNavigation)this.getNavigation();
		groundPathNavigation.setCanFloat(true);
	}

	@NotNull
	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 20.0D)
			.add(Attributes.MOVEMENT_SPEED, 0.225F)
			.add(Attributes.JUMP_STRENGTH, 1.0D)
			.add(Attributes.ATTACK_DAMAGE, 6.0D);
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(TARGET_BEAK_ANIM_PROGRESS, 0F);
		this.entityData.define(TARGET_PASSENGER_PROGRESS, 0F);
		this.entityData.define(IS_ATTACKING, false);
		this.entityData.define(BEAK_COOLDOWN, 0);
		this.entityData.define(STUCK_TICKS, 0);
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
		return (Brain<Ostrich>) OstrichAi.makeBrain(this.brainProvider().makeBrain(dynamic));
	}

	@Override
	public void customServerAiStep() {
		this.level().getProfiler().push("ostrichBrain");
		Brain<Ostrich> brain = this.getBrain();
		brain.tick((ServerLevel)this.level(), this);
		this.level().getProfiler().pop();
		this.level().getProfiler().push("ostrichActivityUpdate");
		OstrichAi.updateActivity(this);
		this.level().getProfiler().pop();
		super.customServerAiStep();
	}

	@Override
	public void tick() {
		this.prevBeakAnimProgress = this.getTargetBeakAnimProgress();
		this.prevBeakPosition = this.getBeakPos();
		super.tick();

		if (this.getBeakCooldown() == 0 && (this.isBeakTouchingFluid() || this.isEyeTouchingFluid() || this.isBeakTouchingHardBlock())) {
			this.setBeakCooldown(1);
		}

		if (this.getBeakCooldown() > 0) {
			this.setBeakCooldown(this.getBeakCooldown() - 1);
			if (this.getBeakCooldown() == 0) {
				this.setTargetBeakAnimProgress(0F);
			}
		}

		if (this.refuseToMove()) {
			this.clampHeadRotationToBody(this, 0F);
		}

		this.prevBeakAnimProgress = this.beakAnimProgress;
		this.beakAnimProgress = this.beakAnimProgress + ((this.getTargetBeakAnimProgress() - this.beakAnimProgress) * 0.3F);
		this.beakPosition = this.makeBeakPos();
		this.beakState = this.makeBeakState();
		this.beakVoxelShape = this.getBeakState().getShape(this.level(), BlockPos.containing(this.getBeakPos()));

		if (!this.level().isClientSide) {
			this.handleAttackAndStuck();

			if (this.getFirstPassenger() != null) {
				this.setTargetPassengerProgress(1F);
			} else {
				this.setTargetPassengerProgress(0F);
			}
		}
		this.prevPassengerProgress = this.passengerProgress;
		this.passengerProgress = this.passengerProgress + ((this.getTargetPassengerProgress() - this.passengerProgress) * this.getEaseAmount());

		if (this.isStuck()) {
			Vec3 deltaMovement = this.getDeltaMovement();
			this.setDeltaMovement(deltaMovement.x() * 0.25D, deltaMovement.y(), deltaMovement.z() * 0.25D);
			this.getNavigation().stop();
		}
	}

	private void handleAttackAndStuck() {
		if (this.isAttacking()) {
			Vec3 beakPos = this.getBeakPos();
			boolean hasAttacked = false;
			AABB attackBox = AABB.ofSize(beakPos, 0.25D, 0.25D, 0.25D);
			/*
			if (this.level() instanceof ServerLevel serverLevel) {
				serverLevel.sendParticles(FrozenParticleTypes.DEBUG_POS, attackBox.minX, attackBox.minY, attackBox.minZ, 1, 0, 0, 0, 0);
				serverLevel.sendParticles(FrozenParticleTypes.DEBUG_POS, attackBox.maxX, attackBox.minY, attackBox.minZ, 1, 0, 0, 0, 0);
				serverLevel.sendParticles(FrozenParticleTypes.DEBUG_POS, attackBox.minX, attackBox.maxY, attackBox.minZ, 1, 0, 0, 0, 0);
				serverLevel.sendParticles(FrozenParticleTypes.DEBUG_POS, attackBox.maxX, attackBox.minY, attackBox.maxZ, 1, 0, 0, 0, 0);
				serverLevel.sendParticles(FrozenParticleTypes.DEBUG_POS, attackBox.maxX, attackBox.maxY, attackBox.minZ, 1, 0, 0, 0, 0);
				serverLevel.sendParticles(FrozenParticleTypes.DEBUG_POS, attackBox.minX, attackBox.minY, attackBox.maxZ, 1, 0, 0, 0, 0);
				serverLevel.sendParticles(FrozenParticleTypes.DEBUG_POS, attackBox.minX, attackBox.maxY, attackBox.maxZ, 1, 0, 0, 0, 0);
				serverLevel.sendParticles(FrozenParticleTypes.DEBUG_POS, attackBox.maxX, attackBox.maxY, attackBox.maxZ, 1, 0, 0, 0, 0);
			}
			 */

			if (this.isBeakTouchingFluid()) this.cancelAttack(false);

			if (this.isBeakTouchingHardBlock()) {
				SoundType soundType = this.getBeakState().getSoundType();
				BlockPos beakBlockPos = BlockPos.containing(beakPos);
				this.level().playSound(null, beakBlockPos, soundType.getHitSound(), this.getSoundSource(), soundType.getVolume(), soundType.getPitch());
				this.spawnBlockParticles(false);
				this.cancelAttack(false);
			}

			boolean strongEnoughToAttack = this.getBeakAnimProgress(1F) >= 0.2F;

			if (strongEnoughToAttack) {
				List<Entity> entities = this.level().getEntities(this, attackBox);
				float beakDamage = ((this.getBeakAnimProgress(1F) + this.getClampedTargetBeakAnimProgress()) * 0.5F) * MAX_ATTACK_DAMAGE;
				for (Entity entity : entities) {
					if (!this.hasPassenger(entity) && !this.isAlliedTo(entity)) {
						if (this.getOwner() instanceof Player player) {
							hasAttacked = entity.hurt(this.damageSources().source(RegisterDamageTypes.OSTRICH_PROXY, player, this), beakDamage);
						} else {
							hasAttacked = entity.hurt(this.damageSources().source(RegisterDamageTypes.OSTRICH_PROXY, this.getOwner(), this), beakDamage);
						}
					}
					if (hasAttacked) {
						this.cancelAttack(true);
						return;
					}
				}

				if (this.canGetHeadStuck()) {
					this.setBeakCooldown(BEAK_STUCK_TICKS + BEAK_COOLDOWN_TICKS);
					this.setStuckTicks(BEAK_STUCK_TICKS);
					this.setAttacking(false);
					this.setTargetBeakAnimProgress(this.getBeakAnimProgress(1F));
					this.playSound(RegisterSounds.ENTITY_OSTRICH_BEAK_STUCK);
					this.spawnBlockParticles(true);
					return;
				}
			}

			if (this.getBeakAnimProgress(1F) >= this.getClampedTargetBeakAnimProgress() - 0.025F) this.cancelAttack(false);

		} else if (this.getStuckTicks() > 0) {
			this.setStuckTicks(this.getStuckTicks() - 1);
			if (this.getStuckTicks() == 0 || !this.canGetHeadStuck()) {
				this.emergeBeak();
			}
		}
	}

	public void spawnBlockParticles(boolean beakBury) {
		if (!this.level().isClientSide && this.level() instanceof ServerLevel server && this.beakVoxelShape != null) {
			if (this.getBeakState().shouldSpawnTerrainParticles() && this.getBeakState().getRenderShape() != RenderShape.INVISIBLE) {
				Vec3 particlePos = this.getBeakPos();
				Vec3 deltaBeakPos = particlePos.subtract(this.getPrevBeakPos()).scale(2D);
				BlockHitResult beakHitResult = this.getBeakHitResult();

				if (beakHitResult.getType() != HitResult.Type.MISS) {
					int count = !beakBury ? this.getRandom().nextInt(7, 12) : this.getRandom().nextInt(12, 20);
					BlockParticleOption blockParticleOption = new BlockParticleOption(ParticleTypes.BLOCK, this.getBeakState());
					Vec3 hitLocation = beakHitResult.getLocation();

					for (int i = 0; i < count; ++i) {
						server.sendParticles(
							blockParticleOption,
							hitLocation.x(),
							hitLocation.y(),
							hitLocation.z(),
							1,
							0D,
							0D,
							0D,
							0.05D + deltaBeakPos.length()
						);
					}
				}
			}
		}
	}

	public BlockHitResult getBeakHitResult() {
		return this.level().clip(
			new ClipContext(
				this.getPrevBeakPos(),
				this.getBeakPos(),
				ClipContext.Block.COLLIDER,
				ClipContext.Fluid.NONE,
				this
			)
		);
	}

	public float getEaseAmount() {
		return this.isAttacking() ? 0.3F : 0.1F;
	}

	@Override
	public void swing(@NotNull InteractionHand hand, boolean updateSelf) {
		if (!this.isAttacking() || this.getBeakCooldown() <= 0 && !this.isStuck()) {
			this.setAttacking(true);
			this.setTargetBeakAnimProgress(0.6F + (this.getRandom().nextFloat() * 0.4F));
			this.setBeakCooldown(BEAK_COOLDOWN_TICKS);
		}
	}

	public void cancelAttack(boolean successful) {
		this.setTargetBeakAnimProgress(0F);
		this.setAttacking(false);
		this.setBeakCooldown(successful ? BEAK_COOLDOWN_TICKS_SUCCESSFUL_HIT : BEAK_COOLDOWN_TICKS);
	}

	public void emergeBeak() {
		this.setStuckTicks(0);
		this.setBeakCooldown(BEAK_COOLDOWN_TICKS);
		this.setTargetBeakAnimProgress(0F);
	}

	@Override
	public boolean canSprint() {
		return true;
	}

	@Override
	public void tickRidden(@NotNull Player player, @NotNull Vec3 travelVector) {
		Vec2 vec2 = this.getRiddenRotation(player);
		this.setRot(vec2.y, vec2.x);
		this.yRotO = this.yBodyRot = this.yHeadRot = this.getYRot();
		if (this.isControlledByLocalInstance()) {
			if (travelVector.z <= 0.0) {
				this.gallopSoundCounter = 0;
			}
			if (this.playerJumpPendingScale > 0.0F) {
				this.executeRidersJump(this.playerJumpPendingScale, travelVector);
			}
			this.playerJumpPendingScale = 0.0F;
		}
	}

	@Override
	public void executeRidersJump(float playerJumpPendingScale, @NotNull Vec3 travelVector) {
		this.setBeakCooldown(BEAK_COOLDOWN_TICKS);
		this.setAttacking(true);
		this.hasImpulse = true;
	}

	@Override
	public void handleStartJump(int jumpPower) {
		this.setBeakCooldown(BEAK_COOLDOWN_TICKS);
		this.setAttacking(true);
		this.gameEvent(GameEvent.ENTITY_ACTION);
		float powerPercent = ((float) jumpPower) * 0.0125F;
		this.setTargetBeakAnimProgress(powerPercent);
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
		return (float)this.getAttributeValue(Attributes.MOVEMENT_SPEED) + this.getAdditionalSpeed();
	}

	public float getAdditionalSpeed() {
		return this.getFirstPassenger() instanceof Player player && player.isSprinting() && this.getJumpCooldown() == 0 ? 0.2F : 0.0F;
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
		return !this.refuseToMove() && super.canJump();
	}

	@Override
	public void onPlayerJump(int jumpPower) {
		if (this.isSaddled() && this.getBeakCooldown() <= 0) {
			super.onPlayerJump(jumpPower);
		}
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
		return TEMPTATION_ITEM.test(stack);
	}

	@Override
	public void onLeashDistance(float distance) {
		if (distance > 6.0F && this.isStuck()) {
			this.emergeBeak();
		}
	}

	@Override
	public InteractionResult mobInteract(@NotNull Player player, @NotNull InteractionHand hand) {
		boolean isGrownAndTamedWithShiftHeldDown = !this.isBaby() && this.isTamed() && player.isSecondaryUseActive();
		if (!this.isVehicle() && !isGrownAndTamedWithShiftHeldDown) {
			ItemStack itemStack = player.getItemInHand(hand);
			if (!itemStack.isEmpty()) {
				if (this.isFood(itemStack)) {
					return this.fedFood(player, itemStack);
				}
			}
        }
        return super.mobInteract(player, hand);
    }

	@Override
	protected boolean handleEating(@NotNull Player player, @NotNull ItemStack stack) {
		if (!this.isFood(stack)) {
			return false;
		} else {
			boolean bl = this.getHealth() < this.getMaxHealth();
			if (bl) {
				this.heal(2.0F);
			}

			boolean bl2 = this.isTamed() && this.getAge() == 0 && this.canFallInLove();
			if (bl2) {
				this.setInLove(player);
			}

			boolean bl3 = this.isBaby();
			if (bl3) {
				this.level().addParticle(ParticleTypes.HAPPY_VILLAGER, this.getRandomX(1.0), this.getRandomY() + 0.5, this.getRandomZ(1.0), 0.0, 0.0, 0.0);
				if (!this.level().isClientSide) {
					this.ageUp(10);
				}
			}

			if (!bl && !bl2 && !bl3) {
				return false;
			} else {
				if (!this.isSilent()) {
					SoundEvent soundEvent = this.getEatingSound();
					if (soundEvent != null) {
						this.level().playSound(null, this.getX(), this.getY(), this.getZ(), soundEvent, this.getSoundSource(), 1.0F, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
					}
				}

				this.gameEvent(GameEvent.EAT);
				return true;
			}
		}
	}

	@Override
	public boolean causeFallDamage(float fallDistance, float multiplier, @NotNull DamageSource source) {
		int i = this.calculateFallDamage(fallDistance, multiplier);
		if (i <= 0) {
			return false;
		} else {
			if (fallDistance >= 6.0F) {
				this.hurt(source, (float)i);
				if (this.isVehicle()) {
                    for (Entity entity : this.getIndirectPassengers()) {
                        entity.hurt(source, (float) i);
                    }
				}
			}

			this.playBlockFallSound();
			return true;
		}
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
	public boolean canMate(@NotNull Animal otherAnimal) {
		if (otherAnimal != this && otherAnimal instanceof Ostrich ostrich) {
            return this.canParent() && ostrich.canParent();
		}
		return false;
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
		return RegisterEntities.OSTRICH.create(level);
	}

	@Override
	public void actuallyHurt(@NotNull DamageSource damageSource, float damageAmount) {
		this.emergeBeak();
		super.actuallyHurt(damageSource, damageAmount);
	}

	@NotNull
	@Override
	public Vec3 getLeashOffset(float partialTick) {
		Vec3 pos = this.position();
		Vec3 beakPos = this.getBeakPos();
		return beakPos.subtract(pos);
	}

	@Override
	public void sendDebugPackets() {
		super.sendDebugPackets();
		DebugPackets.sendEntityBrain(this);
	}

	@NotNull
	@Override
	public BodyRotationControl createBodyControl() {
		return new OstrichBodyRotationControl(this);
	}

	public void setTargetBeakAnimProgress(float progress) {
		this.entityData.set(TARGET_BEAK_ANIM_PROGRESS, progress);
	}

	private float getTargetBeakAnimProgress() {
		return this.entityData.get(TARGET_BEAK_ANIM_PROGRESS);
	}

	private float getClampedTargetBeakAnimProgress() {
		return Math.min(this.entityData.get(TARGET_BEAK_ANIM_PROGRESS), 1F);
	}

	public float getBeakAnimProgress(float delta) {
		return Math.min(Mth.lerp(delta, this.prevBeakAnimProgress, this.beakAnimProgress), 1F);
	}

	public void setTargetPassengerProgress(float progress) {
		this.entityData.set(TARGET_PASSENGER_PROGRESS, progress);
	}

	private float getTargetPassengerProgress() {
		return this.entityData.get(TARGET_PASSENGER_PROGRESS);
	}

	public float getTargetPassengerProgress(float delta) {
		return Mth.lerp(delta, this.prevPassengerProgress, this.passengerProgress);
	}

	private void setAttacking(boolean isAttacking) {
		this.entityData.set(IS_ATTACKING, isAttacking);
	}

	private boolean isAttacking() {
		return this.entityData.get(IS_ATTACKING);
	}

	private void setBeakCooldown(int cooldown) {
		this.entityData.set(BEAK_COOLDOWN, cooldown);
	}

	private int getBeakCooldown() {
		return this.entityData.get(BEAK_COOLDOWN);
	}

	private void setStuckTicks(int stuckTicks) {
		this.entityData.set(STUCK_TICKS, stuckTicks);
	}

	private int getStuckTicks() {
		return this.entityData.get(STUCK_TICKS);
	}

	private void clampHeadRotationToBody(@NotNull Entity entity, float maxYRot) {
		float f = entity.getYHeadRot();
		float g = Mth.wrapDegrees(this.yBodyRot - f);
		float h = Mth.clamp(Mth.wrapDegrees(this.yBodyRot - f), -maxYRot, maxYRot);
		float i = f + g - h;
		entity.setYHeadRot(i);
	}

	@NotNull
	private Vec3 makeBeakPos() {
		Vec3 currentPos = this.position().add(0D, 1.1875D, 0D);
		Vec3 lookOrientation = this.getLookAngle();
		lookOrientation = lookOrientation.subtract(0D, lookOrientation.y(), 0D);
		Vec3 headBasePos = currentPos.add(lookOrientation.scale(0.875D));
		Vec3 rotPos = AdvancedMath.rotateAboutX(Vec3.ZERO, 1.25D, this.getBeakAnimProgress(1F) * 180D);
		Vec3 beakPos = headBasePos.add(0, rotPos.x(), 0).add(lookOrientation.scale(rotPos.z()));

		/*
		if (this.level() instanceof ServerLevel serverLevel) {
			serverLevel.sendParticles(FrozenParticleTypes.DEBUG_POS, headBasePos.x, headBasePos.y, headBasePos.z, 1, 0, 0, 0, 0);
			serverLevel.sendParticles(FrozenParticleTypes.DEBUG_POS, beakPos.x, beakPos.y, beakPos.z, 1, 0, 0, 0, 0);
		}
		 */
		return beakPos;
	}

	@NotNull
	public Vec3 getPrevBeakPos() {
		return this.prevBeakPosition != null ? this.prevBeakPosition : (this.prevBeakPosition = this.makeBeakPos());
	}

	@NotNull
	public Vec3 getBeakPos() {
		return this.beakPosition != null ? this.beakPosition : (this.beakPosition = this.makeBeakPos());
	}

	@NotNull
	public BlockState getBeakState() {
		return this.beakState != null ? this.beakState : (this.beakState = this.makeBeakState());
	}

	@NotNull
	private BlockState makeBeakState() {
		return this.level().getBlockState(BlockPos.containing(this.getBeakPos()));
	}

	public boolean canGetHeadStuck() {
		return this.getBeakState().is(BlockTags.MINEABLE_WITH_SHOVEL);
	}

	public boolean isBeakTouchingFluid() {
		Vec3 beakVec = this.getBeakPos();
		BlockPos beakPos = BlockPos.containing(beakVec);
		FluidState fluidState = this.getBeakState().getFluidState();
		return !fluidState.isEmpty() && (fluidState.getHeight(this.level(), beakPos) + beakPos.getY() >= beakVec.y());
	}

	public boolean isBeakTouchingHardBlock() {
		Vec3 beakVec = this.getBeakPos();
		BlockPos beakPos = BlockPos.containing(beakVec);
		if (this.beakVoxelShape != null && !this.beakVoxelShape.isEmpty()) {
			AABB collisionShape = this.beakVoxelShape.bounds().move(beakPos);
			return !canGetHeadStuck() && collisionShape.contains(beakVec);
		}
		return false;
	}

	public boolean isEyeTouchingFluid() {
		Vec3 eyeVec = this.getEyePosition();
		BlockPos eyePos = BlockPos.containing(eyeVec);
		FluidState fluidState = this.getBeakState().getFluidState();
		return !fluidState.isEmpty() && (fluidState.getHeight(this.level(), eyePos) + eyePos.getY() >= eyeVec.y());
	}

	@Override
	public float getStandingEyeHeight(@NotNull Pose pose, @NotNull EntityDimensions dimensions) {
		return dimensions.height;
	}

	@Nullable
	@Override
	public SoundEvent getEatingSound() {
		return RegisterSounds.ENTITY_OSTRICH_EAT;
	}

	@NotNull
	@Override
	public SoundEvent getSaddleSoundEvent() {
		return RegisterSounds.ENTITY_OSTRICH_SADDLE;
	}

	@Nullable
	@Override
	public SoundEvent getAngrySound() {
		return RegisterSounds.ENTITY_OSTRICH_IDLE;
	}

	@Nullable
	@Override
	public SoundEvent getAmbientSound() {
		return RegisterSounds.ENTITY_OSTRICH_IDLE;
	}

	@Nullable
	@Override
	public SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
		return RegisterSounds.ENTITY_OSTRICH_HURT;
	}

	@Nullable
	@Override
	public SoundEvent getDeathSound() {
		return RegisterSounds.ENTITY_OSTRICH_DEATH;
	}

	@Override
	public float nextStep() {
		return this.moveDist + 1F + (this.isVehicle() ? 0.75F : 0) + (this.getAdditionalSpeed() * 5);
	}

	@Override
	public void playStepSound(@NotNull BlockPos pos, @NotNull BlockState state) {
		this.playSound(RegisterSounds.ENTITY_OSTRICH_STEP, 0.2F, 0.85F + this.getRandom().nextFloat() * 0.3F);
	}

	@NotNull
	@Override
	public Vector3f getPassengerAttachmentPoint(@NotNull Entity entity, @NotNull EntityDimensions dimensions, float scale) {
		return new Vector3f(0.0F, this.getPassengersRidingOffsetY(dimensions, scale), 0);
	}

	@Override
	public float getPassengersRidingOffsetY(@NotNull EntityDimensions entityDimensions, float f) {
		return entityDimensions.height * 0.775F * f;
	}

	@Override
	public void addAdditionalSaveData(@NotNull CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putInt("BeakCooldown", this.getBeakCooldown());
		compound.putFloat("TargetBeakAnimProgress", this.getTargetBeakAnimProgress());
		compound.putFloat("TargetPassengerProgress", this.getTargetPassengerProgress());
		compound.putBoolean("IsAttacking", this.isAttacking());
		compound.putInt("StuckTicks", this.getStuckTicks());
		compound.putFloat("BeakAnimProgress", this.beakAnimProgress);
	}

	@Override
	public void readAdditionalSaveData(@NotNull CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		this.setBeakCooldown(compound.getInt("BeakCooldown"));
		this.setTargetBeakAnimProgress(compound.getFloat("TargetBeakAnimProgress"));
		this.setTargetPassengerProgress(compound.getFloat("TargetPassengerProgress"));
		this.setAttacking(compound.getBoolean("IsAttacking"));
		this.setStuckTicks(compound.getInt("StuckTicks"));
		this.beakAnimProgress = compound.getFloat("BeakAnimProgress");
	}
}
