/*
 * Copyright 2024 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.wilderwild.entity;

import com.mojang.serialization.Dynamic;
import net.frozenblock.wilderwild.entity.ai.penguin.PenguinAi;
import net.frozenblock.wilderwild.registry.WWEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.navigation.AmphibiousPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Penguin extends Animal {
	public AnimationState layDownAnimationState = new AnimationState();
	private float prevWadeProgress;
	private float wadeProgress;

	public Penguin(EntityType<? extends Animal> entityType, Level level) {
		super(entityType, level);
		this.moveControl = new SmoothSwimmingMoveControl(this, 85, 10, 0.55F, 0.2F, true);
		this.lookControl = new SmoothSwimmingLookControl(this, 20);
	}

	@Override
	protected Brain.@NotNull Provider<Penguin> brainProvider() {
		return PenguinAi.brainProvider();
	}

	@NotNull
	@Override
	@SuppressWarnings("unchecked")
	public Brain<Penguin> getBrain() {
		return (Brain<Penguin>) super.getBrain();
	}

	@NotNull
	@Override
	@SuppressWarnings("unchecked")
	public Brain<Penguin> makeBrain(@NotNull Dynamic<?> dynamic) {
		return (Brain<Penguin>) PenguinAi.makeBrain(this, this.brainProvider().makeBrain(dynamic));
	}

	@NotNull
	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 12D)
			.add(Attributes.MOVEMENT_SPEED, 1D)
			.add(Attributes.STEP_HEIGHT, 1D)
			.add(Attributes.ATTACK_DAMAGE, 2D)
			.add(Attributes.SAFE_FALL_DISTANCE, 5D)
			.add(Attributes.WATER_MOVEMENT_EFFICIENCY, 0.1D);
	}

	public static boolean checkPenguinSpawnRules(EntityType<? extends Penguin> entityType, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
		return true;
	}

	@Override
	protected @NotNull PathNavigation createNavigation(Level level) {
		return new AmphibiousPathNavigation(this, level);
	}

	@Override
	public void updateSwimming() {
		if (this.isSwimming()) {
			this.setSwimming(this.isInWater() && !this.isPassenger());
		} else {
			this.setSwimming(this.isUnderWater() && !this.isPassenger() && this.level().getFluidState(this.blockPosition()).is(FluidTags.WATER));
		}
	}

	@Override
	public void calculateEntityAnimation(boolean includeHeight) {
		super.calculateEntityAnimation(this.isSwimming() || includeHeight);
	}

	@Override
	public boolean isVisuallySwimming() {
		return this.isSwimming();
	}

	public boolean isTouchingWaterOrSwimming() {
		return this.isInWaterOrBubble() || this.isVisuallySwimming();
	}

	@Override
	public boolean isFood(@NotNull ItemStack itemStack) {
		// TODO: use a tag
		return itemStack.getItem() == Items.COD || itemStack.getItem() == Items.SALMON;
	}

	public boolean hasAttackTarget() {
		return this.getBrain().hasMemoryValue(MemoryModuleType.ATTACK_TARGET);
	}

	@Override
	public void spawnChildFromBreeding(@NotNull ServerLevel level, @NotNull Animal mate) {
		this.finalizeSpawnChildFromBreeding(level, mate, null);
		this.getBrain().setMemory(MemoryModuleType.IS_PREGNANT, Unit.INSTANCE);
	}

	public boolean isPregnant() {
		return this.getBrain().hasMemoryValue(MemoryModuleType.IS_PREGNANT);
	}

	public void revokePregnancy() {
		this.getBrain().eraseMemory(MemoryModuleType.IS_PREGNANT);
	}

	@Nullable
	@Override
	public Penguin getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob otherParent) {
		return WWEntityTypes.PENGUIN.create(level);
	}

	@Override
	public void tick() {
		super.tick();
		this.prevWadeProgress = this.wadeProgress;
		this.wadeProgress += ((this.isTouchingWaterOrSwimming() ? 1F : 0F) - this.wadeProgress) * 0.175F;
	}

	public float getWadeProgress(float partialTick) {
		return Mth.lerp(partialTick, this.prevWadeProgress, this.wadeProgress);
	}

	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> entityDataAccessor) {
		if (DATA_POSE.equals(entityDataAccessor)) {
			if (this.getPose() == Pose.SLIDING) {
				this.layDownAnimationState.start(this.tickCount);
			} else {
				this.layDownAnimationState.stop();
			}
		}

		super.onSyncedDataUpdated(entityDataAccessor);
	}

	@Override
	protected void customServerAiStep() {
		this.level().getProfiler().push("penguinBrain");
		this.getBrain().tick((ServerLevel)this.level(), this);
		this.level().getProfiler().pop();
		this.level().getProfiler().push("penguinActivityUpdate");
		PenguinAi.updateActivity(this);
		this.level().getProfiler().pop();
		super.customServerAiStep();
	}

	@Override
	protected void sendDebugPackets() {
		super.sendDebugPackets();
		DebugPackets.sendEntityBrain(this);
	}

}
