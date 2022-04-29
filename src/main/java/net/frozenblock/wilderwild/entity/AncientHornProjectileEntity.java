package net.frozenblock.wilderwild.entity;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.frozenblock.wilderwild.WildClientMod;
import net.frozenblock.wilderwild.registry.RegisterEntities;
import net.frozenblock.wilderwild.tag.WildBlockTags;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class AncientHornProjectileEntity extends PersistentProjectileEntity {
    private final TagKey<Block> NON_COLLIDE = WildBlockTags.HORN_PROJECTILE_NON_COLLIDE;
    public double velX;
    public double velY;
    public double velZ;

    public AncientHornProjectileEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
    }
    public AncientHornProjectileEntity(World world, double x, double y, double z) {
        super(RegisterEntities.ANCIENT_HORN_PROJECTILE_ENTITY, x, y, z, world);
    }
    public void tick() {
        super.tick();
        this.setVelocity(this.velX, this.velY, this.velZ);
    }
    public boolean isNoClip() {
        Vec3d vec3d3 = this.getPos();
        Vec3d vec3d = this.getVelocity();
        Vec3d vec3d2 = vec3d3.add(vec3d.multiply(0.08));
        HitResult hitResult = this.world.raycast(new RaycastContext(vec3d3, vec3d2, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, this));
        if (hitResult.getType() == HitResult.Type.BLOCK) {
            if (world.getBlockState(((BlockHitResult)hitResult).getBlockPos()).isIn(this.NON_COLLIDE)) {
                return true;
            }
        } return false;
    }
    @Override
    public Packet<?> createSpawnPacket() {
        return EntitySpawnPacket.create(this, WildClientMod.HORN_PROJECTILE_PACKET_ID);
    }
    @Override
    protected ItemStack asItemStack() {
        return ItemStack.EMPTY;
    }
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putDouble("velX", this.velX);
        nbt.putDouble("velY", this.velY);
        nbt.putDouble("velZ", this.velZ);
    }
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.velX = nbt.getDouble("velX");
        this.velY = nbt.getDouble("velY");
        this.velZ = nbt.getDouble("velZ");
    }
    public void setVelocity(Entity shooter, float pitch, float yaw, float roll, float speed, float divergence) {
        float f = -MathHelper.sin(yaw * 0.017453292F) * MathHelper.cos(pitch * 0.017453292F);
        float g = -MathHelper.sin((pitch + roll) * 0.017453292F);
        float h = MathHelper.cos(yaw * 0.017453292F) * MathHelper.cos(pitch * 0.017453292F);
        this.velX=f;
        this.velY=g;
        this.velZ=h;
        this.setVelocity(f, g, h, speed, divergence);
        Vec3d vec3d = shooter.getVelocity();
        this.setVelocity(this.getVelocity().add(vec3d.x, shooter.isOnGround() ? 0.0D : vec3d.y, vec3d.z));
    }
    protected void onCollision(HitResult hitResult) {
        HitResult.Type type = hitResult.getType();
        if (type == HitResult.Type.ENTITY) {
            this.onEntityHit((EntityHitResult) hitResult);
            this.world.emitGameEvent(GameEvent.PROJECTILE_LAND, hitResult.getPos(), GameEvent.Emitter.of(this, null));
        } else if (type == HitResult.Type.BLOCK) {
            BlockHitResult blockHitResult = (BlockHitResult) hitResult;
            if (!world.getBlockState(blockHitResult.getBlockPos()).isIn(this.NON_COLLIDE)) {
                this.onBlockHit(blockHitResult);
                BlockPos blockPos = blockHitResult.getBlockPos();
                this.world.emitGameEvent(GameEvent.PROJECTILE_LAND, blockPos, GameEvent.Emitter.of(this, this.world.getBlockState(blockPos)));
                this.remove(RemovalReason.DISCARDED);
            }
        }
    }
    protected float getDragInWater() {
        return 1.0F;
    }

    public boolean hasNoGravity() {
        return true;
    }

    public class EntitySpawnPacket { //When the Fabric tutorial WORKS!!!!! BOM BOM BOM BOM BOM BOM BOM, BOBOBOM! DUNDUN!
        public static Packet<?> create(Entity e, Identifier packetID) {
            if (e.world.isClient)
                throw new IllegalStateException("SpawnPacketUtil.create called on the logical client!");
            PacketByteBuf byteBuf = new PacketByteBuf(Unpooled.buffer());
            byteBuf.writeVarInt(Registry.ENTITY_TYPE.getRawId(e.getType()));
            byteBuf.writeUuid(e.getUuid());
            byteBuf.writeVarInt(e.getId());
            PacketBufUtil.writeVec3d(byteBuf, e.getPos());
            PacketBufUtil.writeAngle(byteBuf, e.getPitch());
            PacketBufUtil.writeAngle(byteBuf, e.getYaw());
            return ServerPlayNetworking.createS2CPacket(packetID, byteBuf);
        }

        public static final class PacketBufUtil {

            public static byte packAngle(float angle) {
                return (byte) MathHelper.floor(angle * 256 / 360);
            }

            public static float unpackAngle(byte angleByte) {
                return (angleByte * 360) / 256f;
            }

            public static void writeAngle(PacketByteBuf byteBuf, float angle) {
                byteBuf.writeByte(packAngle(angle));
            }

            public static float readAngle(PacketByteBuf byteBuf) {
                return unpackAngle(byteBuf.readByte());
            }

            public static void writeVec3d(PacketByteBuf byteBuf, Vec3d vec3d) {
                byteBuf.writeDouble(vec3d.x);
                byteBuf.writeDouble(vec3d.y);
                byteBuf.writeDouble(vec3d.z);
            }

            public static Vec3d readVec3d(PacketByteBuf byteBuf) {
                double x = byteBuf.readDouble();
                double y = byteBuf.readDouble();
                double z = byteBuf.readDouble();
                return new Vec3d(x, y, z);
            }
        }
    }
}
