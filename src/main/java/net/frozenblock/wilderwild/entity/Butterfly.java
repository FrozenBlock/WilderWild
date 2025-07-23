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
import net.frozenblock.wilderwild.entity.ai.butterfly.ButterflyAi;
import net.frozenblock.wilderwild.entity.impl.WWBottleable;
import net.frozenblock.wilderwild.entity.variant.butterfly.ButterflyVariant;
import net.frozenblock.wilderwild.entity.variant.butterfly.ButterflyVariants;
import net.frozenblock.wilderwild.item.MobBottleItem;
import net.frozenblock.wilderwild.registry.WWDataComponents;
import net.frozenblock.wilderwild.registry.WWItems;
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
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.profiling.Profiler;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Butterfly extends PathfinderMob implements FlyingAnimal, WWBottleable, VariantHolder<ButterflyVariant> {
	public static final int TICKS_PER_FLAP = 3;
	private static final EntityDataAccessor<Boolean> FROM_BOTTLE = SynchedEntityData.defineId(Butterfly.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<String> VARIANT = SynchedEntityData.defineId(Butterfly.class, EntityDataSerializers.STRING);

	private float prevFlyingXRot;
	private float flyingXRot;
	private float prevDownProgress;
	private float downProgress;
	private float prevGroundProgress;
	private float groundProgress;
	private Optional<ButterflyVariant> butterflyVariant = Optional.empty();

	public Butterfly(@NotNull EntityType<? extends Butterfly> entityType, @NotNull Level level) {
		super(entityType, level);
		this.setPathfindingMalus(PathType.LAVA, -1F);
		this.setPathfindingMalus(PathType.DANGER_FIRE, -1F);
		this.setPathfindingMalus(PathType.WATER, -1F);
		this.setPathfindingMalus(PathType.WATER_BORDER, 16F);
		this.setPathfindingMalus(PathType.UNPASSABLE_RAIL, 0F);
		this.moveControl = new FlyingMoveControl(this, 20, true);
	}

	@Override
	public float getWalkTargetValue(BlockPos blockPos, @NotNull LevelReader levelReader) {
		float bonus = levelReader.getBlockState(blockPos).is(BlockTags.FLOWERS) ? 7F : 0F;
		return levelReader.getPathfindingCostFromLightLevels(blockPos) + bonus;
	}

	public static boolean checkButterflySpawnRules(
		@NotNull EntityType<Butterfly> type, @NotNull LevelAccessor level, EntitySpawnReason reason, @NotNull BlockPos pos, @NotNull RandomSource random
	) {
		if (!EntitySpawnReason.isSpawner(reason) && !WWEntityConfig.get().butterfly.spawnButterflies) return false;
		if (EntitySpawnReason.ignoresLightRequirements(reason)) return true;

		return level.getRawBrightness(pos, 0) > 8 && level.getBlockState(pos.below()).is(BlockTags.ANIMALS_SPAWNABLE_ON);
	}

	@NotNull
	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes()
			.add(Attributes.MAX_HEALTH, 2D)
			.add(Attributes.MOVEMENT_SPEED, 0.1D)
			.add(Attributes.FLYING_SPEED, 0.8D)
			.add(Attributes.FOLLOW_RANGE, 32D);
	}

	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(
		@NotNull ServerLevelAccessor level, @NotNull DifficultyInstance difficulty, @NotNull EntitySpawnReason reason, @Nullable SpawnGroupData spawnData
	) {
		boolean shouldSetHome = shouldSetHome(reason);
		if (shouldSetHome) {
			ButterflyAi.rememberHome(this, this.blockPosition());
		} else {
			ButterflyAi.setNatural(this);
		}

		Holder<Biome> holder = level.getBiome(this.blockPosition());
		if (spawnData instanceof ButterflySpawnGroupData butterflySpawnGroupData) {
			this.setVariant(butterflySpawnGroupData.type.value());
		} else {
			Holder<ButterflyVariant> butterflyVariantHolder = ButterflyVariants.getSpawnVariant(this.registryAccess(), holder, level.getRandom());
			spawnData = new ButterflySpawnGroupData(butterflyVariantHolder);
			this.setVariant(butterflyVariantHolder.value());
		}

		return super.finalizeSpawn(level, difficulty, reason, spawnData);
	}

	private static boolean shouldSetHome(EntitySpawnReason reason) {
		return reason == EntitySpawnReason.DISPENSER
			|| reason == EntitySpawnReason.BUCKET;
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
		builder.define(VARIANT, ButterflyVariants.DEFAULT.location().toString());
	}

	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> entityDataAccessor) {
		super.onSyncedDataUpdated(entityDataAccessor);
		if (entityDataAccessor.equals(VARIANT)) this.butterflyVariant = Optional.of(this.getVariant());
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
	@NotNull
	protected Brain.Provider<Butterfly> brainProvider() {
		return ButterflyAi.brainProvider();
	}

	@Override
	@NotNull
	protected Brain<?> makeBrain(@NotNull Dynamic<?> dynamic) {
		return ButterflyAi.makeBrain(this, this.brainProvider().makeBrain(dynamic));
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

		CustomData.update(
			WWDataComponents.BOTTLE_ENTITY_DATA,
			itemStack,
			compoundTag -> compoundTag.putString(MobBottleItem.BUTTERFLY_BOTTLE_VARIANT_FIELD, this.getVariantLocation().toString())
		);
	}

	@Override
	public void wilderWild$loadFromBottleTag(CompoundTag compoundTag) {
		WWBottleable.loadDefaultDataFromBottleTag(this, compoundTag);
		if (compoundTag.contains(MobBottleItem.BUTTERFLY_BOTTLE_VARIANT_FIELD)) {
			Optional.ofNullable(ResourceLocation.tryParse(compoundTag.getString(MobBottleItem.BUTTERFLY_BOTTLE_VARIANT_FIELD)))
				.map(resourceLocation -> ResourceKey.create(WilderWildRegistries.BUTTERFLY_VARIANT, resourceLocation))
				.flatMap(resourceKey -> this.registryAccess().lookupOrThrow(WilderWildRegistries.BUTTERFLY_VARIANT).get(resourceKey))
				.ifPresent(reference -> this.setVariant(reference.value()));
		}
	}

	@Override
	public void wilderWild$onCapture() {
	}

	@Override
	public void wilderWild$onBottleRelease() {
		ButterflyAi.rememberHome(this, this.blockPosition());
	}

	@Override
	public ItemStack wilderWild$getBottleItemStack() {
		return new ItemStack(WWItems.BUTTERFLY_BOTTLE);
	}

	@Override
	public SoundEvent wilderWild$getBottleCatchSound() {
		return WWSounds.ITEM_BOTTLE_CATCH_BUTTERFLY;
	}

	public boolean hasHome() {
		return this.getBrain().hasMemoryValue(MemoryModuleType.HOME);
	}

	public ResourceLocation getVariantLocation() {
		return ResourceLocation.parse(this.entityData.get(VARIANT));
	}

	public ButterflyVariant getVariantByLocation() {
		return this.registryAccess().lookupOrThrow(WilderWildRegistries.BUTTERFLY_VARIANT).getValue(this.getVariantLocation());
	}

	public Holder<ButterflyVariant> getVariantAsHolder() {
		return this.registryAccess().lookupOrThrow(WilderWildRegistries.BUTTERFLY_VARIANT).get(this.getVariantLocation()).orElseThrow();
	}

	public ButterflyVariant getVariantForRendering() {
		return this.butterflyVariant.orElse(this.registryAccess().lookupOrThrow(WilderWildRegistries.BUTTERFLY_VARIANT).getValue(ButterflyVariants.DEFAULT));
	}

	@Override
	public void setVariant(@NotNull ButterflyVariant variant) {
		this.entityData.set(VARIANT, Objects.requireNonNull(this.registryAccess().lookupOrThrow(WilderWildRegistries.BUTTERFLY_VARIANT).getKey(variant)).toString());
	}

	@Override
	public @NotNull ButterflyVariant getVariant() {
		return this.getVariantByLocation();
	}

	public void setVariant(@NotNull ResourceLocation variant) {
		this.entityData.set(VARIANT, variant.toString());
	}

	@Override
	public boolean requiresCustomPersistence() {
		return super.requiresCustomPersistence() || this.wilderWild$fromBottle();
	}

	@Override
	@NotNull
	public Brain<Butterfly> getBrain() {
		return (Brain<Butterfly>) super.getBrain();
	}

	@Override
	public boolean isFlying() {
		return !this.onGround();
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
		return WWSounds.ENTITY_BUTTERFLY_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return WWSounds.ENTITY_BUTTERFLY_DEATH;
	}

	@Override
	public void tick() {
		super.tick();
		if (!this.isAlive()) this.setNoGravity(false);

		if (this.level() instanceof ServerLevel serverLevel) {
			Vec3 wind = WindManager.getWindManager(serverLevel).getWindMovement(this.position(), 1D, 100D, 100D).scale(0.01D);
			wind = wind.subtract(0D, wind.y * 0.7D, 0D);
			this.setDeltaMovement(this.getDeltaMovement().add(wind.scale(0.02D)));
		}

		Vec3 deltaMovement = this.getDeltaMovement();
		float targetFlyingXRot = (float) Math.clamp(deltaMovement.y * 10F, -1F, 1F);
		this.prevFlyingXRot = this.flyingXRot;
		this.flyingXRot += (targetFlyingXRot - this.flyingXRot) * 0.1F;

		this.prevDownProgress = this.downProgress;
		this.downProgress += ((deltaMovement.y < 0D ? 1F : 0F) - this.downProgress) * 0.05F;

		this.prevGroundProgress = this.groundProgress;
		this.groundProgress += ((this.onGround() ? 1F : 0F) - this.groundProgress) * 0.2F;
	}

	public float getFlyingXRot(float partialTick) {
		return Mth.lerp(partialTick, this.prevFlyingXRot, this.flyingXRot);
	}

	public float getDownProgress(float partialTick) {
		return Mth.lerp(partialTick, this.prevDownProgress, this.downProgress);
	}

	public float getGroundProgress(float partialTick) {
		return Mth.lerp(partialTick, this.prevGroundProgress, this.groundProgress);
	}

	@Override
	protected void customServerAiStep(ServerLevel serverLevel) {
		ProfilerFiller profilerFiller = Profiler.get();
		profilerFiller.push("butterflyBrain");
		this.getBrain().tick((ServerLevel) this.level(), this);
		profilerFiller.pop();
		profilerFiller.push("butterflyActivityUpdate");
		ButterflyAi.updateActivities(this);
		profilerFiller.pop();
		super.customServerAiStep(serverLevel);
	}

	@Override
	public boolean isFlapping() {
		return this.isFlying() && this.tickCount % TICKS_PER_FLAP == 0;
	}

	@Override
	public boolean removeWhenFarAway(double distanceToClosestPlayer) {
		return !this.wilderWild$fromBottle() && !this.hasCustomName();
	}

	@Override
	public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putString("variant", this.getVariantLocation().toString());
		compoundTag.putBoolean("fromBottle", this.wilderWild$fromBottle());
	}

	@Override
	public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		Optional.ofNullable(ResourceLocation.tryParse(compoundTag.getString("variant")))
			.map(resourceLocation -> ResourceKey.create(WilderWildRegistries.BUTTERFLY_VARIANT, resourceLocation))
			.flatMap(resourceKey -> this.registryAccess().lookupOrThrow(WilderWildRegistries.BUTTERFLY_VARIANT).get(resourceKey))
			.ifPresent(reference -> this.setVariant(reference.value()));
		if (compoundTag.contains("fromBottle")) this.wilderWild$setFromBottle(compoundTag.getBoolean("fromBottle"));
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

	public static class ButterflySpawnGroupData extends AgeableMob.AgeableMobGroupData {
		public final Holder<ButterflyVariant> type;

		public ButterflySpawnGroupData(Holder<ButterflyVariant> holder) {
			super(false);
			this.type = holder;
		}
	}
}
