package net.frozenblock.wilderwild.entity;

import net.frozenblock.wilderwild.entity.ai.crab.CrabJumpControl;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WallClimberNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Crab extends Animal {
	private static final EntityDataAccessor<Byte> DATA_FLAGS_ID = SynchedEntityData.defineId(Crab.class, EntityDataSerializers.BYTE);
	private static final EntityDataAccessor<Float> TARGET_CLIMBING_ANIM_X = SynchedEntityData.defineId(Crab.class, EntityDataSerializers.FLOAT);
	private static final float MAX_TARGET_DISTANCE = 15F;
	private static final float MOVEMENT_SPEED = 0.16F;
	private static final float WATER_MOVEMENT_SPEED = 0.576F;
	private static final int DIG_TICKS_UNTIL_PARTICLES = 17;
	private static final int DIG_TICKS_UNTIL_STOP_PARTICLES = 82;

	public AnimationState diggingAnimationState = new AnimationState();
	public float climbAnimX;
	public float prevClimbAnimX;
	public float viewAngle;
	public int digTicks;

	public Crab(EntityType<? extends Crab> entityType, Level level) {
		super(entityType, level);
		this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
		this.jumpControl = new CrabJumpControl(this);
		this.setMaxUpStep(0.2F);
	}

	@Override
	public void registerGoals() {
		this.goalSelector.addGoal(6, new RandomStrollGoal(this, 1));
		this.targetSelector.addGoal(4, new MeleeAttackGoal(this, 1.15, false));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[0]));
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
	protected PathNavigation createNavigation(Level level) {
		return new WallClimberNavigation(this, level);
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(DATA_FLAGS_ID, (byte)0);
		this.entityData.define(TARGET_CLIMBING_ANIM_X, 0F);
	}

	@Override
	public boolean canBreatheUnderwater() {
		return true;
	}

	@Override
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
		if (this.getAttribute(Attributes.MOVEMENT_SPEED) != null) {
			this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(this.isInWater() ? WATER_MOVEMENT_SPEED : MOVEMENT_SPEED);
		}
		super.aiStep();
	}

	@Override
	public void tick() {
		if (!this.level().isClientSide) {
			if (this.isClimbing()) {
				Vec3 deltaMovement = this.getDeltaMovement();
				float deltaAngle = (float) Math.atan2(deltaMovement.z(), deltaMovement.x());
				deltaAngle = (float) (180F * deltaAngle / Math.PI);
				deltaAngle = (360F + deltaAngle) % 360F;
				if (deltaAngle < 0F) {
					deltaAngle += 360F;
				}
				if (deltaAngle > 360F) {
					deltaAngle -= 360F;
				}

				Vec3 viewVector = this.getViewVector(1F);
				float viewAngle = (float) Math.atan2(viewVector.z(), viewVector.x());
				viewAngle = (float) (180F * viewAngle / Math.PI);
				viewAngle = (360F + viewAngle) % 360F;
				if (viewAngle < 0F) {
					viewAngle += 360F;
				}
				if (viewAngle > 360F) {
					viewAngle -= 360F;
				}
				this.viewAngle = viewAngle;

				float difference = deltaAngle - viewAngle;
				if (difference < 0F) {
					difference += 360F;
				}
				if (difference > 360F) {
					difference -= 360F;
				}

				this.setTargetClimbAnimX(difference / 180F);
			} else {
				this.setTargetClimbAnimX(0F);
			}
			if (this.level().random.nextFloat() < 0.01F) {
				this.setPose(Pose.DIGGING);
			}
		}
		super.tick();
		if (!this.level().isClientSide) {
			this.setClimbing(this.horizontalCollision);
		}
		this.prevClimbAnimX = this.climbAnimX;
		this.climbAnimX += ((this.isClimbing() ? Mth.clamp(-Mth.cos(this.targetClimbAnimX() * Mth.PI) * 10F, -1F, 1F) : 0F) - this.climbAnimX) * 0.2F;
		if (this.getPose() == Pose.DIGGING) {
			if (this.level().isClientSide()) {
				if (this.diggingAnimationState.getAccumulatedTime() > DIG_TICKS_UNTIL_PARTICLES && this.diggingAnimationState.getAccumulatedTime() < DIG_TICKS_UNTIL_STOP_PARTICLES) {
					this.clientDiggingParticles(this.diggingAnimationState);
				}
			}
		}
	}

	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
		if (DATA_POSE.equals(key)) {
			if (this.getPose() == Pose.DIGGING) {
				this.diggingAnimationState.start(this.tickCount);
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
		b = climbing ? (byte)(b | 1) : (byte)(b & 0xFFFFFFFE);
		this.entityData.set(DATA_FLAGS_ID, b);
	}

	public float targetClimbAnimX() {
		return this.entityData.get(TARGET_CLIMBING_ANIM_X);
	}

	public void setTargetClimbAnimX(float f) {
		this.entityData.set(TARGET_CLIMBING_ANIM_X, f);
	}

	@Override
	public void calculateEntityAnimation(boolean includeHeight) {
		includeHeight = this.isClimbing();
		float f = (float) Mth.length(this.getX() - this.xo, includeHeight ? this.getY() - this.yo : 0.0, this.getZ() - this.zo);
		this.updateWalkAnimation(f);
	}

	@Nullable
	@Override
	public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
		return null;
	}

	private void clientDiggingParticles(AnimationState animationState) {
		if ((float)animationState.getAccumulatedTime() < 4500.0f) {
			RandomSource randomSource = this.getRandom();
			BlockState blockState = this.getBlockStateOn();
			if (blockState.getRenderShape() != RenderShape.INVISIBLE) {
				for (int i = 0; i < 30; ++i) {
					double d = this.getX() + (double)Mth.randomBetween(randomSource, -0.25f, 0.25f);
					double e = this.getY();
					double f = this.getZ() + (double)Mth.randomBetween(randomSource, -0.25f, 0.25f);
					this.level().addParticle(new BlockParticleOption(ParticleTypes.BLOCK, blockState), d, e, f, 0.0, 0.0, 0.0);
				}
			}
		}
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putInt("DigTicks", this.digTicks);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		this.digTicks = compound.getInt("DigTicks");
	}

	/*
	@Override
	protected float getWaterSlowDown() {
		return 1F;
	}
	 */

}
