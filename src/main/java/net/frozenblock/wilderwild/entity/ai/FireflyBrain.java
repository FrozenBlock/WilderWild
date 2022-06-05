package net.frozenblock.wilderwild.entity.ai;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import net.frozenblock.wilderwild.entity.FireflyEntity;
import net.minecraft.block.Blocks;
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
        brain.setTaskList(Activity.IDLE, ImmutableList.of(Pair.of(2, new WalkTowardsLookTargetTask<>(FireflyBrain::getLookTarget, 4, 16, 1.0F)), Pair.of(3, new TimeLimitedTask<>(new FollowMobTask((firefly) -> true, 6.0F), UniformIntProvider.create(30, 60))), Pair.of(4, new RandomTask<>(ImmutableList.of(Pair.of(new NoPenaltyStrollTask(1.0F), 2), Pair.of(new GoTowardsLookTarget(1.0F, 3), 2), Pair.of(new WaitTask(30, 60), 1))))), ImmutableSet.of());
    }

    public static void updateActivities(FireflyEntity firefly) {
        firefly.getBrain().resetPossibleActivities(ImmutableList.of(Activity.IDLE));
    }

    public static void rememberHome(LivingEntity firefly, BlockPos pos) {
        Brain<?> brain = firefly.getBrain();
        GlobalPos globalPos = GlobalPos.create(firefly.getWorld().getRegistryKey(), pos);
        Optional<GlobalPos> optional = brain.getOptionalMemory(MemoryModuleType.HOME);
        if (optional.isEmpty()) {
            brain.remember(MemoryModuleType.HOME, globalPos);
        }

    }

    private static boolean shouldGoTowardsHome(LivingEntity firefly, Brain<?> brain, GlobalPos pos) {
        World world = firefly.getWorld();
        FireflyEntity fireflyEntity = (FireflyEntity)firefly;
        return world.getRegistryKey() == pos.getDimension() && fireflyEntity.getHome() == pos.getPos();
    }

    private static Optional<LookTarget> getLookTarget(LivingEntity firefly) {
        Brain<?> brain = firefly.getBrain();
        Optional<GlobalPos> optional = brain.getOptionalMemory(MemoryModuleType.HOME);
        if (optional.isPresent()) {
            GlobalPos globalPos = optional.get();
            if (shouldGoTowardsHome(firefly, brain, globalPos)) {
                return Optional.of(new BlockPosLookTarget(globalPos.getPos().up()));
            }
        }

        return Optional.empty();
    }
}
