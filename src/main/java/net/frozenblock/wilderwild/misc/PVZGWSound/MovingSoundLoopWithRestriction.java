package net.frozenblock.wilderwild.misc.PVZGWSound;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.registry.RegisterMovingSoundRestrictions;
import net.minecraft.client.sound.MovingSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.entity.Entity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;

@Environment(EnvType.CLIENT)
public class MovingSoundLoopWithRestriction extends MovingSoundInstance {

    private final Entity entity;
    private final RegisterMovingSoundRestrictions.LoopPredicate<?> predicate;
    private float distance = 0.0F;

    float thisPitch;

    public MovingSoundLoopWithRestriction(Entity entity, SoundEvent sound, SoundCategory category, float volume, float pitch, RegisterMovingSoundRestrictions.LoopPredicate<?> predicate) {
        super(sound, category, SoundInstance.createRandom());
        this.entity = entity;
        this.repeat = true;
        this.repeatDelay = 0;
        this.pitch = pitch;
        thisPitch = this.pitch;
        this.volume = volume;
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
        this.pitch = thisPitch;
        if (this.entity.isRemoved()) {
            this.setDone();
        } else {
            if (!this.predicate.test(this.entity)) {
                this.setDone();
            } else {
                this.x = (float) this.entity.getX();
                this.y = (float) this.entity.getY();
                this.z = (float) this.entity.getZ();
            }
        }
    }

}
