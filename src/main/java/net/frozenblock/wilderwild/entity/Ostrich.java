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
import net.frozenblock.wilderwild.config.WWEntityConfig;
import net.frozenblock.wilderwild.entity.ai.ostrich.OstrichAi;
import net.frozenblock.wilderwild.registry.WWEntityTypes;
import net.frozenblock.wilderwild.registry.WWSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;

public class Ostrich extends AbstractOstrich {

	public Ostrich(EntityType<? extends Ostrich> entityType, Level level) {
		super(entityType, level);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Animal.createAnimalAttributes()
			.add(Attributes.MAX_HEALTH, 20D)
			.add(Attributes.MOVEMENT_SPEED, 0.225F)
			.add(Attributes.STEP_HEIGHT, 1.5D)
			.add(Attributes.ATTACK_DAMAGE, MAX_ATTACK_DAMAGE);
	}

	public static boolean checkOstrichSpawnRules(EntityType<? extends Ostrich> ostrich, ServerLevelAccessor level, EntitySpawnReason reason, BlockPos pos, RandomSource random) {
		if (!EntitySpawnReason.isSpawner(reason) && !WWEntityConfig.get().ostrich.spawnOstriches) return false;
		return Animal.checkAnimalSpawnRules(ostrich, level, reason, pos, random);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Brain<AbstractOstrich> makeBrain(Dynamic<?> dynamic) {
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
	public boolean canMate(Animal otherAnimal) {
		if (otherAnimal != this && otherAnimal instanceof Ostrich ostrich) return this.canParent() && ostrich.canParent();
		return false;
	}

	@Override
	public Ostrich getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
		return WWEntityTypes.OSTRICH.create(level, EntitySpawnReason.BREEDING);
	}

	@Override
	public SoundEvent getEatingSound() {
		if (this.isInbred()) return WWSounds.ENTITY_OSTRICH_INBRED_IDLE_AH;
		return WWSounds.ENTITY_OSTRICH_EAT;
	}

	@Override
	public SoundEvent getAngrySound() {
		if (this.isInbred()) return WWSounds.ENTITY_OSTRICH_INBRED_IDLE_AH;
		return WWSounds.ENTITY_OSTRICH_GRUNT;
	}

	@Override
	public int getAmbientSoundInterval() {
		return !this.isAggressive() ? super.getAmbientSoundInterval() : 50;
	}

	@Override
	public SoundEvent getAmbientSound() {
		if (this.isInbred()) return this.random.nextFloat() <= 0.555F ? WWSounds.ENTITY_OSTRICH_INBRED_IDLE_AH : WWSounds.ENTITY_OSTRICH_INBRED_IDLE_BOCK;
		if (this.isAggressive()) return this.random.nextBoolean() ? WWSounds.ENTITY_OSTRICH_HISS : WWSounds.ENTITY_OSTRICH_GRUNT;
		return WWSounds.ENTITY_OSTRICH_IDLE;
	}

	@Override
	public SoundEvent getHurtSound(DamageSource source) {
		if (this.isInbred()) return WWSounds.ENTITY_OSTRICH_INBRED_HURT;
		return WWSounds.ENTITY_OSTRICH_HURT;
	}

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
	public void playStepSound(BlockPos pos, BlockState state) {
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
