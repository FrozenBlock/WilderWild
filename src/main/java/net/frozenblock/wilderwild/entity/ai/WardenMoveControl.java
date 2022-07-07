package net.frozenblock.wilderwild.entity.ai;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.control.AquaticMoveControl;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.PathNodeMaker;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.WardenEntity;
import net.minecraft.entity.passive.AxolotlEntity;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.shape.VoxelShape;

public class WardenMoveControl extends AquaticMoveControl {

    private final int pitchChange;
    private final int yawChange;
    private final float speedInWater;
    private final float speedInAir;
    private final boolean buoyant;

    public WardenMoveControl(WardenEntity warden, int pitchChange, int yawChange, float speedInWater, float speedInAir, boolean buoyant) {
        super(warden, pitchChange, yawChange, speedInWater, speedInAir, buoyant);
        this.pitchChange = pitchChange;
        this.yawChange = yawChange;
        this.speedInWater = speedInWater;
        this.speedInAir = speedInAir;
        this.buoyant = buoyant;
    }

    @Override
    public void tick() {
        if (this.isEntityTouchingWaterOrLava(this.entity)) {
            if (this.buoyant && this.isEntityTouchingWaterOrLava(this.entity)) {
                if (((WardenEntity) this.entity).getBrain().hasMemoryModule(MemoryModuleType.ROAR_TARGET) || ((WardenEntity) this.entity).getBrain().hasMemoryModule(MemoryModuleType.ATTACK_TARGET)) {
                    this.entity.setVelocity(this.entity.getVelocity().add(0.0, 0.0, 0.0));
                } else {
                    if (!this.isEntitySubmergedInWaterOrLava(this.entity)) {
                        this.entity.setVelocity(this.entity.getVelocity().add(0.0, 0.01, 0.0));
                    } else if (this.isEntityTouchingWaterOrLava(this.entity)) {
                        this.entity.setVelocity(this.entity.getVelocity().add(0.0, 0.005, 0.0));
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
                    this.entity.setYaw(this.wrapDegrees(this.entity.getYaw(), h, (float) this.yawChange));
                    this.entity.bodyYaw = this.entity.getYaw();
                    this.entity.headYaw = this.entity.getYaw();
                    float i = (float) (this.speed * this.entity.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED));
                    if (this.isEntityTouchingWaterOrLava(entity)) {
                        this.entity.setMovementSpeed(i * this.speedInWater);
                        double j = Math.sqrt(d * d + f * f);
                        if (Math.abs(e) > 1.0E-5F || Math.abs(j) > 1.0E-5F) {
                            float k = -((float) (MathHelper.atan2(e, j) * 180.0F / (float) Math.PI));
                            k = MathHelper.clamp(MathHelper.wrapDegrees(k), (float) (-this.pitchChange), (float) this.pitchChange);
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
            if (this.state == MoveControl.State.STRAFE) {
                float f = (float) this.entity.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED);
                float g = (float) this.speed * f;
                float h = this.forwardMovement;
                float i = this.sidewaysMovement;
                float j = MathHelper.sqrt(h * h + i * i);
                if (j < 1.0F) {
                    j = 1.0F;
                }

                j = g / j;
                h *= j;
                i *= j;
                float k = MathHelper.sin(this.entity.getYaw() * (float) (Math.PI / 180.0));
                float l = MathHelper.cos(this.entity.getYaw() * (float) (Math.PI / 180.0));
                float m = h * l - i * k;
                float n = i * l + h * k;
                if (!this.isPosWalkable(m, n)) {
                    this.forwardMovement = 1.0F;
                    this.sidewaysMovement = 0.0F;
                }

                this.entity.setMovementSpeed(g);
                this.entity.setForwardSpeed(this.forwardMovement);
                this.entity.setSidewaysSpeed(this.sidewaysMovement);
                this.state = MoveControl.State.WAIT;
            } else if (this.state == MoveControl.State.MOVE_TO) {
                this.state = MoveControl.State.WAIT;
                double d = this.targetX - this.entity.getX();
                double e = this.targetZ - this.entity.getZ();
                double o = this.targetY - this.entity.getY();
                double p = d * d + o * o + e * e;
                if (p < 2.5000003E-7F) {
                    this.entity.setForwardSpeed(0.0F);
                    return;
                }

                float n = (float) (MathHelper.atan2(e, d) * 180.0F / (float) Math.PI) - 90.0F;
                this.entity.setYaw(this.wrapDegrees(this.entity.getYaw(), n, 90.0F));
                this.entity.setMovementSpeed((float) (this.speed * this.entity.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED)));
                BlockPos blockPos = this.entity.getBlockPos();
                BlockState blockState = this.entity.world.getBlockState(blockPos);
                VoxelShape voxelShape = blockState.getCollisionShape(this.entity.world, blockPos);
                if (o > (double) this.entity.stepHeight && d * d + e * e < (double) Math.max(1.0F, this.entity.getWidth())
                        || !voxelShape.isEmpty()
                        && this.entity.getY() < voxelShape.getMax(Direction.Axis.Y) + (double) blockPos.getY()
                        && !blockState.isIn(BlockTags.DOORS)
                        && !blockState.isIn(BlockTags.FENCES)) {
                    this.entity.getJumpControl().setActive();
                    this.state = MoveControl.State.JUMPING;
                }
            } else if (this.state == MoveControl.State.JUMPING) {
                this.entity.setMovementSpeed((float) (this.speed * this.entity.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED)));
                if (this.entity.isOnGround()) {
                    this.state = MoveControl.State.WAIT;
                }
            } else {
                this.entity.setForwardSpeed(0.0F);
            }

        }
    }

    private boolean isPosWalkable(float x, float z) {
        EntityNavigation entityNavigation = this.entity.getNavigation();
        if (entityNavigation != null) {
            PathNodeMaker pathNodeMaker = entityNavigation.getNodeMaker();
            if (pathNodeMaker != null
                    && pathNodeMaker.getDefaultNodeType(
                    this.entity.world, MathHelper.floor(this.entity.getX() + (double)x), this.entity.getBlockY(), MathHelper.floor(this.entity.getZ() + (double)z)
            )
                    != PathNodeType.WALKABLE) {
                return false;
            }
        }

        return true;
    }

    private boolean isEntityTouchingWaterOrLava(Entity entity) {
        return entity.isTouchingWater() || entity.isInLava();
    }

    private boolean isEntitySubmergedInWaterOrLava(Entity entity) {
        return entity.isSubmergedIn(FluidTags.WATER) || entity.isSubmergedIn(FluidTags.LAVA);
    }
}
