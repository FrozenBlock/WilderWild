package net.frozenblock.wilderwild.entity.ai.crab;

import com.google.common.collect.ImmutableMap;
import net.frozenblock.wilderwild.entity.Crab;
import net.frozenblock.wilderwild.registry.RegisterMemoryModuleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import org.jetbrains.annotations.NotNull;

public class CrabDig<E extends Crab> extends Behavior<E> {
	public CrabDig(int duration) {
		super(ImmutableMap.of(MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_ABSENT, MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT), duration);
	}

	@Override
	protected boolean canStillUse(ServerLevel level, E entity, long gameTime) {
		return true;
	}

	@Override
	protected boolean checkExtraStartConditions(ServerLevel level, @NotNull E owner) {
		return owner.onGround();
	}

	@Override
	protected void start(ServerLevel level, @NotNull E crab, long gameTime) {
		crab.setPose(Pose.DIGGING);
		//TODO: CRAB DIG SOUNDS
		crab.playSound(SoundEvents.WARDEN_DIG, 0.25f, 3.0f);
		crab.resetDiggingTicks();
	}

	@Override
	protected void stop(ServerLevel level, @NotNull E crab, long gameTime) {
		crab.getBrain().setMemory(RegisterMemoryModuleTypes.UNDERGROUND, true);
		crab.getBrain().setMemoryWithExpiry(MemoryModuleType.DIG_COOLDOWN, Unit.INSTANCE, CrabAi.getRandomEmergeCooldown(crab));
		crab.resetDiggingTicks();
	}
}
