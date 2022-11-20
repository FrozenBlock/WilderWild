package net.frozenblock.wilderwild.entity;

import net.frozenblock.wilderwild.registry.RegisterEntities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class Tumbleweed extends Entity {

	public Tumbleweed(EntityType<? extends Tumbleweed> entityType, Level level) {
		super(entityType, level);
		this.blocksBuilding = true;
	}

	public Tumbleweed(Level level, double x, double y, double z, @Nullable LivingEntity owner) {
		this(RegisterEntities.TUMBLEWEED, level);
		this.setPos(x, y, z);
		double d = level.random.nextDouble() * 6.2831854820251465D;
		this.setDeltaMovement(-Math.sin(d) * 0.02D, 0.20000000298023224D, -Math.cos(d) * 0.02D);
		this.xo = x;
		this.yo = y;
		this.zo = z;
	}

	@Override
	protected void defineSynchedData() {

	}

	@Override
	protected void readAdditionalSaveData(CompoundTag compound) {

	}

	@Override
	protected void addAdditionalSaveData(CompoundTag compound) {

	}

	@Override
	public Packet<?> getAddEntityPacket() {
		return new ClientboundAddEntityPacket(this);
	}
}
