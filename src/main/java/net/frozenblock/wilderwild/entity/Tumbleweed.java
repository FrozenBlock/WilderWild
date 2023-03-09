/*
 * Copyright 2022-2023 FrozenBlock
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

import java.util.List;
import net.frozenblock.lib.tag.api.TagUtils;
import net.frozenblock.lib.wind.api.WindManager;
import net.frozenblock.wilderwild.block.MesogleaBlock;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.registry.RegisterDamageTypes;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.frozenblock.wilderwild.tag.WilderItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
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
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Pose;
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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Tumbleweed extends Mob {
	public NonNullList<ItemStack> inventory;

	public boolean spawnedFromShears;
	public int ticksSinceActive;
	public boolean isItemNatural;

	public float prevPitch;
	public float prevRoll;
	public float pitch;
	public float roll;
	public float itemX;
	public float itemZ;

	private static final double windMultiplier = 1.4;
	private static final double windClamp = 0.17;
	private static final float rotationAmount = 55F;

	private static final EntityDataAccessor<ItemStack> ITEM_STACK = SynchedEntityData.defineId(Tumbleweed.class, EntityDataSerializers.ITEM_STACK);
	private static final EntityDataAccessor<Float> ITEM_X = SynchedEntityData.defineId(Tumbleweed.class, EntityDataSerializers.FLOAT);
	private static final EntityDataAccessor<Float> ITEM_Z = SynchedEntityData.defineId(Tumbleweed.class, EntityDataSerializers.FLOAT);

    public Tumbleweed(EntityType<Tumbleweed> entityType, Level level) {
		super(entityType, level);
		this.blocksBuilding = true;
		this.inventory = NonNullList.withSize(1, ItemStack.EMPTY);
	}

	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor level, @NotNull DifficultyInstance difficulty, @NotNull MobSpawnType reason, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag dataTag) {
		if (this.inventory.get(0).isEmpty() && reason == MobSpawnType.NATURAL) {
			int diff = difficulty.getDifficulty().getId();
			if (level.getRandom().nextInt(0,  diff == 0 ? 32 : (27 / diff)) == 0) {
				int tagSelector = level.getRandom().nextInt(1, 6);
				TagKey<Item> itemTag = tagSelector <= 1 ? WilderItemTags.TUMBLEWEED_RARE : tagSelector <= 3 ? WilderItemTags.TUMBLEWEED_MEDIUM : WilderItemTags.TUMBLEWEED_COMMON;
				this.setItem(new ItemStack(TagUtils.getRandomEntry(level.getRandom(), itemTag)), true);
			} else if (level.getRandom().nextInt(0, 15) == 0) {
				this.setItem(new ItemStack(RegisterBlocks.TUMBLEWEED_PLANT), true);
			}
		}
		return super.finalizeSpawn(level, difficulty, reason, spawnData, dataTag);
	}

	public static boolean canSpawn(EntityType<Tumbleweed> type, ServerLevelAccessor level, MobSpawnType reason, BlockPos pos, RandomSource random) {
		return level.getBrightness(LightLayer.SKY, pos) > 7 && random.nextInt(0, 15) == 12 && pos.getY() > level.getSeaLevel();
	}

	public static AttributeSupplier.Builder addAttributes() {
		return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 1D);
	}

	@Override
	protected void doPush(@NotNull Entity entity) {
		boolean isSmall = entity.getBoundingBox().getSize() < this.getBoundingBox().getSize() * 0.9;
		if (entity instanceof Tumbleweed) {
			super.doPush(entity);
		}
		if (this.getDeltaPos().length() > (isSmall ? 0.2 : 0.3) && this.isMovingTowards(entity) && !(entity instanceof Tumbleweed)) {
			boolean hurt = entity.hurt(this.damageSources().source(RegisterDamageTypes.TUMBLEWEED, this), 2F);
			isSmall = isSmall || !entity.isAlive() || !hurt;
			if (!isSmall) {
				this.destroy(false);
			}
		}
	}

	@Override
	protected void dropAllDeathLoot(@NotNull DamageSource damageSource) {
		if (!this.isSilkTouchOrShears(damageSource)) {
			super.dropAllDeathLoot(damageSource);
		}
	}

	@Override
	public void tick() {
		super.tick();
		this.setYRot(0F);
		Vec3 deltaPos = this.getDeltaPos();
		if (deltaPos.horizontalDistance() != 0) {
			this.setYRot(-((float) (Mth.atan2(deltaPos.x * 10, deltaPos.z * 10) * 57.2957763671875D) - 90));
		}
		this.prevPitch = this.pitch;
		this.prevRoll = this.roll;
		this.pitch += deltaPos.z * rotationAmount;
		this.roll += deltaPos.x * rotationAmount;
		if (this.pitch > 360F) {
			this.pitch -= 360F;
			this.prevPitch -= 360F;
		}
		if (this.roll > 360F) {
			this.roll -= 360F;
			this.prevRoll -= 360F;
		}
		if (this.level.isClientSide) {
			this.itemX = this.getItemX();
			this.itemZ = this.getItemZ();
		} else if (!this.isRemoved()) {
			this.heal(1F);
			double brightness = this.level.getBrightness(LightLayer.SKY, this.blockPosition());
			Player entity = this.level.getNearestPlayer(this, -1.0);
			if ((brightness < 7 && !this.requiresCustomPersistence() && (entity == null || entity.distanceTo(this) > 24)) || (this.wasTouchingWater && !(this.getFeetBlockState().getBlock() instanceof MesogleaBlock))) {
				++this.ticksSinceActive;
				if (this.ticksSinceActive >= 200) {
					this.destroy(false);
				}
			} else {
				this.ticksSinceActive = 0;
			}

			Vec3 deltaMovement = this.getDeltaMovement();
			double multiplier = (Math.max((brightness - (Math.max(15 - brightness, 0))), 0) * 0.0667) * (this.wasTouchingWater ? 0.16777216 : 1);
			double windX = Mth.clamp(WindManager.windX * windMultiplier, -windClamp, windClamp);
			double windZ = Mth.clamp(WindManager.windZ * windMultiplier, -windClamp, windClamp);
			deltaMovement = deltaMovement.add((windX * 0.2) * multiplier, 0, (windZ * 0.2) * multiplier);
			deltaMovement = new Vec3(deltaMovement.x, deltaMovement.y < 0 ? deltaMovement.y * 0.88 : deltaMovement.y, deltaMovement.z);
			if (deltaPos.y <= 0 && this.isOnGround()) {
				deltaMovement = deltaMovement.add(0, Math.min(0.65, ((deltaPos.horizontalDistance() * 1.2))) * multiplier, 0);
			}
			if (deltaPos.x == 0) {
				double nonNegX = deltaMovement.x < 0 ? -deltaMovement.x : deltaMovement.x;
				deltaMovement = deltaMovement.add(0, (nonNegX * 1.8) * multiplier, 0);
			}
			if (deltaPos.z == 0) {
				double nonNegZ = deltaMovement.z < 0 ? -deltaMovement.z : deltaMovement.z;
				deltaMovement = deltaMovement.add(0, (nonNegZ * 1.8) * multiplier, 0);
			}
			if (this.wasEyeInWater) {
				deltaMovement = deltaMovement.add(0, 0.01, 0);
			}
			this.setDeltaMovement(deltaMovement);
			this.tickAfterWindLeash();

			ItemStack stack = this.inventory.get(0);
			if (stack.getCount() > 1) {
				this.level.addFreshEntity(new ItemEntity(this.level, this.getX(), this.getY(), this.getZ(), stack.split(stack.getCount() - 1)));
			}
			this.pickupItem();
			this.setVisibleItem(stack);
		}
	}

	protected void tickAfterWindLeash() {
		Entity entity = this.getLeashHolder();
		if (entity != null && entity.level == this.level) {
			this.restrictTo(entity.blockPosition(), 5);
			float f = this.distanceTo(entity);
			if (f > 10.0f) {
				this.dropLeash(true, true);
			} else if (f > 6.0f) {
				double d = (entity.getX() - this.getX()) / (double)f;
				double e = (entity.getY() - this.getY()) / (double)f;
				double g = (entity.getZ() - this.getZ()) / (double)f;
				this.setDeltaMovement(this.getDeltaMovement().add(Math.copySign(d * d * 0.4, d), Math.copySign(e * e * 0.4, e), Math.copySign(g * g * 0.4, g)));
			}
		}
	}

	public void pickupItem() {
		if (this.inventory.get(0).isEmpty() && this.level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING) && !this.isRemoved()) {
			List<ItemEntity> list = this.level.getEntitiesOfClass(ItemEntity.class, this.getBoundingBox().inflate(0.15));
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
	}

	public void dropItem(boolean killed) {
		if (!this.isItemNatural || killed) {
			this.level.addFreshEntity(new ItemEntity(this.level, this.getX(), this.getY() + 0.4375, this.getZ(), this.inventory.get(0).split(1)));
		}
	}

	public void destroy(boolean killed) {
		if (this.isAlive()) {
			this.playSound(RegisterSounds.ENTITY_TUMBLEWEED_BREAK, this.getSoundVolume(), this.getVoicePitch());
		}
		this.dropItem(killed);
		this.spawnBreakParticles();
		this.remove(RemovalReason.KILLED);
	}

	public void setItem(ItemStack stack, boolean natural) {
		this.inventory.set(0, stack);
		this.isItemNatural = natural;
		this.randomizeItemOffsets();
	}

	public boolean isMovingTowards(@NotNull Entity entity) {
		return entity.getPosition(0).distanceTo(this.getPosition(0)) > entity.getPosition(1).distanceTo(this.getPosition(1));
	}

	public Vec3 getDeltaPos() {
		return this.getPosition(0).subtract(this.getPosition(1));
	}

	public boolean isSilkTouchOrShears(DamageSource damageSource) {
		if (damageSource.getDirectEntity() instanceof Player player) {
			ItemStack stack = player.getMainHandItem();
			return EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, stack) > 0 || stack.is(Items.SHEARS);
		}
		return false;
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
	public boolean canBeLeashed(@NotNull Player player) {
		return WilderSharedConstants.config().leashedTumbleweed();
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
	public boolean dampensVibrations() {
		return true;
	}

	@Override
	protected boolean canRide(@NotNull Entity vehicle) {
		return false;
	}

	@Override
	protected float getStandingEyeHeight(@NotNull Pose pose, EntityDimensions dimensions) {
		return dimensions.height * 0.5F;
	}

	@Override
	protected SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
		return RegisterSounds.ENTITY_TUMBLEWEED_DAMAGE;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return RegisterSounds.ENTITY_TUMBLEWEED_BREAK;
	}

	@Override
	protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState state) {
		this.playSound(RegisterSounds.ENTITY_TUMBLEWEED_BOUNCE, 0.15f, 1.0f);
	}

	@Override
	public boolean causeFallDamage(float fallDistance, float multiplier, @NotNull DamageSource source) {
		return false;
	}

	@Override
	public Packet<ClientGamePacketListener> getAddEntityPacket() {
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
				this.yHeadRot
		);
	}

	@Override
	public void recreateFromPacket(ClientboundAddEntityPacket packet) {
		double d = packet.getX();
		double e = packet.getY();
		double f = packet.getZ();
		this.roll = packet.getYRot();
		this.pitch = packet.getXRot();
		this.syncPacketPositionCodec(d, e, f);
		this.yBodyRot = packet.getYHeadRot();
		this.yHeadRot = packet.getYHeadRot();
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
		this.spawnedFromShears = compound.getBoolean("spawned_from_shears");
		this.ticksSinceActive = compound.getInt("ticks_since_active");
		this.isItemNatural = compound.getBoolean("is_tumbleweed_item_natural");
		this.setItemX(compound.getFloat("item_x"));
		this.setItemZ(compound.getFloat("item_z"));
		this.pitch = compound.getFloat("tumble_pitch");
		this.roll = compound.getFloat("tumble_roll");
		this.inventory = NonNullList.withSize(1, ItemStack.EMPTY);
		ContainerHelper.loadAllItems(compound, this.inventory);
	}

	@Override
	public void addAdditionalSaveData(@NotNull CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putBoolean("spawned_from_shears", this.spawnedFromShears);
		compound.putInt("ticks_since_active", this.ticksSinceActive);
		compound.putBoolean("is_tumbleweed_item_natural", this.isItemNatural);
		compound.putFloat("item_x", this.getItemX());
		compound.putFloat("item_z", this.getItemZ());
		compound.putFloat("tumble_pitch", this.pitch);
		compound.putFloat("tumble_roll", this.roll);
		ContainerHelper.saveAllItems(compound, this.inventory);
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(ITEM_STACK, ItemStack.EMPTY);
		this.entityData.define(ITEM_X, 0F);
		this.entityData.define(ITEM_Z, 0F);
	}

	@Override
	public void die(@NotNull DamageSource damageSource) {
		super.die(damageSource);
		if (damageSource.getDirectEntity() instanceof Player) {
			if (this.level.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT) && !damageSource.isCreativePlayer()) {
				if (isSilkTouchOrShears(damageSource)) {
					this.level.addFreshEntity(new ItemEntity(this.level, this.getX(), this.getY(), this.getZ(), new ItemStack(RegisterBlocks.TUMBLEWEED)));
				}
			}
		}
		this.destroy(true);
	}

	public void spawnBreakParticles() {
		if (this.level instanceof ServerLevel level) {
			level.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, RegisterBlocks.TUMBLEWEED.defaultBlockState()), this.getX(), this.getY(0.6666666666666666D), this.getZ(), 20, this.getBbWidth() / 4.0F, this.getBbHeight() / 4.0F, this.getBbWidth() / 4.0F, 0.05D);
		}
	}

	@Override
	public boolean requiresCustomPersistence() {
		return super.requiresCustomPersistence() || this.spawnedFromShears || this.isLeashed();
	}

	@Override
	public boolean removeWhenFarAway(double distanceToClosestPlayer) {
		return !this.spawnedFromShears;
	}

	public void setVisibleItem(ItemStack itemStack) {
		this.getEntityData().set(ITEM_STACK, itemStack);
	}

	public ItemStack getVisibleItem() {
		return this.entityData.get(ITEM_STACK);
	}

	private static final float maxItemOffset = 0.25F;
	public void randomizeItemOffsets() {
		this.setItemX((this.random.nextFloat() * (this.random.nextBoolean() ? 1 : -1)) * maxItemOffset);
		this.setItemZ((this.random.nextFloat() * (this.random.nextBoolean() ? 1 : -1)) * maxItemOffset);
	}

	public void setItemX(float f) {
		this.getEntityData().set(ITEM_X, f);
	}

	public float getItemX() {
		return this.entityData.get(ITEM_X);
	}

	public void setItemZ(float f) {
		this.getEntityData().set(ITEM_Z, f);
	}

	public float getItemZ() {
		return this.entityData.get(ITEM_Z);
	}

	@Override
	protected void createWitherRose(@Nullable LivingEntity entitySource) {
	}

	@Override
	protected void playSwimSound(float volume) {
	}


	@Override
	public Iterable<ItemStack> getArmorSlots() {
		return NonNullList.withSize(1, ItemStack.EMPTY);
	}

	@Override
	public ItemStack getItemBySlot(@NotNull EquipmentSlot slot) {
		return ItemStack.EMPTY;
	}

	@Override
	public void setItemSlot(@NotNull EquipmentSlot slot, @NotNull ItemStack stack) {
	}

	@Override
	public HumanoidArm getMainArm() {
		return HumanoidArm.LEFT;
	}
}
