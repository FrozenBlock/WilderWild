package net.frozenblock.wilderwild.entity.ai;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.WardenEntity;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.NotNull;

public class WardenMoveControl extends MoveControl {

    private final WardenEntity entity;
    private final float pitchChange;
    private final float yawChange;
    private final float speedInWater;
    private final float speedInAir;
    private final boolean buoyant;

    public WardenMoveControl(@NotNull WardenEntity warden, float pitchChange, float yawChange, float speedInWater, float speedInAir, boolean buoyant) {
        super(warden);
        this.entity = warden;
        this.pitchChange = pitchChange;
        this.yawChange = yawChange;
        this.speedInWater = speedInWater;
        this.speedInAir = speedInAir;
        this.buoyant = buoyant;
    }

    @Override
    public void tick() {
        if (this.isEntityTouchingWaterOrLava(this.entity)) {
            if (this.buoyant) {
                if ((this.entity.getBrain().hasMemoryModule(MemoryModuleType.ROAR_TARGET) || this.entity.getBrain().hasMemoryModule(MemoryModuleType.ATTACK_TARGET))) {
                    if (this.entity.getBrain().getOptionalMemory(MemoryModuleType.ROAR_TARGET).isPresent()) {
                        if (this.entity.getBrain().getOptionalMemory(MemoryModuleType.ROAR_TARGET).get().getY() > this.entity.getY()) {
                            this.entity.setVelocity(this.entity.getVelocity().add(0.0D, 0.008D, 0.0D));
                        } else {
                            this.entity.setVelocity(this.entity.getVelocity().add(0.0D, -0.008D, 0.0D));
                        }

                    } else if (this.entity.getBrain().getOptionalMemory(MemoryModuleType.ATTACK_TARGET).isPresent()) {
                        if (this.entity.getBrain().getOptionalMemory(MemoryModuleType.ATTACK_TARGET).get().getY() > this.entity.getY()) {
                            this.entity.setVelocity(this.entity.getVelocity().add(0.0D, 0.008D, 0.0D));
                        } else {
                            this.entity.setVelocity(this.entity.getVelocity().add(0.0D, -0.008D, 0.0D));
                        }
                    }
                } else {
                    if (!this.isEntitySubmergedInWaterOrLava(this.entity)) {
                        this.entity.setVelocity(this.entity.getVelocity().add(0.0D, 0.01D, 0.0D));
                    } else {
                        this.entity.setVelocity(this.entity.getVelocity().add(0.0D, 0.006D, 0.0D));
                    }
                }
            }

            if (this.state == MoveControl.State.MOVE_TO && !this.entity.getNavigation().isIdle()) {
                double d = this.targetX - this.entity.getX();
                double e = this.targetY - this.entity.getY();
                double f = this.targetZ - this.entity.getZ();
                double g = d * d + e * e + f * f;
                if (g < 2.5000003E-7F) {
                    this.entity.setForwardSpeed(0.0F);
                } else {
                    float h = (float) (MathHelper.atan2(f, d) * 180.0F / (float) Math.PI) - 90.0F;
                    this.entity.setYaw(this.wrapDegrees(this.entity.getYaw(), h, this.yawChange));
                    this.entity.bodyYaw = this.entity.getYaw();
                    this.entity.headYaw = this.entity.getYaw();
                    float i = (float) (this.speed * this.entity.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED));
                    if (this.isEntityTouchingWaterOrLava(entity)) {
                        this.entity.setMovementSpeed(i * this.speedInWater);
                        double j = Math.sqrt(d * d + f * f);
                        if (Math.abs(e) > 1.0E-5F || Math.abs(j) > 1.0E-5F) {
                            float k = -((float) (MathHelper.atan2(e, j) * 180.0F / (float) Math.PI));
                            k = MathHelper.clamp(MathHelper.wrapDegrees(k), -this.pitchChange, this.pitchChange);
                            this.entity.setPitch(this.wrapDegrees(this.entity.getPitch(), k, 5.0F));
                        }

                        float k = MathHelper.cos(this.entity.getPitch() * (float) (Math.PI / 180.0));
                        float l = MathHelper.sin(this.entity.getPitch() * (float) (Math.PI / 180.0));
                        this.entity.forwardSpeed = k * i;
                        this.entity.upwardSpeed = -l * i;
                    } else {
                        this.entity.setMovementSpeed(i * this.speedInAir);
                    }

                }
            } else {
                this.entity.setMovementSpeed(0.0F);
                this.entity.setSidewaysSpeed(0.0F);
                this.entity.setUpwardSpeed(0.0F);
                this.entity.setForwardSpeed(0.0F);
            }
        } else {
            super.tick();
        }
    }

    private boolean isEntityTouchingWaterOrLava(Entity entity) {
        return entity.isInsideWaterOrBubbleColumn() || entity.isInLava();
    }

    private boolean isEntitySubmergedInWaterOrLava(Entity entity) {
        return entity.isSubmergedIn(FluidTags.WATER) || entity.isSubmergedIn(FluidTags.LAVA);
    }
}
