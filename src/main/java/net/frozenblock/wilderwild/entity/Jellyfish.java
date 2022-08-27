package net.frozenblock.wilderwild.entity;

import net.frozenblock.wilderwild.registry.RegisterWorldgen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Jellyfish extends AbstractFish {
    public float xBodyRot;
    public float xBodyRotO;
    public float zBodyRot;
    public float zBodyRotO;
    public float tentacleMovement;
    public float oldTentacleMovement;
    public float tentacleAngle;
    public float oldTentacleAngle;
    private float speed;
    private float tentacleSpeed;
    private float rotateSpeed;
    private float tx;
    private float ty;
    private float tz;

    public Jellyfish(EntityType<? extends Jellyfish> entityType, Level level) {
        super(entityType, level);
        this.random.setSeed(this.getId());
        this.tentacleSpeed = 1.0f / (this.random.nextFloat() + 1.0f) * 0.2f;
        this.moveControl = new JellyMoveControl(this);
    }

    @Override
    public void playerTouch(@NotNull Player player) {
        if (player instanceof ServerPlayer && player.hurt(DamageSource.mobAttack(this), 3)) {
            if (!this.isSilent()) {
                ((ServerPlayer)player).connection.send(new ClientboundGameEventPacket(ClientboundGameEventPacket.PUFFER_FISH_STING, 0.0F));
            }

            player.addEffect(new MobEffectInstance(MobEffects.POISON, 300, 0), this);
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
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 3.0D).add(Attributes.MOVEMENT_SPEED, 0.05F).add(Attributes.FLYING_SPEED, 0.05F).add(Attributes.FOLLOW_RANGE, 32);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new Jellyfish.JellySwimGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 10.0);
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntityDimensions entityDimensions) {
        return entityDimensions.height * 0.5f;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.SQUID_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.SQUID_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.SQUID_DEATH;
    }

    protected SoundEvent getSquirtSound() {
        return SoundEvents.SQUID_SQUIRT;
    }

    @Override
    public boolean canBeLeashed(Player player) {
        return !this.isLeashed();
    }

    @Override
    protected float getSoundVolume() {
        return 0.4F;
    }

    @Override
    protected Entity.MovementEmission getMovementEmission() {
        return Entity.MovementEmission.EVENTS;
    }

    @Override
    public void aiStep() {
        super.aiStep();
    }

    @Override
    protected SoundEvent getFlopSound() {
        return SoundEvents.PUFFER_FISH_FLOP;
    }

    @Override
    public boolean hurt(DamageSource damageSource, float f) {
        if (super.hurt(damageSource, f) && this.getLastHurtByMob() != null) {
            if (!this.level.isClientSide) {
                this.spawnJelly();
            }
            return true;
        }
        return false;
    }

    private Vec3 rotateVector(Vec3 vec3) {
        Vec3 vec32 = vec3.xRot(this.xBodyRotO * ((float)Math.PI / 180));
        vec32 = vec32.yRot(-this.yBodyRotO * ((float)Math.PI / 180));
        return vec32;
    }

    private void spawnJelly() {
        this.playSound(this.getSquirtSound(), this.getSoundVolume(), this.getVoicePitch());
        Vec3 vec3 = this.rotateVector(new Vec3(0.0, -1.0, 0.0)).add(this.getX(), this.getY(), this.getZ());
        for (int i = 0; i < 30; ++i) {
            Vec3 vec32 = this.rotateVector(new Vec3((double)this.random.nextFloat() * 0.6 - 0.3, -1.0, (double)this.random.nextFloat() * 0.6 - 0.3));
            Vec3 vec33 = vec32.scale(0.3 + (double)(this.random.nextFloat() * 2.0f));
            ((ServerLevel)this.level).sendParticles(this.getJellyParticle(), vec3.x, vec3.y + 0.5, vec3.z, 0, vec33.x, vec33.y, vec33.z, 0.1f);
        }
    }

    protected ParticleOptions getJellyParticle() {
        return ParticleTypes.BUBBLE;
    }

    @Override
    public void handleEntityEvent(byte b) {
        if (b == 19) {
            this.tentacleMovement = 0.0f;
        } else {
            super.handleEntityEvent(b);
        }
    }

    public void setMovementVector(float f, float g, float h) {
        this.tx = f;
        this.ty = g;
        this.tz = h;
    }

    public boolean hasMovementVector() {
        return this.tx != 0.0f || this.ty != 0.0f || this.tz != 0.0f;
    }

    @Override
    public ItemStack getBucketItemStack() {
        return null;
    }

    static class JellyMoveControl
            extends MoveControl {
        private final Jellyfish jelly;

        JellyMoveControl(Jellyfish jelly) {
            super(jelly);
            this.jelly = jelly;
        }

        @Override
        public void tick() {
            if (this.jelly.isEyeInFluid(FluidTags.WATER)) {
                this.jelly.setDeltaMovement(this.jelly.getDeltaMovement().add(0.0, 0.004, 0.0));
            }
            if (this.operation != MoveControl.Operation.MOVE_TO || this.jelly.getNavigation().isDone()) {
                this.jelly.setSpeed(0.0f);
                return;
            }
            float f = (float)(this.speedModifier * this.jelly.getAttributeValue(Attributes.MOVEMENT_SPEED));
            this.jelly.setSpeed(Mth.lerp(0.125f, this.jelly.getSpeed(), f));
            double d = this.wantedX - this.jelly.getX();
            double e = this.wantedY - this.jelly.getY();
            double g = this.wantedZ - this.jelly.getZ();
            if (e != 0.0) {
                double h = Math.sqrt(d * d + e * e + g * g);
                this.jelly.setDeltaMovement(this.jelly.getDeltaMovement().add(0.0, this.jelly.getSpeed() * (e / h) * 0.1, 0.0));
            }
            if (d != 0.0 || g != 0.0) {
                float i = (float)(Mth.atan2(g, d) * 57.2957763671875) - 90.0f;
                this.jelly.setYRot(this.rotlerp(this.jelly.getYRot(), i, 90.0f));
                this.jelly.yBodyRot = this.jelly.getYRot();
            }
        }
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
            return this.jelly.canRandomSwim() && super.canUse();
        }
    }
}
