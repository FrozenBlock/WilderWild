package net.frozenblock.wilderwild.entity;

import com.google.common.collect.ImmutableList;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Dynamic;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.entity.ai.FireflyBrain;
import net.frozenblock.wilderwild.registry.RegisterItems;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.DebugInfoSender;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.slf4j.Logger;

import java.util.Optional;

public class FireflyEntity extends PathAwareEntity implements Flutterer {

    protected static final ImmutableList<SensorType<? extends Sensor<? super FireflyEntity>>> SENSORS = ImmutableList.of(SensorType.NEAREST_LIVING_ENTITIES, SensorType.HURT_BY);
    protected static final ImmutableList<MemoryModuleType<?>> MEMORY_MODULES = ImmutableList.of(MemoryModuleType.PATH, MemoryModuleType.VISIBLE_MOBS, MemoryModuleType.WALK_TARGET, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
    private static final TrackedData<Boolean> FROM_BOTTLE = DataTracker.registerData(FireflyEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    public boolean flickers;
    public int fakeAge;
    public boolean valuesSet;

    public FireflyEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
        this.moveControl = new FlightMoveControl(this, 20, true);
    }

    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(FROM_BOTTLE, false);
    }

    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        return tryCapture(player, hand, this).orElse(super.interactMob(player, hand));
    }

    public static Optional<ActionResult> tryCapture(PlayerEntity player, Hand hand, FireflyEntity entity) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (itemStack.getItem() == Items.GLASS_BOTTLE && entity.isAlive()) {
            WilderWild.log("Firefly capture attempt starting @ " + entity.getBlockPos().toShortString() + " by " + player.getDisplayName().getString(), WilderWild.UNSTABLE_LOGGING);
            //TODO: FIREFLY BOTTLE SOUNDS
            entity.playSound(SoundEvents.ITEM_BOTTLE_FILL, 1.0F, 1.0F);
            ItemStack itemStack2 = new ItemStack(RegisterItems.FIREFLY_BOTTLE);
            ItemStack itemStack3 = ItemUsage.exchangeStack(itemStack, player, itemStack2, true);
            player.setStackInHand(hand, itemStack3);
            World world = entity.world;
            entity.discard();
            return Optional.of(ActionResult.success(world.isClient));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public boolean canBeLeashedBy(PlayerEntity player) { return false; }

    protected Brain.Profile<FireflyEntity> createBrainProfile() {
        return Brain.createProfile(MEMORY_MODULES, SENSORS);
    }

    protected Brain<?> deserializeBrain(Dynamic<?> dynamic) {
        return FireflyBrain.create(this.createBrainProfile().deserialize(dynamic));
    }

    public boolean isFromBottle() {
        return this.dataTracker.get(FROM_BOTTLE);
    }

    public void setFromBottle(boolean value) {
        this.dataTracker.set(FROM_BOTTLE, value);
    }

    public boolean cannotDespawn() {
        return super.cannotDespawn() || this.isFromBottle();
    }

    public float getPathfindingFavor(BlockPos pos, WorldView world) {
        return 0.0F;
    }

    public Brain<FireflyEntity> getBrain() {
        return (Brain<FireflyEntity>) super.getBrain();
    }

    @Override
    public boolean isInAir() {
        return !this.onGround;
    }

    public static DefaultAttributeContainer.Builder addAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 1.0D).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.08F).add(EntityAttributes.GENERIC_FLYING_SPEED, 0.08F).add(EntityAttributes.GENERIC_FOLLOW_RANGE, 32);
    }

    protected EntityNavigation createNavigation(World world) {
        BirdNavigation birdNavigation = new BirdNavigation(this, world);
        birdNavigation.setCanPathThroughDoors(false);
        birdNavigation.setCanSwim(true);
        birdNavigation.setCanEnterOpenDoors(true);
        return birdNavigation;
    }

    public void travel(Vec3d movementInput) {
        if (this.canMoveVoluntarily() || this.isLogicalSideForUpdatingMovement()) {
            if (this.isTouchingWater()) {
                this.updateVelocity(0.01F, movementInput);
                this.move(MovementType.SELF, this.getVelocity());
                this.setVelocity(this.getVelocity().multiply(0.800000011920929));
            } else if (this.isInLava()) {
                this.updateVelocity(0.01F, movementInput);
                this.move(MovementType.SELF, this.getVelocity());
                this.setVelocity(this.getVelocity().multiply(0.5));
            } else {
                this.updateVelocity(this.getMovementSpeed(), movementInput);
                this.move(MovementType.SELF, this.getVelocity());
                this.setVelocity(this.getVelocity().multiply(0.9100000262260437));
            }
        }

        this.updateLimbs(this, false);
    }

    protected void playStepSound(BlockPos pos, BlockState state) {
    }

    protected void fall(double heightDifference, boolean onGround, BlockState state, BlockPos landedPosition) {
    }

    protected SoundEvent getAmbientSound() {
        return null;
    }

    protected SoundEvent getHurtSound(DamageSource source) {
        return RegisterSounds.ENTITY_FIREFLY_HURT;
    }

    protected SoundEvent getDeathSound() {
        return RegisterSounds.ENTITY_FIREFLY_HURT;
    }

    @Override
    public void tick() {
        super.tick();
        if (world.isClient && !valuesSet) {
            this.fakeAge = random.nextBetween(0,19);
            this.flickers = random.nextInt(5)==0;
            this.valuesSet = true;
        }
    }

    protected void mobTick() {
        this.world.getProfiler().push("fireflyBrain");
        this.getBrain().tick((ServerWorld) this.world, this);
        this.world.getProfiler().pop();
        this.world.getProfiler().push("fireflyActivityUpdate");
        FireflyBrain.updateActivities(this);
        this.world.getProfiler().pop();
        super.mobTick();
    }

    public boolean canEquip(ItemStack stack) {
        return false;
    }

    public boolean hasWings() {
        return true;
    }

    public boolean canImmediatelyDespawn(double distanceSquared) {
        return false;
    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("fromBottle", this.isFromBottle());
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setFromBottle(nbt.getBoolean("fromBottle"));
    }

    protected boolean shouldFollowLeash() {
        return false;
    }

    protected boolean canStartRiding(Entity entity) {
        return false;
    }

    public boolean disablesShield() {
        return false;
    }

    protected void sendAiDebugData() {
        super.sendAiDebugData();
        DebugInfoSender.sendBrainDebugData(this);
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        if (world.isClient) {
            ++this.fakeAge;
        }
    }

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    protected void pushAway(Entity entity) {
    }

    protected void tickCramming() {
    }
}
