package net.frozenblock.wilderwild.entity;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Dynamic;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.entity.ai.FireflyBrain;
import net.frozenblock.wilderwild.entity.ai.FireflyHidingGoal;
import net.frozenblock.wilderwild.misc.server.EasyPacket;
import net.frozenblock.wilderwild.registry.RegisterItems;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.frozenblock.wilderwild.tag.WilderBiomeTags;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.PathNodeType;
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
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.*;
import net.minecraft.world.biome.Biome;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

public class Firefly extends PathAwareEntity implements Flutterer {

    protected static final ImmutableList<SensorType<? extends Sensor<? super Firefly>>> SENSORS = ImmutableList.of(SensorType.NEAREST_LIVING_ENTITIES, SensorType.HURT_BY);
    protected static final ImmutableList<MemoryModuleType<?>> MEMORY_MODULES = ImmutableList.of(MemoryModuleType.PATH, MemoryModuleType.VISIBLE_MOBS, MemoryModuleType.WALK_TARGET, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryModuleType.LOOK_TARGET, MemoryModuleType.HOME);
    private static final TrackedData<Boolean> FROM_BOTTLE = DataTracker.registerData(Firefly.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> FLICKERS = DataTracker.registerData(Firefly.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Integer> AGE = DataTracker.registerData(Firefly.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Float> SCALE = DataTracker.registerData(Firefly.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Float> PREV_SCALE = DataTracker.registerData(Firefly.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<String> COLOR = DataTracker.registerData(Firefly.class, TrackedDataHandlerRegistry.STRING);


    public boolean natural;
    public boolean hasHome; //TODO: POSSIBLY HAVE DIFFERING "SAFE RANGES" INSTEAD OF BOOLEAN
    public boolean despawning;
    public int homeCheckCooldown;
    public boolean wasNamedNectar;
    public boolean shouldCheckSpawn = true;

    public Firefly(EntityType<? extends Firefly> entityType, World world) {
        super(entityType, world);
        this.setPathfindingPenalty(PathNodeType.LAVA, -1.0F);
        this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, -1.0F);
        this.setPathfindingPenalty(PathNodeType.WATER, -1.0F);
        this.setPathfindingPenalty(PathNodeType.WATER_BORDER, 16.0F);
        this.setPathfindingPenalty(PathNodeType.UNPASSABLE_RAIL, 0.0F);
        this.moveControl = new FlightMoveControl(this, 20, true);
        this.setFlickers(world.random.nextInt(5) == 0);
        this.setFlickerAge(world.random.nextBetween(0, 19));
        this.setScale(1.5F);
    }

    public static boolean canSpawn(EntityType<Firefly> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        if (world.getBiome(pos).isIn(WilderBiomeTags.FIREFLY_SPAWNABLE_DURING_DAY)) {
            return world.getLightLevel(LightType.SKY, pos) >= 6;
        }
        if (world.getBiome(pos).isIn(WilderBiomeTags.FIREFLY_SPAWNABLE_CAVE)) {
            return world.getLightLevel(LightType.SKY, pos) <= 6;
        }
        return random.nextFloat() > 0.6F && (!world.getDimension().hasFixedTime() && world.getAmbientDarkness() > 4) && world.isSkyVisible(pos);
    }

    @Nullable
    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound nbt) {
        this.natural = spawnReason == SpawnReason.NATURAL || spawnReason == SpawnReason.CHUNK_GENERATION;
        this.hasHome = true;
        FireflyBrain.rememberHome(this, this.getBlockPos());

        if (spawnReason == SpawnReason.COMMAND) {
            this.setScale(1.5F);
            this.setPrevScale(1.5F);
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
        this.dataTracker.startTracking(PREV_SCALE, 1.5F);
        this.dataTracker.startTracking(COLOR, "on");
    }

    public boolean occludeVibrationSignals() {
        return true;
    }

    protected ActionResult interactMob(PlayerEntity player, Hand hand) {
        if (!this.despawning) {
            return tryCapture(player, hand, this).orElse(super.interactMob(player, hand));
        }
        return ActionResult.PASS;
    }


    public static Optional<ActionResult> tryCapture(PlayerEntity player, Hand hand, @NotNull Firefly entity) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (itemStack.getItem() == Items.GLASS_BOTTLE && entity.isAlive()) {
            WilderWild.log("Firefly capture attempt starting @ " + entity.getBlockPos().toShortString() + " by " + player.getDisplayName().getString(), WilderWild.UNSTABLE_LOGGING);
            String color = entity.getColor();
            Optional<Item> optionalItem = Registry.ITEM.getOrEmpty(WilderWild.id(Objects.equals(color, "on") ? "firefly_bottle" : color + "_firefly_bottle"));
            Item item = RegisterItems.FIREFLY_BOTTLE;
            if (optionalItem.isPresent()) {
                item = optionalItem.get();
            }
            entity.playSound(RegisterSounds.ITEM_BOTTLE_CATCH_FIREFLY, 1.0F, 1.0F);
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

    protected Brain.Profile<Firefly> createBrainProfile() {
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

    public float getPrevScale() {
        return this.dataTracker.get(PREV_SCALE);
    }

    public void setPrevScale(float value) {
        this.dataTracker.set(PREV_SCALE, value);
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

    public Brain<Firefly> getBrain() {
        return (Brain<Firefly>) super.getBrain();
    }

    @Override
    public boolean isInAir() {
        return !this.onGround;
    }

    public boolean shouldHide() {
        if (!this.natural || this.world.getBiome(this.getBlockPos()).isIn(WilderBiomeTags.FIREFLY_SPAWNABLE_DURING_DAY)) {
            return false;
        }

        return this.getWorld().isDay() && this.getWorld().getLightLevel(LightType.SKY, this.getBlockPos()) >= 6;
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new FireflyHidingGoal(this, 2.0D, 40, 32));
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

        if (this.shouldCheckSpawn) {
            if (!this.isFromBottle()) {
                String biomeColor = FireflyBiomeColorRegistry.getBiomeColor(world.getBiome(this.getBlockPos()));
                if (biomeColor != null) {
                    this.setColor(biomeColor);
                }
            }
            this.shouldCheckSpawn = false;
        }

        boolean nectar = false;
        if (this.hasCustomName()) {
            nectar = this.getCustomName().getString().toLowerCase().contains("nectar");
        }
        if (world instanceof ServerWorld server) {
            if (nectar != wasNamedNectar) {
                if (nectar) {
                    EasyPacket.createMovingLoopingSound(server, this, RegisterSounds.ENTITY_FIREFLY_NECTAR, SoundCategory.NEUTRAL, 1.0F, 1.5F, WilderWild.id("nectar"));
                    this.wasNamedNectar = true;
                } else {
                    this.wasNamedNectar = false;
                }
            } else {
                this.wasNamedNectar = false;
            }
        }

        if (!this.isAlive()) {
            this.setNoGravity(false);
        }
        this.setFlickerAge(this.getFlickerAge() + 1);

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

        this.setPrevScale(this.getScale());

        if (this.despawning) {
            this.setScale(this.getScale() - 0.0375F);
            if (this.getScale() < 0.0F) {
                this.discard();
            }
        } else if (this.getScale() < 1.5F) {
            this.setScale(Math.max(this.getScale() + 0.0125F, 1.5F));
        }
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
                boolean dayKey = !this.world.getBiome(this.getBlockPos()).isIn(WilderBiomeTags.FIREFLY_SPAWNABLE_DURING_DAY) && this.world.isDay() && !this.world.getBiome(this.getBlockPos()).isIn(WilderBiomeTags.FIREFLY_SPAWNABLE_CAVE);
                boolean caveKey = this.world.getBiome(this.getBlockPos()).isIn(WilderBiomeTags.FIREFLY_SPAWNABLE_CAVE) && this.world.getLightLevel(LightType.SKY, this.getBlockPos()) >= 6;
                if (this.canImmediatelyDespawn(d) && Math.sqrt(d) > 18) {
                    if (dayKey) {
                        this.despawning = true;
                    } else if (caveKey) {
                        this.despawning = true;
                    }
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
        nbt.putFloat("prevScale", this.getPrevScale());
        nbt.putBoolean("despawning", this.despawning);
        nbt.putString("color", this.getColor());
        nbt.putInt("homeCheckCooldown", this.homeCheckCooldown);
        nbt.putBoolean("wasNamedNectar", this.wasNamedNectar);
        nbt.putBoolean("shouldCheckSpawn", this.shouldCheckSpawn);
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setFromBottle(nbt.getBoolean("fromBottle"));
        this.natural = nbt.getBoolean("natural");
        this.setFlickers(nbt.getBoolean("flickers"));
        this.setFlickerAge(nbt.getInt("flickerAge"));
        this.hasHome = nbt.getBoolean("hasHome");
        this.setScale(nbt.getFloat("scale"));
        this.setPrevScale(nbt.getFloat("prevScale"));
        this.despawning = nbt.getBoolean("despawning");
        this.setColor(nbt.getString("color"));
        this.homeCheckCooldown = nbt.getInt("homeCheckCooldown");
        this.wasNamedNectar = nbt.getBoolean("wasNamedNectar");
        this.shouldCheckSpawn = nbt.getBoolean("shouldCheckSpawn");
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

    public static class FireflyBiomeColorRegistry {

        public static ArrayList<Identifier> BIOMES = new ArrayList<>();
        public static ArrayList<String> COLORS = new ArrayList<>();

        public static void addBiomeColor(Identifier biome, String string) {
            BIOMES.add(biome);
            COLORS.add(string);
        }

        @Nullable
        public static String getBiomeColor(RegistryEntry<Biome> biomeEntry) {
            ArrayList<String> colors = new ArrayList<>();
            for (int i = 0; i < BIOMES.size(); ++i) {
                Identifier biomeID = BIOMES.get(i);
                if (biomeEntry.matchesId(biomeID)) {
                    colors.add(COLORS.get(i));
                }
            }
            if (colors.isEmpty()) {
                return null;
            }
            return colors.get((int) (WilderWild.random().nextDouble() * colors.size()));
        }

    }

}
