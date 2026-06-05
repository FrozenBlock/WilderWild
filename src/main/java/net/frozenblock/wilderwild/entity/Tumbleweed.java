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

package net.frozenblock.wilderwild.entity;

import net.frozenblock.lib.entity.api.AbstractBlockLikeMob;
import net.frozenblock.lib.entity.impl.EntityStepOnBlockInterface;
import net.frozenblock.lib.tag.api.TagUtils;
import net.frozenblock.lib.wind.api.WindManager;
import net.frozenblock.wilderwild.block.MesogleaBlock;
import net.frozenblock.wilderwild.config.WWEntityConfig;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.registry.WWDamageTypes;
import net.frozenblock.wilderwild.registry.WWEntityTypes;
import net.frozenblock.wilderwild.registry.WWSounds;
import net.frozenblock.wilderwild.tag.WWBlockTags;
import net.frozenblock.wilderwild.tag.WWEntityTypeTags;
import net.frozenblock.wilderwild.tag.WWItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.npc.InventoryCarrier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class Tumbleweed extends AbstractBlockLikeMob implements EntityStepOnBlockInterface, InventoryCarrier {
	public static final int SPAWN_CHANCE = 60;
	private static final double WIND_MULTIPLIER = 1.4D;
	private static final double WIND_CLAMP = 0.2D;
	public static final double INACTIVE_PLAYER_DISTANCE_FROM = 24D;
	public static final int MAX_INACTIVE_TICKS = 200;
	public static final int TUMBLEWEED_PLANT_ITEM_CHANCE = 15;
	private static final EntityDataAccessor<ItemStack> ITEM_STACK = SynchedEntityData.defineId(Tumbleweed.class, EntityDataSerializers.ITEM_STACK);
	private final SimpleContainer inventory = new SimpleContainer(1);
	public boolean spawnedFromShears;
	public int ticksSinceActive;
	public boolean isItemNatural;
	public boolean isTouchingStickingBlock;
	public boolean isTouchingStoppingBlock;

	public Tumbleweed(EntityType<Tumbleweed> type, Level level) {
		super(type, level);
	}

	public static boolean checkTumbleweedSpawnRules(EntityType<Tumbleweed> type, ServerLevelAccessor level, EntitySpawnReason reason, BlockPos pos, RandomSource random) {
		if (!EntitySpawnReason.isSpawner(reason) && !WWEntityConfig.SPAWN_TUMBLEWEED.get()) return false;
		return level.getBrightness(LightLayer.SKY, pos) > 7 && random.nextInt(SPAWN_CHANCE) == 0 && pos.getY() > level.getSeaLevel();
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 1D);
	}

	public static boolean isSilkTouchOrShears(DamageSource source) {
		if (!(source.getDirectEntity() instanceof LivingEntity livingEntity)) return false;
		final ItemStack stack = livingEntity.getMainHandItem();
		final var silkTouch = livingEntity.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.SILK_TOUCH);
		return EnchantmentHelper.getItemEnchantmentLevel(silkTouch, stack) > 0 || stack.is(Items.SHEARS);
	}

	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, EntitySpawnReason spawnReason, @Nullable SpawnGroupData groupData) {
		if (this.inventory.isEmpty() && spawnReason == EntitySpawnReason.NATURAL) {
			final int difficultyId = difficulty.getDifficulty().getId();
			if (this.random.nextInt(0, difficultyId == 0 ? 32 : (27 / difficultyId)) == 0) {
				int tagSelector = this.random.nextInt(1, 6);
				final TagKey<Item> itemTag = tagSelector <= 1 ? WWItemTags.TUMBLEWEED_RARE : tagSelector <= 3 ? WWItemTags.TUMBLEWEED_MEDIUM : WWItemTags.TUMBLEWEED_COMMON;
				final ItemLike itemLike = TagUtils.getRandomEntry(this.random, itemTag);
				if (itemLike != null) this.setItem(new ItemStack(itemLike), true);
			} else if (this.random.nextInt(TUMBLEWEED_PLANT_ITEM_CHANCE) == 0) {
				this.setItem(new ItemStack(WWBlocks.TUMBLEWEED_PLANT), true);
			}
		}

		return super.finalizeSpawn(level, difficulty, spawnReason, groupData);
	}

	public static void spawnFromShears(Level level, BlockPos pos) {
		level.playSound(null, pos, WWSounds.BLOCK_TUMBLEWEED_SHEAR, SoundSource.BLOCKS, 1F, 1F);
		final Tumbleweed tumbleweed = new Tumbleweed(WWEntityTypes.TUMBLEWEED, level);
		level.addFreshEntity(tumbleweed);
		tumbleweed.setPos(Vec3.atBottomCenterOf(pos));
		tumbleweed.spawnedFromShears = true;
	}

	@Override
	protected void doPush(Entity entity) {
		if (entity.is(WWEntityTypeTags.TUMBLEWEED_PASSES_THROUGH)) return;

		if (entity instanceof Tumbleweed) super.doPush(entity);

		boolean isSmall = entity.getBoundingBox().getSize() < this.getBoundingBox().getSize() * 0.9D;
		if (this.level() instanceof ServerLevel serverLevel
			&& this.getDeltaPos().length() > (isSmall ? 0.2D : 0.3D)
			&& this.isMovingToward(entity)
			&& !(entity instanceof Tumbleweed)
		) {
			final boolean hurt = entity.hurtServer(
				serverLevel,
				this.damageSources().source(WWDamageTypes.TUMBLEWEED, this),
				2F
			);
			isSmall = isSmall || !entity.isAlive() || !hurt;
			if (!isSmall) this.destroy(false);
		}
	}

	@Override
	protected void dropAllDeathLoot(ServerLevel level, DamageSource source) {
		if (!isSilkTouchOrShears(source)) super.dropAllDeathLoot(level, source);
	}

	@Override
	protected void onInsideBlock(BlockState state) {
		if (state.is(BlockTags.LEAVES)) this.isTouchingStickingBlock = true;
	}

	@Override
	public void tick() {
		if (this.isTouchingStickingBlock) {
			this.setDeltaMovement(Vec3.ZERO);
			this.isTouchingStickingBlock = false;
		}
		this.isTouchingStoppingBlock = false;

		if (this.level() instanceof ServerLevel serverLevel
			&& this.getBlockStateOn().is(BlockTags.CROPS)
			&& serverLevel.getGameRules().get(GameRules.MOB_GRIEFING)
			&& !this.onGround()
		) {
			if (WWEntityConfig.TUMBLEWEED_DESTROYS_CROPS.get()) this.level().destroyBlock(this.blockPosition(), true, this);
		}

		super.tick();

		final Vec3 deltaPos = this.getDeltaPos();
		if (!this.isRemoved() && this.level() instanceof ServerLevel serverLevel) {
			this.heal(1F);
			double brightness = serverLevel.getBrightness(LightLayer.SKY, BlockPos.containing(this.getEyePosition()));
			this.checkActive(brightness);
			this.moveWithWind(serverLevel, brightness, deltaPos);
		}
	}

	private void checkActive(double brightness) {
		final Player entity = this.level().getNearestPlayer(this, -1D);
		if (!this.requiresCustomPersistence()
			&& ((brightness < 7 && (entity == null || entity.distanceTo(this) > INACTIVE_PLAYER_DISTANCE_FROM))
			|| this.isTouchingStoppingBlock || this.isTouchingStickingBlock ||
			(this.wasTouchingWater && !(this.getBlockStateOn().getBlock() instanceof MesogleaBlock)))
		) {
			++this.ticksSinceActive;
			if (this.ticksSinceActive >= MAX_INACTIVE_TICKS) this.destroy(false);
		} else {
			this.ticksSinceActive = 0;
		}
	}

	private void moveWithWind(ServerLevel level, double brightness, Vec3 deltaPos) {
		if (!(this.isTouchingStoppingBlock || this.isTouchingStickingBlock)) {
			final WindManager windManager = WindManager.getOrCreateWindManager(level);
			final Vec3 windVec = windManager.getWindMovement(this.position(), WIND_MULTIPLIER, WIND_CLAMP).scale(this.wasTouchingWater ? 0.16777216D : 1D);
			final double multiplier = (Math.max((brightness - (Math.max(15 - brightness, 0))), 0) * 0.0667D) * (this.wasTouchingWater ? 0.16777216D : 1D);

			Vec3 deltaMovement = this.getDeltaMovement();
			deltaMovement = deltaMovement.add((windVec.x * 0.2D), 0D, (windVec.z * 0.2D));
			deltaMovement = new Vec3(deltaMovement.x, deltaMovement.y < 0D ? deltaMovement.y * 0.88D : deltaMovement.y, deltaMovement.z);
			if (deltaPos.y <= 0D && this.onGround()) {
				deltaMovement = deltaMovement.add(0D, Math.min(0.65D, ((deltaPos.horizontalDistance() * 1.2D))) * multiplier, 0D);
			}
			if (deltaPos.x == 0D) {
				final double nonNegX = deltaMovement.x < 0D ? -deltaMovement.x : deltaMovement.x;
				deltaMovement = deltaMovement.add(0D, (nonNegX * 1.8D) * multiplier, 0D);
			}
			if (deltaPos.z == 0D) {
				final double nonNegZ = deltaMovement.z < 0D ? -deltaMovement.z : deltaMovement.z;
				deltaMovement = deltaMovement.add(0D, (nonNegZ * 1.8D) * multiplier, 0D);
			}
			if (this.wasEyeInWater) deltaMovement = deltaMovement.add(0D, 0.01D, 0D);
			this.setDeltaMovement(deltaMovement);
		}
	}

	@Override
	public boolean wantsToPickUp(ServerLevel level, ItemStack itemStack) {
		return !itemStack.isEmpty()
			&& level.getGameRules().get(GameRules.MOB_GRIEFING)
			&& this.inventory.canAddItem(itemStack)
			&& this.inventory.isEmpty();
	}

	@Override
	protected void pickUpItem(ServerLevel level, ItemEntity itemEntity) {
		final ItemStack itemStack = itemEntity.getItem();
		if (this.wantsToPickUp(level, itemStack)) {
			this.onItemPickup(itemEntity);
			final ItemStack splitStack = itemStack.split(1);
			this.getInventory().setItem(0, splitStack);
			this.take(itemEntity, 1);
			if (itemStack.isEmpty()) itemEntity.discard();
		}
	}

	@Override
	public void onItemPickup(ItemEntity entity) {
		super.onItemPickup(entity);
		this.setVisibleItem(entity.getItem().copyWithCount(1));
		this.isItemNatural = false;
	}

	@Override
	public boolean canPickUpLoot() {
		return this.inventory.isEmpty();
	}

	@Override
	protected Vec3i getPickupReach() {
		return Vec3i.ZERO;
	}

	public void dropItem(boolean killed) {
		if (this.isItemNatural && !killed) return;
		this.level().addFreshEntity(new ItemEntity(this.level(), this.getX(), this.getEyeY(), this.getZ(), this.inventory.getItem(0).copyAndClear()));
	}

	public void destroy(boolean killed) {
		if (this.isAlive()) this.playSound(this.getDeathSound(), this.getSoundVolume(), this.getVoicePitch());
		this.dropItem(killed);
		this.spawnBreakParticles(20);
		this.remove(RemovalReason.KILLED);
	}

	public void setItem(ItemStack stack, boolean natural) {
		this.inventory.setItem(0, stack);
		this.isItemNatural = natural;
	}

	public boolean isMovingToward(Entity entity) {
		return entity.getPosition(0).distanceTo(this.getPosition(0)) > entity.getPosition(1).distanceTo(this.getPosition(1));
	}

	public Vec3 getDeltaPos() {
		return this.getPosition(1).subtract(this.getPosition(0));
	}

	@Override
	public boolean isInvulnerableTo(ServerLevel level, DamageSource source) {
		return source.is(DamageTypeTags.WITCH_RESISTANT_TO)
			|| source.is(DamageTypes.CACTUS)
			|| source.is(DamageTypes.FREEZE)
			|| source.is(DamageTypes.SWEET_BERRY_BUSH)
			|| source.is(DamageTypes.WITHER)
			|| super.isInvulnerableTo(level, source);
	}

	@Override
	public boolean canFreeze() {
		return false;
	}

	@Override
	public boolean canBeLeashed() {
		return WWEntityConfig.LEASHED_TUMBLEWEED.get();
	}

	@Override
	public boolean canBeAffected(MobEffectInstance effect) {
		return false;
	}

	@Override
	public boolean canBeSeenAsEnemy() {
		return false;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return WWSounds.ENTITY_TUMBLEWEED_DAMAGE;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return WWSounds.ENTITY_TUMBLEWEED_BREAK;
	}

	@Override
	protected void playStepSound(BlockPos pos, BlockState state) {
		this.playSound(WWSounds.ENTITY_TUMBLEWEED_BOUNCE, 0.2F, 1F);
	}

	@Override
	public BlockState defaultBlockState() {
		return WWBlocks.TUMBLEWEED.defaultBlockState();
	}

	@Override
	public boolean causeFallDamage(double fallDistance, float damageModifier, DamageSource source) {
		return false;
	}

	@Override
	public void remove(RemovalReason reason) {
		if (reason == RemovalReason.DISCARDED) this.dropItem(false);
		super.remove(reason);
	}

	@Override
	public void addAdditionalSaveData(ValueOutput output) {
		super.addAdditionalSaveData(output);
		output.putBoolean("SpawnedFromShears", this.spawnedFromShears);
		output.putInt("TicksSinceActive", this.ticksSinceActive);
		output.putBoolean("IsTumbleweedItemNatural", this.isItemNatural);
		output.putBoolean("isTouchingStickingBlock", this.isTouchingStickingBlock);
		output.putBoolean("IsTouchingStoppingBlock", this.isTouchingStoppingBlock);
		this.writeInventoryToTag(output);
	}

	@Override
	public void readAdditionalSaveData(ValueInput input) {
		super.readAdditionalSaveData(input);
		this.spawnedFromShears = input.getBooleanOr("SpawnedFromShears", false);
		this.ticksSinceActive = input.getIntOr("TicksSinceActive", 0);
		this.isItemNatural = input.getBooleanOr("IsTumbleweedItemNatural", false);
		this.isTouchingStickingBlock = input.getBooleanOr("isTouchingStickingBlock", false);
		this.isTouchingStoppingBlock = input.getBooleanOr("IsTouchingStoppingBlock", false);
		if (input.contains("Items")) {
			final NonNullList<ItemStack> oldInventory = NonNullList.withSize(1, ItemStack.EMPTY);
			ContainerHelper.loadAllItems(input, oldInventory);
			if (!oldInventory.isEmpty() && !oldInventory.getFirst().isEmpty()) this.inventory.setItem(0, oldInventory.getFirst());
		} else {
			this.readInventoryFromTag(input);
		}
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder entityData) {
		super.defineSynchedData(entityData);
		entityData.define(ITEM_STACK, ItemStack.EMPTY);
	}

	@Nullable
	@Override
	public ItemStack getPickResult() {
		return new ItemStack(WWBlocks.TUMBLEWEED);
	}

	@Override
	public void die(DamageSource source) {
		super.die(source);
		if (this.level() instanceof ServerLevel level && level.getGameRules().get(GameRules.MOB_DROPS) && !source.isCreativePlayer()) {
			if (isSilkTouchOrShears(source)) level.addFreshEntity(new ItemEntity(level, this.getX(), this.getY(), this.getZ(), new ItemStack(WWBlocks.TUMBLEWEED)));
		}
		this.destroy(true);
	}

	@Override
	public boolean requiresCustomPersistence() {
		return super.requiresCustomPersistence() || this.spawnedFromShears || this.hasCustomName();
	}

	public ItemStack getVisibleItem() {
		return this.entityData.get(ITEM_STACK);
	}

	public void setVisibleItem(ItemStack stack) {
		this.getEntityData().set(ITEM_STACK, stack);
	}

	@Override
	protected void createWitherRose(@Nullable LivingEntity entity) {}

	@Override
	protected void playSwimSound(float volume) {}

	@Override
	public ItemStack getItemBySlot(EquipmentSlot slot) {
		return ItemStack.EMPTY;
	}

	@Override
	public void setItemSlot(EquipmentSlot slot, ItemStack stack) {}

	@Override
	public HumanoidArm getMainArm() {
		return HumanoidArm.LEFT;
	}

	@Override
	public void frozenLib$onSteppedOnBlock(Level level, BlockPos pos, BlockState state) {
		if (state.is(WWBlockTags.STOPS_TUMBLEWEED)) this.isTouchingStoppingBlock = true;
	}

	@Override
	public SimpleContainer getInventory() {
		return this.inventory;
	}
}
