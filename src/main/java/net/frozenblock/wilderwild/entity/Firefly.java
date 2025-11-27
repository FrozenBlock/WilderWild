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
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
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
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
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
import org.jetbrains.annotations.Nullable;

public class Firefly extends PathfinderMob implements FlyingAnimal, WWBottleable {
	public static final int RANDOM_FLICKER_AGE_MAX = 19;
	private static final EntityDataAccessor<Boolean> FROM_BOTTLE = SynchedEntityData.defineId(Firefly.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Integer> AGE = SynchedEntityData.defineId(Firefly.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Float> ANIM_SCALE = SynchedEntityData.defineId(Firefly.class, EntityDataSerializers.FLOAT);
	private static final EntityDataAccessor<Float> PREV_ANIM_SCALE = SynchedEntityData.defineId(Firefly.class, EntityDataSerializers.FLOAT);
	private static final EntityDataAccessor<String> COLOR = SynchedEntityData.defineId(Firefly.class, EntityDataSerializers.STRING);

	private Optional<FireflyColor> fireflyColor = Optional.empty();

	public Firefly(EntityType<? extends Firefly> entityType, Level level) {
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
	public float getWalkTargetValue(BlockPos pos, LevelReader level) {
		return -level.getPathfindingCostFromLightLevels(pos);
	}

	public static boolean checkFireflySpawnRules(EntityType<Firefly> type, LevelAccessor level, EntitySpawnReason reason, BlockPos pos, RandomSource random) {
		if (!EntitySpawnReason.isSpawner(reason) && !WWEntityConfig.get().firefly.spawnFireflies) return false;
		if (!(EntitySpawnReason.ignoresLightRequirements(reason) || level.getMaxLocalRawBrightness(pos) <= 13)) return false;
		if (!WWEntityConfig.get().firefly.firefliesNeedBush) return true;
		return ((int)(random.nextDouble() * getNearbyFireflyBushCount(level, pos, 3))) >= 1;
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes()
			.add(Attributes.MAX_HEALTH, 1D)
			.add(Attributes.MOVEMENT_SPEED, 0.08D)
			.add(Attributes.FLYING_SPEED, 0.08D)
			.add(Attributes.FOLLOW_RANGE, 32D);
	}

	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, EntitySpawnReason reason, @Nullable SpawnGroupData spawnData) {
		final boolean shouldSetHome = shouldSetHome(reason);
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
		return reason == EntitySpawnReason.DISPENSER || reason == EntitySpawnReason.BUCKET;
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
		builder.define(COLOR, FireflyColors.DEFAULT.identifier().toString());
	}

	@Override
	public boolean dampensVibrations() {
		return true;
	}

	@Override
	protected InteractionResult mobInteract(Player player, InteractionHand hand) {
		final ItemStack stack = player.getItemInHand(hand);
		final Item item = stack.getItem();
		applyColor : {
			if (!(item instanceof DyeItem dyeItem)) break applyColor;
			final Optional<DyeColor> optionalFireflyDyeColor = this.getVariant().dyeColor();
			if (optionalFireflyDyeColor.isPresent()) {
				final DyeColor fireflyDyeColor = optionalFireflyDyeColor.get();
				if (fireflyDyeColor == dyeItem.getDyeColor()) break applyColor;
			}

			final Optional<FireflyColor> newFireflyColor = FireflyColor.getByDyeColor(this.registryAccess(), dyeItem.getDyeColor());
			if (newFireflyColor.isEmpty()) break applyColor;

			if (!this.level().isClientSide()) {
				this.setColor(newFireflyColor.get());
				this.setPersistenceRequired();
				stack.shrink(1);
			}

			this.level().playSound(player, this, SoundEvents.DYE_USE, SoundSource.PLAYERS, 1F, 1F);
			return InteractionResult.SUCCESS;
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
	protected Brain.Provider<Firefly> brainProvider() {
		return FireflyAi.brainProvider();
	}

	@Override
	protected Brain<?> makeBrain(Dynamic<?> dynamic) {
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
	public void wilderWild$saveToBottleTag(ItemStack stack) {
		WWBottleable.saveDefaultDataToBottleTag(this, stack);
		stack.copyFrom(WWDataComponents.FIREFLY_COLOR, this);
	}

	@Override
	public void wilderWild$loadFromBottleTag(CompoundTag tag) {
		WWBottleable.loadDefaultDataFromBottleTag(this, tag);
	}

	@Nullable
	@Override
	public <T> T get(DataComponentType<? extends T> type) {
		if (type == WWDataComponents.FIREFLY_COLOR) return castComponentValue(type, this.getColorAsHolder());
		return super.get(type);
	}

	@Override
	protected void applyImplicitComponents(DataComponentGetter getter) {
		this.applyImplicitComponentIfPresent(getter, WWDataComponents.FIREFLY_COLOR);
		super.applyImplicitComponents(getter);
	}

	@Override
	protected <T> boolean applyImplicitComponent(DataComponentType<T> type, T object) {
		if (type == WWDataComponents.FIREFLY_COLOR) {
			this.setColor(castComponentValue(WWDataComponents.FIREFLY_COLOR, object).value());
			return true;
		}
		return super.applyImplicitComponent(type, object);
	}

	@Override
	public void wilderWild$onBottled() {
		if (this.isSwarmLeader()) FireflyAi.transferLeadershipToRandomFirefly(this);
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

	public Identifier getColorLocation() {
		return Identifier.parse(this.entityData.get(COLOR));
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

	public void setColor(FireflyColor color) {
		this.entityData.set(COLOR, Objects.requireNonNull(this.registryAccess().lookupOrThrow(WilderWildRegistries.FIREFLY_COLOR).getKey(color)).toString());
	}

	public void setColor(Identifier color) {
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
	protected PathNavigation createNavigation(Level level) {
		final FlyingPathNavigation birdNavigation = new FlyingPathNavigation(this, level);
		birdNavigation.setCanOpenDoors(false);
		birdNavigation.setCanFloat(true);
		birdNavigation.getNodeEvaluator().setCanPassDoors(true);
		return birdNavigation;
	}

	@Override
	public void travel(Vec3 travelVector) {
		if (!this.isEffectiveAi() && !this.isLocalInstanceAuthoritative()) return;
		if (!this.isAlive()) {
			super.travel(travelVector);
			return;
		}
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
	}

	public static int getNearbyFireflyBushCount(LevelAccessor level, BlockPos origin, int distance) {
		int count = 0;
		Iterable<BlockPos> posesToCheck = BlockPos.betweenClosed(origin.offset(-distance, -distance, -distance), origin.offset(distance, distance, distance));
		for (BlockPos pos : posesToCheck) {
			if (level.getBlockState(pos).is(Blocks.FIREFLY_BUSH)) count += 1;
		}
		return count;
	}

	@Override
	protected void playStepSound(BlockPos pos, BlockState state) {
	}

	@Override
	protected void checkFallDamage(double heightDifference, boolean onGround, BlockState state, BlockPos landedPosition) {
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
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
		final float animScale = this.getAnimScale();
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
		final ProfilerFiller profiler = Profiler.get();
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
	public void die(DamageSource source) {
		if (this.isSwarmLeader()) FireflyAi.transferLeadershipToRandomFirefly(this);
		super.die(source);
	}

	@Override
	public void addAdditionalSaveData(ValueOutput valueOutput) {
		super.addAdditionalSaveData(valueOutput);

		this.getColorAsHolder()
			.unwrapKey()
			.ifPresent(resourceKey -> valueOutput.putString("color", resourceKey.identifier().toString()));

		valueOutput.putBoolean("fromBottle", this.wilderWild$fromBottle());
		valueOutput.putInt("flickerAge", this.getFlickerAge());
		valueOutput.putFloat("scale", this.getAnimScale());
		valueOutput.putFloat("prevScale", this.getPrevAnimScale());
	}

	@Override
	public void readAdditionalSaveData(ValueInput valueInput) {
		super.readAdditionalSaveData(valueInput);

		valueInput.getString("color").flatMap(string -> Optional.ofNullable(Identifier.tryParse(string))
				.map(identifier -> ResourceKey.create(WilderWildRegistries.FIREFLY_COLOR, identifier))
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
	public boolean causeFallDamage(double fallDistance, float damageMultiplier, DamageSource source) {
		return false;
	}

	@Override
	protected void doPush(Entity entity) {
	}

	@Override
	protected void pushEntities() {
	}

	public record FireflySpawnGroupData(Holder<FireflyColor> color) implements SpawnGroupData {
	}
}
