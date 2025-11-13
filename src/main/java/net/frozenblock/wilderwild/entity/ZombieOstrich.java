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
import net.frozenblock.wilderwild.tag.WWItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ZombieOstrich extends AbstractOstrich {

	public ZombieOstrich(EntityType<? extends ZombieOstrich> entityType, Level level) {
		super(entityType, level);
	}

	@NotNull
	public static AttributeSupplier.Builder createAttributes() {
		return Animal.createAnimalAttributes()
			.add(Attributes.MAX_HEALTH, 20D)
			.add(Attributes.MOVEMENT_SPEED, 0.275F)
			.add(Attributes.STEP_HEIGHT, 1.5D)
			.add(Attributes.ATTACK_DAMAGE, MAX_ATTACK_DAMAGE_ZOMBIE);
	}

	public static boolean checkZombieOstrichSpawnRules(
		EntityType<? extends ZombieOstrich> ostrich,
		@NotNull ServerLevelAccessor level,
		EntitySpawnReason spawnType,
		@NotNull BlockPos pos,
		RandomSource random
	) {
		if (!EntitySpawnReason.isSpawner(spawnType) && !WWEntityConfig.get().ostrich.spawnZombieOstriches) return false;
		return Monster.checkMonsterSpawnRules(ostrich, level, spawnType, pos, random);
	}

	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(
		ServerLevelAccessor level, DifficultyInstance difficultyInstance, EntitySpawnReason reason, @Nullable SpawnGroupData spawnGroupData
	) {
		if (reason == EntitySpawnReason.NATURAL) {
			final Zombie zombie = EntityType.ZOMBIE.create(this.level(), EntitySpawnReason.JOCKEY);
			if (zombie != null) {
				zombie.snapTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0F);
				zombie.finalizeSpawn(level, difficultyInstance, reason, null);
				zombie.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SPEAR));
				zombie.startRiding(this, false, false);
			}
		}

		return super.finalizeSpawn(level, difficultyInstance, reason, spawnGroupData);
	}

	@NotNull
	@Override
	@SuppressWarnings("unchecked")
	public Brain<AbstractOstrich> makeBrain(@NotNull Dynamic<?> dynamic) {
		return (Brain<AbstractOstrich>) OstrichAi.makeBrain(this, this.brainProvider().makeBrain(dynamic), true);
	}

	@Override
	public boolean canBeLeashed() {
		return this.isTamed() || !this.isMobControlled();
	}

	@Override
	public boolean isFood(@NotNull ItemStack stack) {
		return stack.is(WWItemTags.ZOMBIE_OSTRICH_FOOD);
	}

	@Override
	public boolean removeWhenFarAway(double distance) {
		return true;
	}

	@Override
	public boolean isMobControlled() {
		return this.getFirstPassenger() instanceof Mob;
	}

	@Override
	public InteractionResult interact(Player player, InteractionHand hand) {
		this.setPersistenceRequired();
		return super.interact(player, hand);
	}

	@Override
	public boolean canUseSlot(EquipmentSlot slot) {
		return true;
	}

	@Override
	public float chargeSpeedModifier() {
		return 1.75F;
	}

	@Nullable
	@Override
	public ZombieOstrich getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob otherParent) {
		return WWEntityTypes.ZOMBIE_OSTRICH.create(level, EntitySpawnReason.BREEDING);
	}

	// TODO: CUSTOM SOUNDS

	@Nullable
	@Override
	public SoundEvent getAmbientSound() {
		if (this.isAggressive()) return this.random.nextBoolean() ? WWSounds.ENTITY_OSTRICH_HISS : WWSounds.ENTITY_OSTRICH_GRUNT;
		return WWSounds.ENTITY_OSTRICH_IDLE;
	}

	@Nullable
	@Override
	public SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
		return WWSounds.ENTITY_OSTRICH_HURT;
	}

	@Nullable
	@Override
	public SoundEvent getDeathSound() {
		return WWSounds.ENTITY_OSTRICH_DEATH;
	}

}
