package net.frozenblock.wilderwild.mixin.server;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import static net.minecraft.world.damagesource.DamageSource.CACTUS;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    @Shadow
    public float animationSpeed;
    @Shadow
    public int hurtTime;
    @Shadow
    public int hurtDuration;
    @Shadow
    public float hurtDir;
    @Shadow
    @Nullable
    private DamageSource lastDamageSource;
    @Shadow
    private long lastDamageStamp;

    public LivingEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "handleEntityEvent", at = @At("HEAD"), cancellable = true)
    public void handleEntityEvent(byte b, CallbackInfo info) {
        if (b == (byte) 705) {
            info.cancel();
            LivingEntity entity = LivingEntity.class.cast(this);
            SoundEvent soundEvent;
            this.animationSpeed = 1.5F;
            this.invulnerableTime = 20;
            this.hurtTime = this.hurtDuration = 10;
            this.hurtDir = 0.0F;
            if ((soundEvent = this.getHurtSound(CACTUS)) != null) {
                this.playSound(soundEvent, this.getSoundVolume(), (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
            }
            this.hurt(DamageSource.GENERIC, 0.0F);
            this.lastDamageSource = CACTUS;
            this.lastDamageStamp = this.level.getGameTime();
        }
    }

    @Shadow
    protected float getSoundVolume() {
        return 1.0F;
    }


    @Shadow
    @Nullable
    protected SoundEvent getHurtSound(DamageSource damageSource) {
		throw new AssertionError("Mixin injection failed. - WilderWild LivingEntityMixin");
    }

    //LivingEntity line 1030
    //int b = damageSource == DamageSource.DROWN ? 36 : (damageSource.isFire() ? 37 : (damageSource == DamageSource.SWEET_BERRY_BUSH ? 44 : (damageSource == DamageSource.FREEZE ? 57 : 2)));


}
