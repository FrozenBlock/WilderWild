package net.frozenblock.wilderwild.entity.ai;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import net.frozenblock.wilderwild.entity.FireflyEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.*;
import net.minecraft.entity.ai.brain.task.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.World;

import java.util.Optional;

public class FireflyBrain {

    public FireflyBrain() {
    }

    public static Brain<?> create(Brain<FireflyEntity> brain) {
        addCoreActivities(brain);
        addIdleActivities(brain);
        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.resetPossibleActivities();
        return brain;
    }

    private static void addCoreActivities(Brain<FireflyEntity> brain) {
        brain.setTaskList(Activity.CORE, 0, ImmutableList.of(new StayAboveWaterTask(0.8F), new WalkTask(2.5F), new LookAroundTask(45, 90), new WanderAroundTask()));
    }

    private static void addIdleActivities(Brain<FireflyEntity> brain) {
        brain.setTaskList(Activity.IDLE, ImmutableList.of(/*Pair.of(1, new WalkTowardsLookTargetTask<>(FireflyBrain::getHidingPlaceLookTarget, 1, 16, 1.0F)), */Pair.of(2, new WalkTowardsLookTargetTask<>(FireflyBrain::getLookTarget, 7, 16, 1.0F)), Pair.of(3, new TimeLimitedTask<>(new FollowMobTask((firefly) -> true, 6.0F), UniformIntProvider.create(30, 60))), Pair.of(4, new RandomTask<>(ImmutableList.of(Pair.of(new NoPenaltyStrollTask(1.0F), 2), Pair.of(new GoTowardsLookTarget(1.0F, 3), 2), Pair.of(new WaitTask(30, 60), 1))))), ImmutableSet.of());
    }

    public static void updateActivities(FireflyEntity firefly) {
        firefly.getBrain().resetPossibleActivities(ImmutableList.of(Activity.IDLE));
    }

    public static BlockPos getHome(FireflyEntity firefly) {
        Optional<GlobalPos> optional = firefly.getBrain().getOptionalMemory(MemoryModuleType.HOME);
        return optional.map(GlobalPos::getPos).orElse(null);
    }

    public static BlockPos getHidingPlace(FireflyEntity firefly) {
        Optional<GlobalPos> optional = firefly.getBrain().getOptionalMemory(MemoryModuleType.HIDING_PLACE);
        return optional.map(GlobalPos::getPos).orElse(null);
    }

    public static boolean isInHomeDimension(FireflyEntity firefly) {
        Optional<GlobalPos> optional = firefly.getBrain().getOptionalMemory(MemoryModuleType.HOME);
        return optional.filter(globalPos -> globalPos.getDimension() == firefly.world.getRegistryKey()).isPresent();
    }

    /*public static boolean isInHidingPlaceDimension(FireflyEntity firefly) {
        Optional<GlobalPos> optional = firefly.getBrain().getOptionalMemory(MemoryModuleType.HIDING_PLACE);
        return optional.filter(globalPos -> globalPos.getDimension() == firefly.world.getRegistryKey()).isPresent();
    }*/

    public static void rememberHome(LivingEntity firefly, BlockPos pos) {
        Brain<?> brain = firefly.getBrain();
        GlobalPos globalPos = GlobalPos.create(firefly.getWorld().getRegistryKey(), pos);
        brain.remember(MemoryModuleType.HOME, globalPos);
    }

    /*public static void rememberHidingPlace(LivingEntity firefly, BlockPos pos) {
        Brain<?> brain = firefly.getBrain();
        GlobalPos globalPos = GlobalPos.create(firefly.getWorld().getRegistryKey(), pos);
        brain.remember(MemoryModuleType.HIDING_PLACE, globalPos);
    }*/

    /*private static boolean shouldGoToHidingPlace(LivingEntity firefly, GlobalPos pos) {
        World world = firefly.getWorld();
        return ((FireflyEntity) firefly).hasHidingPlace && world.getRegistryKey() == pos.getDimension() && world.isDay();
    }*/

    private static boolean shouldGoTowardsHome(LivingEntity firefly, GlobalPos pos) {
        World world = firefly.getWorld();
        return ((FireflyEntity) firefly).hasHome && world.getRegistryKey() == pos.getDimension() && !((FireflyEntity) firefly).shouldHide();
    }

    /*private static Optional<LookTarget> getHidingPlaceLookTarget(LivingEntity firefly) {
        Brain<?> brain = firefly.getBrain();
        Optional<GlobalPos> hiding = brain.getOptionalMemory(MemoryModuleType.HIDING_PLACE);
        if (hiding.isPresent()) {
            GlobalPos hidingPos = hiding.get();
            if (shouldGoToHidingPlace(firefly, hidingPos)) {
                return Optional.of(new BlockPosLookTarget(hidingPos.getPos()));
            }
        }
        return Optional.empty();
    }*/

    private static Optional<LookTarget> getLookTarget(LivingEntity firefly) {
        Brain<?> brain = firefly.getBrain();
        Optional<GlobalPos> home = brain.getOptionalMemory(MemoryModuleType.HOME);
        if (home.isPresent()) {
            GlobalPos globalPos = home.get();
            if (shouldGoTowardsHome(firefly, globalPos)) {
                return Optional.of(new BlockPosLookTarget(randomPosAround(globalPos.getPos(), firefly.world)));
            }
        }

        return Optional.empty();
    }

    private static BlockPos randomPosAround(BlockPos pos, World world) {
        return pos.add(world.random.nextBetween(-7, 7), world.random.nextBetween(-7, 7), world.random.nextBetween(-7, 7));
    }
}
