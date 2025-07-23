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
import java.util.Objects;
import java.util.Optional;
import net.frozenblock.lib.wind.api.WindManager;
import net.frozenblock.wilderwild.config.WWEntityConfig;
import net.frozenblock.wilderwild.entity.ai.firefly.FireflyAi;
import net.frozenblock.wilderwild.entity.impl.WWBottleable;
import net.frozenblock.wilderwild.entity.variant.firefly.FireflyColor;
import net.frozenblock.wilderwild.entity.variant.firefly.FireflyColors;
import net.frozenblock.wilderwild.registry.WWDataComponents;
import net.frozenblock.wilderwild.registry.WWItems;
import net.frozenblock.wilderwild.registry.WWMemoryModuleTypes;
import net.frozenblock.wilderwild.registry.WWSounds;
import net.frozenblock.wilderwild.registry.WilderWildRegistries;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.util.profiling.Profiler;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.variant.SpawnContext;
import net.minecraft.world.entity.variant.VariantUtils;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Firefly extends PathfinderMob implements FlyingAnimal, WWBottleable {
	public static final int RANDOM_FLICKER_AGE_MAX = 19;
	private static final EntityDataAccessor<Boolean> FROM_BOTTLE = SynchedEntityData.defineId(Firefly.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Integer> AGE = SynchedEntityData.defineId(Firefly.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Float> ANIM_SCALE = SynchedEntityData.defineId(Firefly.class, EntityDataSerializers.FLOAT);
	private static final EntityDataAccessor<Float> PREV_ANIM_SCALE = SynchedEntityData.defineId(Firefly.class, EntityDataSerializers.FLOAT);
	private static final EntityDataAccessor<String> COLOR = SynchedEntityData.defineId(Firefly.class, EntityDataSerializers.STRING);

	private Optional<FireflyColor> fireflyColor = Optional.empty();

	public Firefly(@NotNull EntityType<? extends Firefly> entityType, @NotNull Level level) {
		super(entityType, level);
		this.setPathfindingMalus(PathType.LAVA, -1F);
		this.setPathfindingMalus(PathType.DANGER_FIRE, -1F);
		this.setPathfindingMalus(PathType.WATER, -1F);
		this.setPathfindingMalus(PathType.WATER_BORDER, 16F);
		this.setPathfindingMalus(PathType.UNPASSABLE_RAIL, 0F);
		this.moveControl = new FlyingMoveControl(this, 20, true);
		this.setFlickerAge(this.random.nextIntBetweenInclusive(0, RANDOM_FLICKER_AGE_MAX));
		this.setAnimScale(1.5F);
	}

	@Override
	public float getWalkTargetValue(@NotNull BlockPos pos, @NotNull LevelReader level) {
		return -level.getPathfindingCostFromLightLevels(pos);
	}

	public static boolean checkFireflySpawnRules(
		@NotNull EntityType<Firefly> type, @NotNull LevelAccessor level, EntitySpawnReason reason, @NotNull BlockPos pos, @NotNull RandomSource random
	) {
		if (!EntitySpawnReason.isSpawner(reason) && !WWEntityConfig.get().firefly.spawnFireflies) return false;
		if (!(EntitySpawnReason.ignoresLightRequirements(reason) || level.getMaxLocalRawBrightness(pos) <= 13)) return false;
		if (!WWEntityConfig.get().firefly.firefliesNeedBush) return true;
		return ((int)(random.nextDouble() * getNearbyFireflyBushCount(level, pos, 3))) >= 1;
	}

	@NotNull
	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes()
			.add(Attributes.MAX_HEALTH, 1D)
			.add(Attributes.MOVEMENT_SPEED, 0.08D)
			.add(Attributes.FLYING_SPEED, 0.08D)
			.add(Attributes.FOLLOW_RANGE, 32D);
	}

	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(
		@NotNull ServerLevelAccessor level, @NotNull DifficultyInstance difficulty, @NotNull EntitySpawnReason reason, @Nullable SpawnGroupData spawnData
	) {
		boolean shouldSetHome = shouldSetHome(reason);
		if (shouldSetHome) {
			FireflyAi.rememberHome(this, this.blockPosition());
		} else {
			FireflyAi.setNatural(this);
		}

		if (spawnData instanceof FireflySpawnGroupData fireflySpawnGroupData) {
			this.setColor(fireflySpawnGroupData.color.value());
		} else {
			Optional<Holder.Reference<FireflyColor>> optionalFireflyColor = VariantUtils.selectVariantToSpawn(
				SpawnContext.create(level, this.blockPosition()),
				WilderWildRegistries.FIREFLY_COLOR
			);
			if (optionalFireflyColor.isPresent()) {
				spawnData = new FireflySpawnGroupData(optionalFireflyColor.get());
				this.setColor(optionalFireflyColor.get().value());
			}

			if (!shouldSetHome) FireflyAi.setSwarmLeader(this);
		}

		return super.finalizeSpawn(level, difficulty, reason, spawnData);
	}

	private static boolean shouldSetHome(EntitySpawnReason reason) {
		return reason == EntitySpawnReason.DISPENSER
			|| reason == EntitySpawnReason.BUCKET;
	}

	@Override
	public int getMaxSpawnClusterSize() {
		return 8;
	}

	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> entityDataAccessor) {
		super.onSyncedDataUpdated(entityDataAccessor);
		if (COLOR.equals(entityDataAccessor)) this.fireflyColor = Optional.of(this.getVariant());
	}

	@Override
	public int decreaseAirSupply(int currentAir) {
		int newSupply = super.decreaseAirSupply(currentAir);
		return newSupply == currentAir - 1 ? newSupply - 1 : newSupply;
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(FROM_BOTTLE, false);
		builder.define(AGE, 0);
		builder.define(ANIM_SCALE, 1.5F);
		builder.define(PREV_ANIM_SCALE, 1.5F);
		builder.define(COLOR, FireflyColors.DEFAULT.location().toString());
	}

	@Override
	public boolean dampensVibrations() {
		return true;
	}

	@Override
	@NotNull
	protected InteractionResult mobInteract(@NotNull Player player, @NotNull InteractionHand hand) {
		return WWBottleable.bottleMobPickup(player, hand, this).orElse(super.mobInteract(player, hand));
	}

	@Override
	public boolean shouldRender(double x, double y, double z) {
		return true;
	}

	@Override
	public boolean canBeLeashed() {
		return false;
	}

	@Override
	@NotNull
	protected Brain.Provider<Firefly> brainProvider() {
		return FireflyAi.brainProvider();
	}

	@Override
	@NotNull
	protected Brain<?> makeBrain(@NotNull Dynamic<?> dynamic) {
		return FireflyAi.makeBrain(this, this.brainProvider().makeBrain(dynamic));
	}

	@Override
	public boolean wilderWild$fromBottle() {
		return this.entityData.get(FROM_BOTTLE);
	}

	@Override
	public void wilderWild$setFromBottle(boolean value) {
		this.entityData.set(FROM_BOTTLE, value);
	}

	@Override
	public void wilderWild$saveToBottleTag(ItemStack itemStack) {
		WWBottleable.saveDefaultDataToBottleTag(this, itemStack);
		itemStack.copyFrom(WWDataComponents.FIREFLY_COLOR, this);
	}

	@Override
	public void wilderWild$loadFromBottleEntityDataTag(@NotNull CompoundTag compoundTag) {
		WWBottleable.loadDefaultDataFromBottleTag(this, compoundTag);
	}

	@Override
	public void wilderWild$loadFromBottleItemStack(@NotNull ItemStack itemStack) {
		Holder<FireflyColor> color = itemStack.getOrDefault(WWDataComponents.FIREFLY_COLOR, this.registryAccess().getOrThrow(FireflyColors.DEFAULT));
		this.setColor(color.value());
	}

	@Nullable
	@Override
	public <T> T get(DataComponentType<? extends T> dataComponentType) {
		if (dataComponentType == WWDataComponents.FIREFLY_COLOR) {
			return castComponentValue(dataComponentType, this.getColorAsHolder());
		}
		return super.get(dataComponentType);
	}

	@Override
	protected void applyImplicitComponents(DataComponentGetter dataComponentGetter) {
		this.applyImplicitComponentIfPresent(dataComponentGetter, WWDataComponents.FIREFLY_COLOR);
		super.applyImplicitComponents(dataComponentGetter);
	}

	@Override
	protected <T> boolean applyImplicitComponent(DataComponentType<T> dataComponentType, T object) {
		if (dataComponentType == WWDataComponents.FIREFLY_COLOR) {
			this.setColor(castComponentValue(WWDataComponents.FIREFLY_COLOR, object).value());
			return true;
		} else {
			return super.applyImplicitComponent(dataComponentType, object);
		}
	}

	@Override
	public void wilderWild$onCapture() {
		if (this.isSwarmLeader()) {
			FireflyAi.transferLeadershipToRandomFirefly(this);
		}
	}

	@Override
	public void wilderWild$onBottleRelease() {
		FireflyAi.rememberHome(this, this.blockPosition());
	}

	@Override
	public ItemStack wilderWild$getBottleItemStack() {
		return new ItemStack(WWItems.FIREFLY_BOTTLE);
	}

	@Override
	public SoundEvent wilderWild$getBottleCatchSound() {
		return WWSounds.ITEM_BOTTLE_CATCH_FIREFLY;
	}

	public boolean hasHome() {
		return this.getBrain().hasMemoryValue(MemoryModuleType.HOME);
	}

	public int getFlickerAge() {
		return this.entityData.get(AGE);
	}

	public void setFlickerAge(int value) {
		this.entityData.set(AGE, value);
	}

	public float getAnimScale() {
		return this.entityData.get(ANIM_SCALE);
	}

	public void setAnimScale(float value) {
		this.entityData.set(ANIM_SCALE, value);
	}

	public float getPrevAnimScale() {
		return this.entityData.get(PREV_ANIM_SCALE);
	}

	public void setPrevAnimScale(float value) {
		this.entityData.set(PREV_ANIM_SCALE, value);
	}

	public ResourceLocation getColorLocation() {
		return ResourceLocation.parse(this.entityData.get(COLOR));
	}

	public FireflyColor getColorByLocation() {
		return this.registryAccess().lookupOrThrow(WilderWildRegistries.FIREFLY_COLOR).getValue(this.getColorLocation());
	}

	public Holder<FireflyColor> getColorAsHolder() {
		return this.registryAccess().lookupOrThrow(WilderWildRegistries.FIREFLY_COLOR).get(this.getColorLocation()).orElseThrow();
	}

	public FireflyColor getColorForRendering() {
		return this.fireflyColor.orElse(this.registryAccess().lookupOrThrow(WilderWildRegistries.FIREFLY_COLOR).getValue(FireflyColors.DEFAULT));
	}

	public void setColor(@NotNull FireflyColor color) {
		this.entityData.set(COLOR, Objects.requireNonNull(this.registryAccess().lookupOrThrow(WilderWildRegistries.FIREFLY_COLOR).getKey(color)).toString());
	}

	public void setColor(@NotNull ResourceLocation color) {
		this.entityData.set(COLOR, color.toString());
	}

	public FireflyColor getVariant() {
		return this.getColorByLocation();
	}

	@Override
	public boolean requiresCustomPersistence() {
		return super.requiresCustomPersistence() || this.wilderWild$fromBottle();
	}

	@Override
	@NotNull
	public Brain<Firefly> getBrain() {
		return (Brain<Firefly>) super.getBrain();
	}

	@Override
	public boolean isFlying() {
		return !this.onGround();
	}

	public boolean shouldHide() {
		return this.isNatural()
			&& this.level().isBrightOutside()
			&& !this.isPersistenceRequired()
			&& !this.requiresCustomPersistence()
			&& this.level().getBrightness(LightLayer.SKY, this.blockPosition()) >= 7;
	}

	public boolean isSwarmLeader() {
		return this.isNatural() && this.getBrain().getMemory(WWMemoryModuleTypes.IS_SWARM_LEADER).orElse(false);
	}

	public boolean isNatural() {
		return this.getBrain().getMemory(WWMemoryModuleTypes.NATURAL).orElse(false);
	}

	@Override
	@NotNull
	protected PathNavigation createNavigation(@NotNull Level level) {
		FlyingPathNavigation birdNavigation = new FlyingPathNavigation(this, level);
		birdNavigation.setCanOpenDoors(false);
		birdNavigation.setCanFloat(true);
		birdNavigation.getNodeEvaluator().setCanPassDoors(true);
		return birdNavigation;
	}

	@Override
	public void travel(@NotNull Vec3 travelVector) {
		if (this.isEffectiveAi() || this.isLocalInstanceAuthoritative()) {
			if (this.isAlive()) {
				if (this.isInWater()) {
					this.moveRelative(0.01F, travelVector);
					this.move(MoverType.SELF, this.getDeltaMovement());
					this.setDeltaMovement(this.getDeltaMovement().scale(0.800000011920929D));
				} else if (this.isInLava()) {
					this.moveRelative(0.01F, travelVector);
					this.move(MoverType.SELF, this.getDeltaMovement());
					this.setDeltaMovement(this.getDeltaMovement().scale(0.5D));
				} else {
					this.moveRelative(this.getSpeed(), travelVector);
					this.move(MoverType.SELF, this.getDeltaMovement());
					this.setDeltaMovement(this.getDeltaMovement().scale(0.9100000262260437D));
				}
			} else {
				super.travel(travelVector);
			}
		}
	}

	public static int getNearbyFireflyBushCount(@NotNull LevelAccessor level, @NotNull BlockPos blockPos, int distance) {
		int count = 0;
		Iterable<BlockPos> posesToCheck = BlockPos.betweenClosed(blockPos.offset(-distance, -distance, -distance), blockPos.offset(distance, distance, distance));
		for (BlockPos pos : posesToCheck) {
			if (level.getBlockState(pos).is(Blocks.FIREFLY_BUSH)) count += 1;
		}
		return count;
	}

	@Override
	protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState state) {
	}

	@Override
	protected void checkFallDamage(double heightDifference, boolean onGround, @NotNull BlockState state, @NotNull BlockPos landedPosition) {
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return null;
	}

	@Override
	protected SoundEvent getHurtSound(@NotNull DamageSource source) {
		return WWSounds.ENTITY_FIREFLY_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return WWSounds.ENTITY_FIREFLY_HURT;
	}

	@Override
	public void tick() {
		super.tick();
		if (!this.isAlive()) this.setNoGravity(false);

		this.setFlickerAge(this.getFlickerAge() + 1);
		float animScale = this.getAnimScale();
		this.setPrevAnimScale(animScale);
		if (animScale < 1.5F) this.setAnimScale(Math.min(this.getAnimScale() + 0.025F, 1.5F));

		if (this.level() instanceof ServerLevel serverLevel) {
			Vec3 wind = WindManager.getOrCreateWindManager(serverLevel).getWindMovement(this.position(), 1D, 100D, 100D).scale(0.01D);
			wind = wind.subtract(0D, wind.y * 0.7D, 0D);
			this.setDeltaMovement(this.getDeltaMovement().add(wind.scale(0.02D)));
		}
	}

	@Override
	protected void customServerAiStep(ServerLevel level) {
		ProfilerFiller profiler = Profiler.get();
		profiler.push("fireflyBrain");
		this.getBrain().tick(level, this);
		profiler.pop();
		profiler.push("fireflyActivityUpdate");
		FireflyAi.updateActivities(this);
		profiler.pop();
		super.customServerAiStep(level);
	}

	@Override
	public boolean isFlapping() {
		return true;
	}

	@Override
	public boolean removeWhenFarAway(double distanceToClosestPlayer) {
		return !this.wilderWild$fromBottle() && !this.hasCustomName();
	}

	@Override
	public void die(DamageSource damageSource) {
		if (this.isSwarmLeader()) FireflyAi.transferLeadershipToRandomFirefly(this);
		super.die(damageSource);
	}

	@Override
	public void addAdditionalSaveData(@NotNull ValueOutput valueOutput) {
		super.addAdditionalSaveData(valueOutput);

		this.getColorAsHolder()
			.unwrapKey()
			.ifPresent(resourceKey -> valueOutput.putString("color", resourceKey.location().toString()));

		valueOutput.putBoolean("fromBottle", this.wilderWild$fromBottle());
		valueOutput.putInt("flickerAge", this.getFlickerAge());
		valueOutput.putFloat("scale", this.getAnimScale());
		valueOutput.putFloat("prevScale", this.getPrevAnimScale());
	}

	@Override
	public void readAdditionalSaveData(@NotNull ValueInput valueInput) {
		super.readAdditionalSaveData(valueInput);

		valueInput.getString("color").flatMap(string -> Optional.ofNullable(ResourceLocation.tryParse(string))
				.map(resourceLocation -> ResourceKey.create(WilderWildRegistries.FIREFLY_COLOR, resourceLocation))
				.flatMap(resourceKey -> this.registryAccess().lookupOrThrow(WilderWildRegistries.FIREFLY_COLOR).get(resourceKey))
		).ifPresent(reference -> this.setColor(reference.value()));

		this.wilderWild$setFromBottle(valueInput.getBooleanOr("fromBottle", false));
		valueInput.getInt("flickerAge").ifPresent(this::setFlickerAge);
		this.setAnimScale(valueInput.getFloatOr("scale", 1.5F));
		this.setPrevAnimScale(valueInput.getFloatOr("prevScale", 1.5F));
	}

	@Override
	protected boolean shouldStayCloseToLeashHolder() {
		return false;
	}

	@Override
	protected void sendDebugPackets() {
		super.sendDebugPackets();
		DebugPackets.sendEntityBrain(this);
	}

	@Override
	public boolean causeFallDamage(double fallDistance, float damageMultiplier, @NotNull DamageSource source) {
		return false;
	}

	@Override
	protected void doPush(@NotNull Entity entity) {
	}

	@Override
	protected void pushEntities() {
	}

	public static class FireflySpawnGroupData implements SpawnGroupData {
		public final Holder<FireflyColor> color;

		public FireflySpawnGroupData(Holder<FireflyColor> holder) {
			this.color = holder;
		}
	}
}
