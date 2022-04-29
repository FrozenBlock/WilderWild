package net.frozenblock.wilderwild.entity;

import com.google.common.collect.Sets;
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
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.potion.Potions;
import net.minecraft.sound.SoundEvents;
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

    public AncientHornProjectileEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
    }
    public AncientHornProjectileEntity(World world, double x, double y, double z) {
        super(RegisterEntities.ANCIENT_HORN_PROJECTILE_ENTITY, x, y, z, world);
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
                /*
                In 1.17,we use these.
                byteBuf.writeVarInt(e.getId());

                PacketBufUtil.writeVec3d(byteBuf, e.getPos());
                PacketBufUtil.writeAngle(byteBuf, e.getPitch());
                PacketBufUtil.writeAngle(byteBuf, e.getYaw());
                */

            return ServerPlayNetworking.createS2CPacket(packetID, byteBuf);
        }

        public static final class PacketBufUtil {

            /**
             * Packs a floating-point angle into a {@code byte}.
             *
             * @param angle angle
             * @return packed angle
             */
            public static byte packAngle(float angle) {
                return (byte) MathHelper.floor(angle * 256 / 360);
            }

            /**
             * Unpacks a floating-point angle from a {@code byte}.
             *
             * @param angleByte packed angle
             * @return angle
             */
            public static float unpackAngle(byte angleByte) {
                return (angleByte * 360) / 256f;
            }

            /**
             * Writes an angle to a {@link PacketByteBuf}.
             *
             * @param byteBuf destination buffer
             * @param angle   angle
             */
            public static void writeAngle(PacketByteBuf byteBuf, float angle) {
                byteBuf.writeByte(packAngle(angle));
            }

            /**
             * Reads an angle from a {@link PacketByteBuf}.
             *
             * @param byteBuf source buffer
             * @return angle
             */
            public static float readAngle(PacketByteBuf byteBuf) {
                return unpackAngle(byteBuf.readByte());
            }

            /**
             * Writes a {@link Vec3d} to a {@link PacketByteBuf}.
             *
             * @param byteBuf destination buffer
             * @param vec3d   vector
             */
            public static void writeVec3d(PacketByteBuf byteBuf, Vec3d vec3d) {
                byteBuf.writeDouble(vec3d.x);
                byteBuf.writeDouble(vec3d.y);
                byteBuf.writeDouble(vec3d.z);
            }

            /**
             * Reads a {@link Vec3d} from a {@link PacketByteBuf}.
             *
             * @param byteBuf source buffer
             * @return vector
             */
            public static Vec3d readVec3d(PacketByteBuf byteBuf) {
                double x = byteBuf.readDouble();
                double y = byteBuf.readDouble();
                double z = byteBuf.readDouble();
                return new Vec3d(x, y, z);
            }
        }
    }
}
