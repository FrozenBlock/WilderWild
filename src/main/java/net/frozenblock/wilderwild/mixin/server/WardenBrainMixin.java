package net.frozenblock.wilderwild.mixin.server;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.DoNothing;
import net.minecraft.world.entity.ai.behavior.RandomStroll;
import net.minecraft.world.entity.ai.behavior.RandomSwim;
import net.minecraft.world.entity.ai.behavior.RunOne;
import net.minecraft.world.entity.ai.behavior.warden.SetRoarTarget;
import net.minecraft.world.entity.ai.behavior.warden.TryToSniff;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.monster.warden.WardenAi;
import net.minecraft.world.entity.schedule.Activity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(WardenAi.class)
public class WardenBrainMixin {

    /**
     * @author FrozenBlock
     * @reason SWIMMY WARDEN
     */
    @Overwrite
    private static void initIdleActivity(Brain<Warden> brain) {
        brain.addActivity(
                Activity.IDLE,
                10,
                ImmutableList.of(
                        new SetRoarTarget<>(Warden::getEntityAngryAt),
                        new TryToSniff(),
                        new RunOne<>(
                                ImmutableMap.of(MemoryModuleType.IS_SNIFFING, MemoryStatus.VALUE_ABSENT),
                                ImmutableList.of(Pair.of(new RandomSwim(0.5F), 2), Pair.of(new RandomStroll(0.5F), 2), Pair.of(new DoNothing(30, 60), 1))
                        )
                )
        );
    }
}
