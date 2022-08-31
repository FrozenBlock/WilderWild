package net.frozenblock.wilderwild.entity;

import net.frozenblock.wilderwild.registry.RegisterEntities;
import net.frozenblock.wilderwild.registry.RegisterItems;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.PushReaction;
import org.jetbrains.annotations.NotNull;

public class JellyCloud extends Entity {
    private static final EntityDataAccessor<Float> DATA_RADIUS = SynchedEntityData.defineId(JellyCloud.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Integer> DATA_COLOR = SynchedEntityData.defineId(JellyCloud.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> DATA_WAITING = SynchedEntityData.defineId(JellyCloud.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<ParticleOptions> DATA_PARTICLE = SynchedEntityData.defineId(JellyCloud.class, EntityDataSerializers.PARTICLE);
    private int duration = 600;
    private int waitTime = 20;
    private int reapplicationDelay = 20;
    private int durationOnUse;
    private float radiusOnUse;
    private float radiusPerTick;

    public JellyCloud(EntityType<? extends JellyCloud> entityType, Level level) {
        super(entityType, level);
        this.noPhysics = true;
        this.setRadius(3.0f);
    }

    public JellyCloud(Level level, double d, double e, double f) {
        this(RegisterEntities.JELLY_CLOUD, level);
        this.setPos(d, e, f);
    }

    @Override
    protected void defineSynchedData() {
        this.getEntityData().define(DATA_COLOR, 0);
        this.getEntityData().define(DATA_RADIUS, 0.5f);
        this.getEntityData().define(DATA_WAITING, false);
        this.getEntityData().define(DATA_PARTICLE, ParticleTypes.ENTITY_EFFECT);
    }

    public void setRadius(float f) {
        if (!this.level.isClientSide) {
            this.getEntityData().set(DATA_RADIUS, Mth.clamp(f, 0.0f, 32.0f));
        }
    }

    @Override
    public void refreshDimensions() {
        double d = this.getX();
        double e = this.getY();
        double f = this.getZ();
        super.refreshDimensions();
        this.setPos(d, e, f);
    }

    public float getRadius() {
        return this.getEntityData().get(DATA_RADIUS);
    }

    public InteractionResult interact(@NotNull Player player, @NotNull InteractionHand interactionHand) {
        ItemStack itemStack = player.getItemInHand(interactionHand);
        if (itemStack.getItem() == Items.GLASS_BOTTLE) {
            Item item = RegisterItems.JELLY_BOTTLE;
            //TODO: JELLY BOTTLE FILL SOUND
            player.playSound(SoundEvents.HONEY_DRINK, 1.0F, this.random.nextFloat() * 0.2f + 0.8f);
            if (!player.isCreative()) {
                player.getItemInHand(interactionHand).shrink(1);
            }
            ItemStack bottleStack = new ItemStack(item);
            player.getInventory().placeItemBackInInventory(bottleStack);
            return InteractionResult.sidedSuccess(this.level.isClientSide);
        }
        return InteractionResult.PASS;
    }

    @Override
    public void tick() {

    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compoundTag) {
        this.tickCount = compoundTag.getInt("Age");
        this.duration = compoundTag.getInt("Duration");
        this.waitTime = compoundTag.getInt("WaitTime");
        this.reapplicationDelay = compoundTag.getInt("ReapplicationDelay");
        this.durationOnUse = compoundTag.getInt("DurationOnUse");
        this.radiusOnUse = compoundTag.getFloat("RadiusOnUse");
        this.radiusPerTick = compoundTag.getFloat("RadiusPerTick");
        this.setRadius(compoundTag.getFloat("Radius"));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compoundTag) {
        compoundTag.putInt("Age", this.tickCount);
        compoundTag.putInt("Duration", this.duration);
        compoundTag.putInt("WaitTime", this.waitTime);
        compoundTag.putInt("ReapplicationDelay", this.reapplicationDelay);
        compoundTag.putInt("DurationOnUse", this.durationOnUse);
        compoundTag.putFloat("RadiusOnUse", this.radiusOnUse);
        compoundTag.putFloat("RadiusPerTick", this.radiusPerTick);
        compoundTag.putFloat("Radius", this.getRadius());
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> entityDataAccessor) {
        if (DATA_RADIUS.equals(entityDataAccessor)) {
            this.refreshDimensions();
        }
        super.onSyncedDataUpdated(entityDataAccessor);
    }

    @Override
    public PushReaction getPistonPushReaction() {
        return PushReaction.IGNORE;
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this);
    }

    @Override
    public EntityDimensions getDimensions(Pose pose) {
        return EntityDimensions.scalable(this.getRadius() * 2.0f, 0.5f);
    }
}

