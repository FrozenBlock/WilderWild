package net.frozenblock.wilderwild.misc.PVZGWSound;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.registry.RegisterLoopingSoundRestrictions;
import net.minecraft.client.sound.MovingSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.entity.Entity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class MovingSoundLoop extends MovingSoundInstance {

    private final Entity entity;
    private final RegisterLoopingSoundRestrictions.LoopPredicate<?> predicate;
    private float distance = 0.0F;

    public MovingSoundLoop(Entity entity, SoundEvent sound, SoundCategory category, float volume, float pitch, RegisterLoopingSoundRestrictions.LoopPredicate<?> predicate) {
        super(sound, category, SoundInstance.createRandom());
        this.entity = entity;
        this.repeat = true;
        this.repeatDelay = 0;
        this.volume = volume;
        this.pitch = pitch;
        this.x = (float) entity.getX();
        this.y = (float) entity.getY();
        this.z = (float) entity.getZ();
        this.predicate = predicate;
    }

    public boolean canPlay() {
        return !this.entity.isSilent();
    }

    public boolean shouldAlwaysPlay() {
        return true;
    }

    public void tick() {
        if (this.entity.isRemoved()) {
            this.setDone();
        } else {
            if (!this.predicate.test(this.entity)) {
                this.setDone();
            } else {
                this.x = (float) this.entity.getX();
                this.y = (float) this.entity.getY();
                this.z = (float) this.entity.getZ();
                float f = (float) this.entity.getVelocity().horizontalLength();
                this.distance = MathHelper.clamp(this.distance + 0.0025F, 0.0F, 1.0F);
                this.volume = MathHelper.lerp(MathHelper.clamp(f, 0.0F, 0.5F), 0.0F, 0.7F);
            }
        }
    }

}
