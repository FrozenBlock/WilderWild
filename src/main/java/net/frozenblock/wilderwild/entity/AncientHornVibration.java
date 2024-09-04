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

import it.unimi.dsi.fastutil.ints.IntArrayList;
import java.util.List;
import java.util.Optional;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBlockTags;
import net.frozenblock.lib.sound.api.FrozenSoundPackets;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.block.entity.HangingTendrilBlockEntity;
import net.frozenblock.wilderwild.config.WWItemConfig;
import static net.frozenblock.wilderwild.item.AncientHorn.getCooldown;
import net.frozenblock.wilderwild.mod_compat.WWModIntegrations;
import net.frozenblock.wilderwild.particle.options.FloatingSculkBubbleParticleOptions;
import net.frozenblock.wilderwild.registry.WWBlockStateProperties;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.registry.WWDamageTypes;
import net.frozenblock.wilderwild.registry.WWEntities;
import net.frozenblock.wilderwild.registry.WWGameEvents;
import net.frozenblock.wilderwild.registry.WWItems;
import net.frozenblock.wilderwild.registry.WWSounds;
import net.frozenblock.wilderwild.tag.WWBlockTags;
import net.frozenblock.wilderwild.tag.WWEntityTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.DustColorTransitionOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.SpawnUtil;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.monster.warden.AngerLevel;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemCooldowns;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BellBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.SculkSensorBlock;
import net.minecraft.world.level.block.SculkShriekerBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SculkSensorBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

//TODO: Fix rendering (Renders too bright or too dark depending on direction)

public class AncientHornVibration extends AbstractArrow {
	public static final int DEFAULT_LIFESPAN = 300;
	public static final float MIN_SIZE = 0.01F;
	public static final float MAX_SIZE = 30F;
	public static final int MIN_BUBBLE_PARTICLES = 1;
	public static final int MAX_BUBBLE_PARTICLES = 3;
	public static final int BUBBLE_PARTICLES_WATER = 4;
	private static final TagKey<Block> NON_COLLIDE = WWBlockTags.ANCIENT_HORN_NON_COLLIDE;
	private static final EntityDataAccessor<Float> BOUNDING_BOX_MULTIPLIER = SynchedEntityData.defineId(AncientHornVibration.class, EntityDataSerializers.FLOAT);
	public boolean canInteractWithPipe = true;
	private boolean leftOwner;
	private int aliveTicks;
	private double vecX;
	private double vecY;
	private double vecZ;
	private boolean shotByPlayer;
	private int bubbles;
	private BlockState inBlockState;
	private List<Integer> hitEntities = new IntArrayList();
	// Client Variables
	private float prevScale;
	private float scale;

	public AncientHornVibration(@NotNull EntityType<? extends AncientHornVibration> entityType, @NotNull Level level) {
		super(entityType, level);
	}

	public AncientHornVibration(@NotNull Level level, double x, double y, double z) {
		super(WWEntities.ANCIENT_HORN_VIBRATION, x, y, z, level, ItemStack.EMPTY, null);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(BOUNDING_BOX_MULTIPLIER, 0F);
	}

	public List<Entity> collidingEntities() {
		return this.level().getEntities(this, this.getBoundingBox().expandTowards(this.getDeltaMovement()).inflate(1D), this::canHitEntity);
	}

	@Override
	public boolean shouldRender(double cameraX, double cameraY, double cameraZ) {
		return true;
	}

	public int getAliveTicks() {
		return this.aliveTicks;
	}

	public void setShotByPlayer(final boolean bl) {
		this.shotByPlayer = bl;
	}

	public void setBubbles(final int amount) {
		this.bubbles = amount;
	}

	@Override
	public void tick() {
		this.prevScale = this.scale;
		this.baseTick();
		this.shakeTime = 0;
		Vec3 pos = this.position();
		RandomSource random = this.random;
		if (this.bubbles > 0 && this.level() instanceof ServerLevel server) {
			--this.bubbles;
			for (int i = 0; i < random.nextIntBetweenInclusive(MIN_BUBBLE_PARTICLES, MAX_BUBBLE_PARTICLES); i++) {
				int size = random.nextDouble() > 0.7D ? 1 : 0;
				server.sendParticles(
					new FloatingSculkBubbleParticleOptions(
						size,
						20 + random.nextInt(40),
						new Vec3(
							FloatingSculkBubbleParticleOptions.getRandomVelocity(random, size),
							0.05D,
							FloatingSculkBubbleParticleOptions.getRandomVelocity(random, size)
						)
					),
					pos.x(),
					pos.y(),
					pos.z(),
					1,
					0D,
					0D,
					0D,
					0D
				);
			}
		}
		if (this.aliveTicks > WWItemConfig.get().ancientHorn.ancientHornLifespan) {
			this.dissipate();
		}
		++this.aliveTicks;
		if (!this.hasBeenShot) {
			this.gameEvent(GameEvent.PROJECTILE_SHOOT, this.getOwner());
			this.hasBeenShot = true;
		}
		if (!this.leftOwner) {
			this.leftOwner = this.checkLeftOwner();
		}
		Vec3 deltaMovement = this.getDeltaMovement();
		if (this.xRotO == 0.0F && this.yRotO == 0.0F) {
			double horizontalDistance = deltaMovement.horizontalDistance();
			this.setYRot((float) (Mth.atan2(deltaMovement.x, deltaMovement.z) * Mth.RAD_TO_DEG));
			this.setXRot((float) (Mth.atan2(deltaMovement.y, horizontalDistance) * Mth.RAD_TO_DEG));
			this.yRotO = this.getYRot();
			this.xRotO = this.getXRot();
		}
		BlockPos blockPos = this.blockPosition();
		BlockState blockState = this.level().getBlockState(blockPos);

		if (this.isInWater() && level() instanceof ServerLevel server) {
			for (int i = 0; i < BUBBLE_PARTICLES_WATER; i++) {
				server.sendParticles(
					new FloatingSculkBubbleParticleOptions(
						0,
						60,
						new Vec3(
							FloatingSculkBubbleParticleOptions.getRandomVelocity(random, 0),
							0.05D,
							FloatingSculkBubbleParticleOptions.getRandomVelocity(random, 0)
						)
					),
					pos.x(),
					pos.y(),
					pos.z(),
					1,
					0D,
					0D,
					0D,
					0D
				);
			}
		}

		if (this.isInWaterOrRain() || blockState.is(Blocks.POWDER_SNOW)) {
			this.clearFire();
		}

		HitResult hitResult = this.level().clip(new ClipContext(pos, pos.add(deltaMovement), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
		if (!this.isRemoved() && this.canInteract()) {
			List<Entity> entities = this.collidingEntities();
			Entity owner = this.getOwner();
			for (Entity entity : entities) {
				if (!this.isRemoved() && entity != null && entity != owner) {
					boolean shouldDamage = true;
					if (entity instanceof Player player) {
						if (player.isCreative()) {
							shouldDamage = false;
						}
						if (owner instanceof Player playerOwner && !playerOwner.canHarmPlayer(player)) {
							shouldDamage = false;
						}
					}
					if (entity.isInvulnerable()) {
						shouldDamage = false;
					}
					int uuid = entity.getUUID().hashCode();
					if (this.hitEntities.contains(uuid)) {
						shouldDamage = false;
					}
					if (shouldDamage) {
						this.hitEntities.add(uuid);
						this.hitEntity(entity);
					}
				}
			}
		}
		boolean noPhysics = this.isNoPhysics();
		if (!this.isRemoved() && !noPhysics) {
			this.onHit(hitResult);
			if (this.isRemoved()) {
				return;
			}
			this.hasImpulse = true;
		}
		double deltaX = deltaMovement.x;
		double deltaY = deltaMovement.y;
		double deltaZ = deltaMovement.z;
		float moveDivider = (float) (this.getBoundingBoxMultiplier() * 0.5D + 1D);
		double newX = pos.x() + (deltaX / moveDivider);
		double newY = pos.y() + (deltaY / moveDivider);
		double newZ = pos.z() + (deltaZ / moveDivider);
		double horizontalDistance = deltaMovement.horizontalDistance();
		if (noPhysics) {
			this.setYRot((float) (Mth.atan2(-deltaX, -deltaZ) * Mth.RAD_TO_DEG));
		} else {
			this.setYRot((float) (Mth.atan2(deltaX, deltaZ) * Mth.RAD_TO_DEG));
		}
		this.setXRot((float) (Mth.atan2(deltaY, horizontalDistance) * Mth.RAD_TO_DEG));
		this.setXRot(lerpRotation(this.xRotO, this.getXRot()));
		this.setYRot(lerpRotation(this.yRotO, this.getYRot()));

		this.setPos(newX, newY, newZ);
		this.checkInsideBlocks();
		float scale = Mth.clamp(
			this.getBoundingBoxMultiplier() + WWItemConfig.get().ancientHorn.ancientHornSizeMultiplier,
			MIN_SIZE,
			MAX_SIZE
		);
		this.setBoundingBoxMultiplier(scale);
		this.scale = scale;
	}

	@NotNull
	@Override
	public EntityDimensions getDimensions(Pose pose) {
		return super.getDimensions(pose).scale(1F + (this.getBoundingBoxMultiplier() / 2F));
	}

	public void setCooldown(int cooldownTicks) {
		if (this.getOwner() instanceof ServerPlayer user) {
			user.getCooldowns().addCooldown(WWItems.ANCIENT_HORN, cooldownTicks);
		}
	}

	public void dissipate() {
		if (this.level() instanceof ServerLevel level) {
			level.sendParticles(
				new DustColorTransitionOptions(DustColorTransitionOptions.SCULK_PARTICLE_COLOR, DustColorTransitionOptions.SCULK_PARTICLE_COLOR, 1F),
				this.getX(),
				this.getY(0.5D),
				this.getZ(),
				20,
				this.getBbWidth() / 2F,
				this.getBbHeight() / 2F,
				this.getBbWidth() / 2F,
				0.05D
			);
			this.playSound(this.getDefaultHitGroundSoundEvent(), 1F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
			this.remove(RemovalReason.DISCARDED);
		}
	}

	public void addCooldown(int cooldownTicks) {
		if (this.getOwner() instanceof ServerPlayer user && !user.isCreative()) {
			ItemCooldowns manager = user.getCooldowns();
			ItemCooldowns.CooldownInstance entry = manager.cooldowns.get(WWItems.ANCIENT_HORN);
			if (entry != null) {
				manager.removeCooldown(WWItems.ANCIENT_HORN);
				manager.addCooldown(WWItems.ANCIENT_HORN, (entry.endTime - entry.startTime) + cooldownTicks);
			} else {
				manager.addCooldown(WWItems.ANCIENT_HORN, cooldownTicks);
			}
		}
	}

	@Override
	public boolean canHitEntity(@NotNull Entity entity) {
		if (!entity.isSpectator() && entity.isAlive() && entity.isPickable() && !(entity instanceof Projectile)) {
			Entity owner = this.getOwner();
			return owner != null && (this.leftOwner || !owner.isPassengerOfSameVehicle(entity));
		} else {
			return false;
		}
	}

	@Override
	public void playerTouch(@NotNull Player player) {
	}

	@Override
	protected void onHitBlock(@NotNull BlockHitResult result) { //BLOCK INTERACTIONS
		BlockPos pos = result.getBlockPos();
		this.inBlockState = this.level().getBlockState(pos);
		BlockState blockState = this.level().getBlockState(pos);
		Entity owner = this.getOwner();
		if (this.canInteractWithPipe
			&& WWModIntegrations.SIMPLE_COPPER_PIPES_INTEGRATION.getIntegration().isCopperPipe(blockState)
			&& owner != null
			&& result.getDirection() == blockState.getValue(BlockStateProperties.FACING).getOpposite()
			&& this.level() instanceof ServerLevel server
			&& WWModIntegrations.SIMPLE_COPPER_PIPES_INTEGRATION.getIntegration().addHornNbtToBlock(server, pos, owner)
		) {
			this.discard();
			return;
		}
		blockState.onProjectileHit(this.level(), blockState, result, this);
		Vec3 hitVec = result.getLocation().subtract(this.getX(), this.getY(), this.getZ());
		this.setDeltaMovement(hitVec);
		Vec3 hitNormal = hitVec.normalize().scale(0.05D);
		this.setPosRaw(this.getX() - hitNormal.x, this.getY() - hitNormal.y, this.getZ() - hitNormal.z);
		this.inGround = true;
		this.setCritArrow(false);
		if (this.level() instanceof ServerLevel server && this.canInteract()) {
			var block = blockState.getBlock();
			if (blockState.getBlock() == Blocks.SCULK_SHRIEKER) {
				if (WWItemConfig.get().ancientHorn.ancientHornCanSummonWarden) {
					if (blockState.getValue(WWBlockStateProperties.SOULS_TAKEN) < 2 && !blockState.getValue(SculkShriekerBlock.SHRIEKING)) {
						if (!blockState.getValue(SculkShriekerBlock.CAN_SUMMON)) {
							server.setBlockAndUpdate(pos, blockState.setValue(WWBlockStateProperties.SOULS_TAKEN, blockState.getValue(WWBlockStateProperties.SOULS_TAKEN) + 1));
						} else {
							server.setBlockAndUpdate(pos, blockState.setValue(SculkShriekerBlock.CAN_SUMMON, false));
						}
						server.sendParticles(
							ParticleTypes.SCULK_SOUL,
							(double) pos.getX() + 0.5D,
							(double) pos.getY() + 1.15D,
							(double) pos.getZ() + 0.5D,
							1,
							0.2D,
							0D,
							0.2D,
							0D
						);
						trySpawnWarden(server, pos);
						Warden.applyDarknessAround(server, Vec3.atCenterOf(pos), null, 40);
						server.levelEvent(LevelEvent.PARTICLES_SCULK_SHRIEK, pos, 0);
						server.gameEvent(GameEvent.SHRIEK, pos, GameEvent.Context.of(owner));
						setCooldown(getCooldown(this.getOwner(), WWItemConfig.get().ancientHorn.ancientHornShriekerCooldown));
					}
				}
			} else if (block instanceof SculkSensorBlock sculkSensor) {
				if (level().getBlockEntity(pos) instanceof SculkSensorBlockEntity sculkSensorBlockEntity) {
					if (blockState.getValue(WWBlockStateProperties.HICCUPPING)) {
						server.setBlockAndUpdate(pos, blockState.setValue(WWBlockStateProperties.HICCUPPING, false));
					} else {
						server.setBlockAndUpdate(pos, blockState.setValue(WWBlockStateProperties.HICCUPPING, true));
					}

					if (SculkSensorBlock.canActivate(blockState)) {
						sculkSensor.activate(null, this.level(), pos, blockState, this.random.nextInt(15), sculkSensorBlockEntity.getLastVibrationFrequency());
						this.level().gameEvent(null, WWGameEvents.SCULK_SENSOR_ACTIVATE, pos);
						setCooldown(getCooldown(owner, WWItemConfig.get().ancientHorn.ancientHornSensorCooldown));
					}
				}
			}
		}

		this.dissipate();
	}

	private static void trySpawnWarden(@NotNull ServerLevel level, @NotNull BlockPos pos) {
		if (level.getGameRules().getBoolean(GameRules.RULE_DO_WARDEN_SPAWNING)) {
			Optional<Warden> warden = SpawnUtil.trySpawnMob(EntityType.WARDEN, MobSpawnType.TRIGGERED, level, pos, 20, 5, 6, SpawnUtil.Strategy.ON_TOP_OF_COLLIDER);
			warden.ifPresent(wardenEntity -> wardenEntity.playSound(SoundEvents.WARDEN_AGITATED, 5F, 1.0F));
		}
	}

	@Override
	@NotNull
	protected SoundEvent getDefaultHitGroundSoundEvent() {
		return WWSounds.ENTITY_ANCIENT_HORN_VIBRATION_DISSIPATE;
	}

	@Override
	public boolean isNoPhysics() {
		BlockState insideState = this.level().getBlockState(this.blockPosition());
		if (insideState.is(WWBlocks.HANGING_TENDRIL) && this.level() instanceof ServerLevel server && this.canInteract()) { //HANGING TENDRIL
			BlockPos pos = this.blockPosition();
			BlockEntity entity = this.level().getBlockEntity(pos);
			if (entity instanceof HangingTendrilBlockEntity tendril) {
				WWConstants.log("Horn Projectile Found Hanging Tendril Entity", WWConstants.UNSTABLE_LOGGING);
				int xp = tendril.getStoredXP();
				if (xp > 0) {
					tendril.setStoredXP(0);
					this.level().explode(this, this.getX(), this.getY(), this.getZ(), 0, Level.ExplosionInteraction.NONE);
					FrozenSoundPackets.createLocalSound(
						this.level(),
						pos,
						BuiltInRegistries.SOUND_EVENT.getHolder(WWSounds.ENTITY_ANCIENT_HORN_VIBRATION_BLAST.getLocation()).orElseThrow(),
						SoundSource.NEUTRAL,
						1.5F,
						1F,
						true
					);
					this.level().destroyBlock(this.blockPosition(), false);
					ExperienceOrb.award(server, Vec3.atCenterOf(pos).add(0, 0, 0), xp);
					setCooldown(getCooldown(this.getOwner(), WWItemConfig.get().ancientHorn.ancientHornTendrilCooldown));
					this.dissipate();
				}
			}
		} else if (insideState.is(NON_COLLIDE)) {
			if (this.level() instanceof ServerLevel server) {
				if (insideState.getBlock() instanceof BellBlock bell) { //BELL INTERACTION
					bell.onProjectileHit(server, insideState, this.level().clip(new ClipContext(this.position(), new Vec3(this.getBlockX(), this.getBlockY(), this.getBlockZ()), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this)), this);
				} else if (insideState.is(ConventionalBlockTags.GLASS_BLOCKS) || insideState.is(ConventionalBlockTags.GLASS_PANES)) {
					if (WWItemConfig.get().ancientHorn.ancientHornShattersGlass || insideState.is(WWBlocks.ECHO_GLASS)) { //GLASS INTERACTION
						insideState.onProjectileHit(this.level(), insideState, this.level().clip(new ClipContext(this.position(), new Vec3(this.getBlockX(), this.getBlockY(), this.getBlockZ()), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this)), this);
						this.level().destroyBlock(this.blockPosition(), false, this);
					}
				}
			}
			return true;
		}
		Vec3 pos = this.position();
		Vec3 deltaMovement = this.getDeltaMovement();
		Vec3 scaledDelta = pos.add(deltaMovement.scale(0.08D));
		BlockHitResult hitResult = this.level().clip(new ClipContext(pos, scaledDelta, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
		if (hitResult.getType() == HitResult.Type.BLOCK) {
			BlockState state = this.level().getBlockState(hitResult.getBlockPos());
			return state.is(NON_COLLIDE);
		}
		return false;
	}

	private boolean checkLeftOwner() {
		Entity owner = this.getOwner();
		if (owner != null) {
			for (Entity entity2 : this.level().getEntities(this, this.getBoundingBox().expandTowards(this.getDeltaMovement()).inflate(1.0D), (entityx) -> !entityx.isSpectator() && entityx.isPickable())) {
				if (entity2.getRootVehicle() == owner.getRootVehicle()) {
					return false;
				}
			}
		}
		return true;
	}

	public boolean canInteract() {
		return this.getOwner() != null;
	}

	@Override
	@NotNull
	protected ItemStack getDefaultPickupItem() {
		return ItemStack.EMPTY;
	}

	public float getBoundingBoxMultiplier() {
		return this.entityData.get(BOUNDING_BOX_MULTIPLIER);
	}

	public float getBoundingBoxMultiplier(float tickDelta) {
		return Mth.lerp(tickDelta, this.prevScale, this.scale);
	}

	public void setBoundingBoxMultiplier(float value) {
		this.entityData.set(BOUNDING_BOX_MULTIPLIER, value);
		this.refreshDimensions();
	}

	@Override
	public void addAdditionalSaveData(@NotNull CompoundTag compound) {
		if (!this.isRemoved()) {
			if (this.inBlockState != null) {
				compound.put("inBlockState", NbtUtils.writeBlockState(this.inBlockState));
			}
			compound.putInt("aliveTicks", this.aliveTicks);
			if (this.leftOwner) {
				compound.putBoolean("LeftOwner", true);
			}
			compound.putBoolean("HasBeenShot", this.hasBeenShot);
			compound.putDouble("originX", this.vecX);
			compound.putDouble("originY", this.vecY);
			compound.putDouble("originZ", this.vecZ);
			compound.putBoolean("shotByPlayer", this.shotByPlayer);
			compound.putInt("bubbles", this.bubbles);
			compound.putFloat("boundingBoxMultiplier", this.getBoundingBoxMultiplier());
			compound.putIntArray("hitEntities", this.hitEntities);
			compound.putBoolean("canInteractWithPipe", this.canInteractWithPipe);
		}
	}

	@Override
	public void readAdditionalSaveData(@NotNull CompoundTag compound) {
		if (!this.isRemoved()) {
			if (compound.contains("inBlockState", 10)) {
				this.inBlockState = NbtUtils.readBlockState(this.level().holderLookup(Registries.BLOCK), compound.getCompound("inBlockState"));
			}
			this.aliveTicks = compound.getInt("aliveTicks");
			this.leftOwner = compound.getBoolean("LeftOwner");
			this.hasBeenShot = compound.getBoolean("HasBeenShot");
			this.vecX = compound.getDouble("originX");
			this.vecY = compound.getDouble("originY");
			this.vecZ = compound.getDouble("originZ");
			this.shotByPlayer = compound.getBoolean("shotByPlayer");
			this.bubbles = compound.getInt("bubbles");
			this.setBoundingBoxMultiplier(compound.getFloat("boundingBoxMultiplier"));
			this.hitEntities = IntArrayList.wrap(compound.getIntArray("hitEntities"));
			if (compound.contains("canInteractWithPipe")) {
				this.canInteractWithPipe = compound.getBoolean("canInteractWithPipe");
			}
		}
	}

	@Override
	public void shootFromRotation(@NotNull Entity shooter, float pitch, float yaw, float roll, float speed, float divergence) {
		float xRot = -Mth.sin(yaw * Mth.DEG_TO_RAD) * Mth.cos(pitch * Mth.DEG_TO_RAD);
		float yRot = -Mth.sin((pitch + roll) * Mth.DEG_TO_RAD);
		float zRot = Mth.cos(yaw * Mth.DEG_TO_RAD) * Mth.cos(pitch * Mth.DEG_TO_RAD);
		this.shoot(xRot, yRot, zRot, speed, divergence);
		this.vecX = shooter.getX();
		this.vecY = shooter.getEyeY();
		this.vecZ = shooter.getZ();
		this.setOwner(shooter);
	}

	@Override
	protected void onHit(@NotNull HitResult result) {
		HitResult.Type type = result.getType();
		if (type == HitResult.Type.BLOCK) {
			BlockHitResult blockHitResult = (BlockHitResult) result;
			if (!level().getBlockState(blockHitResult.getBlockPos()).is(NON_COLLIDE)) {
				this.onHitBlock(blockHitResult);
			}
		}
	}

	public float getDamage(@Nullable Entity entity) {
		int base = entity instanceof Player ? WWItemConfig.get().ancientHorn.ancientHornPlayerDamage : WWItemConfig.get().ancientHorn.ancientHornMobDamage;
		return base / (this.getBoundingBoxMultiplier() + 1F);
	}

	@Override
	protected float getWaterInertia() {
		return 1F;
	}

	@Override
	public boolean isNoGravity() {
		return true;
	}

	private void hitEntity(@NotNull Entity entity) {
		Level level = this.level();
		float damage = this.getDamage(entity);
		Entity owner = this.getOwner();
		if (entity != owner) {
			DamageSource damageSource;
			if (owner == null) {
				damageSource = this.damageSources().source(WWDamageTypes.ANCIENT_HORN, this, this);
			} else {
				damageSource = this.damageSources().source(WWDamageTypes.ANCIENT_HORN, this, owner);
				if (owner instanceof LivingEntity livingEntity) {
					livingEntity.setLastHurtMob(entity);
				}
			}
			int fireTicks = entity.getRemainingFireTicks();
			if (this.isOnFire()) {
				entity.igniteForSeconds(5);
			}
			if (entity instanceof Warden warden && owner != null && canInteract()) {
				warden.increaseAngerAt(owner, AngerLevel.ANGRY.getMinimumAnger() + 20, true);
				warden.playSound(SoundEvents.WARDEN_TENDRIL_CLICKS, 5F, warden.getVoicePitch());
				this.discard();
			} else if (!entity.getType().is(WWEntityTags.ANCIENT_HORN_IMMUNE)) {
				if (entity.hurt(damageSource, damage)) {
					if (entity instanceof LivingEntity livingEntity) {
						if (!level.isClientSide && owner instanceof LivingEntity) {
							EnchantmentHelper.doPostAttackEffectsWithItemSource((ServerLevel) level, livingEntity, damageSource, this.getWeaponItem());
						}
						this.doPostHurtEffects(livingEntity);
						if (livingEntity.isDeadOrDying() && level instanceof ServerLevel server) {
							server.sendParticles(ParticleTypes.SCULK_SOUL, livingEntity.getX(), livingEntity.getEyeY(), livingEntity.getZ(), 1, 0.2D, 0.0D, 0.2D, 0.0D);
							addCooldown(livingEntity.getExperienceReward(server, damageSource.getEntity()) * 10);
						}
					}
				} else {
					entity.setRemainingFireTicks(fireTicks);
					if (!level.isClientSide && this.getDeltaMovement().lengthSqr() < 1.0E-7D) {
						this.discard();
					}
				}
			} else {
				entity.setRemainingFireTicks(fireTicks);
				if (!level.isClientSide && this.getDeltaMovement().lengthSqr() < 1.0E-7D) {
					this.discard();
				}
			}
		}
	}

	@Override
	public void remove(@NotNull RemovalReason reason) {
		this.hitEntities.clear();
		super.remove(reason);
	}

	@Override
	public boolean dampensVibrations() {
		return true;
	}

	@Override
	public void gameEvent(@NotNull Holder<GameEvent> event) {
	}

	@Override
	public void gameEvent(@NotNull Holder<GameEvent> event, @Nullable Entity entity) {
	}

}
