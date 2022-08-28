package net.frozenblock.wilderwild.entity.ai;

import com.mojang.serialization.Dynamic;
import net.frozenblock.wilderwild.entity.Jellyfish;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;

import java.util.List;

public class JellyfishAi {

    private static final List<SensorType<? extends Sensor<? super Jellyfish>>> SENSOR_TYPES = List.of(SensorType.NEAREST_PLAYERS);
    private static final List<MemoryModuleType<?>> MEMORY_TYPES = List.of(MemoryModuleType.ATTACK_TARGET, MemoryModuleType.NEAREST_PLAYERS, MemoryModuleType.NEAREST_VISIBLE_PLAYER, MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER);


    public JellyfishAi() {
    }

    public static Brain<?> create(Jellyfish jelly, Dynamic<?> dynamic) {
        Brain.Provider<Jellyfish> provider = Brain.provider(MEMORY_TYPES, SENSOR_TYPES);
        Brain<Jellyfish> brain = provider.makeBrain(dynamic);
        return brain;
    }


}
