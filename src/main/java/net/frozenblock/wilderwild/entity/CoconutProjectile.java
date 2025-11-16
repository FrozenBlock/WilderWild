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

import net.frozenblock.wilderwild.registry.WWEntityTypes;
import net.frozenblock.wilderwild.registry.WWItems;
import net.frozenblock.wilderwild.registry.WWParticleTypes;
import net.frozenblock.wilderwild.registry.WWSounds;
import net.frozenblock.wilderwild.tag.WWBlockTags;
import net.frozenblock.wilderwild.tag.WWEntityTags;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

public class CoconutProjectile extends ThrowableItemProjectile {
	public static final float ENTITY_SPLIT_CHANCE = 0.7F;

	public CoconutProjectile(EntityType<? extends CoconutProjectile> entityType, Level level) {
		super(entityType, level);
	}

	public CoconutProjectile(Level level, LivingEntity shooter, ItemStack stack) {
		super(WWEntityTypes.COCONUT, shooter, level, stack);
	}

	public CoconutProjectile(Level level, double x, double y, double z, ItemStack stack) {
		super(WWEntityTypes.COCONUT, x, y, z, level, stack);
	}

	@Override
	protected Item getDefaultItem() {
		return WWItems.COCONUT;
	}

	@Override
	protected void onHitEntity(EntityHitResult hitResult) {
		super.onHitEntity(hitResult);
		final Entity entity = hitResult.getEntity();
		entity.hurt(entity.damageSources().thrown(this, this.getOwner()), 2F);

		if (!entity.getType().is(WWEntityTags.COCONUT_CANT_SPLIT)
			&& entity.getBoundingBox().getSize() > this.getBoundingBox().getSize()
			&& this.random.nextFloat() < ENTITY_SPLIT_CHANCE
		) {
			this.splitAndDiscard();
			return;
		}
		SoundEvent hitSound = WWSounds.ITEM_COCONUT_LAND;
		if (this.getY() > entity.getEyeY() && !entity.getType().is(WWEntityTags.COCONUT_CANT_BONK)) hitSound = WWSounds.ITEM_COCONUT_HIT_HEAD;

		this.level().playSound(
			null,
			this.getX(), this.getY(), this.getZ(),
			hitSound,
			this.getSoundSource(),
			1F,
			0.9F + (this.random.nextFloat() * 0.2F)
		);
	}

	@Override
	protected void onHitBlock(BlockHitResult hitResult) {
		super.onHitBlock(hitResult);
		final Level level = this.level();
		if (level.getBlockState(hitResult.getBlockPos()).is(WWBlockTags.SPLITS_COCONUT)) {
			this.splitAndDiscard();
			return;
		}
		level.playSound(
			null,
			this.getX(), this.getY(), this.getZ(),
			WWSounds.ITEM_COCONUT_LAND,
			this.getSoundSource(),
			0.4F,
			0.9F + (this.random.nextFloat() * 0.2F)
		);
		level.addFreshEntity(new ItemEntity(this.level(), this.getX(), this.getY(), this.getZ(), this.getItem()));
		this.discard();
	}

	public void splitAndDiscard() {
		if (!(this.level() instanceof ServerLevel serverLevel)) return;
		serverLevel.playSound(
			null,
			this.getX(), this.getY(), this.getZ(),
			WWSounds.ITEM_COCONUT_LAND_BREAK,
			SoundSource.BLOCKS,
			1F,
			0.9F + 0.2F * this.random.nextFloat()
		);
		for (int i = 0; i < 2; ++i) {
			serverLevel.addFreshEntity(new ItemEntity(this.level(), this.getX(), this.getY(), this.getZ(), new ItemStack(WWItems.SPLIT_COCONUT)));
		}
		final EntityDimensions dimensions = this.getDimensions(Pose.STANDING);
		serverLevel.sendParticles(
			WWParticleTypes.COCONUT_SPLASH,
			this.position().x + (dimensions.width() * 0.5D),
			this.position().y + (dimensions.height() * 0.5D),
			this.position().z + (dimensions.width() * 0.5D),
			this.random.nextInt(1, 5),
			dimensions.width() / 4F,
			dimensions.height() / 4F,
			dimensions.width() / 4F,
			0.1D
		);
		this.discard();
	}

}
