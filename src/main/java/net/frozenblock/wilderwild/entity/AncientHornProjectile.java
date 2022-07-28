package net.frozenblock.wilderwild.entity;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.block.entity.HangingTendrilBlockEntity;
import net.frozenblock.wilderwild.misc.WilderProjectileDamageSource;
import net.frozenblock.wilderwild.misc.server.EasyPacket;
import net.frozenblock.wilderwild.misc.simple_pipe_compatability.InteractionHandler;
import net.frozenblock.wilderwild.registry.*;
import net.frozenblock.wilderwild.tag.WilderBlockTags;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.Angriness;
import net.minecraft.entity.mob.WardenEntity;
import net.minecraft.entity.player.ItemCooldownManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.s2c.play.GameStateChangeS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.GameRules;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.explosion.Explosion;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

import static net.frozenblock.wilderwild.item.AncientHorn.*;

//TODO: Fix rendering (Renders too bright or too dark depending on direction; renders under other translucents like water, doesn't render further than 8 block away)

public class AncientHornProjectile extends PersistentProjectileEntity {
    private final TagKey<Block> NON_COLLIDE = WilderBlockTags.ANCIENT_HORN_NON_COLLIDE;
    private boolean shot;
    private boolean leftOwner;
    public int aliveTicks;
    public double vecX;
    public double vecY;
    public double vecZ;
    public boolean shotByPlayer;
    public int bubbles;
    private BlockState inBlockState;

    public AncientHornProjectile(@NotNull EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
        this.setSound(RegisterSounds.ANCIENT_HORN_PROJECTILE_DISSIPATE);
    }

    public AncientHornProjectile(World world, double x, double y, double z) {
        super(RegisterEntities.ANCIENT_HORN_PROJECTILE_ENTITY, x, y, z, world);
        this.setSound(RegisterSounds.ANCIENT_HORN_PROJECTILE_DISSIPATE);
    }

    public List<Entity> collidingEntities() {
        return world.getOtherEntities(this, this.getBoundingBox().stretch(this.getVelocity()).expand(1.0D), this::canHit);
    }

    public boolean shouldRender(double cameraX, double cameraY, double cameraZ) {
        return true;
    }

    public void tick() {
        this.baseTick();
        if (this.bubbles > 0 && this.world instanceof ServerWorld server) {
            --this.bubbles;
            EasyPacket.EasyFloatingSculkBubblePacket.createParticle(server, this.getPos(), Math.random() > 0.7 ? 1 : 0, 20 + WilderWild.random().nextInt(40), 0.05, server.random.nextBetween(1, 3));
        }
        if (this.aliveTicks > 300) {
            this.remove(RemovalReason.DISCARDED);
        }
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
            this.setYaw((float) (MathHelper.atan2(vec3d.x, vec3d.z) * 57.2957763671875D));
            this.setPitch((float) (MathHelper.atan2(vec3d.y, d) * 57.2957763671875D));
            this.prevYaw = this.getYaw();
            this.prevPitch = this.getPitch();
        }
        BlockPos blockPos = this.getBlockPos();
        BlockState blockState = this.world.getBlockState(blockPos);
        Vec3d vec3d2;

        if (this.shake > 0) {
            --this.shake;
        }

        if (this.isTouchingWater() && world instanceof ServerWorld server) {
            EasyPacket.EasyFloatingSculkBubblePacket.createParticle(server, new Vec3d(this.prevX, this.prevY, this.prevZ), 0, 60, 0.05, 4);
        }
        if (this.isTouchingWaterOrRain() || blockState.isOf(Blocks.POWDER_SNOW)) {
            this.extinguish();
        }
        Vec3d vec3d3 = this.getPos();
        vec3d2 = vec3d3.add(vec3d);
        HitResult hitResult = this.world.raycast(new RaycastContext(vec3d3, vec3d2, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, this));
        while (!this.isRemoved() && canInteract()) {
            List<Entity> entities = this.collidingEntities();
            Entity owner = this.getOwner();
            for (Entity entity : entities) {
                if (!this.isRemoved() && entity != null && entity != owner) {
                    boolean shouldDamage = true;
                    if (entity instanceof PlayerEntity player) {
                        if (player.isCreative()) {
                            shouldDamage = false;
                        }
                        if (owner instanceof PlayerEntity && !((PlayerEntity) owner).shouldDamagePlayer((PlayerEntity) entity)) {
                            shouldDamage = false;
                        }
                    }
                    if (entity.isInvulnerable()) {
                        shouldDamage = false;
                    }
                    if (shouldDamage) {
                        this.hitEntity(entity);
                    }
                }
            }
            break;
        }
        if (!this.isRemoved() && hitResult != null && !bl) {
            this.onCollision(hitResult);
            if (this.isRemoved()) {
                return;
            }
            this.velocityDirty = true;
        }
        vec3d = this.getVelocity();
        double e = vec3d.x;
        double f = vec3d.y;
        double g = vec3d.z;
        if (this.isCritical()) {
            for (int i = 0; i < 4; ++i) {
                this.world.addParticle(ParticleTypes.CRIT, this.getX() + e * (double) i / 4.0D, this.getY() + f * (double) i / 4.0D, this.getZ() + g * (double) i / 4.0D, -e, -f + 0.2D, -g);
            }
        }
        double h = this.getX() + e;
        double j = this.getY() + f;
        double k = this.getZ() + g;
        double l = vec3d.horizontalLength();
        if (bl) {
            this.setYaw((float) (MathHelper.atan2(-e, -g) * 57.2957763671875D));
        } else {
            this.setYaw((float) (MathHelper.atan2(e, g) * 57.2957763671875D));
        }
        this.setPitch((float) (MathHelper.atan2(f, l) * 57.2957763671875D));
        this.setPitch(updateRotation(this.prevPitch, this.getPitch()));
        this.setYaw(updateRotation(this.prevYaw, this.getYaw()));

        this.setPosition(h, j, k);
        this.checkBlockCollision();
    }

    public void setCooldown(int cooldown) {
        Entity entity = this.getOwner();
        if (entity != null) {
            if (entity instanceof PlayerEntity user) {
                user.getItemCooldownManager().set(RegisterItems.ANCIENT_HORN, cooldown);
            }
        }
    }

    public void addCooldown(int i) {
        Entity entity = this.getOwner();
        if (entity != null) {
            if (entity instanceof PlayerEntity user) {
                if (!user.isCreative()) {
                    ItemCooldownManager manager = user.getItemCooldownManager();
                    ItemCooldownManager.Entry entry = manager.entries.get(RegisterItems.ANCIENT_HORN);
                    if (entry != null) {
                        int cooldown = (entry.endTick - entry.startTick) + i;
                        manager.remove(RegisterItems.ANCIENT_HORN);
                        manager.set(RegisterItems.ANCIENT_HORN, Math.min(600, cooldown));
                    } else {
                        manager.set(RegisterItems.ANCIENT_HORN, i);
                    }
                }
            }
        }
    }

    public boolean canHit(Entity entity) {
        if (!entity.isSpectator() && entity.isAlive() && !(entity instanceof ProjectileEntity)) {
            Entity entity2 = this.getOwner();
            return entity2 != null && (this.leftOwner || !entity2.isConnectedThroughVehicle(entity));
        } else {
            return false;
        }
    }

    public void onPlayerCollision(PlayerEntity player) {
    }

    protected void onBlockHit(BlockHitResult blockHitResult) {
        this.inBlockState = this.world.getBlockState(blockHitResult.getBlockPos());
        BlockState blockState = this.world.getBlockState(blockHitResult.getBlockPos());
        Entity owner = this.getOwner();
        if (WilderWild.isCopperPipe(blockState) && owner != null) {
            if (blockHitResult.getSide() == blockState.get(Properties.FACING).getOpposite() && this.world instanceof ServerWorld server) {
                if (InteractionHandler.addHornNbtToBlock(server, blockHitResult.getBlockPos(), owner)) {
                    this.discard();
                }
            }
        }
        blockState.onProjectileHit(this.world, blockState, blockHitResult, this);
        Vec3d vec3d = blockHitResult.getPos().subtract(this.getX(), this.getY(), this.getZ());
        this.setVelocity(vec3d);
        Vec3d vec3d2 = vec3d.normalize().multiply(0.05000000074505806D);
        this.setPos(this.getX() - vec3d2.x, this.getY() - vec3d2.y, this.getZ() - vec3d2.z);
        this.playSound(this.getSound(), 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
        this.inGround = true;
        this.shake = 7;
        this.setCritical(false);
        if (world instanceof ServerWorld server && canInteract()) {
            if (blockState.getBlock() == Blocks.SCULK_SHRIEKER) {
                BlockPos pos = blockHitResult.getBlockPos();
                WilderWild.log(Blocks.SCULK_SHRIEKER, pos, "Horn Projectile Touched", WilderWild.UNSTABLE_LOGGING);
                if (blockState.get(RegisterProperties.SOULS_TAKEN) < 2 && !blockState.get(SculkShriekerBlock.SHRIEKING)) {
                    if (!blockState.get(SculkShriekerBlock.CAN_SUMMON)) {
                        server.setBlockState(pos, blockState.with(RegisterProperties.SOULS_TAKEN, blockState.get(RegisterProperties.SOULS_TAKEN) + 1));
                    } else {
                        server.setBlockState(pos, blockState.with(SculkShriekerBlock.CAN_SUMMON, false));
                    }
                    server.spawnParticles(ParticleTypes.SCULK_SOUL, (double) pos.getX() + 0.5D, (double) pos.getY() + 1.15D, (double) pos.getZ() + 0.5D, 1, 0.2D, 0.0D, 0.2D, 0.0D);
                    trySpawnWarden(server, pos);
                    WardenEntity.addDarknessToClosePlayers(server, Vec3d.ofCenter(this.getBlockPos()), null, 40);
                    server.syncWorldEvent(WorldEvents.SCULK_SHRIEKS, pos, 0);
                    server.emitGameEvent(GameEvent.SHRIEK, pos, GameEvent.Emitter.of(owner));
                    setCooldown(getCooldown(this.getOwner(), SHRIEKER_COOLDOWN));
                    this.setSound(RegisterSounds.ANCIENT_HORN_PROJECTILE_DISSIPATE);
                    this.setShotFromCrossbow(false);
                    this.remove(RemovalReason.DISCARDED);
                }
            }
            if (blockState.getBlock() == Blocks.SCULK_SENSOR) {
                BlockPos pos = blockHitResult.getBlockPos();
                WilderWild.log(Blocks.SCULK_SENSOR, pos, "Horn Projectile Touched", WilderWild.UNSTABLE_LOGGING);
                server.setBlockState(pos, blockState.with(RegisterProperties.NOT_HICCUPPING, false));
                if (SculkSensorBlock.isInactive(blockState)) {
                    SculkSensorBlock.setActive(owner, world, pos, world.getBlockState(pos), WilderWild.random().nextInt(15));
                    world.emitGameEvent(null, GameEvent.SCULK_SENSOR_TENDRILS_CLICKING, pos);
                    world.emitGameEvent(null, RegisterGameEvents.SCULK_SENSOR_ACTIVATE, pos);
                    setCooldown(getCooldown(this.getOwner(), SENSOR_COOLDOWN));
                }
            }
        }
        this.setSound(RegisterSounds.ANCIENT_HORN_PROJECTILE_DISSIPATE);
        this.setShotFromCrossbow(false);
        this.remove(RemovalReason.DISCARDED);
    }

    private static void trySpawnWarden(ServerWorld world, BlockPos pos) {
        if (world.getGameRules().getBoolean(GameRules.DO_WARDEN_SPAWNING)) {
            Optional<WardenEntity> warden = LargeEntitySpawnHelper.trySpawnAt(EntityType.WARDEN, SpawnReason.TRIGGERED, world, pos, 20, 5, 6, LargeEntitySpawnHelper.Requirements.WARDEN);
            warden.ifPresent(wardenEntity -> wardenEntity.playSound(SoundEvents.ENTITY_WARDEN_AGITATED, 5.0F, 1.0F));
        }
    }

    protected SoundEvent getHitSound() {
        return RegisterSounds.ANCIENT_HORN_PROJECTILE_DISSIPATE;
    }

    public boolean isNoClip() {
        BlockState insideState = world.getBlockState(this.getBlockPos());
        if (insideState.isOf(RegisterBlocks.HANGING_TENDRIL) && world instanceof ServerWorld server && canInteract()) {
            BlockPos pos = this.getBlockPos();
            BlockEntity entity = world.getBlockEntity(pos);
            WilderWild.log(RegisterBlocks.HANGING_TENDRIL, pos, "Horn Projectile Touched", WilderWild.DEV_LOGGING);
            if (entity instanceof HangingTendrilBlockEntity tendril) {
                WilderWild.log("Horn Projectile Found Hanging Tendril Entity", WilderWild.UNSTABLE_LOGGING);
                this.playSound(this.getSound(), 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
                int XP = tendril.storedXP;
                if (XP > 0) {
                    tendril.storedXP = 0;
                    world.createExplosion(this, this.getX(), this.getY(), this.getZ(), 0, Explosion.DestructionType.NONE);
                    world.playSound(this.getX(), this.getY(), this.getZ(), RegisterSounds.ANCIENT_HORN_BLAST, SoundCategory.NEUTRAL, 1.0F, 1.0F, true);
                    world.breakBlock(this.getBlockPos(), false);
                    ExperienceOrbEntity.spawn(server, Vec3d.ofCenter(pos).add(0, 0, 0), XP);
                    setCooldown(getCooldown(this.getOwner(), TENDRIL_COOLDOWN));
                    this.setShotFromCrossbow(false);
                    this.remove(RemovalReason.DISCARDED);
                }
            }
        }
        if (insideState.isIn(this.NON_COLLIDE)) {
            if (world instanceof ServerWorld server) {
                if (insideState.isOf(Blocks.BELL)) {
                    ((BellBlock) insideState.getBlock()).onProjectileHit(server, insideState, this.world.raycast(new RaycastContext(this.getPos(), new Vec3d(this.getBlockX(), this.getBlockY(), this.getBlockZ()), RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, this)), this);
                }
            }
            return true;
        }
        Vec3d vec3d3 = this.getPos();
        Vec3d vec3d = this.getVelocity();
        Vec3d vec3d2 = vec3d3.add(vec3d.multiply(0.08));
        BlockHitResult hitResult = this.world.raycast(new RaycastContext(vec3d3, vec3d2, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, this));
        if (hitResult.getType() == HitResult.Type.BLOCK) {
            BlockState state = world.getBlockState(hitResult.getBlockPos());
            return state.isIn(this.NON_COLLIDE);
        }
        return false;
    }

    private boolean shouldLeaveOwner() {
        Entity entity = this.getOwner();
        if (entity != null) {
            for (Entity entity2 : this.world.getOtherEntities(this, this.getBoundingBox().stretch(this.getVelocity()).expand(1.0D), (entityx) -> !entityx.isSpectator() && entityx.isCollidable())) {
                if (entity2.getRootVehicle() == entity.getRootVehicle()) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public Packet<?> createSpawnPacket() {
        return EntitySpawnPacket.create(this, WilderWild.HORN_PROJECTILE_PACKET_ID);
    }

    public boolean canInteract() {
        return this.getOwner() != null;
    }

    @Override
    protected ItemStack asItemStack() {
        return ItemStack.EMPTY;
    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        if (!this.isRemoved()) {
            if (this.inBlockState != null) {
                nbt.put("inBlockState", NbtHelper.fromBlockState(this.inBlockState));
            }
            nbt.putInt("aliveTicks", this.aliveTicks);
            if (this.leftOwner) {
                nbt.putBoolean("LeftOwner", true);
            }
            nbt.putBoolean("HasBeenShot", this.shot);
            nbt.putDouble("originX", this.vecX);
            nbt.putDouble("originY", this.vecY);
            nbt.putDouble("originZ", this.vecZ);
            nbt.putBoolean("shotByPlayer", this.shotByPlayer);
            nbt.putInt("bubbles", this.bubbles);
        }
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        if (!this.isRemoved()) {
            if (nbt.contains("inBlockState", 10)) {
                this.inBlockState = NbtHelper.toBlockState(nbt.getCompound("inBlockState"));
            }
            this.aliveTicks = nbt.getInt("aliveTicks");
            this.leftOwner = nbt.getBoolean("LeftOwner");
            this.shot = nbt.getBoolean("HasBeenShot");
            this.vecX = nbt.getDouble("originX");
            this.vecY = nbt.getDouble("originY");
            this.vecZ = nbt.getDouble("originZ");
            this.shotByPlayer = nbt.getBoolean("shotByPlayer");
            this.bubbles = nbt.getInt("bubbles");
        }
    }

    public void setVelocity(Entity shooter, float pitch, float yaw, float roll, float speed, float divergence) {
        float f = -MathHelper.sin(yaw * 0.017453292F) * MathHelper.cos(pitch * 0.017453292F);
        float g = -MathHelper.sin((pitch + roll) * 0.017453292F);
        float h = MathHelper.cos(yaw * 0.017453292F) * MathHelper.cos(pitch * 0.017453292F);
        this.setVelocity(f, g, h, speed, divergence);
        this.vecX = shooter.getX();
        this.vecY = shooter.getEyeY();
        this.vecZ = shooter.getZ();
        this.setOwner(shooter);
    }

    protected void onCollision(HitResult hitResult) {
        HitResult.Type type = hitResult.getType();
        if (type == HitResult.Type.BLOCK) {
            BlockHitResult blockHitResult = (BlockHitResult) hitResult;
            if (!world.getBlockState(blockHitResult.getBlockPos()).isIn(this.NON_COLLIDE)) {
                this.onBlockHit(blockHitResult);
                this.remove(RemovalReason.DISCARDED);
            }
        }
    }

    public double getDamage(@Nullable Entity entity) {
        if (entity != null) {
            if (!(entity instanceof PlayerEntity)) {
                return 22;
            }
        }
        return 15;
    }

    protected float getDragInWater() {
        return 1.0F;
    }

    public boolean hasNoGravity() {
        return true;
    }

    private void hitEntity(Entity entity) {
        int i = (int) this.getDamage(entity);
        Entity entity2 = this.getOwner();
        if (entity != entity2) {
            DamageSource damageSource;
            if (entity2 == null) {
                damageSource = WilderProjectileDamageSource.ancientHorn(this, this);
            } else {
                damageSource = WilderProjectileDamageSource.ancientHorn(this, entity2);
                if (entity2 instanceof LivingEntity) {
                    ((LivingEntity) entity2).onAttacking(entity);
                }
            }
            int j = entity.getFireTicks();
            if (this.isOnFire()) {
                entity.setOnFireFor(5);
            }
            if (entity instanceof WardenEntity warden && entity2 != null && canInteract()) {
                WilderWild.log(warden, "Horn Projectile Touched", WilderWild.DEV_LOGGING);
                warden.increaseAngerAt(entity2, Angriness.ANGRY.getThreshold() + 20, true);
                warden.playSound(SoundEvents.ENTITY_WARDEN_TENDRIL_CLICKS, 5.0F, warden.getSoundPitch());
                this.discard();
            } else if (entity.damage(damageSource, (float) i)) {
                if (entity instanceof LivingEntity livingEntity) {
                    WilderWild.log(livingEntity, "Horn Projectile Touched", WilderWild.DEV_LOGGING);
                    if (!this.world.isClient && entity2 instanceof LivingEntity) {
                        EnchantmentHelper.onUserDamaged(livingEntity, entity2);
                        EnchantmentHelper.onTargetDamaged((LivingEntity) entity2, livingEntity);
                    }
                    this.onHit(livingEntity);
                    if (livingEntity instanceof PlayerEntity && entity2 instanceof ServerPlayerEntity && !this.isSilent()) {
                        ((ServerPlayerEntity) entity2).networkHandler.sendPacket(new GameStateChangeS2CPacket(GameStateChangeS2CPacket.PROJECTILE_HIT_PLAYER, 0.0F));
                    }
                    if (livingEntity.isDead() && world instanceof ServerWorld server) {
                        server.spawnParticles(ParticleTypes.SCULK_SOUL, livingEntity.getX(), livingEntity.getEyeY(), livingEntity.getZ(), 1, 0.2D, 0.0D, 0.2D, 0.0D);
                        if (this.getOwner() != null) {
                            if (this.getOwner() instanceof ServerPlayerEntity serverPlayer) {
                                addCooldown(livingEntity.getXpToDrop() * 10);
                                EasyPacket.EasyCompetitionPacket.sendAncientHornKillInfo(world, serverPlayer, livingEntity);
                            }
                        }
                    }
                }

                this.playSound(RegisterSounds.ANCIENT_HORN_PROJECTILE_DISSIPATE, 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
            } else {
                entity.setFireTicks(j);
                if (!this.world.isClient && this.getVelocity().lengthSquared() < 1.0E-7D) {
                    this.discard();
                }
            }
        }
    }

    public void emitGameEvent(GameEvent event) {
    }

    public void emitGameEvent(GameEvent event, @Nullable Entity entity) {
    }

    public static class EntitySpawnPacket { //When the Fabric tutorial WORKS!!!!! BOM BOM BOM BOM BOM BOM BOM, BOBOBOM! DUNDUN!
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