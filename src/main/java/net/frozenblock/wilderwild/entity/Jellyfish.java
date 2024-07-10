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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import net.frozenblock.lib.entity.api.EntityUtils;
import net.frozenblock.lib.entity.api.NoFlopAbstractFish;
import net.frozenblock.wilderwild.WilderConstants;
import net.frozenblock.wilderwild.config.EntityConfig;
import net.frozenblock.wilderwild.entity.ai.jellyfish.JellyfishAi;
import net.frozenblock.wilderwild.entity.ai.jellyfish.JellyfishTemptGoal;
import net.frozenblock.wilderwild.entity.variant.JellyfishVariant;
import net.frozenblock.wilderwild.networking.packet.WilderJellyfishStingPacket;
import net.frozenblock.wilderwild.registry.RegisterEntities;
import net.frozenblock.wilderwild.registry.RegisterItems;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.frozenblock.wilderwild.registry.WilderRegistry;
import net.frozenblock.wilderwild.tag.WilderBiomeTags;
import net.frozenblock.wilderwild.tag.WilderEntityTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
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
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Jellyfish extends NoFlopAbstractFish {
	private static final float MAX_TARGET_DISTANCE = 15F;
	public static final int POISON_DURATION_IN_SECONDS_BABY = 4;
	public static final int POISON_DURATION_IN_SECONDS = 10;
	public static final float STING_PITCH = 1F;
	public static final float STING_PITCH_BABY = 1.2F;
	public static final int SPAWN_CHANCE = 110;
	public static final int SPAWN_HEIGHT_NORMAL_SEA_OFFSET = 13;
	public static final float BUBBLE_SPAWN_CHANCE = 0.2F;
	public static final double HIDABLE_PLAYER_DISTANCE = 24D;
	public static final int HIDABLE_TICKS_SINCE_SPAWN = 150;
	public static final int HIDING_CHANCE = 25;
	public static final @NotNull ResourceLocation JELLYFISH_MOVEMENT_SPEED_MODIFIER_BABY_UUID = WilderConstants.id("movement_speed_modifier_baby");
	public static final AttributeModifier JELLYFISH_MOVEMENT_SPEED_MODIFIER_BABY = new AttributeModifier(JELLYFISH_MOVEMENT_SPEED_MODIFIER_BABY_UUID, 0.5D, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
	public static final ArrayList<JellyfishVariant> COLORED_VARIANTS = new ArrayList<>(WilderRegistry.JELLYFISH_VARIANT.stream()
		.filter(JellyfishVariant::isNormal)
		.collect(Collectors.toList())
	);
	public static final ArrayList<JellyfishVariant> PEARLESCENT_VARIANTS = new ArrayList<>(WilderRegistry.JELLYFISH_VARIANT.stream()
		.filter(JellyfishVariant::pearlescent)
		.collect(Collectors.toList())
	);
	private static final EntityDataAccessor<String> VARIANT = SynchedEntityData.defineId(Jellyfish.class, EntityDataSerializers.STRING);
	private static final EntityDataAccessor<Boolean> CAN_REPRODUCE = SynchedEntityData.defineId(Jellyfish.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> IS_BABY = SynchedEntityData.defineId(Jellyfish.class, EntityDataSerializers.BOOLEAN);
	private static final Map<ServerLevelAccessor, Integer> NON_PEARLESCENT_JELLYFISH_PER_LEVEL = new HashMap<>();
	public final TargetingConditions targetingConditions = TargetingConditions.forNonCombat().ignoreInvisibilityTesting().ignoreLineOfSight().selector(this::canTargetEntity);
	public float xBodyRot;
	public float xRot1;
	public float xRot2;
	public float xRot3;
	public float xRot4;
	public float xRot5;
	public float xRot6;
	public boolean vanishing;
	public boolean growing;
	public float prevScale = 1F;
	public float scale = 1F;
	public int ticksSinceSpawn;
	public int age;
	public int fullness;
	public int reproductionCooldown;
	private int forcedAge;
	private int forcedAgeTimer;

	public Jellyfish(@NotNull EntityType<? extends Jellyfish> entityType, @NotNull Level level) {
		super(entityType, level);
		this.getNavigation().setCanFloat(false);
	}

	public static int getJellyfishPerLevel(@NotNull ServerLevel level, boolean pearlescent) {
		AtomicInteger count = new AtomicInteger();
		if (!NON_PEARLESCENT_JELLYFISH_PER_LEVEL.containsKey(level)) {
			EntityUtils.getEntitiesPerLevel(level).forEach(entity -> {
				if (entity instanceof Jellyfish jellyfish && (pearlescent ? jellyfish.getVariant().pearlescent() : jellyfish.getVariant().isNormal())) {
					count.addAndGet(1);
				}
			});
			NON_PEARLESCENT_JELLYFISH_PER_LEVEL.put(level, count.get());
		} else {
			count.set(NON_PEARLESCENT_JELLYFISH_PER_LEVEL.get(level));
		}
		return count.get();
	}

	public static void clearLevelToNonPearlescentCount() {
		NON_PEARLESCENT_JELLYFISH_PER_LEVEL.clear();
	}

	public static boolean checkJellyfishSpawnRules(@NotNull EntityType<Jellyfish> type, @NotNull ServerLevelAccessor level, @NotNull MobSpawnType spawnType, @NotNull BlockPos pos, @NotNull RandomSource random) {
		if (MobSpawnType.isSpawner(spawnType)) {
			return true;
		} else if (!EntityConfig.get().jellyfish.spawnJellyfish) {
			return false;
		}
		Holder<Biome> biome = level.getBiome(pos);
		if (!biome.is(WilderBiomeTags.PEARLESCENT_JELLYFISH) && getJellyfishPerLevel(level.getLevel(), false) >= type.getCategory().getMaxInstancesPerChunk() / 3) {
			return false;
		}
		if (biome.is(WilderBiomeTags.JELLYFISH_SPECIAL_SPAWN)) {
			if (level.getRawBrightness(pos, 0) <= 7 && random.nextInt(level.getRawBrightness(pos, 0) + 3) >= 1) {
				return true;
			}
		}
		int seaLevel = level.getSeaLevel();
		return (random.nextInt(0, SPAWN_CHANCE) == 0 && pos.getY() <= seaLevel && pos.getY() >= seaLevel - SPAWN_HEIGHT_NORMAL_SEA_OFFSET);
	}

	@NotNull
	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes()
			.add(Attributes.MAX_HEALTH, 8D)
			.add(Attributes.MOVEMENT_SPEED, 0.5F)
			.add(Attributes.FOLLOW_RANGE, MAX_TARGET_DISTANCE);
	}

	public static void spawnFromChest(@NotNull Level level, @NotNull BlockState state, @NotNull BlockPos pos, boolean checkConfig) {
		if (checkConfig && !EntityConfig.get().jellyfish.spawnJellyfish) {
			return;
		}
		Jellyfish jellyfish = new Jellyfish(RegisterEntities.JELLYFISH, level);
		jellyfish.setVariantFromPos(level, pos);
		double additionalX = 0D;
		double additionalZ = 0D;
		if (state.hasProperty(BlockStateProperties.CHEST_TYPE) && state.getValue(BlockStateProperties.CHEST_TYPE) != ChestType.SINGLE) {
			Direction direction = ChestBlock.getConnectedDirection(state);
			additionalX += direction.getStepX() * 0.25D;
			additionalZ += direction.getStepZ() * 0.25D;
		}
		jellyfish.setPos(pos.getX() + 0.5D + additionalX, pos.getY() + 0.75D, pos.getZ() + 0.5D + additionalZ);
		jellyfish.setDeltaMovement(0D, 0.1D + level.random.nextDouble() * 0.07D, 0D);
		jellyfish.prevScale = 0F;
		jellyfish.scale = 0F;
		level.addFreshEntity(jellyfish);
		level.broadcastEntityEvent(jellyfish, (byte) 5);
	}

	public static int getSpeedUpSecondsWhenFeeding(int ticksUntilAdult) {
		return (int) ((ticksUntilAdult / 20F) * 0.1F);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(3, new JellyfishTemptGoal(this, 1.25D));
	}

	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor level, @NotNull DifficultyInstance difficulty, @NotNull MobSpawnType reason, @Nullable SpawnGroupData spawnData) {
		JellyfishGroupData jellyfishGroupData;
		if (spawnData instanceof JellyfishGroupData jellyGroupData) {
			this.setVariant(jellyGroupData.variant);
			jellyfishGroupData = jellyGroupData;
		} else {
			spawnData = jellyfishGroupData = new JellyfishGroupData(true, this.setVariant(level.getBiome(this.blockPosition()), level.getRandom()));
		}
		if (jellyfishGroupData.isShouldSpawnBaby() && level.getRandom().nextFloat() <= jellyfishGroupData.getBabySpawnChance()) {
			this.setBaby(true);
		}
		jellyfishGroupData.increaseGroupSizeByOne();
		return super.finalizeSpawn(level, difficulty, reason, spawnData);
	}

	public void setVariantFromPos(@NotNull Level level, @NotNull BlockPos pos) {
		setVariant(level.getBiome(pos), level.getRandom());
	}

	@NotNull
	private JellyfishVariant setVariant(@NotNull Holder<Biome> biome, RandomSource randomSource) {
		this.setVariant(JellyfishVariant.PINK);
		if (biome.is(WilderBiomeTags.PEARLESCENT_JELLYFISH) && !PEARLESCENT_VARIANTS.isEmpty()) {
			this.setVariant(PEARLESCENT_VARIANTS.get(randomSource.nextInt(PEARLESCENT_VARIANTS.size())));
		} else if (!COLORED_VARIANTS.isEmpty()) {
			this.setVariant(COLORED_VARIANTS.get(randomSource.nextInt(COLORED_VARIANTS.size())));
		}
		return this.getVariant();
	}

	@Override
	public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> key) {
		if (IS_BABY.equals(key)) {
			this.refreshDimensions();
		}
		super.onSyncedDataUpdated(key);
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return this.isInWaterOrBubble() ? RegisterSounds.ENTITY_JELLYFISH_AMBIENT_WATER : null;
	}

	@Override
	@NotNull
	protected SoundEvent getSwimSound() {
		return RegisterSounds.ENTITY_JELLYFISH_SWIM;
	}

	@Override
	protected void playSwimSound(float volume) {
		super.playSwimSound(volume);
		if (this.random.nextFloat() < BUBBLE_SPAWN_CHANCE) {
			this.spawnBubbles();
		}
	}

	@Override
	protected SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
		return this.isInWaterOrBubble() ? RegisterSounds.ENTITY_JELLYFISH_HURT_WATER : RegisterSounds.ENTITY_JELLYFISH_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return this.isInWaterOrBubble() ? RegisterSounds.ENTITY_JELLYFISH_DEATH_WATER : RegisterSounds.ENTITY_JELLYFISH_DEATH;
	}

	@Override
	public boolean canBeLeashed() {
		return !this.isLeashed();
	}

	@Override
	protected float getSoundVolume() {
		return 0.4F;
	}

	@Override
	@NotNull
	public Brain.Provider<Jellyfish> brainProvider() {
		return Brain.provider(JellyfishAi.MEMORY_TYPES, JellyfishAi.SENSOR_TYPES);
	}

	@Override
	@NotNull
	protected Brain<Jellyfish> makeBrain(@NotNull Dynamic<?> dynamic) {
		return JellyfishAi.makeBrain(this, this.brainProvider().makeBrain(dynamic));
	}

	@Override
	@NotNull
	public Brain<Jellyfish> getBrain() {
		return (Brain<Jellyfish>) super.getBrain();
	}

	@Override
	protected void sendDebugPackets() {
		super.sendDebugPackets();
		DebugPackets.sendEntityBrain(this);
	}

	@Override
	@Nullable
	public LivingEntity getTarget() {
		return this.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).orElse(null);
	}

	public void setAttackTarget(LivingEntity entity) {
		this.getBrain().setMemory(MemoryModuleType.ATTACK_TARGET, entity);
	}

	@Override
	public void aiStep() {
		this.prevScale = this.scale;
		super.aiStep();
		this.xRot6 = this.xRot5;
		this.xRot5 = this.xRot4;
		this.xRot4 = this.xRot3;
		this.xRot3 = this.xRot2;
		this.xRot2 = this.xRot1;
		this.xRot1 = this.xBodyRot;

		++this.ticksSinceSpawn;

		if (this.isInWaterOrBubble()) {
			this.heal(0.02F);
			Vec3 vec3 = this.getDeltaMovement();
			if (vec3.horizontalDistance() > 0.005) {
				this.yBodyRot += (float) ((-(Mth.atan2(vec3.x, vec3.z)) * Mth.RAD_TO_DEG - this.yBodyRot) * 0.1F);
				this.setYRot(this.yBodyRot);
			}
			this.xBodyRot += (float) ((-(Mth.atan2(vec3.horizontalDistance(), vec3.y)) * Mth.RAD_TO_DEG - this.xBodyRot) * 0.1F);
		} else {
			this.xBodyRot += (-90.0F - this.xBodyRot) * 0.02F;
		}

		this.stingEntities();

		LivingEntity target = this.getTarget();
		if (target != null) {
			this.getNavigation().stop();
			this.moveToAccurate(target, 2D);
		}

		if (this.growing) {
			if (this.scale < 1F) {
				this.scale += 0.5F;
			} else {
				this.scale = 1F;
				this.growing = false;
			}
		} else if (this.vanishing) {
			if (this.prevScale <= 0F) {
				this.discard();
				this.playSound(RegisterSounds.ENTITY_JELLYFISH_HIDE, 0.8F, this.getVoicePitch());
				return;
			} else {
				this.scale -= 0.25F;
			}
		}

		if (this.level().isClientSide) {
			if (this.forcedAgeTimer > 0) {
				if (this.forcedAgeTimer % 4 == 0) {
					this.level().addParticle(ParticleTypes.HAPPY_VILLAGER, this.getRandomX(1D), this.getRandomY(), this.getRandomZ(1D), 0D, 0D, 0D);
				}
				--this.forcedAgeTimer;
			}
		} else if (this.isAlive()) {
			int i = this.getAge();
			if (i < 0) {
				this.setAge(++i);
			} else if (i > 0) {
				this.setAge(--i);
			}
		}

		this.reproductionCooldown = Math.max(0, this.reproductionCooldown - 1);
		this.setCanReproduce(
			this.reproductionCooldown == 0
				&& this.isInWaterOrBubble()
				&& this.isUnderWater()
				&& !this.vanishing
				&& !this.growing
				&& !this.isBaby()
				&& this.isAlive()
				&& !this.isRemoved()
		);

		AttributeInstance movementSpeed = this.getAttributes().getInstance(Attributes.MOVEMENT_SPEED);
		if (movementSpeed != null) {
			if (this.isBaby()) {
				movementSpeed.addOrUpdateTransientModifier(JELLYFISH_MOVEMENT_SPEED_MODIFIER_BABY);
			} else {
				movementSpeed.removeModifier(JELLYFISH_MOVEMENT_SPEED_MODIFIER_BABY_UUID);
			}
		}
	}

	@Override
	public void handleEntityEvent(byte id) {
		if (id == (byte) 4) {
			this.vanishing = true;
		} else if (id == (byte) 5) {
			this.growing = true;
			this.scale = 0F;
			this.prevScale = 0F;
		} else if (id == (byte) 7) {
			double d = this.random.nextGaussian() * 0.02D;
			double e = this.random.nextGaussian() * 0.02D;
			double f = this.random.nextGaussian() * 0.02D;
			this.level().addParticle(ParticleTypes.HAPPY_VILLAGER, this.getRandomX(1D), this.getRandomY() + 0.5D, this.getRandomZ(1D), d, e, f);
		} else if (id == (byte) 18) {
			for (int i = 0; i < 7; ++i) {
				double d = this.random.nextGaussian() * 0.02D;
				double e = this.random.nextGaussian() * 0.02D;
				double f = this.random.nextGaussian() * 0.02D;
				this.level().addParticle(ParticleTypes.HEART, this.getRandomX(1D), this.getRandomY() + 0.5D, this.getRandomZ(1D), d, e, f);
			}
		} else {
			super.handleEntityEvent(id);
		}
	}

	public void stingEntities() {
		if (this.isAlive() && !this.level().isClientSide) {
			List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(0.08D));
			boolean baby = this.isBaby();
			float damage = baby ? 1F : 3F;

			int poisonDuration = baby ? POISON_DURATION_IN_SECONDS_BABY : POISON_DURATION_IN_SECONDS;
			for (LivingEntity entity : list) {
				if (this.targetingConditions.test(this, entity)) {
					if (entity.hurt(this.damageSources().mobAttack(this), damage)) {
						entity.addEffect(new MobEffectInstance(MobEffects.POISON, poisonDuration * 20, 0), this);
						if (!this.isSilent()) {
							Player playSoundForExcept = null;
							if (entity instanceof ServerPlayer player) {
								playSoundForExcept = player;
								WilderJellyfishStingPacket.sendTo(player, baby);
							}
							this.level().playSound(playSoundForExcept, entity.getX(), entity.getY(), entity.getZ(), RegisterSounds.ENTITY_JELLYFISH_STING, this.getSoundSource(), 1F, baby ? STING_PITCH_BABY : STING_PITCH);
						}
					}
				}
			}
		}
	}

	@Override
	protected void onOffspringSpawnedFromEgg(@NotNull Player player, @NotNull Mob child) {
		if (child instanceof Jellyfish jellyfish) {
			jellyfish.setVariant(this.getVariant());
		}
	}

	public void moveToAccurate(@NotNull Entity entity, double speed) {
		Path path = this.getNavigation().createPath(entity, 0);
		if (path != null) {
			this.getNavigation().moveTo(path, speed);
		}
	}

	@Contract("null->false")
	public boolean canTargetEntity(@Nullable Entity entity) {
		return entity instanceof LivingEntity livingEntity
			&& this.level() == livingEntity.level()
			&& !this.level().getDifficulty().equals(Difficulty.PEACEFUL)
			&& EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(livingEntity)
			&& !this.isAlliedTo(livingEntity)
			&& livingEntity.getType() != EntityType.ARMOR_STAND
			&& livingEntity.getType() != RegisterEntities.JELLYFISH
			&& !livingEntity.isInvulnerable()
			&& !livingEntity.isDeadOrDying()
			&& !livingEntity.isRemoved()
			&& livingEntity.distanceTo(this) < MAX_TARGET_DISTANCE
			&& !livingEntity.getType().is(WilderEntityTags.JELLYFISH_CANT_STING)
			&& this.level().getWorldBorder().isWithinBounds(livingEntity.getBoundingBox());
	}

	@Override
	protected void customServerAiStep() {
		ServerLevel serverLevel = (ServerLevel) this.level();
		serverLevel.getProfiler().push("jellyfishBrain");
		this.getBrain().tick(serverLevel, this);
		this.level().getProfiler().pop();
		this.level().getProfiler().push("jellyfishActivityUpdate");
		JellyfishAi.updateActivity(this);
		this.level().getProfiler().pop();
		super.customServerAiStep();
	}

	public boolean shouldHide() {
		return this.level().getNearestPlayer(this, HIDABLE_PLAYER_DISTANCE) == null
			&& this.ticksSinceSpawn >= HIDABLE_TICKS_SINCE_SPAWN
			&& !this.requiresCustomPersistence()
			&& !this.isPersistenceRequired()
			&& !this.hasCustomName()
			&& !this.isLeashed()
			&& EntityConfig.get().jellyfish.jellyfishHiding
			&& this.getPassengers().isEmpty()
			&& this.getTarget() == null
			&& this.random.nextInt(HIDING_CHANCE) == 0;
	}

	@Override
	@NotNull
	public SoundEvent getPickupSound() {
		return RegisterSounds.ITEM_BUCKET_FILL_JELLYFISH;
	}

	@Override
	@NotNull
	public InteractionResult mobInteract(@NotNull Player player, @NotNull InteractionHand hand) {
		ItemStack itemStack = player.getItemInHand(hand);
		if (itemStack.is(Items.WATER_BUCKET)) {
			return super.mobInteract(player, hand);
		}
		if (!itemStack.is(this.getVariant().reproductionFood())) {
			return InteractionResult.PASS;
		}
		if (this.isBaby()) {
			if (!player.getAbilities().instabuild) {
				itemStack.shrink(1);
			}
			this.ageUp(getSpeedUpSecondsWhenFeeding(-this.getAge()), true);
			return InteractionResult.sidedSuccess(this.level().isClientSide);
		} else if (this.canReproduce()) {
			if (this.level().isClientSide) {
				return InteractionResult.CONSUME;
			} else if (this.level() instanceof ServerLevel serverLevel) {
				if (!player.getAbilities().instabuild) {
					itemStack.shrink(1);
				}
				this.fullness += 1;
				this.ticksSinceSpawn = 0;
				if (this.fullness >= 8 && this.random.nextInt(3) == 0) {
					this.spawnChild(serverLevel);
					this.fullness = 0;
					this.level().broadcastEntityEvent(this, (byte) 18);
					this.reproductionCooldown = 6000;
					this.setCanReproduce(false);
					return InteractionResult.SUCCESS;
				} else {
					this.level().broadcastEntityEvent(this, (byte) 7);
				}
				return InteractionResult.SUCCESS;
			}
		}
		return InteractionResult.PASS;
	}

	public void spawnChild(ServerLevel level) {
		Jellyfish jellyfish = RegisterEntities.JELLYFISH.create(level);
		if (jellyfish == null) {
			return;
		}
		jellyfish.setBaby(true);
		float bbHeight = this.getBbHeight();
		Vec3 vec3 = this.rotateVector(new Vec3(0D, -bbHeight, 0D)).add(this.getX(), this.getY(), this.getZ());
		jellyfish.moveTo(vec3.x, vec3.y + (bbHeight * 0.5D), vec3.z, -this.getYRot(), -this.getXRot());
		jellyfish.setDeltaMovement(this.getDeltaMovement().scale(-0.5D));
		jellyfish.setVariant(this.getVariant());
		level.broadcastEntityEvent(this, (byte) 18);
		if (level.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
			level.addFreshEntity(new ExperienceOrb(level, this.getX(), this.getY(), this.getZ(), this.getRandom().nextInt(7) + 1));
		}
		level.addFreshEntityWithPassengers(jellyfish);
	}

	@NotNull
	private Vec3 rotateVector(@NotNull Vec3 vector) {
		Vec3 vec3 = vector.xRot(this.xRot1 * Mth.DEG_TO_RAD);
		vec3 = vec3.yRot(-this.yBodyRotO * Mth.DEG_TO_RAD);
		return vec3;
	}

	private void spawnBubbles() {
		if (this.level() instanceof ServerLevel serverLevel && !this.isBaby()) {
			double deltaLength = this.getDeltaMovement().length();
			float bbHeight = this.getBbHeight();
			Vec3 vec3 = this.rotateVector(new Vec3(0D, -bbHeight, 0D)).add(this.getX(), this.getY(), this.getZ());
			for (int i = 0; i < this.random.nextInt(0, (int) (2D + (deltaLength * 25D))); ++i) {
				Vec3 vec32 = this.rotateVector(new Vec3(this.random.nextDouble() * 0.6D - 0.3D, -1D, this.random.nextDouble() * 0.6D - 0.3D));
				Vec3 vec33 = vec32.scale(0.3D + (this.random.nextFloat() * 2D));
				serverLevel.sendParticles(ParticleTypes.BUBBLE, vec3.x, vec3.y + (bbHeight * 0.5D), vec3.z, 0, vec33.x, vec33.y, vec33.z, (deltaLength * 2D) + 0.1D);
			}
		}
	}

	@Override
	public boolean hurt(@NotNull DamageSource source, float amount) {
		if (super.hurt(source, amount)) {
			if (!this.level().isClientSide && this.level().getDifficulty() != Difficulty.PEACEFUL && !this.isNoAi() && this.brain.getMemory(MemoryModuleType.ATTACK_TARGET).isEmpty()) {
				LivingEntity target = this.getLastHurtByMob();
				if (this.canTargetEntity(target)) {
					this.setAttackTarget(target);
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public ResourceKey<LootTable> getDefaultLootTable() {
		ResourceLocation resourceLocation = BuiltInRegistries.ENTITY_TYPE.getKey(RegisterEntities.JELLYFISH);
		return ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.fromNamespaceAndPath(this.getVariant().key().getNamespace(), "entities/" + resourceLocation.getPath() + "_" + this.getVariant().key().getPath()));
	}

	@Override
	@NotNull
	public ItemStack getBucketItemStack() {
		return new ItemStack(RegisterItems.JELLYFISH_BUCKET);
	}

	@NotNull
	public JellyfishVariant getVariant() {
		return WilderRegistry.JELLYFISH_VARIANT.getOptional(ResourceLocation.parse(this.entityData.get(VARIANT))).orElse(JellyfishVariant.PINK);
	}

	public void setVariant(@NotNull JellyfishVariant variant) {
		this.entityData.set(VARIANT, variant.key().toString());
	}

	public boolean canReproduce() {
		return this.entityData.get(CAN_REPRODUCE);
	}

	public void setCanReproduce(boolean bl) {
		this.entityData.set(CAN_REPRODUCE, bl);
	}

	public boolean isRGB() {
		var name = this.getName().getString();
		return this.hasCustomName() && name.equals("AroundTheWorld");
	}

	public void ageUp(int amount, boolean forced) {
		int i;
		int j = i = this.getAge();
		if ((i += amount * 20) > 0) {
			i = 0;
		}
		int k = i - j;
		this.setAge(i);
		if (forced) {
			this.forcedAge += k;
			if (this.forcedAgeTimer == 0) {
				this.forcedAgeTimer = 40;
			}
		}
		if (this.getAge() == 0) {
			this.setAge(this.forcedAge);
		}
	}

	public void ageUp(int amount) {
		this.ageUp(amount, false);
	}

	public int getAge() {
		if (this.level().isClientSide) {
			return this.entityData.get(IS_BABY) ? -1 : 1;
		}
		return this.age;
	}

	public void setAge(int age) {
		int i = this.getAge();
		this.age = age;
		if (i < 0 && age >= 0 || i >= 0 && age < 0) {
			this.entityData.set(IS_BABY, age < 0);
			this.ageBoundaryReached();
		}
	}

	public void ageBoundaryReached() {
		if (!this.isBaby() && this.isPassenger() && this.getVehicle() instanceof Boat boat && !boat.hasEnoughSpaceFor(this)) {
			this.stopRiding();
		}
	}

	@Override
	public boolean isBaby() {
		return this.getAge() < 0;
	}

	@Override
	public void setBaby(boolean baby) {
		this.setAge(baby ? -24000 : 0);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(VARIANT, JellyfishVariant.PINK.key().toString());
		builder.define(CAN_REPRODUCE, false);
		builder.define(IS_BABY, false);
	}

	@Override
	public void saveToBucketTag(@NotNull ItemStack stack) {
		Bucketable.saveDefaultDataToBucketTag(this, stack);
		CustomData.update(DataComponents.BUCKET_ENTITY_DATA, stack, compoundTag -> {
			compoundTag.putString("variant", Objects.requireNonNull(WilderRegistry.JELLYFISH_VARIANT.getKey(this.getVariant())).toString());
			compoundTag.putBoolean("canReproduce", this.canReproduce());
			compoundTag.putInt("fullness", this.fullness);
			compoundTag.putInt("reproductionCooldown", this.reproductionCooldown);
			compoundTag.putInt("age", this.getAge());
			compoundTag.putInt("forcedAge", this.forcedAge);
			compoundTag.putBoolean("isBaby", this.isBaby());
		});
	}

	@Override
	public void loadFromBucketTag(@NotNull CompoundTag tag) {
		Bucketable.loadDefaultDataFromBucketTag(this, tag);
		if (tag.contains("variant")) {
			JellyfishVariant variant = WilderRegistry.JELLYFISH_VARIANT.get(ResourceLocation.tryParse(tag.getString("variant")));
			if (variant != null) {
				this.setVariant(variant);
			}
		}
		if (tag.contains("canReproduce")) {
			this.setCanReproduce(tag.getBoolean("canReproduce"));
		}
		this.fullness = tag.getInt("fullness");
		this.reproductionCooldown = tag.getInt("reproductionCooldown");
		this.setAge(tag.getInt("age"));
		this.forcedAge = tag.getInt("forcedAge");
		this.setBaby(tag.getBoolean("isBaby"));
	}

	@Override
	public void addAdditionalSaveData(@NotNull CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putString("variant", Objects.requireNonNull(WilderRegistry.JELLYFISH_VARIANT.getKey(this.getVariant())).toString());
		compound.putInt("ticksSinceSpawn", this.ticksSinceSpawn);
		compound.putBoolean("canReproduce", this.canReproduce());
		compound.putInt("fullness", this.fullness);
		compound.putInt("reproductionCooldown", this.reproductionCooldown);
		compound.putInt("age", this.getAge());
		compound.putInt("forcedAge", this.forcedAge);
		compound.putBoolean("isBaby", this.isBaby());
	}

	@Override
	public void readAdditionalSaveData(@NotNull CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		JellyfishVariant variant = WilderRegistry.JELLYFISH_VARIANT.get(ResourceLocation.tryParse(compound.getString("variant")));
		if (variant != null) {
			this.setVariant(variant);
		}
		this.ticksSinceSpawn = compound.getInt("ticksSinceSpawn");
		if (compound.contains("canReproduce")) {
			this.setCanReproduce(compound.getBoolean("canReproduce"));
		}
		this.fullness = compound.getInt("fullness");
		this.reproductionCooldown = compound.getInt("reproductionCooldown");
		this.setAge(compound.getInt("age"));
		this.forcedAge = compound.getInt("forcedAge");
		this.setBaby(compound.getBoolean("isBaby"));
	}

	public static class JellyfishGroupData implements SpawnGroupData {
		private final boolean shouldSpawnBaby;
		private final float babySpawnChance;
		private final JellyfishVariant variant;
		private int groupSize;

		private JellyfishGroupData(boolean shouldSpawnBaby, float babySpawnChance, JellyfishVariant variant) {
			this.shouldSpawnBaby = shouldSpawnBaby;
			this.babySpawnChance = babySpawnChance;
			this.variant = variant;
		}

		public JellyfishGroupData(boolean shouldSpawnBaby, JellyfishVariant variant) {
			this(shouldSpawnBaby, 0.15F, variant);
		}

		public JellyfishGroupData(float babySpawnChance, JellyfishVariant variant) {
			this(true, babySpawnChance, variant);
		}

		public int getGroupSize() {
			return this.groupSize;
		}

		public void increaseGroupSizeByOne() {
			++this.groupSize;
		}

		public boolean isShouldSpawnBaby() {
			return this.shouldSpawnBaby;
		}

		public float getBabySpawnChance() {
			return this.babySpawnChance;
		}
	}
}
