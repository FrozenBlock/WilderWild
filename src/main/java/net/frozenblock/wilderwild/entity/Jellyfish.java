package net.frozenblock.wilderwild.entity;

import com.mojang.serialization.Dynamic;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import net.frozenblock.lib.entities.NoFlopAbstractFish;
import net.frozenblock.wilderwild.entity.ai.JellyfishAi;
import net.frozenblock.wilderwild.misc.JellyfishVariant;
import net.frozenblock.wilderwild.misc.server.EasyPacket;
import net.frozenblock.wilderwild.registry.RegisterEntities;
import net.frozenblock.wilderwild.registry.RegisterItems;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.frozenblock.wilderwild.registry.WilderRegistry;
import net.frozenblock.wilderwild.tag.WilderBiomeTags;
import net.frozenblock.wilderwild.tag.WilderEntityTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.behavior.StartAttacking;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Jellyfish extends NoFlopAbstractFish {
	//Animations
    public float xBodyRot;
    public float xRot1;
    public float xRot2;
    public float xRot3;
    public float xRot4;
    public float xRot5;
    public float xRot6;
    public float zBodyRot;
    public float zRot1;
    public float zRot2;
    public float zRot3;
    public float zRot4;
    public float zRot5;
    public float zRot6;
    private float rotateSpeed;

	private static final float MAX_TARGET_DISTANCE = 20F;

	private static final EntityDataAccessor<JellyfishVariant> VARIANT = SynchedEntityData.defineId(Jellyfish.class, JellyfishVariant.SERIALIZER);

	public static final ArrayList<JellyfishVariant> COLORED_VARIANTS = new ArrayList<>(WilderRegistry.JELLYFISH_VARIANT.stream()
			.filter(JellyfishVariant::isNormal)
			.collect(Collectors.toList())
	);

	public static final ArrayList<JellyfishVariant> PEARLESCENT_VARIANTS = new ArrayList<>(WilderRegistry.JELLYFISH_VARIANT.stream()
			.filter(JellyfishVariant::isPearlescent)
			.collect(Collectors.toList())
	);

    public Jellyfish(EntityType<? extends Jellyfish> entityType, Level level) {
        super(entityType, level);
		this.getNavigation().setCanFloat(false);
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, @NotNull DifficultyInstance difficulty, @NotNull MobSpawnType reason, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag dataTag) {
        Holder<Biome> biome = level.getBiome(this.blockPosition());
		this.setVariant(JellyfishVariant.PINK);
        if (biome.is(WilderBiomeTags.PEARLESCENT_JELLYFISH)) {
            this.setVariant(PEARLESCENT_VARIANTS.get((int) (Math.random() * PEARLESCENT_VARIANTS.size())));
        } else {
            this.setVariant(COLORED_VARIANTS.get((int) (Math.random() * COLORED_VARIANTS.size())));
        }
        return super.finalizeSpawn(level, difficulty, reason, spawnData, dataTag);
    }

    public static boolean canSpawn(EntityType<Jellyfish> type, ServerLevelAccessor level, MobSpawnType reason, BlockPos pos, RandomSource random) {
        Holder<Biome> biome = level.getBiome(pos);

        if (biome.is(WilderBiomeTags.PEARLESCENT_JELLYFISH)) {
            if (reason == MobSpawnType.SPAWNER || level.getRawBrightness(pos, 0) <= 7 && random.nextInt(0, level.getRawBrightness(pos, 0) + 3) >= 1) {
                return true;
            }
        }
        return reason == MobSpawnType.SPAWNER || random.nextInt(1, 10) == 3 && pos.getY() <= level.getSeaLevel() - 3 && pos.getY() >= level.getSeaLevel() - 26;
    }

    @Override
    public void saveToBucketTag(@NotNull ItemStack stack) {
        Bucketable.saveDefaultDataToBucketTag(this, stack);
        CompoundTag compoundTag = stack.getOrCreateTag();
        compoundTag.putString("variant", Objects.requireNonNull(WilderRegistry.JELLYFISH_VARIANT.getKey(this.getVariant())).toString());
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
    }

    public static AttributeSupplier.Builder addAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 8.0D).add(Attributes.MOVEMENT_SPEED, 0.5F).add(Attributes.FOLLOW_RANGE, 32F);
    }

    @Override
    protected float getStandingEyeHeight(@NotNull Pose pose, EntityDimensions dimensions) {
        return dimensions.height * 0.5F;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return this.isInWaterOrBubble() ? RegisterSounds.ENTITY_JELLYFISH_AMBIENT_WATER : RegisterSounds.ENTITY_JELLYFISH_AMBIENT;
    }

    @Override
    protected SoundEvent getSwimSound() {
        return RegisterSounds.ENTITY_JELLYFISH_SWIM;
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
    public boolean canBeLeashed(@NotNull Player player) {
        return !this.isLeashed();
    }

    @Override
    protected float getSoundVolume() {
        return 0.4F;
    }

	@Override
	public Brain.Provider<Jellyfish> brainProvider() {
		return Brain.provider(JellyfishAi.MEMORY_TYPES, JellyfishAi.SENSOR_TYPES);
	}

    @Override
    protected Brain<Jellyfish> makeBrain(@NotNull Dynamic<?> dynamic) {
		return JellyfishAi.makeBrain(this, this.brainProvider().makeBrain(dynamic));
    }

    @Override
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
        super.aiStep();
        this.xRot6 = this.xRot5;
        this.xRot5 = this.xRot4;
        this.xRot4 = this.xRot3;
        this.xRot3 = this.xRot2;
        this.xRot2 = this.xRot1;
        this.xRot1 = this.xBodyRot;

        this.zRot6 = this.zRot5;
        this.zRot5 = this.zRot4;
        this.zRot4 = this.zRot3;
        this.zRot3 = this.zRot2;
        this.zRot2 = this.zRot1;
        this.zRot1 = this.zBodyRot;

        boolean inWater = this.isInWaterOrBubble();
        if (inWater) {
            this.rotateSpeed *= 0.8F;
            Vec3 vec3 = this.getDeltaMovement();
            this.yBodyRot += (-(Mth.atan2(vec3.x, vec3.z)) * 57.295776F - this.yBodyRot) * 0.1F;
            this.setYRot(this.yBodyRot);
            this.zBodyRot += (float) Math.PI * this.rotateSpeed * 1.5F;
            this.xBodyRot += (-(Mth.atan2(vec3.horizontalDistance(), vec3.y)) * 57.295776F - this.xBodyRot) * 0.1F;
        } else {
            this.xBodyRot += (-90.0F - this.xBodyRot) * 0.02F;
        }

        if (inWater) {
            this.heal(0.02F);
        }

        this.stingEntities();

        LivingEntity target = this.getTarget();
        if (target != null) {
			this.getNavigation().stop();
			this.moveToAccurate(this.getTarget(), 2);
		}

    }

	@Override
	public boolean doHurtTarget(Entity target) {
		this.level.broadcastEntityEvent(this, (byte) 4);
		return super.doHurtTarget(target);
	}

    public void stingEntities() {
        if (this.isAlive()) {
            List<LivingEntity> list = this.level.getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(0.08));
            for (LivingEntity entity : list) {
                if (this.targetingConditions.test(this, entity)) {
                    if (entity instanceof ServerPlayer player) {
                        if (player.hurt(DamageSource.mobAttack(this), 3)) {
                            player.addEffect(new MobEffectInstance(MobEffects.POISON, 200, 0, false, false), this);
                            EasyPacket.sendJellySting(player);
                        }
                    } else if (entity instanceof Mob mob) {
                        if (mob.hurt(DamageSource.mobAttack(this), (float) (3))) {
                            mob.addEffect(new MobEffectInstance(MobEffects.POISON, 200, 0), this);
                            this.playSound(RegisterSounds.ENTITY_JELLYFISH_STING, 0.4F, this.random.nextFloat() * 0.2F + 0.9F);
                        }
                    }
                }
            }
        }
    }

	public boolean moveToAccurate(@NotNull Entity entity, double speed) {
		Path path = this.getNavigation().createPath(entity, 0);
		return path != null && this.getNavigation().moveTo(path, speed);
	}

    @Contract("null->false")
    public boolean canTargetEntity(@Nullable Entity entity) {
        return entity instanceof LivingEntity livingEntity
                && this.level == entity.level
                && EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(entity)
                && !this.isAlliedTo(entity)
                && livingEntity.getType() != EntityType.ARMOR_STAND
                && livingEntity.getType() != RegisterEntities.JELLYFISH
                && !livingEntity.isInvulnerable()
                && !livingEntity.isDeadOrDying()
				&& !livingEntity.isRemoved()
				&& livingEntity.distanceTo(this) < MAX_TARGET_DISTANCE
                && !livingEntity.getType().is(WilderEntityTags.JELLYFISH_CANT_STING)
                && this.level.getWorldBorder().isWithinBounds(livingEntity.getBoundingBox());
    }

    public final TargetingConditions targetingConditions = TargetingConditions.forNonCombat().ignoreInvisibilityTesting().ignoreLineOfSight().selector(this::canTargetEntity);

    @Override
    protected void customServerAiStep() {
        ServerLevel serverLevel = (ServerLevel) this.level;
        serverLevel.getProfiler().push("jellyfishBrain");
        this.getBrain().tick(serverLevel, this);
        this.level.getProfiler().pop();
        this.level.getProfiler().push("jellyfishActivityUpdate");
        JellyfishAi.updateActivity(this);
        this.level.getProfiler().pop();
        super.customServerAiStep();
    }

    /*@Override
    protected SoundEvent getFlopSound() {
        return RegisterSounds.ENTITY_JELLYFISH_FLOP;
    }*/

    @Override
    public SoundEvent getPickupSound() {
        return RegisterSounds.ITEM_BUCKET_FILL_JELLYFISH;
    }

    @Override
    public boolean hurt(@NotNull DamageSource source, float amount) {
        if (super.hurt(source, amount)) {
            if (!this.level.isClientSide && this.level.getDifficulty() != Difficulty.PEACEFUL && !this.isNoAi() && this.brain.getMemory(MemoryModuleType.ATTACK_TARGET).isEmpty()) {
                LivingEntity target = this.getLastHurtByMob();
				if (this.canTargetEntity(target)) {
					this.setAttackTarget(target);
				}
            }
            return true;
        }
        return false;
    }

    public ResourceLocation getJellyLootTable() {
        ResourceLocation resourceLocation = BuiltInRegistries.ENTITY_TYPE.getKey(RegisterEntities.JELLYFISH);
        return new ResourceLocation(this.getVariant().getKey().getNamespace(), "entities/" + resourceLocation.getPath() + "_" + this.getVariant().getKey().getPath());
    }

    @Override
    protected void dropFromLootTable(@NotNull DamageSource damageSource, boolean bl) {
        ResourceLocation resourceLocation = this.getJellyLootTable();
        LootTable lootTable = Objects.requireNonNull(this.level.getServer()).getLootTables().get(resourceLocation);
        LootContext.Builder builder = this.createLootContext(bl, damageSource);
        lootTable.getRandomItems(builder.create(LootContextParamSets.ENTITY), this::spawnAtLocation);
    }

    @Override
    public ItemStack getBucketItemStack() {
        return new ItemStack(RegisterItems.JELLYFISH_BUCKET);
    }

    public void setVariant(JellyfishVariant variant) {
        this.entityData.set(VARIANT, variant);
    }

    public JellyfishVariant getVariant() {
        JellyfishVariant variant = this.entityData.get(VARIANT);
        return variant != null ? variant : JellyfishVariant.PINK;
    }

    public boolean isRGB() {
        var name = this.getName().getString();
        return this.hasCustomName() && (name.equalsIgnoreCase("I_am_Merp") || name.equals("AroundTheWorld"));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(VARIANT, JellyfishVariant.PINK);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putString("variant", Objects.requireNonNull(WilderRegistry.JELLYFISH_VARIANT.getKey(this.getVariant())).toString());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        JellyfishVariant variant = WilderRegistry.JELLYFISH_VARIANT.get(ResourceLocation.tryParse(compound.getString("variant")));
        if (variant != null) {
            this.setVariant(variant);
        }
    }
}
