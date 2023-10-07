package net.frozenblock.wilderwild.entity;

import net.frozenblock.wilderwild.entity.ai.crab.CrabJumpControl;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.MoverType;
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
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Crab extends Animal {
	private static final EntityDataAccessor<Byte> DATA_FLAGS_ID = SynchedEntityData.defineId(Crab.class, EntityDataSerializers.BYTE);
	private static final EntityDataAccessor<Float> TARGET_CLIMBING_ANIM = SynchedEntityData.defineId(Crab.class, EntityDataSerializers.FLOAT);
	private static final float MAX_TARGET_DISTANCE = 15F;
	private static final float MOVEMENT_SPEED = 0.45F;

	public float climAnim;
	public float prevClimbAnim;

	public Crab(EntityType<? extends Crab> entityType, Level level) {
		super(entityType, level);
		this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
		this.jumpControl = new CrabJumpControl(this);
		this.setMaxUpStep(0.2F);
	}

	@Override
	public void registerGoals() {
		this.goalSelector.addGoal(6, new RandomStrollGoal(this, MOVEMENT_SPEED));
		this.targetSelector.addGoal(4, new MeleeAttackGoal(this, 0.55, false));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[0]));
	}

	@NotNull
	public static AttributeSupplier.Builder addAttributes() {
		return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 8.0D)
			.add(Attributes.MOVEMENT_SPEED, MOVEMENT_SPEED)
			.add(Attributes.ATTACK_DAMAGE, 1.0F)
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
		this.entityData.define(TARGET_CLIMBING_ANIM, 0F);
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
		super.aiStep();
	}

	@Override
	public void tick() {
		super.tick();
		if (!this.level().isClientSide) {
			this.setClimbing(this.horizontalCollision);
			Vec3 deltaMovement = this.getDeltaMovement();
			float facingAngle = this.getYHeadRot();
			float deltaAngle = (float) Math.atan2(deltaMovement.x(), deltaMovement.z());
			deltaAngle = (float) (180F * deltaAngle / Math.PI);
			deltaAngle = (360F + deltaAngle) % 360F;

			float difference = (deltaAngle - facingAngle) - 180F;
			//System.out.println("facing: " + facingAngle + ", moving: " + deltaAngle + ", difference: " + difference);
			this.setTargetClimbAnim(Mth.clamp((difference / 180F) * 1.5F, -1, 1));
		}
		//System.out.println(this.targetClimbAnim());
		this.prevClimbAnim = this.climAnim;
		this.climAnim += ((this.isClimbing() ? this.targetClimbAnim() : 0F) - this.climAnim) * 0.2F;
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

	public float targetClimbAnim() {
		return this.entityData.get(TARGET_CLIMBING_ANIM);
	}

	public void setTargetClimbAnim(float f) {
		this.entityData.set(TARGET_CLIMBING_ANIM, f);
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

	/*
	@Override
	protected float getWaterSlowDown() {
		return 1F;
	}
	 */

}
