package net.frozenblock.wilderwild.entity;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.frozenblock.wilderwild.WildClientMod;
import net.frozenblock.wilderwild.registry.RegisterEntities;
import net.frozenblock.wilderwild.tag.WildBlockTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.s2c.play.GameStateChangeS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class AncientHornProjectileEntity extends PersistentProjectileEntity {
    private final TagKey<Block> NON_COLLIDE = WildBlockTags.HORN_PROJECTILE_NON_COLLIDE;
    private boolean shot;
    private boolean leftOwner;
    public int aliveTicks;
    public int prevAliveTicks;
    public double vecX;
    public double vecY;
    public double vecZ;
    private BlockState inBlockState;

    public AncientHornProjectileEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
    }
    public AncientHornProjectileEntity(World world, double x, double y, double z) {
        super(RegisterEntities.ANCIENT_HORN_PROJECTILE_ENTITY, x, y, z, world);
    }
    public void tick() {
        this.baseTick();
        if (this.aliveTicks>200) { this.remove(RemovalReason.DISCARDED); }
        this.prevAliveTicks=this.aliveTicks;
        ++this.aliveTicks;
        if (!this.shot) {
            this.shot = true;
        }
        if (!this.leftOwner) {
            this.leftOwner = this.shouldLeaveOwner();
        }
        boolean bl = this.isNoClip();
        Vec3d vec3d = this.getVelocity();
        if (this.prevPitch == 0.0F && this.prevYaw == 0.0F) {
            double d = vec3d.horizontalLength();
            this.setYaw((float)(MathHelper.atan2(vec3d.x, vec3d.z) * 57.2957763671875D));
            this.setPitch((float)(MathHelper.atan2(vec3d.y, d) * 57.2957763671875D));
            this.prevYaw = this.getYaw();
            this.prevPitch = this.getPitch();
        }
        BlockPos blockPos = this.getBlockPos();
        BlockState blockState = this.world.getBlockState(blockPos);
        Vec3d vec3d2;
        if (!blockState.isAir() && !bl) {
            VoxelShape voxelShape = blockState.getCollisionShape(this.world, blockPos);
            if (!voxelShape.isEmpty()) {
                vec3d2 = this.getPos();
                for (Box box : voxelShape.getBoundingBoxes()) {
                    if (box.offset(blockPos).contains(vec3d2)) {
                        this.inGround = true;
                        break;
                    }
                }
            }
        }
        if (this.shake > 0) { --this.shake; }
        if (this.isTouchingWaterOrRain() || blockState.isOf(Blocks.POWDER_SNOW)) { this.extinguish(); }
        if (this.inGround && !bl) {
            if (this.inBlockState != blockState && this.shouldFall()) {
                this.fall();
            } else if (!this.world.isClient) {
                this.age();
            }
            ++this.inGroundTime;
        } else {
            this.inGroundTime = 0;
            Vec3d vec3d3 = this.getPos();
            vec3d2 = vec3d3.add(vec3d);
            HitResult hitResult = this.world.raycast(new RaycastContext(vec3d3, vec3d2, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, this));
            if (hitResult.getType() != HitResult.Type.MISS) {
                vec3d2 = hitResult.getPos();
            }
            while(!this.isRemoved()) {
                EntityHitResult entityHitResult = this.getEntityCollision(vec3d3, vec3d2);
                if (entityHitResult != null) {
                    hitResult = entityHitResult;
                }
                if (hitResult != null && hitResult.getType() == HitResult.Type.ENTITY) {
                    Entity entity = ((EntityHitResult)hitResult).getEntity();
                    Entity entity2 = this.getOwner();
                    if (entity instanceof PlayerEntity && entity2 instanceof PlayerEntity && !((PlayerEntity)entity2).shouldDamagePlayer((PlayerEntity)entity)) {
                        hitResult = null;
                        entityHitResult = null;
                    }
                }
                if (hitResult != null && !bl) {
                    this.onCollision(hitResult);
                    this.velocityDirty = true;
                }
                if (entityHitResult == null || this.getPierceLevel() <= 0) { break; }
                hitResult = null;
            }
            vec3d = this.getVelocity();
            double e = vec3d.x;
            double f = vec3d.y;
            double g = vec3d.z;
            if (this.isCritical()) {
                for(int i = 0; i < 4; ++i) {
                    this.world.addParticle(ParticleTypes.CRIT, this.getX() + e * (double)i / 4.0D, this.getY() + f * (double)i / 4.0D, this.getZ() + g * (double)i / 4.0D, -e, -f + 0.2D, -g);
                }
            }
            double h = this.getX() + e;
            double j = this.getY() + f;
            double k = this.getZ() + g;
            double l = vec3d.horizontalLength();
            if (bl) {
                this.setYaw((float)(MathHelper.atan2(-e, -g) * 57.2957763671875D));
            } else {
                this.setYaw((float)(MathHelper.atan2(e, g) * 57.2957763671875D));
            }
            this.setPitch((float)(MathHelper.atan2(f, l) * 57.2957763671875D));
            this.setPitch(updateRotation(this.prevPitch, this.getPitch()));
            this.setYaw(updateRotation(this.prevYaw, this.getYaw()));

            this.setPosition(h, j, k);
            this.checkBlockCollision();
        }
    }
    public void onPlayerCollision(PlayerEntity player) { }
    private boolean shouldFall() {
        return this.inGround && this.world.isSpaceEmpty((new Box(this.getPos(), this.getPos())).expand(0.06D));
    }
    private void fall() {
        this.inGround = false;
        Vec3d vec3d = this.getVelocity();
        this.setVelocity(vec3d.multiply(this.random.nextFloat() * 0.2F, this.random.nextFloat() * 0.2F, this.random.nextFloat() * 0.2F));
    }
    protected void onBlockHit(BlockHitResult blockHitResult) {
        this.inBlockState = this.world.getBlockState(blockHitResult.getBlockPos());
        BlockState blockState = this.world.getBlockState(blockHitResult.getBlockPos());
        blockState.onProjectileHit(this.world, blockState, blockHitResult, this);
        Vec3d vec3d = blockHitResult.getPos().subtract(this.getX(), this.getY(), this.getZ());
        this.setVelocity(vec3d);
        Vec3d vec3d2 = vec3d.normalize().multiply(0.05000000074505806D);
        this.setPos(this.getX() - vec3d2.x, this.getY() - vec3d2.y, this.getZ() - vec3d2.z);
        this.playSound(this.getSound(), 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
        this.inGround = true;
        this.shake = 7;
        this.setCritical(false);
        this.setPierceLevel((byte)0);
        this.setSound(SoundEvents.ENTITY_ARROW_HIT);
        this.setShotFromCrossbow(false);
        this.remove(RemovalReason.DISCARDED);
    }
    public boolean isTouchingWater() { return true; }
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
    private boolean shouldLeaveOwner() {
        Entity entity = this.getOwner();
        if (entity != null) {
            for (Entity entity2 : this.world.getOtherEntities(this, this.getBoundingBox().stretch(this.getVelocity()).expand(1.0D), (entityx) -> !entityx.isSpectator() && entityx.collides())) {
                if (entity2.getRootVehicle() == entity.getRootVehicle()) { return false; }
            }
        } return true;
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
        if (this.inBlockState != null) {
            nbt.put("inBlockState", NbtHelper.fromBlockState(this.inBlockState));
        }
        nbt.putInt("aliveTicks", this.aliveTicks);
        if (this.leftOwner) {
            nbt.putBoolean("LeftOwner", true);
        } nbt.putBoolean("HasBeenShot", this.shot);
        nbt.putDouble("originX", this.vecX);
        nbt.putDouble("originY", this.vecY);
        nbt.putDouble("originZ", this.vecZ);
    }
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.contains("inBlockState", 10)) {
            this.inBlockState = NbtHelper.toBlockState(nbt.getCompound("inBlockState"));
        }
        this.aliveTicks = nbt.getInt("aliveTicks");
        this.leftOwner = nbt.getBoolean("LeftOwner");
        this.shot = nbt.getBoolean("HasBeenShot");
        this.vecX = nbt.getDouble("originX");
        this.vecY = nbt.getDouble("originY");
        this.vecZ = nbt.getDouble("originZ");
    }
    public void setVelocity(Entity shooter, float pitch, float yaw, float roll, float speed, float divergence) {
        float f = -MathHelper.sin(yaw * 0.017453292F) * MathHelper.cos(pitch * 0.017453292F);
        float g = -MathHelper.sin((pitch + roll) * 0.017453292F);
        float h = MathHelper.cos(yaw * 0.017453292F) * MathHelper.cos(pitch * 0.017453292F);
        this.setVelocity(f, g, h, speed, divergence);
        this.vecX = shooter.getX();
        this.vecY = shooter.getEyeY();
        this.vecZ = shooter.getZ();
        Vec3d vec3d = shooter.getVelocity();
        this.setVelocity(this.getVelocity().add(vec3d.x, shooter.isOnGround() ? 0.0D : vec3d.y, vec3d.z));
    }
    protected void onCollision(HitResult hitResult) {
        HitResult.Type type = hitResult.getType();
        if (type == HitResult.Type.ENTITY) {
            this.onEntityHit((EntityHitResult) hitResult);
        } else if (type == HitResult.Type.BLOCK) {
            BlockHitResult blockHitResult = (BlockHitResult) hitResult;
            if (!world.getBlockState(blockHitResult.getBlockPos()).isIn(this.NON_COLLIDE)) {
                this.onBlockHit(blockHitResult);
                this.remove(RemovalReason.DISCARDED);
            }
        }
    }
    public double getDamage() {
        double distance = Math.sqrt(this.getBlockPos().getSquaredDistance(new Vec3d(this.vecX, this.vecY, this.vecZ)));
        distance = MathHelper.clamp(distance, 7.8, 45);
        System.out.println(distance);
        return 15*Math.sin((distance*Math.PI)/50);
    }
    protected float getDragInWater() { return 1.0F; }
    public boolean hasNoGravity() { return true; }
    protected void onEntityHit(EntityHitResult entityHitResult) {
        Entity entity = entityHitResult.getEntity();
        int i = (int) this.getDamage();
        Entity entity2 = this.getOwner();
        if (entity != entity2) {
            DamageSource damageSource;
            if (entity2 == null) {
                damageSource = DamageSource.arrow(this, this);
            } else {
                damageSource = DamageSource.arrow(this, entity2);
                if (entity2 instanceof LivingEntity) {
                    ((LivingEntity) entity2).onAttacking(entity);
                }
            }
            boolean bl = entity.getType() == EntityType.ENDERMAN;
            int j = entity.getFireTicks();
            if (this.isOnFire() && !bl) {
                entity.setOnFireFor(5);
            }

            if (entity.damage(damageSource, (float) i)) {
                if (bl) {
                    return;
                }

                if (entity instanceof LivingEntity livingEntity) {
                    if (!this.world.isClient && entity2 instanceof LivingEntity) {
                        EnchantmentHelper.onUserDamaged(livingEntity, entity2);
                        EnchantmentHelper.onTargetDamaged((LivingEntity) entity2, livingEntity);
                    }
                    this.onHit(livingEntity);
                    if (livingEntity instanceof PlayerEntity && entity2 instanceof ServerPlayerEntity && !this.isSilent()) {
                        ((ServerPlayerEntity) entity2).networkHandler.sendPacket(new GameStateChangeS2CPacket(GameStateChangeS2CPacket.PROJECTILE_HIT_PLAYER, 0.0F));
                    }
                }

                this.playSound(this.getSound(), 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
            } else {
                entity.setFireTicks(j);
                if (!this.world.isClient && this.getVelocity().lengthSquared() < 1.0E-7D) {
                    this.discard();
                }
            }
        }
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
