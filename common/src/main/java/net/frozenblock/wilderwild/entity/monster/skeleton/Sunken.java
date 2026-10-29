/*
 * Copyright 2025-2026 FrozenBlock
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

package net.frozenblock.wilderwild.entity.monster.skeleton;

import net.frozenblock.lib.platform.api.attachment.DataAttachmentType;
import net.frozenblock.wilderwild.entity.variant.WWEntityVariantHolder;
import net.frozenblock.wilderwild.registry.WWAttachmentTypes;
import net.frozenblock.wilderwild.registry.WWDataComponents;
import net.frozenblock.wilderwild.registry.WilderWildRegistries;
import net.frozenblock.wilderwild.tag.WWBiomeTags;
import net.frozenblock.wilderwild.tag.WWItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.nautilus.ZombieNautilus;
import net.minecraft.world.entity.monster.CrossbowAttackMob;
import net.minecraft.world.entity.monster.skeleton.AbstractSkeleton;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.entity.projectile.arrow.Arrow;
import net.minecraft.world.entity.variant.SpawnContext;
import net.minecraft.world.entity.variant.VariantUtils;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jspecify.annotations.Nullable;

public class Sunken extends AbstractSkeleton implements CrossbowAttackMob, WWEntityVariantHolder<SunkenVariant> {
	private static final EntityDataAccessor<Boolean> IS_CHARGING_CROSSBOW = SynchedEntityData.defineId(Sunken.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> HAS_EYE_PATCH = SynchedEntityData.defineId(Sunken.class, EntityDataSerializers.BOOLEAN);
	public final RangedHarpoonCrossbowAttackGoal<Sunken> harpoonGoal = new RangedHarpoonCrossbowAttackGoal<>(this, 1D, 8F, 20F);

	public Sunken(EntityType<? extends Sunken> type, Level level) {
		super(type, level);
		this.wilderWild$setVariantToDefaultIfEmpty();
		this.setPathfindingMalus(PathType.WATER, 0F);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.removeAllGoals(goal -> goal instanceof WaterAvoidingRandomStrollGoal);
		this.goalSelector.addGoal(5, new RandomStrollGoal(this, 1D));
	}

	public static AttributeSupplier.Builder createAttributes() {
		return AbstractSkeleton.createAttributes()
			.add(Attributes.FOLLOW_RANGE, 24D)
			.add(Attributes.STEP_HEIGHT, 1D)
			.add(Attributes.MOVEMENT_SPEED, 0.27F)
			.add(Attributes.WATER_MOVEMENT_EFFICIENCY, 0.4D);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder entityData) {
		super.defineSynchedData(entityData);
		entityData.define(IS_CHARGING_CROSSBOW, false);
		entityData.define(HAS_EYE_PATCH, false);
	}

	@Override
	protected PathNavigation createNavigation(Level level) {
		final SunkenPathNavigation pathNavigation = new SunkenPathNavigation(this, level);
		pathNavigation.setCanFloat(false);
		return pathNavigation;
	}

	@Override
	protected void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance difficulty) {
		super.populateDefaultEquipmentSlots(random, difficulty);
		this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.CROSSBOW));
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, EntitySpawnReason spawnReason, @Nullable SpawnGroupData groupData) {
		groupData = super.finalizeSpawn(level, difficulty, spawnReason, groupData);

		VariantUtils.selectVariantToSpawn(SpawnContext.create(level, this.blockPosition()), WilderWildRegistries.SUNKEN_VARIANT).ifPresent(this::wilderWild$setVariant);

		if (level.getRandom().nextFloat() <= 0.25F) this.setHasEyePatch(true);

		if (this.getItemBySlot(EquipmentSlot.OFFHAND).isEmpty() && level.getRandom().nextFloat() < 0.01F) {
			this.setItemSlot(EquipmentSlot.OFFHAND, new ItemStack(Items.SPYGLASS));
			this.setGuaranteedDrop(EquipmentSlot.OFFHAND);
		}

		if ((spawnReason == EntitySpawnReason.NATURAL || spawnReason == EntitySpawnReason.STRUCTURE)
			&& this.getMainHandItem().is(Items.CROSSBOW)
			&& level.getRandom().nextFloat() < 0.5F
			&& !level.getBiome(this.blockPosition()).is(WWBiomeTags.MORE_FREQUENT_SUNKEN_SPAWNS)
		) {
			final ZombieNautilus zombieNautilus = EntityTypes.ZOMBIE_NAUTILUS.create(this.level(), EntitySpawnReason.JOCKEY);
			if (zombieNautilus != null) {
				if (spawnReason == EntitySpawnReason.STRUCTURE) zombieNautilus.setPersistenceRequired();

				zombieNautilus.snapTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0F);
				zombieNautilus.finalizeSpawn(level, difficulty, spawnReason, null);
				this.startRiding(zombieNautilus, false, false);
				level.addFreshEntity(zombieNautilus);
			}
		}

		return groupData;
	}

	public static boolean checkSunkenSpawnRules(EntityType<Sunken> type, ServerLevelAccessor level, EntitySpawnReason spawnReason, BlockPos pos, RandomSource random) {
		if (!level.getFluidState(pos.below()).is(Fluids.WATER) && !EntitySpawnReason.isSpawner(spawnReason)) return false;

		final Holder<Biome> biome = level.getBiome(pos);
		boolean canMonsterSpawn = level.getDifficulty() != Difficulty.PEACEFUL
			&& (EntitySpawnReason.ignoresLightRequirements(spawnReason) || isDarkEnoughToSpawn(level, pos, random))
			&& (EntitySpawnReason.isSpawner(spawnReason) || level.getFluidState(pos).is(Fluids.WATER));
		if (!canMonsterSpawn || !EntitySpawnReason.isSpawner(spawnReason)) {
			return biome.is(WWBiomeTags.MORE_FREQUENT_SUNKEN_SPAWNS)
				? random.nextInt(15) == 0 && canMonsterSpawn
				: random.nextInt(40) == 0 && isDeepEnoughToSpawn(level, pos) && canMonsterSpawn;
		}

		return true;
	}

	private static boolean isDeepEnoughToSpawn(LevelAccessor level, BlockPos pos) {
		return pos.getY() < level.getSeaLevel() - 5;
	}

	// TODO: custom sounds
	@Override
	protected SoundEvent getAmbientSound() {
		return SoundEvents.BOGGED_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return SoundEvents.BOGGED_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.BOGGED_DEATH;
	}

	@Override
	protected SoundEvent getStepSound() {
		return SoundEvents.BOGGED_STEP;
	}

	@Override
	protected AbstractArrow getArrow(ItemStack projectile, float power, @Nullable ItemStack firingWeapon) {
		final AbstractArrow abstractArrow = super.getArrow(projectile, power, firingWeapon);
		if (abstractArrow instanceof Arrow arrow) arrow.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 100));
		return abstractArrow;
	}

	@Override
	public boolean canUseNonMeleeWeapon(ItemStack item) {
		return super.canUseNonMeleeWeapon(item) || item.getItem() == Items.CROSSBOW;
	}

	@Override
	public TagKey<Item> getPreferredWeaponType() {
		return WWItemTags.SUNKEN_PREFERRED_WEAPONS;
	}

	public boolean isChargingCrossbow() {
		return this.entityData.get(IS_CHARGING_CROSSBOW);
	}

	@Override
	public void setChargingCrossbow(boolean isCharging) {
		this.entityData.set(IS_CHARGING_CROSSBOW, isCharging);
	}

	@Override
	public void onCrossbowAttackPerformed() {
		this.noActionTime = 0;
	}

	@Override
	public void performRangedAttack(LivingEntity target, float power) {
		this.performCrossbowAttack(this, 1.6F);
	}

	// TODO: see what timing fits harpoons best
	@Override
	protected int getHardAttackInterval() {
		return 50;
	}

	@Override
	protected int getAttackInterval() {
		return 70;
	}

	public void setHasEyePatch(boolean hasEyePatch) {
		this.entityData.set(HAS_EYE_PATCH, hasEyePatch);
	}

	public boolean hasEyePatch() {
		return this.entityData.get(HAS_EYE_PATCH);
	}

	public SunkenArmPos getArmPose() {
		if (this.isChargingCrossbow()) return SunkenArmPos.CROSSBOW_CHARGE;
		if (this.isHolding(Items.CROSSBOW)) return SunkenArmPos.CROSSBOW_HOLD;
		return this.isAggressive() ? SunkenArmPos.ATTACKING : SunkenArmPos.NEUTRAL;
	}

	@Override
	public boolean checkSpawnObstruction(LevelReader level) {
		return level.isUnobstructed(this);
	}

	@Nullable
	@Override
	public <T> T get(DataComponentType<? extends T> type) {
		if (type == WWDataComponents.SUNKEN_VARIANT.get()) return castComponentValue(type, this.wilderWild$getVariant());
		return super.get(type);
	}

	@Override
	protected void applyImplicitComponents(DataComponentGetter components) {
		this.applyImplicitComponentIfPresent(components, WWDataComponents.SUNKEN_VARIANT.get());
		super.applyImplicitComponents(components);
	}

	@Override
	protected <T> boolean applyImplicitComponent(DataComponentType<T> type, T value) {
		if (type == WWDataComponents.SUNKEN_VARIANT.get()) {
			this.wilderWild$setVariant(castComponentValue(WWDataComponents.SUNKEN_VARIANT.get(), value));
			return true;
		}
		return super.applyImplicitComponent(type, value);
	}

	@Override
	public ResourceKey<Registry<SunkenVariant>> wilderWild$variantRegistry() {
		return WilderWildRegistries.SUNKEN_VARIANT;
	}

	@Override
	public ResourceKey<SunkenVariant> wilderWild$defaultVariant() {
		return SunkenVariants.DEFAULT;
	}

	@Override
	public DataAttachmentType<Holder<SunkenVariant>> wilderWild$variantAttachmentType() {
		return WWAttachmentTypes.SUNKEN_VARIANT;
	}

	@Override
	public void addAdditionalSaveData(ValueOutput output) {
		super.addAdditionalSaveData(output);
		this.wilderWild$writeVariant(output);
		output.putBoolean("has_eye_patch", this.hasEyePatch());
	}

	@Override
	public void readAdditionalSaveData(ValueInput input) {
		super.readAdditionalSaveData(input);
		this.wilderWild$readVariant(input).ifPresent(this::wilderWild$setVariant);
		this.setHasEyePatch(input.getBooleanOr("has_eye_patch", false));
	}

	public enum SunkenArmPos {
		ATTACKING,
		BOW_AND_ARROW,
		CROSSBOW_HOLD,
		CROSSBOW_CHARGE,
		NEUTRAL;
	}
}
