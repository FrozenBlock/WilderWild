package net.frozenblock.wilderwild.mixin;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.*;
import net.minecraft.entity.mob.WardenBrain;
import net.minecraft.entity.mob.WardenEntity;
import org.spongepowered.asm.mixin.*;

import java.util.List;

@Mixin(WardenBrain.class)
public class WardenBrainMixin {

    @Shadow
    @Final
    @Mutable
    private static final List<MemoryModuleType<?>> MEMORY_MODULES = List.of(
            MemoryModuleType.MOBS,
            MemoryModuleType.VISIBLE_MOBS,
            MemoryModuleType.NEAREST_VISIBLE_PLAYER,
            MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER,
            MemoryModuleType.NEAREST_VISIBLE_NEMESIS,
            MemoryModuleType.LOOK_TARGET,
            MemoryModuleType.WALK_TARGET,
            MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
            MemoryModuleType.PATH,
            MemoryModuleType.ATTACK_TARGET,
            MemoryModuleType.ATTACK_COOLING_DOWN,
            MemoryModuleType.NEAREST_ATTACKABLE,
            MemoryModuleType.ROAR_TARGET,
            MemoryModuleType.DISTURBANCE_LOCATION,
            MemoryModuleType.RECENT_PROJECTILE,
            MemoryModuleType.IS_SNIFFING,
            MemoryModuleType.IS_EMERGING,
            MemoryModuleType.ROAR_SOUND_DELAY,
            MemoryModuleType.DIG_COOLDOWN,
            MemoryModuleType.ROAR_SOUND_COOLDOWN,
            MemoryModuleType.SNIFF_COOLDOWN,
            MemoryModuleType.TOUCH_COOLDOWN,
            MemoryModuleType.VIBRATION_COOLDOWN,
            MemoryModuleType.SONIC_BOOM_COOLDOWN,
            MemoryModuleType.SONIC_BOOM_SOUND_COOLDOWN,
            MemoryModuleType.SONIC_BOOM_SOUND_DELAY
    );

    /**
     * @author FrozenBlock
     * @reason SWIMMY WARDEN
     */
    @Overwrite
    private static void addIdleActivities(Brain<WardenEntity> brain) {
        brain.setTaskList(
                Activity.IDLE,
                10,
                ImmutableList.of(
                        new FindRoarTargetTask<>(WardenEntity::getPrimeSuspect),
                        new StartSniffingTask(),
                        new RandomTask<>(
                                ImmutableMap.of(MemoryModuleType.IS_SNIFFING, MemoryModuleState.VALUE_ABSENT),
                                ImmutableList.of(Pair.of(new AquaticStrollTask(0.5F), 2), Pair.of(new StrollTask(0.5F), 2), Pair.of(new WaitTask(30, 60), 1))
                        )
                )
        );
    }
}
