package net.frozenblock.wilderwild.entity.ai.crab;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class CrabAi {
    private CrabAi() {}

    @NotNull
    public static Brain<?> makeBrain(@NotNull Crab crab, @NotNull Brain<Crab> brain) {
        addCoreActivities(brain);
        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.useDefaultActivity();
        return brain;
    }

    private static void addCoreActivities(@NotNull Brain<Crab> brain) {
        brain.addActivity(
            Activity.CORE,
            0,
            ImmutableList.of(
                new LookAtTargetSink(45, 90),
                new MoveToTargetSink()
            )
        );
    }
}