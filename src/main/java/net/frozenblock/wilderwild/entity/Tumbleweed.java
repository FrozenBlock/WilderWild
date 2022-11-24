package net.frozenblock.wilderwild.entity;

import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class Tumbleweed extends Mob {

	public Tumbleweed(EntityType<Tumbleweed> entityType, Level level) {
		super(entityType, level);
		this.blocksBuilding = true;
	}

	public static AttributeSupplier.Builder addAttributes() {
		return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 1D);
	}

	@Override
	public void tick() {
		super.tick();
		if (this.wasTouchingWater || this.wasOnFire) {
			this.spawnBreakParticles();
			this.remove(RemovalReason.KILLED);
		}
	}

	@Override
	public boolean causeFallDamage(float fallDistance, float multiplier, DamageSource source) {
		return false;
	}

	@Override
	public void readAdditionalSaveData(@NotNull CompoundTag compound) {
		super.readAdditionalSaveData(compound);
	}

	@Override
	public void addAdditionalSaveData(@NotNull CompoundTag compound) {
		super.addAdditionalSaveData(compound);
	}

	@Override
	public void die(@NotNull DamageSource damageSource) {
		this.spawnBreakParticles();
		this.remove(RemovalReason.KILLED);
	}

	public void spawnBreakParticles() {
		if (this.level instanceof ServerLevel level) {
			level.sendParticles(new ItemParticleOption(ParticleTypes.ITEM, new ItemStack(RegisterBlocks.TUMBLEWEED)), this.getX(), this.getY(0.6666666666666666D), this.getZ(), 10, this.getBbWidth() / 4.0F, this.getBbHeight() / 4.0F, this.getBbWidth() / 4.0F, 0.05D);
		}
	}


	@Override
	public Iterable<ItemStack> getArmorSlots() {
		return NonNullList.withSize(1, ItemStack.EMPTY);
	}

	@Override
	public ItemStack getItemBySlot(@NotNull EquipmentSlot slot) {
		return ItemStack.EMPTY;
	}

	@Override
	public void setItemSlot(@NotNull EquipmentSlot slot, @NotNull ItemStack stack) {

	}

	@Override
	public HumanoidArm getMainArm() {
		return HumanoidArm.LEFT;
	}
}
