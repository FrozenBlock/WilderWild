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
import net.frozenblock.wilderwild.item.MobBottleItem;
import net.frozenblock.wilderwild.registry.WWDataComponents;
import net.frozenblock.wilderwild.registry.WWItems;
import net.frozenblock.wilderwild.registry.WWMemoryModuleTypes;
import net.frozenblock.wilderwild.registry.WWSounds;
import net.frozenblock.wilderwild.registry.WilderWildRegistries;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
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
import net.minecraft.world.entity.VariantHolder;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Firefly extends PathfinderMob implements FlyingAnimal, WWBottleable, VariantHolder<FireflyColor> {
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
		if (EntitySpawnReason.ignoresLightRequirements(reason)) return true;
		return level.getSkyDarken() >= 4 && level.canSeeSky(pos);
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

		Holder<Biome> holder = level.getBiome(this.blockPosition());
		if (spawnData instanceof FireflySpawnGroupData fireflySpawnGroupData) {
			this.setColor(fireflySpawnGroupData.color.value());
		} else {
			Holder<FireflyColor> fireflyColorHolder = FireflyColors.getSpawnVariant(this.registryAccess(), holder, level.getRandom());
			spawnData = new FireflySpawnGroupData(fireflyColorHolder);
			this.setColor(fireflyColorHolder.value());

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
		return 24;
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
		final ItemStack stack = player.getItemInHand(hand);
		final Item item = stack.getItem();
		if (item instanceof DyeItem dyeItem) {
			applyColor: {
				final Optional<DyeColor> optionalFireflyDyeColor = this.getVariant().dyeColor();
				if (optionalFireflyDyeColor.isPresent()) {
					final DyeColor fireflyDyeColor = optionalFireflyDyeColor.get();
					if (fireflyDyeColor == dyeItem.getDyeColor()) break applyColor;
				}

				final Optional<FireflyColor> newFireflyColor = FireflyColor.getByDyeColor(this.registryAccess(), dyeItem.getDyeColor());
				if (newFireflyColor.isEmpty()) break applyColor;

				if (!this.level().isClientSide()) {
					this.setVariant(newFireflyColor.get());
					this.setPersistenceRequired();
					stack.shrink(1);
				}

				this.level().playSound(player, this, SoundEvents.DYE_USE, SoundSource.PLAYERS, 1F, 1F);
				return InteractionResult.SUCCESS;
			}
		}

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
		CompoundTag tag = new CompoundTag();
		tag.putString(MobBottleItem.FIREFLY_BOTTLE_VARIANT_FIELD, this.getColorLocation().toString());
		CustomData.set(
			WWDataComponents.BOTTLE_ENTITY_DATA,
			itemStack,
			tag
		);
	}

	@Override
	public void wilderWild$loadFromBottleTag(@NotNull CompoundTag compoundTag) {
		if (compoundTag.contains(MobBottleItem.FIREFLY_BOTTLE_VARIANT_FIELD)) {
			Optional.ofNullable(ResourceLocation.tryParse(compoundTag.getString(MobBottleItem.FIREFLY_BOTTLE_VARIANT_FIELD)))
				.map(resourceLocation -> ResourceKey.create(WilderWildRegistries.FIREFLY_COLOR, resourceLocation))
				.flatMap(resourceKey -> this.registryAccess().lookupOrThrow(WilderWildRegistries.FIREFLY_COLOR).get(resourceKey))
				.ifPresent(reference -> this.setColor(reference.value()));
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
			&& this.level().isDay()
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
		birdNavigation.setCanPassDoors(true);
		return birdNavigation;
	}

	@Override
	public void travel(@NotNull Vec3 travelVector) {
		if (this.isEffectiveAi() || this.isControlledByLocalInstance()) {
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
			Vec3 wind = WindManager.getWindManager(serverLevel).getWindMovement(this.position(), 1D, 100D, 100D).scale(0.01D);
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
	public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);

		this.getColorAsHolder()
			.unwrapKey()
			.ifPresent(resourceKey -> compoundTag.putString("color", resourceKey.location().toString()));

		compoundTag.putBoolean("fromBottle", this.wilderWild$fromBottle());
		compoundTag.putInt("flickerAge", this.getFlickerAge());
		compoundTag.putFloat("scale", this.getAnimScale());
		compoundTag.putFloat("prevScale", this.getPrevAnimScale());
	}

	@Override
	public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);

		Optional.ofNullable(ResourceLocation.tryParse(compoundTag.getString("color")))
			.map(resourceLocation -> ResourceKey.create(WilderWildRegistries.FIREFLY_COLOR, resourceLocation))
			.flatMap(resourceKey -> this.registryAccess().lookupOrThrow(WilderWildRegistries.FIREFLY_COLOR).get(resourceKey))
			.ifPresent(reference -> this.setColor(reference.value()));

		if (compoundTag.contains("fromBottle")) this.wilderWild$setFromBottle(compoundTag.getBoolean("fromBottle"));
		if (compoundTag.contains("flickerAge")) this.setFlickerAge(compoundTag.getInt("flickerAge"));
		if (compoundTag.contains("scale")) this.setAnimScale(compoundTag.getFloat("scale"));
		if (compoundTag.contains("prevScale")) this.setPrevAnimScale(compoundTag.getFloat("prevScale"));
	}

	@Override
	protected boolean shouldStayCloseToLeashHolder() {
		return false;
	}

	@Override
	public boolean canDisableShield() {
		return false;
	}

	@Override
	protected void sendDebugPackets() {
		super.sendDebugPackets();
		DebugPackets.sendEntityBrain(this);
	}

	@Override
	public boolean causeFallDamage(float fallDistance, float damageMultiplier, @NotNull DamageSource source) {
		return false;
	}

	@Override
	protected void doPush(@NotNull Entity entity) {
	}

	@Override
	protected void pushEntities() {
	}

	@Override
	public void setVariant(FireflyColor color) {
		this.setColor(color);
	}

	@Override
	public @NotNull FireflyColor getVariant() {
		return this.getColorByLocation();
	}

	public static class FireflySpawnGroupData implements SpawnGroupData {
		public final Holder<FireflyColor> color;

		public FireflySpawnGroupData(Holder<FireflyColor> holder) {
			this.color = holder;
		}
	}
}
