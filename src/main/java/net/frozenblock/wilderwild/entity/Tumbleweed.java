package net.frozenblock.wilderwild.entity;

import net.frozenblock.lib.wind.api.WindManager;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class Tumbleweed extends Mob {
	private static final double windMultiplier = 1.4;
	private static final double windClamp = 0.17;

	public float pitch;
	public float prevPitch;
	public float yaw;
	public float prevYaw;
	public float roll;
	public float prevRoll;

	public Tumbleweed(EntityType<Tumbleweed> entityType, Level level) {
		super(entityType, level);
		this.blocksBuilding = true;
	}

	public static boolean canSpawn(EntityType<Tumbleweed> type, ServerLevelAccessor level, MobSpawnType reason, BlockPos pos, RandomSource random) {
		return level.getBrightness(LightLayer.SKY, pos) > 7 && random.nextInt(0, 15) == 12;
	}

	public static AttributeSupplier.Builder addAttributes() {
		return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 1D);
	}

	@Override
	protected void doPush(@NotNull Entity entity) {
		super.doPush(entity);
		if (this.getDeltaMovement().length() > 0.3) {
			//TODO: Tumbleweed Damagesource
			entity.hurt(DamageSource.mobAttack(this), 2F);
		}
	}

	@Override
	public boolean canBeCollidedWith() {
		return true;
	}

	@Override
	public void tick() {
		super.tick();
		this.setYRot(0F);
		double multiplier = (this.level.getBrightness(LightLayer.SKY, this.blockPosition()) * 0.0667) * (this.wasTouchingWater ? this.getWaterSlowDown() : 1F);
		double windX = Mth.clamp(WindManager.windX * windMultiplier, -windClamp, windClamp);
		double windZ = Mth.clamp(WindManager.windZ * windMultiplier, -windClamp, windClamp);
		Vec3 deltaMovement = this.getDeltaMovement();
		deltaMovement = deltaMovement.add((windX * 0.2) * multiplier, 0, (windZ * 0.2) * multiplier);
		deltaMovement = new Vec3(deltaMovement.x, deltaMovement.y < 0 ? deltaMovement.y * 0.88 : deltaMovement.y, deltaMovement.z);
		this.setDeltaMovement(deltaMovement);

		Vec3 deltaPos = this.getPosition(1).subtract(this.getPosition(0));
		this.prevPitch = this.pitch;
		this.prevYaw = this.yaw;
		this.prevRoll = this.roll;

		this.roll += deltaPos.x * 35;
		this.yaw += deltaPos.y * 35;
		this.pitch += deltaPos.z * 35;
		if (deltaPos.y <= 0 && this.isOnGround()) {
			deltaMovement = deltaMovement.add(0, Math.min(0.5, ((deltaPos.horizontalDistance() * 1.1))) * multiplier, 0);
		}
		if (deltaMovement.horizontalDistance() > 0.05 && deltaPos.horizontalDistance() < 0.01) {
			deltaMovement = deltaMovement.add(0, Math.min(0.5, ((deltaMovement.horizontalDistance() * 0.05))) * multiplier, 0);
		}
		this.setDeltaMovement(deltaMovement);
	}

	public void destroy() {
		this.spawnBreakParticles();
		this.remove(RemovalReason.KILLED);
	}

	@Override
	protected SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
		return RegisterSounds.ENTITY_TUMBLEWEED_DAMAGE;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return RegisterSounds.ENTITY_TUMBLEWEED_BREAK;
	}

	@Override
	public boolean causeFallDamage(float fallDistance, float multiplier, @NotNull DamageSource source) {
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
		if (damageSource.getDirectEntity() instanceof Player player) {
			if (this.level.getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS) && !damageSource.isCreativePlayer()) {
				ItemStack stack = player.getMainHandItem();
				if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, stack) > 0) {
					this.level.addFreshEntity(new ItemEntity(this.level, this.getX(), this.getY(), this.getZ(), new ItemStack(RegisterBlocks.TUMBLEWEED)));
				}
			}
		}
		this.destroy();
	}

	public void spawnBreakParticles() {
		if (this.level instanceof ServerLevel level) {
			level.sendParticles(new ItemParticleOption(ParticleTypes.ITEM, new ItemStack(RegisterBlocks.TUMBLEWEED)), this.getX(), this.getY(0.6666666666666666D), this.getZ(), 20, this.getBbWidth() / 4.0F, this.getBbHeight() / 4.0F, this.getBbWidth() / 4.0F, 0.05D);
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
