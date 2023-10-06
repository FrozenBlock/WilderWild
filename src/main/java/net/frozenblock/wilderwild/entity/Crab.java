package net.frozenblock.wilderwild.entity;

import net.frozenblock.wilderwild.entity.ai.crab.CrabJumpControl;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WallClimberNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Crab extends Animal {

	private static final EntityDataAccessor<Byte> DATA_FLAGS_ID = SynchedEntityData.defineId(Crab.class, EntityDataSerializers.BYTE);
	private static final float MAX_TARGET_DISTANCE = 15F;
	private static final float MOVEMENT_SPEED = 0.3F;

	public float climAnim;
	public float prevClimbAnim;

	public Crab(EntityType<? extends Crab> entityType, Level level) {
		super(entityType, level);
		this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
		this.jumpControl = new CrabJumpControl(this);
	}

	@Override
	public void registerGoals() {
		this.goalSelector.addGoal(6, new RandomStrollGoal(this, MOVEMENT_SPEED));
	}

	@NotNull
	public static AttributeSupplier.Builder addAttributes() {
		return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 8.0D).add(Attributes.MOVEMENT_SPEED, MOVEMENT_SPEED).add(Attributes.FOLLOW_RANGE, MAX_TARGET_DISTANCE);
	}

	@Override
	protected PathNavigation createNavigation(Level level) {
		return new WallClimberNavigation(this, level);
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(DATA_FLAGS_ID, (byte)0);
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
		super.aiStep();
	}

	@Override
	public void tick() {
		super.tick();
		if (!this.level().isClientSide) {
			this.setClimbing(this.horizontalCollision);
		}
		this.prevClimbAnim = this.climAnim;
		this.climAnim += ((this.isClimbing() ? 1F : 0F) - this.climAnim) * 0.2F;
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
