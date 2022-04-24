package net.frozenblock.wilderwild.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SculkSensorBlock;
import net.minecraft.block.enums.SculkSensorPhase;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Arm;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SculkSensorTendrilEntity extends LivingEntity {
    public int activeTicksLeft;

    public SculkSensorTendrilEntity(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createLivingAttributes() {
        return DefaultAttributeContainer.builder().add(EntityAttributes.GENERIC_MAX_HEALTH).add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE).add(EntityAttributes.GENERIC_MOVEMENT_SPEED).add(EntityAttributes.GENERIC_ARMOR).add(EntityAttributes.GENERIC_ARMOR_TOUGHNESS);
    }

    public boolean shouldRenderName() {
        return false;
    }
    protected void fall(double heightDifference, boolean onGround, BlockState state, BlockPos landedPosition) { }
    public boolean isGlowing() { return false; }
    public boolean isInsideWall() { return false; }
    protected boolean isOnSoulSpeedBlock() { return false; }
    public boolean damage(DamageSource source, float amount) { return false; }
    protected void knockback(LivingEntity target) {}
    protected void playHurtSound(DamageSource source) {}

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.activeTicksLeft = nbt.getInt("activeTicksLeft");
    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("activeTicksLeft", this.activeTicksLeft);
    }

    public void tickMovement() {
        if (!world.getBlockState(this.getBlockPos()).isOf(Blocks.SCULK_SENSOR)) {
            this.remove(RemovalReason.DISCARDED);
        } else if (SculkSensorBlock.getPhase(world.getBlockState(this.getBlockPos())) == SculkSensorPhase.ACTIVE && this.activeTicksLeft == -1) {
            world.sendEntityStatus(this, (byte) 6);
        }
        if (this.activeTicksLeft > 0) {
            --this.activeTicksLeft;
        } else if (this.activeTicksLeft==0) {
            world.sendEntityStatus(this, (byte)7);
            --this.activeTicksLeft;
        }
    }
    protected void tickCramming() { }

    @Override
    public Arm getMainArm() { return Arm.LEFT; }

    public void handleStatus(byte status) {
        if (status==(byte)6) {
            this.activeTicksLeft=17;
        }
        if (status==(byte)7) {
            this.activeTicksLeft=-1;
        }
    }

    @Override
    public Iterable<ItemStack> getArmorItems() { return DefaultedList.ofSize(4, ItemStack.EMPTY); }

    @Override
    public ItemStack getEquippedStack(EquipmentSlot slot) { return ItemStack.EMPTY; }

    @Override
    public void equipStack(EquipmentSlot slot, ItemStack stack) { }

}
