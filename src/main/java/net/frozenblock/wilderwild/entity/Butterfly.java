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
import net.frozenblock.wilderwild.registry.WWDataComponents;
import net.frozenblock.wilderwild.registry.WWItems;
import net.frozenblock.wilderwild.registry.WWSounds;
import net.frozenblock.wilderwild.registry.WilderWildRegistries;
import net.frozenblock.wilderwild.tag.WWBiomeTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.Identifier;
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
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.variant.SpawnContext;
import net.minecraft.world.entity.variant.VariantUtils;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class Butterfly extends PathfinderMob implements FlyingAnimal, WWBottleable {
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

	public Butterfly(EntityType<? extends Butterfly> entityType, Level level) {
		super(entityType, level);
		this.setPathfindingMalus(PathType.LAVA, -1F);
		this.setPathfindingMalus(PathType.DANGER_FIRE, -1F);
		this.setPathfindingMalus(PathType.WATER, -1F);
		this.setPathfindingMalus(PathType.WATER_BORDER, 16F);
		this.setPathfindingMalus(PathType.UNPASSABLE_RAIL, 0F);
		this.moveControl = new FlyingMoveControl(this, 20, true);
	}

	@Override
	public float getWalkTargetValue(BlockPos pos, LevelReader level) {
		final float bonus = level.getBlockState(pos).is(BlockTags.FLOWERS) ? 7F : 0F;
		return level.getPathfindingCostFromLightLevels(pos) + bonus;
	}

	public static boolean checkButterflySpawnRules(EntityType<Butterfly> type, LevelAccessor level, EntitySpawnReason reason, BlockPos pos, RandomSource random) {
		if (!EntitySpawnReason.isSpawner(reason) && !WWEntityConfig.get().butterfly.spawnButterflies) return false;
		if (!(EntitySpawnReason.ignoresLightRequirements(reason) || Animal.isBrightEnoughToSpawn(level, pos))) return true;
		if (level.getBiome(pos).is(WWBiomeTags.BUTTERFLY_VERY_RARE_SPAWN) && random.nextInt(30) != 0) return false;
		return level.getBlockState(pos.below()).is(BlockTags.ANIMALS_SPAWNABLE_ON);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes()
			.add(Attributes.MAX_HEALTH, 2D)
			.add(Attributes.MOVEMENT_SPEED, 0.1D)
			.add(Attributes.FLYING_SPEED, 0.8D)
			.add(Attributes.FOLLOW_RANGE, 32D);
	}

	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, EntitySpawnReason reason, @Nullable SpawnGroupData spawnData) {
		final boolean shouldSetHome = shouldSetHome(reason);
		if (shouldSetHome) {
			ButterflyAi.rememberHome(this, this.blockPosition());
		} else {
			ButterflyAi.setNatural(this);
		}

		if (spawnData instanceof ButterflySpawnGroupData butterflySpawnGroupData) {
			this.setVariant(butterflySpawnGroupData.type.value());
		} else {
			final Optional<Holder.Reference<ButterflyVariant>> optionalButterflyVariant = VariantUtils.selectVariantToSpawn(
				SpawnContext.create(level, this.blockPosition()),
				WilderWildRegistries.BUTTERFLY_VARIANT
			);
			if (optionalButterflyVariant.isPresent()) {
				spawnData = new ButterflySpawnGroupData(optionalButterflyVariant.get());
				this.setVariant(optionalButterflyVariant.get().value());
			}
		}

		return super.finalizeSpawn(level, difficulty, reason, spawnData);
	}

	private static boolean shouldSetHome(EntitySpawnReason reason) {
		return reason == EntitySpawnReason.DISPENSER || reason == EntitySpawnReason.BUCKET;
	}

	@Override
	public int decreaseAirSupply(int currentAir) {
		final int newSupply = super.decreaseAirSupply(currentAir);
		return newSupply == currentAir - 1 ? newSupply - 1 : newSupply;
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(FROM_BOTTLE, false);
		builder.define(VARIANT, ButterflyVariants.DEFAULT.identifier().toString());
	}

	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> entityDataAccessor) {
		super.onSyncedDataUpdated(entityDataAccessor);
		if (entityDataAccessor.equals(VARIANT)) this.butterflyVariant = Optional.of(this.getVariant());
	}

	@Override
	protected InteractionResult mobInteract(Player player, InteractionHand hand) {
		return WWBottleable.bottleMobPickup(player, hand, this).orElse(super.mobInteract(player, hand));
	}

	@Override
	public boolean shouldRender(double x, double y, double z) {
		return true;
	}

	@Override
	protected Brain.Provider<Butterfly> brainProvider() {
		return ButterflyAi.brainProvider();
	}

	@Override
	protected Brain<?> makeBrain(Dynamic<?> dynamic) {
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
	public void wilderWild$saveToBottleTag(ItemStack stack) {
		WWBottleable.saveDefaultDataToBottleTag(this, stack);
		stack.copyFrom(WWDataComponents.BUTTERFLY_VARIANT, this);
	}

	@Override
	public void wilderWild$loadFromBottleTag(CompoundTag tag) {
		WWBottleable.loadDefaultDataFromBottleTag(this, tag);
	}

	@Nullable
	@Override
	public <T> T get(DataComponentType<? extends T> type) {
		if (type == WWDataComponents.BUTTERFLY_VARIANT) return castComponentValue(type, this.getVariantAsHolder());
		return super.get(type);
	}

	@Override
	protected void applyImplicitComponents(DataComponentGetter getter) {
		this.applyImplicitComponentIfPresent(getter, WWDataComponents.BUTTERFLY_VARIANT);
		super.applyImplicitComponents(getter);
	}

	@Override
	protected <T> boolean applyImplicitComponent(DataComponentType<T> type, T object) {
		if (type == WWDataComponents.BUTTERFLY_VARIANT) {
			this.setVariant(castComponentValue(WWDataComponents.BUTTERFLY_VARIANT, object).value());
			return true;
		}
		return super.applyImplicitComponent(type, object);
	}

	@Override
	public void wilderWild$onBottled() {
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

	public Identifier getVariantIdentifier() {
		return Identifier.parse(this.entityData.get(VARIANT));
	}

	public ButterflyVariant getVariantByIdentifier() {
		return this.registryAccess().lookupOrThrow(WilderWildRegistries.BUTTERFLY_VARIANT).getValue(this.getVariantIdentifier());
	}

	public Holder<ButterflyVariant> getVariantAsHolder() {
		return this.registryAccess().lookupOrThrow(WilderWildRegistries.BUTTERFLY_VARIANT).get(this.getVariantIdentifier()).orElseThrow();
	}

	public ButterflyVariant getVariantForRendering() {
		return this.butterflyVariant.orElse(this.registryAccess().lookupOrThrow(WilderWildRegistries.BUTTERFLY_VARIANT).getValue(ButterflyVariants.DEFAULT));
	}

	public void setVariant(ButterflyVariant variant) {
		this.entityData.set(VARIANT, Objects.requireNonNull(this.registryAccess().lookupOrThrow(WilderWildRegistries.BUTTERFLY_VARIANT).getKey(variant)).toString());
	}

	public void setVariant(Identifier variant) {
		this.entityData.set(VARIANT, variant.toString());
	}

	public ButterflyVariant getVariant() {
		return this.getVariantByIdentifier();
	}

	@Override
	public boolean requiresCustomPersistence() {
		return super.requiresCustomPersistence() || this.wilderWild$fromBottle();
	}

	@Override
	public Brain<Butterfly> getBrain() {
		return (Brain<Butterfly>) super.getBrain();
	}

	@Override
	public boolean isFlying() {
		return !this.onGround();
	}

	@Override
	protected PathNavigation createNavigation(Level level) {
		FlyingPathNavigation birdNavigation = new FlyingPathNavigation(this, level);
		birdNavigation.setCanOpenDoors(false);
		birdNavigation.setCanFloat(true);
		birdNavigation.getNodeEvaluator().setCanPassDoors(true);
		return birdNavigation;
	}

	@Override
	protected void playStepSound(BlockPos pos, BlockState state) {
	}

	@Override
	protected void checkFallDamage(double heightDifference, boolean onGround, BlockState state, BlockPos landedPosition) {
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
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
			Vec3 wind = WindManager.getOrCreateWindManager(serverLevel).getWindMovement(this.position(), 1D, 100D, 100D).scale(0.01D);
			wind = wind.subtract(0D, wind.y * 0.7D, 0D);
			this.setDeltaMovement(this.getDeltaMovement().add(wind.scale(0.02D)));
		}

		final Vec3 deltaMovement = this.getDeltaMovement();
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
		final ProfilerFiller profilerFiller = Profiler.get();
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
	public void addAdditionalSaveData(ValueOutput valueOutput) {
		super.addAdditionalSaveData(valueOutput);
		VariantUtils.writeVariant(valueOutput, this.getVariantAsHolder());
		valueOutput.putBoolean("fromBottle", this.wilderWild$fromBottle());
	}

	@Override
	public void readAdditionalSaveData(ValueInput valueInput) {
		super.readAdditionalSaveData(valueInput);
		VariantUtils.readVariant(valueInput, WilderWildRegistries.BUTTERFLY_VARIANT)
			.ifPresent(butterflyVariantHolder -> this.setVariant(butterflyVariantHolder.value()));
		this.wilderWild$setFromBottle(valueInput.getBooleanOr("fromBottle", false));
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

	public static class ButterflySpawnGroupData extends AgeableMob.AgeableMobGroupData {
		public final Holder<ButterflyVariant> type;

		public ButterflySpawnGroupData(Holder<ButterflyVariant> holder) {
			super(false);
			this.type = holder;
		}
	}
}
