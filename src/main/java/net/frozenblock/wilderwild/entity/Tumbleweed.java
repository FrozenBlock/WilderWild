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

import java.util.List;
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
import net.frozenblock.wilderwild.tag.WWEntityTags;
import net.frozenblock.wilderwild.tag.WWItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Tumbleweed extends Mob implements EntityStepOnBlockInterface {
	public static final int SPAWN_CHANCE = 60;
	private static final double WIND_MULTIPLIER = 1.4D;
	private static final double WIND_CLAMP = 0.2D;
	private static final float ROTATION_AMOUNT = 55F;
	private static final float MAX_ITEM_OFFSET = 0.25F;
	public static final double INACTIVE_PLAYER_DISTANCE_FROM = 24D;
	public static final int MAX_INACTIVE_TICKS = 200;
	public static final int TUMBLEWEED_PLANT_ITEM_CHANCE = 15;
	private static final EntityDataAccessor<ItemStack> ITEM_STACK = SynchedEntityData.defineId(Tumbleweed.class, EntityDataSerializers.ITEM_STACK);
	private static final EntityDataAccessor<Float> ITEM_X = SynchedEntityData.defineId(Tumbleweed.class, EntityDataSerializers.FLOAT);
	private static final EntityDataAccessor<Float> ITEM_Z = SynchedEntityData.defineId(Tumbleweed.class, EntityDataSerializers.FLOAT);
	public NonNullList<ItemStack> inventory;
	public boolean spawnedFromShears;
	public int ticksSinceActive;
	public boolean isItemNatural;
	public boolean isTouchingStickingBlock;
	public boolean isTouchingStoppingBlock;
	public float prevPitch;
	public float prevRoll;
	public float pitch;
	public float roll;
	public float prevTumble;
	public float tumble;
	public float itemX;
	public float itemZ;
	private float lookRot;

	public Tumbleweed(@NotNull EntityType<Tumbleweed> entityType, @NotNull Level level) {
		super(entityType, level);
		this.blocksBuilding = true;
		this.inventory = NonNullList.withSize(1, ItemStack.EMPTY);
	}

	public static boolean checkTumbleweedSpawnRules(EntityType<Tumbleweed> type, @NotNull ServerLevelAccessor level, MobSpawnType spawnType, @NotNull BlockPos pos, @NotNull RandomSource random) {
		if (!MobSpawnType.isSpawner(spawnType) && !WWEntityConfig.get().tumbleweed.spawnTumbleweed) return false;
		return level.getBrightness(LightLayer.SKY, pos) > 7 && random.nextInt(SPAWN_CHANCE) == 0 && pos.getY() > level.getSeaLevel();
	}

	@NotNull
	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 1D);
	}

	public static boolean isSilkTouchOrShears(@NotNull DamageSource damageSource) {
		if (damageSource.getDirectEntity() instanceof LivingEntity livingEntity) {
			ItemStack stack = livingEntity.getMainHandItem();
			var silkTouch = livingEntity.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.SILK_TOUCH);
			return EnchantmentHelper.getItemEnchantmentLevel(silkTouch, stack) > 0 || stack.is(Items.SHEARS);
		}
		return false;
	}

	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor level, @NotNull DifficultyInstance difficulty, @NotNull MobSpawnType reason, @Nullable SpawnGroupData spawnData) {
		if (this.inventory.getFirst().isEmpty() && reason == MobSpawnType.NATURAL) {
			int diff = difficulty.getDifficulty().getId();
			if (this.random.nextInt(0, diff == 0 ? 32 : (27 / diff)) == 0) {
				int tagSelector = this.random.nextInt(1, 6);
				TagKey<Item> itemTag = tagSelector <= 1 ? WWItemTags.TUMBLEWEED_RARE : tagSelector <= 3 ? WWItemTags.TUMBLEWEED_MEDIUM : WWItemTags.TUMBLEWEED_COMMON;
				ItemLike itemLike = TagUtils.getRandomEntry(this.random, itemTag);
				if (itemLike != null) this.setItem(new ItemStack(itemLike), true);
			} else if (this.random.nextInt(TUMBLEWEED_PLANT_ITEM_CHANCE) == 0) {
				this.setItem(new ItemStack(WWBlocks.TUMBLEWEED_PLANT), true);
			}
		}
		return super.finalizeSpawn(level, difficulty, reason, spawnData);
	}

	public static void spawnFromShears(@NotNull Level level, BlockPos pos) {
		level.playSound(null, pos, WWSounds.BLOCK_TUMBLEWEED_SHEAR, SoundSource.BLOCKS, 1F, 1F);
		Tumbleweed weed = new Tumbleweed(WWEntityTypes.TUMBLEWEED, level);
		level.addFreshEntity(weed);
		weed.setPos(Vec3.atBottomCenterOf(pos));
		weed.spawnedFromShears = true;
	}

	@Override
	protected void doPush(@NotNull Entity entity) {
		if (entity.getType().is(WWEntityTags.TUMBLEWEED_PASSES_THROUGH)) return;
		boolean isSmall = entity.getBoundingBox().getSize() < this.getBoundingBox().getSize() * 0.9D;
		if (entity instanceof Tumbleweed) super.doPush(entity);
		if (this.getDeltaPos().length() > (isSmall ? 0.2D : 0.3D) && this.isMovingTowards(entity) && !(entity instanceof Tumbleweed)) {
			boolean hurt = entity.hurt(this.damageSources().source(WWDamageTypes.TUMBLEWEED, this), 2F);
			isSmall = isSmall || !entity.isAlive() || !hurt;
			if (!isSmall) this.destroy(false);
		}
	}

	@Override
	protected void dropAllDeathLoot(ServerLevel level, DamageSource source) {
		if (!isSilkTouchOrShears(source)) super.dropAllDeathLoot(level, source);
	}

	@Override
	protected void onInsideBlock(@NotNull BlockState state) {
		if (state.getBlock() instanceof LeavesBlock) this.isTouchingStickingBlock = true;
	}

	@Override
	public void tick() {
		if (this.isTouchingStickingBlock) {
			this.setDeltaMovement(Vec3.ZERO);
			this.isTouchingStickingBlock = false;
		}
		this.isTouchingStoppingBlock = false;
		if (!this.level().isClientSide && this.getBlockStateOn().is(BlockTags.CROPS) && this.level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING) && !this.onGround()) {
			if (WWEntityConfig.get().tumbleweed.tumbleweedDestroysCrops) this.level().destroyBlock(this.blockPosition(), true, this);
		}
		super.tick();
		Vec3 deltaPos = this.getDeltaPos();
		this.setAngles(deltaPos);
		if (this.level().isClientSide) {
			this.itemX = this.getItemX();
			this.itemZ = this.getItemZ();
		} else if (!this.isRemoved() && this.level() instanceof ServerLevel serverLevel) {
			this.heal(1F);
			double brightness = this.level().getBrightness(LightLayer.SKY, BlockPos.containing(this.getEyePosition()));
			this.checkActive(brightness);
			this.moveWithWind(serverLevel, brightness, deltaPos);
			this.tickAfterWindLeash();
			this.pickupItem();
		}
	}

	public void setAngles(@NotNull Vec3 deltaPos) {
		if (deltaPos.horizontalDistance() > 0.01D) this.lookRot = -((float) Mth.atan2(deltaPos.x, deltaPos.z)) * Mth.RAD_TO_DEG;
		float yRot = this.getYRot();
		this.setYRot(yRot += ((this.lookRot - yRot) * 0.25F));
		this.yBodyRot = yRot;
		this.prevPitch = this.pitch;
		this.prevRoll = this.roll;
		this.prevTumble = this.tumble;
		float yRotAmount = (float) ((Math.abs(deltaPos.y) * 0.5F) * ROTATION_AMOUNT);
		this.pitch -= (float) (deltaPos.z * ROTATION_AMOUNT);
		this.roll -= (float) (deltaPos.x * ROTATION_AMOUNT);
		this.pitch += yRotAmount;
		this.roll += yRotAmount;
		this.tumble += (float) (((Math.abs(deltaPos.z) + Math.abs(deltaPos.x) + (Math.abs(deltaPos.x) * 0.5F)) * 0.5F) * ROTATION_AMOUNT);
		if (this.pitch > 360F) {
			this.pitch -= 360F;
			this.prevPitch -= 360F;
		} else if (this.pitch < 0F) {
			this.pitch += 360F;
			this.prevPitch += 360F;
		}
		if (this.roll > 360F) {
			this.roll -= 360F;
			this.prevRoll -= 360F;
		} else if (this.roll < 0F) {
			this.roll += 360F;
			this.prevRoll += 360F;
		}
		if (this.tumble > 360F) {
			this.tumble -= 360F;
			this.prevTumble -= 360F;
		} else if (this.tumble < 0F) {
			this.tumble += 360F;
			this.prevTumble += 360F;
		}
	}

	private void checkActive(double brightness) {
		Player entity = this.level().getNearestPlayer(this, -1D);
		if (!this.requiresCustomPersistence() && ((brightness < 7 && (entity == null || entity.distanceTo(this) > INACTIVE_PLAYER_DISTANCE_FROM))
			|| this.isTouchingStoppingBlock || this.isTouchingStickingBlock ||
			(this.wasTouchingWater && !(this.getBlockStateOn().getBlock() instanceof MesogleaBlock)))) {
			++this.ticksSinceActive;
			if (this.ticksSinceActive >= MAX_INACTIVE_TICKS) this.destroy(false);
		} else {
			this.ticksSinceActive = 0;
		}
	}

	private void moveWithWind(@NotNull ServerLevel serverLevel, double brightness, @NotNull Vec3 deltaPos) {
		if (!(this.isTouchingStoppingBlock || this.isTouchingStickingBlock)) {
			Vec3 deltaMovement = this.getDeltaMovement();
			WindManager windManager = WindManager.getWindManager(serverLevel);
			Vec3 windVec = windManager.getWindMovement(this.position(), WIND_MULTIPLIER, WIND_CLAMP).scale(this.wasTouchingWater ? 0.16777216D : 1D);
			double multiplier = (Math.max((brightness - (Math.max(15 - brightness, 0))), 0) * 0.0667D) * (this.wasTouchingWater ? 0.16777216D : 1D);
			deltaMovement = deltaMovement.add((windVec.x * 0.2D), 0D, (windVec.z * 0.2D));
			deltaMovement = new Vec3(deltaMovement.x, deltaMovement.y < 0 ? deltaMovement.y * 0.88D : deltaMovement.y, deltaMovement.z);
			if (deltaPos.y <= 0D && this.onGround()) {
				deltaMovement = deltaMovement.add(0D, Math.min(0.65D, ((deltaPos.horizontalDistance() * 1.2D))) * multiplier, 0D);
			}
			if (deltaPos.x == 0D) {
				double nonNegX = deltaMovement.x < 0D ? -deltaMovement.x : deltaMovement.x;
				deltaMovement = deltaMovement.add(0D, (nonNegX * 1.8D) * multiplier, 0D);
			}
			if (deltaPos.z == 0D) {
				double nonNegZ = deltaMovement.z < 0D ? -deltaMovement.z : deltaMovement.z;
				deltaMovement = deltaMovement.add(0D, (nonNegZ * 1.8D) * multiplier, 0D);
			}
			if (this.wasEyeInWater) deltaMovement = deltaMovement.add(0D, 0.01D, 0D);
			this.setDeltaMovement(deltaMovement);
		}
	}

	protected void tickAfterWindLeash() {
		Entity entity = this.getLeashHolder();
		if (entity != null && entity.level() == this.level()) {
			this.restrictTo(entity.blockPosition(), 5);
			float f = this.distanceTo(entity);
			if (f > 10F) {
				this.dropLeash(true, true);
			} else if (f > 6F) {
				double d = (entity.getX() - this.getX()) / (double) f;
				double e = (entity.getY() - this.getY()) / (double) f;
				double g = (entity.getZ() - this.getZ()) / (double) f;
				this.setDeltaMovement(this.getDeltaMovement().add(Math.copySign(d * d * 0.4D, d), Math.copySign(e * e * 0.4D, e), Math.copySign(g * g * 0.4D, g)));
			}
		}
	}

	public void pickupItem() {
		ItemStack inventoryStack = this.inventory.getFirst();
		if (inventoryStack.getCount() > 1) {
			this.level().addFreshEntity(new ItemEntity(this.level(), this.getX(), this.getY(), this.getZ(), inventoryStack.split(inventoryStack.getCount() - 1)));
		}
		if (!this.level().isClientSide && inventoryStack.isEmpty() && this.level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING) && !this.isRemoved()) {
			List<ItemEntity> list = this.level().getEntitiesOfClass(ItemEntity.class, this.getBoundingBox().inflate(0.15D));
			for (ItemEntity item : list) {
				if (this.isMovingTowards(item)) {
					ItemStack stack = item.getItem();
					if (!stack.isEmpty()) {
						this.inventory.set(0, stack.split(1));
						this.isItemNatural = false;
						this.randomizeItemOffsets();
						break;
					}
				}
			}
		}
		this.setVisibleItem(this.inventory.getFirst());
	}

	public void dropItem(boolean killed) {
		if (!this.isItemNatural || killed) {
			this.level().addFreshEntity(new ItemEntity(this.level(), this.getX(), this.getEyeY(), this.getZ(), this.inventory.getFirst().split(1)));
		}
	}

	public void destroy(boolean killed) {
		if (this.isAlive()) {
			this.playSound(WWSounds.ENTITY_TUMBLEWEED_BREAK, this.getSoundVolume(), this.getVoicePitch());
		}
		this.dropItem(killed);
		this.spawnBreakParticles();
		this.remove(RemovalReason.KILLED);
	}

	public void setItem(@NotNull ItemStack stack, boolean natural) {
		this.inventory.set(0, stack);
		this.isItemNatural = natural;
		this.randomizeItemOffsets();
	}

	public boolean isMovingTowards(@NotNull Entity entity) {
		return entity.getPosition(0).distanceTo(this.getPosition(0)) > entity.getPosition(1).distanceTo(this.getPosition(1));
	}

	@NotNull
	public Vec3 getDeltaPos() {
		return this.getPosition(1).subtract(this.getPosition(0));
	}

	@Override
	public boolean isInvulnerableTo(@NotNull DamageSource source) {
		return source.is(DamageTypeTags.WITCH_RESISTANT_TO) || source.is(DamageTypes.CACTUS) || source.is(DamageTypes.FREEZE) || source.is(DamageTypes.SWEET_BERRY_BUSH) || source.is(DamageTypes.WITHER) || super.isInvulnerableTo(source);
	}

	@Override
	public boolean canFreeze() {
		return false;
	}

	@Override
	public boolean canBeLeashed() {
		return WWEntityConfig.get().tumbleweed.leashedTumbleweed;
	}

	@Override
	public boolean canBeAffected(@NotNull MobEffectInstance effectInstance) {
		return false;
	}

	@Override
	public boolean canBeSeenAsEnemy() {
		return false;
	}

	@Override
	protected SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
		return WWSounds.ENTITY_TUMBLEWEED_DAMAGE;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return WWSounds.ENTITY_TUMBLEWEED_BREAK;
	}

	@Override
	protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState state) {
		this.playSound(WWSounds.ENTITY_TUMBLEWEED_BOUNCE, 0.2F, 1F);
	}

	@Override
	public boolean causeFallDamage(float fallDistance, float multiplier, @NotNull DamageSource source) {
		return false;
	}

	@Override
	public void remove(RemovalReason removalReason) {
		if (removalReason == RemovalReason.DISCARDED) this.dropItem(false);
		super.remove(removalReason);
	}

	@Override
	@NotNull
	public Packet<ClientGamePacketListener> getAddEntityPacket(ServerEntity serverEntity) {
		return new ClientboundAddEntityPacket(
			this.getId(),
			this.getUUID(),
			this.getX(),
			this.getY(),
			this.getZ(),
			this.pitch,
			this.roll,
			this.getType(),
			0,
			this.getDeltaMovement(),
			this.tumble
		);
	}

	@Override
	public void recreateFromPacket(@NotNull ClientboundAddEntityPacket packet) {
		double d = packet.getX();
		double e = packet.getY();
		double f = packet.getZ();
		this.roll = packet.getYRot();
		this.pitch = packet.getXRot();
		this.syncPacketPositionCodec(d, e, f);
		this.yBodyRot = packet.getYHeadRot();
		this.tumble = packet.getYHeadRot();
		this.yBodyRotO = this.yBodyRot;
		this.yHeadRotO = this.yHeadRot;
		this.setId(packet.getId());
		this.setUUID(packet.getUUID());
		this.absMoveTo(d, e, f, 0, 0);
		this.setDeltaMovement(packet.getXa(), packet.getYa(), packet.getZa());
	}

	@Override
	public void readAdditionalSaveData(@NotNull CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		this.spawnedFromShears = compound.getBoolean("SpawnedFromShears");
		this.ticksSinceActive = compound.getInt("TicksSinceActive");
		this.isItemNatural = compound.getBoolean("IsTumbleweedItemNatural");
		this.setItemX(compound.getFloat("ItemX"));
		this.setItemZ(compound.getFloat("ItemZ"));
		this.pitch = compound.getFloat("TumblePitch");
		this.roll = compound.getFloat("TumbleRoll");
		this.isTouchingStickingBlock = compound.getBoolean("isTouchingStickingBlock");
		this.isTouchingStoppingBlock = compound.getBoolean("IsTouchingStoppingBlock");
		this.lookRot = compound.getFloat("LookRot");
		this.inventory = NonNullList.withSize(1, ItemStack.EMPTY);
		ContainerHelper.loadAllItems(compound, this.inventory, this.registryAccess());
	}

	@Override
	public void addAdditionalSaveData(@NotNull CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putBoolean("SpawnedFromShears", this.spawnedFromShears);
		compound.putInt("TicksSinceActive", this.ticksSinceActive);
		compound.putBoolean("IsTumbleweedItemNatural", this.isItemNatural);
		compound.putFloat("ItemX", this.getItemX());
		compound.putFloat("ItemZ", this.getItemZ());
		compound.putFloat("TumblePitch", this.pitch);
		compound.putFloat("TumbleRoll", this.roll);
		compound.putBoolean("isTouchingStickingBlock", this.isTouchingStickingBlock);
		compound.putBoolean("IsTouchingStoppingBlock", this.isTouchingStoppingBlock);
		compound.putFloat("LookRot", this.lookRot);
		ContainerHelper.saveAllItems(compound, this.inventory, this.registryAccess());
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(ITEM_STACK, ItemStack.EMPTY);
		builder.define(ITEM_X, 0F);
		builder.define(ITEM_Z, 0F);
	}

	@Nullable
	@Override
	public ItemStack getPickResult() {
		return new ItemStack(WWBlocks.TUMBLEWEED);
	}

	@Override
	public void die(@NotNull DamageSource damageSource) {
		super.die(damageSource);
		if (!this.level().isClientSide && this.level().getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT) && !damageSource.isCreativePlayer()) {
			if (isSilkTouchOrShears(damageSource)) this.level().addFreshEntity(new ItemEntity(this.level(), this.getX(), this.getY(), this.getZ(), new ItemStack(WWBlocks.TUMBLEWEED)));
		}
		this.destroy(true);
	}

	public void spawnBreakParticles() {
		if (this.level() instanceof ServerLevel level) {
			level.sendParticles(
				new BlockParticleOption(ParticleTypes.BLOCK, WWBlocks.TUMBLEWEED.defaultBlockState()),
				this.getX(),
				this.getY(0.6666666666666666D),
				this.getZ(),
				20,
				this.getBbWidth() / 4F,
				this.getBbHeight() / 4F,
				this.getBbWidth() / 4F,
				0.05D
			);
		}
	}

	@Override
	public boolean requiresCustomPersistence() {
		return super.requiresCustomPersistence() || this.spawnedFromShears || this.isLeashed() || this.hasCustomName();
	}

	@Override
	public boolean removeWhenFarAway(double distanceToClosestPlayer) {
		return !this.spawnedFromShears;
	}

	public ItemStack getVisibleItem() {
		return this.entityData.get(ITEM_STACK);
	}

	public void setVisibleItem(ItemStack itemStack) {
		this.getEntityData().set(ITEM_STACK, itemStack);
	}

	public void randomizeItemOffsets() {
		this.setItemX((this.random.nextFloat() * (this.random.nextBoolean() ? 1F : -1F)) * MAX_ITEM_OFFSET);
		this.setItemZ((this.random.nextFloat() * (this.random.nextBoolean() ? 1F : -1F)) * MAX_ITEM_OFFSET);
	}

	public float getItemX() {
		return this.entityData.get(ITEM_X);
	}

	public void setItemX(float f) {
		this.getEntityData().set(ITEM_X, f);
	}

	public float getItemZ() {
		return this.entityData.get(ITEM_Z);
	}

	public void setItemZ(float f) {
		this.getEntityData().set(ITEM_Z, f);
	}

	@Override
	protected void createWitherRose(@Nullable LivingEntity entitySource) {
	}

	@Override
	protected void playSwimSound(float volume) {
	}

	@Override
	@NotNull
	public Iterable<ItemStack> getArmorSlots() {
		return NonNullList.withSize(1, ItemStack.EMPTY);
	}

	@Override
	@NotNull
	public ItemStack getItemBySlot(@NotNull EquipmentSlot slot) {
		return ItemStack.EMPTY;
	}

	@Override
	public void setItemSlot(@NotNull EquipmentSlot slot, @NotNull ItemStack stack) {
	}

	@Override
	@NotNull
	public HumanoidArm getMainArm() {
		return HumanoidArm.LEFT;
	}

	@Override
	public void frozenLib$onSteppedOnBlock(Level level, BlockPos blockPos, @NotNull BlockState blockState) {
		if (blockState.is(WWBlockTags.STOPS_TUMBLEWEED)) {
			this.isTouchingStoppingBlock = true;
		}
	}
}
