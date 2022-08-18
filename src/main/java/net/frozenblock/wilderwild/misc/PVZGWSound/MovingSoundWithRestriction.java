package net.frozenblock.wilderwild.misc.PVZGWSound;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.registry.RegisterMovingSoundRestrictions;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;

@Environment(EnvType.CLIENT)
public class MovingSoundWithRestriction extends AbstractTickableSoundInstance {

    private final Entity entity;
    private final RegisterMovingSoundRestrictions.LoopPredicate<?> predicate;
    private final float distance = 0.0F;

    public MovingSoundWithRestriction(Entity entity, SoundEvent sound, SoundSource category, float volume, float pitch, RegisterMovingSoundRestrictions.LoopPredicate<?> predicate) {
        super(sound, category, SoundInstance.createUnseededRandom());
        this.entity = entity;
        this.looping = false;
        this.volume = volume;
        this.pitch = pitch;
        this.x = (float) entity.getX();
        this.y = (float) entity.getY();
        this.z = (float) entity.getZ();
        this.predicate = predicate;
    }

    public boolean canPlaySound() {
        return !this.entity.isSilent();
    }

    public boolean canStartSilent() {
        return true;
    }

    public void tick() {
        if (this.entity.isRemoved()) {
            this.stop();
        } else {
            if (!this.predicate.test(this.entity)) {
                this.stop();
            } else {
                this.x = (float) this.entity.getX();
                this.y = (float) this.entity.getY();
                this.z = (float) this.entity.getZ();
            }
        }
    }

}