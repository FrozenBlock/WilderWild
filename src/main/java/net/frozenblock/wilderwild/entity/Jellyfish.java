package net.frozenblock.wilderwild.entity;

import com.mojang.serialization.Dynamic;
import net.frozenblock.wilderwild.entity.ai.JellyfishAi;
import net.frozenblock.wilderwild.entity.ai.JellyfishPanicGoal;
import net.frozenblock.wilderwild.misc.server.EasyPacket;
import net.frozenblock.wilderwild.registry.RegisterItems;
import net.frozenblock.wilderwild.registry.RegisterMobEffects;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.frozenblock.wilderwild.tag.WilderBiomeTags;
import net.frozenblock.wilderwild.tag.WilderEntityTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class Jellyfish extends AbstractFish {
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

    public int ticksSinceCantReach;
    public int crazyTicks;

    public Jellyfish(EntityType<? extends Jellyfish> entityType, Level level) {
        super(entityType, level);
        this.setVariant("pink");
    }

    private static final EntityDataAccessor<String> VARIANT = SynchedEntityData.defineId(Jellyfish.class, EntityDataSerializers.STRING);

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
        Holder<Biome> holder = serverLevelAccessor.getBiome(this.blockPosition());
        ArrayList<TagKey<Biome>> possibleTags = new ArrayList<>();
        TagKey<Biome> biomeTag;
        if (holder.is(WilderBiomeTags.PINK_JELLYFISH)) {
            possibleTags.add(WilderBiomeTags.PINK_JELLYFISH);
        }
        if (holder.is(WilderBiomeTags.BLUE_JELLYFISH)) {
            possibleTags.add(WilderBiomeTags.BLUE_JELLYFISH);
        }
        if (holder.is(WilderBiomeTags.LIME_JELLYFISH)) {
            possibleTags.add(WilderBiomeTags.LIME_JELLYFISH);
        }
        if (holder.is(WilderBiomeTags.RED_JELLYFISH)) {
            possibleTags.add(WilderBiomeTags.RED_JELLYFISH);
        }
        if (holder.is(WilderBiomeTags.YELLOW_JELLYFISH)) {
            possibleTags.add(WilderBiomeTags.YELLOW_JELLYFISH);
        }
        if (holder.is(WilderBiomeTags.PEARLESCENT_JELLYFISH)) {
            possibleTags.add(WilderBiomeTags.PEARLESCENT_JELLYFISH);
        }

        if (!possibleTags.isEmpty()) {
            biomeTag = possibleTags.get((int) (Math.random() * possibleTags.size()));
            if (biomeTag == WilderBiomeTags.PEARLESCENT_JELLYFISH) {
                this.setVariant("pearlescent");
            } else if (biomeTag == WilderBiomeTags.RED_JELLYFISH) {
                this.setVariant("red");
            } else if (biomeTag == WilderBiomeTags.YELLOW_JELLYFISH) {
                this.setVariant("yellow");
            } else if (biomeTag == WilderBiomeTags.BLUE_JELLYFISH) {
                this.setVariant("blue");
            } else if (biomeTag == WilderBiomeTags.LIME_JELLYFISH) {
                this.setVariant("lime");
            } else {
                this.setVariant("pink");
            }
        } else {
            this.setVariant("pink");
        }
        return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
    }

    public static boolean canSpawn(EntityType<Jellyfish> type, ServerLevelAccessor world, MobSpawnType spawnReason, BlockPos pos, RandomSource random) {
        ArrayList<TagKey<Biome>> possibleTags = new ArrayList<>();
        TagKey<Biome> biomeTag;
        Holder<Biome> holder = world.getBiome(pos);
        if (holder.is(WilderBiomeTags.PINK_JELLYFISH)) {
            possibleTags.add(WilderBiomeTags.PINK_JELLYFISH);
        }
        if (holder.is(WilderBiomeTags.BLUE_JELLYFISH)) {
            possibleTags.add(WilderBiomeTags.BLUE_JELLYFISH);
        }
        if (holder.is(WilderBiomeTags.LIME_JELLYFISH)) {
            possibleTags.add(WilderBiomeTags.LIME_JELLYFISH);
        }
        if (holder.is(WilderBiomeTags.RED_JELLYFISH)) {
            possibleTags.add(WilderBiomeTags.RED_JELLYFISH);
        }
        if (holder.is(WilderBiomeTags.YELLOW_JELLYFISH)) {
            possibleTags.add(WilderBiomeTags.YELLOW_JELLYFISH);
        }
        if (holder.is(WilderBiomeTags.PEARLESCENT_JELLYFISH)) {
            possibleTags.add(WilderBiomeTags.PEARLESCENT_JELLYFISH);
        }

        if (!possibleTags.isEmpty()) {
            boolean normalSpawn = true;
            if (possibleTags.size() > 1 && possibleTags.contains(WilderBiomeTags.PEARLESCENT_JELLYFISH)) {
                normalSpawn = random.nextBoolean();
            } else if (possibleTags.contains(WilderBiomeTags.PEARLESCENT_JELLYFISH)) {
                normalSpawn = false;
            }
            if (normalSpawn) {
                return spawnReason != MobSpawnType.SPAWNER ? random.nextInt(1, 4) == 3 && pos.getY() <= world.getSeaLevel() - 3 && pos.getY() >= world.getSeaLevel() - 26 && world.getBlockState(pos).is(Blocks.WATER)
                        : world.getBlockState(pos).is(Blocks.WATER);
            }
        }

        return spawnReason != MobSpawnType.SPAWNER ? pos.getY() <= world.getSeaLevel() - 33 && world.getRawBrightness(pos, 0) <= 6 && world.getBlockState(pos).is(Blocks.WATER)
                : world.getBlockState(pos).is(Blocks.WATER);
    }

    public void saveToBucketTag(ItemStack itemStack) {
        Bucketable.saveDefaultDataToBucketTag(this, itemStack);
        CompoundTag compoundTag = itemStack.getOrCreateTag();
        compoundTag.putString("variant", this.getVariant());
    }

    public void loadFromBucketTag(CompoundTag compoundTag) {
        Bucketable.loadDefaultDataFromBucketTag(this, compoundTag);
        if (compoundTag.contains("variant")) {
            this.setVariant(compoundTag.getString("variant"));
        }

    }

    public static AttributeSupplier.Builder addAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 3.0D).add(Attributes.MOVEMENT_SPEED, 0.5f);
    }

    @Override
    protected float getStandingEyeHeight(@NotNull Pose pose, EntityDimensions entityDimensions) {
        return entityDimensions.height * 0.5f;
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
    protected Brain<?> makeBrain(Dynamic<?> dynamic) {
        return JellyfishAi.create(this, dynamic);
    }

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

    public void setAttackTarget(LivingEntity livingEntity) {
        this.getBrain().setMemory(MemoryModuleType.ATTACK_TARGET, livingEntity);
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
            this.rotateSpeed *= 0.8f;
            Vec3 vec3 = this.getDeltaMovement();
            this.yBodyRot += (-(Mth.atan2(vec3.x, vec3.z)) * 57.295776f - this.yBodyRot) * 0.1f;
            this.setYRot(this.yBodyRot);
            this.zBodyRot += (float) Math.PI * this.rotateSpeed * 1.5f;
            this.xBodyRot += (-(Mth.atan2(vec3.horizontalDistance(), vec3.y)) * 57.295776f - this.xBodyRot) * 0.1f;
        } else {
            this.xBodyRot += (-90.0f - this.xBodyRot) * 0.02f;
        }

        if (inWater) {
            this.heal(0.02F);
        }

        this.stingEntities();

        LivingEntity target = this.getTarget();
        if (target != null) {
            ++this.ticksSinceCantReach;
            boolean creative = target instanceof Player player && player.isCreative();
            if (this.ticksSinceCantReach > 300 || target.isDeadOrDying() || target.isRemoved() || target.distanceTo(this) > 20 || this.level.getDifficulty() == Difficulty.PEACEFUL || target.isSpectator() || creative) {
                this.getBrain().eraseMemory(MemoryModuleType.ATTACK_TARGET);
                this.ticksSinceCantReach = 0;
            } else {
                this.getNavigation().stop();
                this.getNavigation().moveTo(this.getTarget(), 2);
                if (target.distanceTo(this) < 5) {
                    this.ticksSinceCantReach = Math.max(this.ticksSinceCantReach - 2, 0);
                }
            }
        } else {
            this.ticksSinceCantReach = 0;
        }
    }

    public void stingEntities() {
        if (this.isAlive()) {
            List<LivingEntity> list = this.level.getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(0.08));
            for (LivingEntity entity : list) {
                if (targetingConditions.test(this, entity)) {
                    if (entity instanceof ServerPlayer player) {
                        if (player.hurt(DamageSource.mobAttack(this), 3)) {
                            player.addEffect(new MobEffectInstance(RegisterMobEffects.VENOMOUS_STING, 200, 0, false, false), this);
                            EasyPacket.sendJellySting(player);
                        }
                    } else if (entity instanceof Mob mob) {
                        if (mob.hurt(DamageSource.mobAttack(this), (float) (3))) {
                            mob.addEffect(new MobEffectInstance(RegisterMobEffects.VENOMOUS_STING, 200, 0), this);
                            this.playSound(RegisterSounds.ENTITY_JELLYFISH_STING, 0.4F, this.random.nextFloat() * 0.2f + 0.9f);
                        }
                    }
                }
            }
        }
    }

    private static final Predicate<LivingEntity> CAN_TARGET = (livingEntity) -> {
        if ((livingEntity instanceof Player && ((Player) livingEntity).isCreative()) || livingEntity.isDeadOrDying()) {
            return false;
        } else {
            return !livingEntity.getType().is(WilderEntityTags.JELLYFISH_CANT_STING);
        }
    };

    public static final TargetingConditions targetingConditions = TargetingConditions.forNonCombat().ignoreInvisibilityTesting().ignoreLineOfSight().selector(CAN_TARGET);

    @Override
    protected void customServerAiStep() {
        ServerLevel serverLevel = (ServerLevel) this.level;
        serverLevel.getProfiler().push("jellyfishBrain");
        this.getBrain().tick(serverLevel, this);
        this.level.getProfiler().pop();
        super.customServerAiStep();
    }

    @Override
    protected SoundEvent getFlopSound() {
        return RegisterSounds.ENTITY_JELLYFISH_FLOP;
    }

    @Override
    public SoundEvent getPickupSound() {
        return RegisterSounds.ITEM_BUCKET_FILL_JELLYFISH;
    }

    @Override
    public boolean hurt(@NotNull DamageSource damageSource, float f) {
        if (super.hurt(damageSource, f)) {
            if (!this.level.isClientSide && this.level.getDifficulty() != Difficulty.PEACEFUL) {
                LivingEntity target = this.getLastHurtByMob();
                if (target instanceof Player player) {
                    if (!player.isCreative() && !player.isSpectator()) {
                        this.setAttackTarget(target);
                    }
                } else {
                    this.setAttackTarget(target);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand interactionHand) {
        ItemStack itemStack = player.getItemInHand(interactionHand);
        if (itemStack.is(Items.FEATHER) && !this.isBaby()) {
            if (this.level.random.nextInt(0, 30) == 14) {
                this.crazyTicks = this.level.random.nextInt(60, 160);
                this.spawnJelly();
            }
            return InteractionResult.sidedSuccess(this.level.isClientSide);
        }
        return super.mobInteract(player, interactionHand);
    }

    private void spawnJelly() {
        //TODO: JELLY JELLY SOUNDS
        this.playSound(RegisterSounds.ENTITY_JELLYFISH_FLOP, this.getSoundVolume(), this.getVoicePitch());
        Vec3 vec3 = this.rotateVector(new Vec3(0.0, -1.0, 0.0)).add(this.getX(), this.getY(), this.getZ());
        for (int i = 0; i < 30; ++i) {
            Vec3 vec32 = this.rotateVector(new Vec3((double)this.random.nextFloat() * 0.6 - 0.3, -1.0, (double)this.random.nextFloat() * 0.6 - 0.3));
            Vec3 vec33 = vec32.scale(0.3 + (double)(this.random.nextFloat() * 2.0f));
            //((ServerLevel)this.level).sendParticles(this.getInkParticle(), vec3.x, vec3.y + 0.5, vec3.z, 0, vec33.x, vec33.y, vec33.z, 0.1f);
            if (i == 1) {
                if (!this.level.isClientSide) {
                    JellyCloud cloud = new JellyCloud(this.level, vec3.x + vec33.x, vec3.y + 0.5 + vec33.y, vec3.z+ vec33.z);
                    this.level.addFreshEntity(cloud);
                }
            }
        }
    }

    private Vec3 rotateVector(Vec3 vec3) {
        Vec3 vec32 = vec3.xRot(this.xRot1 * ((float)Math.PI / 180));
        vec32 = vec32.yRot(-this.yBodyRotO * ((float)Math.PI / 180));
        return vec32;
    }

    @Override
    public ItemStack getBucketItemStack() {
        return new ItemStack(RegisterItems.JELLYFISH_BUCKET);
    }

    public void setVariant(String s) {
        this.entityData.set(VARIANT, s);
    }

    public String getVariant() {
        String variant = this.entityData.get(VARIANT);
        return !Objects.equals(variant, "") ? variant : "pink";
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(VARIANT, "pink");
    }

    public void addAdditionalSaveData(@NotNull CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putInt("ticksSinceCantReach", this.ticksSinceCantReach);
        nbt.putInt("crazyTicks", this.crazyTicks);
        nbt.putString("variant", this.getVariant());
    }

    public void readAdditionalSaveData(@NotNull CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        this.ticksSinceCantReach = nbt.getInt("ticksSinceCantReach");
        this.crazyTicks = nbt.getInt("crazyTicks");
        this.setVariant(nbt.getString("variant"));
    }

    @Override
    protected void registerGoals() {
        //super.registerGoals();
        this.goalSelector.addGoal(0, new JellyfishPanicGoal(this, 1.9));
        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, Player.class, 2f, 1.0, 1.4, EntitySelector.NO_SPECTATORS::test));
        this.goalSelector.addGoal(4, new JellySwimGoal(this));
    }

    static class JellySwimGoal
            extends RandomSwimmingGoal {
        private final Jellyfish jelly;

        public JellySwimGoal(Jellyfish jelly) {
            super(jelly, 1.0, 40);
            this.jelly = jelly;
        }

        @Override
        public boolean canUse() {
            return this.jelly.getTarget() == null && this.jelly.canRandomSwim() && super.canUse();
        }

        @Override
        public boolean canContinueToUse() {
            return this.jelly.getTarget() == null && !this.mob.getNavigation().isDone() && !this.mob.isVehicle();
        }
    }

}
