/*
 * Copyright 2025 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can modify it under
 * the terms of version 1 of the FrozenBlock Modding Oasis License
 * as published by FrozenBlock Modding Oasis.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * FrozenBlock Modding Oasis License for more details.
 *
 * You should have received a copy of the FrozenBlock Modding Oasis License
 * along with this program; if not, see <https://github.com/FrozenBlock/Licenses>.
 */

package net.frozenblock.wilderwild.entity;

import com.mojang.serialization.Dynamic;
import java.util.ArrayList;
import java.util.List;
import net.frozenblock.lib.math.api.AdvancedMath;
import net.frozenblock.lib.screenshake.api.ScreenShakeManager;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.config.WWEntityConfig;
import net.frozenblock.wilderwild.entity.ai.ostrich.OstrichAi;
import net.frozenblock.wilderwild.entity.ai.ostrich.OstrichBodyRotationControl;
import net.frozenblock.wilderwild.entity.ai.ostrich.OstrichLookControl;
import net.frozenblock.wilderwild.entity.ai.ostrich.OstrichMoveControl;
import net.frozenblock.wilderwild.registry.WWDamageTypes;
import net.frozenblock.wilderwild.registry.WWEntityTypes;
import net.frozenblock.wilderwild.registry.WWSounds;
import net.frozenblock.wilderwild.tag.WWBlockTags;
import net.frozenblock.wilderwild.tag.WWItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Unit;
import net.minecraft.util.profiling.Profiler;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.Difficulty;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityReference;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PlayerRideableJumping;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.Equippable;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Ostrich extends AbstractOstrich {

	public Ostrich(EntityType<? extends Ostrich> entityType, Level level) {
		super(entityType, level);
	}

	@NotNull
	public static AttributeSupplier.Builder createAttributes() {
		return Animal.createAnimalAttributes()
			.add(Attributes.MAX_HEALTH, 20D)
			.add(Attributes.MOVEMENT_SPEED, 0.225F)
			.add(Attributes.STEP_HEIGHT, 1.5D)
			.add(Attributes.ATTACK_DAMAGE, MAX_ATTACK_DAMAGE);
	}

	public static boolean checkOstrichSpawnRules(
		EntityType<? extends Ostrich> ostrich,
		@NotNull ServerLevelAccessor level,
		EntitySpawnReason spawnType,
		@NotNull BlockPos pos,
		RandomSource random
	) {
		if (!EntitySpawnReason.isSpawner(spawnType) && !WWEntityConfig.get().ostrich.spawnOstriches) return false;
		return Animal.checkAnimalSpawnRules(ostrich, level, spawnType, pos, random);
	}

	@NotNull
	@Override
	@SuppressWarnings("unchecked")
	public Brain<AbstractOstrich> makeBrain(@NotNull Dynamic<?> dynamic) {
		return (Brain<AbstractOstrich>) OstrichAi.makeBrain(this, this.brainProvider().makeBrain(dynamic), false);
	}

	@Override
	public boolean canSprint() {
		return true;
	}

	@Override
	public float getAdditionalSpeed() {
		return this.getFirstPassenger() instanceof Player player && player.isSprinting() && this.getJumpCooldown() == 0 ? 0.2F : 0F;
	}

	@Override
	public int getMaxTemper() {
		return 150;
	}

	@Override
	public boolean canMate(@NotNull Animal otherAnimal) {
		if (otherAnimal != this && otherAnimal instanceof Ostrich ostrich) return this.canParent() && ostrich.canParent();
		return false;
	}

	@Nullable
	@Override
	public Ostrich getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob otherParent) {
		return WWEntityTypes.OSTRICH.create(level, EntitySpawnReason.BREEDING);
	}

	@Nullable
	@Override
	public SoundEvent getEatingSound() {
		if (this.isInbred()) return WWSounds.ENTITY_OSTRICH_INBRED_IDLE_AH;
		return WWSounds.ENTITY_OSTRICH_EAT;
	}

	@Nullable
	@Override
	public SoundEvent getAngrySound() {
		if (this.isInbred()) return WWSounds.ENTITY_OSTRICH_INBRED_IDLE_AH;
		return WWSounds.ENTITY_OSTRICH_GRUNT;
	}

	@Override
	public int getAmbientSoundInterval() {
		return !this.isAggressive() ? super.getAmbientSoundInterval() : 50;
	}

	@Nullable
	@Override
	public SoundEvent getAmbientSound() {
		if (this.isInbred()) return this.random.nextFloat() <= 0.555F ? WWSounds.ENTITY_OSTRICH_INBRED_IDLE_AH : WWSounds.ENTITY_OSTRICH_INBRED_IDLE_BOCK;
		if (this.isAggressive()) return this.random.nextBoolean() ? WWSounds.ENTITY_OSTRICH_HISS : WWSounds.ENTITY_OSTRICH_GRUNT;
		return WWSounds.ENTITY_OSTRICH_IDLE;
	}

	@Nullable
	@Override
	public SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
		if (this.isInbred()) return WWSounds.ENTITY_OSTRICH_INBRED_HURT;
		return WWSounds.ENTITY_OSTRICH_HURT;
	}

	@Nullable
	@Override
	public SoundEvent getDeathSound() {
		if (this.isInbred()) return WWSounds.ENTITY_OSTRICH_INBRED_DEATH;
		return WWSounds.ENTITY_OSTRICH_DEATH;
	}

	@Override
	public SoundEvent getBeakSwingSound() {
		if (this.isInbred()) return WWSounds.ENTITY_OSTRICH_INBRED_SWING;
		return super.getBeakStuckSound();
	}

	@Override
	public SoundEvent getBeakStuckSound() {
		if (this.isInbred()) return WWSounds.ENTITY_OSTRICH_INBRED_BEAK_STUCK;
		return super.getBeakStuckSound();
	}

	@Override
	public void playStepSound(@NotNull BlockPos pos, @NotNull BlockState state) {
		if (this.isInbred()) {
			this.playSound(WWSounds.ENTITY_OSTRICH_INBRED_STEP, 0.5F, 0.9F + this.random.nextFloat() * 0.2F);
			return;
		}
		super.playStepSound(pos, state);
	}

	@SuppressWarnings("DataFlowIssue")
	@Override
	public boolean isInbred() {
		return this.hasCustomName() && this.getCustomName().getString().equalsIgnoreCase("shadownite64");
	}
}
