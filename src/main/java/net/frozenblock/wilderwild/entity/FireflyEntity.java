package net.frozenblock.wilderwild.entity;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Dynamic;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.entity.ai.FireflyBrain;
import net.frozenblock.wilderwild.misc.server.EasyPacket;
import net.frozenblock.wilderwild.registry.RegisterItems;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.frozenblock.wilderwild.tag.WildBiomeTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
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
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.DebugInfoSender;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;

public class FireflyEntity extends PathAwareEntity implements Flutterer {

    protected static final ImmutableList<SensorType<? extends Sensor<? super FireflyEntity>>> SENSORS = ImmutableList.of(SensorType.NEAREST_LIVING_ENTITIES, SensorType.HURT_BY);
    protected static final ImmutableList<MemoryModuleType<?>> MEMORY_MODULES = ImmutableList.of(MemoryModuleType.PATH, MemoryModuleType.VISIBLE_MOBS, MemoryModuleType.WALK_TARGET, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryModuleType.HOME, MemoryModuleType.HIDING_PLACE);
    private static final TrackedData<Boolean> FROM_BOTTLE = DataTracker.registerData(FireflyEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> FLICKERS = DataTracker.registerData(FireflyEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Integer> AGE = DataTracker.registerData(FireflyEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Float> SCALE = DataTracker.registerData(FireflyEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<String> COLOR = DataTracker.registerData(FireflyEntity.class, TrackedDataHandlerRegistry.STRING);


    public boolean natural;
    public boolean hasHome; //TODO: POSSIBLY HAVE DIFFERING "SAFE RANGES" INSTEAD OF BOOLEAN
    public boolean hasHidingPlace;
    public boolean despawning;
    public int homeCheckCooldown;

    public FireflyEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
        this.moveControl = new FlightMoveControl(this, 20, true);
        this.setFlickers(world.random.nextInt(5) == 0);
        this.setFlickerAge(world.random.nextBetween(0, 19));
        this.setScale(1.5F);
        this.setColor("on");
    }

    public static boolean canSpawn(EntityType<FireflyEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        if (world.getBiome(pos).isIn(WildBiomeTags.FIREFLY_SPAWNABLE_DURING_DAY)) {
            return world.isSkyVisible(pos);
        }
        return random.nextFloat() > 0.6F && (!world.getDimension().hasFixedTime() && world.getAmbientDarkness() > 4) && world.isSkyVisible(pos);
    }

    @Nullable
    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound nbt) {
        this.natural = spawnReason == SpawnReason.NATURAL;
        this.hasHome = true;
        FireflyBrain.rememberHome(this, this.getBlockPos());
        Box box = new Box(this.getBlockPos().add(-10, -10, -10), this.getBlockPos().add(10, 10, 10));
        Iterator<BlockPos> var4 = BlockPos.iterate(this.getBlockPos().add(-10, -10, -10), this.getBlockPos().add(10, 10, 10)).iterator();
        BlockPos blockPos = var4.next();
        BlockState hidingPos = world.getBlockState(blockPos);
        if (hidingPos.isOf(Blocks.GRASS) || hidingPos.isOf(Blocks.TALL_GRASS)) {
            FireflyBrain.rememberHidingPlace(this, blockPos);
        }
        // help how do i find the nearest grass or tall grass
        if (spawnReason == SpawnReason.COMMAND) {
            this.setScale(1.5F);
            this.setColor("on");
        }
        return super.initialize(world, difficulty, spawnReason, entityData, nbt);
    }

    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(FROM_BOTTLE, false);
        this.dataTracker.startTracking(FLICKERS, false);
        this.dataTracker.startTracking(AGE, 0);
        this.dataTracker.startTracking(SCALE, 1.5F);
        this.dataTracker.startTracking(COLOR, "on");
    }

    protected ActionResult interactMob(PlayerEntity player, Hand hand) {
        if (!this.despawning) {
            return tryCapture(player, hand, this).orElse(super.interactMob(player, hand));
        }
        return ActionResult.PASS;
    }


    public static Optional<ActionResult> tryCapture(PlayerEntity player, Hand hand, @NotNull FireflyEntity entity) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (itemStack.getItem() == Items.GLASS_BOTTLE && entity.isAlive()) {
            WilderWild.log("Firefly capture attempt starting @ " + entity.getBlockPos().toShortString() + " by " + player.getDisplayName().getString(), WilderWild.UNSTABLE_LOGGING);
            //TODO: FIREFLY BOTTLE SOUNDS
            String color = entity.getColor();
            Optional<Item> optionalItem = Registry.ITEM.getOrEmpty(WilderWild.id(Objects.equals(color, "on") ? "firefly_bottle" : color + "_firefly_bottle"));
            Item item = RegisterItems.FIREFLY_BOTTLE;
            if (optionalItem.isPresent()) {
                item = optionalItem.get();
            }
            entity.playSound(SoundEvents.ITEM_BOTTLE_FILL, 1.0F, 1.0F);
            if (!player.isCreative()) {
                player.getStackInHand(hand).decrement(1);
            }
            ItemStack bottleStack = new ItemStack(item);
            if (entity.hasCustomName()) {
                bottleStack.setCustomName(entity.getCustomName());
            }
            player.getInventory().offerOrDrop(bottleStack);
            World world = entity.world;
            if (!world.isClient) {
                EasyPacket.EasyCompetitionPacket.sendFireflyCaptureInfo(world, player, entity);
            }
            entity.discard();
            return Optional.of(ActionResult.success(world.isClient));
        } else {
            return Optional.empty();
        }
    }

    public boolean shouldRender(double cameraX, double cameraY, double cameraZ) {
        return true;
    }

    @Override
    public boolean canBeLeashedBy(PlayerEntity player) {
        return false;
    }

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

    public boolean flickers() {
        return this.dataTracker.get(FLICKERS);
    }

    public void setFlickers(boolean value) {
        this.dataTracker.set(FLICKERS, value);
    }

    public int getFlickerAge() {
        return this.dataTracker.get(AGE);
    }

    public void setFlickerAge(int value) {
        this.dataTracker.set(AGE, value);
    }

    public float getScale() {
        return this.dataTracker.get(SCALE);
    }

    public void setScale(float value) {
        this.dataTracker.set(SCALE, value);
    }

    public String getColor() {
        return this.dataTracker.get(COLOR);
    }

    public void setColor(String value) {
        this.dataTracker.set(COLOR, value);
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
        if (!this.isAlive()) {
            this.setNoGravity(false);
        }
        this.setFlickerAge(this.getFlickerAge() + 1);
        if (this.despawning) {
            this.setScale(this.getScale() - 0.0375F);
            if (this.getScale() < 0.0F) {
                this.discard();
            }
        }
        if (this.hasHome) {
            if (this.homeCheckCooldown > 0) {
                --this.homeCheckCooldown;
            } else {
                this.homeCheckCooldown = 200;
                BlockPos home = FireflyBrain.getHome(this);
                if (home != null && FireflyBrain.isInHomeDimension(this)) {
                    if (!isValidHomePos(world, home)) {
                        FireflyBrain.rememberHome(this, this.getBlockPos());
                    }
                }
            }
        }

        if (this.hasHidingPlace) {
            BlockPos hidingPlace = FireflyBrain.getHidingPlace(this);
            if (hidingPlace != null && FireflyBrain.isInHidingPlaceDimension(this)) {
                if (!isValidHidingPlacePos(world, hidingPlace)) {
                    // help how do i find the nearest grass or tall grass
                    FireflyBrain.rememberHidingPlace(this, this.getBlockPos());
                }
            }
        }
        //WilderWild.log(this, this.getBrain().getOptionalMemory(MemoryModuleType.HOME).toString(), WilderWild.DEV_LOGGING);
    }

    public static boolean isValidHomePos(World world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        if (!state.getFluidState().isEmpty()) {
            return false;
        }
        if (state.isSolidBlock(world, pos)) {
            return false;
        }
        return state.isAir() || (!state.getMaterial().blocksMovement() && !state.getMaterial().isSolid());
    }

    public static boolean isValidHidingPlacePos(World world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        if (!state.getFluidState().isEmpty()) {
            return false;
        }

        return state.isOf(Blocks.GRASS) || state.isOf(Blocks.TALL_GRASS);
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
        return !this.isFromBottle() && !this.hasCustomName();
    }

    @Override
    public void checkDespawn() {
        if (!this.despawning) {
            if (this.world.getDifficulty() == Difficulty.PEACEFUL && this.isDisallowedInPeaceful()) {
                this.despawning = true;
                return;
            }
            if (this.isPersistent() || this.cannotDespawn()) {
                this.despawnCounter = 0;
                return;
            }
            PlayerEntity entity = this.world.getClosestPlayer(this, -1.0);
            if (entity != null) {
                int i;
                double d = entity.squaredDistanceTo(this);
                if (this.canImmediatelyDespawn(d) && !this.world.getBiome(this.getBlockPos()).isIn(WildBiomeTags.FIREFLY_SPAWNABLE_DURING_DAY) && this.world.isDay() && Math.sqrt(d) > 18) {
                    this.despawning = true;
                }
                if (d > (double) ((i = this.getType().getSpawnGroup().getImmediateDespawnRange()) * i) && this.canImmediatelyDespawn(d)) {
                    this.despawning = true;
                }
                int k = this.getType().getSpawnGroup().getDespawnStartRange();
                int l = k * k;
                if (this.despawnCounter > 600 && this.random.nextInt(800) == 0 && d > (double) l && this.canImmediatelyDespawn(d)) {
                    this.despawning = true;
                } else if (d < (double) l) {
                    this.despawnCounter = 0;
                }
            }
        }
    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("fromBottle", this.isFromBottle());
        nbt.putBoolean("natural", this.natural);
        nbt.putBoolean("flickers", this.flickers());
        nbt.putInt("flickerAge", this.getFlickerAge());
        nbt.putBoolean("hasHome", this.hasHome);
        nbt.putFloat("scale", this.getScale());
        nbt.putBoolean("despawning", this.despawning);
        nbt.putString("color", this.getColor());
        nbt.putInt("homeCheckCooldown", this.homeCheckCooldown);
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setFromBottle(nbt.getBoolean("fromBottle"));
        this.natural = nbt.getBoolean("natural");
        this.setFlickers(nbt.getBoolean("flickers"));
        this.setFlickerAge(nbt.getInt("flickerAge"));
        this.hasHome = nbt.getBoolean("hasHome");
        this.setScale(nbt.getFloat("scale"));
        this.despawning = nbt.getBoolean("despawning");
        this.setColor(nbt.getString("color"));
        this.homeCheckCooldown = nbt.getInt("homeCheckCooldown");
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
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    protected void pushAway(Entity entity) {
    }

    protected void tickCramming() {
    }

}
