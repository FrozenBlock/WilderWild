package net.frozenblock.wilderwild.entity;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.registry.RegisterEntities;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
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
		return EntitySpawnPacket.create(this, WilderWild.TUMBLEWEED_PACKET_ID);
	}

	public static class EntitySpawnPacket { //When the Fabric tutorial WORKS!!!!! BOM BOM BOM BOM BOM BOM BOM, BOBOBOM! DUNDUN!
		public static Packet<?> create(Entity entity, ResourceLocation packetID) {
			if (entity.level.isClientSide)
				throw new IllegalStateException("SpawnPacketUtil.create called on the logical client!");
			FriendlyByteBuf byteBuf = new FriendlyByteBuf(Unpooled.buffer());
			byteBuf.writeVarInt(Registry.ENTITY_TYPE.getId(entity.getType()));
			byteBuf.writeUUID(entity.getUUID());
			byteBuf.writeVarInt(entity.getId());
			EntitySpawnPacket.PacketBufUtil.writeVec3d(byteBuf, entity.position());
			EntitySpawnPacket.PacketBufUtil.writeAngle(byteBuf, entity.getXRot());
			EntitySpawnPacket.PacketBufUtil.writeAngle(byteBuf, entity.getYRot());
			return ServerPlayNetworking.createS2CPacket(packetID, byteBuf);
		}

		public static final class PacketBufUtil {

			public static byte packAngle(float angle) {
				return (byte) Mth.floor(angle * 256 / 360);
			}

			public static float unpackAngle(byte angleByte) {
				return (angleByte * 360) / 256F;
			}

			public static void writeAngle(FriendlyByteBuf byteBuf, float angle) {
				byteBuf.writeByte(packAngle(angle));
			}

			public static float readAngle(FriendlyByteBuf byteBuf) {
				return unpackAngle(byteBuf.readByte());
			}

			public static void writeVec3d(FriendlyByteBuf byteBuf, Vec3 vec3d) {
				byteBuf.writeDouble(vec3d.x);
				byteBuf.writeDouble(vec3d.y);
				byteBuf.writeDouble(vec3d.z);
			}

			public static Vec3 readVec3d(FriendlyByteBuf byteBuf) {
				double x = byteBuf.readDouble();
				double y = byteBuf.readDouble();
				double z = byteBuf.readDouble();
				return new Vec3(x, y, z);
			}
		}
	}
}
