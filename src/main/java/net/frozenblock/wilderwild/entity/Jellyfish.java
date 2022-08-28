package net.frozenblock.wilderwild.entity;

import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.frozenblock.wilderwild.registry.RegisterWorldgen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Jellyfish extends AbstractFish {
    public float xBodyRot;
    public float xRot1;
    public float xRot2;
    public float xRot3;
    public float xRot4;
    public float xRot5;
    public float xRot6;
    public float xRot7;
    public float xRot8;
    public float xRot9;
    public float xRot10;
    public float zBodyRot;
    public float zRot1;
    public float zRot2;
    public float zRot3;
    public float zRot4;
    public float zRot5;
    public float zRot6;
    public float zRot7;
    public float zRot8;
    public float zRot9;
    public float zRot10;
    public float tentacleMovement;
    public float oldTentacleMovement;
    public float tentacleAngle;
    public float oldTentacleAngle;
    private float speed;
    private float tentacleSpeed;
    private float rotateSpeed;

    public Jellyfish(EntityType<? extends Jellyfish> entityType, Level level) {
        super(entityType, level);
        //this.moveControl = new SmoothSwimmingMoveControl(this, 85, 10, 0.02f, 0.1f, true);
        this.lookControl = new SmoothSwimmingLookControl(this, 10);
    }

    @Override
    public void playerTouch(@NotNull Player player) {
        if (player instanceof ServerPlayer && player.hurt(DamageSource.mobAttack(this), 3)) {
            if (!this.isSilent()) {
                ((ServerPlayer) player).connection.send(new ClientboundGameEventPacket(ClientboundGameEventPacket.PUFFER_FISH_STING, 0.0F));
            }

            player.addEffect(new MobEffectInstance(MobEffects.POISON, 300, 0, false, false), this);
        }

    }

    public static boolean canSpawn(EntityType<Jellyfish> type, ServerLevelAccessor world, MobSpawnType spawnReason, BlockPos pos, RandomSource random) {
        if (world.getBiome(pos).is(RegisterWorldgen.JELLYFISH_CAVES)) {
            return pos.getY() <= world.getSeaLevel() - 33
                    && world.getRawBrightness(pos, 0) <= 6
                    && world.getBlockState(pos).is(Blocks.WATER);
        }
        return false;
    }

    public static AttributeSupplier.Builder addAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 3.0D);
    }

    @Override
    protected float getStandingEyeHeight(@NotNull Pose pose, EntityDimensions entityDimensions) {
        return entityDimensions.height * 0.5f;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return RegisterSounds.ENTITY_JELLYFISH_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
        return RegisterSounds.ENTITY_JELLYFISH_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return RegisterSounds.ENTITY_JELLYFISH_HURT;
    }

    protected SoundEvent getSquirtSound() {
        return RegisterSounds.ENTITY_WARDEN_SWIM;
    }

    @Override
    protected SoundEvent getSwimSound() {
        return RegisterSounds.ENTITY_JELLYFISH_SWIM;
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
    public void aiStep() {
        super.aiStep();
        this.xRot10 = this.xRot9;
        this.xRot9 = this.xRot8;
        this.xRot8 = this.xRot7;
        this.xRot7 = this.xRot6;
        this.xRot6 = this.xRot5;
        this.xRot5 = this.xRot4;
        this.xRot4 = this.xRot3;
        this.xRot3 = this.xRot2;
        this.xRot2 = this.xRot1;
        this.xRot1 = this.xBodyRot;

        this.zRot10 = this.zRot9;
        this.zRot9 = this.zRot8;
        this.zRot8 = this.zRot7;
        this.zRot7 = this.zRot6;
        this.zRot6 = this.zRot5;
        this.zRot5 = this.zRot4;
        this.zRot4 = this.zRot3;
        this.zRot3 = this.zRot2;
        this.zRot2 = this.zRot1;
        this.zRot1 = this.zBodyRot;

        this.oldTentacleMovement = this.tentacleMovement;
        this.oldTentacleAngle = this.tentacleAngle;
        this.tentacleMovement += this.tentacleSpeed;
        if (this.isInWaterOrBubble()) {
            if (this.tentacleMovement < (float) Math.PI) {
                float f = this.tentacleMovement / (float) Math.PI;
                this.tentacleAngle = Mth.sin(f * f * (float) Math.PI) * (float) Math.PI * 0.25f;
                if ((double) f > 0.75) {
                    this.speed = 1.0f;
                    this.rotateSpeed = 1.0f;
                } else {
                    this.rotateSpeed *= 0.8f;
                }
            } else {
                this.tentacleAngle = 0.0f;
                this.speed *= 0.9f;
                this.rotateSpeed *= 0.99f;
            }
            Vec3 vec3 = this.getDeltaMovement();
            double d = vec3.horizontalDistance();
            this.yBodyRot += (-((float) Mth.atan2(vec3.x, vec3.z)) * 57.295776f - this.yBodyRot) * 0.1f;
            this.setYRot(this.yBodyRot);
            this.zBodyRot += (float) Math.PI * this.rotateSpeed * 1.5f;
            this.xBodyRot += (-((float) Mth.atan2(d, vec3.y)) * 57.295776f - this.xBodyRot) * 0.1f;
        } else {
            this.tentacleAngle = Mth.abs(Mth.sin(this.tentacleMovement)) * (float) Math.PI * 0.25f;
            this.xBodyRot += (-90.0f - this.xBodyRot) * 0.02f;
        }
    }

    @Override
    protected SoundEvent getFlopSound() {
        return SoundEvents.PUFFER_FISH_FLOP;
    }

    @Override
    public boolean hurt(@NotNull DamageSource damageSource, float f) {
        if (super.hurt(damageSource, f) && this.getLastHurtByMob() != null) {
            if (!this.level.isClientSide) {
                this.spawnJelly();
                this.getNavigation().moveTo(this.getLastHurtByMob(), 2);
            }
            return true;
        }
        return false;
    }

    private Vec3 rotateVector(Vec3 vec3) {
        Vec3 vec32 = vec3.xRot(this.zRot1 * ((float) Math.PI / 180));
        vec32 = vec32.yRot(-this.yBodyRotO * ((float) Math.PI / 180));
        return vec32;
    }

    private void spawnJelly() {
        this.playSound(this.getSquirtSound(), this.getSoundVolume(), this.getVoicePitch());
        Vec3 vec3 = this.rotateVector(new Vec3(0.0, -1.0, 0.0)).add(this.getX(), this.getY(), this.getZ());
        for (int i = 0; i < 30; ++i) {
            Vec3 vec32 = this.rotateVector(new Vec3((double) this.random.nextFloat() * 0.6 - 0.3, -1.0, (double) this.random.nextFloat() * 0.6 - 0.3));
            Vec3 vec33 = vec32.scale(0.3 + (double) (this.random.nextFloat() * 2.0f));
            ((ServerLevel) this.level).sendParticles(this.getJellyParticle(), vec3.x, vec3.y + 0.5, vec3.z, 0, vec33.x, vec33.y, vec33.z, 0.1f);
        }
    }

    protected ParticleOptions getJellyParticle() {
        return ParticleTypes.BUBBLE;
    }

    @Override
    public ItemStack getBucketItemStack() {
        return null;
    }

}
