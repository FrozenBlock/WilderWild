/*
 * Copyright 2023-2024 FrozenBlock
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
import java.util.Objects;
import net.frozenblock.lib.wind.api.WindManager;
import net.frozenblock.wilderwild.config.WWEntityConfig;
import net.frozenblock.wilderwild.entity.ai.butterfly.ButterflyAi;
import net.frozenblock.wilderwild.entity.impl.Bottleable;
import net.frozenblock.wilderwild.entity.variant.ButterflyVariant;
import net.frozenblock.wilderwild.registry.WWDataComponents;
import net.frozenblock.wilderwild.registry.WWItems;
import net.frozenblock.wilderwild.registry.WWSounds;
import net.frozenblock.wilderwild.registry.WilderWildRegistries;
import net.frozenblock.wilderwild.tag.WWBiomeTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.player.Player;
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
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Butterfly extends PathfinderMob implements FlyingAnimal, Bottleable {
	public static final int SPAWN_CHANCE = 50;
	protected static final List<SensorType<? extends Sensor<? super Butterfly>>> SENSORS = List.of(SensorType.NEAREST_LIVING_ENTITIES);
	protected static final List<MemoryModuleType<?>> MEMORY_MODULES = List.of(
		MemoryModuleType.PATH,
		MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES,
		MemoryModuleType.WALK_TARGET,
		MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
		MemoryModuleType.LOOK_TARGET,
		MemoryModuleType.HOME
	);
	private static final double SPAWN_RADIUS_CHECK_DISTANCE = 20D;
	private static final TargetingConditions SPAWN_RADIUS_CHECK = TargetingConditions.forNonCombat()
		.ignoreLineOfSight()
		.ignoreInvisibilityTesting()
		.range(SPAWN_RADIUS_CHECK_DISTANCE)
		.selector(entity -> entity.isAlive() && !entity.isSpectator());
	private static final EntityDataAccessor<Boolean> FROM_BOTTLE = SynchedEntityData.defineId(Butterfly.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<String> VARIANT = SynchedEntityData.defineId(Butterfly.class, EntityDataSerializers.STRING);

	public boolean natural;
	public boolean hasHome;
	public int homeCheckCooldown;

	private float prevFlyingXRot;
	private float flyingXRot;

	private float prevDownProgress;
	private float downProgress;

	private float prevGroundProgress;
	private float groundProgress;

	public Butterfly(@NotNull EntityType<? extends Butterfly> entityType, @NotNull Level level) {
		super(entityType, level);
		this.setPathfindingMalus(PathType.LAVA, -1F);
		this.setPathfindingMalus(PathType.DANGER_FIRE, -1F);
		this.setPathfindingMalus(PathType.WATER, -1F);
		this.setPathfindingMalus(PathType.WATER_BORDER, 16F);
		this.setPathfindingMalus(PathType.UNPASSABLE_RAIL, 0F);
		this.moveControl = new FlyingMoveControl(this, 20, true);
		this.setVariant(ButterflyVariant.MONARCH);
	}

	public static boolean checkButterflySpawnRules(
		@NotNull EntityType<Butterfly> type, @NotNull LevelAccessor level, MobSpawnType spawnType, @NotNull BlockPos pos, @NotNull RandomSource random
	) {
		if (!MobSpawnType.isSpawner(spawnType) && !WWEntityConfig.get().butterfly.spawnButterflies) return false;
		if (MobSpawnType.ignoresLightRequirements(spawnType)) return true;

		Holder<Biome> biome = level.getBiome(pos);
		if (!biome.is(WWBiomeTags.HAS_COMMON_BUTTERFLY)) {
			Vec3 spawnPos = Vec3.atCenterOf(pos);
			Butterfly nearestButterfly = level.getNearestEntity(
				Butterfly.class,
				SPAWN_RADIUS_CHECK,
				null,
				spawnPos.x,
				spawnPos.y,
				spawnPos.z,
				AABB.ofSize(spawnPos, SPAWN_RADIUS_CHECK_DISTANCE, SPAWN_RADIUS_CHECK_DISTANCE, SPAWN_RADIUS_CHECK_DISTANCE)
			);
			if (nearestButterfly == null) {
				return true;
			}
		}

		return random.nextInt(0, SPAWN_CHANCE) == 0 && (level.getBrightness(LightLayer.SKY, pos) >= 6) || ((!level.dimensionType().hasFixedTime() && level.getSkyDarken() > 4) && level.canSeeSky(pos));
	}

	@NotNull
	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes()
			.add(Attributes.MAX_HEALTH, 4D)
			.add(Attributes.MOVEMENT_SPEED, 0.1D)
			.add(Attributes.FLYING_SPEED, 0.8D)
			.add(Attributes.FOLLOW_RANGE, 32D);
	}

	public static boolean isValidHomePos(@NotNull Level level, @NotNull BlockPos pos) {
		BlockState state = level.getBlockState(pos);
		if (!state.getFluidState().isEmpty()) return false;
		if (state.isRedstoneConductor(level, pos)) return false;
		return state.isAir() || (!state.blocksMotion() && !state.isSolid());
	}

	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor level, @NotNull DifficultyInstance difficulty, @NotNull MobSpawnType spawnType, @Nullable SpawnGroupData spawnData) {
		this.natural = isButterflySpawnTypeNatural(spawnType);
		this.hasHome = this.hasHome || !this.natural;

		BlockPos pos = this.blockPosition();
		ButterflyAi.rememberHome(this, pos);
		Holder<Biome> biome = level.getBiome(pos);
		ButterflyVariant variant = ButterflyVariant.getRandomVariantForBiome(biome, this.random);

		this.setVariant(variant);

		return super.finalizeSpawn(level, difficulty, spawnType, spawnData);
	}

	private static boolean isButterflySpawnTypeNatural(MobSpawnType spawnType) {
		return spawnType == MobSpawnType.NATURAL
			|| spawnType == MobSpawnType.CHUNK_GENERATION
			|| spawnType == MobSpawnType.SPAWNER
			|| spawnType == MobSpawnType.SPAWN_EGG
			|| spawnType == MobSpawnType.COMMAND;
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
		builder.define(VARIANT, ButterflyVariant.MONARCH.key().toString());
	}


	@Override
	@NotNull
	protected InteractionResult mobInteract(@NotNull Player player, @NotNull InteractionHand hand) {
		return Bottleable.bottleMobPickup(player, hand, this).orElse(super.mobInteract(player, hand));
	}

	@Override
	public boolean shouldRender(double x, double y, double z) {
		return true;
	}

	@Override
	@NotNull
	protected Brain.Provider<Butterfly> brainProvider() {
		return Brain.provider(MEMORY_MODULES, SENSORS);
	}

	@Override
	@NotNull
	protected Brain<?> makeBrain(@NotNull Dynamic<?> dynamic) {
		return ButterflyAi.makeBrain(this, this.brainProvider().makeBrain(dynamic));
	}

	@Override
	public boolean fromBottle() {
		return this.entityData.get(FROM_BOTTLE);
	}

	@Override
	public void setFromBottle(boolean value) {
		this.entityData.set(FROM_BOTTLE, value);
	}

	@Override
	public void saveToBottleTag(ItemStack itemStack) {
		Bottleable.saveDefaultDataToBottleTag(this, itemStack);
		CustomData.update(
			WWDataComponents.BOTTLE_ENTITY_DATA,
			itemStack,
			compoundTag -> compoundTag.putString("ButterflyBottleVariantTag", this.getVariant().getSerializedName())
		);
	}

	@Override
	public void loadFromBottleTag(CompoundTag compoundTag) {
		Bottleable.loadDefaultDataFromBottleTag(this, compoundTag);
		if (compoundTag.contains("ButterflyBottleVariantTag")) {
			ButterflyVariant variant = WilderWildRegistries.BUTTERFLY_VARIANT.get(ResourceLocation.tryParse(compoundTag.getString("ButterflyBottleVariantTag")));
			if (variant != null) {
				this.setVariant(variant);
			}
		}
	}

	@Override
	public void onBottleRelease() {
		ButterflyAi.rememberHome(this, this.blockPosition());
		this.hasHome = true;
	}

	@Override
	public ItemStack getBottleItemStack() {
		return new ItemStack(WWItems.BUTTERFLY_BOTTLE);
	}

	@Override
	public SoundEvent getBottleCatchSound() {
		return WWSounds.ITEM_BOTTLE_CATCH_BUTTERFLY;
	}

	public ButterflyVariant getVariant() {
		return WilderWildRegistries.BUTTERFLY_VARIANT.getOptional(ResourceLocation.parse(this.entityData.get(VARIANT))).orElse(ButterflyVariant.MONARCH);
	}

	public void setVariant(@NotNull ButterflyVariant variant) {
		this.entityData.set(VARIANT, variant.key().toString());
	}

	@Override
	public boolean requiresCustomPersistence() {
		return super.requiresCustomPersistence() || this.fromBottle();
	}

	@Override
	public float getWalkTargetValue(@NotNull BlockPos pos, @NotNull LevelReader level) {
		return 0F;
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
		return WWSounds.ENTITY_FIREFLY_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return WWSounds.ENTITY_FIREFLY_HURT;
	}

	@Override
	public void tick() {
		super.tick();
		if (!this.isAlive()) {
			this.setNoGravity(false);
		}

		if (this.hasHome) {
			if (this.homeCheckCooldown > 0) {
				--this.homeCheckCooldown;
			} else {
				this.homeCheckCooldown = 200;
				BlockPos home = ButterflyAi.getHome(this);
				if (home != null && ButterflyAi.isInHomeDimension(this)) {
					if (!isValidHomePos(this.level(), home)) {
						ButterflyAi.rememberHome(this, this.blockPosition());
					}
				}
			}
		}

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
	protected void customServerAiStep() {
		this.level().getProfiler().push("butterflyBrain");
		this.getBrain().tick((ServerLevel) this.level(), this);
		this.level().getProfiler().pop();
		this.level().getProfiler().push("butterflyActivityUpdate");
		ButterflyAi.updateActivities(this);
		this.level().getProfiler().pop();
		super.customServerAiStep();
	}

	@Override
	public boolean canTakeItem(@NotNull ItemStack stack) {
		return false;
	}

	@Override
	public boolean isFlapping() {
		return true;
	}

	@Override
	public boolean removeWhenFarAway(double distanceToClosestPlayer) {
		return !this.fromBottle() && !this.hasCustomName();
	}

	@Override
	public void addAdditionalSaveData(@NotNull CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putBoolean("fromBottle", this.fromBottle());
		compound.putBoolean("natural", this.natural);
		compound.putBoolean("hasHome", this.hasHome);
		compound.putString("variant", Objects.requireNonNull(WilderWildRegistries.BUTTERFLY_VARIANT.getKey(this.getVariant())).toString());
		compound.putInt("homeCheckCooldown", this.homeCheckCooldown);
	}

	@Override
	public void readAdditionalSaveData(@NotNull CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		if (compound.contains("fromBottle")) {
			this.setFromBottle(compound.getBoolean("fromBottle"));
		}
		if (compound.contains("natural")) {
			this.natural = compound.getBoolean("natural");
		}
		if (compound.contains("hasHome")) {
			this.hasHome = compound.getBoolean("hasHome");
		}
		ButterflyVariant variant = WilderWildRegistries.BUTTERFLY_VARIANT.get(ResourceLocation.tryParse(compound.getString("variant")));
		if (variant != null) {
			this.setVariant(variant);
		}
		if (compound.contains("homeCheckCooldown")) {
			this.homeCheckCooldown = compound.getInt("homeCheckCooldown");
		}
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
}
