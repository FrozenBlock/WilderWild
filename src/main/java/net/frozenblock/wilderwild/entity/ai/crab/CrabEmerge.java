package net.frozenblock.wilderwild.entity.ai.crab;

import com.google.common.collect.ImmutableMap;
import net.frozenblock.wilderwild.entity.Crab;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import org.jetbrains.annotations.NotNull;

public class CrabEmerge<E extends Crab> extends Behavior<E> {
	public CrabEmerge(int duration) {
		super(ImmutableMap.of(MemoryModuleType.IS_EMERGING, MemoryStatus.VALUE_PRESENT), duration);
	}

	@Override
	protected boolean canStillUse(ServerLevel level, E entity, long gameTime) {
		return true;
	}

	@Override
	protected void start(ServerLevel level, @NotNull E crab, long gameTime) {
		crab.setPose(Pose.EMERGING);
		crab.playSound(RegisterSounds.ENTITY_CRAB_EMERGE, 0.5F, 1.0F);
		crab.resetDiggingTicks();
	}

	@Override
	protected void stop(ServerLevel level, @NotNull E crab, long gameTime) {
		if (crab.hasPose(Pose.EMERGING)) {
			crab.setPose(Pose.STANDING);
		}
		crab.resetDiggingTicks();
	}
}
