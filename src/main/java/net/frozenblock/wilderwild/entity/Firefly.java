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
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;

public class Firefly extends PathfinderMob implements FlyingAnimal {

    protected static final ImmutableList<SensorType<? extends Sensor<? super Firefly>>> SENSORS = ImmutableList.of(SensorType.NEAREST_LIVING_ENTITIES, SensorType.HURT_BY);
    protected static final ImmutableList<MemoryModuleType<?>> MEMORY_MODULES = ImmutableList.of(MemoryModuleType.PATH, MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES, MemoryModuleType.WALK_TARGET, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryModuleType.LOOK_TARGET, MemoryModuleType.HOME, MemoryModuleType.HIDING_PLACE);
    private static final EntityDataAccessor<Boolean> FROM_BOTTLE = SynchedEntityData.defineId(Firefly.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> FLICKERS = SynchedEntityData.defineId(Firefly.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> AGE = SynchedEntityData.defineId(Firefly.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Float> SCALE = SynchedEntityData.defineId(Firefly.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<String> COLOR = SynchedEntityData.defineId(Firefly.class, EntityDataSerializers.STRING);


    public boolean natural;
    public boolean hasHome; //TODO: POSSIBLY HAVE DIFFERING "SAFE RANGES" INSTEAD OF BOOLEAN
    //public boolean hasHidingPlace;
    public boolean despawning;
    public int homeCheckCooldown;
    public boolean wasNamedNectar;

    //public int hidingPlaceCheckCooldown;

    public Firefly(EntityType<? extends Firefly> entityType, Level world) {
        super(entityType, world);
        this.setPathfindingMalus(BlockPathTypes.LAVA, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.WATER, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.WATER_BORDER, 16.0F);
        this.setPathfindingMalus(BlockPathTypes.UNPASSABLE_RAIL, 0.0F);
        this.moveControl = new FlyingMoveControl(this, 20, true);
        this.setFlickers(world.random.nextInt(5) == 0);
        this.setFlickerAge(world.random.nextIntBetweenInclusive(0, 19));
        this.setScale(1.5F);
        this.setColor("on");
    }

    public static boolean canSpawn(EntityType<Firefly> type, LevelAccessor world, MobSpawnType spawnReason, BlockPos pos, RandomSource random) {
        if (world.getBiome(pos).is(WilderBiomeTags.FIREFLY_SPAWNABLE_DURING_DAY)) {
            return world.getBrightness(LightLayer.SKY, pos) >= 6;
        }
        return random.nextFloat() > 0.6F && (!world.dimensionType().hasFixedTime() && world.getSkyDarken() > 4) && world.canSeeSky(pos);
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType spawnReason, @Nullable SpawnGroupData entityData, @Nullable CompoundTag nbt) {
        this.natural = spawnReason == MobSpawnType.NATURAL;
        this.hasHome = true;
        FireflyBrain.rememberHome(this, this.blockPosition());
        //this.hasHidingPlace = true;
        //FireflyBrain.rememberHidingPlace(this, this.getBlockPos());

        if (spawnReason == MobSpawnType.COMMAND) {
            this.setScale(1.5F);
            this.setColor("on");
        }

        return super.finalizeSpawn(world, difficulty, spawnReason, entityData, nbt);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(FROM_BOTTLE, false);
        this.entityData.define(FLICKERS, false);
        this.entityData.define(AGE, 0);
        this.entityData.define(SCALE, 1.5F);
        this.entityData.define(COLOR, "on");
    }

    public boolean dampensVibrations() {
        return true;
    }

    protected InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (!this.despawning) {
            return tryCapture(player, hand, this).orElse(super.mobInteract(player, hand));
        }
        return InteractionResult.PASS;
    }


    public static Optional<InteractionResult> tryCapture(Player player, InteractionHand hand, @NotNull Firefly entity) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (itemStack.getItem() == Items.GLASS_BOTTLE && entity.isAlive()) {
            WilderWild.log("Firefly capture attempt starting @ " + entity.blockPosition().toShortString() + " by " + player.getDisplayName().getString(), WilderWild.UNSTABLE_LOGGING);
            String color = entity.getColor();
            Optional<Item> optionalItem = Registry.ITEM.getOptional(WilderWild.id(Objects.equals(color, "on") ? "firefly_bottle" : color + "_firefly_bottle"));
            Item item = RegisterItems.FIREFLY_BOTTLE;
            if (optionalItem.isPresent()) {
                item = optionalItem.get();
            }
            entity.playSound(RegisterSounds.ITEM_BOTTLE_CATCH_FIREFLY, 1.0F, 1.0F);
            if (!player.isCreative()) {
                player.getItemInHand(hand).shrink(1);
            }
            ItemStack bottleStack = new ItemStack(item);
            if (entity.hasCustomName()) {
                bottleStack.setHoverName(entity.getCustomName());
            }
            player.getInventory().placeItemBackInInventory(bottleStack);
            Level world = entity.level;
            if (!world.isClientSide) {
                EasyPacket.EasyCompetitionPacket.sendFireflyCaptureInfo(world, player, entity);
            }
            entity.discard();
            return Optional.of(InteractionResult.sidedSuccess(world.isClientSide));
        } else {
            return Optional.empty();
        }
    }

    public boolean shouldRender(double cameraX, double cameraY, double cameraZ) {
        return true;
    }

    @Override
    public boolean canBeLeashed(Player player) {
        return false;
    }

    protected Brain.Provider<Firefly> brainProvider() {
        return Brain.provider(MEMORY_MODULES, SENSORS);
    }

    protected Brain<?> makeBrain(Dynamic<?> dynamic) {
        return FireflyBrain.create(this.brainProvider().makeBrain(dynamic));
    }

    public boolean isFromBottle() {
        return this.entityData.get(FROM_BOTTLE);
    }

    public void setFromBottle(boolean value) {
        this.entityData.set(FROM_BOTTLE, value);
    }

    public boolean flickers() {
        return this.entityData.get(FLICKERS);
    }

    public void setFlickers(boolean value) {
        this.entityData.set(FLICKERS, value);
    }

    public int getFlickerAge() {
        return this.entityData.get(AGE);
    }

    public void setFlickerAge(int value) {
        this.entityData.set(AGE, value);
    }

    public float getScale() {
        return this.entityData.get(SCALE);
    }

    public void setScale(float value) {
        this.entityData.set(SCALE, value);
    }

    public String getColor() {
        return this.entityData.get(COLOR);
    }

    public void setColor(String value) {
        this.entityData.set(COLOR, value);
    }

    public boolean requiresCustomPersistence() {
        return super.requiresCustomPersistence() || this.isFromBottle();
    }

    public float getWalkTargetValue(BlockPos pos, LevelReader world) {
        return 0.0F;
    }

    public Brain<Firefly> getBrain() {
        return (Brain<Firefly>) super.getBrain();
    }

    @Override
    public boolean isFlying() {
        return !this.onGround;
    }

    public boolean shouldHide() {
        if (!this.natural || this.level.getBiome(this.blockPosition()).is(WilderBiomeTags.FIREFLY_SPAWNABLE_DURING_DAY)) {
            return false;
        }

        return this.getLevel().isDay() && this.getLevel().getBrightness(LightLayer.SKY, this.blockPosition()) >= 6;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FireflyHidingGoal(this, 2.0D, 40, 32));
    }

    public static AttributeSupplier.Builder addAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 1.0D).add(Attributes.MOVEMENT_SPEED, 0.08F).add(Attributes.FLYING_SPEED, 0.08F).add(Attributes.FOLLOW_RANGE, 32);
    }

    protected PathNavigation createNavigation(Level world) {
        FlyingPathNavigation birdNavigation = new FlyingPathNavigation(this, world);
        birdNavigation.setCanOpenDoors(false);
        birdNavigation.setCanFloat(true);
        birdNavigation.setCanPassDoors(true);
        return birdNavigation;
    }

    public void travel(Vec3 movementInput) {
        if (this.isEffectiveAi() || this.isControlledByLocalInstance()) {
            if (this.isInWater()) {
                this.moveRelative(0.01F, movementInput);
                this.move(MoverType.SELF, this.getDeltaMovement());
                this.setDeltaMovement(this.getDeltaMovement().scale(0.800000011920929));
            } else if (this.isInLava()) {
                this.moveRelative(0.01F, movementInput);
                this.move(MoverType.SELF, this.getDeltaMovement());
                this.setDeltaMovement(this.getDeltaMovement().scale(0.5));
            } else {
                this.moveRelative(this.getSpeed(), movementInput);
                this.move(MoverType.SELF, this.getDeltaMovement());
                this.setDeltaMovement(this.getDeltaMovement().scale(0.9100000262260437));
            }
        }

        this.calculateEntityAnimation(this, false);
    }

    protected void playStepSound(BlockPos pos, BlockState state) {
    }

    protected void checkFallDamage(double heightDifference, boolean onGround, BlockState state, BlockPos landedPosition) {
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
        boolean nectar = false;
        if (this.hasCustomName()) {
            nectar = this.getCustomName().getString().toLowerCase().contains("nectar");
        }
        if (level instanceof ServerLevel server) {
            if (nectar != wasNamedNectar) {
                if (nectar) {
                    EasyPacket.createMovingLoopingSound(server, this, RegisterSounds.ENTITY_FIREFLY_NECTAR, SoundSource.NEUTRAL, 1.0F, 1.5F, WilderWild.id("nectar"));
                    this.wasNamedNectar = true;
                } else {
                    this.wasNamedNectar = false;
                }
            } else {
                this.wasNamedNectar = false;
            }
        }
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

        /*if (this.hasHidingPlace) {
            if (this.hidingPlaceCheckCooldown > 0) {
                --this.hidingPlaceCheckCooldown;
            } else {
                this.hidingPlaceCheckCooldown = 200;
                BlockPos hidingPlace = FireflyBrain.getHidingPlace(this);
                if (hidingPlace != null && FireflyBrain.isInHidingPlaceDimension(this)) {
                    if (!isValidHidingPlacePos(world, hidingPlace)) {
                        Iterator<BlockPos> hidingPlaceRange = BlockPos.iterateOutwards(this.getBlockPos(), 16, 16, 16).iterator();
                        BlockPos foundPos = null;
                        while (hidingPlaceRange.hasNext()) {
                            foundPos = hidingPlaceRange.next();
                            if (this.world.getBlockState(foundPos).isOf(Blocks.GRASS) || this.world.getBlockState(foundPos).isOf(Blocks.TALL_GRASS) || this.world.getBlockState(foundPos).isOf(Blocks.FERN) || this.world.getBlockState(foundPos).isOf(Blocks.LARGE_FERN)) {
                                FireflyBrain.rememberHidingPlace(this, foundPos);
                            }
                            foundPos = null;
                        }
                    }
                }
            }
        }*/

        if (this.hasHome) {
            if (this.homeCheckCooldown > 0) {
                --this.homeCheckCooldown;
            } else {
                this.homeCheckCooldown = 200;
                BlockPos home = FireflyBrain.getHome(this);
                if (home != null && FireflyBrain.isInHomeDimension(this)/* && this.getBlockPos() != FireflyBrain.getHidingPlace(this)*/) {
                    if (!isValidHomePos(level, home)) {
                        FireflyBrain.rememberHome(this, this.blockPosition());
                    }
                }
            }
        }
        //WilderWild.log(this, this.getBrain().getOptionalMemory(MemoryModuleType.HOME).toString(), WilderWild.DEV_LOGGING);
    }

    public static boolean isValidHomePos(Level world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        if (!state.getFluidState().isEmpty()) {
            return false;
        }
        if (state.isRedstoneConductor(world, pos)) {
            return false;
        }
        return state.isAir() || (!state.getMaterial().blocksMotion() && !state.getMaterial().isSolid());
    }

    /*public static boolean isValidHidingPlacePos(World world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        if (!state.getFluidState().isEmpty()) {
            return false;
        }

        return state.isOf(Blocks.GRASS) || state.isOf(Blocks.TALL_GRASS) || state.isOf(Blocks.FERN) || state.isOf(Blocks.LARGE_FERN);
    }*/

    protected void customServerAiStep() {
        this.level.getProfiler().push("fireflyBrain");
        this.getBrain().tick((ServerLevel) this.level, this);
        this.level.getProfiler().pop();
        this.level.getProfiler().push("fireflyActivityUpdate");
        FireflyBrain.updateActivities(this);
        this.level.getProfiler().pop();
        super.customServerAiStep();
    }

    public boolean canTakeItem(ItemStack stack) {
        return false;
    }

    public boolean isFlapping() {
        return true;
    }

    public boolean removeWhenFarAway(double distanceSquared) {
        return !this.isFromBottle() && !this.hasCustomName();
    }

    @Override
    public void checkDespawn() {
        if (!this.despawning) {
            if (this.level.getDifficulty() == Difficulty.PEACEFUL && this.shouldDespawnInPeaceful()) {
                this.despawning = true;
                return;
            }
            if (this.isPersistenceRequired() || this.requiresCustomPersistence()) {
                this.noActionTime = 0;
                return;
            }
            Player entity = this.level.getNearestPlayer(this, -1.0);
            if (entity != null) {
                int i;
                double d = entity.distanceToSqr(this);
                if (this.removeWhenFarAway(d) && !this.level.getBiome(this.blockPosition()).is(WilderBiomeTags.FIREFLY_SPAWNABLE_DURING_DAY) && this.level.isDay() && Math.sqrt(d) > 18) {
                    this.despawning = true;
                }
                if (d > (double) ((i = this.getType().getCategory().getDespawnDistance()) * i) && this.removeWhenFarAway(d)) {
                    this.despawning = true;
                }
                int k = this.getType().getCategory().getNoDespawnDistance();
                int l = k * k;
                if (this.noActionTime > 600 && this.random.nextInt(800) == 0 && d > (double) l && this.removeWhenFarAway(d)) {
                    this.despawning = true;
                } else if (d < (double) l) {
                    this.noActionTime = 0;
                }
            }
        }
    }

    public void addAdditionalSaveData(CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putBoolean("fromBottle", this.isFromBottle());
        nbt.putBoolean("natural", this.natural);
        nbt.putBoolean("flickers", this.flickers());
        nbt.putInt("flickerAge", this.getFlickerAge());
        nbt.putBoolean("hasHome", this.hasHome);
        nbt.putFloat("scale", this.getScale());
        nbt.putBoolean("despawning", this.despawning);
        nbt.putString("color", this.getColor());
        nbt.putInt("homeCheckCooldown", this.homeCheckCooldown);
        //nbt.putBoolean("hasHidingPlace", this.hasHidingPlace);
        //nbt.putInt("hidingPlaceCheckCooldown", this.hidingPlaceCheckCooldown);
        nbt.putBoolean("wasNamedNectar", this.wasNamedNectar);
    }

    public void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        this.setFromBottle(nbt.getBoolean("fromBottle"));
        this.natural = nbt.getBoolean("natural");
        this.setFlickers(nbt.getBoolean("flickers"));
        this.setFlickerAge(nbt.getInt("flickerAge"));
        this.hasHome = nbt.getBoolean("hasHome");
        this.setScale(nbt.getFloat("scale"));
        this.despawning = nbt.getBoolean("despawning");
        this.setColor(nbt.getString("color"));
        this.homeCheckCooldown = nbt.getInt("homeCheckCooldown");
        //this.hasHidingPlace = nbt.getBoolean("hasHidingPlace");
        //this.hidingPlaceCheckCooldown = nbt.getInt("hidingPlaceCheckCooldown");
        this.wasNamedNectar = nbt.getBoolean("wasNamedNectar");
    }

    protected boolean shouldStayCloseToLeashHolder() {
        return false;
    }

    protected boolean canRide(Entity entity) {
        return false;
    }

    public boolean canDisableShield() {
        return false;
    }

    protected void sendDebugPackets() {
        super.sendDebugPackets();
        DebugPackets.sendEntityBrain(this);
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    protected void doPush(Entity entity) {
    }

    protected void pushEntities() {
    }

}
