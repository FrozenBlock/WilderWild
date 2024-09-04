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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import net.frozenblock.lib.math.api.AdvancedMath;
import net.frozenblock.lib.sound.api.FrozenSoundPackets;
import net.frozenblock.lib.wind.api.WindManager;
import net.frozenblock.wilderwild.config.EntityConfig;
import net.frozenblock.wilderwild.entity.ai.firefly.FireflyAi;
import net.frozenblock.wilderwild.entity.variant.FireflyColor;
import net.frozenblock.wilderwild.mod_compat.FrozenLibIntegration;
import net.frozenblock.wilderwild.registry.WWCriteria;
import net.frozenblock.wilderwild.registry.WWItems;
import net.frozenblock.wilderwild.registry.WWSounds;
import net.frozenblock.wilderwild.registry.WilderWildRegistries;
import net.frozenblock.wilderwild.tag.WilderBiomeTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
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
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
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

public class Firefly extends PathfinderMob implements FlyingAnimal {
	public static final int FLICKERS_CHANCE = 4;
	public static final int RANDOM_FLICKER_AGE_MAX = 19;
	public static final int SPAWN_CHANCE = 75;
	protected static final List<SensorType<? extends Sensor<? super Firefly>>> SENSORS = List.of(SensorType.NEAREST_LIVING_ENTITIES);
	protected static final List<MemoryModuleType<?>> MEMORY_MODULES = List.of(MemoryModuleType.PATH, MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES, MemoryModuleType.WALK_TARGET, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryModuleType.LOOK_TARGET, MemoryModuleType.HOME);
	private static final EntityDataAccessor<Boolean> FROM_BOTTLE = SynchedEntityData.defineId(Firefly.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> FLICKERS = SynchedEntityData.defineId(Firefly.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Integer> AGE = SynchedEntityData.defineId(Firefly.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Float> ANIM_SCALE = SynchedEntityData.defineId(Firefly.class, EntityDataSerializers.FLOAT);
	private static final EntityDataAccessor<Float> PREV_ANIM_SCALE = SynchedEntityData.defineId(Firefly.class, EntityDataSerializers.FLOAT);
	private static final EntityDataAccessor<String> COLOR = SynchedEntityData.defineId(Firefly.class, EntityDataSerializers.STRING);

	public boolean natural;
	public boolean hasHome;
	public boolean despawning;
	public int homeCheckCooldown;
	public boolean wasNamedNectar;
	public boolean shouldCheckSpawn = true;

	public Firefly(@NotNull EntityType<? extends Firefly> entityType, @NotNull Level level) {
		super(entityType, level);
		this.setPathfindingMalus(PathType.LAVA, -1F);
		this.setPathfindingMalus(PathType.DANGER_FIRE, -1F);
		this.setPathfindingMalus(PathType.WATER, -1F);
		this.setPathfindingMalus(PathType.WATER_BORDER, 16F);
		this.setPathfindingMalus(PathType.UNPASSABLE_RAIL, 0F);
		this.moveControl = new FlyingMoveControl(this, 20, true);
		this.setFlickers(this.random.nextInt(FLICKERS_CHANCE) == 0);
		this.setFlickerAge(this.random.nextIntBetweenInclusive(0, RANDOM_FLICKER_AGE_MAX));
		this.setAnimScale(1.5F);
		this.setColor(FireflyColor.ON);
	}

	public static boolean checkFireflySpawnRules(@NotNull EntityType<Firefly> type, @NotNull LevelAccessor level, MobSpawnType spawnType, @NotNull BlockPos pos, @NotNull RandomSource random) {
		if (!MobSpawnType.isSpawner(spawnType) && !EntityConfig.get().firefly.spawnFireflies) return false;
		if (MobSpawnType.ignoresLightRequirements(spawnType)) return true;
		boolean chance = random.nextInt(0, SPAWN_CHANCE) == 0;
		Holder<Biome> biomeHolder = level.getBiome(pos);
		if (biomeHolder.is(WilderBiomeTags.FIREFLY_SPAWNABLE_CAVE)) {
			return chance && level.getBrightness(LightLayer.SKY, pos) == 0;
		}
		return chance && ((biomeHolder.is(WilderBiomeTags.FIREFLY_SPAWNABLE_DURING_DAY) && level.getBrightness(LightLayer.SKY, pos) >= 6) || ((!level.dimensionType().hasFixedTime() && level.getSkyDarken() > 4) && level.canSeeSky(pos)));
	}

	@NotNull
	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes()
			.add(Attributes.MAX_HEALTH, 1D)
			.add(Attributes.MOVEMENT_SPEED, 0.08D)
			.add(Attributes.FLYING_SPEED, 0.08D)
			.add(Attributes.FOLLOW_RANGE, 32D);
	}

	public static boolean isValidHomePos(@NotNull Level level, @NotNull BlockPos pos) {
		BlockState state = level.getBlockState(pos);
		if (!state.getFluidState().isEmpty()) {
			return false;
		}
		if (state.isRedstoneConductor(level, pos)) {
			return false;
		}
		return state.isAir() || (!state.blocksMotion() && !state.isSolid());
	}

	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor level, @NotNull DifficultyInstance difficulty, @NotNull MobSpawnType reason, @Nullable SpawnGroupData spawnData) {
		this.natural = reason == MobSpawnType.NATURAL || reason == MobSpawnType.CHUNK_GENERATION || reason == MobSpawnType.SPAWNER || reason == MobSpawnType.SPAWN_EGG || reason == MobSpawnType.COMMAND;
		this.hasHome = this.hasHome || !this.natural;
		FireflyAi.rememberHome(this, this.blockPosition());

		if (reason == MobSpawnType.COMMAND) {
			this.setAnimScale(1.5F);
			this.setPrevAnimScale(1.5F);
			this.setColor(FireflyColor.ON);
		}

		return super.finalizeSpawn(level, difficulty, reason, spawnData);
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
		builder.define(FLICKERS, false);
		builder.define(AGE, 0);
		builder.define(ANIM_SCALE, 1.5F);
		builder.define(PREV_ANIM_SCALE, 1.5F);
		builder.define(COLOR, FireflyColor.ON.key().toString());
	}

	@Override
	public boolean dampensVibrations() {
		return true;
	}

	@Override
	@NotNull
	protected InteractionResult mobInteract(@NotNull Player player, @NotNull InteractionHand hand) {
		return !this.despawning ? tryCapture(player, hand).orElse(super.mobInteract(player, hand)) : InteractionResult.PASS;
	}

	@NotNull
	public Optional<InteractionResult> tryCapture(@NotNull Player player, @NotNull InteractionHand hand) {
		ItemStack itemStack = player.getItemInHand(hand);
		if (itemStack.getItem() == Items.GLASS_BOTTLE && this.isAlive()) {
			FireflyColor color = this.getColor();
			Optional<Item> optionalItem = BuiltInRegistries.ITEM.getOptional(ResourceLocation.fromNamespaceAndPath(color.key().getNamespace(), Objects.equals(color, FireflyColor.ON) ? "firefly_bottle" : color.key().getPath() + "_firefly_bottle"));
			Item item = WWItems.FIREFLY_BOTTLE;
			if (optionalItem.isPresent()) {
				item = optionalItem.get();
			}
			this.playSound(WWSounds.ITEM_BOTTLE_CATCH_FIREFLY, 1F, this.random.nextFloat() * 0.2F + 0.8F);
			if (!player.getAbilities().instabuild) {
				player.getItemInHand(hand).shrink(1);
			}
			ItemStack bottleStack = new ItemStack(item);
			if (this.hasCustomName()) {
				bottleStack.set(DataComponents.CUSTOM_NAME, this.getCustomName());
			}
			player.getInventory().placeItemBackInInventory(bottleStack);
			Level level = this.level();
			this.discard();
			if (!level.isClientSide) {
				WWCriteria.FIREFLY_BOTTLE.trigger((ServerPlayer) player, bottleStack);
			}
			return Optional.of(InteractionResult.sidedSuccess(level.isClientSide));
		} else {
			return Optional.empty();
		}
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
		return Brain.provider(MEMORY_MODULES, SENSORS);
	}

	@Override
	@NotNull
	protected Brain<?> makeBrain(@NotNull Dynamic<?> dynamic) {
		return FireflyAi.makeBrain(this, this.brainProvider().makeBrain(dynamic));
	}

	public boolean isFromBottle() {
		return this.entityData.get(FROM_BOTTLE);
	}

	public void setFromBottle(boolean value) {
		this.entityData.set(FROM_BOTTLE, value);
	}

	public boolean flickers() {
		return this.entityData.get(FLICKERS);
	}

	public void setFlickers(boolean value) {
		this.entityData.set(FLICKERS, value);
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

	public FireflyColor getColor() {
		return WilderWildRegistries.FIREFLY_COLOR.getOptional(ResourceLocation.parse(this.entityData.get(COLOR))).orElse(FireflyColor.ON);
	}

	public void setColor(@NotNull FireflyColor color) {
		this.entityData.set(COLOR, color.key().toString());
	}

	@Override
	public boolean requiresCustomPersistence() {
		return super.requiresCustomPersistence() || this.isFromBottle();
	}

	@Override
	public float getWalkTargetValue(@NotNull BlockPos pos, @NotNull LevelReader level) {
		return 0F;
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
		return this.natural
			&& !this.level().getBiome(this.blockPosition()).is(WilderBiomeTags.FIREFLY_SPAWNABLE_DURING_DAY)
			&& this.level().isDay()
			&& this.level().getBrightness(LightLayer.SKY, this.blockPosition()) >= 6;
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

		if (this.shouldCheckSpawn) {
			if (!this.isFromBottle()) {
				FireflyColor biomeColor = FireflyBiomeColorRegistry.getBiomeColor(this.level().getBiome(this.blockPosition()));
				if (biomeColor != null) {
					this.setColor(biomeColor);
				}
			}
			this.shouldCheckSpawn = false;
		}

		boolean nectar = this.hasCustomName() && Objects.requireNonNull(this.getCustomName()).getString().toLowerCase().contains("nectar");
		if (this.level() instanceof ServerLevel server) {
			if (nectar != wasNamedNectar) {
				if (nectar) {
					FrozenSoundPackets.createMovingRestrictionLoopingSound(
						server,
						this,
						BuiltInRegistries.SOUND_EVENT.getHolder(WWSounds.ENTITY_FIREFLY_NECTAR.getLocation()).orElseThrow(),
						SoundSource.NEUTRAL,
						1F,
						1F,
						FrozenLibIntegration.NECTAR_SOUND_PREDICATE,
						true
					);
					this.wasNamedNectar = true;
				} else {
					this.wasNamedNectar = false;
				}
			}
		}

		if (!this.isAlive()) {
			this.setNoGravity(false);
		}
		this.setFlickerAge(this.getFlickerAge() + 1);

		if (this.hasHome) {
			if (this.homeCheckCooldown > 0) {
				--this.homeCheckCooldown;
			} else {
				this.homeCheckCooldown = 200;
				BlockPos home = FireflyAi.getHome(this);
				if (home != null && FireflyAi.isInHomeDimension(this)) {
					if (!isValidHomePos(this.level(), home)) {
						FireflyAi.rememberHome(this, this.blockPosition());
					}
				}
			}
		}

		this.setPrevAnimScale(this.getAnimScale());

		if (this.despawning) {
			this.setAnimScale(this.getAnimScale() - 0.5F);
			if (this.getAnimScale() < 0F) {
				this.discard();
			}
		} else if (this.getAnimScale() < 1.5F) {
			this.setAnimScale(Math.min(this.getAnimScale() + 0.025F, 1.5F));
		}

		if (this.level() instanceof ServerLevel serverLevel) {
			Vec3 wind = WindManager.getWindManager(serverLevel).getWindMovement(this.position(), 1D, 100D, 100D).scale(0.01D);
			wind = wind.subtract(0D, wind.y * 0.7D, 0D);
			this.setDeltaMovement(this.getDeltaMovement().add(wind.scale(0.02D)));
		}
	}

	@Override
	protected void customServerAiStep() {
		this.level().getProfiler().push("fireflyBrain");
		this.getBrain().tick((ServerLevel) this.level(), this);
		this.level().getProfiler().pop();
		this.level().getProfiler().push("fireflyActivityUpdate");
		FireflyAi.updateActivities(this);
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
		return !this.isFromBottle() && !this.hasCustomName();
	}

	@Override
	public void checkDespawn() {
		if (!this.despawning) {
			if (this.level().getDifficulty() == Difficulty.PEACEFUL && this.shouldDespawnInPeaceful()) {
				this.despawning = true;
				return;
			}
			if (this.isPersistenceRequired() || this.requiresCustomPersistence()) {
				this.noActionTime = 0;
				return;
			}
			Player entity = this.level().getNearestPlayer(this, -1D);
			if (entity != null) {
				int i;
				double d = entity.distanceToSqr(this);
				boolean dayKey = !this.level().getBiome(this.blockPosition()).is(WilderBiomeTags.FIREFLY_SPAWNABLE_DURING_DAY) && this.level().isDay() && !this.level().getBiome(this.blockPosition()).is(WilderBiomeTags.FIREFLY_SPAWNABLE_CAVE);
				boolean caveKey = this.level().getBiome(this.blockPosition()).is(WilderBiomeTags.FIREFLY_SPAWNABLE_CAVE) && this.level().getBrightness(LightLayer.SKY, this.blockPosition()) >= 6;
				if (this.removeWhenFarAway(d) && Math.sqrt(d) > 18D) {
					if (dayKey) {
						this.despawning = true;
					} else if (caveKey) {
						this.despawning = true;
					}
				}
				if (d > (double) ((i = this.getType().getCategory().getDespawnDistance()) * i) && this.removeWhenFarAway(d)) {
					this.despawning = true;
				}
				int k = this.getType().getCategory().getNoDespawnDistance();
				int l = k * k;
				if (this.noActionTime > 600 && this.random.nextInt(800) == 0 && d > (double) l && this.removeWhenFarAway(d)) {
					this.despawning = true;
				} else if (d < (double) l) {
					this.noActionTime = 0;
				}
			}
		}
	}

	@Override
	public void addAdditionalSaveData(@NotNull CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putBoolean("fromBottle", this.isFromBottle());
		compound.putBoolean("natural", this.natural);
		compound.putBoolean("flickers", this.flickers());
		compound.putInt("flickerAge", this.getFlickerAge());
		compound.putBoolean("hasHome", this.hasHome);
		compound.putFloat("scale", this.getAnimScale());
		compound.putFloat("prevScale", this.getPrevAnimScale());
		compound.putBoolean("despawning", this.despawning);
		compound.putString("color", Objects.requireNonNull(WilderWildRegistries.FIREFLY_COLOR.getKey(this.getColor())).toString());
		compound.putInt("homeCheckCooldown", this.homeCheckCooldown);
		compound.putBoolean("wasNamedNectar", this.wasNamedNectar);
		compound.putBoolean("shouldCheckSpawn", this.shouldCheckSpawn);
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
		if (compound.contains("flickers")) {
			this.setFlickers(compound.getBoolean("flickers"));
		}
		if (compound.contains("flickerAge")) {
			this.setFlickerAge(compound.getInt("flickerAge"));
		}
		if (compound.contains("hasHome")) {
			this.hasHome = compound.getBoolean("hasHome");
		}
		if (compound.contains("scale")) {
			this.setAnimScale(compound.getFloat("scale"));
		}
		if (compound.contains("prevScale")) {
			this.setPrevAnimScale(compound.getFloat("prevScale"));
		}
		if (compound.contains("despawning")) {
			this.despawning = compound.getBoolean("despawning");
		}
		FireflyColor color = WilderWildRegistries.FIREFLY_COLOR.get(ResourceLocation.tryParse(compound.getString("color")));
		if (color != null) {
			this.setColor(color);
		}
		if (compound.contains("homeCheckCooldown")) {
			this.homeCheckCooldown = compound.getInt("homeCheckCooldown");
		}
		if (compound.contains("wasNamedNectar")) {
			this.wasNamedNectar = compound.getBoolean("wasNamedNectar");
		}
		if (compound.contains("shouldCheckSpawn")) {
			this.shouldCheckSpawn = compound.getBoolean("shouldCheckSpawn");
		}
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

	public static class FireflyBiomeColorRegistry {
		public static final ArrayList<ResourceLocation> BIOMES = new ArrayList<>();
		public static final ArrayList<FireflyColor> COLORS = new ArrayList<>();

		public static void addBiomeColor(@NotNull ResourceLocation biome, @NotNull FireflyColor color) {
			BIOMES.add(biome);
			COLORS.add(color);
		}

		public static void addBiomeColor(@NotNull ResourceKey<Biome> biome, @NotNull FireflyColor color) {
			addBiomeColor(biome.location(), color);
		}

		@Nullable
		public static FireflyColor getBiomeColor(@NotNull Holder<Biome> biomeEntry) {
			ArrayList<FireflyColor> colors = new ArrayList<>();
			for (int i = 0; i < BIOMES.size(); ++i) {
				ResourceLocation biomeID = BIOMES.get(i);
				if (biomeEntry.is(biomeID)) {
					colors.add(COLORS.get(i));
				}
			}
			if (colors.isEmpty()) {
				return null;
			}
			return colors.get(AdvancedMath.random().nextInt(colors.size()));
		}
	}
}
